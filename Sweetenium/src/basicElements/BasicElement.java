package basicElements;

import org.openqa.selenium.WebDriver;

public interface BasicElement {
	
	public String ID = "id";
	public String CLASS = "class";
	public String CSS_SELECTOR = "css_selector";
	public String LINK_TEXT = "link_text";
	public String NAME_ID = "name";
	public String TAG = "tag";
	
	public void initialize(WebDriver driver);
	
	public String getType();
	
}
