package application;

import java.text.ParseException;

public class ZiadThreads implements Runnable {

	
	
	@Override
	public void run() {

		try {
			while(true) {
			System.out.println("Threads Start");
			mysqlConnection mysql= new mysqlConnection();
			try {
				mysqlConnection.AutoThreadWork();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//EchoServer.handleMessageFromClient("aa");
			Thread.sleep(1000*60*60*24);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}
