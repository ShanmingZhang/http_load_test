package jp.tools.load.test.httpserver;

/*
 * HTTP要求の詳細属性定義
 */
public class HttpRequestStatusDetail {

	// HTTP要求実行タイミング
	private long startTime = 0L;
	// HTTP要求応答完了タイミング
	private long endTime = 0L;
	// HTTP要求遅延
	private long delayTime = 0L;
	// HTTP要求識別ID
	private String id = null;
	// 並列要求を実行するバッチ番号
	private int batchNumber = -1;
	// HTTP要求実行結果
	private boolean flag = true;

	// クラスを生成、要求実行タイミング初期化
	public HttpRequestStatusDetail() {
		this.startTime = System.currentTimeMillis();
	}

	// HTTP要求応答タイミングを設定、遅延時間を算出
	public void setEndTime(long endTime) {
		this.endTime = endTime;
		this.delayTime = this.endTime - this.getStartTime();
	}

	/*
	 * 下記のget,setメソッドが詳細属性に関する設定、取得
	 */
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		System.out.println(this.getBatchNumber() + " : " + this.getId() + " : " + flag);
		this.flag = flag;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}
}
