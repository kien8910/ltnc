import java.util.ArrayList;

public class ANN {
	int layer;
	Matrix W [];
	Matrix b [];
	
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
	public void training(ArrayList<Matrix> data, ArrayList<Matrix> target)
	{
		int nodeIn = data.get(0).getCol();
		int nodeOut = target.get(0).getCol();
		int [] numNode  = {nodeIn,3,nodeOut};
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
			W[i]= Matrix.random(numNode[i+1],numNode[i]);
			
			deltaW[i] = Matrix.random(numNode[i+1],numNode[i]);
			//tmp = randomMatrix(numNode[i+1],1);
			b[i] = Matrix.random(numNode[i+1],1);
			delta_b[i] = Matrix.random(numNode[i+1],1);
		}
		
		for(int p = 1;p<=150;p++)
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
					
					a[l] = new Matrix(z[l].getData());
					
				}
				
				
				mat = target.get(i);
				g[L-1] = a[L-1].minus(mat);
				d[L-1] = new Matrix(numNode[L-1],1,1);
				square += g[L-1].multiply(g[L-1]).getElement(0, 0);
				sum+= Math.abs(g[L-1].getElement(0, 0));
				for(int l=L-2;l>=0;l--)
				{
					dW[l] = g[l+1].hadamard(d[l+1]).multiply(a[l].transpose());
					db[l] = g[l+1].hadamard(d[l+1]);
				
					g[l] = W[l].transpose().multiply(g[l+1].hadamard(d[l+1]));
					d[l] = new Matrix(numNode[l],1,1);

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
				
				W[l] = W[l].minus(deltaW[l].multiplyNumber(0.00001));
				b[l] = b[l].minus(delta_b[l].multiplyNumber(0.00001));
				
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
