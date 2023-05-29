package ramgee.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.MembersVO;
import ramgee.project.vo.PayVO;

public class PayDAO {
	//데이터 베이스 연결을 해주는 역할(DAO)

	//db properties
	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;
	
    private Connection connection;

    // 생성자
    public PayDAO() {
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			connection = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
			System.out.println("CLASS FOR NAME ERR");
			e.printStackTrace();
		}
    }
    
    
    // 1. 결제내역 생성
    // member_no 가 담긴 MembersVO 객체를 파라메터로 넘겨받아야 한다 (결제자 입력용)
    public int insertPay(PayVO payVO, MembersVO membersVO) {
    	int result = 0; //0이 반환되면 실패, 1이 반환되면 성공

		//insert, update, delete는 ResultSet 객체가 필요없음
		Connection connection = null;
		PreparedStatement statement = null;
		
        String query = "INSERT INTO pay (payment_id, member_no, payment, order_pay, discount, total_pay, d_count) VALUES (PAY_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try{
        	
        	connection = DriverManager.getConnection(url, uid, upw);

            // 준비된 문장에 값을 설정합니다.
			statement = connection.prepareStatement(query);
			statement.setInt(1, membersVO.getMember_no()); // membersVO로 넘어온 회원번호
			statement.setString(2, payVO.getPayment());
            statement.setInt(3, payVO.getOrder_pay());
            statement.setInt(4, payVO.getDiscount());
            statement.setInt(5, payVO.getTotal_pay());
            statement.setInt(6, payVO.getD_count());
            result = statement.executeUpdate();  // 쿼리를 실행하여 회원을 추가합니다.
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
    }

    // 2. 회원 정보 수정
    public void updatePay(PayVO payVO) throws SQLException {
        String query = "UPDATE pay SET payment = ?, order_pay = ?, discount = ?, total_pay = ? , d_count = ? WHERE payment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, payVO.getPayment());
            statement.setInt(2, payVO.getOrder_pay());
            statement.setInt(3, payVO.getDiscount());
            statement.setInt(4, payVO.getTotal_pay());
            statement.setInt(5, payVO.getD_count());

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 업데이트합니다.
        }
    }

    // 3. 회원 삭제
    public void deleteMember(PayVO payVO) throws SQLException {
        String query = "DELETE FROM pay WHERE payment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,payVO.getPayment_id());

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 삭제합니다.
        }
    }

    // 4. ID로 회원을 조회
    public PayVO findMemberById(PayVO payVO) throws SQLException {
        PayVO pay = null;
        String query = "SELECT * FROM pay WHERE payment_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, payVO.getPayment_id());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pay = mapResultSetToPay(resultSet);  // 조회된 결과를 PayVO 객체로 매핑합니다.
                }
            }
        }
        return pay;
    }
 // 5. 모든 결제내역을 조회
    public List<PayVO> findAllPay() throws SQLException {
        List<PayVO> pay = new ArrayList<>();
        String query = "SELECT * FROM pay";
        try (
        		PreparedStatement statement = connection.prepareStatement(query);
        		ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
            	
                PayVO payVO = mapResultSetToPay(resultSet);  // 조회된 결과를 PayVO 객체로 매핑합니다.
                pay.add(payVO);
            }
        }catch (Exception e) {
			e.printStackTrace();
			System.out.print("사유 : " + e.getMessage());
		}
        return pay;
    }

    // ResultSet을 PayVO 객체로 매핑하는 보조 메서드입니다.
    private PayVO mapResultSetToPay(ResultSet resultSet) throws SQLException {
        int payment_id = resultSet.getInt("payment_id");
        int member_no = resultSet.getInt("member_no");
        String payment = resultSet.getString("payment");
        int order_pay = resultSet.getInt("order_pay");
        int discount = resultSet.getInt("discount");
        int total_pay = resultSet.getInt("total_pay");
        int d_count = resultSet.getInt("d_count");
        	
        return new PayVO(payment_id, member_no, payment, order_pay, discount, total_pay, d_count);
    }
    
    public static void main(String[] args) throws Exception {
    	Scanner scan = new Scanner(System.in);
    	
    	PayDAO payDAO = new PayDAO();
    	
    	while(true) {
    	       System.out.println("지불방법을 선택해주세요.");
    	       System.out.println("1.카드  2.현금  3.도토리");
    	       int payment = scan.nextInt();
    	       
    	       if(payment == 1) {//카드선택
    	          System.out.println("카드를 선택하셨습니다.");
    	          System.out.println("1.등록된 카드 2.신규 카드등록 3.이전단계");
    	          
    	          int card = scan.nextInt();
    	          if(card == 1) {
    	             System.out.println("등록된 카드로 결제진행 하겠습니다.");
    	             System.out.println("총 결제금액은" + "total_pay" + "원 입니다.");
    	             System.out.println("결제가 완료되었습니다. 감사합니다.");
    	             break;   
    	          }else if(card == 2) {
    	             System.out.println("신규카드 등록을 진행하겠습니다.");
    	             System.out.print("카드번호 16자리를 입력해주세요: ");
    	             String cardNum = scan.next();
    	             System.out.print("카드 유효기간을 입력해주세요: ");
    	             int cardDate = scan.nextInt();
    	             System.out.print("카드 csv 3자리를 입력해주세요: ");
    	             int cardcsv = scan.nextInt();
    	             
    	             System.out.println("카드등록이 완료되었습니다. 해당카드로 결제진행하겠습니다.");
    	             System.out.println("총 결제금액은" + "total_pay" + "원 입니다.");
    	             System.out.println("결제가 완료되었습니다. 감사합니다.");
    	             break;
    	             
    	          }else if (card == 3) {
    	             System.out.println("이전단계로 돌아가겠습니다.");
    	             
    	          }else {
    	             System.out.println("번호를 잘못입력하셨습니다.");
    	             
    	          }
    	          
    	          
    	          
    	       }else if(payment == 2) {//현금선택
    	          System.out.println("현금을 선택하셨습니다.");
    	          System.out.println("총 결제금액 " + "total_pay" +"원을 결제하겠습니다." );
    	          System.out.println("결제가 완료되었습니다. 감사합니다.");
    	          break;
    	          
    	       }else if(payment == 3) {//도토리선택
    	          System.out.println("도토리를 선택하셨습니다.");
    	          int d_count = 1;
    	          System.out.println("현재 보유하고 있는 쿠폰은 " + d_count + "개 입니다");
    	          
    	          if(d_count >= 10) {
    	          
    	             System.out.println("도토리 10개를 차감하였습니다.");
    	             d_count -= 10;
    	             System.out.println("남은 도토리의 갯수는 " + d_count + "개 입니다.");
    	             System.out.println("주문이 완료되었습니다.");
    	             break;
    	          }else {
    	             System.out.println("도토리의 갯수가 부족합니다.");
    	             System.out.println("이전단계로 돌아가겠습니다");
    	          }
    	       }
    	    }
    }
}




