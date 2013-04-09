package ch.fhwn.algd1;

import java.lang.reflect.Array;
import java.nio.charset.Charset;

public class StringConverter {
	
	//Charset utf8charset = Charset.forName("UTF-8");
	//Charset iso88591charset = Charset.forName("ISO-8859-1");
	
	/***
	 * Converts UTF16 String to Latin1 
	 * @param s String to convert
	 * @return 
	 */
	static byte[] utfToLatin1(String s) {
		byte[] array = new byte[s.length()];
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// Check if Char is in Latin1 code area
			if (c >= 256 && (c < 0xdc00 || c >= 0xdfff)) {
				array[j++] = '?';
			} else if (c < 256) {
				array[j++] = (byte) c;
			} //else: Low-Surrogate
		}
		for (int i = j; i < s.length(); i++) {
			array[j] = (byte) 0;
		}
		
		return array;
	}
	static char[] codepoint2chars(int cp) {
		assert Character.isValidCodePoint(cp) : "illegal code point";
		if (cp < 0x10000) {
		if (cp >= 0xD800 && cp <= 0xDFFF)
			throw new IllegalArgumentException("illegal code point");
		return new char[] { (char) (cp & 0xFFFF) };
		} else {
			if (cp > 0x10FFFF)
				throw new IllegalArgumentException("illegal code point");
			cp -= 0x10000;
			char c1 = (char) ((cp >> 10) | 0xD800);
			char c2 = (char) ((cp & 0x3FF) | 0xDC00);
			return new char[] { c1, c2 };
		}
	}

	/***
	 * Converts UTF32 Character to UTF16 Character
	 * @param c UTF32 Character to convert
	 * @param array 
	 * @param pos Position in Array where to put the UTF16 value
	 * @return number of created Codepoints for convertion
	 */
	static int utf32to16(int c, char[] array, int pos){
		System.out.println(Integer.toBinaryString(c));
		System.out.println(Integer.toHexString(c));
		System.out.println(Integer.toOctalString(c));
		if (c > 0x10000) {
			c -= 0x10000;
			int char1 = 0xD8 + (c & 0xFFC00);
			int char2 = 0xDC + (c & 0x003FF);
			
			array[pos] = (char) char1;
			array[pos+1] = (char) char2;
			
			return 2;
		} else {
			
			array[pos] = (char) c;
			
			return 1;
		}
	}
}