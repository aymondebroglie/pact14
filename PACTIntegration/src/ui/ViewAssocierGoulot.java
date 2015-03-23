package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewAssocierGoulot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	ArrayList<String> tableauAlcool;
	String alcoolSelectionne;
	
	public ViewAssocierGoulot(final Controller controller)
	{
		this.controller = controller;
		
		// definition du layout
				GroupLayout layout = new GroupLayout(this) ;
				this.setLayout(layout);
		
		tableauAlcool = controller.obtenirAlcools();;
		JComboBox<String> menu = new JComboBox<String>();
		alcoolSelectionne = tableauAlcool.get(0);
		
		menu.setPreferredSize(new Dimension(200,25));
		for (String boisson : tableauAlcool)
		{
			menu.addItem(boisson);
		}
		menu.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent arg0) 
			{
				alcoolSelectionne = (String) arg0.getItem();
			}
			
		});
		JLabel msg = new JLabel("Choisissez l'alcool :");
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				controller.associerGoulot(alcoolSelectionne);				
			}	
		});
		
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
	               layout.createParallelGroup(GroupLayout.Alignment.CENTER) 
	                   .addGroup(layout.createSequentialGroup()
	                		   .addComponent(msg)
	                		   .addComponent(menu))
	                	.addComponent(ok)
	               );
	       
	    layout.setVerticalGroup(
	               layout.createSequentialGroup()
	                   .addGroup(layout.createParallelGroup()
	                		   .addComponent(msg)
	                		   .addComponent(menu))
	                   .addComponent(ok)		   
	               );
	}

}
