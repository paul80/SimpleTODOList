package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

public class EmailArchiveList {
	protected ArrayList <Item>emailArchiveList;
	protected ArrayList <Listener> listeners;
	
	public EmailArchiveList() {
		emailArchiveList= new ArrayList<Item>();
		listeners= new ArrayList<Listener>();
	}

	
	public void add(Item testItem) {
		String name= testItem.getName();
		name="[ ]"+name;
		Item newItem= new Item(name);
		emailArchiveList.add(newItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : listeners) {
		listener.update();
		}
	}
	

	public int size() {
		return emailArchiveList.size();
	}

	public boolean contains(Item testItem) {
		return emailArchiveList.contains(testItem);
	}

	public void remove(Item testItem) {
		emailArchiveList.remove(testItem);
		notifyListeners();
		
	}

	public Item get(int i) {
		return emailArchiveList.get(i);
	}

	public Collection<Item> getItems() {
		// TODO Auto-generated method stub
		return emailArchiveList;
	}

	public void addListener(Listener l) {
		listeners.add(l);
		
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
		
	}
	
	public void set(int index, Item item){
		emailArchiveList.set(index, item);
		notifyListeners();
		
	}

}
