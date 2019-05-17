package client;

import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import entites.User;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This Class Controller is to control GUI to show the student's according to
 * status (Active,Suspended,Locked) current user to the GUI
 * 
 * @author Marshood Ayoub , 311286694
 * @param
 * @throws IOException
 */

public class LateStudentTofreezeGUIController {

	// this arrayList message to send the information about new subscriber
	private ArrayList<String> message;
	// this to save the current user id
	User user = new User();
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
	private TableColumn<User, String> SubScriberType;
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
	// this table column lateCounter represent Student's counter return late we
	// found
	@FXML
	private TableColumn<User, String> lateCounter;
	// this table column EndFreezingDate represent Student's have a freeze status this have a date to end freezing

    @FXML
    private TableColumn<User, String> EndFreezingDate;
	// this button backbutt to back to the last gui
	@FXML
	private Button backbutt;
	// this button EditSubscriberbutt to edit the stuend we select in the table view
	// this table column Organization represent Student's Organizationwe found
	@FXML
	private TableColumn<User, String> Organization;
	// this button Refreshbutt to refresh the table
	@FXML
	// this text field is to show the user id in the screen
	String usertype;
	// this text field is to show the id for the current user
	@FXML
	private Text UserIdText;
	// this button freezeStudentbutt if the freeze the selected student's
	@FXML
	private Button freezeStudentbutt;
	// this button LockStudentButt if the lock the selected student's
	@FXML
	private Button LockStudentButt;
	// this button unfreezebutt if the unfreeze the selected student's
	@FXML
	private Button unfreezebutt;
	// this button Unlockbutt if the unlock the selected student's
	@FXML
	private Button Unlockbutt;
	// this arrayList resultLoadData to send the information about the corrent user
	// when we want to switch to another GUI
	ArrayList<String> resultLoadData1 = new ArrayList<String>();
	ArrayList<String> resultLoadData = new ArrayList<String>();
	// this arrayList UserSub to get the information about search by name
	ArrayList<User> UserSub = new ArrayList<User>();
	// this Date to get the date to un freeeze the selected student's
	@FXML
	private DatePicker DatePicker;
	// this text to show text select date
	@FXML
	private Text SelectDateText;
	// this button to confirm the date and freeze the selected student
	@FXML
	private Button confirmbutt;

	// this method to get the selected date to freeze
	@FXML
	void DatePickerFunc(ActionEvent event) {

	}

	/**
	 * this method to confirm the freeze date and freeze the selectde student
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @param
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void confirmbuttfunc(ActionEvent event) throws ClassNotFoundException {
		if (DatePicker.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Empty Date..", ButtonType.CLOSE);
			alert.setTitle("EmptyDate");
			alert.setHeaderText("Re-try Again");
			alert.show();
		} else {
			LocalDate Date = DatePicker.getValue();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String EndDate = dtf.format(Date);
			ClientConsole clientCon = new ClientConsole();
			message = new ArrayList<String>();
			message.add("FreezeStudentWithDate");
			message.add(resultLoadData1.get(0));
			message.add(EndDate);
			Object obj = (Object) message;
			Object obj1 = new Object();
			clientCon.execute(obj);
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Exception At AddNewSubscriberController in Function addNew");
			}
			obj1 = clientCon.Getrespond();

			if (obj1 == null) {
				Alert alert = new Alert(AlertType.INFORMATION, "The Status Not Changed..", ButtonType.CLOSE);
				alert.setTitle("Unfreeze Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();

			} else {

				DatePicker.setValue(null);
				Alert alert = new Alert(AlertType.WARNING,
						"The Student Status is id : " + resultLoadData1.get(0) + " Is Freezed to Date : " + EndDate,
						ButtonType.OK);
				alert.setTitle("WARNING");
				alert.setHeaderText("Re-try Again");
				alert.show();
				ActiveAccountFunc(event);

			}
		}

	}

	/**
	 * This method to back to the last GUI page and send the information about the
	 * current user to the GUI
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @param
	 * @throws IOException
	 */
	@FXML
	void BackFunc(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		MainMangerGUIController display = fxmlLoader.getController();
		// we save the paramter we want to send to the anther GUI
		resultLoadData.add(UserIdText.getText());
		resultLoadData.add(usertype);
		// loadData this func location at the SearchSubscriberGUIController
		display.loadData(resultLoadData);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("Librarian Stock Mangment");
		// stage.setTitle("Librarian Add New Subscriber");
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
	}

	/**
	 * this method to lock status selected student 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void LogoutFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		User user1 = new User();
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

	@FXML
	void LockStudentButtFunc(ActionEvent event) throws ClassNotFoundException {
		ArrayList<String> resultLoadData = new ArrayList<String>();
		if (resultLoadData1.get(3).equals("Locked")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Locked..", ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Re-try Again");
			alert.show();
		} else if (!resultLoadData1.get(3).equals("Locked")) {

			ClientConsole clientCon = new ClientConsole();
			message = new ArrayList<String>();
			message.add("changetoLock");
			message.add(resultLoadData1.get(0));
			Object obj = (Object) message;
			Object obj1 = new Object();
			clientCon.execute(obj);
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Exception At AddNewSubscriberController in Function addNew");
			}
			obj1 = clientCon.Getrespond();

			if (obj1 == null) {
				Alert alert = new Alert(AlertType.INFORMATION, "The Status Not Changed..", ButtonType.CLOSE);
				alert.setTitle("Unfreeze Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();

			} else {
				// tabel.setVisible(true);
				// ArrayList<User> message1 = (ArrayList<User>) obj1;
				// (message1 + "1231231230000000");
				// loadDataUser(message1);
				LockedAccountFunc(event);

			}
		}
	}
	/**
	 * this method UnlockbuttFunc to Unlock status selected student 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void UnlockbuttFunc(ActionEvent event) throws ClassNotFoundException {
		ArrayList<String> resultLoadData = new ArrayList<String>();
		if (resultLoadData1.get(3).equals("Active")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Not Locked..", ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Student Status is Active..");
			alert.show();
		}
		if (resultLoadData1.get(3).equals("Suspended")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Freezed Can''t change to Unlock ..",
					ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Re-try Again");
			alert.show();
		} else if (resultLoadData1.get(3).equals("Locked")) {
			ClientConsole clientCon = new ClientConsole();
			message = new ArrayList<String>();
			message.add("changetoActive");
			message.add(resultLoadData1.get(0));
			Object obj = (Object) message;
			Object obj1 = new Object();
			clientCon.execute(obj);
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Exception At AddNewSubscriberController in Function addNew");
			}
			obj1 = clientCon.Getrespond();

			if (obj1 == null) {
				Alert alert = new Alert(AlertType.INFORMATION, "The Status Not Changed..", ButtonType.CLOSE);
				alert.setTitle("Unfreeze Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();

			} else {
	
				LockedAccountFunc(event);

			}
		}
	}
	/**
	 * this method freezeStudentbuttfunc to freeze status selected student 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void freezeStudentbuttfunc(ActionEvent event) throws ClassNotFoundException {
		ArrayList<String> resultLoadData = new ArrayList<String>();
		if (resultLoadData1.get(3).equals("Suspended")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Freezed..", ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Re-try Again");
			alert.show();
		}
		if (resultLoadData1.get(3).equals("Locked")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Locked Can't be change to Freeze..",
					ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Re-try Again");
			alert.show();
		} else if (!resultLoadData1.get(3).equals("Suspended")) {

			confirmbutt.setVisible(true);
			SelectDateText.setVisible(true);
			DatePicker.setVisible(true);
		}
	}
	/**
	 * this method unfreezebuttFunc to Unfreeze status selected student 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void unfreezebuttFunc(ActionEvent event) throws ClassNotFoundException {
		ArrayList<String> resultLoadData = new ArrayList<String>();
		if (resultLoadData1.get(3).equals("Active")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Not Freezed..", ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("Student Status is Active..");
			alert.show();
		}
		if (resultLoadData1.get(3).equals("Locked")) {
			Alert alert = new Alert(AlertType.WARNING, "The Student Status is Locked Can't be change to UnFreeze.. ",
					ButtonType.CLOSE);
			alert.setTitle("WARNING");
			alert.setHeaderText("The Student Status is Not Freezed..");
			alert.show();
		} else if (resultLoadData1.get(3).equals("Suspended")) {
			ClientConsole clientCon = new ClientConsole();

			message = new ArrayList<String>();
			message.add("changetoActiveAfterFreeze");
			message.add(resultLoadData1.get(0));
			Object obj = (Object) message;
			Object obj1 = new Object();
			clientCon.execute(obj);
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Exception At AddNewSubscriberController in Function addNew");
			}
			obj1 = clientCon.Getrespond();

			if (obj1 == null) {
				Alert alert = new Alert(AlertType.INFORMATION, "The Status Not Changed..", ButtonType.CLOSE);
				alert.setTitle("Unfreeze Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();

			} else {
				// tabel.setVisible(true);
				// ArrayList<User> message1 = (ArrayList<User>) obj1;
				// (message1 + "1231231230000000");
				// loadDataUser(message1);
				SuspendedAccountFunc(event);

			}

		}
	}

	/*
	 * this method to get the details about the subscriber we select to send the
	 * information to edit gui
	 */
	@FXML
	void selectedSub(MouseEvent event) {

		confirmbutt.setVisible(false);
		SelectDateText.setVisible(false);
		DatePicker.setVisible(false);
		if (!table.getSelectionModel().isEmpty()) {
			ArrayList<String> resultLoadData = new ArrayList<String>();
			User student = table.getSelectionModel().getSelectedItem();
			resultLoadData.add(student.getUserID());// userID
			resultLoadData.add(student.getUserPass()); // pass
			resultLoadData.add("Student");// userType
			resultLoadData.add(student.getMemberShipStatus());// MemberShipStatus
			resultLoadData.add(student.getFisrtName());// first name
			resultLoadData.add(student.getLastName());// last name
			resultLoadData.add(student.getEmail());// email
			resultLoadData.add(student.getPhone());// phone
			resultLoadData.add(student.getGradationDate());// GradationDate
			resultLoadData.add(student.getOrganization());// Organization
			resultLoadData.add(student.getCounterLate());// late counter
			resultLoadData.add(student.getGetEndFreezingDate());// End Freezing Date
			resultLoadData1.clear();
			resultLoadData1.addAll(resultLoadData);

		}
	}

	/*
	 * This method to get the Subscriber information we found after search in DB
	 * 
	 * @author Marshood Ayoub , 311286694
	 * 
	 * @param message1 The ArrayList<User> this is the all user we found
	 * 
	 **/
	public int loadDataUser(ArrayList<User> message1) {
		// TODO Auto-generated method stub
		UserSub = new ArrayList<User>();
		int i = message1.size();
		int j;
		for (j = 0; j < i; j++) {
			User Students = new User();
			Students.setUserID(message1.get(j).getUserID());
			Students.setUserPass(message1.get(j).getUserPass());
			Students.setUserType(message1.get(j).getUserType());
			Students.setMemberShipStatus(message1.get(j).getMemberShipStatus());
			Students.setFisrtName(message1.get(j).getFisrtName());
			Students.setLastName(message1.get(j).getLastName());
			Students.setEmail(message1.get(j).getEmail());
			Students.setPhone(message1.get(j).getPhone());
			Students.setGradationDate(message1.get(j).getGradationDate());
			Students.setCounterLate(message1.get(j).getCounterLate());
			Students.setOrganization(message1.get(j).getOrganization());
			Students.setGetEndFreezingDate(message1.get(j).getGetEndFreezingDate());
			UserSub.add(Students);

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
		EndFreezingDate.setCellValueFactory(new PropertyValueFactory<User, String>("getEndFreezingDate"));
		ObservableList<User> toShow = FXCollections.observableArrayList();
		table.getItems().clear();
		// table.refresh();
		toShow.addAll(UserSub);
		// table.getItems().remove(0, table.getItems().size() - 1);
		table.setItems(toShow);
		return 1;
	}


	public void logoutfunc() throws ClassNotFoundException {
		/*
		 * ArrayList message we save the all user data to send to the server clientCon
		 * in to send to server the data and to get the respond from the the server
		 */
		ClientConsole clientCon = new ClientConsole();
		ArrayList<String> message123 = new ArrayList<String>();
		// save all the new data about the Subscriber and
		// in the index 0 in message we send the name about the func we want to do

		message123.add("userlogout");
		message123.add(UserIdText.getText());
		message123.add("test");

		/*
		 * we send from the client to server only object we change the type of the
		 * saveData to object and send it to server
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
		/*
		 * we get the responed from the server in type object obj1 and after this we
		 * change it according to protocol we use
		 * 
		 */
		obj1 = clientCon.Getrespond();

	}

	/**
	 * This method to send to the serever the current user to exit .. and change his
	 * status Login I
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
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
		message.add(UserIdText.getText());
		message.add(UserIdText.getText());
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
	/**
	 * This method showStud to show the selected student
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	void showStud() throws ClassNotFoundException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("statusFreezedShow");
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
			Alert alert = new Alert(AlertType.INFORMATION, "We have No Student's return late.. .", ButtonType.CLOSE);
			alert.setTitle("latte student's");
			alert.setHeaderText("Re-try Again Later");
			alert.show();

		} else {
			// tabel.setVisible(true);
			ArrayList<User> message1 = (ArrayList<User>) obj1;
			loadDataUser(message1);

		}

	}

	/*
	 * This method loadData to get the user id
	 * @author Marshood Ayoub , 311286694
	 **/
	public void loadData(ArrayList<String> resultLoadData) {
		// get all the information for the user from resultLoadData

		
		UserIdText.setText(resultLoadData.get(0));
		usertype = resultLoadData.get(1);
	}

	/*
	 * initialize the current screen
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void initialize() throws ClassNotFoundException {
		// to enable the button after seletec any subscriber

		freezeStudentbutt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
		LockStudentButt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
		unfreezebutt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
		Unlockbutt.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));

		// when we select student and after this we click in freeze this text field and
		// date picker and button well able to show and edit
		confirmbutt.setVisible(false);
		SelectDateText.setVisible(false);
		DatePicker.setVisible(false);
		// showStud();
		DatePicker.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);
			}
		});

	}

	/*
	 * This method SuspendedAccountFunc to get the Subscriber we have a freezed
	 * status
	 * 
	 * @author Marshood Ayoub , 311286694
	 * 
	 **/
	@FXML
	void SuspendedAccountFunc(ActionEvent event) throws ClassNotFoundException {
		confirmbutt.setVisible(false);
		SelectDateText.setVisible(false);
		DatePicker.setVisible(false);
		ClientConsole clientCon = new ClientConsole();

		message = new ArrayList<String>();
		message.add("CheckUsersFreezed");
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		obj1 = clientCon.Getrespond();

		if (obj1 == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "We have No Freezed Student's. .", ButtonType.CLOSE);
			alert.setTitle("latte student's");
			alert.setHeaderText("Re-try Again Later");
			alert.show();

		} else {
			// tabel.setVisible(true);
			ArrayList<User> message1 = (ArrayList<User>) obj1;
			if (message1.get(0) == null) {
				table.getItems().clear();
				Alert alert = new Alert(AlertType.INFORMATION, "We Dont have Suspended Student's. .", ButtonType.CLOSE);
				alert.setTitle("Freeze Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();
			} else {
				loadDataUser(message1);
			}
		}
	}

	/*
	 * This method LockedAccountFunc to get the Subscriber we have a locked status
	 * 
	 * @author Marshood Ayoub , 311286694
	 * 
	 **/
	@FXML
	void LockedAccountFunc(ActionEvent event) throws ClassNotFoundException {
		confirmbutt.setVisible(false);
		SelectDateText.setVisible(false);
		DatePicker.setVisible(false);
		ClientConsole clientCon = new ClientConsole();

		message = new ArrayList<String>();
		message.add("CheckLockedUser");
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		obj1 = clientCon.Getrespond();

		if (obj1 == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "We have No Freezed Student's. .", ButtonType.CLOSE);
			alert.setTitle("latte student's");
			alert.setHeaderText("Re-try Again Later");
			alert.show();

		} else {
			// tabel.setVisible(true);
			ArrayList<User> message1 = (ArrayList<User>) obj1;

			if (message1.get(0) == null) {
				table.getItems().clear();
				Alert alert = new Alert(AlertType.INFORMATION, "We Dont have Locked Student's. .", ButtonType.CLOSE);
				alert.setTitle("Lock Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();
			} else {
				loadDataUser(message1);
			}

		}
	}

	/*
	 * This method ActiveAccountFunc to get the Subscriber we have a Active status
	 * 
	 * @author Marshood Ayoub , 311286694
	 * 
	 **/

	@FXML
	void ActiveAccountFunc(ActionEvent event) throws ClassNotFoundException {
		confirmbutt.setVisible(false);
		SelectDateText.setVisible(false);
		DatePicker.setVisible(false);
		ClientConsole clientCon = new ClientConsole();

		message = new ArrayList<String>();
		message.add("CheckActiveusers");
		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At AddNewSubscriberController in Function addNew");
		}
		obj1 = clientCon.Getrespond();
		if (obj1 == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "We have No Freezed Student's. .", ButtonType.CLOSE);
			alert.setTitle("latte student's");
			alert.setHeaderText("Re-try Again Later");
			alert.show();

		} else {
			// tabel.setVisible(true);
			ArrayList<User> message1 = (ArrayList<User>) obj1;
			if (message1.get(0) == null) {
				table.getItems().clear();
				Alert alert = new Alert(AlertType.INFORMATION, "We Dont have Active Student's. .", ButtonType.CLOSE);
				alert.setTitle("Active Student's");
				alert.setHeaderText("Re-try Again Later");
				alert.show();
			} else {
				loadDataUser(message1);
			}
		}
	}

}
