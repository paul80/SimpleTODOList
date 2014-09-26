package ca.ualberta.ca.assignment1A;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class ItemListManager {
	
	static final String prefile= "ItemList";
	static final String ilkey= "itemList";
	Context context;
	
	static private ItemListManager itemListManager=null;
	
	public static void initManager(Context context) {
		if (itemListManager==null) {
			if(context==null) {
				throw new RuntimeException("Missing context for ItemListManager");
			}
			itemListManager= new ItemListManager(context);
		}
	}
	
	
	
	//Singleton
	public static ItemListManager getManager() {
		if (itemListManager==null) {
			throw new RuntimeException("Did not initialize manager");
			}
		return itemListManager;
	}
	
	public ItemListManager(Context context) {
		this.context= context;
	}

	public ItemList loadItemList() throws ClassNotFoundException, IOException {
		SharedPreferences settings =context.getSharedPreferences(prefile, Context.MODE_PRIVATE);
		String itemListData= settings.getString(ilkey,"");
		if (itemListData.equals("")) {
			return new ItemList();
		} else {
			return itemListFromString(itemListData);
		} 
	}
	
	static public ItemList itemListFromString(String itemListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi= new ByteArrayInputStream(Base64.decode(itemListData, Base64.DEFAULT));
		ObjectInputStream oi= new ObjectInputStream(bi);
		return (ItemList)oi.readObject();
	}
	
	static public String itemListToString(ItemList il) throws IOException {
		ByteArrayOutputStream bo= new ByteArrayOutputStream();
		ObjectOutputStream oo= new ObjectOutputStream(bo);
		oo.writeObject(il);
		oo.close();
		byte bytes[]= bo.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public void saveItemList(ItemList il) throws IOException {
		SharedPreferences settings =context.getSharedPreferences(prefile, Context.MODE_PRIVATE);
		Editor editor= settings.edit();
		editor.putString(ilkey, itemListToString(il));
		editor.commit();
	}


}
	