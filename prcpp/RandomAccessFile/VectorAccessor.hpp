#pragma once

template<class Type> class PersistentVector;

template<class Type> class VectorAccessor {
	friend class PersistentVector<Type>;
	friend class VectorIterator<Type>;

	PersistentVector<Type> *m_vector;
	size_t m_pos;		// current iterator position
	Type m_value;		// current value

	VectorAccessor(PersistentVector<Type> *vector, size_t pos) 
	: m_vector(vector)
	, m_pos(pos)
	{}

public:
	typedef const Type& const_reference;

	VectorAccessor(const VectorAccessor& vh)
	: m_vector(0)
	, m_pos(-1)
	, m_value(vh)
	{}

	VectorAccessor& operator=(const_reference val) {
		if (m_vector) 
			m_vector->write(m_pos, val);
		else
			m_value = val;
		return *this;
	}

	VectorAccessor& operator=(const VectorAccessor& vh) {
		if (m_vector)
			m_vector->write(m_pos, vh);
		else
			m_value = vh;
		return *this;
	}

	operator Type() const { return (m_vector) ? m_vector->read(m_pos) : m_value; }
};

