package studycafe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame{
	Color back_c = new Color(0x0E1D35);
	Color button_c = new Color(0xDDDEE5);//0xFFEFD6
	Font font = new Font("맑은 고딕", Font.BOLD, 20);
	Font font_in = new Font("맑은 고딕", Font.BOLD, 15);
	Font button_font = new Font("맑은 고딕", Font.BOLD, 40);
	static String url = "jdbc:mysql://cjh00.duckdns.org:3307/studycafe";
	static String mysql_user = "root";
	static String mysql_password = "0939";
	static int selected_seat = 0; // 선택한 좌석의 번호
	static int usestatus = 0; // 선택한 좌석의 이용여부
	static int new_selected_seat = 0; // 새로 선택한 좌석의 번호
	static int new_usestatus = 0; // 새로 선택한 좌석의 이용여부
	static String npn = null;
	static String mpn = null;
	static String seat_id = null;
	static Thread[] ths = new Thread[60];
	// 좌석 이용시 (회원,비회원이 이용권 결제 후 이용시 , 회원이 남은시간으로 이용시 ) buytime에 SELECT now(); 저장해야함
	
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
	////////////////////////////////////////////프로그램 시작 시 이용 중인 회원 시간 차감/////////////////////////////
	class TimerRunnable implements Runnable{ 
		String id = null;
		String time = null;
		int timer = 1;
		public TimerRunnable(String id) {
			this.id = id;
		}
		public void run() {
			try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql2 = "SELECT time FROM member where ID = '"+id+"'";
				Statement stmt2 = con.createStatement();
				ResultSet rs = stmt2.executeQuery(sql2);
				if(rs.next())
					timer = 1;
				else
					timer = 0;
				if(con!=null)
					con.close();
				if(stmt2!=null)
					stmt2.close();	
			}
			catch(ClassNotFoundException error) {
				JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
				return;
			}
			catch(SQLException error) {
				JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
				return;
			}
			if(timer == 1) {
				while(true) {
					try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE member SET time = SUBTIME(time, 1) where id = '"+ id +"'";
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
						try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection con =
							DriverManager.getConnection(url, mysql_user, mysql_password);
							String sql2 = "SELECT time FROM member where ID = '"+id+"'";
							Statement stmt2 = con.createStatement();
							ResultSet rs = stmt2.executeQuery(sql2);
							while(rs.next()) {
								time = rs.getString(1);
								if(time.equals("00:00:00"))
									break;
								System.out.println(id + " : " +time);
							}
							if(con!=null)
								con.close();
							if(stmt2!=null)
								stmt2.close();	
						}
						catch(ClassNotFoundException error) {
							JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
							return;
						}
						catch(SQLException error) {
							JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
							return;
						}
						
						if(conn!=null)
							conn.close();
						if(stmt!=null)
							stmt.close();
						if(time.equals("00:00:00")) {
							break;
						}
					}
					catch(ClassNotFoundException error) {
						JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
						return;
					}
					
					catch(SQLException error) {
						JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
						return;
					}
					
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {
						return;
					}
				}
				try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn =
					DriverManager.getConnection(url, mysql_user, mysql_password);
					String sql = "UPDATE seat SET change_count = change_count +1, use_time = "
							+ "addtime(use_time, timediff(now(),buytime)), " // 현재시간과 결제시 시간 계산 후 시간 더하기
							+ "use_Status = 0, buytime = null, ID = null WHERE (ID = '" // buytime 초기화
							+id + "')";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					if(conn!=null)
						conn.close();
					if(stmt!=null)
						stmt.close();
				}
				catch(ClassNotFoundException error) {
					JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
					return;
				}
				
				catch(SQLException error) {
					JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
					return;
				}
			}
			else {
				while(true) {
					try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE nonmember SET ticket = SUBTIME(ticket, 1) where number = '"+ id +"'";
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
						try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection con =
							DriverManager.getConnection(url, mysql_user, mysql_password);
							String sql2 = "SELECT ticket FROM nonmember where number = '"+id+"'";
							Statement stmt2 = con.createStatement();
							ResultSet rs = stmt2.executeQuery(sql2);
							while(rs.next()) {
								time = rs.getString(1);
								if(time.equals("00:00:00"))
									break;
								System.out.println(id + " : " +time);
							}
							if(con!=null)
								con.close();
							if(stmt2!=null)
								stmt2.close();	
						}
						catch(ClassNotFoundException error) {
							JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
							return;
						}
						catch(SQLException error) {
							JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
							return;
						}
						
						if(conn!=null)
							conn.close();
						if(stmt!=null)
							stmt.close();
						if(time.equals("00:00:00")) {
							break;
						}
					}
					catch(ClassNotFoundException error) {
						JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
						return;
					}
					
					catch(SQLException error) {
						JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
						return;
					}
					
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {
						return;
					}
				}
				try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn =
					DriverManager.getConnection(url, mysql_user, mysql_password);
					String sql = "UPDATE seat SET change_count = change_count +1, use_time = "
							+ "addtime(use_time, timediff(now(),buytime)), " // 현재시간과 결제시 시간 계산 후 시간 더하기
							+ "use_Status = 0, buytime = null, ID = null WHERE (ID = '" // buytime 초기화
							+id + "')";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					if(conn!=null)
						conn.close();
					if(stmt!=null)
						stmt.close();
				}
				catch(ClassNotFoundException error) {
					JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
					return;
				}
				
				catch(SQLException error) {
					JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
					return;
				}
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn =
					DriverManager.getConnection(url, mysql_user, mysql_password);
					String sql = "DELETE FROM nonmember where number = '"+id+"'";
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					if(conn!=null)
						conn.close();
					if(stmt!=null)
						stmt.close();
				}
				catch(ClassNotFoundException error) {
					JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
					return;
				}
				
				catch(SQLException error) {
					JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
					return;
				}
			}
		}
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
			
			try { // 선택한 좌석이 이용여부라면 색깔 다르게 지정
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT use_Status FROM seat"; // 선택한 좌석의 사용여부
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);	
				int i = 0;
				while(rs.next()) {
					seat[i] = new JButton(Integer.toString(i+1));
					seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
					seat[i].setSize(80,50);
					seat[i].setBackground(button_c);
					if(rs.getInt(1) == 1) {
						seat[i].setBackground(new Color(0xaaacbe));
					}
					i++;
				}
				
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
			
			for(int i = 0;i<60;i++) {
				if(ths[i] != null)
					ths[i].stop();
			}
			try { /////////////////////남은 시간 차감////////////////////////////////
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT ID FROM seat WHERE use_Status = 1"; // 선택한 좌석의 사용여부
				
				Statement stmt = conn.createStatement();
				ResultSet result2 = stmt.executeQuery(sql);	
				int i = 0;
				while(result2.next()) {
					String id = result2.getString(1);
					TimerRunnable runnable = new TimerRunnable(id);
					ths[i] = new Thread(runnable);
					ths[i].start();
					i ++;
					System.out.println(id);
				}	
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
			
			for(int i = 0; i < 60 ; i++) {
				seat[i].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {	
						selected_seat = Integer.parseInt(e.getActionCommand()); // 선택한 좌석 번호 저장
						try { // 선택한 좌석의 이용여부 저장
							// MySQL DB용 드라이로드
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn =
							DriverManager.getConnection(url, mysql_user, mysql_password);
							String sql = "SELECT use_Status FROM seat WHERE (seat_number = '"
									+selected_seat + "')"; // 선택한 좌석의 사용여부
							
							Statement stmt = conn.createStatement();
							ResultSet result2 = stmt.executeQuery(sql);	
							while(result2.next()) {
								usestatus = result2.getInt(1);
							
							}	
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
						
						if(usestatus == 1) { // 이용중인 좌석선택 시
							String password = null;
							boolean change = false;
							boolean nonchange = false; // 비회원 퇴실 시 삭제를 위해
							JPasswordField pf = new JPasswordField();
							int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

							if (okCxl == JOptionPane.OK_OPTION) {
							  password = new String(pf.getPassword());
							}
							else {
								return;
							}
							try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
								Class.forName("com.mysql.cj.jdbc.Driver");
								Connection conn =
								DriverManager.getConnection(url, mysql_user, mysql_password);
								String sql = "select ID from seat where seat_number = '" + selected_seat + "'"; //회원 자리이동, 퇴실
								Statement stmt = conn.createStatement();
								ResultSet rs = stmt.executeQuery(sql);
								if(rs.next()) {
									seat_id = rs.getString(1);	
								}
								if(conn!=null)
									conn.close();
								if(stmt!=null)
									stmt.close();			
							}catch(ClassNotFoundException error) {
								JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
								return;
							}catch(SQLException error) {
								JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
								return;
							}
							
							try {
								Class.forName("com.mysql.cj.jdbc.Driver");
								Connection con = DriverManager.getConnection(url, mysql_user, mysql_password);
								String sql2 = "select password from member where ID = '" + seat_id + "'"; // 회원 자리이동, 퇴실
								Statement stmt2 = con.createStatement();
								ResultSet rs2 = stmt2.executeQuery(sql2);
								if (rs2.next()) {
									if (rs2.getString(1).equals(password)) {
										change = true;
									}
									else {
										JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
									}
								}
								else {
									try {
										Class.forName("com.mysql.cj.jdbc.Driver");
										Connection conn = DriverManager.getConnection(url, mysql_user, mysql_password);
										String sql = "select number from nonmember where number = '" + seat_id + "'"; // 비회원 자리이동, 퇴실
										Statement stmt = con.createStatement();
										ResultSet rs = stmt2.executeQuery(sql);
										if (rs.next()) {
											if (rs.getString(1).equals(password)) {
												change = true;
												nonchange = true;
											}
											else {
												JOptionPane.showMessageDialog(null, "전화번호가 틀렸습니다.");
											}
										}

										if (conn != null)
											conn.close();
										if (stmt != null)
											stmt.close();
									} catch (ClassNotFoundException error) {
										JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
										return;
									}catch (SQLException error) {
										JOptionPane.showMessageDialog(null, "DB접속오류22" + error.getMessage());
										return;
									}
								}
								if (con != null)
									con.close();
								if (stmt2 != null)
									stmt2.close();
							} catch (ClassNotFoundException error) {
								JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
								return;
							}catch (SQLException error) {
								JOptionPane.showMessageDialog(null, "DB접속오류22" + error.getMessage());
								return;
							}
							////////////////////////////////////////////  좌석이동 ////////////////////////////
							if(change) {
								int result_n = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
										"좌석이동을 하시겠습니까?", "스터디 카페", JOptionPane.YES_NO_OPTION);
								if(result_n == JOptionPane.YES_OPTION) {
									try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
										Class.forName("com.mysql.cj.jdbc.Driver");
										Connection conn =
										DriverManager.getConnection(url, mysql_user, mysql_password);
										String sql = "UPDATE seat SET change_count = change_count +1, use_time = "
												+ "addtime(use_time, timediff(now(),buytime)), " // 현재시간과 결제시 시간 계산 후 시간 더하기
												+ "use_Status = 0, buytime = null, ID = null WHERE (seat_number = '" // buytime 초기화
												+selected_seat + "')"; // 현재 좌석의 자리이동횟수, 여부, 사용시간 변경
										System.out.println(sql);
										Statement stmt = conn.createStatement();
										stmt.executeUpdate(sql);
										if(conn!=null)
											conn.close();
										if(stmt!=null)
											stmt.close();
									}
									catch(ClassNotFoundException error) {
										JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
										return;
									}
									
									catch(SQLException error) {
										JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
										return;
									}
									JOptionPane.showMessageDialog(null, " 이동할 좌석을 선택해주세요.","Message",JOptionPane.INFORMATION_MESSAGE);
									MainFrame.this.setVisible(false);
									MainFrame.this.getContentPane().removeAll();
									new_seat s1 = new new_seat();
									MainFrame.this.add(s1);
									MainFrame.this.setVisible(true);		
								}
								else {////////////////////////////////////////////  퇴실   ////////////////////////////
									int answer = JOptionPane.showConfirmDialog(null,	// OptionPaneEx.this : 가운데 출력
											"퇴실 하시겠습니까?", "스터디 카페", JOptionPane.YES_NO_OPTION);
									if(answer == JOptionPane.YES_OPTION) {
										try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
											Class.forName("com.mysql.cj.jdbc.Driver");
											Connection conn =
											DriverManager.getConnection(url, mysql_user, mysql_password);
											String sql = "UPDATE seat SET use_time = addtime(use_time, timediff(now(),buytime)), " // 현재시간과 결제시 시간 계산 후 시간 더하기
													+ "use_Status = 0, buytime = null, ID = null WHERE (seat_number = '" // buytime 초기화
													+selected_seat + "')"; // 현재 좌석의 자리이동횟수, 여부, 사용시간 변경
											System.out.println(sql);
											Statement stmt = conn.createStatement();
											stmt.executeUpdate(sql);
											if(conn!=null)
												conn.close();
											if(stmt!=null)
												stmt.close();
										}
										catch(ClassNotFoundException error) {
											JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
											return;
										}
										
										catch(SQLException error) {
											JOptionPane.showMessageDialog(null, "DB접속오류22"+error.getMessage());
											return;
										}
										if(nonchange) {
											try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
												Class.forName("com.mysql.cj.jdbc.Driver");
												Connection conn =
												DriverManager.getConnection(url, mysql_user, mysql_password);
												String sql = "DELETE FROM nonmember where number = '"+seat_id+"'"; // 현재 좌석의 자리이동횟수, 여부, 사용시간 변경
												System.out.println(sql);
												Statement stmt = conn.createStatement();
												stmt.executeUpdate(sql);
												if(conn!=null)
													conn.close();
												if(stmt!=null)
													stmt.close();
											}
											catch(ClassNotFoundException error) {
												JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
												return;
											}
											
											catch(SQLException error) {
												JOptionPane.showMessageDialog(null, "여긴가?");
												return;
											}
										}
										MainFrame.this.setVisible(false);
										MainFrame.this.getContentPane().removeAll();
										seat s = new seat();
										MainFrame.this.add(s);
										MainFrame.this.setVisible(true);
									}
								}
								
							}
						}
							
						else { // 좌석 선택 후 회원 비회원 선택 화면
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
									mpn = jv.IDtf_j.getText();
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
												"('"+ jv.Natf_j.getText() + "', " + jv.Agef_j.getText() + ", " + gender + ", '" + jv.Phtf_j.getText() + "', '0', '"+ jv.ADtf_j.getText() +
												"', '" + jv.IDtf_j.getText() + "', '" + jv.PStf_j.getText() + "')";
										PreparedStatement ptmt = conn.prepareStatement(sql);
										ptmt.executeUpdate();
										
										if(conn!=null)    //db 연결해제
											conn.close();
										if(ptmt!=null)
											ptmt.close();
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
								MainFrame.this.setVisible(false);
								MainFrame.this.getContentPane().removeAll();
								seat s = new seat();
								MainFrame.this.add(s);
								MainFrame.this.setVisible(true);
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
						nmv.join_n.addActionListener(new ActionListener() {	// 확인 버튼 눌렀을 때 
							public void actionPerformed(ActionEvent e) {
								npn = nmv.Phtf_n.getText(); //비회원 전화번호 저장
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
	class new_seat extends JPanel{ // 좌석 이동시 뜨게 하는 좌석 선택 창
		JButton[] new_seat = new JButton[60];
		public new_seat() {
			this.setBackground(back_c);
			this.setLayout(null);
			this.setSize(1280,700);
			this.setLocation(-10,10);
			
			try { // 선택한 좌석의 이용여부 저장
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT use_Status FROM seat"; // 선택한 좌석의 사용여부
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);	
				int i = 0;
				while(rs.next()) {
					new_seat[i] = new JButton(Integer.toString(i+1));
					new_seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
					new_seat[i].setSize(80,50);
					new_seat[i].setBackground(button_c);
					if(rs.getInt(1) == 1) {
						new_seat[i].setBackground(new Color(0xaaacbe));
					}
					i++;
				}
				
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
			new_seat[0].setLocation(125,100);
			this.add(new_seat[0]);
			new_seat[1].setLocation(205,100);
			this.add(new_seat[1]);
			new_seat[2].setLocation(285,100);
			this.add(new_seat[2]);
			new_seat[3].setLocation(365,100);
			this.add(new_seat[3]);
			new_seat[4].setLocation(445,100);
			this.add(new_seat[4]);
			new_seat[5].setLocation(525,100);
			this.add(new_seat[5]);
			new_seat[6].setLocation(605,100);
			this.add(new_seat[6]);
			new_seat[7].setLocation(685,100);
			this.add(new_seat[7]);
			new_seat[8].setLocation(765,100);
			this.add(new_seat[8]);
			new_seat[9].setLocation(845,100);
			this.add(new_seat[9]);

			new_seat[10].setLocation(125,150);
			this.add(new_seat[10]);
			new_seat[11].setLocation(205,150);
			this.add(new_seat[11]);
			new_seat[12].setLocation(285,150);
			this.add(new_seat[12]);
			new_seat[13].setLocation(365,150);
			this.add(new_seat[13]);
			new_seat[14].setLocation(445,150);
			this.add(new_seat[14]);
			new_seat[15].setLocation(525,150);
			this.add(new_seat[15]);
			new_seat[16].setLocation(605,150);
			this.add(new_seat[16]);
			new_seat[17].setLocation(685,150);
			this.add(new_seat[17]);
			new_seat[18].setLocation(765,150);
			this.add(new_seat[18]);
			new_seat[19].setLocation(845,150);
			this.add(new_seat[19]);

			new_seat[20].setLocation(125,300);
			this.add(new_seat[20]);
			new_seat[21].setLocation(205,300);
			this.add(new_seat[21]);
			new_seat[22].setLocation(285,300);
			this.add(new_seat[22]);
			new_seat[23].setLocation(365,300);
			this.add(new_seat[23]);
			new_seat[24].setLocation(445,300);
			this.add(new_seat[24]);
			new_seat[25].setLocation(125,350);
			this.add(new_seat[25]);
			new_seat[26].setLocation(205,350);
			this.add(new_seat[26]);
			new_seat[27].setLocation(285,350);
			this.add(new_seat[27]);
			new_seat[28].setLocation(365,350);
			this.add(new_seat[28]);
			new_seat[29].setLocation(445,350);
			this.add(new_seat[29]);

			new_seat[30].setLocation(125,500);
			this.add(new_seat[30]);
			new_seat[31].setLocation(205,500);
			this.add(new_seat[31]);
			new_seat[32].setLocation(285,500);
			this.add(new_seat[32]);
			new_seat[33].setLocation(365,500);
			this.add(new_seat[33]);
			new_seat[34].setLocation(445,500);
			this.add(new_seat[34]);
			new_seat[35].setLocation(125,570);
			this.add(new_seat[35]);
			new_seat[36].setLocation(205,570);
			this.add(new_seat[36]);
			new_seat[37].setLocation(285,570);
			this.add(new_seat[37]);
			new_seat[38].setLocation(365,570);
			this.add(new_seat[38]);
			new_seat[39].setLocation(445,570);
			this.add(new_seat[39]);

			new_seat[40].setLocation(845,370);
			this.add(new_seat[40]);
			new_seat[41].setLocation(925,370);
			this.add(new_seat[41]);
			new_seat[42].setLocation(845,420);
			this.add(new_seat[42]);
			new_seat[43].setLocation(925,420);
			this.add(new_seat[43]);
			new_seat[44].setLocation(845,470);
			this.add(new_seat[44]);
			new_seat[45].setLocation(925,470);
			this.add(new_seat[45]);
			new_seat[46].setLocation(845,520);
			this.add(new_seat[46]);
			new_seat[47].setLocation(925,520);
			this.add(new_seat[47]);
			new_seat[48].setLocation(845,570);
			this.add(new_seat[48]);
			new_seat[49].setLocation(925,570);
			this.add(new_seat[49]);

			new_seat[50].setLocation(1075, 120);
			this.add(new_seat[50]);
			new_seat[51].setLocation(1075, 170);
			this.add(new_seat[51]);
			new_seat[52].setLocation(1075, 220);
			this.add(new_seat[52]);
			new_seat[53].setLocation(1075, 270);
			this.add(new_seat[53]);
			new_seat[54].setLocation(1075, 320);
			this.add(new_seat[54]);
			new_seat[55].setLocation(1075, 370);
			this.add(new_seat[55]);
			new_seat[56].setLocation(1075, 420);
			this.add(new_seat[56]);
			new_seat[57].setLocation(1075, 470);
			this.add(new_seat[57]);
			new_seat[58].setLocation(1075, 520);
			this.add(new_seat[58]);
			new_seat[59].setLocation(1075, 570);
			this.add(new_seat[59]);
			for(int i =0; i < 60; i++) {
				new_seat[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						new_selected_seat = Integer.parseInt(e.getActionCommand()); // 선택한 좌석 번호 저장
						try { // 선택한 좌석의 이용여부 저장
							// MySQL DB용 드라이로드
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn =
							DriverManager.getConnection(url, mysql_user, mysql_password);
							String sql = "SELECT use_Status FROM seat WHERE (seat_number = '"
									+new_selected_seat + "')"; // 새로 선택한 좌석의 사용여부
							
							Statement stmt = conn.createStatement();
							ResultSet result2 = stmt.executeQuery(sql);	
							while(result2.next()) {
								new_usestatus = result2.getInt(1);
							}
							
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
						if(new_usestatus == 1) { // 새로선택한 좌석의 사용여부가 true 면
							JOptionPane.showMessageDialog(null, "사용중인 좌석입니다.");
							return;
						}
						else {
							LocalDateTime now = LocalDateTime.now();	 
					        // 포맷팅
					        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
							try { 
								// MySQL DB용 드라이로드
								Class.forName("com.mysql.cj.jdbc.Driver");
								Connection conn9 =
										DriverManager.getConnection(url, mysql_user, mysql_password);
								String sql = "UPDATE seat SET use_count = use_count + 1, "
										+ "use_Status = 1, ID = '"+ seat_id +"',"
												+ "buytime = '"+formatedNow+"' WHERE (seat_number = '" + new_selected_seat + "')"; 
											// 새로 선택한 좌석의 이용횟수 , 이용 여부 변경
					
								Statement stmt9 = conn9.createStatement();	
								stmt9.executeUpdate(sql);
								if(conn9!=null)    //db 연결해제
									conn9.close();
								if(stmt9!=null)
									stmt9.close();
					
							}
							catch(ClassNotFoundException error) {
								JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
								return;
							}
							catch(SQLException error) {
								JOptionPane.showMessageDialog(null, "DB접속오류");
								return;
							}
							JOptionPane.showMessageDialog(null, " 좌석이동이 완료되었습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
							MainFrame.this.setVisible(false);
							MainFrame.this.getContentPane().removeAll();
							seat s = new seat();
							MainFrame.this.add(s);
							MainFrame.this.setVisible(true);
						}
					}
				});
			}
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
		class loginListener implements ActionListener{ //로그인
			public void actionPerformed(ActionEvent e) {
				int login = -1;
				try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn1 =
					DriverManager.getConnection(url, mysql_user, mysql_password);
					String upsql = "select password from member where "
							+ "ID = '" + IDtf_m.getText() +"'"; // 선택한 좌석의 이용횟수, 여부 변경
					Statement upstmt = conn1.createStatement();
					ResultSet rs = upstmt.executeQuery(upsql);
					if(rs.next()) {
						if(rs.getString(1).equals(PStf_m.getText())){
							login = 1;
							mpn = IDtf_m.getText();
						}
						else
							login = 0;
					}
					if(conn1!=null)
						conn1.close();
					if(upstmt!=null)
						upstmt.close();
				}
				catch(ClassNotFoundException error) {
					JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
					return;
				}
				
				catch(SQLException error) {
					JOptionPane.showMessageDialog(null, "DB접속오류11"+error.getMessage());
					return;
				}
				if(login == 1) {	 		
					int result = JOptionPane.showConfirmDialog(null,  //아이디 비번 동일
							"이용권을 구매하시겠습니까?", "이용권 구매",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						MainFrame.this.setVisible(false);
						MainFrame.this.getContentPane().removeAll();
						member_buy mb = new member_buy();
						mb.Back_mb.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								MainFrame.this.setVisible(false);
								MainFrame.this.getContentPane().removeAll();
								seat s = new seat();
								MainFrame.this.add(s);
								MainFrame.this.setVisible(true);
							}
						});
						MainFrame.this.add(mb);
						MainFrame.this.setVisible(true);
					}
					else { 
						JOptionPane.showMessageDialog(null, "남은시간이 차감됩니다","이용권",JOptionPane.PLAIN_MESSAGE);
						String usertime = null;
						try {
							// MySQL DB용 드라이로드
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn =
							DriverManager.getConnection(url, mysql_user, mysql_password);
							String sql = "SELECT time FROM member WHERE (ID = '"+IDtf_m.getText() +"')"; // 남은시간 000000이면 남은시간 없습니다 메세지창 있으면 진행
							Statement stmt = conn.createStatement();
							ResultSet result1 = stmt.executeQuery(sql);	
							while(result1.next()) {
								usertime = result1.getString(1);
							}
							if(conn!=null)    //db 연결해제
								conn.close();
							if(stmt!=null)
								stmt.close();
							if(usertime.equals("00:00:00") || usertime == null) {
								JOptionPane.showMessageDialog(null, "남은시간이 없습니다.", "Message",JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							else {
								JOptionPane.showMessageDialog(null, "남은시간: " + usertime, "Message",JOptionPane.INFORMATION_MESSAGE);
								try { // 선택한 좌석의 이용횟수 +1 이용여부 1(true)
									
									Class.forName("com.mysql.cj.jdbc.Driver");
									Connection conn1 =
									DriverManager.getConnection(url, mysql_user, mysql_password);
									String upsql = "UPDATE seat SET use_count = use_count+1,"
											+ "use_Status = 1, buytime = now() , ID = '"+mpn+"' WHERE (seat_number = '"
											+selected_seat + "')"; // 선택한 좌석의 이용횟수, 여부 변경
									Statement upstmt = conn1.createStatement();
									upstmt.executeUpdate(upsql);
									if(conn1!=null)
										conn1.close();
									if(upstmt!=null)
										upstmt.close();
								}
								catch(ClassNotFoundException error) {
									JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
									return;
								}				
								catch(SQLException error) {
									JOptionPane.showMessageDialog(null, "DB접속오류11"+error.getMessage());
									return;
								}								
								MainFrame.this.setVisible(false);
								MainFrame.this.getContentPane().removeAll();
								seat s = new seat();
								MainFrame.this.add(s);
								MainFrame.this.setVisible(true);
							}			
						}
						catch(ClassNotFoundException error) {
							JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
							return;
						}
						catch(SQLException error) {
							JOptionPane.showMessageDialog(null, "DB접속오류");
							return;
						}
					}	
				}
				else if(login == 0) { //아이디만 동일
					JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요", "Message",JOptionPane.ERROR_MESSAGE);
				}
				else //아이디 비동일
					JOptionPane.showMessageDialog(null, "아이디를 다시 입력하세요", "Message",JOptionPane.ERROR_MESSAGE);	
			}
		}
	}
	
	class buyListener implements ActionListener{ //이용권 버튼 클릭시 이벤트(다른 클래스에서도 사용가능
		boolean buy = false; //false 일때 비회원 구매
		public buyListener(String id) {
			if(id.equals("회원"))
				buy = true;
		}
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null,
					"구매하시겠습니까?","이용권 구매",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				String time = e.getActionCommand();
				time = time.substring(14, time.indexOf("시"));
				time = time + ":00:00";
				LocalDateTime now = LocalDateTime.now();	 
		        // 포맷팅
		        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		        
				if(buy) {
					try {
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE studycafe.seat SET ID = '" + mpn + "',"
								+ "buytime = '" +formatedNow + "', use_status = '1', use_count = use_count + 1 WHERE"
								+ " (seat_number = '" + selected_seat + "')";
						System.out.println(sql);
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
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
					try {
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE member SET time = '"+time+"' where ID = '"+mpn+"'";
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
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
					
					
					JOptionPane.showMessageDialog(null, "구매완료 !", "구매완료",JOptionPane.PLAIN_MESSAGE);
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					seat s = new seat();
					MainFrame.this.add(s);
					MainFrame.this.setVisible(true);
				}
				else {
					try { //비회원 정보 저장 sql문
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "INSERT INTO nonmember VALUES" + 
								"('"+ npn + "', '" + time + "')";
						System.out.println(sql);
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
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
					
					try { //비회원 정보 seat에 저장 sql문
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE studycafe.seat SET ID = '" + npn + "',"
								+ "buytime = '" +formatedNow + "', use_status = '1', use_count = use_count + 1 WHERE"
								+ " (seat_number = '" + selected_seat + "')";
						System.out.println(sql);
						Statement stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						
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
					
					JOptionPane.showMessageDialog(null, "구매완료 !", "구매완료",JOptionPane.PLAIN_MESSAGE);
					MainFrame.this.setVisible(false);
					MainFrame.this.getContentPane().removeAll();
					seat s = new seat();
					MainFrame.this.add(s);
					MainFrame.this.setVisible(true);
				}
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
		private JButton Back_mb = new JButton("메인으로");		// 뒤로 가는 버튼
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
			
			String title = title1_mb.getText().substring(0,  title1_mb.getText().indexOf(" "));
			buyListener bL = new buyListener(title);
			
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
			
			Back_mb.setBounds(930,520,160,40); 
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
		private JButton Back_nb = new JButton("이전");		// 뒤로 가는 버튼
		public nonmember_buy() {
			this.setSize(1100,600);
			this.setLocation(90,90);
			setBackground(Color.WHITE);
			setLayout(null);
			
			title1_nb.setBounds(80,60,350,40);
			title1_nb.setFont(button_font);
			add(title1_nb);
			
			title2_nb.setBounds(550,140,100,40);
			title2_nb.setFont(font);
			add(title2_nb);
			
			String title = title1_nb.getText().substring(0,  title1_nb.getText().indexOf(" "));
			buyListener bL = new buyListener(title); //회원 구매 파트에서 가져옴
			
			bt1n.setBounds(380,200,200,60);
			bt1n.setFont(font_in);
			bt1n.setBackground(button_c);
			bt1n.addActionListener(bL);
			add(bt1n);
			
			bt2n.setBounds(600,200,200,60);
			bt2n.setFont(font_in);
			bt2n.setBackground(button_c);	
			bt2n.addActionListener(bL);
			add(bt2n);
			
			bt3n.setBounds(380,315,200,60);
			bt3n.setFont(font_in);
			bt3n.setBackground(button_c);	
			bt3n.addActionListener(bL);
			add(bt3n);
			
			bt4n.setBounds(600,315,200,60);
			bt4n.setFont(font_in);
			bt4n.setBackground(button_c);	
			bt4n.addActionListener(bL);
			add(bt4n);
			
			bt5n.setBounds(380,430,200,60);
			bt5n.setFont(font_in);
			bt5n.setBackground(button_c);	
			bt5n.addActionListener(bL);
			add(bt5n);
			
			bt6n.setBounds(600,430,200,60);
			bt6n.setFont(font_in);
			bt6n.setBackground(button_c);
			bt6n.addActionListener(bL);
			add(bt6n);
						
			Back_nb.setBounds(950,520,80,40); 
			Back_nb.setFont(font);
			Back_nb.setBackground(button_c);		
			add(Back_nb);
		}
	}
	class AdminPanel extends JPanel{
//		seat_color sc = new seat_color(); //좌석으로 보기
//		seat_main sg = new seat_main(); //그래프로 보기
		JRadioButton seatcolor = new JRadioButton("좌석으로 보기");
		JRadioButton seatgraph = new JRadioButton("그래프로 보기");
		ButtonGroup group = new ButtonGroup();
		JButton back = new JButton("이전");
		SeatDB[] seatdb = new SeatDB[60];  //seat각각의 객체 생성
		
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
			
			bar.sales.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					removeAll();
					add(bar, BorderLayout.NORTH);
					sales s = new sales();
					add(s);
					setVisible(true);
				}
			});
			
			this.add(bar, BorderLayout.NORTH);
			setBackground(button_c);
			this.setLocation(90, 60);
			this.setLayout(null);
			
			try {
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT * FROM seat";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				int i = 0;
				while(rs.next()) {
					seatdb[i] = new SeatDB();
					seatdb[i].seat_number = Integer.parseInt(rs.getString("seat_number"));
					if(rs.getString("change_count") !=null)
						seatdb[i].change_count = Integer.parseInt(rs.getString("change_count"));
					else
						seatdb[i].change_count = 0;
					if(rs.getString("use_time") !=null)
					{
						String[] time = rs.getString("use_time").split(":");
						int use_time = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
						seatdb[i].use_time = use_time;
					}
					else
						seatdb[i].use_time = 0;
					if(rs.getString("use_count") !=null)
						seatdb[i].use_count = Integer.parseInt(rs.getString("use_count"));
					else
						seatdb[i].use_count = 0;
					seatdb[i].use_Status = rs.getBoolean("use_Status");
					if(rs.getString("use_count") !=null)
						seatdb[i].ID = rs.getString("ID");
//					seatdb[i].ID = null; 굳이 null을 넣지않아도 null
					i+=1;
				}
//				JOptionPane.showMessageDialog(null, seatdb[0].change_count);
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
			seat_color sc = new seat_color(seatdb); //좌석으로 보기
			seat_main sg = new seat_main(seatdb); //그래프로 보기
			
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
	class SeatDB{
		int seat_number;
		int change_count;
		int use_time;
		int use_count;
		boolean use_Status;
		String ID;
		public int seat_calc() {
			int seatdata;
			if(change_count + use_count == 0)
				seatdata = 0;
			else
				seatdata = use_time / (change_count + use_count);
			return seatdata;
		}
		// (이용시간) / (이용횟수 + 이동횟수)
		// use_time / (change_count + use_count)
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
		SeatDB []seatdb;
		public seat_color(SeatDB[] seatdb) {
			setLocation(10,0);
			setSize(850, 570);
			this.setBackground(button_c);
			JButton[] seat = new JButton[60];
			this.seatdb = seatdb;
			int max = seatdb[0].seat_calc();
			for(int i = 1;i<60;i++) {
				if(max < seatdb[i].seat_calc())
					max = seatdb[i].seat_calc();
				if(max == 0)
					max++;
			}
			for(int i=0;i<60;i++) {			
				seat[i] = new JButton(Integer.toString(i+1));
				seat[i].setFont(new Font("맑은 고딕", Font.BOLD, 12));
				seat[i].setSize(60,45);
				if(((seatdb[i].seat_calc() * 100)/max)<=20)
					seat[i].setBackground(Color.red);
				else if(((seatdb[i].seat_calc() * 100)/max)<=70)
					seat[i].setBackground(Color.yellow);
				else
					seat[i].setBackground(Color.green);
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
			
			next.setBackground(back_c);
			next.setBorderPainted(false);
			prev.setBackground(back_c);
			prev.setBorderPainted(false);
			
			add(prev);
			add(next);
		}
	}
	class seat_main extends JPanel{
		seat_graph sg;
		underbar ub;
		int start = 0, end = 15;
		public seat_main(SeatDB[] seatdb) {
			setSize(850, 570);
			setLocation(10,50);
			setBackground(button_c);
			setLayout(null);
			sg = new seat_graph(seatdb, start, end);
			ub = new underbar();
			add(sg);
			add(ub);
			
			ub.next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					remove(sg);
					setVisible(false);
					start = (start + 15)%60;
					end = (end + 15)%60==0?60:(end+ 15)%60;
					sg = new seat_graph(seatdb, start, end);
					add(sg);
					setVisible(true);
				}
			});
			ub.prev.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					remove(sg);
					setVisible(false);
					start -= 15;
					end -= 15;
					start = (start+60)%60;
					end = (end + 60)%60==0?60:(end+60)%60;
					sg = new seat_graph(seatdb, start, end);
					add(sg);
					setVisible(true);
				}
			});
		}
	}
	class seat_graph extends JPanel{
		Font font = new Font("맑은 고딕", Font.BOLD, 11);
		int [] score = new int[60];
		int randomIndex;
		Random random = new Random();
		SeatDB[] seatdb;
		int start, end;
		public seat_graph(SeatDB[] seatdb, int start, int end) {
			setLocation(0, 0);
			setSize(850, 480);
			this.seatdb = seatdb;
			this.start = start;
			this.end = end;
		}
		public void paint(Graphics g){		
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
			int max = seatdb[0].seat_calc();
			for(int i = 1;i<60;i++) {
				if(max<seatdb[i].seat_calc())
					max = seatdb[i].seat_calc(); //가장 많은 이용시간
				if(max == 0)
					max++;
			}
			for(int i = start;i<end;i++) {
				g2.drawString("좌석"+(i+1), (i-start)*43+100,470);
				g.setColor(Color.blue);
				g.fillRect((i-start)*43+120, 450-((seatdb[i].seat_calc() * 100)/max)*4, 10,((seatdb[i].seat_calc() * 100)/max)*4);
			}
			g2.setStroke(dashed);
	        for(int i =1;i<11;i++) {
	        	g2.drawLine(100,450-i*40,750,450-i*40);
	        	g2.drawString(""+i*10, 60, 450-i*40);
	        }	   
		}
	}
	
	class member{
		String name;
		int age;
		boolean gender;
		String phone_number;
		String time;
		String address;
		String ID;
		String password;
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
		JLabel mem_ID = new JLabel("ID : ");
		JLabel mem_mfL = new JLabel();
		JLabel mem_time = new JLabel();
		
		JTextField mem_nameT = new JTextField();
		JTextField mem_ageT = new JTextField();
		JTextField mem_IDT = new JTextField();
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
		int rowCnt;
		String gender;
		member[] members;
		public User() {
			try {
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT COUNT(*) FROM member";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				rowCnt = 0;
				while(rs.next()) {
					rowCnt = rs.getInt(1);
				}				
				if(conn!=null)    //db 연결해제
					conn.close();
				if(stmt!=null)
					stmt.close();
				if(rs!=null)
					rs.close();
				members = new member[rowCnt]; //회원 수만큼 member 객체 생성
				conn = DriverManager.getConnection(url, mysql_user, mysql_password);
				sql = "SELECT * FROM member";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				int i = 0;
				while(rs.next()) {
					members[i] = new member();
					members[i].name = rs.getString("name");
					members[i].age = rs.getInt("age");
					members[i].gender = rs.getBoolean("gender");
					members[i].phone_number = rs.getString("phone_number");
					members[i].time = rs.getString("time");
					members[i].address = rs.getString("address");
					members[i].ID = rs.getString("ID");
					members[i].password = rs.getString("password");
					i++;
				}
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
			setSize(1100,650);
			this.setLayout(null);
			
			String[] columns = {"이름","나이","성별","전화번호","주소","남은시간", "ID"};
			String[][] member_db = new String[rowCnt][7];
			for(int i=0;i<rowCnt;i++) {
				if(members[i].gender)
					gender="여자";
				else
					gender="남자";
				member_db[i] = new String []{members[i].name, members[i].age+"", gender,
						members[i].phone_number, members[i].address, members[i].time,members[i].ID};
			}
			DefaultTableModel model = new DefaultTableModel(member_db,columns);
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
					mem_IDT.setText((String)table.getValueAt(row, 6));
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
					
					int row = table.getSelectedRow();

					try {
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "DELETE FROM `studycafe`.`member` WHERE (`ID` = '" +table.getValueAt(row, 6)+ "')";
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.executeUpdate();
						
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
					
					if(row == -1) {
						return;
					}
					else {
						model.removeRow(row); // 삭제
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
					
					try {
						// MySQL DB용 드라이로드
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn =
						DriverManager.getConnection(url, mysql_user, mysql_password);
						String sql = "UPDATE studycafe.member SET"
								+ " name = '" + mem_nameT.getText() +"',"
								+ " age = '" + mem_ageT.getText() +"',"
								+ " phone_number = '" + mem_phoneT.getText() + "',"
								+ " address = '" + mem_adrT.getText()+ "',"
								+ " ID = '" + mem_IDT.getText() + "' WHERE (ID = '" + table.getValueAt(row, 6)+"')";
						PreparedStatement ptmt = conn.prepareStatement(sql);
						ptmt.executeUpdate();
						
						if(conn!=null)    //db 연결해제
							conn.close();
						if(ptmt!=null)
							ptmt.close();
					}
					catch(ClassNotFoundException error) {
						JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
						return;
					}
					catch(SQLException error) {
						JOptionPane.showMessageDialog(null, "DB접속오류");
						return;
					}
					
					table.setValueAt(mem_nameT.getText(), row, 0);
					table.setValueAt(mem_ageT.getText(), row, 1);
					table.setValueAt(mem_mfL.getText(), row, 2);
					table.setValueAt(mem_phoneT.getText(), row, 3);
					table.setValueAt(mem_adrT.getText(), row, 4);
					table.setValueAt(mem_time.getText(), row, 5);
					table.setValueAt(mem_IDT.getText(), row, 6);	
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
			
			mem_ID.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // ID 레이블
			mem_ID.setLocation(800,150);
			mem_ID.setSize(200,25);
			this.add(mem_ID);
			
			mem_IDT.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // ID 레이블
			mem_IDT.setLocation(850,150);
			mem_IDT.setSize(170,25);
			this.add(mem_IDT);
			
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
	
	public class sales extends JPanel{
		Font font = new Font("맑은 고딕", Font.BOLD, 11);
		int [] sale = new int [12]; // 월 매출 입력받을 배열 (최대 1000만원)
		
		public sales() {
			setTitle("매출 그래프");
			setLocation(0, 0);
			setSize(900, 550);
			setLayout(new BorderLayout());
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setVisible(true);
		    try { // 선택한 좌석의 이용여부 저장
				// MySQL DB용 드라이로드
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn =
				DriverManager.getConnection(url, mysql_user, mysql_password);
				String sql = "SELECT * FROM sales"; // 선택한 좌석의 사용여부
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				int i = 0;
				String sales = null;
				while(rs.next()) {
					sales = rs.getString("price");
					sales = sales.substring(0,sales.length()-3);
					sale[i]=Integer.parseInt(sales);
					System.out.println(sale[i]);
					i++;
					
				}	
				if(conn!=null)    //db 연결해제
					conn.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(ClassNotFoundException error) {
				JOptionPane.showMessageDialog(null, "mysql driver 미설치 또는 드라이버 이름 오류");
			}
			catch(SQLException error) {
				JOptionPane.showMessageDialog(null, "DB접속오류");
			}
		}
		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			
			g.drawLine(100,50,100,450); // y축
			g.drawLine(100,450,750,450); // x축
			g.drawString("0", 60, 450);
			g.drawString("10000", 40, 60);
			g.drawString("단위 : 1000원", 750, 300);
			for(int i=1; i<13; i++) {
				g.drawString(i+"월", 100+i*50, 470); // x축의 월 표시
			}
			
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setStroke(new BasicStroke(5));		
			
			g2.setColor(Color.green);
			for(int i=1; i<12; i++) {
				g2.drawLine(100+i*50, 450-(sale[i-1]/25), 150+i*50, 450-(sale[i]/25));
			}
			g2.setColor(Color.black);
			for(int j=1; j<13; j++) {
				g2.drawString(""+sale[j-1], 95+j*50, 450-(sale[j-1]/25));
			}
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