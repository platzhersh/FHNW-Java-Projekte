package patterns.state.tickets;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TicketMachineGUI extends JFrame {
	private final JTextField destination = new JTextField();
	private final JTextField price = new JTextField();
	private final JTextField money = new JTextField();
	private final JCheckBox halfPrice = new JCheckBox("1/2");
	private final JCheckBox firstClass = new JCheckBox("1st");
	private final JCheckBox retour = new JCheckBox("<=>");
	
	private final TicketMachine t = new TicketMachine2();
	
	TicketMachineGUI() {
		this.setLayout(new GridLayout(0,1));
		this.add(destination);
		this.add(price); price.setEditable(false);
		this.add(halfPrice);
		this.add(firstClass);
		this.add(retour);
		this.add(money);
		
		destination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setDestination(Integer.parseInt(destination.getText()));
				update(t, t.getPrice());
			}
		});
		
		halfPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setHalfPrice(halfPrice.isSelected());
				update(t, t.getPrice());
			}
		});
		
		firstClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setFirstClass(firstClass.isSelected());
				update(t, t.getPrice());
			}
		});
		
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setReturnTicket(retour.isSelected());
				update(t, t.getPrice());
			}
		});
		
		money.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.enterMoney(Double.parseDouble(money.getText()));
				money.setText("");
				update(t, t.getPrice()-t.getEnteredMoney());
			}
		});
		
		update(t, 0);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void update(TicketMachine t, double p) {
		price.setText(String.format("%4.2f", p));
		halfPrice.setSelected(t.isHalfPrice());
		firstClass.setSelected(t.isFirstClass());
		retour.setSelected(t.isRetour());
		
		destination.setEnabled(t.isInStateInit());
		money.setEnabled(t.isInStateDestSelected() || t.isInStateMoneyEntered());
		halfPrice.setEnabled(t.isInStateDestSelected());
		firstClass.setEnabled(t.isInStateDestSelected());
		retour.setEnabled(t.isInStateDestSelected());
	}

	public static void main(String[] args) {
		JFrame f = new TicketMachineGUI();
		f.setVisible(true);
	}

}
