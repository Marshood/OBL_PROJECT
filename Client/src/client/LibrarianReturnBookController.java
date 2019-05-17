package client;
/**
 * this class is for the librarian to return the book 
 */

import java.io.IOException;
import java.util.ArrayList;

import entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LibrarianReturnBookController {
	
	
    @FXML
    private TextField BooklD;
    @FXML
    private TextField studentid;


    @FXML
    private Text returnBookSucess;
	private ArrayList<String> message;
	ClientConsole clientCon = new ClientConsole();
	String userId,userType;
	User user;
	/**
	 * this method is to perform the returning process 
	 * @param event
	 * @throws ClassNotFoundException
	 */
    @FXML
    void returnBook(ActionEvent event) throws ClassNotFoundException {
    	
		message = new ArrayList<String>();
 		message.add("ReturnBook");
		message.add(BooklD.getText());
		message.add(studentid.getText());
		//(message +"msgggggg");
 		Object obj = (Object) message;
 		Object obj1 = new Object();
 		clientCon.execute(obj);
 		ArrayList<String> message = (ArrayList<String>) obj;
 		try {
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		obj1 = clientCon.Getrespond();

			returnBookSucess.setVisible(true);
			
    }
/**
 * this method is to go back to the previous stage 
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void exit(ActionEvent event) throws ClassNotFoundException, IOException{ 
		User user1= new User();
		user1.setUserID(userId);
		ArrayList<String> arr = new ArrayList<String>();
		if(userType.equals("Managment")) {
		((Node) event.getSource()).getScene().getWindow().hide();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		MainMangerGUIController display=fxmlLoader.getController();	
		// we save the paramter we want to send to the anther GUI 
		arr.add(user1.getUserID());
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
			arr1.add(user1.getUserID());
			MainLibrarianGUI.loadData(arr1);
			// Stage stage1 = new Stage();
			stage.setResizable(false);
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("OBL");
			stage.setScene(new Scene(root));
			stage.setOnCloseRequest(e -> {
				try {	
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
 * this method is to load the user type and id from the last stage
 * 
 * @param arr
 */
	public void loadData(ArrayList<String> arr) {
		// TODO Auto-generated method stub
		
	  	userType=arr.get(1);

			userId=arr.get(0);
	}
	/**
	 * this method is log out of the system 
	 * @param User
	 * @throws ClassNotFoundException
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
		message.add(userId);
		message.add(userId);
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
}

