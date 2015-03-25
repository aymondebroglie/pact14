package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import stat.DimensionException;



public class ViewStocksManagement extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private ArrayList<BoissonCheckBox> tableauBouton = new ArrayList<BoissonCheckBox>();
	ArrayList<String> tableauAlcool;
	
	private JRadioButton soiree = new JRadioButton("soirée");
	private JRadioButton semaine = new JRadioButton("semaine");
	private JRadioButton mois = new JRadioButton("mois");
	private JRadioButton annee = new JRadioButton("année");

	public class EcouteurVisualiser implements ActionListener 
	{

		private ViewStocksManagement vsm;

		
		public EcouteurVisualiser(ViewStocksManagement vsm) 
		{
			this.vsm = vsm;
		}
		
	

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			try {
				controller.visualiser(vsm);
			} catch (DimensionException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	
	public class EcouteurStats implements ActionListener 
	{
		private ViewStocksManagement vsm ;
		
			public EcouteurStats(ViewStocksManagement vsm)
		{
			this.vsm = vsm ;
		}

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					controller.visualiserStat(vsm);
				} 
				catch (DimensionException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}

	public ViewStocksManagement(final Controller controller) 
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
		
		tableauAlcool=controller.obtenirAlcools();
		for (String boisson : tableauAlcool) 
		{
			tableauBouton.add(new BoissonCheckBox(boisson));
		}
		JPanel scrollingarea = new JPanel() ;
		scrollingarea.setLayout(new BoxLayout(scrollingarea, BoxLayout.PAGE_AXIS)) ;
		for (BoissonCheckBox bouton : tableauBouton) 
		{
			scrollingarea.add(bouton);
		}
		JScrollPane scroll = new JScrollPane(scrollingarea) ;
		scroll.setPreferredSize(new Dimension(185,140));
				
		JButton bouton_visualiser = new JButton("Visualiser");
		EcouteurVisualiser ev = new EcouteurVisualiser(this);
		bouton_visualiser.addActionListener(ev);
		//this.add(bouton_visualiser);
		
		JButton bouton_outilstat = new JButton("Outil Statistique") ;
		EcouteurStats es = new EcouteurStats(this) ;
		bouton_outilstat.addActionListener(es) ;
		//this.add(bouton_outilstat) ;
		
		layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, bouton_visualiser, bouton_outilstat );
		
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(scroll)
        				.addGroup(layout.createParallelGroup()
        						.addComponent(soiree)
        						.addComponent(semaine)
        						.addComponent(mois)
        						.addComponent(annee)))
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(bouton_visualiser)
        				.addComponent(bouton_outilstat))
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
        		.addGroup(layout.createParallelGroup()
        				.addComponent(bouton_visualiser)
        				.addComponent(bouton_outilstat))
        		
        		);
	}

	public ArrayList<String> obtenirBouttonAlcool() {

		ArrayList<String> tableau = new ArrayList<String>();
		for (BoissonCheckBox boutton : tableauBouton) {
			if (boutton.isSelected()) {
				tableau.add(boutton.getNom());
			}
		}
		return tableau;

	}

}
