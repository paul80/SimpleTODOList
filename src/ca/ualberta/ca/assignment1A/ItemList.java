package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class ItemList {
	protected ArrayList<Item> itemList;
	protected ArrayList<Listener> listeners;
	
	public ItemList() {
		itemList=new ArrayList<Item>();
		listeners= new ArrayList<Listener>();
	}
	
	public void add(Item testItem) {
		itemList.add(testItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
			
		
		
	}

	public int size() {
		return itemList.size();
	}

	public boolean contains(Item testItem) {
		// TODO Auto-generated method stub
		return itemList.contains(testItem);
	}

	public void remove(Item testItem) {
		itemList.remove(testItem);
		notifyListeners();
		
	}

	public Item get(int i) {
		return itemList.get(i);
	}
	public void archiveItem(Item testItem, ArchiveList archiveList) {
		for (int i=0; i<itemList.size(); i++) {
			Item testItemB= itemList.get(i);
			if (testItemB.equals(testItem)) {
				itemList.remove(testItemB);
				archiveList.add(testItemB);
				notifyListeners(); //Update listeners
				break;
			}
		}
	}

	public Collection<Item> getItems() {
		// TODO Auto-generated method stub
		return itemList;
	}

	public void addListener(Listener l) {
		listeners.add(l);
		
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
		
	}
	
	//Should work
	public void set(int index, Item item){
		itemList.set(index, item);
		notifyListeners();
		
	}
	
	

}
