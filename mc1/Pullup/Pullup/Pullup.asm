/*
 * Pullup.asm
 *
 *  Created: 17.04.2015 13:38:04
 *   Author: chregi
 */ 

 #include <avr/io.h>

 volatile unsigned char val1 = 0, val2 = 0, val3 = 0;
 int main() {

	DDRA = 0x0F;
	PORTA = 0x0C;
	val1 = PINA & 0x0F;
	DDRA = 0x0C;
	val2 = PINA & 0x0F;
	PINA = 0x0F;
	val3 = PINA & 0X0F;

	_delay_ms(100);

 }