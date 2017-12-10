import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameScene extends JPanel implements Runnable{
		//스크린 크기를 저장
	   private final int WIDTH = 810, HEIGHT = 600;
	   //과일 이미지 크기를 저장 
	   private final int FR_W = 100, FR_H = 100;
	   //쥬스 이미지 크기를 저장
	   private final int FRJ_W = 100, FRJ_H = 200;
	   //손님 이미지 크기를 저장
	   private final int CU_W = 270, CU_H = 240;
	   //과일 최대 개수를 저장 
	   private final int FR_NUMBER = 5;
	   //배치 할수 있는 최대 쥬스의 개수를 저장
	   private final int FRJ_NUMBER = 2;
	   //과일로 조합 가능한 쥬스 갯수를 저장 
	   private final int FR_MIX_NUMBER = 20;
	   //라이프(실패가능횟수)를 저장 
	   private final int LIFE_MAX = 3;
	   //손님의 최대수를 저장
	   private final int CUSTOMER_MAX = 3;
	   //모든 과일의 조합 리스트를 저장한다. 행은 각 과일이나 쥬스를 의미하며 열은 조합리스트를 의미한다.
	   private int [][] FR_LIST;
	   //모든 과일과 쥬스의 이름들을 저장한다.
	   private String [] FR_NAME;
	   //모든 쥬스의 가격을 저장한다.
	   private int []JU_PRICE;
	   
	   
	   //상단바
	   private JPanel UIPanel;
	   
	   /* nMoney		: 총 수입
	    * nLifeCnt		: 남아있는 생명의수
	    * nCustomCnt	: 현재의 손님 수
	    * nFrNum		: 과일 개수를 저장
	    * nFrListHeight	: 과일 위치를 저장
	    * nFrJNum		: 현재 쥬스의 개수를 저장
	    * nComZorder	: Component들의 z-order를 지정하는데 사용
	    * nLSlotFlag 	: 블랜더의 왼쪽 슬롯에 저장된 과일 flag를 저장
        * nRSlotFlag 	: 블랜더의 오른쪽 슬롯에 저장된 과일 flag를 저장
        * nJChoiceFlag	: 선택된 쥬스의 flag값을 저장
        * IsLSlot		: 블랜더의 왼쪽 슬롯의 배치 여부를 저장
        * IsRSlot		: 블랜더의 오른쪽 슬롯의 배치여부를 저장 
        * IsJChoice		: 쥬스를 선택한 상태인지를 저장
        * IsLJuice		: 왼쪽 편에 쥬스가 저장되어있는지 여부
        * IsRJuice		: 오른쪽 편에 쥬스가 저장되어있는지 여부
        * IsLCutPos		: 왼쪽 편에 손님이 있는지의 여부
        * IsMCutPos		: 가운데에 손님이 있는지의 여부
        * IsRCutPos		: 오른편에 손님이 있는지의 여부
	    */
	   
	   private int nMoney,nLifeCnt,nCustomCnt,nJChoiceFlag;
	   private int nFrNum,nFrJNum,nFrListHeight,nComZorder,nLSlotFlag, nRslotFlag;
	   
	   private boolean IsLSlot, IsRSlot,IsJChoice,IsLJuice,IsRJuice,IsLCutPos,IsMCutPos,IsRCutPos;
	   
	   
	   /*
	    * bkgndimg	: 배경이미지를 저장한 라벨
	    * totalScore: 총 수입을 보여줄 라벨
	    * lifeText	: Life : <- 텍스트를 보여줄 라벨
	    * lifeCount : 현재 라이프 개수를 보여줄 라벨
	    * btnMenu	: 메뉴 버튼을 보여줄 라벨
	    * btnShake	: 쉐이크 버튼을 보여줄 라벨
	    * btnTrash	: 쓰레기통 버튼을 보여줄 라벨
	    * blenderimg: 블랜더 이미지를 저장한 라벨
	    */
	   
	   private JLabel bkgndimg,blenderimg,totalScore, lifeText, lifeCount,totalText;
	   private JLabel LSlot, RSlot, resultText;
	   private JButton btnMenu, btnShake, btnTrash;
	   
	   
	   private ArrayList<Fruit> fruit_ArrList;
	   private ArrayList<Juicy> juice_ArrList;
	   private ArrayList<Customer> customer_ArrList;
	   
	   /*
	    * fileIO 		: 파일의 데이터를 버퍼에 읽어오는데 사용되는 객체
	    */
	   BufferedReader   fileIO;
	   
	   
	   //액션 리스너
	   private GameListener gameL;
	   //마우스 리스너
	   private GameMListener gameML;
	   //손님 스레드
	   private Thread myThread;								//손님이 랜덤한 시간에 나타나는 스레드!

	   
	   public GameScene() {
		    Font uiFont = new Font("Arial",Font.BOLD,15);
			setBounds(0, 0, WIDTH, HEIGHT);
			setBackground(Color.white);
			setLayout(null);
			
		    FR_LIST = new int[FR_MIX_NUMBER][3];
		    FR_NAME = new String[FR_MIX_NUMBER];
		    JU_PRICE = new int[FR_MIX_NUMBER];
		    
		    nFrNum=0;
		    nFrJNum =0;
		    nComZorder=0;
		    nFrListHeight=0;
		    IsLSlot = IsRSlot = IsJChoice = false;
		    IsLJuice = IsRJuice = false;
		    IsLCutPos = IsMCutPos = IsRCutPos = false;
		    nLSlotFlag = nRslotFlag = -1;
		    nCustomCnt = 0;
		    nJChoiceFlag = 0;
			fruit_ArrList = new ArrayList<Fruit>();
			juice_ArrList = new ArrayList<Juicy>();
			customer_ArrList = new ArrayList<Customer>();
			
		    ImageIcon Imgbkgnd = new ImageIcon("images/GameBackGround.png");
		    bkgndimg = new JLabel("", Imgbkgnd, SwingConstants.CENTER);
		    bkgndimg.setBounds(0,40,WIDTH,HEIGHT-40);
		    add(bkgndimg);
			

		    
			gameL = new GameListener();
			gameML = new GameMListener();
			
			myThread = null;									//Tread의 초기값은 null이 일반적

			
			UIPanel = new JPanel();
			UIPanel.setBounds(0, 0, 810,40);
			UIPanel.setBackground(Color.orange);
			UIPanel.setLayout(null);
			add(UIPanel);
	
			lifeText = new JLabel("Life : ");
			lifeText.setBounds(20,10,50,20);
			lifeText.setFont(uiFont);
			lifeText.setVisible(true);
			UIPanel.add(lifeText);
    
			lifeCount = new JLabel(String.valueOf(nLifeCnt));
			lifeCount.setBounds(70,10,60,20);
			lifeCount.setFont(uiFont);
			lifeCount.setVisible(true);
			UIPanel.add(lifeCount);
  
			totalText = new JLabel("Money: ");
			totalText.setBounds(300,10,100,20);
			totalText.setFont(uiFont);
			totalText.setVisible(true);
			UIPanel.add(totalText);
  
			totalScore = new JLabel(String.valueOf(nMoney));
			totalScore.setBounds(400,10,100,20);
			totalScore.setFont(uiFont);
			totalScore.setVisible(true);
			UIPanel.add(totalScore);
  
			btnMenu = new JButton("Home");
			btnMenu.setFont(uiFont);
			btnMenu.setBounds(600,2,200,35);
			btnMenu.setBackground(Color.yellow);
			btnMenu.setForeground(Color.black);
			btnMenu.addActionListener(gameL);
			btnMenu.addMouseListener(gameML);
  			UIPanel.add(btnMenu);
			
		    
		    btnShake = new JButton(new ImageIcon("images/Shake.png"));
		    btnShake.setBounds(340,520,120,80);
		    //btnShake.setFont(uiFont);
		    btnShake.setBorderPainted(false);
		    btnShake.setContentAreaFilled(false);
			btnShake.setFocusPainted(false);
		    btnShake.addActionListener(gameL);
		    btnShake.addMouseListener(gameML);
		    btnShake.setEnabled(true);
		    add(btnShake);
		    nComZorder++;
		    setComponentZOrder(btnShake,0);
		    
		    btnTrash = new JButton(new ImageIcon("images/trashcan.png"));
		    btnTrash.setBounds(730,500,64,64);
		    //btnShake.setFont(uiFont);
		    btnTrash.setBorderPainted(false);
		    btnTrash.setContentAreaFilled(false);
		    btnTrash.setFocusPainted(false);
		    btnTrash.addActionListener(gameL);
		    btnTrash.addMouseListener(gameML);
		    btnTrash.setEnabled(true);
		    add(btnTrash);
		    nComZorder++;
		    setComponentZOrder(btnTrash,0);
		    
		    ImageIcon blender = new ImageIcon("images/b8.png");
		    blenderimg = new JLabel("", blender, SwingConstants.CENTER);
		    blenderimg.setBounds(330,420,113,113);
		    add(blenderimg);
		    nComZorder++;
		    setComponentZOrder(blenderimg,0);
		    setComponentZOrder(bkgndimg,nComZorder);
		    
	   }
	public boolean getJSelect(){return IsJChoice;}
	public int getJChoice() {return nJChoiceFlag;}
	
	public void setJSelect(boolean sell) {IsJChoice = sell;}
	public void setJChoice(int flag) {nJChoiceFlag = flag;}
	
	public int getLifeCnt() {return nLifeCnt;}
	public void setLifeCnt(int life) {nLifeCnt = life;}
	
	public int getScore() {return nMoney;}
	public void Init(){ //게임 시작시 초기화 메소드
		for(Fruit fruit:fruit_ArrList) {
			remove(fruit);
		}
		for(Juicy juice:juice_ArrList) {
			remove(juice);
		}
		for(Customer customer: customer_ArrList) {
			remove(customer);
		}
		fruit_ArrList.clear();
		juice_ArrList.clear();
		customer_ArrList.clear();
		
	    //nScore = 0;
	    nLifeCnt = LIFE_MAX;
		lifeCount.setText(String.valueOf(nLifeCnt));
	    nFrNum = 0;
	    nFrListHeight = 0;
	    nMoney =0;
		totalScore.setText(String.valueOf(nMoney));
	    IsLSlot = IsRSlot = false;
	    nLSlotFlag = nRslotFlag = -1;
	    IsLCutPos = IsMCutPos = IsRCutPos = false;
	    IsLJuice = IsRJuice = false;
	    nCustomCnt = 0;
	    nJChoiceFlag = 0;
	    
	    AddFruit(0);
	    AddFruit(1);
	    AddFruit(2);
	    AddFruit(3);
	    AddFruit(4);
	    start();
	}
	public void FileInputOutput(String list, String description, String price) throws IOException {
		      fileIO = new BufferedReader(new FileReader(list));
		      int y = 0;
		      
		      /*
		       * 파일 읽는 절차
		       * 1.속성(PIVOT, ADD, RESULT)을 읽는다.
		       * 2.속성에 따라 저장되는 배열 위치를 지정하고 읽어들인 값을 대입한다.
		       */
		      
		      while(true){
		         String line = fileIO.readLine();
		            if (line==null) break;
		            
		            switch(line){
		               case "PIVOT":
		               line = fileIO.readLine();
		               FR_LIST[y][0] = Integer.parseInt(line);
		               break;
		               
		               case "ADD":
		               line = fileIO.readLine();
		               FR_LIST[y][1] = Integer.parseInt(line);
		               break;
		               
		               case "RESULT":
		               line = fileIO.readLine();
		               FR_LIST[y][2] = Integer.parseInt(line);
		               y++;
		               break;
		               
		               default:
		               break;
		            }
		      }
		      fileIO.close();
		      
		      y=0;
		      
		      fileIO = new BufferedReader(new FileReader(description));
		      
		      //과일과 쥬스이름들을 순차적으로 넣는다. 파일에는 순차적으로 과일과 과일로만든 쥬스가 저장되어있다.
		      while(true){
		         String line = fileIO.readLine();
		            if (line==null) break;
		            FR_NAME[y] = line;
		            y++;
		      }
		      
		      y=0;
		      
		      fileIO = new BufferedReader(new FileReader(price));
		      
		      //쥬스의 가격을 순차적으로 넣는다. 파일에는 순차적으로 쥬스의 가격이 저장되어있다.
		      while(true) {
		    	  String line = fileIO.readLine();
		    	  if(line==null)break;
		    	  JU_PRICE[y] =  Integer.parseInt(line);
		    	  y++;
		      }
		      
		      fileIO.close();
	}
	   
   //과일을 추가하는 메소드
   public void AddFruit(int flag){
	   // 좌측 리스트에 들어갈 과일의 X, Y값을 저장한다.
      int nFrLyX = (FR_W) * (nFrNum % 3) + 5 * (nFrNum % 3 + 1);
      int nFrLyY = 350 + 10 * (nFrNum / 3 + 1) + ((nFrNum / 3) * FR_H);
      
      //과일을 추가한다.
      Fruit fruit = new Fruit(flag);
      fruit.setBounds(nFrLyX, nFrLyY,FR_W,FR_H);
      fruit.setListPos(nFrLyX, nFrLyY);
      fruit.setgameScene(this);
      add(fruit);

      //획득한 과일 배열에 과일 인덱스를 저장하고, 과일 개수를 증가시킨다.
      fruit_ArrList.add(fruit);
      nFrNum++;
       
      //Slot과 배경이미지는 과일보다 항상 아래에 있어야하므로 다시 z-order를 설정해준다.
      //setComponentZOrder(LSlot, nComZorder+nFrNum+1);
      setComponentZOrder(fruit, 0);
   }
   public void AddJuice(int flag) {
       if( IsLJuice && IsRJuice )	//쥬스가 두개의 공간에 차있으면 종료한다.
           return;
	   int nJuLyX = 490;
	   int nJuLyY = 330;
	   
	   Juicy juice = new Juicy(flag);
       if(!IsLJuice){
    	   IsLJuice = true;
    	   juice.setLRpos(-1);
    	   juice.setBounds(nJuLyX, nJuLyY, FRJ_W, FRJ_H);
        }
       else if(!IsRJuice){
        	IsRJuice = true;
     	   	juice.setLRpos(1);
	        juice.setBounds(nJuLyX+FRJ_W, nJuLyY, FRJ_W, FRJ_H);
	   }
	   juice.setListPos(nJuLyX, nJuLyY);
	   juice.setgameScene(this);
	   juice.setPrice(JU_PRICE[flag]);
	   add(juice);
	   juice_ArrList.add(juice);
	   nFrJNum++;
       setComponentZOrder(juice,0);
	   this.repaint();
   }
   public void AddCustomer(int flag, int fruit1,int fruit2,int juice) {
       if( IsLCutPos && IsMCutPos && IsRCutPos )	//손님이 세개의 공간에 차있으면 생성하지 않는다.
           return;
	   int nCustomerX = 10;
	   int nCustomerY = 75;
	   Customer customer = new Customer(flag,fruit1,fruit2,juice);
       if(!IsLCutPos){
    	   IsLCutPos = true;
    	   customer.setPos(-1);
    	   customer.setBounds(nCustomerX, nCustomerY, CU_W, CU_H);
        }
       else if(!IsMCutPos){
    	    IsMCutPos = true;
    	    customer.setPos(0);
    	    customer.setBounds(nCustomerX+CU_W,nCustomerY, CU_W, CU_H);
	   }
       else if(!IsRCutPos){
   	    IsRCutPos = true;
   	    customer.setPos(1);
   	    customer.setBounds(nCustomerX+CU_W*2,nCustomerY, CU_W, CU_H);
	   }
	   //customer.setBounds(nCustomerX,nCustomerY,CU_W,CU_H);
	   customer.setgameScene(this);
	   add(customer);
	   nCustomCnt++;
	   customer_ArrList.add(customer);
       setComponentZOrder(customer,0);
       this.repaint();
   }
   //flag에 해당하는 슬롯을 Slot에 저장한다.
   public void setSlot(int flag){
      for(Fruit spot : fruit_ArrList){
    	 //flag에 해당하는 fruit를 찾음
         if( spot.getFlag() == flag ){
        	 //둘다 차있으면 메소드를 종료한다.
            if( IsLSlot && IsRSlot )
               return;
            
            //비어있는 곳을 찾아 넣는다.
            if(!IsLSlot){
               IsLSlot = true;
               
               nLSlotFlag = flag;
               spot.setSlotPos(0);
               spot.setBounds(300, 325, FR_W, FR_H);
            }else if(!IsRSlot){
               IsRSlot = true;
               nRslotFlag = flag;
               spot.setIsSlot(true);
               spot.setSlotPos(1);
               spot.setBounds(375, 325, FR_W, FR_H);
            }
            spot.setIsSlot(true);
            //resultText.setVisible(false);
         }   
      }
   }
   
   
   //flag에 해당하는 슬롯을 다시 리스트로 복귀시킨다.
   public void dropSlot(int flag){
      for(Fruit spot : fruit_ArrList){
         if( spot.getFlag() == flag ){
        	 //과일이 어느 슬롯인지 찾는다.
            if( spot.getSlotPos() == 0 ){
               IsLSlot = false;
               nLSlotFlag = -1;
            }
            if( spot.getSlotPos() == 1 ){
               IsRSlot = false;
               nRslotFlag = -1;
            }
            
            spot.setIsSlot(false);
            spot.setSlotPos(-1);

            //과일을 리스트에 다시 복귀시킨다.
            spot.setBounds(spot.getListPosX(),spot.getListPosY(),FR_W,FR_H);
            return;
         }
      }
   }
   
   public void Shake(int Index){
	  //Slot을 비어있는 상태로 만든다.
      dropSlot(nLSlotFlag);
      dropSlot(nRslotFlag);
      //조합에 따른 쥬스를 추가한다.
      AddJuice(FR_LIST[Index][2]);
   }
   public void Shaking(){
	  //둘 중에 하나라도 비어있으면 종료한다.
      if( nLSlotFlag == -1 || nRslotFlag == -1 ){
         return;
      }
      //슬롯에 있는 과일과 조합리스트를 비교하여 결과를 도출한다.
      for(int y=0; y<FR_MIX_NUMBER; y++){
         if( (nLSlotFlag == FR_LIST[y][0]
            &&
            nRslotFlag == FR_LIST[y][1])
            ||
            (nRslotFlag == FR_LIST[y][0]
            &&
            nLSlotFlag == FR_LIST[y][1])
         ){ 
            Shake(y);
            return;
         }
      }
   }
   
   
   public void DeleteAllJuice() {					//모든 쥬스를 삭제한다.
	   for(Juicy spot : juice_ArrList){				
		   remove(spot);
	   }
	   nFrJNum=0;
	   juice_ArrList.clear();
	   IsLJuice = IsRJuice = false;
	   IsJChoice = false;
	   this.repaint();
   }
   public void SellJuice() {					//쥬스를 파는 경우의 함수
	   for(Juicy spot : juice_ArrList){
		   if(spot.getIsSelect())				//선택된 쥬스를 찾는다.
		   {
			remove(spot);
			spot.setIsSelect(false);			//선택된 쥬스의 선택된 상태를 해제함
			nMoney +=spot.getPrice();
			totalScore.setText(String.valueOf(nMoney));
			   if(spot.getLRpos() == -1)	//왼쪽의 쥬스라면?
			   {
				IsLJuice = false;
				IsJChoice = false;
			   }
			   else if(spot.getLRpos() == 1)		//오른쪽의 쥬스라면?
			   {
				IsRJuice = false;
				IsJChoice = false;
//				System.out.println(IsLJuice);
			   }
		   }
	   }
	   for(Customer customer : customer_ArrList) {			//쥬스가 성공적으로 팔릴 경우의 손님을 삭제시키기 위해
		   if(customer.getSelect()){
			   remove(customer);
			   customer.setSelect(false);
			   if(customer.getPos() ==-1) {					//선택된 손님의 위치를 확인 후 그 곳의 위치를 다시 사용될 수 있게 변경한다.
				   IsLCutPos = false;
			   }
			   else if(customer.getPos() ==0) {
				   IsMCutPos = false;
			   }
			   else if(customer.getPos() ==1) {
				   IsRCutPos = false;
			   }
		   }
	   }
	   this.repaint();
   }
   public void failSell() {								//판매를 실패했을 경우
	   for(Juicy spot : juice_ArrList){
		   if(spot.getIsSelect())				//선택된 쥬스를 찾는다.
		   {
			remove(spot);
			spot.setIsSelect(false);			//선택된 쥬스의 선택된 상태를 해제함
			   if(spot.getLRpos() == -1)	//왼쪽의 쥬스라면?
			   {
				IsLJuice = false;
				IsJChoice = false;
			   }
			   else if(spot.getLRpos() == 1)		//오른쪽의 쥬스라면?
			   {
				IsRJuice = false;
				IsJChoice = false;
//				System.out.println(IsLJuice);
			   }
		   }
	   }
	   for(Customer customer : customer_ArrList) {			//쥬스가 성공적으로 팔릴 경우의 손님을 삭제시키기 위해
		   if(customer.getSelect()){
			   remove(customer);
			   customer.setSelect(false);
			   if(customer.getPos() ==-1) {					//선택된 손님의 위치를 확인 후 그 곳의 위치를 다시 사용될 수 있게 변경한다.
				   IsLCutPos = false;
			   }
			   else if(customer.getPos() ==0) {
				   IsMCutPos = false;
			   }
			   else if(customer.getPos() ==1) {
				   IsRCutPos = false;
			   }
		   }
	   }
	   nLifeCnt--;
	   lifeCount.setText(String.valueOf(nLifeCnt));
	   this.repaint();
	   if(nLifeCnt ==0) {
		   ScenePanel.getInstance().getEndingScene().setMoney(nMoney);		//엔딩신의 돈을 설정해줌
		   ScenePanel.getInstance().selectScene("Ending");
		   stop();  
	   }
	   
   }
   
	private class GameListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();

				//Menu버튼 누르면 Game씬으로 이동
				if (btnMenu == obj) {
					ScenePanel.getInstance().selectScene("Menu");
					stop();
				} 
			}
		}

	private class GameMListener implements MouseListener {
			public void mouseClicked(MouseEvent event) {
				Object obj = event.getSource();				
			}

			public void mousePressed(MouseEvent event) {
				Object obj = event.getSource();
		         if( obj == btnMenu ){
		             ScenePanel.getInstance().selectScene("Menu");
		             stop();
		          //쉐이크 버튼 눌렀을 때 조합을 시도
		          }else if( obj == btnShake ){
		             Shaking();
		             }
		          else if (obj == btnTrash)
		          {
		        	  DeleteAllJuice();
		  			  System.out.println("TRASH");
		          }
			}

			public void mouseReleased(MouseEvent event) {

			}

			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		}
	
	public void start() {
		if (myThread == null) {	myThread = new Thread(this);}		//쓰레드가 null일때만 시작
		 myThread.start();//run() 부름
	}
	public void stop() {
		if(myThread != null) {
			myThread = null;
		}
	}
	
	public void run() { // thread inhavior
			try {
				while(true) {
				int rndwaitTime = (int)(Math.random()*10000)+1000;	//손님이 랜덤한 시간을 설정
				int rndflag = (int)(Math.random()*10);				//손님의 랜덤한 이미지를 결정
				int rndfruit = (int)(Math.random()*10);
				AddCustomer(rndflag,FR_LIST[rndfruit][0],FR_LIST[rndfruit][1],FR_LIST[rndfruit][2]);
				Thread.sleep(rndwaitTime);
				}
			} catch (Exception e) {
				System.out.println("Thread error");
		}
			System.out.println("Thread");
	}
	
   public void paintComponent(Graphics page){
	      super.paintComponent(page);
	   }
}
