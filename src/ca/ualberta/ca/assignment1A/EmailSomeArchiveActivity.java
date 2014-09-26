package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EmailSomeArchiveActivity extends Activity
{
	Item count;
	ArrayList <String> emailItems= new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.some_archive_email);
		
		ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
       
        
        ListView listView= (ListView) findViewById(R.id.SomeEmailArchiveListView);
        Collection<Item> items= ArchiveListController.getArchiveList().getArchiveItems();
        ArrayList <Item> before= new ArrayList<Item> (items);
        
        //Change the todo list by adding [] in front to indicate to the user whether they have clicked it or not
        for (int i=0; i<items.size();i++) {
        	Item check=before.get(i);
        	String newcheck= check.getName();
        	newcheck=newcheck.substring(0,3);
        	String testA="[ ]";
        	String testB="[+]";
        	if (newcheck.equals(testA) || newcheck.equals(testB)) {
        		continue;
        	}
        	String newString= "[ ]"+before.get(i);
        	Item newItem= new Item(newString);
        	before.set(i,newItem);
        }
        //final ArrayList <Item> list= new ArrayList <Item>(items);
        final ArrayList<Item> list= before;
        final ArrayAdapter<Item> ItemAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(ItemAdapter);
        
        ArchiveListController.getArchiveList().addListener(new Listener() {
        	@Override
        	public void update() {
        		list.clear();
        		Collection<Item> items= ArchiveListController.getArchiveList().getArchiveItems();
        		list.addAll(items);
        		ItemAdapter.notifyDataSetChanged();
        	}
        });
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				count=list.get(position);
				String s= count.getName();
				char character= s.charAt(1);
				//Since character is a char, it is a primitive and can be compared using ==
				if (character==' ') {
					
					//String notify="Not checked";
					//Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					
					//Check off that the item has been clicked and will be emailed
					//emailItems.add(s.substring(3));
					s= "[+]"+s.substring(3);
					count=new Item(s);
					ArchiveListController.getArchiveList().set(position, count);
					//ItemAdapter.notifyDataSetChanged();
				}
				
				else {
					//String notify="Checked";
					//Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					s="[ ]"+s.substring(3);
					count= new Item(s);
					ArchiveListController.getArchiveList().set(position,count);
				}
				
				ItemAdapter.notifyDataSetChanged();
			}
        
		});
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email_some_archive, menu);
		return true;
	}

}
