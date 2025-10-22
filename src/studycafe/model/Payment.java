package studycafe.model;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private String userId;
    private int seatNumber;
    private String paymentType; // "hourly", "daily", "postpayment"
    private int amount;
    private int timePurchased; // minutes
    private LocalDateTime paymentTime;
    private boolean isCompleted;
    private int remainingTime; // for ticket-based payments
    
    public Payment() {}
    
    public Payment(String userId, int seatNumber, String paymentType, int amount, int timePurchased) {
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.timePurchased = timePurchased;
        this.paymentTime = LocalDateTime.now();
        this.isCompleted = false;
        this.remainingTime = timePurchased;
    }
    
    // Getters and Setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
    public int getTimePurchased() { return timePurchased; }
    public void setTimePurchased(int timePurchased) { this.timePurchased = timePurchased; }
    
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
    
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
    
    // Helper methods
    public boolean isTicketBased() {
        return "hourly".equals(paymentType) || "daily".equals(paymentType);
    }
    
    public boolean isPostPayment() {
        return "postpayment".equals(paymentType);
    }
    
    public void consumeTime(int minutes) {
        if (isTicketBased() && remainingTime > 0) {
            remainingTime = Math.max(0, remainingTime - minutes);
        }
    }
}
