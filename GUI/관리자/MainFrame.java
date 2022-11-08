package studycafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame{
	Color back_c = new Color(0x0E1D35);
	Color button_c = new Color(0xDDDEE5);
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	Font button_font = new Font("맑은 고딕", Font.BOLD, 40);
	
	public MainFrame(){
		setTitle("스터디 카페");		
		setSize(1300,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		Dimension frameSize = getSize();
		 
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2, //화면 중앙에 메인 프레임 띄우기
                (windowSize.height - frameSize.height) / 2 - 10);
        
		c.setBackground(back_c);		
		c.setLayout(null);	

		//버튼 객체 생성
		JButton[] seat = new JButton[60];
		JButton admin = new JButton("관리");
		admin.setBounds(1200, 650, 70, 30);
		admin.setBackground(button_c);
		c.add(admin);
		admin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				Login adminp = new Login();
				c.removeAll();
				
				c.setVisible(false);
				c.add(adminp);
				c.setVisible(true);				
				}
		});
		
		
		for(int i=0;i<60;i++) {
			seat[i] = new JButton(Integer.toString(i+1));
			seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
			seat[i].setSize(80,50);
			seat[i].setBackground(button_c);
			
			/*
			seat[i].addActionListener(new ActionListner() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			*/
			//seat[i].setLocation(50*(i+1),50*(i+1));
			//c.add(seat[i]);
			//setVisible(true);
		}
		
		/*
		seat[0].setLocation(50,50);
		c.add(seat[0]);
		seat[1].setLocation(130,50);
		c.add(seat[1]);
		seat[2].setLocation(210,50);
		c.add(seat[2]);
		*/
		
		seat[0].setLocation(125,100);
		c.add(seat[0]);
		seat[1].setLocation(205,100);
		c.add(seat[1]);
		seat[2].setLocation(285,100);
		c.add(seat[2]);
		seat[3].setLocation(365,100);
		c.add(seat[3]);
		seat[4].setLocation(445,100);
		c.add(seat[4]);
		seat[5].setLocation(525,100);
		c.add(seat[5]);
		seat[6].setLocation(605,100);
		c.add(seat[6]);
		seat[7].setLocation(685,100);
		c.add(seat[7]);
		seat[8].setLocation(765,100);
		c.add(seat[8]);
		seat[9].setLocation(845,100);
		c.add(seat[9]);

		seat[10].setLocation(125,150);
		c.add(seat[10]);
		seat[11].setLocation(205,150);
		c.add(seat[11]);
		seat[12].setLocation(285,150);
		c.add(seat[12]);
		seat[13].setLocation(365,150);
		c.add(seat[13]);
		seat[14].setLocation(445,150);
		c.add(seat[14]);
		seat[15].setLocation(525,150);
		c.add(seat[15]);
		seat[16].setLocation(605,150);
		c.add(seat[16]);
		seat[17].setLocation(685,150);
		c.add(seat[17]);
		seat[18].setLocation(765,150);
		c.add(seat[18]);
		seat[19].setLocation(845,150);
		c.add(seat[19]);

		seat[20].setLocation(125,300);
		c.add(seat[20]);
		seat[21].setLocation(205,300);
		c.add(seat[21]);
		seat[22].setLocation(285,300);
		c.add(seat[22]);
		seat[23].setLocation(365,300);
		c.add(seat[23]);
		seat[24].setLocation(445,300);
		c.add(seat[24]);
		seat[25].setLocation(125,350);
		c.add(seat[25]);
		seat[26].setLocation(205,350);
		c.add(seat[26]);
		seat[27].setLocation(285,350);
		c.add(seat[27]);
		seat[28].setLocation(365,350);
		c.add(seat[28]);
		seat[29].setLocation(445,350);
		c.add(seat[29]);

		seat[30].setLocation(125,500);
		c.add(seat[30]);
		seat[31].setLocation(205,500);
		c.add(seat[31]);
		seat[32].setLocation(285,500);
		c.add(seat[32]);
		seat[33].setLocation(365,500);
		c.add(seat[33]);
		seat[34].setLocation(445,500);
		c.add(seat[34]);
		seat[35].setLocation(125,570);
		c.add(seat[35]);
		seat[36].setLocation(205,570);
		c.add(seat[36]);
		seat[37].setLocation(285,570);
		c.add(seat[37]);
		seat[38].setLocation(365,570);
		c.add(seat[38]);
		seat[39].setLocation(445,570);
		c.add(seat[39]);

		seat[40].setLocation(845,370);
		c.add(seat[40]);
		seat[41].setLocation(925,370);
		c.add(seat[41]);
		seat[42].setLocation(845,420);
		c.add(seat[42]);
		seat[43].setLocation(925,420);
		c.add(seat[43]);
		seat[44].setLocation(845,470);
		c.add(seat[44]);
		seat[45].setLocation(925,470);
		c.add(seat[45]);
		seat[46].setLocation(845,520);
		c.add(seat[46]);
		seat[47].setLocation(925,520);
		c.add(seat[47]);
		seat[48].setLocation(845,570);
		c.add(seat[48]);
		seat[49].setLocation(925,570);
		c.add(seat[49]);

		seat[50].setLocation(1075, 120);
		c.add(seat[50]);
		seat[51].setLocation(1075, 170);
		c.add(seat[51]);
		seat[52].setLocation(1075, 220);
		c.add(seat[52]);
		seat[53].setLocation(1075, 270);
		c.add(seat[53]);
		seat[54].setLocation(1075, 320);
		c.add(seat[54]);
		seat[55].setLocation(1075, 370);
		c.add(seat[55]);
		seat[56].setLocation(1075, 420);
		c.add(seat[56]);
		seat[57].setLocation(1075, 470);
		c.add(seat[57]);
		seat[58].setLocation(1075, 520);
		c.add(seat[58]);
		seat[59].setLocation(1075, 570);
		c.add(seat[59]);
		
		
		for(int i = 0; i < 60 ; i++) {
			seat[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {	
					LonginPanel login = new LonginPanel();
					c.add(login);
					c.removeAll();
					c.setVisible(false);
					main_panel mp = new main_panel();	// 회원 비회원 선택 화면 패널
					c.add(mp);
					c.setVisible(true);	
					

					member_view mv = new member_view();	// 회원 선택시 화면 패널
					mp.member.addActionListener(new ActionListener() {	// 회원 버튼을 눌렀을 때
						public void actionPerformed(ActionEvent e) {
							mp.setVisible(false);
							c.add(mv);	// 회원 화면 부착
						}
					});
					
					join_view jv = new join_view();		// 회원 가입 화면 패널
					notmember_view nmv = new notmember_view();	// 비회원 선택시 화면 패널
					mp.not_member.addActionListener(new ActionListener() {	// 비회원 버튼을 눌렀을 때
						public void actionPerformed(ActionEvent e) {
							int result = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
									"회원가입을 하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
							
							if(result == JOptionPane.YES_OPTION) { // 회원 가입 하는 경우
								mp.setVisible(false);
								c.add(jv);	// 회원 가입 화면 부착
							}
							else if(result == JOptionPane.NO_OPTION) {	// 비회원으로 구매하는 경우
								mp.setVisible(false);
								c.add(nmv);	// 회원 화면 부착
							}				
						}
					});
					
				}
			});
		}
		
		setVisible(true);	
	}
	class main_panel extends JPanel {	// 회원 비회원 선택 화면
		private JButton member = new JButton("회원"); // 회원 버튼
		private JButton not_member = new JButton("비회원"); // 비회원 버튼
		
		public main_panel() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			
			//member.setSize(300,300); // 크기 
			//member.setLocation(275,250); // 위치
			member.setBounds(225,150,300,300); // 위치, 크기 2
			member.setFont(button_font);
			member.setBackground(button_c);		
			add(member); // 회원 버튼 컨텐트페인에 추가
			
			//not_member.setSize(300,300);
			//not_member.setLocation(575,250);
			not_member.setBounds(575,150,300,300); // 위치, 크기 2
			not_member.setFont(button_font);
			not_member.setBackground(button_c);		
			add(not_member);					
		}
	}
	
	class member_view extends JPanel{	// 회원 화면 
		private JLabel name = new JLabel("로그인");
		private JLabel IDl = new JLabel("아이디");
		private JTextField IDtf = new JTextField(10);	// ID 입력 받는 텍스트 필드
		private JLabel PSl = new JLabel("비밀번호");
		private JTextField PStf = new JTextField(10);	// Password 입력 받는 텍스트 필드
		private JButton login = new JButton("로그인");
		
		public member_view() {	// 위치 오류 수정하기
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			
			name.setBounds(350,50,20,20);
			name.setFont(font);
			add(name);
			
			//IDl.setLocation(350,100); // 위치
			IDl.setBounds(350,100,20,20);
			IDl.setFont(font);
			add(IDl);
			
			//IDtf.setLocation(350,150); // 위치
			IDtf.setBounds(350,150,20,20);
			IDtf.setFont(font);
			add(IDtf);
			
			//PSl.setLocation(350,200); // 위치
			PSl.setBounds(350,200,20,20);
			PSl.setFont(font);
			add(PSl);
			
			//PStf.setLocation(350,250); // 위치
			PStf.setBounds(350,250,20,20);
			PStf.setFont(font);
			add(PStf);
			
			login.setBounds(600,500,10,7); // 위치, 크기 2
			login.setFont(font);
			login.setBackground(button_c);		
			add(login);
			
			setVisible(true);
		}
	}
	
	class join_view extends JPanel{	// 비회원 화면 
		public join_view() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setVisible(true);

		}
	}	
	
	class notmember_view extends JPanel{	// 비회원 화면 
		public notmember_view() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setVisible(true);

		}
	}
	class AdminPanel extends JPanel{
		public AdminPanel() {
			this.setSize(1100,600);
			this.setLayout(new BorderLayout());
			BarPanel bar = new BarPanel();
			bar.updateUI(); //panel update 안하면 이상하게 보임
			this.add(bar, BorderLayout.NORTH);
			setBackground(button_c);
			this.setLocation(90, 100);	
		}
		
	}
	class BarPanel extends JPanel{
		JButton SeatStatus = new JButton("좌석 현황");
		JButton member = new JButton("회원 관리");
		JButton sales = new JButton("매출");
		
		public BarPanel() {
			SeatStatus.setFont(font);
			member.setFont(font);
			sales.setFont(font);
			SeatStatus.setBackground(Color.white);
			member.setBackground(Color.white);
			sales.setBackground(Color.white);
			this.setSize(1100,80);
			this.setLayout(new GridLayout(1,3));
			this.add(SeatStatus);
			this.add(member);
			this.add(sales);
		}
	}
	class LonginPanel extends JPanel{
			
		public LonginPanel() {
			setSize(1200,900);
			setBackground(button_c);
			setVisible(true);
		}
	}
	class Login extends JPanel{
		JLabel id = new JLabel("id : ");
		JLabel password = new JLabel("password : ");
		JTextField idt = new JTextField(10);
		JPasswordField passwordt = new JPasswordField(10);
		JButton ok = new JButton("확인");
		JButton cancel = new JButton("취소");
		Font butfont = new Font("맑은 고딕", Font.BOLD, 10);
		public Login() {
			id.setBounds(20,70,100,20);
			idt.setBounds(120,70,100,20);
			password.setBounds(20,120,100,20);
			passwordt.setBounds(120,120,100,20);
			
			ok.setBounds(140,200,60,20);
			ok.setFont(butfont);
			ok.setBackground(back_c);
			ok.setForeground(button_c);
			
			cancel.setBounds(210,200,60,20);
			cancel.setFont(butfont);
			cancel.setBackground(back_c);
			cancel.setForeground(button_c);
			
			this.setBackground(button_c);
			this.setSize(300, 250);
			this.setLocation(550, 250);	
			this.setLayout(null);
			this.add(id);
			this.add(idt);
			this.add(password);
			this.add(passwordt);
			this.add(ok);
			this.add(cancel);
			
			ok.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (idt.getText().equals("cj8556") && passwordt.getText().equals("cjcj8399*")) {
						AdminPanel adminp = new AdminPanel();
						MainFrame.this.add(adminp);
						adminp.updateUI();
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "로그인 실패", "ERROR", JOptionPane.ERROR_MESSAGE );
					}
				}
			});
			
		}
	}
	public static void main(String [] args) {
		new MainFrame();
	}
}