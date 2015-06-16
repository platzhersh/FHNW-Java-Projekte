package ch.fhnw.igs.cbir;

/**
 * Extract and display Scale Invariant Features after the method of David Lowe
 * \cite{Lowe04} in an image.
 * 
 * BibTeX:
 * <pre>
 * &#64;article{Lowe04,
 *   author    = {David G. Lowe},
 *   title     = {Distinctive Image Features from Scale-Invariant Keypoints},
 *   journal   = {International Journal of Computer Vision},
 *   year      = {2004},
 *   volume    = {60},
 *   number    = {2},
 *   pages     = {91--110},
 * }
 * </pre>
*/


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mpi.cbg.fly.Feature;
import mpi.cbg.fly.Filter;
import mpi.cbg.fly.FloatArray2D;
import mpi.cbg.fly.FloatArray2DSIFT;
import ch.fhnw.igs.cbir.classifier.MyClassifier;


@SuppressWarnings("serial")
public class CbirWithSift extends JFrame
{
	//helper variables for the repaint
	IgsImage cur_image;
	
	//the extracted visual words - model for the VisualWordHistogram 
	List<VisualWord> bagofwords = new Vector<VisualWord>();
	
	//how many visual words should be classified
	private static int K = 300;
	
	//the minimum count of members in a "visual-word" class
	private static int MIN_CLASS_SIZE = 5;
	
	private static final boolean CHOOSE_IMAGES_RANDOMLY = true;
	
	//private static final String TRAINING_DIR = "Training";
	//private static final String TEST_DIR = "Test";
	
	private static final String TRAINING_DIR = "Training2";
	private static final String TEST_DIR = "Test2";
	
	//how many images should be read from the input folders
	private static int readImages = 50;
	
	//number of SIFT iterations: more steps will produce more features 
	//default = 4
	private static int steps = 5;
	
	//for testing: delay time for showing images in the GUI
	private static int wait = 0;
	
	//maximal distance for a Feature to still qualify as "close" to the next centroid
	private static int MAX_DIST = Integer.MAX_VALUE;
	
	/**
	 * 
	 * IMPLEMENT THIS METHOD
	 *  
	 * 
	 * Classifies a SIFT-Feature Vector into a VisualWord Class by finding the nearest visual word in the bagofwords "space" 
	 * @param f a SIFT feature
	 * @return the class ID (0..k) or null if quality is not good enough   
	 */
	public Integer doClassifyVisualWord(Feature f)
	{
		if(bagofwords==null || f==null || bagofwords.isEmpty()) return null;
		float dist = MAX_DIST;
		int index = -1;
		for (VisualWord w : bagofwords) {
			if(f.descriptorDistance(w.centroied) < dist){
				index = w.classID;
				dist = f.descriptorDistance(w.centroied);
			}
		}
		if(index == -1)
			return null;
		return index;
	}
	 /**
	  * 
	  * 
	  * IMPLEMENT THIS METHOD
	  * 
	  * 
	  * a k-mean clustering implementation for SIFT-Features: (float[] : Feature.descriptor) 
	  * 
	  * @param _points a list of all found features in the training set
	  * @param K how many classes (visual words)
	  * @param minCount the minimum number of members in each class 
	  * @return the centroides of the k-mean = visual words list
	  */
	 public static List<VisualWord> doClusteringVisualWords(final Feature[] _points,  int K, int minCount)
	  {
		 System.out.println("Start clustering with: "+_points.length+" pkt to "+K+" classes"); 
		 
		 final int MAX_NR_OF_RECREATIONS = 0;
		 LinkedList<Mean> means = new LinkedList<Mean>();
		 
		 for (int k=0; k<K; k++) {
			 Feature f = _points[new Random().nextInt(_points.length)];
			 means.add(new Mean(f.descriptor));
		 }
		 
		 int nrOfRecreations = 100;
		 // calculate clusters
		 while (stillMoving(means)) {
			 for (Feature f : _points) {
				 Mean closestMean = means.getFirst();
				 float minDistance = Float.MAX_VALUE;
				 for (Mean m : means) {
					 float d = calculateEuclidianDistance(f, m);
					 if (d < minDistance) {
						 minDistance = d;
						 closestMean = m;
					 }
				 }
				 closestMean.addFeature(f);
			 }

			 for (Mean m : means) {
				 if (m.getNrOfFeatures() < minCount && (++nrOfRecreations) < MAX_NR_OF_RECREATIONS) {
					 Feature f = _points[new Random().nextInt(_points.length)];
					 m.setCoordinates(f.descriptor);
				 } else {
					 m.calculateNewPosition();
				 }
			 }
		 }

		 // create resultset
		 List<VisualWord> meansResultSet = new LinkedList<VisualWord>();
		 int c = 0;
		 for (Mean m : means) {
			 if (m.getNrOfFeatures() >= minCount) {
				 VisualWord vw = new VisualWord();
				 vw.classID = c++;
				 Feature f = new Feature();
				 f.descriptor = m.getCoordinates();
				 vw.centroied = f;
				 meansResultSet.add(vw);
			 }
		 }
		 return meansResultSet;
	  }


	 private static boolean stillMoving(LinkedList<Mean> means) {
		 for (Mean m : means) {
			 if (m.moved()) return true;
		 }
		 return false;
	 }

	 private static float calculateEuclidianDistance(Feature f, Mean m) {
		 float total = 0.0f;
		 float[] fDesc = f.descriptor;
		 for (int i=0; i<fDesc.length; i++) {
			 float dist = fDesc[i] - m.getCoordinates()[i];
			 total += dist*dist;
		 }
		 return (float) Math.abs(Math.sqrt(total));
	 }
	
	
	 /* Do not change anything from here */
	 
	 
	// initial sigma
	private static float initial_sigma = 1.6f;
	// feature descriptor size
	private static int fdsize = 4;
	// feature descriptor orientation bins
	private static int fdbins = 8;
	// size restrictions for scale octaves, use octaves < max_size and > min_size only
	private static int min_size = 64;
	private static int max_size = 1024;

	private int count = 0;

	// TODO: show dialog box to choose Classifier
	public String showClassifierDialog(JFrame jf) {
		JDialog j = new JDialog();
		JComboBox<String> c = new JComboBox<>();
		
		count = 0;
		
		// TODO: scan classifier package for classes
		
		File dir = new File("./classifier");
		List<String> classifiers = new LinkedList<>();
		
		for (String f : dir.list()) {
			System.out.println(f);
			classifiers.add(f);
		}

		JButton b = new JButton("OK");
		
		
	    for (int i = 0; i < classifiers.size(); i++)
	    	c.addItem(classifiers.get(i));
    	
    
	    b.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			c.getSelectedItem();
    			
    		}
    	});		
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		j.setVisible(true);
		
		return "";
	}
	
	public CbirWithSift() throws IOException
	{
		super("Clustering");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						
						setTitle("Learning: readData");
						LinkedList<IgsImage> trainingImages = readImages(TRAINING_DIR,readImages);
						
				        
				        setTitle("Learning: VisualWord by Clustering");

				        
				        Vector<Feature>  allLearnFeatchers = new Vector<Feature>();  
				        for(IgsImage i : trainingImages) allLearnFeatchers.addAll(i.features);
				        
				        
				        long startTimeVW=System.currentTimeMillis();
				        //calculate the visual words with k-means
				        bagofwords = doClusteringVisualWords(allLearnFeatchers.toArray(new Feature[0]), K ,MIN_CLASS_SIZE);
				        long endTimeVW=System.currentTimeMillis();
				        
				        setTitle("Show: visualWords in TraningsData");
				        Map<String,Vector<int[]>> imageContentTrainingData = new HashMap<String,Vector<int[]>>();
				        
				        //create the VisiualWordHistograms for each training image
				        for(IgsImage i : trainingImages) {
				        	if(!imageContentTrainingData.containsKey(i.className)) imageContentTrainingData.put(i.className,new Vector<int[]>());
				        	int[] ImageVisualWordHistogram = new int[K];
				        	
				        	for(Feature f : i.features) {
				        		Integer wordClass = doClassifyVisualWord(f);
				        		if(wordClass!=null) ImageVisualWordHistogram[wordClass.intValue()]++;
				        	}
				        	
				        	imageContentTrainingData.get(i.className).add(ImageVisualWordHistogram);
				        	
				        	cur_image = i;
				        	repaint();
				        	Thread.sleep(wait);
				        }
				        
				        long startTimeDM=System.currentTimeMillis();
				        setTitle("Learning: decisionModel");
				        
				        // IClassifier classifier = new StatisticClassifier(K);
				        IClassifier classifier = new MyClassifier();
				        classifier.learn(imageContentTrainingData);
				        long endTimeDM=System.currentTimeMillis();
				        
				        
				        setTitle("Testing: readData");
						LinkedList<IgsImage> testImages = readImages(TEST_DIR,readImages);
				        
						long startTime=System.currentTimeMillis();
						
						Map<String, Integer> classStat = new HashMap<String, Integer>();
						int total = testImages.size();
						int success = 0;
						setTitle("Verify: test data");
						
						 //create the VisiualWordHistograms for each test image and classify it
				        for(IgsImage i : testImages) {
				        	int[] ImageVisualWordHistogram = new int[K];
				        	
				        	for(Feature f : i.features) {
				        		Integer wordClass = doClassifyVisualWord(f);
				        		if(wordClass!=null) ImageVisualWordHistogram[wordClass.intValue()]++;
				        	}
				        	
				        	i.classifiedName = classifier.classify(ImageVisualWordHistogram);
				        	if(classStat.containsKey(i.classifiedName)) {
				        		classStat.put(i.classifiedName, classStat.get(i.classifiedName) + 1);
				        	} else {
				        		classStat.put(i.classifiedName, 1);
				        	}
				        	
				        	if(i.isClassificationCorect()) success++;
				        	
				        	cur_image = i;
				        	repaint();
				        	Thread.sleep(wait);
				        }
				        
				        long endTime=System.currentTimeMillis();
				        
				        System.out.println("Verified "+(success/(double)testImages.size())*100+"% in "+(endTime-startTime)+"ms");						
				        System.out.println("Learned "+K+" Visual Words in: "+(endTimeVW-startTimeVW)+"ms!");
				        System.out.println("Learned the image classification in: "+(endTimeDM-startTimeDM)+"ms");
				        
				        System.out.println();
				        for(Entry<String, Integer> e : classStat.entrySet()) {
				        	System.out.println("Classified " + (100 * e.getValue()) / ((double)total) + "% as " +  e.getKey() + ".");
				        }
				        
				} catch (Exception _e) {
					_e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Reads maxImages from a folder, calculates the SIFT features and wraps the results into a IgsImage 
	 * also paints each image on the GUI 
	 * @param folder
	 * @param maxImages
	 * @return the list of read IgsImages
	 * @throws IOException
	 * @throws InterruptedException
	 */
	LinkedList<IgsImage> readImages(String folder, int maxImages) throws IOException, InterruptedException {
		LinkedList<IgsImage> images = new LinkedList<IgsImage>();
		
		File actual = new File("./Images/"+folder);
		
		int i = 0;
		
		List<File> files = new ArrayList<File>();
		for(File f : actual.listFiles()) {
			files.add(f);
		}
		
		if(CHOOSE_IMAGES_RANDOMLY) {
			Collections.shuffle(files);
		} else {
			Collections.sort(files);
		}
		
		int p = Runtime.getRuntime().availableProcessors();
		System.out.println("Pool with " + p + " Threads created.");
		ExecutorService pool = Executors.newFixedThreadPool(p);
		LinkedList<Future<IgsImage>> futures = new LinkedList<Future<IgsImage>>();
		
		for( File f : files){
			if(!f.getName().equals(".svn")) {
				if (i++ > maxImages) break;
				IgsImage image = new IgsImage();
				
				futures.add(pool.submit(new ImageRunnable(f, image), image));
			}
		}
		
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.DAYS);
		
		for (Future<IgsImage> f : futures) {
			try {
				images.add(f.get());
			} catch (ExecutionException e) {
				System.err.println("image not added");
			}
		}
		
		return images;
	}
	
	public class ImageRunnable implements Runnable {
		File file;
		IgsImage image;
		public ImageRunnable(File f, IgsImage i) {
			file = f;
			image = i;
		}
		
		@Override
		public void run() {
			try {
				image.image = ImageIO.read(file);
				image.className = file.getName().substring(0, file.getName().indexOf('_'));
				image.features = calculateSift(image.image);
			} catch (Exception e) {
				image = null;
			}
		}
	}
	
	/**
	 * draws a rotated square with center point  center, having size and orientation
	 */
	static void drawSquare(Graphics _g,double[] o, double scale, double orient, Integer _class )
	{
		scale /= 2;
		
	    double sin = Math.sin( orient );
	    double cos = Math.cos( orient );
	    
	    int[] x = new int[ 6 ];
	    int[] y = new int[ 6 ];
	    

	    x[ 0 ] = ( int )( o[ 0 ] + ( sin - cos ) * scale );
	    y[ 0 ] = ( int )( o[ 1 ] - ( sin + cos ) * scale );
	    
	    x[ 1 ] = ( int )o[ 0 ];
	    y[ 1 ] = ( int )o[ 1 ];
	    
	    x[ 2 ] = ( int )( o[ 0 ] + ( sin + cos ) * scale );
	    y[ 2 ] = ( int )( o[ 1 ] + ( sin - cos ) * scale );
	    x[ 3 ] = ( int )( o[ 0 ] - ( sin - cos ) * scale );
	    y[ 3 ] = ( int )( o[ 1 ] + ( sin + cos ) * scale );
	    x[ 4 ] = ( int )( o[ 0 ] - ( sin + cos ) * scale );
	    y[ 4 ] = ( int )( o[ 1 ] - ( sin - cos ) * scale );
	    x[ 5 ] = x[ 0 ];
	    y[ 5 ] = y[ 0 ];
	    
	    //if(_class==null || _class.intValue()==92 || _class.intValue()==69 || _class.intValue()==91) {
	    
		    _g.setColor( Color.red );
		    _g.drawPolygon( new Polygon( x, y, x.length ) );
		    _g.setColor( Color.yellow );
		    if(_class!=null) _g.drawString(_class+"",x[0], y[0]); 
	    //}
	    
	}

	@Override
	public synchronized void paint(Graphics _g) {
		
		_g.clearRect(0, 0,1000, 1000);
		
		if(cur_image==null) return;
		
		_g.drawImage(cur_image.image,0,0,null);
		
		
		_g.setColor(cur_image.isClassificationCorect() ? Color.green : Color.red);  
		
		_g.drawString(cur_image.className + " > " +cur_image.classifiedName, 20, cur_image.image.getHeight()+40);
		
		
		if(cur_image.features!=null) 
			for ( Feature f : cur_image.features )
				drawSquare(_g, new double[]{ f.location[ 0 ], f.location[ 1 ] }, fdsize * 4.0 * f.scale, f.orientation, doClassifyVisualWord(f) );
		
	}

	
	
    public static FloatArray2D ImageToFloatArray2D(BufferedImage image) throws IOException
    {
        FloatArray2D image_float=null;
        
        int count = 0;
        image_float = new FloatArray2D(image.getWidth(),  image.getHeight());
 
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
            	int rgbV = image.getRGB(x, y);
            	int b = rgbV & 0xff;
            	rgbV = rgbV >> 8;
            	int g = rgbV & 0xff;
            	rgbV = rgbV >> 8;
            	int r = rgbV & 0xff;
            	image_float.data[count++] = 0.3f * r + 0.6f * g + 0.1f * b;
            }
        }
        
        return image_float;
    }
	

	public static void main(String[] _args) throws Exception 
	{
		
		
		new CbirWithSift();
	}
	
	private Vector< Feature > calculateSift(BufferedImage image) throws IOException
	{
				
		Vector< Feature > _features = new Vector<Feature>();
		
		FloatArray2DSIFT sift = new FloatArray2DSIFT( fdsize, fdbins );
		
		FloatArray2D fa = ImageToFloatArray2D(image);
		Filter.enhance( fa, 1.0f );
		
		fa = Filter.computeGaussianFastMirror( fa, ( float )Math.sqrt( initial_sigma * initial_sigma - 0.25 ) );
		
		long start_time = System.currentTimeMillis();
		
		sift.init( fa, steps, initial_sigma, min_size, max_size );
		_features = sift.run( max_size );
		
		System.out.println( "processing SIFT took " + ( System.currentTimeMillis() - start_time ) + "ms to find \t"+ _features.size() + " features");
			
		
		return _features;
	}
	
	  
	 

}
