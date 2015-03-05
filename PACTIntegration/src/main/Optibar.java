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
		
/*		long dayMilli=86400000L;
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(new Date());
		Date maintenant=calendar.getTime();
		calendar.setTimeInMillis(calendar.getTime().getTime()-5*dayMilli);
		Date threeDaysBefore =calendar.getTime();
		ArrayList<ArrayList<HistoBoisson>> data= new ArrayList<ArrayList<HistoBoisson>>();
		data.add(bdd.evolutionDesStocks("Rhum Blanc",threeDaysBefore,maintenant));
		data.add(bdd.evolutionDesStocks("Vodka 100 cl Poliakov",threeDaysBefore,maintenant));
		ArrayList<String> noms = new ArrayList<String>();
		noms.add("Rhum Blanc");
		noms.add("Vodka 100 cl Poliakov");
		/*for(ArrayList<HistoBoisson> test1:data)
		{	
			for(HistoBoisson test:test1)
				System.out.println(test.getVolume());
		}*/
/*		graphique graph= new graphique(data,noms);
		
		/****************************/
		
		/*ArrayList<DispoBoisson> data2=new ArrayList<DispoBoisson>();
		data2=bdd.etatDesStocks(maintenant);
		graphique graph2=new graphique(data2,maintenant);*/
/*		JFrame f = new JFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(10,10,500,500);
		
		f.add(graph);
		f.setVisible(true);
*/		
		
		
/****pour fonctionnalité 'prix'****/
/*1*/		//bdd.ajouterBoisson(12345678, "test1", "PACT1.4", 100, 99.99);
// codeBarre commence par 0 va poser de problèmes
		
/*2*/		//bdd.ajouterBoisson(12346515, "test2", "PACT1.4", 100, 0.0);
// si on ajoute 2 boisson trop rapide comme ici, il y aura de probleme pour le même date(key primary)
		
		//bdd.setPrixParBoisson(12345678, 1);
		//bdd.setPrixParBoisson(12346515, 10);
/*3*/		//bdd.ajouterConsommation(1, 1, 11);
// mal cohérence dans table Associe 
		//bdd.associeBluetoothCodeBarre(12345678, 1);
		//bdd.associeBluetoothCodeBarre(12346515, 2);
		/*bdd.ajouterConsommation(1, 1, 11);
		bdd.ajouterConsommation(2, 1, 11);
		bdd.finDeCommande(1);*/
	}

}
