package dal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import utils.PropertiesUtil;

public class TransactionFactory implements ITransactionFactory{
	static ITransactionFactory factory;
	
	private static TransactionFactory instance;
	
	private TransactionFactory() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		PropertiesUtil properties = new PropertiesUtil();
		String method = properties.getProperty(PropertiesUtil.TransactionFactory);
		Class classFactory = null;
		classFactory = Class.forName(method);
		Method methodSingleton = null;
		methodSingleton = classFactory.getMethod("getInstance",null);
		factory=(ITransactionFactory) methodSingleton.invoke(null,null);
	}
	
	public static TransactionFactory getInstance() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		if (instance==null)
			instance=new TransactionFactory();
		
		return instance;
	}
	
	public ITransaction createTransaction()
	{
		return factory.createTransaction();
	}
}
