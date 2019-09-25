package managers;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import pageObjects.*;

public class PageObjectManager {
	private WebDriver driver;
	private LoginPage loginPage;
	private HomePage homePage;
	private LogoutPage logoutPage;
	private RatePlanPage ratePlanPage;
	private RatePlanAdminPage ratePlanAdminPage;

	
	public PageObjectManager(WebDriver driver){
		this.driver = driver;
	}
	
	public LoginPage getLoginPage(){
		return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
	}
	public LogoutPage getLogoutPage(){
		return (logoutPage == null) ? logoutPage = new LogoutPage(driver) : logoutPage;
	}
	public HomePage getHomePage(){
		return (homePage == null) ? homePage = new HomePage(driver) : homePage;
	}
	public RatePlanPage getRatePlanPage() throws IOException{
		return (ratePlanPage == null) ? ratePlanPage = new RatePlanPage(driver) : ratePlanPage;
	}

	public RatePlanAdminPage getRatePlanAdminPage() throws IOException{
		return (ratePlanAdminPage == null) ? ratePlanAdminPage = new RatePlanAdminPage(driver) : ratePlanAdminPage;
	}

}
