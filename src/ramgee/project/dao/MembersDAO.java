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

public class MembersDAO {
	//데이터 베이스 연결을 해주는 역할(DAO)

	//멤버변수
	private String url = DBProperties.URL;
	private String uid = DBProperties.UID;
	private String upw = DBProperties.UPW;

	
    private Connection connection;

    // 생성자
    public MembersDAO() {
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
			connection = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
			System.out.println("CLASS FOR NAME ERR");
			e.printStackTrace();
		}
    }
    
    
    // 1. 회원가입
    public int insertMember(MembersVO membersVO) {
    	int result = 0; //0이 반환되면 실패, 1이 반환되면 성공

		//insert, update, delete는 ResultSet 객체가 필요없음
		Connection connection = null;
		PreparedStatement statement = null;
		
        String query = "INSERT INTO members (member_no, id, pw, name, phone_number) VALUES (MEMBER_NO_SEQ.NEXTVAL, ?, ?, ?, ?)";
        try{
        	connection = DriverManager.getConnection(url, uid, upw);

            // 준비된 문장에 값을 설정합니다.
			statement = connection.prepareStatement(query);
            statement.setString(1, membersVO.getId());
            statement.setString(2, membersVO.getPw());
            statement.setString(3, membersVO.getName());
            statement.setString(4, membersVO.getPhone_number());
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
    public void updateMember(MembersVO membersVO) throws SQLException {
        String query = "UPDATE members SET id = ?, pw = ?, name = ?, phone_number = ? WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membersVO.getId());
            statement.setString(2, membersVO.getPw());
            statement.setString(3, membersVO.getName());
            statement.setString(4, membersVO.getPhone_number());
            statement.setInt(5, membersVO.getMember_no());

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 업데이트합니다.
        }
    }

    // 3. 회원 삭제
    public void deleteMember(String memberId) throws SQLException {
        String query = "DELETE FROM members WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, memberId);

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 삭제합니다.
        }
    }

    // 4. ID로 회원을 조회
    public MembersVO findMemberById(String id) throws SQLException {
        MembersVO member = null;
        String query = "SELECT * FROM members WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    member = mapResultSetToMember(resultSet);  // 조회된 결과를 MembersVO 객체로 매핑합니다.
                }
            }
        }
        return member;
    }
 // 5. 모든 회원을 조회
    public List<MembersVO> findAllMembers() throws SQLException {
        List<MembersVO> members = new ArrayList<>();
        String query = "SELECT * FROM members";
        try (
        		PreparedStatement statement = connection.prepareStatement(query);
        		ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
            	
                MembersVO member = mapResultSetToMember(resultSet);  // 조회된 결과를 MembersVO 객체로 매핑합니다.
                members.add(member);
            }
        }catch (Exception e) {
			e.printStackTrace();
			System.out.print("사유 : " + e.getMessage());
		}
        return members;
    }

    // ResultSet을 MembersVO 객체로 매핑하는 보조 메서드입니다.
    private MembersVO mapResultSetToMember(ResultSet resultSet) throws SQLException {
        int memberNo = resultSet.getInt("member_no");
        String id = resultSet.getString("id");
        String pw = resultSet.getString("pw");
        String name = resultSet.getString("name");
        String phoneNumber = resultSet.getString("phone_number");
        	
        return new MembersVO(memberNo, id, pw, name, phoneNumber);
    }
    
    public static void main(String[] args) throws Exception {
    	Scanner scan = new Scanner(System.in);
    	
    	MembersDAO membersDAO = new MembersDAO();
    	
    	while(true) {
    		System.out.println("[1. 회원가입, 2.회원수정, 3. 회원삭제, 4. 아이디로 회원조회, 5. 전체회원조회 ]");
    		System.out.print("메뉴>");
			
			int menu = scan.nextInt();
			if(menu==1) {
				System.out.println("============회원가입============");
				MembersVO membersVO = new MembersVO();
				System.out.print("아이디:");
				String id = scan.next();
				System.out.print("비밀번호:");
				String pw = scan.next();
				System.out.print("닉네임:");
				String name = scan.next();
				System.out.print("연락처:");
				String phone_number = scan.next();
				
				membersVO.setId(id);
				membersVO.setMember_no(2);
				membersVO.setName(name);
				membersVO.setPhone_number(phone_number);
				membersVO.setPw(pw);
				
				membersDAO.insertMember(membersVO);
				System.out.println(id + " 회원가입 성공");
			}else if(menu==2) {
				System.out.println("============회원수정============");
				System.out.print("수정할 아이디:");
				String id = scan.next();
				MembersVO membersVO = new MembersVO();
				membersVO = membersDAO.findMemberById(id);
				System.out.print("새 아이디:");
				String new_id = scan.next();
				System.out.print("새 비밀번호:");
				String new_pw = scan.next();
				System.out.print("새 닉네임:");
				String new_name = scan.next();
				System.out.print("새 연락처:");
				String new_phone_number = scan.next();
				membersVO.setId(new_id);
				membersVO.setName(new_name);
				membersVO.setPw(new_pw);
				membersVO.setPhone_number(new_phone_number);
				membersDAO.updateMember(membersVO);
				System.out.println(id + " 수정 완료");
				System.out.println("수정 내용:");

				membersVO = membersDAO.findMemberById(new_id);
				System.out.println(membersVO.toString());
			}else if(menu==3) {
				System.out.println("============회원삭제============");
				System.out.print("삭제할 아이디 입력:");
				String id = scan.next();
				membersDAO.deleteMember(id);
				System.out.println(id + " 삭제 완료");
			}else if(menu==4) {
				System.out.println("============아이디로 회원조회============");
				
				System.out.print("조회할 아이디 입력:");
				String id = scan.next();
				MembersVO membersVO = new MembersVO();
				membersVO = membersDAO.findMemberById(id);
				System.out.println(membersVO.toString());
			}else if(menu==5) {
				System.out.println("============전체회원조회============");
				List<MembersVO> list = membersDAO.findAllMembers();
				for (MembersVO member:list) {
					System.out.println(member.toString());
				}
				
			}else {
				System.out.println("잘못된 입력입니다.");
			}
    	}
    }
}




