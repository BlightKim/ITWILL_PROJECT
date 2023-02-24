package vo;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String address;
	private String storeName;
	private String orderDate;
	private String menuName;
	private int category;
	private int storeNum;
	private int menuNum;
	private int sumPrice;
	private int ordersNum;
	private int qty;
	private int price;
	private int point;
	private ArrayList<BasketVO> bvoList;

	public User() {
	}
	public User(String id) {
		this.id = id;
	}

	public User(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	public User(String id, String password, String name, String phone, String address) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.category = 0;
		this.storeNum = 0;
		this.menuNum = 0;
		this.sumPrice = 0;
		this.ordersNum = 0;
	}

	public User(String id, String password, String name, String phone, String address, int category, int storeNum,
							int menuNum, int sumPrice) {
		this(id, password, name, phone, address);
		this.category = category;
		this.storeNum = storeNum;
		this.menuNum = menuNum;
		this.sumPrice = sumPrice;
	}

	public User(String id, String orderDate, int storeNum, int menuNum, int ordersNum, int qty) {
		this.id = id;
		this.orderDate = orderDate;
		this.storeNum = storeNum;
		this.menuNum = menuNum;
		this.ordersNum = ordersNum;
		this.qty = qty;
	}

	// 아이디, 상호명, 메뉴명, 수량, 가격, 총액, 만족도, 주문날짜
	public User(String id, String storeName, String menuName, int qty, int price, int sumPrice, int point, String orderDate) {
		this.id = id;
		this.storeName = storeName;
		this.menuName = menuName;
		this.qty = qty;
		this.price = price;
		this.sumPrice = sumPrice;
		this.point = point;
		this.orderDate = orderDate;
	}
	public User(String id, String storeName, String menuName, int qty, int price, int sumPrice, int point, String orderDate, int ordersNum) {
		this.id = id;
		this.storeName = storeName;
		this.menuName = menuName;
		this.qty = qty;
		this.price = price;
		this.sumPrice = sumPrice;
		this.point = point;
		this.orderDate = orderDate;
		this.ordersNum = ordersNum;
	}


	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getOrdersNum() {
		return ordersNum;
	}

	public void setOrdersNum(int ordersNum) {
		this.ordersNum = ordersNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public int getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(int menuNum) {
		this.menuNum = menuNum;
	}

	public int getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}


	public ArrayList<BasketVO> getBvoList() {
		return bvoList;
	}

	public void setBvoList(ArrayList<BasketVO> bvoList) {
		this.bvoList = bvoList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(password, user.password)
						&& Objects.equals(phone, user.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password, phone);
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + ", phone='"
						+ phone + '\'' + ", address='" + address + '\'' + ", category=" + category + ", storeNum=" + storeNum
						+ ", menuNum=" + menuNum + ", sumPrice=" + sumPrice + '}';
	}
}
