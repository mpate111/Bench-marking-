

import java.io.IOException;
import java.net.*;

public class serverUDP implements Runnable {
	int portnumber;
	
	
	public serverUDP(int portnumber){
		this.portnumber = portnumber;
		new Thread(this).start();
	}
	

	public static void main( String args[]) throws UnknownHostException{
		
		InetAddress ip1= InetAddress.getLocalHost();
		System.out.println("The Ip address of the server is -:"+ip1);
		
		int port1 = 5555;
		int port2 = 6666;
		int port3 = 7777;
		
		new serverUDP(port1);
		new serverUDP(port2);
		new serverUDP(port3);
				
	}
	
	

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			DatagramSocket socket;
			socket = new DatagramSocket(portnumber);
			// byte array of buffer size to get the data from client
			byte[] Buffer = new byte[65537];
			DatagramPacket rPacket = new DatagramPacket(Buffer,Buffer.length);
			
			
			

			
			while(true)
			{
				socket.receive(rPacket);
				System.out.println("Data received");
				// Data received from the client
				byte[] receive = rPacket.getData();

				String s = new String(receive,0,rPacket.getLength());

				// Send the same size of data to the client
				DatagramPacket reply = new DatagramPacket(s.getBytes(),s.getBytes().length,rPacket.getAddress(),rPacket.getPort());
				// To send data to client back
				socket.send(reply);

			}
									
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
