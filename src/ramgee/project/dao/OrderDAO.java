package ramgee.project.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.OrderVO;

public class OrderDAO {
	
	//field
	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;
	
	//생성자
	public OrderDAO() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("CLASS FOR NAME ERR");
		}		
	}
	
	//

}
