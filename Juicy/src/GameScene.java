import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameScene extends JPanel implements Runnable{
		//��ũ�� ũ�⸦ ����
	   private final int WIDTH = 810, HEIGHT = 600;
	   //���� �̹��� ũ�⸦ ���� 
	   private final int FR_W = 100, FR_H = 100;
	   //�꽺 �̹��� ũ�⸦ ����
	   private final int FRJ_W = 100, FRJ_H = 200;
	   //�մ� �̹��� ũ�⸦ ����
	   private final int CU_W = 270, CU_H = 240;
	   //���� �ִ� ������ ���� 
	   private final int FR_NUMBER = 5;
	   //��ġ �Ҽ� �ִ� �ִ� �꽺�� ������ ����
	   private final int FRJ_NUMBER = 2;
	   //���Ϸ� ���� ������ �꽺 ������ ���� 
	   private final int FR_MIX_NUMBER = 20;
	   //������(���а���Ƚ��)�� ���� 
	   private final int LIFE_MAX = 3;
	   //�մ��� �ִ���� ����
	   private final int CUSTOMER_MAX = 3;
	   //��� ������ ���� ����Ʈ�� �����Ѵ�. ���� �� �����̳� �꽺�� �ǹ��ϸ� ���� ���ո���Ʈ�� �ǹ��Ѵ�.
	   private int [][] FR_LIST;
	   //��� ���ϰ� �꽺�� �̸����� �����Ѵ�.
	   private String [] FR_NAME;
	   //��� �꽺�� ������ �����Ѵ�.
	   private int []JU_PRICE;
	   
	   
	   //��ܹ�
	   private JPanel UIPanel;
	   
	   /* nMoney		: �� ����
	    * nLifeCnt		: �����ִ� �����Ǽ�
	    * nCustomCnt	: ������ �մ� ��
	    * nFrNum		: ���� ������ ����
	    * nFrListHeight	: ���� ��ġ�� ����
	    * nFrJNum		: ���� �꽺�� ������ ����
	    * nComZorder	: Component���� z-order�� �����ϴµ� ���
	    * nLSlotFlag 	: ������ ���� ���Կ� ����� ���� flag�� ����
        * nRSlotFlag 	: ������ ������ ���Կ� ����� ���� flag�� ����
        * nJChoiceFlag	: ���õ� �꽺�� flag���� ����
        * IsLSlot		: ������ ���� ������ ��ġ ���θ� ����
        * IsRSlot		: ������ ������ ������ ��ġ���θ� ���� 
        * IsJChoice		: �꽺�� ������ ���������� ����
        * IsLJuice		: ���� �� �꽺�� ����Ǿ��ִ��� ����
        * IsRJuice		: ������ �� �꽺�� ����Ǿ��ִ��� ����
        * IsLCutPos		: ���� �� �մ��� �ִ����� ����
        * IsMCutPos		: ����� �մ��� �ִ����� ����
        * IsRCutPos		: ������ �մ��� �ִ����� ����
	    */
	   
	   private int nMoney,nLifeCnt,nCustomCnt,nJChoiceFlag;
	   private int nFrNum,nFrJNum,nFrListHeight,nComZorder,nLSlotFlag, nRslotFlag;
	   
	   private boolean IsLSlot, IsRSlot,IsJChoice,IsLJuice,IsRJuice,IsLCutPos,IsMCutPos,IsRCutPos;
	   
	   
	   /*
	    * bkgndimg	: ����̹����� ������ ��
	    * totalScore: �� ������ ������ ��
	    * lifeText	: Life : <- �ؽ�Ʈ�� ������ ��
	    * lifeCount : ���� ������ ������ ������ ��
	    * btnMenu	: �޴� ��ư�� ������ ��
	    * btnShake	: ����ũ ��ư�� ������ ��
	    * btnTrash	: �������� ��ư�� ������ ��
	    * blenderimg: ���� �̹����� ������ ��
	    */
	   
	   private JLabel bkgndimg,blenderimg,totalScore, lifeText, lifeCount,totalText;
	   private JLabel LSlot, RSlot, resultText;
	   private JButton btnMenu, btnShake, btnTrash;
	   
	   
	   private ArrayList<Fruit> fruit_ArrList;
	   private ArrayList<Juicy> juice_ArrList;
	   private ArrayList<Customer> customer_ArrList;
	   
	   /*
	    * fileIO 		: ������ �����͸� ���ۿ� �о���µ� ���Ǵ� ��ü
	    */
	   BufferedReader   fileIO;
	   
	   
	   //�׼� ������
	   private GameListener gameL;
	   //���콺 ������
	   private GameMListener gameML;
	   //�մ� ������
	   private Thread myThread;								//�մ��� ������ �ð��� ��Ÿ���� ������!

	   
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
			
			myThread = null;									//Tread�� �ʱⰪ�� null�� �Ϲ���

			
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
	public void Init(){ //���� ���۽� �ʱ�ȭ �޼ҵ�
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
		       * ���� �д� ����
		       * 1.�Ӽ�(PIVOT, ADD, RESULT)�� �д´�.
		       * 2.�Ӽ��� ���� ����Ǵ� �迭 ��ġ�� �����ϰ� �о���� ���� �����Ѵ�.
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
		      
		      //���ϰ� �꽺�̸����� ���������� �ִ´�. ���Ͽ��� ���������� ���ϰ� ���Ϸθ��� �꽺�� ����Ǿ��ִ�.
		      while(true){
		         String line = fileIO.readLine();
		            if (line==null) break;
		            FR_NAME[y] = line;
		            y++;
		      }
		      
		      y=0;
		      
		      fileIO = new BufferedReader(new FileReader(price));
		      
		      //�꽺�� ������ ���������� �ִ´�. ���Ͽ��� ���������� �꽺�� ������ ����Ǿ��ִ�.
		      while(true) {
		    	  String line = fileIO.readLine();
		    	  if(line==null)break;
		    	  JU_PRICE[y] =  Integer.parseInt(line);
		    	  y++;
		      }
		      
		      fileIO.close();
	}
	   
   //������ �߰��ϴ� �޼ҵ�
   public void AddFruit(int flag){
	   // ���� ����Ʈ�� �� ������ X, Y���� �����Ѵ�.
      int nFrLyX = (FR_W) * (nFrNum % 3) + 5 * (nFrNum % 3 + 1);
      int nFrLyY = 350 + 10 * (nFrNum / 3 + 1) + ((nFrNum / 3) * FR_H);
      
      //������ �߰��Ѵ�.
      Fruit fruit = new Fruit(flag);
      fruit.setBounds(nFrLyX, nFrLyY,FR_W,FR_H);
      fruit.setListPos(nFrLyX, nFrLyY);
      fruit.setgameScene(this);
      add(fruit);

      //ȹ���� ���� �迭�� ���� �ε����� �����ϰ�, ���� ������ ������Ų��.
      fruit_ArrList.add(fruit);
      nFrNum++;
       
      //Slot�� ����̹����� ���Ϻ��� �׻� �Ʒ��� �־���ϹǷ� �ٽ� z-order�� �������ش�.
      //setComponentZOrder(LSlot, nComZorder+nFrNum+1);
      setComponentZOrder(fruit, 0);
   }
   public void AddJuice(int flag) {
       if( IsLJuice && IsRJuice )	//�꽺�� �ΰ��� ������ �������� �����Ѵ�.
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
       if( IsLCutPos && IsMCutPos && IsRCutPos )	//�մ��� ������ ������ �������� �������� �ʴ´�.
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
   //flag�� �ش��ϴ� ������ Slot�� �����Ѵ�.
   public void setSlot(int flag){
      for(Fruit spot : fruit_ArrList){
    	 //flag�� �ش��ϴ� fruit�� ã��
         if( spot.getFlag() == flag ){
        	 //�Ѵ� �������� �޼ҵ带 �����Ѵ�.
            if( IsLSlot && IsRSlot )
               return;
            
            //����ִ� ���� ã�� �ִ´�.
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
   
   
   //flag�� �ش��ϴ� ������ �ٽ� ����Ʈ�� ���ͽ�Ų��.
   public void dropSlot(int flag){
      for(Fruit spot : fruit_ArrList){
         if( spot.getFlag() == flag ){
        	 //������ ��� �������� ã�´�.
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

            //������ ����Ʈ�� �ٽ� ���ͽ�Ų��.
            spot.setBounds(spot.getListPosX(),spot.getListPosY(),FR_W,FR_H);
            return;
         }
      }
   }
   
   public void Shake(int Index){
	  //Slot�� ����ִ� ���·� �����.
      dropSlot(nLSlotFlag);
      dropSlot(nRslotFlag);
      //���տ� ���� �꽺�� �߰��Ѵ�.
      AddJuice(FR_LIST[Index][2]);
   }
   public void Shaking(){
	  //�� �߿� �ϳ��� ��������� �����Ѵ�.
      if( nLSlotFlag == -1 || nRslotFlag == -1 ){
         return;
      }
      //���Կ� �ִ� ���ϰ� ���ո���Ʈ�� ���Ͽ� ����� �����Ѵ�.
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
   
   
   public void DeleteAllJuice() {					//��� �꽺�� �����Ѵ�.
	   for(Juicy spot : juice_ArrList){				
		   remove(spot);
	   }
	   nFrJNum=0;
	   juice_ArrList.clear();
	   IsLJuice = IsRJuice = false;
	   IsJChoice = false;
	   this.repaint();
   }
   public void SellJuice() {					//�꽺�� �Ĵ� ����� �Լ�
	   for(Juicy spot : juice_ArrList){
		   if(spot.getIsSelect())				//���õ� �꽺�� ã�´�.
		   {
			remove(spot);
			spot.setIsSelect(false);			//���õ� �꽺�� ���õ� ���¸� ������
			nMoney +=spot.getPrice();
			totalScore.setText(String.valueOf(nMoney));
			   if(spot.getLRpos() == -1)	//������ �꽺���?
			   {
				IsLJuice = false;
				IsJChoice = false;
			   }
			   else if(spot.getLRpos() == 1)		//�������� �꽺���?
			   {
				IsRJuice = false;
				IsJChoice = false;
//				System.out.println(IsLJuice);
			   }
		   }
	   }
	   for(Customer customer : customer_ArrList) {			//�꽺�� ���������� �ȸ� ����� �մ��� ������Ű�� ����
		   if(customer.getSelect()){
			   remove(customer);
			   customer.setSelect(false);
			   if(customer.getPos() ==-1) {					//���õ� �մ��� ��ġ�� Ȯ�� �� �� ���� ��ġ�� �ٽ� ���� �� �ְ� �����Ѵ�.
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
   public void failSell() {								//�ǸŸ� �������� ���
	   for(Juicy spot : juice_ArrList){
		   if(spot.getIsSelect())				//���õ� �꽺�� ã�´�.
		   {
			remove(spot);
			spot.setIsSelect(false);			//���õ� �꽺�� ���õ� ���¸� ������
			   if(spot.getLRpos() == -1)	//������ �꽺���?
			   {
				IsLJuice = false;
				IsJChoice = false;
			   }
			   else if(spot.getLRpos() == 1)		//�������� �꽺���?
			   {
				IsRJuice = false;
				IsJChoice = false;
//				System.out.println(IsLJuice);
			   }
		   }
	   }
	   for(Customer customer : customer_ArrList) {			//�꽺�� ���������� �ȸ� ����� �մ��� ������Ű�� ����
		   if(customer.getSelect()){
			   remove(customer);
			   customer.setSelect(false);
			   if(customer.getPos() ==-1) {					//���õ� �մ��� ��ġ�� Ȯ�� �� �� ���� ��ġ�� �ٽ� ���� �� �ְ� �����Ѵ�.
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
		   ScenePanel.getInstance().getEndingScene().setMoney(nMoney);		//�������� ���� ��������
		   ScenePanel.getInstance().selectScene("Ending");
		   stop();  
	   }
	   
   }
   
	private class GameListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();

				//Menu��ư ������ Game������ �̵�
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
		          //����ũ ��ư ������ �� ������ �õ�
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
		if (myThread == null) {	myThread = new Thread(this);}		//�����尡 null�϶��� ����
		 myThread.start();//run() �θ�
	}
	public void stop() {
		if(myThread != null) {
			myThread = null;
		}
	}
	
	public void run() { // thread inhavior
			try {
				while(true) {
				int rndwaitTime = (int)(Math.random()*10000)+1000;	//�մ��� ������ �ð��� ����
				int rndflag = (int)(Math.random()*10);				//�մ��� ������ �̹����� ����
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
