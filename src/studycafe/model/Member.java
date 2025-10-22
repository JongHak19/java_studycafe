package studycafe.model;

import java.time.LocalDateTime;

public class Member {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private int time; // remaining time in minutes
    private LocalDateTime buyTime; // usage start time
    private int seatNumber; // currently using seat number
    private boolean isUsing; // whether currently using
    
    public Member() {}
    
    public Member(String id, String password, String name, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.time = 0;
        this.isUsing = false;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }
    
    public LocalDateTime getBuyTime() { return buyTime; }
    public void setBuyTime(LocalDateTime buyTime) { this.buyTime = buyTime; }
    
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    
    public boolean isUsing() { return isUsing; }
    public void setUsing(boolean using) { isUsing = using; }
}
