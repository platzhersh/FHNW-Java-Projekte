package ch.fhnw.vesys;

public class Server2 {

	private int port;
	
	public Server2(int p) {
		this.port = p;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server2 srv = new Server2(4200);
		Thread ts = new Thread(new ThreadSocket(srv.port));
		ts.start();
		
		Server2 srv2 = new Server2(4201);
		Thread ts2 = new Thread(new ThreadSocket(srv2.port));
		ts2.start();

	}

}
