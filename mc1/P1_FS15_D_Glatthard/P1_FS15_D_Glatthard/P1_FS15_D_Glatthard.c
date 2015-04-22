/*
 * P1_FS15_D_Glatthard.c
 *
 * Created: 17.04.2015 16:14:38
 *  Author: Christian Glatthard
 */ 


#include <avr/io.h>


// highest order bit declaration
char hob (char num);

int main(void)
{	
	// Datenrichtungsregister: 
	// - PortC als Ausgänge benutzen (LEDs)
	DDRC = 0xFF;
	// - PortA als Eingänge benutzen DIL Switch
	DDRA = 0x00;
	
	// PortC alle LED aus
	PORTC = 0x00;
	// PortA alle LED aus
	PORTC = 0x00;
	
	static char h, i;
	
	// run in loop, so PINA can be changed all the time
	while(1)
	{
		
		h = hob(PINA);
		PORTC = h;
	}
	
	return 42;	// never reached
}

// get highest order bit
char hob (char num)
{
	if (!num)
	return 0;

	char ret = 1;

	while (num >>= 1)
	ret <<= 1;

	return ret;
}