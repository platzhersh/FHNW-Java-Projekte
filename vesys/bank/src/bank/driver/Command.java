package bank.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/***
 * The Command class is a static helper class to communicate between Client and Server
 * Commands have the following syntax:
 * command:param1,param2,param3,..paramN	(additional Info)
 * @author Christian Glatthard
 *
 */
public class Command {
	/***
	 * Send a Command including its parameters to the other end of the socket connection
	 * after the method got executed you may want to use this method to return the result
	 * use "success" as the @params cmd parameter in case of success, and "failure" in case of failure
	 * in case of failure you also may want to provide the exact Exception as @params params 
	 * @param cmd method you want to execute on the remote, or value you want to return
	 * @param params necessary arguments for the function to execute, or additional information regarding the return value. Separate multiple params with ",".
	 * @param s the socket that is used to send the command
	 * @throws IOException
	 */
	public static void send(String cmd, String params, Socket s) throws IOException {
		PrintWriter outP = new PrintWriter(s.getOutputStream());
		String msg = cmd +":"+params;
		outP.println(msg);
		outP.flush();
	}
	/***
	 * 
	 * @param cmd
	 * @param params
	 * @param info Provide additional info
	 * @param s
	 * @throws IOException
	 */
	public static void send(String cmd, String params, String info, Socket s) throws IOException {
		Command.send(cmd, params+"\t("+info+")", s);
	}
	
	/***
	 * Fetch commands sent to your socket. Commands get read per line. 
	 * @param s the socket that was used to send the command
	 * @return returns the received command string
	 * @throws IOException
	 */
	public static String receive(Socket s) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String input = br.readLine();
		return input;
	}
	/***
	 * Returns only the cmd part of a received command
	 * i.e. you receive "createAccount:max" this function returns only "createAccount"
	 * @param cmdlet
	 * @return
	 */
	public static String parseCommand(String cmd) {
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
	public static String[] parseParams(String cmd) {
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
	public static String parseInfo(String cmd) {
		return cmd.substring(cmd.indexOf("\t")+1,cmd.length()-1);
	}
	
	
}
