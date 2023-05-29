package ramgee.project.vo;

public class Cart_OrderVO {
	private int cart_no;
	private int order_no;
	
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

	@Override
	public String toString() {
		return "Cart_OrderVO [cart_no=" + cart_no + ", order_no=" + order_no + "]";
	}
	public Cart_OrderVO(int cart_no, int order_no) {
		super();
		this.cart_no = cart_no;
		this.order_no = order_no;
	}
}
