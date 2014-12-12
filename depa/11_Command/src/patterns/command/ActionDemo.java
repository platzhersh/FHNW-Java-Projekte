package patterns.command;

/*
 * @(#)ActionDemo.java	1.6 00/04/18
 *
 * Copyright 2000 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.EventListenerList;

/**
 * Demonstrates the JLF Actions classes.
 *
 * - The actions use the JLF icons, text and mnemonics.
 * - Actions are shared between the JToolBar and JMenuBar.
 * - if an Action is enabled/disabled then it will be disabled in both places.
 * - When a mouse is over a toolbar button or a menu item, then the long
 *   description of that action will be displayed in the status bar.
 * - Abstracts the actionPerformed method from the Action class to a handler.
 *
 * This demo requires JDK 1.3
 *
 * @version 1.6 04/18/00
 * @author  Mark Davidson
 */
public class ActionDemo extends JFrame implements ActionListener {
	
    // These are the actions defined for the application
    private AboutAction aboutAction;
    private CutAction cutAction;
    private CopyAction copyAction;
    private PasteAction pasteAction;

    // Vector for holding all the actions.
    private java.util.List<Action> actions;

    // Status bar
    private JLabel status;

    // Text area which acts as a clipboard.
    private JTextArea textArea;

    // This adapter handles Mouse over messages on toolbar buttons and
    // menu items.
    private MouseHandler mouseHandler;

    // Popup Menu with the actions.
    private JPopupMenu popup;

    public ActionDemo() {
        super("Actions Demo");
        initActions();

        status = createStatusBar();
        mouseHandler = new MouseHandler(status);

        setJMenuBar(createMenu());
        getContentPane().add(createToolBar(), BorderLayout.NORTH);
        getContentPane().add(createPanel(), BorderLayout.CENTER);
        getContentPane().add(status, BorderLayout.SOUTH);

        popup = createPopupMenu();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    // This method should be called before creating the UI
    // to create all the Actions
    private void initActions()  {
        actions = new ArrayList<Action>();

        aboutAction = new AboutAction();
        registerAction(aboutAction);

        cutAction = new CutAction();
        registerAction(cutAction);

        copyAction = new CopyAction();
        registerAction(copyAction);

        pasteAction = new PasteAction();
        registerAction(pasteAction);
    }

    private void registerAction(JLFAbstractAction action)  {
        action.addActionListener(this);
        actions.add(action);
    }

    // Creates the application menu bar.
    private JMenuBar createMenu()  {
        JMenuBar menuBar = new JMenuBar();

        JMenuItem menuItem;

        // Build the File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menuItem = fileMenu.add(aboutAction);
        menuItem.addMouseListener(mouseHandler);

        // Build the edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        menuItem = editMenu.add(cutAction);
        menuItem.addMouseListener(mouseHandler);
        menuItem = editMenu.add(copyAction);
        menuItem.addMouseListener(mouseHandler);
        menuItem = editMenu.add(pasteAction);
        menuItem.addMouseListener(mouseHandler);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        return menuBar;
    }

    private JToolBar createToolBar()  {
        JToolBar toolbar = new JToolBar();

        JButton button;

        button = toolbar.add(cutAction);
        button.addMouseListener(mouseHandler);
        button = toolbar.add(copyAction);
        button.addMouseListener(mouseHandler);
        button = toolbar.add(pasteAction);
        button.addMouseListener(mouseHandler);
        toolbar.addSeparator();
        button = toolbar.add(aboutAction);
        button.addMouseListener(mouseHandler);

        return toolbar;
    }

    private JPopupMenu createPopupMenu()  {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem menuItem;

        menuItem = menu.add(cutAction);
        menuItem.addMouseListener(mouseHandler);
        menuItem = menu.add(copyAction);
        menuItem.addMouseListener(mouseHandler);
        menuItem = menu.add(pasteAction);
        menuItem.addMouseListener(mouseHandler);
        menu.addSeparator();
        menuItem = menu.add(aboutAction);
        menuItem.addMouseListener(mouseHandler);

        return menu;
    }

    // Panel which allows for the enabling and disabling of all the actions.
    private JPanel createPanel()  {
        // This check box enables or disables all the actions.
        JCheckBox enable = new JCheckBox("Enable All Actions", true);
        enable.setAlignmentX(0.5f);
        enable.addItemListener(new ItemListener()  {
            public void itemStateChanged(ItemEvent evt)  {
                boolean enabled = false;

                if (evt.getStateChange() == ItemEvent.SELECTED)  {
                    // Enabled all actions
                    enabled = true;
                }

                for(Action a : actions)
                	a.setEnabled(enabled);
            }
        });

        JLabel label = new JLabel("Right click in Text Area for popup menu");
        label.setAlignmentX(0.5f);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.addMouseListener(new MouseAdapter() {
            @Override
			public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    popup.show(textArea, e.getX(), e.getY());
                }
            }
            @Override
			public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    popup.show(textArea, e.getX(), e.getY());
                }
            }
        });

        JPanel p1 = new JPanel();
        p1.add(enable);
        p1.add(label);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(450, 200));
        panel.add(p1, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Creates the status bar.
    private JLabel createStatusBar()  {
        status = new JLabel("Ready");
        status.setBorder(BorderFactory.createEtchedBorder());

        return status;
    }

    /**
     * This method acts as the Action handler delegate for all the actions.
     * The Cut, Copy and Paste Actions operate on the JTextArea.
     */
    public void actionPerformed(ActionEvent evt)  {
        String command = evt.getActionCommand();

        // Compare the action command to the known actions.
        if (command.equals(aboutAction.getActionCommand()))  {
            // The about action was invoked
            JOptionPane.showMessageDialog(this, aboutAction.getLongDescription(),
                        aboutAction.getShortDescription(),
					  JOptionPane.INFORMATION_MESSAGE);
        } else if (command.equals(cutAction.getActionCommand())) {
	    textArea.cut();
        } else if (command.equals(copyAction.getActionCommand())) {
	    textArea.copy();
        } else if (command.equals(pasteAction.getActionCommand())) {
	    textArea.paste();
        }
    }

    /**
     * This adapter is constructed to handle mouse over component events.
     */
    private class MouseHandler extends MouseAdapter  {

        private JLabel label;

        /**
         * ctor for the adapter.
         * @param label the JLabel which will recieve value of the
         *              Action.LONG_DESCRIPTION key.
         */
        public MouseHandler(JLabel label)  {
            setLabel(label);
        }

        public void setLabel(JLabel label)  {
            this.label = label;
        }

        public @Override void mouseEntered(MouseEvent evt)  {
            if (evt.getSource() instanceof AbstractButton)  {
                AbstractButton button = (AbstractButton)evt.getSource();
                Action action = button.getAction(); // getAction is new in JDK 1.3
                if (action != null)  {
                    String message = (String)action.getValue(Action.LONG_DESCRIPTION);
                    label.setText(message);
                }
            }
        }
    }

    /**
     * Main method
     */
    public static void main(String[] args)  {
        ActionDemo demo = new ActionDemo();
        demo.pack();
        demo.setVisible(true);
    }
}

class PasteAction extends JLFAbstractAction {

    private static final String ACTION_COMMAND_KEY_PASTE = "paste-command";
    private static final String NAME_PASTE = "Paste";
    private static final String SMALL_ICON_PASTE = "Paste16.gif";
    private static final String LARGE_ICON_PASTE = "Paste24.gif";
    private static final String SHORT_DESCRIPTION_PASTE = "Paste";
    private static final String LONG_DESCRIPTION_PASTE = "Insert an object or data previously selected via \"Copy\" or \"Cut\"";
    private static final int MNEMONIC_KEY_PASTE = 'P';

    public PasteAction() {
        putValue(Action.NAME, NAME_PASTE);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_PASTE));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_PASTE));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_PASTE);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_PASTE);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_PASTE));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_PASTE);
    }
}

class CutAction extends JLFAbstractAction {

    private static final String ACTION_COMMAND_KEY_CUT = "cut-command";
    private static final String NAME_CUT = "Cut";
    private static final String SMALL_ICON_CUT = "Cut16.gif";
    private static final String LARGE_ICON_CUT = "Cut24.gif";
    private static final String SHORT_DESCRIPTION_CUT = "Cut";
    private static final String LONG_DESCRIPTION_CUT = "Remove the selected item from its current context. It is now available to be pasted elsewhere.";
    private static final int MNEMONIC_KEY_CUT = 'T';

    /**
     * ctor
     */
    public CutAction() {
        putValue(Action.NAME, NAME_CUT);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_CUT));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_CUT));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_CUT);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_CUT);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_CUT));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_CUT);
    }
}

class CopyAction extends JLFAbstractAction {

    private static final String ACTION_COMMAND_KEY_COPY = "copy-command";
    private static final String NAME_COPY = "Copy";
    private static final String SMALL_ICON_COPY = "Copy16.gif";
    private static final String LARGE_ICON_COPY = "Copy24.gif";
    private static final String SHORT_DESCRIPTION_COPY = "Copy";
    private static final String LONG_DESCRIPTION_COPY = "Create a duplicate of the selected object. This duplicate is now available to be pasted elsewhere.";
    private static final int MNEMONIC_KEY_COPY = 'C';

    /**
     * ctor
     */
    public CopyAction() {
        putValue(Action.NAME, NAME_COPY);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_COPY));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_COPY));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_COPY);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_COPY);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_COPY));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_COPY);
    }
}

class AboutAction extends JLFAbstractAction {

    private static final String ACTION_COMMAND_KEY_ABOUT = "about-command";
    private static final String NAME_ABOUT = "About";
    private static final String SMALL_ICON_ABOUT = "About16.gif";
    private static final String LARGE_ICON_ABOUT = "About24.gif";
    private static final String SHORT_DESCRIPTION_ABOUT = "About the Application";
    private static final String LONG_DESCRIPTION_ABOUT = "Provide information regarding the application";
    private static final int MNEMONIC_KEY_ABOUT = 'A';

    /**
     * ctor
     */
    public AboutAction() {
        putValue(Action.NAME, NAME_ABOUT);
        putValue(Action.SMALL_ICON, getIcon(SMALL_ICON_ABOUT));
        putValue(LARGE_ICON, getIcon(LARGE_ICON_ABOUT));
        putValue(Action.SHORT_DESCRIPTION, SHORT_DESCRIPTION_ABOUT);
        putValue(Action.LONG_DESCRIPTION, LONG_DESCRIPTION_ABOUT);
        putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_KEY_ABOUT));
        putValue(Action.ACTION_COMMAND_KEY, ACTION_COMMAND_KEY_ABOUT);
    }

}

abstract class JLFAbstractAction extends AbstractAction {

    // The listener to action events (usually the main UI)
    private EventListenerList listeners;

    // Image directory URL
    public static final String JLF_IMAGE_DIR = "/toolbarButtonGraphics/general/";

    /**
     * The key used for storing a large icon for the action,
     * used for toolbar buttons.
     * <p>
     * Note: Eventually this key belongs in the javax.swing.Action interface.
     */
    public static final String LARGE_ICON = "LargeIcon";

    //
    // These next public methods may belong in the AbstractAction class.
    //

    /**
     * Gets the value from the key Action.ACTION_COMMAND_KEY
     */
    public String getActionCommand()  {
        return (String)getValue(Action.ACTION_COMMAND_KEY);
    }

    /**
     * Gets the value from the key Action.SHORT_DESCRIPTION
     */
    public String getShortDescription()  {
        return (String)getValue(Action.SHORT_DESCRIPTION);
    }

    /**
     * Gets the value from the key Action.LONG_DESCRIPTION
     */
    public String getLongDescription()  {
        return (String)getValue(Action.LONG_DESCRIPTION);
    }

    /* Should finish the implementation and add get/set methods for all the
     * javax.swing.Action keys:

        Action.NAME
        Action.SMALL_ICON
        ActionConstants.LARGE_ICON
        Action.MNEMONIC_KEY
     */


    // ActionListener registration and invocation.

    /**
     * Forwards the ActionEvent to the registered listener.
     */
    public void actionPerformed(ActionEvent evt)  {
        if (listeners != null) {
            Object[] listenerList = listeners.getListenerList();

            // Recreate the ActionEvent and stuff the value of the ACTION_COMMAND_KEY
            ActionEvent e = new ActionEvent(evt.getSource(), evt.getID(),
                                            (String)getValue(Action.ACTION_COMMAND_KEY));
            for (int i = 0; i <= listenerList.length-2; i += 2) {
                ((ActionListener)listenerList[i+1]).actionPerformed(e);
            }
        }
    }

    public void addActionListener(ActionListener l)  {
        if (listeners == null) {
            listeners = new EventListenerList();
	}
        listeners.add(ActionListener.class, l);
    }

    public void removeActionListener(ActionListener l)  {
	if (listeners == null) {
	    return;
	}
        listeners.remove(ActionListener.class, l);
    }

    /**
     * Returns the Icon associated with the name from the resources.
     * The resouce should be in the path.
     * @param name Name of the icon file i.e., help16.gif
     * @return the name of the image or null if the icon is not found.
     */
    public ImageIcon getIcon(String name)  {
        String imagePath = JLF_IMAGE_DIR + name;
        URL url = this.getClass().getResource(imagePath);
        if (url != null)  {
            return new ImageIcon(url);
        }
        return null;
    }
}
