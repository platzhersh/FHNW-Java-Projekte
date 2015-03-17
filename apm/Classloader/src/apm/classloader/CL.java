package apm.classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class CL {
	
	static Class<?> newClass(String path, String classname) throws Exception {
		URL url = new File(path).toURI().toURL();
		URLClassLoader cl = new URLClassLoader(new URL[] {url});	
		Class<?> c = cl.loadClass(classname);	
		cl.close();						
		
		// get Instance of class with Reflection and call getInstance() Method to run the constructor
		Method m = c.getDeclaredMethod("getInstance", new Class<?>[] {});
		m.invoke(null, (Object[]) null);
		
		return c;
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		
		// Uncomment to load the Singleton class via class path
		// this way, the class will only be loaded once, by the ClassPathLoader
		// String projdir = "/";
		
		// load class from different directory than bin
		// used to isolate classes with same namespace
		// this way, each URLClassLoader instance loads the Singleton class separately
		String projdir = System.getProperty("user.dir") + "\\notonclasspath\\";
		System.out.println(projdir);
		
		newClass(projdir, "apm.classloader.Singleton");
		newClass(projdir, "apm.classloader.Singleton");
		newClass(projdir, "apm.classloader.Singleton");
		newClass(projdir, "apm.classloader.Singleton");
		newClass(projdir, "apm.classloader.Singleton");
		
		System.out.println("done");
	}

}
