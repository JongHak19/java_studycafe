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
		Dimension frameSize = getSize();
		 
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2, //화면 중앙에 메인 프레임 띄우기
                (windowSize.height - frameSize.height) / 2 - 10);
        
		c.setBackground(back);		
		c.setLayout(null);	
    
    
    
		//관리자 gui 
		ImageIcon img = new ImageIcon("./image/admin.png");
		JButton adminb = new JButton(img);
		adminb.setBounds(1200, 600, 30, 30);
		c.add(adminb);
		adminb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				AdminPanel adminp = new AdminPanel();
				c.add(adminp);
				adminp.updateUI();
				adminb.setVisible(false);
			}
		});
		//여기까지 다른 패널이 없어서 임의로 추가한 부분
		setVisible(true);

	}
	
	class AdminPanel extends JPanel{
		public AdminPanel() {
			this.setSize(1100,600);
			this.setLayout(new BorderLayout());
			BarPanel bar = new BarPanel();
			bar.updateUI();
			this.add(bar, BorderLayout.NORTH);
			setBackground(but);
			this.setLocation(90, 100);	
		}
		
	}
	class BarPanel extends JPanel{
		JButton SeatStatus = new JButton("좌석 현황");
		JButton member = new JButton("회원 관리");
		JButton sales = new JButton("매출");
		public BarPanel() {
			this.setSize(1100,80);
			this.setLayout(new GridLayout(1,3));
			this.add(SeatStatus);
			this.add(member);
			this.add(sales);
		}
	}
	public static void main(String [] args) {
		new MainFrame();
	}
}