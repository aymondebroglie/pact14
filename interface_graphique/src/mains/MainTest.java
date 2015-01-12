package mains;

import windows.ViewBossHome;
import windows.ViewBottlesManagement;
import windows.ViewController;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		Window window = new Window();
		ViewController controller = new ViewController(window);
		ViewWelcome viewWelcome = new ViewWelcome(controller);
		window.setContentPane(viewWelcome);
		window.validate();
;	}
}
