#pragma once

#include "RandomAccessFile.h"

template<class T> T RandomAccessFile::read(streampos pos /*= -1*/) {
	if (pos >= 0) {
		m_file.seekg(pos);
		if (!m_file) throw RandomAccessFile::IOException("read error");
	}
	T data;
	m_file.read((char *)&data, sizeof(T));
	if (!m_file) throw RandomAccessFile::IOException("read error");

	return data;
}
template<class T> void RandomAccessFile::write(const T& data, streampos pos /*= -1*/) {

	if (pos >= 0) {
		m_file.seekg(pos);
		if(!m_file) throw RandomAccessFile::IOException("write error");
	}
	m_file.write((char*)&data, sizeof(T));
	if(!m_file) throw RandomAccessFile::IOException("write error");
}