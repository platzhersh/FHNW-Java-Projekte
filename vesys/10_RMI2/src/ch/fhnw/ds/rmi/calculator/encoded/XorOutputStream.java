/*
 * Created on 15.01.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ch.fhnw.ds.rmi.calculator.encoded;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class XorOutputStream extends FilterOutputStream {

	/*
	 * The byte used to "encrypt" each byte of data.
	 */
	private final byte pattern;

	/* 
	 * Constructs an output stream that uses the specified pattern
	 * to "encrypt" each byte of data.
	 */
	public XorOutputStream(OutputStream out, byte pattern) {
		super(out);
		this.pattern = pattern;
	}

	/*
	 * XOR's the byte being written with the pattern
	 * and writes the result.  
	 */
	@Override
	public void write(int b) throws IOException {
		//System.out.print((char)b);
		out.write((b ^ pattern) & 0xFF);
	}
}
