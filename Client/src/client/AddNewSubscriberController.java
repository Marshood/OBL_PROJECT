package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.Bindings;
import entites.User;
 
/**
 * This class is Controller to add new  Subscriber 
 * in this class we get the all information about the new  Subscriber 
 * and check if the all information are currect   
 * after this we save the all information about the Subscriber in  ArrayList message
 * to send it to the server to add the Subscriber 
 *
 * @author Marshood Ayoub , 311286694 
 * @version Jan 2019
 */

public class AddNewSubscriberController {
    // this arrayList message to send the information about new subscriber 
	private ArrayList<String> message;
	// this to save the current user id 
	 User user= new User();
	/**
	 * This is the Text Field and the Button we have at the GUI ManagerViewSubscriberGUI
	 */
	String usertype;
	// this text field SubscriberFisrtName represent Student's first name  
	@FXML
	private TextField SubscriberFisrtName;
    // this text field SubscriberLastName represent Student's last name 
	@FXML
	private TextField SubscriberLastName;
    // this text field SubscriberID represent Student's id  
	@FXML
	private TextField SubscriberID;
    // this text field SubscriberPhone represent Student's phone number   
	@FXML
	private TextField SubscriberPhone;
    // this text field SubscriberEmail represent Student's Email   
	@FXML
	private TextField SubscriberEmail;
    // this text field SubscriberPass represent Student's Password   
	@FXML
	private TextField SubscriberPass;
    // this text field EmptyEmail to show if the email are not valid 
    @FXML
    private Text EmptyEmail;
    // this button addbutt to add the new subscriber in DB 
    @FXML
    private Button addbutt= new Button("add");
    @FXML
    private Text UserIDText;
    //  this combobox OrganizationComboBox  represent Student's  Organization
    @FXML
    private ComboBox<String> OrganizationComboBox;

    @FXML
    private DatePicker DateText;

    /*
     * initialize the current screen  
     * */
    @FXML 
    void initialize() {
    	// this is a listener to validate if the id it only numbers and the length  not graeter than 9 number 
    	SubscriberID.textProperty().addListener((observable,oldValue,newValue)->{
    		if(!newValue.matches("\\d*")) {
    			SubscriberID.setText(newValue.replaceAll("[^\\d]", ""));
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must contain only numbers", ButtonType.CLOSE);
    			alert.show();
     		}
    		if(SubscriberID.getLength()>9) {
    			SubscriberID.setText(oldValue);
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must be  9 numbers", ButtonType.CLOSE);
    			alert.show();
    		}
    	});
    	//DateText.promptTextProperty().isEmpty()
    	// this is a listener to validate if the phone number are corect and  it only numbers and the length not equal 10 number 

    	SubscriberPhone.textProperty().addListener((observable,oldValue,newValue)->{
    		if(!newValue.matches("\\d*")) {
    			SubscriberPhone.setText(newValue.replaceAll("[^\\d]", ""));
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must contain only numbers", ButtonType.CLOSE);
    			alert.show();
     		}
    		if(SubscriberPhone.getLength()>10) {
    			SubscriberPhone.setText(oldValue);
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must be  10 numbers", ButtonType.CLOSE);
    			alert.show();
    		}
    	});
    	SubscriberEmail.textProperty().addListener((observable,oldValue,newValue)->{
    	
    		
    		boolean bool = isValidEmailAddress(newValue);
    		if(!newValue.equals(null)) {
			if (!bool) {
				EmptyEmail.setText("Error email..");
				}}
			if(!newValue.equals(oldValue)) {
				EmptyEmail.setText("");}

	
    		});
    	// check if all the fields are fild then show add buttn on to use 
    	BooleanBinding textField2Valid = SubscriberFisrtName.textProperty().isEmpty().or(SubscriberLastName.textProperty().isEmpty()
    			.or(SubscriberID.textProperty().isEmpty().or(SubscriberEmail.textProperty().isEmpty().or(SubscriberPass.textProperty().isEmpty().or(SubscriberPhone.textProperty().isEmpty())))));
    	    // check textField2.getText() and return true/false as appropriate
    	addbutt.disableProperty().bind(textField2Valid);  
    	
    	OrganizationComboBox.setDisable(false);
    	OrganizationComboBox.getEditor().setEditable(true);
     	// add to the combobox the status we have 
    	OrganizationComboBox.getItems().add("Software Engineering ");
    	OrganizationComboBox.getItems().add("Mechanical Engineering");
    	OrganizationComboBox.getItems().add("Optical Enginnering");
    	OrganizationComboBox.getItems().add("Biotechnology Engineering");
    	OrganizationComboBox.getItems().add("Electrical Engineering");
    	OrganizationComboBox.getItems().add("Other..");
    	DateText.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});


    }
    
    /*
     * @author Marshood Ayoub , 311286694
     * this method  check if the email address are valid and after this call method addNew()
     * */    

    
	@FXML
	void AddNewSub(ActionEvent event) throws ClassNotFoundException {
		    /*
		     * bool is the boolean result if the email addres are valid or not 
		     * 
		     */
			boolean bool = isValidEmailAddress(SubscriberEmail.getText());
			if(DateText.getValue() == null)
			{
				Alert alert = new Alert(AlertType.ERROR, "Empty Date..", ButtonType.CLOSE);
				alert.setTitle("EmptyDate");
				alert.setHeaderText("Re-try Again");
				alert.show();
			}
			else {
			if (bool) {
				bool = addNew();
			}
			else 
			{
				EmptyEmail.setText("Error email..");

			}
			}
			}

	/*
	 * @author Marshood Ayoub , 311286694
	 * this mehot addNew() to to add the new Subscriber to DB 
	 * */    

	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean addNew() throws ClassNotFoundException {
        // firstName is the  new subscriber first name 
		String firstName = (String) SubscriberFisrtName.getText();
        // lastName is the  new subscriber last name 
		String lastName = (String) SubscriberLastName.getText();
        // ID is the  new subscriber ID
        String ID = (String) SubscriberID.getText();
        // phone is the  new subscriber phone
		String phone = (String) SubscriberPhone.getText();
        // email is the  new subscriber email
		String email = (String) SubscriberEmail.getText();
        // password is the  new subscriber password
		String password = (String) SubscriberPass.getText();
		String Orag = (String)OrganizationComboBox.getValue();
		LocalDate GraduationDate=DateText.getValue();
 		/*
    	 *    ArrayList message we save the new subscribber to send it to DB
    	 *   clientCon in to send to server the data and to get the respond from the the server 
    	 * */
		ClientConsole clientCon = new ClientConsole();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String StrDate=dtf.format(GraduationDate);
		message = new ArrayList<String>();//
		// save all the new Subscriber  
        //in the index 0 in message we send the name about the func we want to do 
 		message.add("addNewSubscriber");
		message.add(ID);
		message.add(password);
		message.add(firstName);
		message.add(lastName);
		message.add(email);
		message.add(phone);
		message.add(StrDate);
		message.add(Orag);
		
	      
		//message.add((String)GraduationDate);
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
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		  /* we get the responed from the server in type object obj1
 	     * and after this we change it according to protocol we use 
 	     *  
 	     */
		obj1 = clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList<String>) obj1;
		// we check the message  if the add  succsefull or not 
		// and show the correct alert 
		if (message1.get(0).equals("AddNewSubscriberResultSuccess")) {
			Alert alert = new Alert(AlertType.INFORMATION, "Add New SubscriberSuccess", ButtonType.CLOSE);
			alert.setTitle("add new subscriber");
			alert.setHeaderText("Successfully Add Subscriber");
			alert.show();
			SubscriberFisrtName.setText("");
			SubscriberLastName.setText("");
			SubscriberID.setText("");
			SubscriberPhone.setText("");
			SubscriberEmail.setText("");
			SubscriberPass.setText("");
			OrganizationComboBox.setValue("");
			DateText.setValue(null);
		} else if (message1.get(0).equals("UserExist")) {
			Alert alert = new Alert(AlertType.ERROR, "UserExist.", ButtonType.CLOSE);
			alert.setTitle("add new subscriber");
			alert.setHeaderText("Re-try Again");
			alert.show();

		} else if (message1.get(0).equals("AddNewSubscriberResultNotSuccess")) {
			Alert alert = new Alert(AlertType.ERROR, "Added not complete.", ButtonType.CLOSE);
			alert.setTitle("add new subscriber");
			alert.setHeaderText("Re-try Again");
			alert.show();
		}
		return false;

	}
  /*
   * 
   * this func check if the email is vaild 
   * */
	
	public static boolean isValidEmailAddress(String emailAddress) {
		String emailRegEx;
		Pattern pattern;
		// Regex for a valid email address
		emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
		// Compare the regex with the email address
		pattern = Pattern.compile(emailRegEx);
		Matcher matcher = pattern.matcher(emailAddress);
		// (matcher.find()+"matcher");
		return matcher.find();
	}
	/**
	 * This method to back to the last GUI page 
	 * and send the information about the current user to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @param 
	 * @throws IOException 
	 */
	@FXML
	void Cancel1Func(ActionEvent event) throws IOException {
		ArrayList<String> arr = new ArrayList<String>();
		if(usertype.equals("Managment")) {
		((Node) event.getSource()).getScene().getWindow().hide();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		MainMangerGUIController display=fxmlLoader.getController();	
		// we save the paramter we want to send to the anther GUI 
		arr.add(UserIDText.getText());
	    display.loadData(arr);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("OBL");
		stage.setResizable(false);
		stage.show();
	}
		else
		{
			
			((Node) event.getSource()).getScene().getWindow().hide();
			ArrayList<String> arr1 = new ArrayList<String>();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainLibrarianGUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			MainLibrarianGUI MainLibrarianGUI = fxmlLoader.getController();
			Stage stage = new Stage();
			arr1.add(UserIDText.getText());
			MainLibrarianGUI.loadData(arr1);
			// Stage stage1 = new Stage();
			stage.setResizable(false);
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("OBL");
			stage.setScene(new Scene(root));
			stage.setOnCloseRequest(e -> {
				try {	User user1= new User();
		    	user1.setUserID(user.getUserID());
					Logoutx(user1);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			stage.show();
			
			
			
		}
	
	
	
	}
	/**
	 * This method to ExitFunc  the current user 
	 * and send the information about the current user to the server using Logoutx method  
	 * to change the status of is LOg in   
	 *@author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	@FXML
	void ExitFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		User user1= new User();
    	user1.setUserID(user.getUserID());
     	Logoutx(user1);
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("OBL");
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setOnCloseRequest(e -> {
			try {
				Logoutx(user1);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
	}

	/**
	 * This method to send to the server  user id to change the status is log in 
	 * and send the information about the current user to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
    public void Logoutx(User User) throws ClassNotFoundException {
    	/*
    	 *    ArrayList message we save the all user data to send to the server 
    	 *   clientCon in to send to server the data and to get the respond from the the server 
    	 * */
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
        // save all the new data about the Subscriber and 
        //in the index 0 in message we send the name about the func we want to do 

		message.add("userlogout");
		message.add(UserIDText.getText());
		message.add(UserIDText.getText());

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
		
 	}

	/* This method to get the Subscriber information 
	 * 
	 *@author Marshood Ayoub , 311286694
	 * @param resultLoadData The ArrayList<string> to get the information .
	 * and set to the text fields and combobox
	 */
    public void  loadData(ArrayList<String> resultLoadData) {
    //get all the information from resultLoadData
    //and set to the text fields and combobox
  

    	UserIDText.setText(resultLoadData.get(0));
    	usertype=resultLoadData.get(1);
	}
    
    
    
    @FXML
    void OrganizationComboBoxFunc(ActionEvent event) {

    }
    
    @FXML
    void DateTextFunc(ActionEvent event) {

    }
}
