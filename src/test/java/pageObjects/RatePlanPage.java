package pageObjects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.cucumber.listener.Reporter;

import dataProvider.RatePlanDataReader;
import managers.FileReaderManager;
import selenium.seleniumHelper;
import stepDefinitions.LoginSteps;
import utilities.CreateJsonFile;
import utilities.Helper;
import utilities.Log;

public class RatePlanPage {
	private static final String DATA_HIGHLIGHT = "data-highlight";
	private static final String DATA_TOGGLE_HISTORY = "data-toggle-history";
	private static final String DATA_HISTORY = "data-history";
	public WebDriver driver;
	public File jsonfile;
	public RatePlanDataReader ratePlanReader;

	public RatePlanPage(WebDriver driver) throws IOException {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		ratePlanReader = FileReaderManager.getInstance().getRatePlanDataReader();
	}

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'select')]/select")
	private WebElement dropboxBureau;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'select')]/select/option")
	private List<WebElement> optionDBoxBureau;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]")
	private List<WebElement> labelRatePlanId;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span")
	private List<WebElement> valueRatePlanId;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span/p[contains(text(),'PENDING')]")
	private List<WebElement> valueStatusInRatePlanIdPending;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//span[contains(@class,'summary__id')][not(p)]/following-sibling::div[contains(@class,'summary__description')]")
	private List<WebElement> valueDesWithoutPending;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span/p[text()='New']")
	private List<WebElement> valueStatusNew;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span/p[text()='ACCEPTED']")
	private List<WebElement> valueStatusAccepted;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Id')]/following-sibling::span/p[text()='REJECTED']")
	private List<WebElement> valueStatusRejected;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Description')]")
	private List<WebElement> labelRatePlanDes;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Description')]/following-sibling::div[1]")
	private List<WebElement> valueRatePlanDes;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//div[contains(@class,'rate-plan__summary__ratePlanType')][not(text()='International Direct Dial')]/preceding-sibling::div[contains(@class,'rate-plan__summary__description')]")
	private List<WebElement> valueDesRPNoIDD;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//div[contains(@class,'rate-plan__summary__ratePlanType')][text()='International Direct Dial']/preceding-sibling::div[contains(@class,'rate-plan__summary__description')]")
	private List<WebElement> valueDesRPIDD;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Submitted By')]")
	private List<WebElement> labelRatePlanSubBy;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Submitted By')]/following-sibling::div[1]")
	private List<WebElement> valueRatePlanSubBy;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Type')]")
	private List<WebElement> labelRatePlanType;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Type')]/following-sibling::div[1]")
	private List<WebElement> valueRatePlanType;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'IDD reference')]")
	private List<WebElement> labelIDDReference;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'IDD reference')]/following-sibling::div[1]")
	private List<WebElement> valueIDDReference;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Comments')]")
	private List<WebElement> labelRatePlanComments;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Comments')]/following-sibling::div[1]")
	private List<WebElement> valueRatePlanComments;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Submitted Date')]")
	private List<WebElement> labelRatePlanSubDate;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Submitted Date')]/following-sibling::p[1]")
	private List<WebElement> valueRatePlanSubDate;

	@FindBy(how = How.XPATH, using = "//label[text()='Filter By:']/following-sibling::div//div/input")
	private WebElement txtFilter;

	@FindBy(how = How.XPATH, using = "//input[@type='radio'][@value='description']")
	private WebElement radioAll;

	@FindBy(how = How.XPATH, using = "//input[@type='radio'][@value='description']")
	private WebElement radioDesComments;

	@FindBy(how = How.XPATH, using = "//input[@type='radio'][@value='ratePlanId']")
	private WebElement radioRatePlanID;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//table/thead")
	private WebElement table;

	@FindBy(how = How.XPATH, using = "//label[contains(@class,'bureau-rate-plans__list--label')]")
	private WebElement labelRatePlanSelectedBureau;

	@FindBy(how = How.XPATH, using = "//button[@class='button-cmp button  bureau-rate-plans__list--button-create']")
	private WebElement btnCreateRatePlan;

	@FindBy(how = How.XPATH, using = "//form//select")
	private WebElement tagSelectBureauCreateOrCopyDialog;

	@FindBy(how = How.XPATH, using = "//form//input[@type='text']")
	private WebElement txtDesCreateOrCopyDialog;

	@FindBy(how = How.XPATH, using = "//form//input[@type='checkbox']")
	private WebElement cBoxIDDCreateOrCopyDialog;

	@FindBy(how = How.XPATH, using = "//form//button[contains(@class,'cancel')]")
	private WebElement btnCancelCreateOrCopyDialog;

	@FindBy(how = How.XPATH, using = "//form//button[contains(@class,'save')]")
	private WebElement btnCreateOrCopyOnDialog;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'header--x-button')]")
	private WebElement btnX;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-confirm')]")
	private WebElement btnConfirmPopup;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-close')]")
	private WebElement btnCloseConfirmPopup;

	// create at top
	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//div[contains(@class,'rate-plan__summary__description')]//div/textarea")
	private List<WebElement> valueRatePlanDesEdit;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//div[contains(@class,'rate-plan__summary__comment')]//div/textarea")
	private List<WebElement> valueRatePlanCommentsEdit;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'Rate Plan Type')]/following-sibling::div[1]//select")
	private WebElement selectRatePlanTypeEdit;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'is-visible')]//label[contains(text(),'IDD reference')]/following-sibling::div//select")
	private WebElement selectIDDReferenceEdit;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-save undefined')]")
	private WebElement btnSaveChangeOfRatePlan;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-cancel undefined')]")
	private WebElement btnCancelChangeOfRatePlan;

	@FindBy(how = How.XPATH, using = "//p[contains(text(),'Wholesale to Retail Calculator')]")
	private WebElement titleCalRatePerCost;

	@FindBy(how = How.XPATH, using = "//label[text()='Wholesale Amount (ex GST):']/following-sibling::div[1]//input")
	private WebElement txtWholesaleAmount;

	@FindBy(how = How.XPATH, using = "//label[text()='Retail Amount (inc GST):']/following-sibling::div[1]//input")
	private WebElement txtRetailAmount;

	@FindBy(how = How.XPATH, using = "//label[text()='Rate per Cost:']/following-sibling::div//input")
	private WebElement txtResultRatePerCost;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-save set')]")
	private WebElement btnSetOfCal;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'button-cancel cancel')]")
	private WebElement btnCloseOfCal;

	@FindBy(how = How.XPATH, using = "//p[contains(text(),'Data Calculator to KB')]")
	private WebElement titleCalRatePerKB;

	@FindBy(how = How.XPATH, using = "//label[text()='Data type:']/following-sibling::div//select")
	private WebElement tagSelectDataType;

	@FindBy(how = How.XPATH, using = "//label[text()='Amount:']/following-sibling::div[1]//input")
	private WebElement txtAmount;

	@FindBy(how = How.XPATH, using = "//label[text()='Result:']/following-sibling::div//input")
	private WebElement txtResultRatePerKB;

	// copy rate plan
	@FindBy(how = How.XPATH, using = "//p[text()='Copy Rate Plan Dialog']")
	private WebElement titleCopyDialog;

	@FindBy(how = How.XPATH, using = "//p[text()='Description existed']")
	private WebElement msgExistedDes;

	// delete
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Do you really want to delete this change?')]")
	private WebElement msgConfirmDelete;

	// service number list
	@FindBy(how = How.XPATH, using = "//p[contains(text(),'Service Numbers for Rate Plan No.')]")
	private WebElement titleSerNumPopup;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'download')]")
	private WebElement btnDownLoadSerNum;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'cancel')]")
	private WebElement btnClosePopup;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'add-row-btn')]")
	private WebElement btnAddrow;

	public WebElement getElementPendingStatus(String description) {
		String xpath = "//div[contains(text(),'" + description + "')]/preceding-sibling::span/p[@title='PENDING']";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleBtnExpand(String des) {
		String xpath = "//button[@title='Click here to expand this rate plan']/../..//div[contains(@class,'rate-plan__summary__description')][text()='"
				+ des + "']";
		return driver.findElement(By.xpath(xpath));
	}

	// buttons of rate plan
	public WebElement getElementEditRatePlanBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-edit')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementCopyRatePlanBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-copy')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementDeleteRatePlanBtn(String description) {
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-delete')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleActionBtnOfRPID(String btnName, int ratePlanID) {
		String className = "empty";
		switch (btnName.toLowerCase()) {
		case ("edit"):
			className = "button-edit";
			break;
		case ("copy"):
			className = "button-copy";
			break;
		case ("delete"):
			className = "button-delete";
			break;
		default:
			className = "empty";
		}
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanID
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'" + className
				+ "')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleActionBtnOfDes(String btnName, String des) {
		String className;
		switch (btnName) {
		case ("Edit"):
			className = "button-edit";
			break;
		case ("Copy"):
			className = "button-copy";
			break;
		case ("Delete"):
			className = "button-delete";
			break;
		default:
			className = "empty";
		}
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + des
				+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'" + className
				+ "')]";
		return driver.findElement(By.xpath(xpath));
	}

	// compare

	public WebElement getWebElementRevertIconWithDes(String description) {
		String xpath = "//label[contains(text(),'Description')]/following-sibling::div[text()='" + description
				+ "']/preceding-sibling::span/button[contains(@class,'button-history undefined')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleRevertIconOfRPID(int ratePlanID) {
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanID
				+ "']/button[contains(@class,'button-history undefined')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getWebElementRevertIconWithIndex(int indexStartWithOne) {
		String xpath = "//li[contains(@class,'is-visible')][" + indexStartWithOne
				+ "]//button[contains(@class,'button-history undefined')]";
		seleniumHelper.waitElementVisible(driver, 60, xpath);
		return driver.findElement(By.xpath(xpath));
	}

	// element of summary with description
	public WebElement getEleSummaryInfoViewMode(int ratePlanId, String field) {
		String className = "";
		switch (field.toLowerCase()) {
		case ("description"):
			className = "rate-plan__summary__description";
			break;
		case ("comments"):
			className = "rate-plan__summary__comment";
			break;
		case ("rate plan type"):
			className = "rate-plan__summary__ratePlanType";
			break;
		case ("submitted by"):
			className = "rate-plan__summary__modifiedBy";
			break;
		case ("submitted date"):
			className = "rate-plan__summary__modifiedDate";
			break;
		case ("idd reference"):
			className = "rate-plan__summary__iddRatePlanId";
			break;
		default:
			className = "";

		}
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanId
				+ "']/following-sibling::div[contains(@class,'" + className + "')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleRatePlanID(String description) {
		String xpath = "//label[contains(text(),'Description')]/following-sibling::div[text()='" + description
				+ "']/preceding-sibling::span";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleSummaryInfoViewMode(String des, String field) {
		String className = "";
		switch (field.toLowerCase()) {
		case ("comments"):
			className = "rate-plan__summary__comment";
			break;
		case ("rate plan type"):
			className = "rate-plan__summary__ratePlanType";
			break;
		case ("submitted by"):
			className = "rate-plan__summary__modifiedBy";
			break;
		case ("submitted date"):
			className = "rate-plan__summary__modifiedDate";
			break;
		case ("idd reference"):
			className = "rate-plan__summary__iddRatePlanId";
			break;
		default:
			className = "";
		}
		String xpath = "//div[contains(@class,'rate-plan__summary__description')][text()='" + des
				+ "']/../div[contains(@class,'" + className + "')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementDesWithDes(String description) {
		String xpath = "//div[contains(@class,'rate-plan__summary__description')][text()='" + description + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementDoubleClickWithDesView(String description) {
		String xpath = "//div[contains(@class,'rate-plan__summary__description')][text()='" + description
				+ "']/../label[contains(text(),'Rate Plan Id')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleDoubleClickWithRPID(int ratePlanID) {
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanID + "']/..";
		return driver.findElement(By.xpath(xpath));
	}

	// element of call rate with description
	public List<WebElement> getElementCallRateInfoWithDes(String description, String label)
			throws InterruptedException, IOException {
		int td = 1;
		switch (label.toLowerCase()) {
		case ("call rate category"):
			td = 2;
			break;
		case ("initial duration (sec)"):
			td = 3;
			break;
		case ("rate per cost"):
			td = 4;
			break;
		case ("rate fixed"):
			td = 5;
			break;
		case ("rate per kb"):
			td = 6;
			break;
		case ("rate per second"):
			td = 7;
			break;
		case ("rate per 30 sec"):
			td = 8;
			break;
		case ("rate per 60 sec"):
			td = 9;
			break;
		case ("capped fixed duration"):
			td = 10;
			break;
		case ("capped max rate for fixed duration"):
			td = 11;
			break;
		case ("capped rate (after duration) per second"):
			td = 12;
			break;
		case ("floating credit included"):
			td = 13;
			break;
		default:
			td = 0;
		}
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div//table/tbody/tr/td[" + td + "]/div";
		Thread.sleep(500);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getEleCallRateIDDWithDes(String description, String label)
			throws InterruptedException, IOException {
		int td = 1;
		switch (label.toLowerCase()) {
		case ("country"):
			td = 2;
			break;
		case ("call prefix"):
			td = 3;
			break;
		case ("initial duration (sec)"):
			td = 4;
			break;
		case ("rate per cost"):
			td = 5;
			break;
		case ("rate fixed"):
			td = 6;
			break;
		case ("rate per kb"):
			td = 7;
			break;
		case ("rate per second"):
			td = 8;
			break;
		case ("rate per 30 sec"):
			td = 9;
			break;
		case ("rate per 60 sec"):
			td = 10;
			break;
		case ("capped fixed duration"):
			td = 11;
			break;
		case ("capped max rate for fixed duration"):
			td = 12;
			break;
		case ("capped rate (after duration) per second"):
			td = 13;
			break;
		case ("floating credit included"):
			td = 14;
			break;
		default:
			td = 0;
		}
		String xpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div//table/tbody/tr/td[" + td + "]/div";
		Thread.sleep(500);
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElement> getElementCallRateOfRPID(int ratePlanID, String label)
			throws InterruptedException, IOException {
		int td = 1;
		switch (label.toLowerCase()) {
		case ("call rate category"):
			td = 2;
			break;
		case ("initial duration (sec)"):
			td = 3;
			break;
		case ("rate per cost"):
			td = 4;
			break;
		case ("rate fixed"):
			td = 5;
			break;
		case ("rate per kb"):
			td = 6;
			break;
		case ("rate per second"):
			td = 7;
			break;
		case ("rate per 30 sec"):
			td = 8;
			break;
		case ("rate per 60 sec"):
			td = 9;
			break;
		case ("capped fixed duration"):
			td = 10;
			break;
		case ("capped max rate for fixed duration"):
			td = 11;
			break;
		case ("capped rate (after duration) per second"):
			td = 12;
			break;
		case ("floating credit included"):
			td = 13;
			break;
		default:
			td = 0;
		}
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanID
				+ "']/../following-sibling::div//table/tbody/tr/td[" + td + "]/div";
		Thread.sleep(500);
		return driver.findElements(By.xpath(xpath));
	}

	public WebElement getEleHeaderSerNumTable(String header) {
		String xpath = "//tr/th/span[text()='" + header + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getEleHeaderCallRate(String header) {
		String xpath = "//tr/th/span[text()='" + header + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public List<WebElement> getWebEleListValueSerNum(String header) throws InterruptedException {
		int td = 1;
		switch (header.toLowerCase()) {
		case ("cidn"):
			td = 2;
			break;
		case ("salutation"):
			td = 3;
			break;
		case ("first name"):
			td = 4;
			break;
		case ("last name"):
			td = 5;
			break;
		case ("address"):
			td = 6;
			break;
		case ("contact number"):
			td = 7;
			break;
		case ("email"):
			td = 8;
			break;
		case ("service number"):
			td = 9;
			break;
		default:
			td = 0;
		}
		String xpath = "//div[contains(@class,'service-numbers')]//tr/td[" + td + "]";
		Thread.sleep(500);
		return driver.findElements(By.xpath(xpath));

	}

	public WebElement getEleCIDNInSerNumTable(int cidn) {
		return driver
				.findElement(By.xpath("//div[contains(@class,'service-numbers')]//tr/td[2][text()='" + cidn + "']"));
	}

	public WebElement getElementBureauAdmin(String description) {
		String xpath = "//label[contains(text(),'Description')]/following-sibling::div[text()='" + description
				+ "']/following-sibling::p[contains(@class,'bureau')]";
		seleniumHelper.waitLocated(driver, 10, xpath);
		return driver.findElement(By.xpath(xpath));
	}

	public int getTotalBureauList() {
		return optionDBoxBureau.size();
	}

	public String getBureauValue(int i) {
		return optionDBoxBureau.get(i).getText();
	}

	public void clickExpandBtn(String des) {
		getEleBtnExpand(des).click();
		getEleBtnExpand(des).click();
	}

	public void clickBureauDBox() throws InterruptedException, IOException {
		try {
			dropboxBureau.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeSelectBureauDBox",
					"The element of Select Bureau dropdown is changed");
		}
	}

	public boolean visibleFilterTxt() {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnCreateRatePlan);
			return txtFilter.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void enterFilterTxt(String value) throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 60, txtFilter);
			seleniumHelper.sendKey(txtFilter, value);
			txtFilter.sendKeys(Keys.ENTER);
			Thread.sleep(500);
			Log.info("The textbox filter is entered with the value " + value);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeFilterTxt", "The web element of the textbox filter is changed");
		}
	}

	public void clickRadioRatePlanIDFilter() throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 30, radioRatePlanID);
			radioRatePlanID.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeRadioRatePlanID",
					"Cannot click on the radio Rate Plan ID of filter field");
		}
	}

	public void clickRadioDesCommentFilter() throws InterruptedException, IOException {
		seleniumHelper.waitClickAble(driver, 30, radioDesComments);
		radioDesComments.click();

	}

	public void clickRadioAllFilter() throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 30, radioAll);
			radioAll.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeRadioAll", "Cannot click on the radio All of filter field");
		}
	}

	public String getSelectedBureau() throws IOException, InterruptedException {
		try {
			Select oBureau = new Select(dropboxBureau);
			return oBureau.getFirstSelectedOption().getText();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeBureauName",
					"The web element of the Select Bureau dropdown is changed.");
			throw new RuntimeException(ex);

		}
	}

	public String getSelectedBureauAdmin(String description) throws IOException, InterruptedException {
		try {
			Thread.sleep(3000);
			String bureauDesAd = getElementBureauAdmin(description).getText();
			return bureauDesAd;
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeBureauNameInAdmin",
					"The web element of the Bureau field is changed on Rate Plan Admin page");
			throw new RuntimeException(ex);

		}
	}

	public void checkLabelRatePlanSelectedBureau(String bureauName) throws InterruptedException, IOException {
		try {
			String labelectedBureau = "Rate Plans for: " + bureauName;
			if (labelectedBureau.equalsIgnoreCase(labelRatePlanSelectedBureau.getText())) {
				Log.info("The label 'Rate Plans for: " + bureauName + "' is displayed correctly.");
			} else {
				seleniumHelper.setError(driver, "DontseeBureauName" + bureauName,
						"The label 'Rate Plans for: " + bureauName + "' is NOT displayed.");
			}
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontseeBureauName" + bureauName,
					"The web element of label 'Rate Plans for: selected bureau' is changed.");
		}
	}

	public void createJsonBureauList() throws Exception {
		CreateJsonFile.createJsonFileBureauList();
	}

	public void createJsonRatePlanList(String bureauName) throws Exception {
		CreateJsonFile.createJsonFileRatePlanList(bureauName);
	}

	public void createJsonSpecificRatePlan(String bureauName, int id) throws Exception {
		CreateJsonFile.createJsonFileSpecificRatePlan(bureauName, id);
	}

	public void createJsonCallRateWithDes(String description) throws Exception {
		int ratePlanId = getNumberRatePlanIDWithDes(description);
		String bureauName = getSelectedBureau();
		CreateJsonFile.createJsonFileCallRateWithID(bureauName, ratePlanId);
	}

	public void createJsonSerNumWithDes(int ratePlanId, String bureauName) throws Exception {
		try {
			CreateJsonFile.createJsonFileSerNumWithID(bureauName, ratePlanId);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "SomethingWrongJson",
					"Something wrong with the Json file Json SerNumWithID" + ratePlanId + ".js");
		}
	}

	public void createJsonSerNumWithDesAd(String description) throws Exception {
		int ratePlanId = getNumberRatePlanIDWithDes(description);
		String bureauName = getSelectedBureauAdmin(description);
		CreateJsonFile.createJsonFileSerNumWithID(bureauName, ratePlanId);
	}

	public boolean visibleSelectBureauDropBox() {
		try {
			seleniumHelper.waitElementVisible(driver, 60, "//div[contains(@class,'select')]/select");
			return dropboxBureau.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void getDefaultValueInSelectBureauDBox(String value) throws InterruptedException, IOException {
		try {
			Select oBureau = new Select(dropboxBureau);
			String defaultValue = oBureau.getFirstSelectedOption().getText();
			if (value.equalsIgnoreCase(defaultValue)) {
				Log.info("The default value " + value + " is selected in Select Bureau dropdown");
			} else {
				seleniumHelper.setError(driver, "DontSeeDefaulValueInSelectBureau",
						"The default value " + value + " is NOT displayed in Select Bureau dropdown");
			}

		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeDefaulValueInSelectBureau",
					"The web element of Select Bureau dropdown is changed");
		}
	}

	public void selectBureau(String bureauName) throws InterruptedException, IOException {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnCreateRatePlan);
			Select oBureau = new Select(dropboxBureau);
			oBureau.selectByVisibleText(bureauName);
			Log.info("The bureau " + bureauName + " is selected in Select Bureau dropdown");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSee" + bureauName, "Cannot select bureau " + bureauName);
		}
	}

	public int getTotaRatePlans() {
		return valueRatePlanDes.size();
	}

	public int emptyRatePlans() throws InterruptedException {
		Thread.sleep(2000);
		return valueRatePlanDes.size();

	}

	public boolean visibleCallRateWithDes(String description) {
		String tableElement = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../../following-sibling::div//table/tbody";
		try {
			return driver.findElement(By.xpath(tableElement)).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public String getSummaryInfo(String field, int i) throws InterruptedException, IOException {
		try {
			switch (field) {
			case ("Rate Plan Id"):
				String ratePlanAllText = valueRatePlanId.get(i).getText();
				String des = valueRatePlanDes.get(i).getText();
				return ratePlanAllText.contains("New") ? "New" : "" + getNumberRatePlanIDWithDes(des);
			case ("Description"):
				seleniumHelper.scrollDownToElement(driver, valueRatePlanDes.get(i));
				return valueRatePlanDes.get(i).getText();
			case ("Rate Plan Type"):
				return valueRatePlanType.get(i).getText();
			case ("Comments"):
				return valueRatePlanComments.get(i).getText();
			default:
				String screenShotCapture = seleniumHelper.capture(driver, "DontSee" + field);
				Reporter.addScreenCaptureFromPath(screenShotCapture);
				Log.error("The field " + field + " does not exist in our system");
				throw new RuntimeException("The field " + field + " does not exist in our system");

			}
		} catch (Exception ex) {
			return "";
		}
	}

	public List<List<String>> getSummaryInfo() {
		List<String> summaryInfo = new ArrayList<String>();
		List<List<String>> allList = new ArrayList<List<String>>();
		int total = getTotaRatePlans();
		for (int i = 0; i < total; i++) {
			String des = valueRatePlanDes.get(i).getText();
			int ratePlanID = getNumberRatePlanIDWithDes(des);
			if (ratePlanID != 0) {
				summaryInfo.add(valueRatePlanComments.get(i).getText());
				summaryInfo.add("" + ratePlanID);
				summaryInfo.add(valueRatePlanType.get(i).getText());
				summaryInfo.add(des);
			}
		}
		allList.add(summaryInfo);
		return allList;
	}

	public String firstSelectedRPType() {
		Select rpType = new Select(selectRatePlanTypeEdit);
		return rpType.getFirstSelectedOption().getText();
	}

	public String firstSelectedIDDValue() {
		Select idd = new Select(selectIDDReferenceEdit);
		return idd.getFirstSelectedOption().getText();
	}

	public List<String> getSavedSummaryInfo() {
		try {
			List<String> savedSummaryInfo = new ArrayList<String>();
			savedSummaryInfo.add(valueRatePlanCommentsEdit.get(0).getText());
			savedSummaryInfo.add(firstSelectedRPType());
			savedSummaryInfo.add(firstSelectedIDDValue());
			return savedSummaryInfo;
		} catch (Exception ex) {
			return null;
		}
	}

	public String getDesAndScroll(int i) throws InterruptedException, IOException {
		try {
			seleniumHelper.scrollDownToElement(driver, valueRatePlanDes.get(i));
			return valueRatePlanDes.get(i).getText();
		} catch (Exception ex) {
			return "";
		}

	}

	public String getDesRPNoIDDAndScroll(int i) throws InterruptedException, IOException {
		try {
			Thread.sleep(2000);
			WebElement ele = valueDesRPNoIDD.get(i);
			return ele.getText().trim();
		} catch (Exception ex) {
			return "";
		}

	}

	public String getDesRPIDDAndScroll(int i) throws InterruptedException, IOException {
		try {
			WebElement ele = valueDesRPIDD.get(i);
			seleniumHelper.scrollDownToElement(driver, ele);
			return ele.getText().trim();
		} catch (Exception ex) {
			return "";
		}

	}

	public String getDesRPWithNoPending() {
		try {
			String des = "";
			int totalRP = getTotaRatePlans();
			for (int i = 0; i < totalRP; i++) {
				des = getSummaryInfo("Description", i);
				if (!isPending(des))
					break;
			}
			return des;
		} catch (Exception ex) {
			return "";
		}

	}

	public String getDesWithoutPending(int i) {
		return valueDesWithoutPending.get(i).getText().trim();
	}

	public String getDesRPWithPendingOwner() {
		try {
			String SubmittedBy = "";
			String owner = LoginSteps.globalUserName;
			String des = "";
			int totalRP = getTotaRatePlans();
			for (int i = 0; i < totalRP; i++) {
				SubmittedBy = valueRatePlanSubBy.get(i).getText();
				des = valueRatePlanDes.get(i).getText();
				if (isPending(des) && SubmittedBy.equalsIgnoreCase(owner)) {
					break;
				} else
					des = "";
			}
			return des;
		} catch (Exception ex) {
			return "";
		}

	}

	public String getDesRPWithPendingNotOwner() {
		try {
			String SubmittedBy = "";
			String owner = LoginSteps.globalUserName;
			String des = "";
			int totalRP = getTotaRatePlans();
			for (int i = 0; i < totalRP; i++) {
				SubmittedBy = valueRatePlanSubBy.get(i).getText();
				des = valueRatePlanDes.get(i).getText();
				if (isPending(des) && SubmittedBy != owner) {
					break;
				} else
					des = "";
			}
			return des;
		} catch (Exception ex) {
			return "";
		}

	}

	public boolean seeRatePlanLabel(String field, int i) throws InterruptedException, IOException {
		try {
			switch (field) {
			case ("Rate Plan Id"):
				return labelRatePlanId.get(i).isDisplayed();
			case ("Description"):
				return labelRatePlanDes.get(i).isDisplayed();
			case ("Rate Plan Type"):
				return labelRatePlanType.get(i).isDisplayed();
			case ("Comments"):
				return labelRatePlanComments.get(i).isDisplayed();
			case ("Submitted By"):
				return labelRatePlanSubBy.get(i).isDisplayed();
			case ("Submitted Date"):
				return labelRatePlanSubDate.get(i).isDisplayed();
			default:
				return false;

			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeTable() throws InterruptedException {
		try {
			Thread.sleep(1000);
			return table.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public int getTotalRowCallRateWithDes(String description) {
		String callRateCatXpath = "//li[contains(@class,'is-visible')]//div[text()='" + description
				+ "']/../following-sibling::div//table/tbody/tr/td[1]";
		int total = driver.findElements(By.xpath(callRateCatXpath)).size();
		return total;
	}

	public int getTotalRowCallRateOfRPID(int ratePlanID) throws InterruptedException, IOException {
		return getElementCallRateOfRPID(ratePlanID, "Call Rate Category").size();
	}

	public int getTotalRowCallRateEdit() {
		String callRateCatXpath = "//li[contains(@class,'is-visible')]//textarea/../../../../following-sibling::div//table/tbody/tr/td[1]";
		int total = driver.findElements(By.xpath(callRateCatXpath)).size();
		return total;
	}

	public String getValueCallRateWithAPI(String column, String description, int i)
			throws InterruptedException, IOException {
		if (column.equalsIgnoreCase("Floating Credit Included")) {
			String value = getElementCallRateInfoWithDes(description, column).get(i).getText();
			return value.equalsIgnoreCase("Yes") ? "true" : "false";
		} else
			return getElementCallRateInfoWithDes(description, column).get(i).getText();
	}

	public String getValueCallRateOfRPID(String column, int ratePlanID, int i)
			throws InterruptedException, IOException {
		return getElementCallRateOfRPID(ratePlanID, column).get(i).getText();
	}

	public List<List<String>> getCallRateAPIList(String description) throws InterruptedException, IOException {
		Thread.sleep(2000);
		List<List<String>> allCallRate = new ArrayList<List<String>>();
		String[] webLabel = { "Initial Duration (sec)", "Rate per Cost", "Rate Fixed", "Rate per KB", "Rate per Second",
				"Rate per 30 Sec", "Rate per 60 Sec", "Capped Fixed Duration", "Capped Max Rate For Fixed Duration",
				"Capped Rate (after Duration) per Second" };
		int total = getTotalRowCallRateWithDes(description);
		for (int i = 0; i < total; i++) {
			List<String> callRate = new ArrayList<String>();
			callRate.add(getElementCallRateInfoWithDes(description, "Call Rate Category").get(i).getText());
			for (int j = 0; j < webLabel.length; j++) {
				callRate.add(String.valueOf(
						Double.parseDouble(getElementCallRateInfoWithDes(description, webLabel[j]).get(i).getText())));
			}
			String floatCredit = getElementCallRateInfoWithDes(description, "Floating Credit Included").get(i)
					.getText();
			if (floatCredit.equalsIgnoreCase("Yes"))
				floatCredit = "true";
			else
				floatCredit = "false";
			callRate.add(floatCredit);
			allCallRate.add(callRate);
		}
		return allCallRate;
	}

	public String getValueCallRateWithNoAPI(String column, String description, int i)
			throws InterruptedException, IOException {
		return getElementCallRateInfoWithDes(description, column).get(i).getText();
	}

	public String getFieldJsonCallRate(String callRate) {
		switch (callRate) {
		case ("Call Rate Category"):
			return "callRateCategory.description";
		case ("Initial Duration (sec)"):
			return "initialDurationSec";
		case ("Rate per Cost"):
			return "ratePerCost";
		case ("Rate Fixed"):
			return "ratePerSecond";
		case ("Rate per KB"):
			return "ratePerKB";
		case ("Rate per Second"):
			return "ratePerSecond";
		case ("Rate per 30 Sec"):
			return "rate30Sec";
		case ("Rate per 60 Sec"):
			return "rate60Sec";
		case ("Capped Fixed Duration"):
			return "cappedTimeSec";
		case ("Capped Max Rate For Fixed Duration"):
			return "cappedRateInitial";
		case ("Capped Rate (after Duration) per Second"):
			return "cappedRateAfterTime";
		case ("Floating Credit Included"):
			return "floatingCredit";
		default:
			return "";
		}
	}

	public boolean seeBtnCreateNewRatePlan() {
		try {
			return btnCreateRatePlan.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickCreateRatePlan() throws InterruptedException, IOException {
		Thread.sleep(1000);
		seleniumHelper.waitClickAble(driver, 60, btnCreateRatePlan);
		btnCreateRatePlan.click();
		Log.info("The button Create New Rate Plan is clicked");
	}

	public int getTotalListBureauCreateDialog() {
		try {
			Select s = new Select(tagSelectBureauCreateOrCopyDialog);
			return s.getAllSelectedOptions().size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public void selectBureauCreateDialog(String bureau) throws InterruptedException, IOException {
		try {
			Select s = new Select(tagSelectBureauCreateOrCopyDialog);
			s.selectByValue(bureau);
			Log.info("The bureau " + bureau + " is selected in dropdown");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBureauInCreateDialog" + bureau,
					"Cannot select the bureau " + bureau + " in the Target Rate Plan under Create Dialog");
		}
	}

	public void enterDesCreateDialog(String description) throws InterruptedException, IOException {
		try {
			txtDesCreateOrCopyDialog.clear();
			Thread.sleep(2000);
			txtDesCreateOrCopyDialog.sendKeys(description);
			Log.info("The Description textbox under Create Dialog is saying " + description);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeDescriptionInCreateDialog",
					"Cannot select the Description textbox under Create Dialog");
		}
	}

	public void checkBoxIDD() throws InterruptedException, IOException {
		try {
			cBoxIDDCreateOrCopyDialog.click();
			Log.info("The IDD checkbox under Create Dialog is selected");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeCBoxIDDInCreateDialog",
					"Cannot click on the International Direct Dial checkbox under Create Dialog");
		}
	}

	public void clickOnCreateBtnInCreateDialog(String btnName) throws InterruptedException, IOException {
		try {
			Thread.sleep(1000);
			// seleniumHelper.waitLocated(driver, 10,
			// "//form//button[contains(@class,'save')]");
			seleniumHelper.waitClickAble(driver, 60, btnCreateOrCopyOnDialog);
			btnCreateOrCopyOnDialog.click();
			Log.info("The " + btnName + " button under Create Dialog is clicked");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtn" + btnName + "InCreateDialog",
					"Cannot click on the " + btnName + " button under " + btnName + " Dialog");
		}
	}

	public void clickOnCancelBtnInCreateCopyDialog() throws InterruptedException, IOException {
		try {
			btnCancelCreateOrCopyDialog.click();
			Log.info("The Cancel button under Create Dialog is clicked");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtnCancelInCreateDialog",
					"Cannot click on the Cancel button under Create Dialog");
		}
	}

	public void clickOnXBtnInCreateDialog() throws InterruptedException, IOException {
		try {
			btnCreateOrCopyOnDialog.click();
			Log.info("The X button under Create Dialog is clicked");
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtnXInCreateDialog",
					"Cannot click on the X button under Create Dialog");
		}
	}

	public boolean visibleCreateDialog() {
		try {
			Thread.sleep(2000);
			return tagSelectBureauCreateOrCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeNewRatePlanAtTop(String description) {
		try {
			seleniumHelper.waitClickAble(driver, 20, valueRatePlanDesEdit.get(0));
			String currentDescription = valueRatePlanDesEdit.get(0).getText();
			return description.equalsIgnoreCase(currentDescription);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean validSelectedBureau(String bureau) throws IOException, InterruptedException {
		String currentBureau = getSelectedBureau();
		return bureau.equalsIgnoreCase(currentBureau);
	}

	public void validValueNewRatePlan(String field, String value) throws IOException, InterruptedException {
		try {
			String currentValue = "";
			int first = 0;
			switch (field) {
			case ("Description"):
				currentValue = valueRatePlanDesEdit.get(first).getText();
				break;
			case ("Rate Plan ID"):
				currentValue = valueRatePlanId.get(first).getText();
				break;
			case ("Comments"):
				currentValue = valueRatePlanCommentsEdit.get(first).getText();
				break;
			case ("Call Rate Category"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(1).getText();
				break;
			case ("Initial Duration (sec)"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(2).getText();
				break;
			case ("Rate per Cost"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(3).getText();
				break;
			case ("Rate Fixed"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(4).getText();
				break;
			case ("	Rate per KB"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(5).getText();
				break;
			case ("	Rate per Second"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(6).getText();
				break;
			case ("Rate per 30 Sec"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(7).getText();
				break;
			case ("Rate per 60 Sec"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(8).getText();
				break;
			case ("Capped Fixed Duration"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(9).getText();
				break;
			case ("Capped Max Rate For Fixed Duration"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(10).getText();
				break;
			case ("Capped Rate (after Duration) per Second"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(11).getText();
				break;
			case ("Floating Credit Included"):
				currentValue = getDefaultCellOfFirstRowAtTop().get(12).getText();
				break;
			default:
				currentValue = "";
			}
			if (currentValue.equalsIgnoreCase(value)) {
				Log.info("The " + field + " with value " + value + " is displayed for new one");
			} else {
				seleniumHelper.setError(driver, "DontSee" + field + "" + value,
						"The " + field + " with value " + value + " is NOT displayed for new one");
			}
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElement" + field + "" + value, "The website element of " + field
					+ " with value " + value + " is changed. Please update the code.");
		}
	}

	public List<WebElement> getDefaultCellOfFirstRowAtTop() {
		return driver.findElements(By.xpath("//li[contains(@class,'is-visible')][1]//tbody/tr[1]/td"));
	}

	public boolean visibleBtnSaveChangeOfRatePlan() {
		try {
			return btnSaveChangeOfRatePlan.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleBtnCancelChangeOfRatePlan() {
		try {
			return btnCancelChangeOfRatePlan.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickOnSaveChangeOfRatePlan() throws InterruptedException, IOException {
		try {
			Thread.sleep(1000);
			btnSaveChangeOfRatePlan.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElementSaveBtn",
					"The element of Save button in Rate Plan record is changed");
		}
	}

	public void clickOnCancelChangeOfRatePlan() throws InterruptedException, IOException {
		try {
			btnCancelChangeOfRatePlan.click();
			btnConfirmPopup.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElementCancelBtn",
					"The element of Cancel button in Rate Plan record is changed");
		}
	}

	public void enterTxtSummaryInfoAtTop(String value, String field) throws InterruptedException, IOException {
		try {
			switch (field) {
			case ("Description"):
				valueRatePlanDesEdit.get(0).clear();
				Thread.sleep(1000);
				valueRatePlanDesEdit.get(0).sendKeys(value);
				break;
			case ("Comments"):
				valueRatePlanCommentsEdit.get(0).clear();
				Thread.sleep(1000);
				valueRatePlanCommentsEdit.get(0).sendKeys(value);
				break;
			default:
				seleniumHelper.setError(driver, "WrongFieldRatePlan" + field,
						"The field " + field + " does not exist in system");
			}

		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeElement" + field,
					"The website element of the field " + field + " is changed");
		}
	}

	public void enterTxtCallRateNoIDDAtTop(String value, String field) throws InterruptedException, IOException {
		enterTxtNewCallRateNoIDD(1, field, value);
	}

	public void enterTxtCallRateNoIDDAtLast(String value, String field) throws InterruptedException, IOException {
		int newRow = getTotalRowCallRateEdit();
		enterTxtNewCallRateNoIDD(newRow, field, value);
	}

	public void enterTxtCallRateWithIDDAtTop(String value, String field) throws InterruptedException, IOException {
		enterTxtNewCallRateWithIDD(1, field, value);
	}

	public void enterTxtCallRateWithIDDAtLast(String value, String field) throws InterruptedException, IOException {
		int newRow = getTotalRowCallRateEdit();
		enterTxtNewCallRateWithIDD(newRow, field, value);
	}

	public void selectDropBoxSummaryInfoAtTop(String value, String field) throws InterruptedException, IOException {
		switch (field) {
		case ("Rate Plan Type"):
			seleniumHelper.waitClickAble(driver, 60, selectRatePlanTypeEdit);
			Select s1 = new Select(selectRatePlanTypeEdit);
			s1.selectByVisibleText(value);
			break;
		case ("IDD reference"):
			seleniumHelper.waitClickAble(driver, 60, selectIDDReferenceEdit);
			Select s2 = new Select(selectIDDReferenceEdit);
			s2.selectByValue(value);
			break;
		default:
			seleniumHelper.setError(driver, "WrongFieldRatePlan" + field,
					"The field " + field + " does not exist in system");
		}

	}

	public void selectDropBoxCallRateNoIDDAtTop(String value, String field) throws InterruptedException, IOException {
		switch (field) {
		case ("Call Rate Category"):
			selectCallRateCatAtTop(1, value);
			break;
		case ("Floating Credit Included"):
			selectFloatingCreditNoIDDAtTop(1, value);
			break;
		default:
			seleniumHelper.setError(driver, "WrongFieldRatePlan" + field,
					"The field " + field + " does not exist in system");
		}
	}

	public void selectFloatingCreditWithIDDAtTop(String value) throws InterruptedException, IOException {
		selectFloatingCreditWithIDDAtTop(1, value);
	}

	public void selectFloatingCreditWithIDDAtLast(String value) throws InterruptedException, IOException {
		int newRow = getTotalRowCallRateEdit();
		selectFloatingCreditWithIDDAtTop(newRow, value);
	}

	public void selectDropBoxNewCallRate(String value, String field, int lastCallRate)
			throws InterruptedException, IOException {
		value = isCSVLastCallRate(value, field);
		switch (field) {
		case ("Call Rate Category"):
			selectCallRateCatAtTop(lastCallRate, value);
			break;
		case ("Floating Credit Included"):
			selectFloatingCreditNoIDDAtTop(lastCallRate, value);
			break;
		default:
			seleniumHelper.setError(driver, "WrongFieldRatePlan" + field,
					"The field " + field + " does not exist in system");
		}

	}

	public void selectCallRateCatAtTop(int i, String callRateCat) throws InterruptedException, IOException {
		callRateCat = isCSVNormalCallRate(callRateCat, ratePlanReader.getCallRateCat());
		String xpathSelectCallRateCat = "//li[contains(@class,'is-visible')]//tbody/tr[" + i + "]/td[2]//div/select";
		WebElement ele = driver.findElement(By.xpath(xpathSelectCallRateCat));
		seleniumHelper.waitClickAble(driver, 30, ele);
		Select s = new Select(ele);
		s.selectByVisibleText(callRateCat);
	}

	public int getTotalRowCallRateAtTop() {
		try {
			String xpathSelectCallRateCat = "//li[contains(@class,'is-visible')]//tbody/tr/td[2]//div/select";
			List<WebElement> eleList = driver.findElements(By.xpath(xpathSelectCallRateCat));
			return eleList.size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public void selectFloatingCreditNoIDDAtTop(int rowPosition, String floatingCredit)
			throws InterruptedException, IOException {
		String xpathSelectFloatingCredit = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition
				+ "]/td[13]//div/select";
		WebElement ele = driver.findElement(By.xpath(xpathSelectFloatingCredit));
		seleniumHelper.waitClickAble(driver, 30, ele);
		Select s = new Select(ele);
		s.selectByVisibleText(floatingCredit);
	}

	public void selectFloatingCreditWithIDDAtTop(int rowPosition, String floatingCredit)
			throws InterruptedException, IOException {
		String xpathSelectFloatingCredit = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition
				+ "]/td[14]//div/select";
		WebElement ele = driver.findElement(By.xpath(xpathSelectFloatingCredit));
		seleniumHelper.waitClickAble(driver, 30, ele);
		Select s = new Select(ele);
		s.selectByVisibleText(floatingCredit);
	}

	public void enterTxtNewCallRateNoIDD(int rowPosition, String field, String number)
			throws InterruptedException, IOException {
		String xpath = "";
		int i = 0;
		switch (field) {
		case ("Initial Duration (sec)"):
			i = 3;
			break;
		case ("Rate per Cost"):
			i = 4;
			break;
		case ("Rate Fixed"):
			i = 5;
			break;
		case ("Rate per KB"):
			i = 6;
			break;
		case ("Rate per Second"):
			i = 7;
			break;
		case ("Rate per 30 Sec"):
			i = 8;
			break;
		case ("Rate per 60 Sec"):
			i = 9;
			break;
		case ("Capped Fixed Duration"):
			i = 10;
			break;
		case ("Capped Max Rate For Fixed Duration"):
			i = 11;
			break;
		case ("Capped Rate (after Duration) per Second"):
			i = 12;
			break;
		}
		xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition + "]/td[" + i + "]//div/input";
		WebElement ele = driver.findElement(By.xpath(xpath));
		seleniumHelper.sendKey(ele, number);
	}

	public void enterTxtNewCallRateWithIDD(int rowPosition, String field, String number)
			throws InterruptedException, IOException {
		String xpath = "";
		int i = 0;
		switch (field) {
		case ("Country"):
			i = 2;
			break;
		case ("Call Prefix"):
			i = 3;
			break;
		case ("Initial Duration (sec)"):
			i = 4;
			break;
		case ("Rate per Cost"):
			i = 5;
			break;
		case ("Rate Fixed"):
			i = 6;
			break;
		case ("Rate per KB"):
			i = 7;
			break;
		case ("Rate per Second"):
			i = 8;
			break;
		case ("Rate per 30 Sec"):
			i = 9;
			break;
		case ("Rate per 60 Sec"):
			i = 10;
			break;
		case ("Capped Fixed Duration"):
			i = 11;
			break;
		case ("Capped Max Rate For Fixed Duration"):
			i = 12;
			break;
		case ("Capped Rate (after Duration) per Second"):
			i = 13;
			break;
		default:
			i = 100;
		}
		xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition + "]/td[" + i + "]//div/input";
		WebElement ele = driver.findElement(By.xpath(xpath));
		seleniumHelper.sendKey(ele, number);
	}

	public String getValueCallRate(int rowPosition, String field) throws InterruptedException, IOException {
		try {
			String xpath = "";
			int i = 0;
			switch (field) {
			case ("Call Rate Category"):
				i = 2;
				break;
			case ("Initial Duration (sec)"):
				i = 3;
				break;
			case ("Rate per Cost"):
				i = 4;
				break;
			case ("Rate Fixed"):
				i = 5;
				break;
			case ("Rate per KB"):
				i = 6;
				break;
			case ("Rate per Second"):
				i = 7;
				break;
			case ("Rate per 30 Sec"):
				i = 8;
				break;
			case ("Rate per 60 Sec"):
				i = 9;
				break;
			case ("Capped Fixed Duration"):
				i = 10;
				break;
			case ("Capped Max Rate For Fixed Duration"):
				i = 11;
				break;
			case ("Capped Rate (after Duration) per Second"):
				i = 12;
				break;
			case ("Floating Credit Included"):
				i = 13;
				break;
			default:
				seleniumHelper.setError(driver, "DontSeeField" + field,
						"The field " + field + " does not exist in our system");
			}
			if (i == 2 || i == 13) {
				xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition + "]/td[" + i + "]//div/select";
				WebElement ele = driver.findElement(By.xpath(xpath));
				Select s = new Select(ele);
				return s.getFirstSelectedOption().getText();
			} else {
				xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition + "]/td[" + i + "]//div/input";
				WebElement ele = driver.findElement(By.xpath(xpath));
				return ele.getAttribute("value");
			}
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeColumn" + field,
					"The website element of field " + field + " is changed, please update the test case.");
			throw new RuntimeException("");
		}
	}

	public List<List<String>> getSavedCallRateList() throws InterruptedException, IOException {
		Thread.sleep(2000);
		List<List<String>> arrCallRate = new ArrayList<List<String>>();
		int totalRowCallRate = getTotalRowCallRateEdit();
		for (int i = 1; i <= totalRowCallRate; i++) {
			List<String> rowCallRate = new ArrayList<String>();
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getCallRateCat()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getInitialDuration()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRatePerCost()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRateFixed()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRatePerKB()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRatePerSecond()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRatePer30Sec()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getRatePer60Sec()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getCapFixed()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getCapMax()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getCapRate()));
			rowCallRate.add(getValueCallRate(i, ratePlanReader.getFloatCredit()));
			arrCallRate.add(rowCallRate);
		}
		return arrCallRate;
	}

	public boolean saveRatePlanSuccessfully(String description) throws InterruptedException, IOException {
		try {
			String xpath = "//li[contains(@class,'is-visible')]//label[contains(text(),'Description')]/following-sibling::div[contains(text(),'"
					+ description + "')]";
			seleniumHelper.waitElementVisible(driver, 60, xpath);
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickConfirmPopup() throws InterruptedException, IOException {
		try {
			btnConfirmPopup.click();
			Thread.sleep(2000);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontConfirmBtn",
					"The Confirm btn is not displayed or website element is changed.");
		}
	}

	public void clickCloseConfirmPopup() throws InterruptedException, IOException {
		try {
			btnCloseConfirmPopup.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontCloseBtn",
					"The Close btn is not displayed or website element is changed.");
		}
	}

	public boolean visibleClosePopup() throws InterruptedException, IOException {
		try {
			return btnClosePopup.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickClosePopup() throws InterruptedException, IOException {
		try {
			btnClosePopup.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontCloseBtn",
					"The Close btn is not displayed or website element is changed.");
		}
	}

	public boolean visibleRPWithDes(String description) throws InterruptedException, IOException {
		try {
			Thread.sleep(1000);
			seleniumHelper.waitClickAble(driver, 60, txtFilter);
			return getElementDesWithDes(description).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeHeaderCallRate(String header) throws InterruptedException, IOException {
		try {
			return getEleHeaderCallRate(header).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickBtnWithRPDes(String btnName, String description) throws InterruptedException, IOException {
		WebElement ele = getEleActionBtnOfDes(btnName, description);
		seleniumHelper.waitClickAble(driver, 60, ele);
		ele.click();
	}

	public void clickActionBtnOfRPID(String btnName, int ratePlanID) {
		WebElement ele = getEleActionBtnOfRPID(btnName, ratePlanID);
		seleniumHelper.waitClickAble(driver, 60, ele);
		ele.click();

	}

	public boolean enableBtnWithRPDes(String btnName, String description) throws InterruptedException, IOException {
		try {
			switch (btnName) {
			case ("Edit"):
				return getElementEditRatePlanBtn(description).isEnabled();
			case ("Copy"):
				return getElementCopyRatePlanBtn(description).isEnabled();
			case ("Delete"):
				return getElementDeleteRatePlanBtn(description).isEnabled();
			default:
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean disableBtnWithRPDes(String btnName, String description) throws InterruptedException, IOException {
		try {
			String xpathBtn = "";
			switch (btnName) {
			case ("Edit"):
				xpathBtn = "//li[contains(@class,'is-visible')]//div[text()='" + description
						+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-edit')]";
				break;
			case ("Copy"):
				xpathBtn = "//li[contains(@class,'is-visible')]//div[text()='" + description
						+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-copy')]";
				break;
			case ("Delete"):
				xpathBtn = "//li[contains(@class,'is-visible')]//div[text()='" + description
						+ "']/../following-sibling::div[contains(@class,'actions')]/button[contains(@class,'button-delete')]";
				break;
			default:
				seleniumHelper.setError(driver, "DontSeeBtn" + btnName, "The button " + btnName + " does not exist");
			}
			Thread.sleep(2000);
			return !driver.findElement(By.xpath(xpathBtn)).isEnabled();

		} catch (Exception ex) {
			return false;
		}
	}

	public void openCalculator(String fieldCalculator, int positionRowCallRate)
			throws InterruptedException, IOException {
		int td;
		switch (fieldCalculator) {
		case ("Rate per Cost"):
			td = 4;
			break;
		case ("Rate per KB"):
			td = 6;
			break;
		default:
			td = 0;
		}
		String xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + positionRowCallRate + "]/td[" + td
				+ "]//button";
		WebElement ele = driver.findElement(By.xpath(xpath));
		seleniumHelper.waitClickAble(driver, 30, ele);
		ele.click();

	}

	public boolean visibleFieldsInCalculation(String field) {
		try {
			seleniumHelper.waitClickAble(driver, 30, btnSetOfCal);
			if (field.equalsIgnoreCase(ratePlanReader.getTitleCalRatePerCost())) {
				titleCalRatePerCost.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getWholesaleAmountCal())) {
				txtWholesaleAmount.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getRetailAmountCal())) {
				txtRetailAmount.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getResultRatePerCost())) {
				txtResultRatePerCost.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getTitleCalRatePerKB())) {
				titleCalRatePerKB.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getDataTypeCal())) {
				tagSelectDataType.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getAmount())) {
				txtAmount.isDisplayed();
			} else if (field.equalsIgnoreCase(ratePlanReader.getResultKB())) {
				txtResultRatePerKB.isDisplayed();
			} else
				return false;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeWholseSalePopup() {
		try {
			return titleCalRatePerCost.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeDataCalculatorPopup() {
		try {
			return titleCalRatePerKB.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleBtnOfCal(String btnName) {
		try {
			switch (btnName) {
			case ("Set"):
				btnSetOfCal.isDisplayed();
				return true;
			case ("Close"):
				btnCloseOfCal.isDisplayed();
				return true;
			default:
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickBtnOfCal(String btnName) throws InterruptedException, IOException {
		switch (btnName) {
		case ("Set"):
			btnSetOfCal.click();
			break;
		case ("Close"):
			btnCloseOfCal.click();
			break;
		default:
			seleniumHelper.setError(driver, "DontSeeBtn" + btnName, "The button " + btnName + " does not exist");
		}

	}

	public void enterTxtInCal(String field, String value) throws InterruptedException, IOException {
		if (field.equalsIgnoreCase(ratePlanReader.getWholesaleAmountCal()))
			seleniumHelper.sendKey(txtWholesaleAmount, value);
		else if (field.equalsIgnoreCase(ratePlanReader.getRetailAmountCal()))
			seleniumHelper.sendKey(txtRetailAmount, value);
		else if (field.equalsIgnoreCase(ratePlanReader.getAmount()))
			seleniumHelper.sendKey(txtAmount, value);
		else
			seleniumHelper.setError(driver, "DontSeeTxt" + field, "The textbox " + field + " does not exist");
	}

	public boolean validValueRatePerCostInCal(String value) {
		try {
			return txtResultRatePerCost.getAttribute("value").trim().equalsIgnoreCase(value.trim());
		} catch (Exception ex) {
			return false;
		}
	}

	public String getValueTxtBoxOfCallRate(int rowPosition, String field) throws InterruptedException, IOException {
		try {
			String xpath = "";
			int i = 0;
			if (field.equalsIgnoreCase(ratePlanReader.getInitialDuration())) {
				i = 3;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerCost())) {
				i = 4;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRateFixed())) {
				i = 5;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerKB())) {
				i = 6;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerSecond())) {
				i = 7;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer30Sec())) {
				i = 8;
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer60Sec())) {
				i = 9;
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapFixed())) {
				i = 10;
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapMax())) {
				i = 11;
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapRate())) {
				i = 12;
			} else {
				String screenShotPath = seleniumHelper.capture(driver, "DontSeeField" + field);
				Reporter.addScreenCaptureFromPath(screenShotPath);
				Log.error("The field " + field + " does not exist in our system");
				throw new RuntimeException("The field " + field + " does not exist in our system");
			}
			xpath = "//li[contains(@class,'is-visible')]//tbody/tr[" + rowPosition + "]/td[" + i + "]//div/input";
			WebElement ele = driver.findElement(By.xpath(xpath));
			return ele.getAttribute("value").toString();
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean validResultFromCalculatorToCallRate(int rowPosition, String field, String value) {
		try {
			return getValueTxtBoxOfCallRate(rowPosition, field).equalsIgnoreCase(value) ? true : false;
		} catch (Exception ex) {
			return false;
		}
	}

	public void selectDataType(String dataType) throws InterruptedException, IOException {
		try {
			Select s = new Select(tagSelectDataType);
			s.selectByVisibleText(dataType);
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeDataType",
					"The dropdown Data Type is changed in calculation of Rate PLan per KB");
		}
	}

	public boolean validValueRatePerKBInCal(String value) {
		try {
			return txtResultRatePerKB.getAttribute("value").toString().equalsIgnoreCase(value) ? true : false;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleCopyDialog() {
		try {
			return titleCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeDropboxBureauTargetRP() {
		try {
			return tagSelectBureauCreateOrCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeTxtboxDescriptionRP() {
		try {
			return txtDesCreateOrCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeBtnCreateCopyInPopup() {
		try {
			return btnCreateOrCopyOnDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enableBtnCreateCopyInPopup() {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnCancelCreateOrCopyDialog);
			return btnCreateOrCopyOnDialog.isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeBtnCancelInPopup() {
		try {
			return btnCancelCreateOrCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeIDDCBox() {
		try {
			return cBoxIDDCreateOrCopyDialog.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleDefaultBureauOnCreateOrCopyDialog(String bureau) {
		try {
			Select s = new Select(tagSelectBureauCreateOrCopyDialog);
			String currentBureau = s.getFirstSelectedOption().getText();
			return bureau.equalsIgnoreCase(currentBureau);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean emptyInDescriptionOfCreateOrCopy() {
		return txtDesCreateOrCopyDialog.getAttribute("value").toString().isEmpty();
	}

	public boolean equalDesOfCreateOrCopy(String des) {
		try {
			if (txtDesCreateOrCopyDialog.getAttribute("value").toString().trim().equalsIgnoreCase(des)) {
				return true;
			} else
				return false;
		} catch (Exception ex) {
			return false;
		}

	}

	public boolean disableCreateOrCopyBtnInDialog() throws InterruptedException, IOException {
		try {
			Thread.sleep(2000);
			return !btnCreateOrCopyOnDialog.isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean enableCreateOrCopyBtnInDialog() {
		try {
			Thread.sleep(2000);
			seleniumHelper.waitClickAble(driver, 120, btnCreateOrCopyOnDialog);
			return btnCreateOrCopyOnDialog.isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleMsgExistedDes() {
		try {
			seleniumHelper.waitElementVisible(driver, 60, "//p[text()='Description existed']");
			return msgExistedDes.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickTargetBureauDBox() throws InterruptedException, IOException {
		try {
			tagSelectBureauCreateOrCopyDialog.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DOnTSeeTargetBureauDBox",
					"The website element of Target Bureau dropdown is changed.");
		}
	}

	public int getTotalTargetBureauList() {
		try {
			Select s = new Select(tagSelectBureauCreateOrCopyDialog);
			return s.getOptions().size();
		} catch (Exception ex) {
			return 0;
		}
	}

	public String getTargetBureauValue(int i) {
		try {
			Select s = new Select(tagSelectBureauCreateOrCopyDialog);
			return s.getOptions().get(i).getText();
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean visibleMsgConfirmDelete() {
		try {
			seleniumHelper.waitElementVisible(driver, 60,
					"//*[contains(text(),'Do you really want to delete this change?')]");
			return msgConfirmDelete.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeListSerNumIconWithDes(String description) {
		try {
			return getElementSerNumIBtn(description).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public WebElement getElementSerNumIBtn(String description) {
		String xpath = "//label[contains(text(),'Description')]/following-sibling::div[text()='" + description
				+ "']/preceding-sibling::span/button[contains(@class,'button-list undefined')]";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement getElementSerNumIBtn(int ratePlanID) {
		String xpath = "//span[contains(@class,'rate-plan__summary__id')][text()='" + ratePlanID + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public void clickListSerNumIconWithDes(String description) throws InterruptedException, IOException {
		try {
			getElementSerNumIBtn(description).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeRatePlan",
					"The rate plan with description: " + description + " does not exist");
		}
	}

	public void clickListSerNumIconWithRPID(int ratePlanID) throws InterruptedException, IOException {
		try {
			getElementSerNumIBtn(ratePlanID).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeRatePlan",
					"The rate plan with Rate Plan Id '" + ratePlanID + "' does not exist");
		}
	}

	public boolean isPending(String des) {
		try {
			return getElementPendingStatus(des).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleRatePlanNumberWithDes(String description) {
		try {
			String getPlanID = getEleRatePlanID(description).getText();
			if (getPlanID.equalsIgnoreCase("New")) {
				return false;
			} else
				return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleNewInRatePlanWithDes(String description) {
		try {
			String getPlanID = getEleRatePlanID(description).getText();
			if (getPlanID.equalsIgnoreCase("New")) {
				return true;
			} else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public int getNumberRatePlanIDWithDes(String description) {
		try {
			Thread.sleep(1000);
			String allTextRatePlan = getEleRatePlanID(description).getText();
			String ratePlanID = Helper.splitNumCharacter(allTextRatePlan)[0];
			return Helper.isNumeric(ratePlanID) ? Integer.parseInt(ratePlanID) : 0;
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}

	public String getTRatePlanID(String description) {
		try {
			Thread.sleep(1000);
			String allTextRatePlan = getEleRatePlanID(description).getText();
			String ratePlanID = Helper.splitNumCharacter(allTextRatePlan)[0];
			return Helper.isNumeric(ratePlanID) ? ratePlanID : "New";
		} catch (Exception ex) {
			System.out.println(ex);
			return "New";
		}
	}

	public int getRatePlanID(int i) {
		try {
			String allTextRatePlan = valueRatePlanId.get(i).getText();
			String ratePlanID = Helper.splitNumCharacter(allTextRatePlan)[0];
			return Helper.isNumeric(ratePlanID) ? Integer.parseInt(ratePlanID) : 0;
		} catch (Exception ex) {
			return 0;
		}
	}

	public boolean seeSerNumPopup(String description) {
		try {
			seleniumHelper.waitClickAble(driver, 60, btnClosePopup);
			String expectedTitleSerNum = "";
			expectedTitleSerNum = "Service Numbers for Rate Plan No." + getNumberRatePlanIDWithDes(description);
			String currentTitleSerNum = titleSerNumPopup.getText();
			return expectedTitleSerNum.equalsIgnoreCase(currentTitleSerNum) ? true : false;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean dontSeeSerNumPopup() {
		try {
			return titleSerNumPopup.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleDownLoadCSVSerNum() {
		try {
			return btnDownLoadSerNum.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeHeadersSerNum(String headerSerNum) {
		try {
			return getEleHeaderSerNumTable(headerSerNum).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public String getValueColsSerNum(int i, String headerSerNum) {
		try {
			return getWebEleListValueSerNum(headerSerNum).get(i).getText().trim();
		} catch (Exception ex) {
			return "";
		}
	}

	public int getTotalSerNumRow() throws InterruptedException {
		return getWebEleListValueSerNum("CIDN").size();
	}

	public String getRatePlanIDFromTitleSerNumPopup() {
		try {
			String titleSerNum = titleSerNumPopup.getText();
			String[] arr = utilities.Helper.splitSpecificSymbold(titleSerNum, "\\.");
			return arr[1];
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String getFieldJsonSerNum(String headerSerNum) {
		String[] headerJson = { "cidn", "salutation", "firstName", "lastName", "longAddress", "contactNumber", "email",
				"serviceNumber" };
		if (headerSerNum.equalsIgnoreCase(ratePlanReader.getCIDNSerNum())) {
			return headerJson[0];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getSalutationSerNum())) {
			return headerJson[1];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getFirstNameSerNum())) {
			return headerJson[2];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getLastNameSerNum())) {
			return headerJson[3];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getAddressSerNum())) {
			return headerJson[4];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getContactNumSerNum())) {
			return headerJson[5];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getEmailSerNum())) {
			return headerJson[6];
		} else if (headerSerNum.equalsIgnoreCase(ratePlanReader.getServiceNumber())) {
			return headerJson[7];
		} else
			return "";
	}

	public void clickDownLoadSerNum() throws InterruptedException, IOException {
		try {
			btnDownLoadSerNum.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeDownLoadSerNum", "Don't See the Download as CSV button");
		}
	}

	public void doubleClickSerNumRow(int cidn) throws InterruptedException, IOException {
		WebElement ele = getEleCIDNInSerNumTable(cidn);
		seleniumHelper.doubleClick(driver, ele);
	}

	public String getNewWindow() throws Exception {
		try {
			Thread.sleep(2000);
			List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(browserTabs.get(1));
			String currentURL = driver.getCurrentUrl();
			driver.close();
			driver.switchTo().window(browserTabs.get(0));
			return currentURL;
		} catch (Exception ex) {
			return "";
		}
	}

	public List<String> getCurrentSummaryRPWithDes(String description) {
		List<String> arrSummary = new ArrayList<String>();
		try {
			arrSummary.add(description);
			arrSummary.add(getSummaryInfoWithDes(ratePlanReader.getComments(), description));
			arrSummary.add(getSummaryInfoWithDes(ratePlanReader.getRatePlanType(), description));
			arrSummary.add(getSummaryInfoWithDes(ratePlanReader.getSubmittedBy(), description));
			arrSummary.add(getSummaryInfoWithDes(ratePlanReader.getSubmittedDate(), description));
			arrSummary.add(getSummaryInfoWithDes(ratePlanReader.getIddReference(), description));
			return arrSummary;
		} catch (Exception ex) {
			return arrSummary;
		}
	}

	public List<String> getCurrentSummaryRPID(int ratePlanId) {
		List<String> arrSummary = new ArrayList<String>();
		try {
			arrSummary.add(getSummaryInfoViewModeOfRPID("Description", ratePlanId));
			arrSummary.add(getSummaryInfoViewModeOfRPID("Comments", ratePlanId));
			arrSummary.add(getSummaryInfoViewModeOfRPID("Rate Plan Type", ratePlanId));
			arrSummary.add(getSummaryInfoViewModeOfRPID("Submitted By", ratePlanId));
			arrSummary.add(getSummaryInfoViewModeOfRPID("Submitted Date", ratePlanId));
			arrSummary.add(getSummaryInfoViewModeOfRPID("IDD reference", ratePlanId));
			return arrSummary;
		} catch (Exception ex) {
			return arrSummary;
		}
	}

	public List<List<String>> getCurrentCallRateRPWithAPI(String description) {
		List<List<String>> arrCallRate = new ArrayList<List<String>>();
		try {
			Thread.sleep(3000);
			int totalRowCallRate = getTotalRowCallRateWithDes(description);
			for (int i = 0; i < totalRowCallRate; i++) {
				List<String> rowCallRate = new ArrayList<String>();
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getCallRateCat(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getInitialDuration(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRatePerCost(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRateFixed(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRatePerKB(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRatePerSecond(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRatePer30Sec(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getRatePer60Sec(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getCapFixed(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getCapMax(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getCapRate(), description, i));
				rowCallRate.add(getValueCallRateWithAPI(ratePlanReader.getFloatCredit(), description, i));
				arrCallRate.add(rowCallRate);
			}

			return arrCallRate;
		} catch (Exception ex) {
			return arrCallRate;
		}
	}

	public List<List<String>> getCurrentCallRateRPWithNoAPI(String description) {
		List<List<String>> arrCallRate = new ArrayList<List<String>>();
		try {
			int totalRowCallRate = getTotalRowCallRateWithDes(description);
			for (int i = 0; i < totalRowCallRate; i++) {
				List<String> rowCallRate = new ArrayList<String>();
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getCallRateCat(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getInitialDuration(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRatePerCost(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRateFixed(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRatePerKB(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRatePerSecond(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRatePer30Sec(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getRatePer60Sec(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getCapFixed(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getCapMax(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getCapRate(), description, i));
				rowCallRate.add(getValueCallRateWithNoAPI(ratePlanReader.getFloatCredit(), description, i));
				arrCallRate.add(rowCallRate);
			}

			return arrCallRate;
		} catch (Exception ex) {
			return arrCallRate;
		}
	}

	public List<List<String>> getCallRateOfRPID(int ratePlanID) {
		List<List<String>> arrCallRate = new ArrayList<List<String>>();
		try {
			String[] webLabel = { ratePlanReader.getCallRateCat(), ratePlanReader.getInitialDuration(),
					ratePlanReader.getRatePerCost(), ratePlanReader.getRateFixed(), ratePlanReader.getRatePerKB(),
					ratePlanReader.getRatePerSecond(), ratePlanReader.getRatePer30Sec(),
					ratePlanReader.getRatePer60Sec(), ratePlanReader.getCapFixed(), ratePlanReader.getCapMax(),
					ratePlanReader.getCapRate(), ratePlanReader.getFloatCredit() };
			int totalRowCallRate = getTotalRowCallRateOfRPID(ratePlanID);
			for (int i = 0; i < totalRowCallRate; i++) {
				List<String> rowCallRate = new ArrayList<String>();
				for (int j = 0; j < webLabel.length; j++) {
					rowCallRate.add(getValueCallRateOfRPID(webLabel[j], ratePlanID, i));
				}
				arrCallRate.add(rowCallRate);
			}
			return arrCallRate;
		} catch (Exception ex) {
			return arrCallRate;
		}
	}

	public String getSummaryInfoWithDes(String field, String des) {
		try {
			Thread.sleep(2000);
			return getEleSummaryInfoViewMode(des, field).getText().trim();
		} catch (Exception ex) {
			return "";
		}
	}

	public String getSummaryInfoViewModeOfRPID(String field, int ratePlanId) {
		try {
			return getEleSummaryInfoViewMode(ratePlanId, field).getText();
		} catch (Exception ex) {
			return "";
		}
	}

	public boolean visibleRevertIconWithDes(String description) {
		try {
			return getWebElementRevertIconWithDes(description).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean seeRevertIconOfRPID(int ratePlanID) {
		try {
			return getEleRevertIconOfRPID(ratePlanID).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean visibleRevertIconWithDes(int indexStartWithOne) {
		try {
			return getWebElementRevertIconWithIndex(indexStartWithOne).isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public void clickRevertIconWithDes(String description) throws InterruptedException, IOException {
		try {
			getWebElementRevertIconWithDes(description).click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeRevertIcon",
					"Don't see the revert icon for the rate plan with description: " + description);
		}
	}

	public void clickRevertIconOfRPID(int ratePlanID) throws InterruptedException, IOException {
		getEleRevertIconOfRPID(ratePlanID).click();

	}

	public boolean yesHighlightedSummaryWithDes(String field, String description)
			throws InterruptedException, IOException {
		try {
			int ratePlanId = Integer.parseInt(getEleRatePlanID(description).getText());
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_HIGHLIGHT);
			return Boolean.valueOf(value);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesHighlightedSummaryOfRPID(String field, int ratePlanId) throws InterruptedException, IOException {
		try {
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_HIGHLIGHT);
			return Boolean.valueOf(value);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesViewHistorySummaryWithDes(String field, String description)
			throws InterruptedException, IOException {
		try {
			int ratePlanId = Integer.parseInt(getEleRatePlanID(description).getText());
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_TOGGLE_HISTORY);
			return Boolean.valueOf(value);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesViewHistorySummaryOfRPID(String field, int ratePlanId) throws InterruptedException, IOException {
		try {
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_TOGGLE_HISTORY);
			return Boolean.valueOf(value);
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesHighlightedCallRateWithDes(String field, String description, int i)
			throws InterruptedException, IOException {
		try {
			return Boolean
					.valueOf(getElementCallRateInfoWithDes(description, field).get(i).getAttribute(DATA_HIGHLIGHT));

		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesHighlightedCallRateOfRPID(String field, int ratePlanId, int i)
			throws InterruptedException, IOException {
		try {
			return Boolean.valueOf(getElementCallRateOfRPID(ratePlanId, field).get(i).getAttribute(DATA_HIGHLIGHT));

		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesViewHistoryCallRateWithDes(String field, String description, int i)
			throws InterruptedException, IOException {
		try {
			return Boolean.valueOf(
					getElementCallRateInfoWithDes(description, field).get(i).getAttribute(DATA_TOGGLE_HISTORY));

		} catch (Exception ex) {
			return false;
		}
	}

	public boolean yesViewHistoryCallRateOfRPID(String field, int ratePlanId, int i)
			throws InterruptedException, IOException {
		try {
			return Boolean
					.valueOf(getElementCallRateOfRPID(ratePlanId, field).get(i).getAttribute(DATA_TOGGLE_HISTORY));

		} catch (Exception ex) {
			return false;
		}
	}

	public String getDataHistorySummaryWithDes(String field, String description)
			throws InterruptedException, IOException {
		try {
			int ratePlanId = Integer.parseInt(getEleRatePlanID(description).getText());
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_HISTORY);
			return value;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public String getDataHistorySummary(String field, int ratePlanId) throws InterruptedException, IOException {
		try {
			String value = getEleSummaryInfoViewMode(ratePlanId, field).getAttribute(DATA_HISTORY);
			return value;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public String getDataHistoryCallRateWithDes(String field, String description, int i)
			throws InterruptedException, IOException {
		try {
			return getElementCallRateInfoWithDes(description, field).get(i).getAttribute(DATA_HISTORY);
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public String getDataHistoryCallRate(String field, int ratePlanId, int i) throws InterruptedException, IOException {
		try {
			return getElementCallRateOfRPID(ratePlanId, field).get(i).getAttribute(DATA_HISTORY);
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public void clickAddRowBtn() throws InterruptedException, IOException {
		try {
			btnAddrow.click();
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "DontSeeBtnAddNew", "Don't see the Add row button");
		}
	}

	public void doubleClickViewRatePlan(String description) {
		WebElement ele = getElementDoubleClickWithDesView(description);
		seleniumHelper.waitClickAble(driver, 30, ele);
		seleniumHelper.doubleClick(driver, ele);
	}

	public void doubleClickRatePlanID(int ratePlanID) {
		WebElement ele = getEleDoubleClickWithRPID(ratePlanID);
		seleniumHelper.waitClickAble(driver, 30, ele);
		seleniumHelper.doubleClick(driver, ele);
	}

	public String getHeaderTitleCSVCallRate(String field) throws InterruptedException, IOException {
		try {
			List<String> listCallRate = Helper.getListCSV("csv Call Rate").get(0);
			if (field.equalsIgnoreCase(ratePlanReader.getCallRateCat())) {
				return listCallRate.get(0);
			} else if (field.equalsIgnoreCase(ratePlanReader.getInitialDuration())) {
				return listCallRate.get(1);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerCost())) {
				return listCallRate.get(2);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRateFixed())) {
				return listCallRate.get(3);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerKB())) {
				return listCallRate.get(4);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerSecond())) {
				return listCallRate.get(5);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer30Sec())) {
				return listCallRate.get(6);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer60Sec())) {
				return listCallRate.get(7);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapFixed())) {
				return listCallRate.get(8);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapMax())) {
				return listCallRate.get(9);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapRate())) {
				return listCallRate.get(10);
			} else if (field.equalsIgnoreCase(ratePlanReader.getFloatCredit())) {
				return listCallRate.get(11);
			} else
				return "";
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CodeError", "Something wrong with csv Call Rate.csv file");
			throw new RuntimeException(ex);
		}
	}

	public String getValueCSVNormalCallRate(String value, String field) throws InterruptedException, IOException {
		try {
			List<String> listCallRate = Helper.getListCSV("csv Call Rate").get(0);
			if (field.equalsIgnoreCase(ratePlanReader.getCallRateCat())) {
				return listCallRate.get(0);
			} else if (field.equalsIgnoreCase(ratePlanReader.getInitialDuration())) {
				return listCallRate.get(1);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerCost())) {
				return listCallRate.get(2);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRateFixed())) {
				return listCallRate.get(3);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerKB())) {
				return listCallRate.get(4);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerSecond())) {
				return listCallRate.get(5);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer30Sec())) {
				return listCallRate.get(6);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer60Sec())) {
				return listCallRate.get(7);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapFixed())) {
				return listCallRate.get(8);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapMax())) {
				return listCallRate.get(9);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapRate())) {
				return listCallRate.get(10);
			} else if (field.equalsIgnoreCase(ratePlanReader.getFloatCredit())) {
				return listCallRate.get(11);
			} else
				return value;
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CodeError", "Something wrong with csv Call Rate.csv file");
			throw new RuntimeException(ex);
		}
	}

	public String getValueCSVLastCallRate(String value, String field) throws InterruptedException, IOException {
		try {
			List<String> listCallRate = Helper.getListCSV("csv Call Rate").get(1);
			if (field.equalsIgnoreCase(ratePlanReader.getCallRateCat())) {
				return listCallRate.get(0);
			} else if (field.equalsIgnoreCase(ratePlanReader.getInitialDuration())) {
				return listCallRate.get(1);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerCost())) {
				return listCallRate.get(2);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRateFixed())) {
				return listCallRate.get(3);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerKB())) {
				return listCallRate.get(4);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePerSecond())) {
				return listCallRate.get(5);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer30Sec())) {
				return listCallRate.get(6);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePer60Sec())) {
				return listCallRate.get(7);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapFixed())) {
				return listCallRate.get(8);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapMax())) {
				return listCallRate.get(9);
			} else if (field.equalsIgnoreCase(ratePlanReader.getCapRate())) {
				return listCallRate.get(10);
			} else if (field.equalsIgnoreCase(ratePlanReader.getFloatCredit())) {
				return listCallRate.get(11);
			} else
				return value;
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CodeError", "Something wrong with csv Call Rate.csv file");
			throw new RuntimeException(ex);
		}
	}

	public String getValueCSVSummaryInfo(String value, String field) throws InterruptedException, IOException {
		try {
			List<String> listCallRate = Helper.getListCSV("csv Summary Info").get(0);
			if (field.equalsIgnoreCase(ratePlanReader.getDescription())) {
				return listCallRate.get(0);
			} else if (field.equalsIgnoreCase(ratePlanReader.getComments())) {
				return listCallRate.get(1);
			} else if (field.equalsIgnoreCase(ratePlanReader.getRatePlanType())) {
				return listCallRate.get(2);
			} else if (field.equalsIgnoreCase(ratePlanReader.getIddReference())) {
				return listCallRate.get(3);
			} else
				return value;
		} catch (Exception ex) {
			seleniumHelper.setError(driver, "CodeError", "Something wrong with csv Summary Info.csv file");
			throw new RuntimeException(ex);
		}
	}

	public String isCSVSummaryInfo(String value, String field) throws InterruptedException, IOException {
		if (value.contains("<CSV Summary Info>")) {
			value = getValueCSVSummaryInfo(value, field);
		}
		return value;
	}

	public String isCSVNormalCallRate(String value, String field) throws InterruptedException, IOException {
		if (value.contains("<CSV Call Rate>")) {
			value = getValueCSVNormalCallRate(value, field);
		}
		return value;
	}

	public String isCSVLastCallRate(String value, String field) throws InterruptedException, IOException {
		if (value.contains("<CSV Call Rate>")) {
			value = getValueCSVLastCallRate(value, field);
		}
		return value;
	}

	public void createRatePlanForAllBureaus() throws InterruptedException, IOException {
		seleniumHelper.waitClickAble(driver, 60, dropboxBureau);
		int lastBureau = getTotalBureauList() - 1;
		Select s = new Select(dropboxBureau);
		for (int i = lastBureau; i >= 0; i--) {
			s.selectByIndex(i);
			btnCreateRatePlan.click();
			txtDesCreateOrCopyDialog.sendKeys("Rate Plan " + i);
			clickOnCreateBtnInCreateDialog("Create");
			if (seeNewRatePlanAtTop("Rate Plan " + i)) {
				Log.info("I created rate plan for bureau: Rate Plan " + i);
			} else
				throw new RuntimeException("Failed to check description existence due to error(s)");
			Thread.sleep(500);
		}
	}
}
