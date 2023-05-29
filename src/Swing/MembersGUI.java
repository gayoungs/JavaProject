package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ramgee.project.dao.MembersDAO;
import ramgee.project.vo.MembersVO;

public class MembersGUI extends JFrame {
    private JTextField idTextField;
    private JPasswordField pwPasswordField;
    private JTextField nameTextField;
    private JTextField phoneTextField;

    public MembersGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("회원 관리");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Use GridBagLayout
        mainPanel.setBackground(Color.PINK); // 핑크 배경으로 설정

        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints object
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("아이디:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        idTextField = new JTextField();
        idTextField.setPreferredSize(new Dimension(200, 25)); // Increase the preferred size
        mainPanel.add(idTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("비밀번호:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pwPasswordField = new JPasswordField();
        pwPasswordField.setPreferredSize(new Dimension(200, 25)); // Increase the preferred size
        mainPanel.add(pwPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("닉네임:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(200, 25)); // Increase the preferred size
        mainPanel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("전화번호:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        phoneTextField = new JTextField();
        phoneTextField.setPreferredSize(new Dimension(200, 25)); // Increase the preferred size
        mainPanel.add(phoneTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the button
        JButton registerButton = new JButton("회원 가입");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원 가입 로직 처리
                String id = idTextField.getText();
                String pw = new String(pwPasswordField.getPassword());
                String name = nameTextField.getText();
                String phone = phoneTextField.getText();
                
                MembersVO membersVO = new MembersVO();
                membersVO.setId(id);
                membersVO.setPw(pw);
                membersVO.setName(name);
                membersVO.setPhone_number(phone);
                
                MembersDAO membersDAO = new MembersDAO();
                int result = membersDAO.insertMember(membersVO);
                
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "회원가입을 성공했습니다.");
                    idTextField.setText("");
                    pwPasswordField.setText("");
                    nameTextField.setText("");
                    phoneTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "회원가입을 실패했습니다.");
                }
            }
        });
        mainPanel.add(registerButton, gbc);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MembersGUI membersGUI = new MembersGUI();
                membersGUI.setVisible(true);
            }
        });
    }
}