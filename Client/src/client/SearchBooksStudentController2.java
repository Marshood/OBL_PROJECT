package client;

/**
 * this class is for the student search option 
 */
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

import entites.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SearchBooksStudentController2 implements Initializable {
	private String userid2;

	@FXML
	private TableView<Book> Tabel;

	@FXML
	private TableColumn<Book, String> BookName;

	@FXML
	private TableColumn<Book, String> BookAuthor;

	@FXML
	private TableColumn<Book, String> BookCategory;

	@FXML
	private TableColumn<Book, String> BookDescription;

	@FXML
	private TableColumn<Book, String> ShelfDate;

	@FXML
	private TextField SearchField;

	@FXML
	private ChoiceBox<String> SearchType;

	@FXML
	private ChoiceBox<String> Catagory;

	@FXML
	private Text EmptyFields;

	@FXML
	private Text NoBooksFound;

	@FXML
	private Text Availabledate;

	@FXML
	private TextField date111;

	@FXML
	private Button Requestbut;

	@FXML
	private Text RequestedSucc;

	@FXML
	private Button ShowPDFbut;

	@FXML
	private Text requestreachlimit;

	private String StringPDF = null;

	@FXML
	void exit(ActionEvent event) {
		System.out.println("Student Account Working Controller");
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	private ArrayList<String> message;

	private int Available;

	ClientConsole clientCon = new ClientConsole();

	public void filluser(String userID) {
		this.userid2 = userID;
	}

	/**
	 * this method is for the student to request a book if there is no copy
	 * available at the library
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	void RequestBook(ActionEvent event) throws ClassNotFoundException {
		message = new ArrayList<String>();
		Book book = Tabel.getSelectionModel().getSelectedItem();
		message.add("RequestBook");

		message.add(book.getBookID());
		message.add(userid2);

		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		ArrayList<String> message = (ArrayList<String>) obj;
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At SearchBooksStudentController2 in Function RequestBook");
		}
		obj1 = clientCon.Getrespond();
		RequestedSucc.setText("Requested Successfully");
		Serach(event);
	}

	@FXML
	void showpdffun(ActionEvent event) throws IOException {

		byte[] by_new = Base64.getDecoder().decode(StringPDF);
		File someFile2 = new File("fromserverinclient.pdf"); // change the Byte[] to file
		FileOutputStream fos1 = new FileOutputStream(someFile2);
		fos1.write(by_new);
		fos1.flush();
		fos1.close();
		Desktop.getDesktop().open(someFile2);

	}

	@FXML
	void Selected(MouseEvent event) throws ClassNotFoundException {
		if (Tabel.getSelectionModel().getSelectedItem() != null) {
			ShowPDFbut.setVisible(false);
			Availabledate.setText("");
			RequestedSucc.setText("");
			requestreachlimit.setVisible(false);
			Book book = Tabel.getSelectionModel().getSelectedItem();
			StringPDF = book.getPDF();
			if (StringPDF != null)
				ShowPDFbut.setVisible(true);
			Available = (Integer.parseInt(book.getCopyNumber())) - (Integer.parseInt(book.getLoanedCopies()));
			if (Available == 0) {

				message = new ArrayList<String>();
				message.add("ClosestReturnDate");
				message.add(book.getBookID());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At SearchBooksStudentController2 in Function Serach By book name");
				}
				obj1 = clientCon.Getrespond();
				String convertedToString = String.valueOf(obj1);
				// System.out.println(obj1);+(String)obj1
				Availabledate.setText("The Closest Return Date : " + convertedToString);
				if (StringPDF != null)
					ShowPDFbut.setVisible(true);
				int asas = Integer.parseInt(book.getCopyNumber());
				if (asas - Integer.parseInt(book.getRequestedCounter()) > 0) {
					Requestbut.setVisible(true);
				}

				else {
					requestreachlimit.setVisible(true);
					Requestbut.setVisible(false);
				}

			} else {
				Requestbut.setVisible(false);
				Availabledate.setText("The Book Is Available At Shelf : " + book.getShelfDate());
			}
		}

	}

	@FXML
	void Serach(ActionEvent event) throws ClassNotFoundException {

		Requestbut.setVisible(false);
		NoBooksFound.setText("");
		message = new ArrayList<String>();
		if (SearchField.getText().trim().isEmpty()) {
			EmptyFields.setText("Empty Field");
		} else {
			switch ((String) SearchType.getValue()) {

			case "By Name": {

				message.add("FindBookByName");
				message.add(SearchField.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At SearchBooksStudentController2 in Function Serach By book name");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					Tabel.setVisible(false);
					NoBooksFound.setText("No Books Found");

				} else {
					Tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					// ShelfDate.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("ShelfDate"));
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					Tabel.setItems(toShow);

				}

				break;

			}
			case "By Author Name": {

				message.add("FindBookByAuthor");
				message.add(SearchField.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At SearchBooksStudentController2 in Function Serach By book name");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					Tabel.setVisible(false);
					NoBooksFound.setText("No Books Found");

				} else {
					Tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					// ShelfDate.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("ShelfDate"));
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					Tabel.setItems(toShow);
				}

				break;
			}
			case "By Category": {
				message.add("FindBookByCategory");
				message.add((String) Catagory.getValue());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At SearchBooksStudentController2 in Function Serach By book name");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					Tabel.setVisible(false);
					NoBooksFound.setText("No Books Found");
				} else {
					Tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					// ShelfDate.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("ShelfDate"));
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					Tabel.setItems(toShow);
				}

				break;
			}
			case "By Description": {

				message.add("FindBookByDescription");
				message.add(SearchField.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				try {
					Thread.currentThread().sleep(1200);
				} catch (Exception e) {
					System.out.println("Exception At SearchBooksStudentController2 in Function Serach By book name");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					Tabel.setVisible(false);
					NoBooksFound.setText("No Books Found");

				} else {
					Tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					// ShelfDate.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("ShelfDate"));
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					Tabel.setItems(toShow);
				}

				break;
			}
			default:
				System.out.println("Error Most7eeeeeeeel");
				break;
			}

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		SearchType.getItems().add("By Name");
		SearchType.getItems().add("By Author Name");
		SearchType.getItems().add("By Category");
		SearchType.getItems().add("By Description");
		SearchType.getSelectionModel().select(0);

		Catagory.getItems().add("Biographies");
		Catagory.getItems().add("Business");
		Catagory.getItems().add("Computers & Tech");
		Catagory.getItems().add("Cooking");
		Catagory.getItems().add("History");
		Catagory.getItems().add("Entertainment");
		Catagory.getItems().add("Sci-Fi & Fantasy");
		Catagory.getItems().add("Sport");

		EmptyFields.setText("");

		RequestedSucc.setText("");

		Tabel.setVisible(false);

		Requestbut.setVisible(false);

		ShowPDFbut.setVisible(false);
		requestreachlimit.setVisible(false);

	}

}
