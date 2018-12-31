package ch.bibbias.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * @author Christian Jeitziner
 *
 */
public class AppProperties {
	
	/**
	 * Used for logging. 
	 */
	private static final Logger LOG = getLogger(AppProperties.class);
	
	/**
	 * Singleton instance
	 */
	private static AppProperties instance;	
	
	/**
	 * Application name
	 */
	public String appName;
	
	/**
	 * Application version
	 */
	public Integer appVersion;
	
	
	/**
	 * Private constructor, AppProperties is a singleton and must be accessed
	 * with static method getInstance.
	 * 
	 * @param properties : Object that hold all properties read from .properties
	 *                     file.
	 */
	private AppProperties(Properties properties) {
		this.appName = getString(properties, "wineinventory.appName", "WineInventory Application");
		this.appVersion = getInteger(properties, "wineinventory.appVersion", 99);
	}
		
	/**
	 * Private method to initialize a instance variable using a properties
	 * object. If propName is not a key in properties, the instance variable
	 * is set to defaultValue.
	 * 
	 * @param properties
	 * @param propName
	 * @param defaultValue
	 * @return
	 */
	private String getString(Properties properties, String propName, String defaultValue)
	{
		String returnValue = properties.getProperty(propName);
		if (returnValue == null) {
			LOG.error(String.format("Property %s not found", propName));
		}
		return (returnValue != null) ? returnValue  : defaultValue; 
	}

	/**
	 * Private method to initialize a instance variable using a properties
	 * object. If propName is not a key in properties, the instance variable
	 * is set to defaultValue.
	 * 
	 * @param properties
	 * @param propName
	 * @param defaultValue
	 * @return
	 */
	private Integer getInteger(Properties properties, String propName, Integer defaultValue)
	{
		Integer intValue = null;
		try {
			String stringValue = properties.getProperty(propName);
			if (stringValue == null) {
				LOG.warn(String.format("Property %s not found", propName));
			} else {
				intValue = Integer.parseInt(stringValue);
			}
		}
		catch (NumberFormatException ex) {
			LOG.error(String.format("Property %s is not a Integer", propName));			
		}
		return (intValue != null) ? intValue  : defaultValue; 
	}
	
	/**
	 * Used to explicitly initialize the singleton AppProperties at application
	 * startup. getInstance does not need to be made thread-safe, i.e. with 
	 * the means of the double-checked locking pattern. 
	 * 
	 * @param propFilePath : The path to the .properties file.
	 */
	public static void init(InputStream propertiesStream) {
		Properties prop = new Properties();
		try {
			LOG.info(String.format("Load properties file: %s", propertiesStream.toString()));
		    prop.load(propertiesStream);
		} 
		catch (IOException ex) {
			LOG.error(String.format("Properties file not found: %s", propertiesStream.toString()));
		    ex.printStackTrace();
		}
		
		AppProperties.instance = new AppProperties(prop);
	}
	
	public static AppProperties getInstance() {
		return AppProperties.instance;
	}

}
