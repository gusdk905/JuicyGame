import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Customer extends JLabel
{
	private int nFlag;				//�մ��� �ĺ��� �� �ִ� ��
	private int nWant;				//�մ��� ���ϴ� �꽺�� ��ȣ
	private EMListener EML;			//���콺 ������
	private GameScene gameScene;	//Game Scene�� �ν��Ͻ��� �����ְ� �ν��Ͻ��� �̿��� �Լ��� ȣ���Ѵ�.
	private int nPos;				//GameScene������ �մ��� ��ġ
	private boolean IsSelect;		//���õ� �մ������� ����
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
			if(gameScene.getJSelect() == true)			//���ӽ� ���� �꽺�� ���õǾ��ִ� ��Ȳ�̸�
			{
				IsSelect = true;						//�մ��� ���¸� ���õ����� ����
				if(gameScene.getJChoice() == nWant) {	//���ϰ� �ִ� �꽺�� flag ��ȣ�� �Ȱ��� �ϴ� ���õ� �꽺�� ���ٸ�
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
