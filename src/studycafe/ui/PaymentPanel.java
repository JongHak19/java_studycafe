package studycafe.ui;

import studycafe.AppContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onPaymentSuccess;
    private final ActionListener onBackClick;
    
    private JLabel amountLabel;
    private JTextField cardNumberField;
    private JTextField expiryField;
    private JTextField cvvField;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 18);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    
    public PaymentPanel(AppContext context, ActionListener onPaymentSuccess, ActionListener onBackClick) {
        this.context = context;
        this.onPaymentSuccess = onPaymentSuccess;
        this.onBackClick = onBackClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Payment");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(550, 100, 200, 40);
        this.add(titleLabel);
        
        // Amount display
        amountLabel = new JLabel();
        amountLabel.setFont(labelFont);
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setBounds(500, 150, 300, 30);
        this.add(amountLabel);
        
        // Card number
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(labelFont);
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setBounds(400, 220, 150, 30);
        this.add(cardLabel);
        
        cardNumberField = new JTextField();
        cardNumberField.setFont(fieldFont);
        cardNumberField.setBounds(550, 220, 200, 30);
        this.add(cardNumberField);
        
        // Expiry date
        JLabel expiryLabel = new JLabel("Expiry (MM/YY):");
        expiryLabel.setFont(labelFont);
        expiryLabel.setForeground(Color.WHITE);
        expiryLabel.setBounds(400, 260, 150, 30);
        this.add(expiryLabel);
        
        expiryField = new JTextField();
        expiryField.setFont(fieldFont);
        expiryField.setBounds(550, 260, 200, 30);
        this.add(expiryField);
        
        // CVV
        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(labelFont);
        cvvLabel.setForeground(Color.WHITE);
        cvvLabel.setBounds(400, 300, 150, 30);
        this.add(cvvLabel);
        
        cvvField = new JTextField();
        cvvField.setFont(fieldFont);
        cvvField.setBounds(550, 300, 200, 30);
        this.add(cvvField);
        
        // Pay button
        JButton payButton = new JButton("Pay Now");
        payButton.setFont(new Font("Arial", Font.BOLD, 20));
        payButton.setBackground(buttonColor);
        payButton.setBounds(500, 380, 200, 50);
        payButton.addActionListener(this::onPayClick);
        this.add(payButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
    
    public void updateAmount(int amount) {
        amountLabel.setText("Amount to Pay: " + amount + " won");
    }
    
    private void onPayClick(java.awt.event.ActionEvent e) {
        String cardNumber = cardNumberField.getText().trim();
        String expiry = expiryField.getText().trim();
        String cvv = cvvField.getText().trim();
        
        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all payment details.");
            return;
        }
        
        // Simple validation
        if (cardNumber.length() != 16) {
            JOptionPane.showMessageDialog(this, "Card number must be 16 digits.");
            return;
        }
        
        if (expiry.length() != 5 || !expiry.matches("\\d{2}/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Expiry must be in MM/YY format.");
            return;
        }
        
        if (cvv.length() != 3) {
            JOptionPane.showMessageDialog(this, "CVV must be 3 digits.");
            return;
        }
        
        // Simulate payment processing
        JOptionPane.showMessageDialog(this, "Payment successful!");
        onPaymentSuccess.actionPerformed(e);
    }
    
    public void clearFields() {
        cardNumberField.setText("");
        expiryField.setText("");
        cvvField.setText("");
    }
}
