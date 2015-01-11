package mains;

import windows.ViewBossHome;
import windows.ViewBottlesManagement;
import windows.ViewWelcome;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		ViewWelcome vbm = new ViewWelcome();
		Window window = new Window(vbm);

	}
}
