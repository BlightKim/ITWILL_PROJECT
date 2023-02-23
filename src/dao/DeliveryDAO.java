package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import vo.BasketVO;
import vo.CategoryVO;
import vo.FoodVO;
import vo.StoreVO;
import vo.User;

public class DeliveryDAO {
	BasketVO basketVO;
	private String DRIVER = "oracle.jdbc.OracleDriver";
	private String URL = "jdbc:oracle:thin:@192.168.18.3:1521:xe";
	private String USER = "ITWILL";
	private String PASSWORD = "1234";
	private int choice = 0;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	User user = new User();
	ArrayList<CategoryVO> cvoList = new ArrayList<CategoryVO>();
	ArrayList<StoreVO> svoList = new ArrayList<StoreVO>();
	ArrayList<FoodVO> fvoList = new ArrayList<FoodVO>();
	ArrayList<BasketVO> bvoList = new ArrayList<BasketVO>();
	ArrayList<User> serchList = new ArrayList<User>();
	CategoryVO cvo = new CategoryVO();
	StoreVO svo = new StoreVO();
	FoodVO fvo = new FoodVO();
	BasketVO bvo = new BasketVO();
	
	
	
	
	
	
	
	
	
	public ArrayList<CategoryVO> foodCategory() { // 카테고리 출력
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM CATEGORY_NAME");

			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cvo = new CategoryVO(rs.getInt("CATEGORY"), rs.getString("CATEGORY_NAME"));
				cvoList.add(cvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		System.out.println();

		return cvoList;
	}

	public ArrayList<StoreVO> store(int choice) { // 가게정보 출력

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM STORE_NAME WHERE CATEGORY = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, choice);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StoreVO(rs.getInt("STORE_NUM"), rs.getString("STORE_NAME"), rs.getInt("STAR_POINT"), rs.getInt("CATEGORY"),rs.getInt("STAR_POINT"));
				svoList.add(svo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}

		return svoList;
	}
	
	public ArrayList<FoodVO> food(int choice) { // 음식판매정보 출력
		
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM MENU WHERE STORE_NUM = ? ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, choice);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fvo = new FoodVO(rs.getInt("MENU_NUM"), rs.getString("MENU_NAME"), rs.getInt("MENU_PRICE"), rs.getInt("STORE_NUM"), rs.getInt("NUM"));
				fvoList.add(fvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}

		return fvoList;
	}
	
	public ArrayList<BasketVO> selectFood(int storeChoice, int choice, int ea) {// 장바구니 담기
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT S.*,M.*,(MENU_PRICE * ?) ");
			sql.append("FROM STORE_NAME S, MENU M ");
			sql.append("WHERE S.STORE_NUM = M.STORE_NUM ");
			sql.append("AND S.STORE_NUM = ? ");
			sql.append("AND M.NUM = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, ea);
			pstmt.setInt(2, storeChoice);
			pstmt.setInt(3, choice);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bvo = new BasketVO(rs.getInt("MENU_NUM"),rs.getString("MENU_NAME") ,rs.getInt("MENU_PRICE"), (rs.getInt("MENU_PRICE") * ea), ea, storeChoice);
				bvoList.add(bvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return bvoList; 
		
		
	}
	
public ArrayList<User> serchOrder(String id) {// 장바구니 담기
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT O.ID, S.STORE_NAME, M.MENU_NAME, O.CNT, M.MENU_PRICE, "
					+ "(MENU_PRICE * CNT), O.STAR_POINT, O.BUY_DATE ");
			sql.append("FROM ORDERS O, STORE_NAME S, MENU M ");
			sql.append("WHERE O.STORE_NUM = S.STORE_NUM ");
			sql.append("AND O.MENU_NUM = M.MENU_NUM ");
			sql.append("AND O.ID = ? ");

			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				user = new User(rs.getString("ID"),rs.getString("STORE_NAME") ,rs.getString("MENU_NAME"), rs.getInt("CNT"), 
								rs.getInt("MENU_PRICE"), rs.getInt("(MENU_PRICE*CNT)"),rs.getInt("STAR_POINT"),rs.getString("BUY_DATE"));
				serchList.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return serchList; 
		
		
	}

	public ArrayList<User> searchLastOrder(String id) { // 15분 이내 주문리스트 불러오기

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, MENU_NUM, CNT, BUY_DATE, STAR_POINT, ORDERS_NUM, STORE_NUM ");
			sql.append("FROM ORDERS ");
			sql.append("WHERE BUY_DATE > (SYSDATE-1/96) ");
			sql.append("AND ID = ? ");
			sql.append("ORDER BY BUY_DATE");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, id);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				user = new User(rs.getString("ID"),
								rs.getString("BUY_DATE"),
								rs.getInt("MENU_NUM"),
								rs.getInt("CNT"),
								rs.getInt("ORDERS_NUM"),
								rs.getInt("STORE_NUM")
				);
				serchList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
			return serchList;
		}
	}


	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			if (pstmt != null) pstmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn, PreparedStatement pstmt) {
		try {
			if (pstmt != null) pstmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
}
