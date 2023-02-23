package vo;

public class CategoryVO {
	
	private int categoryNum;
	private String categoryName;
	
	public CategoryVO() {
	}
	
	public CategoryVO(int categoryNum, String categoryName) {
		this.categoryNum = categoryNum;
		this.categoryName = categoryName;
	}

	
	public int getCategoryNum() {
		return categoryNum;
	}
	public void setCategoryNum(int categoryNum) {
		this.categoryNum = categoryNum;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
	
}
