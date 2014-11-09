package patterns.clone.puzzle.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This non-instantiable non-extendible class provides a static clone() method
 * suitable for cloning an instance of any Serializable class.<P>
 * 
 * Threading-safety: this class is safe for use from mutliple concurrent threads.
 * 
 * @author (C) <a href="mailto:vroubtsov@illinoisalumni.org">Vlad Roubtsov</a>, 2002
 */
public final class SerializableClone {
    /**
     * Makes a Serialization-based deep clone of 'obj'.
     * 
     * @param obj input object to clone [null will cause a
     * NullPointerException]
     * @return obj's deep clone [never null; can be == to 'obj']
     * 
     * @throws RuntimeException on any failure
     */
	public static Object clone(final Object obj) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);

			oout.writeObject(obj);
			oout.flush();
			oout.close();

			// note: toByteArray() does a defensive data copy
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(out.toByteArray()));

			return in.readObject();
		} catch (Exception e) {
			throw new RuntimeException("cannot clone class ["
					+ obj.getClass().getName() + "] via serialization: "
					+ e.toString());
		}
	}

	private SerializableClone() { } // prevent instantiation

}
