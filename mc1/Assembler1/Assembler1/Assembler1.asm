/*
 * Assembler1.asm
 *
 *  Created: 13.03.2015 15:33:17
 *   Author: chregi
 */ 


 .include "m2560def.inc"	; lade 2560-er Deklarationen
 .def work = r16			; definiere R16 als mein work-Register
 .cseg						; schreibe von hier weg in den Programmsepicher
	rjmp START				; das ist der Reset-Vektor
.org 0x2a					; setzt den Schreibzeiger auf Adresse 0x2a

START:						; Das ist die Anfangsadresse NACH der Vektortabelle
	; DDRC = 0xFF
	ldi work, 0xFF			; lade work mit 0xFF
	out DDRC, work			; PORTC wird als Ausgang (8-Bit) definiert
	out PORTA, work			; die Pullups von PINA werden eingeschaltet

LOOP:						; hier beginnt die Endlosschlaufe
	in work, PINA			; PINA einlesen
	com work				; Bilde das Komplement im Register work
	out PORTC, work			; .. und schreibe das Ergebnis auf PORTC
	rjmp LOOP				; endlose Schlaufe zu LOOP
	.exit					; alles fertig