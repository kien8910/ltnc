import org.jfree.chart.ChartPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

   public LineChart_AWT( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Years","Number of Schools",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      File f = new File("input.txt");

      try
      {
    	  BufferedReader buffer = new BufferedReader(new FileReader(f));

          String readLine = "";
          String t  = "";
          int count = 0;	
          while ((readLine = buffer.readLine()) != null) {
              
        	  dataset.addValue( Double.parseDouble(readLine.substring(readLine.lastIndexOf(" "))) , "y" , String.valueOf(Double.parseDouble(readLine.substring(0,readLine.indexOf(' ')))));
        	  count++;
        	  if(count==30)
        	  {
        		  break;
        	  }
            
          }
      }
      catch(Exception e)
      {
    	  
      }
     
     
      return dataset;
   }
   
   public static void main( String[ ] args ) {
      LineChart_AWT chart = new LineChart_AWT(
         "School Vs Years" ,
         "Numer of Schools vs years");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}