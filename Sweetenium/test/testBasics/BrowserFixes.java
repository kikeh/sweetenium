package testBasics;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.*;

public class BrowserFixes {

	static public WebElement findElement(WebDriver driver, By path) {
		WebElement result = null;
		try {
			result = driver.findElement(path);
		} catch(NoSuchElementException e) {
			String message = "Could not find the element (" + path.toString() + ")";
			System.out.println("[Failed   : " + message + "]");
	    	assertTrue(message, false);
		}
		return result;
	}
	
	static public void clickElement(WebDriver driver, WebElement element) {		
		Capabilities cp = ((RemoteWebDriver) driver).getCapabilities();
	    if (cp.getBrowserName().equals("chrome")) {
	        try {
	            ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView(true);", element);
	        } catch (Exception e) {
	        	// Error
	        }
	    }

	    System.out.println("  [Info   : clicking '" + element.toString() + "']");
	    try {
	    	element.click();
	    } catch (TimeoutException e) {
	    	String message = "Request timeout";
	    	System.out.println("[Failed   : " + message + "]");
	    	assertTrue(message, false);
	    }

    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
	    if (cp.getBrowserName().equals("explorer")) {
	    	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	}
	
}
