import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 File f = new File("input2.txt");
		 ArrayList <Matrix> data = new ArrayList<Matrix>();
		 ArrayList <Matrix> target = new ArrayList<Matrix>();
         try {
        	 String readLine = "";
			BufferedReader buffer = new BufferedReader(new FileReader(f));
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         ArrayList<Matrix> ret = Utility.minmaxmap(target, -1, 1);
         for(int i = 0;i<ret.size();i++)
         {
        	// ret.get(i).show();
        	// System.out.println();
         }
         //Matrix m = new Matrix(3,1,0.2072);
        // Matrix m1 = new Matrix(1,3,0.1);
        // m1.multiply(m).show();
       //  Matrix ret1 = Utility.tanh(m);
        // ret1.show();
         data.clear();
         target.clear();
         double tmp = (4*Math.PI)/200;
			String listData = "";
			int count = 0;
			for(double i = 0;i<=4*Math.PI;i=i+tmp)
			{
				double temp [][] = {{i}};
				data.add(new Matrix(temp));
				
				
				
				temp = new double [][]{{Math.sin(i)}};
				target.add(new Matrix(temp));
					
				
			}
        /* data = Utility.zscore(data);
         for(int i = 0;i<data.size();i++)
         {
        	 data.get(i).show();
         }
         //target = Utility.minmaxmap(target, -1, 1);
        ANN ne = new ANN(3);
        ne.setTransfer("tanh");
        ne.training(data, target, 0.05);
        Matrix m [] = ne.getW();
        for(int i = 0;i<m.length;i++)
        {
        	m[i].show();
        }*/
			
			double tmp1 = 6*Math.random() - 3;
			double tmp2 = 6*Math.random() - 3;
			
			double tmpMat[][] = new double[][]{{tmp1},{tmp2}};
			Matrix mat1 = new Matrix(tmpMat);
			mat1.show();
			int [] loc = mat1.maxValueLocation();
			System.out.println(loc[0]+","+loc[1]);
	}

}
