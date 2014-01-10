#pragma once

template<class T> class VectorIterator;

template<class T> class VectorIteratorAccessor {

	friend class VectorIterator<T>;

protected:

	VectorIterator<T>& m_it;
	int m_pos;

public:

	VectorIteratorAccessor(VectorIterator<T>& it, int index) : m_it(it), m_pos(index) {}

	VectorIterator<T>& operator=(int val) {
		m_it.write(m_pos, val);
		return m_it;
	}
	VectorIterator<T>& operator=(VectorIterator<T>& it) {
		m_it.m_pos = it.m_pos;
		return m_it;
	}

	operator VectorIterator<T> const& () {
		return m_it;
	}
	operator T const () {
		return m_it.get();
	}

};