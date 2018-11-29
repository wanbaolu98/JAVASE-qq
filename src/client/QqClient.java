package client;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import Connect.Message;
import user.User;

public class QqClient 
{
	public Socket client ;
	
	
	public QqClient() {
		try {
			this.client = new Socket("localhost",8888);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public User oneMessage(User u) 
	{ 
		User fh = null ;
			try {
				ObjectOutputStream outClient = new ObjectOutputStream(client.getOutputStream());
				outClient.writeObject(u);
				
				ObjectInputStream inClient = new ObjectInputStream(client.getInputStream());
				fh = (User) inClient.readObject();
//				System.out.println(fh.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fh;
			
	}
	
	

}
