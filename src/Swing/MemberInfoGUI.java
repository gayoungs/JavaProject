package Swing;

import ramgee.project.dao.MembersDAO;
import ramgee.project.vo.MembersVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// 전체회원정보창
public class MemberInfoGUI extends JFrame {
    private JTable memberTable;

    public MemberInfoGUI() {
        setTitle("전체 회원 정보 조회");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // 화면 중앙에 창을 표시

        // 회원 정보 조회 컴포넌트 생성
        DefaultTableModel model = new DefaultTableModel();
        memberTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(memberTable);

        // 회원 정보 조회 로직 수행
        populateMemberTable(model); // JTable에 회원 정보를 표시하는 메서드 호출

        // 회원 정보 조회 창 레이아웃 설정
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 설정
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }

    private void populateMemberTable(DefaultTableModel model) {
        MembersDAO dao = new MembersDAO();
        try {
            List<MembersVO> vo = dao.findAllMembers();

            // JTable에 표시할 데이터를 이차원 배열로 변환
            Object[][] data = new Object[vo.size()][5];
            for (int i = 0; i < vo.size(); i++) {
                MembersVO member = vo.get(i);
                data[i][0] = member.getMember_no();
                data[i][1] = member.getId();
                data[i][2] = member.getPw();
                data[i][3] = member.getName();
                data[i][4] = member.getPhone_number();
            }

            // JTable의 열 이름
            String[] columnNames = { "회원번호", "아이디", "비밀번호", "이름", "전화번호" };

            // 데이터 모델에 데이터 설정
            model.setDataVector(data, columnNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 프로그램 시작 시 회원 정보 조회 창 생성
        SwingUtilities.invokeLater(() -> {
            new MemberInfoGUI();
        });
    }
}