package jdbc;

import java.lang.reflect.InvocationTargetException;

import dal.ITransaction;
import dal.ITransactionFactory;

public class JDBCTransactionFactory implements ITransactionFactory{

	private static JDBCTransactionFactory instance;

	
	public static JDBCTransactionFactory getInstance() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		if (instance==null)
			instance=new JDBCTransactionFactory();
		
		return instance;
	}
	
	public ITransaction createTransaction() {
		return new JDBCTransaction();
	}

}
