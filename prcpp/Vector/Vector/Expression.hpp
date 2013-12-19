template <typename Left, typename Op, typename Right, typename T>
struct Expr {
	Left m_left;
	Right m_right;
	Expr(Left t1, Right t2) : m_left(t1), m_right(t2) { }
	T operator[](int i) { return Op::apply(m_left[i], m_right[i]); }
};