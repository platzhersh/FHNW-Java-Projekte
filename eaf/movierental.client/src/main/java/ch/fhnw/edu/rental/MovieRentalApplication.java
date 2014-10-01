package ch.fhnw.edu.rental;

import ch.fhnw.edu.rental.gui.MovieRentalApplicationGui;
import ch.fhnw.edu.rental.services.BusinessLogic;

public class MovieRentalApplication {

	public static void main(String[] args) {
		BusinessLogic logic = null;
		if (args.length == 0) {
			System.err.println("Usage: MovieRentalApplication <classname>");
			System.exit(0);
		}
		String className = args[0];
		try {
			logic = (BusinessLogic) Class.forName(className).newInstance();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(0);
		}

		final BusinessLogic service = logic;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MovieRentalApplicationGui(service).setVisible(true);
			}
		});
	}

}
