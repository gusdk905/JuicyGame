import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScenePanel extends JPanel {
	//��� ���� �ν��Ͻ� �����ͷ� �����Ͽ�����Ѵ�.
	private MenuScene2	menuPanel;
	private GameScene	gamePanel;
	private EndingScene2	endingPanel;
	private HowtoScene	HowtoPanel;
	
	public ScenePanel() {
		setPreferredSize(new Dimension(810,600));
		setBackground(Color.white);
		setLayout(null);
		
		menuPanel = new MenuScene2();
		gamePanel = new GameScene();
		endingPanel = new EndingScene2();
		HowtoPanel = new HowtoScene();
		
		add(menuPanel);
		add(gamePanel);
		add(endingPanel);
		add(HowtoPanel);
		
		selectScene("Menu");
	}
	
	public GameScene getgameScene(){
		return gamePanel;
	}
	public MenuScene2 getMenuScene(){
		return menuPanel;
	}
	public EndingScene2 getEndingScene(){
		return endingPanel;
	}
	public HowtoScene getHowtoScene(){
		return HowtoPanel;
	}
	
	//�̱��� ������ ����ϰ� ���� ��ü�� ����ȴ�.
	private static class Singleton{
		private static final ScenePanel instance = new ScenePanel();	
	}
	
	//�̱����� Scene �ν��Ͻ��� ��ȯ��. public static���� ���������� ����� �����ϴ�.
	public static ScenePanel getInstance() {
		return Singleton.instance;
	}
	
	//������ ���� ȭ�鿡 �����ش�.
	public void selectScene(String str){
		switch(str){
			case "Menu":
			menuPanel.setVisible(true);
			gamePanel.setVisible(false);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(false);
			break;
			
			case "Game":
			menuPanel.setVisible(false);
			gamePanel.Init();
			gamePanel.setVisible(true);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(false);
			break;
			
			case "Ending":
			menuPanel.setVisible(false);
			gamePanel.setVisible(false);
			endingPanel.Init();
			endingPanel.setVisible(true);
			HowtoPanel.setVisible(false);
			break;
			
			case "Howto":
			menuPanel.setVisible(false);
			gamePanel.setVisible(false);
			endingPanel.setVisible(false);
			HowtoPanel.setVisible(true);
			//HowtoPanel.Init();
			break;
		}
	}
}
