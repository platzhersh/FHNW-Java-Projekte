package ch.fhnw.igs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**********************************************************************
 * �bung 5
 * 
 * Implementieren Sie die Methode doClustering(...).
 * sowie die funktion calculateDistance(...)
 */

class ClusteringVis extends JFrame
{
  private static final long serialVersionUID=672162827975717219L;
  private final Point2D[] points;
  private Point2D[] centers;
  private double minX;
  private double maxX;
  private double minY;
  private double maxY;
 
  private Semaphore runAlgorithm;
  
  public Type distance = Type.EUCLIDIAN;
	
	public enum Type {
		EUCLIDIAN,
		MANHATTEN,
		WEIGHTED_EUCLIDIAN,
		CHEBYSHEV,
		MIN,
		IRIS,
		NON_NORM
	};
  
  private double calculateDistance(Point2D p1, Point2D p2)
  {
	  
	  if (p1 == null || p2 == null) return -1;
	  
	  /*********************
	     * Insert your code here
	     * 
	     * 
	     * Args:
	     *   p1: Point1
	     *   p2: Point2
	     *
	     * Returns:
	     *   a distance between p1 and p2 (euklid,manhatten ...)
	     */
	
	System.out.println(distance);
	
	
	switch (distance) {
		case EUCLIDIAN:
			return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
			
		case MANHATTEN:
			
		case WEIGHTED_EUCLIDIAN:
			
		case CHEBYSHEV:
			
		case MIN:
			
		case IRIS:
			
		case NON_NORM:
			
		default:
			throw new RuntimeException("This method to be implemented.");
		} 
	    
  }
  
  public void doClustering(final Point2D[] _points,  int K) throws Exception
  {
	  /*********************
	     * Insert your code here
	     * 
	     * The method can call updateClusters(Point2D[]) multiple times to animate the
	     * process of forming clusters.
	     * 
	     * Args:
	     *   _points: A list of the samples to be clustered
	     *   K: The number of clusters to be used
	     *
	     * Returns:
	     *   This method shouldn't return anything. Instead call updateClusters(Point2D[])
	     *   with the calculated clusters at the end.
	     */
	  
	  	updateClusters(_points);
	  
	    //throw new RuntimeException("This method to be implemented.");
  }
  
  public static void main(String[] _args) throws Exception
  {
    new ClusteringVis();
  }
  
  public ClusteringVis() throws Exception
  {
    super("Clustering");
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500,500);
    
    runAlgorithm=new Semaphore(0);
    
    minX=Double.POSITIVE_INFINITY;
    maxX=Double.NEGATIVE_INFINITY;
    minY=Double.POSITIVE_INFINITY;
    maxY=Double.NEGATIVE_INFINITY;
    
    LinkedList<Point2D> points_list=new LinkedList<Point2D>();
    BufferedReader r=new BufferedReader(new FileReader("res/ADR4.csv"));
    String l=r.readLine(); //read over header
    while((l=r.readLine())!=null)
    {
      String[] items=l.split(",");
      Point2D npf=new Point2D.Double(
          Double.parseDouble(items[0]),
          Double.parseDouble(items[1])
        );
      points_list.add(npf);
      
      if(npf.getX()<minX)
        minX=npf.getX();
      if(npf.getX()>maxX)
        maxX=npf.getX();
      if(npf.getY()<minY)
        minY=npf.getY();
      if(npf.getY()>maxY)
        maxY=npf.getY();
    }
    r.close();
    
    points=points_list.toArray(new Point2D.Double[0]);
    centers=null;
    
    Thread t=new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            for(;;runAlgorithm.acquire())
              doClustering(points,7);
          }
          catch(Exception _e)
          {
            _e.printStackTrace();
          }
        }
      });
    t.setDaemon(true);
    t.start();
    
    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        runAlgorithm.release();
      }
    });
  }
  
 
  
  private double calculateDistance(Point2D p1, double p2x, double p2y)
  {
	  return calculateDistance(p1,new Point2D.Double(p2x,p2y));
  }
  
  @Override
  public synchronized void paint(Graphics _g)
  {
    int w=getWidth();
    int h=getHeight();
    
    BufferedImage bi=new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
    
    if(centers!=null)
    {
      int[] colors=new int[centers.length];
      Random r=new Random(7);
      for(int i=0;i<colors.length;i++)
        colors[i]=0xff000000+r.nextInt();
      
      double yd=minY;
      for(int y=0;y<h;y++)
      {
        double xd=minX;
        for(int x=0;x<w;x++)
        {
          int minDistIdx=0;
          double minDist=calculateDistance(centers[0],xd,yd);
                  
          
          for(int i=1;i<centers.length;i++)
          {
            double curDist=calculateDistance(centers[i],xd,yd);
            if(curDist<minDist)
            {
              minDist=curDist;
              minDistIdx=i;
            }
          }
          
          bi.setRGB(x,y,colors[minDistIdx]);
          xd+=(maxX-minX)/w;
        }
        yd+=(maxY-minY)/h;
      }
    }
      
    if(points!=null)
      for(Point2D pf:points)
      {
        int x=(int)((pf.getX()-minX)/(maxX-minX)*(w-1));
        int y=(int)((pf.getY()-minY)/(maxY-minY)*(h-1));
        
        bi.setRGB(x,y,0);
        if(x>0)
          bi.setRGB(x-1,y,0);
        if(x<w-1)
          bi.setRGB(x+1,y,0);
        if(y>0)
          bi.setRGB(x,y-1,0);
        if(y<h-1)
          bi.setRGB(x,y+1,0);
      }
    
    if(centers!=null)
    {
      Graphics g=bi.getGraphics();
      g.setColor(Color.WHITE);
      for(Point2D pf:centers)
        g.fillOval(
            (int)((pf.getX()-minX)/(maxX-minX)*(w-1))-2,
            (int)((pf.getY()-minY)/(maxY-minY)*(h-1))-2,5,5);
    }
    
    _g.drawImage(bi,0,0,null);
  }
  
  public synchronized void updateClusters(Point2D[] _centers)
  {
    centers=new Point2D[_centers.length];
    for(int i=0;i<centers.length;i++)
      centers[i]=(Point2D)_centers[i].clone();
    
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        repaint();
      }
    });
  }
}
