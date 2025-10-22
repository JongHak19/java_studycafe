package studycafe.ui;

import studycafe.AppContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChoicePanel extends JPanel {
    private final AppContext context;
    private final ActionListener onMemberClick;
    private final ActionListener onNonMemberClick;
    private final ActionListener onBackClick;
    
    private Color backColor = new Color(0x0E1D35);
    private Color buttonColor = new Color(0xDDDEE5);
    private Font titleFont = new Font("Arial", Font.BOLD, 40);
    private Font buttonFont = new Font("Arial", Font.BOLD, 30);
    
    public ChoicePanel(AppContext context, ActionListener onMemberClick, 
                      ActionListener onNonMemberClick, ActionListener onBackClick) {
        this.context = context;
        this.onMemberClick = onMemberClick;
        this.onNonMemberClick = onNonMemberClick;
        this.onBackClick = onBackClick;
        
        initializeUI();
    }
    
    private void initializeUI() {
        this.setBackground(backColor);
        this.setLayout(null);
        this.setSize(1280, 700);
        this.setLocation(-10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("Select Usage Method");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(400, 200, 500, 50);
        this.add(titleLabel);
        
        // Member button
        JButton memberButton = new JButton("Member");
        memberButton.setFont(buttonFont);
        memberButton.setBackground(buttonColor);
        memberButton.setBounds(400, 300, 200, 80);
        memberButton.addActionListener(onMemberClick);
        this.add(memberButton);
        
        // Non-member button
        JButton nonMemberButton = new JButton("Non-Member");
        nonMemberButton.setFont(buttonFont);
        nonMemberButton.setBackground(buttonColor);
        nonMemberButton.setBounds(700, 300, 200, 80);
        nonMemberButton.addActionListener(onNonMemberClick);
        this.add(nonMemberButton);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(buttonColor);
        backButton.setBounds(50, 50, 120, 40);
        backButton.addActionListener(onBackClick);
        this.add(backButton);
    }
}
