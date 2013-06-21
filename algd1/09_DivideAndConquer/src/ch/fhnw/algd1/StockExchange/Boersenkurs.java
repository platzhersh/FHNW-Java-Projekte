package ch.fhnw.algd1.StockExchange;

/**
 * Write a description of class MaxTeilsumme here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import java.util.*;

public class Boersenkurs {
    final static int MaxValue = 200;
    private float[] m_array;
    private int m_left, m_right;
    
    public Boersenkurs(int len) {
        assert len > 0 : len;
        m_array = new float[len];
        
        for (int i=0; i < len; i++) {
            // Math.random() liefert double zwischen [0..1)
            m_array[i] = (float)(MaxValue*Math.random() - MaxValue/2.0);
        }
    }
    
    public int getLength() {
        return m_array.length;
    }
    
    public int getLeft() {
        return m_left; 
    }
    
    public int getRight() {
        return m_right;
    }
    
    public void print() {
        System.out.println(Arrays.toString(m_array));
    }
    
    public void exportData() {
        String fileName;
        ObjectOutputStream out;
        BufferedReader in;
        
        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Geben Sie bitte den Dateipfad ein: ");        
            fileName = in.readLine();
        } catch(IOException ex) {
            System.out.println("Fehler: Es konnte kein gültiger Dateipfad eingelesen werden.");
            return;
        }
        
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(m_array);
            out.close();
        } catch(IOException ex) {
            System.out.println("Fehler: Datei wurde nicht geschrieben.");
        }
    }
    
    public void importData() {
        String fileName;
        ObjectInputStream ois;
        BufferedReader in;
        
        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Geben Sie bitte den Dateipfad ein: ");        
            fileName = in.readLine();
        } catch(IOException ex) {
            System.out.println("Fehler: Es konnte kein gültiger Dateipfad eingelesen werden.");
            return;
        }
        
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            m_array = (float[])ois.readObject();
            ois.close();
        } catch(Exception ex) {
            System.out.println("Fehler: Datei wurde nicht eingelesen.");
        }
        m_left = 0; m_right = 0;
    }
    
    class Result {
        double m_sum;
        int m_left, m_right;

        Result(double sum, int left, int right) { 
            m_sum = sum; m_left = left; m_right = right; 
        }
    }
    
    private Result recursive(int from, int to) {
        if (to - from < 2) {
            return (m_array[from] > 0) ? new Result(m_array[from], from, from) : new Result(0, 0, -1);
        } else {
            // divide
            int median = (from + to)/2;
            // conquer
            Result maxtinL = recursive(from, median);
            Result maxtinR = recursive(median, to);
            double rmaxL = 0;
            int i, left = median, right = median - 1;
            double summe = 0;
            for (i=median - 1; i >= from; i--) {
                summe += m_array[i];
                if (summe > rmaxL) {
                    rmaxL = summe;
                    left = i;
                }
            }
            double rmaxR = 0;
            summe = 0;
            for (i=median; i < to; i++) {
                summe += m_array[i];
                if (summe > rmaxR) {
                    rmaxR = summe;
                    right = i;
                }
            }
            // merge
            summe = rmaxL + rmaxR;
            if (maxtinL.m_sum >= maxtinR.m_sum) {
                if (summe >= maxtinL.m_sum) {
                    return new Result(summe, left, right);
                } else {            
                    return maxtinL;
                }
            } else {
                if (summe > maxtinR.m_sum) {
                    return new Result(summe, left, right);
                } else {
                    return maxtinR;
                }
            }
        }
    }
    
    public double computeRecursive() {
        Result result = recursive(0, m_array.length);
        m_left = result.m_left;
        m_right = result.m_right;
        return result.m_sum;
    }
    
    public double computeEfficient() {
        double retValue = 0, summe = 0;
        int left = 0;
        
        m_left = left; m_right = -1;
        
        for (int i=0; i < m_array.length; i++) {
            summe += m_array[i];
            if (summe > retValue) {
                retValue = summe;
                m_left = left; m_right = i;
            } else if (summe < 0) {
                summe = 0;
                left = i + 1;
            }
        }
        return retValue;
    }
    
    public static void main(String[] args) {
        final int len = 100000;
        
        System.out.println("\n\nBoersenkurse\n");
        
        System.out.println("Generiere Boersenkursverlauf der Länge " + len);
        Boersenkurs bk = new Boersenkurs(len);
        
        System.out.print("Berechne den optimalen Kursgewinn: ");
        System.out.println(bk.computeRecursive());
        System.out.println("Verwendeter Bereich: [" + bk.getLeft() + "," + bk.getRight() + "]");
        
        System.out.println("Speichere den Kurverlauf in eine Datei");
        bk.exportData();
        
        System.out.println("Lese einen Kursverlauf ein");
        bk.importData();
        
        System.out.print("Berechne den optimalen Kursgewinn: ");
        System.out.println(bk.computeEfficient());
        System.out.println("Verwendeter Bereich: [" + bk.getLeft() + "," + bk.getRight() + "]");
    }
}
