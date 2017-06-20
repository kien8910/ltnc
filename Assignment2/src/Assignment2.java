import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Assignment2 {
	public static void createData()
	{
		 PrintWriter writer;
			try {
				writer = new PrintWriter("input.txt", "UTF-8");
				  for(int i =0;i<20;i++)
			         {
					  writer.print(String.format("%d", i));
			 	        writer.print(String.format("%10.3f", Math.sin(i)));
			         
			         	writer.println();
			         }
			         writer.close();	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void printMatrix(double [][] m)
	{
		int row = m.length;
		int col = m[0].length;
		
		for(int i = 0;i<row;i++)
		{
			for(int j = 0; j<col;j++)
			{
				System.out.print(String.format("%10.3f",m[i][j]));
			}
			System.out.println();
		}
	}
	
	public static double [][] randomMatrix(int row, int col)
	{
		double [][] ret = new double[row][col];
		
		for(int i = 0; i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				ret[i][j] = Math.random() - 1;
			}
		}
		return ret;
	}
	public static double [][] addMatrix(double[][] m1, double [][] m2)
	{
		int row = m1.length;
		int col = m1[0].length;
		double ret[][] = new double [row][col];
		
		if(m1.length == m2.length && m1[0].length==m2[0].length)
		{
			for(int i = 0; i<row; i++)
			{
				for(int j=0;j<col;j++)
				{
					ret[i][j] = m1[i][j]+m2[i][j];
				}
			}
		}
		else
		{
			System.out.println("Error add matrix");
		}
		return ret;
	}
	public static double [][] subtractMatrix(double[][] m1, double [][] m2)
	{
		int row = m1.length;
		int col = m1[0].length;
		double ret[][] = new double [row][col];
		
		if(m1.length == m2.length && m1[0].length==m2[0].length)
		{
			for(int i = 0; i<row; i++)
			{
				for(int j=0;j<col;j++)
				{
					ret[i][j] = m1[i][j]-m2[i][j];
				}
			}
		}
		else
		{
			System.out.println("Error add matrix");
		}
		return ret;
	}
	public static double [][] mulMatrix(double[][] m1, double [][] m2)
	{
		int row = m1.length;
		int col = m2[0].length;
		double ret[][] = new double [row][col];
		
		if(m1[0].length==m2.length)
		{
			for(int i = 0; i<row; i++)
			{
				for(int j=0;j<col;j++)
				{
					ret[i][j] = 0;
					for(int k = 0;k<m1[0].length;k++)
					{
						ret[i][j]+=m1[i][k]*m2[k][j];
					}
					
				}
			}
		}
		else
		{
			System.out.println("Error multiply matrix");
		}
		return ret;
	}
	
	public static double [][] mulMatrixWithNumber(double n, double [][] m)
	{
		int row = m.length;
		int col = m[0].length;
		double ret[][] = new double [row][col];
		
		for(int i = 0; i<row; i++)
		{
			for(int j=0;j<col;j++)
			{
				ret[i][j] = m[i][j]*n;				
			}
		}
		
		return ret;
	}
	
	
	public static double [][] hadamard(double [][] v1, double [][] v2)
	{
		int row = v1.length;
		
		double ret[][] = new double [row][1];
		
		if(v1.length==v2.length)
		{
			for(int i = 0; i<row; i++)
			{
				ret[i][0] = v1[i][0]*v2[i][0];
			}
		}
		else
		{
			System.out.println("Error hadamard");
		}
		return ret;
	}
	
	public static double[][] transpose(double [][] m)
	{
		int row = m.length;
		int col = m[0].length;
		double ret[][] = new double [col][row];
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				ret[j][i] = m[i][j];
			}
		}
		return ret;
	}
	public static double[][] transferFunction(double [][]x)
	{
		int row = x.length;
		int col = x[0].length;
		
		double ret [][] = new double [row][col];
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				ret[i][j] = 1/(1+Math.exp(-x[i][j]));
			}
		}
		return ret;
	}
	
	public static double[][] calculate_d(double [][]a)
	{
		int row = a.length;
		
		double ret [][] = new double [row][1];
		for(int i = 0;i<row;i++)
		{
			ret[i][0] = a[i][0]*(1-a[i][0]);
		}
		return ret;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [] numNode  = {1,3,1};
		int L = 3;
		ArrayList<Double> target = new ArrayList<Double>();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<double[][]> W = new ArrayList<double[][]>();
		ArrayList<double[][]> b = new ArrayList<double[][]>();
		ArrayList<double[][]> a = new ArrayList<double[][]>();
		ArrayList<double[][]> z = new ArrayList<double[][]>();
		ArrayList<double[][]> g = new ArrayList<double[][]>();
		ArrayList<double[][]> d = new ArrayList<double[][]>();
		ArrayList<double[][]> dW = new ArrayList<double[][]>();
		ArrayList<double[][]> db = new ArrayList<double[][]>();
		ArrayList<double[][]> deltaW = new ArrayList<double[][]>();
		ArrayList<double[][]> delta_b = new ArrayList<double[][]>();
		//double [][] t1= {{1,1,1},{0,0,0}};
		//t.add(t1);
		 File f = new File("input2.txt");

       
		try {
			BufferedReader buffer;
			buffer = new BufferedReader(new FileReader(f));
			String readLine = "";
			String t  = "";
			int count = 0;	
			while ((readLine = buffer.readLine()) != null) {
             	x.add(Double.parseDouble(readLine.substring(0,readLine.indexOf(' '))));
             	target.add(Double.parseDouble(readLine.substring(readLine.lastIndexOf(" "))));
             
	         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
		
		for(int i = 0; i<L-1;i++)
		{
			double [][] tmp = randomMatrix(numNode[i+1],numNode[i]);
			W.add(tmp);
			tmp = new double [numNode[i+1]][numNode[i]];
			deltaW.add(tmp);
			tmp = randomMatrix(numNode[i+1],1);
			b.add(tmp);
			tmp = new double [numNode[i+1]][1];
			delta_b.add(tmp);
		}
		for(int i = 0; i<L;i++)
		{
			double [][] tmp;
			
			tmp = randomMatrix(numNode[i],1);
			a.add(tmp);
			z.add(tmp);
			g.add(tmp);
			d.add(tmp);
			dW.add(tmp);
			db.add(tmp);
		}
		
		//double [][] tmp = randomMatrix(2,3);
		for(int i = 0;i<b.size();i++)
		{
			//printMatrix(b.get(i));
			//System.out.println();
		}
		for(int p = 0; p<20;p++)
		{
			for(int i = 0;i<x.size();i++)
			{
				double tmp [][] = {{x.get(i)}};
				a.set(0,tmp);
				
				for(int l = 1; l<L;l++)
				{
					//printMatrix(b.get(l-1));
					//printMatrix(addMatrix(mulMatrix(W.get(l-1),a.get(l-1)),b.get(l-1)));
					//System.out.println();
					z.set(l,addMatrix(mulMatrix(W.get(l-1),a.get(l-1)),b.get(l-1)));
					a.set(l, transferFunction(z.get(l)));
				}
				double tmp2 [][] = {{target.get(i)}};
				g.set(L-1, subtractMatrix(a.get(L-1),tmp2));
				d.set(L-1, calculate_d(a.get(L-1)));
				for(int l=L-2;l>=0;l--)
				{
					dW.set(l,mulMatrix(hadamard(g.get(l+1),d.get(l+1)),transpose(a.get(l))));
					db.set(l, hadamard(g.get(l+1),d.get(l+1)));
					g.set(l, mulMatrix(transpose(W.get(l)),hadamard(g.get(l+1),d.get(l+1))));
					d.set(l,calculate_d(a.get(l)));
				}
				
				for (int l = 0;l<L-1;l++)
				{
					deltaW.set(l, addMatrix(deltaW.get(l),dW.get(l)));
					delta_b.set(l, addMatrix(delta_b.get(l),db.get(l)));
				}
				
			}
			
			for (int l = 0;l<L-1;l++)
			{
				double [][] params1 = mulMatrixWithNumber(1/x.size(),deltaW.get(l));
				double [][] params2 = mulMatrixWithNumber(0.9,W.get(l));
				deltaW.set(l, addMatrix(params1,params2));
				delta_b.set(l, mulMatrixWithNumber(1/x.size(),db.get(l)));
				params1 = mulMatrixWithNumber(0.9,deltaW.get(l));
				W.set(l, subtractMatrix(W.get(l),params1));
				params1 = mulMatrixWithNumber(0.9,delta_b.get(l));
				b.set(l, subtractMatrix(b.get(l),params1));
			}
		
		}
		for (int l = 0;l<L-1;l++)
		{
			printMatrix(W.get(l));
			//System.out.println();
			//printMatrix(b.get(l));
			//System.out.println();
		}
		//double [][] m1 = {{1,2,3},{4,5,6}};
		//double [][] m2 = {{0,1000},{1,100},{0,10}};
		//double[][] ret = mulMatrix(m1,m2);
		//double [][] tmp = new double [5][2];
		//printMatrix(tmp);
	}

}
