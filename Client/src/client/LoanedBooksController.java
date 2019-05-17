package client;

/**
 * this class is for the librarian to manually extend the loaned book for the student
 * 
 */
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entites.HistoryForStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoanedBooksController implements Initializable {

	private ArrayList<String> message;
	private ArrayList<String> message1;
	@FXML
	private Text StudentTXT;
	@FXML
	private Text EmptyFieldTXT;
	@FXML
	private Button extendplease;

	@FXML
	private DatePicker RequestExtensionDATE;
	@FXML
	private TableView<HistoryForStudent> table;

	@FXML
	private TableColumn<HistoryForStudent, String> BookNameTBFD;
	@FXML
	private TableColumn<HistoryForStudent, String> BookIDTBFD;
	@FXML
	private TableColumn<HistoryForStudent, String> BookReturnTBFD;
	ClientConsole clientCon = new ClientConsole();
	ClientConsole clientCon1 = new ClientConsole();
	String UserID;
	String LibrarianID;
	@FXML
	private Text NoHistory;

	@FXML
	void Exit(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
	}
/**
 * this method is to load from the last stage
 * 
 * @param resultLoadData
 */
	public void loadData(ArrayList<String> resultLoadData) {
		// get all the information from resultLoadData
		// and set to the text fields and combobox

		StudentTXT.setText(resultLoadData.get(0));
		UserID = resultLoadData.get(1);
		LibrarianID = resultLoadData.get(2);
	}

	/**
	 * this method RequestNewLoanDate is to check if the book can
	 * 
	 * @param event
	 * @throws Exception
	 */
	
	 @FXML
	    void selectetmode(MouseEvent event) {
		 
		 message = new ArrayList<String>();
		 HistoryForStudent Selected = table.getSelectionModel().getSelectedItem();
		 if(Selected.getRequestedbook().equals("No")){
			 extendplease.setVisible(true);
			 if (RequestExtensionDATE.getValue() == null || RequestExtensionDATE.getValue().isBefore(LocalDate.now())) {
					EmptyFieldTXT.setText("Choose a Valid Date please!");
				} else {
					/**
					 * get the selected book table details and the date selected from the date
					 * picker and sends the details to the server
					 * 
					 */
					LocalDate Date = RequestExtensionDATE.getValue();
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					String EndDate = dtf.format(Date);
					EmptyFieldTXT.setText("");
					message.add("RequestNewLoanDate");
					message.add(EndDate);
					message.add(Selected.getBookID());
					message.add(Selected.getReturnDate());
					message.add(UserID);
					message.add(Selected.getHistoryId());
					message.add(LibrarianID);
					Object obj = (Object) message;
					Object obj1 = new Object();
					try {
						clientCon1.execute(obj);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList<String> message = (ArrayList<String>) obj;
					try {
						Thread.currentThread().sleep(1200);
					} catch (Exception e) {
						System.out.println("Exception At LoanedBooksController in Function RequestNewLoanDate");
					}
					Object obj22 = clientCon1.Getrespond();
					ArrayList<String> message11 = (ArrayList<String>) obj22;
					if (message11.get(0).equals("Success")) {
						EmptyFieldTXT.setText("Extending lending period was Succssesful!");
					}
					if (message11.get(0).equals("Not14")) {
						EmptyFieldTXT.setText("Requested period is more than 14 days!");
					}
			
					try {
						/**
						 * 
						 * this part to refresh the table
						 */
						message1 = new ArrayList<String>();
						message1.add("ShowStudentHistoryForManager");
						message1.add(UserID);
						Object obj2 = (Object) message1;
						Object obj3 = new Object();
						try {
							clientCon.execute(obj2);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ArrayList<String> message1 = (ArrayList<String>) obj2;
						try {
							Thread.currentThread().sleep(1200);
						} catch (Exception e) {
							System.out.println("Exception At LoanedBooksController in Function start");
						}
						obj3 = clientCon.Getrespond();
						if (obj3 == null) {
							table.setVisible(false);
							NoHistory.setVisible(true);
							NoHistory.setText("No History Found");
						} else {
							table.setVisible(true);
							ArrayList<HistoryForStudent> message2 = (ArrayList<HistoryForStudent>) obj3;
							BookNameTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookName"));
							BookIDTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookID"));
							BookReturnTBFD
									.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("ReturnDate"));
							ObservableList<HistoryForStudent> toShow = FXCollections.observableArrayList();
							toShow.addAll(message2);
							table.setItems(toShow);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("could not refresh the table Exception");
					}
				}
			 
			 
		 }
		 else {
			 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Cannt Extend Book");
			 alert.setHeaderText(null);
			 alert.setContentText("Cannt Extend This book becouse this book is requested");

			 alert.showAndWait();
			 
		 }
	    }
	
	@FXML
	void RequestNewLoanDate(ActionEvent event) throws Exception {
		message = new ArrayList<String>();
		/**
		 * check if the fields are empty
		 */
		if (RequestExtensionDATE.getValue() == null || RequestExtensionDATE.getValue().isBefore(LocalDate.now())) {
			EmptyFieldTXT.setText("Choose a Valid Date please!");
		} else {
			/**
			 * get the selected book table details and the date selected from the date
			 * picker and sends the details to the server
			 * 
			 */
			HistoryForStudent Selected = table.getSelectionModel().getSelectedItem();
			LocalDate Date = RequestExtensionDATE.getValue();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String EndDate = dtf.format(Date);
			EmptyFieldTXT.setText("");
			message.add("RequestNewLoanDate");
			message.add(EndDate);
			message.add(Selected.getBookID());
			message.add(Selected.getReturnDate());
			message.add(UserID);
			message.add(Selected.getHistoryId());
			message.add(LibrarianID);
			Object obj = (Object) message;
			Object obj1 = new Object();
			try {
				clientCon1.execute(obj);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList<String> message = (ArrayList<String>) obj;
			try {
				Thread.currentThread().sleep(1200);
			} catch (Exception e) {
				System.out.println("Exception At LoanedBooksController in Function RequestNewLoanDate");
			}
			Object obj22 = clientCon1.Getrespond();
			ArrayList<String> message11 = (ArrayList<String>) obj22;
			if (message11.get(0).equals("Success")) {
				EmptyFieldTXT.setText("Extending lending period was Succssesful!");
			}
			if (message11.get(0).equals("Not14")) {
				EmptyFieldTXT.setText("Requested period is more than 7 days!");
			}
	
			try {
				/**
				 * 
				 * this part to refresh the table
				 */
				message1 = new ArrayList<String>();
				message1.add("ShowStudentHistoryForManager");
				message1.add(UserID);
				Object obj2 = (Object) message1;
				Object obj3 = new Object();
				try {
					clientCon.execute(obj2);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ArrayList<String> message1 = (ArrayList<String>) obj2;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At LoanedBooksController in Function start");
				}
				obj3 = clientCon.Getrespond();
				if (obj3 == null) {
					table.setVisible(false);
					NoHistory.setVisible(true);
					NoHistory.setText("No History Found");
				} else {
					table.setVisible(true);
					ArrayList<HistoryForStudent> message2 = (ArrayList<HistoryForStudent>) obj3;
					BookNameTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookName"));
					BookIDTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookID"));
					BookReturnTBFD
							.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("ReturnDate"));
					ObservableList<HistoryForStudent> toShow = FXCollections.observableArrayList();
					toShow.addAll(message2);
					table.setItems(toShow);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("could not refresh the table Exception");
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////

	}
/**
 * this method starts when this class is called
 * 
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RequestExtensionDATE.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) < 0 );
	        }
	    });
	}
/**
 * this method is to show the manager and the librarian the books that the student has not returned yet 
 * so that they can be asked for extension 
 * @param primaryStage
 * @throws IOException
 */
	public void start(Stage primaryStage) throws IOException {
		message = new ArrayList<String>();
		message.add("ShowStudentHistoryForManager");
		message.add(UserID);
		Object obj = (Object) message;
		Object obj1 = new Object();
		try {
			clientCon.execute(obj);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> message = (ArrayList<String>) obj;
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At LoanedBooksController in Function start");
		}
		obj1 = clientCon.Getrespond();
		if (obj1 == null) {
			table.setVisible(false);
			NoHistory.setVisible(true);
			NoHistory.setText("No History Found");
		} else {
			table.setVisible(true);
			ArrayList<HistoryForStudent> message1 = (ArrayList<HistoryForStudent>) obj1;
			BookNameTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookName"));
			BookIDTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("BookID"));
			BookReturnTBFD.setCellValueFactory(new PropertyValueFactory<HistoryForStudent, String>("ReturnDate"));
			ObservableList<HistoryForStudent> toShow = FXCollections.observableArrayList();
			toShow.addAll(message1);
			table.setItems(toShow);
		}

	}
}
