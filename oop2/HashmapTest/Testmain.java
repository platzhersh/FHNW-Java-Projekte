import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Random;

public class Testmain {
	
	public static void main(String[] args) 
	{
//*******************************************************************		
//Vornamen von Datei einlesen
		String[] vornamen=new String[10000]; //String[#Datensätze in File]
		try {
			BufferedReader in = new BufferedReader(new FileReader("vornamen.csv"));
			String zeile = null;
			int i=0;
			while ((zeile = in.readLine()) != null) {
				vornamen[i]=zeile;
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//*******************************************************************			
//Nachnamen von Datei einlesen
		String[] nachnamen=new String[10000];//String[#Datensätze in File]
		try {
			BufferedReader in = new BufferedReader(new FileReader("nachnamen.csv"));
			String zeile = null;
			int i=0;
			while ((zeile = in.readLine()) != null) {
				nachnamen[i]=zeile;
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//*******************************************************************			
//Zufallspersonen erzeugen
		int AnzahlPersonen=10000; //Gewünschte Anzahl Zufallspersonen eingeben!!!!!
		Person[] Personarray=new Person[AnzahlPersonen];
		Random zufallint=new Random();
		for(int i=0;i<AnzahlPersonen;i++)
		{
			int zufall1=zufallint.nextInt(AnzahlPersonen);
			int zufall2=zufallint.nextInt(AnzahlPersonen);
			Personarray[i]=new Person(i,vornamen[zufall1%10000],nachnamen[zufall2%10000]);
		}
		System.out.print("Anzahl Elemente = ");
		System.out.print(AnzahlPersonen);
		System.out.print("\n");
		System.out.print("\n");

//*******************************************************************		
//ArrayList
		ArrayList<Person> personStruct1=new ArrayList<Person>();
  //Einfügen der Zufallspersonen in ArrayListe
		long Alist_einfügen=System.currentTimeMillis();
		for(int i=0; i<AnzahlPersonen;i++)
		{
			personStruct1.add(Personarray[i]);
		}
		long Alist_einfügen2=System.currentTimeMillis();
		
 //Suchen nach allen Personen
		long Alist_suchen=System.currentTimeMillis();
		for(int i=0;i<AnzahlPersonen;i++)
		{
			personStruct1.indexOf(Personarray[i]);
		}
		long Alist_suchen2=System.currentTimeMillis();
		
//Löschen aller Elemente
		long Alist_deleteall=System.currentTimeMillis();
		personStruct1.clear();
		long Alist_deleteall2=System.currentTimeMillis();
		
//Ausgeben der Zeitdifferenzen
		System.out.println("ArrayList");
		System.out.print("Einfügen aller Elemente: t = ");
		System.out.print(Alist_einfügen2-Alist_einfügen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Suchen aller Elemente:  t = ");
		System.out.print(Alist_suchen2-Alist_suchen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Löschen aller Elemente: t = ");
		System.out.print(Alist_deleteall2-Alist_deleteall);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("\n");
		
//*******************************************************************		
//TreeSet
		TreeSet<Person> personStruct2=new TreeSet<Person>();
  //Einfügen der Zufallspersonen in den Baum
		long Tree_einfügen=System.currentTimeMillis();
		for(int i=0; i<AnzahlPersonen;i++)
		{
			personStruct2.add(Personarray[i]);
		}
		long Tree_einfügen2=System.currentTimeMillis();

//Suchen nach allen Elementen
		long Tree_suchen=System.currentTimeMillis();
		for(int i=0;i<AnzahlPersonen;i++)
		{
			personStruct2.contains(Personarray[i]);
		}
		long Tree_suchen2=System.currentTimeMillis();
		
//Löschen aller Elemente
		long Tree_deleteall=System.currentTimeMillis();
		personStruct2.clear();
		long Tree_deleteall2=System.currentTimeMillis();

//Ausgeben der Zeitdifferenzen
		System.out.println("ThreeSet");
		System.out.print("Einfügen aller Elemente: t = ");
		System.out.print(Tree_einfügen2-Tree_einfügen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Suchen aller Elemente:  t = ");
		System.out.print(Tree_suchen2-Tree_suchen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Löschen aller Elemente: t = ");
		System.out.print(Tree_deleteall2-Tree_deleteall);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("\n");
  
//*******************************************************************
//HashMap
		HashMap<Integer,Person> personStruct3=new HashMap<Integer,Person>();
 //Einfügen der Zufallspersonen in den Hashtabelle
		long Hash_einfügen=System.currentTimeMillis();		
		for(int i=0; i<AnzahlPersonen;i++)
		{
			personStruct3.put(new Integer(i), Personarray[i]);
		}
		long Hash_einfügen2=System.currentTimeMillis();

//Suchen nach allen Elementen nach Inhalt
		long Hash_suchen=System.currentTimeMillis();
		for(int i=0;i<AnzahlPersonen;i++)
		{
			personStruct3.containsValue(Personarray[i]);
		}
		long Hash_suchen2=System.currentTimeMillis();

//Suchen nach allen Elementen nach Inhalt
		long Hash_suchenkey=System.currentTimeMillis();
		for(int i=0;i<AnzahlPersonen;i++)
		{
			personStruct3.containsKey(i);
		}
		long Hash_suchenkey2=System.currentTimeMillis();

//Löschen aller Elemente
		long Hash_deleteall=System.currentTimeMillis();
		personStruct2.clear();
		long Hash_deleteall2=System.currentTimeMillis();

//Ausgeben der Zeitdifferenzen
		System.out.println("HashMap");
		System.out.print("Einfügen aller Elemente: t = ");
		System.out.print(Hash_einfügen2-Hash_einfügen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Suchen aller Elemente nach Inhalt:  t = ");
		System.out.print(Hash_suchen2-Hash_suchen);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Suchen aller Elemente nach Key:  t = ");
		System.out.print(Hash_suchenkey2-Hash_suchenkey);
		System.out.print(" ms");
		System.out.print("\n");
		System.out.print("Löschen aller Elemente: t = ");
		System.out.print(Hash_deleteall2-Hash_deleteall);
		System.out.print(" ms");
		System.out.print("\n");

		
	}

}
