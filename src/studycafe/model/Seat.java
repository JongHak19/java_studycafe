package studycafe.model;

public class Seat {
    private int seatNumber;
    private boolean isOccupied; // whether occupied
    private String userId; // using user ID
    private int remainingTime; // remaining time
    
    public Seat() {}
    
    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isOccupied = false;
        this.remainingTime = 0;
    }
    
    // Getters and Setters
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
}
