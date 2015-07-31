package tests;

import org.testng.annotations.Test;

import basicElements.Page;
import basicElements.Group;
import basicElements.Link;
import testBasics.TestBase;

public class LinkTest extends TestBase {
	
  @Test(description="Testing Links")
  public void testLinks() throws InterruptedException {
    // Access main English page
    navigateTo("http://cordis.europa.eu/search/advanced_en");
    
    Link aboutCordis = new Link("About CORDIS");
    Link advancedSearch = new Link("Advanced Search");
    Link contact = new Link("Contact");
    Link legalNotice = new Link("Legal Notice");
    Group headerLinks = new Group("Header Links", aboutCordis, advancedSearch, contact, legalNotice);
    
    Link home = new Link("Home");
    Link newsAndEvents = new Link("News and Events");
    Link programmes = new Link("Programmes");
    Link projectsAndResults = new Link("Projects and Results");
    Link topStories = new Link("Top Stories");
    Link researchEUMagazines = new Link("research*eu magazines");
    Link researchPartners = new Link("Research Partners");
    Link nationalAndRegional = new Link("National and Regional");
    Group mainMenuLinks = new Group("Main Menu Links",
    		                         home,
    		                         newsAndEvents,
    		                         programmes,
    		                         projectsAndResults,
    		                         topStories,
    		                         researchEUMagazines,
    		                         researchPartners,
    		                         nationalAndRegional);

    Group header = new Group("Header", headerLinks, mainMenuLinks);
    
    Page cordis = new Page("Cordis Main Page", header);
    
    cordis.initialize(driver);
    
  }

}
