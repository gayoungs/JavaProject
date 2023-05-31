package ramgee.project.dao;

import ramgee.project.db.DBProperties;
import ramgee.project.vo.AuthorityVO;
import ramgee.project.vo.MemberVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private String url = DBProperties.URL;
    private String uid = DBProperties.UID;
    private String upw = DBProperties.UPW;

    private Connection connection;

    private AuthorityDAO authorityDAO = new AuthorityDAO();
    public void addMember(MemberVO member) {
        String sql = "INSERT INTO members (member_no, username, password, name, email, phoneNumber, authority_no) VALUES (member_no_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getUsername());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            statement.setString(5, member.getPhoneNumber());
            statement.setInt(6, member.getAuthorityVO().getAuthority_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateMember(MemberVO member) {
        String sql = "UPDATE members SET username = ?, password = ?, name = ?, email = ?, phoneNumber = ?, authority_no = ? WHERE member_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getUsername());
            statement.setString(2, member.getPassword());
            statement.setString(3, member.getName());
            statement.setString(4, member.getEmail());
            statement.setString(5, member.getPhoneNumber());
            statement.setInt(6, member.getAuthorityVO().getAuthority_no());
            statement.setInt(7, member.getMember_no());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteMember(int member_no) {
        String sql = "DELETE FROM members WHERE member_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member_no);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public MemberVO findMemberByUsername(String username) {
        String sql = "SELECT * FROM members WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MemberVO memberVO = new MemberVO(
                            resultSet.getInt("member_no"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phoneNumber"),
                            null
                    );

                    int authority_no = resultSet.getInt("authority_no");
                    AuthorityVO authorityVO = authorityDAO.findAuthorityByAuthorityNo(authority_no);
                    memberVO.setAuthorityVO(authorityVO);

                    return memberVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public MemberVO findMemberByMemberNo(int member_no) {
        String sql = "SELECT * FROM members WHERE member_no = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member_no);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MemberVO memberVO = new MemberVO(
                            resultSet.getInt("member_no"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phoneNumber"),
                            null
                    );

                    int authority_no = resultSet.getInt("authority_no");
                    AuthorityVO authorityVO = authorityDAO.findAuthorityByAuthorityNo(authority_no);
                    memberVO.setAuthorityVO(authorityVO);

                    return memberVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    public List<MemberVO> findAllMembers() {
        List<MemberVO> member_list = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                MemberVO memberVO = new MemberVO(
                        resultSet.getInt("member_no"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        null
                );
                int authority_no = resultSet.getInt("authority_no");

                AuthorityVO authorityVO = authorityDAO.findAuthorityByAuthorityNo(authority_no);
                memberVO.setAuthorityVO(authorityVO);

                member_list.add(memberVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return member_list;
    }

    public MemberVO findMemberByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM members WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(url, uid, upw);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int member_no = resultSet.getInt("member_no");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    int authority_no = resultSet.getInt("authority_no");

                    AuthorityVO authorityVO = authorityDAO.findAuthorityByAuthorityNo(authority_no);
                    MemberVO memberVO = new MemberVO(member_no, username, password, name, email, phoneNumber, authorityVO);

                    return memberVO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
