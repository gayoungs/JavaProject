package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.OrderProductVO;
import ramgee.project.vo.OrderVO;
import ramgee.project.vo.ProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;
    private Connection connection;

    public OrderDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(OrderVO orderVO) {
        String sql = "INSERT INTO orders (order_no, cart_no, order_date, total_amount, status) VALUES (order_no_seq.NEXTVAL, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderVO.getCart_no());
            statement.setDate(2, orderVO.getOrderDate());
            statement.setDouble(3, orderVO.getTotalAmount());
            statement.setString(4, orderVO.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateOrder(OrderVO orderVO) {
        String sql = "UPDATE orders SET cart_no = ?, order_date = ?, total_amount = ?, status = ? WHERE order_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderVO.getCart_no());
            statement.setDate(2, orderVO.getOrderDate());
            statement.setDouble(3, orderVO.getTotalAmount());
            statement.setString(4, orderVO.getStatus());
            statement.setInt(5, orderVO.getOrder_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteOrder(int order_no) {
        String sql = "DELETE FROM orders WHERE order_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public OrderVO findOrderByOrderNo(int order_no) {
        String sql = "SELECT * FROM orders WHERE order_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    OrderVO order = new OrderVO(
                            resultSet.getInt("order_no"),
                            resultSet.getInt("cart_no"),
                            resultSet.getDate("order_date"),
                            resultSet.getDouble("total_amount"),
                            resultSet.getString("status"),
                            findProductsByOrderNo(order_no)
                    );
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    List<OrderVO> findOrdersByCartNo(int cart_no) {
        List<OrderVO> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement("SELECT o.order_no, o.cart_no, o.order_date, o.total_amount, o.status " +
                     "FROM orders o " +
                     "JOIN cart_order co ON o.order_no = co.order_no " +
                     "WHERE co.cart_no = ?")) {
            statement.setInt(1, cart_no);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int order_no = resultSet.getInt("order_no");
                    int cartNo = resultSet.getInt("cart_no");
                    Date orderDate = resultSet.getDate("order_date");
                    double totalAmount = resultSet.getDouble("total_amount");
                    String status = resultSet.getString("status");

                    List<ProductVO> products = findProductsByOrderNo(order_no);

                    OrderVO orderVO = new OrderVO(order_no, cartNo, orderDate, totalAmount, status, products);
                    orders.add(orderVO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return orders;
    }

    public List<OrderVO> findAllOrders() {
        List<OrderVO> order_list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int order_no = resultSet.getInt("order_no");
                int cart_no = resultSet.getInt("cart_no");
                Date orderDate = resultSet.getDate("order_date");
                double totalAmount = resultSet.getDouble("total_amount");
                String status = resultSet.getString("status");
                List<ProductVO> products = findProductsByOrderNo(order_no);
                OrderVO orderVO = new OrderVO(order_no, cart_no, orderDate, totalAmount, status, products);
                order_list.add(orderVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return order_list;
    }

    private List<ProductVO> findProductsByOrderNo(int order_no) {
        List<ProductVO> product_list = new ArrayList<>();
        String sql = "SELECT p.product_no, p.name, p.price, p.description, p.amount " +
                "FROM products p " +
                "JOIN order_product op ON p.product_no = op.product_no " +
                "WHERE op.order_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_no);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int product_no = resultSet.getInt("product_no");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    int amount = resultSet.getInt("amount");

                    ProductVO productVO = new ProductVO(product_no, name, price, description, amount);
                    product_list.add(productVO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return product_list;
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

    public List<OrderProductVO> findAllOrderProducts() {
        List<OrderProductVO> orderProducts = new ArrayList<>();
        String sql = "SELECT order_no, product_no, quantity FROM order_product";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int order_no = resultSet.getInt("order_no");
                int product_no = resultSet.getInt("product_no");
                int quantity = resultSet.getInt("quantity");

                OrderProductVO orderProductVO = new OrderProductVO(order_no, product_no, quantity);
                orderProducts.add(orderProductVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return orderProducts;
    }
}
