package ch.fhnw.igs.cbir;
import java.awt.image.BufferedImage;
import java.util.Vector;

import mpi.cbg.fly.Feature;


public class IgsImage {
	
	//the true image class known by the filename
	String className;
	
	//the estimated image class 
	String classifiedName;
	
	//the image bitmap
	BufferedImage image;
	
	//all SIFT feature found in the image
	Vector<Feature> features;
	
	//is className = classifiedName?
	boolean isClassificationCorect() {
		return className!=null && classifiedName !=null && className.equals(classifiedName);
	}
}
