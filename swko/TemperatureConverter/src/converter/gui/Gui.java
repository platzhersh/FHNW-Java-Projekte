package converter.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import converter.bl.BL;
import converter.bl.Scale;


public class Gui extends JFrame {
  
  Container content;
  LayoutManager mgr = new FlowLayout();
  JLabel fromLabel = new JLabel("From");
  JLabel toLabel = new JLabel("To");
  JTextField valueTextField = new JTextField("0", 8);
  JLabel resultLabel = new JLabel();
  JButton convertButton = new JButton("Convert");
  JComboBox fromChoice = new JComboBox();
  JComboBox toChoice = new JComboBox();
  BL bl = new BL();
  
  public Gui() {
    setTitle("Temperature Converter");
    content = new JPanel();
    content.setLayout(mgr);
    for(Scale s: bl.getScales()) {
      fromChoice.addItem(s);
      toChoice.addItem(s);
    }
    content.add(fromLabel);
    content.add(fromChoice);
    content.add(toLabel);
    content.add(toChoice);
    content.add(valueTextField);
    resultLabel.setPreferredSize(new Dimension(80, 11));
    content.add(resultLabel);
    convertButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Scale from = (Scale)fromChoice.getSelectedItem();
        Scale to = (Scale)toChoice.getSelectedItem();
        double value = Double.parseDouble(valueTextField.getText());
        double result = bl.convert(from, to, value);
        resultLabel.setText(Double.toString(result));
        
      }
      
    });
    content.add(convertButton);
    setContentPane(content);
    pack();
  }
  
  public static void main(String[] args) {
    JFrame main = new Gui();
    main.setVisible(true);
  }
}
