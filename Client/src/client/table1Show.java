package client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/*
 * this class is to fill the table1 in class  reports activitieReportsGUI
 * @author Marshood Ayoub , 311286694 
 */
public class table1Show {
	String AllUser1;
	String ActivityUsers1;
	String FreezedUser1;
	String LockedUser1;
	String AverageLocked;
	String AverageFreezed;
	// this arrayList message to send the information about new subscriber
	private ArrayList<String> message;
    /* 
     * this constractor is to conect with the DB and get the all information to get the actitivty reports 
     * 
     */
	public table1Show(String fisrtDate, String lastDate) throws ClassNotFoundException {
    		/*
    		 * ArrayList message we save the new subscribber to send it to DB clientCon in
    		 * to send to server the data and to get the respond from the the server
    		 */
    		ClientConsole clientCon = new ClientConsole();
    		message = new ArrayList<String>();//
    		// save all the new Subscriber
    		// in the index 0 in message we send the first day and in index 1 the last day 
            message.add("GetActiviteReports");
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
    			setAllUser1(Integer.toString(Integer.parseInt(message1.get(1))+Integer.parseInt(message1.get(2))+Integer.parseInt(message1.get(3))));
    			setActivityUsers1(message1.get(3));
    			setFreezedUser1(message1.get(2));
    			setLockedUser1(message1.get(1));
    			setAverageLocked(message1.get(5));
    			setAverageFreezed(message1.get(4));
    		}
    	}
 
	/*
	 * this  is a seters and gerters 
	 * */
	public synchronized String getAllUser1() {
		return AllUser1;
	}
	public  void setAllUser1(String allUser1) {
		AllUser1 = allUser1;
	}
	public  String getActivityUsers1() {
		return ActivityUsers1;
	}
	public  void setActivityUsers1(String activityUsers1) {
		ActivityUsers1 = activityUsers1;
	}
	public  String getFreezedUser1() {
		return FreezedUser1;
	}
	public  void setFreezedUser1(String freezedUser1) {
		FreezedUser1 = freezedUser1;
	}
	public  String getLockedUser1() {
		return LockedUser1;
	}
	public  void setLockedUser1(String lockedUser1) {
		LockedUser1 = lockedUser1;
	}
	public  String getAverageLocked() {
		return AverageLocked;
	}
	public  void setAverageLocked(String averageLocked) {
		AverageLocked = averageLocked;
	}
	public  String getAverageFreezed() {
		return AverageFreezed;
	}
	public  void setAverageFreezed(String averageFreezed) {
		AverageFreezed = averageFreezed;
	}
}
