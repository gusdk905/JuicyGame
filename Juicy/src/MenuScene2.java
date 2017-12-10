import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuScene2 extends JPanel
{
	private ImageIcon Imgbkgnd;
	private JLabel lblTitle; 
	private ImageIcon imgTitle;
	private JButton btnStartGame;
	private ImageIcon imgStartGame;
	private JButton btnHowTo;
	private ImageIcon imgHowTo;
	private JLabel lblKakao;
	private ImageIcon imgKakao;
	// 2. declaration of the listener object
	private GameMListener gameML;
	
	public MenuScene2() {
		setBounds(0,0,810,600);
		setBackground(Color.white);
		setLayout(null);		
		// 2. creating of the listener object
		gameML = new GameMListener();
		
		Imgbkgnd = new ImageIcon("images/mainpic.jpg");   //배경 이미지
		
		imgTitle=new ImageIcon("images/gameName.png");
		lblTitle = new JLabel(imgTitle);
		lblTitle.setBounds(50,50,700,150);
		add(lblTitle); //ImageIcon으로 제목 생성
		
		imgKakao=new ImageIcon("images/kakao.png"); // 로딩장식효과
		lblKakao=new JLabel(imgKakao);
	    lblKakao.setBounds(280, 350, 240, 240);
	    lblKakao.setVisible(false);
	    add(lblKakao);
		
	    imgStartGame=new ImageIcon("images/startHover.png");
		btnStartGame = new JButton(imgStartGame);
		btnStartGame.setBorderPainted(false); //버튼 테두리 제거
		btnStartGame.setContentAreaFilled(false); //버튼 배경 제거 
		btnStartGame.setFocusPainted(false); // 포커스 부분 제거
		btnStartGame.setBounds(50,470,300,85); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton으로 시작 버튼 생성
		
		imgHowTo=new ImageIcon("images/howtoHover.png");
		btnHowTo = new JButton(imgHowTo);
		btnHowTo.setBorderPainted(false); //버튼 테투리 제거
		btnHowTo.setContentAreaFilled(false); // 버튼 배경 제거
		btnHowTo.setFocusPainted(false); // 포커스 부분 제거
		btnHowTo.setBounds(450,470,300,85);	
		//btnHowTo.addActionListener(gameL);
		btnHowTo.addMouseListener(gameML);
		add(btnHowTo); //JButton으로 설명 버튼 생성
	
	} //constructor

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		page.drawImage(Imgbkgnd.getImage(), 0, 0, null);
		// setOpaque(false); //투명하게 만듦. ???
	 } //paintComponent로 배경이미지 불러와 그림.
	
	public void moveHowTo() {ScenePanel.getInstance().selectScene("Howto");} //HowtoScene으로 이동.
	public void moveStartGame() {ScenePanel.getInstance().selectScene("Game");}//GameScene으로 이동. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource();
			if( btnStartGame == obj ){	// 시작버튼 누르면 이미지 변경으로 인한 버튼 누름효과 보임.
				btnStartGame.setVisible(false);
				btnHowTo.setVisible(false);
				lblKakao.setVisible(true);
			}else if( btnHowTo == obj ){ // 설명버튼 누르면 이미지 변경으로 인한 버튼 누름효과 보임.
				btnStartGame.setVisible(false);
				btnHowTo.setVisible(false);
				lblKakao.setVisible(true);
			}
		} //press 시 이벤트
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();
			if( btnStartGame == obj ){ //시작 버튼에서 Release시 moveStartGame메소드 호		
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {}
				moveStartGame();
				btnStartGame.setVisible(true);
				btnHowTo.setVisible(true);
				lblKakao.setVisible(false);
				btnStartGame.setIcon(imgStartGame);
				
			}
			if( btnHowTo == obj ){	//설명 버튼에서 Release시 moveHowTo메소드 호출.
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {}
				moveHowTo();
				btnStartGame.setVisible(true);
				btnHowTo.setVisible(true);
				lblKakao.setVisible(false);
				btnHowTo.setIcon(imgHowTo);
			}
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
	
} 