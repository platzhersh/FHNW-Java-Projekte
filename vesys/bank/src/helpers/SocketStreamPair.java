package helpers;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * Datastructure to easily store related Socket In- and Outputstreams
 * @author chregi
 *
 */
public class SocketStreamPair {

	public InputStream in;
	public OutputStream out;
	
	public SocketStreamPair(InputStream i, OutputStream o) {
		in = i;
		out = o;
	}

}
