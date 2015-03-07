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
		bdd.associerGoulot(1, bdd.codeBarreDeBoisson("Rhum Blanc"));
		/*bdd.setPrixParBoisson(3147690059004L, 1.157f);
		bdd.ajouterConsommation(1, 1, 15);
		bdd.associerGoulot(1, 3180290047897L);
		bdd.ajouterConsommation(1, 1, 24);*/
	}

}
