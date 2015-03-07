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
	private Controller controller;
	private JTextField tf = new JTextField();
public ViewAddBottle(final Controller controller){
	this.controller = controller;
	this.add(new JLabel("Veuillez rentrer le code barre de la bouteille � ajouter"));
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
}
}
