package studycafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.sql.*;

public class MainFrame extends JFrame{
	Color back_c = new Color(0x0E1D35);
	Color button_c = new Color(0xDDDEE5);//0xFFEFD6
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	Font font_in = new Font("맑은 고딕", Font.BOLD, 15);
	Font button_font = new Font("맑은 고딕", Font.BOLD, 40);
	static String url = "jdbc:mysql://cjh00.duckdns.org:3307/studycafe";
	static String mysql_user = "root";
	static String mysql_password = "0939";
	
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
		seat s = new seat();
		c.add(s);
		
		setVisible(true);	
	}
	class seat extends JPanel{
		//버튼 객체 생성
		JButton[] seat = new JButton[60];
		JButton admin = new JButton("관리");
		public seat() {
			this.setBackground(back_c);
			this.setLayout(null);
			this.setSize(1280,700);
			this.setLocation(-10,10);
			admin.setBounds(1200, 650, 70, 30);
			admin.setBackground(button_c);
			this.add(admin);
			admin.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {	
					Login adminp = new Login();
					MainFrame.this.getContentPane().removeAll();
					MainFrame.this.setVisible(false);
					MainFrame.this.add(adminp);
					MainFrame.this.setVisible(true);				
				}
			});
			
			
			for(int i=0;i<60;i++) {
				seat[i] = new JButton(Integer.toString(i+1));
				seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
				seat[i].setSize(80,50);
				seat[i].setBackground(button_c);

			}
			
			seat[0].setLocation(125,100);
			this.add(seat[0]);
			seat[1].setLocation(205,100);
			this.add(seat[1]);
			seat[2].setLocation(285,100);
			this.add(seat[2]);
			seat[3].setLocation(365,100);
			this.add(seat[3]);
			seat[4].setLocation(445,100);
			this.add(seat[4]);
			seat[5].setLocation(525,100);
			this.add(seat[5]);
			seat[6].setLocation(605,100);
			this.add(seat[6]);
			seat[7].setLocation(685,100);
			this.add(seat[7]);
			seat[8].setLocation(765,100);
			this.add(seat[8]);
			seat[9].setLocation(845,100);
			this.add(seat[9]);

			seat[10].setLocation(125,150);
			this.add(seat[10]);
			seat[11].setLocation(205,150);
			this.add(seat[11]);
			seat[12].setLocation(285,150);
			this.add(seat[12]);
			seat[13].setLocation(365,150);
			this.add(seat[13]);
			seat[14].setLocation(445,150);
			this.add(seat[14]);
			seat[15].setLocation(525,150);
			this.add(seat[15]);
			seat[16].setLocation(605,150);
			this.add(seat[16]);
			seat[17].setLocation(685,150);
			this.add(seat[17]);
			seat[18].setLocation(765,150);
			this.add(seat[18]);
			seat[19].setLocation(845,150);
			this.add(seat[19]);

			seat[20].setLocation(125,300);
			this.add(seat[20]);
			seat[21].setLocation(205,300);
			this.add(seat[21]);
			seat[22].setLocation(285,300);
			this.add(seat[22]);
			seat[23].setLocation(365,300);
			this.add(seat[23]);
			seat[24].setLocation(445,300);
			this.add(seat[24]);
			seat[25].setLocation(125,350);
			this.add(seat[25]);
			seat[26].setLocation(205,350);
			this.add(seat[26]);
			seat[27].setLocation(285,350);
			this.add(seat[27]);
			seat[28].setLocation(365,350);
			this.add(seat[28]);
			seat[29].setLocation(445,350);
			this.add(seat[29]);

			seat[30].setLocation(125,500);
			this.add(seat[30]);
			seat[31].setLocation(205,500);
			this.add(seat[31]);
			seat[32].setLocation(285,500);
			this.add(seat[32]);
			seat[33].setLocation(365,500);
			this.add(seat[33]);
			seat[34].setLocation(445,500);
			this.add(seat[34]);
			seat[35].setLocation(125,570);
			this.add(seat[35]);
			seat[36].setLocation(205,570);
			this.add(seat[36]);
			seat[37].setLocation(285,570);
			this.add(seat[37]);
			seat[38].setLocation(365,570);
			this.add(seat[38]);
			seat[39].setLocation(445,570);
			this.add(seat[39]);

			seat[40].setLocation(845,370);
			this.add(seat[40]);
			seat[41].setLocation(925,370);
			this.add(seat[41]);
			seat[42].setLocation(845,420);
			this.add(seat[42]);
			seat[43].setLocation(925,420);
			this.add(seat[43]);
			seat[44].setLocation(845,470);
			this.add(seat[44]);
			seat[45].setLocation(925,470);
			this.add(seat[45]);
			seat[46].setLocation(845,520);
			this.add(seat[46]);
			seat[47].setLocation(925,520);
			this.add(seat[47]);
			seat[48].setLocation(845,570);
			this.add(seat[48]);
			seat[49].setLocation(925,570);
			this.add(seat[49]);

			seat[50].setLocation(1075, 120);
			this.add(seat[50]);
			seat[51].setLocation(1075, 170);
			this.add(seat[51]);
			seat[52].setLocation(1075, 220);
			this.add(seat[52]);
			seat[53].setLocation(1075, 270);
			this.add(seat[53]);
			seat[54].setLocation(1075, 320);
			this.add(seat[54]);
			seat[55].setLocation(1075, 370);
			this.add(seat[55]);
			seat[56].setLocation(1075, 420);
			this.add(seat[56]);
			seat[57].setLocation(1075, 470);
			this.add(seat[57]);
			seat[58].setLocation(1075, 520);
			this.add(seat[58]);
			seat[59].setLocation(1075, 570);
			this.add(seat[59]);
			
			
			for(int i = 0; i < 60 ; i++) {
				seat[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {	
						// 좌석 선택 후 회원 비회원 선택 화면
						MainFrame.this.getContentPane().removeAll();
						MainFrame.this.setVisible(false);
						main_panel mp = new main_panel();	// 회원 비회원 선택 화면 패널
						MainFrame.this.add(mp);
						member_view mv = new member_view();	// 회원 선택시 화면 패널
						MainFrame.this.add(mv);
						mv.setVisible(false); // 비활성화 상태로 두기
						
						mp.member.addActionListener(new ActionListener() {	// 회원 버튼을 눌렀을 때
							public void actionPerformed(ActionEvent e) {
								mp.setVisible(false);
								mv.setVisible(true); // c.add(mv);	
							}
						});
						
						nonmember_view nmv = new nonmember_view();	// 비회원 선택시 화면 패널
						MainFrame.this.add(nmv);
						nmv.setVisible(false); // 비활성화 상태로 두기
						
						join_view jv = new join_view();		// 회원 가입 화면 패널
						MainFrame.this.add(jv);
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
								mp.setVisible(false);
								seat s = new seat();
								MainFrame.this.setVisible(false);
								MainFrame.this.add(s);
								MainFrame.this.setVisible(true);
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
						MainFrame.this.add(mb);
						mb.setVisible(false);
						jv.join_j.addActionListener(new ActionListener() {	// 가입 버튼 눌렀을 때 
							public void actionPerformed(ActionEvent e) {
								int result_j = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
										"가입 하시겠습니까?", "스터디 카페", JOptionPane.YES_NO_OPTION);
								
								if(result_j == JOptionPane.YES_OPTION) { // 회원 가입 하는 경우
									
									boolean gender;
									
									if(jv.man_j.isSelected()) //성별이 남자일 경우 false로 저장
										gender = false;
									else
										gender = true;
									
									try {
										// MySQL DB용 드라이로드
										Class.forName("com.mysql.cj.jdbc.Driver");
										Connection conn =
										DriverManager.getConnection(url, mysql_user, mysql_password);
										String sql = "INSERT INTO MEMBER VALUES" + 
												"('"+ jv.Natf_j.getText() + "', " + jv.Agef_j.getText() + ", " + gender + ", '" + jv.Phtf_j.getText() + "', null, '"+ jv.ADtf_j.getText() +
												"', '" + jv.IDtf_j.getText() + "', '" + jv.PStf_j.getText() + "')";
										System.out.println(sql);
										Statement stmt = conn.createStatement();
										stmt.execute(sql);
										
										if(conn!=null)    //db 연결해제
											conn.close();
										if(stmt!=null)
											stmt.close();
									}
									catch(ClassNotFoundException error) {
										JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
										return;
									}
									catch(SQLException error) {
										JOptionPane.showMessageDialog(null, "DB접속오류");
										return;
									}
					
									
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
						MainFrame.this.add(nmb);
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
						
						MainFrame.this.setVisible(true);
					}
				});
			}
		}
		
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
		private JTextField IDtf_m = new JTextField(10);	// ID 입력 받는 텍스트 필드
		private JLabel PSl_m = new JLabel("비밀번호");
		private JTextField PStf_m = new JTextField(10);	// Password 입력 받는 텍스트 필드
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
			
			loginListener logL = new loginListener();
			login_m.setBounds(600,420,100,50); // 위치, 크기 2
			login_m.setFont(font);
			login_m.setBackground(button_c);	
			login_m.addActionListener(logL);
			add(login_m);
			
			Back_m.setBounds(950,520,80,40); // 위치, 크기 2
			Back_m.setFont(font);
			Back_m.setBackground(button_c);		
			add(Back_m);
		}
		class loginListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(IDtf_m.getText().equals("test")) {
					if(PStf_m.getText().equals("1234")) {
						int result = JOptionPane.showConfirmDialog(null, 
								"이용권을 구매하시겠습니까?", "이용권 구매",JOptionPane.YES_NO_OPTION);
						if(result == JOptionPane.YES_OPTION) {
							MainFrame.this.setVisible(false);
							MainFrame.this.getContentPane().removeAll();
							MainFrame.this.add(new sc_Buypass());
							MainFrame.this.setVisible(true);
					}
						else {
							JOptionPane.showMessageDialog(null, "남은시간이 차감됩니다","이용권",JOptionPane.PLAIN_MESSAGE);
						}
						
					}
					else {
						JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요", "Message",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "아이디를 다시 입력하세요", "Message",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public class sc_Buypass extends JPanel{ // 이용권 구매 창
		Color back = new Color(0x030066);
		Color sm_back = new Color(0x99CCFF);
		Font font = new Font("맑은 고딕", Font.BOLD, 20); 
		String Hlist[] =  {"2시간  4000원", "4시간  7000원",
		    	"6시간  10000원","8시간  13000원","10시간  15000원","30시간  50000원",
		    	"50시간  80000원","100시간  150000원","150시간  210000원","200시간  260000원"};
		public sc_Buypass(){
			setTitle("이용권 구매 창");
			setSize(1000,780);
			setLocation(220,50);
			setResizable(false); // 프레임 창 고정
			setLocationRelativeTo(null); // 창이 가운데 뜨도록
			setBackground(back_c);
			
			setLayout(null);
		
			
			
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
		    add(txt2);
		    add(txtbuy); 
		    
		    buyListener bL = new buyListener();
		    
		    for(int i = 0; i<5; i++) {
		    	String text = Hlist[i];
		    	JButton btn = new JButton(text);
		    	btn.setLocation(80,100+(i*110));
		    	btn.setSize(200,100);
		    	btn.setForeground(Color.black);
				btn.setBackground(button_c);
				btn.setFont(font);
				btn.addActionListener(bL);
		    	add(btn);
		    }
		    for(int i = 5; i<10; i++) {
		    	String text = Hlist[i];
		    	JButton btn = new JButton(text);
		    	btn.setLocation(580,100+((i-5)*110));
		    	btn.setSize(210,100);
		    	btn.setForeground(Color.black);
				btn.setBackground(button_c);
				btn.setFont(font);
				btn.addActionListener(bL);
		    	add(btn);
		    }		
		    setVisible(true);			
		}	
	}
	class buyListener implements ActionListener{ //이용권 버튼 클릭시 이벤트(다른 클래스에서도 사용가능)
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null,
					"구매하시겠습니까?","이용권 구매",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "구매완료 !", "구매완료",JOptionPane.PLAIN_MESSAGE);
				MainFrame.this.setVisible(false);
				MainFrame.this.getContentPane().removeAll();
				seat s = new seat();
				MainFrame.this.add(s);
				MainFrame.this.setVisible(true);
			}	
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
		private JTextArea ADtf_j = new JTextArea();	// 주소 입력 받는 텍스트 필드
		private JScrollPane ADs_j = new JScrollPane(ADtf_j);
		private JButton join_j = new JButton("가입");
		private JButton Back_j = new JButton("이전");		// 뒤로 가는 버튼
		private JLabel Gender_j = new JLabel("성     별");
		private JRadioButton man_j = new JRadioButton("남");
		private JRadioButton woman_j = new JRadioButton("여");
		private ButtonGroup genderg_j = new ButtonGroup();
		private JLabel Age_j = new JLabel("나     이");
		private JTextField Agef_j = new JTextField(3);
		public join_view() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title_j.setBounds(150,60,200,40);
			title_j.setFont(button_font);
			add(title_j);
			
			Name_j.setBounds(131,145,80,40);
			Name_j.setFont(font);
			add(Name_j);
				
			Gender_j.setBounds(600, 145, 100, 40);
			Gender_j.setFont(font);
			add(Gender_j);
			
			genderg_j.add(man_j);   //성별 라디오 버튼 그룹
			genderg_j.add(woman_j);
			
			man_j.setBounds(710, 145, 50, 40);
			man_j.setFont(font_in);
			man_j.setBackground(Color.white);
			add(man_j);	

			woman_j.setBounds(770, 145, 50, 40);
			woman_j.setFont(font_in);
			woman_j.setBackground(Color.white);
			add(woman_j);
			
			Age_j.setBounds(131, 390, 80, 40);
			Age_j.setFont(font);
			add(Age_j);
			
			Agef_j.setBounds(240, 390, 320, 40);
			Agef_j.setFont(font_in);
			add(Agef_j);
			
			Natf_j.setBounds(240,145,320,40);
			Natf_j.setFont(font_in);
			add(Natf_j);
			
			IDl_j.setBounds(132,210,90,40);
			IDl_j.setFont(font);
			add(IDl_j);
			
			IDtf_j.setBounds(240,210,320,40);
			IDtf_j.setFont(font_in);
			add(IDtf_j);
			
			PSl_j.setBounds(130,270,100,40);
			PSl_j.setFont(font);
			add(PSl_j);
			
			PStf_j.setBounds(240,270,320,40);
			PStf_j.setFont(font_in);
			add(PStf_j);
			
			Ph_j.setBounds(130,330,100,40);
			Ph_j.setFont(font);
			add(Ph_j);
			
			Phtf_j.setBounds(240,330,320,40);
			Phtf_j.setFont(font_in);
			add(Phtf_j);
			
			AD_j.setBounds(600,210,80,40);
			AD_j.setFont(font);
			add(AD_j);
			
			ADtf_j.setFont(font_in);
			ADtf_j.setLineWrap(true);
			
			ADs_j.setBounds(700,210, 320, 120);
			ADs_j.setViewportView(ADtf_j);
			ADs_j.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			add(ADs_j);
			ADs_j.setVisible(true);
			
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
			
			buyListener bL = new buyListener();
			
			bt1m.setBounds(80,200,200,60);
			bt1m.setFont(font_in);
			bt1m.setBackground(button_c);
			bt1m.addActionListener(bL);
			add(bt1m);
			
			bt2m.setBounds(300,200,200,60);
			bt2m.setFont(font_in);
			bt2m.setBackground(button_c);
			bt2m.addActionListener(bL);
			add(bt2m);
			
			bt3m.setBounds(80,315,200,60);
			bt3m.setFont(font_in);
			bt3m.setBackground(button_c);
			bt3m.addActionListener(bL);
			add(bt3m);
			
			bt4m.setBounds(300,315,200,60);
			bt4m.setFont(font_in);
			bt4m.setBackground(button_c);
			bt4m.addActionListener(bL);
			add(bt4m);
			
			bt5m.setBounds(80,430,200,60);
			bt5m.setFont(font_in);
			bt5m.setBackground(button_c);	
			bt5m.addActionListener(bL);
			add(bt5m);
			
			bt6m.setBounds(300,430,200,60);
			bt6m.setFont(font_in);
			bt6m.setBackground(button_c);
			bt6m.addActionListener(bL);
			add(bt6m);
			
			title4_mb.setBounds(777,140,100,40);
			title4_mb.setFont(font);
			add(title4_mb);
			
			bt7m.setBounds(600,200,200,60);
			bt7m.setFont(font_in);
			bt7m.setBackground(button_c);	
			bt7m.addActionListener(bL);
			add(bt7m);
			
			bt8m.setBounds(820,200,200,60);
			bt8m.setFont(font_in);
			bt8m.setBackground(button_c);	
			bt8m.addActionListener(bL);
			add(bt8m);
			
			bt9m.setBounds(600,315,200,60);
			bt9m.setFont(font_in);
			bt9m.setBackground(button_c);
			bt9m.addActionListener(bL);
			add(bt9m);
			
			bt10m.setBounds(820,315,200,60);
			bt10m.setFont(font_in);
			bt10m.setBackground(button_c);	
			bt10m.addActionListener(bL);
			add(bt10m);
			
			bt11m.setBounds(600,430,200,60);
			bt11m.setFont(font_in);
			bt11m.setBackground(button_c);	
			bt11m.addActionListener(bL);
			add(bt11m);
			
			bt12m.setBounds(820,430,200,60);
			bt12m.setFont(font_in);
			bt12m.setBackground(button_c);	
			bt12m.addActionListener(bL);
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
			
			buyListener bL = new buyListener(); //회원 구매 파트에서 가져옴
			
			bt1n.setBounds(80,200,200,60);
			bt1n.setFont(font_in);
			bt1n.setBackground(button_c);
			bt1n.addActionListener(bL);
			add(bt1n);
			
			bt2n.setBounds(300,200,200,60);
			bt2n.setFont(font_in);
			bt2n.setBackground(button_c);	
			bt2n.addActionListener(bL);
			add(bt2n);
			
			bt3n.setBounds(80,315,200,60);
			bt3n.setFont(font_in);
			bt3n.setBackground(button_c);	
			bt3n.addActionListener(bL);
			add(bt3n);
			
			bt4n.setBounds(300,315,200,60);
			bt4n.setFont(font_in);
			bt4n.setBackground(button_c);	
			bt4n.addActionListener(bL);
			add(bt4n);
			
			bt5n.setBounds(80,430,200,60);
			bt5n.setFont(font_in);
			bt5n.setBackground(button_c);	
			bt5n.addActionListener(bL);
			add(bt5n);
			
			bt6n.setBounds(300,430,200,60);
			bt6n.setFont(font_in);
			bt6n.setBackground(button_c);
			bt6n.addActionListener(bL);
			add(bt6n);
			
			title3_nb.setBounds(777,140,100,40);
			title3_nb.setFont(font);
			add(title3_nb);
			
			bt7n.setBounds(600,200,200,60);
			bt7n.setFont(font_in);
			bt7n.setBackground(button_c);	
			bt7n.addActionListener(bL);
			add(bt7n);
			
			bt8n.setBounds(820,200,200,60);
			bt8n.setFont(font_in);
			bt8n.setBackground(button_c);
			bt8n.addActionListener(bL);
			add(bt8n);
			
			bt9n.setBounds(600,315,200,60);
			bt9n.setFont(font_in);
			bt9n.setBackground(button_c);	
			bt9n.addActionListener(bL);
			add(bt9n);
			
			bt10n.setBounds(820,315,200,60);
			bt10n.setFont(font_in);
			bt10n.setBackground(button_c);	
			bt10n.addActionListener(bL);
			add(bt10n);
			
			bt11n.setBounds(600,430,200,60);
			bt11n.setFont(font_in);
			bt11n.setBackground(button_c);	
			bt11n.addActionListener(bL);
			add(bt11n);
			
			bt12n.setBounds(820,430,200,60);
			bt12n.setFont(font_in);
			bt12n.setBackground(button_c);	
			bt12n.addActionListener(bL);
			add(bt12n);
			
			Back_nb.setBounds(950,520,80,40); 
			Back_nb.setFont(font);
			Back_nb.setBackground(button_c);		
			add(Back_nb);
		}
	}
	class AdminPanel extends JPanel{
		seat_color sc = new seat_color(); //좌석으로 보기
		seat_main sg = new seat_main(); //그래프로 보기
		JRadioButton seatcolor = new JRadioButton("좌석으로 보기");
		JRadioButton seatgraph = new JRadioButton("그래프로 보기");
		ButtonGroup group = new ButtonGroup();
		JButton back = new JButton("이전");
		public AdminPanel() {	
			group.add(seatcolor);
			group.add(seatgraph);
			
			this.setSize(1100,650);
			this.setLayout(new BorderLayout());
			BarPanel bar = new BarPanel();
			bar.member.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					removeAll();
					add(bar, BorderLayout.NORTH);
					User u = new User();
					add(u);
					setVisible(true);	
				}
			});
			bar.SeatStatus.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					AdminPanel ap = new AdminPanel();
					MainFrame.this.add(ap);
					MainFrame.this.setVisible(true);
				}
			});
			this.add(bar, BorderLayout.NORTH);
			setBackground(button_c);
			this.setLocation(90, 60);
			this.setLayout(null);
			
			seatcolor.addItemListener(new ItemListener() {			
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						sg.setVisible(false);
						sc.setVisible(true);
					}
				}
			});
			
			seatgraph.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						sc.setVisible(false);
						sg.setVisible(true);
					}
				}
			});
			
			this.add(sc);
			this.add(sg);
			sg.setVisible(false);
			
			//좌석 현황 - 좌석으로 보기
			seatcolor.setBounds(900, 200, 150, 40);
			seatcolor.setBackground(button_c);
			seatcolor.setSelected(true);
			this.add(seatcolor);
			//좌석 현황 - 그래프로 보기
			seatgraph.setBounds(900, 240, 150, 40);
			seatgraph.setBackground(button_c);
			this.add(seatgraph);
			
			back.setBounds(950,550,80,40); 
			back.setFont(font);
			back.setBackground(back_c);
			back.setForeground(button_c);
			this.add(back);	
		
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					seat s = new seat();
					MainFrame.this.add(s);
					MainFrame.this.setVisible(true);
				}
			});
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
	
	class seat_color extends JPanel{
		Color r = Color.red;
		Color g = Color.green;
		Color y = Color.yellow;
		int randomIndex;
		Random random = new Random();
		Color [] color = new Color []{r,g,y};
		
		public seat_color() {
			setLocation(10,0);
			setSize(850, 570);
			this.setBackground(button_c);
			JButton[] seat = new JButton[60];
			
			for(int i=0;i<60;i++) {
				randomIndex = random.nextInt(3);
				seat[i] = new JButton(Integer.toString(i+1));
				seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
				seat[i].setSize(60,45);
				seat[i].setBackground(color[randomIndex]);
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
	class underbar extends JPanel{
		ImageIcon nexticon= new ImageIcon("./image/next.png");
		ImageIcon previcon= new ImageIcon("./image/prev.png");
		JButton next = new JButton(nexticon);
		JButton prev = new JButton(previcon);
		
		public underbar() {
			setLayout(null);
			setBackground(back_c);
			setSize(850, 50);
			setLocation(0,480);
			next.setBounds(700, 10, 30, 30);
			prev.setBounds(650, 10, 30, 30);
			
			add(prev);
			add(next);
		}
	}
	class seat_main extends JPanel{
		public seat_main() {
			setSize(850, 570);
			setLocation(10,50);
			setBackground(button_c);
			setLayout(null);
			
			add(new seat_graph());
			add(new underbar());
		}
	}
	class seat_graph extends JPanel{
		Font font = new Font("맑은 고딕", Font.BOLD, 11);
		int [] score = new int[60];
		int randomIndex;
		Random random = new Random();
		public seat_graph() {
			setLocation(0, 0);
			setSize(850, 480);
		}
		public void paint(Graphics g){	
			for(int i = 0 ;i<60;i++) {
				randomIndex = random.nextInt(100);
				score[i] = randomIndex;
			}
			g.clearRect(0,0,getWidth(),getHeight());
			
			Stroke line = new BasicStroke(3);
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
			Graphics2D g2 = (Graphics2D) g.create();
			g.setColor(Color.blue);
			g2.setStroke(line);
			g2.drawLine(100,50,100,450);
			g2.drawLine(100,450,750,450);
			g2.setFont(font);
			g2.drawString("0", 60, 450);
			g2.drawString("(%)", 80, 30);
			
			for(int i = 0;i<15;i++) {
				g2.drawString("좌석"+(i+1), i*43+100,470);
				g.setColor(Color.blue);
				g.fillRect(i*43+120, 450-score[i]*4, 10, score[i]*4);
			}
			g2.setStroke(dashed);
	        for(int i =1;i<11;i++) {
	        	g2.drawLine(100,450-i*40,750,450-i*40);
	        	g2.drawString(""+i*10, 60, 450-i*40);
	        }	   
		}
	}
	
	public class User extends JPanel{
		JButton mem_editBtn = new JButton("수정");
		JButton mem_delBtn = new JButton("삭제");
		
		JLabel mem_im = new JLabel("회원정보");
		JLabel mem_nameL = new JLabel("이름 : ");
		JLabel mem_ageL = new JLabel("나이 : ");
		JLabel mem_sL = new JLabel("성별 : ");
		JLabel mem_phoneL = new JLabel("전화번호 : ");
		JLabel mem_adrL = new JLabel("주소 : ");
		JLabel mem_timeL = new JLabel("남은시간 : ");
		JLabel mem_mfL = new JLabel("남자");
		JLabel mem_time = new JLabel("00:00:00");
		
		JTextField mem_nameT = new JTextField();
		JTextField mem_ageT = new JTextField();
		JTextField mem_stxT = new JTextField();
		JTextField mem_phoneT = new JTextField();
		JTextArea mem_adrT = new JTextArea();
		
		JButton back = new JButton("이전");
		/*JRadioButton mem_rb1 = new JRadioButton("남자");
		JRadioButton mem_rb2 = new JRadioButton("여자");
		ButtonGroup mem_group = new ButtonGroup();
		mem_group.add(mem_rb1);
		mem_group.add(mem_rb2);
		mem_rb1.setSelected(true);*/

		public User() {
			setSize(1100,650);
			this.setLayout(null);
		
			String[] columns = {"이름","나이","성별","전화번호","주소","남은시간"};
			String[][] data = {{"홍길동","12","남자","010-3423-1234","대한민국","00:00:00"},
					{"김영희","23","여자","123-4567-8910","대한민국","00:00:00"}
			};
			DefaultTableModel model = new DefaultTableModel(data,columns);
			// 회원 정보 테이블 생성
			JTable table = new JTable(model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 테이블 폰트 설정
			scrollPane.setLocation(30,50);
			scrollPane.setSize(500,550);
			this.add(scrollPane);
			
			table.addMouseListener(new MouseAdapter() { // 테이블 클릭했을때 회원정보 창에 정보 불러오기
				public void mouseClicked(MouseEvent e) {
					int row = table.getSelectedRow();
					mem_nameT.setText((String)table.getValueAt(row, 0));
					mem_ageT.setText((String)table.getValueAt(row, 1));
					mem_mfL.setText((String)table.getValueAt(row, 2));
					mem_phoneT.setText((String)table.getValueAt(row, 3));
					mem_adrT.setText((String)table.getValueAt(row, 4));
					mem_time.setText((String)table.getValueAt(row, 5));
				}
			});
			
			mem_delBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 삭제버튼
			mem_delBtn.setLocation(660,550);
			mem_delBtn.setSize(80,40);
			mem_delBtn.setFont(font);
			mem_delBtn.setBackground(back_c);
			mem_delBtn.setForeground(button_c);
			mem_delBtn.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(table.getSelectedRow() == -1) {
						return;
					}
					else {
						model.removeRow(table.getSelectedRow()); // 삭제
					}
				}
			});
			this.add(mem_delBtn);
			
			mem_editBtn.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 수정 버튼
			mem_editBtn.setLocation(770,550);
			mem_editBtn.setSize(80,40);
			mem_editBtn.setFont(font);
			mem_editBtn.setBackground(back_c);
			mem_editBtn.setForeground(button_c);
			this.add(mem_editBtn);
			mem_editBtn.addActionListener(new ActionListener() {  // 수정버튼 일단 string 으로 구현함
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					table.setValueAt(mem_nameT.getText(), row, 0);
					table.setValueAt(mem_ageT.getText(), row, 1);
					table.setValueAt(mem_mfL.getText(), row, 2);
					table.setValueAt(mem_phoneT.getText(), row, 3);
					table.setValueAt(mem_adrT.getText(), row, 4);
					table.setValueAt(mem_time.getText(), row, 5);
					}
			});
			
			//
			// 회원 정보 창
			//
			mem_im.setFont(new Font("맑은 고딕", Font.BOLD, 20)); 
			mem_im.setLocation(600,50);
			mem_im.setSize(100,25);
			this.add(mem_im);
			
			mem_nameL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 이름 레이블
			mem_nameL.setLocation(600,100);
			mem_nameL.setSize(200,25);
			this.add(mem_nameL);
			
			mem_nameT.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 이름 텍스트
			mem_nameT.setLocation(670,100);
			mem_nameT.setSize(200,25);
			this.add(mem_nameT);
			
			mem_ageL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 나이 레이블
			mem_ageL.setLocation(600,150);
			mem_ageL.setSize(200,25);
			this.add(mem_ageL);
			
			mem_ageT.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 나이 텍스트
			mem_ageT.setLocation(670,150);
			mem_ageT.setSize(50,25);
			this.add(mem_ageT);
			
			mem_sL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 성별 레이블
			mem_sL.setLocation(600,200);
			mem_sL.setSize(60,25);
			this.add(mem_sL);
			
			//c.add(mem_rb1); c.add(mem_rb2);
			//mem_rb1.setLocation(670, 280);
			//mem_rb2.setLocation(700, 280);
			//mem_rb1.setSize(60,25);
			//mem_rb2.setSize(60,25);
			
			mem_mfL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 성별 표시하는 레이블
			mem_mfL.setLocation(670,200);
			mem_mfL.setSize(60,25);
			this.add(mem_mfL);
			
			mem_phoneL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 전화번호 레이블
			mem_phoneL.setLocation(600,250);
			mem_phoneL.setSize(100,25);
			this.add(mem_phoneL);
			
			mem_phoneT.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 전화번호 텍스트
			mem_phoneT.setLocation(700,250);
			mem_phoneT.setSize(300,25);
			this.add(mem_phoneT);
			
			mem_adrL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 주소 레이블
			mem_adrL.setLocation(600,350);
			mem_adrL.setSize(60,25);
			this.add(mem_adrL);
			
			mem_adrT.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 주소 텍스트
			mem_adrT.setLocation(670,350);
			mem_adrT.setSize(400,150);
			mem_adrT.setLineWrap(true);
			this.add(mem_adrT);
			
			mem_timeL.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 남은시간 레이블
			mem_timeL.setLocation(600,300);
			mem_timeL.setSize(100,25);
			this.add(mem_timeL);
			
			mem_time.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 남은시간 표시하는 레이블
			mem_time.setLocation(700,300);
			mem_time.setSize(100,25);
			this.add(mem_time);
			
			back.setBounds(950,550,80,40); 
			back.setFont(font);
			back.setBackground(back_c);
			back.setForeground(button_c);
			this.add(back);	
		
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					seat s = new seat();
					MainFrame.this.add(s);
					MainFrame.this.setVisible(true);
				}
			});
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
		Font butfont = new Font("맑은 고딕", Font.BOLD, 30);
		JButton back = new JButton("이전");
		public Login() {
			id.setBounds(200,100,100,30);
			id.setFont(butfont);
			
			idt.setBounds(400,100,200,30);
			
			password.setBounds(200,200,170,30);
			password.setFont(butfont);
			
			passwordt.setBounds(400,200,200,30);
			
			back.setBounds(600,450,80,40); 
			back.setFont(font);
			back.setBackground(button_c);		
			
			ok.setBounds(200,320,100,40);
			ok.setFont(butfont);
			ok.setBackground(back_c);
			ok.setForeground(button_c);
			
			cancel.setBounds(400,320,100,40);
			cancel.setFont(butfont);
			cancel.setBackground(back_c);
			cancel.setForeground(button_c);
			
			this.setSize(700, 500);
			this.setLocation(300, 100);	
			this.setLayout(null);
			this.add(id);
			this.add(idt);
			this.add(password);
			this.add(passwordt);
			this.add(ok);
			this.add(cancel);
			this.add(back);
			
			ok.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
//					if (idt.getText().equals("test") && passwordt.getText().equals("1234")) {
						AdminPanel adminp = new AdminPanel();
						MainFrame.this.add(adminp);
						adminp.updateUI();
						setVisible(false);
						back.setVisible(false);
//					}
//					else {
//						JOptionPane.showMessageDialog(null, "로그인 실패", "ERROR", JOptionPane.ERROR_MESSAGE );
//					}
				}
			});
			
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					seat s = new seat();
					MainFrame.this.add(s);
					MainFrame.this.setVisible(true);
				}
			});		
		}
	}
	public static void main(String [] args) {
		new MainFrame();
	}
}