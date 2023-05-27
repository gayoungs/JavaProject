package ramgee.project.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ramgee.project.db.DBProperties;
import ramgee.project.vo.MembersVO;

public class MembersDAO {
    private Connection connection;

    // 생성자
    public MembersDAO(Connection connection) {
        this.connection = connection;
    }

    // 새로운 회원을 추가합니다.
    public void insertMember(MembersVO member) throws SQLException {
        String query = "INSERT INTO members (member_no, id, pw, name, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // 준비된 문장에 값을 설정합니다.
            statement.setInt(1, member.getMember_no());
            statement.setString(2, member.getId());
            statement.setString(3, member.getPw());
            statement.setString(4, member.getName());
            statement.setInt(5, member.getPhone_number());

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 추가합니다.
        }
    }

    // 기존 회원을 업데이트합니다.
    public void updateMember(MembersVO member) throws SQLException {
        String query = "UPDATE members SET id = ?, pw = ?, name = ?, phone_number = ? WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getId());
            statement.setString(2, member.getPw());
            statement.setString(3, member.getName());
            statement.setInt(4, member.getPhone_number());
            statement.setInt(5, member.getMember_no());

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 업데이트합니다.
        }
    }

    // 회원을 삭제합니다.
    public void deleteMember(int memberNo) throws SQLException {
        String query = "DELETE FROM members WHERE member_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, memberNo);

            statement.executeUpdate();  // 쿼리를 실행하여 회원을 삭제합니다.
        }
    }

    // ID로 회원을 조회합니다.
    public MembersVO getMemberById(String memberId) throws SQLException {
        MembersVO member = null;
        String query = "SELECT * FROM members WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, memberId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    member = mapResultSetToMember(resultSet);  // 조회된 결과를 MembersVO 객체로 매핑합니다.
                }
            }
        }
        return member;
    }

    // 모든 회원을 조회합니다.
    public List<MembersVO> getAllMembers() throws SQLException {
        List<MembersVO> members = new ArrayList<>();
        String query = "SELECT * FROM members";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                MembersVO member = mapResultSetToMember(resultSet);  // 조회된 결과를 MembersVO 객체로 매핑합니다.
                members.add(member);
            }
        }
        return members;
    }

    // ResultSet을 MembersVO 객체로 매핑하는 보조 메서드입니다.
    private MembersVO mapResultSetToMember(ResultSet resultSet) throws SQLException {
        int memberNo = resultSet.getInt("member_no");
        String id = resultSet.getString("id");
        String pw = resultSet.getString("pw");
        String name = resultSet.getString("name");
        int phoneNumber = resultSet.getInt("phone_number");

        return new MembersVO(memberNo, id, pw, name, phoneNumber);
    }
}




