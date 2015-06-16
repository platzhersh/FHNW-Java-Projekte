package ch.fhnw.igs.cbir;

import java.util.LinkedList;

import mpi.cbg.fly.Feature;


public class Mean {
	
	/**
	 * Control double precision when calculating movement to last position.
	 */
	private final double DIFF = 1E-6;

	private float[] coordinates;
	private float[] coordinatesOld;
	private LinkedList<Feature> features = new LinkedList<Feature>();
	private int dimensions;
	
	public Mean(float[] coordinates) {
		this.dimensions = coordinates.length;
		this.coordinates = coordinates;
		this.coordinatesOld = new float[dimensions];
	}

	public void addFeature(Feature f) {
		features.add(f);
	}
	
	public int getNrOfFeatures() {
		return features.size();
	}

	public void calculateNewPosition() {
		this.coordinatesOld = coordinates;

		float total = 0.0f;
		if (features.size() > 0) {
			for (int i=0; i<dimensions; i++) {
				for (Feature f : features) {
					total += f.descriptor[i];
				}
				coordinates[i] = total/features.size();
				total = 0.0f;
			}
		}
	}
	
	public boolean moved() {
		boolean moved = false;
		for (int i=0; i<dimensions; i++) {
			if (coordinatesOld[i]+DIFF < coordinates[i] || coordinatesOld[i]-DIFF > coordinates[i]) {
				moved = true;
				break;
			}
		}
		return moved;
	}

	public float[] getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
		this.coordinatesOld = new float[dimensions];
		features.clear();
	}

}
