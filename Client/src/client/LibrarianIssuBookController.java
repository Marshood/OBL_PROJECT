package client;
/**
 * this class is for the librarian to issue a book for the student
 * 
 */
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entites.Book;
import entites.User;
import javafx.fxml.Initializable;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LibrarianIssuBookController implements Initializable {

	User user = new User();

	@FXML
	private Text BookNotAvailbe;

	private ArrayList<String> message;
	@FXML
	private Button Issu;
	@FXML
	private TextField StudentID;
	@FXML
	private TextField SearchText;
	@FXML
	private ChoiceBox<String> SearchType;
	@FXML
	private TableView<Book> tabel;
	@FXML
	private TableColumn<Book, String> BookID;
	@FXML
	private TableColumn<Book, String> BookName;
	@FXML
	private TableColumn<Book, String> BookAuthor;
	@FXML
	private TableColumn<Book, String> EditionNumber;
	@FXML
	private TableColumn<Book, String> PublishDate;
	@FXML
	private TableColumn<Book, String> Category;
	@FXML
	private TableColumn<Book, String> BookDescription;
	@FXML
	private TableColumn<Book, String> CopyNumber;
	@FXML
	private TableColumn<Book, String> PurchaseDate;
	@FXML
	private TableColumn<Book, String> ShelfDate;
	@FXML
	private TableColumn<Book, String> Requested;

	private String BookIDSaved;
	private String BookRequested;
	private String CopiesSaved;
	private String LoanedCopiesSaved;
	private int IsAvalibe;
	private int SavedCopies;
	String userid, userType;

	ClientConsole clientCon = new ClientConsole();
/**
 * to exit the current stage 
 * @param event
 */
	@FXML
	void exit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}
/**
 * this method is to control the back button and return to the last page
 * @param event
 * @throws IOException
 */
	@FXML
	void back(ActionEvent event) throws IOException {
		User user1 = new User();
		user1.setUserID(userid);
		ArrayList<String> arr = new ArrayList<String>();
		if (userType.equals("Managment")) {
			((Node) event.getSource()).getScene().getWindow().hide();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			MainMangerGUIController display = fxmlLoader.getController();
			// we save the paramter we want to send to the anther GUI
			arr.add(user1.getUserID());
			display.loadData(arr);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/Pictures/fivecon.png"));
			stage.setTitle("OBL");
			stage.setResizable(false);
			stage.show();
		} else {

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
 * this method is to load the selected item from the table
 * 
 * @param event
 */
	@FXML
	void Select(MouseEvent event) {
		Book book = tabel.getSelectionModel().getSelectedItem();
		if (book != null) {
			// Issu.setVisible(true);
			// StudentID.setVisible(true);
			BookIDSaved = book.getBookID();
			BookRequested = book.getRequested();
			IsAvalibe = (Integer.parseInt(book.getCopyNumber())) - (Integer.parseInt(book.getLoanedCopies()));
			SavedCopies = Integer.parseInt(book.getLoanedCopies());
			// (IsAvalibe);
			if (IsAvalibe > 0) {
				Issu.setVisible(true);
				StudentID.setVisible(true);
				BookNotAvailbe.setText("");
			} else {
				BookNotAvailbe.setText("No Avalibe Copies");
				Issu.setVisible(false);
				StudentID.setVisible(false);

			}
		}
	}
/**
 * this method is to find the wanted book
 * 
 * @param event
 * @throws ClassNotFoundException
 */
	@FXML
	void FindBook(ActionEvent event) throws ClassNotFoundException {
		// NoBooksFound.setText("");
		message = new ArrayList<String>();
		if (SearchText.getText().trim().isEmpty()) {
			// NoBooksFound.setText("Empty Field");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Empty Fields");

			alert.showAndWait();
		} else {
			// NoBooksFound.setText("");
			switch ((String) SearchType.getValue()) {

			case "By Name": { // By Book Name

				message.add("FindBookByName");
				message.add(SearchText.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Librarian issu book ERROR");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					tabel.setVisible(false);

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					Category.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// (message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					tabel.setItems(toShow);
				}

				break;

			}

			case "By Author Name": { // By author Name

				message.add("FindBookByAuthor");
				message.add(SearchText.getText());

				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					tabel.setVisible(false);

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					Category.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					tabel.setItems(toShow);
				}

				break;
			}
			case "By Description": { // By author Name

				message.add("FindBookByDescription");
				message.add(SearchText.getText());

				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At AddNewSubscriberController in Function addNew");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					tabel.setVisible(false);
					

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					Category.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
				
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					tabel.setItems(toShow);
				}

				break;
			}
			default:
				System.out.println("Error Most7eeeeeeeel");
				break;
			}

		}

	}
/**
 * this method is to issue the the selected book
 * @param event
 * @throws ClassNotFoundException
 */
	@FXML
	void Issu(ActionEvent event) throws ClassNotFoundException {
		StudentID.getText();
		message = new ArrayList<String>();
		if (StudentID.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Empty Fields");

			alert.showAndWait();
		}

		else {
			message.add("IssueThisBook");
			message.add(BookIDSaved);
			message.add(StudentID.getText());
			message.add(BookRequested);
			SavedCopies++;
			message.add(String.valueOf(SavedCopies));
			Object obj = (Object) message;
			Object obj1 = new Object();
			clientCon.execute(obj);
			ArrayList<String> message = (ArrayList<String>) obj;
			// (message + "final");
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Librarian issu book EROR");
			}
			obj1 = clientCon.Getrespond();//not done
			int i = (int) obj1;
			Alert alert = new Alert(AlertType.INFORMATION);
			switch (i) {
            case 0: 
            	alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("There was and ERROR");
				alert.setContentText("There was an Error Issue the book please contact the software developer");
				StudentID.setText("");
				alert.showAndWait();
                     break;
            case 1:  
            		alert = new Alert(AlertType.INFORMATION);
            		alert.setTitle("The Book Have Been Issued Successfully");
            		alert.setHeaderText(null);
            		alert.setContentText("The Book Have Been Issued Successfully");
            		StudentID.setText("");
            		alert.showAndWait();
                     break;
            case -1:  
            	alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Suspended");
        		alert.setHeaderText(null);
        		alert.setContentText("This User can't Issue book because the account is Suspended");
        		alert.showAndWait();
        		StudentID.setText("");
                     break;
            default: 
            	System.out.println("How Did You Get HERE?");;
                     break;
        }
			FindBook(event);

		}

	}
/**
 *  this method is to initialize the choice box
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		SearchType.getItems().add("By Name");
		SearchType.getItems().add("By Author Name");
		SearchType.getItems().add("By Description");
		SearchType.getSelectionModel().select(0);
		Issu.setVisible(false);
		StudentID.setVisible(false);

	}
/**
 * this method is for the logout button to logout the user
 * @param event
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@FXML
	void logoutFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		User user1 = new User();
		user1.setUserID(userid);
		Logoutx(user1);
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
				Logoutx(user1);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
	}
/**
 * this method is to load the data from the previous stage
 * @param resultLoadData
 */
	public void loadData(ArrayList<String> resultLoadData) {
		userid = resultLoadData.get(0);
		userType = resultLoadData.get(1);
	}

	public void Logoutx(User User) throws ClassNotFoundException {
		/*
		 * ArrayList message we save the all user data to send to the server clientCon
		 * in to send to server the data and to get the respond from the the server
		 */
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		// save all the new data about the Subscriber and
		// in the index 0 in message we send the name about the func we want to do

		message.add("userlogout");
		message.add(userid);
		message.add(userid);

		/*
		 * we send from the client to server only object we change the type of the
		 * saveData to object and send it to server
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
		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();

	}

}
