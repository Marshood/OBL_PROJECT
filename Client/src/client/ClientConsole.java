package client;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	public static ArrayList<Student> stud = new ArrayList<Student>();
	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	static ChatClient client;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	public ClientConsole() {

	}

	public void execute(Object msg) throws ClassNotFoundException {
		//System.out.println("clientConsloe execute");
		try {
		//	System.out.println(msg + "execute msg");
			client.handleMessageFromClientUI((Object) msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */

	public void accept() {
		try {

			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			ArrayList<String> message1 = new ArrayList<String>();
			String message;
			while (true)

			{
				System.out.println("Enter id :\n");
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
				message1.add(message);
				// System.out.println ("Enter Func:\n");
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
				message1.add(message);
				// System.out.println ("Enter status :\n");
				// message1 = fromConsole.readLine();
				client.handleMessageFromClientUI(message1);
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	static Object obj;

	public void display(String message) {
		//System.out.println("> " + message);
		obj = (Object) message;
	}
 
	public void respond(Object object) {
		obj = object;
	}

	public Object Getrespond() {
		//System.out.println("OBJJJJJJ"+obj);
		return this.obj;

	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
	public boolean startconnection() {
		String host = "";
		try {
			host = ClientGUI.ip;
		} catch (Exception e) {
			host = "localhost";
		}
		ClientConsole chat = new ClientConsole(host, DEFAULT_PORT);
		// chat.accept(); // Wait for console data
		return true;
	}
}
//End of ConsoleChat class
