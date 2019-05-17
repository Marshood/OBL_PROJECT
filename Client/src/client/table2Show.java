package client;

import java.util.ArrayList;
/*
 * this class is to fill the table2 in class  reports activitieReportsGUI
 * @author Marshood Ayoub , 311286694 
 */
public class table2Show {
	String UserID;
	String BookLend;
	String BookLate;
	String PerLate;
	
	// this arrayList message to send the information about new subscriber
	private ArrayList<String> message;
	 /* 
     * this constractor is to conect with the DB and get the all information to get the actitivty reports 
     * 
     */
	public table2Show(String fisrtDate, String lastDate) throws ClassNotFoundException {
		// TODO Auto-generated constructor stub
		/*
		 * ArrayList message we save the new subscribber to send it to DB clientCon in
		 * to send to server the data and to get the respond from the the server
		 */
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();//
		// save all the new Subscriber
		// in the index 0 in message we send the first day and in index 1 the last day 
        message.add("GetLateUsers");
        message.add(fisrtDate);
        message.add(lastDate);
		/*  
		 * we send from the client to server only object we change the type of the
		 * saveData to object and send it to server
		 *
		 */
		Object obj = (Object) message;
		Object obj1 = new Object();
		// send the message to the server
		clientCon.execute(obj);
		// this sleep to wait to server to send the respond
		try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList<String>) obj1;
		// we check the message if the add succsefull or not
		// and after this we save the result at the showTable1 to show it 
		if (message1.get(0).equals("Succ")) {
			setUserID(message1.get(3));
			setBookLend(message1.get(1));
			double sum = (Double.parseDouble(message1.get(1))/Double.parseDouble(message1.get(3)))*100;
			setPerLate(new Double(sum).toString());
			setBookLate(message1.get(4));
 			//setPerLate(message1.get(4));
			System.out.println("size  "+ message1.size()+"aaammm "+message1.get(4));
		
		}
		
	}



	public String getUserID() {
		return UserID;
	}



	public void setUserID(String userID) {
		UserID = userID;
	}



	public String getBookLend() {
		return BookLend;
	}



	public void setBookLend(String BookLend1) {
		BookLend = BookLend1;
	}



	public String getBookLate() {
		return BookLate;
	}



	public void setBookLate(String bookLate) {
		BookLate = bookLate;
	}



	public String getPerLate() {
		return PerLate;
	}



	public void setPerLate(String perLate) {
		PerLate = perLate;
	}

	
	
	
	
}
