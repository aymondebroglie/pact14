package mains;

import BDD.BDDInterface;
import controller.Controller;
import windows.ViewSeeDatas;
import windows.ViewWelcome;
import windows.Window;

public class MainTest {
	public static void main(String[] args) {
		Window window = new Window();
		BDDInterface bdd = null;
		Controller controller = new Controller(window, bdd);
		ViewWelcome welcome = new ViewWelcome(controller);
		window.setContentPane(welcome);
		window.validate();
	}
}
