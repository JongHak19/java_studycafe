package studycafe;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame{
	Color back = new Color(0x030066);
	Color but = new Color(0xC9C6FF);
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	
	public MainFrame(){
		setTitle("스터디 카페");
		setSize(1300,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();

		c.setBackground(back);
		
		setVisible(true);
	}

	public static void main(String [] args) {
		new MainFrame();
	}
}