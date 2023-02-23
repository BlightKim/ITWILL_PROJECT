package vo;

public class StoreVO {
	private int storeNum;
	private String storeName;
	private int starPoint;
	private int category;
	private double point;
	public StoreVO() {
	}
	
	public StoreVO(int storeNum, String storeName, int starPoint, int category, double point) {
		super();
		this.storeNum = storeNum;
		this.storeName = storeName;
		this.starPoint = starPoint;
		this.category = category;
		this.point = point;
	}
	
	public StoreVO(int storeNum, String storeName, int starPoint) { 
		super();
		this.storeNum = storeNum;
		this.storeName = storeName;
		this.starPoint = starPoint;
		this.category = category;
	}
	
	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getStarPoint() {
		return starPoint;
	}

	public void setStarPoint(int starPoint) {
		this.starPoint = starPoint;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	
}
