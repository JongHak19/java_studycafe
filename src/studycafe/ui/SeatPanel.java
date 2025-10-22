package studycafe.ui;

import studycafe.AppContext;
import studycafe.model.Seat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SeatPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onSeatClick;
    private final ActionListener onAdminClick;
    
    private JButton[] seats = new JButton[60];
    private JButton adminButton;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Color occupiedColor = new Color(0xaaacbe);
    private Font seatFont = new Font("Arial", Font.BOLD, 12);
    
    public SeatPanel(AppContext context, ActionListener onSeatClick, ActionListener onAdminClick) {
        this.context = context;
        this.onSeatClick = onSeatClick;
        this.onAdminClick = onAdminClick;
        
        initializeUI();
        setupSeats();
        refreshSeatStatus();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Admin button
        adminButton = new JButton("Admin");
        adminButton.setBounds(1200, 650, 70, 30);
        adminButton.setBackground(buttonColor);
        adminButton.addActionListener(onAdminClick);
        this.add(adminButton);
    }
    
    private void setupSeats() {
        for (int i = 0; i < 60; i++) {
            seats[i] = new JButton(Integer.toString(i + 1));
            seats[i].setFont(seatFont);
            seats[i].setSize(80, 50);
            seats[i].setBackground(buttonColor);
            seats[i].setActionCommand(String.valueOf(i + 1));
            seats[i].addActionListener(onSeatClick);
        }
        
        // Seat arrangement
        setupSeatPositions();
    }
    
    private void setupSeatPositions() {
        // First row (1-10)
        for (int i = 0; i < 10; i++) {
            seats[i].setLocation(125 + i * 80, 100);
            this.add(seats[i]);
        }
        
        // Second row (11-20)
        for (int i = 10; i < 20; i++) {
            seats[i].setLocation(125 + (i - 10) * 80, 150);
            this.add(seats[i]);
        }
        
        // Third row (21-25)
        for (int i = 20; i < 25; i++) {
            seats[i].setLocation(125 + (i - 20) * 80, 300);
            this.add(seats[i]);
        }
        
        // Fourth row (26-30)
        for (int i = 25; i < 30; i++) {
            seats[i].setLocation(125 + (i - 25) * 80, 350);
            this.add(seats[i]);
        }
        
        // Fifth row (31-35)
        for (int i = 30; i < 35; i++) {
            seats[i].setLocation(125 + (i - 30) * 80, 500);
            this.add(seats[i]);
        }
        
        // Sixth row (36-40)
        for (int i = 35; i < 40; i++) {
            seats[i].setLocation(125 + (i - 35) * 80, 570);
            this.add(seats[i]);
        }
        
        // Right top (41-50)
        for (int i = 40; i < 50; i++) {
            int row = (i - 40) / 2;
            int col = (i - 40) % 2;
            seats[i].setLocation(845 + col * 80, 370 + row * 50);
            this.add(seats[i]);
        }
        
        // Right bottom (51-60)
        for (int i = 50; i < 60; i++) {
            seats[i].setLocation(1075, 120 + (i - 50) * 50);
            this.add(seats[i]);
        }
    }
    
    public void refreshSeatStatus() {
        List<Seat> seatData = context.getSeatService().getAllSeats();
        
        for (int i = 0; i < 60; i++) {
            if (i < seatData.size()) {
                Seat seat = seatData.get(i);
                if (seat.isOccupied()) {
                    seats[i].setBackground(occupiedColor);
                } else {
                    seats[i].setBackground(buttonColor);
                }
            } else {
                seats[i].setBackground(buttonColor);
            }
        }
    }
    
    public void setSeatOccupied(int seatNumber, boolean occupied) {
        if (seatNumber >= 1 && seatNumber <= 60) {
            int index = seatNumber - 1;
            if (occupied) {
                seats[index].setBackground(occupiedColor);
            } else {
                seats[index].setBackground(buttonColor);
            }
        }
    }
}
