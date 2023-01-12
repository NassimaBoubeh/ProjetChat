package server;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class serverMain {

	
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		DatagramSocket socket = new DatagramSocket(3001);
		InetAddress adr = InetAddress.getByName("localhost");
		System.out.println("LE SERVER ATTEND SUR LA PORT 3001");
		while (true) {
			UDPServer server = new UDPServer(socket, adr);
			
			server.start();
		try {
			server.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			break;
		}
		}
		
	}

}
