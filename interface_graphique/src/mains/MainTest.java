package mains;

import windows.ViewBossHome;
import windows.ViewBottlesManagement;
import windows.Window;

public class MainTest 
{
	public static void main(String[] args) 
	{
		ViewBottlesManagement vbm = new ViewBottlesManagement();
		Window window = new Window(vbm);

	}
}
