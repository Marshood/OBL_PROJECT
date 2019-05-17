package client;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import entites.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class is Controller to show the activity reports
 *
 * @author Marshood Ayoub , 311286694 
 * @version Jan 2019
 */
public class ActivitieReportsGUIController implements Initializable {
	// this arrayList message to send the information about new subscriber
	private ArrayList<String> message;

    @FXML
    private Pane thispane;
	/*
	 * this for first table User's  TableUser
	 * */
	
	@FXML
	private TableView<table1Show> TableUser;
	// this table column AllUsers represent All students we have a reader card 

	@FXML
	private TableColumn<table1Show, String> AllUsers;
	// this table column ActivityUsers represent All students we have a Active  Status 

	@FXML
	private TableColumn<table1Show, String> ActivityUsers;
	// this table column FreezedUser represent All students we have a Freeze  Status 

	@FXML
	private TableColumn<table1Show, String> FreezedUser;
	// this table column LockedUser represent All students we have a Lock  Status 

	@FXML
	private TableColumn<table1Show, String> LockedUser;
	// this table column Average represent to the acerage percent of user's are freezed and locked 
	@FXML
	private TableColumn<table1Show, String> Average;
	// this FisrtDate and  LastDate to set the selected date to show the reports 
	String FisrtDate,LastDate;
     
	//table1Show showTable1 = new table1Show();
 	// Create 2 sub column for FullName.
	TableColumn<table1Show, String> firstNameCol //
			= new TableColumn<table1Show, String>("Freezed User");
	TableColumn<table1Show, String> lastNameCol //
			= new TableColumn<table1Show, String>("Locked User");
	/*
	 * second table
	 * 
	 * */
    @FXML
    private TableView<table2Show> LateTableUsers;
	// this table column UserID represent to the user Id we have return late a book 
    @FXML
    private TableColumn<table2Show, String> UserID;
	// this table column FirstName represent to the user fisrt name we have return late a book 
    @FXML
    private TableColumn<table2Show, String> FirstName;
	// this table column AllBooks represent to counter the all the books 
    @FXML
    private TableColumn<table2Show, String>AllBooks;
	// this table column PerLate represent to the user it's average of the book he loand and retrun lated 
    @FXML
    private TableColumn<table2Show, String> PerLate;
    // this text represent to the first date 
    @FXML
    private Text dateText1;
    // this text represent to the LastDate
    @FXML
    private Text dateText2;
    // this text represent to the the user id 
    @FXML
    private Text userID;
    
    
    /*
	 * This method to show in table 1 and table 2 and table 3   the all information we get from DB
	 * @author Marshood Ayoub , 311286694
	 * @param message1 The ArrayList<User> this is the all user we found
	 * 
	 **/

	public void start(Stage stage) throws ClassNotFoundException {
		 
       /*
        * this to insert into the first table 
        */
    	table1Show showTable1 = new table1Show(FisrtDate,LastDate);
    	// Add sub columns to the FullName
    	Average.getColumns().addAll(firstNameCol, lastNameCol);
    	
		AllUsers.setCellValueFactory(new PropertyValueFactory<table1Show, String>("AllUser1"));
		ActivityUsers.setCellValueFactory(new PropertyValueFactory<table1Show, String>("ActivityUsers1"));
		FreezedUser.setCellValueFactory(new PropertyValueFactory<table1Show, String>("FreezedUser1"));
		LockedUser.setCellValueFactory(new PropertyValueFactory<table1Show, String>("LockedUser1"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<table1Show, String>("AverageLocked"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<table1Show, String>("AverageFreezed"));
		ObservableList<table1Show> toShow = FXCollections.observableArrayList();
		toShow.addAll(showTable1);
		TableUser.setItems(toShow);
		
		/*
	    * this to insert into the second table 
	    */
		System.out.println("gui1");
		table2Show showTable2 = new table2Show(FisrtDate,LastDate);
		System.out.println("gui2");
		
		AllBooks.setCellValueFactory(new PropertyValueFactory<table2Show, String>("BookLate"));
		UserID.setCellValueFactory(new PropertyValueFactory<table2Show, String>("UserID"));
		FirstName.setCellValueFactory(new PropertyValueFactory<table2Show, String>("BookLend"));
		PerLate.setCellValueFactory(new PropertyValueFactory<table2Show, String>("PerLate"));
		ObservableList<table2Show> toShow2= FXCollections.observableArrayList();
		toShow2.addAll(showTable2);
		LateTableUsers.setItems(toShow2);
		
	}
	  /*
     * initialize the current screen  
     * */
    @FXML 
    void initialize() throws ClassNotFoundException {
    	
    	//DatePicker.setVisible(false);
    }
    
	/*
	 * 
	 * */
	private boolean SuspendedUserInDB() throws ClassNotFoundException {
		/*
		 * ArrayList message we save the new subscribber to send it to DB clientCon in
		 * to send to server the data and to get the respond from the the server
		 */
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();//
		// save all the new Subscriber
		// in the index 0 in message we send the first day and in index 1 the last day 
        message.add("GetActiviteReports");
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
			Thread.currentThread().sleep(1500);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();
		ArrayList<String> message1 = (ArrayList<String>) obj1;
		// we check the message if the add succsefull or not
		// and after this we save the result at the showTable1 to show it 
		if (message1.get(0).equals("Succ")) {
			//showTable1.setLockedUser1(message.get(1));
			//showTable1.setFreezedUser1(message.get(2));
			//showTable1.setActivityUsers1(message.get(3));
			//showTable1.setAverageFreezed(message.get(4));
			//showTable1.setAverageLocked(message.get(5));
			return true;
		}
		return true;
	} 
	
	/*
	 * this method to get the seleted date to show the report's 
	 * */
	public void loadDate(String firstD, String lastD,String userid) {
		// TODO Auto-generated method stub
		FisrtDate=firstD;
		LastDate=lastD;
		dateText1.setText(FisrtDate);
		dateText2.setText(LastDate);
		userID.setText(userid);

	}
	/*
	 * this method backFunc to back to the last GUI 
	 */
    @FXML
    void backFunc(ActionEvent event) throws IOException {
    	((Node) event.getSource()).getScene().getWindow().hide();
         ArrayList<String> arr = new ArrayList<String>();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/SelectReportsGUI.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		SelectReportsGUIController SelectReportsGUIController=fxmlLoader.getController();
		Stage stage = new Stage();
		arr.add(userID.getText());
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
    }
	
    /*
     * this Logout to log out the current user , we send the id of the user and change the status active to off 
     */
    public boolean Logout() throws ClassNotFoundException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("userlogout");
		message.add(userID.getText());
		message.add(userID.getText());
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			System.out.println("Exception At MainLibrarianGUI in Function Logout");
		}
		obj1 = clientCon.Getrespond(); //
		ArrayList<String> message1 = (ArrayList<String>) obj1; //
		return true;
	}
    /*
     * this method SaveReports is to save the current page 
     * we selected the path where to save the picture and afer this we save the picture 
     */
	
    @FXML
    void SaveReportss(ActionEvent event) throws AWTException, IOException {
    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh mm ss a  ");
    	    Stage stageScreen = null;
    	    stageScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	    double X=stageScreen.getX();
    	    double Y=stageScreen.getY();
    	    System.out.println("X   "+ X+ " y  " + Y);
          Calendar now = Calendar.getInstance();
          Robot robot;
    		robot = new Robot();
      	   Bounds bounds = thispane.getBoundsInLocal();
             Bounds screenBounds = thispane.localToScreen(bounds);
             Integer x = Integer.valueOf((int) screenBounds.getMinX());
             Integer y = Integer.valueOf((int) screenBounds.getMinY());
             WritableImage image = thispane.snapshot(new SnapshotParameters(), null);
              System.out.println("X   "+ x+ " y  " + y);
             int width = (int) screenBounds.getWidth();
             int height = (int) screenBounds.getHeight();
  	           new File("c:\\OBL\\Reports").mkdirs();
             Rectangle screenRect = new Rectangle(x, y, width, height);
             BufferedImage capture = new Robot().createScreenCapture(screenRect);
             ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("c:\\OBL\\Reports\\"+formatter.format(now.getTime())+ "Activity Reports"+".png"));
             Alert alert = new Alert(AlertType.WARNING, "File Saved as c:\\\\OBL\\\\Reports", ButtonType.CLOSE);
  			alert.show();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}