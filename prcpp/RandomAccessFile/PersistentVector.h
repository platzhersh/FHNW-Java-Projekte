#pragma once

#include "RandomAccessFile.h"
#include "VectorIterator.hpp"
#include "VectorAccessor.hpp"

template<class T> class PersistentVector {

	friend class VectorAccessor<T>;

	RandomAccessFile m_file;
	size_t m_size; // number of vector elements

public:
	typedef T value_type;
	typedef T& reference;
	typedef const T& const_reference;
	typedef VectorIterator<T> iterator;

	PersistentVector(const char* fname) 
		: m_file(fname)
		, m_size(capacity())
	{}

	~PersistentVector() {
		size_t cap = capacity();
		//assert(m_size <= cap);
		if (m_size < cap) m_file.truncate(m_size*sizeof(T));
	}

	size_t capacity() const {
		return (size_t)(const_cast<RandomAccessFile&>(m_file).length()/sizeof(T));
	}

	iterator begin() {
		return VectorIterator<T>(*this, 0);
	}
	iterator end() {
		return VectorIterator<T>(*this, m_size);
	}

	void push_back(const T& val) {
		write(m_size++, val);
	}


	VectorAccessor<T> operator[](int index) {
		VectorAccessor<T> v(*this, index);
		return v;
	}

	bool isEmpty() {
		return m_size == 0;
	}

private:
	void write(size_t index, const T& val) {
		m_file.write(val, index*sizeof(T));
	}

	T read(size_t index) const {
		return const_cast<RandomAccessFile&>(m_file).read<int>(index*sizeof(T));
	}

};