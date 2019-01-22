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

public class ResultSet {
	public static int totalAmountOfRequest = 0;
	public static int successfulRequest = 0;
	public static int failureRequst = 0;

	public static Map<String, HttpRequestStatusDetail> reSet = new HashMap<String, HttpRequestStatusDetail>();

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

	public static synchronized void addResultSet(String threadID, HttpRequestStatusDetail status) {
		++ResultSet.totalAmountOfRequest;
		if (status.getFlag() == true) {
			++ResultSet.successfulRequest;
		} else {
			++ResultSet.failureRequst;
		}

		ResultSet.reSet.put(threadID, status);
	}

	public static void printResultSet() {

		for (Map.Entry<String, HttpRequestStatusDetail> entry : ResultSet.reSet.entrySet()) {
			System.out.println("Key = " + entry.getKey() + " end time = "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(entry.getValue().getEndTime()));
		}
	}

	public static void outputResultSetIntoFile() {

		BufferedWriter wrt = null;

		try {
			wrt = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File("HttpRequestLoadTest.txt")), "UTF-8"));

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (wrt != null) {
				try {
					wrt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//ShowResultChart.showChart(batchNumberArray, totalAmountArray, successfulAmountArray, failureAmountArray);
	}
}
