package jp.tools.load.test.httpserver;

public class HttpRequestStatusDetail {

	private long startTime = 0L;
	private long endTime = 0L;
	private long delayTime = 0L; // second
	private String id = null;
	private int batchNumber = -1;
	
	public int getBatchNumber() {
		return batchNumber;
	}

	public  void setBatchNumber(int batchNumber) {
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

	private boolean flag = true;

	public HttpRequestStatusDetail() {
		this.startTime = System.currentTimeMillis(); // millisecond difference/
														// (1000* 60) => second
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
		this.delayTime = this.endTime - this.getStartTime();
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		System.out.println( this.getBatchNumber() + " : "+ this.getId() + " : " + flag);
		this.flag = flag;
	}

}
