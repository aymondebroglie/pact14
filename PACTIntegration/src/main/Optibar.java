package main;
import ui.Window;
import materiel.SerialTest;
import bdd.*;

public class Optibar {

	private static BDDInterface bdd = new BDD("BAR", "root", "");

	@SuppressWarnings("unused")
	private static Window window =new Window(bdd);
	private static SerialTest main = new SerialTest(bdd);
	private static Thread t=new Thread() {
		public void run() {
			//the following line will keep this app alive for 1000 seconds,
			//waiting for events to occur and responding to them (printing incoming messages to console).
			try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
	};
	public static void main(String[] args)
	{

		//InitializationPassword.initialiserPassword("poney");
		main.initialize();
		
		t.start();
		
	}
	static int barman()
	{
		return main.getBarman();
	}
}
