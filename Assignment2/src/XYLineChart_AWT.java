import java.awt.Color;
import java.util.ArrayList;
import java.awt.BasicStroke; 

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart_AWT extends ApplicationFrame {

   public XYLineChart_AWT( String applicationTitle, String chartTitle,ArrayList<Double>[] x, ArrayList<Double>[] y ) {
      super(applicationTitle);
    
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Category" ,
         "Score" ,
         createDataset(x,y) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset(ArrayList<Double>[] x, ArrayList<Double>[] y ) {
	   final XYSeriesCollection dataset = new XYSeriesCollection( );   
	   XYSeries series;
	   for(int i = 0;i<x.length;i++)
	   {
		   String title ="";
		   if(i==0)
			 title = "Predict";
		   else
			 title = "Target";
		   series = new XYSeries( title);
		   for(int j = 0;j<x[i].size();j++)
		   {
			   series.add(x[i].get(j),y[i].get(j));
		   }
		   dataset.addSeries(series);
	   }
      return dataset;
   }

   public static void main( String[ ] args ) {
	    ArrayList<Double>[] ret = (ArrayList<Double>[])new ArrayList[1];
	      ret[0] = new ArrayList<Double>();
	      ret[0].add(1.0);ret[0].add(2.0);ret[0].add(3.0);
	      ArrayList<Double>[] ret2 = (ArrayList<Double>[])new ArrayList[1];
	      ret2[0] = new ArrayList<Double>();
	      ret2[0].add(1.0);ret2[0].add(2.0);ret2[0].add(3.0);
      XYLineChart_AWT chart = new XYLineChart_AWT("Browser Usage Statistics",
         "Which Browser are you using?",ret,ret2);
      chart.pack( );          
      RefineryUtilities.centerFrameOnScreen( chart );          
      chart.setVisible( true ); 
   }
}