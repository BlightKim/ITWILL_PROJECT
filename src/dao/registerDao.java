package dao;

import vo.MenuVO;
import vo.User;

import java.util.List;

public interface registerDao {
  boolean checkPhone(String phone); // 전화번호 받아서 중복 확인
  boolean checkId(String id); // 아이디 중복체크
  boolean checkPwd(String id, String anotherPwd); // 비밀번호가 똑같은지 체크
  int insertId(User user); // 아이디 입력
  boolean login(String id, String password); // 로그인 기능
  User selectOne(String id); // 아이디 입력받아 유저 한명 반환
  int deleteUser(String id); // 아이디 입력받아 유저 삭제
  int updateUserId(String id, String anotherId); // 아이디 업데이트
  int updateUserPwd(String id, String anotherPwd); // 패스워드 업데이트
  int updateUserName(String id, String anotherName); // 이름 업데이트
  int updateUserPhone(String id, String anotherPhone); // 핸드폰 번호 업데이트
  int updateUserAddress(String id, String anotherAddress); // 주소 업데이트
  List<Store> StoreList(int categoryNum); // 카테고리 넘버를 받으면 해당 카테고리 내의 식당 리스트를 반환
  List<MenuVO> menuList(int realStoreNum); // 가계 번호 받아서 그 가게 메뉴 반환
}
