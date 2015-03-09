package main;
import ui.Window;
import materiel.SerialTest;
import bdd.*;

public class Optibar {

	public static void main(String[] args)
	{

		BDDInterface bdd = new BDD("BAR", "root", "12345678");
		//InitializationPassword.initialiserPassword("poney");
		@SuppressWarnings("unused")
		Window window =new Window(bdd);
		SerialTest main = new SerialTest(bdd);
		main.initialize();
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		bdd.setPrixParBoisson(5449000131805L, 0.07f);
	}

}
