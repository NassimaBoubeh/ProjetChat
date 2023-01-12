package server;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.Client;
import client.Session;

public class UDPServer extends Thread implements authentification{
	
	private  DatagramSocket socket;
	
	private  InetAddress adress;
	private static final int PORT=300;
	private byte[] buffer= new byte[1024];
	private static List<Session> session = new ArrayList<Session>();
	private static List<String> group = new ArrayList<String>();
	
	//Les paramètres de connexion à BD
		private String url="jdbc:mysql://localhost:3307/chat?serverTimezone=UTC";
        private String userName="root";
        private String pwd="";
	
	public UDPServer(DatagramSocket socket, InetAddress adress) {
		super();
		this.socket = socket;
		this.adress = adress;
	}
//-----------------------------------------------------------------------------------------------
	@Override
	public void  inscrire(Client clt) {
        String requete="insert into client(login, password) values(?,?)";
        try {
			Connection con= DriverManager.getConnection(url, userName, pwd);
			PreparedStatement ps = con.prepareStatement(requete);
			
			ps.setString(1, clt.getLogin());
			ps.setString(2, clt.getPassword());
			ps.executeUpdate();
			ps.close();
			con.close();
			System.out.println("----------------------------------------------");
			System.out.println("le client "+clt.getLogin()+" est enregistré.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//-----------------------------------------------------------------------------------------------	
	@Override
	public boolean authentifier(String login, String password, InetAddress iPAddress, int port) {
        String requete="select * from client where login='"+login+"' and password='"+password+"';";
      
        boolean valide=false;
        
        try {
			Connection con= DriverManager.getConnection(url, userName, pwd);
			PreparedStatement ps = con.prepareStatement(requete);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Session client = new Session(login, iPAddress, port);
				session.add(client);
				valide=true;
			}
			
			System.out.println("--------------LISTE DES CLIENTS AUTHENTIFIEES-----------------");
			for(int i=0; i<session.size(); i++) {
				System.out.println(session.get(i).toString());
			}
			
			ps.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return valide;
		
		
	}
//-----------------------------------------------------------------------------------------------
	@Override
	public void deconnecter(int port, InetAddress adr) {
		for (int i=0; i<session.size(); i++) {
			if(session.get(i).getPort()==port) {
				System.out.println("----------------------------------------------");
				System.out.println(session.get(i).getLogin_clt()+" est déconnecté.");
				session.remove(session.get(i));
			}
		}
	}
		

	
//-----------------------------------------------------------------------------------------------
	
	public void inscritClient(String client) {
		Client clt = new Client();
		String[] info = client.split(",");
		clt.setLogin(info[0]);
		clt.setPassword(info[1]);
		inscrire(clt);
	}
	
//-----------------------------------------------------------------------------------------------

	public boolean connectClient(String client, InetAddress adr, int port) {
		boolean valide=false;
		String[] info = client.split(",");
		String login=info[0];
		String password= info[1];
		
		if(authentifier(login, password, adr, port)) {
			valide=true;
		}
		return valide;
		
	}
//-----------------------------------------------------------------------------------------------	

	public void send(String msg, int port, InetAddress adr) {
		try {
			buffer = msg.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adr, port);
			socket.send(packet);
		}catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
//-----------------------------------------------------------------------------------------------	
	
	public void addAmis(String login, int port) {
		try {
			String login_clt="";
			String requete = "insert into amis values(?,?);";
        
			Connection con = DriverManager.getConnection(url, userName, pwd);
		
		//chercher le login de client courant pour ajouter à la table amis	
		for (Session clt : session) {
			if(clt.getPort()==port) {
				login_clt=clt.getLogin_clt();
			}
		}
		
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, login_clt);
		ps.setString(2, login);
		ps.executeUpdate();
		ps.close();
		con.close();
		System.out.println("***"+login+" est ajouté à la liste des amis");
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//----------------------------------------------------------------------------------------------
	
	private void deleteAmi(String ami, int port) {
		String login_clt="";
		for (Session clt : session) {
			if(clt.getPort()==port) {
				login_clt=clt.getLogin_clt();
			}
		}
		String requete = "delete from amis where pseudo_clt ='"+login_clt+"' and pseudo_ami='"+ami+"';";
        try {
		Connection con = DriverManager.getConnection(url, userName, pwd);
		
		
		PreparedStatement ps = con.prepareStatement(requete);
		ps.executeUpdate();
		ps.close();
		con.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//-----------------------------------------------------------------------------------------------
	//identifier le client pour lister leurs amis
	public void sendLogin( int port, InetAddress adr) {

		String login="";		
		for (Session clt : session) {
			if(clt.getPort()==port) {
				login=clt.getLogin_clt();
			}
		}
		send(login,port,adr);
	}
	
//-----------------------------------------------------------------------------------------------
	
			
	public void sendToAmi(String msg, String ami) {
		System.out.println("DANS SENDTOAMI+++++++++++++++++++++++++++++++");
			int port_ami=0;
			InetAddress adr_ami=null;
			String msgE = "one:"+msg;
			
			for (Session clt : session) {
				if(clt.getLogin_clt().equals(ami)) {
					port_ami=clt.getPort();
					adr_ami=clt.getAdress();
					System.out.println(port_ami+" "+adr_ami);
					send(msgE,port_ami, adr_ami);
					
					System.out.println(msg+" envoyé............................");
				}
			}

	}
	
//-----------------------------------------------------------------------------------------------
	
	public void addToGroup(String login, int port) {
		try {
			
	        String login_clt="";
	        String requete = "insert into groupe values(?,?);";
	        
			Connection con = DriverManager.getConnection(url, userName, pwd);
			
			//pour identifier l'admin de group
			for (Session clt : session) {
				if(clt.getPort()==port) {
					login_clt=clt.getLogin_clt();
				}
			}
			
			PreparedStatement ps = con.prepareStatement(requete);
			ps.setString(1, login_clt);
			ps.setString(2, login);
			ps.executeUpdate();
			ps.close();
			con.close();
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
//-------------------------------------------------------------------------------------------
	
	public void deleteFromGrp(String ami, int port) {
		String login_clt="";
		for (Session clt : session) {
			if(clt.getPort()==port) {
				login_clt=clt.getLogin_clt();
			}
		}
		System.out.println(login_clt+" "+ami);
		String requete = "delete from groupe where admin ='"+login_clt+"' and ami='"+ami+"';";
        try {
		Connection con = DriverManager.getConnection(url, userName, pwd);
		
		
		PreparedStatement ps = con.prepareStatement(requete);
		ps.executeUpdate();
		ps.close();
		con.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendToGroup(String msg, int port) {
		
		String url="jdbc:mysql://localhost:3307/chat?serverTimezone=UTC";
        String userName="root";
        String pwd="";
        String login_clt="";
        String requete="";
        for (Session clt : session) {
			if(clt.getPort()==port) {
				login_clt=clt.getLogin_clt();
				 requete= "select * from groupe where admin='"+login_clt+"';";
				 
			}
		}
        
        
		try {
        Connection con = DriverManager.getConnection(url, userName, pwd);
        PreparedStatement ps = con.prepareStatement(requete);
		
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	String ami = rs.getString("AMI");
		int port_ami=0;
		InetAddress adr_ami=null;
		for (Session clt : session) {
			if(clt.getLogin_clt().equals(ami)) {
				port_ami=clt.getPort();
				adr_ami=clt.getAdress();
				String newMsg = "groupe:"+login_clt+","+msg;
				send(newMsg,port_ami, adr_ami);
			}
		}
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
}
	
	public void run() {
		
		try {
			String recept = new String();
			DatagramPacket packetIn = new DatagramPacket(buffer, buffer.length);
			socket.receive(packetIn);
			recept=new String( packetIn.getData(), 0, packetIn.getLength());
			int port_clt = packetIn.getPort();
			InetAddress adr_clt = packetIn.getAddress();
			String[] fonction = recept.split(":");
		
		switch (fonction[0]) {
		case "inscrire": {
			inscritClient(fonction[1]);
			break;
		}
		case "connecter": {
			if(connectClient(fonction[1],adr_clt,port_clt)) {
				send("true",port_clt, adr_clt);
			}else {
				send("false", port_clt, adr_clt);
			}
			break;
		}
		case "deconnecter": {
			deconnecter(port_clt, adr_clt);
			break;
		}
		case "chat": {
			
			send(fonction[1], port_clt, adr_clt);
			break;
		}
		case "amis": {
			
			addAmis(fonction[1], port_clt);
			break;
		}case "deleteAmi": {
			
			deleteAmi(fonction[1], port_clt);
			break;
		}
		case "listAmis": {
			
			sendLogin(port_clt, adr_clt);
			break;
		}
		case "chatAmiToAmi": {
			String tab[] = fonction[1].split(",");
			sendToAmi(tab[0],tab[1]);
			break;
		}
		case "addToGroup": {
			
			addToGroup(fonction[1], port_clt);
			break;
		}
		case "deleteFromGrp": {
			
			deleteFromGrp(fonction[1],port_clt);
			break;
		}
		case "sendToGroup": {
			
			sendToGroup(fonction[1], port_clt);
			break;
		}
		case "retour": {
			
			send("retour:vide", port_clt, adr_clt);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + fonction[0]);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
