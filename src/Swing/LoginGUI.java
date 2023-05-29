package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField idTextField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("로그인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel idLabel = new JLabel("아이디:");
        idTextField = new JTextField(10);
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("로그인");
        JButton registerButton = new JButton("회원가입");

        // 로그인 버튼 이벤트 처리
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 로그인 로직 처리
                // ...
            }
        });

        // 회원가입 버튼 이벤트 처리
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 창 열기
                openMembersGUI();
                dispose();
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets.top = 20;
        panel.add(loginButton, gbc);

        // 회원가입 버튼 왼쪽 정렬 및 핑크 배경 설정
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 수정된 부분
        gbc.anchor = GridBagConstraints.CENTER; // 수정된 부분
        gbc.insets.top = 5;
        panel.add(registerButton, gbc);

        panel.setBackground(Color.PINK);

        add(panel);

        setVisible(true);
    }

    private void openMembersGUI() {
        MembersGUI membersGUI = new MembersGUI();
        membersGUI.setVisible(true);
    }

    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
    }
}