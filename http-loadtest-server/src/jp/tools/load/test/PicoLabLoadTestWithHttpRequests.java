package jp.tools.load.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.tools.db.mysql.MysqlOperations;

/*
 * HTTP要求URLに対するサーバ側のロジック処理サービス
 * 　クライアントからURLからIDパラメタを取得
 * 　IDによる一回MysqlデータベースにIDを検索
 * 　負荷テストするため、連続整数和の計算処理を追加
 */
@WebServlet("/PicoLabLoadTestWithHttpRequests")
public class PicoLabLoadTestWithHttpRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static long number = 0L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PicoLabLoadTestWithHttpRequests() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * HTTP GETメソッドを受け扱い、応答
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HTTP要求からクライアント側伝えたパラメタを取得
		String id = request.getParameter("ID");

		// 通常HTTP応答の属性設定
		response.setContentType("text/htm");
		response.setCharacterEncoding("UTF-8");

		/*
		 * IDによりデータベースにアクセスし、検索処理を実施 結果を応答としてHTTP応答に設定
		 */
		response.addHeader("EXIST_FLAG", new Boolean(MysqlOperations.CheckDataExist(id)).toString());

		// サーバ負荷処理を増えるため、かきのフープ処理を追加
		long start_timing = System.currentTimeMillis();
		List<Integer> lst = new ArrayList();

		int sum = 0;
		for (int index = 0; index < 100000; index++) {
			sum = sum + index;
			lst.add(new Integer(sum));
		}

		for (Integer itg : lst) {
			itg.intValue();
		}

		// HTTP要求に対しサーバ側処理時間を出力
		long end_timing = System.currentTimeMillis();
		System.out.println(" The " + (++(this.number)) + " th Http Request is from " + request.getRemoteAddr()
				+ "; total time:" + (end_timing - start_timing));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
