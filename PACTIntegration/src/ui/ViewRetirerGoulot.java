package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ViewRetirerGoulot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ButtonGroup bg = new ButtonGroup();
	private ArrayList<BoissonRadioButton> tableauBoutton = new ArrayList<BoissonRadioButton>();
	private ArrayList<String> tableauBoisson;
	private Controller controller;
	

	public ViewRetirerGoulot(Controller controller) {
		this.controller = controller;
		tableauBoisson = controller.obtenirAlcools();
		for (String boisson : tableauBoisson) {
           tableauBoutton.add(new BoissonRadioButton(boisson));
		}
        for(BoissonRadioButton boutton : tableauBoutton ){
        	bg.add(boutton);
        	this.add(boutton);
        }
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
        	;
        	
        });
        this.add(ok);
	}
}