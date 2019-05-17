package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerController {
	
	@FXML
	private TextField dbusn;
	@FXML
	private TextField dbpass;
	@FXML
	private Label connectFail;

	public static String serverMsg;
	public static Alert alert;
	
	private String DBUsn;
	private String DBPass;
	
	EchoServer es = new EchoServer(5555);
	
	public ServerController() {
		
	}

	public void connect(ActionEvent event) {

		DBUsn = dbusn.getText();
		DBPass = dbpass.getText();
		
		if (DBUsn.equals("root") && DBPass.equals("Braude")) {
			

			es.connectTOServer();

			 Connector c = new Connector();
			 if (Connector.flag == 1) {
				 try {
						c.connectToDB();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				 Connector.flag = 0;
			 }

			

		}
		else {
			alert = new Alert(AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
			alert.setTitle("Server Error");
			alert.setHeaderText("Re-try again");
			alert.show();
		}
	}
	
	public void exit() throws IOException {		
		try{
		EchoServer.saveClient.sendToClient("kick");	
		}catch(NullPointerException e) {
			System.exit(0);
		}
		finally {
			System.exit(0);	
		}
			
	}

	
	
	
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/application/ServerGUI.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		primaryStage.setTitle("Server Connection");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent we) {
				try {
					exit();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
