package imageprocessing;

import java.awt.Label;

import javax.swing.*;

import utils.AMatrixProcessing;
import utils.InterpolationLib;
import utils.Transformation;

public class Scale extends AMatrixProcessing{
	
	@Override
	protected void getTransformationMatrix() {
		//Input
	    JPanel myPanel = new JPanel();
	    JTextField field1 = new JTextField(10);
	    JTextField field2 = new JTextField(10);
	    myPanel.add(new Label("Skalierung der x und y Achse:"));
	    myPanel.add(field1);
	    myPanel.add(field2);
	    JOptionPane.showMessageDialog(null, myPanel);
	    String x_scal = field1.getText();
	    String y_scal = field2.getText();
	    if(x_scal == null || x_scal == "") x_scal = "1";
	    if(y_scal == null || y_scal == "") y_scal = "1"; 
	    double[] s = { Double.valueOf(x_scal), Double.valueOf(y_scal) };
		
		//Transformation Matrix	
		model = model.times(Transformation.scaleLocal(inData.width / 2, inData.height / 2, s[0], s[1])); 
	}

	@Override
	protected int colorInterpolation(double u, double v) {
		//return Interpolation.nearestNeighbour(inData, u, v);
		return InterpolationLib.bilinear(inData, u, v);
	}

	
	

}
