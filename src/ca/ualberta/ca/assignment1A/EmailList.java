package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.widget.Toast;

public class EmailList {
	protected ArrayList <Item>emailList;
	protected ArrayList <Listener> listeners;
	
	public EmailList() {
		emailList= new ArrayList<Item>();
		listeners= new ArrayList<Listener>();
	}

	
	public void add(Item testItem) {
		String name= testItem.getName();
		name="[ ]"+name;
		Item newItem= new Item(name);
		emailList.add(newItem);
		notifyListeners();
	}

	private void notifyListeners() {
		for (Listener listener : listeners) {
		listener.update();
		}
	}
	

	public int size() {
		return emailList.size();
	}

	public boolean contains(Item testItem) {
		return emailList.contains(testItem);
	}

	public void remove(Item testItem) {
		emailList.remove(testItem);
		notifyListeners();
		
	}

	public Item get(int i) {
		return emailList.get(i);
	}

	public Collection<Item> getItems() {
		// TODO Auto-generated method stub
		return emailList;
	}

	public void addListener(Listener l) {
		listeners.add(l);
		
	}

	public void removeListener(Listener l) {
		listeners.remove(l);
		
	}
	
	public void set(int index, Item item){
		emailList.set(index, item);
		notifyListeners();
		
	}

}
