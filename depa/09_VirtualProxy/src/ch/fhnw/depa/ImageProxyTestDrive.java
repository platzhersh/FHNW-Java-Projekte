package ch.fhnw.depa;

import java.net.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class ImageProxyTestDrive {

	ImageComponent imageComponent;
	JFrame frame = new JFrame("CD Cover Viewer");
	JMenuBar menuBar;
	JMenu menu;
	Hashtable<String, String> cds = new Hashtable<String, String>();
	
	
	public static void main (String[] args) throws Exception {
		ImageProxyTestDrive testDrive = new ImageProxyTestDrive();
	}
	
	public ImageProxyTestDrive() throws Exception {
		
		cds.put("Platzh1rsch", "http://platzh1rsch.ch/media/platzh1rsch-profilepic.png");
		cds.put("Rodriguez - Searching for Sugar Man", "http://sugarman.org/images/Searching%20For%20Sugar%20Man%20%28Orange%29.jpg");
		
		
		URL initialUrl = new URL(cds.get("Platzh1rsch"));
		menuBar = new JMenuBar();
		menu = new JMenu("Favorite CDs");
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		for (Enumeration e = cds.keys(); e.hasMoreElements();) {
			String name = (String)e.nextElement();
			JMenuItem menuItem = new JMenuItem(name);
			menu.add(menuItem);
			menuItem.addActionListener(event -> {
				imageComponent.setIcon(new ImageProxy(getCDUrl(event.getActionCommand())));
				frame.repaint();
			});
		}
		
		// set up frame and menus
		Icon icon = new ImageProxy(initialUrl);
		imageComponent = new ImageComponent(icon);
		frame.getContentPane().add(imageComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
		
	}
	
	URL getCDUrl(String name) {
		try {
			return new URL(cds.get(name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
