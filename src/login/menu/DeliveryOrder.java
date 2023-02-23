package login.menu;

import dao.DeliveryDAO;
import dao.UserDao;
import vo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryOrder implements Runnable {
  static Scanner scan = new Scanner(System.in);
  static int choice = 0;
  static int storeChoice = 0;
  static int ea = 0;
  User user;
  BasketVO basketVO;
  DeliveryDAO dDao = new DeliveryDAO();
  UserDao uDao = new UserDao();
  ArrayList<CategoryVO> cvoList = new ArrayList<CategoryVO>();
  ArrayList<StoreVO> svoList = new ArrayList<StoreVO>();
  ArrayList<FoodVO> fvoList = new ArrayList<FoodVO>();
  ArrayList<BasketVO> bvoList = new ArrayList<BasketVO>();
  ArrayList<User> serchList = new ArrayList<User>();
  CategoryVO cvo = new CategoryVO();
  StoreVO svo = new StoreVO();
  FoodVO fvo = new FoodVO();
  BasketVO bvo = new BasketVO();

  public DeliveryOrder(User user) {
    this.user = user;
  }

  @Override
  public void run() {

    user.setBvoList(bvoList);

    deliveryMenu();
//		System.out.println("==== 배달주문 ==================================");
//		System.out.println("1.음식 카테고리보기 0.프로그램 종료");

    int choiceNum = menuChoice();

    switch (choiceNum) {

      case 1:
        menuToSelect();

        break;

      case 0:
        System.exit(0);

        break;

    }

  } // run() 끝 ----

  private void menuToSelect() {
    cvoList.clear();
    cvoList = dDao.foodCategory();
    showCategory(cvoList);
//			==== 메뉴 리스트 ==================================
//			1.양식	2.한식	3.일식	4.중식	5.치킨	6.분식
    choice = menuChoice(); // 가게정보
    svoList.clear();
    svoList = dDao.store(choice);
    showStore(svoList);
//			==== 가게 리스트 ==================================
//			1.스테이킷	2.홈레스토랑		0.뒤로가기

    storeChoice = menuChoice(); // 음식판매정보
    switch (choice) { // 뒤로가기
      case 0:
        menuToSelect();
        break;
    }
    fvoList.clear();
    fvoList = dDao.food(storeChoice);

    while (true) {
      showFood(fvoList);
//				==== 음식 판매정보 ==================================
//			    1.쉬림프 스테이크 : 16900	2.찹 스테이크 : 12900	3.하와이안 스테이크 : 13900	9.장바구니
      choice = menuChoice();
      if (choice == 9) {
        break;
      } else if (choice == 0) {
        menuToSelect();
        break;
      }
      System.out.print("수량을 선택하세요 : ");
      ea = scan.nextInt();
      user.setBvoList(dDao.selectFood(storeChoice, choice, ea));
    }
    showBasket(user.getBvoList(), ea);
//			==== 주문정보 ==================================
//			선택메뉴 : 쉬림프 스테이크, 수량 : 1개	 결제가격 : 16900원
//			총결제금액 : 16900원
    System.out.println("1. 결제하기" + "\t" + "2. 더 담기");
    choice = menuChoice();

    switch (choice) {
      case 1:
        String id = user.getId();
        System.out.println("접속된 아이디 : " + id);
        buy(id);

        break;
      case 2:
        menuToSelect();

        break;
    }

  }

  public ArrayList<BasketVO> showBasket(ArrayList<BasketVO> bvoList, int ea) {
    System.out.println("==== 주문정보 ==================================");
    int sum = 0;
    for (int i = 0; i < user.getBvoList().size(); i++) {
      bvo = user.getBvoList().get(i);
      System.out.print("선택메뉴 : " + bvo.getMenuName() + ", 수량 : " + bvo.getQty() + "개" + "\t");
      System.out.println(" 결제가격 : " + bvo.getSumPrice() + "원");
      sum += bvo.getSumPrice();
    }
    System.out.println("총결제금액 : " + sum + "원");
    System.out.println();
    return bvoList;

  }

  private void buy(String id) { // 결제
    System.out.println("==== 주문정보 ==================================");
    int sum = 0;
    for (int i = 0; i < user.getBvoList().size(); i++) {
      bvo = user.getBvoList().get(i);
      System.out.print("선택메뉴 : " + bvo.getMenuName() + "\t 수량 : " + bvo.getQty() + "개" + "\t");
      System.out.println(" 결제가격 : " + bvo.getSumPrice() + "원");
      sum += bvo.getSumPrice();
    }
    System.out.println("총결제금액 : " + sum + "원");
    System.out.println();
    System.out.println("1. 구매자 정보 확인" + "    " + "2. 종료하기");
    choice = menuChoice();
    switch (choice) {
      case 1:
        realBuy(id, sum);

        break;

      case 2:
        bvoList.clear();

        break;

    }

  }

  private void realBuy(String id, int sum) {


    System.out.println("주문자 이름 : " + user.getName());
    System.out.println("연락받을 전화번호 : " + user.getPhone());
    System.out.println("배달받을 주소 : " + user.getAddress());
    System.out.println();
    System.out.println("1. 결제" + "    " + "2. 주문정보 수정" + "    " + "3. 종료");
    choice = menuChoice();
    switch (choice) {

      case 1:
        System.out.println(sum + "원이 결제 되었습니다");
        for (int i = 0; i < user.getBvoList().size(); i++) {
          bvo = user.getBvoList().get(i);
          int point = points(bvo.getMenuName());
          uDao.userBuy(id, bvo.getMenuName(), bvo.getQty(), point, bvo.getStore());
          uDao.storePoint(bvo.getStore());

        }
        System.out.println();
        deliveryTime(); // 예상 배달소요 시간
        System.out.println("구매해 주셔서 감사합니다.");
        System.out.println();

        break;

      case 2: // 2. 주문정보 수정

        // uDao.update();

        break;

      case 3:
        bvoList.clear();

        break;
    }

  }

  private void deliveryTime() {
    int randomvalue;

    randomvalue = ((int) (Math.random() * 31) + 30);

    System.out.println("배달예상시간 : " + randomvalue + "분");

  }

  private void showFood(ArrayList<FoodVO> fvoList) {

    System.out.println("==== 음식 판매정보 ==================================");
    for (int i = 0; i < fvoList.size(); i++) {

      fvo = fvoList.get(i);
      System.out.print(fvo.getNum() + "." + fvo.getMenuName() + " : " + fvo.getMenuPrice() + "    ");
    }
    System.out.print("9.장바구니" + "    " + "0.뒤로가기");
    System.out.println();

  }

  private void showStore(ArrayList<StoreVO> svoList) {
    System.out.println("==== 가게 리스트 ==================================");
    for (int i = 0; i < svoList.size(); i++) {
      svo = svoList.get(i);
      System.out.print(
              svo.getStoreNum() + "." + svo.getStoreName() + "  평점(1~5) : " + svo.getPoint() + " 점" + "\t");
    }
    System.out.print("\t" + "0.뒤로가기");
    System.out.println();

  }

  private void showCategory(ArrayList<CategoryVO> cvolist) {
    System.out.println("==== 메뉴 리스트 ==================================");
    for (int i = 0; i < cvoList.size(); i++) {
      cvo = cvoList.get(i);
      System.out.print(cvo.getCategoryNum() + "." + cvo.getCategoryName() + "\t");
    }
    System.out.println();
  }

  public int menuChoice() {
    System.out.print("번호를 선택해주세요. >>");
    choice = scan.nextInt();

    return choice;
  }

  public void deliveryMenu() {
    System.out.println("==== 배달주문 ==================================");
    System.out.println("1.음식 카테고리보기 0.프로그램 종료");

  }

  public int points(String food) {
    System.out.print(food + " 별점을 주세요(1~5)>>");
    int point = scan.nextInt();
    switch (point) {
      case 1:
        point = 1;
        break;
      case 2:
        point = 2;
        break;
      case 3:
        point = 3;
        break;
      case 4:
        point = 4;
        break;
      case 5:
        point = 5;
      default:
        break;
    }
    return point;

  }

  public void serchOrder(String id) { // 주문 내역 조회
    serchList = dDao.searchOrder(id);
    for (int i = 0; i < serchList.size(); i++) {
      user = serchList.get(i);
      // 아이디, 상호명, 메뉴명, 수량, 가격, 총액, 만족도, 주문날짜
      System.out.println("아이디 : " + user.getId() + "\t" + " | 상호명 : " + user.getStoreName() + "\t" + " | 수량 : "
              + user.getQty() + "\t" + " | 가격 : " + user.getPrice() + "\t" + " | 합계 : " + user.getSumPrice()
              + "\t" + " | 만족도(1~5) : " + user.getPoint() + "\t" + " | 주문일자 : " + user.getOrderDate());
      System.out.println();
    }

  }

  public void searchLastOrder(String id) {
    serchList = dDao.searchLastOrder(id);
    for (int i = 0; i < serchList.size(); i++) {
      user = serchList.get(i);
      // 아이디, 상호명, 메뉴명, 수량, 가격, 총액, 만족도, 주문날짜
      System.out.println("아이디 : " + user.getId() + "\t" + " | 상호명 : " + user.getStoreName() + "\t" + " | 수량 : "
              + user.getQty() + "\t" + " | 가격 : " + user.getPrice() + "\t" + " | 합계 : " + user.getSumPrice()
              + "\t" + " | 만족도(1~5) : " + user.getPoint() + "\t" + " | 주문일자 : " + user.getOrderDate());
      System.out.println();
    }
  }

  public void cancelOrder(String id) {
    searchLastOrder(id);
  }
}
