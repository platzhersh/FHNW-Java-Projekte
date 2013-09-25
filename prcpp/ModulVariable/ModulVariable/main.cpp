#include "stdio.h"
#include <cmath>

double d = log10(100.0);
static int x = 4;

double horror() {
	x *= x;
	return d*x;
}

int main(int argc, char* argv[]) {
	int x = 5;
	printf("%.2f, %d\n", horror(), x);
	printf("%.2f, %d\n", horror(), ::x);
}