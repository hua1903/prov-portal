package stepDefinitions;

import org.junit.Assert;

import cucumber.TestContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import pageObjects.LogoutPage;
import utilities.Log;

public class LogoutSteps {
	TestContext testContext;
	LogoutPage logout;

	public LogoutSteps(TestContext context) {
		testContext = context;
		logout = testContext.getPageObjectManager().getLogoutPage();
	}

	// logout function
	@And("Under Logout pop-up, I click on the button \"([^\"]*)\"$")
	public void clickBtnOfLogout(String button) throws Throwable {
		logout.clickOptions(button);

	}

	@Then("Under Logout pop-up, the button \"([^\"]*)\" should be displayed$")
	public void visibleBtnOfLogout(String button) throws Throwable {
		if (logout.visibleOptions(button)) {
			Log.info("The button " + button + " is displayed in the Logout pop-up");
		} else {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The button " + button
					+ " is NOT displayed in the Logout pop-up");
			Assert.fail("The button " + button + " is NOT displayed in the Logout pop-up");
		}
	}

	@Then("Under Logout pop-up, the logout confirmation message should be displayed$")
	public void visibleMsgOfLogout() throws Throwable {
		if (logout.visibleMsgLogout()) {
			Log.info("The message >> Do you want to logout? >> is displayed in the Logout pop-up");
		} else {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario
					+ "<< The message >> Do you want to logout? >> is NOT displayed in the Logout pop-up");
			Assert.fail("The message >> Do you want to logout? >> is NOT displayed in the Logout pop-up");
		}
	}

	@And("I click on the Logout button$")
	public void clickBtnLogout() throws Throwable {
		logout.clickLogout();
	}
	@And("Logout the website$")
	public void logoutPP() throws Throwable {
		logout.clickLogout();
		logout.clickOptions("Confirm");
	}

	@Then("^I should see the Logout button$")
	public void visibleLogout() throws Throwable {
		if (logout.visibleLogoutBtn()) {
			Log.info("The Logout button is displayed");
		} else {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The Logout button is NOT displayed");
			Assert.fail("The Logout button is NOT displayed");

		}

	}

	// logout successfully
	@Then("^The website logout successfully and I can see the login page$")
	public void visibleLoginBtn() throws Throwable {
		if (logout.visibleBtnLogin()) {
			Log.info("The web logout successfully");
		} else {
			Log.error("Stop the scenario: >>" + LoginSteps.scenario + "<< The web does not login.");
			Assert.fail("The web does not login.");

		}

	}

}
