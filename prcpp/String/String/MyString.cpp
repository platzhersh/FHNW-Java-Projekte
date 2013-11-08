#include "MyString.h"


/*--------------------------------------
	Konstruktoren
--------------------------------------*/

// Default Constructor
String::String() 
	: m_string(nullptr)
	, m_start(0)
	, m_length(0) 
{}

// Copy Constructor
String::String(const String &s)
	: m_length(s.getLength())
	, m_start(s.getStart())
	, m_string(s.getString())
{}

// Typ Konvertierungs Constructor
String::String(char* c)
	: m_length(0)
	, m_start(0)
{
	// determine string length
	size_t current = 0;
	while (c[current] != '\0') {
		m_length++;
		current++;
	}

	// initialize m_string
	m_string = shared_ptr<char>(new char[m_length]);


	cout << "ConvertConstructor: m_length = " << m_length << endl;

	// copy chars to
	for (size_t i = 0; i < m_length; i++) {
		m_string.get()[i] = *(c+i);
		#ifdef DEBUG
		cout << "m_string.get()[" << i << "] = " << m_string.get()[i] << endl;
		#endif
	}
}

/*--------------------------------------
	Instanz-Methoden
--------------------------------------*/

char String::charAt(size_t index) const {
	if (index >= m_length) throw exception();
	return m_string.get()[index];
}

int String::compareTo(const String& s) const{
	//if (m_length > s.m_length) return 1;
	//else if (m_length < s.m_length) return -1;
	//else {
	int r = 0;
	char a,b;
	for (size_t i = 0; i < m_length; i++) {
		a = m_string.get()[i];
		b = s.charAt(i);
		a = (a >= 97) && (a <= 122) ? a - 32 : a;
		b = (b >= 97) && (b <= 122) ? b - 32 : b;
		if (a == b) continue;
		else { 
			r = a > b ? 1 : -1;
			break;
		}
	}
	return r;
	}

String String::concat(char c) const {
	char* p = new char[m_length+1];
	copy(m_string.get(),&m_string.get()[m_length],p);
	p[m_length] = c;
	p[m_length+1] = '\0';
	return String(p);
}

String String::concat(const String& s) const {
	size_t l = m_length+s.getLength();
	char* p = new char[l];
	copy(m_string.get(),&m_string.get()[m_length],p);
	copy(s.getString().get(),&s.getString().get()[s.getLength()],&p[m_length]);
	p[l] = '\0';
	return String(p);
}

String String::substring(size_t beg, size_t end) const {
	if (end < beg) throw exception();
	if (end == beg) return String();
	
	size_t length = end - beg;

	String s(*this);
	s.m_start = beg;
	s.m_length = length;

	return s;
}

unique_ptr<char[]> String::toCString() const {
	unique_ptr<char[]> r = unique_ptr<char[]>(new char[m_length+1]);
	copy(&m_string.get()[m_start],&m_string.get()[m_start+m_length],r.get());
	r.get()[m_length] = '\0';
	return move(r);
}
size_t String::length() const {
	return m_length;
}

void String::print() {
	cout << "Print: ";
	for (size_t i = 0; i < m_length; i++) {
		cout << (char) m_string.get()[m_start+i];
	}
	cout << endl;
}

String::~String(void) {
	cout << "reset " << toCString().get() << endl;
	m_string.reset();
}