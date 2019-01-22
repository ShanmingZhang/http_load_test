package jp.tools.load.test.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunLoadTestClient {
	private static int number = 0;
	private static int interval = 0;
	private static int totalTime = 0;
	private static int startTime = 0;

	private final static String TEST_URL = "http://192.168.56.103:8080/picolab/loadtest";
	private final static String PRAM_URL = "ID=2";

	public static void printOperationMessage() {

		try {
			System.out.print(" Input the number of parallel Httprequests per time (0 < number < 50) : ");
			number = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();
			System.out.println();

			System.out.print("Input the time interval ( < 10) between two HttpRequests :");
			interval = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();
			System.out.println();

			System.out.print(" Input the total time (T) of load test ( 0 < T < 100) : ");
			totalTime = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();
			System.out.println();

			System.out.print(" Input the start time after specied seconds ( 0 < sec < 10) : ");
			startTime = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		printOperationMessage();
		System.out.println(number);
		System.out.println(interval);
		System.out.println(totalTime);
		System.out.println(startTime);

		LoadTestClientWithHttpRequests loadTestClient = new LoadTestClientWithHttpRequests(number, interval, totalTime,
				startTime, TEST_URL, PRAM_URL);
		loadTestClient.perfromLoadTest();
		
		System.out.println(" # 結果整理出力中．．．");
		try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet.outputResultSetIntoFile();
		System.out.println(" # 結果整理出力完了！");
	}

}
