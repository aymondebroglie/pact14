import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 

import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*; 
import org.jfree.data.general.DefaultPieDataset;
public class main{
public static void main(String[] a){
	List<Float> donnees = new ArrayList<Float>();
	List<String> l1 = new ArrayList<String>();
	List<String> l2 = new ArrayList<String>();
	l2.add("0");
	l1.add("1");
	l1.add("2");
	l1.add("3");
	l1.add("4");
	donnees.add(1f);
	donnees.add(2f);
	donnees.add(4f);
	donnees.add(8f);
	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setBounds(10,10,500,500);
	graphique g = new graphique("Consommation Poliakoff", "jour", "litre", donnees, Color.white, l2, l1, true);
	f.add(g);
	f.setVisible(true);
}

}
