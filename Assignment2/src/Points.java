import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Points extends JPanel {

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.black);
    Dimension size = getSize();
    int w = size.width ;
    int h = size.height;
    g2d.setStroke(new BasicStroke(1));
   
	g2d.drawLine(w/2, 0, w/2, h);
	g2d.drawLine(0, h/2, w, h/2);
	
	g2d.setColor(Color.red);
	double maxValue=7;
	double minValue = 1;
	int r = 5;
	for(int i = 0;i<30;i++)
	{
		int tmp1 = (int)((maxValue-minValue)*Math.random() + minValue);
		int tmp2 = (int)((maxValue-minValue)*Math.random() + minValue);
		//System.out.println(tmp1);
		//System.out.println(tmp2);
		
		g2d.fillOval(tmp1*50-r, tmp2*50-r, r*2,r*2);
	}
	g2d.setColor(Color.green);
	for(int i = 0;i<30;i++)
	{
		int tmp1 = (int)((maxValue-minValue)*Math.random() + minValue);
		int tmp2 = (int)((maxValue-minValue)*Math.random() + minValue);
		//System.out.println(tmp1);
		//System.out.println(tmp2);
		
		g2d.fillOval(tmp1*50-r, tmp2*50-r, r*2,r*2);
	}
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
    Points points = new Points();
    JFrame frame = new JFrame("Points");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(points);
    frame.setSize(300, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}