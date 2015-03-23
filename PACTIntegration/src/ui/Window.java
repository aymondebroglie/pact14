package ui;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import bdd.*;
public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	String title = "OptiBar";
	private int width = 500;
	private int height = 300;
	BDDInterface bdd = null;
	private Controller controller ;
	//private GroupLayout layout ;

	private JMenuBar menubar = new JMenuBar();
	private JMenu general = new JMenu("Général");
	private JMenu general_user = new JMenu("Utilisateur");
	private JMenuItem barman = new JMenuItem("Barman");
	private JMenuItem boss = new JMenuItem("Patron");
	private JMenu general_navigation = new JMenu("Navigation");
	private JMenuItem previous = new JMenuItem("<<<");
	private JMenuItem next = new JMenuItem(">>>");
	
	private JMenu settings = new JMenu("Paramètres");
	private JMenuItem settings_language = new JMenuItem("Langue");
	private JMenuItem settings_mdp = new JMenuItem("Mot de Passe");
	private JMenu settings_units = new JMenu("Unités");
	private JMenuItem settings_units_money = new JMenuItem("Monnaie");
	private JMenuItem settings_units_metric = new JMenuItem("Système métrique");

	private JMenu help = new JMenu("Aide");
	private JMenuItem help_help = new JMenuItem("Aide");
	private JMenuItem help_about = new JMenuItem("A propos d'OptiBar");

	public void setWSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Window(BDDInterface bdd) 
	{
		
		Container cont = this.getContentPane();
        cont.setLayout(new GridBagLayout());

		this.bdd=bdd;
		this.setTitle(title);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/** Menu general */
		// Sous-menu utilisateur
		this.general.add(this.general_user);
			this.general_navigation.add(previous);
				this.previous.addActionListener(new ActionListener()
				{					
					public void actionPerformed(ActionEvent arg0)
					{
						controller.previousView(controller.getActualView());
					}
				});
			this.general_navigation.add(next);
			this.next.addActionListener(new ActionListener()
			{					
				public void actionPerformed(ActionEvent arg0)
				{
					controller.nextView(controller.getActualView());
				}
			});
			
			
			//Sous-menu navigation
			this.general.add(this.general_navigation);
				this.general_user.add(barman);
					this.barman.addActionListener(new ActionListener()
					{					
						public void actionPerformed(ActionEvent arg0)
						{
							controller.boutonBarman();
						}
					});
				this.general_user.add(boss);
					this.boss.addActionListener(new ActionListener()
					{					
						public void actionPerformed(ActionEvent arg0)
						{
							controller.boutonGestionnaire();
							
						}
					});
				
		
			
		// Sous_menu 2

		/** Menu settings */
		// Sous-menu mdp
		this.settings.add(settings_mdp);
		this.settings_mdp.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.changementMDP();
			}
		});
		this.settings.addSeparator();
		
		
		// Sous-menu langue
		this.settings.add(this.settings_language);
		this.settings_language.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.changementLanguage();
			}
		});
		// Sous-menu units

		this.settings.add(this.settings_units);
		this.settings_units.add(this.settings_units_money);
		this.settings_units_money.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.changementSystemeMonetaire();
			}
		});
		this.settings_units.add(this.settings_units_metric);
		this.settings_units_metric.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.changementSystemeMetrique();
			}
		});

		/** Menu help */
		this.help.add(this.help_help);
		this.help_help.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.afficherAide();
			}
		});
		this.help.add(this.help_about);
		this.help_about.addActionListener(new ActionListener()
		{					
			public void actionPerformed(ActionEvent arg0)
			{
				controller.aProposOptibar();
			}
		});

		this.menubar.add(general);
		this.menubar.add(settings);
		this.menubar.add(help);

		this.setJMenuBar(menubar);
		this.setVisible(true);
		
		controller=new Controller(this,bdd);
		ViewWelcome vw = new ViewWelcome(controller);
		
		Container cp = new Container() ;		
		cp.setLayout(new GridBagLayout());
		cp.add(vw) ;
		this.setContentPane(cp);
	}
}
