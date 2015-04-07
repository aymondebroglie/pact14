package visu;

import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*; 

import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*; 
import org.jfree.data.general.DefaultPieDataset;

import bdd.DispoBoisson;

public class PieChart extends JFrame { 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel pnl; 

 /* public PieChart() { 
    addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) { 
        dispose(); 
        System.exit(0); 
      } 
    }); 
    pnl = new JPanel(new BorderLayout()); 
    setContentPane(pnl); 
    setSize(400, 250); 

    DefaultPieDataset pieDataset = new DefaultPieDataset(); 
    pieDataset.setValue("Valeur1", new Integer(27)); 
    pieDataset.setValue("Valeur2", new Integer(10)); 
    pieDataset.setValue("Valeur3", new Integer(50)); 
    pieDataset.setValue("Valeur4", new Integer(5)); 

    JFreeChart pieChart = ChartFactory.createPieChart("Test camembert", 
      pieDataset, true, true, true); 
    ChartPanel cPanel = new ChartPanel(pieChart); 
    pnl.add(cPanel); 
  } */
 


public PieChart(ArrayList<DispoBoisson> data, Date date) { 
    addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) { 
        dispose(); 
        System.exit(0); 
      } 
    }); 
    pnl = new JPanel(new BorderLayout()); 
    setContentPane(pnl); 
    setSize(400, 250); 

    DefaultPieDataset pieDataset = new DefaultPieDataset(); 
    for(DispoBoisson temp:data)
    	
      {
    	pieDataset.setValue(temp.getBoisson(), new Integer(temp.getVolume())); 
    	
      }
 

    JFreeChart pieChart = ChartFactory.createPieChart("Etat des stocks le "+date.toString().subSequence(4, 16).toString() + date.toString().subSequence(23,28).toString(), 
      pieDataset, true, true, true);
    pieChart.setBackgroundPaint(new Color(250,250,227));
    Plot plot = pieChart.getPlot();
    plot.setBackgroundPaint(new Color(239,239,234));
    ChartPanel cPanel = new ChartPanel(pieChart); 
    pnl.add(cPanel); 
}
}
