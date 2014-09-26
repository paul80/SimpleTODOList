package ca.ualberta.ca.assignment1A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class ItemList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5676069128146769628L;
	protected ArrayList<Item> itemList=null;
	//
	protected transient ArrayList<Listener> listeners= null;
	//
	public ItemList() {
		itemList=new ArrayList<Item>();
		listeners= new ArrayList<Listener>();
	}
	
	//
	private ArrayList <Listener>getListeners(){
		if (listeners== null) {
			listeners= new ArrayList<Listener>();
		}
		return listeners;
	}
	
	public void add(Item testItem) {
		itemList.add(testItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}

	}
	

	public int size() {
		return itemList.size();
	}

	public boolean contains(Item testItem) {
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
		getListeners().add(l);
		
	}

	public void removeListener(Listener l) {
		getListeners().remove(l);
		
	}
	
	public void set(int index, Item item){
		itemList.set(index, item);
		notifyListeners();
		
	}
	
	

}
