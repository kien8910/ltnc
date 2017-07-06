import java.util.ArrayList;

public class Utility {
	
	public static ArrayList<Matrix> zscore(ArrayList<Matrix> data)
	{
		ArrayList<Matrix> ret = new ArrayList<Matrix>();
		double sum[] = new double[data.get(0).getCol()];
		double average[]=new double[data.get(0).getCol()];
		double standard[]=new double[data.get(0).getCol()];
		
		for(int i = 0;i<sum.length;i++)
			sum[i]=0;
		
		for(int i = 0;i<data.size();i++)
		{
			for(int j = 0;j<data.get(i).getCol();j++)
			{
				sum[j]+=data.get(i).getElement(0, j);
			}
			
		}
		
		for(int i = 0;i<sum.length;i++)
			average[i] = sum[i]/data.size();
		
		for(int i = 0;i<sum.length;i++)
			sum[i]=0;
		for(int i = 0;i<data.size();i++)
		{
			for(int j = 0;j<data.get(i).getCol();j++)
			{
				sum[j]+=(data.get(i).getElement(0, j)-average[j])*(data.get(i).getElement(0, j)-average[j]);
			}
		}
		
		for(int i = 0;i<sum.length;i++)
			standard[i] = Math.sqrt(sum[i]/(data.size()-1));
		
		
		for(int i = 0;i<data.size();i++)
		{
			double tmp [][] = new double[1][data.get(i).getCol()];
			for(int j = 0;j<data.get(i).getCol();j++)
			{
				tmp [0][j] =  (data.get(i).getElement(0, j)-average[j])/standard[j];
				
			}
			
			ret.add(new Matrix(tmp));
		}
		return ret;
	}
	
	public static ArrayList<Matrix> minmax(ArrayList<Matrix> data)
	{
		double [] max =  new double[data.get(0).getCol()];
		ArrayList<Matrix> ret = new ArrayList<Matrix>();
		for(int i = 0;i<data.get(0).getCol();i++)
		{
			max[i] = data.get(0).getElement(0, i);
		}
		for(int i = 0;i<data.size();i++)
		{
			for(int j = 0;j<data.get(i).getCol();j++)
			{
				if(max[j]<data.get(i).getElement(0, j))
				{
					max[j] = data.get(i).getElement(0, j);
				}
			}
			
		}
		
		for(int i = 0;i<data.size();i++)
		{
			double tmp [][] = new double[1][data.get(i).getCol()];
			for(int j = 0;j<data.get(i).getCol();j++)
			{
				tmp [0][j] =  (data.get(i).getElement(0, j))/max[j];
				
			}
			ret.add(new Matrix(tmp));
			
			
		}
		return ret;
	}
	

}
