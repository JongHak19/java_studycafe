package studycafe.ui;

import studycafe.AppContext;
import studycafe.model.Member;
import studycafe.model.Seat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPanel extends JPanel {
    private final AppContext context;
    private final ActionListener onBackClick;
    
    private JTable memberTable;
    private JTable seatTable;
    private JLabel statsLabel;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 30);
    private Font labelFont = new Font("Arial", Font.BOLD, 18);
    
    public AdminPanel(AppContext context, ActionListener onBackClick) {
        this.context = context;
        this.onBackClick = onBackClick;
        
        initializeUI();
        loadData();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(500, 20, 300, 40);
        this.add(titleLabel);
        
        // Statistics
        statsLabel = new JLabel();
        statsLabel.setFont(labelFont);
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setBounds(50, 80, 600, 30);
        this.add(statsLabel);
        
        // Member table
        JLabel memberLabel = new JLabel("Members");
        memberLabel.setFont(labelFont);
        memberLabel.setForeground(Color.WHITE);
        memberLabel.setBounds(50, 120, 100, 30);
        this.add(memberLabel);
        
        String[] memberColumns = {"ID", "Name", "Phone", "Time", "Seat", "Using"};
        DefaultTableModel memberModel = new DefaultTableModel(memberColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(memberModel);
        memberTable.setBackground(buttonColor);
        memberTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberScrollPane.setBounds(50, 150, 600, 200);
        this.add(memberScrollPane);
        
        // Seat table
        JLabel seatLabel = new JLabel("Seats");
        seatLabel.setFont(labelFont);
        seatLabel.setForeground(Color.WHITE);
        seatLabel.setBounds(50, 370, 100, 30);
        this.add(seatLabel);
        
        String[] seatColumns = {"Seat #", "Status", "User", "Remaining Time"};
        DefaultTableModel seatModel = new DefaultTableModel(seatColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        seatTable = new JTable(seatModel);
        seatTable.setBackground(buttonColor);
        seatTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane seatScrollPane = new JScrollPane(seatTable);
        seatScrollPane.setBounds(50, 400, 600, 200);
        this.add(seatScrollPane);
        
        // Control buttons
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(buttonColor);
        refreshButton.setBounds(700, 150, 120, 40);
        refreshButton.addActionListener(e -> loadData());
        this.add(refreshButton);
        
        JButton exportButton = new JButton("Export Data");
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setBackground(buttonColor);
        exportButton.setBounds(700, 200, 120, 40);
        exportButton.addActionListener(e -> exportData());
        this.add(exportButton);
        
        JButton forceCheckoutButton = new JButton("Force Checkout");
        forceCheckoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        forceCheckoutButton.setBackground(Color.RED);
        forceCheckoutButton.setForeground(Color.WHITE);
        forceCheckoutButton.setBounds(700, 250, 120, 40);
        forceCheckoutButton.addActionListener(e -> forceCheckout());
        this.add(forceCheckoutButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
    
    private void loadData() {
        // Load member data
        DefaultTableModel memberModel = (DefaultTableModel) memberTable.getModel();
        memberModel.setRowCount(0);
        
        List<Member> members = context.getRepository().findAllMembers();
        for (Member member : members) {
            Object[] row = {
                member.getId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getTime() + " min",
                member.getSeatNumber() > 0 ? member.getSeatNumber() : "-",
                member.isUsing() ? "Yes" : "No"
            };
            memberModel.addRow(row);
        }
        
        // Load seat data
        DefaultTableModel seatModel = (DefaultTableModel) seatTable.getModel();
        seatModel.setRowCount(0);
        
        List<Seat> seats = context.getSeatService().getAllSeats();
        for (Seat seat : seats) {
            Object[] row = {
                seat.getSeatNumber(),
                seat.isOccupied() ? "Occupied" : "Available",
                seat.getUserId() != null ? seat.getUserId() : "-",
                seat.getRemainingTime() + " min"
            };
            seatModel.addRow(row);
        }
        
        // Update statistics
        updateStatistics();
    }
    
    private void updateStatistics() {
        List<Member> members = context.getRepository().findAllMembers();
        List<Seat> seats = context.getSeatService().getAllSeats();
        
        int totalMembers = members.size();
        int activeMembers = (int) members.stream().filter(Member::isUsing).count();
        int occupiedSeats = (int) seats.stream().filter(Seat::isOccupied).count();
        int totalSeats = seats.size();
        double occupancyRate = totalSeats > 0 ? (double) occupiedSeats / totalSeats * 100 : 0;
        
        String stats = String.format(
            "Total Members: %d | Active: %d | Occupied Seats: %d/%d (%.1f%%)",
            totalMembers, activeMembers, occupiedSeats, totalSeats, occupancyRate
        );
        statsLabel.setText(stats);
    }
    
    private void exportData() {
        // TODO: Implement data export functionality
        JOptionPane.showMessageDialog(this, "Data export feature will be implemented soon.");
    }
    
    private void forceCheckout() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to force checkout.");
            return;
        }
        
        String memberId = (String) memberTable.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this, 
            "Force checkout for member: " + memberId + "?", 
            "Force Checkout", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            if (context.getSeatService().checkoutUser(memberId)) {
                JOptionPane.showMessageDialog(this, "Force checkout completed.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Force checkout failed.");
            }
        }
    }
}
