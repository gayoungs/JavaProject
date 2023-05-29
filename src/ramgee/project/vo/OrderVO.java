package ramgee.project.vo;

import java.util.*;


// oracle 상에서 테이블명은 USER_ORDER
public class OrderVO {
	
	private int order_no; //주문번호
	private Date order_date; //주문일자
	private int total; //총금액
	private String to_hall; //포장여부
	private String size; //사이즈
	private int product_count; //주문수량
	private int product_no;//상품번호
	//기본생성자
	public OrderVO(int order_no, Date order_date, int total, String to_hall, String size, int product_count,
			int product_no) {
		super();
		this.order_no = order_no;
		this.order_date = order_date;
		this.total = total;
		this.to_hall = to_hall;
		this.size = size;
		this.product_count = product_count;
		this.product_no = product_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getTo_hall() {
		return to_hall;
	}
	public void setTo_hall(String to_hall) {
		this.to_hall = to_hall;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getProduct_count() {
		return product_count;
	}
	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	@Override
	public String toString() {
		return "OrderVO [order_no=" + order_no + ", order_date=" + order_date + ", total=" + total + ", to_hall="
				+ to_hall + ", size=" + size + ", product_count=" + product_count + ", product_no=" + product_no + "]";
	}
	
	
	
	
	

}
