package vo;

public class FoodVO {
	private int menuNum;
	private String menuName;
	private int menuPrice;
	private int storeNum;
	private int num;
	



	public FoodVO() {
	}
	
	
	
	public FoodVO(int menuNum, String menuName, int menuPrice, int storeNum, int num) {
		super();
		this.menuNum = menuNum;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.storeNum = storeNum;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public int getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(int menuNum) {
		this.menuNum = menuNum;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	
	
	
	
	
}
