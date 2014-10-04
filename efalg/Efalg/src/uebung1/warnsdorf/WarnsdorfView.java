package uebung1.warnsdorf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/***
 * Containing, generating and displaying all the view elements
 * @author chregi
 *
 */
public class WarnsdorfView extends JFrame {

	private WarnsdorfModel model;
	private WarnsdorfController controller;
	private JPanel contentPane;
	private static JTextField txtBoardSize;
	public ChessBoardFieldLabel[][] fields;
	private JPanel jPnlChessboard;
	
	public JLabel lblStartingValue;
	public JLabel lblTimeValue;
	
	
	/***
	 * Special JLabel, storing information about position on ChessBoard
	 * @author chregi
	 *
	 */
	class ChessBoardFieldLabel extends JLabel {
		int i;
		int j;
		
		public ChessBoardFieldLabel(int cordI, int cordJ) {
			i = cordI;
			j = cordJ;
			this.setText(getValueAsText());
			this.setAlignmentX(SwingConstants.CENTER);
			this.setAlignmentY(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
		}
		
		public String getValueAsText() {
			return Integer.toString(model.board.get(i,j).getCount());
		}
		public void updateText() {
			this.setText(getValueAsText());
		}
		public int getI() {
			return i;
		}
		public int getJ(){
			return j;
		}
	}
	
	/***
	 * generate ChessBoard according to the Model
	 */
	public void generateChessBoard() {

		// cleanup
		jPnlChessboard.removeAll();
		jPnlChessboard.setLayout(new GridLayout(model.getSize(),model.getSize()));
		
		// build chessboard
		fields = new ChessBoardFieldLabel[model.getSize()][model.getSize()];
		
		for (int i = 0; i < model.getSize(); i++) {
			for (int j = 0; j < model.getSize(); j++) {
				
				fields[i][j] = new ChessBoardFieldLabel(i, j);
			}
		}
		
		// display chessboard
		for (int i = 0; i < model.getSize(); i++) {
			for (int j = 0; j < model.getSize(); j++) {
				if ((i+j)%2 ==0) {
					fields[i][j].setBackground(new Color(255,255,255));
					fields[i][j].setOpaque(true);
				}
				else {
					fields[i][j].setBackground(new Color(0,0,0));
					fields[i][j].setOpaque(true);
					fields[i][j].setForeground(new Color(255,255,255));
				}
				jPnlChessboard.add(fields[i][j]);
				fields[i][j].addMouseListener(new ChessBoardFieldClickListener(controller));
			}
		}

	}

	/**
	 * Create the JFrame
	 */
	public WarnsdorfView(WarnsdorfModel m, WarnsdorfController c) {
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
		
		JLabel lblSize = new JLabel("Size");
		panel.add(lblSize);
		
		txtBoardSize = new JTextField();
		panel.add(txtBoardSize);
		txtBoardSize.setColumns(10);
		
		txtBoardSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.generateChessBoard(Integer.parseInt(txtBoardSize.getText()));
			}
		});
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.generateChessBoard(Integer.parseInt(txtBoardSize.getText()));
			}
		});
		
		panel.add(btnGenerate);
		
		
		// CENTER
		jPnlChessboard = new JPanel();
		this.getContentPane().add(jPnlChessboard, BorderLayout.CENTER);

		
		generateChessBoard();
		
		
		// SOUTH
		JPanel panelMeta = new JPanel();
		this.getContentPane().add(panelMeta, BorderLayout.SOUTH);
		
		JLabel lblStartingTitle = new JLabel("Starting: ");
		lblStartingValue = new JLabel("...");
		JLabel lblTimeTitle = new JLabel("Time: ");
		lblTimeValue = new JLabel("...");
		
		panelMeta.add(lblStartingTitle);
		panelMeta.add(lblStartingValue);
		panelMeta.add(lblTimeTitle);
		panelMeta.add(lblTimeValue);

		
	}
	

	
}
