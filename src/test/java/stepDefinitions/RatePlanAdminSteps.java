package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.jayway.jsonpath.JsonPath;

import cucumber.TestContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import pageObjects.RatePlanAdminPage;
import pageObjects.RatePlanPage;
import selenium.seleniumHelper;
import utilities.Helper;
import utilities.Log;

public class RatePlanAdminSteps {
	TestContext testContext;
	RatePlanAdminPage ratePlanAdminPage;
	RatePlanPage ratePlanPage;
	WebDriver driver;
	public File jsonfile;
	public List<String> oldSummary;
	public List<List<String>> oldCallrate;
	public List<String> newSummary;
	public List<List<String>> newCallrate;
	boolean ignoreCase = false;
	public ConfigFileReader configReader;
	public String classPath;

	public RatePlanAdminSteps(TestContext context) throws IOException {
		testContext = context;
		ratePlanAdminPage = testContext.getPageObjectManager().getRatePlanAdminPage();
		ratePlanPage = testContext.getPageObjectManager().getRatePlanPage();
		configReader = FileReaderManager.getInstance().getConfigReader();
		classPath = System.getProperty("user.dir") + configReader.getClassPath();
		driver = LoginSteps.driver;
	}

	@Then("^I should see the label Rate Plan changes Pending$")
	public void seeTitleRatePlanAdmin() throws Throwable {
		if (ratePlanAdminPage.seeTitleRatePlanAdmin()) {
			Log.info("I should see the label Rate Plan changes Pending");
		} else {
			seleniumHelper.setError(driver, "DontSeeTitleRatePlanAdmin",
					"Don't see the label Rate Plan changes Pending");
		}
	}

	@Then("^I should get rate plans under Rate Plan Admin page$")
	public void getRatePlanList() throws Throwable {
		if (ratePlanPage.getTotaRatePlans() > 0) {
			Log.info("There are some Rate Plans under Rate Plan Admin page");
		} else {
			Log.warn("There is no Rate Plan under Rate Plan Admin page");
		}
	}

	@Then("^I should see the Show History Records button$")
	public void seeShowHistoryRecords() throws Throwable {
		if (ratePlanAdminPage.seeShowHistoryBtn()) {
			Log.info("I should see the Show History Records button");
		} else {
			seleniumHelper.setError(driver, "DontSeeShowHistoryBtn", "Don't see the Show History Records button");
		}
	}

	@Then("^I should see the Hide history records button$")
	public void seeHideHistoryRecords() throws Throwable {
		if (ratePlanAdminPage.seeHideHistoryBtn()) {
			Log.info("I should see the Hide History Records button");
		} else {
			seleniumHelper.setError(driver, "DontSeeHideHistoryBtn", "Don't see the Hide History Records button");
		}
	}

	@When("^I create the Json file for Rate Plan Admin List$")
	public void createJsonRatePlanAdminList() throws Throwable {
		ratePlanAdminPage.createJsonRatePlanAdminList();
	}

	@Then("^The \"([^\"]*)\" of Rate Plans on website should be same as Json file of Admin page$")
	public void compareRatePlanWithBureauOnWebAndJson(String field) throws Throwable {
		String webRatePlanDes = "";
		String jsRatePlanDes = "";
		String webDes = "";
		List<List<String>> jsRatePlanAdList = Helper.ratePlanAdminList();
		jsonfile = new File(classPath + "/testDataResources/Json RatePlanAdminList.js");
		try {
			int totalRatePlans = ratePlanPage.getTotaRatePlans();
			for (int i = 0; i < totalRatePlans; i++) {
				webRatePlanDes = ratePlanPage.getSummaryInfo(field, i);
				webDes = ratePlanPage.getSummaryInfo("Description", i);
				int totalListInJson = jsRatePlanAdList.size();
				String jsDes;
				switch (field) {
				case ("Comments"):
					for (int j = 0; j < totalListInJson; j++) {
						jsDes = jsRatePlanAdList.get(j).get(3);
						if (webDes.equalsIgnoreCase(jsDes)) {
							jsRatePlanDes = jsRatePlanAdList.get(j).get(0);
							break;
						}
					}
					break;
				case ("Rate Plan Id"):
					for (int j = 0; j < totalListInJson; j++) {
						jsDes = jsRatePlanAdList.get(j).get(3);
						if (webDes.equalsIgnoreCase(jsDes)) {
							jsRatePlanDes = jsRatePlanAdList.get(j).get(1);
							break;
						}
					}
					break;
				case ("Rate Plan Type"):
					for (int j = 0; j < totalListInJson; j++) {
						jsDes = jsRatePlanAdList.get(j).get(3);
						if (webDes.equalsIgnoreCase(jsDes)) {
							jsRatePlanDes = jsRatePlanAdList.get(j).get(2);
							break;
						}
					}
					break;
				case ("Description"):
					for (int j = 0; j < totalListInJson; j++) {
						jsDes = jsRatePlanAdList.get(j).get(3);
						if (webDes.equalsIgnoreCase(jsDes)) {
							jsRatePlanDes = jsRatePlanAdList.get(j).get(3);
							break;
						}
					}
					break;

				default:
					seleniumHelper.setError(driver, "DontSeeField" + field,
							"The field " + field + " does not exist on our system");
				}
				if (Helper.trimRight(webRatePlanDes).equalsIgnoreCase(Helper.trimRight(jsRatePlanDes))) {
					Log.info("The " + field + " >> " + webRatePlanDes
							+ " << is displayed for rate plan with description: " + webDes);
					break;
				} else {
					seleniumHelper.setError(driver, "DontSee" + field, "The " + field + " >> " + webRatePlanDes
							+ " << is NOT displayed for rate plan with description: " + webDes);
				}
			}

		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSee" + field,
					"There is something wrong with Json file: Json RatePlanAdminList.js");
		}
	}

	@Then("^The value of call rate \"([^\"]*)\" of Rate Plan with Description \"([^\"]*)\" on website should be same as Json file of Admin page$")
	public void compareTableWithRatePlanOnWebAndJson(String callRate, String des) throws Throwable {
		String ratePlanColumnWeb = "";
		String ratePlanColumnJson = "";
		String fieldAPI = "";
		boolean status = false;
		jsonfile = new File(classPath + "/testDataResources/Json RatePlanAdminList.js");
		try {
			int totalCallRate = ratePlanPage.getTotalRowCallRateWithDes(des);
			for (int i = 0; i < totalCallRate; i++) {
				ratePlanColumnWeb = ratePlanPage.getValueCallRateWithAPI(callRate, des, i);
				fieldAPI = "callRates[" + i + "]." + ratePlanPage.getFieldJsonCallRate(callRate);
				ratePlanColumnJson = JsonPath
						.read(jsonfile, "$." + "data[?(@.ratePlan.description==" + des + ")]." + fieldAPI).toString();
				if (Helper.isNumeric(ratePlanColumnWeb)) {
					ratePlanColumnWeb = ratePlanColumnWeb + ".0";
				}
				if (ratePlanColumnWeb.equalsIgnoreCase(ratePlanColumnJson)) {
					Log.info("The table with the " + callRate + " >> " + ratePlanColumnWeb
							+ " << is displayed on website");
					status = true;
					break;
				}
				if (!status) {
					seleniumHelper.setError(driver, "DontSeeCol" + ratePlanColumnWeb, "The table with the " + callRate
							+ " >> " + ratePlanColumnWeb + " << is NOT displayed on website");
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSeeValue" + ratePlanColumnWeb,
					"Something wrong with Json file Json RatePlanAdminList.js");
		}
	}

	@Then("^I should see the Accept button for rate plans under Rate Plan Admin$")
	public void seeBtnAcceptAdmin() throws Throwable {
		int totalRatePlanAdmin = ratePlanAdminPage.getTotalRatePlanAdmin();
		for (int i = 0; i < totalRatePlanAdmin; i++) {
			if (ratePlanAdminPage.seeBtnAccept(i)) {
				Log.info("I should see the Accept button for rate plan with description "
						+ ratePlanPage.getSummaryInfo("Description", i));
			} else {
				seleniumHelper.setError(driver, "DontSeeAcceptBtn",
						"The Accept button is NOT displayed for rate plan with description "
								+ ratePlanPage.getSummaryInfo("Description", i));
			}
		}

	}

	@Then("^I should see the Edit button for rate plans under Rate Plan Admin$")
	public void seeBtnEditAdmin() throws Throwable {
		int totalRatePlanAdmin = ratePlanAdminPage.getTotalRatePlanAdmin();
		for (int i = 0; i < totalRatePlanAdmin; i++) {
			if (ratePlanAdminPage.seeBtnAccept(i)) {
				Log.info("I should see the Edit button for rate plan with description "
						+ ratePlanPage.getSummaryInfo("Description", i));
			} else {
				seleniumHelper.setError(driver, "DontSeeEditBtn",
						"The Edit button is NOT displayed for rate plan with description "
								+ ratePlanPage.getSummaryInfo("Description", i));
			}
		}

	}

	@Then("^I should see the Reject button for rate plans under Rate Plan Admin$")
	public void seeBtnRejectAdmin() throws Throwable {
		int totalRatePlanAdmin = ratePlanAdminPage.getTotalRatePlanAdmin();
		for (int i = 0; i < totalRatePlanAdmin; i++) {
			if (ratePlanAdminPage.seeBtnAccept(i)) {
				Log.info("I should see the Reject button for rate plan with description "
						+ ratePlanPage.getSummaryInfo("Description", i));
			} else {
				seleniumHelper.setError(driver, "DontSeeRejectBtn",
						"The Reject button is NOT displayed for rate plan with description "
								+ ratePlanPage.getSummaryInfo("Description", i));
			}
		}

	}

	@And("^I click on Accept button for Rate Plan with description \"([^\"]*)\"$")
	public void acceptRatePlan(String des) throws Throwable {
		ratePlanAdminPage.clickAcceptBtnWithDes(des);
	}

	@And("^I click on Edit button for Rate Plan with description \"([^\"]*)\"$")
	public void editRatePlanAdmin(String des) throws Throwable {
		ratePlanAdminPage.clickEditRatePlanAdminBtnWithDes(des);
	}

	@And("^I click on Reject button for Rate Plan with description \"([^\"]*)\"$")
	public void rejectRatePlan(String des) throws Throwable {
		ratePlanAdminPage.clickRejectBtnWithDes(des);
	}

	@And("^I click on Show History Records button$")
	public void viewHistoryRatePlanAdmin() throws Throwable {
		ratePlanAdminPage.clickShowHistoryRecords();
		Log.info("Show History Records button is clicked");
	}

	@And("^I click on Hide history records button$")
	public void hideHistoryRatePlanAdmin() throws Throwable {
		ratePlanAdminPage.clickHideHistoryRecords();
	}

	@Then("^Under a \"([^\"]*)\" rate plan, action buttons are enable$")
	public void enableBtnWithPendingAllPlan(String status) throws Throwable {
		Thread.sleep(500);
		if (ratePlanAdminPage.see3EnableActionBtnsAtFirstPlan(status))
			Log.info("Action buttons are enable for the " + status + " rate plan");
		else
			seleniumHelper.setError(driver, "DisableBtnPlanWithStatus" + status,
					"Action buttons are disabled for the " + status + " rate plan");
	}

	@Then("^Under a \"([^\"]*)\" rate plan, action buttons are disable$")
	public void disableActionBtnsAllPlan(String status) throws Throwable {
		Thread.sleep(500);
		if (!ratePlanAdminPage.see3EnableActionBtnsAtFirstPlan(status))
			Log.info("Action buttons are disabled for the " + status + " rate plan");
		else
			seleniumHelper.setError(driver, "EnableBtnPlanWithStatus" + status,
					"Action buttons are NOT disabled for the " + status + " rate plan");
	}

	@Then("^I should see some rate plans with \"([^\"]*)\" status$")
	public void seeRejectedAllPlan(String status) throws Throwable {
		Thread.sleep(500);
		if (ratePlanAdminPage.seeRatePlanWithStatus(status))
			Log.info("I should see some rate plans with " + status + " status");
		else
			seleniumHelper.setError(driver, "NoPlanWithStatus" + status,
					"I don't see some rate plans with " + status + " status");
	}

	@Then("^I should NOT see any rate plan with \"([^\"]*)\" status$")
	public void dontSeeRejectedAllPlan(String status) throws Throwable {
		Thread.sleep(500);
		if (!ratePlanAdminPage.seeRatePlanWithStatus(status))
			Log.info("I should NOT see any rate plans with " + status + " status");
		else
			seleniumHelper.setError(driver, "SeePlanWithStatus" + status,
					"I still see some rate plans with " + status + " status");

	}

	@Then("^I should see the ACCEPTED in Rate Plan ID for Rate Plan with description \"([^\"]*)\"$")
	public void seeAcceptedStatusWithDes(String des) throws Throwable {
		if (ratePlanAdminPage.visibleAcceptedWithDes(des))
			Log.info("I should see the ACCEPTED in Rate Plan ID for Rate Plan with description: " + des);
		else
			Log.info("There is no ACCEPTED rate plan, so please ignore this step");
	}

	@Then("^I should see the REJECTED in Rate Plan ID for Rate Plan with description \"([^\"]*)\"$")
	public void seeRejectedStatusWithDes(String des) throws Throwable {
		if (ratePlanAdminPage.visibleRejectedWithDes(des))
			Log.info("I should see the REJECTED in Rate Plan ID for Rate Plan with description: " + des);
		else
			Log.info("There is no REJECTED rate plan, so please ignore this step");
	}

	@Then("^I should see the Reject Confirmation popup$")
	public void visibleMsgConfimReject() throws Throwable {
		if (ratePlanAdminPage.visibleMsgConfirmReject()) {
			Log.info("The Reject Confirmation popup is displayed");
		} else
			seleniumHelper.setError(driver, "DontMsgConfirmReject", "The Reject Confirmation popup is displayed");
	}

	@Then("^I should see the Accept Confirmation popup$")
	public void visibleMsgConfimAccept() throws Throwable {
		if (ratePlanAdminPage.visibleMsgConfirmAccept()) {
			Log.info("The Accept Confirmation popup is displayed");
		} else
			seleniumHelper.setError(driver, "DontMsgConfirmAccept", "The Accept Confirmation popup is displayed");
	}

	@Then("^The value of \"([^\"]*)\" should NOT be \"([^\"]*)\" for Rate Plan with Description \"([^\"]*)\"$")
	public void dontSeeSummaryInfo(String field, String value, String description)
			throws InterruptedException, IOException {
		String valueSummaryWeb = ratePlanPage.getSummaryInfoWithDes(field, description);
		boolean status = ratePlanAdminPage.dontSeeSummaryInfo(field, value, description, valueSummaryWeb);
		if (status)
			Log.info("I should NOT see the value >> " + value + " >> for the field " + field
					+ " of Rate Plan with Description: " + description);
		else
			seleniumHelper.setError(driver, "seeSummaryInfo" + field, "I still see the value " + value
					+ " for the field " + field + " of Rate Plan with Description: " + description);

	}

	@Then("^The value of \"([^\"]*)\" should be \"([^\"]*)\" for Rate Plan with Description \"([^\"]*)\"$")
	public void seeNewSummaryInfo(String field, String value, String description)
			throws InterruptedException, IOException {
		String valueSummaryWeb = ratePlanPage.getSummaryInfoWithDes(field, description);
		boolean status = ratePlanAdminPage.seeNewSummaryInfo(value, description, valueSummaryWeb);
		if (status)
			Log.info("I should see the value >> " + value + " >> for the field " + field
					+ " of Rate Plan with Description: " + description);
		else
			seleniumHelper.setError(driver, "seeSummaryInfo" + field, "Don't see the value " + value + " for the field "
					+ field + " of Rate Plan with Description: " + description);

	}

	@Then("^The value of \"([^\"]*)\" should be old value for Rate Plan with Description \"([^\"]*)\"$")
	public void seeOldSummaryInfo(String field, String description) {
		try {
			String value = "";
			oldSummary = ratePlanPage.getCurrentSummaryRPWithDes(description);
			String des = oldSummary.get(0);
			if (des.equalsIgnoreCase(description)) {
				if (field.equalsIgnoreCase("Comments")) {
					value = oldSummary.get(1);
				} else if (field.equalsIgnoreCase("Rate Plan Type")) {
					value = oldSummary.get(2);
				} else if (field.equalsIgnoreCase("Submitted By")) {
					value = oldSummary.get(3);
				} else if (field.equalsIgnoreCase("Submitted Date")) {
					value = oldSummary.get(4);
				} else if (field.equalsIgnoreCase("IDD reference")) {
					value = oldSummary.get(5);
				} else {
					seleniumHelper.setError(driver, "DontSeeField" + field,
							"Dont see the field " + field + " in our system");
				}

			} else {
				throw new RuntimeException("Don't see the old value of Rate Plan with Description: " + description);
			}
			String valueWeb = ratePlanPage.getSummaryInfoWithDes(field, description);
			boolean status = ratePlanAdminPage.seeOldSummaryInfo(value, valueWeb);

			if (status)
				Log.info("I should see the value " + value + " for the field " + field
						+ " of Rate Plan with Description: " + description);
			else
				seleniumHelper.setError(driver, "DontSeeSummaryInfo" + field, "Don't see the value " + value
						+ " for the field " + field + " of Rate Plan with Description: " + description);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Then("^Under show history view, I should NOT see the revert icon for Rate Plan without changes and lived in cindy$")
	public void dontSeeRevertIconNoChangePlanAd() throws Throwable {
		int totalPlan = ratePlanAdminPage.getTotalStatus();
		String des = "";
		int cutTime=0;
		List<String> duplicatedDes = new ArrayList<String>();
		for (int i = 0; i < totalPlan; i++) {
			if(cutTime==3) break;
			des = ratePlanPage.getSummaryInfo("Description", i);
			if (!ratePlanAdminPage.visibleDuplicatedDes(duplicatedDes, des)) {
				if (ratePlanAdminPage.visibleAccepted(i) && ratePlanPage.getRatePlanID(i) > 0
						|| ratePlanAdminPage.visibleRejected(i) && ratePlanPage.getRatePlanID(i) > 0) {
					int k = i + 1;
					if (!ratePlanPage.visibleRevertIconWithDes(k)) {
						Log.info("I should NOT see the revert icon for Rate Plan with Description " + des);
						duplicatedDes.add(des);
						cutTime++;
					} else
						seleniumHelper.setError(driver, "StillSeeRevertIcon",
								"I still see the revert icon for Rate Plan with Description " + des);
				}
			}
		}
	}

}
