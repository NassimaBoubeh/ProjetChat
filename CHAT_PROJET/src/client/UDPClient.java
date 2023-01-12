package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient extends Thread{
	private DatagramSocket socket;
	private InetAddress adress;
	private byte[] buffer;
	private String msgR;
	
	
	public UDPClient(DatagramSocket socket, InetAddress adress) {
		super();
		this.socket = socket;
		this.adress = adress;
		System.out.println("je suis connecté..................");
	}
	
	public void send(String msg) {
		
			try {
				System.out.println("send : "+msg);
				buffer = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, 3001);
				socket.send(packet);
				System.out.println("message envoyer");
			}catch (IOException e) {
				e.printStackTrace();
				
			}
			
		}
	
	
	public String sendThenReceive(String msgE) {
		String msgR = "";
		
		try {
			buffer = msgE.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adress, 3001);
			socket.send(packet);
			socket.receive(packet);
			int port = packet.getPort();
			msgR = new String(packet.getData(), 0, packet.getLength());
		}catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return msgR;
}
	
		
	

}
