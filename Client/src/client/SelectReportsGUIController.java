package client;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

import entites.User;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is Controller to Show the all reports in this class the management
 * choice any report he want to see and after this we send to DB to get the all
 * details about the specific report he choice after this we save the data and
 * create a PDF file to show the report's *
 * 
 * @author Marshood Ayoub , 311286694
 * @version Jan 2019
 */

public class SelectReportsGUIController {
	// this arrayList message to send the name of the report's to DB
	private ArrayList<String> message;
	// this to save the current user id
	User user = new User();
	// this RadioButton ReturnLateReportsRadio represent to the choice of the
	// report's
	@FXML
	private RadioButton ReturnLateReportsRadio;
	// this ToggleGroup to choice only one report
	@FXML
	private ToggleGroup GroupA;
	// this RadioButton LendReportsRadio represent to the choice of the report's
	@FXML
	private RadioButton LendReportsRadio;
	// this RadioButton ActivitiesReportsRadio represent to the choice of the
	// report's
	@FXML
	private RadioButton ActivitiesReportsRadio;
	// this Text UserIDText represent the management User ID
	@FXML
	private Text UserIDText;
	// this button Backbutt to go back to the last GUI ( Cancel )
	@FXML
	private Button Backbutt;
	// this button ConfirmButt to confirm the selected report and to send in to DB
	// to get the all details and information
	@FXML
	private Button ConfirmButt;
	// this button LogOutButt LOgout the user and go to the start GUI ( OBLLlogin)
	@FXML
	private Button LogOutButt;

	@FXML
	private DatePicker FirstDay;

	@FXML
	private DatePicker LastDay;

	/*
	 * initialize the current screen
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void initialize() throws ClassNotFoundException {
		// to enable the button after seletec any subscriber

		// when we select student and after this we click in freeze this text field and
		// date picker and button well able to show and edit
		FirstDay.setVisible(false);
		LastDay.setVisible(false);
		ConfirmButt.setVisible(true);
		// this Listener for know the selected toggle and show the firstday and last day
		// to select to activities reports
		GroupA.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				ReturnLateReportsRadio = (RadioButton) GroupA.getSelectedToggle();
				ReturnLateReportsRadio.getText();
				if (ReturnLateReportsRadio.getText().equals("Activities Reports")) {
					FirstDay.setVisible(true);
					LastDay.setVisible(true);
				}

				if (!ReturnLateReportsRadio.getText().equals("Activities Reports")) {
					FirstDay.setVisible(false);
					LastDay.setVisible(false);
				}
			}
		});
	}

	/**
	 * This method BackbuttFunc to back to the last GUI page and send the
	 * information about the current user to the GUI
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @throws IOException
	 */
	@FXML
	void BackbuttFunc(ActionEvent event) throws IOException {
		ArrayList<String> arr = new ArrayList<String>();
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		MainMangerGUIController display = fxmlLoader.getController();
		// we save the paramter we want to send to the anther GUI
		arr.add(UserIDText.getText());
		// this.user.setUserID("311286694");
		display.loadData(arr);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("OBL");
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> {
			try {
				User user1 = new User();
				user1.setUserID(user.getUserID());
				Logoutx(user1);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.show();
	}

	/*
	 * this method ConfirmButtFunc to confirm the seleted reports to show it
	 * 
	 */
	@FXML
	void ConfirmButtFunc(ActionEvent event) throws AWTException, IOException {
		// this to know what is the seleted radio roggle
		ReturnLateReportsRadio = (RadioButton) GroupA.getSelectedToggle();
		ReturnLateReportsRadio.getText();
		// if the select report is Activities Reports
		if (ReturnLateReportsRadio.getText().equals("Activities Reports")) { // if the DatePicker FirstDay is empty we
																				// show alert
			if (FirstDay.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR, "Empty Fisrt Date to select..", ButtonType.CLOSE);
				alert.setTitle("EmptyDate");
				alert.setHeaderText("Re-try Again");
				alert.show();
			}
			// if the DatePicker LastDay is empty we show alert
			if (LastDay.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR, "Empty Last Date to select..", ButtonType.CLOSE);
				alert.setTitle("EmptyDate");
				alert.setHeaderText("Re-try Again");
				alert.show();
			}
			// if they fill the DatePicker
			else {
				LocalDate Date1 = FirstDay.getValue();
				LocalDate Date2 = LastDay.getValue();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				String FirstD = dtf.format(Date1);
				String LastD = dtf.format(Date2);
				try {
					((Node) event.getSource()).getScene().getWindow().hide();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ActivitiesReportsGUI.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					ActivitieReportsGUIController ActivitieReportsGUIController = fxmlLoader.getController();
					Stage stage = new Stage();
					ActivitieReportsGUIController.loadDate(FirstD, LastD, UserIDText.getText());
					ActivitieReportsGUIController.start(stage);
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/Pictures/fivecon.png"));
					stage.setTitle("Manager Report's");
					stage.setResizable(false);

					stage.show();
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
				}
			}
		}
		if (ReturnLateReportsRadio.getText().equals("Lend Reports")) {
			try {
				((Node) event.getSource()).getScene().getWindow().hide();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/LendReport.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				LendReportsGUIController LendReportsGUIController = fxmlLoader.getController();
				LendReportsGUIController.loadDate(UserIDText.getText());
				Stage stage = new Stage();
				// LendReportsGUIController.start(stage);
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/Pictures/fivecon.png"));
				stage.setTitle("Manager Report's");
				stage.setResizable(false);

				stage.show();
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
			}
		}

		if (ReturnLateReportsRadio.getText().equals("Return Late Reports")) {
			try {
				((Node) event.getSource()).getScene().getWindow().hide();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ReturnLateReport.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				ReturnLateReportController ReturnLateReportController = fxmlLoader.getController();
				Stage stage = new Stage();
				ReturnLateReportController.loadDate(UserIDText.getText());
				// LendReportsGUIController.start(stage);
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/Pictures/fivecon.png"));
				stage.setTitle("Manager Report's");
				stage.setResizable(false);

				stage.show();
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Error with opening Librarian Add New Subscriber  aaaa ");
			}
		}

	}

	@FXML
	void ActivitiesReportsRadioFunc(ActionEvent event) {

	}

	@FXML
	void LendReportsRadioFunc(ActionEvent event) {

	}

	@FXML
	void ReturnLateReportsRadioFunc(ActionEvent event) {

	}

	/**
	 * This method to ExitFunc the current user and send the information about the
	 * current user to the server using Logoutx method to change the status of is
	 * LOg in
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void LogOutButtFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		User user1 = new User();
		user1.setUserID(user.getUserID());
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
	 * This method to send to the server user id to change the status is log in and
	 * send the information about the current user to the GUI
	 * 
	 * @author Marshood Ayoub , 311286694
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
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
		message.add(UserIDText.getText());
		message.add(UserIDText.getText());

		System.out.println("logout..");
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

		System.out.println("exited");
	}

	/*
	 * This method to get User information
	 * 
	 * @author Marshood Ayoub , 311286694
	 * 
	 * @param resultLoadData The ArrayList<string> to get the information .
	 *
	 */
	public void loadData(ArrayList<String> resultLoadData) {
		// get all the information from resultLoadData

		UserIDText.setText(resultLoadData.get(0));
	}
}

