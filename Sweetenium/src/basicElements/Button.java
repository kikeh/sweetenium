package basicElements;

public class Button extends BasicElement {

	public Button(String identifier, String value) {
		super(identifier, value);
		setUp();
	}
	
	public void setUp() {
		this.TYPE = "Button";
		this.ELEMENT_TAGS.add("button");
		this.ELEMENT_TAGS.add("input");
	}
	
}

