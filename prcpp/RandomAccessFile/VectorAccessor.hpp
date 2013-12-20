template<class T> class VectorAccessor {

	friend class PersistentVector<T>;

	PersistentVector<T>& m_pv;
	int m_pos;

	VectorAccessor(PersistentVector<T>& pv, int index) : m_pv(pv), m_pos(index) {}

	PersistentVector<T> operator=(int val) {
		m_pv.write(m_pos, val);
		return m_pv;
	}

public:
	operator VectorIterator<T> const& () {
		return VectorIterator<T> a(m_pv ,m_pos);
	}
	operator PersistentVector<T> const& () {
		return m_pv;
	}
	operator T const () {
		return m_pv.read(m_pos);
	}

};