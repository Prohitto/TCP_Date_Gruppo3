import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServer {
	private ServerSocket serversock;
	
	public TCPServer (int port) throws IOException {
		serversock = new ServerSocket(port);
		serversock.setSoTimeout(1000);
	}
	public TCPServer () throws IOException {
		serversock = new ServerSocket(13);
		serversock.setSoTimeout(1000);
	}
	
	public void run () {
		Socket connection = null;
		
		while(true) {
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
	
}
