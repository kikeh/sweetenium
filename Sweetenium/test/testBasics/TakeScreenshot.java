package testBasics;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TakeScreenshot {

	public static String captureScreen(Object base, WebDriver driver, String message) {		

		Date date = new Date();        	
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String filedate = hourdateFormat.format(date);
		String filebase = base.getClass().getSimpleName();
		String path;

		try {
			// Screenshot
			driver = new Augmenter().augment(driver);	
			File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String browser = ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
			browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
			path = "./test-output/screenshots/Failed-" + browser + "-" + filebase;
			
			// Text file
			String url = driver.getCurrentUrl();
			PrintWriter writer = new PrintWriter(path + ".txt", "UTF-8");
			writer.println("Test: " + filebase);
			writer.println("Browser: " + browser);
			writer.println("Date: " + filedate);
			writer.println("Error: " + "[Failed : " + message + "]");
			writer.println("Screenshot: Failed-" + browser + "-" + filebase + ".png");
			writer.println("URL: " + url);
			writer.close();

			source.setWritable(true);
			if(source.canWrite()) {
				FileUtils.copyFile(source, new File(path + ".png"));
			}
		}
		catch(IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}
		catch(Exception e) {
			System.out.println("Error: " + e.getStackTrace());
			path = "Fail";
		}
		return path;

	}
}

