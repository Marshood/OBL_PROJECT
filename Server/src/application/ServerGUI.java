package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;


public class ServerGUI extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	/*@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("test");
		
	}*/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerController sc = new ServerController();
		sc.start(primaryStage);
	}

	
}