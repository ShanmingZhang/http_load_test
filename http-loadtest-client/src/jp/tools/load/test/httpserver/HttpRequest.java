package jp.tools.load.test.httpserver;

import java.net.URL;
import java.net.URLConnection;

/*
 * 一つのHTTP要求は一つのスレッドを起動し、実行する。
 * HTTPクラスはスレッドインタフェースRunnableを継承し、Runメソッドにより要求を実現する。
 */
public class HttpRequest implements Runnable {

	/*
	 * HTTPリクエストの属性定義
	 */

	// HTTP要求の所属並列発出番号（バッチ番号）
	private int batchNumber = 0;
	// HTTP要求の宛先アクセスURL
	private String url = null;
	// HTTP要求に関する詳細情報
	private HttpRequestStatusDetail detail = null;

	/*
	 * HTTT要求を生成し、属性情報を初期化
	 */
	public HttpRequest(String url, int batchNumber) {
		this.batchNumber = batchNumber;
		this.url = url;
		detail = new HttpRequestStatusDetail();
		detail.setBatchNumber(this.batchNumber);
	}

	/*
	 * 
	 */
	public boolean sendGet() {
		try {
			// URLを作成、初期化
			URL realUrl = new URL(this.url);
			// URLコネクションを作成
			URLConnection connection = realUrl.openConnection();
			// 通常のHTTP要求プロトコルのパラメタを設定
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// サーバと間のコネクションを確立
			connection.connect();

			/*
			 * サーバから応答結果を取って、returnで一回のHTTP要求を完了 EXIST_FLAG: クライアントはサーバから取った情報
			 */
			return new Boolean(connection.getHeaderField("EXIST_FLAG")).booleanValue();

		} catch (Exception ex) {
			/*
			 * HTTP要求に関する異常例外の詳細出力、
			 * ネットワーク輻輳を発生、サーバ側負荷が重いなどの情報により異常例外発生する際この詳細を出力
			 */
			System.out.println("↓↓↓↓↓↓↓↓↓ Exception Detail ↓↓↓↓↓↓↓↓↓↓↓");
			ex.printStackTrace();
			System.out.println("BatchNumber:" + detail.getBatchNumber() + "  HTTP GET Request (Thread ID) ："
					+ detail.getId() + "　請求の時、 異常例外を発生した！");
			System.out.println("↑↑↑↑↑↑↑↑↑ Exception Detail ↑↑↑↑↑↑↑↑↑↑↑");
		}
		return false;
	}

	/*
	 * 要求処理
	 */
	@Override
	public void run() {
		System.out.println(" --Thread ID: " + Thread.currentThread().getName());
		// HTTP要求IDを設定
		detail.setId(Thread.currentThread().getName());
		// HTTP要求を実行し、結果を設定
		detail.setFlag(sendGet());

		detail.setEndTime(System.currentTimeMillis());
		// 最後結果統計出力用のため、HTTP要求詳細結果を保存
		ResultSet.addResultSet(detail.getId(), detail);
	}
}
