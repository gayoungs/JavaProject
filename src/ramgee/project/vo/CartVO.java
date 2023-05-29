package ramgee.project.vo;

import java.util.List;

public class CartVO {
	
	int cart_no;
	int member_no;
	List<OrderVO> order_list;
	int total_price;
	
	public List<OrderVO> getOrder_list() {
		return order_list;
	}

	public void setOrder_list(List<OrderVO> order_list) {
		this.order_list = order_list;
	}

	//생성자
	public CartVO(int cart_no, int member_no, List<OrderVO> order_list, int total_price) {
		super();
		this.cart_no = cart_no;
		this.member_no = member_no;
		this.order_list = order_list;
		this.total_price = total_price;
	}

	//getter, setter
	public int getCart_no() {
		return cart_no;
	}

	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	
	

}
