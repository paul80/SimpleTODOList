package ca.ualberta.ca.assignment1A;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EmailActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		
		ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());
		
      //Initialize 4 buttons for email
		Button emailAllTodo= (Button) findViewById(R.id.EmailTodoButton);
		Button emailAll= (Button) findViewById(R.id.EmailAllButton);
		Button emailSomeTodo= (Button) findViewById(R.id.EmailSomeTodoButton);
		emailAllTodo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(EmailActivity.this, "Emailing TODO items", Toast.LENGTH_SHORT).show();
				String s="";
				int size=ItemListController.getItemList().size();
				if(size>0) {
					for (int i=0; i<size-1;i++){
					//emailItems.add(ItemListController.getItemList().get(i));
						s=s+ItemListController.getItemList().get(i) +", ";
					}
					s=s+ItemListController.getItemList().get(size-1);

				}
				//s=s+ItemListController.getItemList().get(size-1);
				Intent emailTodoIntent = new Intent(Intent.ACTION_SEND);
				
				//Make sure only email clients are selected and body of text contains TODO items
				emailTodoIntent.setType("message/rfc822");
				emailTodoIntent.putExtra(Intent.EXTRA_TEXT,s);
				try {
				    startActivity(Intent.createChooser(emailTodoIntent, "Select your email client"));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		emailAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(EmailActivity.this, "Emailing all items", Toast.LENGTH_SHORT).show();
				String s2="";
				int item_list_size= ItemListController.getItemList().size();
				int archive_size=ArchiveListController.getArchiveList().size();
				if (item_list_size>0){
					for (int i=0; i<item_list_size-1;i++){
						s2=s2+ItemListController.getItemList().get(i) +", ";
					}
					s2=s2+ItemListController.getItemList().get(item_list_size-1)+" ";

				}
				//s2=s2+ItemListController.getItemList().get(item_list_size-1)+" ";
				
				if (archive_size>0){
					for(int i=0; i<archive_size-1; i++) {
						s2=s2+ArchiveListController.getArchiveList().get(i)+", ";
					}
					s2=s2+ArchiveListController.getArchiveList().get(archive_size-1);

				}
				//s2=s2+ArchiveListController.getArchiveList().get(archive_size-1);
				Intent emailAll = new Intent(Intent.ACTION_SEND);
				
				//Make sure only email clients are selected and body of text contains TODO items
				emailAll.setType("message/rfc822");
				emailAll.putExtra(Intent.EXTRA_TEXT,s2);
				try {
				    startActivity(Intent.createChooser(emailAll, "Select your email client"));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(EmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		emailSomeTodo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(EmailActivity.this,EmailSomeTodoActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
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
