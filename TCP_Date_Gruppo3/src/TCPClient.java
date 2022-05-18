import java.io.*;
import java.net.*;

public class TCPClient {
	
	private String server_name;
	private int server_port;
	
	public TCPClient (String server, int port) throws IOException {
		server_name=server;
		server_port = port;
	}
	
	public TCPClient (String server) throws IOException {
		server_name = server;
		//la porta TCP 13 è la porta del servizio daytime
		server_port = 13;
	}

	public String getDaytime () throws SocketTimeoutException, IOException {
		InputStream stream;
		String answer = "";
		String fragment;
		int n;
		byte [] buffer = new byte [1024];
		Socket client_socket = new Socket();
		InetSocketAddress server_address = new InetSocketAddress(server_name, server_port);
		client_socket.setSoTimeout(1000); //1000ms = 1s
		//richiesta di connessione al server (attesa massima di 1 secondo)
		client_socket.connect(server_address, 1000); //1000ms = 1s
		//stream di input  per ricezioen dati dal server
		stream = client_socket.getInputStream();
		//ciclo di lettura dei dati ricevuti dal server nello stream di input fino alla chiusura da pa rte del server
		while (( n = stream.read(buffer) ) != -1 ) {
			fragment = new String (buffer, 0, n ,"UTF-8");
			answer = answer + fragment;
		}
		//chiususra dello stream di ricezione dei dati e dei socket di connessione al server
		stream.close();
		client_socket.close();
		return answer;

	}

	public static void main (String args[]){
		String server = "127.0.0.1";
		int port = 13;
		String daytime;
		TCPClient client ;
		
		
		try {
			client = new TCPClient(server, port);
			daytime = client.getDaytime();
			System.out.println(daytime);
		}
		catch (SocketTimeoutException exception) {
			System.err.println(exception);
		}
		catch (IOException exception) {
			System.err.println(exception);
		}

	}

}
