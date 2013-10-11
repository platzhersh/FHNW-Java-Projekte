#pragma once
#include <iostream>
using namespace std;


class Point {
	int m_x, m_y;
public:
	//Point() : /* Initialisierungsliste */ m_x(0), m_y(0) { /* body */ };

	Point(int x = 0, int y = 0) : m_x(x), m_y(y) {}
	~Point() { cout << "A destructor called." << endl; }

	int getX() const { return m_x; }	// const macht klar, dass nur Lesezugriff stattfinden kann
	int getY() const { return m_y; }	// const macht klar, dass nur Lesezugriff stattfinden kann

	void setX(int x) { m_x = x; }
	void setY(int y) { m_y = y; }
};

class PointArray
{
	size_t m_size;			// current size = number of Points in the array
	size_t m_capacity;		// array length
	Point *m_points;		// array of points

public:
	PointArray();						// standard constructor
	PointArray(const PointArray& pa);	// copy constructor
	PointArray(Point a[], size_t len);	// C-Array
	~PointArray();						// standard destructor
};


