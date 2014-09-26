package ca.ualberta.ca.assignment1A;

import java.io.IOException;

//Singleton
public class ItemListController {
	private static ItemList itemList= null;
	static public ItemList getItemList() {
		if(itemList== null) {
			try {
				itemList=ItemListManager.getManager().loadItemList();
				//New stuff
				itemList.addListener(new Listener() {
					
					@Override
					public void update() {
						saveItemList();
						
					}
				});
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize item list from item list manager");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize item list from item list manager");

			}
			//itemList= new ItemList();
		}
		return itemList;
		
	}
	
	static public void saveItemList() {
		try {
			ItemListManager.getManager().saveItemList(getItemList());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Can not deserialize item list from item list manager");

		}
	}
	
	
	
	public void add(Item item) {
		// TODO Auto-generated method stub
		getItemList().add(item);
		
	}
	

}
