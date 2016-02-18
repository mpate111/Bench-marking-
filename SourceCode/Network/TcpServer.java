

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpServer implements Runnable{
	
	int portnumber ;

	
	public TcpServer(int portnumber ){
		
		
		this.portnumber = portnumber;
		// Starts the new thread 
		new Thread(this).start();
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		// To get the ip address of the server
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println("The ip address of the server is-:" + ip);
		
		
		int port = 7777;
		int port1 = 8888;
		int port2 = 9999;
		
		
		new TcpServer(port);
		new TcpServer(port1);
		new TcpServer(port2);
			
			
		}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int[] bufferSize = {1,1024,65536};
		
		try {
			ServerSocket server = new ServerSocket(portnumber);
			
			@SuppressWarnings("resource")
			// Create a server socket
			Socket serverSocket = new Socket();
			// Listens to the incoming request
			serverSocket = server.accept();

			
			
				
			
			ObjectInputStream obj_in = new ObjectInputStream(serverSocket.getInputStream());
			ObjectOutputStream obj_out = new ObjectOutputStream(serverSocket.getOutputStream());
//			while(true){
			for (int i = 0; i < 3; i++) {
			// To recieve the data from the client
			Object obj = obj_in.readObject();
			System.out.println("Data Received");
			// To send the data back to client of same size
			obj_out.writeObject(obj);
//			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
