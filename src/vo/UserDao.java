package vo;


import java.sql.*;

public class UserDao implements Dao {
  private final static String DRIVER = "oracle.jdbc.OracleDriver";
  private static final String URL = "jdbc:oracle:thin:@192.168.18.3:1521:xe";
  private static final String USER = "ITWILL";
  private static final String PASSWORD = "1234";

  static {
    try {
      Class.forName(DRIVER);
      System.out.println(">> JDBC 드라이버 로딩 성공");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private Connection conn = null;
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;

  public UserDao() {
  }
  
  public void userBuy(String id, String menuName , int qty, int point, int store) {
	  try {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ORDERS (MENU_NUM, ID, CNT, ORDERS_NUM, STAR_POINT, STORE_NUM) ");
		sql.append("VALUES((SELECT MENU_NUM FROM MENU WHERE MENU_NAME = ?),?,? " );
		sql.append(", (SELECT NVL(MAX(ORDERS_NUM),0)+1 FROM ORDERS), ?, ?) " );
		
		
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, menuName);
		pstmt.setString(2, id);
		pstmt.setInt(3, qty);
		pstmt.setInt(4, point);
		pstmt.setInt(5, store);
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		close(conn, pstmt);
	}
	  
  }
  public void storePoint(int store) {
	  try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE STORE_NAME ");
			sql.append("SET STAR_POINT = (SELECT ROUND(AVG(STAR_POINT),1) FROM ORDERS WHERE STORE_NUM = ?) " );
			sql.append("WHERE STORE_NUM IN (SELECT STORE_NUM FROM ORDERS WHERE STORE_NUM = ?) " );
			
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, store);
			pstmt.setInt(2, store);
		
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
  }
  
  
  
  
  @Override
  public User userSelect(String id) {
    User user = null;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println();

      StringBuilder sql = new StringBuilder();
      sql.append("SELECT ID, PASSWORD, NAME, PHONE, ADDRESS")
              .append(" FROM CUSTOMER")
              .append(" WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, id);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        user = new User(rs.getString("ID"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("PHONE"),
                rs.getString("ADDRESS")
               
        );
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(conn, pstmt, rs);
      return user;
    }
  }

  @Override
  public int delete(String id) throws Exception {
    int result = 0;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("DB 연결 성공");
      System.out.println();

      StringBuilder sql = new StringBuilder();
      sql.append("DELETE FROM CUSTOMER ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, id);

      result = pstmt.executeUpdate();

      System.out.println("회원 탈퇴 처리가 완료되었습니다.");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn, pstmt);
      return 1;
    }
  }

  @Override
  public int insert(User user) throws Exception {
    int result = 0;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("DB 연결 성공");
      System.out.println();

      StringBuilder sql = new StringBuilder();
      sql.append("insert into CUSTOMER ");
      sql.append("(ID, PASSWORD, NAME, PHONE, ADDRESS) ");
      sql.append("VALUES(?, ?, ?, ?, ?)");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, user.getId());
      pstmt.setString(2, user.getPassword());
      pstmt.setString(3, user.getName());
      pstmt.setString(4, user.getPhone());
      pstmt.setString(5, user.getAddress());

      result = pstmt.executeUpdate();

      System.out.println("회원가입이 완료되었습니다.");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn, pstmt);
      return 1;
    }
  }

  @Override
  public int update(User user) throws Exception {
    int result = 0;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("DB 연결 성공");
      System.out.println();

      StringBuilder sql = new StringBuilder();
      sql.append("UPDATE CUSTOMER ");
      sql.append("SET PASSWORD = ?, ");
      sql.append("    NAME = ?, ");
      sql.append("    PHONE = ?, ");
      sql.append("    ADDRESS = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, user.getPassword());
      pstmt.setString(2, user.getName());
      pstmt.setString(3, user.getPhone());
      pstmt.setString(4, user.getAddress());
      pstmt.setString(5, user.getId());

      result = pstmt.executeUpdate();

      System.out.println("회원정보수정이 완료되었습니다.");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn, pstmt);
      return 1;
    }
  }

  private void close(AutoCloseable... acs) {
    for (AutoCloseable ac : acs)
      try {
        if (ac != null) ac.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }


}
