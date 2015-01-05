package windows;

import java.awt.Color;
import javax.swing.JFrame;



public class Window extends JFrame
{
	String title ="OptiBar" ;
	int width = 400;
	int height = 100;
	
	private final ViewBossHome vbh = new ViewBossHome();
	
	public Window()
	{
		this.setTitle(title);
		this.setSize(width,height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		

		this.setContentPane(vbh);
		this.setVisible(true);
	}
}
