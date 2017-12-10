import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Juicy extends JLabel
{	
	private int nFlag;				//�꽺�� �ĺ��� �� �ִ� ��
	private int nPrice;				//�꽺�� ����
	private EMListener EML;			//���콺 ������
	private GameScene gameScene;	//Game Scene�� �ν��Ͻ��� �����ְ� �ν��Ͻ��� �̿��� �Լ��� ȣ���Ѵ�.
	private Point nListPos;			//GameScene������ �꽺 ����Ʈ������ ��ǥ
	private boolean IsSelect;		//�꽺�� ���õǾ����� ���θ� ����
	private int nLRpos;				//�꽺�� ���������� ���������� ����
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
			//���ýſ��� �꽺�� ���õǾ��ִ����� �˻���
			if(gameScene.getJSelect() == false)
			{
				IsSelect = !IsSelect; //'�� �꽺'�� ���õǾ������� ���θ� �ٲ۴�.
				if(IsSelect) {
					setBorder(eborder);
					System.out.println("Select");
					gameScene.setJSelect(true);
					gameScene.setJChoice(getFlag());
				}
			}
			//���ýſ��� �꽺�� ���õǾ��ִ�!!
			else {
				//���ýſ��� �꽺�� ���õǾ��ְ� �� �꽺�� ���õǾ��ִ��� �ƴ����� �Ǻ�
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