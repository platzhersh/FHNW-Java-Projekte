package ch.fhnw.swent.exceptionhandling.sensor;

import java.io.IOException;

public interface ISensorComponent {
  /**
   * Listener that is informed about a sensor read.
   */
  public interface StatisticListener {
    /**
     * Call back method to inform listener about a new sensor read
     * @param min the minimum value of all physical reads (between 0.0 and 1.0)
     * @param max the maximum value of all physical reads (between 0.0 and 1.0)
     * @param avg the average value of all physical reads.
     */
    public void update(double min, double max, double avg);
  }
  
  /**
   * Start automatic mode. In automatic mode, the sensor is continuously read
   * and the min, max, and avg values are sent to the registered listeners at
   * predefined intervals.
   * @see setReadInterval
   * @see getReadInterval
   * @see addStatisticListener
   * @see removeStatisticListener
   */
  void startContinuousRead();

  /**
   * Stop automatic mode.
   * @see startContinuousRead
   */
  void stopContinuousRead();

  /**
   * Reads the sensor logically once. A logical read consists of several
   * physical reads. A logical sensor read is reported to registered
   * statistic listeners.
   * @throws IOException
   */
  void readSensor() throws IOException;

  /**
   * Set the time between two consecutive reads in automatic mode.
   * @param interval the time in milliseconds to wait before reading the
   * sensor again.
   */
  void setReadInterval(int interval);

  /**
   * @return the time between two consecutive reads in automatic mode.
   */
  int getReadInterval();

  /**
   * Set the number of reads that will be done on sensor per
   * readSensor activity.
   * Precondition: times > 0 && times < 100000
   * @param times number of actual sensor reads done in order to calculate
   * a valid average.
   */
  void setReadRepeat(int times);

  /**
   * The number of actual reads done on sensor in order to calculate a
   * valid average.
   * @return The number of actual reads on sensor.
   */
  int getReadRepeats();

  /**
   * Register a new listener
   * @param sl listener which wants to be informed about statistic events.
   */
  void addStatisticListener(StatisticListener sl);

  /**
   * Remove a registered listener
   * @param sl the listener that does not want to be informed about statistic
   * events any more.
   */
  void removeStatisticListener(StatisticListener sl);

}