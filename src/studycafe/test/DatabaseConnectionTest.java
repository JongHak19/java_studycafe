package studycafe.test;

import studycafe.db.StudyCafeRepository;
import studycafe.model.Member;
import studycafe.model.Seat;
import java.util.List;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        System.out.println("=== MySQL Database Connection Test ===");
        
        try {
            // Create Repository instance
            StudyCafeRepository repository = new StudyCafeRepository();
            System.out.println("[SUCCESS] Repository instance created successfully");
            
            // Test seat data retrieval
            System.out.println("\n--- Seat Data Retrieval Test ---");
            List<Seat> seats = repository.findAllSeats();
            System.out.println("[SUCCESS] Seat data retrieved successfully: " + seats.size() + " seats");
            
            // Print seat information (first 5 only)
            for (int i = 0; i < Math.min(5, seats.size()); i++) {
                Seat seat = seats.get(i);
                System.out.println("  Seat " + seat.getSeatNumber() + ": " + 
                    (seat.isOccupied() ? "Occupied" : "Available"));
            }
            
            // Test member data retrieval
            System.out.println("\n--- Member Data Retrieval Test ---");
            List<Member> members = repository.findAllMembers();
            System.out.println("[SUCCESS] Member data retrieved successfully: " + members.size() + " members");
            
            // Print member information
            for (Member member : members) {
                System.out.println("  Member: " + member.getId() + " (" + member.getName() + ")");
            }
            
            // Test member login
            System.out.println("\n--- Login Test ---");
            Member testMember = repository.findMemberByIdAndPassword("test01", "password123");
            if (testMember != null) {
                System.out.println("[SUCCESS] Login test successful: " + testMember.getName());
            } else {
                System.out.println("[FAILED] Login test failed");
            }
            
            // Test seat status update
            System.out.println("\n--- Seat Status Update Test ---");
            boolean updateResult = repository.updateSeatStatus(1, true, "test01");
            if (updateResult) {
                System.out.println("[SUCCESS] Seat 1 status updated successfully");
                
                // Check updated seat information
                List<Seat> updatedSeats = repository.findAllSeats();
                Seat seat1 = updatedSeats.stream()
                    .filter(s -> s.getSeatNumber() == 1)
                    .findFirst()
                    .orElse(null);
                
                if (seat1 != null && seat1.isOccupied()) {
                    System.out.println("[SUCCESS] Seat 1 changed to occupied");
                }
                
                // Restore original state
                repository.updateSeatStatus(1, false, null);
                System.out.println("[SUCCESS] Seat 1 status restored");
            } else {
                System.out.println("[FAILED] Seat status update failed");
            }
            
            System.out.println("\n=== All Tests Completed ===");
            System.out.println("[SUCCESS] MySQL database connection is working properly!");
            
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection error:");
            e.printStackTrace();
            
            System.out.println("\n--- Troubleshooting ---");
            System.out.println("1. Check if MySQL server is running");
            System.out.println("2. Check if 'studycafe' database exists");
            System.out.println("3. Check if database_setup.sql script was executed");
            System.out.println("4. Check if connection info in StudyCafeRepository.java is correct");
        }
    }
}
