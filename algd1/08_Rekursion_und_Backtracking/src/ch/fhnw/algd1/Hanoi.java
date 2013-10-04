package ch.fhnw.algd1;

/**
 * Write a description of class Hanoi here.
 * 
 * @author C. Stamm 
 * @version 1
 */
public class Hanoi {
    private int m_anzahlScheiben;
    
    public Hanoi(int n) {
        assert n > 0 : n;
        m_anzahlScheiben = n;
    }
    
    public void start() {
        System.out.println("\n\nTürme von Hanoi\n");
        System.out.println("Verschiebe Turm der Höhe " + m_anzahlScheiben + " von A nach B.");
        System.out.println("Verschiebe dabei die Scheiben in folgender Reihenfolge:");
        move(m_anzahlScheiben, 'A', 'B', 'C');
        System.out.println();
    }
    
    private void move(int anzahl, char from, char to, char via) {
        assert anzahl > 0 : anzahl;
        
        if (anzahl == 1) {
            //System.out.println("von " + from + " nach " + to);
            print(from, to);
        } else {
            move(anzahl - 1, from, via, to);
            move(1, from, to, via);
            move(anzahl - 1, via, to, from);
        }
    }
    
    private void print(char from, char to) {
        switch(from) {
        case 'A': 
            switch(to) {
            case 'B': 
                System.out.println("[A]----->[B]");
                break;
            case 'C': 
                System.out.println("[A]-------------->[C]");
                break;
            }
            break;
        case 'B': 
            switch(to) {
            case 'A': 
                System.out.println("[A]<-----[B]");
                break;
            case 'C': 
                System.out.println("         [B]----->[C]");
                break;
            }
            break;
        case 'C': 
            switch(to) {
            case 'A': 
                System.out.println("[A]<--------------[C]");
                break;
            case 'B': 
                System.out.println("         [B]<-----[C]");
                break;
            }
            break;
        }
    }
}
