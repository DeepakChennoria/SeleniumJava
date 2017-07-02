package com.SeleniumJava.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.SeleniumJava.utils.ConfigReader;

public class Base{
	
	public static WebDriver driver;
	private long maxWaitTimeToPOLLElement;
	private String browserName;

	public static Properties config = new Properties();
	private String applicationURL;
	private String userName;
	private String key;
	private String browserVersion;
	private String os;
	

	@BeforeTest
	@Parameters("Env")
	protected void initiateDriver(@Optional("local") String Env) throws Exception {
		applicationURL = ConfigReader.getProperty("applicationURL");
		if(Env.equalsIgnoreCase("sauce")){
			browserName = ConfigReader.getProperty("browserName");
			browserVersion = ConfigReader.getProperty("browserVersion");
			os = ConfigReader.getProperty("os");
			maxWaitTimeToPOLLElement = Long.valueOf(ConfigReader
					.getProperty("maxWaitTimeToPOLLElement"));
			applicationURL = ConfigReader.getProperty("applicationURL");
			createSauceDriver();
			
		}
		else{
			
		
		browserName = ConfigReader.getProperty("browserName");
		maxWaitTimeToPOLLElement = Long.valueOf(ConfigReader
				.getProperty("maxWaitTimeToPOLLElement"));
		

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
		capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);

		if (browserName != null && browserName.equalsIgnoreCase("Firefox")) {

			capabilities.setBrowserName("Firefox");
			System.out.println("Initiating " + browserName + " driver");
			driver = new FirefoxDriver(capabilities);

		} else if (browserName != null && browserName.equalsIgnoreCase("IE")) {

			System.setProperty(
					"webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "/src/main/resources/drivers/IE_32bit/IEDriverServer.exe");
			capabilities = DesiredCapabilities.internetExplorer();
			driver = new InternetExplorerDriver();

		} else {

			throw new Exception("Browser name should be 'IE' or 'Firefox'");
		}

		}
		driver.manage().deleteAllCookies();

		driver.navigate().to(applicationURL);

		driver.manage().window().maximize();
	}

	@AfterTest(alwaysRun=true)
	protected void tearDown() {
		driver.quit();

	}

	
	public static WebDriver getDriverInstance(){
		return  driver;
	}


	/**
	 * URL for Sauce Labs
	 */
	public static final String URL = "http://" + ConfigReader.getProperty("username") + ":" + ConfigReader.getProperty("key") + "@ondemand.saucelabs.com:80/wd/hub";

	
	/**
	 * Execute a simple test on Sauce Labs
	 * @param args
	 * @throws MalformedURLException 
	 * @throws Exception
	 */
	public void createSauceDriver() throws MalformedURLException, UnexpectedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(CapabilityType.BROWSER_NAME, ConfigReader.getProperty("browserName"));
        capabilities.setCapability(CapabilityType.VERSION, ConfigReader.getProperty("browserVersion"));
        capabilities.setCapability(CapabilityType.PLATFORM, ConfigReader.getProperty("os"));
		driver = new RemoteWebDriver(new URL(URL), capabilities);

	}
}
