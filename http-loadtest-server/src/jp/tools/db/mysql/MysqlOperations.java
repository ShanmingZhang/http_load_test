package jp.tools.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MysqlOperations {

	// Mysqlデータベースに接続用のパラメタ定義と設定
	// データベース接続用ユーザ
	private final static String USERNAME = "picolab";
	// データベース接続用パスワード
	private final static String PASSWORD = "picolab";
	// JDBCドライバー
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	// データベースサーバアドレスおよびアクセス属性設定
	private final static String URL = "jdbc:mysql://192.168.56.104:3306/test_db?autoReconnect=true&useSSL=false&serverTimezone=UCT";

	//　データベースとのコネクションを確立
	public static Connection getConnection() {
		Connection conn = null;
		try {
			//データベースに接続のドライバをロード
			Class.forName(DRIVER);
			//コネクションを取得
			conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("データベースとのコネクション確立異常例外発生　１");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("データベースとのコネクション確立異常例外発生　２");
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean CheckDataExist(String id) {
		// コネクション定義
		Connection conn = null;
		// SQL実行用
		PreparedStatement pstmt = null;
		// データベースの結果
		ResultSet resultSet = null;

		boolean flag = false;
		try {
			// データベースとのコネクションを確立、取得
			conn = getConnection();
			//　SQL文を設定
			pstmt = (PreparedStatement) conn.prepareStatement("SELECT id, name FROM test_table WHERE id =" + id);
			//  SQL文を実行、結果を返す
			resultSet = pstmt.executeQuery();
			//  結果を確認
			if (resultSet.next()) {
				//System.out.println("  Data => " + resultSet.getInt(1) + " : " + resultSet.getString(2));
				flag = true;
			}
			
		} catch (SQLException e) {
			System.out.println("データベースからデータ取得に関する異常例外発生　１");
			e.printStackTrace();
		} catch (Exception ex) {
			System.out.println("データベースからデータ取得に関する異常例外発生　２");
			ex.printStackTrace();
		} finally {
			//　コネクションなどをしめて、メモリを解放
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
				System.out.println("データベースからデータ取得に関する異常例外発生　３");
				e.printStackTrace();
			}
		}
		return flag;
	}

}
