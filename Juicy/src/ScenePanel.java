import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScenePanel extends JPanel {
	//모든 씬을 인스턴스 데이터로 저장하여사용한다.
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
	
	//싱글톤 패턴을 사용하고 단일 객체로 선언된다.
	private static class Singleton{
		private static final ScenePanel instance = new ScenePanel();	
	}
	
	//싱글톤의 Scene 인스턴스를 반환함. public static으로 전역적으로 사용이 가능하다.
	public static ScenePanel getInstance() {
		return Singleton.instance;
	}
	
	//선택한 씬만 화면에 보여준다.
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
