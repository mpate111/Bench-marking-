

import java.io.*;
import java.util.*;



public class diskPerformance implements Runnable {
	
	static String readFileName= randomString(0);
	static int threads;
	
	
	
	
	public static void main(String args []) throws IOException, InterruptedException{
	newFile();
	System.out.println("Enter the number of threads you want to create-:");
	Scanner sc = new Scanner(System.in);
	threads = sc.nextInt();
	
	Thread[] threadlist = new Thread[threads];
	
	for (int i = 0; i < threadlist.length; i++) {
		threadlist[i] = new Thread(new diskPerformance());
		threadlist[i].start();
	}
	
	
	for (int i = 0; i < threadlist.length; i++) {
		threadlist[i].join();
	}

	}
	

	
	//@ To create a 10MB file for reading the data
	public static void newFile() throws IOException{
		File newFile = new File(readFileName+".txt");
		newFile.createNewFile();
		String writeData = randomString((2*1024*1024)+(8*1024));
		BufferedWriter bf = new BufferedWriter(new FileWriter(newFile));
		// 10MB file is created for reading sequentially and randomly
		for (int i = 0; i < 5; i++) {
		bf.write(writeData);
		}
//		System.out.println("The size of the file is"+(newFile.length()/(1024*1024))+ "MB");
		
	}
	
	
	
	//Create a random string and return it for writing the file name and 
	//writing data in the file created for sequential write and random
	//write
	
	public static synchronized String randomString(double size)
	{
		
		StringBuilder sb= new StringBuilder();	
		for (char c = 'a';  c <= 'z'; c++) {
			sb.append(c);
		}
		
		char[] forwriting;
		forwriting = sb.toString().toCharArray();
		Random random = new Random();
		StringBuilder sb1 = new StringBuilder();
		if(size == 0){
			for (int i = 0; i <10 ; i++) {
				char ch1 = forwriting[random.nextInt(forwriting.length)];
				sb1.append(ch1);
		}
		}
		else{
		for (int i = 0; i <size ; i++) {
			char ch1 = forwriting[random.nextInt(forwriting.length)];
			sb1.append(ch1);
		}
		}
		return sb1.toString();
	}
	
	
	// For writing sequentially and measure the time with the data size
	//1byte 1KB and 1MB
	
	public static synchronized void sequentialWrite() throws IOException
	{

		int[] stringSize = {1,1024,1024*1024};
		String writeData;

		File newFile = new File(randomString(0)+".txt");
		for (int i = 0; i < stringSize.length; i++) {
			
			newFile.createNewFile();	
		writeData = randomString(stringSize[i]);
			
		FileWriter fw = new FileWriter(newFile);
		BufferedWriter bf = new BufferedWriter(fw);
		long startTime = System.nanoTime();
		if(stringSize[i] == 1){
			for (int j = 0; j < 10*1024*1024; j++) {
				bf.write(writeData);
			}
		}
		
		else if(stringSize[i]==1024){
			for (int j2 = 0; j2 < 10*1024; j2++) {
					bf.write(writeData);
				}
		}
		else{
			for (int j = 0; j < 10; j++) {
				
			
		bf.write(writeData);
			}
		}
		bf.close();
		long stopTime = System.nanoTime();
		long total = stopTime - startTime;
//		System.out.printf("%4dbytes: %6.2fmsec%n", stringSize[i], (total / 1000000.0));
		System.out.printf("The throughput of the sequential write for buffersize %4dbytes is %6.2f MBps%n", stringSize[i],(10000.0/(total/1000000.0)));
		System.out.printf("The latency of the sequential write for buffersize %4dbytes is %6.2f ms%n", stringSize[i],((total/10000000.0)));
		}
		
	}
	
	// To read sequentially from the file that is generated at the start.
	// It will read the data with the buffer size of 1Byte 1 KB and 1 MB
	// Calculate the throughput and latency and display on the console
	
	public static synchronized void sequentialRead() throws FileNotFoundException
	{
		double storeTime[] = new double[3];
		int time = 0;
		
		File myfile = new File(readFileName+".txt");

		int[ ] bufferArray = {1,1024,1024*1024};
		int bufferSize;
		System.out.println("-----------------------Sequential Read------------------------");
		for (int i = 0; i < bufferArray.length; i++) {
			bufferSize = bufferArray[i];
		
		try(FileInputStream fin = new FileInputStream(myfile)){
			long startTime = System.nanoTime();
			byte[] byteSize = new byte[bufferSize];
			while((fin.read(byteSize)) != -1){
				
			}
			long stopTimer = System.nanoTime();
			long total = stopTimer-startTime;

			System.out.printf("The throughput of the sequential read for buffersize %4dbytes is %6.2f MBps%n", bufferArray[i],(10000.0/(total/1000000.0)));
			System.out.printf("The latency of the sequential write for buffersize %4dbytes is %6.2f ms%n", bufferArray[i],((total/10000000.0)));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}
	
	// To randomly write the data on the disk 
	// Write the data with buffersize of varying size of 1 Byte 1KB and 1MB
	
	public static synchronized void randomWrite()
	{

		String stringSize ;
		// Array of 1byte,1KB and 1MB 
		int[] stringWrite = {1,1024,1024*1024};

		String path = randomString(0)+".txt";
		System.out.println("-----------------------Random Write------------------------");
		for (int i = 0; i < stringWrite.length; i++) {
			//stores randomly generated string ov varying size
			stringSize = randomString(stringWrite[i]);
		try(RandomAccessFile raf = new RandomAccessFile(path, "rw")){
			long startTime = System.nanoTime();
			if(stringWrite[i] == 1){
				for (int j = 0; j < 1024*1024*10; j++) {
					raf.writeChars(stringSize);
				}
			}
			else if(stringWrite[i] == 1024){
				for (int j = 0; j < 1024*10; j++) {
					raf.writeChars(stringSize);
				}
			}
			else{
				for (int j = 0; j < 10; j++) {
					raf.writeChars(stringSize);
				}
			}

			long stopTime = System.nanoTime();
			long total = stopTime-startTime;
			System.out.printf("The throughput of the random write for buffersize %4dbytes is %6.2f MBps%n", stringWrite[i],(10000.0/(total/1000000.0)));
			System.out.printf("The latency of the sequential write for buffersize %4dbytes is %6.2f ms%n", stringWrite[i],((total/10000000.0)));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		 
	}
	
	// To randomly read the data from the file created 
	// Will read the data with varying buffer size 1 Byte, 1KB and 1MB
	
	public static synchronized void randomRead()
	{

		String stringSize ;
		// Array of 1byte,1KB and 1MB 
		int[] stringWrite = {1,1024,1024*1024};

		System.out.println("-----------------------Random Read------------------------");
		for (int i = 0; i < stringWrite.length; i++) {
			//stores randomly generated string of varying size of 1Byte 1KB and 1MB
			stringSize = randomString(stringWrite[i]);
		try(RandomAccessFile raf = new RandomAccessFile(readFileName+".txt", "rw")){
			long startTime = System.nanoTime();
			raf.seek(0);
			raf.readLine();
			long stopTime = System.nanoTime();
			long total = stopTime-startTime;
			System.out.printf("The throughput of the random read for buffersize %4dbytes is %6.2f MBps%n", stringWrite[i],(10000.0/(total/1000000.0)));
			System.out.printf("The latency of the sequential write for buffersize %4dbytes is %6.2f ms%n", stringWrite[i],(total/10000000.0));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		 
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sequentialWrite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sequentialRead();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		randomWrite();
		randomRead();
		
	}
	

}
