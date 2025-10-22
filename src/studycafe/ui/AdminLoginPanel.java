package studycafe.ui;

import studycafe.AppContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminLoginPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onLoginSuccess;
    private final ActionListener onBackClick;
    
    private JTextField adminIdField;
    private JPasswordField passwordField;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 20);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    
    public AdminLoginPanel(AppContext context, ActionListener onLoginSuccess, ActionListener onBackClick) {
        this.context = context;
        this.onLoginSuccess = onLoginSuccess;
        this.onBackClick = onBackClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(550, 150, 200, 40);
        this.add(titleLabel);
        
        // Admin ID label
        JLabel adminIdLabel = new JLabel("Admin ID:");
        adminIdLabel.setFont(labelFont);
        adminIdLabel.setForeground(Color.WHITE);
        adminIdLabel.setBounds(450, 250, 100, 30);
        this.add(adminIdLabel);
        
        // Admin ID input field
        adminIdField = new JTextField();
        adminIdField.setFont(fieldFont);
        adminIdField.setBounds(550, 250, 200, 30);
        this.add(adminIdField);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(450, 300, 100, 30);
        this.add(passwordLabel);
        
        // Password input field
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBounds(550, 300, 200, 30);
        this.add(passwordField);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(buttonColor);
        loginButton.setBounds(500, 350, 200, 40);
        loginButton.addActionListener(this::onLoginClick);
        this.add(loginButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
        
        // Default credentials info
        JLabel infoLabel = new JLabel("Default: admin / admin123");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setForeground(Color.YELLOW);
        infoLabel.setBounds(450, 400, 300, 30);
        this.add(infoLabel);
    }
    
    private void onLoginClick(java.awt.event.ActionEvent e) {
        String adminId = adminIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (adminId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter admin ID and password.");
            return;
        }
        
        // Simple admin authentication (in real app, this would check database)
        if ("admin".equals(adminId) && "admin123".equals(password)) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            onLoginSuccess.actionPerformed(e);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials.");
        }
    }
    
    public void clearFields() {
        adminIdField.setText("");
        passwordField.setText("");
    }
}
