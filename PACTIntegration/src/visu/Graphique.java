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


	public class Graphique extends JPanel {

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
		

		public Graphique(String titre, String abscisse, String ordonnee, List<Float> valeurs, Color fond, List<String> listeSeries, List<String> listeCategory, boolean legende) {
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
		
		public Graphique(ArrayList<ArrayList<HistoBoisson>> data,ArrayList<String> boissons,int n, boolean stocks)//true si on visualise les stocks, false si on visualise les commandes 
		{
			super(new GridLayout(1,0));
			
			this.titre=stocks?"Evolution des stocks":"Consommation en cL dans les commandes";
			this.ordonnee=stocks?"Volume (cl)":"Volume (bouteille)";
			this.abscisse="Temps";
			this.valeurs= new ArrayList<Float>();
			Date[] dates=new Date[n];
			/*j'avais dis des bêtises, les différentes arraylist<histoBoisson> n'ont pas nécessairement le même nombre d'élément, je complète donc avec des zéros*/
			ArrayList<HistoBoisson> plusLongue= new ArrayList<HistoBoisson>();
			int i,j=0,k,size;
			
			for(ArrayList<HistoBoisson> temp:data)
			{
				plusLongue=temp.size()>plusLongue.size()?temp:plusLongue;
			}
			
			size=plusLongue.size();
			
			if(size==0)
				return;
			
			dates[0]=plusLongue.get(0).getDate();
			dates[n-1]=plusLongue.get(size-1).getDate();
			long longueur = dates[n-1].getTime() - dates[0].getTime();
			long pas = longueur/(n-1);
			for(i=1; i<n-1; i++)
			dates[i]=new Date(dates[0].getTime() + i*pas) ;
			
			/*int[] compteurs=new int[data.size()];
			for(i=0; i<data.size();i++)
				compteurs[i]=0;
			for(i=0; i<size; i++)
			{
				for(j=0; j<data.size(); j++)
				{
					if(data.get(j).size()==compteurs[j]||plusLongue.get(i).getDate().before(data.get(j).get(compteurs[j]).getDate()))
						valeurs.add(0f);
					else
					{
						valeurs.add((float)data.get(j).get(compteurs[j]).getVolume());
						compteurs[j]++;
					}
					
				}
			}*/
			//voilà en fait j'en ai chié des boules c'était pas évident du tout.
			
			for(i=0; i<n; i++)
			{
				for(j=0; j<data.size(); j++)
				{
					System.out.println(dates[0]);
					if(dates[i].before(data.get(j).get(0).getDate()))
					{
						valeurs.add(0f);
					}
					else
					{						
						k=0;
						for(HistoBoisson temp:data.get(j))
						{
							if(dates[i].after(temp.getDate()))
								k++;
						}
						valeurs.add((float)data.get(j).get(k).getVolume());
					}	
				}
			}
			
			this.series=boissons;
			
			this.categories=new ArrayList<String>();
		
			/*for(HistoBoisson temp:plusLongue)
			{
				categories.add(temp.getDate().toString().subSequence(4, 16).toString() + temp.getDate().toString().subSequence(23,28).toString());
			}*/
			for(Date temp:dates)
				categories.add(temp.toString().subSequence(4, 16).toString() + temp.toString().subSequence(23,28).toString());
			
			this.legende=true;
			this.couleurFond=Color.white;
			initialiser();
		}
		
		public Graphique(ArrayList<DispoBoisson> data,Date date ) 
		{
			super(new GridLayout(1,0));
			String dateString;
			dateString=date.toString().subSequence(4, 16).toString() + date.toString().subSequence(23,28).toString();
			this.titre="Etat des stocks � la date "+dateString;
			this.ordonnee="Volume (bouteille)";
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
	

			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setFillZoomRectangle(true);
			chartPanel.setMouseWheelEnabled(true);
			chartPanel.setPreferredSize(new Dimension(500, 270));

			add(chartPanel);
		} 
}