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
 * Übung 5
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
  
  private double max_diff = 0.0000001;	// indicates the stopping criteria, maximum position difference between iterations
 
  private Semaphore runAlgorithm;
  
  public Type distance = Type.CHEBYSHEV;
	
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
		
	
	switch (distance) {
		case EUCLIDIAN:
			return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
			
		case MANHATTEN:
			return (Math.abs(p1.getX()-p2.getX()) + Math.abs(p1.getY()-p2.getY()));
			
		case WEIGHTED_EUCLIDIAN:
			double weightX = 5, weightY = 1;
			return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2)*weightX + Math.pow(p1.getY() - p2.getY(), 2)*weightY);
			
		case CHEBYSHEV:
			return Math.max(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
			
		case MIN:
			return Math.min(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
			
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
	  	  
	  System.out.println("Distanzberechnung: " + distance);
	  System.out.println("max_diff: " + max_diff);
	  centers = new Point2D[K];
	  Point2D[] centers_prev = new Point2D[K];
	  int[] centers_cnt = new int[K];
	  double[] centers_x = new double[K];
	  double[] centers_y = new double[K];
	  int[] cluster = new int[_points.length];
	  double difference = Double.MAX_VALUE;

	  // create random initial centers
	  for (int i = 0; i < K; i++) {
		  double x = (Math.random() * 1000) % maxX;
		  double y = (Math.random() * 1000) % maxY;
		  centers[i] = new Point2D.Double(x, y);
	  }

	  // variables to check how much the centers changed since the last iteration
	  double c_diff_curr = 0.0, c_diff_prev = 42.0;  
	  
	  int blubb = 0;
	  do {
		  updateClusters(centers);
		  centers_prev = centers.clone();
		  
		  for (int i = 0; i < points.length; i++) {
			  Point2D p = points[i];
			  double min_dist = Double.MAX_VALUE;
			  for (int j = 0; j < K; j++) {
				  double dist = calculateDistance(p, centers[j]);
				  if (dist < min_dist) {
					  min_dist = dist;
					  cluster[i] = j;
				  }
			  }
			  // count the points per cluster
			  centers_x[cluster[i]] += p.getX();
			  centers_y[cluster[i]] += p.getY();
			  centers_cnt[cluster[i]]++;
		  }
		  
		  
		  // set the new centers
		  for (int i = 0; i < K; i++) {

			  // If one cluster has no points, reset randomly on the system
			  if (centers_cnt[i] == 0) {
				  System.out.println("randomly reset center");
				  double x = (Math.random() * 1000) % maxX;
				  double y = (Math.random() * 1000) % maxY;
				  centers[i] = new Point2D.Double(x, y);
			  } else {
				  double x_new = centers_x[i] / centers_cnt[i];
				  double y_new = centers_y[i] / centers_cnt[i];
				  
				  centers[i].setLocation(x_new, y_new);
			  }
			  
			  // TODO: calculate Distortion
			  
			  difference += calculateDistance(centers[i], centers_prev[i]);
		  }
		  
		  difference = difference / K;
		  //System.out.println("Difference: " + difference);
		  blubb++;
	  } while(difference  > max_diff);
	  updateClusters(centers);	
	  System.out.println("Finished");
	  System.out.println("Moving Difference: " + difference);
	  System.out.println("Number of iterations: " + blubb);

	  // cumulate distances of points to their center per cluster
	  double[] dist = new double[K];
	  int[] dist_cnt = new int[K];
	  for (int i = 0; i < points.length; i++) {		  
		  // use euclidian distance for distortion to make it comparable
		  dist[cluster[i]] += points[i].distance(centers[cluster[i]]); 
		  dist_cnt[cluster[i]]++;
	  }
	  // calculate Distortion
	  double distortion = 0.0;
	  for (int i = 0; i < K; i++) {
		  //System.out.println("dist i " + dist[i]);
		  //System.out.println("dist_cnt i " + dist_cnt[i]);
		  distortion += dist[i] / (double)dist_cnt[i];
		  //System.out.println("Distortion: " + distortion);
	  }
	  distortion /= (double)K;
	  System.out.println("Average Distortion per cluster: " + distortion);
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
    //BufferedReader r=new BufferedReader(new FileReader("res/ADR4.csv"));
    BufferedReader r=new BufferedReader(new FileReader("res/lilien.csv"));
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
              doClustering(points,3);
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
