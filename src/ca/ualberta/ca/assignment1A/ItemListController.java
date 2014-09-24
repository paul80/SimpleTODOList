package ca.ualberta.ca.assignment1A;

//Singleton
public class ItemListController {
	private static ItemList itemList= null;
	static public ItemList getItemList() {
		if(itemList== null) {
			itemList= new ItemList();
		}
		return itemList;
		
	}
	public void add(Item item) {
		// TODO Auto-generated method stub
		getItemList().add(item);
		
	}
	

}
