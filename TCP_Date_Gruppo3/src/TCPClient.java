import java.io.*;
import java.net.*;

public class TCPClient {
	
	private String server_name;			//variabile contenente il nome del server
	private int server_port;		//variabile contenente il numero di porta
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public TCPClient (String server, int port) throws IOException {			//costruttore costruttore che ha come input esterni il nome del server e il numero di porta
		server_name=server;
		server_port = port;
	}
	
	//--------------------------------------------------------------------------------------------------//
	
	public TCPClient (String server) throws IOException {		//costruttore che ha come input esterno il nome del server
		server_name = server;
		//la porta TCP 13 è la porta del servizio daytime
		server_port = 13;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public String getDaytime () throws SocketTimeoutException, IOException {		//metodo che permette la connessione col server per la ricezione della data e ora
		
		InputStream stream;			//oggetto InputStream usato per inviare il pacchetto
		String answer = "";			
		String fragment;			//variabile che conterra' cio' che verra' ricevuto dal server
		int n;						
		byte [] buffer = new byte [1024];
		
	//--------------------------------------------------------------------------------------------------//
		
		Socket client_socket = new Socket();
		InetSocketAddress server_address = new InetSocketAddress(server_name, server_port);

		client_socket.setSoTimeout(1000); //1000ms = 1s
		//richiesta di connessione al server (attesa massima di 1 secondo)

		client_socket.connect(server_address, 1000); 		//connessione al server
		
		//stream di input  per ricezione dati dal server
		stream = client_socket.getInputStream();
	
	//--------------------------------------------------------------------------------------------------//
		
		while ( ( n = stream.read( buffer ) ) != -1 ) {		//ciclo di lettura dei dati ricevuti dal server nello stream di input fino alla chiusura da parte del server

			fragment = new String (buffer, 0, n ,"UTF-8");
			answer = answer + fragment;
			
		}
		
	//--------------------------------------------------------------------------------------------------//
		
		//chiususra dello stream di ricezione dei dati e dei socket di connessione al server
		stream.close();
		client_socket.close();
		return answer;

	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public static void main (String args[]) {
		String server = "127.0.0.1";
		int port = 13;
		String daytime;
		TCPClient client ;
		
		try {

			client = new TCPClient(server, port);		//instanziazione dell' oggetto client
			daytime = client.getDaytime();				//inserimento della risposta del server con la data e ora corrente in daytime
			System.out.println(daytime);
			
		}
		catch (SocketTimeoutException exception) {
			System.err.println(exception);
		}
		catch (IOException exception) {
			System.err.println(exception);
		}

	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

}
