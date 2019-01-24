package jp.tools.load.test.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShowResultsChart {
	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void ShowChar(String dataSourceFile) {
		String exe = "python";
		// String command = "D:\\calculator_simple.py";
		System.out.println(System.getProperty("user.dir"));
		String[] cmdArr = new String[] { exe, dataSourceFile };
		Process proc;
		try {
			proc = Runtime.getRuntime().exec(cmdArr);// 执行py文件
			// 用输入输出流来截取结果
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
