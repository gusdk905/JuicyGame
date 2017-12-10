import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Customer extends JLabel
{
	private int nFlag;				//손님을 식별할 수 있는 값
	private int nWant;				//손님이 원하는 쥬스의 번호
	private EMListener EML;			//마우스 리스너
	private GameScene gameScene;	//Game Scene을 인스턴스로 갖고있고 인스턴스를 이용해 함수를 호출한다.
	private int nPos;				//GameScene에서의 손님의 위치
	private boolean IsSelect;		//선택된 손님인지의 여부
	private Fruit fruit1,fruit2;
	public Customer(int flag, int nfruit1, int nfruit2, int WantJuice){
		nFlag = flag;
		nPos = -2;
		nWant = WantJuice;
		IsSelect =false;
		fruit1 = new Fruit(nfruit1);
		fruit1.setBounds(40,-10,100,100);
		fruit2 = new Fruit(nfruit2);
		fruit2.setBounds(140,-10,100,100);
		EML = new EMListener();
		addMouseListener(EML);
		
		ImageIcon icon = new ImageIcon("images/Customer/"+nFlag+".png");
		setIcon(icon);
		add(fruit1);
		add(fruit2);
	}
	
	public EMListener getListener() {
		return EML;
	}
	
	public void setFlag(int flag){
		nFlag = flag;
	}
	public void setPrice(int want) {
		nWant = want;
	}
	
	public void setPos(int pos){
		nPos = pos;
	}

	public void setSelect(boolean select) {
		IsSelect = select;
	}
	public int getFlag(){
		return nFlag;	
	}
	
	public int getWant() {
		return nWant;
	}
	public int getPos() {
		return nPos;
	}
	public boolean getSelect() {
		return IsSelect;
	}
	public void setgameScene(GameScene scene){
		gameScene = scene;
	}
	
	public GameScene getgameScene() {
		return gameScene;
	}

	private class EMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {
			if(gameScene.getJSelect() == true)			//게임신 내에 쥬스가 선택되어있는 상황이면
			{
				IsSelect = true;						//손님의 상태를 선택됨으로 변경
				if(gameScene.getJChoice() == nWant) {	//원하고 있는 쥬스의 flag 번호와 팔고자 하는 선택된 쥬스가 같다면
					System.out.println("SEll");
					gameScene.setJSelect(false);
					gameScene.SellJuice();
				}
				else {
					System.out.println("FAILSELL");

					gameScene.setJSelect(false);
					gameScene.failSell();
					IsSelect = false;
				}
			}
			
		}
		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {
			System.out.println(nFlag);
			System.out.println(nWant);
		}
		public void mouseExited(MouseEvent event) {}
	}

}
