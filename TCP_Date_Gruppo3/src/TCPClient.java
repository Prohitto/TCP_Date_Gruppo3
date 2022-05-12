import java.io.*;
import java.net.*;

public class TCPClient {
	
	private String server_name;
	private int server_port;
	
	public TCPClient (String server, int port) throws IOException {
		server_name=server;
		server_port = port;
	}
	
}
