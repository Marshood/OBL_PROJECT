package client;

/**
 * this class is to preform the login to the system 
 */
import java.awt.Button;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import entites.User;

import com.sun.glass.ui.Window.Level;
import com.sun.javafx.logging.Logger;

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
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private Text EmptyFields;

	public User User;

	JOptionPane frame;
	private ArrayList<String> message;
	private String pass;
/**
 * this method is to start the connection with the server 
 * @return
 */
	public boolean connectNOW() {
		ClientConsole c = new ClientConsole();
		boolean con = c.startconnection();
		return true;
	}
/**
 * this method is for searching in the login screen 
 * 
 * @param event
 */
	@FXML
	void Search(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SearchBooks.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("Obl Search Book");
			stage.setResizable(false);
			
			stage.show();
		} catch (Exception e) {
			System.out.println("Error with opening the Search books before login");
		}
	}
/**
 * this method is to preform login to the system with the username and password given in the fields 
 * w
 * @param event
 * @throws IOException
 * @throws ClassNotFoundException
 */
	public void PerformLogin(ActionEvent event) throws IOException, ClassNotFoundException {
		if (txtPassword.getText().trim().isEmpty() || txtUsername.getText().trim().isEmpty()) {
			EmptyFields.setText("Empty Fields");
		} else {
			EmptyFields.setText("");
			User User = new User();
			String pass = (String) txtPassword.getText();
			String user = (String) txtUsername.getText();
			ClientConsole clientCon = new ClientConsole();
			message = new ArrayList<String>();
			message.add("Login");
			message.add(user);
			message.add(pass);
			Object obj = (Object) message;
			User.setUserID(user);
			User.setUserPass(pass);
			Object obj1 = new Object();
			clientCon.execute(obj);
			ArrayList<String> message = (ArrayList<String>) obj;
			try {
				Thread.currentThread().sleep(1500);
			} catch (Exception e) {
			}
			obj1 = clientCon.Getrespond();
			ArrayList<String> message1 = (ArrayList<String>) obj1;

			if (message1.get(0).equals("SuccessfulLogin")) {
				if (message1.get(1).equals("2")) {


					((Node) event.getSource()).getScene().getWindow().hide();
					ArrayList<String> arr = new ArrayList<String>();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainLibrarianGUI.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					MainLibrarianGUI MainLibrarianGUI = fxmlLoader.getController();
					Stage stage = new Stage();
					arr.add(user);
					//arr.add(message1.get(4));
					MainLibrarianGUI.loadData(arr);
					// Stage stage1 = new Stage();
					stage.setResizable(false);
					stage.getIcons().add(new Image("/Pictures/fivecon.png"));
					stage.setTitle("Main Librarian GUI With Working Controller");
					stage.setScene(new Scene(root));
					stage.setOnCloseRequest(e -> {
						try {
							Logoutx(User);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					stage.show();
				}

				if (message1.get(1).equals("1")) {
					((Node) event.getSource()).getScene().getWindow().hide();

					ArrayList<String> arr = new ArrayList<String>();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainStudentGUI.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					MainStudentGUI MainStudentGUI = fxmlLoader.getController();
					MainStudentGUI.FillUser(User);
					Stage stage = new Stage();
					arr.add(user);
					//arr.add(message1.get(4));
					MainStudentGUI.loadData(arr);
					// Stage stage1 = new Stage();
					stage.setResizable(false);
					stage.getIcons().add(new Image("/Pictures/fivecon.png"));
					stage.setTitle("Main Student GUI With Working Controller");
					stage.setScene(new Scene(root));
					stage.setOnCloseRequest(e -> {
						try {
							Logoutx(User);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					stage.show();

				}
				if (message1.get(1).equals("3")) {
					((Node) event.getSource()).getScene().getWindow().hide();
					ArrayList<String> arr = new ArrayList<String>();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					MainMangerGUIController MainMangerGUIController = fxmlLoader.getController();
					Stage stage = new Stage();
					arr.add(user);
					//arr.add(message1.get(4));
					MainMangerGUIController.loadData(arr);
					// Stage stage1 = new Stage();
					stage.setResizable(false);
					stage.setTitle("Main Manger GUI With Working Controller");
					stage.setScene(new Scene(root));
					stage.setOnCloseRequest(e -> {
						try {
							Logoutx(User);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
					stage.show();

				} 
			} else if (message1.get(0).equals("alreadyloggedin")) {
				Alert alert = new Alert(AlertType.ERROR, " This account is already logged in.", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Re-try Again");
				alert.show();
			} else if (message1.get(0).equals("WrongPassword")) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid Username or Password.", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Re-try Again");
				alert.show();
			} else if (message1.get(0).equals("UserDoesn'tExist")) {
				Alert alert = new Alert(AlertType.ERROR, "User Doesn't Exist.", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Re-try Again");
				alert.show();
			}
			else if (message1.get(0).equals("Locked")) {
				Alert alert = new Alert(AlertType.WARNING, "Reader Card is Locked, for further information Contact the library managment ", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Reader Card Is Locked");
				alert.show();
			}
			else if (message1.get(0).equals("Suspended")) {
				Alert alert = new Alert(AlertType.WARNING, "Reader Card is Suspended, for further information Contact the library managment, you can only use the Search Option", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Reader Card Is Locked");
				alert.show();
			}
			else if (message1.get(0).equals("Freez")) {
				Alert alert = new Alert(AlertType.WARNING, "Reader Card is Freez, for further information Contact the library managment, you can only use the Search Option", ButtonType.CLOSE);
				alert.setTitle("Login failure");
				alert.setHeaderText("Reader Card Is Freez");
				alert.show();
			}
		}

	}

	public void exitLogin() {
		// JOptionPane.showMessageDialog(frame, "Goodbye , See ya Next Time :)");
		System.out.println("Goodbye , See ya Next Time :)");
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// User User = new User();
		// TODO Auto-generated method stub

	}

	public void Logoutx(User User) throws ClassNotFoundException {

		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("userlogout");
		message.add(User.getUserID());
		message.add(User.getUserPass());


		Object obj = (Object) message;
		Object obj1 = new Object();

		clientCon.execute(obj);

		ArrayList<String> message = (ArrayList<String>) obj;

		

		try {
			Thread.currentThread().sleep(1400);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond();
	}
}
