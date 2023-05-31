package ramgee.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TestDB {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;

    private Connection connection;
    public TestDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, uid, upw);
            System.out.println("success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
        TestDB testDB = new TestDB();
    }
}
