package ui;

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
		this.controller.setActualView(this) ;
		
		JPanel pan = new JPanel();
		BDDInterface bdd = this.controller.getBDD() ;
		
    	ArrayList<DetailDeCommand> list= bdd.imprimerCommande(1);//suposse rfid ets 1
		String resultat="******Command******";
		pan.add(new JLabel(resultat));
		double prix=0;
		for(DetailDeCommand t:list)
		{
			resultat=t.getNom()+"  "+t.getPrixParVolume()+"  "+t.getVolume();
			pan.add(new JLabel(resultat));
	    	prix=t.getPrixTotal();
		}
		pan.add(new JLabel("PRIX TOTAL :"+prix));
	}
	
}
