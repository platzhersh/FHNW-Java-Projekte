#pragma once

template<class T> class PersistentVector;
template<class T> class VectorAccessor;

/**

	Random Access Iterator corresponding the C++11 standard
	
	http://www.cplusplus.com/reference/iterator/

	more information: http://stackoverflow.com/questions/8054273/how-to-implement-an-stl-style-iterator-and-avoid-common-pitfalls

	author: Christian Glatthard
	email:	chregi.glatthard@gmail.com

**/
template<class T> class VectorIterator {
	
	/* 
		Friends
	*/
	friend class PersistentVector<T>;
	friend class VectorAccessor<T>;

	/*
		Member Attributes
	*/
	PersistentVector<T>& m_vector;	// PersistentVector to iterate on
	size_t m_pos;					// current position on PersistentVector

	/*
		Constructors
	*/

	VectorIterator() {}
	VectorIterator(PersistentVector<T>& pv)
		: m_pos(0)
		, m_vector(pv)
	{}
	VectorIterator(PersistentVector<T>& pv, size_t pos)
		: m_pos(pos)
		, m_vector(pv)
	{}

public:

	/*
		Typedefs
	*/
	typedef int _w64 difference_type;
	typedef T value_type;
	typedef T& reference;
	typedef const T& const_reference;
	typedef T* pointer;
	typedef random_access_iterator_tag iterator_category;

	/*
		Forward Iterator
		- can be incremented
	*/
	
	// prefix increment
	VectorIterator<T>& operator++() {
		m_pos++;
		return *this;
	}
	//postfix increment
	VectorIterator<T> operator++(int) {
		return VectorIterator<T>(m_vector,m_pos++);
	}

	/*
		Bidirectional Iterator
		- Supports equality/inequality comparisons
		- Can be dereferenced as an rvalue
		- Can be dereferenced as an lvalue 
		- default-constructible
		- Multi-pass: neither dereferencing nor incrementing affects dereferenceability
		- Can be decremented
	*/
	
	// equality
	bool operator==(const VectorIterator<T>& it) const {
		return &m_vector == &it.m_vector && m_pos == it.m_pos;
	}

	// inequality
	bool operator!=(const VectorIterator<T>& it) const {
		return !(*this == it);
	}

	/* 
		Random Access Iterator
		- Supports arithmetic operators + and -
		- Supports inequality comparisons (<, >, <= and >=) between iterators
		- Supports compound assignment operations += and -=
		- Supports offset dereference operator ([])
	*/
	VectorIterator<T>& operator=(const VectorIterator<T>& it) {
		m_pos = it.m_pos;
		return *this;
	}

	void operator=(const T& val) { 
		m_vector.write(val, m_pos); 
	}	

	bool operator<(const VectorIterator<T>& it) {
		return &m_vector == &it.m_vector && m_pos < it.m_pos;
	}

	bool operator>(const VectorIterator<T>& it) {
		return &m_vector == &it.m_vector && m_pos > it.m_pos;
	}

	// incremente and decrement operators

	VectorIterator<T>& operator--() {
		m_pos--;
		return *this;
	}
	// postfix
	// todo: check if returns correct value
	VectorIterator<T> operator--(int) {
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
	VectorIterator<T> operator+(difference_type dist) {
		return VectorIterator<T>(m_vector, m_pos + dist);
	}
	VectorIterator<T> operator-(difference_type dist) {
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
	// read & write access
	VectorAccessor<T> operator*() {
		return m_vector[m_pos];
	}

	
	
};