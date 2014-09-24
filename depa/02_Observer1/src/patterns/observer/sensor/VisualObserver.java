package patterns.observer.sensor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Formatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import patterns.observer.Observable;
import patterns.observer.Observer;


public class VisualObserver extends JFrame implements Observer {
  
  private Sensor sensor;
  private MinMaxObserver minmax;
  private AverageObserver avg;
  
  private JLabel currentTemp = new JLabel("-");
  private JLabel minTemp = new JLabel("-");
  private JLabel maxTemp = new JLabel("-");
  private JLabel avgTemp = new JLabel("-");
  private JProgressBar thermometer = new JProgressBar();
  
  public VisualObserver(Sensor s, MinMaxObserver minmaxObserver, AverageObserver avgObserver) {
    sensor = s;
    sensor.addObserver(this);
    minmax = minmaxObserver;
    avg = avgObserver;
    init();
  }
  
  private void init() {
    setLayout(new BorderLayout());
    JPanel main = new JPanel(new GridLayout(2,2));
    add(main, BorderLayout.CENTER);   
    add(thermometer, BorderLayout.SOUTH);
    JPanel current = new JPanel(new BorderLayout());
    JPanel min = new JPanel(new BorderLayout());
    JPanel max = new JPanel(new BorderLayout());
    JPanel av = new JPanel(new BorderLayout());
    Font font = new Font("Verdana", Font.PLAIN, 10);
    JLabel label = new JLabel("Current", SwingConstants.LEFT);
    label.setFont(font);
    current.add(label, BorderLayout.NORTH);
    label = new JLabel("Minimum", SwingConstants.LEFT);
    label.setFont(font);
    min.add(label, BorderLayout.NORTH);
    label = new JLabel("Maximum", SwingConstants.LEFT);
    label.setFont(font);
    max.add(label, BorderLayout.NORTH);
    label = new JLabel("Average", SwingConstants.LEFT);
    label.setFont(font);
    av.add(label, BorderLayout.NORTH);
    main.add(current);
    main.add(max);
    main.add(av);
    main.add(min);
    
    font = new Font("Verdana", Font.BOLD, 20);
    currentTemp.setFont(font);
    minTemp.setFont(font);
    minTemp.setForeground(Color.blue);
    maxTemp.setFont(font);
    maxTemp.setForeground(Color.red);
    avgTemp.setFont(font);
    
    current.add(currentTemp, BorderLayout.SOUTH);
    min.add(minTemp, BorderLayout.CENTER);
    max.add(maxTemp, BorderLayout.CENTER);
    av.add(avgTemp, BorderLayout.CENTER);
    
    thermometer.setMinimum(-10);
    thermometer.setMaximum(110);
    
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(NORMAL);
      }
    });
  }

  @Override
  public void update(Observable source) {
    if(sensor == source) {
      thermometer.setValue(sensor.getTemperature());
      currentTemp.setText(Integer.toString(sensor.getTemperature()));
      if (minmax != null) {
        minTemp.setText(Integer.toString(minmax.getMinimum()));
        maxTemp.setText(Integer.toString(minmax.getMaximum()));
      }
      if (avg != null) {
        Formatter formatter = new Formatter();
        formatter.format("%2.1f", avg.getAverage());
        avgTemp.setText(formatter.toString());
        formatter.close();
      }
    }
  }

}
