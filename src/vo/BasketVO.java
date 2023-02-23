package vo;

public class BasketVO {
	private int menuNum;
	private int foodPrice;
	private int sumPrice;
	private String menuName;
	private int qty;
	private int store;
	
	public BasketVO() {
	}
	

	public BasketVO(int menuNum, String menuName, int foodPrice, int sumPrice, int qty, int store) {
		this.menuNum = menuNum;
		this.menuName = menuName;
		this.foodPrice = foodPrice;
		this.sumPrice = sumPrice;
		this.qty = qty;
		this.store = store;
	}
	public BasketVO(String menuName, int foodPrice) {
		this.menuName = menuName;
		this.foodPrice = foodPrice;
	}
	
	
	public int getStore() {
		return store;
	}


	public void setStore(int store) {
		this.store = store;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public int getSumPrice() {
		return sumPrice;
	}


	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}


	public int getMenuNum() {
		return menuNum;
	}


	public void setMenuNum(int menuNum) {
		this.menuNum = menuNum;
	}


	public int getFoodPrice() {
		return foodPrice;
	}


	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	
}
