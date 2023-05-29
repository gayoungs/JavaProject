package ramgee.project.vo;

import java.sql.SQLException;

import ramgee.project.dao.MembersDAO;
import ramgee.project.vo.MembersVO;

public class ramge {
	public void Test2(String id, String pw){
		MembersDAO dao = new MembersDAO();
		MembersVO vo = new MembersVO();
		try {
			dao.findMemberById(id);
			String dbId = vo.getId();
			String dbPw = vo.getPw();
			System.out.println("db_id : " + dbId);
			System.out.println("db_pw : " + dbPw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
