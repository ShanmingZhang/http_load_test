package jp.tools.load.test.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 負荷テスト結果を可視化、JavaからPythonプログラムを呼び出し、実行する機能クラス
 * 統計結果画面が外部のPython実行処理により表示される
 */
public class ShowResultsChart {
	/*
	 * JVM外部のPythonモジュールを呼びたし、実行する
	 */
	public static void ShowChart(String dataSourceFile) {
		// 実行プログラムパラメータ初期化
		String exe = "python";
		String[] cmdArr = new String[] { exe, dataSourceFile };

		// OSのプロセスを作成
		Process proc;
		try {
			// プロセスにより、外部のPythonモジュールを実行
			proc = Runtime.getRuntime().exec(cmdArr);
			// 外部Pythonモジュール実行結果をプロセスから取得、出力
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			// プロセス間の流、プロセスをクローズ
			in.close();
			proc.waitFor();
		} catch (InterruptedException | IOException e) {
			System.out.println("↓↓↓↓↓↓↓↓↓ 負荷テスト結果可視化モジュール実行異常例外が発生 1 ↓↓↓↓↓↓↓↓↓↓↓");
			e.printStackTrace();
			System.out.println("↑↑↑↑↑↑↑↑↑ 負荷テスト結果可視化モジュール実行異常例外が発生 1 ↑↑↑↑↑↑↑↑↑↑↑");
		}

	}
}
