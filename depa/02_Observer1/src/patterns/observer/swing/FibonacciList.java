package patterns.observer.swing;

import java.math.BigInteger;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FibonacciList extends JFrame {
	FibonacciList() {
		final JTextField field = new JTextField();
		final JList<BigInteger> list = new JList<BigInteger>(
				new AbstractListModel<BigInteger>() {
					public int getSize() {
						return 1000;
					}

					public BigInteger getElementAt(int n) {
						// return n'th fibonacci number
						System.out.println("getElementAt(" + n + ")");
						if (n == 0) {
							return BigInteger.ZERO;
						} else {
							BigInteger f0 = BigInteger.ZERO;
							BigInteger f1 = BigInteger.ONE;
							for (int i = 1; i < n; i++) {
								BigInteger f2 = f0.add(f1);
								f0 = f1;
								f1 = f2;
							}
							return f1;
						}
					}
				});
		list.setVisibleRowCount(5);
		list.setFixedCellHeight(18);
		list.setFixedCellWidth(500);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				BigInteger sum = BigInteger.ZERO;
				for (BigInteger bi : list.getSelectedValuesList()) {
					sum = sum.add(bi);
				}
				field.setText(sum.toString());
			}
		});
		getContentPane().add(new JScrollPane(list));
		getContentPane().add("South", field);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		JFrame f = new FibonacciList();
		f.pack();
		f.setVisible(true);
	}
}