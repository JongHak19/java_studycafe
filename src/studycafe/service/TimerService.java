package studycafe.service;

import studycafe.db.StudyCafeRepository;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerService {
    private final StudyCafeRepository repository;
    private final ScheduledExecutorService scheduler;
    private boolean isRunning = false;
    
    public TimerService(StudyCafeRepository repository) {
        this.repository = repository;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }
    
    public void startService() {
        if (isRunning) {
            return;
        }
        
        // Decrement time and checkout expired users every minute
        scheduler.scheduleAtFixedRate(this::updateUsageTime, 0, 1, TimeUnit.MINUTES);
        isRunning = true;
    }
    
    private void updateUsageTime() {
        try {
            // Decrement all active users' time by 1 minute
            repository.decrementAllActiveMemberTimes();
            
            // Checkout expired users
            repository.checkoutExpiredUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void stopService() {
        if (isRunning) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            isRunning = false;
        }
    }
    
    public boolean isRunning() {
        return isRunning;
    }
}
