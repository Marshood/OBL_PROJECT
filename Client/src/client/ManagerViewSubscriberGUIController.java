package client;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.glass.events.MouseEvent;

import entites.User;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is Controller to manager view Subscriber and edit Subscriber in
 * this class we get the all information about the specific Subscriber AFter
 * search it
 * 
 *
 * @author Marshood Ayoub , 311286694
 * @version Jan 2019
 */
public class ManagerViewSubscriberGUIController {

	/**
	 * This is the Text Field and the Button we have at the GUI
	 * ManagerViewSubscriberGUI
	 */
	// this text field IDteXT represent Student's id
	@FXML
	private TextField IDteXT;
	// this text field Passtext the represent Student's Password
	@FXML
	private TextField Passtext;
	// this text field SubscriberTypeText represent the Student's Type ( Student ,
	// worker )
	@FXML
	private TextField SubscriberTypeText;
	// text1 is to show the Managment or Libriran ID
	@FXML
	private Text text1;
	// this text field FirstNameText represent the Student's First name
	@FXML
	private TextField FirstNameText;
	// this text field represent the Student's Last name
	@FXML
	private TextField LastNameText;
	// this text field emilTExt represent the Student's Email
	@FXML
	private TextField emilTExt;
	// this text field PhoneText represent the Student's Phone number
	@FXML
	private TextField PhoneText;
	// this text field GradiaionDateText represent the Student's Graduation Date
	@FXML
	private TextField GradiaionDateText;
	// this button savebutt to save all the new information about the Student's
	@FXML
	private Button savebutt;
	// this button EDitbutt to Enable to Edit the information about the Student's
	// and Enable savebutt the click
	@FXML
	private Button EDitbutt;
	// this button Backbutt to Back to the last frame ( last GUI )
	@FXML
	private Button Backbutt;
	// this button logoutbutt to logout the user ant return to the log in frame
	@FXML
	private Button logoutbutt;
	// this arrayList resultLoadData to send the information about the corrent user
	// when we want to switch to another GUI
	ArrayList<String> resultLoadData = new ArrayList<String>();

	// this ComboBox comboStatus represent Student's Status and can to change it
	@FXML
	private ComboBox<String> comboStatus;
	String status;
	// this conter the return book late
	@FXML
	private TextField lateConterText;

	@FXML
	private TextField OrganizationText;
	String usertype;

	/**
	 * 
	 * This method ShowLoanedBooks is for the librarian to open a page showing what
	 * books the current student has and to manually extend the a selected book if
	 * possible
	 * 
	 * @throws IOException
	 * 
	 */

    @FXML
    void ShowLoanedBooks(ActionEvent event) throws IOException ,ClassNotFoundException{

    	ArrayList<String> arr = new ArrayList<String>();
    	//((Node) event.getSource()).getScene().getWindow().hide();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LoanedBooksGUI.fxml"));
    	Parent root =(Parent) fxmlLoader.load();
		LoanedBooksController display=fxmlLoader.getController();
		Stage stage = new Stage();
		arr.add(FirstNameText.getText());
		arr.add(IDteXT.getText());
		arr.add(text1.getText());
	    display.loadData(arr);
		stage.setScene(new Scene(root));
		display.start(stage);
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("Librarian See Student Loaned Books");
		stage.setResizable(true);
		stage.show();
		
    }

	/**
	 * This method EditFunc to enable to edit the information about the subscriber
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @param
	 */
	@FXML
	void EditFunc(ActionEvent event) {
		savebutt.setVisible(true);
		// in this func we change the text field and the combobox to enable to change
		// IDteXT.setEditable(true);
		Passtext.setEditable(true);
		// SubscriberTypeText.setEditable(true);
		// FirstNameText.setEditable(true);
		// LastNameText.setEditable(true);
		emilTExt.setEditable(true);
		PhoneText.setEditable(true);
		// GradiaionDateText.setEditable(true);
		// comboStatus.setDisable(false);
		comboStatus.getEditor().setEditable(true);
		savebutt.setDisable(false);

	}

	/**
	 * This method to get the Subscriber information
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @param resultLoadData The ArrayList<string> to get the information . and set
	 *                       to the text fields and combobox
	 */
	public void loadData(ArrayList<String> resultLoadData) {
		// get all the information from resultLoadData
		// and set to the text fields and combobox
		IDteXT.setText(resultLoadData.get(0));
		Passtext.setText(resultLoadData.get(1));
		SubscriberTypeText.setText(resultLoadData.get(2));
		FirstNameText.setText(resultLoadData.get(4));
		LastNameText.setText(resultLoadData.get(5));
		emilTExt.setText(resultLoadData.get(6));
		PhoneText.setText(resultLoadData.get(7));
		GradiaionDateText.setText(resultLoadData.get(8));
		comboStatus.setPromptText(resultLoadData.get(3));
		text1.setText(resultLoadData.get(11));
		System.out.println(resultLoadData.get(11));
		OrganizationText.setText(resultLoadData.get(9));
		lateConterText.setText(resultLoadData.get(10));
		usertype = resultLoadData.get(11);
		status = resultLoadData.get(3);
	}

	@FXML
	void comboStatusFunc(ActionEvent event) {

	}

	/**
	 * This method to back to the last GUI page and send the information about the
	 * current user to the GUI
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @param
	 * @throws IOException
	 */
	@FXML
	void backFunc(ActionEvent event) throws IOException, ClassNotFoundException {
		((Node) event.getSource()).getScene().getWindow().hide();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberGUI.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		SearchSubscriberGUIController display = fxmlLoader.getController();
		// we save the paramter we want to send to the anther GUI
		resultLoadData.add(text1.getText());
		resultLoadData.add(usertype);
		// fxmlLoader.load();
		// loadData this func location at the SearchSubscriberGUIController
		display.loadData(resultLoadData);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		// stage.setTitle("Librarian Add New Subscriber");
		// stage.setResizable(false);
		stage.setOnCloseRequest(e -> {
			try {
				logoutfunc();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();

	}
	/*
	 * @author Marshood Ayoub , 311286694 this func to save the all new information
	 * aout the student's we save the new data in arrayList and send it to the
	 * server to change the information and after this we get the respond from the
	 * server and check if the changed are Succsesed
	 */

	@FXML
	void Save(ActionEvent event) throws Exception {
		/*
		 * ArrayList saveData we save the all new data about the student we want to
		 * update clientCon in to send to server the data and to get the respond from
		 * the the server
		 */
		ArrayList<String> saveData = new ArrayList<String>();
		ClientConsole clientCon = new ClientConsole();
		// save all the new data about the Subscriber and
		// in the index 0 in saveData we send the name about the func we want to do
		saveData.add("SaveSub");
		saveData.add(IDteXT.getText()); // 1
		saveData.add(Passtext.getText());// 2
		saveData.add("1");// 3
		saveData.add(status);// 4
		saveData.add(FirstNameText.getText());// 5
		saveData.add(LastNameText.getText());// 6
		saveData.add(emilTExt.getText());// 7/
		saveData.add(PhoneText.getText());// 8
		saveData.add(GradiaionDateText.getText());// 9
		/*
		 * we send from the client to server only object we change the type of the
		 * saveData to object and send it to server
		 *
		 */
		System.out.println("asdasdsad" + comboStatus.getValue());
		Object obj = (Object) saveData;
		Object obj1 = new Object();
		// send the message to the server
		clientCon.execute(obj);
		// this sleep to wait to server to send the respond
		try {
			Thread.currentThread().sleep(100);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}

		// synchronized (clientCon.client){clientCon.client.wait();}

		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();
		ArrayList<String> message = (ArrayList<String>) obj1;

		// we check the message if the edit succsefull or not
		if (message.get(0).equals("AddedSucc")) {
			Alert alert = new Alert(AlertType.INFORMATION, "Edit Subscriber completed..", ButtonType.CLOSE);
			// alert.show();
			alert.showAndWait();
			((Node) event.getSource()).getScene().getWindow().hide();
			backFunc(event);

		} else {
			Alert alert = new Alert(AlertType.ERROR, "We have Error to edit .", ButtonType.CLOSE);
			alert.setTitle("Edit Subscriber");
			alert.setHeaderText("Re-try Again");
			alert.show();
		}

	}

	public void logoutfunc() throws ClassNotFoundException, IOException {
		/*
		 * ArrayList message we save the all user data to send to the server clientCon
		 * in to send to server the data and to get the respond from the the server
		 */
		ClientConsole clientCon = new ClientConsole();
		ArrayList<String> message123 = new ArrayList<String>();
		// save all the new data about the Subscriber and
		// in the index 0 in message we send the name about the func we want to do

		message123.add("userlogout");
		message123.add(text1.getText());
		message123.add("test");

		System.out.println("logout..111");
		/*
		 * we send from the client to server only object we change the type of the
		 * saveData to object and send it to server
		 *
		 */
		Object obj = (Object) message123;
		Object obj1 = new Object();
		// send the message to the server

		clientCon.execute(obj);

		// this sleep to wait to server to send the respond
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();

		System.out.println("exited");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("OBL ");
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
 * this method 
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void logoutFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		/*
		 * ArrayList message we save the all user data to send to the server clientCon
		 * in to send to server the data and to get the respond from the the server
		 */
		Logout();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("OBL ");
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

	public boolean Logout() throws ClassNotFoundException {

		ClientConsole clientCon = new ClientConsole();
		ArrayList<String> message = new ArrayList<String>();
		message.add("userlogout");
		message.add(text1.getText());
		message.add(text1.getText());

		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); //
		System.out.println(obj1 + "test for obj1"); //
		ArrayList<String> message1 = (ArrayList<String>) obj1; //
		// System.out.println("hkahslajldskasd" + message1.get(0));//
		return false;

	}

	/*
	 * initialize the current screen
	 */

	@FXML
	void initialize() {
		// set all the text field and commbobox to not enable to edit
		IDteXT.setEditable(false);
		Passtext.setEditable(false);
		SubscriberTypeText.setEditable(false);
		// MemberShipText.setEditable(false);
		FirstNameText.setEditable(false);
		LastNameText.setEditable(false);
		emilTExt.setEditable(false);
		PhoneText.setEditable(false);
		GradiaionDateText.setEditable(false);
		// comboStatus.setEditable(false);
		// if(GradiaionDateText.isEditable())
		lateConterText.setEditable(false);
		OrganizationText.setEditable(false);
		comboStatus.setDisable(true);
		comboStatus.getEditor().setEditable(true);
		// add to the combobox the status we have
		comboStatus.getItems().add("Active");
		comboStatus.getItems().add("suspended");
		comboStatus.getItems().add("locked");
		savebutt.setDisable(true);

	}

}
