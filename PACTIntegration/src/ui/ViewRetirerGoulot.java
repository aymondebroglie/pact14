package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewRetirerGoulot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ButtonGroup bg = new ButtonGroup();
	private ArrayList<BoissonRadioButton> tableauBoutton = new ArrayList<BoissonRadioButton>();
	private ArrayList<String> tableauBoisson;
	private Controller controller;
	

	public ViewRetirerGoulot(Controller controller) 
	{
		this.controller = controller;
		
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
		
		tableauBoisson = controller.obtenirAlcools();
		for (String boisson : tableauBoisson) {
           tableauBoutton.add(new BoissonRadioButton(boisson));
		}
		JPanel scrollingarea = new JPanel() ;
		scrollingarea.setLayout(new BoxLayout(scrollingarea, BoxLayout.PAGE_AXIS)) ;
        for(BoissonRadioButton boutton : tableauBoutton )
        {
        	bg.add(boutton);
        	
        	scrollingarea.add(boutton);
        }
        JScrollPane scroll = new JScrollPane(scrollingarea) ;
		scroll.setPreferredSize(new Dimension(185,140));
		
        JButton ok = new JButton("OK");
      
        ok.addActionListener(new Associer());

        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addComponent(scroll)
        		.addGap(25) 
        		.addComponent(ok)
        		);
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addComponent(scroll)
        		.addComponent(ok)
        		);
	}
	public ArrayList<String> obtenirBouttonAlcool() {

		ArrayList<String> tableau = new ArrayList<String>();
		for (BoissonRadioButton boutton : tableauBoutton) {
			if (boutton.isSelected()) {
				tableau.add(boutton.getNom());
			}
		}
		return tableau;

	}
	public class Associer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			controller.attribution(obtenirBouttonAlcool());
		}
	}
}
