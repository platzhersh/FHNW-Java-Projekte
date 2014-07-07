package ch.fhnw.swent.exceptionhandling.sensor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.swent.exceptionhandling.meter.Sensor;
import ch.fhnw.swent.exceptionhandling.meter.SimpleLogger;

public class SensorComponentImpl implements ISensorComponent {
  private class SensorReader implements Runnable {

    /**
     * Obtain a valid sensor read.
     * @throws IOException if there is a problem with the log.
     * @throws InterruptedException
     */
    private synchronized void readSensor() throws IOException {
      double min = Double.MAX_VALUE;
      double max = 0.0;
      double sum = 0.0;
      double avg = 0.0;
      
      logger.startLogging();
      int count = 0;
      readRepeats = getReadRepeats();
      while(count < readRepeats) {
        double m = sensor.getMeasurement();
        logger.log(m);
        // calculate statistic data
        if (m == -1.0) {
        	readRepeats--;
        } else {        
	        if (m < min) min = m;
	        if (m > max) max = m;
	        sum += m;
	        count++;
        }
      }
      avg = sum/count;
      logger.stopLogging();
      
      notifyListeners(min, max, avg);
    }

    @Override
    public void run() {
      while(true) {
        if(continuousReadOn) {
//MT:            readSensor();
//MT:            Thread.sleep(intervalTime);
        } else {
          Thread.yield();
        }
      }
    }
    
  }

  private static final String LOGPATH = "c:\\tmp";
  private static final String LOGFILE = "\\Logfile.txt";

  private Sensor sensor;
  private SimpleLogger logger;
  private SensorReader sensorReader;

  private List<StatisticListener> listeners = new LinkedList<StatisticListener>();

  private int readRepeats = 10;
  private int intervalTime = 3000;
  private boolean continuousReadOn = false;

  public SensorComponentImpl() throws FileNotFoundException, IOException {
    String filepath = System.getProperty("user.home", LOGPATH) + LOGFILE; 
    logger = new SimpleLogger(filepath);
    sensor = new Sensor();
    sensorReader = new SensorReader();
    //MT: new Thread(sensorReader).start();
  }
  
  @Override
  public void startContinuousRead() {
    continuousReadOn = true;
  }
  
  @Override
  public void stopContinuousRead() {
    continuousReadOn = false;
  }

  private void notifyListeners(double min, double max, double avg) {
    for(StatisticListener sl : listeners) {
      sl.update(min, max, avg);
    }
  }
  
  @Override
  public void readSensor() throws IOException {
    sensorReader.readSensor();
  }
  
  /////
  // Getters and Setters
  /////
  
  @Override
  public void setReadInterval(int interval) {
    intervalTime = interval;
  }
  
  @Override
  public int getReadInterval() {
    return intervalTime;
  }

  @Override
  public void setReadRepeat(int times) {
    readRepeats = times;
  }

  @Override
  public int getReadRepeats() {
    return readRepeats;
  }
  
  @Override
  public void addStatisticListener(StatisticListener sl) {
    if (!listeners.contains(sl)) {
      listeners.add(sl);
    }
  }

  @Override
  public void removeStatisticListener(StatisticListener sl) {
    listeners.remove(sl);
  }

}
