package pageObjects;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import stepDefinitions.LoginSteps;
import selenium.seleniumHelper;
import utilities.Log;

public class LoginPage {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Provisioning Portal')]")
	private WebElement titleWebsite;

	@FindBy(how = How.NAME, using = "username")
	private WebElement txtbx_UserName;

	@FindBy(how = How.NAME, using = "password")
	private WebElement txtbxPassword;

	@FindBy(how = How.XPATH, using = "//button/span[contains(text(),'Login')]")
	private WebElement btnLogin;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Logout')]")
	private WebElement btnLogout;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-confirm')]")
	private WebElement btnConfirmPopup;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-close')]")
	private WebElement btnClosePopup;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Username or password invalid')]")
	private WebElement wrongUserNameOrPasswordMsg;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Please login again')]")
	private List<WebElement> msgLoginAgain;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Close')]")
	private WebElement btnClosedMsg;

	@FindBy(how = How.XPATH, using = "//div[@class='header__logo']")
	private WebElement btnLogo;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'x-button')]")
	private WebElement btnX;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'header__nav--home')]")
	private WebElement btnHome;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-cmp button is-loading is-info long is-rounded')]")
	private List<WebElement> btnLoadLogin;

	public void waitFisnishLoadLogin(){
		while (btnLoadLogin.size()>0){
			System.out.println("Login button is still loading");
		}
	}
	
	public WebElement getElementMsgWrongLogin(String msg){
		String xpath ="//*[contains(text(),'"+msg+"')]";
		return driver.findElement(By.xpath(xpath));
	}
	
	public boolean visiblePPWebsite() {
		try {
			return titleWebsite.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleBtnX() {
		try {
			return btnX.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeHomeBtn() {
		try {
			return btnHome.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickXbtn() {
		try {
			btnX.click();
		} catch (Exception ex) {

		}
	}

	public void enterUserName(String name) throws Exception {
		try {
			txtbx_UserName.clear();
			txtbx_UserName.sendKeys(name);
		} catch (Exception ex) {
			String screenShotPath = seleniumHelper.capture(driver, "DontSeeUserNameTxt");
			Reporter.addScreenCaptureFromPath(screenShotPath);
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of UserName textbox is changed.");
			Assert.fail("The element of UserName textbox is changed.");
		}
	}

	public void enterPassword(String pw) throws Exception {
		try {
			txtbxPassword.clear();
			txtbxPassword.sendKeys(pw);
		} catch (Exception ex) {
			String screenShotPath = seleniumHelper.capture(driver, "DontSeePasswordTxt");
			Reporter.addScreenCaptureFromPath(screenShotPath);
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Password textbox is changed.");
			Assert.fail("The element of Password textbox is changed.");
		}
	}

	public void clickLogin() throws Exception {

		try {
			btnLogin.click();
		} catch (Exception ex) {
			String screenShotPath = seleniumHelper.capture(driver, "DontSeeLoginBtn");
			Reporter.addScreenCaptureFromPath(screenShotPath);
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Login button is changed.");
			Assert.fail("The element of Login button is changed.");
		}
	}

	public void accessPP(String username, String password) throws Exception {
		enterUserName(username);
		enterPassword(password);
		clickLogin();
	}

	public boolean notVisibleLoginAgainMsg() {
		return msgLoginAgain.isEmpty();
	}

	public boolean visibleLoginBtn() {
		try {
			return btnLogin.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickCloseMsgBtn() throws Exception {

		try {
			btnClosedMsg.click();
		} catch (Exception ex) {
			String screenShotPath = seleniumHelper.capture(driver, "DontCloseBtnMsg");
			Reporter.addScreenCaptureFromPath(screenShotPath);
			Log.error("Stop the scenario: >>" + LoginSteps.scenario
					+ "<< The element of Close button in the pop-up >> Please Login >> is changed.");
			Assert.fail("The element of Close button in the pop-up >> Please Login >> is changed.");
		}
	}

	public boolean visibleWrongAccountMsg(String msg) {
		try {
			waitFisnishLoadLogin();
			return getElementMsgWrongLogin(msg).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleBTBLogo() {
		try {
			Thread.sleep(2000);
			return btnLogo.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}
	public boolean visibleLogoutBtn() {
		try {
			Thread.sleep(2000);
			return btnLogout.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickLogout() {
		try {
			btnLogout.click();
		} catch (Exception ex) {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Logout button is changed.");
			Assert.fail("The element of Logout button is changed.");
		}
	}

	public void logoutWebsite() throws InterruptedException, IOException {
		try {
			btnLogout.click();
			btnConfirmPopup.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CannotLogout",
					"Cannot logout the website after scenario " + LoginSteps.scenario);
		}
	}

}
