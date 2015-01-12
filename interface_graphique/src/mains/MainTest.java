package mains;

import controller.Controller;
import windows.ViewBossLogin;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		Window window = new Window();
		Controller controller = new Controller(window);
		ViewBossLogin viewWelcome = new ViewBossLogin(controller);
		window.setContentPane(viewWelcome);
		window.validate();
;	}
}
