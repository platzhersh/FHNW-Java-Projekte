package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import view.elements.BrightnessButton;
import view.elements.ColorLabelHex;
import view.elements.ColorPanel;
import view.elements.ColorRadioButton;
import view.elements.ColorScrollBar;
import view.elements.ColorTextDecimal;

public class ColorPickerView extends JFrame {

	private JPanel contentPane;
	
	public ColorScrollBar sb_red;
	public ColorScrollBar sb_green;
	public ColorScrollBar sb_blue;
	
	public ColorTextDecimal text_decimal_red;
	public ColorTextDecimal text_decimal_green;
	public ColorTextDecimal text_decimal_blue;

	public ColorLabelHex lbl_hex_red;
	public ColorLabelHex lbl_hex_green;
	public ColorLabelHex lbl_hex_blue;
	
	public ColorPanel panel;
	
	public ColorRadioButton rdbtnRed;
	public ColorRadioButton rdbtnGreen;
	public ColorRadioButton rdbtnBlue;
	public ColorRadioButton rdbtnYellow;
	public ColorRadioButton rdbtnCyan;
	public ColorRadioButton rdbtnOrange;
	public ColorRadioButton rdbtnBlack; 
	public ColorRadioButton rdbtnGrey;
	
	public BrightnessButton btnDarker;
	public BrightnessButton btnBrighter;
	
	/**
	 * Create the frame.
	 */
	public ColorPickerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 470);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("File");
		menuBar.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Attributes");
		menuBar.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		sb_red = new ColorScrollBar();
		sb_red.setBackground(Color.RED);
		sb_red.setOrientation(JScrollBar.HORIZONTAL);
		
		sb_green = new ColorScrollBar();
		sb_green.setBackground(Color.GREEN);
		sb_green.setOrientation(JScrollBar.HORIZONTAL);
		
		sb_blue = new ColorScrollBar();
		sb_blue.setBackground(Color.BLUE);
		sb_blue.setOrientation(JScrollBar.HORIZONTAL);
		
		text_decimal_red = new ColorTextDecimal();
		
		text_decimal_green = new ColorTextDecimal();
		
		text_decimal_blue = new ColorTextDecimal();
		
		lbl_hex_red = new ColorLabelHex("New label");
		
		lbl_hex_green = new ColorLabelHex("New label");
		
		lbl_hex_blue = new ColorLabelHex("New label");
		
		panel = new ColorPanel();
		panel.setBackground(Color.GRAY);
		
		rdbtnRed = new ColorRadioButton("red");
		
		rdbtnBlue = new ColorRadioButton("blue");
		
		rdbtnGreen = new ColorRadioButton("green");
		
		rdbtnYellow = new ColorRadioButton("yellow");
		
		rdbtnCyan = new ColorRadioButton("cyan");
		
		rdbtnOrange = new ColorRadioButton("orange");
		
		rdbtnBlack = new ColorRadioButton("black");
		
		rdbtnGrey = new ColorRadioButton("grey");
		
		btnDarker = new BrightnessButton("Darker");
		
		btnBrighter = new BrightnessButton("Brighter");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(sb_red, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(text_decimal_red, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbl_hex_red))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(sb_green, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(text_decimal_green, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbl_hex_green))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
								.addComponent(sb_blue, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnRed)
								.addComponent(rdbtnBlue, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnOrange, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnBlack, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnGrey, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(rdbtnCyan, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
											.addComponent(rdbtnYellow, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnBrighter))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(rdbtnGreen, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnDarker))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(text_decimal_blue, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(lbl_hex_blue))))))
					.addGap(23))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(text_decimal_red, 0, 0, Short.MAX_VALUE)
							.addComponent(lbl_hex_red))
						.addComponent(sb_red, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(sb_green, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(text_decimal_green, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbl_hex_green)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(text_decimal_blue, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbl_hex_blue))
						.addComponent(sb_blue, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(rdbtnRed)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnBlue)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnGreen)
								.addComponent(btnDarker))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnYellow)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnCyan)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnOrange))
								.addComponent(btnBrighter))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnBlack))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnGrey)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
