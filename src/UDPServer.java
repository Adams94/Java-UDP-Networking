import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A User Datagram Protocol (UDP) Server that will continue to read
 * a string message sent from a client until the client sends a "exit".
 * When a user types "exit", and this message is read by the server; the
 * server will attempt to shut itself down.
 * 
 * @author Chad Adams <https://github.com/Adams94>
 */
public class UDPServer {
	
	/**
	 * The single logger for this class.
	 */
	public static final Logger logger = Logger.getLogger(UDPServer.class.getName());

	/**
	 * The port to which this server will be bound to.
	 */
	public static final int PORT = 3000;
	
	public static void main(String[] args) {
		try {
			//Creating the server socket and binding to port 3000
			DatagramSocket ds = new DatagramSocket(PORT);
			
			logger.log(Level.INFO, "UDP Server online and bound to port: " + PORT);
			
			byte[] buf = new byte[1024];
			
			//Constructing a datagram packet
			DatagramPacket dp = new DatagramPacket(buf, 1024);
			
			//Initializing string
			String message = "";
			
			while(!message.equalsIgnoreCase("exit")) {
			//If the message doesn't contain "exit" then continue to accept a new packet.
			ds.receive(dp);
			//Construct our new message.
			message = new String(dp.getData(), 0, dp.getLength());
			//Read the message
			System.out.println("Message recieved from [" + ds.getLocalAddress().getHostAddress() + "]: " + message);			
			}
			if (message.equalsIgnoreCase("exit")) {
				logger.log(Level.INFO, "Server shutting down..");
			}
			//Always close the Datagram socket.
			ds.close();
			logger.log(Level.INFO, "Connection has been closed, and server has succesfully shut down.");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "The server encontered an error while recieving a packet.", e);
		}
	}

}
