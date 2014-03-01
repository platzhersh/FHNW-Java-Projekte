//  -------------   JOGL SampleProgram  (Dreieck im Raum) ------------

import javax.media.opengl.*;
import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.*;


public class Raumspirale implements WindowListener, GLEventListener, KeyListener
{

     //  --------------  globale Daten  -----------------

    double alpha=40, beta=10, r=1;                        // Azimut, Elevation, .. , in Grad
    double centerX=0, centerY=0, centerZ=0;
    double upX=0, upY=1, upZ=0;
    double left=-2, right=2;                              // ViewingVolume
    double bottom, top;
    double near=-100, far=100;
    GLCanvas canvas;

    GLU glu = new GLU();                                  // Objekt fuer Utility-Library


     //  ------------------  Methoden  --------------------

     void zeichneAchsen(GL2 gl, double len)                 // Koordinatenachsen zeichnen
     {  gl.glBegin(gl.GL_LINES);
        gl.glVertex3d(0,0,0);        // x-Achse
        gl.glVertex3d(len,0,0);
        gl.glVertex3d(0,0,0);        // y-Achse
        gl.glVertex3d(0,len,0);
        gl.glVertex3d(0,0,0);        // z-Achse
        gl.glVertex3d(0,0,len);
        gl.glEnd();
     }


     void zeichneRaumspirale(GL2 gl) {
        gl.glBegin(gl.GL_LINE_STRIP);
        for (float t = 0; t < 35; t += 0.1) {
        	double r = 1 - 0.02 * t;
        	double x = r * Math.sin(t);
        	double y = 0.03*t;
        	double z = r * Math.cos(t);
        	gl.glVertex3d(x,y,z);
        }        
        gl.glEnd();
     }


     public Raumspirale()                                             // Konstruktor
     {  Frame f = new Frame("MyFirst");
        f.setSize(800, 600);
        f.addWindowListener(this);
        canvas = new GLCanvas();                         // OpenGL-Window
        canvas.addKeyListener(this);
        canvas.addGLEventListener(this);
        f.add(canvas);
        f.setVisible(true);
     }


     public static void main(String[] args) {                      // main-Methode der Applikation
    	 new Raumspirale();
     }


     //  ---------  OpenGL-Events  -----------------------

     public void init(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();                               // OpenGL-Objekt
        GL2 gl = gl0.getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                 // erasing color (RGBA)
        gl.glEnable(gl.GL_DEPTH_TEST);                           // Sichtbarkeitstest (z-Buffer)
     }
     
     void lookAt(double r, double alpha, double beta) {
    	 double sinAlpha = Math.sin(Math.toRadians(alpha));
    	 double cosAlpha = Math.cos(Math.toRadians(alpha));
    	 double sinBeta = Math.sin(Math.toRadians(beta));
    	 double cosBeta = Math.cos(Math.toRadians(beta));
    	 
    	 double x = r*sinAlpha*cosBeta;
    	 double y = r*sinBeta;
    	 double z = r*cosAlpha*cosBeta;
    	 
    	 glu.gluLookAt(x, y, z,                         // Kamera-System positionieren
                 0, 0, 0,
                 0, 1, 0);
     }


     public void display(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glClear(gl.GL_COLOR_BUFFER_BIT |                     // Bild loeschen
                   gl.GL_DEPTH_BUFFER_BIT);                     // z-Buffer loeschen
        gl.glColor3d(0.5,0.5,0.5);                              // Zeichenfarbe (RGB)
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();                                    // Rueckstellung (Einheitsmatrix)
        
        lookAt(r,alpha,beta);
        
        zeichneAchsen(gl,10);
        gl.glColor3d(1, 0, 0);                                  // Zeichenfarbe
        
        
        
        zeichneRaumspirale(gl);
        gl.glColor3d(255,255,255);
        zeichneAchsen(gl,2);
     }


     public void reshape(GLAutoDrawable drawable,               // Window resized
                         int x, int y,
                         int width, int height)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glViewport(0, 0, width, height);                     // Ausgabe-Window
        double aspect = (double)height/width;                   // Window-Verhaeltnis
        bottom=aspect*left;
        top=aspect*right;
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();                                    // Rueckstellung (Einheitsmatrix)
        gl.glOrtho(left,right,bottom,top,near,far);             // ViewingVolume fuer Orthogonalprojektion
     }


     public void dispose(GLAutoDrawable drawable)
     { }


     //  ---------  Window-Events  --------------------

     public void windowClosing(WindowEvent e)
     {  System.exit(0);
     }
     public void windowActivated(WindowEvent e) {  }
     public void windowClosed(WindowEvent e) {  }
     public void windowDeactivated(WindowEvent e) {  }
     public void windowDeiconified(WindowEvent e) {  }
     public void windowIconified(WindowEvent e) {  }
     public void windowOpened(WindowEvent e) {  }


	@Override
	public void keyPressed(KeyEvent e) {
		
		int v = e.getKeyCode();
		switch (v) {
			case KeyEvent.VK_UP : 
				beta+=10;
				canvas.repaint();
				break;
			case KeyEvent.VK_DOWN : 
				beta-=10;
				canvas.repaint();
				break;
			case KeyEvent.VK_LEFT : 
				alpha-=10;
				canvas.repaint();
				break;
			case KeyEvent.VK_RIGHT : 
				alpha+=10;
				canvas.repaint();
				break;
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

  }
