package studycafe.ui;

import studycafe.AppContext;
import studycafe.model.Member;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberLoginPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onLoginSuccess;
    private final ActionListener onBackClick;
    private final ActionListener onJoinClick;
    
    private JTextField idField;
    private JPasswordField passwordField;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 20);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    
    public MemberLoginPanel(AppContext context, ActionListener onLoginSuccess, 
                           ActionListener onBackClick, ActionListener onJoinClick) {
        this.context = context;
        this.onLoginSuccess = onLoginSuccess;
        this.onBackClick = onBackClick;
        this.onJoinClick = onJoinClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Member Login");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(550, 150, 200, 40);
        this.add(titleLabel);
        
        // ID label
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(labelFont);
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(450, 250, 50, 30);
        this.add(idLabel);
        
        // ID input field
        idField = new JTextField();
        idField.setFont(fieldFont);
        idField.setBounds(500, 250, 200, 30);
        this.add(idField);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(450, 300, 100, 30);
        this.add(passwordLabel);
        
        // Password input field
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBounds(500, 300, 200, 30);
        this.add(passwordField);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(buttonColor);
        loginButton.setBounds(500, 350, 200, 40);
        loginButton.addActionListener(this::onLoginClick);
        this.add(loginButton);
        
        // Join button
        JButton joinButton = new JButton("Join");
        joinButton.setFont(new Font("Arial", Font.BOLD, 16));
        joinButton.setBackground(buttonColor);
        joinButton.setBounds(500, 400, 100, 30);
        joinButton.addActionListener(onJoinClick);
        this.add(joinButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
    
    private void onLoginClick(java.awt.event.ActionEvent e) {
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID and password.");
            return;
        }
        
        Member member = context.getAuthService().login(id, password);
        if (member != null) {
            context.setCurrentUserId(member.getId());
            context.setCurrentUserType("member");
            JOptionPane.showMessageDialog(this, "Login successful!");
            onLoginSuccess.actionPerformed(e);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid ID or password.");
        }
    }
    
    public void clearFields() {
        idField.setText("");
        passwordField.setText("");
    }
}
