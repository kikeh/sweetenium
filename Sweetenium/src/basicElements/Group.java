package basicElements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class Group implements Element {

	List<Element> elements = new ArrayList<Element>();
	private String TYPE;
	private String NAME;
	
	public Group(String name) {
		TYPE = "Group";
		NAME = name;
	}
	
	public Group(String name, Element... elements) {
		this(name);
		for(Element element : elements) {
			this.elements.add(element);
		}
	}
	
	public Group(String name, List<Element> elements) {
		this(name);
		if(!elements.isEmpty())
			this.elements = elements;
	}
	
	public void addElementToGroup(Element element) {
		if(elementIsMyself(element)) {
			System.out.println("Warning: Group cannot contain itself.");
		}
		else if(elements.contains(element)) {
			System.out.println("Warning: " + element.getType() + " is already in the group.");
		}
		else {
			elements.add(element);
		}
	}

	public void addElementsToGroup(List<Element> elements) {
		for(Element e : elements) {
			addElementToGroup(e);
		}
	}
	
	public void initialize(WebDriver driver) {
		if(!elements.isEmpty()) {
			for(Element e : elements) {
				e.initialize(driver);
			}
			System.out.println(NAME + ": All elements have been initialized.");
		}
		else {
			System.out.println("No elements in group to be initialized.");
		}
	}
	
	private boolean elementIsMyself(Element element) {
		return this == element;
	}
	
	public void printElementsList() {
		System.out.println("Group: " + NAME);
		System.out.println("  Elements: " + elements.size());
		for(Element element : elements) {
			if(element.getType().equalsIgnoreCase("group"))
				((Group) element).printElementsList();
			else
				System.out.println("    " + element.getType() + ": ...");
		}
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public String getName() {
		return NAME;
	}
	
	public List<Element> getElements() {
		return elements;
	}

}
