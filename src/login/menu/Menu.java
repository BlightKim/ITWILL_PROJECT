package login.menu;

import login.Login;
import login.menu.UpdateInfor;
import register.Register;

import java.util.Scanner;

public class Menu implements Runnable {
  Scanner sc;

  Login login;

  Register register;


  public Menu(Scanner sc) {
    this.sc = sc;
  }

  @Override
  public void run() {
    outer :
    while (true) {
      System.out.println("1.로그인  2.회원가입  3.프로그램 종료");

      System.out.print("번호를 선택해주세요. >>");

      String choice = sc.next();

      switch (choice) {
        case "1" :
          login = new Login(this.sc);
          login.run();
          continue outer;

        case "2" :
          register = new Register(this.sc);
          register.run();
          continue outer;

        case "3" : // 프로그램 종료
          break outer;
      }
    }
  }
}
