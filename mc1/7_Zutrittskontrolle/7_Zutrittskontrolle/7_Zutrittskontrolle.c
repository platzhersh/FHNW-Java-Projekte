/*
 * _7_Zutrittskontrolle.c
 *
 * Created: 24.04.2015 17:23:41
 *  Author: chregi
 
 Es geht um ein Haus mit empfindlichen tropischen Pflanzen und Tieren.
 Aus Kosten- und Qualit�tsgr�nden soll der Zugang zum klimatisierten Raum limitiert werden. Die T�r darf h�chstens w�hrend 20% der Zeit offen stehen. Die l�ngste erlaubte �ffnungszeit ist auf 5s begrenzt. Danach muss die T�r w�hrend mindestens 20s geschlossen bleiben. Ein Alarm signalisiert, wenn die T�re zu lange offen steht.
 Schnittstellen:
 
	 - An DIL0 (Port A) ist der T�rkontakt angeschlossen. 0 bedeutet �T�re offen�.
	 - An LED0..LED7 (Port C) geben Sie laufend die Zeitdauer (bin�r, Sekunden) aus, die bis zur n�chsten T�rfreigabe verstreichen.
	 - An LED9 (Port G.1) befindet sich der Alarmausgang. 1 bedeutet �Alarm�
	 - An LED8 (Port G.0) befindet sich der Sperrmagnet f�r das T�rschloss. 1beteutet �gesperrt�
	 
	 Vorgehen, Teilschritte:
	 1.T1 f�r die Zeitmessung initialisieren. Initialisieren Sie den T1 und Prescaler so, dass Sie einen Takt von genau 1s an OCF1A erreichen. (?CTC-Mode) [Zur Kontrolle k�nnen Sie diesen Takt an LED0 sichtbar machen].
	 2.Schreiben Sie eine ISR f�r OCF1A, die bei jedem Aufruf eine Z�hlervariable um 1 erh�ht. [Geben Sie diese Zahl auf LED0..7 aus].
	 3.Analysieren Sie die Schnittstellen (E/A).
	 4.Analysieren Sie die Aufgaben der Zutrittskontrolle mit Hilfe einer Zustandsmaschine.
	 5.Schreiben Sie ein Hauptprogramm welches diese Zustandsmaschine abbildet, und integrieren Sie die Routinen f�r die Zeitmessung.
	 6.Erg�nzen Sie wo n�tig das Programm, bis alle Schnittstellen-Definitionen erf�llt sind.
	 7.�berpr�fen Sie die korrekte Funktion.
 
 */ 


#include <avr/io.h>

int main(void)
{
    while(1)
    {
        //TODO:: Please write your application code 
    }
}