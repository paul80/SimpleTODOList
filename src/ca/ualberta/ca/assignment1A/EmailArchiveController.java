package ca.ualberta.ca.assignment1A;

public class EmailArchiveController {
	private static EmailArchiveList emailArchiveList= null;
	
	static public EmailArchiveList getEmailArchiveList() {
		if(emailArchiveList== null) {
			emailArchiveList=new EmailArchiveList();
		}
		return emailArchiveList;
		
	}
	
	public void add(Item item) {
		getEmailArchiveList().add(item);
		
	}
	

}
