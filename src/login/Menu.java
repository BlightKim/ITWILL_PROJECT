package login;

import menu.deleteUser.DeleteUser;
import menu.login.Login;
import menu.register.Register;
import menu.showinfor.ShowInfor;

import java.util.Scanner;

public class Menu implements Runnable {
  Scanner sc;

  Login login;


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

        case "3" : // 회원정보 확인
          showInfor = new ShowInfor(this.sc);
          showInfor.run();
          continue outer;

        case "4" : // 회원정보 수정
          updateInfor = new UpdateInfor(this.sc);
          updateInfor.run();
          continue outer;

        case "5" : // 회원탈퇴
          deleteUser = new DeleteUser(this.sc);
          deleteUser.run();
          continue outer;

        case "0": // 프로그램 종료
          break outer;
      }
    }
  }
}
