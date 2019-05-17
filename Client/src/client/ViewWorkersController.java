package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import entites.Book;
import entites.LibrarianNew;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
/**
 * This class ViewWorkersController is to Controller to sho workers we have in db 
 * in this class we search workers by :By First Name,By Last  Name, By ID
 * after we choies the way we want to search and get the text fild from the user and send it to the 
 * server to check if the user are exist or not 
 * if exit we show it in table view 
 * 
 */
public class ViewWorkersController implements Initializable {
	private ArrayList<String> message;
	@FXML
	private Text NoWorkersFound;
	@FXML
	private TableView<LibrarianNew> table;
	@FXML
	private TableColumn<LibrarianNew, String> LibrarianID;
	@FXML
	private TableColumn<LibrarianNew, String> FirstName;
	@FXML
	private TableColumn<LibrarianNew, String> LastName;
	@FXML
	private TableColumn<LibrarianNew, String> Email;
	@FXML
	private TableColumn<LibrarianNew, String> PhoneNumber;
	@FXML
	private TableColumn<LibrarianNew, String> Organization;
	@FXML
	private TextField SearchBox;
	@FXML
	private ChoiceBox<String> SearchOptions;
	@FXML
	private Button SearchBTN;
	ClientConsole clientCon = new ClientConsole();
	// @FXML
	// void Exit(ActionEvent event) {
	// ((Node) event.getSource()).getScene().getWindow().hide();
	// }
/*
 * this method Exit  to exit frome the current page and return to the last gui (page)
 * 
 */
	@FXML
	void Exit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

 	/*
 	 * this method to find the worker by the selected search we select 
 	 *  we send to the server the selected way we choies with the text we get from the user  
	 */
	@FXML
	void FindWorkers(ActionEvent event) throws ClassNotFoundException {
		message = new ArrayList<String>();
		if (SearchBox.getText().trim().isEmpty()) {
			NoWorkersFound.setText("Empty Field");
		} else {
			NoWorkersFound.setText("");
			switch ((String) SearchOptions.getValue()) {

			case "By First Name": { // Search for workers By first name
				message.add("FindWorkersByFirstName");
				message.add(SearchBox.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				System.out.println(message + "final");
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At AddNewSubscriberController in Function addNew");
				}
				obj1 = clientCon.Getrespond();
				if (obj1 == null) {
					NoWorkersFound.setText("No Workers Found");
				} else {
					ArrayList<LibrarianNew> message1 = (ArrayList<LibrarianNew>) obj1;

					LibrarianID.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("UserID"));
					FirstName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("FirstName"));
					LastName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("LastName"));
					Email.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Email"));
					PhoneNumber.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("PhoneNumber"));
					Organization.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Organization"));
					ObservableList<LibrarianNew> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					table.setItems(toShow);
				}
				break;

			}
			// new case
			case "By Last Name": { // Search for workers By first name

				message.add("FindWorkersByLastName");
				message.add(SearchBox.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				System.out.println(message + "final");
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At ViewWorkersController in Function FindWorkers");
				}
				obj1 = clientCon.Getrespond();
				if (obj1 == null) {
					NoWorkersFound.setText("No Workers Found");
				} else {
					ArrayList<LibrarianNew> message1 = (ArrayList<LibrarianNew>) obj1;

					LibrarianID.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("UserID"));
					FirstName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("FirstName"));
					LastName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("LastName"));
					Email.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Email"));
					PhoneNumber.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("PhoneNumber"));
					Organization.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Organization"));
					ObservableList<LibrarianNew> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					table.setItems(toShow);
				}
				break;

			}
			case "By ID": { // Search for workers By first name

				message.add("FindWorkersByID");
				message.add(SearchBox.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				System.out.println(message + "final");
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At ViewWorkersController in Function FindWorkers");
				}
				obj1 = clientCon.Getrespond();
				if (obj1 == null) {
					NoWorkersFound.setText("No Workers Found");
				} else {
					ArrayList<LibrarianNew> message1 = (ArrayList<LibrarianNew>) obj1;

					LibrarianID.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("UserID"));
					FirstName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("FirstName"));
					LastName.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("LastName"));
					Email.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Email"));
					PhoneNumber.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("PhoneNumber"));
					Organization.setCellValueFactory(new PropertyValueFactory<LibrarianNew, String>("Organization"));
					ObservableList<LibrarianNew> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					table.setItems(toShow);
				}
				break;

			}
			}
		}
	}
/*
 * 
 * initialize the current screen  
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		SearchOptions.getItems().add("By First Name");
		SearchOptions.getItems().add("By Last Name");
		SearchOptions.getItems().add("By ID");
		SearchOptions.getSelectionModel().select(0);
		// TODO Auto-generated method stub

	}

}