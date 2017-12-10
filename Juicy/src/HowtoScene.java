import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HowtoScene extends JPanel
{
	private JLabel lblTitle; 
	private JButton btnStartGame;
	private JButton btnHowTo;
	// 2. declaration of the listener object
	private GameMListener gameML;
	
	public HowtoScene() {
		setBounds(0,0,810,600);
		setBackground(Color.white);
		setLayout(null);		
		// 2. creating of the listener object
		gameML = new GameMListener();
		
		lblTitle = new JLabel(new ImageIcon("images/HowtoScene.png"));
		lblTitle.setBounds(0,0,810,600);
		add(lblTitle); //ImageIcon
		
			
		btnStartGame = new JButton(new ImageIcon("images/start.png"));
		btnStartGame.setBorderPainted(false); //버튼 테두리 제거
		btnStartGame.setContentAreaFilled(false); //버튼 배경 제거 
		btnStartGame.setFocusPainted(false); // 포커스 부분 제거
		btnStartGame.setBounds(50,470,300,85); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton으로 시작 버튼 생성
		setComponentZOrder(btnStartGame,0);
		
	} //constructor
	//ImageIcon Imgbkgnd = new ImageIcon("imgs/backgroundmenu.png"); //배경 이미지

	public void paintComponent(Graphics g) {
	//g.drawImage(Imgbkgnd.getImage(), 0, 0, null);
	 super.paintComponent(g);
	 } //paintComponent로 배경이미지 불러와 그림.
		
	
	public void moveStartGame() {
		ScenePanel.getInstance().selectScene("Game");
	}//GameScene으로 이동. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource(); 

			if( btnStartGame == obj ){	// 시작버튼 누르면 이미지 변경으로 인한 버튼 누름효과 보임.
				btnStartGame.setIcon(new ImageIcon("images/startHover.png"));
			}
		} //press 시 이벤트
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();

			if( btnStartGame == obj ){ //시작 버튼에서 Release시 moveStartGame메소드 호
				moveStartGame();
			}
			btnStartGame.setIcon(new ImageIcon("images/start.png")); 
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
} 