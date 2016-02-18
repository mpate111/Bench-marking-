


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
public class Cpu2 implements Callable<ArrayList<Long>> {


			long sTime;
			Cpu2(long startTime)
			{
				this.sTime = startTime;
			}
			//call method implementing callable
			public ArrayList<Long> call() {
				// TODO Auto-generated method stub
				long j = 0;
				long a = 5,b = 10;
				long c = 0,e =0, f = 0,x= 89, y= 92, z =88;
				long fTime = sTime; 
				long TimeNow = sTime;
				//boolean flag = true;
				ArrayList<Long> list = new ArrayList<Long>();
				//  multiplication operation
				while(TimeNow<=(fTime+600000))
				{
					a = a+10;
					a = x+y;
					b = 90+17;
					c = x*y;
					e= 100+b;
					f = 10+90;
					a = a+ 90*89;
					a = 666+88;
					f = 12+x;
					e = 99*b;
					a = 10000*12;
					b = 78*+40;
					c= 12*70;
					a = a+b;
					b = a+50;
					c = 9000*1000;
					a = x*y;
					b = 10+f;
					y = a*b;
					x =c+e;
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
				while(j<threads)
				{
					future[j] = execPool.submit(new Cpu2(startTime));
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
				
				writetoFile("cpuSamples.txt",sampleList);
				System.out.println("end");						
			}
			

	
	
}
