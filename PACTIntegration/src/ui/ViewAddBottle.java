package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ViewAddBottle extends JPanel 
{


	private static final long serialVersionUID = 1L;
	
	
    public ViewAddBottle(final Controller controller)
    {
    	// definition du layout
    	GroupLayout layout = new GroupLayout(this) ;
    	this.setLayout(layout);
    	
    	JLabel msg1 = new JLabel("Veuillez rentrer le code barre de la bouteille à ajouter");
    	
    	final JTextField tf = new JTextField();
    	tf.setPreferredSize(new Dimension(300,25));
    	
    	//this.add(tf);
    	JButton ok = new JButton("OK");
    	ok.addActionListener(new ActionListener()
    	{
    		@Override
    		public void actionPerformed(ActionEvent e) 
    		{
    			controller.ajoutBouteille(tf.getText());
    		}
    	});
    	//this.add(ok);
	
    	JLabel msg2 = new JLabel("Sinon, rentrez les informations à la main");
    	JLabel msg3 = new JLabel("Code-barre");
    	
    	final JTextField codeBarre = new JTextField();
    	codeBarre.setPreferredSize(new Dimension(300,25));
    	
    	//this.add(codeBarre);
    	JLabel msg4 = new JLabel("Nom");
    	
    	final JTextField nom = new JTextField();
    	nom.setPreferredSize(new Dimension(300,25));
    	//this.add(nom);
    	
    	JLabel msg5 = new JLabel("Marque");
    	
    	final JTextField marque = new JTextField();
    	marque.setPreferredSize(new Dimension(300,25));
    	//this.add(marque);
    	
    	JLabel msg6 = new JLabel("Degré");
    	
    	final JTextField degre = new JTextField();
    	degre.setPreferredSize(new Dimension(300,25));
    	//this.add(degre);
    	
    	/**	this.add(new JLabel("Prix au cL de la boisson"));
    	//	final JTextField prixCl = new JTextField();
    	//	prixCl.setPreferredSize(new Dimension(300,25));
    	//	this.add(prixCl); */
    	
    	JLabel msg7 = new JLabel("Volume");
    	
    	final JTextField volume = new JTextField();
    	volume.setPreferredSize(new Dimension(300,25));
    	//this.add(volume);
    	
    	JButton ok2 = new JButton("OK");
    	ok2.addActionListener(new ActionListener()
    	{
    		@Override
    		public void actionPerformed(ActionEvent e) 
    		{
    			controller.ajoutBouteilleMain(Long.parseLong(codeBarre.getText()),nom.getText(),marque.getText()
    				,Float.parseFloat(degre.getText()),Integer.parseInt(volume.getText()));
    			System.out.println(nom.getText()+marque.getText()
					+Integer.parseInt(degre.getText())+Integer.parseInt(volume.getText()));
    		}
    	});
    	//this.add(ok2);
    	
    	layout.setAutoCreateGaps(true);
		layout.linkSize(SwingConstants.HORIZONTAL, msg3,msg4,msg5,msg6,msg7 );
		layout.linkSize(SwingConstants.HORIZONTAL, tf,codeBarre,nom,marque,degre,volume );
		layout.linkSize(SwingConstants.HORIZONTAL, ok, ok2);
		
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	               	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	               			.addComponent(msg1)
	               			.addComponent(tf)
	               			.addComponent(ok))
	               	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	               			.addComponent(msg2)
	               			.addGroup(layout.createSequentialGroup()
	               					.addComponent(msg3)
	               					.addComponent(codeBarre))
	               			.addGroup(layout.createSequentialGroup()
	               					.addComponent(msg4)
	               					.addComponent(nom))
	               			.addGroup(layout.createSequentialGroup()
	               					.addComponent(msg5)
	               					.addComponent(marque))
	               			.addGroup(layout.createSequentialGroup()
	               					.addComponent(msg6)
	               					.addComponent(degre))
	               			.addGroup(layout.createSequentialGroup()
	               					.addComponent(msg7)
	               					.addComponent(volume))
	               			.addComponent(ok2))
	               	);
	       
	    layout.setVerticalGroup(layout.createSequentialGroup()
	    		.addGroup(layout.createSequentialGroup()
               			.addComponent(msg1)
               			.addComponent(tf)
               			.addComponent(ok))
               	.addGap(25)
               	.addGroup(layout.createSequentialGroup()
               			.addComponent(msg2)
               			.addGroup(layout.createParallelGroup()
               					.addComponent(msg3)
               					.addComponent(codeBarre))
               			.addGroup(layout.createParallelGroup()
               					.addComponent(msg4)
               					.addComponent(nom))
               			.addGroup(layout.createParallelGroup()
               					.addComponent(msg5)
               					.addComponent(marque))
               			.addGroup(layout.createParallelGroup()
               					.addComponent(msg6)
               					.addComponent(degre))
               			.addGroup(layout.createParallelGroup()
               					.addComponent(msg7)
               					.addComponent(volume))
               			.addComponent(ok2))
               	);
    }
}
