/*
 * _7_Zutrittskontrolle.c
 *
 * Created: 24.04.2015 17:23:41
 *  Author: chregi
 
 Es geht um ein Haus mit empfindlichen tropischen Pflanzen und Tieren.
 Aus Kosten- und Qualitätsgründen soll der Zugang zum klimatisierten Raum limitiert werden. Die Tür darf höchstens während 20% der Zeit offen stehen. Die längste erlaubte Öffnungszeit ist auf 5s begrenzt. Danach muss die Tür während mindestens 20s geschlossen bleiben. Ein Alarm signalisiert, wenn die Türe zu lange offen steht.
 Schnittstellen:
 
	 - An DIL0 (Port A) ist der Türkontakt angeschlossen. 0 bedeutet «Türe offen».
	 - An LED0..LED7 (Port C) geben Sie laufend die Zeitdauer (binär, Sekunden) aus, die bis zur nächsten Türfreigabe verstreichen.
	 - An LED9 (Port G.1) befindet sich der Alarmausgang. 1 bedeutet «Alarm»
	 - An LED8 (Port G.0) befindet sich der Sperrmagnet für das Türschloss. 1beteutet «gesperrt»
	 
	 Vorgehen, Teilschritte:
	 1.T1 für die Zeitmessung initialisieren. Initialisieren Sie den T1 und Prescaler so, dass Sie einen Takt von genau 1s an OCF1A erreichen. (?CTC-Mode) [Zur Kontrolle können Sie diesen Takt an LED0 sichtbar machen].
	 2.Schreiben Sie eine ISR für OCF1A, die bei jedem Aufruf eine Zählervariable um 1 erhöht. [Geben Sie diese Zahl auf LED0..7 aus].
	 3.Analysieren Sie die Schnittstellen (E/A).
	 4.Analysieren Sie die Aufgaben der Zutrittskontrolle mit Hilfe einer Zustandsmaschine.
	 5.Schreiben Sie ein Hauptprogramm welches diese Zustandsmaschine abbildet, und integrieren Sie die Routinen für die Zeitmessung.
	 6.Ergänzen Sie wo nötig das Programm, bis alle Schnittstellen-Definitionen erfüllt sind.
	 7.Überprüfen Sie die korrekte Funktion.
 
 */ 


#include <avr/io.h>

int main(void)
{
    while(1)
    {
        //TODO:: Please write your application code 
    }
}