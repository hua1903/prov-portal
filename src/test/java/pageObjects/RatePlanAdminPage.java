package pageObjects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import dataProvider.RatePlanDataReader;
import managers.FileReaderManager;
import selenium.seleniumHelper;
import utilities.CreateJsonFile;
import utilities.Helper;
import utilities.Log;

public class RatePlanAdminPage {
	public WebDriver driver;
	public File jsonfile;
	public RatePlanDataReader ratePlanDataReader;

	public RatePlanAdminPage(WebDriver driver) throws IOException {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		ratePlanDataReader = FileReaderManager.getInstance().getRatePlanDataReader();
	}

	@FindBy(how = How.XPATH, using = "//label[text()='Rate Plan changes Pending:']")
	private WebElement titleRatePlanAdmin;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'list--button-history')]")
	private WebElement btnHistory;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'list--button-history')]/span[text()='Show History Records']")
	private WebElement btnShowHistory;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'list--button-history')]/span[text()='Hide history records']")
	private WebElement btnHideHistory;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//button[contains(@class,'button-save undefined')]")
	private List<WebElement> btnAccept;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Do you want to accept this rate plan? (This action cannot be undone)')]")
	private WebElement msgConfirmAccept;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//button[contains(@class,'button-edit undefined')]")
	private List<WebElement> btnEdit;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//button[contains(@class,'button-delete undefined')]")
	private List<WebElement> btnReject;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Do you want to reject this rate plan? (This action cannot be undone)')]")
	private WebElement msgConfirmReject;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span/p[@title]")
	private List<WebElement> statusPlan;

	// buttons of rate plans
	public List<WebElement> getElementAcceptBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-save undefined')]";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementEditRatePlanAdminBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-edit')]";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementRejectBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-delete undefined')]";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementListAcceptedWithDes(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/preceding-sibling::span/p[@title='ACCEPTED']";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementListRejectedWithDes(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/preceding-sibling::span/p[@title='REJECTED']";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementListPendingWithDes(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/preceding-sibling::span/p[@title='PENDING']";
		seleniumHelper.waitLocated(driver, 2, xpath);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getListEleOfStatus(String status) {
		return driver.findElements(
				By.xpath("//li[contains(@class,'is-visible')]//span[@class='single-rate-plan__summary__id']/p[@title='"
						+ status + "']"));
	}

	public List<WebElement> getListEleOfDesWithStatus(String status) {
		return driver.findElements(
				By.xpath("//li[contains(@class,'is-visible')]//span[@class='single-rate-plan__summary__id']/p[@title='"
						+ status + "']/../following-sibling::div[contains(@class,'rate-plan__summary__description')]"));
	}

	public boolean seeTitleRatePlanAdmin() {
		try {
			seleniumHelper.waitElementVisible(driver, 30, "//label[text()='Rate Plan changes Pending:']");
			return titleRatePlanAdmin.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeShowHistoryBtn() {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnShowHistory);
			return btnShowHistory.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickShowHistoryRecords() throws InterruptedException, IOException {
		try {
			Thread.sleep(1000);
			seleniumHelper.waitClickAble(driver, 60, btnShowHistory);
			btnShowHistory.click();
			seleniumHelper.waitClickAble(driver, 60, btnHideHistory);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeShowHistoryRecords",
					"The website element of Show History Records is changed, please check your code again.");
		}
	}

	public boolean seeHideHistoryBtn() {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnHideHistory);
			return btnHideHistory.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickHideHistoryRecords() throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnHideHistory);
			btnHideHistory.click();
			seleniumHelper.waitClickAble(driver, 60, btnShowHistory);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeHideHistoryRecords",
					"The website element of Hide History Records is changed, please check your code again.");
		}
	}

	public boolean seeBtnAccept(int i) {
		try {
			return btnAccept.get(i).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enableBtnAccept(int i) {
		try {
			seleniumHelper.waitLocated(driver, 2, "//button[contains(@class,'button-save undefined')]");
			return btnAccept.get(i).isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeBtnEditAdmin(int i) {
		try {
			return btnEdit.get(i).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enableBtnEditAdmin(int i) {
		try {
			seleniumHelper.waitLocated(driver, 2,
					"//li[contains(@class,'is-visible')]//button[contains(@class,'button-edit undefined')]");
			return btnEdit.get(i).isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeBtnReject(int i) {
		try {
			return btnReject.get(i).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enableBtnReject(int i) {
		try {
			seleniumHelper.waitLocated(driver, 2, "//button[contains(@class,'button-delete undefined')]");
			return btnReject.get(i).isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enable3ActionBtnsAdmin(int i) {
		try {
			return btnReject.get(i).isEnabled() && btnEdit.get(i).isEnabled() && btnAccept.get(i).isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public int getTotalRatePlanAdmin() {
		return btnAccept.size();
	}

	public int getTotalStatus() {
		return statusPlan.size();
	}

	public int getTotalStatus(String status) {
		return getListEleOfStatus(status).size();
	}

	public String getStatusPlan(int i) {
		try {
			return statusPlan.get(i).getText();
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean visibleAccepted(int i) {
		try {
			return statusPlan.get(i).getText().equalsIgnoreCase("Accepted");
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleRejected(int i) {
		try {
			return statusPlan.get(i).getText().equalsIgnoreCase("Rejected");
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visiblePending(int i) {
		try {
			return statusPlan.get(i).getText().equalsIgnoreCase("Pending");
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleDuplicatedDes(List<String> duplicatedDes, String des) {
		boolean visibleDupDes = false;
		for (String item : duplicatedDes) {
			if (des.equalsIgnoreCase(item)) {
				visibleDupDes = true;
			}
		}
		return visibleDupDes;
	}

	public boolean visibleAcceptedWithDes(String des) {
		try {
			return !getElementListAcceptedWithDes(des).isEmpty();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleRejectedWithDes(String des) {
		try {
			return !getElementListRejectedWithDes(des).isEmpty();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visiblePendingWithDes(String des) {
		try {
			return !getElementListPendingWithDes(des).isEmpty();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickAcceptBtnWithDes(String description) throws InterruptedException, IOException {
		try {
			getElementAcceptBtn(description).get(0).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeAcceptBtn",
					"Under Rate Plan Admin page, Don't see Accept button of rate plan with description: "
							+ description);
		}
	}

	public void clickEditRatePlanAdminBtnWithDes(String description) throws InterruptedException, IOException {
		try {
			getElementEditRatePlanAdminBtn(description).get(0).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeEditRatePlanAdminBtn",
					"Under Rate Plan Admin page, Don't see Edit button of rate plan with description: " + description);
		}
	}

	public void clickRejectBtnWithDes(String description) throws InterruptedException, IOException {
		try {
			getElementRejectBtn(description).get(0).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeRejectBtn",
					"Under Rate Plan Admin page, Don't see Reject button of rate plan with description: "
							+ description);
		}
	}

	public boolean visibleMsgConfirmReject() {
		try {
			seleniumHelper.waitElementVisible(driver, 60,
					"//*[contains(text(),'Do you want to reject this rate plan? (This action cannot be undone)')]");
			return msgConfirmReject.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleMsgConfirmAccept() {
		try {
			seleniumHelper.waitElementVisible(driver, 60,
					"//*[contains(text(),'Do you want to accept this rate plan? (This action cannot be undone)')]");
			return msgConfirmAccept.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean dontSeeSummaryInfo(String field, String value, String description, String valueWeb)
			throws InterruptedException, IOException {
		try {
			boolean status = false;
			if (field.equalsIgnoreCase("Comments")) {
				if (!value.equalsIgnoreCase(valueWeb)) {
					status = true;
				} else {
					status = false;
				}
			}
			return status;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeNewSummaryInfo(String value, String description, String valueWeb)
			throws InterruptedException, IOException {
		try {
			boolean status = false;
			if (value.equalsIgnoreCase(valueWeb)) {
				status = true;
			} else {
				status = false;
			}
			return status;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeOldSummaryInfo(String oldSummary, String valueSummaryWeb) {
		try {
			boolean status = false;
			if (oldSummary.equalsIgnoreCase(valueSummaryWeb)) {
				status = true;
			} else {
				status = false;
			}
			return status;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeRatePlanWithStatus(String status) {
		return getTotalStatus(status) > 0;
	}

	public int getTotalPlanHistoryWithDes(String des) {
		return getElementAcceptBtn(des).size();
	}

	public void createJsonRatePlanAdminList() throws Exception {
		CreateJsonFile.createJsonFileRatePlanAdminList();
	}

	public String getFieldJsonCallRateAdmin(String callRate) {
		if (callRate.equalsIgnoreCase(ratePlanDataReader.getCallRateCat())) {
			return "callRateCategory.description";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getInitialDuration())) {
			return "initialDurationSec";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRatePerCost())) {
			return "ratePerCost";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRateFixed())) {
			return "rateFixed";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRatePerKB())) {
			return "ratePerKB";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRatePerSecond())) {
			return "ratePerSecond";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRatePer30Sec())) {
			return "rate30Sec";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getRatePer60Sec())) {
			return "rate60Sec";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getCapFixed())) {
			return "cappedTimeSec";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getCapMax())) {
			return "cappedRateInitial";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getCapRate())) {
			return "cappedRateAfterTime";
		} else if (callRate.equalsIgnoreCase(ratePlanDataReader.getFloatCredit())) {
			return "floatingCredit";
		} else {
			return "";
		}
	}

	public int getTotalDesWithStatus(String status) {
		return getListEleOfDesWithStatus(status).size();
	}

	public String getDesWithStatusAtPosition(String status, int i) {
		return getListEleOfDesWithStatus(status).get(i).getText();
	}

	public List<String> getListDesWithStatus(String status) {
		int total = getTotalDesWithStatus(status);
		List<String> desList = new ArrayList<String>();
		String des = getDesWithStatusAtPosition(status, 0);
		desList.add(des);
		for (int i = 1; i < total; i++) {
			des = getDesWithStatusAtPosition(status, i);
			desList.add(des);
			for (String item : desList) {
				if (!item.equalsIgnoreCase(des)) {
					desList.add(des);
				}
			}
		}
		return desList;
	}

	public boolean see3EnableActionBtnsAtFirstPlan(String status) {
		int firstRP = 0;
		String des = getDesWithStatusAtPosition(status, firstRP);
		Log.info("Checking the status of action buttons for rate plan with des: " + des);
		return getElementEditRatePlanAdminBtn(des).get(firstRP).isEnabled()
				&& getElementAcceptBtn(des).get(firstRP).isEnabled()
				&& getElementRejectBtn(des).get(firstRP).isEnabled();
	}

	public boolean see3EnableActionBtnsAtAllPlans(String status) {
		int total = getListDesWithStatus(status).size();
		int fstNotDuplicatedPlan = 0;
		boolean enable = false;
		for (int i = 0; i < total; i++) {
			String des = getDesWithStatusAtPosition(status, i);
			if (getElementEditRatePlanAdminBtn(des).get(fstNotDuplicatedPlan).isEnabled()
					&& getElementAcceptBtn(des).get(fstNotDuplicatedPlan).isEnabled()
					&& getElementRejectBtn(des).get(fstNotDuplicatedPlan).isEnabled()) {
				enable = true;
				Log.info("Action buttons are enable for the rate plan with des: " + des);
			}
		}
		return enable;
	}

}
