package ca.ualberta.ca.assignment1A;

public class EmailListController {

	private static EmailList emailList= null;
	static public EmailList getEmailList() {
		if(emailList== null) {
			emailList=new EmailList();
		}
		return emailList;
		
	}
	
	public void add(Item item) {
		getEmailList().add(item);
		
	}
	

}
