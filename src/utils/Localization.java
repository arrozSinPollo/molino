package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

	private static ResourceBundle instance=null;
	
	public static String getString(String key)
	{
		if (instance==null)
		{
			//El primer par�metro de Locale es el idioma y est� estandarizado en:
			//  http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt
				//Ejemplo: Espa�a: es
				//Ejemplo: Ingl�s: en
				//Ejemplo: Brazil: pt
			
			//El segundo par�metro es el pa�s y est� estandarizado aqu�:
			//  http://userpage.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
				//Ejemplo: Espa�a: ES
				//Ejemplo: USA: US
				//Ejemplo: Portugal: PT
			
			PropertiesUtil properties = new PropertiesUtil();
			Locale currentLocale = new Locale(properties.getProperty(PropertiesUtil.LanguageCode),properties.getProperty(PropertiesUtil.CountryCode));
			instance = 
		         ResourceBundle.getBundle("sambaoleLocalization",currentLocale);
		}
		return instance.getString(key);
	}

}
