package basicElements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class Page {

	List<Group> groups = new ArrayList<Group>();
	List<Link> links = new ArrayList<Link>();
	
	private String TYPE;
	private String NAME;
	private WebDriver driver;
	
	public Page(String name) {
		TYPE = "Page";
		NAME = name;
	}
	
	public Page(String name, Group... groups) {
		this(name);
		for(Group group : groups) {
			this.groups.add(group);
		}
	}
	
	public Page(String name, List<Group> groups) {
		this(name);
		if(!groups.isEmpty())
			this.groups = groups;
	}
	
	public void addGroupToPage(Group group) {
		if(groups.contains(group)) {
			System.out.println("Warning: " + group.getName() + " is already in the group.");
		}
		else {
			groups.add(group);
		}
	}

	public void addGroupsToPage(List<Group> groups) {
		for(Group g : groups) {
			addGroupToPage(g);
		}
	}
	
	public void initialize(WebDriver driver) {
		this.driver = driver;
		initializeGroups();
		createBasicElementsLists();
	}

	private void initializeGroups() {
		if(!groups.isEmpty()) {
			for(Group g : groups) {
				g.initialize(driver);
			}
			System.out.println(NAME + ": All elements have been initialized.");
		}
		else {
			System.out.println("No elements in page to be initialized.");
		}
	}
	
	private void createBasicElementsLists() {
		for(Group group : groups) {
			classifyElementsInGroup(group);
		}
	}

	private void classifyElementsInGroup(Group group) {
		for(Element element : group.getElements()) {
			if(element.getType().equalsIgnoreCase("group")) {
				classifyElementsInGroup((Group)element);
			}
			else {
				classifyElement(element);
			}
		}
	}
	
	private void classifyElement(Element element) {
		if(element.getType().equalsIgnoreCase("link")) {
			this.links.add((Link)element);
		}
	}
	
//	public void printElementsList() {
//		System.out.println("Page: " + NAME);
//		System.out.println("  Groups: " + groups.size());
//		for(Group group : groups) {
//			group.printElementsList();
//		}
//	}

	public String getType() {
		return TYPE;
	}

	public String getName() {
		return NAME;
	}

}
