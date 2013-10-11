#include "PointArray.h"
using namespace std;

int main() {
	Point p1;			// (0,0)
	Point p2(55, 66);	// (55,66)
	Point p3(5);		// (5,0)
	const Point p4;
	Point p5 = 5;		// (5,0) "Typumwandlungskonstruktor" -> entspricht Point p3(5,0), d.h. erzeugt neues Objekt

	p3.setX(5);

	{
		Point p;
	}

	cout << "before p3 = 0" << endl;
	p3 = 0;				// 1. neues Punkt Objekt (0,0) erstellt
						// 2. standardmässiger Zuweisungsoperator kopiert die Attributwerte nach p3
						// 3. anonymes Punktobjekt wird zerstört, p3 wird NICHT zerstört
	cout << "after p3 = 0" << endl;

	
	Point *parray;
	PointArray pa1;
}