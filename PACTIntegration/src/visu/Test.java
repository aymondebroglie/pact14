package visu;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*; 

import bdd.DispoBoisson;
import bdd.HistoBoisson;


public class Test {

	public static void main(String[] a){
	
	ArrayList<String> boissons = new ArrayList<String>();
	ArrayList<ArrayList<HistoBoisson>> dataHisto = new ArrayList<ArrayList<HistoBoisson>>();
	ArrayList<HistoBoisson> temp = new ArrayList<HistoBoisson>();
	boissons.add("Whisky");
	boissons.add("Vodka");
	boissons.add("Tequila");
	temp.add(new HistoBoisson(new Date(432536345L),8));
	temp.add(new HistoBoisson(new Date(4325363422L),3));
	temp.add(new HistoBoisson(new Date(),11));
	dataHisto.add(new ArrayList<HistoBoisson>(temp));

	temp.clear();
	temp.add(new HistoBoisson(new Date(432536345L),4));
	temp.add(new HistoBoisson(new Date(4325363422L),12));
	temp.add(new HistoBoisson(new Date(),7));
	dataHisto.add(new ArrayList<HistoBoisson>(temp));
	
	temp.clear();
	temp.add(new HistoBoisson(new Date(432536345L),5));
	temp.add(new HistoBoisson(new Date(4325363422L),15));
	temp.add(new HistoBoisson(new Date(),9));
	dataHisto.add(new ArrayList<HistoBoisson>(temp));

	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setBounds(10,10,500,500);
	Graphique g = new Graphique(dataHisto, boissons, 6,true);
	f.add(g);
	f.setVisible(true);
	
	ArrayList<DispoBoisson> temp2 = new ArrayList<DispoBoisson>();
	temp2.add(new DispoBoisson("Whisky", 10));
	temp2.add(new DispoBoisson("Vodka", 15));
	temp2.add(new DispoBoisson("Tequila", 20));
	Date date = new Date(432536345L);
	
	PieChart tpc = new PieChart(temp2, date); 
    tpc.setVisible(true); 
    
    JFrame f2 = new JFrame();
	f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f2.setBounds(10,10,500,500);
	Graphique g2 = new Graphique(temp2, date);
	f2.add(g2);
	f2.setVisible(true);
    
}

}
