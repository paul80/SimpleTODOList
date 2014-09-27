package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EmailSomeArchiveActivity extends Activity
{
	Item checkItem;
	
	//Have an arraylist to store items to be emailed, before arraylist was type string
	ArrayList <String> aemailItems= new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.some_archive_email);
		
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
        
        
        ListView Aemail_listView= (ListView) findViewById(R.id.SomeEmailArchiveListView);
        //Get the list of todo items
        Collection<Item> archive_items= ArchiveListController.getArchiveList().getArchiveItems();
        ArrayList <Item> before= new ArrayList <Item>(archive_items);
        Toast.makeText(this,"before list works", Toast.LENGTH_SHORT).show();
        Collection<Item> Aemail_items= EmailArchiveController.getEmailArchiveList().getItems();
        ArrayList<Item> after= new ArrayList<Item>(Aemail_items);
  
        
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
        
        final ArrayList<Item> archive_list= new ArrayList <Item> (after);
        final ArrayAdapter<Item> AEmailAdapter= new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, archive_list);
        Aemail_listView.setAdapter(AEmailAdapter);
        
        
        //Works but not on click...
        EmailArchiveController.getEmailArchiveList().addListener(new Listener() {
        	@Override
        	public void update() {
        		archive_list.clear();
        		Collection<Item> archive_items= EmailArchiveController.getEmailArchiveList().getItems();
        		archive_list.addAll(archive_items);
        		AEmailAdapter.notifyDataSetChanged();
        	}
        });
        
        //Up to here now

        Aemail_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				checkItem=archive_list.get(position);
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
					archive_list.set(position, checkItem);
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
					archive_list.set(position, checkItem);
					//EmailListController.getEmailList().set(position,checkItem);
					//emailItems.remove(s1);
					//emailItems.set(position, checkItem);
				}
				
				AEmailAdapter.notifyDataSetChanged();
			}
        
		});
        
        final ArrayList <Item> aemailout= archive_list;
        
        Button email_some_archive= (Button) findViewById(R.id.EmailSomeArchiveButton);
        email_some_archive.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				String s="";
				int size=aemailout.size();
				if (size>0) {
					for(int i=0;i<size-1;i++) {
						Item test= aemailout.get(i);
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
					    Toast.makeText(EmailSomeArchiveActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
				}
				
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
