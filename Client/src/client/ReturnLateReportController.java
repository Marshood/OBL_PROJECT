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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class ReturnLateReportController is Controller to show the return late reports 
 *
 * @author Marshood Ayoub , 311286694 
 * @version Jan 2019
 */  
public class ReturnLateReportController {
    // this BarChart to show the BarChart graph 
    @FXML
    private BarChart<String, Number> LateBookChart;

    @FXML
    private CategoryAxis x1;
    @FXML
    private Pane paneReturnLateReports;

    @FXML
    private NumberAxis y1;
    // this TextField AvgLateBook in to show the Avg late's books0 

    @FXML
    private TextField AvgLateBook;
    // this TextField MedianLateBook in to show Median late's books0 
    @FXML
    private TextField MedianLateBook;
    // this sumOfLateBook to sum the number of return lated book 
	private int sumOfLateBook;
	// this ArrayList message  to send to the server information and get from the server 
	private ArrayList<String> message;
	// this Srtings is for the chart graph 
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
	
	   // this text represent to the the user id 
    @FXML
    private Text userID;
	public void initialize() {
		int X;
		ClientConsole clientCon = new ClientConsole();
		message = new ArrayList<String>();
		message.add("ReturnLateReport");
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
		System.out.println("obj1  "+ obj1);
		List<Double> counter = (ArrayList<Double>) obj1; // in place 0 we have summary of lended books
		int sum=0;
		List<Double> median = new ArrayList<>(); ;
 		for(int i=0;i<counter.size()-1;i++)
		{
			sum+=counter.get(i);
			median.add(counter.get(i));
		}
		System.out.println(" sum  " + sum);
		calculateAvgLateBook(counter.get(counter.size()-1),counter.size()-1);
		//List<Double> LoanReportMedian = median.subList(median.size(), median.size());
		Collections.sort(median);
		int length = median.size() / 2;
		double d;
		if (median.size() % 2 == 0) {
			d = (median.get(length) + median.get(length - 1)) / 2;
			MedianLateBook.setText(Double.toString(d));
		} else {
			d = (median.get(length));
			MedianLateBook.setText(Double.toString(d));
		}
		int[] a = new int[11];
		for(int i=0;i<median.size();i++) {
			X=median.get(i).intValue();
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
	    LateBookChart.getData().addAll(set1);
		
	    
	}
	/*
	 * this method to get the seleted date to show the report's 
	 * */
	public void loadDate( String userid) {
		// TODO Auto-generated method stub
		userID.setText(userid);
	}
/*
 * this method calculateAvgLateBook is for the sum the avg 
 */
	private void calculateAvgLateBook(double sum, double booksum) {
		double avg = 0,div=booksum+sum;
		avg = sum / div;
		System.out.println("avg  "+ avg + " sum "+ sum );
		AvgLateBook.setText(String.valueOf(avg));
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
	    @SuppressWarnings("unused")
		@FXML
	    void SaveReports(ActionEvent event) throws IOException, AWTException {
	    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh mm ss a  ");
	  	    Stage stageScreen = null;
	  	    stageScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
 	        Calendar now = Calendar.getInstance();
	        Robot robot;
	  		robot = new Robot();
	    	   Bounds bounds = paneReturnLateReports.getBoundsInLocal();
	           Bounds screenBounds = paneReturnLateReports.localToScreen(bounds);
	           int x = (int) screenBounds.getMinX();
	           int y = (int) screenBounds.getMinY();
	           int width = (int) screenBounds.getWidth();
	           int height = (int) screenBounds.getHeight();
 	           new File("c:\\OBL\\Reports").mkdirs();
	           Rectangle screenRect = new Rectangle(x, y, width, height);
	           BufferedImage capture = new Robot().createScreenCapture(screenRect);
	             WritableImage image = paneReturnLateReports.snapshot(new SnapshotParameters(), null);

	           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("c:\\OBL\\Reports\\"+formatter.format(now.getTime())+ "ReturnLateReports"+".png"));
 	           Alert alert = new Alert(AlertType.WARNING, "File Saved as c:\\\\OBL\\\\Reports", ButtonType.CLOSE);
				alert.show();
	    	}
	 
}
