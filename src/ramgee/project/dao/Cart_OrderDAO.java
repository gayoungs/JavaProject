package ramgee.project.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.*;


public class Cart_OrderDAO {
		
	
	//field
	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;
	
	private Connection connection;
	
	//생성자
	public Cart_OrderDAO() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("CLASS FOR NAME ERR");
		}
	}
	//주문 생성
	public int insertCartOrder(OrderVO orderVO, CartVO cartVO) {
		int result = 0; //0이 반환되면 실패, 1이 반환되면 성공

		//insert, update, delete는 ResultSet 객체가 필요없음
		Connection connection = null;
		PreparedStatement statement = null;
		
        String query = "INSERT INTO cart_order VALUES (?, ?)";
        try{
        	connection = DriverManager.getConnection(url, uid, upw);

            // 준비된 문장에 값을 설정합니다.
			statement = connection.prepareStatement(query);
            statement.setInt(1, cartVO.getCart_no());
            statement.setInt(2, orderVO.getOrder_no());

            

            result = statement.executeUpdate();  // 쿼리를 실행하여 주문을 추가합니다.
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

    // 4. order_no 로 오더 조회
    public Cart_OrderVO findCartOrderByOrderNo(OrderVO orderVO) throws SQLException {
    	Cart_OrderVO order = null; // 조회받은 OrderVO

        String query = "SELECT * FROM cart_order WHERE order_no = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderVO.getOrder_no());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = mapResultSetToCartOrder(resultSet);  // 조회된 결과를 OrderVO 객체로 매핑합니다.
                }
            }
        }
        return order;
    }
    public Cart_OrderVO findCartOrderByCartNo(CartVO cartVO) throws SQLException {
    	Cart_OrderVO order = null; // 조회받은 OrderVO

        String query = "SELECT * FROM cart_order WHERE cart_no = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartVO.getCart_no());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = mapResultSetToCartOrder(resultSet);  // 조회된 결과를 OrderVO 객체로 매핑합니다.
                }
            }
        }
        return order;
    }
 // 5. 모든 주문을 조회
    public List<Cart_OrderVO> findAllCartOrder() throws SQLException {
        List<Cart_OrderVO> cart_order_list = new ArrayList<>();
        String query = "SELECT * FROM cart_order";
        try (
        		PreparedStatement statement = connection.prepareStatement(query);
        		ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
            	
                Cart_OrderVO cart_order = mapResultSetToCartOrder(resultSet);  // 조회된 결과를 OrderVO 객체로 매핑합니다.
                cart_order_list.add(cart_order);
            }
        }catch (Exception e) {
			e.printStackTrace();
			System.out.print("사유 : " + e.getMessage());
		}
        return cart_order_list;
    }

    // ResultSet을 OrderVO 객체로 매핑하는 보조 메서드입니다.
    private Cart_OrderVO mapResultSetToCartOrder(ResultSet resultSet) throws SQLException {
        int order_no = resultSet.getInt("order_no");
        int cart_no = resultSet.getInt("cart_no");
        
        return new Cart_OrderVO(order_no, cart_no);
    }
	
	//주문 메서드(포장여부,hot/ice, size 등)
//	public List<OrderVO> addOrdertoCart(ProductVO productVO, CartVO cartVO) {
//		
//		List<MenuVO> cart = new ArrayList<>();
//		
//		//수정 필요
//		String sql = "INSERT INTO ORDER_LIST VALUES (?, ?, SYSDATE, ?, ?, ?, ?)";
//		
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		int result = 0;
//		
//		try {
//			
//			conn = DriverManager.getConnection(url, uid, upw);
//			
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, menu.getProduct_name());
//			pstmt.setInt(2, menu.getPrice());
//			
//			result = pstmt.executeUpdate();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//				pstmt.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		
//		return cart;
//	}
	
	
	//주문 내역 메서드
	

}
