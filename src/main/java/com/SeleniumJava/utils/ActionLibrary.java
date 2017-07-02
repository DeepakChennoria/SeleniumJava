package com.SeleniumJava.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SeleniumJava.base.Base;
import com.google.common.base.Verify;

/**
 * @author dpk
 *
 */
/**
 * @author dpk
 *
 */
public class ActionLibrary {

	protected static WebDriver driver;
	private WebElement element;
	private By by;
	Base driverObject;
	protected WebDriverWait wait;
	
	Long maxWaitTimeToFindElement = Long.valueOf(ConfigReader.getProperty("maxWaitTimeToFindElement"));
	Long maxWaitTimeToPOLLElement = Long.valueOf(ConfigReader.getProperty("maxWaitTimeToPOLLElement"));

	public ActionLibrary(WebDriver driver)
	{
		this.driver = driver;
	}
	public ActionLibrary()
	{
	}

	public WebElement waitAndFindElement() {

		element = new WebDriverWait(driver, maxWaitTimeToFindElement)
				.ignoring(NotFoundException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(IndexOutOfBoundsException.class)
				.ignoring(WebDriverException.class)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(by);
					}

					@Override
					public String toString() {
						return " - searched for element located by " + by;
					}
				});

		return element;
	}
	
	public boolean isElementPresent(By by) {
		try {

			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement waitForElement(final By by,int timeOutInSeconds) {
		this.by=by;
		element = new FluentWait<WebDriver>(this.driver)
				.withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(maxWaitTimeToPOLLElement, TimeUnit.SECONDS)
				.ignoring(NotFoundException.class)
           		.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(by);
					}

					@Override
					public String toString() {
						return " - waiting for element located by " + by;
					}
				});
		return element;
	}
	
	public static void waitForAjax() {
	    new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            return (Boolean) js.executeScript("return jQuery.active == 0");
	        }
	    });
	}

	public void click(By by) {
		this.by = by;
		waitAndFindElement().click();
	}

	public void sendKeys(By by, String value) {
		this.by = by;
		clear(by);
		waitAndFindElement().sendKeys(value);
	}
	
	public String getAttribute(By by, String attributeName) {
		this.by = by;
		return waitAndFindElement().getAttribute(attributeName);
	}
	
	public String  getText(By by) {
		this.by = by;
		return waitAndFindElement().getText();
	}
	
	
	public void  clear(By by) {
		this.by = by;
		waitAndFindElement().clear();
	}
	
	public void  selectFromDropDownByValue(final By by, String value) {
		this.by = by;
		
		final Select droplist = new Select(driver.findElement(by));
		element = new FluentWait<WebDriver>(this.driver)
				.withTimeout(maxWaitTimeToFindElement, TimeUnit.SECONDS)
				.pollingEvery(maxWaitTimeToPOLLElement, TimeUnit.SECONDS)
				.ignoring(NotFoundException.class)
           		.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						boolean temp=!droplist.getOptions().isEmpty();
						return driver.findElement(by);
					}

					@Override
					public String toString() {
						return " - waiting for element located by " + by;
					}
				});
		
		Select dropDown = new Select(element);
		dropDown.selectByValue(value);
	}
	
	public void  selectFromDropDownByText(By by, String text) {
		this.by = by;
		Select dropDown = new Select(waitAndFindElement());
		dropDown.selectByVisibleText(text);
	}
	
	public void  switchToIframe(String frameName) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeToFindElement);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.xpath("//iframe[contains(@src,'"+frameName+"')]"))));
		driver.switchTo().frame(frameName);
	}
	
	public boolean isDisplayed(By by) {
		this.by=by;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(maxWaitTimeToFindElement, TimeUnit.SECONDS)
					.pollingEvery(maxWaitTimeToPOLLElement, TimeUnit.SECONDS);
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(by));
			return element.isDisplayed() ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isEnabled(By by) {
		this.by=by;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(maxWaitTimeToFindElement, TimeUnit.SECONDS)
					.pollingEvery(maxWaitTimeToPOLLElement, TimeUnit.SECONDS);
			element = wait.until(ExpectedConditions.elementToBeClickable(by));
			return element.isEnabled() ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Get webdriver
	 * @return
	 * 
	 * */
	public final WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * Get error logs and return error message
	 * @return
	 * 
	 *
	 * */

	public final String getErrorLog( String errorMessage  ){
		return "<br/> <b> <font color='Red' style='background-color: white;'> [" + errorMessage + "]</font> </b>" ;
	}

	/**
	 * Get warning logs and return warning message
	 * @return
	 * 
	 *
	 * */
	public final String getWarningLog( String warningMessage ){
		return "<br/> <b> <font color='Yellow' style='background-color: gray;'> [" + warningMessage + "]</font> </b>" ;
	}
	/**
	 * Pause the driver for required time
	 * @param time
	 *
	 * 
	 *
	 * */
	public final void pause(int seconds){
		pauseMilis(seconds*500);  
	}

	/**
	 * pause milisecond 
	 * 
	 * @param locator, value
	 */

	public final void pauseMilis(long miliSeconds){
		try { 
			Thread.sleep(miliSeconds); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


public void switchToFrame(String _frameText){
		
		wait = new WebDriverWait(driver, 10); 
	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_frameText)); 
	
	}


/**
 * Method wait for max time and pause 1 second if element not found
 * @param loc : Locator of Identified object
 * @param wait : Max wait time for element
 * @return : boolean
 */
public boolean dynamicPauseForElement(By loc, int wait)
{
	boolean present =false;
	for(int i=0;i<=wait;i++)
	try {
		pause(1);
	   driver.findElement(loc);
	   present = true;
	   
	} catch (NoSuchElementException e) {
		if(i>wait){
			e.getStackTrace();
		}
			
	  
	} 
	return present;
	}
	
public void navigateToURL(String url){
	driver.get(url);
}

/*public void  verify(){
	driver.	
}*/

}
