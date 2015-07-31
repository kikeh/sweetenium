package testBasics;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

public class TestBase {

  final public boolean PASSED = true;
  final public boolean FAILED = false;
	
  public WebDriver driver = null;
  
  public String lot4 = null;
  
  public int TIMEOUT = 10;

  @Parameters({ "browser", "lot" })
  @BeforeClass(alwaysRun = true)
  public void setup(String browser, String lot) throws MalformedURLException {

    System.out.println();
    System.out.println("[Test case: " + this.getClass().getSimpleName() + "]");
    System.out.println("[Browser  : " + browser         + "]");

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setPlatform(Platform.LINUX);
    lot4 = lot;

    // Browsers
    if (browser.equalsIgnoreCase("explorer"))
      caps = DesiredCapabilities.internetExplorer();

    if (browser.equalsIgnoreCase("firefox")) {
      caps = DesiredCapabilities.firefox();
      FirefoxProfile profile = new FirefoxProfile();
      profile.setPreference("dom.enable_resource_timing",true);
      caps.setCapability(FirefoxDriver.PROFILE, profile);
    }

    if (browser.equalsIgnoreCase("chrome"))
      caps = DesiredCapabilities.chrome();

    if (browser.equalsIgnoreCase("ipad"))
      caps = DesiredCapabilities.ipad();

    if (browser.equalsIgnoreCase("android"))
      caps = DesiredCapabilities.android();

    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps);

    this.setTimeout(TIMEOUT);
  }

  @AfterClass
  public void afterClass() {
    driver.quit();
  }
	
  public void setTimeout(int seconds) {
	  driver.manage().timeouts().implicitlyWait(seconds + 10, TimeUnit.SECONDS);
	  driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
  }
  
  public void navigateTo(String url) {
	  try {
		  if(lot4.compareTo("4") == 0) {
			  url = url.replaceAll("http://", "http://ppe-lot4.");
		  }
		  driver.get(url);
	  } catch (TimeoutException e) {
		  result(false,"","Request timeout");
	  }
	  // ((JavascriptExecutor) driver).executeScript("var resources = window.performance.getEntriesByType('resource');var responseTime = resources[0].responseStart - resources[0].fetchStart;var donwloadTime = resources[0].responseEnd - resources[0].responseStart; if ((resources[0].domainLookupStart - resources[0].fetchStart) == 0) cache ='0'; else cache='1'; dcsMultiTrack('WT.dl','21','WT.ctime',cache,'WT.rtime',responseTime,'WT.dtime',donwloadTime);");
  }
  
  public void result(boolean result, String passedMessage, String failedMessage) {
    if(result) {
      System.out.println("[Passed   : " + passedMessage + "]");
      assertTrue(passedMessage, result);
	}
    else {
      System.out.println("[Failed   : " + failedMessage + "]");
      TakeScreenshot.captureScreen(this, driver, failedMessage);
      assertTrue(failedMessage, result);
    }
  }
	
  public void result(String s1, String s2, String passedMessage, String failedMessage) {
    boolean res = (s1.compareTo(s2) == 0);
    result(res,passedMessage, failedMessage);
  }

  public void printInfo(String info) {
    System.out.println("  [Info   : " + info + "]" );
  }
  
  public void printWarning(String info) {
	  System.out.println("  [Warning: " + info + "]" );
  }	
  
  public List<WebElement> getPageList(WebDriver driver) {
	  return driver.findElements(By.xpath("//div[@id='pagelist']/*"));
  }
  
  public List<WebElement> getResults(WebDriver driver) {
	  return driver.findElements(By.cssSelector("div.matchlist > div"));
  }
  
  /* Auxiliary functions for searching tests */
  
  public List<String> getTags(List<WebElement> list) {
	  List<String> tags = new ArrayList<String>();
	  if(list != null) {
		  for(WebElement e : list) {
			  tags.add(e.getTagName());
		  }
	  }
	  return tags;
  }
  
  public int numberOfResults(WebDriver driver) {
	  int results = 0;
	  String[] matches = BrowserFixes.findElement(driver, By.cssSelector("div.matches")).getText().split(" ");
	  results = Integer.parseInt( (matches[5].split("\\r?\\n"))[0] );
	  return results;
  }
  
  public int numberOfPages(int number) {
	  // Number of pages based on resultPerPage
	  double resultsPerPage = 10.0;
	  if (number > resultsPerPage)
	  	return (int) Math.ceil(number/resultsPerPage);
	  else
		return 1;
  }
  
  public boolean isMultiPage(List<String> tags) {
	  return (tags.size() > 1);
  }
  
  public boolean isLastPage(List<String> tags) {
	  return ( tags.get(tags.size() - 1).compareTo("span") == 0 );
  }
  
  public boolean isFullPage(List<WebElement> results, int resultsPerPage) {
	  return ( results.size() == resultsPerPage );
  }
  
  public boolean pageIsEmpty(List<WebElement> results) {
	  return ( results.get(0).getAttribute("class").compareTo("updated") == 0 );
  }
  
  public ArrayList<Boolean> initializeMatchesList(List<String> expectedElements) {
	  ArrayList<Boolean> result = new ArrayList<Boolean>();
	  for(int i = 0; i < expectedElements.size(); i++) {
		  result.add(false);
	  }
	  return result;
  }
  
  public boolean expectedElementsMatchesFound(List<Boolean> matches) {
	  for(boolean match : matches)
		  if(!match) return false;
	  return true;
  }
  
  public boolean searchExpectedElements(List<WebElement> results, List<String> expectedElements, List<Boolean> expectedElementsMatches) {
	  for(WebElement element : results) {
		  String entryName = element.findElement(By.className("contenttype")).getText() + " " + element.findElement(By.tagName("a")).getText();

		  for(String expectedElement : expectedElements) {
			  if(entryName.compareTo(expectedElement) == 0) {
				  expectedElementsMatches.set(expectedElements.indexOf(expectedElement), true);
				  result(true,"found: '" + expectedElement + "'", "");
			  }
		  }
	  }
	  return expectedElementsMatchesFound(expectedElementsMatches);
  }
  
  public WebElement getExpectedElement(List<WebElement> results, String expectedElement) {
	  WebElement project = null;
	  for(WebElement element : results) {
		  String entryName = element.findElement(By.className("contenttype")).getText() + " " + element.findElement(By.tagName("a")).getText();
		  if(entryName.compareTo(expectedElement) == 0) {
			  project = element;
			  result(true,"found: '" + expectedElement + "'", "");
		  }
	  }
	  return project;
  }
  
  public void goToNextPage(WebDriver driver, int nextPage) {
	  setTimeout(5);
	  BrowserFixes.clickElement(driver, driver.findElement(By.linkText(Integer.toString(nextPage))));
	  setTimeout(TIMEOUT);
  }
  
  public boolean searchReturnsAllExpectedElements(WebDriver driver, List<String> expectedElements) {
	  boolean expectedElementsFound = false;
	  
	  int numberOfPages = numberOfPages(numberOfResults(driver));
	  int actualPage = 1;
	  
	  List<String> tags = new ArrayList<String>();
	  List<WebElement> results = new ArrayList<WebElement>();
	  
	  List<Boolean> expectedElementsMatches = initializeMatchesList(expectedElements);
	  
	  printInfo("Starting search for " + expectedElements.size() + " expected elements");
	  
	  while(!expectedElementsFound && (actualPage <= numberOfPages) ) {
		  printInfo("Page " + actualPage + "/" + numberOfPages);
		  tags = getTags(getPageList(driver));
		  results = getResults(driver);

		  if(pageIsEmpty(results)) {
			  printWarning("showing page with no results");
			  actualPage = numberOfPages + 1;
		  }
		  else {
			  actualPage = actualPage + 1;
			  expectedElementsFound = searchExpectedElements(results, expectedElements, expectedElementsMatches);
			  if(!isLastPage(tags)) {
				  if(!isFullPage(results, 10))
					  printWarning("results are not shown properly");				  
				  goToNextPage(driver, actualPage);
			  }
		  }
	  }
	  
	  return expectedElementsFound;
  }
  
  public boolean searchAndAccessElement(WebDriver driver, String expectedElement) {
	  int numberOfPages = numberOfPages(numberOfResults(driver));
	  int actualPage = 1;
	  
	  List<String> tags = new ArrayList<String>();
	  List<WebElement> results = new ArrayList<WebElement>();
	  WebElement project = null;
	  
	  boolean expectedElementsFound = false;
	  
	  while(!expectedElementsFound && (actualPage <= numberOfPages) ) {
		  printInfo("Page " + actualPage + "/" + numberOfPages);
		  tags = getTags(getPageList(driver));
		  results = getResults(driver);

		  if(pageIsEmpty(results)) {
			  printWarning("showing page with no results");
			  actualPage = numberOfPages + 1;
		  }
		  else {
			  actualPage = actualPage + 1;
			  project = getExpectedElement(results, expectedElement);
			  if(project != null) {
				  BrowserFixes.clickElement(driver, project.findElement(By.tagName("a")));
				  expectedElementsFound = true;
			  }
			  else {
				  if(!isLastPage(tags)) {
					  if(isFullPage(results, 10))
						  printWarning("results are not shown properly");					  
					  goToNextPage(driver, actualPage);
				  }
			  }
		  }
	  }
	  
	  return expectedElementsFound;
  }
  
  /* Timestamp */
  
  public String responseLiteral(long diff) {
	  String result = "";
	  if(diff < 60000) {
		  result = ((double)diff / 1000) + " s";
	  }
	  else { // if(diff >= 60000) {
		  result = ((double)diff / 60000) + " m";
	  }
	  return result;
  }	
  
  public void responseTime(Date click) {
	  Date endTime = new Date();
	  long diff = (endTime.getTime() - click.getTime());
	  String response = this.responseLiteral(diff);
	  System.out.println("    [Load time: " + response + "]");
  }
  
  /* Clicking */
  
  public WebElement findElement(By path) {
	  // Set timeout for not having to wait too long for a click
	  this.setTimeout(2);
	  
	  WebElement result = null;
	  try {
		  result = driver.findElement(path);
	  } catch(NoSuchElementException e) {
		  result(false,"","Could not find the element (" + path.toString() + ")");
	  }
	  
	  // Set timeout back to its default value
	  this.setTimeout(TIMEOUT);
	  
	  return result;
  }
  
  public boolean elementContainsError(String errorMessage) {
	  boolean result = false;
	  
	  this.setTimeout(2);
	  
	  try {
		  if(driver.findElement(By.xpath("//*[contains(.,'" + errorMessage + "')]")) != null);
			  result = true;
	  } catch(NoSuchElementException e) {
		  // If no error is found, result stays false
	  }
	  
	  // Set timeout back to its default value
	  this.setTimeout(TIMEOUT);
	  
	  return result;
  }

  public void clickElement(By path) {
	  
	  // Start time
	  // Date startTime = new Date();
	  
	  WebElement element = findElement(path);
	  
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
		  // this.responseTime(startTime);
		  result(false,"","Request timeout");
	  }

//	  try {
//		  Thread.sleep(2000);
//	  } catch (InterruptedException e) {
//		  e.printStackTrace();
//	  }

	  if (cp.getBrowserName().equals("explorer")) {
		  try {
			  Thread.sleep(3000);
		  } catch (InterruptedException e) {
			  // this.responseTime(startTime);
			  e.printStackTrace();
		  }
	  }
	  
	  // Response time
	  ((JavascriptExecutor) driver).executeScript("var resources = window.performance.getEntriesByType('resource');var responseTime = resources[0].responseStart - resources[0].fetchStart;var donwloadTime = resources[0].responseEnd - resources[0].responseStart; if ((resources[0].domainLookupStart - resources[0].fetchStart) == 0) cache ='0'; else cache='1'; dcsMultiTrack('WT.dl','21','WT.ctime',cache,'WT.rtime',responseTime,'WT.dtime',donwloadTime);");
	  // this.responseTime(startTime);
  }

}
