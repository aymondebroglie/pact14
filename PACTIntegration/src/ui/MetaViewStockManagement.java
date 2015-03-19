package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MetaViewStockManagement extends JPanel
{
	private Controller controller ;
	
	public MetaViewStockManagement(Controller controller)
	{
		this.controller = controller ;
		ViewStocksManagement vsm_east = new ViewStocksManagement(controller) ;
		ViewStockManagementSouth vsm_south = new ViewStockManagementSouth(controller) ;
		
		this.setLayout(new BorderLayout());
		
		this.add(vsm_east, BorderLayout.NORTH);
		this.add(vsm_south, BorderLayout.SOUTH);
	}
	
	
}
