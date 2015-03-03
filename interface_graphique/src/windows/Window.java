package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import controller.Controller;




public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;
	String title ="OptiBar" ;
	private int width = 400;
	private int height = 200; 
	
	private Controller view = new Controller(this);
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu general = new JMenu("G�n�ral");
	private JMenu general_user = new JMenu("Utilisateur");
	private JRadioButtonMenuItem barman = new JRadioButtonMenuItem("Barman");
	private JRadioButtonMenuItem boss = new JRadioButtonMenuItem("Patron");
	
	private JMenu settings = new JMenu("Param�tres");
	private JMenuItem settings_language = new JMenuItem("Langue");
	private JMenu settings_units = new JMenu("Unit�s");
	private JMenuItem settings_units_money = new JMenuItem("Monnaie");
	private JMenuItem settings_units_metric = new JMenuItem("Syst�me m�trique"); 
	
	private JMenu help = new JMenu("Aide");
	private JMenuItem help_help = new JMenuItem("Aide");
	private JMenuItem help_about = new JMenuItem("A propos d'OptiBar");
	
	public void setWSize(int width,int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public Window()
	{
		this.setTitle(title);
		this.setSize(width,height); 
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	/** Menu general*/
		//Sous-menu utilisateur
		this.general.add(this.general_user);
			ButtonGroup bg = new ButtonGroup();
				bg.add(barman);
				bg.add(boss);
			boss.setSelected(true);
			this.general_user.add(barman);
				this.barman.addActionListener(new ActionListener()
				{					
					public void actionPerformed(ActionEvent arg0)
					{
						view.boutonBarman();
					}
				});
			this.general_user.add(boss);
			this.boss.addActionListener(new ActionListener()
			{					
				public void actionPerformed(ActionEvent arg0)
				{
					view.login();
				}
			});
			this.general_user.addSeparator();
		
			
		// Sous_menu 2
		
	/** Menu settings*/
		// Sous-menu langue
		this.settings.add(this.settings_language);
			
		
		// Sous-menu units
		
		this.settings.add(this.settings_units);
			this.settings_units.add(this.settings_units_money);
			this.settings_units.add(this.settings_units_metric);
		
	/** Menu help*/
		this.help.add(this.help_help);
		this.help.add(this.help_about);
			
		this.menubar.add(general);
		this.menubar.add(settings);
		this.menubar.add(help);
			
		this.setJMenuBar(menubar);
		this.setVisible(true);
	} 
}