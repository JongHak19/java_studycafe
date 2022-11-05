package study_cafe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sc_Login extends JFrame {
	Color back = new Color(0x030066);
	Color sm_back = new Color(0x99CCFF);
	Font font = new Font("맑은 고딕", Font.BOLD, 20);  
	public sc_Login(){
		setTitle("로그인 창");
		Container cp = getContentPane();
		setSize(600,500);
		setResizable(false); // 프레임 창 고정
		setLocationRelativeTo(null); // 창이 가운데 뜨도록
		cp.setBackground(sm_back);
		
		cp.setLayout(null);
		
		
		JLabel txtlogin = new JLabel("로그인");
		JLabel txtid = new JLabel("ID");
		JLabel txtpw = new JLabel("PASSWORD");
		
		txtlogin.setLocation(80, -20);
		txtlogin.setSize(300,200);
		txtlogin.setForeground(Color.white);
	    txtlogin.setFont(font.deriveFont(40.0f));
		
		txtid.setLocation(80,100);
		txtid.setSize(150,100);
		txtid.setForeground(Color.white);
		txtid.setFont(font.deriveFont(25.0f));   
		
	    txtpw.setLocation(80,200);
	    txtpw.setSize(150,100);
	    txtpw.setForeground(Color.white);
	    txtpw.setFont(font.deriveFont(25.0f));  
	
	    JTextField tfid = new JTextField();
		JTextField tfpw = new JTextField();
		tfid.setBounds(80,180,300,30);
		tfpw.setBounds(80,280,300,30);
	    
		cp.add(txtlogin);
		cp.add(txtid);
		cp.add(tfid);
		cp.add(txtpw);
		cp.add(tfpw);
		
		setVisible(true);
		
		}
	public static void main(String[] args) {
		new sc_Login();
	}
}