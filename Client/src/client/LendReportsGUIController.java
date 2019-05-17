package client;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import entites.Book;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class LendReportsGUIController implements Initializable {
	private int sumOfLoanBook;
	private ArrayList<String> message;

	@FXML
	private BarChart<String, Number> requestedBookChart;

	@FXML
	private CategoryAxis x2;

	@FXML
	private NumberAxis y2;

	@FXML
	private BarChart<String, Number> regularBookChart;

	@FXML
	private CategoryAxis x1;

	@FXML
	private NumberAxis y1;

	@FXML
	private TextField AvgRegularBook;

	@FXML
	private TextField AvgRequestedBook;

	@FXML
	private TextField MedianRegularBook;

	@FXML
	private TextField MedianRequestedBook;

    @FXML
    private Text userID;
    
	final static String rang1 = "0-3";
	final static String rang2 = "4-7";
	final static String rang3 = "8-11";
	final static String rang4 = "12-15";
	final static String rang5 = "16-19";
	final static String rang6 = "20-23";
	final static String rang7 = "24-27";
	final static String rang8 = "28-31";
	final static String rang9 = "32-35";
	final static String rang10 = "36-39";
	final static String rang11 = "40+";
	/*
	 * this method to get the seleted date to show the report's 
	 * */
	public void loadDate( String userid) {
		// TODO Auto-generated method stub
		userID.setText(userid);
	}

    @FXML
    private Pane ThisPane;
	/*
	 * public void start(Stage stage) {
	 * 
	 * }
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int X;
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("LoanReport");
		Object obj = (Object) message;
		Object obj1 = new Object();

		try {
			clientCon.execute(obj);
			Thread.currentThread().sleep(1500);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj1 = clientCon.Getrespond();
		List<Double> counter = (ArrayList<Double>) obj1; // in place 0 we have summary of lended books
		System.out.println("Client "+ counter);
		calculateAvgregular(counter.get(0), counter.get(1));
		calculateAvgrequested(counter.get(2), counter.get(3));
		List<Double> LoanReportMedian = counter.subList(4, counter.size());
		if (LoanReportMedian.get(0)!=-1.0)
		{
			Collections.sort(LoanReportMedian);
			int length = LoanReportMedian.size() / 2;
			double d;
			if (LoanReportMedian.size() % 2 == 0) {
				d = (LoanReportMedian.get(length) + LoanReportMedian.get(length - 1)) / 2;
				MedianRegularBook.setText(Double.toString(d));
			} else {
				d = (LoanReportMedian.get(length));
				MedianRegularBook.setText(Double.toString(d));
			}
			int[] a = new int[11];
			for(int i=0;i<LoanReportMedian.size();i++) {
				X=LoanReportMedian.get(i).intValue();
				switch (X/4)
				{
				case 0:
					a[0]++;
					break;
				case 1:
					a[1]++;
					break;
				case 2:
					a[2]++;
					break;
				case 3:
					a[3]++;
					break;
				case 4:
					a[4]++;
					break;
				case 5:
					a[5]++;
					break;
				case 6:
					a[6]++;
					break;
				case 7:
					a[7]++;
					break;
				case 8:
					a[8]++;
					break;
				case 9:
					a[9]++;
					break;
				case 10:
					a[9]++;
					break;
				default:
					a[10]++;
					break;
					
				}
			
			}
			XYChart.Series set1 = new XYChart.Series();
		    set1.getData().add(new XYChart.Data(rang1, a[0]));
		    set1.getData().add(new XYChart.Data(rang2, a[1]));
		    set1.getData().add(new XYChart.Data(rang3, a[2]));
		    set1.getData().add(new XYChart.Data(rang4, a[3]));
		    set1.getData().add(new XYChart.Data(rang5, a[4]));
		    set1.getData().add(new XYChart.Data(rang6, a[5]));
		    set1.getData().add(new XYChart.Data(rang7, a[6]));
		    set1.getData().add(new XYChart.Data(rang8, a[7]));
		    set1.getData().add(new XYChart.Data(rang9, a[8]));
		    set1.getData().add(new XYChart.Data(rang10, a[9]));
		    set1.getData().add(new XYChart.Data(rang11, a[10]));
		    regularBookChart.getData().addAll(set1);
		}

		message = new ArrayList<String>();
		message.add("LoanReportRequestedBook");
		obj = (Object) message;
		obj1 = new Object();

		try {
			clientCon.execute(obj);
			Thread.currentThread().sleep(1500);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj1 = clientCon.Getrespond();
		ArrayList<Double> ResRequestedBook = (ArrayList<Double>) obj1;
		if(ResRequestedBook.get(0)!=-1.0) {
		Collections.sort(ResRequestedBook);
		System.out.println(ResRequestedBook);
		int length2 = ResRequestedBook.size() / 2;
		System.out.println(length2);
		double d2;
		if (ResRequestedBook.size() % 2 == 0) {
			d2 = (ResRequestedBook.get(length2) + ResRequestedBook.get(length2 - 1)) / 2;
			MedianRequestedBook.setText(Double.toString(d2));
		} else {
			d2 = (ResRequestedBook.get(length2));
			MedianRequestedBook.setText(Double.toString(d2));
		}
		
		int[] b = new int[11];
		for(int i=0;i<ResRequestedBook.size();i++) {
			X=ResRequestedBook.get(i).intValue();
			System.out.println("This " + X);
			switch (X/4)
			{
			case 0:
				b[0]++;
				break;
			case 1:
				b[1]++;
				break;
			case 2:
				b[2]++;
				break;
			case 3:
				b[3]++;
				break;
			case 4:
				b[4]++;
				break;
			case 5:
				b[5]++;
				break;
			case 6:
				b[6]++;
				break;
			case 7:
				b[7]++;
				break;
			case 8:
				b[8]++;
				break;
			case 9:
				b[9]++;
				break;
			case 10:
				b[9]++;
				break;
			default:
				b[10]++;
				break;
				
			}
		
		}
		XYChart.Series set2 = new XYChart.Series();
		set2.getData().add(new XYChart.Data(rang1, b[0]));
		set2.getData().add(new XYChart.Data(rang2, b[1]));
		set2.getData().add(new XYChart.Data(rang3, b[2]));
		set2.getData().add(new XYChart.Data(rang4, b[3]));
		set2.getData().add(new XYChart.Data(rang5, b[4]));
		set2.getData().add(new XYChart.Data(rang6, b[5]));
		set2.getData().add(new XYChart.Data(rang7, b[6]));
		set2.getData().add(new XYChart.Data(rang8, b[7]));
		set2.getData().add(new XYChart.Data(rang9, b[8]));
		set2.getData().add(new XYChart.Data(rang10, b[9]));
		set2.getData().add(new XYChart.Data(rang11, b[10]));
	    requestedBookChart.getData().addAll(set2);
		}
	}

	private void calculateAvgregular(double sum, double booksum) {
		double avg = 0;
		avg = sum / booksum;
		AvgRegularBook.setText(String.valueOf(avg));
	}

	private void calculateAvgrequested(double sum, double booksum) {
		double avg = 0;
		avg = sum / booksum;
		AvgRequestedBook.setText(String.valueOf(avg));
	}
	
	   /*
     * this method SaveReports is to save the current page 
     * we selected the path where to save the picture and afer this we save the picture 
     */
	
	
    @FXML
    void SaveReports(ActionEvent event) throws IOException, AWTException {
  	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh mm ss a  ");
  	    Stage stageScreen = null;
  	    stageScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
  	    double X=stageScreen.getX();
  	    double Y=stageScreen.getY();
  	    System.out.println("X   "+ X+ " y  " + Y);
        Calendar now = Calendar.getInstance();
        Robot robot;
  		robot = new Robot();
    	   Bounds bounds = ThisPane.getBoundsInLocal();
           Bounds screenBounds = ThisPane.localToScreen(bounds);
           Integer x = Integer.valueOf((int) screenBounds.getMinX());
           Integer y = Integer.valueOf((int) screenBounds.getMinY());
           WritableImage image = ThisPane.snapshot(new SnapshotParameters(), null);
            System.out.println("X   "+ x+ " y  " + y);
           int width = (int) screenBounds.getWidth();
           int height = (int) screenBounds.getHeight();
	           new File("c:\\OBL\\Reports").mkdirs();
           Rectangle screenRect = new Rectangle(x, y, width, height);
           BufferedImage capture = new Robot().createScreenCapture(screenRect);
           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("c:\\OBL\\Reports\\"+formatter.format(now.getTime())+ "LendReports"+".png"));
           Alert alert = new Alert(AlertType.WARNING, "File Saved as c:\\\\OBL\\\\Reports", ButtonType.CLOSE);
			alert.show();
    	}
    /*
	 * this method  is to back to the last gui 
	 * 
	 * */
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
	   * this method Logout is to logout the corrent user and change the status to no online 
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
	  
}