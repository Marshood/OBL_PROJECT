package client;
/**
 * this class for the main manager gui 
 * 
 */
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.glass.ui.Window.Level;
import com.sun.javafx.logging.Logger;

import entites.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class MainMangerGUIController implements Initializable {

	private ArrayList<String> message;

	public String Username;

	public String Password;
    @FXML
    private Text hellotext;
    @FXML
    private Text text2;
	public User User;
	@FXML
	private Button IssueNewReport;
	
	@FXML
	private Text Greeting;

/**
 * this method is to initilalize the greeting message for the user 
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		// TODO Auto-generated method stub
	}

	public void FillUser(User user) {
		this.User = user;
	}
/**
 * this method is to go back to the login menu after log out 
 * 
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void exit(MouseEvent event) throws ClassNotFoundException, IOException { // here the logout

		boolean testat = Logout();
		((Node) event.getSource()).getScene().getWindow().hide();
    	User user1= new User();
    	user1.setUserID(text2.getText());
     	Logoutx(user1);
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

	private void Logoutx(entites.User user1) {
		// TODO Auto-generated method stub
		
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
		message.add(text2.getText());
		message.add(text2.getText());


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
		return false;

	}
/**
 * this method is for the manager to add new subscriber 
 * 
 * @param event
 */
	@FXML
	void AddNewSunscriber(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AddNewSubscriberGUI.fxml"));
			Parent root =(Parent) fxmlLoader.load();
			AddNewSubscriberController AddNewSubscriberController=fxmlLoader.getController();
			Stage stage = new Stage();
			arr.add(text2.getText());
			arr.add("Managment");
			AddNewSubscriberController.loadData(arr);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Manager Add New Subscriber");
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
 *this method is for the manager to view student info 
 * 
 * @param event
 */
	@FXML
	void ViewSubscriber(MouseEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();

			ArrayList<String> arr = new ArrayList<String>();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchSubscriberGUI.fxml"));
			Parent root =(Parent) fxmlLoader.load();
			SearchSubscriberGUIController SearchSubscriberGUI =fxmlLoader.getController();
			Stage stage = new Stage();
			arr.add(text2.getText());
			arr.add("Managment");
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
		}
		catch (Exception e) {
			System.out.println("Error with opening Librarian view  Subscriber  ");
		}
	}
/**
 * this method is for the manger to manage the books in  the library 
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
			arr.add(text2.getText());
			arr.add("Managment");
			// arr.add("311286694");
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
 * this method is for the manager to issue a book for a student
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
			arr.add(text2.getText());
			arr.add("Managment");
			// arr.add("311286694");
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
 * this method is for the manager to perform a retrurn of a book 
 * 
 * @param event
 */
	@FXML
	void ReturnBook(MouseEvent event) {

		System.out.println("Working GUI");
		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LibrarianReturnBookGUI.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			LibrarianReturnBookController LibrarianReturnBookController = fxmlLoader.getController();
			Stage stage = new Stage();
			ArrayList<String> arr= new ArrayList<String>();
			arr.add(text2.getText());
			arr.add("Managment");
			LibrarianReturnBookController.loadData(arr);
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("LibrarianReturnBooksGUI");
			stage.setResizable(false);
			stage.show();
			} catch (Exception e) {
			System.out.println("Error with opening Librarian Issue Book");
		}
	}

	@FXML
	void Search(MouseEvent event) {
		System.out.println("Working GUI");
	}
/**
 * this method is for the manager to view the workers in the library 
 * @param event
 */
	@FXML
	void ViewWorkers(MouseEvent event) {
		try {

	 		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ViewWorkersGUI.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("ViewWorkersGUI");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening ViewWorkersGUI");
		}
	}
	/**
	 * this method is for the manger to freeze a student card manaually 
	 * @param event
	 */
	@FXML
	void FreezeStudentCard(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ShowSubScriberAccountStatusGUI.fxml"));
			Parent root =(Parent) fxmlLoader.load();
			LateStudentTofreezeGUIController LateStudentTofreezeGUIController=fxmlLoader.getController();
			Stage stage = new Stage();
			arr.add(text2.getText());
			arr.add("Managment");
			LateStudentTofreezeGUIController.loadData(arr);
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Manager Add New Subscriber");
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
			System.out.println("Error with opening Late Student to freeze GUI");
			}	}
/**
 * this method is for the manger to issue a new report
 * @param event
 */
	@FXML
	void IssueNewReport(MouseEvent event) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/IssueNewReportGUI.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("ReportsGUI");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening IssueNewReportGUI");
		}
	}
/**
 * this method is for the selection of the report it self 
 * @param event
 */
	 @FXML
	    void ReportsFunc(MouseEvent event) {
	    	ArrayList<String> arr = new ArrayList<String>();

			try {
				((Node) event.getSource()).getScene().getWindow().hide();

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SelectReportsGUI.fxml"));
				Parent root =(Parent) fxmlLoader.load();
				SelectReportsGUIController SelectReportsGUIController=fxmlLoader.getController();
				Stage stage = new Stage();
				arr.add(text2.getText());
				SelectReportsGUIController.loadData(arr);
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/Pictures/fivecon.png"));
				stage.setTitle("Manager Report's");
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
				System.out.println("Error with opening ReportsFunc");
			}
			}
	 /**
	  * this method is for loading info from the last stage that we were in 
	  * @param resultLoadData
	  */
    public void  loadData(ArrayList<String> resultLoadData) {
        //get all the information from resultLoadData
        //and set to the text fields and combobox
    	text2.setText(resultLoadData.get(0));
		System.out.println(resultLoadData.get(0)+"Main Manager 3 ");

         	
    	}
}
