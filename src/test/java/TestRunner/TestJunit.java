package TestRunner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import stepDefinitions.LoginSteps;
import managers.WebDriverManager;
import utilities.Helper;
import utilities.Log;
import utilities.zipFolders;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "ProvisioningPortalFeatures" }, glue = { "stepDefinitions" }, format = {
		"json:target/Destination/cucumber.json" }, monochrome = true, 
				tags = {"@all" }, 
				plugin = {"com.cucumber.listener.ExtentCucumberFormatter:" })
public class TestJunit{
	public static LoginSteps s;
	public static WebDriver driver;
	public static WebDriverManager webDriverManager;

	@BeforeClass
	public static void setup() throws Exception {
		String dateTimeName = Helper.nameDateTime();
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath("ReportForEmail/Provisioning Portal Report.html");
		System.setProperty("logfilename", dateTimeName);
		Log.startLog("stepsDefinition");
	}

	@AfterClass
	public static void teardown() throws Exception {
		Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/config/extent-config.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", System.getProperty("os.name"));
		Reporter.setTestRunnerOutput("Verify the Provisioning Portal website");
		driver = LoginSteps.driver;
		driver.quit();
		Log.endLog("stepsDefinition");
		//zip the report file
		utilities.Helper.DeleteAFile(System.getProperty("user.dir"), "ReportForEmail.zip");
		zipFolders.zipFolder(System.getProperty("user.dir") + "/ReportForEmail",
				System.getProperty("user.dir") + "/ReportForEmail.zip");
	}

}
