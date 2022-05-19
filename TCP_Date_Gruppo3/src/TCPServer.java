import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServer extends Thread{
	
	private ServerSocket serversock;		//ServerSocket è la classe che crea il socket TCP di un server

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public TCPServer (int port) throws IOException {		//costruttore in cui viene richiesta un numero di porta come input esterno
		serversock = new ServerSocket(port);
		serversock.setSoTimeout(1000);		//indica il tempo in cui il socket rimarrà aperto se non riceve nessuna richiesta
	}
	
	public TCPServer () throws IOException {		//costruttore senza input esterni in cui prende la porta standard del protocollo TCP , (13) 
		serversock = new ServerSocket(13);
		serversock.setSoTimeout(1000);		//indica il tempo in cui il socket rimarrà aperto se non riceve nessuna richiesta
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	
	public void run () {
		Socket connection = null;		//creazione di un oggetto di tipo socket
		
		while( !Thread.interrupted() ) {		//fa le seguenti azioni finche il thread non è interrotto
			
	//--------------------------------------------------------------------------------------------------//
			
			try {
				//attesa richiesta connessione da parte del client (attesa massima 1s)
				connection = serversock.accept();
				System.out.println("Data/ora richiesta da:" +connection.getInetAddress().toString() +":" +connection.getPort());
				
				//creazione stream di output con codifica UTF-8
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");			//OutputStreamWriter serve per gestire i flussi di dati in uscita
				
				//creazione ogetto data/ora corrente chiamato now
				Date now = new Date();
				
				//invio al client della stringa che rappresenta la data/ora corrente con terminatore di stringa
				out.write(now.toString() +"\r\n");
				out.flush();		//comando per inviare
				
				//chiusura stream di output e socket di connessione 
				out.close();
				connection.shutdownOutput();	//blocca l' uso della classe OutputStreamWriter
				connection.close();
			}
			
	//--------------------------------------------------------------------------------------------------//
			
			//in caso incontra un errore di quelli qui elencati, chiude il socket
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
		
		//chiusura server di ascolto
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
			TCPServer date_server = new TCPServer();
			date_server.start();			//start cerca per il metodo di TCPServer chiamato run e lo esegue
			System.out.println("metodo run avviato");
			c = System.in.read();				//ricezione dati del client
			date_server.interrupt();		//interruzione del thread del socket
			date_server.join();				//aspetta che il thread si chiuda 
			
		}
		catch(IOException exception) {
			System.err.println(exception);
		}
		catch(InterruptedException exception ) {
			System.err.println("Fine.");
		}
	}
	
}
