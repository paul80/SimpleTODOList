package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EmailSomeTodoActivity extends Activity {

	Item checkItem;
	
	//Have an arraylist to store items to be emailed, before arraylist was type string
	ArrayList <String> emailItems= new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.some_todo_email);
		
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
        
        
        ListView email_listView= (ListView) findViewById(R.id.EmailSomeTodoListView);
        //Get the list of todo items
        Collection<Item> todo_items= ItemListController.getItemList().getItems();
        ArrayList <Item> before= new ArrayList <Item>(todo_items);
        
        Collection<Item> email_items= EmailListController.getEmailList().getItems();
        ArrayList<Item> after= new ArrayList<Item>(email_items);
  
        
        //Change the todo list by adding [] in front to indicate to the user whether they have clicked it or not
        for (int i=0; i<before.size();i++) {
        	Item checkItem=before.get(i);
        	String newcheck= checkItem.getName();
        	newcheck=newcheck.substring(0,3);
        	String testA="[ ]";
        	String testB="[+]";
        	if (newcheck.equals(testA) || newcheck.equals(testB)) {
        		continue;
        	}
        	String newString= "[ ]"+before.get(i);
        	Item newItem= new Item(newString);
        	after.add(newItem);
        }
        
        final ArrayList<Item> email_list= new ArrayList <Item> (after);
        final ArrayAdapter<Item> EmailAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, email_list);
        email_listView.setAdapter(EmailAdapter);
        
        
        //Works but not on click...
        EmailListController.getEmailList().addListener(new Listener() {
        	@Override
        	public void update() {
        		email_list.clear();
        		Collection<Item> email_items= EmailListController.getEmailList().getItems();
        		email_list.addAll(email_items);
        		EmailAdapter.notifyDataSetChanged();
        	}
        });
        
        //Up to here now

        email_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				checkItem=email_list.get(position);
				String s1= checkItem.getName();
				//Toast.makeText(getBaseContext(), s1, Toast.LENGTH_SHORT).show();
				
				char character= s1.charAt(1);
				//Since character is a char, it is a primitive and can be compared using ==
				if (character==' ') {
					//String notify="Check";
					//Toast.makeText(getBaseContext(), notify,Toast.LENGTH_SHORT).show();
					
					//Check off that the item has been clicked and will be e-mailed
					//emailItems.add(s.substring(3));
					String s2= "[+]"+s1.substring(3);
					checkItem=new Item(s2);
					email_list.set(position, checkItem);
					//EmailListController.getEmailList().set(position, checkItem);
					//ItemAdapter.notifyDataSetChanged()
					//emailItems.remove(s1);
					//emailItems.set(position, checkItem);
				}
				
				else {
					//String notify="Uncheck";
					//Toast.makeText(getBaseContext(), notify, Toast.LENGTH_SHORT).show();
					//emailItems.remove(s.substring(3));
					String s2="[ ]"+s1.substring(3);
					checkItem= new Item(s2);
					email_list.set(position, checkItem);
					//EmailListController.getEmailList().set(position,checkItem);
					//emailItems.remove(s1);
					//emailItems.set(position, checkItem);
				}
				
				EmailAdapter.notifyDataSetChanged();
			}
        
		});
        
        final ArrayList <Item> emailout= email_list;
        Button email_some_todo= (Button) findViewById(R.id.EmailSelectedTodoButton);
        email_some_todo.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				String s="";
				int size=emailout.size();
				if (size>0) {
					for(int i=0;i<size-1;i++) {
						Item test= emailout.get(i);
						String testName=test.getName();
						char character= testName.charAt(1);
						if(character==' ') {
							continue;
						}
						else {
							s=s+testName.substring(3)+", ";
						}
					}
					Intent emailTodoIntent = new Intent(Intent.ACTION_SEND);
					emailTodoIntent.setType("message/rfc822");
					emailTodoIntent.putExtra(Intent.EXTRA_TEXT,s);
					try {
					    startActivity(Intent.createChooser(emailTodoIntent, "Select your email client"));
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(EmailSomeTodoActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
				}
				
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
	
	/*        Button b = (Button)findViewById(R.id.EmailSomeTodoButton);
    b.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			 int list_size= ItemListController.getItemList().size();
			 String s="";
			 for(int i=0; i<list_size; i++) {
			 Item item= ItemListController.getItemList().get(i); // Item in the list
			 String item_name= item.getName();
			 int length= 1;
			 char character= item_name.charAt(length);
			 if (character==' ') {
			 s=s+item_name+", ";
			 }
			
		}
		}
	});*/
	
}
