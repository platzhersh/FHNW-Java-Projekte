#pragma once
#include <memory>
#include <iostream>
using namespace std;

class String final
{
	shared_ptr<char> m_string;	// Pointer zu CharArray
	size_t m_start;				// Beginn des (Sub)Strings
	size_t m_length;			// Länge des Strings

public:
	String();
	String(const String& s);
	String(char* c);

	// Getter Methods
	size_t getLength() const{
		return m_length;
	};
	size_t getStart() const{
		return m_start;
	};
	shared_ptr<char> getString() const{
		return m_string;
	};

	// Instanz-Methoden 
	char charAt(size_t index) const; // bei falschem Index: wirft exception 
	int compareTo(const String& s) const; // C++ untypische Funktion: gibt -1, 0, 1 zurück 
	String concat(char c) const; // hängt c ans Ende und gibt neuen String zurück 
	String concat(const String& s) const; 
	size_t length() const; // gibt die Länge des Strings zurück 
	// Substring des Bereichs [beg, end) 
	// falls beg = m_len oder end = beg: gibt leeren String zurück 
	String substring(size_t beg, size_t end) const; 
	// erzeugt 0-terminierten C-String, kopiert Inhalt und gibt Zeigerobjekt zurück 
	unique_ptr<char[]> toCString() const;


	// Gleichheitsoperator (Inline-Implementation schon gegeben) 
	bool operator==(const String& s) const { 
		return compareTo(s) == 0; 
	}
	// Ausgabe-Operator für Output-Streams (Inline-Implementation schon gegeben) 
	friend ostream& operator<<(ostream& os, const String& s) { 
		const size_t end = s.m_start + s.m_length; 
		const char* const sc = s.m_string.get(); 
		for(size_t i=s.m_start; i < end; i++) os << sc[i]; 
		return os; 
	}
	// Klassen-Methode 
	static String valueOf(int i); // erzeugt eine String-Repräsentation von i

	void print();

	~String(void);
};

