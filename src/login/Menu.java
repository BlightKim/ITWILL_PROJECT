package login;

import dao.RegisterDao;
import dao.RegisterDaoImpl;
//import login.menu.DeliveryOrder;
import dao.UserDao;
import login.menu.DeliveryOrder;
import register.Register;
import vo.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Menu implements Runnable {
  Scanner sc;
  //  DeliveryOrder deliveryOrder = new DeliveryOrder();
  Register register;
  UserDao userDao;
  DeliveryOrder deliveryOrder;
  private RegisterDao registerDao = new RegisterDaoImpl();

  public Menu(Scanner sc) {
    this.sc = sc;
  }

  @Override
  public void run() {
    String choice = "";
    int loginTry = 0; // 로그인 시도횟수

    showLogo(); // 로고 보여주기

    System.out.print("이용을 원하시면 엔터를 눌러주세요. (프로그램 종료는 0번)");

    if ((choice = sc.nextLine()).equals("")) { // 엔터치면 프로그램 시작

      menu: while (true) {

        System.out.println("1.로그인  2.회원가입  3.프로그램 종료");

        System.out.print("원하시는 메뉴를 선택해주세요. >>");

        choice = sc.nextLine();

        switch (choice) {
          case "1":
            login: while (true) {
              String id = ""; // 아이디 변수
              String password = ""; // 비밀번호 변수

              System.out.print("아이디를 입력하세요(뒤로가기 : 0) >>");
              id = sc.nextLine();
              if ("0".equals(id))
                continue menu;

              System.out.print("비밀번호를 입력하세요(뒤로가기 : 0) >>");
              password = sc.nextLine();
              if ("0".equals(password))
                continue menu;

              if (registerDao.login(id, password)) { // 로그인 성공
                User user = registerDao.selectOne(id);

                deliveryOrder = new DeliveryOrder(user);



                orderMenu :
                while (true) {

                  System.out.println("1.주문  2.주문내역 조회  3.주문 취소  4.뒤로가기");

                  System.out.print("원하시는 서비스를 선택해주세요. >>");
                  choice = sc.nextLine();

                  switch (choice) {
                    case "1":
                      deliveryOrder.run();
                      continue orderMenu;

                    case "2":
                      deliveryOrder.serchOrder(user.getId());
                      continue menu;

                    case "3":
                      deliveryOrder.cancelOrder(user.getId());


                  }
                }

              } else {
                System.out.println("아이디 비밀번호가 일치하지 않습니다.");
                loginTry++; // 로그인 횟수 1회 증가

                if (loginTry > 5) { // 만약 로그인 횟수가 5회 이상 초과하면
                  System.out.println("로그인 횟수가 5회를 초과하였습니다. 프로그램을 종료합니다.");
                  break menu;

                } else {
                  continue login; // 5회를 초과하지 않았으면 다시 아이디 입력 화면으로 되돌아간다.
                }
              }
            }

          case "2": // 2번 누르면 회원가입 실행
            register = new Register(this.sc);
            register.run();
            continue menu;

          case "3": // 3번 누르면 프로그램 종료
            break menu;
        }

      }
    } else if ("0".equals(choice)) {
    }
  }

  private static int sInread(String id) {
    int readValue = 0;
    try {
      readValue = System.in.read("0".getBytes());
      return readValue;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void showLogo() {
    System.out.println("\r\n"
            + "______  _____  _      _____  _   _  _____ ______ __   __        _   _  _____ ______  _____ \r\n"
            + "|  _  \\|  ___|| |    |_   _|| | | ||  ___|| ___ \\\\ \\ / /       | | | ||  ___|| ___ \\|  _  |\r\n"
            + "| | | || |__  | |      | |  | | | || |__  | |_/ / \\ V /        | |_| || |__  | |_/ /| | | |\r\n"
            + "| | | ||  __| | |      | |  | | | ||  __| |    /   \\ /         |  _  ||  __| |    / | | | |\r\n"
            + "| |/ / | |___ | |____ _| |_ \\ \\_/ /| |___ | |\\ \\   | |         | | | || |___ | |\\ \\ \\ \\_/ /\r\n"
            + "|___/  \\____/ \\_____/ \\___/  \\___/ \\____/ \\_| \\_|  \\_/         \\_| |_/\\____/ \\_| \\_| \\___/ \r\n"
            + "                                                                                           \r\n"
            + "                                                                                           \r\n"
            + "");
  }
}
