package studycafe.service;

import studycafe.db.StudyCafeRepository;
import studycafe.model.Seat;
import studycafe.model.Member;

import java.util.List;

public class SeatService {
    private final StudyCafeRepository repository;
    
    public SeatService(StudyCafeRepository repository) {
        this.repository = repository;
    }
    
    public List<Seat> getAllSeats() {
        return repository.findAllSeats();
    }
    
    public boolean occupySeat(int seatNumber, String userId) {
        // Check if seat is already occupied
        List<Seat> seats = getAllSeats();
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber && seat.isOccupied()) {
                return false; // already occupied
            }
        }
        
        // Update seat status
        boolean seatUpdated = repository.updateSeatStatus(seatNumber, true, userId);
        if (seatUpdated) {
            // Update member's seat information
            return repository.setMemberSeat(userId, seatNumber);
        }
        return false;
    }
    
    public boolean releaseSeat(int seatNumber) {
        // Update seat status
        boolean seatUpdated = repository.updateSeatStatus(seatNumber, false, null);
        return seatUpdated;
    }
    
    public boolean moveSeat(String userId, int fromSeat, int toSeat) {
        // Release existing seat
        boolean fromReleased = releaseSeat(fromSeat);
        if (!fromReleased) {
            return false;
        }
        
        // Occupy new seat
        return occupySeat(toSeat, userId);
    }
    
    public boolean checkoutUser(String userId) {
        // Checkout member
        boolean memberCheckedOut = repository.checkoutMember(userId);
        if (memberCheckedOut) {
            // Release seat
            List<Seat> seats = getAllSeats();
            for (Seat seat : seats) {
                if (userId.equals(seat.getUserId())) {
                    return releaseSeat(seat.getSeatNumber());
                }
            }
        }
        return memberCheckedOut;
    }
    
    public Seat getSeatByNumber(int seatNumber) {
        List<Seat> seats = getAllSeats();
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }
}
