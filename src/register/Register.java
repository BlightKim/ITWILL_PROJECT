package register;

import dao.RegisterDao;
import dao.RegisterDaoImpl;
import vo.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register implements Runnable {
  Scanner sc;
  private RegisterDao registerDao;

  public Register(Scanner sc) {
    this.sc = sc;
    registerDao = new RegisterDaoImpl();
  }

  public void run() {

    System.out.println("=============================================");
    System.out.println("           화원가입 화면 입니다. ");

    String id = ""; // 아이디 변수
    String password = ""; // 패스워드 변수
    String name = ""; // 이름 변수
    String phone = ""; // 전화번호 변수
    String address = ""; // 주소 변수
    String choice = "";

    outer :
    while (true) {

      id:
      while (true) { // 유효성 통과 못할 시 반복
        System.out.print("아이디를 입력해주세요(뒤로가기 : 0) >>");
        id = sc.nextLine();

        if ("0".equals(id)) // 0번 누르면 뒤로가기
          break outer;

        if (!registerDao.checkId(id)) {
          System.out.println("이미 가입된 아이디입니다.");
          System.out.println("다시 입력해주세요.");
          continue id;
        } else
          break id;
      } //while문 id

      pwd:
      while (true) {
        System.out.print("6자리 이상 비밀번호를 입력해주세요(뒤로가기 : 0) >>");
        password = sc.nextLine();

        if ("0".equals(password)) // 0번 누르면 뒤로가기
          break outer;

        if ("".equals(password) || password.length() < 6) {
          System.out.println("비밀번호가 적합하지 않습니다. ");
          System.out.println("다시 입력해주세요");
          continue pwd;
        } else
          break pwd;
      } //while문 password

      name:
      while (true) {
        System.out.print("이름을 입력해주세요(뒤로가기 : 0) >>");
        name = sc.nextLine().replaceAll("\\s", "");

        if ("0".equals(name)) // 0번 누르면 뒤로가기
          break outer;

        if ("".equals(name)) {
          System.out.println("공백은 허용되지 않습니다.");
          System.out.println("다시 입력해주세요.");
          System.out.println();
          continue name;
        } else
          break name;
      } //while문 name

      phone:
      while (true) {
        System.out.println("* 010-1234-5678 형식으로 입력해주세요.");
        System.out.print("전화번호를 입력해주세요(뒤로가기 : 0) >>");
        phone = sc.nextLine();

        if ("0".equals(phone)) // 0번 누르면 뒤로가기
          break outer;

        if ("".equals(phone)) {
          System.out.println("공백은 허용되지 않습니다.");
          continue phone;

        } else if (invalidPhoneNumber(phone)) {
          System.out.println("올바르지 않은 형식입니다.");
          continue phone;
        } else if (registerDao.checkPhone(phone)) { // 전화번호 중복 확인
          System.out.println("이미 가입된 전화번호입니다.");
          continue phone;
        }
        break phone;
      } // while문 phone

      address:
      while (true) {
        System.out.println("* --특별시/광역시/특별자치시/도  --시/군/구  --행정구/읍/면/동  --도로명  --건물번호  --상세주소");
        System.out.print("위 형식에 따라 주소를 입력해주세요(뒤로가기 : 0) >>");

        address = sc.nextLine();

        if ("0".equals(address)) // 0번 누르면 뒤로가기
          break outer;

        if ("".equals(address)) {
          System.out.println("공백은 허용되지 않습니다.");
          continue address;
        } else
          break address;
      } // while문 address

      User user = new User(id, password, name, phone, address);
      registerDao.insertId(user);

      System.out.println("회원가입이 완료되었습니다.");

      break outer;
    }
  }

  private boolean invalidPhoneNumber(String phone) { // 전화번호 검사
    Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
    Matcher matcher = pattern.matcher(phone);

    if (matcher.matches())
      return false;
    else
      return true;
  }

//  private boolean invalidAddress(String address) {
//    Pattern pattern = Pattern.compile(addReg) ;
//    Matcher matcher = pattern.matcher(address);
//
//    if(matcher.matches())
//      return false;
//    else
//      return true;
//  }
}
