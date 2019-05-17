package client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
/*
 * this class is to connect the client to the server 
 */
public class IPCheck_And_Login_Controller implements Initializable{
    
	@FXML
	private AnchorPane RP;
	@FXML
	private TextField IPAddress;
	@FXML
	private TextField Port;

	
	JOptionPane frame;
	
	public static ClientConsole clientConsole = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
    /**
     * this method is this method is to to get the port and the server's IP from the user after typing them
     * 
     * @param event
     * @throws IOException
     */
	@FXML
	void Connectfung(ActionEvent event) throws IOException {
		System.out.println("Trying to connect to : "+IPAddress.getText()+" At Port : "+ Port.getText());
		int portnum=Integer.parseInt(Port.getText());	
		String IPnum= (String)IPAddress.getText();
		if(IPAddress.getText().equals(null) || Port.getText().equals(null)) IPError() ;
		
		ClientConsole clientConsole = new ClientConsole (IPnum,portnum);
		if(clientConsole.client.isConnected()){
			System.out.println("You Are Connected");	 
		  }
		else{
			IPError() ;
		}
		try {
			Pane P = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
			RP.getChildren().setAll(P);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void IPError() {
		System.out.println("is not Connected !!!! (Error)");
	} 
	/**
	 * this method is to start the connection
	 * 
	 * @return
	 */
	public boolean connectNOW() {
		ClientConsole c = new ClientConsole();
		boolean con = c.startconnection();
		return true;
	}
	
	 @FXML
	 public void exit() {
		 System.out.println("Goodbye , See ya Next Time :)");
		 System.exit(0);
	 }
}
