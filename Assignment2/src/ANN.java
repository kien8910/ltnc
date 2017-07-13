import java.util.ArrayList;

public class ANN {
	int layer;
	Matrix W [];
	Matrix b [];
	String transfer="";
	public ANN(int layer)
	{
		this.layer = layer;
	}
	public Matrix [] getW()
	{
		return W;
	}
	public Matrix [] getb()
	{
		return b;
	}
	
	public int getLayerNum()
	{
		return this.layer;
	}
	public void setTransfer(String name)
	{
		this.transfer = name;
	}
	
	private Matrix sigmoid(Matrix m)
	{
		int row = m.getRow();
		int col = m.getCol();
		double [][] tmp = new double[row][col];
	
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				tmp[i][j] = 1.0/(1+Math.exp(-m.getElement(i, j)));
			}
		}
		Matrix ret = new Matrix(tmp);
		return ret;
	}
	//dao ham sigmoid
	private Matrix d_sigmoid(Matrix m)
	{
		int row = m.getRow();
		int col = m.getCol();
		double [][] tmp = new double[row][col];
	
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				tmp[i][j] = m.getElement(i, j)*(1-m.getElement(i,j));
			}
		}
		Matrix ret = new Matrix(tmp);
		return ret;
	}
	
	
	private Matrix tanh(Matrix m)
	{
		int row = m.getRow();
		int col = m.getCol();
		double [][] tmp = new double[row][col];
	
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				tmp[i][j] = (Math.exp(m.getElement(i,j))-Math.exp(-m.getElement(i,j)))/(Math.exp(m.getElement(i,j))+Math.exp(-m.getElement(i,j)));
			}
		}
		Matrix ret = new Matrix(tmp);
		return ret;
	}
	
	//dao ham tanh
	private Matrix d_tanh(Matrix m)
	{
		int row = m.getRow();
		int col = m.getCol();
		double [][] tmp = new double[row][col];
	
		for(int i = 0;i<row;i++)
		{
			for(int j = 0;j<col;j++)
			{
				tmp[i][j] = (1-m.getElement(i,j)*m.getElement(i,j));
			}
		}
		Matrix ret = new Matrix(tmp);
		return ret;
	}
	
	private Matrix d_uniform(Matrix m)
	{
		int row = m.getRow();
		int col = m.getCol();
		Matrix ret = new Matrix(row,col,1);
		return ret;
	}
	
	private Matrix calculateTransfer(Matrix m, String transferFunction)
	{
		Matrix ret;
		switch(transferFunction)
		{
			case "sigmoid":
				ret = sigmoid(m);
				break;
			case "tanh":
				ret = tanh(m);
				break;
			default:
				ret = m; 
		}
		return ret;
		
	}
	
	private Matrix calculate_d_Transfer(Matrix m, String transferFunction)
	{
		Matrix ret;
		switch(transferFunction)
		{
			case "sigmoid":
				ret = d_sigmoid(m);
				break;
			case "tanh":
				ret = d_tanh(m);
				break;
			default:
				ret = d_uniform(m); 
		}
		return ret;
		
	}
	
	public void training(ArrayList<Matrix> data, ArrayList<Matrix> target, double learningRate)
	{
		int nodeIn = data.get(0).getRow();
		int nodeOut = target.get(0).getRow();
		int [] numNode = new int[this.layer];
		if(this.layer==4)
		{
			numNode= new int []{nodeIn,8,3,nodeOut};
		}
		else
		{
			numNode= new int []{nodeIn,3,nodeOut};
		}
		int L = this.layer;
	
		Matrix W [] = new Matrix[L-1];
		Matrix deltaW [] = new Matrix[L-1]; 
		Matrix b [] = new Matrix[L-1];
		Matrix delta_b [] = new Matrix[L-1];
		Matrix a [] = new Matrix[L];
		Matrix z [] = new Matrix[L];
		Matrix g [] = new Matrix [L];
		Matrix d [] = new Matrix [L];
		Matrix dW [] = new Matrix [L];
		Matrix db [] = new Matrix [L];
		
		for(int i = 0; i<L-1;i++)
		{
			Matrix tmp = new Matrix(numNode[i+1],numNode[i],0.1);
			Matrix tmp2 = new Matrix(numNode[i+1],1,0.1);
			
			W[i]= Matrix.random(numNode[i+1],numNode[i]).multiplyNumber(0.2);
			
			deltaW[i] = Matrix.random(numNode[i+1],numNode[i]);
			//tmp = randomMatrix(numNode[i+1],1);
			b[i] = Matrix.random(numNode[i+1],1).multiplyNumber(0.2);
			delta_b[i] = Matrix.random(numNode[i+1],1);
		
		}
		
		
		for(int p = 1;p<=30000;p++)
		{
			double square = 0, sum = 0;
			for(int l=0;l<L-1;l++)
			{
				deltaW[l] = new Matrix(deltaW[l].getRow(),deltaW[l].getCol()); //khoi tao = 0;
				delta_b[l] = new Matrix(delta_b[l].getRow(),delta_b[l].getCol()); //khoi tao = 0;
			}
			for(int i = 0;i<data.size();i++)
			{
				
				Matrix mat = data.get(i);
				a[0] = mat;
				for(int l = 1; l<L;l++)
				{
					
					z[l] = W[l-1].multiply(a[l-1]).plus(b[l-1]);
					//z[l].show();
					a[l] = this.calculateTransfer(z[l], this.transfer); //new Matrix(z[l].getData());
		
				}
				
				
				
				mat = target.get(i);
				g[L-1] = a[L-1].minus(mat);
				d[L-1] = this.calculate_d_Transfer(a[L-1], this.transfer);//new Matrix(numNode[L-1],1,1);
				//square += g[L-1].multiply(g[L-1]).getElement(0, 0);
				sum+= Math.abs(g[L-1].getElement(0, 0));
				
				for(int l=L-2;l>=0;l--)
				{
					dW[l] = g[l+1].hadamard(d[l+1]).multiply(a[l].transpose());
					db[l] = g[l+1].hadamard(d[l+1]);
				
					g[l] = W[l].transpose().multiply(g[l+1].hadamard(d[l+1]));
					d[l] = this.calculate_d_Transfer(a[l], this.transfer);//new Matrix(numNode[l],1,1);

				}
				
				for (int l = 0;l<L-1;l++)
				{
					deltaW[l] = deltaW[l].plus(dW[l]);
					delta_b[l] = delta_b[l].plus(db[l]);
				}
			
			}
		
			for (int l = 0;l<L-1;l++)
			{
				deltaW[l] = deltaW[l].multiplyNumber(1.0/data.size());
				delta_b[l]= delta_b[l].multiplyNumber(1.0/data.size());
				
				W[l] = W[l].minus(deltaW[l].multiplyNumber(learningRate));
				b[l] = b[l].minus(delta_b[l].multiplyNumber(learningRate));
				
			}
			
			//System.out.println("square:"+Math.sqrt(square));
			//System.out.println("sum:"+sum);
			
		}
		//data.get(0).show();
		this.W = W;
		this.b = b;
		/*for(int i = 300;i<321;i++)
		{
			
			Matrix mat = data.get(i);
			a[0] = mat;
			for(int l = 1; l<L;l++)
			{
				
				z[l] = W[l-1].multiply(a[l-1]).plus(b[l-1]);
				
				a[l] = new Matrix(z[l].getData());
				
			}
			System.out.println(a[L-1].getElement(0, 0)+","+target.get(i).getElement(0, 0));
			
		}*/
	
		
	}
}
