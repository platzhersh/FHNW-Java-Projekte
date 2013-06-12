package helpers;

import java.io.IOException;
import java.net.Socket;

public abstract class CommandHandler<T> {
	
	/* ------------- member variables ------------------ */
	
	protected T s;		// Can be anything, just needs to be able to send / receive the messages
	
	/* ------------- Constructor -----------------------*/
	
	public CommandHandler (T sock) {
		this.s = sock;
	}
	
	/* ------------- To be implemented ---------------- */
	public abstract void send(String cmd, String params) throws IOException;
	public abstract String receive() throws IOException;
	
	/***
	 * 
	 * @param cmd
	 * @param params
	 * @param info Provide additional info
	 * @param s
	 * @throws IOException
	 */
	public void send(String cmd, String params, String info) throws IOException {
		this.send(cmd, params+"\t("+info+")");
	}
	
	/***
	 * Returns only the cmd part of a received command
	 * i.e. you receive "createAccount:max" this function returns only "createAccount"
	 * @param cmdlet
	 * @return
	 */
	public String parseCommand(String cmd) {
		return cmd.substring(0, cmd.indexOf(":"));
	}
	/***
	 * Returns an array of all the params
	 * i.e. you receive "transfer:S01-000001,S01-000002,200" this function returns an Array of three Strings
	 * [0] = "S01-000001"
	 * [1] = "S01-000002"
	 * [2] = "200"
	 * @param cmd
	 * @return
	 */
	public String[] parseParams(String cmd) {
		String cmd2 = cmd.substring(cmd.indexOf(":")+1,cmd.length());
		if (cmd2.contains("\t")) cmd2 = cmd2.substring(0,cmd2.indexOf("\t"));
		return cmd2.split(",");
	}
	/***
	 * Returns the additional info part of the received command
	 * i.e. you receive "SUCCESS:S01-0000001	(createAccount:S01-0000001)", this function returns "createAccount:S01-0000001"
	 * @param cmd
	 * @return
	 */
	public String parseInfo(String cmd) {
		return cmd.substring(cmd.indexOf("\t")+1,cmd.length()-1);
	}
	
	
}
