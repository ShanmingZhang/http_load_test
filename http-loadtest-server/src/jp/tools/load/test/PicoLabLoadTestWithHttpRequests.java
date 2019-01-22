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

/**
 * Servlet implementation class PicoLabLoadTestWithHttpRequests
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("ID");

		response.setContentType("text/htm");
		response.setCharacterEncoding("UTF-8");

		response.addHeader("EXIST_FLAG", new Boolean(MysqlOperations.CheckDataExist(id)).toString());

//		PrintWriter out = response.getWriter();

//		out.println("<!DOCTYPE html><html>");
//		out.println("<head>");
//		out.println("<meta charset=\"UTF-8\" />");
//
//		out.println("<title>" + "PicoLab Load Test" + "</title>");
//		out.println("</head>");
//		out.println("<body>");
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
		
		long end_timing = System.currentTimeMillis();

		System.out.println(" The " + (++(this.number)) + " th Http Request is from " + request.getRemoteAddr()
				+ "; Sum = " + sum + "; total time:" + ( end_timing - start_timing));

		//
//		
//		out.println("<h1>" + " The " + (++(this.number)) + " th Http Request is " + (MysqlOperations.CheckDataExist(id) == true ? " successful" : " failure" ) + "! </h1>");
//		out.println("</body>");

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
