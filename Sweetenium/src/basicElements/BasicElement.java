package basicElements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class BasicElement implements Element {

	protected WebElement element = null;
	
	protected String identifier = null;
	protected String identifierValue = null;
	
	protected String TYPE;
	protected List<String> ELEMENT_TAGS = new ArrayList<String>();
	
	public BasicElement(String identifier, String value) {
		this.identifier = identifier;
		this.identifierValue = value;
	}
	
	protected void setUp() {}

	@Override
	public void initialize(WebDriver driver) {
		if(element == null) {
			setElement(getElementIfPresent(driver));
			if(getElement() != null) {
				if(checkElementTag()) {
					System.out.println("Registered " + TYPE + " with text: " + identifierValue);
				}
				else {
					System.out.println(TYPE + " has not any expected tag.");
				}
			}
			else {
				throw new NoSuchElementException("Error: " + TYPE + " '" + this.identifierValue + "' could not be found on the page.");
			}
		}
		else {
			System.out.println("Link '" + this.identifierValue + "' has already been initialized.");
		}
	}

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement e) {
		if(e != null)
			element = e;
	}
	
	private boolean checkElementTag() {
		for(String tag : ELEMENT_TAGS) {
			if(getElement().getTagName().equalsIgnoreCase(tag)) {
				return true;
			}
		}
		return false;
	}

	public WebElement getElementIfPresent(WebDriver driver) {
		WebElement element = null;
		By ByIdentifier = getByIdentifier();
		if(ByIdentifier != null) {
			List<WebElement> search = driver.findElements(ByIdentifier);
			if(!search.isEmpty())
				element = search.get(0);
		}
		return element;
	}
	
	private By getByIdentifier() {
		By by = null;
		if(this.identifier.equalsIgnoreCase(ID))
			by = new By.ById(this.identifierValue);
		else if(this.identifier.equalsIgnoreCase(CLASS))
			by = new By.ByClassName(this.identifierValue);
		else if(this.identifier.equalsIgnoreCase(CSS_SELECTOR))
			by = new By.ByCssSelector(this.identifierValue);
		else if(this.identifier.equalsIgnoreCase(LINK_TEXT))
			by = new By.ByLinkText(this.identifierValue);
		else if(this.identifier.equalsIgnoreCase(NAME_ID))
			by = new By.ByName(this.identifierValue);
		else if(this.identifier.equalsIgnoreCase(TAG))
			by = new By.ByTagName(this.identifierValue);
		return by;
	}
	
	public void click() {
		if(element != null)
			element.click();
	}

	@Override
	public String getType() {
		return TYPE;
	}	
}

