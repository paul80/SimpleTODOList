package ca.ualberta.ca.assignment1A;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1651488714702366580L;
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
	
	
	public boolean equals(Object compareItem) {
		if(compareItem!=null && compareItem.getClass()==this.getClass()) {
			return this.equals((Item) compareItem);
		} 
		else {
			return false;
		}
	}
	public boolean equals(Item compareItem) {
		if (compareItem==null){
			return false;
		}
		return getName().equals(compareItem.getName());
	}
	
	public int hashCode() {
		return ("Item:"+getName()).hashCode();
	}
}
