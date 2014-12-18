package ca.ualberta.ca.assignment1A;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
        //Get both the todo list and the archive list from memory
        ItemListManager.initManager(this.getApplicationContext());
        ArchiveListManager.initManager2(this.getApplicationContext());

		
		//Get the five textviews for summarizing data
		TextView textViewA= (TextView) findViewById(R.id.TodoCheckedTextView);
		TextView textViewB= (TextView) findViewById(R.id.TodoUncheckedTextView);
		TextView textViewC= (TextView) findViewById(R.id.ArchivedItemsTextView);
		TextView textViewD= (TextView) findViewById(R.id.ArchivedCheckedTextView);
		TextView textViewE= (TextView) findViewById(R.id.ArchivedUncheckedTextView);
		
		
		//Get item list and archive list sizes
		int list_size= ItemListController.getItemList().size(); 
		int archive_size=ArchiveListController.getArchiveList().size(); 
		
		
		int TodoChecked=0;
		int TodoUnchecked=0;
		int ArchiveChecked=0;
		int ArchiveUnchecked=0;
		
		//Get # of checked/unchecked items in both todo list and archive list
		for(int i=0; i<list_size; i++) {
			Item item= ItemListController.getItemList().get(i); // Item in the list
			String item_name= item.getName();
			int length= item_name.length();
			length=length-2;
			char character= item_name.charAt(length);
			if (character==' ') {
				TodoUnchecked+=1;
			}
			else {
				TodoChecked+=1;
			}
		}
		
		for (int i=0; i<archive_size; i++) {
			Item item= ArchiveListController.getArchiveList().get(i);
			String item_name= item.getName();
			int length= item_name.length();
			length=length-2;
			char character= item_name.charAt(length);
			if (character==' '){
				ArchiveUnchecked+=1;
			}
			else {
				ArchiveChecked+=1;
			}
		}
		
		//Now have two for loops to calculate the checked and unchecked items
		//Now have 5 strings for display 
		String TodoCheck= String.valueOf(TodoChecked)+ " TODO items checked";
		String TodoUncheck= String.valueOf(TodoUnchecked) + " TODO items unchecked";
		String ArchiveCheck= String.valueOf(ArchiveChecked) + " archive items checked";
		String ArchiveUncheck= String.valueOf(ArchiveUnchecked) + " archive items unchecked";
		String ArchiveAmount= String.valueOf(archive_size) + " items in archive";
		
		//Change the texts in the textviews to reflect summary stats
		textViewA.setText(TodoCheck);
		textViewB.setText(TodoUncheck);
		textViewC.setText(ArchiveAmount);
		textViewD.setText(ArchiveCheck);
		textViewE.setText(ArchiveUncheck);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
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
