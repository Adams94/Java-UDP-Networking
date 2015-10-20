import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A User Datagram Protocol (UDP) Client that will allow a user to input
 * text messages and the client will send these messages to the server
 * for the server to read. The client will continue to send messages until
 * the user has entered "exit", in which then the client will attempt to
 * shut itself down.
 * 
 * @author Chad Adams <https://github.com/Adams94>
 */
public class UDPClient {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(UDPClient.class.getName());
	
	/**
	 * The port to which the DatagramPacket will be sent to.
	 */
	public static final int PORT = 3000;
	
	public static void main(String[] args) {
		try {
			//Creating the socket for this client.
			DatagramSocket ds = new DatagramSocket();
			
			//Creating our user input
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			//Obtain the address to which the DatagramSocket will be sent to.
			InetAddress address = InetAddress.getByName("127.0.0.1");
			
			//Declaring the message.
			String message = "";
			
			//Declaring a DatagramPacket
			DatagramPacket dp;
			
			while(!message.equalsIgnoreCase("exit")) {
				//Assigning message the string of the users input.
				message = reader.readLine();
				//Initializing and constructing the DatagramPacket
				dp = new DatagramPacket(message.getBytes(), message.length(), address, PORT);
				//After constructing the DatagramPacket, send to server.
				ds.send(dp);
			}
			if (message.equalsIgnoreCase("exit")) {
				logger.log(Level.INFO, "Client is now shutting down..");
			}
			//We are no longer taking user input, so close the input stream.
			reader.close();
			//Once the user has entered "exit", the DatagramSocket will close.
			ds.close();
			logger.log(Level.INFO, "Connection has been closed, and client has successfully shut down.");
		} catch (IOException e) {
			logger.log(Level.INFO, "An error has occured while sending a message.", e);
		}
	}

}
