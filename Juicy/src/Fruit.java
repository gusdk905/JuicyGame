import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Fruit extends JLabel
{	
	private int nFlag;				//������ �ĺ��� �� �ִ� ��
	private EMListener EML;			//���콺 ������
	private GameScene gameScene;	//Game Scene�� �ν��Ͻ��� �����ְ� �ν��Ͻ��� �̿��� �Լ��� ȣ���Ѵ�.
	private boolean IsSlot;			//������ ��ġ���θ� ����
	private int nSlotPos;			//������ ��ġ�� ���� (-1:���Կ� ��ġ�� ���� ����,0:���� ����, 1:�����ʽ���)
	private Point nListPos;			//GameScene������ ���� ����Ʈ������ ��ǥ

	public Fruit(int flag){
		nFlag = flag;
		nSlotPos = -1;
		IsSlot = false;
		nListPos = new Point(0, 0);
		EML = new EMListener();
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
	public void setIsSlot(boolean IsSet){
		IsSlot = IsSet;
	}
	public void setSlotPos(int nPos){
		nSlotPos = nPos;	
	}

	
	public int getFlag(){
		return nFlag;	
	}

	public boolean getIsSlot(){
		return IsSlot;	
	}
	public int getSlotPos(){
		return nSlotPos;	
	}
	
	public void setgameScene(GameScene scene){
		gameScene = scene;
	}
	
	public GameScene getgameScene() {
		return gameScene;
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
	
	public Point getListPos() {
		return nListPos;
	}
	
	public int getListPosX() {
		return nListPos.x;	
	}
	
	public int getListPosY() {
		return nListPos.y;
	}
	
	
	private class EMListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {	//���콺 �Է��� ������ ������ ��� ���Կ� ��ġ���ִ����� �Ǻ��� ������ ��ġ��Ų��.
			IsSlot = !IsSlot;
			
			if( IsSlot ) {
				gameScene.setSlot(nFlag);
			}
			if( !IsSlot ) {
				gameScene.dropSlot(nFlag);
			}
		}
		public void mouseReleased(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {
			System.out.println(nFlag);
		}
		public void mouseExited(MouseEvent event) {}
	}
}