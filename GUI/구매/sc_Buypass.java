package study_cafe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sc_Buypass extends JFrame{
	Color back = new Color(0x030066);
	Color sm_back = new Color(0x99CCFF);
	Font font = new Font("맑은 고딕", Font.BOLD, 20); 
	String Hlist[] =  {"2시간  4000원", "4시간  7000원",
	    	"6시간  10000원","8시간  13000원","10시간  15000원","30시간  50000원",
	    	"50시간  80000원","100시간  15000원","150시간  21000원","200시간  26000원"};
	public sc_Buypass(){
		setTitle("이용권 구매 창");
		Container cp = getContentPane();
		setSize(1000,780);
		setResizable(false); // 프레임 창 고정
		setLocationRelativeTo(null); // 창이 가운데 뜨도록
		cp.setBackground(sm_back);
		
		cp.setLayout(null);
	
		
		
		JLabel txtbuy = new JLabel("이용권 구매");
		JLabel txt2 = new JLabel("* 30시간부터 사물함 이용 가능");
		
		txtbuy.setLocation(80, -50);
		txtbuy.setSize(300,200);
		txtbuy.setForeground(Color.white);
	    txtbuy.setFont(font.deriveFont(40.0f));
	    txt2.setLocation(300, -40);
		txt2.setSize(210,200);
		txt2.setForeground(Color.white);
	    txt2.setFont(font.deriveFont(14.0f));
	    cp.add(txt2);
	    cp.add(txtbuy); 
	    
	    for(int i = 0; i<5; i++) {
	    	String text = Hlist[i];
	    	JButton btn = new JButton(text);
	    	btn.setLocation(80,100+(i*110));
	    	btn.setSize(200,100);
	    	btn.setForeground(Color.white);
			btn.setBackground(back);
			btn.setFont(font);
	    	cp.add(btn);
	    }
	    for(int i = 5; i<10; i++) {
	    	String text = Hlist[i];
	    	JButton btn = new JButton(text);
	    	btn.setLocation(580,100+((i-5)*110));
	    	btn.setSize(200,100);
	    	btn.setForeground(Color.white);
			btn.setBackground(back);
			btn.setFont(font);
	    	cp.add(btn);
	    }
		
	    setVisible(true);
		
	}
	public static void main(String[] args) {
		new sc_Buypass();
	}
}
