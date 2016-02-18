

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient implements Runnable {
	static int threads;
	int size;
	static String mike;
	int portnumber;
	static String sen;
	
	public TcpClient(int portnumber, String size ){
		this.portnumber = portnumber;
		this.sen = size;
		// To start the new thread 
		new Thread(this).start();
	}
	
	public static void main(String args[]){
		input();
		
		if (threads == 1){
		new TcpClient(7777,sen);
		}
		
		else if (threads ==2){
			new TcpClient(8888,sen);
			new TcpClient(9999,sen);
		}
		
		else{
			System.out.println("Enter the value of threads correctly-:");
		}
		
	}
	
	public static void input(){
		System.out.println("Enter the number of threads u wanna start");
		Scanner sc = new Scanner(System.in);
		threads = sc.nextInt();
		
		System.out.println("Enter the ip address of the server-: ");
		Scanner sc1 = new Scanner(System.in);
		sen = sc1.nextLine();
		
		System.out.println("------------------ TCP throughput and latency for threads-: "+threads+"-----------");
		
	}
	
	// To create the string of size specified in the argument
	public static String stringSize(int stringSize){
		int i = 0;
		StringBuilder sb = new StringBuilder(); 
		String sc;
		while(i<= stringSize){
			sb.append('a');
			i++;
		}
		sc = sb.toString();
		return sc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int[] arraySize = {1,1024,65536};
		
		try {
			// Create socket connection to specefied ip adress and port number 
			Socket client = new Socket(sen,portnumber);
			ObjectOutputStream obj_out = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream obj_in = new ObjectInputStream(client.getInputStream());
			
			for (int i = 0; i < arraySize.length; i++) {
				
			
			String inputString = stringSize(arraySize[i]);
			byte[ ] sizeass = inputString.getBytes();
			long starttime = System.nanoTime();
			
			// To send data to the server
			obj_out.writeObject(inputString);

			// To receive data from server(ack)
			Object obj = obj_in.readObject(); ;
			long stopTime = System.nanoTime();
			long total = stopTime-starttime;

			System.out.printf("The throughput with buffer size %4d is %6.4f MBps\n",arraySize[i],(15258.78*arraySize[i])/total);
			System.out.printf("The latency with buffer size %4d is %6.2f ms\n",arraySize[i],(total*1000/(15258.78*arraySize[i])));
			}
		} catch (IOException e) {
			// TOttDO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
