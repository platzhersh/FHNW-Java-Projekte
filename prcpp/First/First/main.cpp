#include "stdio.h"
#include <iostream>


int main(int argc, char* argv[]) {
	printf("The program arguments are: \n");
	for (int i = 0; i < argc; i++)
		printf("%d: %s\n", i, argv[i]);
}
