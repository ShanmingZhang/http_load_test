package jp.tools.load.test.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * HTTP要求によるサーバ負荷テスト実行クラス
 */
public class RunLoadTestClient {
	// 並列実行HTTP要求の数/回
	private static int number = 0;
	// 並列実行の時間間隔（秒単位）
	private static int interval = 0;
	// 並列実行回数
	private static int totalTime = 0;
	// サーバ負荷テストの開始間隔（秒単位）
	private static int startTime = 0;
	// 負荷テスト結果出力ファイル
	private final static String RESULT_FILE = "HttpRequestLoadTest.txt";
	// 負荷テスト用URL
	private final static String TEST_URL = "http://192.168.56.103:8080/http-loadtest-server/loadtest";
	// GETメソッドのパラメータ
	private final static String PRAM_URL = "ID=2";

	/*
	 * 負荷テスト実行する前、パラメタの案内提示と入力 パラメタ入力案内するので、入力チェックここで略
	 */

	public static void printOperationMessage() {

		try {

			System.out.print(" 毎回並列的に実行されるHTTP要求の数量 Number（0 < Number、整数）を入力してください: ");
			number = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();

			System.out.print(" 並列実行の時間間隔 I（0　< I、整数、秒単位）を入力してください: ");
			interval = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();

			System.out.print(" 並列実行総回数 Total（0　< Total, 整数）を入力してください: ");
			totalTime = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();

			System.out.print(" 負荷テストの開始間隔(秒単位) を入力してください: ");
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
		System.out.println("HTTP要求の数量/回:" + number);
		System.out.println("並列実行時間間隔/回:" + interval);
		System.out.println("並列実行総回数:" + totalTime);

		LoadTestClientWithHttpRequests loadTestClient = new LoadTestClientWithHttpRequests(number, interval, totalTime,
				startTime, TEST_URL, PRAM_URL);
		loadTestClient.perfromLoadTest();

		System.out.println(" # 結果整理出力中．．．");
		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultSet.outputResultSetIntoFile(RESULT_FILE);
		System.out.println(" # 結果整理出力完了！");

		ShowResultsChart.ShowChart("showResultChart.py");
	}

}
