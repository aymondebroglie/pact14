package mains;

import windows.ViewWelcome;
import windows.Window;
import BDD.BDDInterface;
import controller.Controller;

public class Main {

	public static void main(String[] args) 
	{
		Window window = new Window();
		BDDInterface bdd = null;
		Controller controller = new Controller(window, bdd);
		ViewWelcome welcome = new ViewWelcome(controller);
		window.setContentPane(welcome);
		window.validate();
	}

}
