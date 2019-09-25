package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bson.Document;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.MongoCollection;

import cucumber.TestContext;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import pageObjects.RatePlanPage;
import selenium.seleniumHelper;
import utilities.Helper;
import utilities.Log;
import utilities.ReadJson;

public class RatePlanSteps {

	TestContext testContext;
	RatePlanPage ratePlanPage;
	WebDriver driver;
	public File jsonfile;
	public List<String> oldSummary;
	public List<List<String>> oldCallrate;
	public List<String> newSummary;
	public List<List<String>> newCallrate;
	public List<String> savedSummary;
	public List<List<String>> savedCallrate;
	boolean ignoreCase = false;
	public ConfigFileReader configReader;
	public String classPath;
	public MongoCollection<Document> coll;
	public String desOfDouClick = "";

	public RatePlanSteps(TestContext context) throws IOException {
		testContext = context;
		ratePlanPage = testContext.getPageObjectManager().getRatePlanPage();
		configReader = FileReaderManager.getInstance().getConfigReader();
		classPath = System.getProperty("user.dir") + configReader.getClassPath();
		driver = LoginSteps.driver;
	}

	@Then("^I should see the Select Bureau dropdown$")
	public void visibleSelectBureauDropBox() throws Throwable {
		if (ratePlanPage.visibleSelectBureauDropBox()) {
			Log.info("The dropdown Select Bureau is displayed on Rate Plan page");
		} else {
			seleniumHelper.setError(driver, "DontSeeDBoxSelectBureau",
					"The dropdown Select Bureau is NOT displayed on Rate Plan page. Maybe the web element is changed.");
		}
	}

	@Then("^I should see the label \"([^\"]*)\" value in dropbox by default$")
	public void defaultValueInBureauDropBox(String value) throws Throwable {
		ratePlanPage.getDefaultValueInSelectBureauDBox(value);
	}

	@Then("^I should see the title Rate Plans for the bureau \"([^\"]*)\"$")
	public void labelRatePlansForBureau(String bureauName) throws Throwable {
		ratePlanPage.checkLabelRatePlanSelectedBureau(bureauName);
	}

	@When("^I click on the Select Bureau dropdown$")
	public void clickBureauDbox() throws Throwable {
		ratePlanPage.clickBureauDBox();
	}

	@When("^I create the Json file for Bureau List$")
	public void createJsonBureauList() throws Throwable {
		ratePlanPage.createJsonBureauList();
	}

	@When("^I select the bureau \"([^\"]*)\" in Select Bureau dropdown$")
	public void selectSpecificBureau(String bureauName) throws Throwable {
		ratePlanPage.selectBureau(bureauName);
	}

	@Then("^The list of bureaus on website should be same as Json file$")
	public void compareBureauOnWebAndJson() throws Throwable {
		String bureauWeb = "";
		try {
			List<String> allBureaus = utilities.ReadJson.getAllBureaus();
			int totalBureau = ratePlanPage.getTotalBureauList();
			for (int i = 0; i < totalBureau; i++) {
				bureauWeb = ratePlanPage.getBureauValue(i);
				String bureauJson = allBureaus.get(i);
				if (bureauWeb.equalsIgnoreCase(bureauJson))
					Log.info("The value " + bureauWeb + " is displayed on website");
				else
					seleniumHelper.setError(driver, "DontSee" + bureauWeb,
							"The value " + bureauWeb + " is NOT displayed on website");

			}

		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSee" + bureauWeb, "The value bureau " + bureauWeb + " does not exist");
		}
	}

	@When("^I create the Json file for Rate Plan List under bureau \"([^\"]*)\"$")
	public void createJsonRatePlanList(String bureauName) throws Throwable {
		ratePlanPage.createJsonRatePlanList(bureauName);
	}

	@Then("^I should get rate plans under Rate Plan page$")
	public void getRatePlanList() throws Throwable {
		String bureau = ratePlanPage.getSelectedBureau();
		if (ratePlanPage.getTotaRatePlans() > 0) {
			Log.info("There are some Rate Plans under this bureau " + bureau);
		} else {
			Log.info("There is no Rate Plan under this bureau " + bureau);
		}
	}

	@Then("^I should see the filter textbox$")
	public void visibleFilterTxt() throws Throwable {
		if (ratePlanPage.visibleFilterTxt()) {
			Log.info("The filter textbox is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeFilterTxt", "The filter textbox is NOT displayed");
		}
	}

	@Then("^I enter the text \"([^\"]*)\" in the filter textbox$")
	public void enterFilterTxt(String value) throws Throwable {
		ratePlanPage.enterFilterTxt(value);
		Log.info("The value " + value + " is entered in the filter textbox");
	}

	@Then("^I click on Description, comment button of the filter group$")
	public void clickRadioDesCommentsFilter() throws Throwable {
		try {
			ratePlanPage.clickRadioDesCommentFilter();
			Log.info("I clicked on Description, comment button of the filter group");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeRadioDescriptionComments",
					"Cannot click on the btn Description, Comments of filter group");
		}
	}

	@Then("^I click on Rate Plan ID button of the filter group$")
	public void clickRadioRatePlanIDFilter() throws Throwable {
		try {
			ratePlanPage.clickRadioRatePlanIDFilter();
			Log.info("I clicked on Rate Plan ID button of the filter group");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeRadioRatePlanID",
					"Cannot click on the btn Rate Plan ID of filter group");
		}
	}

	@Then("^I click on All button of the filter group$")
	public void clickRadioAllFilter() throws Throwable {
		try {
			ratePlanPage.clickRadioAllFilter();
			Log.info("I clicked on All button of the filter group");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeRadioAllFilter", "Cannot click on the btn All of filter group");
		}
	}

	@Then("^Under each rate plan, I should see the text \"([^\"]*)\" in Description or Comments$")
	public void verifyDesCommentsFilterResult(String value) throws Throwable {
		Thread.sleep(1000);
		int totalRatePlan = ratePlanPage.getTotaRatePlans();
		if (totalRatePlan == 0) {
			Log.warn("There is no rate plan with Description or Comments: " + value);
		} else {
			Log.info("I got " + totalRatePlan + " row(s) after filtering the value: " + value);
			for (int i = 0; i < totalRatePlan; i++) {
				String description = ratePlanPage.getSummaryInfo("Description", i);
				String comments = ratePlanPage.getSummaryInfo("Comments", i);
				if (description.contains(value) || comments.contains(value)) {
					Log.info("Row [" + (i + 1) + "]: I should see the text '" + value + "' in Description or Comments");
				} else {
					seleniumHelper.setError(driver, "DontSeeDesComments" + value,
							"Row [" + (i + 1) + "]: I don't see the text '" + value + "' in Description or Comments");
				}
			}
		}
	}

	@Then("^Under each rate plan, I should see the value \"([^\"]*)\" in Rate Plan ID$")
	public void verifyRatePlanIDFilterResult(String value) throws Throwable {
		Thread.sleep(1000);
		int totalRatePlan = ratePlanPage.getTotaRatePlans();
		if (totalRatePlan == 0) {
			Log.warn("There is no Rate Plan ID: " + value);
		} else {
			Log.info("I got " + totalRatePlan + " row(s) after filtering the value: " + value);
			for (int i = 0; i < totalRatePlan; i++) {
				String ratePlanID = ratePlanPage.getSummaryInfo("Rate Plan Id", i);
				if (ratePlanID.contains(value)) {
					Log.info("Row [" + (i + 1) + "]: I should see the value '" + value + "' in Rate Plan ID");
				} else {
					seleniumHelper.setError(driver, "DontSeeRPID" + ratePlanID,
							"Row [" + (i + 1) + "]: I don't see the value '" + value + "' in Rate Plan ID");
				}
			}
		}
	}

	@Then("^Under each rate plan, I should see the value \"([^\"]*)\" in Description or Comments or Rate Plan ID$")
	public void verifyAllFilterResult(String value) throws Throwable {
		Thread.sleep(1000);
		int totalRatePlan = ratePlanPage.getTotaRatePlans();
		if (totalRatePlan == 0) {
			Log.warn("There is no rate plan with Description or Comments: " + value);
		} else {
			Log.info("I got " + totalRatePlan + " row(s) after filtering the value: " + value);
			for (int i = 0; i < totalRatePlan; i++) {
				String description = ratePlanPage.getSummaryInfo("Description", i);
				String comments = ratePlanPage.getSummaryInfo("Comments", i);
				String ratePlanID = ratePlanPage.getSummaryInfo("Rate Plan Id", i);
				if (description.contains(value) || comments.contains(value) || ratePlanID.contains(value)) {
					Log.info("Row [" + (i + 1) + "]: I should see the value '" + value
							+ "' in Description/ Comments/ Rate Plan ID");
				} else {
					seleniumHelper.setError(driver, "DontSeeDesCommentsRPID" + value, "Row [" + (i + 1)
							+ "]: I don't see the value '" + value + "' in Description/ Comments/ Rate Plan ID");
				}
			}
		}
	}

	@Then("^There is no rate plan$")
	public void noFilterResult() throws Throwable {
		int totalRatePlan = ratePlanPage.emptyRatePlans();
		if (totalRatePlan == 0) {
			Log.info("There is no rate plan");
		} else {
			seleniumHelper.setError(driver, "HasManyRatePlans", "There are some rate plans, that is not correct");
		}
	}

	@Then("^Under each rate plan, I should see the label \"([^\"]*)\"$")
	public void visibleLabelRatePlanInfo(String field) throws Throwable {
		int totalRatePlan = ratePlanPage.getTotaRatePlans();
		for (int i = 0; i < totalRatePlan; i++) {
			if (ratePlanPage.seeRatePlanLabel(field, i)) {
				Log.info("The lable " + field + " is displayed under rate plan with description "
						+ ratePlanPage.getSummaryInfo("Description", i));
			} else {
				seleniumHelper.setError(driver, "DontSeeLabelRatePlan" + field,
						"The lable " + field + " is NOT displayed under rate plan with description "
								+ ratePlanPage.getSummaryInfo("Description", i));
			}
		}

	}

	@Then("^Under first rate plan, I should see the label \"([^\"]*)\"$")
	public void seeLabelFirstRatePlanInfo(String field) throws Throwable {
		if (ratePlanPage.seeRatePlanLabel(field, 0))
			Log.info("The lable " + field + " is displayed under rate plan with description "
					+ ratePlanPage.getSummaryInfo("Description", 0));
		else
			seleniumHelper.setError(driver, "DontSeeLabelRatePlan" + field,
					"The lable " + field + " is NOT displayed under rate plan with description "
							+ ratePlanPage.getSummaryInfo("Description", 0));
	}

	@Then("^Under table, I should see the \"([^\"]*)\" column$")
	public void seeHeaderTableOfFirstRatePlan(String colName) throws Throwable {
		if (ratePlanPage.seeHeaderCallRate(colName)) {
			Log.info("Under table, I should see the " + colName + " column");
		} else {
			seleniumHelper.setError(driver, "DontSeeCol" + colName, "The column " + colName + " does not exist.");
		}

	}

	@Then("^I should see a table with detail info$")
	public void seeTableRatePlan() throws Throwable {
		if (ratePlanPage.seeTable())
			Log.info("Under first rate plan, I should see a table with detail info");
		else
			seleniumHelper.setError(driver, "DontSeeTabelRatePlan",
					"The table with detail info is NOT displayed under first rate plan");
	}

	@And("^I double click on row of first rate plan$")
	public void doubleClickFirstRatePlan() throws Throwable {
		String des = "";
		try {
			des = ratePlanPage.getDesAndScroll(0);
			ratePlanPage.doubleClickViewRatePlan(des);
			Log.info("I double click on rate plan: " + des);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "CannotDoubleClick", "Cannot double click on the description: " + des);
		}

	}

	@And("^I double click on first rate plan without IDD$")
	public void doubleClickFirstRatePlanNoIDD() throws Throwable {
		String des = "";
		try {
			des = ratePlanPage.getDesRPNoIDDAndScroll(0);
			ratePlanPage.doubleClickViewRatePlan(des);
			Log.info("I double click on rate plan: " + des);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "CannotDoubleClick", "Cannot double click on the description: " + des);
		}

	}

	@And("^I double click on first rate plan IDD$")
	public void doubleClickFirstRatePlanIDD() throws Throwable {
		try {
			desOfDouClick = ratePlanPage.getDesRPIDDAndScroll(0);
			ratePlanPage.doubleClickViewRatePlan(desOfDouClick);
			Log.info("I double click on rate plan: " + desOfDouClick);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "CannotDoubleClick",
					"Cannot double click on the description: " + desOfDouClick);
		}

	}

	@Then("^The \"([^\"]*)\" of Rate Plans on website should be same as Json file of Rate Plan page$")
	public void compareRatePlanWithBureauOnWebAndJson(String field) throws Throwable {
		String ratePlanDesWeb = "";
		String ratePlanDesJson = "";
		String description = "";
		List<List<String>> ratePlanList = utilities.ReadJson.getAllRatePlanSummary();
		try {
			int totalRatePlans = ratePlanPage.getTotaRatePlans();
			for (int i = 0; i < totalRatePlans; i++) {
				ratePlanDesWeb = ratePlanPage.getSummaryInfo(field, i);
				description = ratePlanPage.getSummaryInfo("Description", i);
				if (ratePlanPage.isPending(description)) {

				} else {
					int totalListInJson = ratePlanList.size();
					String desJson;
					switch (field) {
					case ("Comments"):
						for (int j = 0; j < totalListInJson; j++) {
							desJson = ratePlanList.get(j).get(3);
							if (description.equalsIgnoreCase(desJson)) {
								ratePlanDesJson = ratePlanList.get(j).get(0);
								break;
							}
						}

						break;
					case ("Rate Plan Id"):
						for (int j = 0; j < totalListInJson; j++) {
							desJson = ratePlanList.get(j).get(3);
							if (description.equalsIgnoreCase(desJson)) {
								ratePlanDesJson = ratePlanList.get(j).get(1);
								break;
							}
						}
						break;
					case ("Rate Plan Type"):
						for (int j = 0; j < totalListInJson; j++) {
							desJson = ratePlanList.get(j).get(3);
							if (description.equalsIgnoreCase(desJson)) {
								ratePlanDesJson = ratePlanList.get(j).get(2);
								break;
							}
						}
						break;
					case ("Description"):
						for (int j = 0; j < totalListInJson; j++) {
							desJson = ratePlanList.get(j).get(3);
							if (description.equalsIgnoreCase(desJson)) {
								ratePlanDesJson = ratePlanList.get(j).get(3);
								break;
							}
						}
						break;

					default:
						seleniumHelper.setError(driver, "DontSeeField" + field,
								"The field " + field + " does not exist on our system");
					}
					if (ratePlanDesWeb.replaceAll("\\s+$", "")
							.equalsIgnoreCase(ratePlanDesJson.replaceAll("\\s+$", ""))) {
						Log.info("The " + field + " >> " + ratePlanDesWeb
								+ " << is displayed for rate plan with description: " + description);
						break;
					} else {
						seleniumHelper.setError(driver, "DontSee" + field, "The " + field + " >> " + ratePlanDesWeb
								+ " << is NOT displayed for rate plan with description: " + description);
					}
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSee" + field,
					"There is something wrong with Json file: Json RatePlanList.js");
		}
	}

	@When("^I create the Json file for Call Rate of Rate Plan with Description \"([^\"]*)\"$")
	public void createJsonSpecificRatePlan(String des) throws Throwable {
		try {
			ratePlanPage.createJsonCallRateWithDes(des);
			Log.info("I created the Json file for Call Rate of Rate Plan with Description " + des);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSeeDes" + des,
					"Cannot create the Json file for rate plan with description: " + des);
		}

	}

	@Then("^All call rate info of Rate Plan with Description \"([^\"]*)\" on website should be same as Json file$")
	public void compareTableWithRatePlanOnWebAndJson(String des) throws Throwable {
		String ratePlanColumnWeb = "";
		String ratePlanColumnJson = "";
		String callRate = "";
		String[] webLabel = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		try {
			int ratePlanID = ratePlanPage.getNumberRatePlanIDWithDes(des);
			List<List<String>> allCallRateJson = utilities.ReadJson.getSepecificCallRate(ratePlanID);
			List<List<String>> allCallRateWeb = ratePlanPage.getCallRateAPIList(des);
			int totalCallRate = ratePlanPage.getTotalRowCallRateWithDes(des);
			for (int i = 0; i < totalCallRate; i++) {
				for (int j = 0; j < webLabel.length; j++) {
					callRate = webLabel[j];
					ratePlanColumnWeb = allCallRateWeb.get(i).get(j);
					ratePlanColumnJson = allCallRateJson.get(i).get(j);
					if (ratePlanColumnWeb.equalsIgnoreCase(ratePlanColumnJson))
						Log.info("The table with the " + callRate + " >> " + ratePlanColumnWeb
								+ " << is displayed on website");
					else
						seleniumHelper.setError(driver, "DontSeeCol" + ratePlanColumnJson, "The table with the "
								+ callRate + " >> " + ratePlanColumnJson + " << is NOT displayed on website");
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSeeValue" + ratePlanColumnJson,
					"The value rate plan with the " + callRate + " >> " + ratePlanColumnJson + " << does not exist");
		}
	}

	@Then("^I should see the Create New Rate Plan button$")
	public void visibleBtnCreateRatePlan() throws Throwable {
		if (ratePlanPage.seeBtnCreateNewRatePlan()) {
			Log.info("The Create New Rate Plan button is displayed");
		} else {
			seleniumHelper.setError(driver, "DontSeeCreateNewRatePlan",
					"Don't see the Create New Rate Plan button on website");
		}

	}

	@And("^I click on the Create New Rate Plan$")
	public void clickCreateRatePlan() throws Throwable {
		try {
			ratePlanPage.clickCreateRatePlan();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeCreateRatePlanBtn",
					"Cannot click on the button Create New Rate Plan");
		}

	}

	@And("^I select bureau \"([^\"]*)\" in dropdown Bureau target for Rate Plan$")
	public void selectBureauInCreateDialog(String bureau) throws Throwable {
		ratePlanPage.selectBureauCreateDialog(bureau);

	}

	@And("^I enter \"([^\"]*)\" in Description for the Rate Plan textbox$")
	public void enterDesInCreateDialog(String description) throws Throwable {
		ratePlanPage.enterDesCreateDialog(description);

	}

	@And("^I click on the checkbox IDD$")
	public void checkIDD() throws Throwable {
		ratePlanPage.checkBoxIDD();
	}

	@And("^I click on the Create button under Create dialog$")
	public void clickCreateInCreateDialog() throws Throwable {
		ratePlanPage.clickOnCreateBtnInCreateDialog("Create");
	}

	@And("^I click on the Cancel button under Create dialog$")
	public void clickCancelInCreateDialog() throws Throwable {
		ratePlanPage.clickOnCancelBtnInCreateCopyDialog();

	}

	@Then("^The New Rate Plan Dialog is displayed$")
	public void visibleCreateDialog() throws Throwable {
		boolean vi = ratePlanPage.visibleCreateDialog();
		if (vi) {
			Log.info("The New Rate Plan Dialog is displayed");
		} else {
			seleniumHelper.setError(driver, "NotClosedCreateDialog",
					"The New Rate Plan Dialog is still open instead of closed");
		}

	}

	@Then("^The New Rate Plan Dialog is closed$")
	public void notVisibleCreateDialog() throws Throwable {
		boolean vi = ratePlanPage.visibleCreateDialog();
		if (!vi) {
			Log.info("The Create dialog is closed");
		} else {
			seleniumHelper.setError(driver, "NotClosedCreateDialog",
					"The Create dialog is still open instead of closed");
		}

	}

	@Then("^New rate plan with description \"([^\"]*)\" is on top of Rate Plan list for bureau \"([^\"]*)\"$")
	public void verifyAtTopOfNewRatePlan(String description, String bureau) throws Throwable {
		if (ratePlanPage.validSelectedBureau(bureau)) {
			if (ratePlanPage.seeNewRatePlanAtTop(description))
				Log.info("The new Rate Plan with description " + description
						+ " is displayed at the top of the rate plan list");
			else
				seleniumHelper.setError(driver, "NewRatePlanDoesntShowAtTop",
						"Dont see the new Rate Plan with description " + description
								+ " at the top of the rate plan list");
		} else {
			seleniumHelper.setError(driver, "WrongSelectedBureau",
					"The bureau " + bureau + " is not selected on drop down");
		}

	}

	@Then("^I should see \"([^\"]*)\" value of this new one should be \"([^\"]*)\"$")
	public void verifyValuesNewRatePlan(String field, String value) throws Throwable {

	}

	@Then("^I should see the Save button of rate plan record$")
	public void saveChangeOfRatePlan() throws Throwable {
		if (ratePlanPage.visibleBtnSaveChangeOfRatePlan()) {
			Log.info("I should see the Save button of rate plan record");
		} else
			seleniumHelper.setError(driver, "DontSeeSaveOfChangeBtn",
					"I don't see the Save button of rate plan record");
	}

	@Then("^I should see the Cancel button of rate plan record$")
	public void cancelChangeOFRatePlan() throws Throwable {
		if (ratePlanPage.visibleBtnCancelChangeOfRatePlan()) {
			Log.info("I should see the Cancel button of rate plan record");
		} else
			seleniumHelper.setError(driver, "DontSeeCancelOfChangeBtn",
					"I don't see the Cancel button of rate plan record");
	}

	@And("^Under Summary fields, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" of this new one$")
	public void enterTxtBoxOfSummaryInfo(String value, String field) throws Throwable {
		value = ratePlanPage.isCSVSummaryInfo(value, field);
		ratePlanPage.enterTxtSummaryInfoAtTop(value, field);
	}

	@And("^Under Call Rate without IDD, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" of first row$")
	public void enterTxtBoxOfCallRateNoIDD(String value, String field) throws Throwable {
		try {
			value = ratePlanPage.isCSVNormalCallRate(value, field);
			ratePlanPage.enterTxtCallRateNoIDDAtTop(value, field);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElement" + field,
					"The website element of the field " + field + " is changed");
		}
	}

	@And("^Under Call Rate with IDD, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" of first row$")
	public void enterTxtBoxOfCallRateWithIDD(String value, String field) throws Throwable {
		try {
			ratePlanPage.enterTxtCallRateWithIDDAtTop(value, field);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElement" + field,
					"The website element of the field " + field + " is changed");
		}
	}

	@And("^Under Summary fields, I select the value \"([^\"]*)\" for the \"([^\"]*)\" of this new one$")
	public void selectTxtBoxOfSummaryInfo(String value, String field) throws Throwable {
		value = ratePlanPage.isCSVSummaryInfo(value, field);
		ratePlanPage.selectDropBoxSummaryInfoAtTop(value, field);
	}

	@And("^Under Call Rate without IDD, I select the value \"([^\"]*)\" for the \"([^\"]*)\" of first row$")
	public void selectTxtBoxOfCallRateNoIDD(String value, String field) throws Throwable {
		try {
			value = ratePlanPage.isCSVNormalCallRate(value, field);
			ratePlanPage.selectDropBoxCallRateNoIDDAtTop(value, field);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value,
					"The value " + value + " does not exist in " + field + " of system");
		}
	}

	@And("^Under Call Rate with IDD, I select the value \"([^\"]*)\" for Floating Credit Included of first row$")
	public void selectTxtBoxOfCallRateWithIDD(String value) throws Throwable {
		String field = "Floating Credit Included";
		try {
			ratePlanPage.selectFloatingCreditWithIDDAtTop(value);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value,
					"The value " + value + " does not exist in " + field + " of system");
		}
	}

	@And("^Under Call Rate without IDD, I select the value \"([^\"]*)\" for the \"([^\"]*)\" of new row$")
	public void selectNewCallRateNoIDD(String value, String field) throws Throwable {
		try {
			int lastCallRate = oldCallrate.size() + 1;
			ratePlanPage.selectDropBoxNewCallRate(value, field, lastCallRate);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value + "InDbox" + field,
					"Cannot select the value " + value + " in dropbox: " + field);
		}

	}

	@And("^Under Call Rate without IDD, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" of new row$")
	public void enterNewCallRateNoIDD(String value, String field) throws Throwable {
		try {
			ratePlanPage.enterTxtCallRateNoIDDAtLast(value, field);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value + "InDbox" + field,
					"Cannot select the value " + value + " in new row of column: " + field);
		}

	}

	@And("^Under Call Rate with IDD, I select the value \"([^\"]*)\" for Floating Credit Included of new row$")
	public void selectNewCallRateWithIDD(String value) throws Throwable {
		String field = "Floating Credit Included";
		try {
			ratePlanPage.selectFloatingCreditWithIDDAtLast(value);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value + "InDbox" + field,
					"Cannot select the value " + value + " in dropbox: " + field);
		}

	}

	@And("^Under Call Rate with IDD, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" of new row$")
	public void enterNewCallRateIDD(String value, String field) throws Throwable {
		try {
			// int lastCallRate = oldCallrate.size() + 1;
			ratePlanPage.enterTxtCallRateWithIDDAtLast(value, field);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeValue" + value + "InDbox" + field,
					"Cannot select the value " + value + " in new row of column: " + field);
		}

	}

	@And("^I click on the Save button of rate plan record$")
	public void clickSaveChangeOfRatePlan() throws Throwable {
		savedSummary = ratePlanPage.getSavedSummaryInfo();
		savedCallrate = ratePlanPage.getSavedCallRateList();
		ratePlanPage.clickOnSaveChangeOfRatePlan();
	}

	@And("^I only click on the Save button$")
	public void clickSaveBtnOfRatePlan() throws Throwable {
		ratePlanPage.clickOnSaveChangeOfRatePlan();
	}

	@And("^I click on the Cancel button of rate plan record$")
	public void clickCancelChangeOfRatePlan() throws Throwable {
		ratePlanPage.clickOnCancelChangeOfRatePlan();
	}

	@Then("^The Rate Plan with Description \"([^\"]*)\" is saved successfully$")
	public void saveNewRatePlanSuccessfully(String description) throws Throwable {
		if (ratePlanPage.saveRatePlanSuccessfully(description)) {
			Log.info("The rate plan with Description " + description + " is saved successfully");
		} else
			seleniumHelper.setError(driver, "DontSeeRatePlanWithDes" + description,
					"The rate plan with Description " + description + " is NOT saved");
	}

	@Then("^The Rate Plan with Description \"([^\"]*)\" is NOT saved successfully$")
	public void saveNewRatePlanNOTSuccessfully(String description) throws Throwable {
		if (!ratePlanPage.saveRatePlanSuccessfully(description)) {
			Log.info("The rate plan with Description " + description + " is NOT saved successfully");
		} else
			seleniumHelper.setError(driver, "DontSeeRatePlanWithDes" + description,
					"The rate plan with Description --- " + description
							+ " --- is displayed, it is not correct because I cancel the rate plan");
	}

	@Then("^I should see Rate Plan with Description \"([^\"]*)\"$")
	public void visibleRatePlanList(String description) throws Throwable {
		if (ratePlanPage.visibleRPWithDes(description))
			Log.info("The rate plan with description " + description + " is displayed");
		else
			seleniumHelper.setError(driver, "DontSeeRPDes" + description,
					"The rate plan with description " + description + " does not exist");
	}

	@And("^I click on \"([^\"]*)\" button for Rate Plan with Description \"([^\"]*)\"$")
	public void clickRatePlanBtn(String btnName, String description) throws Throwable {
		try {
			ratePlanPage.clickBtnWithRPDes(btnName, description);
			Log.info("I click on " + btnName + " button for Rate Plan with Description: " + description);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtn" + btnName,
					"The website element of button " + btnName + " is changed");
		}
	}

	@And("^I click on \"([^\"]*)\" button of Rate Plan Id \"([^\"]*)\"$")
	public void clickRatePlanBtn(String btnName, int ratePlanID) throws Throwable {
		try {
			ratePlanPage.clickActionBtnOfRPID(btnName, ratePlanID);
			Log.info("I click on " + btnName + " button for Rate Plan Id: " + ratePlanID);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "CannotClickActionBtn" + btnName,
					"Cannot click on the btn " + btnName + " of rate lan id: " + ratePlanID);
		}
	}

	@And("^I delete Rate Plan with Description \"([^\"]*)\"$")
	public void deleteRatePlan(String btnName, String description) throws Throwable {
		ratePlanPage.clickBtnWithRPDes(btnName, description);
		Log.info("I click on " + btnName + " button for Rate Plan with Description: " + description);
	}

	@And("^Under Summary fields, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" under edit state$")
	public void enterTxtBoxOfSummaryInfoEdit(String value, String field) throws Throwable {
		value = ratePlanPage.isCSVSummaryInfo(value, field);
		ratePlanPage.enterTxtSummaryInfoAtTop(value, field);
		Log.info("I enter the value " + value + " for the field " + field);
	}

	@And("^Under Call Rate, I enter the value \"([^\"]*)\" for the \"([^\"]*)\" under edit state$")
	public void enterTxtBoxOfRatePlanEdit(String value, String field) throws Throwable {
		value = ratePlanPage.isCSVNormalCallRate(value, field);
		ratePlanPage.enterTxtCallRateNoIDDAtTop(value, field);
		Log.info("I enter the value " + value + " for the field " + field);
	}

	@And("^Under Summary fields, I select the value \"([^\"]*)\" for the \"([^\"]*)\" under edit state$")
	public void selectTxtBoxOfSummaryInfoEdit(String value, String field) throws Throwable {
		try {
			value = ratePlanPage.isCSVSummaryInfo(value, field);
			ratePlanPage.selectDropBoxSummaryInfoAtTop(value, field);
			Log.info("I select the value " + value + " for the field " + field);
		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSeeValue" + value,
					"The value " + value + " does not exist in " + field + " of system");
		}
	}

	@And("^Under Call Rate, I select the value \"([^\"]*)\" for the \"([^\"]*)\" under edit state$")
	public void selectTxtBoxOfRatePlanEdit(String value, String field) throws Throwable {
		value = ratePlanPage.isCSVNormalCallRate(value, field);
		ratePlanPage.selectDropBoxCallRateNoIDDAtTop(value, field);
		Log.info("I select the value " + value + " for the field " + field);
	}

	@And("^I click on the caculation icon next to \"([^\"]*)\" textbox$")
	public void clickCalculator(String field) throws Throwable {
		try {
			ratePlanPage.openCalculator(field, 1);
			Log.info("I click on the caculation icon next to " + field + " textbox");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeColumn" + field, "The website element " + field + " is changed.");
		}
	}

	@Then("^I should see the \"([^\"]*)\" dialog$")
	public void visibleCalPopup(String field) throws Throwable {
		if (ratePlanPage.visibleFieldsInCalculation(field)) {
			Log.info("I should see the " + field + " dialog");
		} else
			seleniumHelper.setError(driver, "DontSee" + field + "Dialog", "I don't see the " + field + " dialog");
	}

	@Then("^I should see the textbox \"([^\"]*)\"$")
	public void visibleTxtOfCal(String field) throws Throwable {
		if (ratePlanPage.visibleFieldsInCalculation(field)) {
			Log.info("I should see the textbox " + field);
		} else
			seleniumHelper.setError(driver, "DontSee" + field + "TxtBox", "I don't see the textbox " + field);
	}

	@Then("^I should see the dropdown \"([^\"]*)\" in calculation popup$")
	public void visibleDropDownInCal(String field) throws Throwable {
		if (ratePlanPage.visibleFieldsInCalculation(field)) {
			Log.info("I should see the dropdown " + field);
		} else
			seleniumHelper.setError(driver, "DontSee" + field + "DropDown", "I don't see the dropdown " + field);
	}

	@Then("^I should see the field \"([^\"]*)\"$")
	public void visibleFieldOfCal(String field) throws Throwable {
		if (ratePlanPage.visibleFieldsInCalculation(field)) {
			Log.info("I should see the field " + field);
		} else
			seleniumHelper.setError(driver, "DontSee" + field + "field", "I don't see the field " + field);
	}

	@Then("^I should see the \"([^\"]*)\" button of calculation popup$")
	public void visibleBtnOfCal(String btnName) throws Throwable {
		if (ratePlanPage.visibleBtnOfCal(btnName)) {
			Log.info("I should see the button " + btnName + " of calculation popup");
		} else
			seleniumHelper.setError(driver, "DontSee" + btnName + "btn",
					"I don't see the button " + btnName + " of calculation popup");
	}

	@And("^I click on \"([^\"]*)\" button of calculation popup$")
	public void clickBtnOfCal(String btnName) throws Throwable {
		try {
			ratePlanPage.clickBtnOfCal(btnName);
			Log.info("The button " + btnName + " of calculation popup is clicked");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtn" + btnName, "The website element " + btnName + " is changed");
		}

	}

	@Then("^The Wholesale to Retail Calculator dialog is closed$")
	public void dontSeeWholeSalePopup() throws Throwable {
		if (!ratePlanPage.seeWholseSalePopup()) {
			Log.info("The Wholesale to Retail Calculator dialog is closed");
		} else
			seleniumHelper.setError(driver, "DontSeeWholeSalesDialog",
					"The Wholesale to Retail Calculator dialog is NOT closed");
	}

	@Then("^The Data Calculator to KB dialog is closed$")
	public void closedCalPopup() throws Throwable {
		if (!ratePlanPage.seeDataCalculatorPopup()) {
			Log.info("The Data Calculator to KB dialog is closed");
		} else
			seleniumHelper.setError(driver, "DontSeeDataCalculatorDialog",
					"The Data Calculator to KB dialog is NOT closed");
	}

	@And("^I enter the value \"([^\"]*)\" on textbox \"([^\"]*)\"$")
	public void enterTxtOfCal(String value, String field) throws Throwable {
		try {
			ratePlanPage.enterTxtInCal(field, value);
			Log.info("The value " + value + " in textbox " + field + " of calculation popup is entered");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeTxt" + field, "The website element " + field + " is changed");
		}

	}

	@Then("^I should see the value \"([^\"]*)\" for the Rate per Cost in calculation popup$")
	public void validResultInCal(String value) throws Throwable {
		if (ratePlanPage.validValueRatePerCostInCal(value)) {
			Log.info("The " + value + " of Rate per Cost in calculator popup is correct");
		} else
			seleniumHelper.setError(driver, "Invalid" + value + "OfRatePerCost",
					"The " + value + " of Rate per Cost in calculator popup is not correct");
	}

	@Then("^I should see the value \"([^\"]*)\" for the Rate per Cost in call rate$")
	public void validResultFromCalculatorToCallRate(String value) throws Throwable {
		if (ratePlanPage.validResultFromCalculatorToCallRate(1, "Rate per Cost", value)) {
			Log.info("The " + value + " of Rate per Cost in call rate is correct");
		} else
			seleniumHelper.setError(driver, "Invalid" + value + "OfRatePerCost",
					"The " + value + " of Rate per Cost in call rate is not correct");
	}

	@And("^I select the \"([^\"]*)\" in Data Type dropdown$")
	public void selectDataTypeInCal(String dataType) throws Throwable {
		ratePlanPage.selectDataType(dataType);
		Log.info("The Data Type with " + dataType + " of calculation popup is selected");

	}

	@Then("^I should see the value \"([^\"]*)\" for the Result$")
	public void validResultKBInCal(String value) throws Throwable {
		if (ratePlanPage.validValueRatePerKBInCal(value)) {
			Log.info("The " + value + " of Rate per KB in calculator popup is correct");
		} else
			seleniumHelper.setError(driver, "Invalid" + value + "OfRatePerKB",
					"The " + value + " of Rate per KB in calculator popup is not correct");
	}

	@Then("^I should see the value \"([^\"]*)\" for Rate per KB in call rate$")
	public void validResultKBFromCalculatorToCallRate(String value) throws Throwable {
		if (ratePlanPage.validResultFromCalculatorToCallRate(1, "Rate per KB", value)) {
			Log.info("The " + value + " of Rate per KB in call rate is correct");
		} else
			seleniumHelper.setError(driver, "Invalid" + value + "OfRatePerKB",
					"The " + value + " of Rate per KB in call rate is not correct");
	}

	@Then("^The Copy Rate Plan Dialog is displayed$")
	public void visibleCopyDialog() throws Throwable {
		if (ratePlanPage.visibleCopyDialog()) {
			Log.info("The Copy Rate Plan Dialog is displayed");
		} else
			seleniumHelper.setError(driver, "DontSeeCopyDialog", "The Copy Rate Plan Dialog is NOT displayed");
	}

	@Then("^I should see the dropdown Bureau for target Rate Plan in dialog$")
	public void visibleDropDownInCreateOrCopyDialog() throws Throwable {
		if (ratePlanPage.seeDropboxBureauTargetRP()) {
			Log.info("The dropdown 'Please select a bureau for target Rate Plan' is displayed in the popup");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateCopyDialog",
					"The dropdown 'Please select a bureau for target Rate Plan' is NOT displayed");
	}

	@Then("^The bureau \"([^\"]*)\" is selected in dropdown Bureau for target Rate Plan under popup$")
	public void defaultBureauInCreateOrCopyDialog(String bureau) throws Throwable {
		if (ratePlanPage.visibleDefaultBureauOnCreateOrCopyDialog(bureau)) {
			Log.info("The bureau " + bureau + " is selected in dropdown Bureau for target Rate Plan under popup");
		} else
			seleniumHelper.setError(driver, "DontSeeDefaultBureau" + bureau,
					"The bureau " + bureau + " is NOT selected in dropdown Bureau for target Rate Plan under popup");
	}

	@Then("^I should see the textbox Description for the Rate Plan in dialog$")
	public void visibleTxtboxInCreateOrCopyDialog() throws Throwable {
		if (ratePlanPage.seeTxtboxDescriptionRP()) {
			Log.info("The textbox 'Description for the Rate Plan' is displayed");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateCopyDialog",
					"The textbox 'Description for the Rate Plan' is NOT displayed");
	}

	@Then("^The description textbox is empty by default$")
	public void emptyDescriptionOfCreateOrCopy() throws Throwable {
		if (ratePlanPage.emptyInDescriptionOfCreateOrCopy()) {
			Log.info("The textbox desciption for Rate Plan in dialog is empty");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateCopyDialog",
					"The textbox desciption for Rate Plan in dialog is NOT empty");
	}

	@Then("^The description textbox is auto filled \"([^\"]*)\"$")
	public void copiedDescriptionOfCreateOrCopy(String des) throws Throwable {
		if (ratePlanPage.equalDesOfCreateOrCopy(des)) {
			Log.info("The textbox desciption for Rate Plan in dialog is auto filled with value: " + des);
		} else
			seleniumHelper.setError(driver, "DontSeeValue" + des, "Don't see the description " + des);
	}

	@Then("^Copy button should be inactive$")
	public void enableCreateOrCopyBtnInDialog() throws Throwable {
		if (ratePlanPage.disableCreateOrCopyBtnInDialog()) {
			Log.info("The copy button is disable");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateCopyDialog",
					"The website element of copy button is changed.");
	}

	@Then("^Copy button should be active$")
	public void disableCreateOrCopyBtnInDialog() throws Throwable {
		if (ratePlanPage.enableCreateOrCopyBtnInDialog()) {
			Log.info("The copy button is enable");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateCopyDialog", "The copy button is disable");
	}

	@And("^I click on the Copy button under Copy dialog$")
	public void clickCopyInCreateDialog() throws Throwable {
		ratePlanPage.clickOnCreateBtnInCreateDialog("Copy");
	}

	@And("^I click on the Cancel button under Copy dialog$")
	public void clickCancelInCopyDialog() throws Throwable {
		ratePlanPage.clickOnCancelBtnInCreateCopyDialog();
	}

	@Then("^I should see the Create button in dialog$")
	public void visibleBtnCreateInDialog() throws Throwable {
		if (ratePlanPage.seeBtnCreateCopyInPopup()) {
			Log.info("The Create button is displayed in dialog");
		} else
			seleniumHelper.setError(driver, "DontSeeCreateBtnInDialog", "The Create button is NOT displayed in dialog");
	}

	@Then("^I should see the Copy button in dialog$")
	public void visibleBtnCopyInDialog() throws Throwable {
		if (ratePlanPage.seeBtnCreateCopyInPopup()) {
			Log.info("The Copy button is displayed in dialog");
		} else
			seleniumHelper.setError(driver, "DontSeeCopyBtnInDialog", "The Copy button is NOT displayed in dialog");
	}

	@Then("^I should see the Cancel button in dialog$")
	public void visibleBtnCancelInDialog() throws Throwable {
		if (ratePlanPage.seeBtnCancelInPopup()) {
			Log.info("The Cancel button is displayed in dialog");
		} else
			seleniumHelper.setError(driver, "DontSeeCancelBtnInDialog", "The Cancel button is NOT displayed in dialog");
	}

	@Then("^I should see the International Direct Dial rate plan in dialog$")
	public void visibleCboxIDDInDialog() throws Throwable {
		if (ratePlanPage.seeBtnCancelInPopup()) {
			Log.info("The Cancel button is displayed in dialog");
		} else
			seleniumHelper.setError(driver, "DontSeeCancelBtnInDialog", "The Cancel button is NOT displayed in dialog");
	}

	@Then("^I should see the message that Description existed$")
	public void visibleMsgExistedDes() throws Throwable {
		if (ratePlanPage.visibleMsgExistedDes()) {
			Log.info("The message >> Description existed >> is displayed");
		} else
			seleniumHelper.setError(driver, "DontSeeCancelBtnInDialog",
					"The message >> Description existed >> is NOT displayed");
	}

	@And("^I click on the dropdown Please select a bureau for target rate plan$")
	public void clickTargetBureauDboxInCreateCopyDialog() throws Throwable {
		ratePlanPage.clickTargetBureauDBox();
	}

	@Then("^I should see the list of Bureau for Target Rate Plan should be same as Json file$")
	public void compareTargetBureauOnWebAndJson() throws Throwable {
		String bureauWeb = "";
		String bureauJson = "";
		boolean status = false;
		jsonfile = new File(classPath + "/testDataResources/Json BureauList.js");
		try {
			int totalBureau = ratePlanPage.getTotalTargetBureauList();
			for (int i = 0; i < totalBureau; i++) {
				bureauWeb = ratePlanPage.getTargetBureauValue(i);
				for (int j = 0; j < totalBureau; j++) {
					bureauJson = JsonPath.read(jsonfile, "$." + "data[" + j + "]").toString();
					if (bureauWeb.equalsIgnoreCase(bureauJson)) {
						Log.info("The value " + bureauWeb + " is displayed on website");
						status = true;
					}
				}
				if (!status) {
					String screenShotPath = seleniumHelper.capture(driver, "DontSee" + bureauWeb);
					Reporter.addScreenCaptureFromPath(screenShotPath);
					Log.error("The value " + bureauWeb + " is NOT displayed on website");
					Assert.fail("The value " + bureauWeb + " is NOT displayed on website");
				}

			}

		} catch (Exception ex) {
			System.out.println(ex);
			Log.error("The value bureau " + bureauWeb + " does not exist");
			Assert.fail("The value bureau " + bureauWeb + " does not exist");
		}
	}

	// delete
	@Then("^Delete button of Rate Plan with Description \"([^\"]*)\" is inactive$")
	public void disableDeleteBtn(String des) throws Throwable {
		if (ratePlanPage.disableBtnWithRPDes("Delete", des)) {
			Log.info("Delete button of Rate Plan with Description " + des + " is inactive");
		} else
			seleniumHelper.setError(driver, "DontSeeDeleteBtn",
					"The element of Delete button is changed or it is enable.");
	}

	@Then("^Delete button of Rate Plan with Description \"([^\"]*)\" is active$")
	public void enableDeleteBtn(String des) throws Throwable {
		if (ratePlanPage.enableBtnWithRPDes("Delete", des)) {
			Log.info("Delete button of Rate Plan with Description " + des + " is active");
		} else
			seleniumHelper.setError(driver, "DontSeeDeleteBtn",
					"The element of Delete button is changed or it is disable.");
	}

	@Then("^I should see the Delete Confirmation popup$")
	public void visibleMsgConfimDelete() throws Throwable {
		if (ratePlanPage.visibleMsgConfirmDelete()) {
			Log.info("The Delete Confirmation popup is displayed");
		} else
			seleniumHelper.setError(driver, "DontMsgConfirmDelete", "The Delete Confirmation popup is displayed");
	}

	@And("^I click on the Confirm button$")
	public void clickConfirmBtn() throws Throwable {
		ratePlanPage.clickConfirmPopup();
	}

	@And("^I click on the Cancel button$")
	public void clickCancelBtn() throws Throwable {
		ratePlanPage.clickCloseConfirmPopup();
	}

	@Then("^Under not pending Rate Plan, \"([^\"]*)\" button is active$")
	public void enableBtnRatePlanWithNoPending(String btnName) throws Throwable {
		try {
			String des = ratePlanPage.getDesWithoutPending(0);
			if (ratePlanPage.enableBtnWithRPDes(btnName, des))
				Log.info("Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is active");
			else
				seleniumHelper.setError(driver, des + "NotActive" + btnName,
						"Under not pending Rate Plan with desciption: " + des + ", " + btnName
								+ " button is not active");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "NoRatePlanNotPending", "There is no rate plan without pending status");
		}
	}

	@Then("^Under not pending Rate Plan, Delete button is inactive$")
	public void disableBtnRatePlanWithNoPending() throws Throwable {
		try {
			String btnName = "Delete";
			String des = ratePlanPage.getDesWithoutPending(0);
			if (!ratePlanPage.enableBtnWithRPDes(btnName, des))
				Log.info(
						"Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is inactive");
			else
				seleniumHelper.setError(driver, des + "NotActive" + btnName,
						"Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is active");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "NoRatePlanNotPending", "There is no rate plan without pending status");
		}
	}

	@Then("^Under pending Rate Plan and owner, \"([^\"]*)\" button is active$")
	public void enableBtnRatePlanWithPendingOwner(String btnName) throws Throwable {
		String des = "";
		des = ratePlanPage.getDesRPWithPendingOwner();
		if (ratePlanPage.enableBtnWithRPDes(btnName, des)) {
			Log.info("Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is active");
		} else if (des.isEmpty()) {

		} else
			seleniumHelper.setError(driver, "SomethingWrong", "There is something wrong!");

	}

	@Then("^Under pending Rate Plan and owner \"([^\"]*)\", \"([^\"]*)\" button is active$")
	public void enableBtnRatePlanWithPendingOwner(String des, String btnName) throws Throwable {
		if (ratePlanPage.enableBtnWithRPDes(btnName, des))
			Log.info("Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is active");
		else
			seleniumHelper.setError(driver, "SomethingWrong", "There is something wrong!");

	}

	@Then("^Under pending Rate Plan and not owner, \"([^\"]*)\" button is inactive$")
	public void enableBtnRatePlanWithPendingNotOwner(String btnName) throws Throwable {
		String des = "";
		des = ratePlanPage.getDesRPWithPendingNotOwner();
		if (!ratePlanPage.enableBtnWithRPDes(btnName, des)) {
			Log.info("Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is inactive");
		} else if (des.isEmpty()) {

		} else
			seleniumHelper.setError(driver, "SomethingWrong", "There is something wrong!");

	}

	@Then("^Under pending Rate Plan and not owner \"([^\"]*)\", \"([^\"]*)\" button is inactive$")
	public void enableBtnRatePlanWithPendingNotOwner(String des, String btnName) throws Throwable {
		if (ratePlanPage.disableBtnWithRPDes(btnName, des))
			Log.info("Under not pending Rate Plan with desciption: " + des + ", " + btnName + " button is inactive");
		else
			seleniumHelper.setError(driver, "SomethingWrong", "There is something wrong!");

	}

	@Then("^Under pending Rate Plan and not owner, Copy button is active$")
	public void enableBtnRatePlanWithPendingNotOwner() throws Throwable {
		String des = "";
		des = ratePlanPage.getDesRPWithPendingNotOwner();
		if (ratePlanPage.enableBtnWithRPDes("Copy", des)) {
			Log.info("Under not pending Rate Plan with desciption: " + des + ", Copy button is active");
		} else if (des.isEmpty()) {

		} else
			seleniumHelper.setError(driver, "SomethingWrong", "There is something wrong!");

	}

	@Then("^Under pending Rate Plan and not owner \"([^\"]*)\", Copy button is active$")
	public void enableCopyBtnRatePlanWithPendingNotOwner(String des) throws Throwable {
		if (ratePlanPage.enableBtnWithRPDes("Copy", des)) {
			Log.info("Under not pending Rate Plan with desciption: " + des + ", Copy button is active");
		} else
			seleniumHelper.setError(driver, "NoRatePlanWithPendingAndNotOwner", "There is no rate plan " + des);

	}

	// service number list of rate plan
	@Then("^I should see the list icon for Rate Plan with Description \"([^\"]*)\"$")
	public void visibleBtnListSerNumWithDes(String des) throws Throwable {
		if (ratePlanPage.seeListSerNumIconWithDes(des)) {
			Log.info("I should see the list icon for Rate Plan with Description: " + des);
		} else
			seleniumHelper.setError(driver, "DontSeeRatePlan", "Don't see the rate plan with Description: " + des);

	}

	@And("^I click on the list icon of Rate Plan with Description \"([^\"]*)\"$")
	public void clickBtnListSerNumWithDes(String des) throws Throwable {
		ratePlanPage.clickListSerNumIconWithDes(des);
	}

	@And("^I click on the list icon of Rate Plan Id \"([^\"]*)\"$")
	public void clickBtnListSerNumWithRPID(int ratePlanID) throws Throwable {
		ratePlanPage.clickListSerNumIconWithRPID(ratePlanID);
	}

	@Then("^I should see the Service Numbers dialog with Description \"([^\"]*)\"$")
	public void visibleSerNumPopupWithDes(String des) throws Throwable {
		if (ratePlanPage.seeSerNumPopup(des)) {
			Log.info("I should see the Sevice Number popup of rate plan with Description: " + des);
		} else
			seleniumHelper.setError(driver, "DontSeeSerNumPopup",
					"Don't see the Service Number popup of rate plan with Description: " + des);

	}

	@Then("^I should NOT see the Service Numbers dialog$")
	public void dontSeeSerNumPopupWithDes() throws Throwable {
		if (!ratePlanPage.dontSeeSerNumPopup()) {
			Log.info("I should NOT see the Sevice Number popup");
		} else
			seleniumHelper.setError(driver, "StillSeeSerNumPopup", "Still see the Service Number popup");

	}

	@Then("^I should see the Download as CSV button$")
	public void visibleDownloadCSVSerNum() throws Throwable {
		if (ratePlanPage.visibleDownLoadCSVSerNum()) {
			Log.info("The Download as CSV button is displayed");
		} else
			seleniumHelper.setError(driver, "DontSeeSerNumPopup", "The Download as CSV button is not displayde");

	}

	@Then("^I should see the header \"([^\"]*)\"$")
	public void visibleHeadersSerNum(String headerSerNum) throws Throwable {
		if (ratePlanPage.seeHeadersSerNum(headerSerNum)) {
			Log.info("I should see the header " + headerSerNum);
		} else
			seleniumHelper.setError(driver, "DontSeeSerNumPopup", "Don't see the header " + headerSerNum);

	}

	@And("^I close the popup$")
	public void closePopup() throws Throwable {
		ratePlanPage.clickClosePopup();
	}

	@Then("^I got a list of Service Numbers$")
	public void hasRowInSerNum() throws Throwable {
		int totalSerNumRow = ratePlanPage.getTotalSerNumRow();
		if (totalSerNumRow > 0) {
			Log.info("I got " + totalSerNumRow + " row(s) in Service Number popup");
		} else
			Log.info("The Service Number has no row.");

	}

	@When("^I create Json file for Service Number of Rate Plan ID \"([^\"]*)\" under bureau \"([^\"]*)\"$")
	public void createJsonSerNumWithDes(int id, String bureauName) throws Throwable {
		ratePlanPage.createJsonSerNumWithDes(id, bureauName);
	}

	@When("^Under Rate Plan Admin page, I create the Json file for Service Number of Rate Plan with Description \"([^\"]*)\"$")
	public void createJsonSerNumWithDesAd(String des) throws Throwable {
		ratePlanPage.createJsonSerNumWithDesAd(des);
	}

	@Then("^All values on web should be same as on Json file of Service Number under Rate Plan ID \"([^\"]*)\"$")
	public void compareSerNumOnWebAndJson(int ratePlanID) throws Throwable {
		String valueColSerNumJson = "";
		String headerSerNum = "";
		String[] webLabel = { "CIDN", "Salutation", "First Name", "Last Name", "Address", "Contact Number", "Email",
				"Service Number" };
		try {
			List<List<String>> allSerNum = ReadJson.getSerNumList(ratePlanID);
			int totalSerNumRow = allSerNum.size();
			for (int i = 0; i < totalSerNumRow; i++) {
				List<String> serNum = allSerNum.get(i);
				for (int j = 0; j < webLabel.length; j++) {
					headerSerNum = webLabel[j];
					String valueColSerNumWeb = ratePlanPage.getValueColsSerNum(i, webLabel[j]);
					valueColSerNumJson = serNum.get(j);
					if (valueColSerNumWeb.equalsIgnoreCase(valueColSerNumJson))
						Log.info("The value " + valueColSerNumWeb + " is displayed on website");
					else
						seleniumHelper.setError(driver, "DontSee" + valueColSerNumWeb, "The value " + valueColSerNumWeb
								+ " of header " + headerSerNum + " is NOT displayed on website");
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
			seleniumHelper.setError(driver, "DontSee" + valueColSerNumJson,
					"The value " + valueColSerNumJson + " of header " + headerSerNum + " is NOT displayed on website");
		}
	}

	@And("^I can download file by clicking on the Download as CSV button$")
	public void clickDownLoadSerNum() throws Throwable {
		ratePlanPage.clickDownLoadSerNum();
	}

	@And("^I double click on the row with CIDN \"([^\"]*)\"$")
	public void doubleClickSerNumRow(int cidn) throws Throwable {
		try {
			ratePlanPage.doubleClickSerNumRow(cidn);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeSerNumRow",
					"There is no row with cidn " + cidn + ", so cannot double click");
		}
	}

	@Then("The new window is open with the URL \"([^\"]*)\"$")
	public void getNewWindows(String expectedURL) throws Throwable {
		String actualURL = ratePlanPage.getNewWindow();
		System.out.println("url----" + actualURL);
		if (expectedURL.equalsIgnoreCase(actualURL)) {
			Log.info("The new windows is open with the URL " + expectedURL);
		} else if (actualURL
				.equalsIgnoreCase("https://cindy.telecombilling.com.au/cortel-admin/security/showLoginAdmin.do")) {
			Log.info(
					"The new page is not logged in, so URL is directed to URL: https://cindy.telecombilling.com.au/cortel-admin/security/showLoginAdmin.do");
		} else {
			seleniumHelper.setError(driver, "DontSeeNewWindows",
					"Cannot open a new windows after double-clicking on the service number row");

		}
	}

	@Then("^I should NOT see the revert icon for Rate Plan without changes and lived in cindy$")
	public void dontSeeRevertIconNoChangePlan() throws Throwable {
		int totalPlan = ratePlanPage.getTotaRatePlans();
		String des = "";
		for (int i = 0; i < totalPlan; i++) {
			des = ratePlanPage.getSummaryInfo("Description", i);
			if (!ratePlanPage.isPending(des) && ratePlanPage.getNumberRatePlanIDWithDes(des) > 0) {
				if (!ratePlanPage.visibleRevertIconWithDes(des)) {
					Log.info("I should NOT see the revert icon for Rate Plan with Description " + des);
				} else
					seleniumHelper.setError(driver, "StillSeeRevertIcon",
							"I still see the revert icon for Rate Plan with Description " + des);
			}
		}

	}

	@Then("^I should NOT see the revert icon for Rate Plan with Description \"([^\"]*)\"$")
	public void dontSeeRevertIconWithDes(String des) throws Throwable {
		if (ignoreCase) {
			if (!ratePlanPage.visibleRevertIconWithDes(des)) {
				Log.info("I should NOT see the revert icon for Rate Plan with Description " + des);
			} else
				Log.warn("I still see the revert icon for Rate Plan with Description " + des);
		}
		ignoreCase = false;

	}

	@Then("^I should see the revert icon for Rate Plan with Description \"([^\"]*)\"$")
	public void visibleRevertIconWithDes(String des) throws Throwable {
		if (ratePlanPage.visibleRevertIconWithDes(des)) {
			Log.info("I should see the revert icon for Rate Plan with Description " + des);
		} else
			Log.warn("Don't see the revert icon for Rate Plan with Description " + des);

	}

	@Then("^I should see the revert icon of Rate Plan Id \"([^\"]*)\"$")
	public void visibleRevertIconOfRPId(int ratePlanID) throws Throwable {
		if (ratePlanPage.seeRevertIconOfRPID(ratePlanID)) {
			Log.info("I should see the revert icon for Rate Plan Id: " + ratePlanID);
		} else
			Log.warn("Don't see the revert icon for Rate Plan Id: " + ratePlanID);

	}

	@And("^I click on revert icon for Rate Plan with Description \"([^\"]*)\"$")
	public void clickRevertIconWithDes(String des) throws Throwable {
		ratePlanPage.clickRevertIconWithDes(des);
		Log.info("Revert icon of rate plan with description: " + des + " is clicked");
	}

	@And("^I click on revert icon for Rate Plan Id \"([^\"]*)\"$")
	public void clickRevertIcon(int ratePlanID) throws Throwable {
		try {
			ratePlanPage.clickRevertIconOfRPID(ratePlanID);
			Log.info("I clicked on revert icon for Rate Plan Id: " + ratePlanID);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeRevertIcon",
					"Don't see the revert icon for the rate plan id: " + ratePlanID);
		}
	}

	// compare old version and new version of rate plan
	@Then("I got all of curent values for fields of Rate Plan with Description \"([^\"]*)\"$")
	public void getOldRatePlanWithDes(String des) throws Throwable {
		oldSummary = ratePlanPage.getCurrentSummaryRPWithDes(des);
		oldCallrate = ratePlanPage.getCurrentCallRateRPWithAPI(des);
		System.out.println("oldSummary: " + oldSummary);
		System.out.println("oldCallrate: " + oldCallrate);
	}

	@Then("I got all of curent values for fields of Rate Plan Id \"([^\"]*)\"$")
	public void getOldRatePlanRPID(int ratePlanID) throws Throwable {
		oldSummary = ratePlanPage.getCurrentSummaryRPID(ratePlanID);
		oldCallrate = ratePlanPage.getCallRateOfRPID(ratePlanID);
		System.out.println("oldSummary: " + oldSummary);
		System.out.println("oldCallrate: " + oldCallrate);
	}

	@Then("I got all of values after changing Rate Plan with Description \"([^\"]*)\"$")
	public void getNewVersionRatePlanWithDes(String des) throws Throwable {
		newSummary = ratePlanPage.getCurrentSummaryRPWithDes(des);
		newCallrate = ratePlanPage.getCurrentCallRateRPWithAPI(des);
	}

	@Then("I got all of values after changing Rate Plan Id \"([^\"]*)\"$")
	public void getNewVersionRatePlanId(int ratePlanID) throws Throwable {
		newSummary = ratePlanPage.getCurrentSummaryRPID(ratePlanID);
		newCallrate = ratePlanPage.getCallRateOfRPID(ratePlanID);
	}

	@Then("^All changes of Rate Plan with Description \"([^\"]*)\" are highlighted$")
	public void yesHighlightedValuesWithDes(String des) throws Throwable {
		// higlighted for summary info
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		for (String item : summaryInfo) {
			if (ratePlanPage.yesHighlightedSummaryWithDes(item, des)
					&& ratePlanPage.yesViewHistorySummaryWithDes(item, des)) {
				Log.info(item + " is highlighted");
			}
		}
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		for (int i = 0; i < totalRowCallRate; i++) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateWithDes(item, des, i)
						&& ratePlanPage.yesViewHistoryCallRateWithDes(item, des, i)) {
					Log.info(item + " is highlighted");
				}
			}
		}

	}

	@Then("^All changes of Rate Plan Id \"([^\"]*)\" are highlighted$")
	public void yesHighlightedValues(int ratePlanId) throws Throwable {
		// higlighted for summary info
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		for (String item : summaryInfo) {
			if (ratePlanPage.yesHighlightedSummaryOfRPID(item, ratePlanId)
					&& ratePlanPage.yesViewHistorySummaryOfRPID(item, ratePlanId)) {
				Log.info(item + " is highlighted");
			}
		}
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		for (int i = 0; i < totalRowCallRate; i++) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateOfRPID(item, ratePlanId, i)
						&& ratePlanPage.yesViewHistoryCallRateOfRPID(item, ratePlanId, i)) {
					Log.info(item + " is highlighted");
				}
			}
		}

	}

	@Then("^The old values of Rate Plan with Description \"([^\"]*)\" should be displayed$")
	public void compareHighlightedValuesWithDes(String des) throws Throwable {
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		int totalSummary = summaryInfo.length;
		for (int i = 0; i < totalSummary; i++) {
			String summaryValue = ratePlanPage.getDataHistorySummaryWithDes(summaryInfo[i], des);
			if (ratePlanPage.yesHighlightedSummaryWithDes(summaryInfo[i], des)
					&& ratePlanPage.yesViewHistorySummaryWithDes(summaryInfo[i], des)) {
				if (summaryValue.equalsIgnoreCase(oldSummary.get(i))) {
					Log.info("The highlighted value of column " + summaryInfo[i] + " should be " + summaryValue);
				} else
					seleniumHelper.setError(driver, "InvalidOldSummary" + summaryInfo[i],
							"The highlighted value on webiste >> " + summaryValue + " is not correct, it should be "
									+ oldSummary.get(i));
			}
		}
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		int totalFieldsCallRate = callRateInfo.length;
		for (int i = 0; i < totalRowCallRate; i++) {
			for (int j = 0; j < totalFieldsCallRate; j++) {
				String callRateValue = ratePlanPage.getDataHistoryCallRateWithDes(callRateInfo[j], des, i);
				if (ratePlanPage.yesHighlightedCallRateWithDes(callRateInfo[j], des, i)
						&& ratePlanPage.yesViewHistoryCallRateWithDes(callRateInfo[j], des, i)) {
					if (callRateValue.equalsIgnoreCase(oldCallrate.get(i).get(j))) {
						Log.info("The highlighted value of column " + callRateInfo[j] + " should be " + callRateValue);
					} else
						seleniumHelper.setError(driver, "InvalidOldSummary" + callRateInfo[j],
								"The highlighted value on webiste >> " + callRateValue
										+ " is not correct, it should be " + oldCallrate.get(i).get(j));
				}
			}
		}

	}

	@Then("^The old Summary Info of Rate Plan Id \"([^\"]*)\" should be displayed$")
	public void compareHighlightedValuesSummaryInfoOfRPID(int ratePlanId) throws Throwable {
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		int totalSummary = summaryInfo.length;
		for (int i = 0; i < totalSummary; i++) {
			String summaryValue = ratePlanPage.getDataHistorySummary(summaryInfo[i], ratePlanId);
			if (ratePlanPage.yesHighlightedSummaryOfRPID(summaryInfo[i], ratePlanId)
					&& ratePlanPage.yesViewHistorySummaryOfRPID(summaryInfo[i], ratePlanId)) {
				if (summaryValue.equalsIgnoreCase(oldSummary.get(i))) {
					Log.info("The highlighted value of column " + summaryInfo[i] + " should be " + summaryValue);
				} else
					seleniumHelper.setError(driver, "InvalidOldSummary" + summaryInfo[i],
							"The highlighted value on webiste >> " + summaryValue + " is not correct, it should be "
									+ oldSummary.get(i));
			}
		}

	}

	@Then("^The old Call Rate Info of Rate Plan Id \"([^\"]*)\" should be displayed$")
	public void compareHighlightedValuesCallRateOfRPID(int ratePlanId) throws Throwable {
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		int totalFieldsCallRate = callRateInfo.length;
		for (int i = 0; i < totalRowCallRate; i++) {
			for (int j = 0; j < totalFieldsCallRate; j++) {
				String callRateValue = ratePlanPage.getDataHistoryCallRate(callRateInfo[j], ratePlanId, i);
				if (ratePlanPage.yesHighlightedCallRateOfRPID(callRateInfo[j], ratePlanId, i)
						&& ratePlanPage.yesViewHistoryCallRateOfRPID(callRateInfo[j], ratePlanId, i)) {
					if (callRateValue.equalsIgnoreCase(oldCallrate.get(i).get(j)))
						Log.info("The highlighted value of column " + callRateInfo[j] + " should be " + callRateValue);
					else
						seleniumHelper.setError(driver, "InvalidOldSummary" + callRateInfo[j],
								"The highlighted value on webiste >> " + callRateValue
										+ " is not correct, it should be " + oldCallrate.get(i).get(j));

				}
			}
		}
	}

	@Then("^All changes of Rate Plan with Description \"([^\"]*)\" are NOT highlighted$")
	public void noHighlightedValuesWithDes(String des) throws Throwable {
		// higlighted for summary info
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		for (String item : summaryInfo) {
			if (ratePlanPage.yesHighlightedSummaryWithDes(item, des)
					&& ratePlanPage.yesViewHistorySummaryWithDes(item, des)) {
				seleniumHelper.setError(driver, "SeeHighlighted" + item, item + " is highlighted");
			}
		}
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		for (int i = 0; i < totalRowCallRate; i++) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateWithDes(item, des, i)
						&& ratePlanPage.yesViewHistoryCallRateWithDes(item, des, i)) {
					seleniumHelper.setError(driver, "SeeHighlighted" + item, item + " is highlightedwith value "
							+ ratePlanPage.getDataHistoryCallRateWithDes(item, des, i));
				}
			}
		}

	}

	@Then("^All changes of Rate Plan Id \"([^\"]*)\" are NOT highlighted$")
	public void noHighlightedValuesWithDes(int ratePlanId) throws Throwable {
		// higlighted for summary info
		String[] summaryInfo = { "Description", "Comments", "Rate Plan Type", "Submitted By", "Submitted Date",
				"IDD reference" };
		for (String item : summaryInfo) {
			if (ratePlanPage.yesHighlightedSummaryOfRPID(item, ratePlanId)
					&& ratePlanPage.yesViewHistorySummaryOfRPID(item, ratePlanId)) {
				seleniumHelper.setError(driver, "SeeHighlighted" + item, item + " is highlighted");
			}
		}
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalRowCallRate = oldCallrate.size();
		for (int i = 0; i < totalRowCallRate; i++) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateOfRPID(item, ratePlanId, i)
						&& ratePlanPage.yesViewHistoryCallRateOfRPID(item, ratePlanId, i)) {
					seleniumHelper.setError(driver, "SeeHighlighted" + item, item + " is highlighted with value "
							+ ratePlanPage.getDataHistoryCallRate(item, ratePlanId, i));
				}
			}
		}

	}

	@And("^I click on Add row button$")
	public void clickAddNewCallRate() throws Throwable {
		ratePlanPage.clickAddRowBtn();
		Log.info("Add row button is clicked");
	}

	@Then("^The new call rates of Rate Plan with Description \"([^\"]*)\" are highlighted$")
	public void yesHighlightedNewCallRateWithDes(String des) throws Throwable {
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalOldRowCallRate = oldCallrate.size();
		int totalCurrentRowCallRate = newCallrate.size();
		for (int i = totalCurrentRowCallRate - 1; i > totalOldRowCallRate - 1; i--) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateWithDes(item, des, i)
						&& ratePlanPage.yesViewHistoryCallRateWithDes(item, des, i)) {
					Log.info(item + " is highlighted");
				}
			}
		}

	}

	@Then("^The new call rates of Rate Plan Id \"([^\"]*)\" are highlighted$")
	public void yesHighlightedNewCallRate(int ratePlanId) throws Throwable {
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalOldRowCallRate = oldCallrate.size();
		int totalCurrentRowCallRate = newCallrate.size();
		for (int i = totalCurrentRowCallRate - 1; i > totalOldRowCallRate - 1; i--) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateOfRPID(item, ratePlanId, i)
						&& ratePlanPage.yesViewHistoryCallRateOfRPID(item, ratePlanId, i)) {
					Log.info(item + " is highlighted");
				}
			}
		}

	}

	@Then("^The value of new call rates of Rate Plan with Description \"([^\"]*)\" should be \"([^\"]*)\"$")
	public void compareNewCallRateWithDes(String des, String value) throws Throwable {
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalOldRowCallRate = oldCallrate.size();
		int totalCurrentRowCallRate = newCallrate.size();
		for (int i = totalCurrentRowCallRate - 1; i > totalOldRowCallRate - 1; i--) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateWithDes(item, des, i)
						&& ratePlanPage.yesViewHistoryCallRateWithDes(item, des, i)) {
					if (ratePlanPage.getDataHistoryCallRateWithDes(item, des, i).equalsIgnoreCase(value)) {
						Log.info("The value of new call rate" + item + " is highlighted with value "
								+ ratePlanPage.getDataHistoryCallRateWithDes(item, des, i));
					}
				}
			}
		}

	}

	@Then("^The value of new call rates of Rate Plan Id \"([^\"]*)\" should be \"([^\"]*)\"$")
	public void compareNewCallRate(int ratePlanId, String value) throws Throwable {
		String[] callRateInfo = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
				"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
				"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
				"Floating Credit Included" };
		int totalOldRowCallRate = oldCallrate.size();
		int totalCurrentRowCallRate = newCallrate.size();
		for (int i = totalCurrentRowCallRate - 1; i > totalOldRowCallRate - 1; i--) {
			for (String item : callRateInfo) {
				if (ratePlanPage.yesHighlightedCallRateOfRPID(item, ratePlanId, i)
						&& ratePlanPage.yesViewHistoryCallRateOfRPID(item, ratePlanId, i)) {
					if (ratePlanPage.getDataHistoryCallRate(item, ratePlanId, i).equalsIgnoreCase(value)) {
						Log.info("The value of new call rate" + item + " is highlighted with value "
								+ ratePlanPage.getDataHistoryCallRate(item, ratePlanId, i));
					}
				}
			}
		}

	}

	@Then("^I should see the number in Rate Plan ID for Rate Plan with Description \"([^\"]*)\"$")
	public void seeRatePlanIDWithDes(String des) throws Throwable {
		if (ratePlanPage.visibleRatePlanNumberWithDes(des)) {
			Log.info("I should see the number in Rate Plan ID for Rate Plan with Description " + des);
		} else
			Log.info("Don't see the number in Rate Plan ID for Rate Plan with Description " + des);

	}

	@Then("^I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description \"([^\"]*)\"$")
	public void dontSeePENDINGWithDes(String des) throws Throwable {
		if (!ratePlanPage.isPending(des)) {
			Log.info("I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description " + des);
		} else {
			ignoreCase = true;
			Log.info("Seemly, some one changed the Rate Plan with Description " + des + " ,so please skip this case.");
		}

	}

	@Then("^I should see the PENDING in Rate Plan ID for Rate Plan with Description \"([^\"]*)\"$")
	public void seePENDINGWithDes(String des) throws Throwable {
		if (ratePlanPage.isPending(des)) {
			Log.info("I should see the PENDING in Rate Plan ID for Rate Plan with Description " + des);
		} else {
			seleniumHelper.setError(driver, "DontSeePendingStatus",
					"Don't see the PENDING in Rate Plan ID for Rate Plan with Description " + des);
		}

	}

	@Then("^I should see the New value in Rate Plan ID for Rate Plan with Description \"([^\"]*)\"$")
	public void seeNewInRatePlanIDWithDes(String des) throws Throwable {
		if (!ratePlanPage.visibleNewInRatePlanWithDes(des)) {
			Log.info("I should see the New value in Rate Plan ID for Rate Plan with Description " + des);
		} else
			Log.info("Don't see the New value in Rate Plan ID for Rate Plan with Description " + des);

	}

	@And("^I create new rate plan with description \"([^\"]*)\" for bureau \"([^\"]*)\"$")
	public void createSaveRatePlan(String description, String bureau) throws Throwable {
		selectSpecificBureau(bureau);
		clickCreateRatePlan();
		enterDesInCreateDialog(description);
		clickCreateInCreateDialog();
		enterTxtBoxOfSummaryInfo(description, "Comments");
		clickSaveChangeOfRatePlan();
	}

	@And("^I create new rate plan with description \"([^\"]*)\"$")
	public void createSaveRatePlan(String description) throws Throwable {
		clickCreateRatePlan();
		enterDesInCreateDialog(description);
		clickCreateInCreateDialog();
		enterTxtBoxOfSummaryInfo(description, "Comments");
		clickSaveChangeOfRatePlan();
	}

	@And("^I double click on Rate Plan with Description \"([^\"]*)\"$")
	public void doubleClickRatePlan(String des) throws Throwable {
		try {
			ratePlanPage.doubleClickViewRatePlan(des);
			Log.info("I double click on Rate Plan with Description: " + des);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@And("^I double click on Rate Plan Id \"([^\"]*)\"$")
	public void doubleClickRatePlanID(int ratePlanID) throws Throwable {
		try {
			ratePlanPage.doubleClickRatePlanID(ratePlanID);
			Log.info("I double click on Rate Plan Id: " + ratePlanID);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@And("^Connect to Rate Plan table in Mongo database$")
	public void connectRatePlanTableInMongo() throws Throwable {
		coll = Helper.connectRatePlanTableInMongoDB();
		Log.info("Connected to Rate Plan table in Mongo database");
	}

	@And("^Under Mongo Database, I delete Rate Plan with Description \"([^\"]*)\"$")
	public void deleteRatePlanwithMongo(String des) throws Throwable {
		Helper.deleteDocumentMongoDB(coll, des);
		Log.info("Under Mongo Database, I deleted Rate Plan with Description: " + des);
	}

	@And("^Under Mongo Database, I delete Rate Plan Id \"([^\"]*)\"$")
	public void deleteRatePlanwithMongo(int ratePlanId) throws Throwable {
		String des = ratePlanPage.getSummaryInfoViewModeOfRPID("Description", ratePlanId);
		System.out.println(des);
		Helper.deleteDocumentMongoDB(coll, des);
		Log.info("Under Mongo Database, I deleted Rate Plan Id: " + ratePlanId);
		seleniumHelper.f5KeyBoard(driver);
	}

	@Then("^The summary info of Rate Plan with Description \"([^\"]*)\" is correct$")
	public void seeSavedSummaryInfo(String des) throws Throwable {
		try {
			String subBy = ratePlanPage.getSummaryInfoWithDes("Submitted By", des);
			String subDate = ratePlanPage.getSummaryInfoWithDes("Submitted Date", des);
			String[] summaryField = { "Comments", "Rate Plan Type", "IDD reference" };
			String[] currentSummary = { ratePlanPage.getSummaryInfoWithDes(summaryField[0], des),
					ratePlanPage.getSummaryInfoWithDes(summaryField[1], des),
					ratePlanPage.getSummaryInfoWithDes(summaryField[2], des) };
			int totalSavedSummary = savedSummary.size();
			for (int i = 0; i < totalSavedSummary; i++) {
				if (savedSummary.get(i).equalsIgnoreCase(currentSummary[i])) {
					Log.info("The value of " + summaryField[i] + " is " + currentSummary[i]);
				} else {
					seleniumHelper.setError(driver, "Wrong" + summaryField[i] + summaryField[i],
							"The " + summaryField[i] + " with the value '" + currentSummary[i]
									+ "' is not correct, it should be " + savedSummary.get(i));
				}
			}
			if (LoginSteps.globalUserName.equalsIgnoreCase(subBy)) {
				Log.info("Submitted By is " + subBy);
				if (subDate.equalsIgnoreCase(Helper.getCurrentDate())) {
					Log.info("Submitted Date is " + subDate);
					Log.info("The summary info of rate plan with '" + des + "' is correct");
				} else {
					seleniumHelper.setError(driver, "WrongSubDate" + subDate, "The Submitted Date '" + subDate
							+ "' is not correct, it should be " + Helper.getCurrentDate());
				}
			} else {
				seleniumHelper.setError(driver, "WrongSubBy" + subBy,
						"The Submitted By '" + subBy + "' is not correct, it should be " + LoginSteps.globalUserName);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Then("^The call rate info of Rate Plan with Description \"([^\"]*)\" is correct$")
	public void seeSavedCallRateInfo(String des) throws Throwable {
		try {
			Thread.sleep(2000);
			List<List<String>> currentCallRate = ratePlanPage.getCurrentCallRateRPWithNoAPI(des);
			int totalCallRate = ratePlanPage.getTotalRowCallRateWithDes(des);
			int totalColumn = 12;
			String[] columns = { "Call Rate Category", "Initial Duration (sec)", "Rate per Cost", "Rate Fixed",
					"Rate per KB", "Rate per Second", "Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration",
					"Capped Max Rate For Fixed Duration", "Capped Rate (after Duration) per Second",
					"Floating Credit Included" };
			Log.info("We got " + totalCallRate + " rows in call rate table");
			for (int i = 0; i < totalCallRate; i++) {
				for (int j = 0; j < totalColumn; j++) {
					String currentValue = currentCallRate.get(i).get(j);
					String savedValue = savedCallrate.get(i).get(j);
					String columnName = columns[j];
					if (currentValue.equalsIgnoreCase(savedValue)) {
						Log.info("Row [" + (i + 1) + "]: '" + columnName + "' has the value '" + savedValue + "'");
					} else {
						seleniumHelper.setError(driver, "DontSeeValue" + savedValue, "Row [" + (i + 1)
								+ "]: The value '" + currentValue + "' of " + columnName + " is not correct");
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@And("^I create rate plan for all bureaus$")
	public void createRPAllBureaus() throws Throwable {
		ratePlanPage.createRatePlanForAllBureaus();
	}

}