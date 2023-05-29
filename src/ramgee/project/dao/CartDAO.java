package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDAO {

	//field
	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;
	
	private Connection connection;
	
	//생성자
	public CartDAO() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("CLASS FOR NAME ERR");
		}		
	}
	//주문 생성
	public int insertCart(ProductVO productVO, CartVO cartVO) {
		int result = 0; //0이 반환되면 실패, 1이 반환되면 성공

		//insert, update, delete는 ResultSet 객체가 필요없음
		Connection connection = null;
		PreparedStatement statement = null;
		
        String query = "INSERT INTO cart VALUES()";
        try{
        	connection = DriverManager.getConnection(url, uid, upw);

            // 준비된 문장에 값을 설정합니다.
			statement = connection.prepareStatement(query);
            statement.setInt(1, cartVO.getTotal());
            statement.setString(2, cartVO.getTo_hall());
            

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
	
	// 주문 수정
	public void updateCart(CartVO cartVO) throws SQLException {
        String query = "UPDATE user_cart SET total = ?, to_hall = ?, size = ?, product_count =?  WHERE cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartVO.getTotal());
            statement.setString(2, cartVO.getTo_hall());
            statement.setString(3, cartVO.getSize());
            statement.setInt(4, cartVO.getProduct_count());
            statement.setInt(5, cartVO.getCart_no());

            statement.executeUpdate();  // 쿼리를 실행하여 주문을 업데이트합니다.
        }
    }

    // 3. 주문 삭제
    public void deleteCart(CartVO cartVO) throws SQLException {
        String query = "DELETE FROM user_cart WHERE cart_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartVO.getCart_no());

            statement.executeUpdate();  // 쿼리를 실행하여 주문을 삭제합니다.
        }
    }

    // 4. cart_no 로 오더 조회
    public CartVO findCartById(CartVO cartVO) throws SQLException {
        CartVO cart = null; // 조회받은 CartVO

        String query = "SELECT * FROM USER_ORDER WHERE cart_no = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cartVO.getCart_no());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cart = mapResultSetToCart(resultSet);  // 조회된 결과를 CartVO 객체로 매핑합니다.
                }
            }
        }
        return cart;
    }
 // 5. 모든 주문을 조회
    public List<CartVO> findAllCart() throws SQLException {
        List<CartVO> cart_list = new ArrayList<>();
        String query = "SELECT * FROM user_cart";
        try (
        		PreparedStatement statement = connection.prepareStatement(query);
        		ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
            	
                CartVO cart = mapResultSetToCart(resultSet);  // 조회된 결과를 CartVO 객체로 매핑합니다.
                cart_list.add(cart);
            }
        }catch (Exception e) {
			e.printStackTrace();
			System.out.print("사유 : " + e.getMessage());
		}
        return cart_list;
    }

    // ResultSet을 CartVO 객체로 매핑하는 보조 메서드입니다.
    private CartVO mapResultSetToCart(ResultSet resultSet) throws SQLException {
        int cart_no = resultSet.getInt("cart_no");
        Date cart_date = resultSet.getDate("cart_date");
        int total = resultSet.getInt("total");
        String to_hall = resultSet.getString("to_hall");
        String size = resultSet.getString("size");
        int product_count = resultSet.getInt("product_count");
        int product_no = resultSet.getInt("product_no");
        	
        return new CartVO(cart_no, cart_date, total, to_hall, size, product_count, product_no);
    }
	


}
