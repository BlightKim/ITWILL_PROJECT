package login.menu;

import dao.RegisterDao;
import dao.RegisterDaoImpl;
import dao.RegisterDaoImpl;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateInfor implements Runnable{ // 유저의 정보를 확인하여 정보 수정
  Scanner sc;
  private RegisterDao registerDao = new RegisterDaoImpl();
  public UpdateInfor(Scanner sc) {
    this.sc = sc;
  }

  public void run() {
    int loginTry = 0; // 로그인 시도 횟수 변수

    String id = "";
    String password = "";
    String name = "";
    String phone = "";
    String address = "";
    int choice = 0;

    System.out.println("=============================================");
    System.out.println("           회원정보 수정 화면입니다. ");

    out:
    while (true) {
      if(loginTry > 5) {
        System.out.println("로그인 시도가 5회를 초과하여 프로그램을 종료합니다.");
        break;
      }

      System.out.print("아이디를 입력하세요 >>");
      id = sc.next();

      System.out.print("비밀번호를 입력하세요 >>");
      password = sc.next();

      if(registerDao.login(id, password)) { // id와 password가 일치
        System.out.println("수정을 원하시는 항목을 선택해주세요.");
        System.out.println("1.패스워드  2.이름  3.전화번호  4.주소");
        choice = sc.nextInt();


        switch(choice) {
//          case 1 :
//            System.out.print("새로운 아이디를 입력해주세요 >>");
//            String anotherId = "";
//            anotherId = sc.next().trim();
//            if(!daoo.checkId(anotherId)) {
//              System.out.println("이미 가입된 아이디입니다.");
//            } else {
//              daoo.updateUserId(id, anotherId);
//              System.out.println("아이디 수정이 완료되었습니다.");
//            }
//            break outer;

          case 1 :
            System.out.print("새로운 비밀번호를 입력해주세요 >>");
            String anotherPwd = "";
            anotherPwd = sc.next();
            if(registerDao.checkPwd(id, anotherPwd)) {
              System.out.println("동일한 비밀번호 입니다.");
            } else {
              registerDao.updateUserPwd(id, anotherPwd);
              System.out.println("비밀번호 수정이 완료되었습니다.");
            }
            break out;

          case 2 :
            System.out.print("새로운 이름을 입력해주세요 >>");
            String anotherName = "";
            anotherName = sc.next().replaceAll("\\s", "");
            registerDao.updateUserName(id, anotherName);
            System.out.println("이름 수정이 완료되었습니다.");
            break out;

          case 3 :
            phone :
            while(true) {
              System.out.print("새로운 전화번호를 입력해주세요 >>");
              String anotherPhone = "";
              anotherPhone = sc.next();
              if (invalidPhoneNumber(anotherPhone)) {
                System.out.println("전화번호를 다시 입력해주세요");
                System.out.println();
                continue phone;
              } else {
                registerDao.updateUserPhone(id, anotherPhone);
                System.out.println("전화번호 수정이 완료되었습니다.");
                break out;
              }
            } // while - phone

          case 4 :
            System.out.print("새로운 주소를 입력해주세요 >>");
            String anotherAddress = "";
            anotherAddress = sc.next();

            registerDao.updateUserAddress(id, anotherAddress);
            System.out.println("주소 수정이 완료되었습니다.");
            break out;
        } // switch문
      } else {
        System.out.println("입력하신 아이디와 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
        loginTry++; // 로그인 시도횟수 1 증가
        continue;
      }
    }
  }

  private boolean invalidPhoneNumber(String anotherPhone) { // 전화번호 검사
    Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}") ;
    Matcher matcher = pattern.matcher(anotherPhone);

    if(matcher.matches())
      return false;
    else
      return true;
  }
}
