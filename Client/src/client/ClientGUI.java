package client;

/**
 * this method contains the main method of the client 
 */
import java.io.IOException;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientGUI extends Application {
	JOptionPane frame;
	public static String ip;
	public static String fxmlDir = "fxml/";

	public static void main(String args[]) {
		try {
			ip = args[0];
		} catch (Exception e) {

		}
		launch(args);
	}
/**
 * this method is the start method to load the login page for the user 
 * 
 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource("/FXML/IPCheck_And_Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/client/application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("/Pictures/fivecon.png"));
			primaryStage.setTitle("OBL Systems G5");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
