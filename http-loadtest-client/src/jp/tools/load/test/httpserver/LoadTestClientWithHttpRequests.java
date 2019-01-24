package jp.tools.load.test.httpserver;

/*
 * サーバ負荷テスト用のHTTP並列要求クラス
 * 指定されたパラメタによる負荷テストを実行
 */
public class LoadTestClientWithHttpRequests {

	// 並列実行HTTP要求の数/回
	private int number = 0;
	// 並列実行の時間間隔（秒単位）
	private int interval = 0;
	// 並列実行回数
	private int totalTime = 0;
	// サーバ負荷テストの開始間隔（秒単位）
	private int startTime = 0;

	// サーバ側負荷テスト用のサービスアクセスのURL
	private String serverURL = "";

	/*
	 * サーバ負荷テストクラスを生成、関連属性を設定
	 */
	public LoadTestClientWithHttpRequests(int number, int interval, int totalTime, int startTime, String serverURL,
			String param) {

		this.number = number;
		this.interval = interval;
		this.totalTime = totalTime;
		this.startTime = startTime;
		this.serverURL = serverURL + "?" + param;
	}

	/*
	 * 指定されたパラメタにより並列HTTP要求を実行し、サーバ負荷テストを実施
	 */
	public void perfromLoadTest() {
		try {
			System.out.println(startTime + " 秒あと、負荷テストを開始します。少々待ちください。 ");
			Thread.sleep((this.startTime) * 1000);

			/*
			 * 指定された並列実行回数により並列HTTP要求を実行
			 */
			for (int index = 0; index < totalTime; index++) {
				/*
				 * 指定された並列実行HTTP要求の数/回により並列のHTTP要求を生成、実行
				 */
				for (int num = 0; num < number; num++) {
					// HTTP要求を生成
					HttpRequest httpReq = new HttpRequest(serverURL, index + 1);
					// HTTP要求を実行
					new Thread(httpReq, new Integer((num + 1 + (index * this.number))).toString()).start();
					// スレッドのID情報出力
					System.out.println(Thread.currentThread().getName() + ":" + (num + 1));
				}
				// 指定された並列実行の時間間隔（秒単位）により次回並列HTTP要求の実行を待ち
				Thread.sleep((this.interval) * 1000);
			}

		} catch (InterruptedException e) {
			/*
			 * HTTP要求に関する異常例外の詳細出力、
			 * ネットワーク輻輳を発生、サーバ側負荷が重いなどの情報により異常例外発生する際この詳細を出力
			 */
			System.out.println("↓↓↓↓↓↓↓↓↓ サーバ負荷テスト実行異常例外発生 ↓↓↓↓↓↓↓↓↓↓↓");
			e.printStackTrace();
			System.out.println("↑↑↑↑↑↑↑↑↑ サーバ負荷テスト実行異常例外発生 ↑↑↑↑↑↑↑↑↑↑↑");
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

}
