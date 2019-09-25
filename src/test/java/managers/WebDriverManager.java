package managers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import enums.DriverType;
import enums.EnvironmentType;

public class WebDriverManager {
	private WebDriver driver;
	private static DriverType driverType;
	private static EnvironmentType environmentType;

	public WebDriverManager() throws IOException {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}

	public WebDriver getDriver() throws IOException {
		if (driver == null)
			driver = createDriver();
		return driver;
	}

	private WebDriver createDriver() throws IOException {
		switch (environmentType) {
		case LOCAL:
			driver = createLocalDriver();
			break;
		case REMOTE:
			driver = createRemoteDriver();
			break;
		}
		return driver;
	}

	private WebDriver createRemoteDriver() throws MalformedURLException, IOException {
		new DesiredCapabilities();
		URL serverurl = new URL(FileReaderManager.getInstance().getConfigReader().getRemoteJenkinsURL());
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		switch (driverType) {
		case FIREFOX:
			capabilities = DesiredCapabilities.firefox();
			break;
		case CHROME:
			capabilities = DesiredCapabilities.chrome();
			break;
		case INTERNETEXPLORER:
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		default:
			capabilities = DesiredCapabilities.chrome();

		}

		driver = new RemoteWebDriver(serverurl, capabilities);
		driver.get(FileReaderManager.getInstance().getConfigReader().getURLProvPortal());
		if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
			driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
				TimeUnit.SECONDS);
		return driver;

	}

	private WebDriver createLocalDriver() throws IOException {
		new DesiredCapabilities();
		URL serverurl = new URL(FileReaderManager.getInstance().getConfigReader().getRemoteLocalURL());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		switch (driverType) {
		case FIREFOX:
			capabilities = DesiredCapabilities.firefox();
			break;
		case CHROME:
			capabilities = DesiredCapabilities.chrome();
			break;
		case INTERNETEXPLORER:
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		default:
			capabilities = DesiredCapabilities.chrome();
		}
		driver = new RemoteWebDriver(serverurl, capabilities);
		driver.get(FileReaderManager.getInstance().getConfigReader().getURLProvPortal());
		if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
			driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
				TimeUnit.SECONDS);
		return driver;
	}

	public void closeDriver() {
		driver.close();
		driver.quit();
	}
}
