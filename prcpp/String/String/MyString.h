#pragma once
#include <memory>
using namespace std;

class String final
{
	shared_ptr<char> m_string;	// Pointer zu CharArray
	shared_ptr<char> m_start;	// Alternative int m_start ?
	size_t m_length;				// Länge des Strings

public:
	String();
	String(const String& s);
	String(char& c);

	// Getter Methods
	size_t getLength() const{
		return m_length;
	};
	shared_ptr<char> getStart() const{
		return m_start;
	};
	shared_ptr<char> getString() const{
		return m_string;
	};

	~String(void);
};

