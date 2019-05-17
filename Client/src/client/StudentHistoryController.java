package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import entites.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import entites.Book;
import entites.HistoryForStudent;
import javafx.scene.Node;
/**
 * This class is Controller to show student history 
 * in this class we get the all information about the students activities in the library 
 * we send to the server the student's id and get ArrayList as respond having all the user history
 * then we fill in the table from the respond we got
 *
 * @author Aroob daqduqi , 318489598 
 * @version Jan 2019
 */
public class StudentHistoryController implements Initializable {
	//create user type User to store user's id
	public User User;
	//to store user's id and send it to the server to get the history for this specific student
	String userid;
    // this arrayList message to send the information to the server in it 
	private ArrayList<String> message;
	//clientCon in to send to server the data and to get the respond from the the server 
	ClientConsole clientCon = new ClientConsole();
	//create table in scene builder to show the data in
	@FXML
    private TableView<HistoryForStudent> tabel;
	//table column to store historytype in
    @FXML
    private TableColumn<HistoryForStudent, String>  HistoryType;
	//table column to show if late 
    @FXML
    private TableColumn<HistoryForStudent, String>  late;
    //table column filled in when the history type is 'loaned'
    @FXML
    private TableColumn<HistoryForStudent, String> PickUpDate;
    //table column filled in when the history type is 'returned' or 'extended'
    @FXML
    private TableColumn<HistoryForStudent, String> returnDate;
    //table column filled in when the history type is 'requested'
    @FXML
    private TableColumn<HistoryForStudent, String> RequestDate;
    //table column filled in when the reader lost book
    @FXML
    private TableColumn<HistoryForStudent, String>  lostbook;
    //table column filled in with book name done the activity at
    @FXML
    private TableColumn<HistoryForStudent, String> BOOKNAME;
    @FXML
    private TextField idText;
    //text to be showed when the user doesn't have history
    @FXML
    private Text nohistory;
    
    /*
     * exit function to exit
     */
    @FXML
    void exit(ActionEvent event) {
    	System.out.println("Student Account Working Controller");
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
    /*
     * fill user function get the useris as parameter, and call the server to get the reader history
     */
    public void filluser(String User) throws ClassNotFoundException {
	
		this.userid=User;
		idText.setText(userid);
		message = new ArrayList<String>();
		message.add("ShowStudentHistory");
		message.add(userid);
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
			nohistory.setVisible(true);
			nohistory.setText("NoHistoryFound");
		}
		else {
			tabel.setVisible(true);
			ArrayList<HistoryForStudent> message1 = (ArrayList<HistoryForStudent>) obj1;
			HistoryType.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("HistoryType"));
			BOOKNAME.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookName")); 
			late.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("WasLate"));
			PickUpDate.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("PickUpDate"));
			returnDate.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("ReturnDate")); 
			RequestDate.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("RequestedDate")); 
			ObservableList<HistoryForStudent> toShow = FXCollections.observableArrayList();
			toShow.addAll(message1);
			tabel.setItems(toShow);

	}
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources){


		
	}

}