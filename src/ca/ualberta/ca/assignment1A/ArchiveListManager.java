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

public class ArchiveListManager {
	static final String prefile2= "ArchiveList";
	static final String archive_key= "archiveList";
	Context context;
	
	static private ArchiveListManager archiveListManager=null;
	
	public static void initManager2 (Context context) {
		if (archiveListManager==null) {
			if(context==null) {
				throw new RuntimeException("Missing context for ArchiveListManager");
			}
			archiveListManager= new ArchiveListManager(context);
		}
	}
	
	// Singleton
	public static ArchiveListManager getManager() {
		if (archiveListManager==null) {
			throw new RuntimeException("Did not initialize manager");
			}
		return archiveListManager;
	}
	
	public ArchiveListManager(Context context) {
		this.context= context;
	}

	public ArchiveList loadArchiveList() throws ClassNotFoundException, IOException {
		SharedPreferences settings =context.getSharedPreferences(prefile2, Context.MODE_PRIVATE);
		String archiveListData= settings.getString(archive_key,"");
		if (archiveListData.equals("")) {
			return new ArchiveList();
		} else {
			return archiveListFromString(archiveListData);
		} 
	}
	
	static public ArchiveList archiveListFromString(String archiveListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi= new ByteArrayInputStream(Base64.decode(archiveListData, Base64.DEFAULT));
		ObjectInputStream oi= new ObjectInputStream(bi);
		return (ArchiveList)oi.readObject();
	}
	
	static public String archiveListToString(ArchiveList al) throws IOException {
		ByteArrayOutputStream bo= new ByteArrayOutputStream();
		ObjectOutputStream oo= new ObjectOutputStream(bo);
		oo.writeObject(al);
		oo.close();
		byte bytes[]= bo.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public void saveArchiveList(ArchiveList al) throws IOException {
		SharedPreferences settings =context.getSharedPreferences(prefile2, Context.MODE_PRIVATE);
		Editor editor= settings.edit();
		editor.putString(archive_key, archiveListToString(al));
		editor.commit();
	}

}
