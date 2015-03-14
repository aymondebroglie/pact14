package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bdd.BDDInterface;
import bdd.DetailDeCommand;

public class ViewNote extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Controller controller ;
	
	public ViewNote(Controller controller)
	{
		this.controller = controller ;
		
		//Methode appelee si on appuie sur imprimer note dans l'ecran du Barman
		//Code pour le test, il faudra demander a la base de donnee de nous fournir la note pour le serveur donne
		BDDInterface bdd = this.controller.getBDD() ;		
		
		
		   ArrayList<DetailDeCommand> list= bdd.imprimerCommande(1);//suppose rfid ets 1
		    
		String result="<html><br />";	
		result += "<h1>********Commande********<h1 /><br />";
				
		for(DetailDeCommand t:list)
		{
			result += "<h3>"+t.toString()+"<h3 /><br />";
		}
				
		result += "<h2 >Total:\t\t"+bdd.finDeCommande(1)+"€<h2 /><br /><html />";
		
		this.add(new JLabel(result),BorderLayout.SOUTH);
	}
	
}
