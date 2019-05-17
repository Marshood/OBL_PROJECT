package client;

import java.io.IOException;
import java.util.ArrayList;

import entites.Book;
import entites.LibrarianNew;
import entites.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
* This class is Controller to Search Subscriber First Name Or Last Name And show t
* he all Subscriber we have the same name 
* in this class we get the all information about Subscriber's
* and sho the all information about the Subscriber   
* and we can chose some one and edit him 
*
* @author Marshood Ayoub , 311286694
* @version Jan 2019
*/
public class SearchSubscriberByNameController {
	 // this arrayList message to send the information about new subscriber 
	private ArrayList<String> message;
	// this to save the current user id 
	User user= new User();
    // this button Logoutbutt to log out ant return to the start paje (Exit)
    @FXML
    private Button Logoutbutt;
    // this table View is to show the search result 
    @FXML
    private TableView<User> table;
    // this table column IDSubScriber represent Student's Id we found   
    @FXML
    private TableColumn<User, String> IDSubScriber;
    // this table column Pass represent Student's Password we found   

    @FXML
    private TableColumn<User, String> Pass;
    // this table column SubScriberType represent Student'sType we found   

    @FXML
    private TableColumn<User,String> SubScriberType;
    // this table column memberShip represent Student's memberShip status we found   

    @FXML
    private TableColumn<User, String> memberShip;
    // this table column FirstName represent Student's First name we found   

    @FXML
    private TableColumn<User, String> FirstName;
    // this table column LastName represent Student's Last name we found   

    @FXML
    private TableColumn<User, String> LastName;
    // this table column Email represent Student's email we found   

    @FXML
    private TableColumn<User, String> Email;
    // this table column Phone represent Student's Phone we found   
    @FXML
    private TableColumn<User, String> Phone;
    // this table column GradationDate represent Student's Gradation Date we found   
    @FXML
    private TableColumn<User, String> GradationDate;
    // this table column lateCounter represent Student's counter return late we found  
    @FXML
    private TableColumn<User, String> lateCounter;
    // this button backbutt to back to the last gui 
    @FXML
    private Button backbutt;
 // this button ShowLoanedBooksBTN to show the selected student loaned books' 
    @FXML
    private Button ShowLoanedBooksBTN;
    // this button EditSubscriberbutt to edit the stuend we select in the table view 
    @FXML
    private Button EditSubscriberbutt;
    // this table column Organization represent Student's Organizationwe found   
    @FXML
    private TableColumn<User, String> Organization;
    @FXML
    private Button Refreshbutt;
    // this text field is to show the user id in the screen 
     String usertype;
    @FXML
    private Text UserIdText;
    // this arrayList resultLoadData to send the information about the corrent user when we want to switch to another GUI
    ArrayList<String> resultLoadData1= new ArrayList<String>(); 
    ArrayList<String> resultLoadData= new ArrayList<String>(); 

    // this arrayList UserSub to get  the information about search by name
    ArrayList<User> UserSub= new ArrayList<User>();
    
    String searchtype ;
    String  searchName;
	/* This method to get the Subscriber information  we found after search in DB 
	 * 
	 *@author Marshood Ayoub , 311286694
	 * @param message1 The ArrayList<User>  this is the all user we found 
	 * 
	 **/
    public int loadDataUser(ArrayList<User> message1) {
    	// TODO Auto-generated method stub
    	int i= message1.size();
    	int j;
        for (j=0;j<i;j++) 
      { 				 
        User Students = new User();   
        Students.setUserID(message1.get(j).getUserID());
		Students.setUserPass(message1.get(j).getUserPass());
		Students.setUserType("Student");
		Students.setMemberShipStatus(message1.get(j).getMemberShipStatus());
		Students.setFisrtName(message1.get(j).getFisrtName());
		Students.setLastName(message1.get(j).getLastName());
		Students.setEmail(message1.get(j).getEmail());
		Students.setPhone(message1.get(j).getPhone());
		Students.setGradationDate(message1.get(j).getGradationDate());
		Students.setOrganization(message1.get(j).getOrganization());
		Students.setCounterLate(message1.get(j).getCounterLate());
		UserSub.add(Students);
		System.out.println(message1.get(j).getOrganization()+"11111111111");
		System.out.println("usersub"+UserSub.get(0).getOrganization());
       }
    IDSubScriber.setCellValueFactory(new PropertyValueFactory<User, String>("UserID"));
	Pass.setCellValueFactory(new PropertyValueFactory<User, String>("UserPass"));
	SubScriberType.setCellValueFactory(new PropertyValueFactory<User, String>("UserType"));
	memberShip.setCellValueFactory(new PropertyValueFactory<User, String>("MemberShipStatus"));
	FirstName.setCellValueFactory(new PropertyValueFactory<User, String>("FisrtName"));
	LastName.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));
	Email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
	Phone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
	GradationDate.setCellValueFactory(new PropertyValueFactory<User, String>("GradationDate"));
	lateCounter.setCellValueFactory(new PropertyValueFactory<User, String>("counterLate"));
	Organization.setCellValueFactory(new PropertyValueFactory<User, String>("Organization"));

	ObservableList<User> toShow = FXCollections.observableArrayList();
	toShow.addAll(UserSub);
	table.setItems(toShow);
	return 1;
    }
  /*
   * this method to get the details about the subscriber
   *  we select to send the information to edit gui 
   * */
    @FXML
    void selectedSub(MouseEvent event) {
    	if(!table.getSelectionModel().isEmpty()) {
        ArrayList<String> resultLoadData= new ArrayList<String>(); 
    	User student = table.getSelectionModel().getSelectedItem();
    	resultLoadData.add(student.getUserID());//userID 
    	resultLoadData.add(student.getUserPass());  //pass  	
   		resultLoadData.add("Student");//userType  
   		resultLoadData.add(student.getMemberShipStatus());//MemberShipStatus
   		resultLoadData.add(student.getFisrtName());//first name
   		resultLoadData.add(student.getLastName());//last name
   		resultLoadData.add(student.getEmail());//email 
   		resultLoadData.add(student.getPhone());//phone  
   		resultLoadData.add(student.getGradationDate());//GradationDate
   		resultLoadData.add(student.getOrganization());//Organization
   		resultLoadData.add(student.getCounterLate());//late counter 
        resultLoadData1.clear();

   		resultLoadData1.addAll(resultLoadData);

   		System.out.println(resultLoadData1);
					    }
    	}
    
    
    /*
     * initialize the current screen  
     * */
    @SuppressWarnings("unchecked")
	@FXML
    void initialize() {
          // to enable the button after seletec any subscriber 
    	
    	  EditSubscriberbutt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
    	  ShowLoanedBooksBTN.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));

    }
    
	/**
	 * This method to back to the last GUI page 
	 * and send the information about the current user to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @param 
	 * @throws IOException 
	 */
    @FXML
    void BackFunc(ActionEvent event) throws IOException {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberGUI.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		SearchSubscriberGUIController display=fxmlLoader.getController();
		// we save the paramter we want to send to the anther GUI 
		resultLoadData.add(UserIdText.getText());
		resultLoadData.add(usertype);
	//	fxmlLoader.load();
		// loadData this func location at the SearchSubscriberGUIController 
		display.loadData(resultLoadData);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));

		//stage.setTitle("Librarian Add New Subscriber");
		//stage.setResizable(false);
		stage.setOnCloseRequest(e -> {
			try {
				logoutfunc();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
    }
	/**
	 * This method to Edit   the specific subscriber  
	 * and save the new information about the current subscriber to the GUI 
	 *@author Marshood Ayoub , 311286694
	 * @param 
	 * @throws IOException 
	 */
    @FXML
    void EditSubscriberFunc(ActionEvent event) {
    	try {
    		//((Node) event.getSource()).getScene().getWindow().hide();

    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/EditSubscriberAfterSearch.fxml"));
    		Parent root =(Parent) fxmlLoader.load();
    		EditSubscriberAfterSearchController display=fxmlLoader.getController();
    		resultLoadData.add(UserIdText.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
    	  //fxmlLoader.load();
    		resultLoadData1.add(UserIdText.getText());// to send the information id about the current user
    	    display.loadData(resultLoadData1);
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setTitle("Edit Subscriber");
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));

    		stage.setResizable(false);
    		stage.setOnCloseRequest(e -> {
    			//LogoutFunc(event);
				//((Node) event.getSource()).getScene().getWindow().hide();
    		});
    		
    		//stage.show();
    		//stage.isShowing();
    		stage.showAndWait();
    		
    		RefreshbuttFuncc(event);    		
    	} catch (Exception e)
    	{ System.out.println(e);
    		System.out.println("Error with opening Librarian Edit subscriber ");
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
    void LogoutFunc(ActionEvent event) throws ClassNotFoundException, IOException {
    	User user1= new User();
    	user1.setUserID(user.getUserID());
     	Logoutx(user1);
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
				Logoutx(user1);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
		
		
		
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
      
        	UserIdText.setText(resultLoadData.get(0));
        	 searchtype=resultLoadData.get(2);
        	 searchName=resultLoadData.get(3);
        	 usertype=resultLoadData.get(4);
    	}
    
    @FXML
    void RefreshbuttFuncc(ActionEvent event) throws ClassNotFoundException {
      if(searchtype.equals("FirstName"))
      {
    	  /*
      	 *ArrayList messageID we save the  user id to check if exist 
      	 *clientCon in to send to server the data and to get the respond from the the server 
      	 * ArrayList message1 to get the respond from the server 
      	 */
      	ClientConsole clientCon = new ClientConsole();
   		ArrayList<String> messageID = new ArrayList<String>();
   		  // save user id in messageID
          //in the index 0 in saveData we send the name about the func we want to do 
      	messageID.add("checkUserExistByFirstName"); 
      	messageID.add(searchName);
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
  			Thread.currentThread().sleep(1000);
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
  				resultLoadData.add(UserIdText.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
  			//	fxmlLoader.load();
  			
  				resultLoadData.add(UserIdText.getText());// to send the information id about the current user
  				int k=display.loadDataUser(message1);
  				resultLoadData.add("FirstName");
  				resultLoadData.add(searchName);
  				resultLoadData.add(usertype);
  				display.loadData(resultLoadData);
  				Stage stage = new Stage();
  				stage.setScene(new Scene(root));
  				stage.setTitle("Subscriber Details");
  				stage.setResizable(false);
  				stage.setOnCloseRequest(e -> {
  					try {
  						logoutfunc();
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
       * else if search by last name 
       * */
      else if (searchtype.equals("LastName")) {
    	  /*
      	 *ArrayList messageID we save the  user id to check if exist 
      	 *clientCon in to send to server the data and to get the respond from the the server 
      	 * ArrayList message1 to get the respond from the server 
      	 */
      	ClientConsole clientCon = new ClientConsole();
   		ArrayList<String> messageID = new ArrayList<String>();
   		  // save user id in messageID
          //in the index 0 in saveData we send the name about the func we want to do 
      	messageID.add("checkUserExistByLastName"); 
      	messageID.add(searchName);
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
  				resultLoadData.add(UserIdText.getText());// resultLoadData  // this to show the user (Managment or libriran )id 
  			//	fxmlLoader.load();
  				resultLoadData.add(UserIdText.getText());// to send the information id about the current user
  				int k=display.loadDataUser(message1);
  				resultLoadData.add("LastName");
  				resultLoadData.add(searchName);
  				resultLoadData.add(usertype);
  				display.loadData(resultLoadData);
  				Stage stage = new Stage();
  				stage.setScene(new Scene(root));
  				stage.setTitle("Subscriber Details");
  				stage.setResizable(false);
  				stage.setOnCloseRequest(e -> {
  					try {
  						logoutfunc();
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
		message.add(UserIdText.getText());
		message.add(UserIdText.getText());

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
    public void logoutfunc() throws ClassNotFoundException
    {
  		/*
    	 *    ArrayList message we save the all user data to send to the server 
    	 *   clientCon in to send to server the data and to get the respond from the the server 
    	 * */
 		ClientConsole clientCon = new ClientConsole();
 		ArrayList<String> message123 = new ArrayList<String>();
        // save all the new data about the Subscriber and 
        //in the index 0 in message we send the name about the func we want to do 

 		message123.add("userlogout");
 		message123.add(UserIdText.getText());
 		message123.add("test");


 		System.out.println("logout..");
 		/* we send from the client to server only object we
    	*change the type of the saveData to object and send it to server
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
 		/* we get the responed from the server in type object obj1
 	     * and after this we change it according to protocol we use 
 	     *  
 	     */
 		obj1 = clientCon.Getrespond();
 		
 		System.out.println("exited");
    }
    /**
     * this method is to show loaned book for the selected from the table 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    void ShowLoanedBooks(ActionEvent event) throws IOException ,ClassNotFoundException{

    	ArrayList<String> arr = new ArrayList<String>();
    	//((Node) event.getSource()).getScene().getWindow().hide();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LoanedBooksGUI.fxml"));
    	Parent root =(Parent) fxmlLoader.load();
		LoanedBooksController display=fxmlLoader.getController();
		Stage stage = new Stage();
		arr.add(resultLoadData1.get(4));
		arr.add(resultLoadData1.get(0));
		arr.add(UserIdText.getText());
	    display.loadData(arr);
		stage.setScene(new Scene(root));
		
		display.start(stage);
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("Librarian See Student Loaned Books");
		stage.setResizable(true);
		stage.show();
		
    }
    }
 
    
