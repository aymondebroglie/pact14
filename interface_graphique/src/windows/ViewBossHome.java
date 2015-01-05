package windows;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewBossHome extends JPanel 
{
	private final JButton consultervosdonnees =  new JButton("Consulter vos donnees");
	private final JButton gestionstocks =  new JButton("Gérer vos stocks");
	
	public  ViewBossHome()
	{
		this.add(consultervosdonnees);
		this.add(gestionstocks);
	}
	
}
