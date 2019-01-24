package jp.tools.load.test.httpserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * 負荷テストの結果を統計、出力するため、実行されたHTTP要求の詳細情報を保存するクラス
 */
public class ResultSet {

	// 負荷テストの開始から終了まで実行されたHTTP要求の総数
	public static int totalAmountOfRequest = 0;
	// 負荷テストの開始から終了まで正常（無事終了）HTTP要求の数
	public static int successfulRequest = 0;
	// 負荷テストの開始から終了まで異常例外発生HTTP要求の数
	public static int failureRequst = 0;

	/*
	 * 各HTTP要求詳細情報を記録 String: HTTP要求のID(スレッドID) HttpRequestStatusDetail:
	 * HTTP要求の詳細
	 */
	public static Map<String, HttpRequestStatusDetail> reSet = new HashMap<String, HttpRequestStatusDetail>();

	/*
	 * 実行結果を含め、HTTP要求の詳細情報を格納 同時に 格納されたHTTP要求数を更新 正常、異常HTTP要求を統計
	 */
	public static synchronized void addResultSet(String threadID, HttpRequestStatusDetail status) {
		// 格納されたHTTP要求数を更新
		++ResultSet.totalAmountOfRequest;
		// 正常、異常HTTP要求を統計
		if (status.getFlag() == true) {
			++ResultSet.successfulRequest;
		} else {
			++ResultSet.failureRequst;
		}
		// HTTP要求の詳細情報を登録
		ResultSet.reSet.put(threadID, status);
	}

	/*
	 * 結果出力用メソッド
	 */
	public static void printResultSet() {
		for (Map.Entry<String, HttpRequestStatusDetail> entry : ResultSet.reSet.entrySet()) {
			System.out.println("Key = " + entry.getKey() + " end time = "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(entry.getValue().getEndTime()));
		}
	}

	/*
	 * 結果出力をHttpRequestLoadTestファイルに出力
	 */
	public static void outputResultSetIntoFile(String outputFile) {

		BufferedWriter wrt = null;

		try {
			wrt = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFile)), "UTF-8"));

			wrt.write("--------  LoadTest result of HttpRequests  -------- " + "\r\n");

			wrt.write("TotalHttpResults=" + ResultSet.totalAmountOfRequest + "\r\n");

			wrt.write("SuccessfulRequest=" + ResultSet.successfulRequest + "\r\n");

			wrt.write("FailureRequst=" + ResultSet.failureRequst + "\r\n");

			wrt.write("-------- -------- -------- -------- -------- -------- " + "\r\n");

			wrt.write("----" + "\r\n");
			wrt.write("##### Details of httpReqeusts #####" + "\r\n");

			String lineStr = null;

			for (Map.Entry<String, HttpRequestStatusDetail> entry : ResultSet.reSet.entrySet()) {

				lineStr = "BatchNumber=" + entry.getValue().getBatchNumber() + ";RequestNo=" + entry.getValue().getId()
						+ ";Flag=" + entry.getValue().getFlag() + ";StartTiming(second)="
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(entry.getValue().getStartTime())
						+ ";StartTiming(millisecond)=" + entry.getValue().getStartTime() + ";EndTiming(second)="
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(entry.getValue().getEndTime())
						+ ";EndTiming(millisecond)=" + entry.getValue().getEndTime() + ";Delay(second)="
						+ Duration.between(new Date(entry.getValue().getStartTime()).toInstant(),
								new Date(entry.getValue().getEndTime()).toInstant())
						+ ";Delay(millisecond)=" + entry.getValue().getDelayTime();

				wrt.write(lineStr + "\r\n");
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			System.out.println("↓↓↓↓↓↓↓↓↓ 負荷テスト結果出力実行異常例外発生 1 ↓↓↓↓↓↓↓↓↓↓↓");
			e.printStackTrace();
			System.out.println("↑↑↑↑↑↑↑↑↑ 負荷テスト結果出力実行異常例外発生 1 ↑↑↑↑↑↑↑↑↑↑↑");
		} catch (IOException e) {
			System.out.println("↓↓↓↓↓↓↓↓↓ 負荷テスト結果出力実行異常例外発生 2 ↓↓↓↓↓↓↓↓↓↓↓");
			e.printStackTrace();
			System.out.println("↑↑↑↑↑↑↑↑↑ 負荷テスト結果出力実行異常例外発生 2 ↑↑↑↑↑↑↑↑↑↑↑");
		} finally {
			// ファイル書き込み用パイプをクローズ
			if (wrt != null) {
				try {
					wrt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static synchronized int getTotalAmountOfRequest() {
		return totalAmountOfRequest;
	}

	public static synchronized void setTotalAmountOfRequest(int totalAmountOfRequest) {
		ResultSet.totalAmountOfRequest = totalAmountOfRequest;
	}

	public static synchronized int getSuccessfulRequest() {
		return successfulRequest;
	}

	public static synchronized void setSuccessfulRequest(int successfulRequest) {
		ResultSet.successfulRequest = successfulRequest;
	}

	public static synchronized int getFailureRequst() {
		return failureRequst;
	}

	public static synchronized void setFailureRequst(int failureRequst) {
		ResultSet.failureRequst = failureRequst;
	}

	public static Map<String, HttpRequestStatusDetail> getReSet() {
		return reSet;
	}

	public static void setReSet(Map<String, HttpRequestStatusDetail> reSet) {
		ResultSet.reSet = reSet;
	}
}
