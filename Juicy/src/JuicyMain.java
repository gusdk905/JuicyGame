import javax.swing.*;
import java.io.*;

public class JuicyMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Juicy!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//윈도우 사이즈를 임의로 변경하지 못하게 설정한다.
		ScenePanel ScenePanel = new ScenePanel();

		frame.getContentPane().add(ScenePanel.getInstance());
		ScenePanel.getInstance().getgameScene().FileInputOutput("data/list.txt","data/description.txt","data/price.txt");
		
				
		frame.pack();
		frame.setVisible(true);
	}

}
