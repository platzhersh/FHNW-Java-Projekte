 #include <avr/io.h>

 volatile unsigned char val1 = 0, val2 = 0, val3 = 0, val4 = 0;
 volatile unsigned char p = 0;
 int main() {

	 DDRA = 0x0F;			// 0000 1111
	 PORTA = 0x0C;			// 0000 1100
	 p = PINA;
	 val1 = PINA & 0x0F;
	 DDRA = 0x0C;			// 0000 1100
	 p = PINA;
	 val2 = PINA & 0x0F;
	 PINA = 0x0F;
	 p = PINA;
	 val3 = PINA & 0X0F;
	 
	 
	val4 = 1;

 }