package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.Feature;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import selenium.seleniumHelper;
import utilities.Helper;
import utilities.Log;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

public class LoginSteps {
	private static LoginPage loginPage;
	private static PageObjectManager pageObjectManager;
	public static WebDriverManager webDriverManager;
	public static WebDriver driver;
	public File jsonfile;
	public boolean ignoreCase = false;
	public static String scenario = "";
	public Feature feature;
	boolean visibleDriver = false;
	public static String globalUserName = "";
	public static String globalPassword = "";
	ExtentProperties extentProperties;

	@Before
	public void SetUp(Scenario scenario) throws Exception {
		try {
			if (!isBrowserOpen()) {
				webDriverManager = new WebDriverManager();
				driver = webDriverManager.getDriver();
				pageObjectManager = new PageObjectManager(driver);
				loginPage = pageObjectManager.getLoginPage();
				visibleDriver = true;

			}
			this.scenario = scenario.getName();
			Log.info("The scenario: " + scenario.getName() + " is running...");
		} catch (Exception ex) {
			Log.error("Stop the scenario: >>" + scenario.getName()
					+ "<< Cannot start browser, please check your configuration file");
			throw new RuntimeException("Stop the scenario: >>" + scenario.getName()
					+ "<< Cannot start browser, please check your configuration file");
		}
	}

	@After
	public void CloseScenario() throws InterruptedException, IOException {
//		if (loginPage.visibleBtnX()) {
//			loginPage.clickXbtn();
//		}
		seleniumHelper.f5KeyBoard(driver);
		Log.info("End the scenario...");

	}

	@Given("^I am on the Provisioning Portal Website$")
	public void onProvPortalWebsite() throws Throwable {
		if (loginPage.visiblePPWebsite()) {
			Log.info("I am on the login page of Provisioning Portal");
		} else if (loginPage.visibleBTBLogo()) {
			Log.info("I am on the Provisioning Portal website");
		} else {
			String screenShotPath = seleniumHelper.capture(driver, "ServerDown");
			Reporter.addScreenCaptureFromPath(screenShotPath);
			Log.error("Server is down, cannot go to the website");
			Assert.fail("Server is down, cannot go to the website");
		}
	}

	@And("^I click on the LogIn button$")
	public void clickOnLogin() throws Throwable {
		loginPage.clickLogin();
	}

	// login with Json
	@Given("^I access the Provisioning Portal website with user role \"([^\"]*)\"$")
	public void loginJson(String role) throws Throwable {
		try {
			if (loginPage.visibleLogoutBtn()) {
				loginPage.logoutWebsite();
			}
			String userName = null;
			String password = null;
			List<List<String>> accountList = Helper.getAccount();
			int totalAccounts = accountList.size();
			String roleJson;
			for (int i = 0; i < totalAccounts; i++) {
				roleJson = accountList.get(i).get(2);
				if (role.equalsIgnoreCase(roleJson)) {
					userName = accountList.get(i).get(0);
					password = accountList.get(i).get(1);
					globalUserName = userName;
					globalPassword = password;
					break;
				}
			}
			loginPage.accessPP(userName, password);
			Log.info("I access the Provisioning Portal website with user role " + role);

		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CannotLogin",
					"Cannot login! Because user with role " + role + " is not specified in our system");

		}
	}

	// login success
	@Then("^Log in successfully and I can see the main screen$")
	public void loginsuccess() throws Throwable {
		if (loginPage.seeHomeBtn()) {
			Log.info("Login successfully");
		} else {
			seleniumHelper.setError(driver, "DontSeeMainScreen",
					"Login fail! Cannot connect to server, please contact to dev");
		}
	}

	// login fail
	@When("^I enter a wrong username or password to LogIn$")
	public void loginfail(DataTable usercredentials) throws Throwable {
		List<List<String>> data = usercredentials.raw();
		loginPage.enterUserName(data.get(0).get(0));
		loginPage.enterPassword(data.get(0).get(1));
		Log.info("I enter a wrong username or password to LogIn");

	}

	@Then("^There should be the message \"([^\"]*)\"$")
	public void wrongAccountMsg(String msg) throws Throwable {
		Thread.sleep(2000);
		if (loginPage.visibleWrongAccountMsg(msg)) {
			Log.info("The message >> " + msg + " >> is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeMsgLoginFail", "Cannot see the message >> " + msg);

		}
	}

	public boolean isBrowserOpen() {
		try {
			driver.getTitle();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
