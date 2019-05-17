package client;
/**
 * this class is for the main librarian gui  window 
 */
import java.io.IOException;


import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainLibrarianGUI implements Initializable {

	private ArrayList<String> message;

	public String Username;

	public String Password;
	@FXML
	private Text UserIDTExt;
	public User User;
	
	
	@FXML
	private Text Greeting;
/**
 * this mehod is to fill the user 
 * @param user
 */
	public void FillUser(User user) {
		this.User = user;
		
	}
/**
 * this method is to preform logout
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void exit(MouseEvent event) throws ClassNotFoundException, IOException { // here the logout

		boolean testat = Logout();
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Main Manger GUI With Working Controller");
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setOnCloseRequest(e -> {
			try {
				Logout();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
	}
/**
 * this method is to perform log out of the system 
 * @return
 * @throws ClassNotFoundException
 */
	public boolean Logout() throws ClassNotFoundException {

		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("userlogout");
		
		message.add(Username);


		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		ArrayList<String> message = (ArrayList<String>) obj;

		

		try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); //
	
		return false;
	}
	
	@FXML
	public boolean ZiadTest() throws ClassNotFoundException {
		System.out.println("start");
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("ZiadnewFun");
	
		message.add("50");
		
		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		ArrayList<String> message = (ArrayList<String>) obj;

	

		try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); //
		
		return false;
	}
/**
 * this method is for the manager to add new subscribers
 * 
 * @param event
 */
	@FXML
	void AddNewSunscriber(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AddNewSubscriberGUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			AddNewSubscriberController AddNewSubscriberController = fxmlLoader.getController();
			Stage stage = new Stage();
			arr.add(UserIDTExt.getText());	
			arr.add("Libriran");
			AddNewSubscriberController.loadData(arr);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Librian  Add New Subscriber");
			stage.setResizable(false);
			stage.setOnCloseRequest(e -> {
				try {
					Logout();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening Librarian Add New Subscriber");
		}
	}
/**
 * this method is for the manager to view the subscribers in the library 
 * 
 * @param event
 */
	@FXML
	void ViewSubscriber(MouseEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();

			ArrayList<String> arr = new ArrayList<String>();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberGUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			SearchSubscriberGUIController SearchSubscriberGUI = fxmlLoader.getController();
			Stage stage = new Stage();
			arr.add(UserIDTExt.getText());
			// arr.add("311286694");
			arr.add("Libriran");
			SearchSubscriberGUI.loadData(arr);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Manager Search And Edit Subscriber");
			stage.setResizable(false);
			stage.setOnCloseRequest(e -> {
				try {
					Logout();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening Librarian Add New Subscriber  asdddd");
		}
	}
/**
 * this method is for the manager to go to stock mangment 
 * 
 * @param event
 */
	@FXML
	void StockManage(MouseEvent event) {
		System.out.println("Working GUI");
		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/StockMangmentGUI2.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			StockMangmentController StockMangmentController = fxmlLoader.getController();
			Stage stage = new Stage();
			ArrayList<String> arr= new ArrayList<String>();
			arr.add(UserIDTExt.getText());
			arr.add("Libriran");
			StockMangmentController.loadData(arr);
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Librarian Issue Book");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening Librarian Issue Book");
		}
		
	}
/**
 * this method is for the issue book option 
 * 
 * @param event
 */
	@FXML
	void IssueBook(MouseEvent event) {
		System.out.println("Working GUI");
		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LibrarianIsuuBook.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			LibrarianIssuBookController LibrarianIssuBookController = fxmlLoader.getController();
			Stage stage = new Stage();
			ArrayList<String> arr= new ArrayList<String>();
			arr.add(UserIDTExt.getText());
			arr.add("Libriran");
			LibrarianIssuBookController.loadData(arr);
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Librarian Issue Book");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening Librarian Issue Book");
		}
}
/**
 * this method for the return book function for the manager
 * w
 * @param event
 */
	@FXML
	void ReturnBook(MouseEvent event) {

		System.out.println("Working GUI");
		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LibrarianReturnBookGUI.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			LibrarianReturnBookController LibrarianReturnBookController = fxmlLoader.getController();			Stage stage = new Stage();
			ArrayList<String> arr= new ArrayList<String>();
			arr.add(UserIDTExt.getText());
			arr.add("Libriran");
			LibrarianReturnBookController.loadData(arr);
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Librarian Issue Book");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening Librarian Issue Book");
		}
	}
/**
 * this method is to load the userid and username from the last stage
 * 
 * @param resultLoadData
 */
	public void loadData(ArrayList<String> resultLoadData) {
	
		UserIDTExt.setText(resultLoadData.get(0));
		Username = resultLoadData.get(0);
		
	}
/**
 * this method is to load the greeting mesage in the main menu 
 * 
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
