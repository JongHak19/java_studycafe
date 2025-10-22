package studycafe.ui;

import studycafe.AppContext;
import studycafe.model.Seat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SeatConfirmationPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onCheckout;
    private final ActionListener onBackClick;
    
    private Seat selectedSeat;
    private String seatUserId;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 20);
    private Font infoFont = new Font("Arial", Font.PLAIN, 16);
    
    public SeatConfirmationPanel(AppContext context, ActionListener onCheckout, ActionListener onBackClick) {
        this.context = context;
        this.onCheckout = onCheckout;
        this.onBackClick = onBackClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Seat Information");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(500, 100, 300, 40);
        this.add(titleLabel);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
    
    public void updateSeatInfo(Seat seat, String userId) {
        this.selectedSeat = seat;
        this.seatUserId = userId;
        
        // Clear existing components (except back button)
        Component[] components = getComponents();
        for (Component comp : components) {
            if (!(comp instanceof JButton && ((JButton) comp).getText().equals("Back"))) {
                remove(comp);
            }
        }
        
        int seatNumber = seat.getSeatNumber();
        boolean isOccupied = seat.isOccupied();
        String currentUserId = context.getCurrentUserId();
        
        // Seat number
        JLabel seatNumberLabel = new JLabel("Seat Number: " + seatNumber);
        seatNumberLabel.setFont(labelFont);
        seatNumberLabel.setForeground(Color.WHITE);
        seatNumberLabel.setBounds(500, 200, 200, 30);
        this.add(seatNumberLabel);
        
        if (isOccupied) {
            // Occupied seat
            JLabel statusLabel = new JLabel("Status: OCCUPIED");
            statusLabel.setFont(labelFont);
            statusLabel.setForeground(Color.RED);
            statusLabel.setBounds(500, 240, 200, 30);
            this.add(statusLabel);
            
            JLabel userLabel = new JLabel("User: " + userId);
            userLabel.setFont(infoFont);
            userLabel.setForeground(Color.WHITE);
            userLabel.setBounds(500, 280, 200, 30);
            this.add(userLabel);
            
            if (currentUserId != null && currentUserId.equals(userId)) {
                // Current user's seat - show checkout option
                JLabel messageLabel = new JLabel("This is your seat. Would you like to checkout?");
                messageLabel.setFont(infoFont);
                messageLabel.setForeground(Color.YELLOW);
                messageLabel.setBounds(400, 320, 400, 30);
                this.add(messageLabel);
                
                JButton checkoutButton = new JButton("Checkout");
                checkoutButton.setFont(new Font("Arial", Font.BOLD, 20));
                checkoutButton.setBackground(Color.RED);
                checkoutButton.setForeground(Color.WHITE);
                checkoutButton.setBounds(500, 380, 200, 50);
                checkoutButton.addActionListener(e -> {
                    int result = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to checkout?", 
                        "Checkout Confirmation", 
                        JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        onCheckout.actionPerformed(e);
                    }
                });
                this.add(checkoutButton);
            } else {
                // Other user's seat
                JLabel messageLabel = new JLabel("This seat is occupied by another user.");
                messageLabel.setFont(infoFont);
                messageLabel.setForeground(Color.ORANGE);
                messageLabel.setBounds(400, 320, 400, 30);
                this.add(messageLabel);
                
                JButton selectAnotherButton = new JButton("Select Another Seat");
                selectAnotherButton.setFont(new Font("Arial", Font.BOLD, 18));
                selectAnotherButton.setBackground(buttonColor);
                selectAnotherButton.setBounds(450, 380, 250, 50);
                selectAnotherButton.addActionListener(onBackClick);
                this.add(selectAnotherButton);
            }
        } else {
            // Available seat
            JLabel statusLabel = new JLabel("Status: AVAILABLE");
            statusLabel.setFont(labelFont);
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setBounds(500, 240, 200, 30);
            this.add(statusLabel);
            
            JLabel messageLabel = new JLabel("This seat is available for booking.");
            messageLabel.setFont(infoFont);
            messageLabel.setForeground(Color.WHITE);
            messageLabel.setBounds(400, 280, 400, 30);
            this.add(messageLabel);
            
            JButton bookButton = new JButton("Book This Seat");
            bookButton.setFont(new Font("Arial", Font.BOLD, 20));
            bookButton.setBackground(Color.GREEN);
            bookButton.setForeground(Color.WHITE);
            bookButton.setBounds(500, 350, 200, 50);
            bookButton.addActionListener(e -> {
                context.setSelectedSeat(seatNumber);
                onBackClick.actionPerformed(e);
            });
            this.add(bookButton);
        }
        
        revalidate();
        repaint();
    }
}
