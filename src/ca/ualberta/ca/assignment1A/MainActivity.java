//GNU licences below


/*
 
ATODOList:Records items to be done by the user
Copyright (C) 2014 Paul Nhan pnhan@ualberta.ca
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

*/

/*
Student Picker: Randomly pick students to answer questions
Copyright (C) 2014 Abram Hindle abram.hindle@softwareprocess.ca
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

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
        
        //Load and save TODO and archive lists from memory
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());

        
        ListView listView= (ListView) findViewById(R.id.ItemListView);
        Collection<Item> items= ItemListController.getItemList().getItems();
        final ArrayList <Item> list= new ArrayList <Item>(items);
        final ArrayAdapter<Item> ItemAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(ItemAdapter);
        
        //Change observer to update TODO list
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
				
				//Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
				
				int length= s.length();
				length=length-2;
				
				//Commented out block isn't working, test later
/*				if (s.length()>=3) {
					String test= s.substring(0,3);
					String testA="[ ]";
		        	String testB="[+]";
					if (test.equals(testA) ||test.equals(testB)){
						s=s.substring(3);
						count=new Item(s);
						ItemListController.getItemList().set(position,count);
						ItemAdapter.notifyDataSetChanged();

					}
				}*/
				
				char character= s.charAt(length);
				//Since character is a char, it is a primitve and can be compared using ==
				if (character==' ') {
					//String notify="Not checked";
					//Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					s= s.substring(0,length-1)+"[x]";
					count=new Item(s);
					ItemListController.getItemList().set(position, count);
					//ItemAdapter.notifyDataSetChanged();
				}
				
				else {
					//String notify="Checked";
					//Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					s=s.substring(0,length-1)+ "[ ]";
					count= new Item(s);
					ItemListController.getItemList().set(position,count);
				}
				
				ItemAdapter.notifyDataSetChanged();
				
			}
        
		});
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				
					count= list.get(position);
				//ItemListController.getItemList().remove(item);
				return false;
			}
		});
		
        
        // Passing listView to ContextMenu for onCreateContextMenu
        registerForContextMenu(listView);
        
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

    @Override
    //Create a floating context menu upon longclicking an item in the list view
    public boolean onContextItemSelected(MenuItem item){    
    	if(item.getTitle()=="Delete item"){  
            Toast.makeText(getApplicationContext(),"Deleting item",Toast.LENGTH_SHORT).show();
            
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
    
    // The below 3 functions go to different views when a button is pressed
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
    
    
    //Used to add an item to the TODO list view
    public void AddItemAction(View v) {
    	Toast.makeText(this,"Adding a item", Toast.LENGTH_SHORT).show();
    	ItemListController st= new ItemListController();
    	EditText textView= (EditText) findViewById(R.id.AddItemTextBox);
    	st.add(new Item(textView.getText().toString()+ "[ ]"));
    }
}
