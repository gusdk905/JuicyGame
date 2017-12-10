import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Juicy extends JLabel
{	
	private int nFlag;				//쥬스를 식별할 수 있는 값
	private int nPrice;				//쥬스의 가격
	private EMListener EML;			//마우스 리스너
	private GameScene gameScene;	//Game Scene을 인스턴스로 갖고있고 인스턴스를 이용해 함수를 호출한다.
	private Point nListPos;			//GameScene에서의 쥬스 리스트에서의 좌표
	private boolean IsSelect;		//쥬스가 선택되었는지 여부를 저장
	private int nLRpos;				//쥬스가 오른쪽인지 왼쪽인지를 저장
	EtchedBorder eborder;

	public Juicy(int flag){
		nFlag = flag;
		nListPos = new Point(0, 0);
		nPrice = 1;
		IsSelect = false;
		nLRpos = 0;
		EML = new EMListener();
		eborder=new EtchedBorder(EtchedBorder.RAISED);


		
		addMouseListener(EML);
		ImageIcon icon = new ImageIcon("images/"+nFlag+".png");
		setIcon(icon);
	}
	
	public EMListener getListener() {
		return EML;
	}
	
	public void setFlag(int flag){
		nFlag = flag;
	}
	public void setPrice(int price) {
		nPrice = price;
	}
	public void setLRpos(int pos) {
		nLRpos = pos;
	}
	
	public void setListPos(int x, int y){
		nListPos.x = x;
		nListPos.y = y;	
	}
	public void setListPosX(int x){
		nListPos.x = x;
	}
	
	public void setListPosY(int y){
		nListPos.y = y;
	}
	public void setIsSelect(boolean select) {
		IsSelect = select;
	}
	public int getFlag(){
		return nFlag;	
	}
	
	public int getPrice() {
		return nPrice;
	}
	public int getLRpos() {
		return nLRpos;
	}
	public boolean getIsSelect() {
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
			//선택신에서 쥬스가 선택되어있는지를 검사함
			if(gameScene.getJSelect() == false)
			{
				IsSelect = !IsSelect; //'이 쥬스'가 선택되었는지의 여부를 바꾼다.
				if(IsSelect) {
					setBorder(eborder);
					System.out.println("Select");
					gameScene.setJSelect(true);
					gameScene.setJChoice(getFlag());
				}
			}
			//선택신에서 쥬스가 선택되어있다!!
			else {
				//선택신에서 쥬스가 선택되어있고 이 쥬스가 선택되어있는지 아닌지를 판별
				if(IsSelect) {
					setBorder(null);
					System.out.println("Not Select");
					gameScene.setJSelect(false);
					IsSelect = !IsSelect;
				}
			}
		}
		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {
			System.out.println(nFlag);
			System.out.println(nPrice);
			System.out.println(nLRpos);
		}
		public void mouseExited(MouseEvent event) {}
	}
}