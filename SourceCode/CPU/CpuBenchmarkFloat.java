

import java.util.Scanner;

public class CpuBenchmarkFloat implements Runnable {
	
	long t_iter;
	Thread thread;
	CpuBenchmarkFloat(long t_iter) throws InterruptedException
	{
		this.t_iter= t_iter;
		//thread.start();
		//Thread.currentThread().join();
		
	}
	
	public static void main(String args[]) throws InterruptedException
	{
		int k=0,j=0;		 
		float IOPS = 0;
		float GIOPS =0;
		System.out.println("Enter the number of threads");
		Scanner scanner = new Scanner(System.in);
		int threads = scanner.nextInt();		
		System.out.println("Enter opreations");
		Scanner scanner1 = new Scanner(System.in);
		long operations = scanner1.nextLong();		
		Thread[] threadArray = new Thread[threads];
		long sTime = System.currentTimeMillis();
		System.out.println(sTime);		
		while(k<threads)
		{
			CpuBenchmark bm = new CpuBenchmark(operations);
			threadArray[k] = new Thread(bm);
			threadArray[k].start();
			k++;
		}

		while(j<threads)
		{
			threadArray[j].join();;
			j++;
		}
		
		long total = System.currentTimeMillis() - sTime;
		System.out.println("Total time taken"+ total);		
	    IOPS = operations*20*1000*threads/total;
	    GIOPS = IOPS/1000000000;
	    System.out.println("FLOPS : " + IOPS);
	    System.out.println("GFLOPS : " + GIOPS);
	    scanner.close();
	    scanner1.close();
	}
	
	public void run() {
		
		
		double a = 40,b = 200,c = 20 ;
		long j ;
		for(j =0; j<t_iter; j++)
		{
			//System.out.println(Thread.currentThread().getName());
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
			
		}
		//System.out.println(j);
		
	}
}
