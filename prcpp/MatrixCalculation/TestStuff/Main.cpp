#include <iostream>
using namespace std;

int main(int argc, char* argv[]) {
        double * pa = new double[3];
		double * pb = new double[3];

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

}