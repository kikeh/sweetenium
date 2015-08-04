package tests;

import org.testng.annotations.Test;

import testBasics.TestBase;

public class LinkTest extends TestBase {
	
  @Test(description="Testing Links")
  public void testLinks() throws InterruptedException {
    // Access main English page
    navigateTo("http://cordis.europa.eu/search/advanced_en");
    
    ExamplePage example = new ExamplePage("Example");
    example.initialize(driver);
    
    example.click("Contact");
    
  }

}
