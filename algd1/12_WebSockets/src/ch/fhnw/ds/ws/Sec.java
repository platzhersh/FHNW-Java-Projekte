package ch.fhnw.ds.ws;
import java.nio.charset.Charset;
import java.security.MessageDigest;


public class Sec {
	
	private static final String KEY_SUFFIX = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	
	public static String computeReturnKey(String key) throws Exception  {
		 MessageDigest md = MessageDigest.getInstance("SHA-1");
		 byte[] res = md.digest((key+KEY_SUFFIX).getBytes(Charset.forName("ascii")));
		 return Base64.encodeBytes(res);
	}
	
	public static void main(String[] args) throws Exception  {
		String key = "mqn5Pm7wtXEX6BzqDInLjw==";
		//key = "dGhlIHNhbXBsZSBub25jZQ==";
		System.out.println(key);
		System.out.println(computeReturnKey(key));
		
		System.out.println(new String(Base64.decode(key)));
	}

}
