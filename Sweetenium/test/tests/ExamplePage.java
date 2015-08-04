package tests;

import basicElements.Button;
import basicElements.Element;
import basicElements.Group;
import basicElements.Link;
import basicElements.Page;

public class ExamplePage extends Page {

	public ExamplePage(String name) {
		super(name);

		Link aboutCordis = new Link("About CORDIS");
		Link advancedSearch = new Link("Advanced Search");
		Link contact = new Link("Contact");
		Link legalNotice = new Link("Legal Notice");
		Group headerLinks = new Group("Header Links", aboutCordis, advancedSearch, contact, legalNotice);

		Button searchProject = new Button(Element.NAME_ID, "search");
		Group searchForm = new Group("Search Form", searchProject);

		//	    Link home = new Link("Home");
		//	    Link newsAndEvents = new Link("News and Events");
		//	    Link programmes = new Link("Programmes");
		//	    Link projectsAndResults = new Link("Projects and Results");
		//	    Link topStories = new Link("Top Stories");
		//	    Link researchEUMagazines = new Link("research*eu magazines");
		//	    Link researchPartners = new Link("Research Partners");
		//	    Link nationalAndRegional = new Link("National and Regional");
		//	    Group mainMenuLinks = new Group("Main Menu Links",
		//	    		                         home,
		//	    		                         newsAndEvents,
		//	    		                         programmes,
		//	    		                         projectsAndResults,
		//	    		                         topStories,
		//	    		                         researchEUMagazines,
		//	    		                         researchPartners,
		//	    		                         nationalAndRegional);
		//	
		//	    Group header = new Group("Header", headerLinks, mainMenuLinks);

		this.addGroupToPage(headerLinks);
		this.addGroupToPage(searchForm);
	}

}
