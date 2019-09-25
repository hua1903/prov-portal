package pageObjects;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import selenium.seleniumHelper;
import stepDefinitions.LoginSteps;
import utilities.Log;

public class LogoutPage {
	WebDriver driver;

	public LogoutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'logout')]")
	private WebElement btnLogout;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Login')]")
	private WebElement btnLogin;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-confirm')]")
	private WebElement btnConfirm;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-close')]")
	private WebElement btnCancel;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Please click confirm to proceed')]")
	private WebElement msgLogout;
	
	public void clickLogout() throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 30, btnLogout);
			btnLogout.click();
		} catch (Exception ex) {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Logout button is changed.");
			Assert.fail("The element of Logout button is changed.");
			seleniumHelper.setError(driver, "CannotClickLogOut", "Cannot click on the Logout button");
		}
	}

	public boolean visibleLogoutBtn() {
		try {
			seleniumHelper.waitClickAble(driver, 30, btnLogout);
			return btnLogout.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickOptions(String btnName) {
		try {
			switch (btnName) {
			case ("Confirm"):
				btnConfirm.click();
				break;
			case ("Cancel"):
				btnCancel.click();
				break;
			default:
				Log.error(
						"Stop the scenario: >>" + LoginSteps.scenario + "<< The button " + btnName + " does not exist");
				Assert.fail("The button " + btnName + " does not exist");
			}

		} catch (Exception ex) {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The element of button " + btnName
					+ " is changed.");
			Assert.fail("The element of button " + btnName + " is changed.");
		}
	}

	public boolean visibleOptions(String option) {
		try {
			switch (option) {
			case ("Confirm"):
				return btnConfirm.isDisplayed();
			case ("Cancel"):
				return btnCancel.isDisplayed();
			default:
				Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The option " + option
						+ " does not exist in Logout Confirmation pop-up");
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleMsgLogout() {
		try {
			return msgLogout.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleBtnLogin() {
		try {
			return btnLogin.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

}
