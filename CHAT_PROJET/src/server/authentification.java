package server;

import java.net.InetAddress;

import client.Client;

public interface authentification {
	void inscrire(Client clt);
	
	boolean authentifier(String login, String password, InetAddress adr, int port);
	
	
	void deconnecter(int port, InetAddress adr);
}
