package studycafe.ui;

import studycafe.AppContext;
import studycafe.model.Seat;
import studycafe.model.Payment;
import studycafe.service.PaymentService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private AppContext context;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private SeatPanel seatPanel;
    private ChoicePanel choicePanel;
    private MemberLoginPanel memberLoginPanel;
    private TicketSelectionPanel ticketSelectionPanel;
    private PaymentPanel paymentPanel;
    private SeatConfirmationPanel seatConfirmationPanel;
    private AdminLoginPanel adminLoginPanel;
    private AdminPanel adminPanel;
    
    public MainFrame() {
        initializeContext();
        initializeUI();
        setupPanels();
        startServices();
    }
    
    private void initializeContext() {
        this.context = new AppContext();
    }
    
    private void initializeUI() {
        setTitle("Study Cafe");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Center on screen
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                   (windowSize.height - frameSize.height) / 2 - 10);
        
        // CardLayout setup
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(0x0E1D35));
        add(mainPanel);
    }
    
    private void setupPanels() {
        // Create seat panel
        seatPanel = new SeatPanel(context, this::onSeatClick, this::onAdminClick);
        mainPanel.add(seatPanel, "SEAT_PANEL");
        
        // Create member/non-member choice panel
        choicePanel = new ChoicePanel(context, this::onMemberClick, this::onNonMemberClick, this::onBackToSeat);
        mainPanel.add(choicePanel, "CHOICE_PANEL");
        
        // Create member login panel
        memberLoginPanel = new MemberLoginPanel(context, this::onLoginSuccess, this::onBackToChoice, this::onJoinClick);
        mainPanel.add(memberLoginPanel, "MEMBER_LOGIN_PANEL");
        
        // Create ticket selection panel
        ticketSelectionPanel = new TicketSelectionPanel(context, this::onTicketSelected, this::onBackToChoice);
        mainPanel.add(ticketSelectionPanel, "TICKET_SELECTION_PANEL");
        
        // Create payment panel
        paymentPanel = new PaymentPanel(context, this::onPaymentSuccess, this::onBackToTicketSelection);
        mainPanel.add(paymentPanel, "PAYMENT_PANEL");
        
        // Create seat confirmation panel
        seatConfirmationPanel = new SeatConfirmationPanel(context, this::onCheckout, this::onBackToSeat);
        mainPanel.add(seatConfirmationPanel, "SEAT_CONFIRMATION_PANEL");
        
        // Create admin login panel
        adminLoginPanel = new AdminLoginPanel(context, this::onAdminLoginSuccess, this::onBackToSeat);
        mainPanel.add(adminLoginPanel, "ADMIN_LOGIN_PANEL");
        
        // Create admin panel
        adminPanel = new AdminPanel(context, this::onBackToSeat);
        mainPanel.add(adminPanel, "ADMIN_PANEL");
        
        // Set default screen to seat panel
        cardLayout.show(mainPanel, "SEAT_PANEL");
    }
    
    private void startServices() {
        // Start timer service
        context.getTimerService().startService();
        
        setVisible(true);
    }
    
    // Seat click event handler
    private void onSeatClick(java.awt.event.ActionEvent e) {
        int seatNumber = Integer.parseInt(e.getActionCommand());
        context.setSelectedSeat(seatNumber);
        
        // Check if current user already has a seat
        String currentUserId = context.getCurrentUserId();
        if (currentUserId != null) {
            Seat currentUserSeat = context.getSeatService().getSeatByNumber(seatNumber);
            if (currentUserSeat != null && currentUserSeat.isOccupied() && currentUserSeat.getUserId().equals(currentUserId)) {
                // User clicked their own seat - show checkout option
                seatConfirmationPanel.updateSeatInfo(currentUserSeat, currentUserId);
                showPanel("SEAT_CONFIRMATION_PANEL");
                return;
            }
            
            // Check if user already has another seat
            boolean hasOtherSeat = context.getSeatService().getAllSeats().stream()
                .anyMatch(s -> s.isOccupied() && s.getUserId().equals(currentUserId));
            
            if (hasOtherSeat) {
                JOptionPane.showMessageDialog(this, "You already have a seat reserved. Please checkout first before booking another seat.");
                return;
            }
        }
        
        // Check if seat is occupied by someone else
        Seat seat = context.getSeatService().getSeatByNumber(seatNumber);
        if (seat != null && seat.isOccupied()) {
            // Show seat confirmation panel
            seatConfirmationPanel.updateSeatInfo(seat, seat.getUserId());
            showPanel("SEAT_CONFIRMATION_PANEL");
        } else {
            // Move to member/non-member choice screen after seat selection
            showPanel("CHOICE_PANEL");
        }
    }
    
    // Admin button click event handler
    private void onAdminClick(java.awt.event.ActionEvent e) {
        adminLoginPanel.clearFields();
        showPanel("ADMIN_LOGIN_PANEL");
    }
    
    // Member button click event handler
    private void onMemberClick(java.awt.event.ActionEvent e) {
        showPanel("MEMBER_LOGIN_PANEL");
    }
    
    // Non-member button click event handler
    private void onNonMemberClick(java.awt.event.ActionEvent e) {
        // TODO: Implement non-member purchase panel
        JOptionPane.showMessageDialog(this, "Non-member purchase feature needs to be implemented.");
    }
    
    // Login success event handler
    private void onLoginSuccess(java.awt.event.ActionEvent e) {
        // Move to ticket selection
        showPanel("TICKET_SELECTION_PANEL");
    }
    
    // Join button click event handler
    private void onJoinClick(java.awt.event.ActionEvent e) {
        // TODO: Implement member registration panel
        JOptionPane.showMessageDialog(this, "Member registration feature needs to be implemented.");
    }
    
    // Ticket selection event handler
    private void onTicketSelected(java.awt.event.ActionEvent e) {
        String ticketType = context.getCurrentUserType();
        int amount = calculateAmount(ticketType);
        
        paymentPanel.updateAmount(amount);
        showPanel("PAYMENT_PANEL");
    }
    
    // Payment success event handler
    private void onPaymentSuccess(java.awt.event.ActionEvent e) {
        // Handle seat occupation
        int selectedSeat = context.getSelectedSeat();
        String userId = context.getCurrentUserId();
        String ticketType = context.getCurrentUserType();
        
        if (context.getSeatService().occupySeat(selectedSeat, userId)) {
            seatPanel.setSeatOccupied(selectedSeat, true);
            
            // Store payment information
            storePaymentInfo(userId, selectedSeat, ticketType);
            
            JOptionPane.showMessageDialog(this, "Seat " + selectedSeat + " has been reserved!");
            showPanel("SEAT_PANEL");
        } else {
            JOptionPane.showMessageDialog(this, "Seat reservation failed.");
        }
    }
    
    // Checkout event handler
    private void onCheckout(java.awt.event.ActionEvent e) {
        String userId = context.getCurrentUserId();
        int selectedSeat = context.getSelectedSeat();
        
        // Process checkout and calculate final payment
        PaymentService.CheckoutResult result = context.getPaymentService().processCheckout(userId);
        
        if (result.isSuccess()) {
            // Release seat
            if (context.getSeatService().checkoutUser(userId)) {
                seatPanel.setSeatOccupied(selectedSeat, false);
                
                // Show checkout summary
                String message = "Checkout completed successfully!\n";
                if (result.getFinalAmount() > 0) {
                    message += "Additional payment required: " + result.getFinalAmount() + " won\n";
                    message += "(Used more time than purchased)";
                }
                if (result.getRemainingTime() > 0) {
                    message += "\nRemaining time saved: " + result.getRemainingTime() + " minutes";
                }
                
                JOptionPane.showMessageDialog(this, message);
                showPanel("SEAT_PANEL");
            } else {
                JOptionPane.showMessageDialog(this, "Seat checkout failed.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Checkout failed: " + result.getMessage());
        }
    }
    
    // Back button event handlers
    private void onBackToSeat(java.awt.event.ActionEvent e) {
        showPanel("SEAT_PANEL");
    }
    
    private void onBackToChoice(java.awt.event.ActionEvent e) {
        memberLoginPanel.clearFields();
        showPanel("CHOICE_PANEL");
    }
    
    private void onBackToTicketSelection(java.awt.event.ActionEvent e) {
        ticketSelectionPanel.clearSelection();
        showPanel("TICKET_SELECTION_PANEL");
    }
    
    // Panel switching method
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    // Admin login success event handler
    private void onAdminLoginSuccess(java.awt.event.ActionEvent e) {
        showPanel("ADMIN_PANEL");
    }
    
    // Calculate payment amount based on ticket type
    private int calculateAmount(String ticketType) {
        return context.getPaymentService().calculateAmount(ticketType, 1); // Default 1 hour
    }
    
    // Store payment information
    private void storePaymentInfo(String userId, int seatNumber, String ticketType) {
        int amount = calculateAmount(ticketType);
        int timePurchased = context.getPaymentService().calculateTimePurchased(ticketType, 1);
        
        Payment payment = new Payment(userId, seatNumber, ticketType, amount, timePurchased);
        context.getPaymentService().createPayment(payment);
        
        // Update member's time if it's a ticket-based payment
        if (payment.isTicketBased()) {
            context.getAuthService().updateMemberTime(userId, timePurchased);
        }
    }
    
    // Clean up resources on app exit
    @Override
    public void dispose() {
        if (context != null) {
            context.shutdown();
        }
        super.dispose();
    }
}
