package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import controller.Controller;




public class Window extends JFrame
{
	 String title ="OptiBar" ;
	private static int width = 400;
	private int width = 400;
	
	private int height = 120; 
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu general = new JMenu("G�n�ral");
	private JMenu general_user = new JMenu("Utilisateur");
	private JRadioButtonMenuItem barman = new JRadioButtonMenuItem("Barman");
	private JRadioButtonMenuItem boss = new JRadioButtonMenuItem("Patron");
	
	private JMenu settings = new JMenu("Param�tres");
	
	public static void setWSize(int width,int height)
	{
		Window.width = width;
		Window.height = height;
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
					view.boutonGestionnaire();
				}
			});
			this.general_user.addSeparator();
		this.general.add(this.general_user);
			
		// Sous_menu 2
		
	/** Menu settings*/
		this.menubar.add(general);
		this.menubar.add(settings);
		
			
		this.setJMenuBar(menubar);
		this.setVisible(true);
	} 
}