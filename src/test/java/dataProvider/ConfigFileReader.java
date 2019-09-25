package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import enums.DriverType;
import enums.EnvironmentType;

public class ConfigFileReader {

	private Properties properties;
	private String propertyFilePath = System.getProperty("user.dir")
			+ "/config/Configuration.properties";


	public static class ConfigProp {
		public static final String DRIVER_PATH = "driverPath";
	}
	
	public ConfigFileReader() throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	public String getDriverPath() {
		String driverPath = properties.getProperty(ConfigProp.DRIVER_PATH);
		if (driverPath != null)
			return driverPath;
		else
			throw new RuntimeException("driverPath is not specified in the Configuration.properties");
	}
	
	public static Properties readPropertiesFromFile(String xPath) throws FileNotFoundException, IOException{
		Properties properties = new Properties();
		try (BufferedReader reader = new BufferedReader(new FileReader(xPath))){
			properties.load(reader);
		}
		return properties;
	}
	
	public static String validateProp(Properties properties, String propName, String defaultValue) throws IOException{
		String propValue = properties.getProperty(propName, defaultValue);
		if(StringUtils.isEmpty(propValue)){
			throw new IOException("Could not read property '" + propName + "'");
		}
		return propValue;
	}

	public DriverType getBrowser() {
		String browserName = properties.getProperty("browser");
		return DriverType.valueOf(browserName.toUpperCase());
//		if (browserName == null || browserName.equals("chrome"))
//			return DriverType.CHROME;
//		else if (browserName.equalsIgnoreCase("firefox"))
//			return DriverType.FIREFOX;
//		else if (browserName.equalsIgnoreCase("iexploer"))
//			return DriverType.INTERNETEXPLORER;
//		else
//			throw new RuntimeException(
//					"Browser name key in the Configuration.properties is not matched: " + browserName);
	}

	public EnvironmentType getEnvironment() {
		String environmentName = properties.getProperty("environment");
		if (environmentName == null || environmentName.equalsIgnoreCase("local"))
			return EnvironmentType.LOCAL;
		else if (environmentName.equals("remote"))
			return EnvironmentType.REMOTE;
		else
			throw new RuntimeException(
					"Environment Type Key value in Configuration.properties is not matched : " + environmentName);
	}

	public Boolean getBrowserWindowSize() {
		String windowSize = properties.getProperty("windowMaximize");
		if (windowSize != null)
			return Boolean.valueOf(windowSize);
		return true;
	}

	public String getRemoteJenkinsURL() {
		String remoteJenkinsURL = properties.getProperty("remoteJenkinsURL");
		if (remoteJenkinsURL != null)
			return remoteJenkinsURL;
		else
			throw new RuntimeException("remoteJenkinsURL is not specified in the Configuration.properties file.");
	}

	public String getRemoteLocalURL() {
		String remoteLocalURL = properties.getProperty("remoteLocalURL");
		if (remoteLocalURL != null)
			return remoteLocalURL;
		else
			throw new RuntimeException("remoteLocalURL is not specified in the Configuration.properties file.");
	}

	public String getUserDIR() {
		String userDIR = properties.getProperty("userDIR");
		if (userDIR != null)
			return userDIR;
		else
			throw new RuntimeException("userDIR is not specified in the Configuration.properties file.");
	}

	public String getClassPath() {
		String classPath = properties.getProperty("classPath");
		if (classPath != null)
			return classPath;
		else
			throw new RuntimeException("classPath is not specified in the Configuration.properties file.");
	}

	public String getURLProvPortal() {
		String urlProvisioningPortal = properties.getProperty("urlProvisioningPortal");
		if (urlProvisioningPortal != null)
			return urlProvisioningPortal;
		else
			throw new RuntimeException("urlProvisioningPortal is not specified in the Configuration.properties file.");
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitlyWait");
		if (implicitlyWait != null)
			return Long.parseLong(implicitlyWait);
		else
			throw new RuntimeException("implicitlyWait is not specified in the Configuration.properties file.");
	}

	public long getTimeoutWait() {
		String timeoutWait = properties.getProperty("timeoutWait");
		if (timeoutWait != null)
			return Long.parseLong(timeoutWait);
		else
			throw new RuntimeException("timeoutWait is not specified in the Configuration.properties file.");
	}

	public int getminimizeSleep() throws ParseException {
		String minimizeSleep = properties.getProperty("minimizeSleep");
		if (minimizeSleep != null)
			return Integer.parseInt(minimizeSleep);
		else
			throw new RuntimeException("minimizeSleep is not specified in the Configuration.properties.");
	}

	public String getURLPostman() {
		String urlPostman = properties.getProperty("urlPostman");
		if (urlPostman != null)
			return urlPostman;
		else
			throw new RuntimeException("urlPostman is not specified in the Configuration.properties file.");
	}

}
