import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Boundary extends JPanel {
 int color [][];
 int data [][];
 public Boundary(int [][]color, int [][]data)
 {
	 this.color = color;
	 this.data = data;
 }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    
	int count = 0;
	for(int i = 0;i<color.length;i++)
	{
		for(int j = 0;j<color[0].length;j++)
		{
			if(color[i][j] == 1)
			{
				count++;
				g2d.setColor(Color.red);
				g2d.fillOval(i, j, 5,5);
			}
			else if(color[i][j]==2)
			{
				count++;
				g2d.setColor(Color.green);
				g2d.fillOval(i, j, 5,5);
			}
			
		}
	}

	g2d.setColor(Color.black);
    Dimension size = getSize();
    int w = 300;//size.width;
    int h = 300;//size.height;
    g2d.setStroke(new BasicStroke(1));
   
	g2d.drawLine(w/2, 0, w/2, h);
	g2d.drawLine(0, h/2, w, h/2);
	
	for(int i = 0;i<data.length;i++)
	{
		for(int j = 0;j<data[0].length;j++)
		{
			if(data[i][j] == 1)
			{
				count++;
				g2d.setColor(Color.blue);
				g2d.fillOval(i, j, 10,10);
			}
			else if(data[i][j]==2)
			{
				count++;
				g2d.setColor(Color.pink);
				g2d.fillOval(i, j, 10,10);
			}
			
		}
	}
	//g2d.setColor(Color.red);
	/*double maxValue=3;
	double minValue = -3;
	int r = 5;
	
	for(int i = 0;i<30;i++)
	{
		double tmp1 = (maxValue-minValue)*Math.random() + minValue+3;
		double tmp2 = (maxValue-minValue)*Math.random() + minValue+3;
		//System.out.println(tmp1*50);
		//System.out.println(tmp2*50);
		
		g2d.fillOval((int)(tmp1*50)-r, (int)(tmp2*50)-r, r*2,r*2);
	}*/
	//g2d.setColor(Color.green);
	/*for(int i = 0;i<30;i++)
	{
		double tmp1 = (maxValue-minValue)*Math.random() + minValue+3;
		double tmp2 = (maxValue-minValue)*Math.random() + minValue+3;
		
		System.out.println(tmp1);
		System.out.println(tmp2);
		
		g2d.fillOval((int)(tmp1*50)-r, (int)(tmp2*50)-r, r*2,r*2);
	}*/
   // g2d.fillOval(30-5, 20-5, 10, 10);
    //g2d.fillOval(42, 20, 10, 10);
   // g2d.drawLine(30, 20, 30, 20);
   /* for (int i = 0; i <= 100000; i++) {
      Dimension size = getSize();
      int w = size.width ;
      int h = size.height;

      Random r = new Random();
      int x = Math.abs(r.nextInt()) % w;
      int y = Math.abs(r.nextInt()) % h;
      g2d.drawLine(x, y, x, y);
    }*/
  }

  public static void main(String[] args) {
	 int data [][] = new int [300][300];
	 data[20][20]=1;
	 data[30][30]=1;
	 data[121][50]=2;
	 data[200][100]=2;
    Boundary points = new Boundary(data,data);
    JFrame frame = new JFrame("Points");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(points);
    frame.setSize(300, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}