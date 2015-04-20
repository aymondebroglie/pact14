package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bdd.BDD;
import bdd.BDDInterface;

public class ViewEntreeVolume extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controller controller ;
	
	public ViewEntreeVolume(final Controller controller,final String codeBarre)
	{
		this.controller = controller ;
		BDDInterface bdd = controller.getBDD() ;
		final long code = Long.parseLong(codeBarre);
		
		JPanel pan = new JPanel() ;
		pan.add(new JLabel("<html>Pas de volume, merci d'entrer a la main<br /> Volume de  la boisson <html/>"));
		final JTextField volume1 = new JTextField();
		volume1.setPreferredSize(new Dimension(300,25));
		pan.add(volume1);
		JButton ok3 = new JButton("OK");
		ok3.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) 
			{
				bdd.setVolumeDeBoisson(Integer.parseInt(volume1.getText()), code);
				JOptionPane.showMessageDialog(null,
					"Boisson ajoutée: "+ codeBarre, "Info",
					JOptionPane.INFORMATION_MESSAGE);
				controller.gestionStocks();
			
			}
		});
		pan.add(ok3);
	}
	
	
}
