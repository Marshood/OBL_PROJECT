package client;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


/**
 * This class is Controller to edit student info 
 * in this class we get the all information about the students data saved in library 
 * we send to the server the student's id and get ArrayList as respond having all the user data
 * then we fill the fields 
 *
 * @author Aroob daqduqi , 318489598 
 * @version Jan 2019
 */
public class StudentAccountController implements Initializable{
    // this arrayList message to send the information to the server in it 
	private ArrayList<String> message;
	//to store user's id and send it to the server 
	public User User;
	//create user from type User
	public Student student;
	//to alert when empty phone number
	@FXML
    private Text EmptyPhoneNumber=new Text();
	//to alert when empty email
	@FXML
    private Text EmptyEmail=new Text();
	//to fill in first name 
    @FXML
    private TextField firstname; 
  //to fill in card number
    @FXML
    private TextField cardnumber;
  //to fill in phone number 
    @FXML
    private TextField PhoneNumber;
  //to fill in Email 
    @FXML
    private TextField Email;
  //to fill in first last name 
    @FXML
    private TextField lastname;
  //to fill in status 
    @FXML
    private TextField status;
//to fill in password
    @FXML
    private TextField password;
    //to show successful message
    @FXML
    private Text successfulsave2;
    /*
     * exit func to exit 
     */
    @FXML
    void Exit(MouseEvent event) {
    	System.out.println("Student Account Working Controller");
    	((Node) event.getSource()).getScene().getWindow().hide();
   
    }
    /*
     * function save checks if the fields are empty, if yes it shows a message depending on it.
     */
    @FXML
    void Save(ActionEvent event) throws ClassNotFoundException {
    	int Emailflag=0,PhoneNumberflag=0;
    	if(PhoneNumber.getText().trim().isEmpty()){
    		EmptyPhoneNumber.setText("Empty");
    		successfulsave2.setText("");
    		PhoneNumberflag=0;
    	}
    	if(Email.getText().trim().isEmpty()){
    		EmptyEmail.setText("Empty");
    		successfulsave2.setText("");
    		Emailflag=0;
    	}
    	if(!PhoneNumber.getText().trim().isEmpty()) {
    		EmptyPhoneNumber.setText("");
    		PhoneNumberflag=1;
    	}
    	if(!Email.getText().trim().isEmpty()){
    		EmptyEmail.setText("");
    		Emailflag=1;
    	}
    	if(Emailflag==1&&PhoneNumberflag==1&&!isValidEmailAddress(Email.getText())) {
    		EmptyEmail.setText("Invalid Email");
    		
    	}
    	//if the fields email and phone number are not empty send them to server
    	else if (Emailflag==1&&PhoneNumberflag==1&&isValidEmailAddress(Email.getText())) {
    		successfulsave2.setText("Saved");
    		EmptyPhoneNumber.setText("");
    		EmptyEmail.setText("");
    		updateStudnetData();
    	}
    	
//to update student data and go to server 
    }
    /*@author Aroob
   *
    * get from the gui the user data we wan to update and send it to server to save in database
    */
    public void updateStudnetData() throws ClassNotFoundException {
    	String phone = (String) PhoneNumber.getText();
		String email = (String) Email.getText();
		String passWord = (String) password.getText();
		String Firstname = (String) firstname.getText();
		String Lastname = (String) lastname.getText();
		String CardNumber = (String) cardnumber.getText();
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("updateStudnetData");//=>type 0
		message.add(phone);//=>phone 1
		message.add(email);//=>email 2
		message.add(passWord);//password =>3
		message.add(Firstname);//firstname =>4
		message.add(Lastname);//lastname =>5
		message.add(CardNumber);//userid =>6
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		ArrayList<String> message = (ArrayList<String>) obj;
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		obj1=clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList <String>) obj1;
    } 
    
    /*
     * to check if the email is valid
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
 }
/*
 * fill in the user id in initialize    (non-Javadoc)
 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) { //get data from the data base

		try {
			filluser("1");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    /*
     * function fill user goes to server and get data of the student to fill in
     */
	public void filluser(String userID) throws ClassNotFoundException {
		
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();//
		message.add("GetDataToFillIn");
		message.add(userID);
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		ArrayList<String> message = (ArrayList<String>) obj;
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At fail getting data");
		}
		obj1=clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList <String>) obj1;		
		cardnumber.setText(message1.get(0));
		PhoneNumber.setText(message1.get(2));
		Email.setText(message1.get(1));
		firstname.setText(message1.get(3));
		lastname.setText(message1.get(4));
		password.setText(message1.get(5));
		status.setText(message1.get(6));
		
		
	}

}