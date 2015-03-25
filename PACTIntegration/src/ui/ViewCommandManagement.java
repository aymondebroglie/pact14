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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class ViewCommandManagement extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private JRadioButton soiree = new JRadioButton("soirée");
	private JRadioButton semaine = new JRadioButton("semaine");
	private JRadioButton mois = new JRadioButton("mois");
	private JRadioButton annee = new JRadioButton("année");
	
	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
	ArrayList<String> tableauAlcool ;
	
	public class EcouteurAction implements ActionListener 
	{		
		private ViewCommandManagement vsm;
		
		public EcouteurAction(ViewCommandManagement viewCommandManagement)
		{
			this.vsm =viewCommandManagement;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			controller.visualiserCommandes(vsm);
			
	}
}

	public ViewCommandManagement(final Controller controller) 
	{
		this.controller = controller;
		
		// definition du layout
		GroupLayout layout = new GroupLayout(this) ;
		this.setLayout(layout);
				
		/**********************************************************************************/
		//Correspond a l'ancien ViewStockManagementSouth : definit les boutons soiree,semaine,mois,annee...
				
		ButtonGroup bg = new ButtonGroup();
		bg.add(soiree);
		bg.add(semaine);
		bg.add(mois);
		bg.add(annee);
			
		soiree.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.setDuree("soiree");
			}
		});
		semaine.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.setDuree("semaine");
			}

		});
		mois.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.setDuree("mois");
			}
		});
		annee.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controller.setDuree("annee");
			}
		});

		/**********************************************************************************/
		
		this.tableauAlcool=controller.obtenirAlcools();
		for (String boisson : tableauAlcool) 
		{
			tableauBouton.add(new BoissonCheckBox(boisson));

		}
		JPanel scrollingarea = new JPanel() ;
		scrollingarea.setLayout(new BoxLayout(scrollingarea, BoxLayout.PAGE_AXIS)) ;
		for(BoissonCheckBox boutton: tableauBouton){
			scrollingarea.add(boutton);
		}
		JScrollPane scroll = new JScrollPane(scrollingarea) ;
		scroll.setPreferredSize(new Dimension(185,140));
		
		JButton boutton = new JButton("Visualiser");
		EcouteurAction ea = new EcouteurAction(this);
		boutton.addActionListener(ea);
		
		layout.setAutoCreateGaps(true);
		//layout.linkSize(SwingConstants.HORIZONTAL, bouton_visualiser, bouton_outilstat );
		
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(scroll)
        				.addGroup(layout.createParallelGroup()
        						.addComponent(soiree)
        						.addComponent(semaine)
        						.addComponent(mois)
        						.addComponent(annee)))
        		.addComponent(boutton)
        		);
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup()
        				.addComponent(scroll)
        				.addGroup(layout.createSequentialGroup()
        						.addComponent(soiree)
        						.addComponent(semaine)
        						.addComponent(mois)
        						.addComponent(annee))
        				)
        		.addComponent(boutton)
        		);
	}

	public ArrayList<String> obtenirBouttonAlcool() 
	{
		ArrayList<String> tableau = new ArrayList<String>();
		for (BoissonCheckBox boutton : tableauBouton) 
		{
			if (boutton.isSelected()) {
				tableau.add(boutton.getNom());
			}
		}
		return tableau;
	}
}
