package main;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;

import bdd.*;
import visu.*;

public class Optibar {

	public static void main(String[] args)
	{
		BDDInterface bdd = new BDD("BAR", "root", "12345678");
		long dayMilli=86400000L;
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(new Date());
		Date maintenant=calendar.getTime();
		calendar.setTimeInMillis(calendar.getTime().getTime()-5*dayMilli);
		Date threeDaysBefore =calendar.getTime();
		ArrayList<ArrayList<HistoBoisson>> data= new ArrayList<ArrayList<HistoBoisson>>();
		data.add(bdd.evolutionDesStocks("Rhum Blanc",threeDaysBefore,maintenant));
		ArrayList<String> noms = new ArrayList<String>();
		noms.add("Curacao");
		/*for(ArrayList<HistoBoisson> test1:data)
		{	
			for(HistoBoisson test:test1)
				System.out.println(test.getVolume());
		}*/
		graphique graph= new graphique(data,noms);
		
		/****************************/
		
		/*ArrayList<DispoBoisson> data2=new ArrayList<DispoBoisson>();
		data2=bdd.etatDesStocks(maintenant);
		graphique graph2=new graphique(data2,maintenant);*/
		JFrame f = new JFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(10,10,500,500);
		
		f.add(graph);
		f.setVisible(true);
		
		
	}

}
