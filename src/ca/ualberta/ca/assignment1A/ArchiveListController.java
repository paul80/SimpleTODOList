package ca.ualberta.ca.assignment1A;

public class ArchiveListController {
	private static ArchiveList archiveList= null;
	static public ArchiveList getArchiveList() {
		if(archiveList== null) {
			archiveList= new ArchiveList();
		}
		return archiveList;
		
	}
	public void add(Item item) {
		// TODO Auto-generated method stub
		getArchiveList().add(item);
		
	}
	

	
}
