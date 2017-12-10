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
		
		Imgbkgnd = new ImageIcon("images/mainpic.jpg");   //��� �̹���
		
		imgTitle=new ImageIcon("images/gameName.png");
		lblTitle = new JLabel(imgTitle);
		lblTitle.setBounds(50,50,700,150);
		add(lblTitle); //ImageIcon���� ���� ����
		
		imgKakao=new ImageIcon("images/kakao.png"); // �ε����ȿ��
		lblKakao=new JLabel(imgKakao);
	    lblKakao.setBounds(280, 350, 240, 240);
	    lblKakao.setVisible(false);
	    add(lblKakao);
		
	    imgStartGame=new ImageIcon("images/startHover.png");
		btnStartGame = new JButton(imgStartGame);
		btnStartGame.setBorderPainted(false); //��ư �׵θ� ����
		btnStartGame.setContentAreaFilled(false); //��ư ��� ���� 
		btnStartGame.setFocusPainted(false); // ��Ŀ�� �κ� ����
		btnStartGame.setBounds(50,470,300,85); 
		// 3. add the listener object to the component
		btnStartGame.addMouseListener(gameML);
		add(btnStartGame); //JButton���� ���� ��ư ����
		
		imgHowTo=new ImageIcon("images/howtoHover.png");
		btnHowTo = new JButton(imgHowTo);
		btnHowTo.setBorderPainted(false); //��ư ������ ����
		btnHowTo.setContentAreaFilled(false); // ��ư ��� ����
		btnHowTo.setFocusPainted(false); // ��Ŀ�� �κ� ����
		btnHowTo.setBounds(450,470,300,85);	
		//btnHowTo.addActionListener(gameL);
		btnHowTo.addMouseListener(gameML);
		add(btnHowTo); //JButton���� ���� ��ư ����
	
	} //constructor

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		page.drawImage(Imgbkgnd.getImage(), 0, 0, null);
		// setOpaque(false); //�����ϰ� ����. ???
	 } //paintComponent�� ����̹��� �ҷ��� �׸�.
	
	public void moveHowTo() {ScenePanel.getInstance().selectScene("Howto");} //HowtoScene���� �̵�.
	public void moveStartGame() {ScenePanel.getInstance().selectScene("Game");}//GameScene���� �̵�. 
	
	//1.
	private class GameMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			Object obj = event.getSource();
			if( btnStartGame == obj ){	// ���۹�ư ������ �̹��� �������� ���� ��ư ����ȿ�� ����.
				btnStartGame.setVisible(false);
				btnHowTo.setVisible(false);
				lblKakao.setVisible(true);
			}else if( btnHowTo == obj ){ // �����ư ������ �̹��� �������� ���� ��ư ����ȿ�� ����.
				btnStartGame.setVisible(false);
				btnHowTo.setVisible(false);
				lblKakao.setVisible(true);
			}
		} //press �� �̺�Ʈ
		public void mouseReleased(MouseEvent event) {
			Object obj = event.getSource();
			if( btnStartGame == obj ){ //���� ��ư���� Release�� moveStartGame�޼ҵ� ȣ		
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {}
				moveStartGame();
				btnStartGame.setVisible(true);
				btnHowTo.setVisible(true);
				lblKakao.setVisible(false);
				btnStartGame.setIcon(imgStartGame);
				
			}
			if( btnHowTo == obj ){	//���� ��ư���� Release�� moveHowTo�޼ҵ� ȣ��.
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