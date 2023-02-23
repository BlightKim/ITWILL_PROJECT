package dao;

import vo.StoreVO;
import vo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterDaoImpl implements RegisterDao {
  private final static String DRIVER = "oracle.jdbc.OracleDriver";
  private static final String URL = "jdbc:oracle:thin:@192.168.18.3:1521:xe";
  private static final String USER = "ITWILL";
  private static final String PASSWORD = "1234";

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  @Override
  public boolean checkId(String id) { // 아이디 중복 체크
    List<User> userList = new ArrayList<>();

    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("SELECT ID, PASSWORD, NAME, PHONE, ADDRESS "); // ID가 똑같은 유저가 있는가 ?
      sql.append("FROM CUSTOMER ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, id);

      rs = pstmt.executeQuery();

      while(rs.next()) {
        User user = new User(rs.getString("ID"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("PHONE"),
                rs.getString("ADDRESS")
        );

        userList.add(user);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return userList.isEmpty();
    }
  }

  @Override
  public boolean checkPhone(String phone) {
    List<User> userList = new ArrayList<>();

    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("SELECT ID, PASSWORD, NAME, PHONE, ADDRESS "); // ID가 똑같은 유저가 있는가 ?
      sql.append("FROM CUSTOMER ");
      sql.append("WHERE PHONE = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, phone);

      rs = pstmt.executeQuery();

      while(rs.next()) {
        User user = new User(rs.getString("ID"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("PHONE"),
                rs.getString("ADDRESS")
        );

        userList.add(user);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return !userList.isEmpty(); // true면 중복 O / false면 중복 X
    }
  }

  @Override
  public boolean checkPwd(String id, String anotherPwd) {
    User user = selectOne(id);

    if(user.getPassword().equals(anotherPwd))
      return true;
    else
      return false;
  }

  @Override
  public int insertId(User user) {
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("insert into CUSTOMER "); //
      sql.append("(ID, PASSWORD, NAME, PHONE, ADDRESS) ");
      sql.append("values (?, ?, ?, ?, ?)");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, user.getId());
      pstmt.setString(2, user.getPassword());
      pstmt.setString(3, user.getName());
      pstmt.setString(4, user.getPhone());
      pstmt.setString(5, user.getAddress());

      pstmt.executeUpdate();

      rs = pstmt.executeQuery();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      return 1;
    }
  }

  @Override
  public boolean login(String id, String password) { // id, password 입력받아서 확인
    User user = selectOne(id);

    if(user == null)
      return false;

    return user.getPassword().equals(password); // 비밀번호가 같으면 true, 다르면 false;
  }

  @Override
  public User selectOne(String id) { // 아이디를 입력받아 유저 한 명을 반환
    User user = null;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("SELECT ID, PASSWORD, NAME, PHONE, ADDRESS ");
      sql.append("FROM CUSTOMER ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, id);

      rs = pstmt.executeQuery();

      while(rs.next()) {
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
      close(rs, pstmt, conn);
      return user;
    }
  }

  @Override
  public int deleteUser(String id) { // 아이디 입력받아 유저 삭제
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("DELETE ");
      sql.append("FROM CUSTOMER ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, id);

      pstmt.executeUpdate();


    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }

  @Override
  public int updateUserId(String id, String anotherId) { // 아이디를 입력받아 업데이트 하는 메서드

    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET ID = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherId);
      pstmt.setString(2, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }

  @Override
  public int updateUserPwd(String id, String anotherPwd) { // 비밀번호를 입력받아 업데이트 하는 메서드

    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET PASSWORD = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherPwd);
      pstmt.setString(2, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }

  @Override
  public int updateUserName(String id, String anotherName) {
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET NAME = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherName);
      pstmt.setString(2, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }

  @Override
  public int updateUserPhone(String id, String anotherPhone) {
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET PHONE = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherPhone);
      pstmt.setString(2, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }

  @Override
  public int updateUserAddress(String id, String anotherAddress) {
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET ADDRESS = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherAddress);
      pstmt.setString(2, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }
  public int updateUserAddressAndPhone(String id, String anotherAddress, String anotherPhone) { // 전화번호와 주소 같이 수정
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결

      StringBuilder sql = new StringBuilder();

      sql.append("UPDATE CUSTOMER ");
      sql.append("SET ADDRESS = ? ");
      sql.append("  , PHONE = ? ");
      sql.append("WHERE ID = ?");

      pstmt = conn.prepareStatement(sql.toString());

      pstmt.setString(1, anotherAddress);
      pstmt.setString(2, anotherPhone);
      pstmt.setString(3, id);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(rs, pstmt, conn);
      return 1;
    }
  }
//  @Override
//  public List<StoreVO> StoreList(int categoryNum) {
//    List<StoreVO> storeList = new ArrayList<>();
//
//    try {
//      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결
//
//      StringBuilder sql = new StringBuilder();
//
//      sql.append("SELECT STORE_NUM, STORE_NAME, STAR_POINT, CATEGORY ");
//      sql.append("FROM STORE ");
//      sql.append("WHERE CATEGORY = ?");
//
//      pstmt = conn.prepareStatement(sql.toString());
//
//      pstmt.setInt(1, categoryNum);
//
//      rs = pstmt.executeQuery();
//
//      while(rs.next()) {
//        StoreVO store = new StoreVO(rs.getInt("STORE_NUM"),
//                rs.getString("STORE_NAME"),
//                rs.getDouble("STAR_POINT"),
//                rs.getInt("CATEGORY"),
//                rs.get
//        );
//
//        storeList.add(store);
//      }
//
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      close(rs, pstmt, conn);
//      return storeList;
//    }
//  }

//  @Override
//  public List<MenuVO> menuList(int realStoreNum) {
//    List<MenuVO> menuList = new ArrayList<>();
//
//    try {
//      conn = DriverManager.getConnection(URL, USER, PASSWORD); // 드라이버 연결
//
//      StringBuilder sql = new StringBuilder();
//
//      sql.append("SELECT MENU_NUM, MENU_NAME, MENU_PRICE, STORE_NUM ");
//      sql.append("FROM MENU ");
//      sql.append("WHERE STORE_NUM = ?");
//
//      pstmt = conn.prepareStatement(sql.toString());
//
//      pstmt.setInt(1, realStoreNum);
//
//      rs = pstmt.executeQuery();
//
//      while(rs.next()) {
//         MenuVO menuVO = new MenuVO(rs.getInt("MENU_NUM"),
//                rs.getString("MENU_NAME"),
//                rs.getInt("MENU_PRICE"),
//                rs.getInt("STORE_NUM")
//        );
//
//        menuList.add(menuVO);
//      }
//
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      close(rs, pstmt, conn);
//      return menuList;
//    }
//  }

  private void close(AutoCloseable... acs) { //note close 메서드
    for (AutoCloseable ac : acs)
      try {
        if (ac != null) ac.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
