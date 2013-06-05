package ch.fhnw.cs.rmi;

import java.rmi.Naming;

public class ListRMIRegistry {
	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			String host = args[0];
			if (args.length > 1) {
				host = host + ":" + Integer.parseInt(args[1]);
			}
			String s[] = Naming.list("rmi://" + host);
			for (int i = 0; i < s.length; i++) {
				System.out.println(s[i]);
			}
		} else {
			System.out.println("Usage: java ListRMIREgistry "
					+ "<host> [<port>]");
		}
	}
}
