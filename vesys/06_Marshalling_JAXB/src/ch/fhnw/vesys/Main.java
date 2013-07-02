package ch.fhnw.vesys;

import javax.xml.bind.JAXBException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student s = new Student();
		
		s.setFirstName("chregi");
		s.setLastName("glatthard");
		s.setId(1234);
		
		try {
			System.out.println(MyMarshaller.marshall(s));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Student s2 = MyMarshaller.unmarshallSt("lib\\student.xml");
			System.out.println(s2.getFirstName());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
