package ramgee.project.vo;

import java.util.*;

public class OrderVO {
	
	private int order_no; //주문번호
	//private String payment_id; //결제아이디
	private Date order_date; //주문일자
	private int total; //총금액
	private String to_hall; //포장여부

	//기본생성자
	public OrderVO() {
	}
		
	//생성자
	public OrderVO(int order_no, Date order_date, int total, String to_hall) {
		super();
		this.order_no = order_no;
		this.order_date = order_date;
		this.total = total;
		this.to_hall = to_hall;
	}
	
	
	

}
