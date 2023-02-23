package login.menu;

import dao.RegisterDao;
import dao.RegisterDaoImpl;
import register.Register;

import java.util.Scanner;

public class Menu implements Runnable {
  Scanner sc;


  Register register;
  private RegisterDao registerDao = new RegisterDaoImpl();

  public Menu(Scanner sc) {
    this.sc = sc;
  }

  @Override
  public void run() {
    int loginTry = 0; // 로그인 시도횟수
    int choiceNum = 0;
    menu:
    while (true) {

      System.out.println("1.로그인  2.회원가입  3.프로그램 종료");

      System.out.print("번호를 선택해주세요. >>");

      String choice = sc.nextLine();

        switch (choice) {
          case "1":
            login :
            while(true) {
              String id = ""; // 아이디 변수
              String password = ""; // 비밀번호 변수

              System.out.print("아이디를 입력하세요(뒤로가기 : 0) >>");
              id = sc.nextLine();
              if("0".equals(id))
                continue menu;

              System.out.print("비밀번호를 입력하세요(뒤로가기 : 0) >>");
              password = sc.nextLine();
              if("0".equals(password))
                continue menu;

              if (registerDao.login(id, password)) { // 로그인 성공


              } else {
                System.out.println("아이디 비밀번호가 일치하지 않습니다.");
                loginTry++; // 로그인 횟수 1회 증가

                if(loginTry > 5) { // 만약 로그인 횟수가 5회 이상 초과하면
                  System.out.println("로그인 횟수가 5회를 초과하였습니다. 프로그램을 종료합니다.");
                  break menu;

                } else {
                  continue login; // 5회를 초과하지 않았으면 다시 아이디 입력 화면으로 되돌아간다.
                }
              }
            }

          case "2":
            register = new Register(this.sc);
            register.run();
            continue menu;

          case "3": // 프로그램 종료
            break menu;
        }



    }
  }
}
