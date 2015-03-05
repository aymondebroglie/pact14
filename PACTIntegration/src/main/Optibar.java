package main;
import ui.ViewWelcome;
import ui.Window;
import ui.Controller;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bdd.*;
import visu.*;

public class Optibar {

	public static void main(String[] args)
	{
		BDDInterface bdd = new BDD("BAR", "root", "12345678");
		Window window =new Window(bdd);
		
		
		/*ArrayList<DispoBoisson> data2=new ArrayList<DispoBoisson>();
		data2=bdd.etatDesStocks(maintenant);
		graphique graph2=new graphique(data2,maintenant);
	JFrame f = new JFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(10,10,500,500);
		
		f.add(graph2);
		f.setVisible(true);*/
		
		
		
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
