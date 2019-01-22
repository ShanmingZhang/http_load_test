package jp.tools.load.test.httpserver;

public class LoadTestClientWithHttpRequests {

	private int number = 0;
	private int interval = 0;
	private int totalTime = 0;
	private int startTime = 0;

	private String serverURL = "";

	public LoadTestClientWithHttpRequests() {
	}

	public LoadTestClientWithHttpRequests(int number, int interval, int totalTime, int startTime, String serverURL,
			String param) {

		this.number = number;
		this.interval = interval;
		this.totalTime = totalTime;
		this.startTime = startTime;
		this.serverURL = serverURL + "?" + param;
	}

	public void perfromLoadTest() {
		try {
			System.out.println(" The load test will be started after the specified " + startTime + " second(s)");
			Thread.sleep((this.startTime) * 1000);

			for (int index = 0; index < totalTime; index++) {
				for (int num = 0; num < number; num++) {
					// System.out.println(num);
					HttpRequest httpReq = new HttpRequest(serverURL, index + 1 );
					new Thread(httpReq, new Integer((num + 1 + (index * this.number))).toString()).start();

					System.out.println(Thread.currentThread().getName() + ":" + (num + 1));
				}
				//
				Thread.sleep((this.interval) * 1000);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
