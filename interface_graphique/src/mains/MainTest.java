package mains;

import windows.ViewBossLogin;
import windows.ViewController;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		Window window = new Window();
		ViewController controller = new ViewController(window);
		ViewBossLogin viewWelcome = new ViewBossLogin(controller);
		window.setContentPane(viewWelcome);
		window.validate();
;	}
}
