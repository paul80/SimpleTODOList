package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EmailSomeTodoActivity extends Activity {

	Item count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.some_todo_email);
		ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
        
        //
        
        ListView listView= (ListView) findViewById(R.id.EmailSomeTodoListView);
        Collection<Item> items= ItemListController.getItemList().getItems();
        ArrayList <Item> before= new ArrayList<Item> (items);
        //Change the todo list by adding [] in front
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
        
        ItemListController.getItemList().addListener(new Listener() {
        	@Override
        	public void update() {
        		list.clear();
        		Collection<Item> items= ItemListController.getItemList().getItems();
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
				Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
				
				//
				
				char character= s.charAt(1);
				//Since character is a char, it is a primitve and can be compared using ==
				if (character==' ') {
					String notify="Not checked";
					Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					//Here works
					s= "[+]"+s.substring(3);
					count=new Item(s);
					ItemListController.getItemList().set(position, count);
					//ItemAdapter.notifyDataSetChanged();
				}
				
				else {
					String notify="Checked";
					Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					s="[ ]"+s.substring(3);
					count= new Item(s);
					ItemListController.getItemList().set(position,count);
				}
				
				ItemAdapter.notifyDataSetChanged();
				
			}
        
		});
        
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.some_todo_email, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
