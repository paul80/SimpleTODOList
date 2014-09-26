package ca.ualberta.ca.assignment1A;

import java.io.IOException;

//Singleton
public class ArchiveListController {
	private static ArchiveList archiveList= null;
	static public ArchiveList getArchiveList() {
		if(archiveList== null) {
			try {
				archiveList=ArchiveListManager.getManager().loadArchiveList();
				//New stuff
				archiveList.addListener(new Listener() {
					
					@Override
					public void update() {
						saveArchiveList();
						
					}
				});
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize archive list from archive list manager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Can not deserialize archive list from archive list manager");

			}
		}
		return archiveList;
		
	}
	
	static public void saveArchiveList() {
		try {
			ArchiveListManager.getManager().saveArchiveList(getArchiveList());

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not deserialize archive from archive list manager");

		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void add(Item item) {
		// TODO Auto-generated method stub
		getArchiveList().add(item);
		
	}
	

	
}
