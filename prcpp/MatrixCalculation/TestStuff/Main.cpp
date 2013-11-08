#include <iostream>
using namespace std;

int main(int argc, char* argv[]) {
        double * pa = new double[3];
		double * pb = new double[3];
		double c[] = {0,1,2,3,4,5};

		for (int i = 0; i < 3; i++) {
		pa[i] = 1;
		}

		for (int j = 0; j < 3; j++) {
		cout << pa[j] << " " << pb[j] << endl;
		}

		copy(pa,&pa[3],pb);
		
		for (int j = 0; j < 3; j++) {
		cout << pa[j] << " " << pb[j] << endl;
		}

		cout << equal(pa,&pa[3],pb) << endl;

		cout << "*(c+1): " << *(c+1) << endl;
		cout << "*(c+2*2+1): " << *(c+2*2+1) << endl;
		cout << "c[1]: " << *(c+1) << endl;
		cout << "c[1]: " << *(c+1) << endl;


}