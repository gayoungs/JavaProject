package ramgee.project.vo;

public class PayVO {

//	payment_id
//	member_no
//	payment
//	order_pay
//	discount
//	total_pay
//	d_count
   private int payment_id; //결제아이디 (pk)
   private int member_no; //회원번호 (fk)
   private String payment; //결제방법
   private int order_pay; //주문금액
   private int discount; //도토리할인
   private int total_pay; //결제금액 
   
   private int d_count; // 도토리갯수
   
   public PayVO() {
   }

   public PayVO(int payment_id, int member_no, String payment, int order_pay, int discount, int total_pay,
         int d_count) {
      super();
      this.payment_id = payment_id;
      this.member_no = member_no;
      this.payment = payment;
      this.order_pay = order_pay;
      this.discount = discount;
      this.total_pay = total_pay;
      this.d_count = d_count;
   }

   public int getPayment_id() {
      return payment_id;
   }

   public void setPayment_id(int payment_id) {
      this.payment_id = payment_id;
   }

   public int getMember_no() {
      return member_no;
   }

   public void setMember_no(int member_no) {
      this.member_no = member_no;
   }

   public String getPayment() {
      return payment;
   }

   public void setPayment(String payment) {
      this.payment = payment;
   }

   public int getOrder_pay() {
      return order_pay;
   }

   public void setOrder_pay(int order_pay) {
      this.order_pay = order_pay;
   }

   public int getDiscount() {
      return discount;
   }

   public void setDiscount(int discount) {
      this.discount = discount;
   }

   public int getTotal_pay() {
      return total_pay;
   }

   public void setTotal_pay(int total_pay) {
      this.total_pay = total_pay;
   }

   public int getD_count() {
      return d_count;
   }

   public void setD_count(int d_count) {
      this.d_count = d_count;
   }
}
