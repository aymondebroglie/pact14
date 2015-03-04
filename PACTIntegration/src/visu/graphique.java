package visu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	

import javax.swing.JPanel;

	import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import bdd.DispoBoisson;
import bdd.HistoBoisson;


	public class graphique extends JPanel {

		private static final long serialVersionUID = 1L;

		/** titre : Le titre du graphique affich� en haut */
		private String titre;
		/** ordonnee : le nom de l'axe des ordonn�es */
		private String ordonnee;
		/** abscisses : le nom de l'axe des abscisses */
		private String abscisse;
		/** valeurs : les valeurs � afficher, elles sont tri�es par s�ries et par cat�gories*/
		private List<Float> valeurs;
		/** series : la liste des s�ries */
		private List<String> series;
		/** categories : la liste des categories */
		private List<String> categories;
		/** legende : booleen vrai si on affiche la l�gende */
		private boolean legende;
		/** couleurFond : la couleur du fond */
		private Color couleurFond;
		/** couleurBarres : les couleurs appliqu�es aux barres */
		private Color[] couleursBarres = {Color.cyan.darker(), 
				Color.red, Color.green, Color.cyan, Color.magenta, 
				Color.yellow, Color.pink, Color.darkGray, Color.orange};
		

		public graphique(String titre, String abscisse, String ordonnee, List<Float> valeurs, Color fond, List<String> listeSeries, List<String> listeCategory, boolean legende) {
			super(new GridLayout(1,0));
			this.titre=titre;
			this.ordonnee=ordonnee;
			this.abscisse=abscisse;
			this.valeurs=valeurs;
			this.series=listeSeries;
			this.categories=listeCategory;
			this.legende=legende;
			this.couleurFond=fond;
			initialiser();
		}
		
		public graphique(ArrayList<ArrayList<HistoBoisson>> data,ArrayList<String> boissons) 
		{
			super(new GridLayout(1,0));
			this.titre="Evolution des stocks";
			this.ordonnee="Volume";
			this.abscisse="Temps";
			this.valeurs= new ArrayList<Float>();
			/*j'avais dis des bêtises, les différentes arraylist<histoBoisson> n'ont pas nécessairement le même nombre d'élément, je complète donc avec des zéros*/
			ArrayList<HistoBoisson> plusLongue= new ArrayList<HistoBoisson>();
			int i;
			for(ArrayList<HistoBoisson> temp1:data)
			{
				plusLongue=plusLongue.size()<temp1.size()?temp1:plusLongue;//ca s'appelle une condition ternaire la flemme d'expliquer c'est facile.
			}
			for(ArrayList<HistoBoisson> temp1:data)
			{
				i=0;
				if(temp1.size()!=0)
				{
					while(temp1.get(0).getDate().after(plusLongue.get(i).getDate()))
					{
						valeurs.add(0f);
						i++;
					}
				}
				for(HistoBoisson temp:temp1)
				{
					valeurs.add((float)temp.getVolume());
					i++;
				}
				while(i<plusLongue.size())
				{
					valeurs.add(0f);
					i++;
				}
			}
			//voilà ça change pas le fonctionnement global
			this.series=boissons;
			
			this.categories=new ArrayList<String>();
		
			for(HistoBoisson temp:data.get(0))
			{
				categories.add(temp.getDate().toString());
			}
			
			this.legende=true;
			this.couleurFond=Color.white;
			initialiser();
		}
		
		public graphique(ArrayList<DispoBoisson> data,Date date ) 
		{
			super(new GridLayout(1,0));
			this.titre="Etat des stocks � la date "+date.toString();
			this.ordonnee="Volume";
			this.abscisse="Boissons";
			this.valeurs= new ArrayList<Float>();
			for(DispoBoisson temp:data)
			{
				valeurs.add((float)temp.getVolume());
			}
			
			this.series=new ArrayList<String>();
			series.add("Boissons");
			
			this.categories=new ArrayList<String>();
			for(DispoBoisson temp:data)
			{
				categories.add(temp.getBoisson());
			}
			
			this.legende=false;
			this.couleurFond=Color.white;
			initialiser();
		}


		private void initialiser(){
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			int k = 0;
			for ( int j=0; j<categories.size(); j++){
				for (int i=0; i<series.size(); i++){
					dataset.addValue(valeurs.get(k), series.get(i), categories.get(j));
					k++;
				}

			}
			JFreeChart chart = ChartFactory.createBarChart(
					titre,   					// chart title
					abscisse,					// domain axis label
					ordonnee,   				// range axis label
					dataset,    				// data
					PlotOrientation.VERTICAL, 	// orientation
					legende,                    // include legend
					true,                     	// tooltips
					false                     	// URL
			);

			// definition de la couleur de fond
			chart.setBackgroundPaint(couleurFond);

			CategoryPlot plot = (CategoryPlot) chart.getPlot();

			//valeur comprise entre 0 et 1 transparence de la zone graphique
			plot.setBackgroundAlpha(0.9f);

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setDrawBarOutline(false);

			// pour la couleur des barres pour chaque serie

			for (int s=0; s<series.size(); s++){
				GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, couleursBarres[s],
						0.0f, 0.0f, new Color(0, 40, 70));
				renderer.setSeriesPaint(s, gp0);

			}		

			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setFillZoomRectangle(true);
			chartPanel.setMouseWheelEnabled(true);
			chartPanel.setPreferredSize(new Dimension(500, 270));

			add(chartPanel);
		} 
}