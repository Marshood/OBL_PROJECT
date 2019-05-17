package client;
/**
 * this class is for viewing the main student screen 
 */
import java.awt.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.sun.glass.ui.Window.Level;
import com.sun.javafx.logging.Logger;

import entites.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainStudentGUI {
	
	
	@FXML
	private Text Greeting;
	@FXML
	private Button logoutbutt;
	private ArrayList<String> message;

	public User User;
	public String UserID;
	
    @FXML
    private Text UserIDtext;
    
	public void FillUser(User user) {
		this.User = user;
		this.UserID=user.getUserID();
		
	}
/**
 * this method is for the student to open his order history 
 * @param event
 * @throws ClassNotFoundException
 */
	@FXML
	void OpenOrderHistory(MouseEvent event) throws ClassNotFoundException {
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("/FXML/StudentHistory.fxml"));
		try {
			Loader.load();
		} catch (Exception e) {
			System.out.println("Error with student history");
		}
		StudentHistoryController display = Loader.getController();
		display.filluser(UserID);
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("Main Student GUI With Working Controller");
		stage.setScene(new Scene(p));
		stage.show();
	}
/*
 * this method is for the student to search for books 
 */
	@FXML
	void OpenSerachBooks(MouseEvent event) throws ClassNotFoundException {
		System.out.println("click");
		
			FXMLLoader Loader = new FXMLLoader();
			
			Loader.setLocation(getClass().getResource("/FXML/SearchBooksForStudent2.fxml"));
			try {
				Loader.load();
			} catch (Exception e) {
				System.out.println("Error with opening the Student Search books");
			}
			SearchBooksStudentController2 display = Loader.getController();
			display.filluser(UserID);
			Parent p = Loader.getRoot();
			Stage stage = new Stage();
			
			stage.setResizable(false);
			
			stage.setScene(new Scene(p));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Student Search Books With Working Controller");
			stage.setResizable(false);
			stage.show();
	
	}

	@FXML
	void MyAccount(MouseEvent event) throws ClassNotFoundException {
		
		FXMLLoader Loader = new FXMLLoader();
		Loader.setLocation(getClass().getResource("/FXML/StudentAccount.fxml"));
		try {
			Loader.load();
		} catch (Exception e) {
			System.out.println("Error with opening student my account");
		}
		StudentAccountController display = Loader.getController();
		display.filluser(UserID);
		Parent p = Loader.getRoot();
		Stage stage = new Stage();
		
		stage.setResizable(false);
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("Main Student GUI With Working Controller");
		stage.setScene(new Scene(p));
		stage.show();
	}
/**
 * this method is to go the login page after logout
 * 
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void LogOUT(MouseEvent event) throws ClassNotFoundException, IOException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("userlogout");
		message.add(UserID);

		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		ArrayList<String> message = (ArrayList<String>) obj;

		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); 
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("OBL ");
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setOnCloseRequest(e -> {
			try {
				Logoutx(UserID);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
		
	}
	/**
	 * this method is to preform the log out from the system 
	 * 
	 * @param User
	 * @throws ClassNotFoundException
	 */
	  public void Logoutx(String User) throws ClassNotFoundException {
	    	/*
	    	 *    ArrayList message we save the all user data to send to the server 
	    	 *   clientCon in to send to server the data and to get the respond from the the server 
	    	 * */
			ClientConsole clientCon = new ClientConsole();
			message = new ArrayList<String>();
	        // save all the new data about the Subscriber and 
	        //in the index 0 in message we send the name about the func we want to do 

			message.add("userlogout");
			message.add(UserID);
			message.add(UserID);

			System.out.println("logout..");
			/* we send from the client to server only object we
	    	*change the type of the saveData to object and send it to server
	    	*
	    	*/
			Object obj = (Object) message;
			Object obj1 = new Object();
			// send the message to the server 

			clientCon.execute(obj);

			// this sleep to wait to server to send the respond 
			try {
				Thread.currentThread().sleep(1000);
			} catch (Exception e) {
				System.out.println("Exception At MainLibrarianGUI in Function Logout");
			}
			/* we get the responed from the server in type object obj1
	 	     * and after this we change it according to protocol we use 
	 	     *  
	 	     */
			obj1 = clientCon.Getrespond();
			
			System.out.println("exited");
	 	}
/**
 * this method is to load the greeting message for the user
 * 
 * @param resultLoadData
 */
	  public void  loadData(ArrayList<String> resultLoadData) {
	        //get all the information from resultLoadData
	        //and set to the text fields and combobox
		  UserIDtext.setText(resultLoadData.get(0));
		  UserID= resultLoadData.get(0);
		  Calendar c = Calendar.getInstance();
			int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

			if(timeOfDay >= 0 && timeOfDay < 12){
				Greeting.setText("Good Morning");      
			}else if(timeOfDay >= 12 && timeOfDay < 16){
				Greeting.setText("Good Afternoon");
			}else if(timeOfDay >= 16 && timeOfDay < 21){
				Greeting.setText("Good Evening");
			}else if(timeOfDay >= 21 && timeOfDay < 24){
				Greeting.setText("Good Night");
			}

	         	
	    	}
}