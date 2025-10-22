package studycafe.ui;

import studycafe.AppContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TicketSelectionPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onTicketSelect;
    private final ActionListener onBackClick;
    
    private JRadioButton hourlyTicket;
    private JRadioButton dailyTicket;
    private JRadioButton postPayment;
    private ButtonGroup ticketGroup;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 20);
    private Font buttonFont = new Font("Arial", Font.BOLD, 18);
    
    public TicketSelectionPanel(AppContext context, ActionListener onTicketSelect, ActionListener onBackClick) {
        this.context = context;
        this.onTicketSelect = onTicketSelect;
        this.onBackClick = onBackClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Select Ticket Type");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(500, 100, 300, 40);
        this.add(titleLabel);
        
        // Selected seat info
        int selectedSeat = context.getSelectedSeat();
        JLabel seatInfoLabel = new JLabel("Selected Seat: " + selectedSeat);
        seatInfoLabel.setFont(labelFont);
        seatInfoLabel.setForeground(Color.WHITE);
        seatInfoLabel.setBounds(500, 150, 200, 30);
        this.add(seatInfoLabel);
        
        // Ticket options
        ticketGroup = new ButtonGroup();
        
        // Hourly ticket (1 hour = 1000 won)
        hourlyTicket = new JRadioButton("Hourly Ticket - 1,000 won/hour");
        hourlyTicket.setFont(buttonFont);
        hourlyTicket.setForeground(Color.WHITE);
        hourlyTicket.setBackground(backColor);
        hourlyTicket.setBounds(400, 250, 300, 30);
        this.add(hourlyTicket);
        ticketGroup.add(hourlyTicket);
        
        // Daily ticket (8 hours = 6,000 won, 25% discount)
        dailyTicket = new JRadioButton("Daily Ticket - 6,000 won/8 hours (25% off)");
        dailyTicket.setFont(buttonFont);
        dailyTicket.setForeground(Color.WHITE);
        dailyTicket.setBackground(backColor);
        dailyTicket.setBounds(400, 300, 400, 30);
        this.add(dailyTicket);
        ticketGroup.add(dailyTicket);
        
        // Post payment (pay after use, 20% more expensive)
        postPayment = new JRadioButton("Post Payment - 1,200 won/hour (20% more)");
        postPayment.setFont(buttonFont);
        postPayment.setForeground(Color.WHITE);
        postPayment.setBackground(backColor);
        postPayment.setBounds(400, 350, 400, 30);
        this.add(postPayment);
        ticketGroup.add(postPayment);
        
        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.setBackground(buttonColor);
        confirmButton.setBounds(500, 450, 200, 50);
        confirmButton.addActionListener(this::onConfirmClick);
        this.add(confirmButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
    
    private void onConfirmClick(java.awt.event.ActionEvent e) {
        String ticketType = getSelectedTicketType();
        if (ticketType == null) {
            JOptionPane.showMessageDialog(this, "Please select a ticket type.");
            return;
        }
        
        // Store ticket type in context
        context.setCurrentUserType(ticketType);
        
        // Proceed to next step
        onTicketSelect.actionPerformed(e);
    }
    
    private String getSelectedTicketType() {
        if (hourlyTicket.isSelected()) {
            return "hourly";
        } else if (dailyTicket.isSelected()) {
            return "daily";
        } else if (postPayment.isSelected()) {
            return "postpayment";
        }
        return null;
    }
    
    public void clearSelection() {
        ticketGroup.clearSelection();
    }
}
