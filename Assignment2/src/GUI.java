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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.ui.RefineryUtilities;

public class GUI extends JFrame implements ActionListener {

	ArrayList <Matrix> data = new ArrayList<Matrix>();
	ArrayList <Matrix> target = new ArrayList<Matrix>();
	JFileChooser fc = new JFileChooser();
	JButton btnGenerate = new JButton("Generate data");
	JButton btnLoad = new JButton("Load data");
	JButton btnTrain = new JButton("Training");
	JButton btnTest = new JButton("Test");
	JLabel funcLabel = new JLabel("Hàm: ");
	JLabel taskLabel = new JLabel("Tác vụ: ");
	JLabel showLabel = new JLabel("");
	String[] funcStrings = { "sin(x)", "cos(x)", "sin(x)/x", "log(x)"};
	String[] taskStrings = { "Regression","Classification"};

	JComboBox listFunc = new JComboBox(funcStrings);
	JComboBox listTask = new JComboBox(taskStrings);
	
	public GUI()
	{
		this.setSize(500,800);
		
		GridBagLayout layout = new GridBagLayout();
		
	    this.setLayout(layout); //Dinh dang GridBayLayout 
		GridBagConstraints gbc = new GridBagConstraints();
		
	    
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.ipady = 10;
	    gbc.insets = new Insets(0,3,10,0);;
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    this.add(funcLabel);
	    gbc.gridx = 1;
	    this.add(listFunc,gbc); 
	    
	    gbc.gridx = 2;
	    this.add(taskLabel);
	    gbc.gridx = 3;
	    this.add(listTask);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    this.add(btnLoad,gbc); 
	    gbc.gridx = 1;
	    this.add(btnGenerate,gbc); 
	    gbc.gridx = 2;
	    this.add(btnTrain,gbc);
	    gbc.gridx = 3;
	    gbc.ipady = 10;   
	    this.add(btnTest,gbc);
	    gbc.gridx = 0;
	    gbc.gridy = 4;
	    gbc.gridwidth = 5;
	    this.add(showLabel,gbc);
	    btnLoad.addActionListener( this );	//Khai bao su kien click cho button btnLoad
	    btnTrain.addActionListener(this);
	    btnGenerate.addActionListener(this);
	    //this.add(fileChooser);

	}
	public void actionPerformed(ActionEvent e)
    {
		//Xu ly khi click button
		
		if(e.getSource()==btnLoad)
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
					
						//count++;
						while ((readLine = buffer.readLine()) != null) {
							
							double tmp [][] = {{Double.parseDouble(readLine.substring(readLine.lastIndexOf(" ")))}};
							target.add(new Matrix(tmp));
							
							
							String str = readLine.substring(0,readLine.lastIndexOf(" ")-1);
							String arr [] = str.split(" ");
							tmp = new double[arr.length][1];
							for(int i = 0;i<tmp.length;i++)
							{
								tmp[i][0] = Double.parseDouble(arr[i]);
							}
							Matrix mat = new Matrix(tmp);
							data.add(mat);
				         }
						
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		}
		else if(e.getSource()==btnTrain)
		{
			
			ArrayList <Matrix> dataTrain = new ArrayList<Matrix>();
			ArrayList <Matrix> dataTest = new ArrayList<Matrix>();
			ArrayList <Matrix> targetTrain = new ArrayList<Matrix>();
			ArrayList <Matrix> targetTest = new ArrayList<Matrix>();
			if(listTask.getSelectedIndex()==0)
			{
				data = Utility.zscore(data);
				if(listFunc.getSelectedIndex()==4)
				{
					target = Utility.minmaxmap(target, -1,1);
					for(int i = 0;i<data.size();i++)
					{
						if(i<300)
						{
							targetTrain.add(target.get(i));
							dataTrain.add(data.get(i));
						}
						else
						{
							targetTest.add(target.get(i));
							dataTest.add(data.get(i));
						}
					}
				}
				else
				{
					dataTrain = data;
					targetTrain = target;
					dataTest = data;
					targetTest = target;
				}
				//dataTrain = Utility.zscore(dataTrain);
				//targetTrain = Utility.minmaxmap(dataTest, -1,1);
				ANN neural = new ANN(3);
				neural.setTransfer("tanh");
				neural.training(dataTrain, targetTrain,0.9);
				Matrix W [] = neural.getW();
				Matrix b [] = neural.getb();
				ArrayList<Double>[] x = (ArrayList<Double>[])new ArrayList[2];
				ArrayList<Double>[] y = (ArrayList<Double>[])new ArrayList[2];
				for(int i = 0;i<x.length;i++)
				{
					x[i] = new ArrayList<Double>();
					y[i] = new ArrayList<Double>();
				}
				
				//dataTest = Utility.zscore(dataTest);
				//targetTest = Utility.minmaxmap(targetTest, -1, 1);
				for(int i = 0;i<dataTest.size();i++)
				{
					//dataTest.get(i).show();
					Matrix mat = dataTest.get(i);
					Matrix a [] = new Matrix[neural.getLayerNum()];
					Matrix z [] = new Matrix[neural.getLayerNum()];
					
					a[0] = mat;
					for(int l = 1; l<neural.getLayerNum();l++)
					{
						
						z[l] = W[l-1].multiply(a[l-1]).plus(b[l-1]);
						
						a[l] = Utility.tanh(new Matrix(z[l].getData()));
						
					}
					x[0].add(dataTest.get(i).getElement(0, 0));
					y[0].add(a[neural.getLayerNum()-1].getElement(0, 0));
					x[1].add(dataTest.get(i).getElement(0, 0));
					y[1].add(targetTest.get(i).getElement(0, 0));
					//System.out.println(a[2].getElement(0, 0)+","+targetTest.get(i).getElement(0, 0));
				}
				
				  XYLineChart_AWT chart = new XYLineChart_AWT("Browser Usage Statistics",
					         "Which Browser are you using?",x,y);
					      chart.pack( );          
					      RefineryUtilities.centerFrameOnScreen( chart );          
					      chart.setVisible( true ); 
			}
			else
			{
				ANN neural = new ANN(4);
				neural.setTransfer("tanh");
				neural.training(data, target,0.05);
				Matrix W [] = neural.getW();
				Matrix b [] = neural.getb();
				int count = 0;
				for(int i = 0;i<data.size();i++)
				{
					//dataTest.get(i).show();
					Matrix mat = data.get(i);
					Matrix a [] = new Matrix[neural.getLayerNum()];
					Matrix z [] = new Matrix[neural.getLayerNum()];
					
					a[0] = mat;
					for(int l = 1; l<neural.getLayerNum();l++)
					{
						
						z[l] = W[l-1].multiply(a[l-1]).plus(b[l-1]);
						
						a[l] = Utility.tanh(new Matrix(z[l].getData()));
						
					}
					//a[neural.getLayerNum()-1].show();
					//System.out.println();
					
					//target.get(i).show();
					//System.out.println();
					int loc1[] = a[neural.getLayerNum()-1].maxValueLocation();
					int loc2[] = target.get(i).maxValueLocation();
					if(loc1[0]==loc2[0]&&loc1[1]==loc2[1])
					{
						count +=1;
					}
					
					//System.out.println(a[2].getElement(0, 0)+","+targetTest.get(i).getElement(0, 0));
				}
				System.out.println(count);
				
			}
		}
		else if(e.getSource()==btnGenerate)
		{
			String listData = "";
			if(listTask.getSelectedIndex()==0)
			{
				double tmp = (4*Math.PI)/200;
				double start = 0;
				if(listFunc.getSelectedIndex()==2)
				{
					start = Math.PI;
				}
				
				int count = 0;
				for(double i = start;i<4*Math.PI;i=i+tmp)
				{
					double temp [][] = {{i}};
					data.add(new Matrix(temp));
					if(count<20)
					{
						listData+= String.format("%.3g%n",i)+",";
						if(count>0&&count%5==0)
						{
							listData+="<br>";
						}
					}
					
					switch(listFunc.getSelectedIndex())
					{
						case 0:
							temp = new double [][]{{Math.sin(i)}};
							target.add(new Matrix(temp));
							break;
						case 1:
							temp = new double [][]{{Math.cos(i)}};
							target.add(new Matrix(temp));
							break;
						case 2:
							temp = new double [][]{{Math.sin(i)/i}};
							target.add(new Matrix(temp));
							break;
					}
					count++;
				}
				listData+="...</html>";
				listData = "<html>"+listData;
				showLabel.setText(listData);
				//System.out.println("fasfasf");
				for(int i = 0;i<target.size();i++)
				{
					target.get(i).show();
					System.out.println();
				}
			}
			else
			{
				double maxValue=3;
				double minValue = -3;
				for(int i = 0;i<30;i++)
				{
					double tmp1 = (maxValue-minValue)*Math.random() + minValue;
					double tmp2 = (maxValue-minValue)*Math.random() + minValue;
					
					double tmpMat[][] = new double[][]{{tmp1},{tmp2}};
					
					data.add(new Matrix(tmpMat));
					listData+= "("+String.format("%.3g%n",tmp1)+","+String.format("%.3g%n",tmp2)+")";
					tmp1 = Math.random();
					listData+= "_C:"+Math.round(tmp1)+",";
					if(i%5==0&&i>0)
					{
						listData+="<br";
					}
					if(Math.round(tmp1)==0)
					{
						tmpMat = new double[][]{{1},{0}};
						target.add(new Matrix(tmpMat));
					}
					else
					{
						tmpMat = new double[][]{{0},{1}};
						target.add(new Matrix(tmpMat));
					}	
					
				}
				listData+="...</html>";
				listData = "<html>"+listData;
				showLabel.setText(listData);
				//System.out.println(listData);
				/*for(int i = 0;i<data.size();i++)
				{
					target.get(i).show();
					System.out.println();
				}*/
			}
		}
    }
	public static void main (String args [])
	{
		GUI g = new GUI();
		g.setVisible(true);
	}
}
