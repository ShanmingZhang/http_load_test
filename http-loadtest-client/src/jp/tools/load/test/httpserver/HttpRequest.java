package jp.tools.load.test.httpserver;

import java.net.URL;
import java.net.URLConnection;

public class HttpRequest implements Runnable{
	private int batchNumber = -1;
	private String url = null;
	private HttpRequestStatusDetail detail = null;
	
	// param=>{ID=1}
	public HttpRequest(String url, int batchNumber) {
		this.batchNumber = batchNumber;
		this.url = url;
		detail = new HttpRequestStatusDetail();
		detail.setBatchNumber(this.batchNumber);
	}

	public boolean sendGet() {
		try {
			URL realUrl = new URL(this.url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// 建立实际的连接
			connection.connect();

			return new Boolean(connection.getHeaderField("EXIST_FLAG")).booleanValue();

		} catch (Exception ex) {
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh");
			ex.printStackTrace();
			System.out.println("BatchNumber:" + detail.getBatchNumber() + " Thread ID " + detail.getId() + " 发送GET请求出现异常！");
			System.out.println("ppppppppppppppppppppppppp");
		}
		return false;
	}

	@Override
	public void run() {
		System.out.println( " --Thread ID: " + Thread.currentThread().getName());
		detail.setId(Thread.currentThread().getName());
		detail.setFlag(sendGet());
		detail.setEndTime(System.currentTimeMillis());
		//
		ResultSet.addResultSet(detail.getId(), detail);
	}
}
