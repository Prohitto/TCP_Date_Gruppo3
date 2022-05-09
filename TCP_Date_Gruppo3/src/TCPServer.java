import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServer extends Thread {
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
				System.out.println("Data/ora richiesta da:" +connection.getInetAddress().toString() +connection.getPort());
				
			}
		}
	}
	
}
