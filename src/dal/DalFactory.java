package dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import utils.PropertiesUtil;

public class DalFactory implements IDalFactory {

	static IDalFactory factory;
	
	private static DalFactory instance;
	
	private DalFactory() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		PropertiesUtil properties = new PropertiesUtil();
		String method = properties.getProperty(PropertiesUtil.DalFactory);
		Class classFactory = null;
		classFactory = Class.forName(method);
		Method methodSingleton = null;
		methodSingleton = classFactory.getMethod("getInstance",null);
		factory=(IDalFactory) methodSingleton.invoke(null,null);
		
		databaseStartup();
	}
	
	public static DalFactory getInstance() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		if (instance==null)
			instance=new DalFactory();
		
		return instance;
	}

	public IFormulaDal createFormulaDal(IDetailFormulaDal detailDal) {
		return factory.createFormulaDal(detailDal);
	}

	public IDetailFormulaDal createDetailDal() {
		return factory.createDetailDal();
	}

	public void databaseStartup() {
		factory.databaseStartup();
	}
}
