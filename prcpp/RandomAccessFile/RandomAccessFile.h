#pragma once
#include <fstream>
#include <algorithm>
using namespace std;

class RandomAccessFile {
	fstream m_file;
	string m_fileName;

public:
	struct IOException : public exception {
		IOException(const char* msg) : exception(msg) {}
	};

	RandomAccessFile(const string& fileName);
	RandomAccessFile() {}
	virtual ~RandomAccessFile() { m_file.close(); }

	streamsize length();
	operator bool() const { return m_file.good(); }
	bool truncate(streamsize s);

	// generic methods
	template<class T> T read(streampos pos = -1);
	template<class T> void write(const T& data, streampos pos = -1);

};