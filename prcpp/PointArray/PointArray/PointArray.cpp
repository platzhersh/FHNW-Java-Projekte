#include "PointArray.h"
// using namespace geht hier nicht da wir die Funktionen erst definieren

// standard constructor
PointArray::PointArray() 
: m_capacity(0)
, m_size(0)
, m_points(nullptr)
{

}

// copy constructor
PointArray::PointArray(const PointArray& pa)	// const versichert read-only für übergebenes Objekt
: m_capacity(pa.m_capacity)
, m_size(pa.m_size)
{
	// i.d.R. DeepCopy, FlatCopy wird vom System bereits angeboten
	m_points = new Point[m_capacity];
	for (size_t i = 0; i < m_size; i++) {
		m_points[i] = pa.m_points[i];
	}
}

// C-Array
PointArray::PointArray(Point pts[], size_t len) 
: m_capacity()
, m_size()
{
	m_points = new Point[m_capacity];
	for (size_t i = 0; i < m_size; i++) {
		m_points[i] = pts[i];
	}
}

// standard destructor
PointArray::~PointArray() {

}