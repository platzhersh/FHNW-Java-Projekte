package imageprocessing;

import javax.swing.JOptionPane;

import utils.AMatrixProcessing;
import utils.InterpolationLib;
import utils.Transformation;

public class Rotate extends AMatrixProcessing{

	
	@Override
	protected void getTransformationMatrix() {
		//Input
		String in = JOptionPane.showInputDialog("Drehwinkel eingeben.");
		double phi = 0;
		if(in != null) phi = Double.parseDouble(in);
		
		//Transformation Matrix		
		model = model.times(Transformation.pointRotation2D(phi, inData.width / 2, inData.height / 2)); 	//rotation
		
	}

	@Override
	protected int colorInterpolation(double u, double v) {
		
		return InterpolationLib.nearestNeighbour(inData, u, v);
		//return InterpolationLib.bilinear(inData, u, v);
	}

	
	

}
