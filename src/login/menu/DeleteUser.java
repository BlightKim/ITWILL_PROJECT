package login.menu;

import dao.RegisterDao;
import dao.RegisterDaoImpl;

import java.util.Scanner;

public class DeleteUser extends Thread {
  Scanner sc;
  RegisterDao registerDao = new RegisterDaoImpl();

  public DeleteUser(Scanner sc) {
    this.sc = sc;
  }

  public void run()  {
    int loginTry = 0; // 로그인 시도 횟수 변수

    String id = "";
    String password = "";

    String choice = ""; // 정말로 삭제하시겠습니까 ? 대답 저장할 변수

    System.out.println("=============================================");
    System.out.println("           화원탈퇴 화면 입니다. ");

    while (true) {
      if(loginTry > 5) {
        System.out.println("로그인 시도가 5회를 초과하여 프로그램을 종료합니다.");
        break;
      }

      System.out.print("아이디를 입력하세요 >>");
      id = sc.next().trim();

      System.out.print("비밀번호를 입력하세요 >>");
      password = sc.next().trim();

      if(registerDao.login(id, password)) { // id와 password가 일치
        System.out.print("정말로 삭제하시겠습니까 ?(Y/N) >>");
        choice = sc.next().trim();

        switch(choice) {
          case "Y" :
            registerDao.deleteUser(id);
            break;

          case "N" :
            break;
        }
        break;
      } else {
        System.out.println("입력하신 아이디와 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
        loginTry++; // 로그인 시도횟수 1 증가
        continue;
      }
    }
  }
}
