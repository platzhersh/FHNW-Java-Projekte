package ch.fhnw.swent.exceptionhandling.sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SF implements ISensorComponent {
	
	ISensorComponent controller;
	
	private static final String LOGPATH = "c:\\tmp";
	private static final String LOGFILE = "\\Logfile.txt";
	
	public SF() {
		try {
			controller = new SensorComponentImpl();
		} catch (FileNotFoundException e) {
			String filepath = System.getProperty("user.home", LOGPATH) + LOGFILE;
			System.out.println(filepath);
			File file = new File(filepath);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Logfile.txt erstellt.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startContinuousRead() {
		controller.startContinuousRead();
	}

	@Override
	public void stopContinuousRead() {
		controller.stopContinuousRead();
		
	}

	@Override
	public void readSensor() throws IOException {
		try {
			controller.readSensor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setReadInterval(int interval) {
		controller.setReadInterval(interval);		
	}

	@Override
	public int getReadInterval() {
		return controller.getReadInterval();
	}

	@Override
	public void setReadRepeat(int times) {
		controller.setReadRepeat(times);
		
	}

	@Override
	public int getReadRepeats() {
		return controller.getReadRepeats();
	}

	@Override
	public void addStatisticListener(StatisticListener sl) {
		controller.addStatisticListener(sl);
		
	}

	@Override
	public void removeStatisticListener(StatisticListener sl) {
		controller.removeStatisticListener(sl);
	}

	
	
}
