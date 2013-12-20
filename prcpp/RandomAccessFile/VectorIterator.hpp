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

	VectorIterator& operator=(const VectorIterator& it) {
		m_vector = it.m_vector;
		m_pos = it.m_pos;
		return *this;
	}

	// relational operators
	bool operator==(const VectorIterator& it) {
		return &m_vector == &it.m_vector && m_pos == it.m_pos;
	}
	bool operator!=(const VectorIterator& it) {
		return !(*this == it);
	}

	bool operator<(const VectorIterator& it) {
		return &m_vector == &it.m_vector && m_pos < it.m_pos;
	}

	// incremente and decrement operators
	// prefix
	VectorIterator& operator++() {
		m_pos++;
		return *this;
	}
	VectorIterator& operator--() {
		m_pos--;
		return *this;
	}
	// postfix
	VectorIterator& operator++(int) {
		return VectorIterator(m_vector, m_pos++);
	}
	VectorIterator& operator--(int) {
		return VectorIterator(m_vector, m_pos--);
	}
	
	// arithmetic operator
	difference_type operator-(VectorIterator& it) {
		return m_pos - it.m_pos;
	}
	difference_type operator+(VectorIterator& it) {
		return m_pos + it.m_pos;
	}

	VectorIterator& operator+=(difference_type dist) {
		m_pos+s;
		return *this;
	}
	VectorIterator& operator-=(difference_type dist) {
		m_pos-s;
		return *this;
	}
	VectorIterator& operator+(difference_type dist) {
		return VectorIterator(m_vector, m_pos+dist);
	}
	VectorIterator& operator-(difference_type dist) {
		return VectorIterator(m_vector, m_pos - dist);
	}

	// access operators
	// java style
	T get() {
		return m_vector[m_pos];
	}
	void set(const_reference val) {
		m_vector[m_pos] = val;
	}
	// c++ style
	// read access
	const_reference operator*() const {
		return m_vector[m_pos];
	}

	// write access
	reference operator[](size_t pos){
		return m_vector[m_pos + pos];	// problem
	}
	
	// operator->() // not implemented
	
};