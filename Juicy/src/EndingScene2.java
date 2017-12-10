import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;


public class EndingScene2 extends JPanel {

   //패널 크기
   private final int WIDTH = 810, HEIGHT = 600;
   
   private JButton btnStart, btnMenu;            //start버튼, menu버튼
   private ImageIcon imgStart, imgMenu;
   private ImageIcon imgStartClicked, imgMenuClicked;
   private JLabel bkgndImgResult;   //결과를 알려주는 라벨, 뒷배경 결정하는 라벨
   private ImageIcon imgBossbkgnd;
   private ImageIcon imgMainpic;
   private JPanel txtTemp;
   private JLabel lblResult;
   private JLabel lblResultNextLine;
   private String txtResult;
   private Font fnt;
   private GameListener gameL;                        //액션 리스너
   private GameMListener gameML;                     //마우스 리스너
   private int Money;
   public EndingScene2() {
      //패널 크기 및 레이아웃 메니저 설정
      setBounds(0, 0, WIDTH, HEIGHT);
      setBackground(new Color(221, 221, 207));
      setLayout(null);
      imgMainpic=new ImageIcon("images/mainpic.jpg");
      Money= 0;
      
      gameL = new GameListener();                     //액션리스너
      gameML = new GameMListener();                  //마우스리스너

      //엔딩씬 처음 들어왔을때  사장님  그림   
      imgBossbkgnd = new ImageIcon("images/angryMan.png");
      bkgndImgResult = new JLabel(imgBossbkgnd);
      bkgndImgResult.setBounds(50, 180, 257, 288);
      add(bkgndImgResult);
      
      //엔딩씬 들어오면 보이는 결과 라벨  말풍선 출력
      txtTemp=new JPanel();
      txtTemp.setBackground(new Color(254, 184, 83)); // 밑의 아이콘 색과 통일
      txtTemp.setBounds(270, 100, 500, 180);
      txtTemp.setBorder(new LineBorder(new Color(255, 212, 84),10,true)); // 밑의 아이콘 테두리와 통일
      fnt=new Font("맑은 고딕", Font.BOLD, 40);
      txtResult= String.valueOf(Money); // testing\
      
      // gamescene에서 getText로 결과값(txtResult)을 가져오는 메소드 추가
      lblResult = new JLabel("$"+txtResult);
      lblResult.setBorder(BorderFactory.createLineBorder(Color.red, 3));
      lblResultNextLine=new JLabel(" 을 벌었지만 넌 해고야!!!");
      //lblResult.setHorizontalAlignment(SwingConstants.CENTER);
      lblResult.setFont(fnt);
      lblResult.setForeground(new Color(105, 44, 8)); // 밑의 아이콘 글씨 색과 통일
      lblResultNextLine.setFont(fnt);
      lblResultNextLine.setForeground(new Color(105, 44, 8)); // 밑의 아이콘 글씨 색과 통일
      txtTemp.add(lblResult);
      txtTemp.add(lblResultNextLine);
      add(txtTemp);

      //아래쪽 패널
      
      //Menu씬으로 이동하는 버튼
      imgMenu = new ImageIcon("images/continueHover.png");
      btnMenu = new JButton(imgMenu);
      btnMenu.setHorizontalAlignment(SwingConstants.CENTER);
      btnMenu.setBounds(90, 470, 235, 75);
      btnMenu.setBorderPainted(false);
      btnMenu.setContentAreaFilled(false);
      btnMenu.setFocusPainted(false);
      btnMenu.addActionListener(gameL);               //버튼 클릭시 메뉴씬으로 이동
      btnMenu.addMouseListener(gameML);               //버튼 색 변경
      add(btnMenu);
      imgStartClicked=new ImageIcon("images/continue.png");

      
      //Game씬으로 가능 버튼
      imgStart = new ImageIcon("images/exitHover.png");
      btnStart = new JButton(imgStart);
      btnStart.setHorizontalAlignment(SwingConstants.CENTER);
      btnStart.setBounds(495, 470, 235, 75);
      btnStart.setBorderPainted(false);
      btnStart.setContentAreaFilled(false);
      btnStart.setFocusPainted(false);
      btnStart.addActionListener(gameL);               //버튼 클릭시 게임씬으로 이동
      btnStart.addMouseListener(gameML);               //버튼 색 변경
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
         //Menu버튼 누르면 Game씬으로 이동
         if (btnMenu == obj) {ScenePanel.getInstance().selectScene("Game");} 
         //Start버튼 누르면 Menu씬으로 이동
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
         //버튼 눌리면 색 어둡게함
         if (btnMenu == obj) { btnMenu.setIcon(imgStartClicked); }
         else if (btnStart == obj) { btnStart.setIcon(imgMenuClicked); }
      }
      public void mouseReleased(MouseEvent event) {
         //버튼을 눌렀다가 뗴면 모든 버튼색이 다 돌아옴
         btnMenu.setIcon(imgMenu);
         btnStart.setIcon(imgStart);
      }
      public void mouseEntered(MouseEvent event) {}
      public void mouseExited(MouseEvent event) {}
   }
}