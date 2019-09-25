package selenium;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.cucumber.listener.Reporter;
import managers.FileReaderManager;
import utilities.Log;

public class seleniumHelper {
	public static String capture(WebDriver driver, String screenShotName) throws InterruptedException, IOException {
		String classPath = System.getProperty("user.dir")
				+ FileReaderManager.getInstance().getConfigReader().getClassPath();
		driver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dest = classPath + "/ReportForEmail/ErrorScreenshots/" + screenShotName + ".png";
		FileUtils.copyFile(scrFile, new File(dest));
		return classPath + "ErrorScreenshots/" + screenShotName + ".png";
	}

	public static void setError(WebDriver driver, String nameScreenShot, String messageOfError)
			throws InterruptedException, IOException {
		String screenShotPath;
		try {
			screenShotPath = capture(driver, nameScreenShot);
		} catch (Exception ex) {
			screenShotPath = capture(driver, "error");
		}
		Reporter.addScreenCaptureFromPath(screenShotPath);
		Log.error(messageOfError);
		Assert.fail(messageOfError);
	}

	public static void waitElementVisible(WebDriver driver, int timeoutSecond, String stringXpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutSecond);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(stringXpath)));
		} catch (Exception ex) {

		}
	}

	public static void waitForPageLoaded(WebDriver driver) throws InterruptedException, IOException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			seleniumHelper.setError(driver, "PageNotCompleted", "Timeout waiting for Page Load Request to complete.");
		}
	}

	public static void waitClickAble(WebDriver driver, int timeoutSecond, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutSecond);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception ex) {

		}
	}

	public static void waitLocated(WebDriver driver, int timeoutSecond, String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutSecond);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		} catch (Exception ex) {

		}
	}

	public static void doubleClick(WebDriver driver, WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).doubleClick().perform();
	}

	public static void scrollDownToPixel(WebDriver driver, int pixel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixel + ")");
	}

	public static void scrollDownToElement(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	public static void scrollDownToBottom(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	}
	
	public static void f5KeyBoard(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public static void sendKey(WebElement ele, String value) throws InterruptedException{
		ele.clear();
		Thread.sleep(500);
		ele.sendKeys(value);
	}

}
