package studycafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame{
	Color back_c = new Color(0x0E1D35);
	Color button_c = new Color(0xDDDEE5);
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	Font font_in = new Font("맑은 고딕", Font.BOLD, 15);
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
					// 좌석 선택 후 회원 비회원 선택 화면
					c.removeAll();
					c.setVisible(false);
					main_panel mp = new main_panel();	// 회원 비회원 선택 화면 패널
					c.add(mp);
					
					member_view mv = new member_view();	// 회원 선택시 화면 패널
					c.add(mv);
					mv.setVisible(false); // 비활성화 상태로 두기
					
					mp.member.addActionListener(new ActionListener() {	// 회원 버튼을 눌렀을 때
						public void actionPerformed(ActionEvent e) {
							mp.setVisible(false);
							mv.setVisible(true); // c.add(mv);	
						}
					});
					
					nonmember_view nmv = new nonmember_view();	// 비회원 선택시 화면 패널
					c.add(nmv);
					nmv.setVisible(false); // 비활성화 상태로 두기
					
					join_view jv = new join_view();		// 회원 가입 화면 패널
					c.add(jv);
					jv.setVisible(false); // 비활성화 상태로 두기
					
					mp.not_member.addActionListener(new ActionListener() {	// 비회원 버튼을 눌렀을 때
						public void actionPerformed(ActionEvent e) {
							int result_n = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
									"회원가입을 하시겠습니까?", "스터디 카페", JOptionPane.YES_NO_OPTION);
							
							if(result_n == JOptionPane.YES_OPTION) { // 회원 가입 하는 경우
								mp.setVisible(false);
								jv.setVisible(true); // c.add(jv);	// 회원 가입 화면 부착
							}
							else if(result_n == JOptionPane.NO_OPTION) {	// 비회원으로 구매하는 경우
								mp.setVisible(false);
								nmv.setVisible(true); // c.add(nmv);	// 비회원 화면 부착
							}				
						}
					});
					
					mp.Back_c.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 (나중에 얘기 해보기)
						public void actionPerformed(ActionEvent e) {
							//mp.setVisible(false);
							//좌석 선택 화면 붙이기, 활성화
						}
					});
					
					// 회원 로그인 화면(mv)
					mv.Back_m.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 
						public void actionPerformed(ActionEvent e) {
							mv.setVisible(false);
							mp.setVisible(true);
						}
					});
					
					// 비회원 
					// 예 : 회원 가입 화면(jv)
					jv.Back_j.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 
						public void actionPerformed(ActionEvent e) {
							jv.setVisible(false);
							mp.setVisible(true);
						}
					});
					member_buy mb = new member_buy();	// 회원 구매 패널
					c.add(mb);
					mb.setVisible(false);
					jv.join_j.addActionListener(new ActionListener() {	// 가입 버튼 눌렀을 때 
						public void actionPerformed(ActionEvent e) {
							int result_j = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
									"가입 하시겠습니까?", "스터디 카페", JOptionPane.YES_NO_OPTION);
							
							if(result_j == JOptionPane.YES_OPTION) { // 회원 가입 하는 경우
								JOptionPane.showMessageDialog(null, "회원가입 되었습니다.", "스터디 카페", JOptionPane.INFORMATION_MESSAGE);
								jv.setVisible(false);
								mb.setVisible(true); // 구매 화면 보이기
							}
						}
					});
					
					// 회원 구매 화면
					mb.Back_mb.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 
						public void actionPerformed(ActionEvent e) {
							mb.setVisible(false);
							jv.setVisible(true);
						}
					});
					
					// 아니오 : 비회원 로그인 화면(nmv)
					nmv.Back_n.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 
						public void actionPerformed(ActionEvent e) {
							nmv.setVisible(false);
							mp.setVisible(true);
						}
					});
					
					nonmember_buy nmb = new nonmember_buy();	// 비회원 구매 패널
					c.add(nmb);
					nmb.setVisible(false);
					nmv.join_n.addActionListener(new ActionListener() {	// 가입 버튼 눌렀을 때 
						public void actionPerformed(ActionEvent e) {
							nmv.setVisible(false);
							nmb.setVisible(true); // 비회원 구매 화면 보이기
						}
					});
					
					// 비회원 구매 화면
					nmb.Back_nb.addActionListener(new ActionListener() {	// 뒤로 가기 버튼 
						public void actionPerformed(ActionEvent e) {
							nmb.setVisible(false);
							nmv.setVisible(true);
						}
					});
					
					c.setVisible(true);
				}
			});
		}
		
		setVisible(true);	
	}
	class main_panel extends JPanel {	// 회원 비회원 선택 화면
		private JButton member = new JButton("회원"); // 회원 버튼
		private JButton not_member = new JButton("비회원"); // 비회원 버튼
		private JButton Back_c = new JButton("이전");		// 뒤로 가는 버튼
		
		public main_panel() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);

			member.setBounds(225,150,300,300); 
			member.setFont(button_font);
			member.setBackground(button_c);		
			add(member); // 회원 버튼 추가
			
			not_member.setBounds(575,150,300,300);
			not_member.setFont(button_font);
			not_member.setBackground(button_c);		
			add(not_member);
			
			Back_c.setBounds(950,520,80,40); 
			Back_c.setFont(font);
			Back_c.setBackground(button_c);		
			add(Back_c);
		}
	}
	
	class member_view extends JPanel {	// 회원 화면 
		private JLabel title_m = new JLabel("로그인");
		private JLabel IDl_m = new JLabel("아이디");
		private JTextField IDtf_m = new JTextField(40);	// ID 입력 받는 텍스트 필드
		private JLabel PSl_m = new JLabel("비밀번호");
		private JTextField PStf_m = new JTextField(40);	// Password 입력 받는 텍스트 필드
		private JButton login_m = new JButton("로그인");
		private JButton Back_m = new JButton("이전");		// 뒤로 가는 버튼
		
		public member_view() {	// 위치 오류 수정하기
			setLayout(null);
			setSize(1100,600);
			setLocation(90,90);
			setBackground(Color.WHITE);
			
			title_m.setBounds(400,80,150,40);
			title_m.setFont(button_font);
			add(title_m);
			
			IDl_m.setBounds(400,170,100,40);
			IDl_m.setFont(font);
			add(IDl_m);
			
			IDtf_m.setBounds(400,220,300,40);
			IDtf_m.setFont(font_in);
			add(IDtf_m);
			
			PSl_m.setBounds(400,270,100,40);
			PSl_m.setFont(font);
			add(PSl_m);
			
			PStf_m.setBounds(400,320,300,40);
			PStf_m.setFont(font_in);
			add(PStf_m);
			
			login_m.setBounds(600,420,100,50); // 위치, 크기 2
			login_m.setFont(font);
			login_m.setBackground(button_c);		
			add(login_m);
			
			Back_m.setBounds(950,520,80,40); // 위치, 크기 2
			Back_m.setFont(font);
			Back_m.setBackground(button_c);		
			add(Back_m);
		}
	}
	
	class join_view extends JPanel{		// 회원 가입 화면
		private JLabel title_j = new JLabel("회원가입");
		private JLabel Name_j = new JLabel("이     름");
		private JTextField Natf_j = new JTextField(50);	// 이름 입력 받는 텍스트 필드
		private JLabel IDl_j = new JLabel("아 이 디");
		private JTextField IDtf_j = new JTextField(50);	// ID 입력 받는 텍스트 필드
		private JLabel PSl_j = new JLabel("비밀번호");
		private JTextField PStf_j = new JTextField(50);	// Password 입력 받는 텍스트 필드
		//private JLabel PSl_re = new JLabel("비밀번호 확인");
		//private JTextField PStf_re = new JTextField(40);// Password 확인 입력 받는 텍스트 필드
		private JLabel Ph_j = new JLabel("전화번호");
		private JTextField Phtf_j = new JTextField(50);	// 전화번호 입력 받는 텍스트 필드
		private JLabel AD_j = new JLabel("주     소");
		private JTextField ADtf_j = new JTextField();	// 주소 입력 받는 텍스트 필드
		private JButton join_j = new JButton("가입");
		private JButton Back_j = new JButton("이전");		// 뒤로 가는 버튼
		public join_view() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title_j.setBounds(320,60,200,40);
			title_j.setFont(button_font);
			add(title_j);
			
			Name_j.setBounds(341,145,80,40);
			Name_j.setFont(font);
			add(Name_j);
			
			Natf_j.setBounds(450,145,320,40);
			Natf_j.setFont(font_in);
			add(Natf_j);
			
			IDl_j.setBounds(342,210,90,40);
			IDl_j.setFont(font);
			add(IDl_j);
			
			IDtf_j.setBounds(450,210,320,40);
			IDtf_j.setFont(font_in);
			add(IDtf_j);
			
			PSl_j.setBounds(340,270,100,40);
			PSl_j.setFont(font);
			add(PSl_j);
			
			PStf_j.setBounds(450,270,320,40);
			PStf_j.setFont(font_in);
			add(PStf_j);
			
			Ph_j.setBounds(340,330,100,40);
			Ph_j.setFont(font);
			add(Ph_j);
			
			Phtf_j.setBounds(450,330,320,40);
			Phtf_j.setFont(font_in);
			add(Phtf_j);
			
			AD_j.setBounds(341,390,80,40);
			AD_j.setFont(font);
			add(AD_j);
			
			ADtf_j.setBounds(450,390,320,40);
			ADtf_j.setFont(font_in);
			add(ADtf_j);
			
			join_j.setBounds(690,460,80,40);
			join_j.setFont(font);
			join_j.setBackground(button_c);		
			add(join_j);
			
			Back_j.setBounds(950,520,80,40); 
			Back_j.setFont(font);
			Back_j.setBackground(button_c);		
			add(Back_j);
		}
	}	
	
	class nonmember_view extends JPanel{	// 비회원 화면 
		private JLabel title_n = new JLabel("비회원 로그인");
		private JLabel Ph_n = new JLabel("전화번호");
		private JTextField Phtf_n = new JTextField();	// 전화번호 입력 받는 텍스트 필드
		private JButton join_n = new JButton("확인");
		private JButton Back_n = new JButton("이전");		// 뒤로 가는 버튼
		public nonmember_view() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title_n.setBounds(320,150,300,40);
			title_n.setFont(button_font);
			add(title_n);
			
			Ph_n.setBounds(340,275,100,40);
			Ph_n.setFont(font);
			add(Ph_n);
			
			Phtf_n.setBounds(450,275,320,40);
			Phtf_n.setFont(font_in);
			add(Phtf_n);
			
			join_n.setBounds(690,350,80,40);
			join_n.setFont(font);
			join_n.setBackground(button_c);		
			add(join_n);
			
			Back_n.setBounds(950,520,80,40); 
			Back_n.setFont(font);
			Back_n.setBackground(button_c);		
			add(Back_n);
		}
	}
	
	class member_buy extends JPanel{	// 회원 구매 화면
		private JLabel title1_mb = new JLabel("회원 이용권 구매");
		private JLabel title2_mb = new JLabel("* 30시간 부터 사물함 사용 가능");
		private JLabel title3_mb = new JLabel("당일권");
		private JButton bt1m = new JButton("<HTML><center>2시간<br/>4,000원<HTML>");
		private JButton bt2m = new JButton("<HTML><center>4시간<br/>7,000원<HTML>");	
		private JButton bt3m = new JButton("<HTML><center>6시간<br/>10,000원<HTML>");
		private JButton bt4m = new JButton("<HTML><center>8시간<br/>13,000원<HTML>");	
		private JButton bt5m = new JButton("<HTML><center>10시간<br/>15,000원<HTML>");
		private JButton bt6m = new JButton("<HTML><center>12시간<br/>17,000원<HTML>");	
		private JLabel title4_mb = new JLabel("시간권");
		private JButton bt7m = new JButton("<HTML><center>30시간<br/>50,000원<HTML>");
		private JButton bt8m = new JButton("<HTML><center>50시간<br/>80,000원<HTML>");	
		private JButton bt9m = new JButton("<HTML><center>100시간<br/>150,000원<HTML>");
		private JButton bt10m = new JButton("<HTML><center>150시간<br/>210,000원<HTML>");
		private JButton bt11m = new JButton("<HTML><center>200시간<br/>260,000원<HTML>");
		private JButton bt12m = new JButton("<HTML><center>300시간<br/>300,000원<HTML>");
		private JButton Back_mb = new JButton("이전");		// 뒤로 가는 버튼
		public member_buy() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title1_mb.setBounds(80,60,325,40);
			title1_mb.setFont(button_font);
			add(title1_mb);
			
			title2_mb.setBounds(410,60,300,40);
			title2_mb.setFont(font);
			add(title2_mb);
			
			title3_mb.setBounds(257,140,100,40);
			title3_mb.setFont(font);
			add(title3_mb);
			
			bt1m.setBounds(80,200,200,60);
			bt1m.setFont(font_in);
			bt1m.setBackground(button_c);	
			add(bt1m);
			
			bt2m.setBounds(300,200,200,60);
			bt2m.setFont(font_in);
			bt2m.setBackground(button_c);	
			add(bt2m);
			
			bt3m.setBounds(80,315,200,60);
			bt3m.setFont(font_in);
			bt3m.setBackground(button_c);	
			add(bt3m);
			
			bt4m.setBounds(300,315,200,60);
			bt4m.setFont(font_in);
			bt4m.setBackground(button_c);	
			add(bt4m);
			
			bt5m.setBounds(80,430,200,60);
			bt5m.setFont(font_in);
			bt5m.setBackground(button_c);	
			add(bt5m);
			
			bt6m.setBounds(300,430,200,60);
			bt6m.setFont(font_in);
			bt6m.setBackground(button_c);	
			add(bt6m);
			
			title4_mb.setBounds(777,140,100,40);
			title4_mb.setFont(font);
			add(title4_mb);
			
			bt7m.setBounds(600,200,200,60);
			bt7m.setFont(font_in);
			bt7m.setBackground(button_c);	
			add(bt7m);
			
			bt8m.setBounds(820,200,200,60);
			bt8m.setFont(font_in);
			bt8m.setBackground(button_c);	
			add(bt8m);
			
			bt9m.setBounds(600,315,200,60);
			bt9m.setFont(font_in);
			bt9m.setBackground(button_c);	
			add(bt9m);
			
			bt10m.setBounds(820,315,200,60);
			bt10m.setFont(font_in);
			bt10m.setBackground(button_c);	
			add(bt10m);
			
			bt11m.setBounds(600,430,200,60);
			bt11m.setFont(font_in);
			bt11m.setBackground(button_c);	
			add(bt11m);
			
			bt12m.setBounds(820,430,200,60);
			bt12m.setFont(font_in);
			bt12m.setBackground(button_c);	
			add(bt12m);
			
			Back_mb.setBounds(950,520,80,40); 
			Back_mb.setFont(font);
			Back_mb.setBackground(button_c);		
			add(Back_mb);
		}
	}
	
	class nonmember_buy extends JPanel{	// 비회원 구매 화면
		private JLabel title1_nb = new JLabel("비회원 이용권 구매");
		private JLabel title2_nb = new JLabel("당일권");
		private JButton bt1n = new JButton("<HTML><center>2시간<br/>4,000원<HTML>");
		private JButton bt2n = new JButton("<HTML><center>4시간<br/>7,000원<HTML>");	
		private JButton bt3n = new JButton("<HTML><center>6시간<br/>10,000원<HTML>");
		private JButton bt4n = new JButton("<HTML><center>8시간<br/>13,000원<HTML>");	
		private JButton bt5n = new JButton("<HTML><center>10시간<br/>15,000원<HTML>");
		private JButton bt6n = new JButton("<HTML><center>12시간<br/>17,000원<HTML>");	
		private JLabel title3_nb = new JLabel("시간권");
		private JButton bt7n = new JButton("<HTML><center>30시간<br/>50,000원<HTML>");
		private JButton bt8n = new JButton("<HTML><center>50시간<br/>80,000원<HTML>");	
		private JButton bt9n = new JButton("<HTML><center>100시간<br/>150,000원<HTML>");
		private JButton bt10n = new JButton("<HTML><center>150시간<br/>210,000원<HTML>");
		private JButton bt11n = new JButton("<HTML><center>200시간<br/>260,000원<HTML>");
		private JButton bt12n = new JButton("<HTML><center>300시간<br/>300,000원<HTML>");
		private JButton Back_nb = new JButton("이전");		// 뒤로 가는 버튼
		public nonmember_buy() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title1_nb.setBounds(80,60,350,40);
			title1_nb.setFont(button_font);
			add(title1_nb);
			
			title2_nb.setBounds(257,140,100,40);
			title2_nb.setFont(font);
			add(title2_nb);
			
			bt1n.setBounds(80,200,200,60);
			bt1n.setFont(font_in);
			bt1n.setBackground(button_c);	
			add(bt1n);
			
			bt2n.setBounds(300,200,200,60);
			bt2n.setFont(font_in);
			bt2n.setBackground(button_c);	
			add(bt2n);
			
			bt3n.setBounds(80,315,200,60);
			bt3n.setFont(font_in);
			bt3n.setBackground(button_c);	
			add(bt3n);
			
			bt4n.setBounds(300,315,200,60);
			bt4n.setFont(font_in);
			bt4n.setBackground(button_c);	
			add(bt4n);
			
			bt5n.setBounds(80,430,200,60);
			bt5n.setFont(font_in);
			bt5n.setBackground(button_c);	
			add(bt5n);
			
			bt6n.setBounds(300,430,200,60);
			bt6n.setFont(font_in);
			bt6n.setBackground(button_c);	
			add(bt6n);
			
			title3_nb.setBounds(777,140,100,40);
			title3_nb.setFont(font);
			add(title3_nb);
			
			bt7n.setBounds(600,200,200,60);
			bt7n.setFont(font_in);
			bt7n.setBackground(button_c);	
			add(bt7n);
			
			bt8n.setBounds(820,200,200,60);
			bt8n.setFont(font_in);
			bt8n.setBackground(button_c);	
			add(bt8n);
			
			bt9n.setBounds(600,315,200,60);
			bt9n.setFont(font_in);
			bt9n.setBackground(button_c);	
			add(bt9n);
			
			bt10n.setBounds(820,315,200,60);
			bt10n.setFont(font_in);
			bt10n.setBackground(button_c);	
			add(bt10n);
			
			bt11n.setBounds(600,430,200,60);
			bt11n.setFont(font_in);
			bt11n.setBackground(button_c);	
			add(bt11n);
			
			bt12n.setBounds(820,430,200,60);
			bt12n.setFont(font_in);
			bt12n.setBackground(button_c);	
			add(bt12n);
			
			Back_nb.setBounds(950,520,80,40); 
			Back_nb.setFont(font);
			Back_nb.setBackground(button_c);		
			add(Back_nb);
		}
	}
	class AdminPanel extends JPanel{
		public AdminPanel() {
			this.setSize(1100,650);
			this.setLayout(new BorderLayout());
			BarPanel bar = new BarPanel();
			bar.updateUI(); //panel update 안하면 이상하게 보임
			this.add(bar, BorderLayout.NORTH);
			setBackground(button_c);
			this.setLocation(90, 60);
			
			
			JButton[] seat = new JButton[60];

			for(int i=0;i<60;i++) {
				seat[i] = new JButton(Integer.toString(i+1));
				seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
				seat[i].setSize(60,45);
				seat[i].setBackground(back_c);	
				seat[i].setForeground(button_c);
			}
			
			this.setLayout(null);
			for(int i =0;i<10;i++) {
				seat[i].setLocation(i*60+50, 135);
				this.add(seat[i]);
			}
			
			for(int i =0;i<10;i++) {
				seat[i+10].setLocation(i*60+50, 180);
				this.add(seat[i+10]);
			}
			
			for(int i =0;i<5;i++) {
				seat[i+20].setLocation(i*60+50, 295);
				this.add(seat[i+20]);
			}
			
			for(int i =0;i<5;i++) {
				seat[i+25].setLocation(i*60+50, 340);
				this.add(seat[i+25]);
			}
			
			for(int i =0;i<5;i++) {
				seat[i+30].setLocation(i*60+50, 455);
				this.add(seat[i+30]);
			}
			
			for(int i =0;i<5;i++) {
				seat[i+35].setLocation(i*60+50, 515);
				this.add(seat[i+35]);
			}
			int h = 315;
			for(int i =0;i<10;i++) {
				seat[i+40].setLocation(590+(i%2)*60, h);
				if(i%2==1)
					h+=45;
				this.add(seat[i+40]);
			}
			
			for(int i=0;i<10;i++) {
				seat[i+50].setLocation(770, 90+i*45);
				this.add(seat[i+50]);
			}
			
			
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
			this.setSize(1100,40);
			this.setLayout(new GridLayout(1,3));
			this.add(SeatStatus);
			this.add(member);
			this.add(sales);
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
//					if (idt.getText().equals("admin") && passwordt.getText().equals("admin*")) {
						AdminPanel adminp = new AdminPanel();
						MainFrame.this.add(adminp);
						adminp.updateUI();
						setVisible(false);
//					}
//					else {
//						JOptionPane.showMessageDialog(null, "로그인 실패", "ERROR", JOptionPane.ERROR_MESSAGE );
//					}
				}
			});
			
		}
	}
	public static void main(String [] args) {
		new MainFrame();
	}
}