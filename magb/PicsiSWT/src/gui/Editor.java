package gui;
import files.Document;
import files.PNM;
import gui.MainWindow.StringInt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

// Swing style editor
public class Editor extends JFrame {
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static final long serialVersionUID = 1L;
	private JTextArea m_textPane;
	private JToolBar jToolBar;
	private JButton jSaveBtn;
	private JButton jSaveAsBtn;
	private JSlider jFontSizeSld;
	private String m_path;
	private boolean m_save;
	private MainWindow m_mainWnd;

	public Editor(MainWindow mainWnd) {
		m_mainWnd = mainWnd;
		{
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			{
				jSaveBtn = new JButton();
				jSaveBtn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/saveHS.png")));
				jSaveBtn.setToolTipText("Save");
				jSaveBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						//System.out.println("jSaveBtn.actionPerformed, event="+evt);
						saveFile(m_path == null);
					}
				});
			}
			{
				jSaveAsBtn = new JButton();
				jSaveAsBtn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/saveAsHS.png")));
				jSaveAsBtn.setToolTipText("Save As");
				jSaveAsBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						//System.out.println("jSaveAsBtn.actionPerformed, event="+evt);
						saveFile(true);
					}
				});
			}
			{
				// font size slider
				jFontSizeSld = new JSlider(JSlider.HORIZONTAL, 8, 36, 11);
				jFontSizeSld.setMaximumSize(new Dimension(100, 20));
				jFontSizeSld.setToolTipText("Font Size");
				jFontSizeSld.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						//System.out.println("jFontSizeSld.stateChanged, event="+((JSlider)evt.getSource()).getValue());
					    m_textPane.setFont(m_textPane.getFont().deriveFont((float)((JSlider)evt.getSource()).getValue()));

					}					
				});
			}
			jToolBar.add(jSaveBtn);
			jToolBar.add(jSaveAsBtn);
			jToolBar.add(jFontSizeSld);
		}
		m_textPane = new JTextArea();
		m_textPane.setEditable(true);
		m_textPane.setOpaque(true);
		add(new JScrollPane(m_textPane), BorderLayout.CENTER);
		setBounds(100, 100, 700, 500);
		add(jToolBar, BorderLayout.PAGE_START);
	}
	
	public void openFile(String path) {
		m_path = path;
		setTitle(m_path);
		try {
			m_textPane.setText("");
			m_textPane.read(new FileReader(m_path), m_path);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void openBinaryFile(Document doc, Image image, String path) {
		m_path = path;
		setTitle(m_path);
		m_textPane.setText("");
		m_mainWnd.displayTextOfBinaryImage(doc, image, m_textPane);
	}
	
	public void newFile() {
		m_path = null;
		setTitle("New Image");
		m_textPane.setText("");
	}
	
	private void saveFile(boolean createNew) {
		m_save = true;
		if (createNew) {
			// this Swing thread doesn't have direct access to SWT display thread, hence we need syncEcec
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
					StringInt si = m_mainWnd.chooseFileName();
					if (si != null) {
						m_path = si.filename;
					} else {
						m_save = false;
					}
			    }
			});
		}
		
		if (m_save) {
			try {
				// save image in ASCII format
				m_textPane.write(new FileWriter(m_path));
				
				// read header of written file and check validity
				if (! new PNM().checkHeader(m_path)) {
					JOptionPane.showMessageDialog(this, "Invalid header.\nHeader has to be a valid PBM, PGM, or PPM image header (ASCII format).", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				setTitle(m_path);
				
				// this Swing thread doesn't have direct access to SWT display thread, hence we need syncEcec
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
						m_mainWnd.updateFile(m_path);
				    }
				});
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
