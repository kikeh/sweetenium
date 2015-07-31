package basicElements;

public class Link extends BasicElement {
	
	public Link(String text) {
		super(LINK_TEXT, text);
		setUp();
	}
	
	public Link(String identifier, String value) {
		super(identifier, value);
		setUp();
	}
	
	public void setUp() {
		this.TYPE = "Link";
		this.ELEMENT_TAGS.add("a");
	}
	
}