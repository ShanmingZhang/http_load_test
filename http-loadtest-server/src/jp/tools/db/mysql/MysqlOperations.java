package jp.tools.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MysqlOperations {

	// 定义数据库的用户名
	private final static String USERNAME = "picolab";
	// 定义数据库的密码
	private final static String PASSWORD = "picolab";
	// 定义数据库的驱动信息
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	// 定义访问数据库的地址
	private final static String URL = "jdbc:mysql://192.168.56.104:3306/test_db?autoReconnect=true&useSSL=false&serverTimezone=UCT";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean CheckDataExist(String id) {
		// 定义访问数据库的连接
		Connection conn = null;
		// 定义sql语句的执行对象
		PreparedStatement pstmt = null;
		// 定义查询返回的结果集合
		ResultSet resultSet = null;

		boolean flag = false;
		try {
			conn = getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement("SELECT id, name FROM test_table WHERE id =" + id);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				//System.out.println("  Data => " + resultSet.getInt(1) + " : " + resultSet.getString(2));
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(" Error about Database:");
			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println(" Exception Error: ");
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

}
