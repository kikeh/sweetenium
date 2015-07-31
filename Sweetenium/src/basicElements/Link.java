package basicElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class Link implements BasicElement {

	WebElement element = null;
	
	String identifier = null;
	String identifierValue = null;
	
	private String TYPE;
	public static final String ELEMENT_TAG = "a";
	
	private Link() {
		this.TYPE = "Link";
	}
	
	public Link(String text) {
		this();
		this.identifier = LINK_TEXT;
		this.identifierValue = text;
	}
	
	public Link(String identifier, String value) {
		this();
		this.identifier = identifier;
		this.identifierValue = value;
	}

	@Override
	public void initialize(WebDriver driver) {
		if(element == null) {
			setElement(getElementIfPresent(driver));
			if(getElement() != null) {
				if(checkElementTag()) {
					System.out.println("Registered link with text: " + identifierValue);
				}
				else {
					System.out.println("Link has not the tag: " + ELEMENT_TAG);
				}
			}
			else {
				throw new NoSuchElementException("Error: Link '" + this.identifierValue + "' could not be found on the page.");
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
		return getElement().getTagName().equalsIgnoreCase(ELEMENT_TAG);
	}

	public WebElement getElementIfPresent(WebDriver driver) {
		WebElement element = null;
		List<WebElement> search = driver.findElements(By.linkText(this.identifierValue));
		if(!search.isEmpty())
			element = search.get(0);
		return element;
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
