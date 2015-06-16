/*
 * UART_test.c
 *
 * Created: 22.05.2015 17:04:17
 * Author: chregi
 */ 


#include <avr/io.h>
#include <stdlib.h>
#include "lcd.h"
#include <util/delay.h>

int main(void)
{
	// LCD initialisieren
	lcd_init_samsung(LCD_DISP_ON_CURSOR_BLINK);
	lcd_puts("Serial test:");
	
	// USART0 initialiseren: 8 Datenbit, 1 Stopbit, No Parity, 0600 bps, double speed...
	UBRR0 = 207;	// 9600bps
	UCSR0A = 0x02;	// double speed
	UCSR0B = 0x18;	// Receiver und Transmitter enabled
	UCSR0C = 0x06;	// 8 Datenbit
	
	//UDR0 = i;
	//char i = 0;
	char c = 'a';
	UDR0 = 'a';
    while(1)
    {	
		lcd_puts(&c);
		//lcd_gotoxy()
		_delay_ms(100);
		UDR0 = c++;
		if(UCSR0A&(1<<RXC0)) // Warten bis Bit empfangen -> does not work
		{
			
		}
    }
}