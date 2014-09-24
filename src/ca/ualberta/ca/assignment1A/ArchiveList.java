package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

public class ArchiveList {
	protected  ArrayList <Item> archiveList;
	protected ArrayList<Listener> listeners;
	
	public ArchiveList() {
		archiveList= new ArrayList <Item> ();
		listeners= new ArrayList <Listener>();
	}

	public void add(Item testItem) {
		archiveList.add(testItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}

	public int size() {
		return archiveList.size();
	}

	public boolean contains(Item testItem) {
		// TODO Auto-generated method stub
		return archiveList.contains(testItem);
	}

	public void remove(Item testItem) {
		archiveList.remove(testItem);
		notifyListeners();
	}

	public void unarchiveItem(Item testItem, ItemList itemList) {
		for (int i=0; i<archiveList.size(); i++) {
			Item testItemB= archiveList.get(i);
			if (testItemB.equals(testItem)) {
				archiveList.remove(testItemB);
				itemList.add(testItemB);
				notifyListeners();
				break;
			}
		}
		
	}
	
	public Collection<Item> getArchiveItems() {
		// TODO Auto-generated method stub
		return archiveList;
	}
	
	public void addListener(Listener l) {
		listeners.add(l);
		
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
		
	}

	public void set(int index, Item item) {
		// TODO Auto-generated method stub
		archiveList.set(index, item);
		notifyListeners();
		
	}

	public Item get(int i) {
		// TODO Auto-generated method stub
		return archiveList.get(i);
	}

}
