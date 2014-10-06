package uebung1.sudoko;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SudokuView extends JFrame{

	private SudokuModel model;
	private SudokuController controller;
	private JPanel contentPane;
	private JPanel jPnlChessboard;
	
	public JLabel lblSolutionsValue;
	public JLabel lblTimeValue;
	SudokuField[][] fields;
	
	
	
	/**
	 * Create the JFrame
	 */
	public SudokuView(SudokuModel m, SudokuController c) {
		model = m;
		controller = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Add components
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.NORTH);
		
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateSudoku();
				validate();
				repaint();
			}
		});
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.solve();
			}
		});
		
		panel.add(btnReset);
		panel.add(btnSolve);
		
		
		// CENTER
		jPnlChessboard = new JPanel();
		this.getContentPane().add(jPnlChessboard, BorderLayout.CENTER);

		generateSudoku();
		
		// SOUTH
		JPanel panelMeta = new JPanel();
		this.getContentPane().add(panelMeta, BorderLayout.SOUTH);
		
		JLabel lblSolutionsTitle = new JLabel("Solutions: ");
		lblSolutionsValue = new JLabel("");
		JLabel lblTimeTitle = new JLabel("Time: ");
		lblTimeValue = new JLabel("...");
		
		panelMeta.add(lblSolutionsTitle);
		panelMeta.add(lblSolutionsValue);
		panelMeta.add(lblTimeTitle);
		panelMeta.add(lblTimeValue);

		
	}
	
	/***
	 * generate ChessBoard according to the Model
	 */
	public void generateSudoku() {

		// cleanup
		jPnlChessboard.removeAll();
		jPnlChessboard.setLayout(new GridLayout(model.getSize(),model.getSize()));
		
		// build chessboard
		fields = new SudokuField[model.getSize()][model.getSize()];
		
		for (int i = 0; i < model.getSize(); i++) {
			for (int j = 0; j < model.getSize(); j++) {
				
				fields[i][j] = new SudokuField(i,j);
			}
		}
		
		// display chessboard
		for (int i = 0; i < model.getSize(); i++) {
			for (int j = 0; j < model.getSize(); j++) {
				jPnlChessboard.add(fields[i][j]);
				fields[i][j].addKeyListener(new SudokuFieldChangedListener(controller));
			}
		}

	}


}
