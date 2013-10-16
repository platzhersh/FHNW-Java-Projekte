#include "MyString.h"
#include <iostream>
using namespace std;

// Default Constructor
String::String() 
	: m_string(nullptr)
	, m_start(nullptr)
	, m_length(0) 
{};

// Copy Constructor
String::String(const String& s)
	: m_length(s.getLength())
	, m_start(s.getStart())
	, m_string(s.getString())
{};

// Typ Konvertierungs Constructor
String::String(char& c)
	: m_length(0)
{
	// determine string length
	size_t current = 0;
	while (c+current != '\0') {
		m_length++;
		current++;
	};

	// initialize m_string and helper pointer
	char* p = new char[m_length];
	m_string = make_shared<char>(p);

	cout << "m_length = " << m_length << endl;

	// copy chars to
	for (size_t i = 0; i <= m_length; i++) {
		p[i] = c+i;
		cout << p[i];
	}
};