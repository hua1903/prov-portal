package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import stepDefinitions.LoginSteps;
import selenium.seleniumHelper;
import utilities.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Logout')]")
	private List<WebElement> btnLogout;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Logout')]")
	private WebElement btnLogoutAPT;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'header__nav--home')]")
	private WebElement btnHome;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'header__logo')]")
	private WebElement btnLogo;

	@FindBy(how = How.XPATH, using = "//div[@class='inactive-bar']")
	private WebElement btnInactiveHamburger;

	@FindBy(how = How.XPATH, using = "//div[@class='active-bar']")
	private WebElement btnActiveHamburger;

	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-content']//div[contains(text(),'Rate Plan')]")
	private WebElement linkRatePlan;

	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-content']//div[contains(text(),'APT (Gladice)')]")
	private WebElement linkAPT;

	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-content']//div[contains(text(),'Number Groups')]")
	private WebElement linkNumGroup;

	@FindBy(how = How.XPATH, using = "//div[@class='dropdown-content']//div[contains(text(),'Rate Plan Admin')]")
	private WebElement linkRatePlanAdmin;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'react-sweet-progress-circle-outer')]")
	private List<WebElement> loadingIcon;

	@FindBy(how = How.XPATH, using = "//section[contains(@class,'bureau-rate-plans')]")
	private WebElement pageRatePlan;

	@FindBy(how = How.XPATH, using = "//section[contains(@class,'bureau-apt')]")
	private WebElement pageAPT;

	@FindBy(how = How.XPATH, using = "//section[contains(@class,'bureau-number-groups')]")
	private WebElement pageNumGroup;

	@FindBy(how = How.XPATH, using = "//section[contains(@class,'bureau-rate-plans is-admin')]")
	private WebElement pageRatePlanAdmin;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Gladice Login')]")
	private WebElement pageAPTLogin;

	@FindBy(how = How.XPATH, using = "//li[contains(text(),'Actionable')]")
	private WebElement pageAPTHome;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Number Allocation Tool')]")
	private WebElement pageNumberGroupLogin;

	@FindBy(how = How.XPATH, using = "//input[@name='newGroupName']")
	private WebElement pageNumberGroupHome;

	public WebElement getElementBoxName(String boxName) {
		return driver.findElement(By.xpath("//*[text()='" + boxName + "']"));
	}

	public boolean visibleBoxes(String boxName) {
		try {
			Thread.sleep(1000);
			return getElementBoxName(boxName).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visiblePages(String namePage) {
		try {
			Thread.sleep(2000);
			seleniumHelper.waitClickAble(driver, 60, btnHome);
			switch (namePage) {
			case ("Rate Plan"):
				Thread.sleep(4000);
				seleniumHelper.waitClickAble(driver, 60, btnHome);
				return pageRatePlan.isDisplayed();
			case ("APT (Gladice)"):
				return pageAPT.isDisplayed();
			case ("Number Groups"):
				return pageNumGroup.isDisplayed();
			case ("Rate Plan Admin"):
				return pageRatePlanAdmin.isDisplayed();
			default:
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean notVisibleLogout() {
		return btnLogout.isEmpty();
	}

	public boolean seeHomeBtn() {
		try {
			Thread.sleep(2000);
			return btnHome.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickBoxBtn(String boxName) throws IOException, InterruptedException {
		try {
			Thread.sleep(2000);
			seleniumHelper.waitClickAble(driver, 60, getElementBoxName(boxName));
			getElementBoxName(boxName).click();
			seleniumHelper.waitClickAble(driver, 60, btnHome);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBox" + boxName, "Cannot click on the box " + boxName);
		}

	}

	public void clickLogout() throws Exception {
		try {
			btnLogout.get(0).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeLogoutElement", "The element of Logout button is changed.");
		}
	}

	public void clickLogoutAPT() throws Exception {
		try {
			btnLogoutAPT.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeLogoutElement",
					"Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Logout button is changed.");
		}
	}

	public void clickHome() throws Exception {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnHome);
			btnHome.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeHomeBtnElement",
					"Stop the scenario: >>" + LoginSteps.scenario + "<< The element of Home button is changed.");
		}

	}

	public boolean visibleBTBLogo() {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnLogo);
			return btnLogo.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickBtbLogo() throws Exception {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnLogo);
			btnLogo.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBTBLogoElement", "Element of BTB logo is changed");
		}
	}

	public boolean visibleHamburgerMenu() {
		try {
			return btnInactiveHamburger.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public String getNewWindow() throws Exception {
		try {
			Thread.sleep(1000);
			List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(browserTabs.get(1));
			String currentURL = "";
			currentURL = driver.getCurrentUrl();
			driver.close();
			driver.switchTo().window(browserTabs.get(0));
			return currentURL;
		} catch (Exception ex) {
			return "";
		}
	}

	public void switchToNewTab() throws Exception {
		try {
			Thread.sleep(2000);
			List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(browserTabs.get(1));

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public String getURL() {
		try {
			String currentURL = "";
			List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(browserTabs.get(0));
			currentURL = driver.getCurrentUrl();
			return currentURL;
		} catch (Exception ex) {
			return "";
		}
	}

	public void closeNewTab() {
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(browserTabs.get(0));
	}

	public boolean visibleAPTPages(String namePage) {
		try {
			switchToNewTab();
			switch (namePage) {
			case ("Gladice Login"):
				return pageAPTLogin.isDisplayed();
			case ("Gladice Home"):
				return pageAPTHome.isDisplayed();
			default:
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleNumberGroupPages(String namePage) {
		try {
			switchToNewTab();
			switch (namePage) {
			case ("NumberGroup Login"):
				return pageNumberGroupLogin.isDisplayed();
			case ("NumberGroup Home"):
				return pageNumberGroupHome.isDisplayed();
			default:
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

}
