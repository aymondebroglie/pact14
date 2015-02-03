package mains;

import controller.Controller;
import windows.ViewBossLogin;
import windows.ViewChangePassword;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		Window window = new Window();
		Controller controller = new Controller(window);
		ViewChangePassword viewWelcome = new ViewChangePassword(controller);
		window.setContentPane(viewWelcome);
		window.validate();	
	}
}
