package study_cafe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sc_SelectMember extends JFrame {
	Color back = new Color(0x030066);
	Color sm_back = new Color(0x99CCFF);
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	//Color back_sm  
	public sc_SelectMember(){
		setTitle("회원 비회원 선택 창");
		Container cp = getContentPane();
		setSize(600,500);
		setResizable(false); // 프레임 창 고정
		setLocationRelativeTo(null); // 창이 가운데 뜨도록
		cp.setBackground(sm_back);
		
		cp.setLayout(null);
		
		setVisible(true);
		JButton mem = new JButton("회원");
		JButton no_mem = new JButton("비회원");
		
		mem.setLocation(80,150);
		mem.setSize(150,100);
		mem.setForeground(Color.white);
		mem.setBackground(back);
		mem.setFont(font);
		
		no_mem.setLocation(360,150);
		no_mem.setSize(150,100);
		no_mem.setForeground(Color.white);
		no_mem.setBackground(back);
		no_mem.setFont(font);
		
		cp.add(mem);
		cp.add(no_mem);
		
		mem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					new sc_Login();
					// 회원 선택 시 Login 창 뜨게 한 후 회원,비회원 선택 창 숨기는 것 구현해야함
				}
		 	});
		
		no_mem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,
						"회원가입을 하시겠습니까?","비회원 회원가입",JOptionPane.YES_NO_OPTION);
				
				//if(result == JOptionPane.YES_OPTION)
					
				}
		 	});
		
		
		
		setVisible(true);
	}
	
	
	public static void main(String[]args) {
		new sc_SelectMember();
	}
}
