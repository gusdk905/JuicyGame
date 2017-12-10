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
		btnStartGame.setBorderPainted(false); //��ư �׵θ� ����
		btnStartGame.setContentAreaFilled(false); //��ư ��� ���� 
		btnStartGame.setFocusPainted(false); // ��Ŀ�� �κ� ����
		btnStartGame.setBounds(50,470,300,85); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton���� ���� ��ư ����
		setComponentZOrder(btnStartGame,0);
		
	} //constructor
	//ImageIcon Imgbkgnd = new ImageIcon("imgs/backgroundmenu.png"); //��� �̹���

	public void paintComponent(Graphics g) {
	//g.drawImage(Imgbkgnd.getImage(), 0, 0, null);
	 super.paintComponent(g);
	 } //paintComponent�� ����̹��� �ҷ��� �׸�.
		
	
	public void moveStartGame() {
		ScenePanel.getInstance().selectScene("Game");
	}//GameScene���� �̵�. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource(); 

			if( btnStartGame == obj ){	// ���۹�ư ������ �̹��� �������� ���� ��ư ����ȿ�� ����.
				btnStartGame.setIcon(new ImageIcon("images/startHover.png"));
			}
		} //press �� �̺�Ʈ
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();

			if( btnStartGame == obj ){ //���� ��ư���� Release�� moveStartGame�޼ҵ� ȣ
				moveStartGame();
			}
			btnStartGame.setIcon(new ImageIcon("images/start.png")); 
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
} 