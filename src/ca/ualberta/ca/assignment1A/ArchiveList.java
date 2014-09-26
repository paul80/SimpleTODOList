package ca.ualberta.ca.assignment1A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ArchiveList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4837734984531825124L;
	protected  ArrayList <Item> archiveList= null;
	protected  transient ArrayList<Listener> listeners=null;
	
	public ArchiveList() {
		archiveList= new ArrayList <Item> ();
		listeners= new ArrayList <Listener>();
	}
	
	private ArrayList <Listener>getListeners() {
		if(listeners==null){
			listeners= new ArrayList<Listener>();
		}
		return listeners;
	}

	public void add(Item testItem) {
		archiveList.add(testItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}

	public int size() {
		return archiveList.size();
	}

	public boolean contains(Item testItem) {
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
		getListeners().add(l);
		
	}

	public void removeListener(Listener l) {
		getListeners().remove(l);
		
	}

	public void set(int index, Item item) {
		archiveList.set(index, item);
		notifyListeners();
		
	}

	public Item get(int i) {
		return archiveList.get(i);
	}

}
