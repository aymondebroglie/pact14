import java.awt.*; 
 
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 



public class main {

	public static void main(String[] a){
	List<Float> donnees = new ArrayList<Float>();
	List<String> l1 = new ArrayList<String>();
	List<String> l2 = new ArrayList<String>();
	l2.add("Vodka");
	l2.add("Bière");
	l1.add("1");
	l1.add("2");
	l1.add("3");
	l1.add("4");
	donnees.add(1f);
	donnees.add(2f);
	donnees.add(4f);
	donnees.add(8f);
	donnees.add(3f);
	donnees.add(5f);
	donnees.add(5f);
	donnees.add(9f);
	
	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setBounds(10,10,500,500);
	graphique g = new graphique("Consommation", "jour", "litre", donnees, Color.white, l2, l1, true);
	f.add(g);
	f.setVisible(true);
}

}
