import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;


public class EndingScene2 extends JPanel {

   //�г� ũ��
   private final int WIDTH = 810, HEIGHT = 600;
   
   private JButton btnStart, btnMenu;            //start��ư, menu��ư
   private ImageIcon imgStart, imgMenu;
   private ImageIcon imgStartClicked, imgMenuClicked;
   private JLabel bkgndImgResult;   //����� �˷��ִ� ��, �޹�� �����ϴ� ��
   private ImageIcon imgBossbkgnd;
   private ImageIcon imgMainpic;
   private JPanel txtTemp;
   private JLabel lblResult;
   private JLabel lblResultNextLine;
   private String txtResult;
   private Font fnt;
   private GameListener gameL;                        //�׼� ������
   private GameMListener gameML;                     //���콺 ������
   private int Money;
   public EndingScene2() {
      //�г� ũ�� �� ���̾ƿ� �޴��� ����
      setBounds(0, 0, WIDTH, HEIGHT);
      setBackground(new Color(221, 221, 207));
      setLayout(null);
      imgMainpic=new ImageIcon("images/mainpic.jpg");
      Money= 0;
      
      gameL = new GameListener();                     //�׼Ǹ�����
      gameML = new GameMListener();                  //���콺������

      //������ ó�� ��������  �����  �׸�   
      imgBossbkgnd = new ImageIcon("images/angryMan.png");
      bkgndImgResult = new JLabel(imgBossbkgnd);
      bkgndImgResult.setBounds(50, 180, 257, 288);
      add(bkgndImgResult);
      
      //������ ������ ���̴� ��� ��  ��ǳ�� ���
      txtTemp=new JPanel();
      txtTemp.setBackground(new Color(254, 184, 83)); // ���� ������ ���� ����
      txtTemp.setBounds(270, 100, 500, 180);
      txtTemp.setBorder(new LineBorder(new Color(255, 212, 84),10,true)); // ���� ������ �׵θ��� ����
      fnt=new Font("���� ���", Font.BOLD, 40);
      txtResult= String.valueOf(Money); // testing\
      
      // gamescene���� getText�� �����(txtResult)�� �������� �޼ҵ� �߰�
      lblResult = new JLabel("$"+txtResult);
      lblResult.setBorder(BorderFactory.createLineBorder(Color.red, 3));
      lblResultNextLine=new JLabel(" �� �������� �� �ذ��!!!");
      //lblResult.setHorizontalAlignment(SwingConstants.CENTER);
      lblResult.setFont(fnt);
      lblResult.setForeground(new Color(105, 44, 8)); // ���� ������ �۾� ���� ����
      lblResultNextLine.setFont(fnt);
      lblResultNextLine.setForeground(new Color(105, 44, 8)); // ���� ������ �۾� ���� ����
      txtTemp.add(lblResult);
      txtTemp.add(lblResultNextLine);
      add(txtTemp);

      //�Ʒ��� �г�
      
      //Menu������ �̵��ϴ� ��ư
      imgMenu = new ImageIcon("images/continueHover.png");
      btnMenu = new JButton(imgMenu);
      btnMenu.setHorizontalAlignment(SwingConstants.CENTER);
      btnMenu.setBounds(90, 470, 235, 75);
      btnMenu.setBorderPainted(false);
      btnMenu.setContentAreaFilled(false);
      btnMenu.setFocusPainted(false);
      btnMenu.addActionListener(gameL);               //��ư Ŭ���� �޴������� �̵�
      btnMenu.addMouseListener(gameML);               //��ư �� ����
      add(btnMenu);
      imgStartClicked=new ImageIcon("images/continue.png");

      
      //Game������ ���� ��ư
      imgStart = new ImageIcon("images/exitHover.png");
      btnStart = new JButton(imgStart);
      btnStart.setHorizontalAlignment(SwingConstants.CENTER);
      btnStart.setBounds(495, 470, 235, 75);
      btnStart.setBorderPainted(false);
      btnStart.setContentAreaFilled(false);
      btnStart.setFocusPainted(false);
      btnStart.addActionListener(gameL);               //��ư Ŭ���� ���Ӿ����� �̵�
      btnStart.addMouseListener(gameML);               //��ư �� ����
      add(btnStart);
      imgMenuClicked=new ImageIcon("images/exit.png");
   }
   public void setMoney(int money) {Money = money;
   System.out.println(Money);}
   public void Init() {
	   lblResult.setText("$" + Money);
   }
   
   private class GameListener implements ActionListener {

      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();
         //Menu��ư ������ Game������ �̵�
         if (btnMenu == obj) {ScenePanel.getInstance().selectScene("Game");} 
         //Start��ư ������ Menu������ �̵�
         else if (btnStart == obj) {ScenePanel.getInstance().selectScene("Menu");}
      }
   }

   public void paintComponent(Graphics page) {
      super.paintComponent(page);
      page.drawImage(imgMainpic.getImage(), 0, 0, this); // 
   }
   
   private class GameMListener implements MouseListener {
      public void mouseClicked(MouseEvent event) {}
      public void mousePressed(MouseEvent event) {
         Object obj = event.getSource();
         //��ư ������ �� ��Ӱ���
         if (btnMenu == obj) { btnMenu.setIcon(imgStartClicked); }
         else if (btnStart == obj) { btnStart.setIcon(imgMenuClicked); }
      }
      public void mouseReleased(MouseEvent event) {
         //��ư�� �����ٰ� ��� ��� ��ư���� �� ���ƿ�
         btnMenu.setIcon(imgMenu);
         btnStart.setIcon(imgStart);
      }
      public void mouseEntered(MouseEvent event) {}
      public void mouseExited(MouseEvent event) {}
   }
}