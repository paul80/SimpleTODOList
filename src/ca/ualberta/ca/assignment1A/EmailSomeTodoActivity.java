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
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EmailSomeTodoActivity extends Activity {

	Item checkItem;
	
	//Have an arraylist to store items to be emailed, before arraylist was type string
	ArrayList <String> emailItems= new ArrayList<String>();
	
	String listChoice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.some_todo_email);
		
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
        
        final ListView email_listView= (ListView) findViewById(R.id.EmailSomeTodoListView);
        
        Intent intent= getIntent();
        listChoice= intent.getStringExtra("listToUse");
        
        //Get the list of items
        Collection<Item> items;
        if (listChoice.equals("to do")) {
            items= ItemListController.getItemList().getItems();
            //final ArrayList <Item> toDoItems= new ArrayList <Item>(items);
        }
        
        else {
            items= ArchiveListController.getArchiveList().getArchiveItems();
            //final ArrayList <Item> toDoItems= new ArrayList <Item>(items);
        }
        
        //Collection<Item> items= ItemListController.getItemList().getItems();
        final ArrayList <Item> toDoItems= new ArrayList <Item>(items);
        
        final Collection<Item> email_items= EmailListController.getEmailList().getItems();
        final ArrayList<Item> after= new ArrayList<Item>(email_items);
  
        //No longer using [] to denote a checkbox for emailing items, using an actual checkbox
        final ArrayAdapter <Item> EmailAdapter= new ArrayAdapter<Item> (this,android.R.layout.simple_list_item_multiple_choice, toDoItems);
        email_listView.setAdapter(EmailAdapter);
        
        email_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				CheckedTextView checkBox= (CheckedTextView) view;
				boolean result= checkBox.isChecked();
				Item item= toDoItems.get(position);

				if (result) {
					after.add(item);
				}
				else {
					after.remove(item);
				}
				
			}
		});


        Button selectAll= (Button) findViewById(R.id.SelectAllButton);
        selectAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int number=email_listView.getCheckedItemCount();
				if (number==0) {
					for (int i=0; i< EmailAdapter.getCount(); i++) {
						email_listView.setItemChecked(i,true);
						Item item= toDoItems.get(i);
						after.add(item);
					}
				}
				else {
					for (int i=0; i< EmailAdapter.getCount(); i++) {
						email_listView.setItemChecked(i, false);
					}
					after.clear();
				}

			
				
			}
		});
        
        //final ArrayList <Item> emailout= email_list;
        Button email_some_todo= (Button) findViewById(R.id.EmailSelectedTodoButton);
        email_some_todo.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				String s="";
				//int size=emailout.size();
				int size= after.size();
				if (size>0) {
					for(int i=0;i<size;i++) {
						//Item test= emailout.get(i);
						Item test= after.get(i);
						String testName=test.getName();

						s=s+testName+ ", ";
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
				else {
				    Toast.makeText(EmailSomeTodoActivity.this, "There are no items selected", Toast.LENGTH_SHORT).show();

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
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
	


	
}
