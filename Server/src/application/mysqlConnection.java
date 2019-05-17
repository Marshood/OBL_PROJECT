package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import entites.Book;
import entites.HistoryForStudent;
import entites.LibrarianNew;
import entites.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class mysqlConnection {
	private static Connection con;
	private static Connection tempcon;
	private Statement statement;
	private String query = "SELECT * FROM obl.users";
	private ResultSet rs;
	public static int flag = 1;
	private static Statement stmt, stmt1, stmt2;
	static JOptionPane frame;
	public static ArrayList<Student> list = new ArrayList<Student>();
	// private static String date1;

	/*
	 * Marshood
	 */
	/*
	 * 
	 * @param con
	 * 
	 * @param userID
	 * 
	 * @param status
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	/*
	 * this method connectToDB is to connect with the DB using JDBC and drive
	 * manager
	 */
	public int connectToDB(String root,String pass) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			System.out.println("Error Connecting to DB connectToDB-mysql...");
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/obl", root, pass);
			System.out.println("SQL connection succeed");
			Thread t1 = new Thread(new ZiadThreads());
			t1.start();
			 
 
		} catch (SQLException ex) {/* handle any errors */
			
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
          return 0;
		}
		tempcon = con;

		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (rs.next()) {
			list.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5)));
		}
		return 1;

	}

	/*
	 * this method GetReturnLate to get all the user history have a return bookID
	 * ->book late
	 * 
	 * @param String bookID is to get the book id to counter the number of return
	 * late this method return the counter of late book return
	 */
	public static double GetReturnLate(String bookID) {
		java.sql.Statement stmt;
		double lendtime = 0;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM obl.history where idBook= '" + bookID + "' And isLate= 'Yes'");
			while (rs1.next()) {
				lendtime++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lendtime;

	}

	/*
	 * this method GetReturnNotLate to get the all subscriber was returned not late
	 * this method return the counter of this books
	 * 
	 */
	public static double GetReturnNotLate() {
		String sql = "SELECT * FROM  obl.history WHERE isLate='No'  ;";

		java.sql.Statement stmt;
		double lendtime = 0;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(sql);
			while (rs1.next()) {
				lendtime++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lendtime;

	}

	/*
	 * this method ReturnLateReportFunc to return late reports
	 * 
	 * @param string get the name of the func we come frome
	 * 
	 * @return ArrayList<Double> that have a lone period array list
	 * 
	 */
	public static ArrayList<Double> ReturnLateReportFunc(String string) {
		java.sql.Statement stmt;
		double count = 0;
		double bookcounter = 0;
		String Bookid;
		ArrayList<Double> LoanperiodarrayList = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.book ");
			while (rs1.next()) {
				bookcounter++;
				Bookid = rs1.getString(1);
				LoanperiodarrayList.add(GetReturnLate(Bookid));

			}
			LoanperiodarrayList.add(GetReturnNotLate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return LoanperiodarrayList;
	}

	/**
	 * this method GetLateUsersReports to get the number of late user's to show in
	 * the activities reports
	 * 
	 * @param message get in index 1 and index 2 the time we want to show the report
	 * @return array list in the index 0 we have a return late and in index 2 the
	 *         return book not late ...
	 * @throws SQLException
	 */
	public static ArrayList<String> GetLateUsersReports(ArrayList<String> message) throws SQLException {
		ArrayList<String> ret = new ArrayList<String>();
		stmt = con.createStatement();
		stmt1 = con.createStatement();
		stmt2 = con.createStatement();
		int i = 0, j = 0;
		try {
			ResultSet rs2 = stmt.executeQuery("Select * From obl.history where isLate ='" + "Yes"
					+ "'And ReturnDate >= '" + message.get(1) + "'And ReturnDate <= '" + message.get(2) + "';");
			while (rs2.next()) {
				i++;
			}
			ResultSet rs3 = stmt.executeQuery("Select * From obl.history where isLate ='" + "No"
					+ "'And ReturnDate >= '" + message.get(1) + "'And ReturnDate <= '" + message.get(2) + "';");
			while (rs3.next()) {
				j++;
			}
			ret.add("Succ");
			ret.add(Integer.toString(i));
			ret.add(Integer.toString(j));
			ret.add(Integer.toString(i + j));
			ResultSet rs4 = stmt.executeQuery("Select * From obl.book ;");
			int k = 0;
			while (rs4.next()) {
				k++;
			}
			ret.add(Integer.toString(k));
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return ret;
	}

	/*
	 * this method GetActiviteReportsResult to get the Activite reports result
	 * 
	 * @param msg in this array list we have a selected date to show
	 * 
	 * @return ArrayList String that we have in index 0 the locked counter and in
	 * index 1 the freeze counter and in index 2 the active counter , in index 3 the
	 * lock avg counter , in index 4 the the freeze avg counter
	 * 
	 */
	public static ArrayList<String> GetActiviteReportsResult(ArrayList<String> msg) throws SQLException {
		int ActiveSum = 0, FreezeSum = 0, LockedSum = 0;
		ArrayList<String> ret = new ArrayList<String>();
		stmt = con.createStatement();
		ResultSet rs2 = stmt.executeQuery("Select * From obl.history where HistoryType ='" + "Active"
				+ "'And ActiveDate >= '" + msg.get(1) + "'And ActiveDate <= '" + msg.get(2) + "';");
		while (rs2.next()) {
			ActiveSum++;
		}
		stmt = con.createStatement();

		ResultSet rs3 = stmt.executeQuery("Select * From obl.history where HistoryType ='" + "Locked"
				+ "'And LockDate >= '" + msg.get(1) + "'And LockDate <= '" + msg.get(2) + "';");
		while (rs3.next()) {
			LockedSum++;
		}
		stmt = con.createStatement();

		ResultSet rs4 = stmt.executeQuery("Select * From obl.history where HistoryType ='" + "Suspended"
				+ "'And FrezzeDate >= '" + msg.get(1) + "'And FrezzeDate <= '" + msg.get(2) + "';");
		while (rs4.next()) {
			FreezeSum++;
		}
		stmt = con.createStatement();

		ResultSet rs5 = stmt.executeQuery("Select * From obl.history where HistoryType ='" + "Freez"
				+ "'And FrezzeDate >= '" + msg.get(1) + "'And FrezzeDate <= '" + msg.get(2) + "';");
		while (rs5.next()) {
			FreezeSum++;
		}
		ret.add("Succ");
		ret.add(Integer.toString(LockedSum));
		ret.add(Integer.toString(FreezeSum));
		ret.add(Integer.toString(ActiveSum));
		double avg = ActiveSum + FreezeSum + LockedSum;
		double lockAvg = LockedSum / avg;
		double FreezeAvg = FreezeSum / avg;
		ret.add(Double.toString(lockAvg));
		ret.add(Double.toString(FreezeAvg));
		return ret;

	}

	/*
	 * this method Change_Login_Status_ziad to change the UserLoginStatus when
	 * heloged in or loged out
	 * 
	 * @param userID,status
	 * 
	 * @return true if succ ,else return false
	 * 
	 */
	public static boolean Change_Login_Status_ziad(Connection con, String userID, String status) throws SQLException {

		if (status.equals("yes")) {
			status = "no";
		} else {
			return false;
		}
		Statement stmt;
		try {
			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE obl.users SET UserLoginStatus ='" + status + "'WHERE UserID='" + userID + "';");
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return true;

	}

	/*
	 * this method updateSubscriberData to update the subscriber
	 * 
	 * @param msg is a object we casting it to array list String we get in this msg
	 * the new data we want to save in DB
	 * 
	 */
	public static ArrayList<String> updateSubscriberData(Object msg) throws SQLException {
		java.sql.Statement stmt;
		ArrayList<String> updatedata = (ArrayList<String>) msg;
		ArrayList<String> res = new ArrayList<String>();
		String q = "UPDATE obl.users SET UserPass = '" + updatedata.get(2) + "' WHERE StudentID = '" + updatedata.get(2)
				+ "';";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE obl.users SET " + "UserType = '" + updatedata.get(3) + "', UserPass ='"
					+ updatedata.get(2) + ///
					"',MemberShipStatus ='" + updatedata.get(4) + "',FirstName ='" + updatedata.get(5) + "',LastName ='"
					+ updatedata.get(6) + "',Email ='" + updatedata.get(7) + "',PhoneNumber ='" + updatedata.get(8)
					+ "',GradationDate ='" + updatedata.get(9) + "'WHERE UserID='" + updatedata.get(1) + "';");
			res.add("AddedSucc");
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res.add("AddedNotSuc");
		return res;
	}

	/*
	 * this method GetUserDataForShowScriber is to get a user data for show
	 * 
	 * @UserID in this String we get a user id we want to show
	 * 
	 * @return ArrayList String that have a all data about the specific user we want
	 * to show
	 */
	public static ArrayList<String> GetUserDataForShowScriber(String UserID) throws SQLException {
		java.sql.Statement stmt;
		ArrayList<String> UserLogin = new ArrayList<String>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.users  WHERE UserID = " + UserID);
			if (rs1.next()) {
				UserLogin.add("SuccessfulL");
				UserLogin.add(rs1.getString(1));// userID
				UserLogin.add(rs1.getString(2));// userpass
				UserLogin.add(rs1.getString(3));// userType
				UserLogin.add(rs1.getString(5));// MemberShipStatus
				UserLogin.add(rs1.getString(6));// first name
				UserLogin.add(rs1.getString(7));// last name
				UserLogin.add(rs1.getString(8));// email
				UserLogin.add(rs1.getString(9));// phone
				UserLogin.add(rs1.getString(10));// GradationDate
				UserLogin.add(rs1.getString(11));// Organization
				UserLogin.add(rs1.getString(12));// late counter
				return UserLogin;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserLogin;
	}

	/*
	 * 
	 * this method AddNewSubscriber is to add a new subscriber in DB
	 * 
	 * @param msg2 in this msg we have all the information we want to add about the
	 * new subscriber
	 * 
	 * @retuen ArrayList String that have in index 0 if the add succ or not
	 * 
	 */
	public static ArrayList<String> AddNewSubscriber(Object msg2) throws SQLException {
		ArrayList<String> msg = (ArrayList<String>) msg2;
		ArrayList<String> update = new ArrayList<String>();
		String num = "1";
		boolean check = CheckUserExits(msg.get(1));
		if (check) {
			update.add("UserExist");
			return update;
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			try {
				stmt = con.createStatement();
				{
					stmt.executeUpdate(
							"INSERT INTO users (UserID ,UserPass ,UserType,UserLoginStatus,MemberShipStatus,FirstName,LastName,Email,PhoneNumber,GradationDate,Organization,lateCounter) VALUES('"
									+ msg.get(1) + "','" + msg.get(2) + "','" + num + "','" + "no" + "','" + "Active"
									+ "','" + msg.get(3) + "','" + msg.get(4) + "','" + msg.get(5) + "','" + msg.get(6)
									+ "','" + msg.get(7) + "','" + msg.get(8) + "','" + "0" + "');");

					String q1 = "INSERT INTO history (UserId,HistoryType,ActiveDate) VALUES('" + msg.get(1) + "','"
							+ "Active" + "','" + dateFormat.format(date) + "')";
					stmt = con.createStatement();
					stmt.executeUpdate(q1);
					update.add("AddNewSubscriberResultSuccess");
					return update;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				update.add("AddNewSubscriberResultNotSuccess");
				return update;
			}
		}

	}

	/*
	 * this method findSubscriberByFisrtName is to search the subscriber by first
	 * name
	 * 
	 * @param StudnetFirstName in this param we get the first name from the client
	 * and after this we check if the user exist
	 * 
	 * @return ArrayList <User> that have the all user we have the same first day
	 * 
	 */
	public static ArrayList<User> findSubscriberByFisrtName(String StudnetFirstName) {
		// TODO Auto-generated method stub

		ArrayList<User> StudentInformation = new ArrayList<User>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.users where FirstName ='" + StudnetFirstName + "'And  UserType ='1';");
			while (rs2.next()) {
				User Students = new User();
				Students.setUserID(rs2.getString(1));
				Students.setUserPass(rs2.getString(2));
				Students.setUserType(rs2.getString(3));
				Students.setMemberShipStatus(rs2.getString(5));
				Students.setFisrtName(rs2.getString(6));
				Students.setLastName(rs2.getString(7));
				Students.setEmail(rs2.getString(8));
				Students.setPhone(rs2.getString(9));
				Students.setGradationDate(rs2.getString(10));
				Students.setOrganization(rs2.getString(11));
				Students.setCounterLate(rs2.getString(12));
				StudentInformation.add(Students);
			}
			return StudentInformation;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return StudentInformation;
	}

	/*
	 * this method UsersFreezedReturn is to return the all user that we have a
	 * member ship status Suspended or freez
	 * 
	 * @return ArrayList<user> in this ArrayList we have the all user that we
	 * suspended or freeze
	 * 
	 */
	public static ArrayList<User> UsersFreezedReturn() {
		// TODO Auto-generated method stub
		ArrayList<User> StudentInformation = new ArrayList<User>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery("Select * From obl.users where MemberShipStatus ='" + "Suspended"
					+ "'or MemberShipStatus='" + "Freez" + "'And  UserType ='1';");
			while (rs2.next()) {
				User Students = new User();
				Students.setUserID(rs2.getString(1));
				Students.setUserPass(rs2.getString(2));
				Students.setUserType(rs2.getString(3));
				Students.setMemberShipStatus(rs2.getString(5));
				Students.setFisrtName(rs2.getString(6));
				Students.setLastName(rs2.getString(7));
				Students.setEmail(rs2.getString(8));
				Students.setPhone(rs2.getString(9));
				Students.setGradationDate(rs2.getString(10));
				Students.setOrganization(rs2.getString(11));
				Students.setCounterLate(rs2.getString(12));
				Students.setGetEndFreezingDate(rs2.getString(13));

				StudentInformation.add(Students);
			}
			return StudentInformation;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return StudentInformation;
	}

	/*
	 * this method UsersLocked is to return the all user that we have a member ship
	 * status locked
	 * 
	 * @return ArrayList<user> in this ArrayList we have the all user that we locked
	 * 
	 */
	public static ArrayList<User> UsersLocked() {
		// TODO Auto-generated method stub
		ArrayList<User> StudentInformation = new ArrayList<User>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.users where MemberShipStatus ='" + "Locked" + "'And  UserType ='1';");
			while (rs2.next()) {
				User Students = new User();
				Students.setUserID(rs2.getString(1));
				Students.setUserPass(rs2.getString(2));
				Students.setUserType(rs2.getString(3));
				Students.setMemberShipStatus(rs2.getString(5));
				Students.setFisrtName(rs2.getString(6));
				Students.setLastName(rs2.getString(7));
				Students.setEmail(rs2.getString(8));
				Students.setPhone(rs2.getString(9));
				Students.setGradationDate(rs2.getString(10));
				Students.setOrganization(rs2.getString(11));
				Students.setCounterLate(rs2.getString(12));
				Students.setGetEndFreezingDate(rs2.getString(13));
				StudentInformation.add(Students);
			}
			return StudentInformation;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return StudentInformation;
	}

	/*
	 * this method UsersActive is to return the all user that we have a member ship
	 * status active
	 * 
	 * @return ArrayList<user> in this ArrayList we have the all user that we active
	 * 
	 */
	public static ArrayList<User> UsersActive() {
		// TODO Auto-generated method stub
		ArrayList<User> StudentInformation = new ArrayList<User>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.users where MemberShipStatus ='" + "Active" + "'And  UserType ='1';");
			while (rs2.next()) {
				User Students = new User();
				Students.setUserID(rs2.getString(1));
				Students.setUserPass(rs2.getString(2));
				Students.setUserType(rs2.getString(3));
				Students.setMemberShipStatus(rs2.getString(5));
				Students.setFisrtName(rs2.getString(6));
				Students.setLastName(rs2.getString(7));
				Students.setEmail(rs2.getString(8));
				Students.setPhone(rs2.getString(9));
				Students.setGradationDate(rs2.getString(10));
				Students.setOrganization(rs2.getString(11));
				Students.setCounterLate(rs2.getString(12));
				Students.setGetEndFreezingDate(rs2.getString(13));
				StudentInformation.add(Students);
			}
			return StudentInformation;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return StudentInformation;
	}

	/*
	 * this method changetoActiveAfterFreeze is to change user that have a freeze
	 * status member ship or suspended and after this change to active and whare the
	 * end freezing date we put a null
	 * 
	 * @return true if succ else false
	 */
	public static boolean changetoActiveAfterFreeze(String userID) {
		// TODO Auto-generated method stub
		String q = "UPDATE obl.users SET EndFreezingDate = '" + "" + "' WHERE UserID = '" + userID + "';";
		try {
			stmt = tempcon.createStatement();
			stmt.executeUpdate(q);
			changeStatus(userID, "Active");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * this method changeStatus get a student id and the status we want to change it
	 * 
	 * @param id, Status id s a id that we want to change the status and status is
	 * the new status
	 * 
	 * @return true if succ else false
	 * 
	 */
	public static boolean changeStatus(String id, String Status) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String q = "UPDATE obl.users SET MemberShipStatus = '" + Status + "' WHERE UserID = '" + id + "';";
		String q1 = "INSERT INTO history (UserId,HistoryType,ActiveDate) VALUES('" + id + "','" + Status + "','"
				+ dateFormat.format(date) + "')";
		String q2 = "INSERT INTO history (UserId,HistoryType,LockDate) VALUES('" + id + "','" + Status + "','"
				+ dateFormat.format(date) + "')";
		String q3 = "INSERT INTO history (UserId,HistoryType,FrezzeDate) VALUES('" + id + "','" + Status + "','"
				+ dateFormat.format(date) + "')";

		try {
			stmt = tempcon.createStatement();
			stmt.executeUpdate(q);

			if (Status.equals("Suspended")) {
				stmt.executeUpdate(q3);
			}
			if (Status.equals("Active")) {
				stmt.executeUpdate(q1);
			}
			if (Status.equals("Locked")) {
				stmt.executeUpdate(q2);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}

	/*
	 * this method FreezeStudentWithDate is to freeze the specific student to
	 * specific date to unlock
	 * 
	 * @param message in index 1 get a userid , index 2 get a date to end freezing
	 * 
	 */
	public static boolean FreezeStudentWithDate(ArrayList<String> message) {
		// TODO Auto-generated method stub
		String q = "UPDATE obl.users SET EndFreezingDate = '" + message.get(2) + "' WHERE UserID = '" + message.get(1)
				+ "';";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try {
			stmt = tempcon.createStatement();
			stmt.executeUpdate(q);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return changeStatus(message.get(1), "Suspended");
	}

	/*
	 * this method findSubscriberByLastName is to search the subscriber by last name
	 * 
	 * @param StudnetFirstName in this param we get the last name from the client
	 * and after this we check if the user exist
	 * 
	 * @return ArrayList <User> that have the all user we have the same last day
	 * 
	 */
	public static ArrayList<User> findSubscriberByLastName(String StudnetLastName) {
		// TODO Auto-generated method stub
		ArrayList<User> StudentInformation = new ArrayList<User>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.users where LastName ='" + StudnetLastName + "'And  UserType ='1';");
			while (rs2.next()) {
				User Students = new User();
				Students.setUserID(rs2.getString(1));
				Students.setUserPass(rs2.getString(2));
				Students.setUserType(rs2.getString(3));
				Students.setMemberShipStatus(rs2.getString(5));
				Students.setFisrtName(rs2.getString(6));
				Students.setLastName(rs2.getString(7));
				Students.setEmail(rs2.getString(8));
				Students.setPhone(rs2.getString(9));
				Students.setGradationDate(rs2.getString(10));
				Students.setOrganization(rs2.getString(11));
				Students.setCounterLate(rs2.getString(12));
				StudentInformation.add(Students);
			}
			return StudentInformation;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return StudentInformation;
	}

	///////
	/*
	 * this method SaveChangeInDB is to save the new status member ship
	 * 
	 * @param Stundet s , int row this student that have a student information that
	 * we want to change ,
	 * 
	 */
	public static void SaveChangeInDB(Student s, int row) {
		list.set(row, s);
		String q = "UPDATE olb.users SET StatusMembership = '" + s.getStudentName() + "' WHERE StudentID = '"
				+ s.getStudID() + "';";

		try {
			stmt = tempcon.createStatement();
			stmt.executeUpdate(q);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// adding Book

	/*
	 * 
	 * this method AddBook2 to add a new book in DB
	 * 
	 * @param msg2 we get the all new date that we want to add in DB about the new
	 * book
	 * 
	 */
	public static ArrayList<String> AddBook2(Object msg2) throws SQLException, IOException { // Fixes
		ArrayList<String> msg = (ArrayList<String>) msg2;

		java.sql.Statement stmt;
		String sql = "INSERT INTO obl.book(NameBook,Author,EditionNumber,Publish,Category,Description,CopiesNumber,PurchaseDate,Shelf,Requested,PDF) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		byte[] by_new = Base64.getDecoder().decode(msg.get(11));
		java.sql.Blob blob = con.createBlob();
		blob.setBytes(1, by_new);
		ps.setBlob(11, blob);
		ps.setString(1, msg.get(1));
		ps.setString(2, msg.get(2));
		ps.setString(3, msg.get(3));
		ps.setString(4, msg.get(4));
		ps.setString(5, msg.get(5));
		ps.setString(6, msg.get(6));
		ps.setString(7, msg.get(7));
		ps.setString(8, msg.get(8));
		ps.setString(9, msg.get(9));
		ps.setString(10, msg.get(10));
		ps.executeUpdate();
		return null;

	}

	/*
	 * this method AddBookPDF to add a pdf book to db
	 * 
	 * @param we get a pdf path and the bookid and adding it in DB
	 * 
	 */
	public static void AddBookPDF(String PDF, String BookId) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE obl.book SET PDF ='" + PDF + "'WHERE idBook='" + BookId + "';");
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	/*
	 * this method loginUser is to login the user to OBL
	 * 
	 * @param UserID,pass we get the userid and the pass and check if the user exist
	 * and have ths same pass if succ they log in
	 * 
	 */
	public static ArrayList<String> loginUser(String UserID, String pass)

	{
		java.sql.Statement stmt;
		ArrayList<String> UserLogin = new ArrayList<String>();
		try {

			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.users  WHERE UserID = " + UserID);
			if (rs1.next())// if the student is existing
			{
				if (rs1.getString(2).equals(pass)) {

					if (rs1.getString(5).equals("Active")&&rs1.getString(4).equals("no")) {
						Change_Login_Status(con, UserID, pass, rs1.getString(4));
						UserLogin.add("SuccessfulLogin");
						UserLogin.add(rs1.getString(3));
						UserLogin.add(rs1.getString(1));
						UserLogin.add(rs1.getString(8));
					} else if (rs1.getString(5).equals("Suspended")) {
						UserLogin.add("Suspended");
					} else if (rs1.getString(5).equals("Locked")) {
						UserLogin.add("Locked");
					} else if (rs1.getString(5).equals("Freez")) {
						UserLogin.add("Freez");
					} else {
						UserLogin.add("alreadyloggedin");
					}
					return UserLogin;
				} else {
					UserLogin.add("WrongPassword");
				}
				return UserLogin;
			} else {
				UserLogin.add("UserDoesn'tExist");
			}
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return UserLogin;
	}

	/**
	 * this method ziad is to call the user from the database and to change his
	 * userlogin status with the method Change_login_Status_ziad
	 * 
	 * @param UserID
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> ziad(String UserID) throws SQLException {
		java.sql.Statement stmt;
		ArrayList<String> UserLogin = new ArrayList<String>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.users  WHERE UserID ='" + UserID + "';");
			if (rs1.next())// if the student is existing
				Change_Login_Status_ziad(con, UserID, rs1.getString(4));
			else
				System.out.println("Cann't Find user to logout ziad-mysql...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserLogin;
	}

	/**
	 * Change_Login_Status this method is to change the status of the user in the
	 * login menu when he logs in to change the fieldin the databse from no to yes
	 * to notify that the user has been logged out so that his account can't bre
	 * logged to from two computers method
	 * 
	 * @param con
	 * @param userID
	 * @param userPass
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public static void Change_Login_Status(Connection con, String userID, String userPass, String status)
			throws SQLException {
		if (status.equals("no")) {
			status = "yes"; // change to yes
		}
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE obl.users SET UserLoginStatus ='" + status + "'WHERE UserID='" + userID + "';");
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	/*
	 * this method CheckUserExits that check if the user exits in db
	 * 
	 * @param userid , get a user id we want to check
	 * 
	 * @return return boolean true or false
	 * 
	 */
	public static boolean CheckUserExits(String userID) throws SQLException {
		boolean flag2 = false;
		Statement statment;
		statment = con.createStatement();
		ResultSet rs2 = statment.executeQuery("Select * From obl.users where userID=" + userID + ";");
		if (rs2.next())
			flag2 = true;
		return flag2;
	}

	/*
	 * this method GetUserDataForShow if to show the studnet date in my account
	 * 
	 * @param UserID , get a userid we want to show diteals about it
	 * 
	 * @return ArrayList String that have a data about the specific user
	 * 
	 */
	// @author Aroob
	// this function is to get data from db to fill in editaccount student page

	public static ArrayList<String> GetUserDataForShow(String UserID) throws SQLException {
		java.sql.Statement stmt;
		ArrayList<String> UserLogin = new ArrayList<String>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.users  WHERE UserID = " + UserID);
			if (rs1.next()) {
				UserLogin.add(rs1.getString(1));// cardid=>0
				UserLogin.add(rs1.getString(8));// email=>1
				UserLogin.add(rs1.getString(9));// phone=>2
				UserLogin.add(rs1.getString(6));// firstnme=>3
				UserLogin.add(rs1.getString(7));// lastname=>4
				UserLogin.add(rs1.getString(2));// password=>5
				UserLogin.add(rs1.getString(5));// status=>6

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserLogin;
	}

	/*
	 * this method updateStudnetData is to uodate the student date from myaccount
	 * 
	 * @param msg2 , the method get get a new data about the user we update it in DB
	 * 
	 */
	public static ArrayList<String> updateStudnetData(Object msg2) throws SQLException {
		java.sql.Statement stmt;
		ArrayList<String> updatedata = (ArrayList<String>) msg2;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE obl.users SET Email ='" + updatedata.get(2) + "', PhoneNumber ='"
					+ updatedata.get(1) + "', UserPass ='" + updatedata.get(3) + "', FirstName ='" + updatedata.get(4)
					+ "', LastName ='" + updatedata.get(5) + "'WHERE UserID='" + updatedata.get(6) + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updatedata;
	}

	/*
	 * this method findAllBooksByBookName get the book name we want to search in DB
	 * 
	 * @Param bookName , this is a book name that we want to search
	 * 
	 * @return ArrayList<Book> that all book we found that have the same name
	 */
	public static ArrayList<Book> findAllBooksByBookName(String bookName) throws IOException {
		java.sql.Statement stmt;
		ArrayList<Book> BookDetails = new ArrayList<Book>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where NameBook ='" + bookName + "';");
			while (rs1.next()) {
				Book book = new Book();
				book.setBookID(rs1.getString(1));
				book.setBookName(rs1.getString(2));
				book.setBookAuthor(rs1.getString(3));
				book.setEditionNumber(rs1.getString(4));
				book.setPublishDate(rs1.getString(5));
				book.setBookCategory(rs1.getString(6));
				book.setBookDescription(rs1.getString(7));
				book.setCopyNumber(rs1.getString(8));
				book.setLoanedCopies(rs1.getString(9));
				book.setPurchaseDate(rs1.getString(10));
				book.setShelfDate(rs1.getString(11));
				book.setRequested(rs1.getString(12));
				if (rs1.getBytes(13) != null) {
					byte[] bytes = rs1.getBytes(13);
					book.setPDF(Base64.getEncoder().encodeToString(bytes));
				}
				book.setRequestedCounter(rs1.getString(14));
				BookDetails.add(book);
			}
			if (BookDetails.size() == 0) {
				return null;
			} else {
				return BookDetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * this method findAllBooksByBookAuthor get the BookAuthor we want to search in
	 * DB
	 * 
	 * @Param bookName , this is a BookAuthor that we want to search
	 * 
	 * @return ArrayList<Book> that all book we found that have the same BookAuthor
	 */
	public static ArrayList<Book> findAllBooksByBookAuthor(String bookName) throws IOException {
		java.sql.Statement stmt;
		ArrayList<Book> BookDetails = new ArrayList<Book>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where Author ='" + bookName + "';");
			while (rs1.next()) {
				Book book = new Book();
				book.setBookID(rs1.getString(1));
				book.setBookName(rs1.getString(2));
				book.setBookAuthor(rs1.getString(3));
				book.setEditionNumber(rs1.getString(4));
				book.setPublishDate(rs1.getString(5));
				book.setBookCategory(rs1.getString(6));
				book.setBookDescription(rs1.getString(7));
				book.setCopyNumber(rs1.getString(8));
				book.setLoanedCopies(rs1.getString(9));
				book.setPurchaseDate(rs1.getString(10));
				book.setShelfDate(rs1.getString(11));
				book.setRequested(rs1.getString(12));
				if (rs1.getBytes(13) != null) {
					byte[] bytes = rs1.getBytes(13);
					book.setPDF(Base64.getEncoder().encodeToString(bytes));
				}
				book.setRequestedCounter(rs1.getString(14));
				BookDetails.add(book);
			}
			if (BookDetails.size() == 0) {
				return null;
			} else {
				return BookDetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * this method UpdateBook is to update specific book
	 * 
	 * 
	 * @Param msg , in the new data about the book in index 1 we have a book name
	 */
	public static void UpdateBook(Object msg2) throws SQLException {
		java.sql.Statement stmt;
		boolean flag = false;
		int X = 0;
		ArrayList<String> updatedata = (ArrayList<String>) msg2;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("UPDATE obl.book SET NameBook ='" + updatedata.get(1) + "', Author ='"
					+ updatedata.get(2) + "', EditionNumber ='" + updatedata.get(3) + "', Publish ='"
					+ updatedata.get(4) + "', Category ='" + updatedata.get(5) + "', Description ='" + updatedata.get(6)
					+ "', CopiesNumber ='" + updatedata.get(7) + "', PurchaseDate ='" + updatedata.get(8)
					+ "', Shelf ='" + updatedata.get(9) + "', Requested ='" + updatedata.get(10) + "'WHERE idBook='"
					+ updatedata.get(11) + "';");
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where idBook ='" + updatedata.get(11) + "';");
			while (rs1.next() && flag == false) {
				flag = true;
				X = (Integer.parseInt(rs1.getString(8))) - (Integer.parseInt(rs1.getString(9)));
			}
			if (X > 0) {
				ReuqedtedBookAuto(updatedata.get(11));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * this method DeleteBook is to delete the specific book
	 * 
	 * @param msg2 get a book id we want to deletee and delete it
	 * 
	 */
	public static void DeleteBook(String msg2) throws SQLException {
		String sql = "DELETE FROM obl.book WHERE idBook = ?";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, msg2);
		pstmt.executeUpdate();
	}

	/*
	 * this method DeleteHistory to delete history
	 * 
	 * @param msg2 is the history id we want to delete
	 * 
	 */
	public static void DeleteHistory(String msg2) throws SQLException {
		String sql = "DELETE FROM obl.history WHERE HistoryID = ?";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, msg2);
		pstmt.executeUpdate();
	}

	/*
	 * this method findAllBooksByCategory if to search the all book that have a same
	 * category
	 * 
	 * @param bookName, is the category we want to show
	 * 
	 * @return ArryList <book> that have the all book in DB have the same Category
	 * 
	 */
	public static ArrayList<Book> findAllBooksByCategory(String bookName) throws IOException {
		java.sql.Statement stmt;
		ArrayList<Book> BookDetails = new ArrayList<Book>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where Category ='" + bookName + "';");
			while (rs1.next()) {
				Book book = new Book();
				book.setBookID(rs1.getString(1));
				book.setBookName(rs1.getString(2));
				book.setBookAuthor(rs1.getString(3));
				book.setEditionNumber(rs1.getString(4));
				book.setPublishDate(rs1.getString(5));
				book.setBookCategory(rs1.getString(6));
				book.setBookDescription(rs1.getString(7));
				book.setCopyNumber(rs1.getString(8));
				book.setLoanedCopies(rs1.getString(9));
				book.setPurchaseDate(rs1.getString(10));
				book.setShelfDate(rs1.getString(11));
				book.setRequested(rs1.getString(12));
				if (rs1.getBytes(13) != null) {
					byte[] bytes = rs1.getBytes(13);
					book.setPDF(Base64.getEncoder().encodeToString(bytes));
				}
				book.setRequestedCounter(rs1.getString(14));
				BookDetails.add(book);
			}
			if (BookDetails.size() == 0) {
				return null;
			} else {
				return BookDetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * this method findAllBooksByDescription if to search the all book that have a
	 * same Description
	 * 
	 * @param bookName, is the Description we want to show
	 * 
	 * @return ArryList <book> that have the all book in DB have the same
	 * Description
	 * 
	 */
	public static ArrayList<Book> findAllBooksByDescription(String bookName) throws IOException, SQLException {

		java.sql.PreparedStatement stmt;

		ResultSet resultSet = null;
		ArrayList<Book> BookDetails = new ArrayList<Book>();
		String Sql = "SELECT * from obl.book where `Description` LIKE ?";

		stmt = con.prepareStatement(Sql);

		stmt.setString(1, "%" + bookName + "%");

		resultSet = stmt.executeQuery();

		while (resultSet.next()) {
			Book book = new Book();
			book.setBookID(resultSet.getString(1));
			book.setBookName(resultSet.getString(2));
			book.setBookAuthor(resultSet.getString(3));
			book.setEditionNumber(resultSet.getString(4));
			book.setPublishDate(resultSet.getString(5));
			book.setBookCategory(resultSet.getString(6));
			book.setBookDescription(resultSet.getString(7));
			book.setCopyNumber(resultSet.getString(8));
			book.setLoanedCopies(resultSet.getString(9));
			book.setPurchaseDate(resultSet.getString(10));
			book.setShelfDate(resultSet.getString(11));
			book.setRequested(resultSet.getString(12));
			if (resultSet.getBytes(13) != null) {
				byte[] bytes = resultSet.getBytes(13);
				book.setPDF(Base64.getEncoder().encodeToString(bytes));
			}
			book.setRequestedCounter(resultSet.getString(14));
			BookDetails.add(book);
			if (BookDetails.size() == 0) {
				return null;
			} else {
				return BookDetails;
			}
		}

		/*
		 * java.sql.Statement stmt; ArrayList<Book> BookDetails = new ArrayList<Book>();
		 * try { stmt = con.createStatement(); ResultSet rs1 =
		 * stmt.executeQuery("Select * From obl.book where Description ='" + bookName +
		 * "';"); while (rs1.next()) { Book book = new Book();
		 * book.setBookID(rs1.getString(1)); book.setBookName(rs1.getString(2));
		 * book.setBookAuthor(rs1.getString(3));
		 * book.setEditionNumber(rs1.getString(4));
		 * book.setPublishDate(rs1.getString(5));
		 * book.setBookCategory(rs1.getString(6));
		 * book.setBookDescription(rs1.getString(7));
		 * book.setCopyNumber(rs1.getString(8)); book.setLoanedCopies(rs1.getString(9));
		 * book.setPurchaseDate(rs1.getString(10));
		 * book.setShelfDate(rs1.getString(11)); book.setRequested(rs1.getString(12));
		 * if(rs1.getBytes(13)!=null) { byte[] bytes = rs1.getBytes(13);
		 * book.setPDF(Base64.getEncoder().encodeToString(bytes)); }
		 * BookDetails.add(book); } if (BookDetails.size() == 0) { return null; } else {
		 * return BookDetails; } } catch (SQLException e) { e.printStackTrace(); }
		 */
		return null;

	}

	/*
	 * 
	 * this method ShowStudentHistory is to show the history that have a specific
	 * student
	 * 
	 * @param userid ,get the user id for the student
	 * 
	 * @return ArrayList<History> this is all the history the student have in DB
	 * 
	 */
	public static ArrayList<HistoryForStudent> ShowStudentHistory(String userid) throws IOException {
		java.sql.Statement stmt;
		ArrayList<HistoryForStudent> HistoryForStudentdetails = new ArrayList<HistoryForStudent>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.history where UserId ='" + userid + "';");
			while (rs1.next()) {
				HistoryForStudent history = new HistoryForStudent();
				history.setBookID(rs1.getString(2));
				history.setHistoryType(rs1.getString(3));
				history.setPickUpDate(rs1.getString(4));
				history.setReturnDate(rs1.getString(5));
				history.setRequestedDate(rs1.getString(6));
				history.setLostBook(rs1.getString(7));
				history.setWasLate(rs1.getString(8));
				history.setBookName(getBookNametoshow2(rs1.getString(2)));
				HistoryForStudentdetails.add(history);
			}

			if (HistoryForStudentdetails.size() == 0) {
				return null;
			} else {
				return HistoryForStudentdetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * this method getBookNametoshow2 to get the book name to show we get a book id
	 * 
	 * @param BOOKID , this is a book id
	 * 
	 * @return the book name or null if not exist
	 * 
	 */
	public static String getBookNametoshow2(String BOOKID) throws IOException {
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery("Select * From obl.book where idBook ='" + BOOKID + "';");
			while (rs2.next()) {
				return rs2.getString(2);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 
	 * this method FindWorkersByFirstName is to search worker that have the same
	 * first name
	 * 
	 * @param FirstName ,get it from the client ant search it in table book
	 * 
	 * @return ArrayList<LibrarianNew> is a array list that have all the worker we
	 * have the same first name
	 */
	public static ArrayList<LibrarianNew> FindWorkersByFirstName(String FirstName) throws IOException {
		java.sql.Statement stmt;
		ArrayList<LibrarianNew> librarianDetailsByName = new ArrayList<LibrarianNew>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt
					.executeQuery("Select * From obl.users where FirstName ='" + FirstName + "'And Not UserType ='1';");
			while (rs2.next()) {
				LibrarianNew librarian = new LibrarianNew();
				librarian.setUserID(rs2.getString(1));
				librarian.setFirstName(rs2.getString(6));
				librarian.setLastName(rs2.getString(7));
				librarian.setEmail(rs2.getString(8));
				librarian.setPhoneNumber(rs2.getString(9));
				librarian.setOrganization(rs2.getString(11));
				librarianDetailsByName.add(librarian);
			}

			if (librarianDetailsByName.size() == 0) {
				return null;
			} else {
				return librarianDetailsByName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 
	 * this method FindWorkersByLastName is to search worker that have the same Last
	 * name
	 * 
	 * @param LasttName ,get it from the client ant search it in table book
	 * 
	 * @return ArrayList<LibrarianNew> is a array list that have all the worker we
	 * have the same Last name
	 */
	public static ArrayList<LibrarianNew> FindWorkersByLastName(String LastName) throws IOException {
		java.sql.Statement stmt;
		ArrayList<LibrarianNew> librarianDetailsByLastName = new ArrayList<LibrarianNew>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt
					.executeQuery("Select * From obl.users where LastName ='" + LastName + "'And Not UserType ='1';");
			while (rs2.next()) {
				LibrarianNew librarian = new LibrarianNew();
				librarian.setUserID(rs2.getString(1));
				librarian.setFirstName(rs2.getString(6));
				librarian.setLastName(rs2.getString(7));
				librarian.setEmail(rs2.getString(8));
				librarian.setPhoneNumber(rs2.getString(9));
				librarian.setOrganization(rs2.getString(11));
				librarianDetailsByLastName.add(librarian);
			}

			if (librarianDetailsByLastName.size() == 0) {
				return null;
			} else {
				return librarianDetailsByLastName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 
	 * this method FindWorkersByID is to search worker that have the same ID
	 * 
	 * @param ID ,get it from the client ant search it in table book
	 * 
	 * @return ArrayList<LibrarianNew> is a array list that have all the worker we
	 * have the same ID
	 */
	public static ArrayList<LibrarianNew> FindWorkersByID(String UserID) throws IOException {
		java.sql.Statement stmt;
		ArrayList<LibrarianNew> librarianDetailsByID = new ArrayList<LibrarianNew>();
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt
					.executeQuery("Select * From obl.users where UserID ='" + UserID + "'And Not UserType ='1';");
			while (rs2.next()) {
				LibrarianNew librarian = new LibrarianNew();
				librarian.setUserID(rs2.getString(1));
				librarian.setFirstName(rs2.getString(6));
				librarian.setLastName(rs2.getString(7));
				librarian.setEmail(rs2.getString(8));
				librarian.setPhoneNumber(rs2.getString(9));
				librarian.setOrganization(rs2.getString(11));
				librarianDetailsByID.add(librarian);
			}

			if (librarianDetailsByID.size() == 0) {
				return null;
			} else {
				return librarianDetailsByID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * this method IssueBook is to issue a book
	 * 
	 * @param msg we get in index 2 the user id we want to issue
	 * 
	 * @return 1 if succ else -1
	 */
	public static int IssueBook(Object msg) throws SQLException {
		ArrayList<String> updatedata = (ArrayList<String>) msg;

		boolean flag = false;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.users where userID ='" + updatedata.get(2) + "'And MemberShipStatus ='Active';");
			while (rs2.next()) {
				flag = true;
			}
			if (flag) {

				if (updatedata.get(3).equals("Yes")) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime tomorrow = now.plusDays(3);
					stmt.executeUpdate(
							"INSERT INTO history (UserId,idBook,HistoryType,PickUpDate,shouldreturndate) VALUES('"
									+ updatedata.get(2) + "','" + updatedata.get(1) + "','" + "loaned" + "','"
									+ dtf.format(now) + "','" + dtf.format(tomorrow) + "')");
					stmt.executeUpdate("UPDATE obl.book SET LoanedCopies ='" + updatedata.get(4) + "'WHERE idBook='"
							+ updatedata.get(1) + "';");
					return 1;
				} else {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime tomorrow = now.plusDays(14);
					stmt.executeUpdate(
							"INSERT INTO history (UserId,idBook,HistoryType,PickUpDate,shouldreturndate) VALUES('"
									+ updatedata.get(2) + "','" + updatedata.get(1) + "','" + "loaned" + "','"
									+ dtf.format(now) + "','" + dtf.format(tomorrow) + "')");
					stmt.executeUpdate("UPDATE obl.book SET LoanedCopies ='" + updatedata.get(4) + "'WHERE idBook='"
							+ updatedata.get(1) + "';");

					return 1;
				}
			} else {
				return -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	/*
	 * this method ReuqedtedBookAuto is if that have a order to book and have it
	 * know we send to the subscriber email to tell him about the book
	 * 
	 */
	public static void ReuqedtedBookAuto(String BookID) throws SQLException {
		boolean flag = false;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"Select * From obl.history where idBook ='" + BookID + "'And HistoryType ='request';");
			while (rs2.next() && flag == false) {
				flag = true;
				SendEmailForRequestedBook(rs2.getString(1));
				DeleteHistory(rs2.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error In ReuqedtedBookAuto-mysql....");
		}
	}

	/*
	 * this method SendEmailForRequestedBook is to send a email
	 * 
	 * @param email to send
	 * 
	 */
	public static void SendEmailForRequestedBook(String UserID) throws SQLException {
		boolean flag = false;
		// String UserId;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery("Select * From obl.users where UserID ='" + UserID + "';");
			while (rs2.next() && flag == false) {
				flag = true;
				SendMail.sendEmail("The Requested Book Is Now Available",
						"The book you have requested is now available in the library you have two days to take it.",
						rs2.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error In SendEmailForRequestedBook-mysql....");
		}

	}

	/*
	 * this method SendEmailForReturningBook is to send a email
	 * 
	 * @param UserID , this is the id we want to send email for this and after this
	 * we get a emial and send the mail
	 * 
	 * 
	 */
	public static void SendEmailForReturningBook(String UserID) throws SQLException {
		boolean flag = false;
		// String UserId;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery("Select * From obl.users where UserID ='" + UserID + "';");
			while (rs2.next() && flag == false) {
				flag = true;
				SendMail.sendEmail("Return the Book", "You have to return the book or your account will be freezed",
						rs2.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error In SendEmailForRequestedBook-mysql....");
		}

	}

	/*
	 * this method ClosestReturnDate is to get the closest return date
	 * 
	 * @param BookID is the book id we want to check the date for it
	 * 
	 */
	public static String ClosestReturnDate(String BookID) throws SQLException {
		boolean flag = false;
		String Date;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt
					.executeQuery("Select * From obl.history where idBook ='" + BookID + "'And HistoryType ='loaned';");
			while (rs2.next() && flag == false) {
				flag = true;
				Date = rs2.getString(10);
				return Date;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error In ReuqedtedBookAuto-mysql....");
		}
		return null;
	}

	/*
	 * this method RequestBook is to requestbook is a request book
	 * 
	 * @param msg this is the message we habe in index 1 historytyoe and in index 2
	 * user id
	 * 
	 */
	public static void RequestBook(Object msg) throws SQLException {
		ArrayList<String> updatedata = (ArrayList<String>) msg;
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			stmt.executeUpdate(
					"INSERT INTO history (UserId,idBook,HistoryType,RequestedDate) VALUES('" + updatedata.get(2) + "','"
							+ updatedata.get(1) + "','" + "request" + "','" + dtf.format(now) + "')");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		java.sql.Statement stmt1;
		try {
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("Select * From obl.book where idBook ='" + updatedata.get(1) + "';");
			while (rs1.next()) {
				int x = Integer.parseInt(rs1.getString(14));
				x++;
				stmt2.executeUpdate("UPDATE obl.book SET RequestedCounter ='" + Integer.toString(x) + "'WHERE idBook='"
						+ updatedata.get(1) + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * this method returnbookbyid is to get the all history that have a historytype
	 * loaned and check the return date and the shouldreturndate
	 * 
	 */
	public static int returnbookbyid(Object msg2) throws SQLException, ParseException {

		ArrayList<String> updatedata = (ArrayList<String>) msg2;
		boolean flag = false;
		boolean islateflag = false;
		String IsLate = "No";
		String OldDate = null;
		int lateCounter = 0;
		String UserID = null;
		int res = 0;
		long X = 0;
		java.sql.Statement stmt;

		try {
			stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery("Select * From obl.history where idBook ='" + updatedata.get(1)
					+ "'And HistoryType ='loaned' And UserId='" + updatedata.get(2) + "';");

			while (rs2.next() && flag == false) {
				flag = true;
				UserID = rs2.getString(1);
				OldDate = rs2.getString(5);
				res = MinusBook(updatedata.get(1));
				Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(rs2.getString(10));
				Date date2 = new Date(System.currentTimeMillis());

				X = getDifferenceDays(date1, date2);
				if (X <= 0) {
					IsLateCheaker(UserID);
					IsLate = "Yes";
					islateflag = true;
				} else if (date1.compareTo(date2) > 0 || date1.compareTo(date2) == 0) {

				} else
					System.out.println("No Way");
			}

			ReuqedtedBookAuto(updatedata.get(1)); // check if the book is requested
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();

			stmt.executeUpdate("INSERT INTO history (UserId,idBook,HistoryType,ReturnDate,isLate,LoadPeriod) VALUES('"
					+ UserID + "','" + updatedata.get(1) + "','" + "returned" + "','" + dtf.format(now) + "','" + IsLate
					+ "','" + Long.toString(X) + "')");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		java.sql.Statement stmt2;
		java.sql.Statement stmt3;
		stmt2 = con.createStatement();
		stmt3 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery("Select * From obl.book where idBook ='" + updatedata.get(1) + "';");
		int down = 0;
		while (rs2.next()) {
			down = Integer.parseInt(rs2.getString(14));
			down--;
		}
		stmt3.executeUpdate("UPDATE obl.book SET RequestedCounter ='" + Integer.toString(down) + "'WHERE idBook='"
				+ updatedata.get(1) + "';");
		return res;
	}

	/*
	 * this method MinusBook is if we return a book we minus the reurn copies
	 * 
	 * @idbook , is the id book we return
	 * 
	 */
	public static int MinusBook(String idBook) {
		java.sql.Statement stmt;
		boolean flag = true;
		int x = -1;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where idBook ='" + idBook + "';");
			while (rs1.next()) {
				x = Integer.parseInt(rs1.getString(9));
				x--;
			}
			try {
				stmt.executeUpdate("UPDATE obl.book SET LoanedCopies ='" + Integer.toString(x) + "'WHERE idBook='"
						+ idBook + "';");
				return 1;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * this method IsLateCheaker check if is late cheaker
	 * 
	 * @param UserID this is a user id we want to check
	 * 
	 */
	public static void IsLateCheaker(String UserID) {
		java.sql.Statement stmt;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		int lateCounter = -1;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.users where UserID ='" + UserID + "';");
			while (rs1.next()) {
				lateCounter = Integer.parseInt(rs1.getString(12));
				lateCounter++;
			}
			UpdateIsLate(Integer.toString(lateCounter), UserID);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * this method UpdateIsLate is to update islate in DB
	 * 
	 * @param Islate,UserID , islate is equal to yes or no ,
	 * 
	 * 
	 */
	public static void UpdateIsLate(String IsLate, String UserID) {
		java.sql.Statement stmt;
		java.sql.Statement stmt2;
		try {
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			stmt.executeUpdate("UPDATE obl.users SET lateCounter ='" + IsLate + "'WHERE UserID='" + UserID + "';");
			stmt2.executeUpdate(
					"UPDATE obl.users SET MemberShipStatus ='" + "Suspended" + "'WHERE UserID='" + UserID + "';");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * this method is threads
	 */
	public static void AutoThreadWork() throws ParseException {

		ThreadCheckGradationDate();
		ThreadAutoSendEmailReminder();
		ThreadAutoFreezCard();
		ThreadUnFreezCard();
	}

	/*
	 * this method ThreadCheckGradationDate is a thread that check Gradation date
	 */
	public static void ThreadCheckGradationDate() throws ParseException {
		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.users where UserType ='1';");
			while (rs1.next()) {
				Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(rs1.getString(10));
				Date date2 = new Date(System.currentTimeMillis());
				if (date1.compareTo(date2) > 0) {
					// (rs1.getString(6) + "All Clear");
				} else if (date1.compareTo(date2) < 0) {
					FreezeLockGradStudent(rs1.getString(1));
					// (rs1.getString(6) + "you need to send email for
					// GradationDate");
					// ("Date1 is before Date2");
				} else if (date1.compareTo(date2) == 0) {
					FreezeLockGradStudent(rs1.getString(1));
					// (rs1.getString(6) + "you need to send email for
					// GradationDate");
					// ("Date1 is equal to Date2");
				} else {
					// ("How to get here?");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * this method is for the thread to check if the user has books in possession
	 * when he graduates to update the
	 *
	 *
	 * memberstatusship to suspended if so or if else to locked
	 */
	public static void FreezeLockGradStudent(String UserID) throws SQLException {
		java.sql.Statement stmt1;
		java.sql.Statement stmt2;
		java.sql.Statement stmt;
		try {
			stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("Select * From obl.users where UserID ='" + UserID + "';");
			while (rs1.next()) {
				if (rs1.getString(14).equals("0")) {
					stmt2 = con.createStatement();
					stmt2.executeUpdate(
							"UPDATE obl.users SET MemberShipStatus ='Locked' WHERE UserID = '" + UserID + "';");
				} else {
					stmt = con.createStatement();
					stmt.executeUpdate(
							"UPDATE obl.users SET MemberShipStatus ='Suspended' WHERE UserID = '" + UserID + "';");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
 * this method ThreadUnFreezCard is a thread that check the date to unfreeze
 * 
 */
	public static void ThreadUnFreezCard() throws ParseException {

		java.sql.Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.users where MemberShipStatus ='" + "Suspended" + "';");
			while (rs1.next()) {

				Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(rs1.getString(13));
				Date date2 = new Date(System.currentTimeMillis());

				long X = getDifferenceDays(date1, date2);
				if (X <= 0) {
					stmt = con.createStatement();
					stmt.executeUpdate("UPDATE obl.users SET MemberShipStatus ='" + "Active" + "'WHERE UserID='"
							+ rs1.getString(1) + "';");
					System.out.println("case 1");
				}
				if (date1.compareTo(date2) > 0 || date1.compareTo(date2) == 0) {
					System.out.println("case 2");

				} else
					System.out.println("How did you get here");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
/*
 * this method getDifferenceDays is to get the diferant date betwaen 2 days 
 */
	public static long getDifferenceDays(java.util.Date d2, java.util.Date d1) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
/*
 * this method ThreadAutoFreezCard when the subscribe
 * r return  a book and is late this is a auto thread 
 */
	public static void ThreadAutoFreezCard() {// when islate hit 3+
		java.sql.Statement stmt;
		java.sql.Statement stmt2;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.users where UserType ='" + "1" + "';");
			while (rs1.next()) {
				if (Integer.parseInt(rs1.getString(12)) > 2) {
					stmt2 = con.createStatement();

					stmt2.executeUpdate("UPDATE obl.users SET MemberShipStatus ='" + "Freez" + "'WHERE UserID='"
							+ rs1.getString(1) + "';");
					// String q3 = "INSERT INTO history (UserId,HistoryType,FrezzeDate) VALUES('" +
					// rs1.getString(1) + "','" + "Freez" + "','"
					// + dateFormat.format(date) + "')";
					// stmt2.executeUpdate(q3);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
/*
 * 
 * this method ThreadAutoSendEmailReminder this is a thread that have to auto send email reminder 
 * 
 */
	public static void ThreadAutoSendEmailReminder() throws ParseException {
		java.sql.Statement stmt;
		long X;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.history where HistoryType ='" + "loaned" + "';");
			while (rs1.next()) {
				Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(rs1.getString(10));
				Date date2 = new Date(System.currentTimeMillis());
				X = getDifferenceDays(date1, date2);
				if (X == 0) {
					SendEmailForReturningBook(rs1.getString(1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	/*
	 * this method ShowStudentHistoryForManager is to show the student  history for manager 
	 * 
	 */
	public static ArrayList<HistoryForStudent> ShowStudentHistoryForManager(String UserID) throws IOException {
		java.sql.Statement stmt;
		ArrayList<HistoryForStudent> HistoryForStudentdetails = new ArrayList<HistoryForStudent>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();

		try {

			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.history where UserId ='" + UserID
					+ "' And (HistoryType ='loaned' or HistoryType='extended' )And shouldreturndate >= '"
					+ dtf.format(localDate).toString() + "';");
			while (rs1.next()) {
				HistoryForStudent history = new HistoryForStudent();
				history.setBookName(getBookNametoshow2(rs1.getString(2))); // 0=> bookname
				history.setBookID(rs1.getString(2)); // => 1 book id
				history.setReturnDate(rs1.getString(10));// =>2 reutrndate
				history.setHistoryId(rs1.getString(9));
				history.setRequestedbook(Getifrequestedbubookid(rs1.getString(2)));
				HistoryForStudentdetails.add(history);
			}
			if (HistoryForStudentdetails.size() == 0) {
				return null;
			} else {
				return HistoryForStudentdetails;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return HistoryForStudentdetails;
	}
	public static String Getifrequestedbubookid(String UserID){
		java.sql.Statement stmt;
		try {

			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("Select * From obl.book where idBook ='" + UserID + "';");
			while (rs1.next()) {
				return rs1.getString(1);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * this method RequestNewLoanDate is to check if it is possible to manually
	 * extend or not if so the new date will be sent to the client and will be
	 * inserted in the table
	 * 
	 * @author amir
	 * @param BookID
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws Exception
	 */

	public static String CheckIfRequested(int BookID) throws SQLException {

		java.sql.Statement stmt;
		stmt = con.createStatement();
		ResultSet rs1 = stmt.executeQuery("Select * From obl.book where idBook ='" + BookID + "';");
		while (rs1.next()) {
			return rs1.getString(12);
		}

		return null;
	}
/*
 * 
 * this method RequestNewLoanDate is to request new loan date 
 * 
 */
	public static ArrayList<String> RequestNewLoanDate(Object msg2) throws SQLException, ParseException {
		ArrayList<String> info = (ArrayList<String>) msg2;
		ArrayList<String> respond = new ArrayList<String>();
		java.sql.Statement stmt;
		stmt = con.createStatement();
		int id = Integer.parseInt(info.get(2));
		String Res = CheckIfRequested(id);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();

		if (Res.equals("Yes")) {
			respond.add("BookRequested");
			return respond;
		} else {
			// (info.get(1));
			// (info.get(3));
			Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(info.get(1));
			Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(info.get(3));
			long X = getDifferenceDays(date1, date2);
			if (X <= 7) {
				stmt = con.createStatement();
				stmt.executeUpdate("UPDATE obl.history SET HistoryType ='" + "extended" + "', shouldreturndate='"
						+ info.get(1) + "',LibrarianTagForExtend='" + info.get(6) + "',ExtendDateTag='"
						+ dtf.format(localDate).toString() + "' WHERE HistoryID='" + info.get(5) + "';");
				respond.add("Success");
				return respond;
			} else {
				respond.add("Not14");
				return respond;
			}

		}
	}

	/*
	 * this method LoanReportfuncregular is to loan report funcregular 
	 * 
	 */
	public static ArrayList<Double> LoanReportfuncregular(String string) {
		java.sql.Statement stmt;
		double count = 0;
		double bookcounter = 0;
		String Bookid;
		ArrayList<Double> LoanperiodarrayList = new ArrayList<Double>();
		try {
			System.out.println("1");
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.book where Requested='No' ");
			while (rs1.next()) {
				System.out.println("2");
				// LoanReportMedianRegularfun();
				bookcounter++;
				Bookid = rs1.getString(1);
				count += GetLoanPeriodForReport(Bookid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("3");
		System.out.println(count);
		System.out.println(bookcounter);
		LoanperiodarrayList.add(count);
		LoanperiodarrayList.add(bookcounter);
		return LoanperiodarrayList;
	}

	/*
	 * this method GetLoanPeriodForReport is to get loan period for report 
	 * 
	 */
	public static double GetLoanPeriodForReport(String bookID) {
		java.sql.Statement stmt;
		double lendtime = 0;
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(
					"SELECT * FROM obl.history where idBook= '" + bookID + "' And HistoryType= 'returned'");
			while (rs1.next()) {
				lendtime += Integer.parseInt(rs1.getString(16));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lendtime;

	}
/*
 * this method LoanReportfuncrequested if to get Loan Report funcrequested
 */
	public static ArrayList<Double> LoanReportfuncrequested(String string) {
		java.sql.Statement stmt;
		double count = 0;
		double bookcounter = 0;
		String Bookid;
		ArrayList<Double> LoanperiodarrayList = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.book where Requested='Yes' ");
			while (rs1.next()) {
				bookcounter++;
				Bookid = rs1.getString(1);
				count += GetLoanPeriodForReport(Bookid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LoanperiodarrayList.add(count);
		LoanperiodarrayList.add(bookcounter);
		return LoanperiodarrayList;
	}
/*
 * 
 * this method LoanReportMedianRegularfun is to Loan Report MedianRegularfun
 */
	public static ArrayList<Double> LoanReportMedianRegularfun() {
		java.sql.Statement stmt;
		ArrayList<Double> LoanReportMedianRegular = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.book where Requested='No' ");
			while (rs1.next()) {
				LoanReportMedianRegular.addAll(0, GetLoanArrayForReport(rs1.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return LoanReportMedianRegular;
	}
/*
 * 
 * this method GetLoanArrayForReport is to GetLoan Array For Report 
 */
	public static ArrayList<Double> GetLoanArrayForReport(int bookID) {
		java.sql.Statement stmt;
		ArrayList<Double> Res = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(
					"Select * From obl.history where idBook ='" + bookID + "' And HistoryType ='returned' ;");
			while (rs1.next()) {
				Res.add(Double.parseDouble(rs1.getString(16)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Res;

	}
/*
 * this method LoanReportMedianRegularfunRequestedBook is to Loan 
 * Report Median Regularfun Requested Book
 * 
 */
	public static ArrayList<Double> LoanReportMedianRegularfunRequestedBook() {
		java.sql.Statement stmt;
		ArrayList<Double> LoanReportMedianRegular = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM obl.book where Requested='Yes' ");
			while (rs1.next()) {
				LoanReportMedianRegular.addAll(0, GetLoanArrayForReportRequestedBook(rs1.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return LoanReportMedianRegular;
	}
/*
 * this method GetLoanArrayForReportRequestedBook is to GetLoan Array For Report Requested Book
 */
	public static ArrayList<Double> GetLoanArrayForReportRequestedBook(int bookID) {
		java.sql.Statement stmt;
		ArrayList<Double> Res = new ArrayList<Double>();
		try {
			stmt = con.createStatement();
			ResultSet rs1 = stmt.executeQuery(
					"Select * From obl.history where idBook ='" + bookID + "' And HistoryType ='returned' ;");
			while (rs1.next()) {
				Res.add(Double.parseDouble(rs1.getString(16)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Res;
	}

}