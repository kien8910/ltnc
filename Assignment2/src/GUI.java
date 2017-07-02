import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.ui.RefineryUtilities;

public class GUI extends JFrame implements ActionListener {

	JFileChooser fc = new JFileChooser();
	JButton btnLoad = new JButton("Load data");
	JButton btnTrain = new JButton("Training");
	JButton btnTest = new JButton("Test");
	public GUI()
	{
		this.setSize(300,300);
		GridBagLayout layout = new GridBagLayout();

	    this.setLayout(layout); 
		GridBagConstraints gbc = new GridBagConstraints();
		
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.ipady = 10;   
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(0,3,0,0);;
	    this.add(btnLoad,gbc); 
	    gbc.gridx = 1;
	    this.add(btnTrain,gbc);
	    gbc.gridx = 2;
	    gbc.ipady = 10;   
	    this.add(btnTest,gbc);
	    btnLoad.addActionListener( this );
	    //this.add(fileChooser);

	}
	public void actionPerformed(ActionEvent e)
    {
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		 int returnVal = fc.showOpenDialog(GUI.this);
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fc.getSelectedFile();
			    System.out.println("Selected file: " + selectedFile.getName());
			    
			    try {
					BufferedReader buffer;
					buffer = new BufferedReader(new FileReader(selectedFile));
					String readLine = "";
					String t  = "";
					int count = 0;	
					ArrayList <Matrix> dataTrain = new ArrayList<Matrix>();
					ArrayList <Matrix> dataTest = new ArrayList<Matrix>();
					ArrayList <Matrix> targetTrain = new ArrayList<Matrix>();
					ArrayList <Matrix> targetTest = new ArrayList<Matrix>();
					count++;
					while ((readLine = buffer.readLine()) != null) {
						
						double tmp [][] = {{Double.parseDouble(readLine.substring(readLine.lastIndexOf(" ")))}};
						if(count<100)
							targetTrain.add(new Matrix(tmp));
						else
							targetTest.add(new Matrix(tmp));
						
						String str = readLine.substring(0,readLine.lastIndexOf(" ")-1);
						String arr [] = str.split(" ");
						tmp = new double[arr.length][1];
						for(int i = 0;i<tmp.length;i++)
						{
							tmp[i][0] = Double.parseDouble(arr[i]);
						}
						Matrix mat = new Matrix(tmp);
						if(count<100)
							dataTrain.add(mat);
						else
							dataTest.add(mat);
						count++;
						
			         }
					//for(int i = 0;i<10;i++)
					//{
					//	target.get(i).show();
					//}
					ANN neural = new ANN(3);
					neural.training(dataTrain, targetTrain);
					Matrix W [] = neural.getW();
					Matrix b [] = neural.getb();
					ArrayList<Double>[] x = (ArrayList<Double>[])new ArrayList[2];
					ArrayList<Double>[] y = (ArrayList<Double>[])new ArrayList[2];
					for(int i = 0;i<x.length;i++)
					{
						x[i] = new ArrayList<Double>();
						y[i] = new ArrayList<Double>();
					}
					for(int i = 0;i<700;i++)
					{
						//dataTest.get(i).show();
						Matrix mat = dataTest.get(i);
						Matrix a [] = new Matrix[3];
						Matrix z [] = new Matrix[3];
						
						a[0] = mat;
						for(int l = 1; l<3;l++)
						{
							
							z[l] = W[l-1].multiply(a[l-1]).plus(b[l-1]);
							
							a[l] = new Matrix(z[l].getData());
							
						}
						x[0].add(dataTest.get(i).getElement(0, 0));
						y[0].add(a[2].getElement(0, 0));
						x[1].add(dataTest.get(i).getElement(0, 0));
						y[1].add(targetTest.get(i).getElement(0, 0));
						//System.out.println(a[2].getElement(0, 0)+","+targetTest.get(i).getElement(0, 0));
					}
					
					  XYLineChart_AWT chart = new XYLineChart_AWT("Browser Usage Statistics",
						         "Which Browser are you using?",x,y);
						      chart.pack( );          
						      RefineryUtilities.centerFrameOnScreen( chart );          
						      chart.setVisible( true ); 
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    }
	public static void main (String args [])
	{
		GUI g = new GUI();
		g.setVisible(true);
	}
}
