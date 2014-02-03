#pragma once
#include <algorithm>

using namespace std::rel_ops;

class RelationalOperator {

	int m_a;

public:

	RelationalOperator(int a) : m_a(a) {}

	int getA() const {
		return m_a;
	}

	// Operatoren
	bool operator==(const RelationalOperator& foo) const { return getA() == foo.getA(); }
	bool operator<(const RelationalOperator& foo) const { return getA() < foo.getA(); }

};