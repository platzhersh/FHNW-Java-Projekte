#include "RandomAccessFile.hpp"
#include <algorithm>

RandomAccessFile::RandomAccessFile(const string& fileName) 
	: m_fileName(fileName) {
	
	// try to open file for reading and writing
	m_file.open(m_fileName.c_str(), ios::in | ios::out | ios::binary);
	if (!m_file) {
		// file doesn't exist
		m_file.clear();

		// create file and try to open file for reading and writing
		m_file.open(m_fileName.c_str(), ios::in | ios::out | ios::binary | ios::trunc);
		if (!m_file) {
			m_file.setf(ios::failbit);
		}
	}
}


streamsize RandomAccessFile::length() {
	streampos pos = m_file.tellg();

	m_file.seekg(0, ios::end);
	streampos length = m_file.tellg();
	m_file.seekg(pos);

	return length;
}

bool RandomAccessFile::truncate(streamsize length) {
	if (length < 0) return false;

	const streamsize buffSize = 128;
	char buffer[buffSize];
	const char *fileName = "tmp.tmp";

	// create temp file
	ofstream tmp(fileName, ios::out | ios::trunc | ios::binary);

	// copy first length bytes
	m_file.seekg(0);
	if (!m_file) return false;
	streamsize n;
	while(length) {
		n = min(buffSize, length);
		m_file.read(buffer, n);
		tmp.write(buffer, n);
		length -= n;
	}

	// close both files
	m_file.close();
	tmp.close();

	// delete this file and rename tmp
	if (remove(m_fileName.c_str())) return false;
	if (rename(fileName, m_fileName.c_str())) return false;

	// open this file
	m_file.open(m_fileName.c_str(), ios::in | ios::out | ios::binary);
	
	return m_file.good();
}