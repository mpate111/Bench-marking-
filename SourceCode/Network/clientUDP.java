
import java.io.IOException;
import java.net.*;
import java.util.*;

public class clientUDP implements Runnable {

	int portnumber ;
	static byte[] ipAdd ;
	static String  ipAddress;
	
	public clientUDP(int portnumber){
		this.portnumber = portnumber;
		new Thread(this).start();
	}
	
	public static void main(String args[]){
		System.out.println("Enter the number of threads you wanna start-:");
		Scanner sc =new Scanner(System.in);
		int threads = sc.nextInt();
		
		System.out.println("Enter the ip address of the server-: ");
		Scanner sc1 = new Scanner(System.in);
		ipAddress = sc1.nextLine();
		ipAdd = ipAddress.getBytes();
		
		System.out.println("------------------ TCP throughput and latency for threads-: "+threads+"-----------");
		
		if (threads == 1){
			new clientUDP(5555);
		}
		
		if (threads == 2){
			new clientUDP(7777);
			new clientUDP(6666);
		}
		
		
		
	}
	
	// To create the string of the specified size given as the argument
	public String createString(int size){
		int i = 0;
		byte[] sample = new byte[size]; 
		StringBuilder sb = new StringBuilder();
		while(i<size){
			sb.append('c');
			i++;
		}
		String sc = sb.toString();
		return sc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		DatagramSocket client = null;
		try {
			
			byte[] send ;
			client = new DatagramSocket();
			InetAddress ip1 = InetAddress.getByName(ipAddress); 
			
			
			int[] bufferSize = {1,1024,63535};
			for (int i = 0; i < bufferSize.length; i++) {
				
			String sendString = createString(bufferSize[i]); 
			send = sendString.getBytes();
			long startTime = System.nanoTime();
			
			// Create a data gram packet with ip and portnumber as argument in the constructor
			DatagramPacket sPacket = new DatagramPacket(send,send.length,ip1,portnumber);
			client.send(sPacket);			
			
			//receive buffer the message
			byte[] receiveBuffer = new byte[65536];
			DatagramPacket getReply = new DatagramPacket(receiveBuffer,receiveBuffer.length);
			
			// Get the data from the server
			byte[] data= getReply.getData();

			String data1 = data.toString(); 
			long stopTime = System.nanoTime();
			long total = stopTime-startTime;
			
			System.out.printf("The throughput of size %4d is %6.4f MBps\n",bufferSize[i],(15258.78*bufferSize[i])/total);
			System.out.printf("The latency of size %4d is %6.4f ms\n",bufferSize[i],(total*1000/(15258.78*bufferSize[i])));
			
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
