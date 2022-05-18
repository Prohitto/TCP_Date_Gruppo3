import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServer extends Thread{
	private ServerSocket serversock;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public TCPServer (int port) throws IOException {
		serversock = new ServerSocket(port);
		serversock.setSoTimeout(1000);
	}
	public TCPServer () throws IOException {
		serversock = new ServerSocket(13);
		serversock.setSoTimeout(1000);
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public void run () {
		Socket connection = null;
		
		while(Thread.interrupted()) {
			
//--------------------------------------------------------------------------------------------------//
			
			try {
				connection = serversock.accept();
				System.out.println("Data/ora richiesta da:" +connection.getInetAddress().toString() +":" +connection.getPort());
				
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
				
				Date now = new Date();
				
				out.write(now.toString() +"\r\n");
				out.flush();
				
				out.close();
				connection.shutdownOutput();
				connection.close();
			}
			
//--------------------------------------------------------------------------------------------------//
			
			catch(SocketTimeoutException exception) {
			}
			catch(IOException exception) {
			}
			finally {
				if(connection != null) {
					try {
						connection.shutdownOutput();
						connection.close();
					}
					catch(IOException exception) {
					}
				}
			}
		}
		
		try {
			serversock.close();
		}
		catch(IOException exception) {
		}
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void main (String[] args) {
		int c;
		
		try {
			TCPServer date_server = new TCPServer();System.out.println("calalco");
			date_server.start();
			c = System.in.read();
			date_server.interrupt();
			date_server.join();
			
		}
		catch(IOException exception) {
			System.err.println(exception);
		}
		catch(InterruptedException exception ) {
			System.err.println(exception);
		}
	}
	
}
