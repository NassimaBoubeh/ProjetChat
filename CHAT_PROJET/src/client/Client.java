package client;

import java.io.Serializable;
import java.net.InetAddress;

public class Client implements Serializable {
	private String login;
	private String password;
	private InetAddress adress;
	private int CPort;
	
	public Client() {
		super();
	}
	
	public Client(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	public Client(String login) {
		this.login=login;
	}
	
	public Client( String login, String password, InetAddress adress, int cPort) {
		super();
		this.login = login;
		this.password = password;
		this.adress = adress;
		CPort = cPort;
	}
	public InetAddress getAdress() {
		return adress;
	}
	public void setAdress(InetAddress adress) {
		this.adress = adress;
	}
	public int getCPort() {
		return CPort;
	}
	public void setCPort(int cPort) {
		CPort = cPort;
	}
	
	
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogin() {
		return login;
	}
	@Override
	public String toString() {
		return "Client " + " login=" + login + ", password=" + password + ", adress=" + adress
				+ ", CPort=" + CPort + "]";
	}
	
}
