package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ArchiveListActivity extends Activity {
	
	Item count;  //Added in

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archive_list);
		
		//--------------------------New items here
		
		ListView archive_listView= (ListView) findViewById(R.id.ArchiveListView);
        Collection<Item> archive_items= ArchiveListController.getArchiveList().getArchiveItems();
        final ArrayList <Item> archive_list= new ArrayList <Item>(archive_items);
        final ArrayAdapter<Item> ArchiveAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, archive_list);
        archive_listView.setAdapter(ArchiveAdapter);
        
        
                //Change observer to update item list is here
        ArchiveListController.getArchiveList().addListener(new Listener() {
        	@Override
        	public void update() {
        		archive_list.clear();
        		Collection<Item> archive_items= ArchiveListController.getArchiveList().getArchiveItems();
        		archive_list.addAll(archive_items);
        		ArchiveAdapter.notifyDataSetChanged();
        	}
        });
        
        //------------------------End new stuff here, it works. New test below-----------------------------//
        
        
        
                 //Trial code for setOnItemClickListener
        
        archive_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				count=archive_list.get(position);
				String s= count.getName();
				Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
				
				//OK UP to here
				int length= s.length();
				length=length-2;
				
				char character= s.charAt(length);
				if (character==' ') {
					String notify="Not checked";
					Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					//Here works
					s= s.substring(0,length-1)+"[x]";
					count=new Item(s);
					ArchiveListController.getArchiveList().set(position, count);
					//ItemAdapter.notifyDataSetChanged();
				}
				
				else {
					String notify="Checked";
					Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					s=s.substring(0,length-1)+ "[ ]";
					count= new Item(s);
					ArchiveListController.getArchiveList().set(position,count);
				}
				
				ArchiveAdapter.notifyDataSetChanged();
				
			}
        
		});
         
        archive_listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				//Toast.makeText(MainActivity.this,
				//		"Delete"+list.get(position).toString(),
				//		Toast.LENGTH_SHORT).show();
				
					count= archive_list.get(position);
				//ArchiveListController.getArchiveList().remove(item);
				return false;
			}
		});
		
        
        // Passing listView to ContextMenu for onCreateContextMenu
        registerForContextMenu(archive_listView);
	}
	
    //Trial code here, to create a context menu, works, yay!
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0,v.getId(),0, "Delete item");
        menu.add(0,v.getId(),0, "Unarchive item");
    }
        
        
        //-----------------End new test here.Code works-----------------------------------//
        
        
        
        
             //New trial here
    @Override
    public boolean onContextItemSelected(MenuItem item){    
    	if(item.getTitle()=="Delete item"){  
            Toast.makeText(getApplicationContext(),"Deleting item",Toast.LENGTH_SHORT).show();
            
            //  ******New test section here, works!
            ArchiveListController.getArchiveList().remove(count); 
        }    
    	else if(item.getTitle()=="Unarchive item"){  
            Toast.makeText(getApplicationContext(),"Archiving item",Toast.LENGTH_SHORT).show();
            ArchiveListController.getArchiveList().remove(count);
            ItemListController.getItemList().add(count);
    	}
    	else {
    		return false;
    	}
    	return true;
    }

         
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive_list, menu);
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
