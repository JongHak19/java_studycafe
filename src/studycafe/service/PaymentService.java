package studycafe.service;

import studycafe.db.StudyCafeRepository;
import studycafe.model.Payment;
import studycafe.model.Member;
import java.util.List;
import java.util.ArrayList;

public class PaymentService {
    private final StudyCafeRepository repository;
    
    public PaymentService(StudyCafeRepository repository) {
        this.repository = repository;
    }
    
    // Create payment record
    public boolean createPayment(Payment payment) {
        // TODO: Implement payment creation in database
        System.out.println("Payment created: " + payment.getUserId() + " - " + payment.getAmount() + " won");
        return true;
    }
    
    // Calculate payment amount based on ticket type
    public int calculateAmount(String ticketType, int hours) {
        switch (ticketType) {
            case "hourly":
                return 1000 * hours; // 1,000 won per hour
            case "daily":
                return 6000; // 6,000 won for 8 hours (25% discount)
            case "postpayment":
                return 1200 * hours; // 1,200 won per hour (20% more expensive)
            default:
                return 1000 * hours;
        }
    }
    
    // Calculate time purchased based on ticket type
    public int calculateTimePurchased(String ticketType, int hours) {
        switch (ticketType) {
            case "hourly":
                return hours * 60; // Convert hours to minutes
            case "daily":
                return 8 * 60; // 8 hours = 480 minutes
            case "postpayment":
                return 0; // No upfront time for post payment
            default:
                return hours * 60;
        }
    }
    
    // Process checkout and calculate final payment
    public CheckoutResult processCheckout(String userId) {
        Member member = repository.findMemberByIdAndPassword(userId, "");
        if (member == null || !member.isUsing()) {
            return new CheckoutResult(false, "User not found or not using a seat", 0, 0);
        }
        
        // Get user's current payment info
        Payment currentPayment = getCurrentPayment(userId);
        if (currentPayment == null) {
            return new CheckoutResult(false, "No payment record found", 0, 0);
        }
        
        // Calculate used time
        int usedTime = calculateUsedTime(member);
        int remainingTime = 0;
        int finalAmount = 0;
        
        if (currentPayment.isTicketBased()) {
            // Ticket-based payment: check remaining time
            remainingTime = currentPayment.getRemainingTime() - usedTime;
            if (remainingTime < 0) {
                // Used more time than purchased - charge extra
                int extraTime = Math.abs(remainingTime);
                finalAmount = extraTime * 20; // 20 won per minute for extra time
                remainingTime = 0;
            } else {
                // Save remaining time for future use
                currentPayment.setRemainingTime(remainingTime);
                finalAmount = 0;
            }
        } else if (currentPayment.isPostPayment()) {
            // Post payment: charge for all used time
            finalAmount = usedTime * 20; // 20 won per minute
        }
        
        return new CheckoutResult(true, "Checkout successful", finalAmount, remainingTime);
    }
    
    // Get current payment for user
    private Payment getCurrentPayment(String userId) {
        // TODO: Implement database query
        // For now, return a mock payment
        return new Payment(userId, 1, "hourly", 1000, 60);
    }
    
    // Calculate used time in minutes
    private int calculateUsedTime(Member member) {
        if (member.getBuyTime() == null) {
            return 0;
        }
        
        long minutes = java.time.Duration.between(member.getBuyTime(), java.time.LocalDateTime.now()).toMinutes();
        return (int) Math.max(0, minutes);
    }
    
    // Get user's remaining time
    public int getRemainingTime(String userId) {
        Payment payment = getCurrentPayment(userId);
        if (payment != null && payment.isTicketBased()) {
            Member member = repository.findMemberByIdAndPassword(userId, "");
            if (member != null && member.isUsing()) {
                int usedTime = calculateUsedTime(member);
                return Math.max(0, payment.getRemainingTime() - usedTime);
            }
            return payment.getRemainingTime();
        }
        return 0;
    }
    
    // Checkout result class
    public static class CheckoutResult {
        private final boolean success;
        private final String message;
        private final int finalAmount;
        private final int remainingTime;
        
        public CheckoutResult(boolean success, String message, int finalAmount, int remainingTime) {
            this.success = success;
            this.message = message;
            this.finalAmount = finalAmount;
            this.remainingTime = remainingTime;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public int getFinalAmount() { return finalAmount; }
        public int getRemainingTime() { return remainingTime; }
    }
}
