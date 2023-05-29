package Swing;

import ramgee.project.vo.MembersVO;

import javax.swing.*;
import java.awt.*;

// 회원 정보 조회 창
public class MemberInfoDetailGUI extends JFrame {
    private MembersVO member;

    public MemberInfoDetailGUI(MembersVO member) {
        this.member = member;
        setTitle("회원 정보 조회");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // 화면 중앙에 창을 표시

        // 회원 정보 조회 컴포넌트 생성
        JLabel nameLabel = new JLabel("닉네임: " + member.getName());
        JLabel idLabel = new JLabel("아이디: " + member.getId());

        // 회원 정보 조회 창 레이아웃 설정
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 설정
        panel.add(nameLabel);
        panel.add(idLabel);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Scanner를 사용하여 닉네임과 아이디 입력 받기
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog("닉네임을 입력하세요:");
            String id = JOptionPane.showInputDialog("아이디를 입력하세요:");

            // 회원 정보 생성
            MembersVO member = new MembersVO();
            member.setName(name);
            member.setId(id);

            // 회원 정보 조회 창 생성
            new MemberInfoDetailGUI(member);
        });
    }
}





