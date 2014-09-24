package ca.ualberta.ca.assignment1A;

public class Item {
	protected String itemName;
	
	//Constructor
	public Item(String itemName) {
		this.itemName=itemName;
	}

	public String getName() {
		
		return this.itemName;
	}
	public String toString() {
		return getName();
	
	}
	
	//Might work? - Get length of the item/string
	public int count() {
		return this.count();
	}
	
	//Might work as well- Get the character at what index of the item
	public char charAt(int index) {
		return this.charAt(index);
	}
}
