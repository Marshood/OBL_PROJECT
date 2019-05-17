package application;

import java.io.FileOutputStream;

// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import entites.Book;
import entites.HistoryForStudent;
import entites.LibrarianNew;
import entites.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *****************

	/**
	 * 
	 */

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	static ConnectionToClient saveClient;
	static EchoServer e = new EchoServer(5555);
	public int row;

//	public Product newP;
	// Constructors ******************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */

	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ****************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 * this method  get a message from the client and after this we check the message and compare  
	 * the array list we get in position 0 to know what func we want to do and what information 
	 * to get from the server , and after this we choices the correct func to call in class   mysqlConnection
	 *   
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException, SQLException, ParseException

	{
		ArrayList<String> message = (ArrayList<String>) msg;
		ArrayList<String> result = new ArrayList<String>();
		if (message.get(0).equals("ReturnLateReport")) {
			ArrayList<Double> counterLateBook;
			counterLateBook = mysqlConnection.ReturnLateReportFunc(message.get(0));

			client.sendToClient(counterLateBook);
		}
		if (message.get(0).equals("GetLateUsers")) {
			ArrayList<String> ReportRes = new ArrayList<String>();
			ReportRes = mysqlConnection.GetLateUsersReports(message);	
			if (!ReportRes.get(0).equals("Succ")) {
				client.sendToClient(null);
			}
			client.sendToClient(ReportRes);
		}
		if (message.get(0).equals("GetLateUsers")) {
			ArrayList<String> ReportRes = new ArrayList<String>();
			ReportRes = mysqlConnection.GetLateUsersReports(message);	
			if (!ReportRes.get(0).equals("Succ")) {
				client.sendToClient(null);
			}
			client.sendToClient(ReportRes);
		}
		if (message.get(0).equals("GetActiviteReports")) {
			ArrayList<String> ReportRes = new ArrayList<String>();
			ReportRes = mysqlConnection.GetActiviteReportsResult(message);

			if (!ReportRes.get(0).equals("Succ")) {
				client.sendToClient(null);
			}
			client.sendToClient(ReportRes);
		}
		if (message.get(0).equals("changetoActiveAfterFreeze")) {
			boolean StudentDetails;
			System.out.println("afteer");
			StudentDetails = mysqlConnection.changetoActiveAfterFreeze(message.get(1));
			System.out.println("befoor");

			if (!StudentDetails) {
				client.sendToClient(null);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("FreezeStudentWithDate")) {
			boolean StudentDetails;
			System.out.println("afteer");
			StudentDetails = mysqlConnection.FreezeStudentWithDate(message);
			System.out.println("befoor");

			if (!StudentDetails) {
				client.sendToClient(null);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("changetoLock")) {
			boolean StudentDetails;
			StudentDetails = mysqlConnection.changeStatus(message.get(1),"Locked");
			if (!StudentDetails) {
				client.sendToClient(null);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("changetoFreeze")) {
			boolean StudentDetails;
			StudentDetails = mysqlConnection.changeStatus(message.get(1),"Suspended");
			if (!StudentDetails) {
				client.sendToClient(null);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("changetoActive")) {
			boolean StudentDetails;
			StudentDetails = mysqlConnection.changeStatus(message.get(1),"Active");
			if (!StudentDetails) {
				client.sendToClient(null);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("CheckLockedUser")) {
			ArrayList<User> StudentDetails;
			StudentDetails = mysqlConnection.UsersLocked();
			if (StudentDetails.isEmpty()) {
				StudentDetails.add(null);
				client.sendToClient(StudentDetails);
			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("CheckActiveusers")) {
			ArrayList<User> StudentDetails;
			StudentDetails = mysqlConnection.UsersActive();
			if (StudentDetails.isEmpty()) {
				StudentDetails.add(null);
				client.sendToClient(StudentDetails);}
			System.out.println("+marshood"+StudentDetails);
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("CheckUsersFreezed")) {
			ArrayList<User> StudentDetails;
			StudentDetails = mysqlConnection.UsersFreezedReturn();
			if (StudentDetails.isEmpty()) {
				StudentDetails.add(null);
				client.sendToClient(StudentDetails);
			}
			System.out.println("+marshood"+StudentDetails);
			client.sendToClient(StudentDetails);}
		if (message.get(0).equals("checkUserExistByFirstName")) {
			ArrayList<User> StudentDetails;
			StudentDetails = mysqlConnection.findSubscriberByFisrtName(message.get(1));
			if (StudentDetails.isEmpty()) {
				client.sendToClient(null);

			}
			client.sendToClient(StudentDetails);
		}
		if (message.get(0).equals("checkUserExistByLastName")) {
			ArrayList<User> StudentDetails1;
			StudentDetails1 = mysqlConnection.findSubscriberByLastName(message.get(1));
			if (StudentDetails1.isEmpty()) {
				client.sendToClient(null);

			}
			client.sendToClient(StudentDetails1);
		}
		if (message.get(0).equals("checkUserExistOnSearchSub")) {
			boolean r = mysqlConnection.CheckUserExits(message.get(1));
			result.add("checkUserExist");
			if (r) {
				result = mysqlConnection.GetUserDataForShowScriber(message.get(1));
				client.sendToClient(result);

			} else {
				result.add("0");
				client.sendToClient(result);

			}

		}
		if (message.get(0).equals("addNewSubscriber")) {
			try {
				result = mysqlConnection.AddNewSubscriber(msg);
				client.sendToClient(result);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("SaveSub")) {
			result = mysqlConnection.updateSubscriberData(msg);
			client.sendToClient(result);

		}
		if (message.get(0).equals("getDetails")) {

			result = mysqlConnection.GetUserDataForShowScriber(message.get(1));
			client.sendToClient(result);

		}

		if (message.get(0).equals("Login")) {

			result = mysqlConnection.loginUser((String) message.get(1), (String) message.get(2));
			client.sendToClient(result);
			//SendMail.sendEmail("New Login To OBL Systems", "a new Login has been recorded to your User Name:"+ result.get(2), result.get(3));

		}

		if (message.get(0).equals("userlogout")) {
			try {
				result = mysqlConnection.ziad(message.get(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("GetDataToFillIn")) {
			ArrayList<String> result1 = new ArrayList<String>();
			try {
				result1 = mysqlConnection.GetUserDataForShow(message.get(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.sendToClient(result1);
		}
		if (message.get(0).equals("AddBook")) { // here

			try {
				result = mysqlConnection.AddBook2(msg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("updateStudnetData")) {

			try {
				result = mysqlConnection.updateStudnetData(msg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("FindBookByName")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<Book> BookDetails;
			BookDetails = mysqlConnection.findAllBooksByBookName(message.get(1));
			client.sendToClient(BookDetails);
		}

		if (message.get(0).equals("FindBookByAuthor")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<Book> BookDetails;
			BookDetails = mysqlConnection.findAllBooksByBookAuthor(message.get(1));
			client.sendToClient(BookDetails);
		}
		if (message.get(0).equals("FindBookByCategory")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<Book> BookDetails;
			BookDetails = mysqlConnection.findAllBooksByCategory(message.get(1));
			client.sendToClient(BookDetails);
		}
		if (message.get(0).equals("FindBookByDescription")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<Book> BookDetails;
			BookDetails = mysqlConnection.findAllBooksByDescription(message.get(1));
			client.sendToClient(BookDetails);
		}
		if (message.get(0).equals("EditBook")) {
			try {
				mysqlConnection.UpdateBook(msg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("DeleteBook")) {
			try {
				mysqlConnection.DeleteBook(message.get(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.get(0).equals("IssueThisBook")) {
			// ArrayList<String> result3=new ArrayList<String>();
			int i;
			i = mysqlConnection.IssueBook(msg);
			client.sendToClient(i);
		}
		if (message.get(0).equals("ShowStudentHistory")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<HistoryForStudent> HistoryForStudentdetails;
			HistoryForStudentdetails = mysqlConnection.ShowStudentHistory(message.get(1));
			client.sendToClient(HistoryForStudentdetails);
		}
	/*	if (message.get(0).equals("getBookNametoshow")) {
			// ArrayList<String> result3=new ArrayList<String>();
			ArrayList<HistoryForStudent> BookDetails;
			BookDetails = mysqlConnection.getBookNametoshow(message.get(1));
			client.sendToClient(BookDetails);
		}*/

		if (message.get(0).equals("FindWorkersByFirstName")) {

			ArrayList<LibrarianNew> librarianDetailsByName;
			librarianDetailsByName = mysqlConnection.FindWorkersByFirstName(message.get(1));
			client.sendToClient(librarianDetailsByName);
		}
		if (message.get(0).equals("FindWorkersByLastName")) {

			ArrayList<LibrarianNew> librarianDetailsByLastName;
			librarianDetailsByLastName = mysqlConnection.FindWorkersByLastName(message.get(1));
			client.sendToClient(librarianDetailsByLastName);
		}
		if (message.get(0).equals("FindWorkersByID")) {

			ArrayList<LibrarianNew> librarianDetailsByID;
			librarianDetailsByID = mysqlConnection.FindWorkersByID(message.get(1));
			client.sendToClient(librarianDetailsByID);
		}
		if (message.get(0).equals("ZiadnewFun")) {
			mysqlConnection.ReuqedtedBookAuto(message.get(1));
		}
		if (message.get(0).equals("ClosestReturnDate")) {
			String Date;
			Date = mysqlConnection.ClosestReturnDate(message.get(1));
			client.sendToClient(Date);
		}
		if (message.get(0).equals("RequestBook")) {
			System.out.println("Echo Server");
			mysqlConnection.RequestBook(msg);
		}
		
		if (message.get(0).equals("ReturnBook")) {
			try {
				System.out.println("try in echo server");
				int res =mysqlConnection.returnbookbyid(msg); //send to client *****************
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(message.get(0).equals("ShowStudentHistoryForManager")) {
			ArrayList<HistoryForStudent> HistoryForStudentdetails;
				HistoryForStudentdetails =  mysqlConnection.ShowStudentHistoryForManager(message.get(1));
				client.sendToClient(HistoryForStudentdetails);
		}
		if (message.get(0).equals("RequestNewLoanDate")) {
			ArrayList<String> Respond;
			Respond = mysqlConnection.RequestNewLoanDate(msg);
			client.sendToClient(Respond);

		}
			
		if (message.get(0).equals("LoanReport")) {
			ArrayList<Double> LastSum = new ArrayList<Double>();
			ArrayList<Double> counterRegularBook, counterRequestedBook;
			counterRegularBook = mysqlConnection.LoanReportfuncregular(message.get(0));
			counterRequestedBook = mysqlConnection.LoanReportfuncrequested(message.get(0));
			
			LastSum.add(counterRegularBook.get(0)); // 0 => regular book loan sum
			LastSum.add(counterRegularBook.get(1)); // 1 => regular book sum
			LastSum.add(counterRequestedBook.get(0));// 2 => requested book loan sum
			LastSum.add(counterRequestedBook.get(1));// 3 =>requested book sum
			///System.out.println("Fist"+LastSum);
			
			ArrayList<Double> LastSum2 = new ArrayList<Double>();
			ArrayList<Double> counterRegularBook2, counterRequestedBook2;
			counterRegularBook2 = mysqlConnection.LoanReportMedianRegularfun();
			if(counterRegularBook2.size()==0) {
				counterRegularBook2.add(-1.0);
			}
			System.out.println("counterRegularBook2"+counterRegularBook2);
			LastSum.addAll(counterRegularBook2);
			System.out.println("LAST SUM"+LastSum);
			client.sendToClient(LastSum);
		}
		if (message.get(0).equals("LoanReportRequestedBook")) {
			ArrayList<Double> counterRequstedBook;
			counterRequstedBook= mysqlConnection.LoanReportMedianRegularfunRequestedBook();
			if(counterRequstedBook.size()==0)
				counterRequstedBook.add(-1.0);
			client.sendToClient(counterRequstedBook);
		}
		
		

	}

	protected void serverStarted() {

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods *****************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *        is entered.
	 */
	public void connectTOServer() {
		Alert alert;
		int port = 0; // Port to listen on

		port = DEFAULT_PORT; // Set port to 5555
		EchoServer sv = new EchoServer(port);
		try {
			System.out.println("Host:" + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sv.listen(); // Start listening for connections
			String msg = "Server listening for connections on port 5555 ---- SQL connection succeeded";
			alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
			alert.setHeaderText("Running the server");
			alert.setTitle("Information");
			// alert.show();
		} catch (Exception ex) {
			alert = new Alert(AlertType.WARNING, "ERROR - Could not listen for clients!", ButtonType.OK);
			alert.setHeaderText("Server is already running");
			alert.setTitle("Warning");
			alert.show();
		}

	}
}
// End of EchoServer class