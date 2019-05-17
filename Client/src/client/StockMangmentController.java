package client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

import entites.Book;
import entites.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
/**
 * in this controller the librarian can manage the book: add new book, Edit Book, search book, delete book
 * @author BlackStar
 *
 */
public class StockMangmentController implements Initializable, ChatIF {
	// this to save the current user id
	User user= new User();	
	//this arraylist used to send to the server
	private ArrayList<String> message;
	//this textfield used to get the book author name to add the book
	@FXML
	private TextField AddBookAuthor;
	//this choicebox is used to list the catgory
	@FXML
	private ChoiceBox<String> catgory;
	//this string used to save the bookid
	private String BookIDSave;
	//used to select the search type
	@FXML
	private ChoiceBox<String> findbookcatgory;
	//select the catgory for the search
	@FXML
	private ChoiceBox<String> catgoryForSearch;
	//name of the tabel
	@FXML
	private TableView<Book> tabel;
	//name for the tabel column
	@FXML
	private TableColumn<Book, String> BookID;
	@FXML
	private TableColumn<Book, String> BookName;
	@FXML
	private TableColumn<Book, String> BookAuthor;
	@FXML
	private TableColumn<Book, String> BookCategory;
	@FXML
	private TableColumn<Book, String> EditionNumber;
	@FXML
	private TableColumn<Book, String> PublishDate;
	@FXML
	private TableColumn<Book, String> Category;
	@FXML
	private TableColumn<Book, String> catlognumber;
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
	//used to save the book id for delete
	private String DeleteBookID;
	//delete button 
	@FXML
	private Button DeleteBut;
	//select the search type
	@FXML
	private ChoiceBox<String> SelectSearchTypeTab3;
	//take the text for search
	@FXML
	private TextField SearchInputTab3;
	//used to show no book found text
	@FXML
	private Text NoBooksFoundDelete;
	//another tabel
	@FXML
	private TableView<Book> dtabel2;
	//name of the column
	@FXML
	private TableColumn<Book, String> dBookID2;
	@FXML
	private TableColumn<Book, String> dBookName2;
	@FXML
	private TableColumn<Book, String> dBookAuthor2;
	@FXML
	private TableColumn<Book, String> dBookCategory2;
	@FXML
	private TableColumn<Book, String> dEditionNumber2;
	@FXML
	private TableColumn<Book, String> dPublishDate2;
	@FXML
	private TableColumn<Book, String> dCategory2;
	@FXML
	private TableColumn<Book, String> dcatlognumber2;
	@FXML
	private TableColumn<Book, String> dBookDescription2;
	@FXML
	private TableColumn<Book, String> dCopyNumber2;
	@FXML
	private TableColumn<Book, String> dPurchaseDate2;
	@FXML
	private TableColumn<Book, String> dShelfDate2;
	@FXML
	private TableColumn<Book, String> dRequested2;
	//button to show
	@FXML
	private Button savebut;
	//the text for the search
	@FXML
	private TextField FindBookName;
	@FXML
	private TextField decrption2;
	@FXML
	private TextField publish2;
	@FXML
	private TextField purchasedate2;
	@FXML
	private TextField catalognumber2;
	@FXML
	private TextField copiesnumber2;
	@FXML
	private TextField shelfplace2;
	@FXML
	private TextField BookName2;
	@FXML
	private TextField EditionNumber2;
	@FXML
	private TextField BookAuthor2;
	@FXML
	private ChoiceBox<String> catgory2;
	@FXML
	private ChoiceBox<String> requested2;
	@FXML
	private Text NoBooksFound;
	@FXML
	private Text Updating;


	@FXML
	private TextField decrption;
	
	@FXML
	private TextField publish;
	
	@FXML
	private TextField purchasedate;
	
	@FXML
	private TextField catalognumber;
	
	@FXML
	private TextField copiesnumber;
	
	@FXML
	private TextField shelfplace;
	
	@FXML
	private TextField AddBookName;

	@FXML
	private TextField AddEditionNumber;
	
	@FXML
	private Text saved;
	
	@FXML
	private ChoiceBox<String> requested1;

	@FXML
	private Text Failed;
   
	@FXML
    private TextField filetxtname;
    
	ArrayList<Book> NewBooks = new ArrayList<Book>();
	
	private String msg;
	
	ClientConsole clientCon = new ClientConsole();
	
	String userId;
	
	String BookPDF;
	
	boolean flagforPDF;
	
	private String userType;

	/**
	 * the method to select the search type search book by name, by author,by description,by catgory
	 * @param event
	 */
	@FXML
	void selectsearchtype(MouseEvent event) {// hereeeeeeeeeeeeeeeeeeee fix the catlog issue
		if ((String) findbookcatgory.getValue() == "By Category") {
			FindBookName.setVisible(false);
			catgoryForSearch.setVisible(true);
		} else {
			FindBookName.setVisible(true);
			catgoryForSearch.setVisible(false);
		}
	}
	
	/**
	 * this method used to select the book from the tab searchbook and edit, after the select this method fill all the text field so the librarian can edit them and save it
	 * @param event
	 */
	@FXML
	void Edit2(MouseEvent event) {
		if (tabel.getSelectionModel().getSelectedItem() != null) {
			savebut.setVisible(true);
			
			Book book = tabel.getSelectionModel().getSelectedItem();
			BookName2.setText(book.getBookName());
			BookAuthor2.setText(book.getBookAuthor());
			EditionNumber2.setText(book.getEditionNumber());
			publish2.setText(book.getPublishDate());
			catalognumber2.setText(book.getBookID());
			decrption2.setText(book.getBookDescription());
			copiesnumber2.setText(book.getCopyNumber());
			purchasedate2.setText(book.getPurchaseDate());
			shelfplace2.setText(book.getShelfDate());
			BookIDSave = book.getBookID();
			switch (book.getBookCategory()) {
			case "Biographies": {
				catgory2.getSelectionModel().select(0);
				break;
			}
			case "Business": {
				catgory2.getSelectionModel().select(1);
				break;
			}
			case "Computers & Tech": {
				catgory2.getSelectionModel().select(2);
				break;
			}
			case "Cooking": {
				catgory2.getSelectionModel().select(3);
				break;
			}
			case "History": {
				catgory2.getSelectionModel().select(4);
				break;
			}
			case "Entertainment": {
				catgory2.getSelectionModel().select(5);
				break;
			}
			case "Sci-Fi & Fantasy": {
				catgory2.getSelectionModel().select(6);
				break;
			}
			case "Sport": {
				catgory2.getSelectionModel().select(7);
				break;
			}
			}
			switch (book.getRequested()) {
			case "Yes": {
				requested2.getSelectionModel().select(0);
				break;
			}
			case "No": {
				requested2.getSelectionModel().select(1);
				break;
			}
			}
		}

	}
	/**
	 * 
	 * this method take let the librarian to select the pdf file from the pc
	 * 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void SelectFile(ActionEvent event) throws IOException {
		BookPDF = new String();

		flagforPDF = true;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
		FileInputStream fis = new FileInputStream(file); // get the file
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at offset off to this
				// byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {
		}
		byte[] bytes = bos.toByteArray(); // save it to Byte
		BookPDF = Base64.getEncoder().encodeToString(bytes); // change Byte[] to string
		filetxtname.setText(file.getName());
	}
	/**
	 * this method let the librarian select the book that she want to delete
	 * 
	 * @param event
	 */
	@FXML
	void Edit3(MouseEvent event) {
		if (tabel.getSelectionModel().getSelectedItem() != null) {
			DeleteBut.setVisible(true);
			Book book = dtabel2.getSelectionModel().getSelectedItem();
			DeleteBookID = book.getBookID();
		}
	}
	/**
	 * 
	 * the method to select the search type search book by name, by author,by description,by catgory and fill the tabel with the content
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	void FindBookDelete(ActionEvent event) throws ClassNotFoundException {
		message = new ArrayList<String>();
		if (SearchInputTab3.getText().trim().isEmpty()) {
			NoBooksFoundDelete.setText("Empty Field");
		} else {
			NoBooksFoundDelete.setText("");
			switch ((String) SelectSearchTypeTab3.getValue()) {

			case "By Name": { // By Book Name
				message.add("FindBookByName");
				message.add(SearchInputTab3.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				System.out.println(message + "final");
				try {
					Thread.currentThread().sleep(1500);
				} catch (Exception e) {
					System.out.println("Exception At AddNewSubscriberController in Function addNew");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					System.out.println("+++++++++++++++++++++++++");
					dtabel2.setVisible(false);
					DeleteBut.setVisible(false);
					NoBooksFoundDelete.setText("NoBooksFound");

				} else {
					System.out.println("=========================");
					dtabel2.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					dBookID2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					dBookName2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					dBookAuthor2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					dBookCategory2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					dEditionNumber2.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					dPublishDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					dBookDescription2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					dCopyNumber2.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					dShelfDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					dPurchaseDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					dRequested2.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					dtabel2.setItems(toShow);
				}

				break;

			}

			case "By Author Name": { // By author Name

				message.add("FindBookByAuthor");
				message.add(SearchInputTab3.getText());
				Object obj = (Object) message;
				Object obj1 = new Object();
				clientCon.execute(obj);
				ArrayList<String> message = (ArrayList<String>) obj;
				System.out.println(message + "final");
				try {
					Thread.currentThread().sleep(1500);
				} catch (Exception e) {
					System.out.println("Exception At AddNewSubscriberController in Function addNew");
				}
				obj1 = clientCon.Getrespond();

				if (obj1 == null) {
					System.out.println("+++++++++++++++++++++++++");
					dtabel2.setVisible(false);
					DeleteBut.setVisible(false);
					NoBooksFoundDelete.setText("NoBooksFound");

				} else {
					System.out.println("=========================");
					dtabel2.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					dBookID2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					dBookName2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					dBookAuthor2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					dBookCategory2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					dEditionNumber2.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					dPublishDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					dBookDescription2.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					dCopyNumber2.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					dShelfDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					dPurchaseDate2.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					dRequested2.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					dtabel2.setItems(toShow);
				}

				break;

			}
			case "By Category": {
				message.add("FindBookByCategory"); // messing

				break;
			}
			case "By Description": {
				message.add("FindBookByDescription"); // messing

				break;
			}
			default:
				System.out.println("Error Most7eeeeeeeel");
				break;
			}

		}

	}
	
	/**
	 * the method to select the search type search book by name, by author,by description,by catgory and fill the tabel with the content
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	void FindBook(ActionEvent event) throws ClassNotFoundException {
		NoBooksFound.setText("");
		message = new ArrayList<String>();
		if (FindBookName.getText().trim().isEmpty()) {
			NoBooksFound.setText("Empty Field");
		} else {
			NoBooksFound.setText("");
			switch ((String) findbookcatgory.getValue()) {

			case "By Name": { // By Book Name

				message.add("FindBookByName");
				message.add(FindBookName.getText());
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
					tabel.setVisible(false);
					savebut.setVisible(false);
					NoBooksFound.setText("NoBooksFound");

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					tabel.setItems(toShow);
				}

				break;

			}

			case "By Author Name": { // By author Name

				message.add("FindBookByAuthor");
				message.add(FindBookName.getText());

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
					tabel.setVisible(false);
					savebut.setVisible(false);
					NoBooksFound.setText("NoBooksFound");

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					System.out.println(" => " + toShow);
					tabel.setItems(toShow);
				}

				break;
			}
			case "By Category": {
				// FindBookName.setVisible(value);
				message.add("FindBookByCategory"); // messing
				message.add((String) catgoryForSearch.getValue());
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
					tabel.setVisible(false);
					savebut.setVisible(false);
					NoBooksFound.setText("NoBooksFound");

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					System.out.println(" => " + toShow);
					tabel.setItems(toShow);
				}

				break;
			}
			case "By Description": { // By author Name

				message.add("FindBookByDescription");
				message.add(FindBookName.getText());

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
					tabel.setVisible(false);
					savebut.setVisible(false);
					NoBooksFound.setText("NoBooksFound");

				} else {
					tabel.setVisible(true);
					ArrayList<Book> message1 = (ArrayList<Book>) obj1;
					BookID.setCellValueFactory(new PropertyValueFactory<Book, String>("BookID"));
					BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					BookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("BookAuthor"));
					BookCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("BookCategory"));
					EditionNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("EditionNumber"));
					PublishDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PublishDate"));
					// catlognumber.setCellValueFactory(new PropertyValueFactory<Book,
					// String>("catlognumber"));
					BookDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("BookDescription"));
					CopyNumber.setCellValueFactory(new PropertyValueFactory<Book, String>("CopyNumber"));
					ShelfDate.setCellValueFactory(new PropertyValueFactory<Book, String>("ShelfDate"));
					PurchaseDate.setCellValueFactory(new PropertyValueFactory<Book, String>("PurchaseDate"));
					Requested.setCellValueFactory(new PropertyValueFactory<Book, String>("Requested"));
					// System.out.println(message1.get(1).getRequested());
					ObservableList<Book> toShow = FXCollections.observableArrayList();
					toShow.addAll(message1);
					System.out.println(" => " + toShow);
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
	 * Unused
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	void SaveEdit(ActionEvent event) throws ClassNotFoundException {

	}

////////////////////////////////////////////////////////////////////////////////////////////////	
	/**this method take all the book details and save them in the database
	 * 
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	@FXML
	void confirm(ActionEvent event) throws ClassNotFoundException, InterruptedException { 
		saved.setText("");														
		if (!AddBookName.getText().trim().isEmpty() && !AddBookAuthor.getText().trim().isEmpty()
				&& !AddEditionNumber.getText().trim().isEmpty() && !publish.getText().trim().isEmpty()
				&& !decrption.getText().trim().isEmpty()
				&& !copiesnumber.getText().trim().isEmpty() && !purchasedate.getText().trim().isEmpty()
				&& !shelfplace.getText().trim().isEmpty() && flagforPDF ) {
			System.out.println("tezt");
			if (addingBook()) {
				saved.setText("Saved");
				flagforPDF = false;
				AddBookName.setText("");
				AddBookAuthor.setText("");
				AddEditionNumber.setText("");
				publish.setText("");
				//catgory.getSelectionModel().select(0);
				decrption.setText("");
				copiesnumber.setText("");
				purchasedate.setText("");
				shelfplace.setText("");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert");
				alert.setHeaderText(null);
				alert.setContentText("The Book Have Been Saved");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText("There was and ERROR");
				alert.setContentText("There was an Error saving the book please contact the software developer");
				
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText(null);
			alert.setContentText("Empty Fields, Please Fill the Fields");
			alert.showAndWait();
		}
	}

	boolean addingBook() throws ClassNotFoundException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("AddBook");
		message.add(AddBookName.getText());
		message.add(AddBookAuthor.getText());
		message.add(AddEditionNumber.getText());
		message.add(publish.getText());
		message.add((String) catgory.getValue());
		message.add(decrption.getText());
		message.add(copiesnumber.getText());
		message.add(purchasedate.getText());
		message.add(shelfplace.getText());
		message.add((String) requested1.getValue());
		message.add(BookPDF);

		Object obj = (Object) message;
		Object obj1 = new Object();
		clientCon.execute(obj);
		ArrayList<String> message = (ArrayList<String>) obj;
		try {
			Thread.currentThread().sleep(1200);
		} catch (Exception e) {
			System.out.println("Exception At Add Book in Function addNew");
			return false;
		}
		obj1 = clientCon.Getrespond();
		return true;
	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		this.msg = message;

	}

	@Override
	public void respond(Object object) {
		// TODO Auto-generated method stub

	}

	
	/**
	 * 
	 * Here we initialize the node before start
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		findbookcatgory.getItems().add("By Name");
		findbookcatgory.getItems().add("By Author Name");
		findbookcatgory.getItems().add("By Category");
		findbookcatgory.getItems().add("By Description");
		findbookcatgory.getSelectionModel().select(0);
		// findbookcatgory.getItems().add("by teze");
		catgoryForSearch.setVisible(false);

		SelectSearchTypeTab3.getItems().add("By Name");
		SelectSearchTypeTab3.getItems().add("By Author Name");
		SelectSearchTypeTab3.getItems().add("By Category");
		SelectSearchTypeTab3.getItems().add("By Description");
		SelectSearchTypeTab3.getSelectionModel().select(0);

		tabel.setVisible(false);
		dtabel2.setVisible(false);

		savebut.setVisible(false);
		DeleteBut.setVisible(false);

		catgory.getItems().add("Biographies");
		catgory.getItems().add("Business");
		catgory.getItems().add("Computers & Tech");
		catgory.getItems().add("Cooking");
		catgory.getItems().add("History");
		catgory.getItems().add("Entertainment");
		catgory.getItems().add("Sci-Fi & Fantasy");
		catgory.getItems().add("Sport");

		catgory2.getItems().add("Biographies");
		catgory2.getItems().add("Business");
		catgory2.getItems().add("Computers & Tech");
		catgory2.getItems().add("Cooking");
		catgory2.getItems().add("History");
		catgory2.getItems().add("Entertainment");
		catgory2.getItems().add("Sci-Fi & Fantasy");
		catgory2.getItems().add("Sport");

		catgoryForSearch.getItems().add("Biographies");
		catgoryForSearch.getItems().add("Business");
		catgoryForSearch.getItems().add("Computers & Tech");
		catgoryForSearch.getItems().add("Cooking");
		catgoryForSearch.getItems().add("History");
		catgoryForSearch.getItems().add("Entertainment");
		catgoryForSearch.getItems().add("Sci-Fi & Fantasy");
		catgoryForSearch.getItems().add("Sport");

		requested1.getItems().add("Yes");
		requested1.getItems().add("No");
		requested2.getItems().add("Yes");
		requested2.getItems().add("No");

	}
	
	/**
	 * this method saves the changes for the book
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	
	@FXML
	void SaveEdit2(ActionEvent event) throws ClassNotFoundException {

		// Updating.setText("Wait Please");
		if (!BookName2.getText().trim().isEmpty() && !BookAuthor2.getText().trim().isEmpty()
				&& !EditionNumber2.getText().trim().isEmpty() && !publish2.getText().trim().isEmpty()
				&& !catalognumber2.getText().trim().isEmpty() && !decrption2.getText().trim().isEmpty()
				&& !copiesnumber2.getText().trim().isEmpty() && !purchasedate2.getText().trim().isEmpty()
				&& !shelfplace2.getText().trim().isEmpty()) {

			// send to server
			// private ArrayList<String> message;
			message = new ArrayList<String>();
			message.add("EditBook");
			message.add(BookName2.getText());
			message.add(BookAuthor2.getText());
			message.add(EditionNumber2.getText());
			message.add(publish2.getText());
			message.add((String) catgory2.getValue());
			message.add(decrption2.getText());
			message.add(copiesnumber2.getText());
			message.add(purchasedate2.getText());
			message.add(shelfplace2.getText());
			message.add((String) requested2.getValue());
			message.add(BookIDSave);

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
			FindBook(event);
		} else {
			System.out.println("Empty Fields");
		}
	}
	/**
	 * this method send the bookid that we want to delete
	 * 
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	void DeleteBook(ActionEvent event) throws ClassNotFoundException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("DeleteBook");
		message.add(DeleteBookID);

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
		FindBookDelete(event);

	}
	/**
	 * 
	 * this method is to logout
	 * 
	 * 
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	void LogoUtFunc(ActionEvent event) throws ClassNotFoundException, IOException {
		User user1 = new User();
		user1.setUserID(userId);
		Logoutx(user1);
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("OBL");
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setOnCloseRequest(e -> {
			try {
				Logoutx(user1);
			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
			}
		});
		stage.show();
	}

	public void Logoutx(User User) throws ClassNotFoundException {
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();

		message.add("userlogout");
		message.add(userId);
		message.add(userId);

	
		Object obj = (Object) message;
		Object obj1 = new Object();
		// send the message to the server

		clientCon.execute(obj);

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

	public void loadData(ArrayList<String> arr) {
		// TODO Auto-generated method stub
		userId = arr.get(0);
		userType=arr.get(1);
	}
	
	/**
	 * this method is for geting back to the mainpage as manger or librairan
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void back(ActionEvent event) throws IOException {
		User user1= new User();
		user1.setUserID(userId);
		ArrayList<String> arr = new ArrayList<String>();
		if(userType.equals("Managment")) {
		((Node) event.getSource()).getScene().getWindow().hide();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMangerGUI3.fxml"));
		Parent root =(Parent) fxmlLoader.load();
		MainMangerGUIController display=fxmlLoader.getController();	
		// we save the paramter we want to send to the anther GUI 
		arr.add(user1.getUserID());
		System.out.println(user.getUserID()+"marshood user");
	    display.loadData(arr);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.getIcons().add(new Image("/Pictures/fivecon.png"));
		stage.setTitle("OBL");
		stage.setResizable(false);
		stage.show();
	}
		else
		{
			
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
}
