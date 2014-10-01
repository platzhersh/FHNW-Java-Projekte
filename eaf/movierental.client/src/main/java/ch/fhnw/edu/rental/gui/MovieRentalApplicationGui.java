package ch.fhnw.edu.rental.gui;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import ch.fhnw.edu.rental.services.BusinessLogic;
import ch.fhnw.edu.rental.services.BusinessLogic.UserVisitor;

public class MovieRentalApplicationGui extends javax.swing.JFrame {

	private static final long serialVersionUID = -222656902828639843L;
	public static final String LOOK_AND_FEEL_ERR_MSG = "Error attempting to set look and feel of the system";
	public static final String DATA_DASE_ERR_MSG = "Error attempting to initialize database";
	private static final String ABOUT_ERR_MSG = "Error attempting to launch web browser.";

	public MovieRentalApplicationGui(BusinessLogic logic) {
		// set system look and feel of the application
		try {
			String ui = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(ui);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, LOOK_AND_FEEL_ERR_MSG + ":\n"
					+ e.getLocalizedMessage());
		} // end of try/catch

		// initialize rental database
		try {
			services = logic;
			mappers = new MovieRentalMappers(services);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, DATA_DASE_ERR_MSG + ":\n"
					+ e.getLocalizedMessage() + "\nApplication is closed.");
			System.exit(JFrame.EXIT_ON_CLOSE);
		}

		// initialize date settings
		TimeZone localTz = TimeZone.getDefault();
		SDF.setTimeZone(localTz);
		currDate = SDF.format(new Date());

		// initialize application components
		initComponents();
	}
	
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	/**
	 * none.
	 */
	private void initComponents() {

		aboutDialog = new javax.swing.JDialog();
		javax.swing.JLabel headingLabel = new javax.swing.JLabel();
		javax.swing.JLabel headingTwoLabel = new javax.swing.JLabel();
		javax.swing.JLabel descriptionLabelAbout = new javax.swing.JLabel();
		javax.swing.JLabel productLabel = new javax.swing.JLabel();
		javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
		javax.swing.JLabel authorsLabel = new javax.swing.JLabel();
		javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
		javax.swing.JLabel versionNrLabel = new javax.swing.JLabel();
		javax.swing.JLabel vendorTextLabel = new javax.swing.JLabel();
		javax.swing.JLabel authorsTextLabel = new javax.swing.JLabel();
		javax.swing.JLabel homepageTextLabel = new javax.swing.JLabel();
		javax.swing.JButton cancelAboutButton = new javax.swing.JButton();
		aboutSeparator = new javax.swing.JSeparator();
		getRentalsDialog = new javax.swing.JDialog();
		getRentalsScrollPane = new javax.swing.JScrollPane();
		getRentalsTable = new javax.swing.JTable();
		closeGetRentalsButton = new javax.swing.JButton();
		deleteGetRentalsButton = new javax.swing.JButton();
		javax.swing.JLabel userGetRentalsLabel = new javax.swing.JLabel();
		userGetRentalsTextLabel = new javax.swing.JLabel();
		mainPane = new javax.swing.JTabbedPane();
		mainPanel = new javax.swing.JPanel();
		javax.swing.JLabel lastNameLabel = new javax.swing.JLabel();
		javax.swing.JLabel firstNameLabel = new javax.swing.JLabel();
		javax.swing.JLabel rentalDateLabel = new javax.swing.JLabel();
		javax.swing.JLabel rentalDaysLabel = new javax.swing.JLabel();
		rentalDaysTextField = new javax.swing.JTextField();
		rentalDateTextField = new javax.swing.JTextField();
		firstNameTextField = new javax.swing.JTextField();
		lastNameTextField = new javax.swing.JTextField();
		getUserButton = new javax.swing.JButton();
		saveRentalButton = new javax.swing.JButton();
		newUserCheckBox = new javax.swing.JCheckBox();
		javax.swing.JLabel userIdLabel = new javax.swing.JLabel();
		clearAllButton = new javax.swing.JButton();
		userIdForTextField = new javax.swing.JFormattedTextField();
		movieScrollPane = new javax.swing.JScrollPane();
		movieTable = new javax.swing.JTable();
		movieCRUDPanel = new javax.swing.JPanel();
		javax.swing.JScrollPane movieCRUDScrollPane = new javax.swing.JScrollPane();
		movieCRUDTable = new javax.swing.JTable();
		saveMoviesButton = new javax.swing.JButton();
		newMoviesButton = new javax.swing.JButton();
		deleteMoviesButton = new javax.swing.JButton();
		editMoviesButton = new javax.swing.JButton();
		javax.swing.JLabel movieTitleMoviesLabel = new javax.swing.JLabel();
		javax.swing.JLabel releaseDateMoviesLabel = new javax.swing.JLabel();
		javax.swing.JLabel priceCatMoviesLabel = new javax.swing.JLabel();
		priceCatMoviesComboBox = new javax.swing.JComboBox<>();
		releaseDateMoviesTextField = new javax.swing.JTextField();
		movieTitleMoviesTextField = new javax.swing.JTextField();
		cancelMoviesButton = new javax.swing.JButton();
		usersCRUDPanel = new javax.swing.JPanel();
		javax.swing.JScrollPane usersCRUDScrollPane = new javax.swing.JScrollPane();
		usersCRUDTable = new javax.swing.JTable();
		saveUsersButton = new javax.swing.JButton();
		deleteUsersButton = new javax.swing.JButton();
		editUsersButton = new javax.swing.JButton();
		newUsersButton = new javax.swing.JButton();
		javax.swing.JLabel firstNameUsersLabel = new javax.swing.JLabel();
		cancelUsersButton = new javax.swing.JButton();
		firstNameUsersTextField = new javax.swing.JTextField();
		javax.swing.JLabel lastNameUsersLabel = new javax.swing.JLabel();
		lastNameUsersTextField = new javax.swing.JTextField();
		getRentalsButton = new javax.swing.JButton();
		rentalsCRUDPanel = new javax.swing.JPanel();
		javax.swing.JScrollPane rentalsCRUDScrollPane = new javax.swing.JScrollPane();
		rentalsCRUDTable = new javax.swing.JTable();
		deleteRentalsButton = new javax.swing.JButton();
		menuBar = new javax.swing.JMenuBar();
		javax.swing.JMenu fileMenu = new javax.swing.JMenu();
		exitMenuItem = new javax.swing.JMenuItem();
		javax.swing.JMenu helpMenu = new javax.swing.JMenu();
		aboutMenuItem = new javax.swing.JMenuItem();
		refreshButton1 = new javax.swing.JButton();
		refreshButton2 = new javax.swing.JButton();
		refreshButton3 = new javax.swing.JButton();
		refreshButton4 = new javax.swing.JButton();
		
		refreshButton1.setText("Refresh");
		refreshButton1.setEnabled(true);
		refreshButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton2.setText("Refresh");
		refreshButton2.setEnabled(true);
		refreshButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton3.setText("Refresh");
		refreshButton3.setEnabled(true);
		refreshButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton4.setText("Refresh");
		refreshButton4.setEnabled(true);
		refreshButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});

		aboutDialog.setTitle("About ...");
		aboutDialog.setMinimumSize(new java.awt.Dimension(460, 265));
		aboutDialog.setResizable(false);
		aboutDialog.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				aboutDialogWindowClosing(evt);
			}
		});
		headingLabel.setFont(new java.awt.Font("Tahoma", 1, 16));
		headingLabel.setText("EAF Project");

		descriptionLabelAbout
				.setText("<html>A movie rental application for the "
						+ "\"Enterprise Application Framework\" lab at the "
						+ "University of Applied Sciences "
						+ "Northwestern Switzerland brought to you by the IMVS.");

		productLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		productLabel.setText("Product Version:");

		vendorLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		vendorLabel.setText("Vendor:");

		authorsLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		authorsLabel.setText("Authors(s):");

		homepageLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		homepageLabel.setText("Homepage:");

		versionNrLabel.setText("1.0");

		vendorTextLabel
				.setText("University of Applied Sciences Northwestern Switzerland");

		authorsTextLabel
				.setText("Dominik Gruntz | J\u00fcrg Luthiger");

		homepageTextLabel.setText("http://www.fhnw.ch/technik/imvs");
		homepageTextLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				homepageTextLabelMouseClicked(evt);
			}
		});

		cancelAboutButton.setText("Close");
		cancelAboutButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						cancelAboutButtonActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout aboutDialogLayout = new javax.swing.GroupLayout(
				aboutDialog.getContentPane());
		aboutDialog.getContentPane().setLayout(aboutDialogLayout);
		aboutDialogLayout
				.setHorizontalGroup(aboutDialogLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								aboutDialogLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				descriptionLabelAbout,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				421,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				headingTwoLabel,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				61,
																				Short.MAX_VALUE)
																		.addGap(
																				370,
																				370,
																				370))
														.addComponent(
																headingLabel)
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				productLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				313,
																				Short.MAX_VALUE)
																		.addComponent(
																				versionNrLabel)
																		.addContainerGap())
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				vendorLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				108,
																				Short.MAX_VALUE)
																		.addComponent(
																				vendorTextLabel)
																		.addContainerGap())
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				authorsLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				115,
																				Short.MAX_VALUE)
																		.addComponent(
																				authorsTextLabel)
																		.addContainerGap())
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				homepageLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				258,
																				Short.MAX_VALUE)
																		.addComponent(
																				homepageTextLabel)
																		.addContainerGap())
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																aboutDialogLayout
																		.createSequentialGroup()
																		.addComponent(
																				cancelAboutButton)
																		.addContainerGap())))
						.addGroup(
								aboutDialogLayout
										.createSequentialGroup()
										.addComponent(
												aboutSeparator,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												431, Short.MAX_VALUE)
										.addContainerGap()));
		aboutDialogLayout
				.setVerticalGroup(aboutDialogLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								aboutDialogLayout
										.createSequentialGroup()
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																aboutDialogLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				headingLabel)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				headingTwoLabel)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(descriptionLabelAbout)
										.addGap(18, 18, 18)
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																productLabel)
														.addComponent(
																versionNrLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																vendorLabel)
														.addComponent(
																vendorTextLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																authorsLabel)
														.addComponent(
																authorsTextLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												aboutDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																homepageLabel)
														.addComponent(
																homepageTextLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(cancelAboutButton)
										.addGap(1, 1, 1)
										.addComponent(
												aboutSeparator,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		getRentalsDialog.setTitle("Rental Overview");
		getRentalsDialog.setMinimumSize(new java.awt.Dimension(600, 200));
		getRentalsDialog.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				getRentalsDialogWindowClosing(evt);
			}
		});

		getRentalsTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "Rental ID", "Rental Days", "Rental Date",
						"Title", "Remaining Days", "Rental Fee" }) {
			private static final long serialVersionUID = -615724883768145787L;
			private Class<?>[] types = new Class[] { java.lang.Long.class,
					java.lang.Integer.class, java.lang.Object.class,
					java.lang.String.class, java.lang.Integer.class,
					java.lang.Double.class };
			private boolean[] canEdit = new boolean[] { false, false, false,
					false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		getRentalsTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				getRentalsTableMouseClicked(evt);
			}
		});
		getRentalsTable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				getRentalsTableKeyPressed(evt);
			}
		});
		getRentalsScrollPane.setViewportView(getRentalsTable);

		closeGetRentalsButton.setText("Close");
		closeGetRentalsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						closeGetRentalsButtonActionPerformed(evt);
					}
				});

		deleteGetRentalsButton.setText("Delete");
		deleteGetRentalsButton.setEnabled(false);
		deleteGetRentalsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						deleteGetRentalsButtonActionPerformed(evt);
					}
				});

		userGetRentalsLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		userGetRentalsLabel.setText("User:");
		

		javax.swing.GroupLayout getRentalsDialogLayout = new javax.swing.GroupLayout(
				getRentalsDialog.getContentPane());
		getRentalsDialog.getContentPane().setLayout(getRentalsDialogLayout);
		getRentalsDialogLayout
				.setHorizontalGroup(getRentalsDialogLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								getRentalsDialogLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(userGetRentalsLabel)
										.addGap(18, 18, 18)
										.addComponent(userGetRentalsTextLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												405, Short.MAX_VALUE)
										.addComponent(deleteGetRentalsButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(closeGetRentalsButton)
										.addContainerGap()).addComponent(
								getRentalsScrollPane,
								javax.swing.GroupLayout.DEFAULT_SIZE, 600,
								Short.MAX_VALUE));
		getRentalsDialogLayout
				.setVerticalGroup(getRentalsDialogLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								getRentalsDialogLayout
										.createSequentialGroup()
										.addComponent(
												getRentalsScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												144, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(
												getRentalsDialogLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																closeGetRentalsButton)
														.addComponent(
																deleteGetRentalsButton)
														.addComponent(
																userGetRentalsLabel)
														.addComponent(
																userGetRentalsTextLabel))
										.addContainerGap()));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("EAF Lab");
		setName("mainFrame");

		mainPane.setName("mainPane");
		mainPane.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				mainPaneStateChanged(evt);
			}
		});

		initRentMoviePanel(lastNameLabel, firstNameLabel, rentalDateLabel,
				rentalDaysLabel, userIdLabel);

		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(refreshButton1)
										.addGap(290, 290, 290)
										.addComponent(clearAllButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGap(18, 18, 18)
										.addComponent(saveRentalButton)
										.addContainerGap())
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																rentalDaysLabel)
														.addComponent(
																rentalDateLabel)
														.addComponent(
																firstNameLabel)
														.addComponent(
																lastNameLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				lastNameTextField,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				125,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				userIdLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				userIdForTextField,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				40,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																firstNameTextField)
														.addComponent(
																rentalDateTextField)
														.addComponent(
																rentalDaysTextField))
										.addGap(68, 68, 68)
										.addComponent(newUserCheckBox)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(getUserButton)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addComponent(
								movieScrollPane,
								javax.swing.GroupLayout.DEFAULT_SIZE, 571,
								Short.MAX_VALUE));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								mainPanelLayout
										.createSequentialGroup()
										.addComponent(
												movieScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												314, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lastNameLabel)
														.addComponent(
																lastNameTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																userIdLabel)
														.addComponent(
																userIdForTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																getUserButton)
														.addComponent(
																newUserCheckBox))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																firstNameLabel)
														.addComponent(
																firstNameTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																rentalDateLabel)
														.addComponent(
																rentalDateTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																rentalDaysLabel)
														.addComponent(
																rentalDaysTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																saveRentalButton)
														.addComponent(
																clearAllButton)
														.addComponent(
																refreshButton1))
										.addContainerGap()));

		mainPane.addTab("Rent Movie", mainPanel);

		movieCRUDPanel.setName("moviesTab");

		movieCRUDTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {}, {} }, new String[] {

				}));
		movieCRUDTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				movieCRUDTableMouseClicked(evt);
			}
		});
		movieCRUDTable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				movieCRUDTableKeyPressed(evt);
			}
		});
		movieCRUDScrollPane.setViewportView(movieCRUDTable);

		saveMoviesButton.setText("Save");
		saveMoviesButton.setEnabled(false);
		saveMoviesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveMoviesButtonActionPerformed(evt);
			}
		});

		newMoviesButton.setText("New ...");
		newMoviesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newMoviesButtonActionPerformed(evt);
			}
		});

		deleteMoviesButton.setText("Delete");
		deleteMoviesButton.setEnabled(false);
		deleteMoviesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						deleteMoviesButtonActionPerformed(evt);
					}
				});

		editMoviesButton.setText("Edit");
		editMoviesButton.setEnabled(false);
		editMoviesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editMoviesButtonActionPerformed(evt);
			}
		});

		movieTitleMoviesLabel.setText("Movie Title:");

		releaseDateMoviesLabel.setText("Release Date:");

		priceCatMoviesLabel.setText("Price category:");

		priceCatMoviesComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { "Children", "New Release", "Regular" }));
		priceCatMoviesComboBox
				.setToolTipText("Please select a price category.");
		priceCatMoviesComboBox.setEnabled(false);
		priceCatMoviesComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				priceCatMoviesComboBoxKeyPressed(evt);
			}
		});

		releaseDateMoviesTextField.setText(currDate);
		releaseDateMoviesTextField
				.setToolTipText("Please enter the release date of the movie.");
		releaseDateMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent evt) {
						priceCatMoviesComboBoxKeyPressed(evt);
					}
				});

		movieTitleMoviesTextField
				.setToolTipText("Please enter or re-enter the title of the movie.");
		movieTitleMoviesTextField.setEnabled(false);
		movieTitleMoviesTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent evt) {
						priceCatMoviesComboBoxKeyPressed(evt);
					}
				});

		cancelMoviesButton.setText("Cancel");
		cancelMoviesButton.setEnabled(false);
		cancelMoviesButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						cancelMoviesButtonActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout movieCRUDPanelLayout = new javax.swing.GroupLayout(
				movieCRUDPanel);
		movieCRUDPanel.setLayout(movieCRUDPanelLayout);
		movieCRUDPanelLayout
				.setHorizontalGroup(movieCRUDPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								movieCRUDPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(refreshButton2)
										.addContainerGap(149, Short.MAX_VALUE)
										.addComponent(cancelMoviesButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(newMoviesButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(editMoviesButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(deleteMoviesButton)
										.addGap(18, 18, 18).addComponent(
												saveMoviesButton)
										.addContainerGap())
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								movieCRUDPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																priceCatMoviesLabel)
														.addComponent(
																releaseDateMoviesLabel)
														.addComponent(
																movieTitleMoviesLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																movieTitleMoviesTextField,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																412,
																Short.MAX_VALUE)
														.addGroup(
																movieCRUDPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																		.addComponent(
																				releaseDateMoviesTextField,
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				priceCatMoviesComboBox,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()).addComponent(
								movieCRUDScrollPane,
								javax.swing.GroupLayout.DEFAULT_SIZE, 527,
								Short.MAX_VALUE));
		movieCRUDPanelLayout
				.setVerticalGroup(movieCRUDPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								movieCRUDPanelLayout
										.createSequentialGroup()
										.addComponent(
												movieCRUDScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												346, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																movieTitleMoviesLabel)
														.addComponent(
																movieTitleMoviesTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																releaseDateMoviesLabel)
														.addComponent(
																releaseDateMoviesTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																priceCatMoviesLabel)
														.addComponent(
																priceCatMoviesComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												movieCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																saveMoviesButton)
														.addComponent(
																deleteMoviesButton)
														.addComponent(
																editMoviesButton)
														.addComponent(
																newMoviesButton)
														.addComponent(
																cancelMoviesButton)
														.addComponent(
																refreshButton2))
										.addContainerGap()));

		mainPane.addTab("Movies",
				new javax.swing.ImageIcon(getClass().getResource(
						"/ch/fhnw/edu/rental/gui/resources/movieLogo.png")),
				movieCRUDPanel); // NOI18N

		usersCRUDPanel.setName("usersTab");

		usersCRUDTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {}, {} }, new String[] {

				}));
		usersCRUDTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				usersCRUDTableMouseClicked(evt);
			}
		});
		usersCRUDTable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				usersCRUDTableKeyPressed(evt);
			}
		});
		usersCRUDScrollPane.setViewportView(usersCRUDTable);

		saveUsersButton.setText("Save");
		saveUsersButton.setEnabled(false);
		saveUsersButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveUsersButtonActionPerformed(evt);
			}
		});

		deleteUsersButton.setText("Delete");
		deleteUsersButton.setEnabled(false);
		deleteUsersButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						deleteUsersButtonActionPerformed(evt);
					}
				});

		editUsersButton.setText("Edit");
		editUsersButton.setEnabled(false);
		editUsersButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editUsersButtonActionPerformed(evt);
			}
		});

		newUsersButton.setText("New ...");
		newUsersButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newUsersButtonActionPerformed(evt);
			}
		});

		firstNameUsersLabel.setText("First name:");

		cancelUsersButton.setText("Cancel");
		cancelUsersButton.setEnabled(false);
		cancelUsersButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						cancelUsersButtonActionPerformed(evt);
					}
				});

		firstNameUsersTextField.setEnabled(false);
		firstNameUsersTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				firstNameUsersTextFieldKeyPressed(evt);
			}
		});

		lastNameUsersLabel.setText("Last name:");

		lastNameUsersTextField.setEnabled(false);
		lastNameUsersTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				lastNameUsersTextFieldKeyPressed(evt);
			}
		});

		getRentalsButton.setText("Get Rentals");
		getRentalsButton.setToolTipText("Get rentals of the selected user.");
		getRentalsButton.setEnabled(false);
		getRentalsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getRentalsButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout usersCRUDPanelLayout = new javax.swing.GroupLayout(
				usersCRUDPanel);
		usersCRUDPanel.setLayout(usersCRUDPanelLayout);
		usersCRUDPanelLayout
				.setHorizontalGroup(usersCRUDPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								usersCRUDPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(refreshButton3)
										.addContainerGap(149, Short.MAX_VALUE)
										.addComponent(cancelUsersButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(newUsersButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(editUsersButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(deleteUsersButton)
										.addGap(18, 18, 18).addComponent(
												saveUsersButton)
										.addContainerGap())
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								usersCRUDPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												usersCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																firstNameUsersLabel)
														.addComponent(
																lastNameUsersLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												usersCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lastNameUsersTextField,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																282,
																Short.MAX_VALUE)
														.addComponent(
																firstNameUsersTextField,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																282,
																Short.MAX_VALUE))
										.addGap(53, 53, 53).addComponent(
												getRentalsButton)
										.addContainerGap()).addComponent(
								usersCRUDScrollPane,
								javax.swing.GroupLayout.DEFAULT_SIZE, 527,
								Short.MAX_VALUE));
		usersCRUDPanelLayout
				.setVerticalGroup(usersCRUDPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								usersCRUDPanelLayout
										.createSequentialGroup()
										.addComponent(
												usersCRUDScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												372, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(
												usersCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lastNameUsersLabel)
														.addComponent(
																lastNameUsersTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																getRentalsButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												usersCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																firstNameUsersLabel)
														.addComponent(
																firstNameUsersTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												usersCRUDPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																saveUsersButton)
														.addComponent(
																deleteUsersButton)
														.addComponent(
																editUsersButton)
														.addComponent(
																newUsersButton)
														.addComponent(
																cancelUsersButton)
														.addComponent(refreshButton3))
										.addContainerGap()));

		mainPane.addTab("Users",
				new javax.swing.ImageIcon(getClass().getResource(
						"/ch/fhnw/edu/rental/gui/resources/userLogo.png")),
				usersCRUDPanel); // NOI18N

		rentalsCRUDPanel.setName("rentalsTab");

		rentalsCRUDTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {}, {} }, new String[] {

				}));
		rentalsCRUDTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				rentalsCRUDTableMouseClicked(evt);
			}
		});
		rentalsCRUDTable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				rentalsCRUDTableKeyPressed(evt);
			}
		});
		rentalsCRUDScrollPane.setViewportView(rentalsCRUDTable);

		deleteRentalsButton.setText("Delete");
		deleteRentalsButton.setEnabled(false);
		deleteRentalsButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						deleteRentalsButtonActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout rentalsCRUDPanelLayout = new javax.swing.GroupLayout(
				rentalsCRUDPanel);
		rentalsCRUDPanel.setLayout(rentalsCRUDPanelLayout);
		rentalsCRUDPanelLayout.setHorizontalGroup(rentalsCRUDPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(rentalsCRUDScrollPane,
						javax.swing.GroupLayout.DEFAULT_SIZE, 527,
						Short.MAX_VALUE).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						rentalsCRUDPanelLayout.createSequentialGroup()
								.addContainerGap(149, Short.MAX_VALUE)
								.addComponent(deleteRentalsButton)
								.addContainerGap()));
		rentalsCRUDPanelLayout
				.setVerticalGroup(rentalsCRUDPanelLayout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						rentalsCRUDPanelLayout.createSequentialGroup()
								.addComponent(rentalsCRUDScrollPane,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										444, Short.MAX_VALUE)
								.addGap(18, 18, 18).addComponent(
										deleteRentalsButton).addContainerGap()));

		mainPane.addTab("Rentals", new javax.swing.ImageIcon(
				getClass().getResource(
						"/ch/fhnw/edu/rental/gui/resources/rentalLogo.png")),
				rentalsCRUDPanel); // NOI18N

		menuBar.setName("menuBar");

		fileMenu.setText("File");
		fileMenu.setName("fileMenu");

		exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q,
				java.awt.event.InputEvent.CTRL_MASK));
		exitMenuItem.setText("Exit");
		exitMenuItem.setName("exitMenuItem");
		exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitMenuItemActionPerformed(evt);
			}
		});
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		helpMenu.setText("Help");
		helpMenu.setName("helpMenu");

		aboutMenuItem.setText("About ...");
		aboutMenuItem.setName("aboutMenuItem");
		aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aboutMenuItemActionPerformed(evt);
			}
		});
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, 532,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, 530,
				Short.MAX_VALUE));

		pack();
	} // </editor-fold>

	// GEN-END:initComponents

	/**
	 * @param lastNameLabel
	 *            none.
	 * @param firstNameLabel
	 *            none.
	 * @param rentalDateLabel
	 *            none.
	 * @param rentalDaysLabel
	 *            none.
	 * @param userIdLabel
	 *            none.
	 */
	private void initRentMoviePanel(javax.swing.JLabel lastNameLabel,
			javax.swing.JLabel firstNameLabel,
			javax.swing.JLabel rentalDateLabel,
			javax.swing.JLabel rentalDaysLabel, javax.swing.JLabel userIdLabel) {
		mainPanel.setName("rentMovieTab");

		lastNameLabel.setText("Last Name:");
		lastNameLabel.setToolTipText("Please enter the last name of the customer.");

		firstNameLabel.setText("First Name:");
		firstNameLabel
				.setToolTipText("Please enter the first name of the customer.");

		rentalDateLabel.setText("Rental Date:");

		rentalDaysLabel.setText("Rental Days:");

		rentalDaysTextField.setEditable(true);
		rentalDaysTextField.setText("1");
		rentalDaysTextField
				.setToolTipText("Please enter how long the movie will be rented.");
		rentalDaysTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				rentalDaysTextFieldKeyPressed(evt);
			}
		});

		rentalDateTextField.setEditable(false);
		rentalDateTextField.setText(currDate);
		rentalDateTextField
				.setToolTipText("Rental date is by default todays date.");

		firstNameTextField.setEditable(true);
		firstNameTextField
				.setToolTipText("Please enter first name of the customer.");
		firstNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				firstNameTextFieldKeyPressed(evt);
			}
		});

		lastNameTextField.setEditable(true);
		lastNameTextField
				.setToolTipText("Please enter last name of the customer.");
		lastNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				lastNameTextFieldKeyPressed(evt);
			}
		});

		getUserButton.setText("get User");
		getUserButton
				.setToolTipText("Gets the entered user from the database.");
		getUserButton.setEnabled(false);
		getUserButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getUserButtonActionPerformed(evt);
			}
		});

		saveRentalButton.setText("Save");
		saveRentalButton.setEnabled(false);
		saveRentalButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveRentalButtonActionPerformed(evt);
			}
		});

		newUserCheckBox.setText("new User");
		newUserCheckBox
				.setToolTipText("Check if the user is not stored or find in the database.");
		newUserCheckBox.setEnabled(false);
		newUserCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newUserCheckBoxActionPerformed(evt);
			}
		});

		userIdLabel.setText("User ID:");

		clearAllButton.setText("Clear All");
		clearAllButton.setEnabled(false);
		clearAllButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resetRentalActionForm(evt);
			}
		});

		userIdForTextField.setEditable(false);
		userIdForTextField.setToolTipText("Enter user id to find the user.");
		userIdForTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				userIdForTextFieldKeyPressed(evt);
			}
		});

		movieTable.setModel(new javax.swing.table.DefaultTableModel(
				mappers.getMovieListAsObject(false), new String[] { "Movie ID",
				"Title", "Release Date", "Is Rented?", "Price Category" }) {
			private static final long serialVersionUID = -5376743898459692217L;
			private Class<?>[] types = new Class[] { java.lang.Long.class,
					java.lang.String.class, java.lang.Object.class,
					java.lang.Boolean.class, java.lang.Object.class };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieScrollPane.setViewportView(movieTable);
	}

	private void userIdForTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			getUserButtonActionPerformed(null);
			rentalDaysTextField.requestFocus();
		}
	}

	private void lastNameUsersTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			firstNameUsersTextField.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelUsersButtonActionPerformed(null);
		}
	}

	private void firstNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			rentalDaysTextField.requestFocus();
		}
	}

	private void priceCatMoviesComboBoxKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			saveMoviesButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveMoviesButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelMoviesButtonActionPerformed(null);
		}
	}

	private void firstNameUsersTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			saveUsersButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveUsersButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelUsersButtonActionPerformed(null);
		}
	}

	private void getRentalsTableKeyPressed(java.awt.event.KeyEvent evt) {
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			getRentalsTableMouseClicked(null);
			break;
		case KeyEvent.VK_DOWN:
			getRentalsTableMouseClicked(null);
			break;
		case KeyEvent.VK_ESCAPE:
			getRentalsButtonActionPerformed(null);
			break;
		case KeyEvent.VK_TAB:
			closeGetRentalsButton.requestFocus();
			break;
		default:
			// do nothing
			break;
		}
	}

	private void getRentalsDialogWindowClosing(java.awt.event.WindowEvent evt) {
		this.setEnabled(true);
	}

	private void aboutDialogWindowClosing(java.awt.event.WindowEvent evt) {
		this.setEnabled(true);
	}

	// Method which is called when getRentals is called on a selected user.
	// The only operations provided by this dialog is delete (and close)
	private void deleteGetRentalsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// get id of the selected rental
		int selRow = getRentalsTable.getSelectedRow();
		Long rentalId = (Long) getRentalsTable.getModel().getValueAt(selRow, 0);
		
		services.removeRental(rentalId);
		
		deleteGetRentalsButton.setEnabled(false);
		getRentalsTable.requestFocus();

		getRentalsButtonActionPerformed(null);
	}

	private void getRentalsTableMouseClicked(java.awt.event.MouseEvent evt) {
		deleteGetRentalsButton.setEnabled(true);
	}

	private void closeGetRentalsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		getRentalsDialog.setVisible(false);

		editUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		this.setEnabled(true);
		this.requestFocus();
	}

	private void getRentalsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		getRentalsDialog.setVisible(true);
		deleteGetRentalsButton.setEnabled(false);

		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);
		userGetRentalsTextLabel.setText(firstName + " " + lastName);

		// fill table
		getRentalsTable.setModel(new javax.swing.table.DefaultTableModel(
				mappers.getRentalListAsObjectForUser(userId),
				new String[] { "Rental ID", "Rental Days", "Rental Date",
						"Title", "Remaining Days", "Rental Fee" }) {
			private static final long serialVersionUID = 3969122560547437541L;
			private Class<?>[] types = new Class[] { java.lang.Long.class,
					java.lang.Integer.class, java.lang.Object.class,
					java.lang.String.class, java.lang.Integer.class,
					java.lang.Double.class };
			private boolean[] canEdit = new boolean[] { false, false, false,
					false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		this.setEnabled(false);
	}

	private void homepageTextLabelMouseClicked(java.awt.event.MouseEvent evt) {
		try {
			Desktop.getDesktop().browse(new URI("http://www.fhnw.ch"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ABOUT_ERR_MSG + ":\n"
					+ e.getLocalizedMessage());
		}
	}

	private void cancelAboutButtonActionPerformed(java.awt.event.ActionEvent evt) {
		aboutDialog.setVisible(false);
		this.setEnabled(true);
		this.requestFocus();
	}

	private void movieCRUDTableKeyPressed(java.awt.event.KeyEvent evt) {
		if (movieCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				movieCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DOWN:
				movieCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteMoviesButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				cancelMoviesButtonActionPerformed(null);
				break;
			case KeyEvent.VK_TAB:
				if (deleteMoviesButton.isEnabled()) {
					deleteMoviesButton.requestFocus();
				} else {
					editMoviesButton.requestFocus();
				}
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void usersCRUDTableKeyPressed(java.awt.event.KeyEvent evt) {
		if (usersCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				usersCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DOWN:
				usersCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteUsersButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				cancelUsersButtonActionPerformed(null);
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void rentalsCRUDTableKeyPressed(java.awt.event.KeyEvent evt) {
		if (rentalsCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				rentalsCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_UP:
				rentalsCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteRentalsButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				deleteRentalsButton.setEnabled(false);
				mainPaneStateChanged(null);
				break;
			case KeyEvent.VK_TAB:
				deleteRentalsButton.requestFocus();
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void editUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);

		lastNameUsersTextField.setText(lastName);
		firstNameUsersTextField.setText(firstName);

		lastNameUsersTextField.setEnabled(true);
		firstNameUsersTextField.setEnabled(true);

		newUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		cancelUsersButton.setEnabled(true);
		saveUsersButton.setEnabled(true);
		getRentalsButton.setEnabled(false);

		this.editMode = true;
	}

	private void deleteUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);

		if (services.getUserRentalsSize(userId) == 0) {
			try {
				services.deleteUser(userId);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"There are still rentals from \""
							+ firstName + " "
							+ lastName + "\".\n"
							+ "User can not be deleted.");
		}

		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		newUsersButton.setEnabled(true);
		cancelUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		usersCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void cancelUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {
		cancelUsersButton.setEnabled(false);
		newUsersButton.setEnabled(true);
		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		saveUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		lastNameUsersTextField.setEnabled(false);
		lastNameUsersTextField.setText("");
		firstNameUsersTextField.setEnabled(false);
		firstNameUsersTextField.setText("");

		usersCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void usersCRUDTableMouseClicked(java.awt.event.MouseEvent evt) {
		if (usersCRUDTable.getRowCount() > 0) {
			editUsersButton.setEnabled(true);
			deleteUsersButton.setEnabled(true);
			saveUsersButton.setEnabled(false);
			cancelUsersButton.setEnabled(false);
			newUsersButton.setEnabled(true);
			getRentalsButton.setEnabled(true);

			lastNameUsersTextField.setEnabled(false);
			lastNameUsersTextField.setText("");
			firstNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setText("");
		} else {
			cancelUsersButtonActionPerformed(null);
		}

	}

	private void saveUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String lastName = lastNameUsersTextField.getText();
		String firstName = firstNameUsersTextField.getText();

		if (!lastName.isEmpty()) {
			if (editMode) {
				// update user
				Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
				services.updateUser(userId, lastName, firstName);
				editMode = false;
			} else {
				// new user
				services.createUser(lastName, firstName);
			}

			lastNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setEnabled(false);

			newUsersButton.setEnabled(true);
			saveUsersButton.setEnabled(false);
			cancelUsersButton.setEnabled(false);
			deleteUsersButton.setEnabled(false);
			editUsersButton.setEnabled(false);
			getRentalsButton.setEnabled(false);

			mainPaneStateChanged(null);
		} else {
			JOptionPane.showMessageDialog(null, "Last name must not be empty.");
			lastNameTextField.requestFocus();
			newUsersButtonActionPerformed(null);
		}
	}

	private void newUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {
		lastNameUsersTextField.setEnabled(true);
		lastNameUsersTextField.setText("");
		firstNameUsersTextField.setEnabled(true);
		firstNameUsersTextField.setText("");

		newUsersButton.setEnabled(false);
		cancelUsersButton.setEnabled(true);
		saveUsersButton.setEnabled(true);
		editUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		lastNameUsersTextField.requestFocus();
	}

	private void cancelMoviesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		// reset buttons
		newMoviesButton.setEnabled(true);
		editMoviesButton.setEnabled(false);
		deleteMoviesButton.setEnabled(false);
		saveMoviesButton.setEnabled(false);
		cancelMoviesButton.setEnabled(false);

		// clear all text fields
		movieTitleMoviesTextField.setEnabled(false);
		movieTitleMoviesTextField.setText("");
		releaseDateMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField.setText("");

		priceCatMoviesComboBox.setEnabled(false);

		movieCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void editMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int rowCount = movieCRUDTable.getSelectedRow();
		Long movieId = (Long)movieCRUDTable.getModel().getValueAt(rowCount, 0);
		String title = services.getMovieTitle(movieId);
		String priceCategory = services.getMoviePriceCategory(movieId);
		Date releaseDate = services.getMovieReleaseDate(movieId);

		// fill text fields with information
		movieTitleMoviesTextField.setText(title);
		releaseDateMoviesTextField.setText(SDF.format(releaseDate));
		priceCatMoviesComboBox.setSelectedItem(priceCategory);

		movieTitleMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField.setEnabled(false);
		priceCatMoviesComboBox.setEnabled(true);

		saveMoviesButton.setEnabled(true);
		cancelMoviesButton.setEnabled(true);
		deleteMoviesButton.setEnabled(false);
		newMoviesButton.setEnabled(false);
		editMoviesButton.setEnabled(false);

		movieTitleMoviesTextField.requestFocus();

		// set edit mode
		this.editMode = true;
	}

	private void rentalsCRUDTableMouseClicked(java.awt.event.MouseEvent evt) {
		if (rentalsCRUDTable.getRowCount() > 0) {
			deleteRentalsButton.setEnabled(true);
		} else {
			deleteRentalsButton.setEnabled(false);
		}
	}

	// called when a rental is deleted on the rentals tab
	private void deleteRentalsButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		int selRow = rentalsCRUDTable.getSelectedRow();
		Long rentalId = (Long)rentalsCRUDTable.getModel().getValueAt(selRow, 0);
		
		services.removeRental(rentalId);
		
		deleteRentalsButton.setEnabled(false);
		rentalsCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void deleteMoviesButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		int rowCount = movieCRUDTable.getSelectedRow();
		Long movieId = (Long)movieCRUDTable.getModel().getValueAt(rowCount, 0);
		String title = services.getMovieTitle(movieId);
		boolean isRented = services.getMovieIsRented(movieId);

		if (!isRented) {
			services.deleteMovie(movieId);
		} else {
			JOptionPane.showMessageDialog(null, "The movie \""
					+ title + "\" is rented.\n"
					+ "Movie can not be deleted.");
		}

		deleteMoviesButton.setEnabled(false);
		editMoviesButton.setEnabled(false);
		newMoviesButton.setEnabled(true);
		cancelMoviesButton.setEnabled(false);

		movieCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void movieCRUDTableMouseClicked(java.awt.event.MouseEvent evt) {
		if (movieCRUDTable.getRowCount() > 0) {
			deleteMoviesButton.setEnabled(true);
			editMoviesButton.setEnabled(true);
			newMoviesButton.setEnabled(true);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);

			movieTitleMoviesTextField.setEnabled(false);
			movieTitleMoviesTextField.setText("");
			releaseDateMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setText("");

			priceCatMoviesComboBox.setEnabled(false);
		} else {
			cancelMoviesButtonActionPerformed(null);
		}
	}

	private void saveMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String movieTitle = movieTitleMoviesTextField.getText();
		String releaseDate = releaseDateMoviesTextField.getText();
		Date date = new Date();
		try {
			date = SDF.parse(releaseDate);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Date format can not be parsed and is replaced"
							+ " by the current date:\n"
							+ e.getLocalizedMessage());
		}

		if (!movieTitle.isEmpty()) {
			String category = (String) priceCatMoviesComboBox.getSelectedItem();
			if (editMode) {
				// update movie
				int rowCount = movieCRUDTable.getSelectedRow();
				Long movieId = (Long)movieCRUDTable.getModel().getValueAt(rowCount, 0);
				services.updateMovie(movieId, movieTitle, date, category);
				editMode = false;
			} else {
				// new movie
				services.createMovie(movieTitle, date, category);
			}

			// reset buttons and text fields
			movieTitleMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setEnabled(false);
			priceCatMoviesComboBox.setEnabled(false);

			newMoviesButton.setEnabled(true);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);
			editMoviesButton.setEnabled(false);
			deleteMoviesButton.setEnabled(false);

			mainPaneStateChanged(null);
		} else {
			JOptionPane
					.showMessageDialog(null, "Movie title must not be empty.");
			movieTitleMoviesTextField.requestFocus();
			newMoviesButtonActionPerformed(null);
		}
	}

	private void newMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// set all text fields enabled
		movieTitleMoviesTextField.setEnabled(true);
		movieTitleMoviesTextField.setText("");
		releaseDateMoviesTextField.setEnabled(true);
		releaseDateMoviesTextField.setText(currDate);
		priceCatMoviesComboBox.setEnabled(true);

		newMoviesButton.setEnabled(false);
		cancelMoviesButton.setEnabled(true);
		saveMoviesButton.setEnabled(true);

		movieTitleMoviesTextField.requestFocus();
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void mainPaneStateChanged(javax.swing.event.ChangeEvent evt) {
		// refresh data in tables when it is selected
		switch (mainPane.getSelectedIndex()) {
		case 0:
			// Rent Movie Tab
			// set new model with new data
			movieTable.setModel(new DefaultTableModel(
					mappers.getMovieListAsObject(false), new String[] {
					"Movie ID", "Title", "Release Date", "Is Rented?",
					"Price Category" }) {
				private static final long serialVersionUID = 2456659513544091063L;
				private Class<?>[] types = new Class[] { java.lang.Long.class,
						java.lang.String.class, java.lang.Object.class,
						java.lang.Boolean.class, java.lang.Object.class };
				private boolean[] canEdit = new boolean[] { false, false,
						false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			lastNameTextField.setEditable(true);
			lastNameTextField.setText("");
			firstNameTextField.setEditable(true);
			firstNameTextField.setText("");
			rentalDateTextField.setEditable(false);
			rentalDateTextField.setText(currDate);
			rentalDaysTextField.setEditable(true);
			rentalDaysTextField.setText("1");
			userIdForTextField.setEditable(true);
			userIdForTextField.setText("");

			newUserCheckBox.setSelected(false);
			newUserCheckBox.setEnabled(true);

			getUserButton.setEnabled(true);
			saveRentalButton.setEnabled(false);
			clearAllButton.setEnabled(true);

			break;
		case 1:
			// Movies Tab
			// set new model with new data
			movieCRUDTable.setModel(new DefaultTableModel(
					mappers.getMovieListAsObject(), new String[] { "Movie ID",
					"Title", "Release Date", "Is Rented?", "Price Category" }) {
				private static final long serialVersionUID = -8807187529192958223L;
				private Class<?>[] types = new Class[] { java.lang.Long.class,
						java.lang.String.class, java.lang.Object.class,
						java.lang.Boolean.class, java.lang.Object.class };
				private boolean[] canEdit = new boolean[] { false, false,
						false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			newMoviesButton.setEnabled(true);
			editMoviesButton.setEnabled(false);
			deleteMoviesButton.setEnabled(false);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);

			movieTitleMoviesTextField.setEnabled(false);
			movieTitleMoviesTextField.setText("");
			releaseDateMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setText("");

			priceCatMoviesComboBox.setEnabled(false);

			break;

		case 2:
			// Users Tab
			usersCRUDTable.setModel(new javax.swing.table.DefaultTableModel(
					mappers.getUserListAsObject(), new String[] { "User ID",
							"Last name", "First name" }) {
				private static final long serialVersionUID = 8362335490919602337L;
				private Class<?>[] types = new Class[] { java.lang.Long.class,
						java.lang.String.class, java.lang.String.class };
				private boolean[] canEdit = new boolean[] { false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			cancelUsersButton.setEnabled(false);
			newUsersButton.setEnabled(true);
			deleteUsersButton.setEnabled(false);
			editUsersButton.setEnabled(false);
			saveUsersButton.setEnabled(false);
			getRentalsButton.setEnabled(false);

			lastNameUsersTextField.setEnabled(false);
			lastNameUsersTextField.setText("");
			firstNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setText("");

			break;

		case 3:
			// Rentals Tab
			rentalsCRUDTable.setModel(new javax.swing.table.DefaultTableModel(
					mappers.getRentalListAsObject(), new String[] {
							"Rental ID", "Rental Days", "Rental Date",
							"Last name", "First name", "Title", "Remaining Days",
							"Rental fee" }) {
				private static final long serialVersionUID = 2849097606635036753L;
				private Class<?>[] types = new Class[] { java.lang.Long.class,
						java.lang.Integer.class, java.lang.Object.class,
						java.lang.String.class, java.lang.String.class,
						java.lang.String.class, java.lang.Integer.class,
						java.lang.Double.class };
				private boolean[] canEdit = new boolean[] { false, false,
						false, false, false, false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			deleteRentalsButton.setEnabled(false);

			break;

		default:
			JOptionPane.showMessageDialog(null, "Please select a tab.");
			break;
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void rentalDaysTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
			saveRentalButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveRentalButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void lastNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
			rentalDaysTextField.requestFocus();
			getUserButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void newUserCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
		// if new user is checked
		if (newUserCheckBox.isSelected()) {
			getUserButton.setEnabled(false);
			saveRentalButton.setEnabled(true);

			lastNameTextField.setEditable(true);
			firstNameTextField.setEditable(true);
			firstNameTextField.setText("");
			userIdForTextField.setEditable(false);
			userIdForTextField.setText("");

			if (lastNameTextField.getText().length() > 0) {
				firstNameTextField.requestFocus();
			} else {
				lastNameTextField.requestFocus();
			}
		} else {
			// new user is unchecked
			getUserButton.setEnabled(true);
			saveRentalButton.setEnabled(false);

			lastNameTextField.setEditable(true);
			lastNameTextField.setText("");
			firstNameTextField.setEditable(false);
			firstNameTextField.setText("");
			userIdForTextField.setEditable(true);
			userIdForTextField.setText("");
			userIdForTextField.requestFocus();
		}
	}

	private void saveRentalButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// get data from input fields
		String lastName = lastNameTextField.getText().trim();
		String firstName = firstNameTextField.getText().trim();
		String rentalDays = rentalDaysTextField.getText();


		// check selection in movie table
		if(movieTable.getSelectedRow() < 0){
			JOptionPane.showMessageDialog(null,	"select a movie to rent");
			return;
		}
		// check values in text fields
		if (!lastName.isEmpty() || !firstName.isEmpty()) {
			Long userId;
			if (newUserCheckBox.isSelected()) {
				userId = services.createUser(lastName, firstName);
			}
			else {
				userId = new Long(userIdForTextField.getText());
			}

			// save rental in list
			Long movieId = (Long)movieTable.getValueAt(movieTable.getSelectedRow(), 0);
			
			services.createRental(movieId, userId, Integer.valueOf(rentalDays));

			mainPaneStateChanged(null);

			// reset application
			movieTable.requestFocus();

			resetRentalActionForm(null);
		} else {
			JOptionPane.showMessageDialog(null,
					"first and last name must not be empty.");
			newUserCheckBoxActionPerformed(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void resetRentalActionForm(java.awt.event.ActionEvent evt) {
		lastNameTextField.setEditable(false);
		lastNameTextField.setText("");
		firstNameTextField.setEditable(false);
		firstNameTextField.setText("");
		rentalDateTextField.setEditable(false);
		rentalDateTextField.setText(currDate);
		rentalDaysTextField.setEditable(false);
		rentalDaysTextField.setText("1");
		userIdForTextField.setEditable(false);
		userIdForTextField.setText("");

		newUserCheckBox.setSelected(false);
		newUserCheckBox.setEnabled(false);

		getUserButton.setEnabled(false);
		saveRentalButton.setEnabled(false);
		clearAllButton.setEnabled(false);

		movieTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private boolean found = false;
	private boolean nameDoNotMatchId = false;
	private int userCount = 0;
	private void getUserButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// get last name and/or user id as search parameter
		final String name = lastNameTextField.getText().toLowerCase();
		String userId = userIdForTextField.getText();
		nameDoNotMatchId = false;
		found = false;
		userCount = 0;

		// name is not empty
		if (!name.isEmpty() && userId.isEmpty()) {
			// search for user in list
			services.visitUsers(new UserVisitor() {
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (lastName.toLowerCase().equals(name)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
						userCount++;
					}
				}
			});
		} else if (!userId.isEmpty() && name.isEmpty()
				&& Pattern.matches("\\d*", userId)) {
			final Long uId = Long.valueOf(userId).longValue();
			// search for user in list
			services.visitUsers(new UserVisitor(){
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (!found && id.equals(uId)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
					}
				}}
			);
		} else if (!name.isEmpty() && !userId.isEmpty()
				&& Pattern.matches("\\d*", userId)) {
			final Long uId = Long.valueOf(userId).longValue();
			services.visitUsers(new UserVisitor(){
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (!found && id.equals(uId) && lastName.toLowerCase().equals(name)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
						nameDoNotMatchId = false;
					} else {
						nameDoNotMatchId = true;
						found = false;
					}
				}}
			);
		} else {
			found = true;
			JOptionPane.showMessageDialog(null,
					"Please enter a new user or a valid "
							+ "'User Id' or 'Last name'.");
			userIdForTextField.requestFocus();
		}

		// check found value
		if (!found && !nameDoNotMatchId) {
			JOptionPane.showMessageDialog(null, "User not found in database.");
			lastNameTextField.requestFocus();
		}
		// name do not match user id
		if (nameDoNotMatchId) {
			JOptionPane.showMessageDialog(null,
					"User name do not match user id.");
			userIdForTextField.requestFocus();
		}
		// check if multiple users were found
		if (userCount > 1) {
			JOptionPane.showMessageDialog(null, "Multiple users found. Please,"
					+ " additionally enter the user id.");
			userIdForTextField.setEditable(true);
			firstNameTextField.setText("");
			userIdForTextField.requestFocus();
		}

		// activate save button
		if (!lastNameTextField.isEditable() && !firstNameTextField.isEditable()
				&& !userIdForTextField.isEditable()) {
			saveRentalButton.setEnabled(true);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		if (evt.getActionCommand().equals(exitMenuItem.getText())) {
			System.exit(JFrame.EXIT_ON_CLOSE);
		} // end of if
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		if (evt.getActionCommand().equals(aboutMenuItem.getText())) {
			aboutDialog.setVisible(true);
			this.setEnabled(false);
		} // end of if
	}

	/**
	 * @param args
	 *            none.
	 */


	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton refreshButton1;
	private javax.swing.JButton refreshButton2;
	private javax.swing.JButton refreshButton3;
	private javax.swing.JButton refreshButton4;

	private javax.swing.JDialog aboutDialog;
	/**
	 * none.
	 */
	private javax.swing.JMenuItem aboutMenuItem;
	/**
	 * none.
	 */
	private javax.swing.JSeparator aboutSeparator;
	/**
	 * none.
	 */
	private javax.swing.JButton cancelMoviesButton;
	/**
	 * none.
	 */
	private javax.swing.JButton cancelUsersButton;
	/**
	 * none.
	 */
	private javax.swing.JButton clearAllButton;
	/**
	 * none.
	 */
	private javax.swing.JButton closeGetRentalsButton;
	/**
	 * none.
	 */
	private javax.swing.JButton deleteGetRentalsButton;
	/**
	 * none.
	 */
	private javax.swing.JButton deleteMoviesButton;
	/**
	 * none.
	 */
	private javax.swing.JButton deleteRentalsButton;
	/**
	 * none.
	 */
	private javax.swing.JButton deleteUsersButton;
	/**
	 * none.
	 */
	private javax.swing.JButton editMoviesButton;
	/**
	 * none.
	 */
	private javax.swing.JButton editUsersButton;
	/**
	 * none.
	 */
	private javax.swing.JMenuItem exitMenuItem;
	/**
	 * none.
	 */
	private javax.swing.JTextField firstNameTextField;
	/**
	 * none.
	 */
	private javax.swing.JTextField firstNameUsersTextField;
	/**
	 * none.
	 */
	private javax.swing.JButton getRentalsButton;
	/**
	 * none.
	 */
	private javax.swing.JDialog getRentalsDialog;
	/**
	 * none.
	 */
	private javax.swing.JScrollPane getRentalsScrollPane;
	/**
	 * none.
	 */
	private javax.swing.JTable getRentalsTable;
	/**
	 * none.
	 */
	private javax.swing.JButton getUserButton;
	/**
	 * none.
	 */
	private javax.swing.JTabbedPane mainPane;
	/**
	 * none.
	 */
	private javax.swing.JPanel mainPanel;
	/**
	 * none.
	 */
	private javax.swing.JMenuBar menuBar;
	/**
	 * none.
	 */
	private javax.swing.JPanel movieCRUDPanel;
	/**
	 * none.
	 */
	private javax.swing.JTable movieCRUDTable;
	/**
	 * none.
	 */
	private javax.swing.JScrollPane movieScrollPane;
	/**
	 * none.
	 */
	private javax.swing.JTable movieTable;
	/**
	 * none.
	 */
	private javax.swing.JTextField movieTitleMoviesTextField;
	/**
	 * none.
	 */
	private javax.swing.JButton newMoviesButton;
	/**
	 * none.
	 */
	private javax.swing.JCheckBox newUserCheckBox;
	/**
	 * none.
	 */
	private javax.swing.JButton newUsersButton;
	/**
	 * none.
	 */
	private javax.swing.JComboBox<String> priceCatMoviesComboBox;
	/**
	 * none.
	 */
	private javax.swing.JTextField releaseDateMoviesTextField;
	/**
	 * none.
	 */
	private javax.swing.JTextField rentalDateTextField;
	/**
	 * none.
	 */
	private javax.swing.JTextField rentalDaysTextField;
	/**
	 * none.
	 */
	private javax.swing.JPanel rentalsCRUDPanel;
	/**
	 * none.
	 */
	private javax.swing.JTable rentalsCRUDTable;
	/**
	 * none.
	 */
	private javax.swing.JButton saveMoviesButton;
	/**
	 * none.
	 */
	private javax.swing.JButton saveRentalButton;
	/**
	 * none.
	 */
	private javax.swing.JButton saveUsersButton;
	/**
	 * none.
	 */
	private javax.swing.JTextField lastNameTextField;
	/**
	 * none.
	 */
	private javax.swing.JTextField lastNameUsersTextField;
	/**
	 * none.
	 */
	private javax.swing.JLabel userGetRentalsTextLabel;
	/**
	 * none.
	 */
	private javax.swing.JFormattedTextField userIdForTextField;
	/**
	 * none.
	 */
	private javax.swing.JPanel usersCRUDPanel;
	/**
	 * none.
	 */
	private javax.swing.JTable usersCRUDTable;
	// End of variables declaration//GEN-END:variables

	public BusinessLogic services;
//	private DataSet dataSet;
	private MovieRentalMappers mappers;

	private boolean editMode = false;

	private String currDate;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
}
