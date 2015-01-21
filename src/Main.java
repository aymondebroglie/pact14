import javax.swing.JFrame;
import org.math.plot.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		  
		  double x[] = {1,2}  ;
		  double y[] = {2,3} ;
		  
		 
		  // create your PlotPanel (you can use it as a JPanel)
		  Plot2DPanel plot = new Plot2DPanel();
		 
		  // add a line plot to the PlotPanel
		  plot.addLinePlot("my plot",x,y);
		 
		  // put the PlotPanel in a JFrame, as a JPanel
		  JFrame frame = new JFrame("a plot panel");
		  frame.setContentPane(plot);
		  frame.setVisible(true);

	}

}
