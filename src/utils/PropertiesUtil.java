package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import mysqldal.MySQLFormulaDal;
import mysqldal.MySQLJDBCFacade;
import domain.Formula;

public class PropertiesUtil {
	
	public static String DalFactory="DalFactory";
	public static String JDBCURL="JDBCURL";
	public static String JDBCUsername="JDBCUsername";
	public static String JDBCPassword="JDBCPassword";
	public static String TransactionFactory="TransactionFactory";
	public static String JDBCDriver="JDBCDriver";
	public static String CountryCode="CountryCode";
	public static String LanguageCode="LanguageCode";
	public static String Impresora="Impresora";
	public static String reportsPath="reportsPath";
	

    private static Properties properties = null;

    public String getProperty(String propertyName) {
            if(properties == null) {
                    properties = new Properties();
                    InputStream propFile = null;
                    try {
                            propFile = new FileInputStream("molino.properties");
                    } catch(FileNotFoundException e) {
                            propFile = getClass().getResourceAsStream("/molino.properties");
                    }
                    try {
                            properties.load(propFile);
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
            }
            return properties.getProperty(propertyName);
    }
}
