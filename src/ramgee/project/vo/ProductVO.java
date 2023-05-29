package ramgee.project.vo;

public class ProductVO {
	int produt_no;
	String product_name;
	int price;
	String explanation;
	int product_count;
	
	
	@Override
	public String toString() {
		return "ProductVO [produt_no=" + produt_no + ", product_name=" + product_name + ", price=" + price
				+ ", explanation=" + explanation + ", product_count=" + product_count + "]";
	}

	
	public int getProdut_no() {
		return produt_no;
	}

	public void setProdut_no(int produt_no) {
		this.produt_no = produt_no;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getProduct_count() {
		return product_count;
	}

	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}

	public ProductVO(int produt_no, String product_name, int price, String explanation, int product_count) {
		super();
		this.produt_no = produt_no;
		this.product_name = product_name;
		this.price = price;
		this.explanation = explanation;
		this.product_count = product_count;
	}
	
	
	
}
