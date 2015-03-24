package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewAddBottle extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTextField tf = new JTextField();
	
    public ViewAddBottle(final Controller controller)
    {
	this.add(new JLabel("Veuillez rentrer le code barre de la bouteille à ajouter"));
	tf.setPreferredSize(new Dimension(300,25));
	this.add(tf);
	JButton ok = new JButton("OK");
	ok.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.ajoutBouteille(tf.getText());
			
		}
	});
	this.add(ok);
	
	this.add(new JLabel("Ou rentrer les informations à la main"));
	this.add(new JLabel("codeBarre de la boisson"));
	final JTextField codeBarre = new JTextField();
	codeBarre.setPreferredSize(new Dimension(300,25));
	this.add(codeBarre);
	this.add(new JLabel("nom de la boisson"));
	final JTextField nom = new JTextField();
	nom.setPreferredSize(new Dimension(300,25));
	this.add(nom);
	this.add(new JLabel("Marque de la boisson"));
	final JTextField marque = new JTextField();
	marque.setPreferredSize(new Dimension(300,25));
	this.add(marque);
	this.add(new JLabel("Degré de la boisson"));
	final JTextField degre = new JTextField();
	degre.setPreferredSize(new Dimension(300,25));
	this.add(degre);
//	this.add(new JLabel("Prix au cL de la boisson"));
//	final JTextField prixCl = new JTextField();
//	prixCl.setPreferredSize(new Dimension(300,25));
//	this.add(prixCl);
	this.add(new JLabel("Volume de la boisson"));
	final JTextField volume = new JTextField();
	volume.setPreferredSize(new Dimension(300,25));
	this.add(volume);
	JButton ok2 = new JButton("OK");
	ok2.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.ajoutBouteilleMain(Long.parseLong(codeBarre.getText()),nom.getText(),marque.getText()
					,Float.parseFloat(degre.getText()),Integer.parseInt(volume.getText()));
			System.out.println(nom.getText()+marque.getText()
					+Integer.parseInt(degre.getText())+Integer.parseInt(volume.getText()));
		}
	});
	this.add(ok2);
}
}
