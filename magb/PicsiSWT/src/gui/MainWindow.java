package gui;

import imageprocessing.ImageProcessing;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import main.PicsiSWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.printing.*;
import org.eclipse.swt.widgets.*;

import files.Document;

public class MainWindow {
	public TwinView m_views;
	
	private Shell m_shell;
	private Display m_display;
	private Editor m_editor;
	private String m_lastPath; // used to seed the file dialog
	private Label m_statusLabel, m_zoomLabel;
	private MenuItem m_editMenuItem, m_outputMenuItem;

	/////////////////////////////////////////////////////////////////////////////////////////////////////7
	// public methods section

	public Shell open(Display dpy) {
		// Create a window and set its title.
		m_display = dpy;
		m_shell = new Shell(m_display);
		m_shell.setLayout(new GridLayout());
		
		// Hook listeners.
		m_shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				e.doit = true;
			}
		});
		m_shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				// Clean up.
				if (m_views != null) m_views.clean();;
				if (m_editor != null) m_editor.dispose();
			}
		});

		// set icon
		try {
			m_shell.setImage(new Image(m_display, getClass().getClassLoader().getResource("images/picsi.png").openStream()));			
		} catch(IOException e) {
		}
		
		// set title
		m_shell.setText(PicsiSWT.APP_NAME);
		
		// create twin view: must be done before createMenuBar, because of dynamic image processing menu items
		m_views = new TwinView(this, m_shell, SWT.NONE);
		
		// create
		createMenuBar();
		
		// create status bar
		{
			Composite compo = new Composite(m_shell, SWT.NONE);
			GridData data = new GridData (SWT.FILL, SWT.BOTTOM, true, false);
			data.heightHint = 15;
			compo.setLayoutData(data);
			
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			gridLayout.horizontalSpacing = 10;
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			compo.setLayout(gridLayout);
			
			// Label to show status and cursor location in image.
			m_statusLabel = new Label(compo, SWT.NONE);
			m_statusLabel.setText("");
			data = new GridData (SWT.FILL, SWT.FILL, true, true);
			m_statusLabel.setLayoutData(data);
			
			// Label to show zoom value
			m_zoomLabel = new Label(compo, SWT.NONE);
			m_zoomLabel.setText("Zoom");
			data = new GridData (SWT.RIGHT, SWT.FILL, false, true);
			data.widthHint = 50;
			m_zoomLabel.setLayoutData(data);
		}
		
		m_shell.pack();

		// Open the window
		m_shell.open();
		return m_shell;
	}
	
	public void displayTextOfBinaryImage(Document doc, Image image, JTextArea text) {
		doc.displayTextOfBinaryImage(image, text);
	}
	
	/*
	 * Set the status label to show color information
	 * for the specified pixel in the image.
	 */
	public void showColorForPixel(Object[] args) {
		if (args == null) {
			m_statusLabel.setText("");
		} else {
			m_statusLabel.setText(PicsiSWT.createMsg("Image color at ({0,number,integer}, {1,number,integer}) - pixel {2,number,integer} [0x{3}] - is {4} [0x{5}] {6}", args));
		}
	}

	public void showZoomFactor(float zoom) {
		m_zoomLabel.setText("" + Math.round(zoom*100) + '%');
	}
	
	public void toggleOutputWindow() {
		m_outputMenuItem.setSelection(!m_outputMenuItem.getSelection());
		m_views.split(m_outputMenuItem.getSelection());
	}
	
	public void showErrorDialog(String operation, String filename, Throwable e) {
		MessageBox box = new MessageBox(m_shell, SWT.ICON_ERROR);
		String message = PicsiSWT.createMsg("Error {0}\nin {1}\n\n", new String[] {operation, filename});
		String errorMessage = "";
		if (e != null) {
			if (e instanceof SWTException) {
				SWTException swte = (SWTException)e;
				errorMessage = swte.getMessage();
				if (swte.throwable != null) {
					errorMessage += ":\n" + swte.throwable.toString();
				}
			} else if (e instanceof SWTError) {
				SWTError swte = (SWTError)e;
				errorMessage = swte.getMessage();
				if (swte.throwable != null) {
					errorMessage += ":\n" + swte.throwable.toString();
				}
			} else {
				errorMessage = e.toString();
			}
		}
		box.setText("Error");
		box.setMessage(message + errorMessage);
		box.open();
	}
	
	public static class StringInt {
		public String filename;
		public int filetype;
	}
	
	public StringInt chooseFileName() {
		// Get the user to choose a file name and type to save.
		FileDialog fileChooser = new FileDialog(m_shell, SWT.SAVE);
		fileChooser.setFilterPath(m_lastPath);
		fileChooser.setFilterExtensions(PicsiSWT.SAVE_FILTER_EXTENSIONS);
		fileChooser.setFilterNames(PicsiSWT.SAVE_FILTER_NAMES);
		
		String filename = null;
		
		if (m_views.hasSecondView()) {
			Document doc = m_views.getDocument(false);
			filename = doc.getFileName();			
		}
		if (filename != null) {
			fileChooser.setFileName(filename);
			fileChooser.setFilterIndex(PicsiSWT.determineFilterIndex(filename));
		}
		filename = fileChooser.open();
		m_lastPath = fileChooser.getFilterPath();
		if (filename == null)
			return null;

		// Figure out what file type the user wants saved.
		//fileChooser.getFilterIndex();
		int filetype = PicsiSWT.determineFileType(filename);
		if (filetype == SWT.IMAGE_UNDEFINED) {
			MessageBox box = new MessageBox(m_shell, SWT.ICON_ERROR);
			box.setMessage(PicsiSWT.createMsg("Unknown file extension: {0}\nPlease use bmp, gif, ico, jfif, jpeg, jpg, pbm, pgm, png, ppm, tif, or tiff.", 
				filename.substring(filename.lastIndexOf('.') + 1)));
			box.open();
			return null;
		}
		
		if (new java.io.File(filename).exists()) {
			MessageBox box = new MessageBox(m_shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
			box.setMessage(PicsiSWT.createMsg("Overwrite {0}?", filename));
			if (box.open() == SWT.CANCEL)
				return null;
		}
		
		StringInt si = new StringInt();		
		si.filename = filename;
		si.filetype = filetype;
		return si;
	}
	
	public void updateFile(String filename) {
		Cursor waitCursor = m_display.getSystemCursor(SWT.CURSOR_WAIT);
		m_shell.setCursor(waitCursor);
		m_views.setCursor(waitCursor);
		int filetype = PicsiSWT.determineFileType(filename);
		
		try {
			m_views.load(filename, filetype, m_display);
			setTitle(filename, filetype);
		} catch (Throwable e) {
			showErrorDialog("loading", filename, e);					
		}
	
		m_editMenuItem.setEnabled(true);
		m_shell.setCursor(null);
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////7
	// private methods section
	
	private Menu createMenuBar() {
		// Menu bar.
		Menu menuBar = new Menu(m_shell, SWT.BAR);
		m_shell.setMenuBar(menuBar);
		createFileMenu(menuBar);
		createImageMenu(menuBar);
		createWindowMenu(menuBar);
		createHelpMenu(menuBar);
		return menuBar;
	}
	
	// File menu
	private void createFileMenu(Menu menuBar) {
		final int SAVE = 3;
		final int SAVEAS = 4;
		final int PRINT = 8;
		final int SWAP = 10;
		
		// File menu
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("&File");
		final Menu fileMenu = new Menu(m_shell, SWT.DROP_DOWN);
		item.setMenu(fileMenu);
		fileMenu.addListener(SWT.Show,  new Listener() {
			public void handleEvent(Event e) {
				MenuItem[] menuItems = fileMenu.getItems();
				menuItems[SWAP].setEnabled(m_views.hasSecondView());
				menuItems[SAVE].setEnabled(m_views.hasSecondView());
				menuItems[SAVEAS].setEnabled(m_views.hasSecondView());
				menuItems[PRINT].setEnabled(!m_views.isEmpty());
			}
		});

		// File -> New...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&New...\tCtrl+N");
		item.setAccelerator(SWT.MOD1 + 'N');
		setIcon(item, "images/newHS.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editFile(null, null, null);
			}
		});
		
		// File -> Open...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&Open...\tCtrl+O");
		item.setAccelerator(SWT.MOD1 + 'O');
		setIcon(item, "images/openHS.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Get the user to choose an image file.
				FileDialog fileChooser = new FileDialog(m_shell, SWT.OPEN);
				if (m_lastPath != null)
					fileChooser.setFilterPath(m_lastPath);
				fileChooser.setFilterExtensions(PicsiSWT.OPEN_FILTER_EXTENSIONS);
				fileChooser.setFilterNames(PicsiSWT.OPEN_FILTER_NAMES);
				String filename = fileChooser.open();
				if (filename == null)
					return;
				m_lastPath = fileChooser.getFilterPath();

				updateFile(filename);
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Save
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&Save Output\tCtrl+S");
		item.setAccelerator(SWT.MOD1 + 'S');
		setIcon(item, "images/saveHS.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				saveFile(false);
			}
		});
		
		// File -> Save As...
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Save Output As...");
		setIcon(item, "images/saveAsHS.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				saveFile(true);
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// Image -> Edit...
		m_editMenuItem = new MenuItem(fileMenu, SWT.PUSH);
		m_editMenuItem.setText("&Edit...\tCtrl+E");
		m_editMenuItem.setAccelerator(SWT.MOD1 + 'E');
		m_editMenuItem.setEnabled(false);
		setIcon(m_editMenuItem, "images/editHS.png");
		m_editMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Document doc = m_views.getDocument(true);
				View view = m_views.getView(true);
				
				if (m_views.hasSecondView()) {
					// ask the user to specify the image to print
					Object[] filterTypes = { "Input", "Output" };
					int o = JOptionPane.showOptionDialog(null, "Choose the image to edit", "Edit", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, filterTypes, filterTypes[0]);
					if (o > 0) {
						view = m_views.getView(false);
						doc = m_views.getDocument(false);
					}
				}
				
				String filename = doc.getFileName();
				if (filename == null) {
					// must be saved before
					if (!saveFile(true)) return;
				}
				filename = doc.getFileName();
				if (filename != null) {
					editFile(doc, view.getImage(), filename);
				}
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Print
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&Print...\tCtrl+P");
		item.setAccelerator(SWT.MOD1 + 'P');
		setIcon(item, "images/printHS.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (m_views.isEmpty()) return;
				
				View view = m_views.getView(true);
				Document doc = m_views.getDocument(true);
			
				if (m_views.hasSecondView()) {
					// ask the user to specify the image to print
					Object[] filterTypes = { "Input", "Output" };
					int o = JOptionPane.showOptionDialog(null, "Choose the image to print", "Print", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, filterTypes, filterTypes[0]);
					if (o > 0) {
						view = m_views.getView(false);
						doc = m_views.getDocument(false);
					}
				}
				
				// Ask the user to specify the printer.
				PrintDialog dialog = new PrintDialog(m_shell, SWT.NONE);
				PrinterData printerData = view.getPrinterData();
				if (printerData != null) dialog.setPrinterData(printerData);
				printerData = dialog.open();
				if (printerData == null) return;
				
				Throwable ex = view.print(m_display);
				if (ex != null) {
					showErrorDialog("printing", doc.getFileName(), ex);					
				}
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);

		// File -> Swap I/O
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Swap &Images\tCtrl+I");
		item.setAccelerator(SWT.MOD1 + 'I');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				m_views.swapImages();
			}
		});
		
		new MenuItem(fileMenu, SWT.SEPARATOR);
		
		// File -> Exit
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("E&xit");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				m_shell.close();
			}
		});
	
	}
	
	// Image menu
	private void createImageMenu(Menu menuBar) {
		final ImageProcessing ip = new ImageProcessing(m_views);
		
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("&Image");
		final Menu imageMenu = new Menu(m_shell, SWT.DROP_DOWN);
		item.setMenu(imageMenu);
		imageMenu.addListener(SWT.Show,  new Listener() {
			public void handleEvent(Event e) {
				MenuItem[] menuItems = imageMenu.getItems();
				for (int i=0; i < menuItems.length; i++) {
					menuItems[i].setEnabled(!m_views.isEmpty() && ip.isEnabled(i));
				}
			}
		});

		// user defined image menu items
		ip.createMenuItems(imageMenu);

	}
	
	// Window menu
	private void createWindowMenu(Menu menuBar) {
		final int AUTO_ZOOM = 0;
		final int ORIGINAL_SIZE = 1;
		
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("&Window");
		final Menu windowMenu = new Menu(m_shell, SWT.DROP_DOWN);
		item.setMenu(windowMenu);
		windowMenu.addListener(SWT.Show,  new Listener() {
			public void handleEvent(Event e) {
				MenuItem[] menuItems = windowMenu.getItems();
				menuItems[AUTO_ZOOM].setEnabled(!m_views.isEmpty());
				menuItems[AUTO_ZOOM].setSelection(m_views.hasAutoZoom());
				menuItems[ORIGINAL_SIZE].setEnabled(!m_views.isEmpty());
			}
		});

		// Window -> Auto Zoom
		item = new MenuItem(windowMenu, SWT.CHECK);
		item.setText("&Auto Zoom\tCtrl+A");
		item.setAccelerator(SWT.MOD1 + 'A');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				assert !m_views.isEmpty() : "menu item must be grayed";
				MenuItem item = (MenuItem)event.widget;
				m_views.setAutoZoom(item.getSelection());
			}
		});

		// Window -> Original Size
		item = new MenuItem(windowMenu, SWT.PUSH);
		item.setText("Original Si&ze\tCtrl+Z");
		item.setAccelerator(SWT.MOD1 + 'Z');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				assert !m_views.isEmpty() : "menu item must be grayed";
				MenuItem[] menuItems = windowMenu.getItems();
				m_views.zoom100();
				menuItems[AUTO_ZOOM].setSelection(m_views.hasAutoZoom());
			}
		});

		new MenuItem(windowMenu, SWT.SEPARATOR);

		// Window -> Show Output
		m_outputMenuItem = new MenuItem(windowMenu, SWT.CHECK);
		m_outputMenuItem.setText("Sho&w Output\tCtrl+W");
		m_outputMenuItem.setAccelerator(SWT.MOD1 + 'W');
		m_outputMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				MenuItem item = (MenuItem)event.widget;
				m_views.split(item.getSelection());
			}
		});
	}
	
	// Help menu
	private void createHelpMenu(Menu menuBar) {
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("&Help");
		final Menu helpMenu = new Menu(m_shell, SWT.DROP_DOWN);
		item.setMenu(helpMenu);

		// Help -> About
		item = new MenuItem(helpMenu, SWT.PUSH);
		item.setText("About");
		//item.setID(SWT.ID_ABOUT);
		setIcon(item, "images/picsi.png");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				MessageBox box = new MessageBox(m_shell, SWT.OK);
				box.setText("About " + PicsiSWT.APP_NAME);
				Calendar calendar = new GregorianCalendar();
				box.setMessage("Copyright (c) " + calendar.get(Calendar.YEAR) + " FHNW\nInstitute of Mobile and Distributed Systems\nBrugg-Windisch\nSwitzerland\nwww.fhnw.ch/imvs\nVersion " + PicsiSWT.APP_VERSION);
				box.open();
			}
		});
	}
	
	private boolean saveFile(boolean saveAs) {
		StringInt si = null;
		
		if (saveAs || m_views.getDocument(false).getFileName() == null) {
			si = chooseFileName();
			if (si == null) return false;
		}
		
		Cursor waitCursor = m_display.getSystemCursor(SWT.CURSOR_WAIT);
		m_shell.setCursor(waitCursor);
		m_views.setCursor(waitCursor);
		
		try {
			if (si != null) {
				m_views.save(si.filename, si.filetype);
				setTitle(si.filename, si.filetype);
			} else {
				assert m_views.getDocument(false).getFileName() != null : "doc2 has no filename";
				m_views.save(null, -1);
			}
			return true;
		} catch (Throwable e) {
			showErrorDialog("saving", (si != null) ? si.filename : m_views.getDocument(false).getFileName(), e);
			return false;
		} finally {
			m_shell.setCursor(null);
			m_views.refresh();
		}
	}
	
	private void setTitle(String filename, int filetype) {
		m_shell.setText(PicsiSWT.createMsg(PicsiSWT.APP_NAME + " - {0} ({1})", new Object[]{filename, PicsiSWT.fileTypeString(filetype)}));		
	}
	
	private void setIcon(Item item, String resourceName) {
		try {
			item.setImage(new Image(m_display, getClass().getClassLoader().getResource(resourceName).openStream()));			
		} catch(IOException e) {
		}
	}
	
	private void editFile(Document doc, Image image, String path) {
		if (m_editor == null) {
			m_editor = new Editor(this);
		}
		if (path == null) {
			m_editor.newFile();
		} else {
			if (doc.isBinaryFormat()) {
				m_editor.openBinaryFile(doc, image, path);
			} else {
				m_editor.openFile(path);
			}
		}
		m_editor.setVisible(true);
	}
	
}
