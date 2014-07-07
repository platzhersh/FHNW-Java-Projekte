package ch.fhnw.swent.exceptionhandling.application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import ch.fhnw.swent.exceptionhandling.sensor.ISensorComponent;
import ch.fhnw.swent.exceptionhandling.sensor.SF;
import ch.fhnw.swent.exceptionhandling.sensor.SensorComponentImpl;



public class Application {

	public static void main(final String[] args) throws FileNotFoundException, IOException {
		JFrame frame = new JFrame("Sensor");
		final ISensorComponent sensor = new SF();
		GUI gui = new GUI(sensor);
		frame.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent arg0) {
        System.exit(0);
      }

		});
		frame.add(gui);
		frame.pack();
		frame.setVisible(true);
	}

}
