package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;

import cucumber.TestContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.HomePage;
import selenium.seleniumHelper;
import utilities.Log;

public class HomePageSteps {

	TestContext testContext;
	HomePage homePage;
	WebDriver driver;

	public HomePageSteps(TestContext context) {
		testContext = context;
		homePage = testContext.getPageObjectManager().getHomePage();
		driver = LoginSteps.driver;
	}

	// ----------home page------------
	@When("^I am on the \"([^\"]*)\" page$")
	public void amOnPage(String boxName) throws Throwable {
		homePage.clickBoxBtn(boxName);
	}

	@When("^I am on the Home page$")
	public void amOnHomePage() throws Throwable {
		// only text, there is no code for this
		if(homePage.visibleBTBLogo()){
			Log.info("I am on the Home page");
		}else seleniumHelper.setError(driver, "NoHompage", "Don't see the Home page");
	}

	@Then("^I should see the box \"([^\"]*)\"$")
	public void visibleBtnBoxes(String boxName) throws Throwable {

		if (homePage.visibleBoxes(boxName)) {
			Log.info("The option " + boxName + " is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSee" + boxName + "Box", "The option " + boxName + " is not displayed");
		}
	}

	@And("^I click on the box \"([^\"]*)\"$")
	public void clickBoxes(String boxName) throws Throwable {
		homePage.clickBoxBtn(boxName);
		Log.info("Directed successfully to " + boxName + " page");
	}

	@Then("^I should not see the box \"([^\"]*)\"$")
	public void notVisibleBtnBoxes(String boxName) throws Throwable {
		if (!homePage.visibleBoxes(boxName)) {
			Log.info("The option " + boxName + " is NOT displayed");
		} else {
			seleniumHelper.setError(driver, "DontSee" + boxName + "Box", "The option " + boxName + " is displayed");
		}
	}

	@Then("^I should not see the login page of APT on new windows tab$")
	public void notVisibleAPTLoginPage() throws Throwable {
		Thread.sleep(1000);
		if (!homePage.visibleAPTPages("Gladice Login")) {
			Log.info("The login page of APT (Gladice) website should NOT be displayed");
		} else {
			seleniumHelper.setError(driver, "StillSeeAPTLoginPage",
					"The login page of APT (Gladice) website is still displayed");
		}

	}

	@Then("^I should not see the login page of NumberGroup on new windows tab$")
	public void notVisibleNumberGroupLoginPage() throws Throwable {
		Thread.sleep(1000);
		if (!homePage.visibleNumberGroupPages("NumberGroup Login")) {
			Log.info("The login page of NumberGroup website should NOT be displayed");
		} else {
			seleniumHelper.setError(driver, "StillSeeNumberGroupLoginPage",
					"The login page of NumberGroup website is still displayed");
		}

	}

	@Then("^I should see the APT home page on new windows tab$")
	public void visibleAPTHomePage() throws Throwable {
		Thread.sleep(1000);
		if (homePage.visibleAPTPages("Gladice Home")) {
			Log.info("The home page of APT (Gladice) website should be displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeAPTLoginPage",
					"The home page of APT (Gladice) website is NOT displayed");
		}
	}

	@Then("^I should see the NumberGroup home page on new windows tab$")
	public void visibleNumberGroupHomePage() throws Throwable {
		Thread.sleep(1000);
		if (homePage.visibleNumberGroupPages("NumberGroup Home")) {
			Log.info("The home page of NumberGroup website should be displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeNumberGroupLoginPage",
					"The home page of NumberGroup) website is NOT displayed");

		}
	}

	@Then("^I should see the Hamburger menu$")
	public void visibleHamburgerbtn() throws Throwable {
		if (homePage.visibleHamburgerMenu()) {
			Log.info("The Hamburger menu is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeHamburgerMenu", "The Hamburger menu is not displayed");

		}
	}

	@Then("^I should see the Home button$")
	public void visibleHomebtn() throws Throwable {
		if (homePage.seeHomeBtn()) {
			Log.info("The Home button is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeHomeBtn", "The Home button is not displayed");
		}
	}

	@And("^I go to Home page by clicking on Home button$")
	public void clickHome() throws Throwable {
		// visibleHomebtn();
		homePage.clickHome();
		Log.info("Directed successfully to Home page");
	}

	@Then("^I should see the BTB logo$")
	public void visibleLogobtn() throws Throwable {
		if (homePage.visibleBTBLogo()) {
			Log.info("The BTB logo is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeBTBLogo", "The BTB logo is not displayed");
		}
	}

	@And("^I go to Home page by clicking on BTB logo$")
	public void clickLogo() throws Throwable {
		homePage.clickBtbLogo();
		Log.info("Directed successfully to Home page");
	}

	@Then("^I should see the \"([^\"]*)\" page$")
	public void visiblePages(String pageName) throws Throwable {
		if (homePage.visiblePages(pageName)) {
			Log.info("The page " + pageName + " is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSee" + pageName + "Page",
					"The page " + pageName + " is not displayed");

		}
	}

	@Then("^The windows tab of APT is closed$")
	public void compareAPTURL() throws Throwable {
		String provPortalURL = "provisioning-portal";
		if (homePage.getURL().contains(provPortalURL)) {
			Log.info("The APT windows is closed");
		} else {
			seleniumHelper.setError(driver, "StillSeeAPTWindows", "The APT windows is still displayed");
		}
	}

	@Then("^The windows tab of NumberGroup is closed$")
	public void compareNumberGroupURL() throws Throwable {
		String provPortalURL = "provisioning-portal";
		if (homePage.getURL().contains(provPortalURL)) {
			Log.info("The Number Groups windows is closed");
		} else {
			seleniumHelper.setError(driver, "StillSeeNumberGroupWindows",
					"The Number Groups windows is still displayed");
		}
	}

	@Then("^I should see the Provisiong Portal windows$")
	public void visibleHomePortal() throws Throwable {
		if (homePage.visibleBTBLogo()) {
			Log.info("The Provisiong Portal windows is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeProvPortalWindows",
					"The Provisiong Portal windows is NOT displayed");
		}
	}

	@And("^I logout the website$")
	public void clickLogout() throws Throwable {
		homePage.clickLogout();
		Log.info("I click on Logout button of Prov Portal");
	}

	@And("^Under APT website, I click on the Logout button$")
	public void clickLogoutAPT() throws Throwable {
		homePage.clickLogoutAPT();
		Log.info("I clicked on Logout APT button");
	}

	@And("^I go to Rate Plan page$")
	public void goToRatePlan() throws Throwable {
		visibleHomebtn();
		homePage.clickHome();
		homePage.clickBoxBtn("Rate Plan");
		Log.info("Directed successfully to Rate Plan page");
	}

	@And("^I go to Rate Plan Admin page$")
	public void goToRatePlanAdmin() throws Throwable {
		visibleHomebtn();
		homePage.clickHome();
		homePage.clickBoxBtn("Rate Plan Admin");
		Log.info("Directed successfully to Rate Plan Admin page");
	}

}
