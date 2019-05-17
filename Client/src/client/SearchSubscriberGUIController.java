package client;

 import java.io.IOException;
import java.util.ArrayList;

import entites.LibrarianNew;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * This class is Controller to manager search  Subscriber and after enter the  Subscriber id 
 * click Searchbuttn and go to the Manager View Subscriber gui  
 * 
 *
 * @author Marshood Ayoub , 311286694
 * @version Jan 2019
 */
public class SearchSubscriberGUIController {
	/**
	 * This is the Text Field and the Button we have at the GUI SearchSubscriberGUIController
	 */
    // this button Backbutt to Back to the last frame ( last GUI ) 
    @FXML
    private Button Backbutt;
    // this button logoutbutt to logout the user and return to the log in frame
    @FXML
    private Button Logoutbutt;
   /* this botton Searchbuttn to search the subscriber if exist in DB
    *  and after this check if the user exist we open 
    *  Manager view Subscriber gui with the information about the Subscriber 
    *    
    */
    @FXML
    private Button Searchbuttn;
    // this bottun to show the user id in the top of the gui 
    @FXML
    private  Text text1;
    // this text field SubScriberID have a ID Subscriber to search 
    @FXML
    private TextField SubScriberID;
    
    static  String ID;

    @FXML
    private Text UserIDtext;
    // this text field   SubscriberFirstName have a ID Subscriber First Name to search 
    @FXML
    private TextField SubscriberFirstName;
    // this text field   SubscriberLastName have a ID Subscriber Last Name to search 
    @FXML
    private TextField SubscriberLastName;
    /* this botton Searchbuttn to search the subscriber if exist in DB
     *  and after this check if the user exist we open 
     *  Manager view Subscriber gui with the information about the Subscriber 
     *  Search by first Name 
     */
    @FXML
    private Button Searchbuttn1;
    /* this botton Searchbuttn to search the subscriber if exist in DB
     *  and after this check if the user exist we open 
     *  Manager view Subscriber gui with the information about the Subscriber 
     *  Search by Last Name 	
     */
    @FXML
    private Button Searchbuttn2;
	public ArrayList<String> message;
/* this arrayList resultLoadData to send the information 
 * about the send the user ID and the information we get frome the server 
 * about the Subscriber 
 */
     ArrayList<String> resultLoadData= new ArrayList<String>(); 
     
     String id;
     User user;  
     String FisrtName;    
     String LastName;
     String usertype;
     
 	/**
 	 * This method to get the Manager \ Libriran id  
 	 *@author Marshood Ayoub , 311286694
 	 * @param resultLoadData The ArrayList<string> to get the information .
 	 * and set it to text field text1 
 	 */ 
     
 
    public void  loadData(ArrayList<String> resultLoadData) {
        //get all the information from resultLoadData
        //and set to the text fields and combobox
    	id=resultLoadData.get(0);
    	UserIDtext.setText(resultLoadData.get(0));
    	usertype=resultLoadData.get(1);
         	
    	}
    
	/**setText
	 * This method to back to the last GUI page 
	 * and send the information about the current user to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @throws IOException 
	 */
    @FXML 
   void BackbuttFunc(ActionEvent event) throws IOException {
    	if(usertype.equals("Managment")) {
    		((Node) event.getSource()).getScene().getWindow().hide();

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
    		Parent root =(Parent) fxmlLoader.load();
    		MainMangerGUIController display=fxmlLoader.getController();	
    		ArrayList<String> arr = new ArrayList<String>();;
			// we save the paramter we want to send to the anther GUI 
    		arr.add(UserIDtext.getText());
    		arr.add(usertype);
//    		this.user.setUserID("311286694");
    	    display.loadData(arr);
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		stage.setTitle("Librarian Add New Subscriber");
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
    			arr1.add(UserIDtext.getText());
    			MainLibrarianGUI.loadData(arr1);
    			// Stage stage1 = new Stage();
    			stage.setResizable(false);
    			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
    			stage.setTitle("Main Librarian GUI With Working Controller");
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
	 * This method to logout  the current user 
	 * and send the information about the current user to the server using Logoutx method  
	 * to change the status of is LOg in   
	 *@author Marshood Ayoub , 311286694
	 * @throws IOException 
	 */
    @FXML
    void LogoutbuttFunc(ActionEvent event) throws ClassNotFoundException, IOException {
    	User user= new User();
    	user.setUserID(id);
     	Logoutx(user);
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
		     	Logoutx(user);
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
		message.add(id);
		message.add(id);


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
    
    
    
    /*
     * @author Marshood Ayoub , 311286694
     * this method SearchbuttnFunc is after to click in the Searchbuttn
     * we check if the user exist in the db and after this if 
     * the user exist  we get all the information about the user 
     * and send it to the next gui  ManagerViewSubscriberGUIController
     * */   
    
    @FXML
    void SearchbuttnFunc(ActionEvent event) throws ClassNotFoundException, InterruptedException {
    	/*
    	 *ArrayList messageID we save the  user id to check if exist 
    	 *clientCon in to send to server the data and to get the respond from the the server 
    	 * ArrayList message1 to get the respond from the server 
    	 */
    	ClientConsole clientCon = new ClientConsole();
 		ArrayList<String> messageID = new ArrayList<String>();
 		  // save user id in messageID
        //in the index 0 in saveData we send the name about the func we want to do 
         ID= SubScriberID.getText();
    	messageID.add("checkUserExistOnSearchSub"); 
    	messageID.add(ID);
    	/* we send from the client to server only object we
    	*change the type of the saveData to object and send it to server
    	*
    	*/
    	Object obj = (Object) messageID;
		Object obj1 = new Object();
		// send the message to the server 

		clientCon.execute(obj);
		// this sleep to wait to server to send the respond 

 	   try {
			Thread.currentThread().sleep(800);
		} catch (Exception e) {
			System.out.println("Exception At search subscriber");
		}
 	    
 	    
	    
 	   //synchronized (clientCon){clientCon.wait();}
 	   
 	  /* we get the responed from the server in type object obj1
	     * and after this we change it according to protocol we use 
	     *  ArrayList message1 to get the respond from the server 
	     */
		obj1=clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList <String>) obj1;

		// we check the message  if the edit succsefull ot not 

 		if(message1.get(0).equals("SuccessfulL")&&message1.get(3).equals("1"))
		{     	
		resultLoadData.add(message1.get(1));//userID 
   		resultLoadData.add(message1.get(2));//userpass
   		resultLoadData.add("Student");//userType  
//   	resultLoadData.add(message1.get(3));//userType  
   		resultLoadData.add(message1.get(4));//MemberShipStatus
   		resultLoadData.add(message1.get(5));//first name
   		resultLoadData.add(message1.get(6));//last name
   		resultLoadData.add(message1.get(7));//email 
   		resultLoadData.add(message1.get(8));//phone  
   		resultLoadData.add(message1.get(9));//GradationDate
   		resultLoadData.add(message1.get(10));//Organization
   		resultLoadData.add(message1.get(11));//late counter 

   		
	try {
		((Node) event.getSource()).getScene().getWindow().hide();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ManagerViewSubscriberGUI.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		ManagerViewSubscriberGUIController display=fxmlLoader.getController();
		resultLoadData.add(UserIDtext.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
	//	fxmlLoader.load();
		resultLoadData.add(text1.getText());// to send the information id about the current user
		resultLoadData.add(usertype);
		display.loadData(resultLoadData);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Subscriber Details");
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
		
	} catch (Exception e)
	{ System.out.println(e);
		System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
	}
	
		}
		else {
   
    	Alert alert = new Alert(AlertType.ERROR, "Student are not Exist..", ButtonType.CLOSE);
		alert.setTitle("Search Subscriber");
		alert.setHeaderText("Re-try Again");
		alert.show();}
		
    	
    }
    /*
     * @author Marshood Ayoub , 311286694
     * this method SearchbuttnFunc is after to click in the Searchb uttn
     * we check if the fisrt name  exist in the db and after this if 
     * the user exist  we get all the information about the user 
     * and send it to the next gui  ManagerViewSubscriberGUIController
     * */  

    @FXML
    void SearchbyFisrName(ActionEvent event) throws ClassNotFoundException {
    	/*
    	 *ArrayList messageID we save the  user id to check if exist 
    	 *clientCon in to send to server the data and to get the respond from the the server 
    	 * ArrayList message1 to get the respond from the server 
    	 */
    	ClientConsole clientCon = new ClientConsole();
 		ArrayList<String> messageID = new ArrayList<String>();
 		  // save user id in messageID
        //in the index 0 in saveData we send the name about the func we want to do 
         FisrtName= SubscriberFirstName.getText();
    	messageID.add("checkUserExistByFirstName"); 
    	messageID.add(FisrtName);
    	/* we send from the client to server only object we
    	*change the type of the saveData to object and send it to server
    	*
    	*/
    	Object obj = (Object) messageID;
		Object obj1 = new Object();
		// send the message to the server 

		clientCon.execute(obj);
		// this sleep to wait to server to send the respond 

 	   try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At search subscriber");
		}
 	    
 	    
	    
 	   //synchronized (clientCon){clientCon.wait();}
 	   
 	  /* we get the responed from the server in type object obj1
	     * and after this we change it according to protocol we use 
	     *  ArrayList message1 to get the respond from the server 
	     */
		obj1=clientCon.Getrespond();
		ArrayList<User> message1 = (ArrayList <User>) obj1;
      // check if  we have some subscriber have the same name 
		if (message1.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR, "No Subscriber Found", ButtonType.CLOSE);
			alert.show();
		}
		else {
			 
			try {
				((Node) event.getSource()).getScene().getWindow().hide();

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberByName.fxml"));
				Parent root =(Parent) fxmlLoader.load();
				SearchSubscriberByNameController display=fxmlLoader.getController();
				resultLoadData.add(UserIDtext.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
			//	fxmlLoader.load();
				resultLoadData.add(text1.getText());// to send the information id about the current user
				int k=display.loadDataUser(message1);
				//System.out.println(message1.get(1).getOrganization()+"org"+message1.get(1).getCounterLate()+" late "+message1.get(1).getGradationDate()+" gra");
				resultLoadData.add("FirstName");
				resultLoadData.add(FisrtName);
				resultLoadData.add(usertype);
				display.loadData(resultLoadData);
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Subscriber Details");
				stage.setResizable(false);
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
				
			} catch (Exception e)
			{ System.out.println(e);
				System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
			}
			
			
			
			
			
		}
    }
    /*
     * @author Marshood Ayoub , 311286694
     * this method SearchbuttnFunc is after to click in the Searchb uttn
     * we check if the last name  exist in the db and after this if 
     * the user exist  we get all the information about the user 
     * and send it to the next gui  ManagerViewSubscriberGUIController
     * */
    @FXML
    void SearchbyLastName(ActionEvent event) throws ClassNotFoundException {
    	/*
    	 *ArrayList messageID we save the  user id to check if exist 
    	 *clientCon in to send to server the data and to get the respond from the the server 
    	 * ArrayList message1 to get the respond from the server 
    	 */
    	ClientConsole clientCon = new ClientConsole();
 		ArrayList<String> messageID = new ArrayList<String>();
 		  // save user id in messageID
        //in the index 0 in saveData we send the name about the func we want to do 
 		LastName= SubscriberLastName.getText();
    	messageID.add("checkUserExistByLastName"); 
    	messageID.add(LastName);
    	
    	/* we send from the client to server only object we
    	*change the type of the saveData to object and send it to server
    	*
    	*/
    	Object obj = (Object) messageID;
		Object obj1 = new Object(); 
		// send the message to the server 

		clientCon.execute(obj);
		// this sleep to wait to server to send the respond 

 	   try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At search subscriber");
		}
 	    
 	    
	    
 	   //synchronized (clientCon){clientCon.wait();}
 	   
 	  /* we get the responed from the server in type object obj1
	     * and after this we change it according to protocol we use 
	     *  ArrayList message1 to get the respond from the server 
	     */
		obj1=clientCon.Getrespond();
		ArrayList<User> message1 = (ArrayList <User>) obj1;
		System.out.println(message1+"rawwwwwww");
      // check if  we have some subscriber have the same name 
		if (message1.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR, "No Subscriber Found", ButtonType.CLOSE);
			alert.show();
		}
		else {
			 
			try {
				((Node) event.getSource()).getScene().getWindow().hide();
 
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberByName.fxml"));
				Parent root =(Parent) fxmlLoader.load();
				SearchSubscriberByNameController display=fxmlLoader.getController();
				resultLoadData.add(UserIDtext.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
				int k=display.loadDataUser(message1);
			
				//fxmlLoader.load();
				resultLoadData.add(text1.getText());// to send the information id about the current user
				resultLoadData.add("LastName");
				resultLoadData.add(LastName);
				resultLoadData.add(usertype);

				//display.loadDataUser(message1);
				display.loadData(resultLoadData);
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Subscriber Details");
				stage.setResizable(false);
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
				
			} catch (Exception e)
			{ System.out.println(e);
				System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
			}
			
			
			
			
			
		}
    }
    
    
    
    /*
     * initialize the current screen 
     * */
    @FXML
    void initialize() {
    // text1.setText(id);
    	BooleanBinding textField2Valid = SubScriberID.textProperty().isEmpty();
    	// check textField2.getText() and return true/false as appropriate
    	Searchbuttn.disableProperty().bind(textField2Valid);  
    	
    	// this is a listener to validate if the id it only numbers and the length  not graeter than 9 number 
    	SubScriberID.textProperty().addListener((observable,oldValue,newValue)->{
    		if(!newValue.matches("\\d*")) {
    			SubScriberID.setText(newValue.replaceAll("[^\\d]", ""));
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must contain only numbers", ButtonType.CLOSE);
    			alert.show();
     		}
    		if(SubScriberID.getLength()>9) {
    			SubScriberID.setText(oldValue);
    			Alert alert = new Alert(AlertType.WARNING, "The Subscriber ID must be  9 numbers", ButtonType.CLOSE);
    			alert.show();
    		}
    	});
    	BooleanBinding textField2Valid1 = SubscriberFirstName.textProperty().isEmpty();
    	// check textField2.getText() and return true/false as appropriate
    	Searchbuttn1.disableProperty().bind(textField2Valid1); 
    	
    	BooleanBinding textField2Valid2 = SubscriberLastName.textProperty().isEmpty();
    	// check textField2.getText() and return true/false as appropriate
    	Searchbuttn2.disableProperty().bind(textField2Valid2);
    
    }
    
    public boolean Logout() throws ClassNotFoundException {

		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("userlogout");
		message.add(UserIDtext.getText());
		message.add(UserIDtext.getText());

		System.out.println("Request to logout by mrblackstar");

		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		ArrayList<String> message = (ArrayList<String>) obj;

		System.out.println(message + "final");

		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); //
		System.out.println(obj1 + "test for obj1"); //
		ArrayList<String> message1 = (ArrayList<String>) obj1; //
		//System.out.println("hkahslajldskasd" + message1.get(0));//
		return false;

	}
    }


