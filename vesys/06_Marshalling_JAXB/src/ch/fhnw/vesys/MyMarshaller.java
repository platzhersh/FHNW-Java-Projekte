package ch.fhnw.vesys;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MyMarshaller {

	public static String marshall(Object object) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		StringWriter writer = new StringWriter();
		m.marshal(object, writer);
		
		return writer.toString();
	}
	
	public static <T> T unmarshall (String path) throws JAXBException {

		T object = null;
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Unmarshaller u = context.createUnmarshaller();
		
		u.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		File f = new File(path);
		return (T) u.unmarshal(f);
	}

	public static Student unmarshallSt (String path) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Student.class);
		Unmarshaller u = context.createUnmarshaller();
		
		// u.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		File f = new File(path);
		return (Student) u.unmarshal(f);
	}
}
