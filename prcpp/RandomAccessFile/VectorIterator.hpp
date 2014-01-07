#pragma once


template<class T> class PersistentVector;

template<class T> class VectorIterator {
	friend class PersistentVector<T>;

	// attributes
	size_t m_pos;	// current iterator position
	PersistentVector<T>& m_vector;

	// ctor
	VectorIterator(PersistentVector<T>& pv, size_t pos)
		: m_pos(pos)
		, m_vector(pv)
	{}

public:
	typedef random_access_iterator_tag iterator_category;
	typedef T value_type;
	typedef T& reference;
	typedef const T& const_reference;
	typedef T* pointer;
	typedef int _w64 difference_type;

	VectorIterator<T>& operator=(const VectorIterator<T>& it) {
		m_vector = it.m_vector;
		m_pos = it.m_pos;
		return *this;
	}

	// relational operators
	bool operator==(const VectorIterator<T>& it) {
		return &m_vector == &it.m_vector && m_pos == it.m_pos;
	}
	bool operator!=(const VectorIterator<T>& it) {
		return !(*this == it);
	}

	bool operator<(const VectorIterator<T>& it) {
		return &m_vector == &it.m_vector && m_pos < it.m_pos;
	}

	bool operator>(const VectorIterator<T>& it) {
		return &m_vector == &it.m_vector && m_pos > it.m_pos;
	}

	// incremente and decrement operators
	// prefix
	VectorIterator<T>& operator++() {
		m_pos++;
		return *this;
	}
	VectorIterator<T>& operator--() {
		m_pos--;
		return *this;
	}
	// postfix
	// todo: check if returns correct value
	VectorIterator<T>& operator++(int) {
		m_pos++;
		return *this-1;
	}
	// todo: check if returns correct value
	VectorIterator<T>& operator--(int) {
		return VectorIterator<T>(m_vector, m_pos--);
	}
	
	// arithmetic operator 2 Iterators
	difference_type operator-(VectorIterator<T>& it) {
		return m_pos - it.m_pos;
	}
	difference_type operator+(VectorIterator<T>& it) {
		return m_pos + it.m_pos;
	}

	// arithmetic operator Iterator and difference_type
	VectorIterator<T>& operator+(difference_type dist) {
		return VectorIterator<T>(m_vector, m_pos + dist);
	}
	VectorIterator<T>& operator-(difference_type dist) {
		return VectorIterator<T>(m_vector, m_pos - dist);
	}

	VectorIterator<T>& operator+=(difference_type dist) {
		m_pos += dist;
		return *this;
	}
	VectorIterator<T>& operator-=(difference_type dist) {
		m_pos -= dist;
		return *this;
	}


	// access operators
	// java style
	T get() {
		return (T) m_vector[m_pos];
	}
	void set(const_reference val) {
		m_vector[m_pos] = val;
	}
	// c++ style
	// read access
	// todo: check if returns correct value
	const_reference operator*() const {
		return m_vector[m_pos];
	}

	// write access
	/*
	reference operator[](size_t pos){
		return VectorAccessor<T>(*this, index);
		return (reference)m_vector[m_pos + pos];	// problem
	}*/
	
	// operator->() // not implemented
	
};