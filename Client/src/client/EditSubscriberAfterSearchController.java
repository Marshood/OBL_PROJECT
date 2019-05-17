package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class is Controller to manager edit Subscriber we found after search by name 
 * in this class we get the all information about the specific Subscriber AFter search it  
 * 
 *
 * @author Marshood Ayoub , 311286694
 * @version Jan 2019
 */
public class EditSubscriberAfterSearchController {

	/** 
	 * This is the Text Field and the Button we have at the GUI ManagerViewSubscriberGUI
	 */
    // this text field IDteXT represent Student's id 
    @FXML
    private TextField IDteXT;
    // this text field Passtext the represent Student's Password 
    @FXML
    private TextField Passtext;
    // this text field SubscriberTypeText represent the Student's Type ( Student , worker ) 
    @FXML
    private TextField SubscriberTypeText;
    // this button Backbutt to Back to the last frame ( last GUI ) 
    @FXML
    private Button Backbutt;
    // this button savebutt to save all the new information about the Student's 
    @FXML
    private Button savebutt;
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
    // this ComboBox comboStatus represent Student's Status  and can to change it 
    @FXML
    private ComboBox<String> comboStatus;
    // this conter the return book late 
    @FXML
    private TextField lateConterText;
    // this text field OrganizationText represent the Student's Organization   
    @FXML
    private TextField OrganizationText;
    String status;
    /*
     * @author Marshood Ayoub , 311286694
     * this func to save the all new information aout the student's 
     * we save the new data in arrayList and send it to the server to  change the information 
     * and after this we get the respond from the server and check if the changed are Succsesed  
     * */
    @FXML
    void Save(ActionEvent event) throws ClassNotFoundException {
    	/*
    	 *    ArrayList saveData we save the all new  data about the student we want to update 
    	 *   clientCon in to send to server the data and to get the respond from the the server 
    	 * */
    	
    	
    	// in the first we want to check if the syntax mail are currect and after this we save the new information 
    	boolean bool = isValidEmailAddress(emilTExt.getText());
		if (!bool) {
			Alert alert = new Alert(AlertType.WARNING, " Error Syntax Email", ButtonType.CLOSE);
			alert.show();
			
		}
		else 
		{

        ArrayList<String> saveData= new ArrayList<String>(); 
        ClientConsole clientCon = new ClientConsole();
        // save all the new data about the Subscriber and 
        //in the index 0 in saveData we send the name about the func we want to do 
    	saveData.add("SaveSub");
    	saveData.add(IDteXT.getText());  //1
    	saveData.add(Passtext.getText());//2
    	saveData.add("1");
    	//saveData.add(SubscriberTypeText.getText());//3
    	saveData.add(status);//4
    	saveData.add(FirstNameText.getText());//5
    	saveData.add(LastNameText.getText());//6
    	saveData.add(emilTExt.getText());//7/
    	saveData.add(PhoneText.getText());//8
    	saveData.add(GradiaionDateText.getText());//9
     	/* we send from the client to server only object we
    	*change the type of the saveData to object and send it to server
    	*
    	*/
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
 	    
 	    /* we get the responed from the server in type object obj1
 	     * and after this we change it according to protocol we use 
 	     *  
 	     */
		obj1=clientCon.Getrespond();
		ArrayList<String> message = (ArrayList <String>) obj1;
		
		// we check the message  if the edit succsefull or not 
		if(message.get(0).equals("AddedSucc"))
		{
			Alert alert = new Alert(AlertType.INFORMATION, "Edit Subscriber completed..", ButtonType.CLOSE);
			//alert.show();
			alert.showAndWait();
			((Node) event.getSource()).getScene().getWindow().hide();
			backFunc(event);

		}
		else {
			Alert alert = new Alert(AlertType.ERROR, "We have Error to edit .", ButtonType.CLOSE);
			alert.setTitle("Edit Subscriber");
			alert.setHeaderText("Re-try Again");
			alert.show();
		}
	 
    }
		}
    
	/**
	 * This method to back to the last GUI page 
	 * and send the information about the current user to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @param 
	 * @throws IOException 
	 */
    @FXML
    void backFunc(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	

    }

    @FXML
    void comboStatusFunc(ActionEvent event) {

    }
    
	/**
	 * This method to get the Subscriber information 
	 * 
	 *@author Marshood Ayoub , 311286694
	 * @param resultLoadData The ArrayList<string> to get the information .
	 * and set to the text fields and combobox
	 */
    public void  loadData(ArrayList<String> resultLoadData) {
    //get all the information from resultLoadData
    //and set to the text fields and combobox
    	IDteXT.setText(resultLoadData.get(0));
    	Passtext.setText(resultLoadData.get(1));
    	SubscriberTypeText.setText("Student");
    	FirstNameText.setText(resultLoadData.get(4));
    	LastNameText.setText(resultLoadData.get(5));
     	emilTExt.setText(resultLoadData.get(6));
    	PhoneText.setText(resultLoadData.get(7));
    	GradiaionDateText.setText(resultLoadData.get(8));
    	comboStatus.setPromptText(resultLoadData.get(3));
        OrganizationText.setText(resultLoadData.get(9));
    	lateConterText.setText(resultLoadData.get(10));
    	text1.setText(resultLoadData.get(11));
    	status=resultLoadData.get(3);
    }
    
    /*
     * initialize the current screen 
     * */
    
 @FXML
 void initialize()
 {
	 // set all the text field and commbobox to not enable to edit 
    IDteXT.setEditable(false);
  	Passtext.setEditable(true);
 	SubscriberTypeText.setEditable(false);
   //	MemberShipText.setEditable(false);
 	FirstNameText.setEditable(false);
 	LastNameText.setEditable(false);
  	emilTExt.setEditable(true);
 	PhoneText.setEditable(true);
 	GradiaionDateText.setEditable(false);
 	//comboStatus.setEditable(false);
  	//if(GradiaionDateText.isEditable()) 
 	lateConterText.setEditable(false);
 	OrganizationText.setEditable(false);
 	comboStatus.setDisable(true);
 	comboStatus.getEditor().setEditable(true);
 	// add to the combobox the status we have 
	comboStatus.getItems().add("Active");
	comboStatus.getItems().add("suspended");
	comboStatus.getItems().add("locked");
	savebutt.setDisable(false);
	
	
	
	// this is a listener to validate if the phone number are corect and  it only numbers and the length not equal 10 number 

	PhoneText.textProperty().addListener((observable,oldValue,newValue)->{
		if(!newValue.matches("\\d*")) {
			PhoneText.setText(newValue.replaceAll("[^\\d]", ""));
			Alert alert = new Alert(AlertType.WARNING, "The phone number must contain only numbers", ButtonType.CLOSE);
			alert.show();
 		}
		if(PhoneText.getLength()>10) {
			PhoneText.setText(oldValue);
			Alert alert = new Alert(AlertType.WARNING, "The  phone number must be  10 numbers", ButtonType.CLOSE);
			alert.show();
		}
	});

	 
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

}
