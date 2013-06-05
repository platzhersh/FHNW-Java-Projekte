package ch.fhnw.ds.rmi.quotes;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;

public class FrameClient extends Frame {

	private TextArea output;
	private TextField input;
	private QuoteServer server;

	public FrameClient(String title) throws Exception {
		super(title);

		output = new TextArea();
		input = new TextField();
		output.setEditable(false);

		setLayout(new BorderLayout());
		add("Center", output);
		add("South", input);

		// server = (IQuoteServer)Naming.lookup("rmi://<server>/QuoteServer");
		server = (QuoteServer) Naming.lookup("rmi://localhost/QuoteServer");

		server.addQuoteListener(new AbstractQuoteListener() {
			public void update(String s) {
				output.append(s + "\n");
			}
		});

		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.addQuote(input.getText());
				} catch (java.rmi.RemoteException ex) {
				}
				input.setText("");
			}
		});

		pack();
		input.requestFocus();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String args[]) throws Exception {
		Frame f = new FrameClient("Quotes of the day");
		f.setVisible(true);
	}
}
