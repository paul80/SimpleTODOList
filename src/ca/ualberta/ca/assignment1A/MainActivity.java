package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	Item count;
	int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Trial code
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());

        //
        ListView listView= (ListView) findViewById(R.id.ItemListView);
        Collection<Item> items= ItemListController.getItemList().getItems();
        final ArrayList <Item> list= new ArrayList <Item>(items);
        final ArrayAdapter<Item> ItemAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(ItemAdapter);
        
        //Change observer to update item list is here
        ItemListController.getItemList().addListener(new Listener() {
        	@Override
        	public void update() {
        		list.clear();
        		Collection<Item> items= ItemListController.getItemList().getItems();
        		list.addAll(items);
        		ItemAdapter.notifyDataSetChanged();
        	}
        });
        
        //Trial code for setOnItemClickListener
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				count=list.get(position);
				String s= count.getName();
				Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
				
				//OK UP to here
				int length= s.length();
				length=length-2;
				
				char character= s.charAt(length);
				//Since character is a char, it is a primitve and can be compared using ==
				if (character==' ') {
					String notify="Not checked";
					Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					//Here works
					s= s.substring(0,length-1)+"[x]";
					count=new Item(s);
					ItemListController.getItemList().set(position, count);
					//ItemAdapter.notifyDataSetChanged();
				}
				
				else {
					String notify="Checked";
					Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					s=s.substring(0,length-1)+ "[ ]";
					count= new Item(s);
					ItemListController.getItemList().set(position,count);
				}
				
				ItemAdapter.notifyDataSetChanged();
		
				
				//count is a item aka a string
				//length= count.count();
				//length=length-2;  // Get the stuff inside [ ]
				
				//char character= count.charAt(length);
				
				/*if (character==' ') {
					String newCount= count+"[X]";
					Item newItem= new Item(newCount);
					ItemListController.getItemList().set(position, newItem);
					ItemAdapter.notifyDataSetChanged();
				}
				else{
					String newCount=count+"[ ]";
					Item newItem= new Item(newCount);
					ItemListController.getItemList().set(position, newItem);
					ItemAdapter.notifyDataSetChanged();*/
				//}
				
			}
        
		});
        
        
        //Trial code for setOnItemClickListener ends here
        //New items set On item Long Click deletes items but want a Context menu
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				//Toast.makeText(MainActivity.this,
				//		"Delete"+list.get(position).toString(),
				//		Toast.LENGTH_SHORT).show();
				
					count= list.get(position);
				//ItemListController.getItemList().remove(item);
				return false;
			}
		});
		
        
        // Passing listView to ContextMenu for onCreateContextMenu
        registerForContextMenu(listView);
        //Test here
        
    }
    
    //Trial code here, to create a context menu, works, yay!
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0,v.getId(),0, "Delete item");
        menu.add(0,v.getId(),0, "Archive item");
    }

    //New trial here, it works, yay!
    @Override
    public boolean onContextItemSelected(MenuItem item){    
    	if(item.getTitle()=="Delete item"){  
            Toast.makeText(getApplicationContext(),"Deleting item",Toast.LENGTH_SHORT).show();
            
            //  ******New test section here, works!
            ItemListController.getItemList().remove(count); 
            
        }    
    	else if(item.getTitle()=="Archive item"){  
            Toast.makeText(getApplicationContext(),"Archiving item",Toast.LENGTH_SHORT).show();
            ItemListController.getItemList().remove(count);
            ArchiveListController.getArchiveList().add(count);
    	}
    	else {
    		return false;
    	}
    	return true;
    }

    
    //Trial code ends here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void goToArchive(MenuItem menu) {
    	Toast.makeText(this,"Go to archive", Toast.LENGTH_SHORT).show();
    	Intent intent= new Intent(MainActivity.this, ArchiveListActivity.class);
    	startActivity(intent);
    }
    public void goToSummary(MenuItem menu) {
    	Toast.makeText(this,"Go to summary", Toast.LENGTH_SHORT).show();
    	Intent intent= new Intent(MainActivity.this,SummaryActivity.class);
    	startActivity(intent);
    }
    
    public void goToEmail(MenuItem menu) {
    	Toast.makeText(this, "Go to email", Toast.LENGTH_SHORT).show();
    	Intent intent= new Intent(MainActivity.this,EmailActivity.class);
    	startActivity(intent);
    }
    
    public void AddItemAction(View v) {
    	Toast.makeText(this,"Adding a item", Toast.LENGTH_SHORT).show();
    	ItemListController st= new ItemListController();
    	EditText textView= (EditText) findViewById(R.id.AddItemTextBox);
    	st.add(new Item(textView.getText().toString()+ "[ ]"));
    }
}
