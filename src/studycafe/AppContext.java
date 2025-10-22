package studycafe;

import studycafe.db.StudyCafeRepository;
import studycafe.service.AuthService;
import studycafe.service.SeatService;
import studycafe.service.TimerService;
import studycafe.service.PaymentService;

public class AppContext {
    // Layer objects
    private final StudyCafeRepository repository;
    private final AuthService authService;
    private final SeatService seatService;
    private final TimerService timerService;
    private final PaymentService paymentService;
    
    // Global state
    private int currentSelectedSeat = -1;
    private String currentUserId = null;
    private String currentUserType = null; // "member" or "nonmember"
    
    public AppContext() {
        // Create objects in dependency order
        this.repository = new StudyCafeRepository();
        this.authService = new AuthService(repository);
        this.seatService = new SeatService(repository);
        this.timerService = new TimerService(repository);
        this.paymentService = new PaymentService(repository);
    }
    
    // Service Getter
    public AuthService getAuthService() { 
        return authService; 
    }
    
    public SeatService getSeatService() { 
        return seatService; 
    }
    
    public TimerService getTimerService() { 
        return timerService; 
    }
    
    public StudyCafeRepository getRepository() {
        return repository;
    }
    
    public PaymentService getPaymentService() {
        return paymentService;
    }
    
    // Global state Getter/Setter
    public void setSelectedSeat(int seat) { 
        this.currentSelectedSeat = seat; 
    }
    
    public int getSelectedSeat() { 
        return this.currentSelectedSeat; 
    }
    
    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
    }
    
    public String getCurrentUserId() {
        return this.currentUserId;
    }
    
    public void setCurrentUserType(String userType) {
        this.currentUserType = userType;
    }
    
    public String getCurrentUserType() {
        return this.currentUserType;
    }
    
    // Clean up resources on app exit
    public void shutdown() {
        timerService.stopService();
    }
}
