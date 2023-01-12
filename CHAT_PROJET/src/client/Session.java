package client;

import java.net.InetAddress;

public class Session {
	private String login_clt;
	private InetAddress adress;
	private int port;
	
	
	
	public Session(String login_clt, InetAddress adress, int port) {
		super();
		this.login_clt = login_clt;
		this.adress = adress;
		this.port = port;
	}
	public InetAddress getAdress() {
		return adress;
	}
	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public Session() {
		super();
	}
	public String getLogin_clt() {
		return login_clt;
	}
	public void setLogin_clt(String login_clt) {
		this.login_clt = login_clt;
	}
	
	
	@Override
	public String toString() {
		return   login_clt + " "+adress+" "+port;
	}
	
	
}
