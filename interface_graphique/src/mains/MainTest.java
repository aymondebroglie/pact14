package mains;

import BDD.BDDInterface;
import controller.Controller;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		Window window = new Window();
		BDDInterface bdd = null;
		Controller controller = new Controller(window,bdd);
		ViewWelcome viewWelcome = new ViewWelcome(controller);
		window.setContentPane(viewWelcome);
		window.validate();	
	}
}
