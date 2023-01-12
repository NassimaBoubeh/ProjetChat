package presentation;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import client.Client;
import client.UDPClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
public class fenetre extends JFrame {
	private static DatagramSocket socket;
	private InetAddress adress;
	private byte[] buffer = new byte[1024];
	private String msgR;
	
	
		public void first(UDPClient client) {
			setTitle("ACCEUIL");
		    setSize(600, 500);
		    setLocationRelativeTo(null);
		    setLayout(new FlowLayout());
		    
		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		    
		    JLabel l = new JLabel("Welcome to chat application");
		    JPanel p = new JPanel();
		    p.add(l, BorderLayout.CENTER);
		    
		    
		    
		    JButton inscrire = new JButton("S'INSCRIRE");
		    add(inscrire);
		    
		    JButton connecter = new JButton("SE CONNECTER");
		    add(connecter);
		    
		    JPanel p1 = new JPanel();
		    p1.add(inscrire);
		    p1.add(connecter);
		    
		    
		    panel.add(p);
		    panel.add(p1);
		    add(panel);
		    
		    inscrire.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					(new fenetre()).inscritForm(client);
		            dispose(); 
				}});
		    
		    connecter.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					(new fenetre()).loginForm(client);
		            dispose(); 
				}});
		    setVisible(true);  
		}
		

//---------------------------------------------------------------------------------------		  
		public void inscritForm(UDPClient client) {
		    setTitle("INSCRIT_FORM");
		    setSize(600, 500);
		    setLocationRelativeTo(null);
		    setLayout(new FlowLayout());
		   
		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		    
		    JLabel l = new JLabel("Entrer vos informations :");
		    JPanel p = new JPanel();
		    p.add(l);
		    
		    
		    JPanel p1 = new JPanel();
		    JLabel usernameLabel = new JLabel("   LOGIN:");
		    JTextField usernameField = new JTextField(20);
		    p1.add(usernameLabel);
		    p1.add(usernameField);
		    
		    
		    JPanel p2 = new JPanel();
		    JLabel passwordLabel = new JLabel("PASSWORD:");
		    JPasswordField passwordField = new JPasswordField(20);
		    p2.add(passwordLabel);
		    p2.add(passwordField);
		    
		    

		    JPanel p3 = new JPanel();
		    JButton loginButton = new JButton("INSCRIRE");
		    p3.add(loginButton);
		    
		    loginButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					 String login = usernameField.getText();
					 char[] mdp = passwordField.getPassword();
		             String pwd = new String(mdp);
		             System.out.println(pwd);
		             String msg="inscrire:"+login+","+pwd;
		             client.send(msg);
		             String reponse = "vous êtes inscrit.";
					 JOptionPane.showMessageDialog(null, reponse);
					(new fenetre()).loginForm(client);
		            dispose(); 
		            
					
					 
				}});
		    
		    
		    JButton retourButton = new JButton("RETOUR");
		    
		    p3.add(retourButton);
		    
		    retourButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					(new fenetre()).first(client);
		            dispose(); 
		            
					
					 
				}});
		    panel.add(p);
		    panel.add(p1);
		    panel.add(p2);
		    panel.add(p3);
		    add(panel);
		    
		    
		    setVisible(true);
		  
		  } 

//-----------------------------------------------------------------------------------		 
		   public void loginForm(UDPClient client) {
			   	setTitle("LOGIN_FORM");
			    setSize(600, 500);
			    setLocationRelativeTo(null);
			    setLayout(new FlowLayout());
			    
			    JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			    
			    JLabel l = new JLabel("Saisir vos données :");
			    JPanel p = new JPanel();
			    p.add(l);
			    
			    JPanel p1 = new JPanel();
			   
			    JLabel usernameLabel = new JLabel("   LOGIN:");
			    JTextField usernameField = new JTextField(20);
			    p1.add(usernameLabel);
			    p1.add(usernameField);
			    
			    JPanel p2 = new JPanel();
			    
			    JLabel passwordLabel = new JLabel("PASSWORD:");
			    JPasswordField passwordField = new JPasswordField(20);
			    p2.add(passwordLabel);
			    p2.add(passwordField);
			    
			    
			    JPanel p3 = new JPanel();
			   
			    
			    JButton buttonValider = new JButton("CONNECTER");
			    p3.add(buttonValider);
			    
			    buttonValider.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
							String login = usernameField.getText();
							char[] mdp = passwordField.getPassword();
							String pwd = new String(mdp);
							String msg = "connecter:"+login+","+pwd;
							String rep=client.sendThenReceive(msg);
							String recept="";
							
							switch (rep) {
							case "true": {
								recept="vous êtes connecté !!";
								JOptionPane.showMessageDialog(null, recept);
								(new fenetre()).choisirAmi(client);
								dispose();
								break;
							}
							case "false": {
								recept="login ou password incorrect, réessayer !!";
								JOptionPane.showMessageDialog(null, recept);
								(new fenetre()).loginForm(client);
								dispose();
								break;
							}
							default:
								throw new IllegalArgumentException("Unexpected value: " + rep);
							}
							
							
							
						
						
					}
			    	
			    });
			    
			    JButton retourButton = new JButton("RETOUR");
			    p3.add(retourButton);
			    
			    retourButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						(new fenetre()).first(client);
			            dispose(); 
			            
						
						 
					}});
			    
			    panel.add(p);
			    panel.add(p1);
			    panel.add(p2);
			    panel.add(p3);
			    add(panel);
			    setVisible(true); 
		   }
		   
		   
		  
		   
//---------------------------------------------------------------------------------------		   

		   public List<Client> listClient(){
				List<Client> clients = new ArrayList<Client>();
				String url="jdbc:mysql://localhost:3307/chat?serverTimezone=UTC";
		        String userName="root";
		        String pwd="";
		        String requete="select * from client ";
		        try {
					Connection con= DriverManager.getConnection(url, userName, pwd);
					//System.out.println("CONNECTION SUCCES!!");
					PreparedStatement ps = con.prepareStatement(requete);
				    ResultSet rs = ps.executeQuery();
				    while (rs.next()) {
				    	Client client = new Client();
						client.setLogin(rs.getString("LOGIN"));
						client.setPassword(rs.getString("PASSWORD"));
						clients.add(client);
					}
					ps.close();
					con.close();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return clients;
			}
//----------------------------------------------------------------------------------		   
		   public List<String> listAmis(String login_clt){
				List<String> clients = new ArrayList<String>();
				String url="jdbc:mysql://localhost:3307/chat?serverTimezone=UTC";
		        String userName="root";
		        String pwd="";
		        String requete="select * from Amis where pseudo_clt='"+login_clt+"';";
		        try {
					Connection con= DriverManager.getConnection(url, userName, pwd);
					//System.out.println("CONNECTION SUCCES!!");
					PreparedStatement ps = con.prepareStatement(requete);
				    ResultSet rs = ps.executeQuery();
				    while (rs.next()) {
				    	String client ="";
						client=rs.getString("PSEUDO_AMI");
						clients.add(client);
					}
					ps.close();
					con.close();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return clients;
			}
//--------------------------------------------------------------------		   
		   public List<String> listGroup(String login_clt){
				List<String> clients = new ArrayList<String>();
				String url="jdbc:mysql://localhost:3307/chat?serverTimezone=UTC";
		        String userName="root";
		        String pwd="";
		        String requete="select * from groupe where admin='"+login_clt+"';";
		        try {
					Connection con= DriverManager.getConnection(url, userName, pwd);
					//System.out.println("CONNECTION SUCCES!!");
					PreparedStatement ps = con.prepareStatement(requete);
				    ResultSet rs = ps.executeQuery();
				    while (rs.next()) {
				    	String client ="";
						client=rs.getString("AMI");
						clients.add(client);
					}
					ps.close();
					con.close();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return clients;
			}
		         
//--------------------------------------------------------------		    
		   public void choisirAmi(UDPClient client) {
				
				
				setTitle("AMIS");
			    setSize(600, 500);
			    setLocationRelativeTo(null);
			    
			    JLabel l = new JLabel("Pages des amis :");
			    JPanel p3 = new JPanel();
			    p3.add(l);
			    
			    JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			    
			    JPanel p1 = new JPanel();
			    JPanel p2 = new JPanel();
			    JPanel p4 = new JPanel();
			    JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			    
			    List<Client> clients = new ArrayList<Client>();
				clients = this.listClient();
				String[] noms_client = new String[clients.size()] ;
				
				int i=0;
				for (Client clt : clients) {
					noms_client[i]=clt.getLogin();
					i++;
				}
				
					JComboBox comboboxClt = new JComboBox(noms_client);
					JLabel l1 = new JLabel("AJOUTER AMIS : ");
					
					
					
					String login_clt=client.sendThenReceive("listAmis");
					List<String> amis = new ArrayList<String>();
					amis = this.listAmis(login_clt); 
					String[] noms_amis = new String[amis.size()] ;
					int j=0;
					for (String clt : amis) {
						noms_amis[j]=clt;
						j++;
					} 
					
					JComboBox comboboxAmi = new JComboBox(noms_amis);
					JLabel l2 = new JLabel("ENVOYER UN MSG A : ");
					
					JComboBox retirer = new JComboBox(noms_amis);
					JLabel l4 = new JLabel("SELECTIONNE UN POUR RETIRER DE LA LISTE DES AMIS : ");
					
					JButton retourButton = new JButton("DECONNECTER");
					JButton groupButton = new JButton("GROUP");
					JLabel l3 = new JLabel("Cliquez ici pour passer à la page de groupe : ");
				    
					
					// ajouter combobox et labels au panneau
			        p1.add(l1);
			        p1.add(comboboxClt); 
			        p2.add(l2);
			        p2.add(comboboxAmi);
			        p4.add(l4);
			        p4.add(retirer);
			        p.add(l3);
			        p.add(groupButton);
			        p.add(retourButton);
			        
			        panel.add(p3);
			        panel.add(p1);
			        panel.add(p2);
			        panel.add(p4);
			        panel.add(p);
			        add(panel);
			        
			        comboboxClt.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	//UDPClient clt = new UDPClient();
			            	String nom = comboboxClt.getSelectedItem().toString(); 
			            	System.out.println(nom);
			            	String message = "amis:"+nom;
			            	client.send(message);
			            	JOptionPane.showMessageDialog(null, nom+" ajouté");
							(new fenetre()).choisirAmi(client);
							dispose();
			            }
			          });
			        
			        comboboxAmi.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String nom = comboboxAmi.getSelectedItem().toString(); 
			            	System.out.println(nom);
			            	(new fenetre()).chat(client,nom);
							dispose();
			            }
			          });
			        
			        retirer.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String nom = retirer.getSelectedItem().toString(); 
			            	String message = "deleteAmi:"+nom;
			            	client.send(message);
			            	(new fenetre()).choisirAmi(client);
							dispose();
			            }
			          });
			        
			        groupButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							(new fenetre()).groupe(client);
				            dispose(); 
				            
							
							 
						}});
				    
				    retourButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String message = "deconnecter:";
			            	client.send(message);
			            	JOptionPane.showMessageDialog(null," vous êtes deconnecté");
							(new fenetre()).first(client);
				            dispose(); 
				            
							
							 
						}});
			        
			         setVisible(true);
			}
		   
//-------------------------------------------------------------------------			
		   public void groupe(UDPClient client) {
			   setTitle("PAGE_GROUPE");
			    setSize(600, 500);
			    setLocationRelativeTo(null);
			    
			    JLabel l = new JLabel("La page des groupes :");
			    JPanel p4 = new JPanel();
			    p4.add(l);
			    
			    JPanel p1 = new JPanel();
			    JPanel p2 = new JPanel();
			    JPanel p3 = new JPanel();
			    //JPanel p5 = new JPanel();
			    
			    JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					
					
					String login_clt=client.sendThenReceive("listAmis");
					List<String> amis = new ArrayList<String>();
					amis = this.listAmis(login_clt); 
					String[] noms_amis = new String[amis.size()] ;
					int j=0;
					for (String clt : amis) {
						noms_amis[j]=clt;
						j++;
					} 
					
					JComboBox comboboxAmi = new JComboBox(noms_amis);
					JLabel l2 = new JLabel("AJOUTER AU GROUPE : ");
					
					List<String> group = new ArrayList<String>();
					group = this.listGroup(login_clt); 
					String[] noms_group = new String[group.size()] ;
					int k=0;
					for (String clt : group) {
						noms_group[k]=clt;
						k++;
					} 
					
					JComboBox comboboxGroup = new JComboBox(noms_group);
					JLabel l3 = new JLabel("LES MEMBRES DU GROUPE : ");
					
					JComboBox retirer = new JComboBox(noms_group);
					JLabel l4 = new JLabel("SELECTIONNE UN POUR RETIRER DE GROUP : ");
					
					JButton retourButton = new JButton("DECONNECTER");
					JButton chatButton = new JButton("CHATROOM");
				    
					JPanel p = new JPanel();
					// ajouter combobox et labels au panneau
			        p1.add(l2);
			        p1.add(comboboxAmi);
			        p2.add(l3);
			        p2.add(comboboxGroup);
			        p3.add(l4);
			        p3.add(retirer);
			        p.add(retourButton);
			        p.add(chatButton);
			        panel.add(p4);
			        panel.add(p1);
			        panel.add(p2);
			        panel.add(p3);
			        panel.add(p);
			        add(panel);
			        
			        comboboxAmi.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String nom = comboboxAmi.getSelectedItem().toString(); 
			            	System.out.println(nom);
			            	String message = "addToGroup:"+nom;
			            	client.send(message);
			            	(new fenetre()).groupe(client);
							dispose();
			            }
			          });
			        
			        retirer.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String nom = retirer.getSelectedItem().toString(); 
			            	String message = "deleteFromGrp:"+nom;
			            	client.send(message);
			            	(new fenetre()).groupe(client);
							dispose();
			            }
			          });
			        
			        chatButton.addActionListener(new ActionListener() {     
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	(new fenetre()).chatRoom(client);
							dispose();
			            }
			          });
			        
			        
				    
				    retourButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String message = "deconnecter:";
			            	client.send(message);
			            	JOptionPane.showMessageDialog(null," vous êtes deconnecté");
							(new fenetre()).first(client);
				            dispose(); 
				            
							
							 
						}});
			        
			         setVisible(true);
		   }
			
//----------------------------------------------------------------------------------------		   
		  
		   
			public void message(String msg, JTextArea chatArea, String nom) {
				chatArea.append(nom+" : "+msg + "\n");
			}
			
			
			JTextArea chatArea = new JTextArea();
			JTextArea chatArea1 = new JTextArea();
			static String ami="";
	///-----------------------------------------------------------------------
		   Runnable runnable = () -> {
			   label:
			   while(true) {
				try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			msgR = new String(packet.getData(), 0, packet.getLength());
			String[] tab = msgR.split(":");
			if(tab.length==2) {
			switch (tab[0]) {
			case "one": {
				message(tab[1], chatArea, ami);
				break;
			}
			case "groupe": {
				String[] info = tab[1].split(",");
				message(info[1], chatArea1, info[0]);
				break;
			}
			case "deconnect": {
				break;
			}
			case "retour": {
				System.out.println("vide");
				break label;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + tab[1]);
			}
			}else {
				break;
			}
			
			}catch (IOException e) {
			e.printStackTrace();
			break;
			}
			   }
			};
	
//----------------------------------------------------------------------------------------------
			 public void chat(UDPClient client, String nom) {
				  
				 
				 	Thread thread = new Thread(runnable);
	    			thread.start();
	    			ami=nom;
	    			
				 
				   	setTitle(nom);
			        setSize(600, 500);
			        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        JLabel l = new JLabel("Vous êtes dans le chat avec "+nom);
			        add(l);
			        
			        
			        //chatArea.setEditable(true);
			        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
			        chatArea.setPreferredSize(new Dimension(300, 50));
			        JScrollPane scrollPane = new JScrollPane(chatArea);
			        scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			        add(scrollPane, BorderLayout.CENTER);

			        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			        JTextField messageField = new JTextField();
			        messageField.setFont(new Font("Arial", Font.PLAIN, 16));
			        messageField.setPreferredSize(new Dimension(300, 25));
			        bottomPanel.add(messageField);
			        
			  
			        
			        JButton sendButton = new JButton("ENVOYER");
			        sendButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                String message = messageField.getText();
			                System.out.println(message);
			                String msgE = "chatAmiToAmi:"+message+","+nom;
			                messageField.setText("");
			             
			                 chatArea.append("Moi : "+message + "\n");
			                 System.out.println(msgE);
			                 client.send(msgE);
			                
			            }
			        });
			        
			        
			        	
			        bottomPanel.add(sendButton);
			        
			        JButton clearButton = new JButton("RETOUR");
			        clearButton.addActionListener(new ActionListener() {
			        	
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String rtr="retour:";
						     client.send(rtr);
			            	(new fenetre()).choisirAmi(client);
			            	dispose();
			            }
			        });
			        bottomPanel.add(clearButton);
			        
			        
			        add(bottomPanel, BorderLayout.SOUTH);
			        setVisible(true);
			    }
//----------------------------------------------------------------------------------------------			 
			 public void chatRoom(UDPClient client) {
				 
				 	Thread thread = new Thread(runnable);
	    			thread.start();
				 
				   	setTitle("ChatRoom");
			        setSize(600, 500);
			        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        JLabel l = new JLabel("Vous êtes dans le chatRoom ");
			        add(l);

			        //JTextArea chatArea = new JTextArea();
			        //chatArea.setEditable(true);
			        chatArea1.setFont(new Font("Arial", Font.PLAIN, 16));
			        chatArea1.setPreferredSize(new Dimension(300, 50));
			        JScrollPane scrollPane = new JScrollPane(chatArea1);
			        scrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			        add(scrollPane, BorderLayout.CENTER);

			        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			        JTextField messageField = new JTextField();
			        messageField.setFont(new Font("Arial", Font.PLAIN, 16));
			        messageField.setPreferredSize(new Dimension(300, 25));
			        bottomPanel.add(messageField);

			        
			        JButton sendButton = new JButton("ENVOYER");
			        sendButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                String message = messageField.getText();
			                
			                messageField.setText("");
			                String msgE = "sendToGroup:"+message;
			                chatArea1.append("Moi : "+message + "\n");
			                client.send(msgE);
			               
			                
			            }
			        });
			        
			        bottomPanel.add(sendButton);
			        
			        
			        JButton clearButton = new JButton("RETOUR");
			        clearButton.addActionListener(new ActionListener() {
			        	
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	String rtr="retour:";
						     client.send(rtr);
			            	(new fenetre()).groupe(client);
			            	dispose();
			            }
			        });
			        bottomPanel.add(clearButton);
			        

			        add(bottomPanel, BorderLayout.SOUTH);
			        setVisible(true);
			    }
			   
			
			
		  public static void main(String[] args) {
			   try {
				 socket = new DatagramSocket();
				InetAddress adr= InetAddress.getByName("localhost");
			   UDPClient client = new UDPClient(socket, adr);
			   
			   (new fenetre()).first(client);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		    
		    
		  }



 }
		    

