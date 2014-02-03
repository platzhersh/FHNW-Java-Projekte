#pragma once

#include <iostream>
#include <stdio.h>

using namespace std;

class StateMachine {

	enum State {s1, s2, s3, s4};
	State m_state;

public:

	StateMachine() : m_state(s1) {}
	StateMachine(State state) : m_state(state) {}

	void start() {
		start(m_state);
	}

	void start(State i) {

		m_state = i;

		cout << "init state: " << m_state << endl;

		do {

			switch (m_state) {

			case s1: m_state = s3; break;
			case s2: m_state = s2; break;
			case s3: m_state = s4; break;
			case s4: m_state = s4; break;
			default: m_state = s1; break;
			}

			cout << "state: " << m_state << endl;

		} while (m_state != s4);
	}


};