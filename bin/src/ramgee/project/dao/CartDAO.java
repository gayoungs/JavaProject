package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CartDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;
    private MemberDAO memberDAO;
    private OrderDAO orderDAO = new OrderDAO();

    public void addCart(CartVO cartVO) {
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO carts (cart_no, member_no) VALUES (cart_no_seq.NEXTVAL, ?)")) {
            statement.setInt(1, cartVO.getMember_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateCart(CartVO cartVO) {
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("UPDATE carts SET member_no = ? WHERE cart_no = ?")) {
            statement.setInt(1, cartVO.getMember_no());
            statement.setInt(2, cartVO.getCart_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteCart(int cart_no) {
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM carts WHERE cart_no = ?")) {
            statement.setInt(1, cart_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public CartVO findCartByCartNo(int cart_no) {
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM carts WHERE cart_no = ?")) {
            statement.setInt(1, cart_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CartVO cartVO = new CartVO(
                            resultSet.getInt("cart_no"),
                            resultSet.getInt("member_no"),
                            orderDAO.findOrdersByCartNo(cart_no)
                    );
                    return cartVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public CartVO findCartByMemberNo(int member_no) {
        String sql = "SELECT * FROM carts WHERE member_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CartVO cartVO = new CartVO(
                            resultSet.getInt("cart_no"),
                            resultSet.getInt("member_no"),
                            null
                    );

                    List<OrderVO> orders = orderDAO.findOrdersByCartNo(resultSet.getInt("cart_no"));
                    cartVO.setOrders(orders);

                    return cartVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }



    public List<CartVO> findAllCarts() {
        List<CartVO> cart_list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM carts");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int cart_no = resultSet.getInt("cart_no");
                int member_no = resultSet.getInt("member_no");

                List<OrderVO> orders = orderDAO.findOrdersByCartNo(cart_no);

                CartVO cartVO = new CartVO(cart_no, member_no, orders);
                cart_list.add(cartVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cart_list;
    }

    public List<CartOrderVO> findAllCartOrders() {
        List<CartOrderVO> cartOrderList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("SELECT cart_no, order_no FROM cart_order");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int cart_no = resultSet.getInt("cart_no");
                int order_no = resultSet.getInt("order_no");

                CartOrderVO cartOrderVO = new CartOrderVO(cart_no, order_no);
                cartOrderList.add(cartOrderVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cartOrderList;
    }

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
