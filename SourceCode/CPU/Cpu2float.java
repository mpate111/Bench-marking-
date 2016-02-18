



import java.io.*;
import java.util.*;
import java.util.concurrent.*;
/*import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
*/
public class Cpu2float implements Callable<ArrayList<Long>> {


			long sTime;
			Cpu2float(long startTime)
			{
				this.sTime = startTime;
			}
			//call method implementing callable
			public ArrayList<Long> call() {
				// TODO Auto-generated method stub
				double a = 40,b = 200,c = 20 ;
				long j =0;
				long fTime = sTime; 
				long TimeNow = sTime;
				//boolean flag = true;
				ArrayList<Long> list = new ArrayList<Long>();
				//  multiplication operation
				while(TimeNow<=(fTime+600000))
				{
					a = a+10.89;
					b = b+j;
					a =a+b;
					b =b+b;
					c = a*b;
					b = 70.88+c;
					c = a+10.09;
					a = b+c;
					a = a+a;
					a = 100.8*c;
					b= 200.49+c;
					b = c+j;
					c = c+b;
					c = a+b;
					a = b+c;
					a =a+j;
					c = c*a;
					b= a+c;
					b= b+c;
					j++;
					TimeNow = System.currentTimeMillis();
														
					if(TimeNow>=(sTime+1000))
					{
						
						list.add(j*22);
						sTime = TimeNow;
						j =0;
					}				
					
				}
				return list;
			}
			
			public static void writetoFile (String filename, long[]array) throws IOException{
				  BufferedWriter oWriter = null;
				  oWriter = new BufferedWriter(new FileWriter(filename));
				  for (int i = 0; i < array.length; i++) {
				    oWriter.write(Long.toString(array[i]));
				    oWriter.newLine();
				  }
				  oWriter.flush();  
				  oWriter.close();  
				}
			
			
			@SuppressWarnings("unchecked")
			public static void main(String args[]) throws IOException, InterruptedException, ExecutionException{
				System.out.println("Enter the number of threads you wanna start -:");
				Scanner sc = new Scanner(System.in);
				int threads = sc.nextInt();
				ArrayList<Long> tolist = new ArrayList<Long>();
				ArrayList<ArrayList<Long>> ListLists = new ArrayList<ArrayList<Long>>();
				long[] sampleList = new long[600];
				int k=0,j=0;
				// Executor service used to create a pool of threads				
				ExecutorService execPool = Executors.newFixedThreadPool(4);
				Future<ArrayList<Long>>[] future = new Future[threads];				
				long startTime = System.currentTimeMillis();
				System.out.println(startTime);
				
				// To create a thread pool
				while(j<threads)
				{
					future[j] = execPool.submit(new Cpu2float(startTime));
					j++;
				}			
				while(k<threads)
				{
					tolist = future[k].get();
					for(int n=0;n<tolist.size();n++)
					{
						sampleList[n] = sampleList[n]+tolist.get(n);
					}
					k++;
				}		
				// Write the sample values in the text file.
				writetoFile("cpuSamples.txt",sampleList);
				System.out.println("end");						
			}
			

	
	
}
