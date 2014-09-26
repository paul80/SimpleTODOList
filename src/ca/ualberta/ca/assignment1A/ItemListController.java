package ca.ualberta.ca.assignment1A;

import java.io.IOException;

//Controller to control data flow for the TODO list between multiple views
//Singleton
public class ItemListController {
	private static ItemList itemList= null;
	static public ItemList getItemList() {
		if(itemList== null) {
			try {
				itemList=ItemListManager.getManager().loadItemList();
				itemList.addListener(new Listener() {
					
					@Override
					public void update() {
						saveItemList();
						
					}
				});
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize item list from item list manager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize item list from item list manager");

			}
		}
		return itemList;
		
	}
	
	static public void saveItemList() {
		try {
			ItemListManager.getManager().saveItemList(getItemList());

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not deserialize item list from item list manager");

		}
	}
	
	
	
	public void add(Item item) {
		getItemList().add(item);
		
	}
	

}
