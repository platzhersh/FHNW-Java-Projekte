// ----------------  Eulerwinkel  --------------------------------------

import javax.media.opengl.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.*;


public class EulerAngles implements WindowListener, GLEventListener,
                                KeyListener
{

     //  --------------  globale Daten  -----------------

    double alpha=40, beta=10, r=1;                        // Azimut, Elevation Kamera-System in Grad
    double alphaEuler, betaEuler, gammaEuler;
    double left=-2, right=2;                              // ViewingVolume (Orthogonalprojktion)
    double bottom, top;
    double near=-100, far=100;
    GLU glu = new GLU();                                  // Objekt fuer Utility-Library
    GLCanvas canvas;                                      // OpenGL-Window


    //  ------------------  Methoden  --------------------

     void lookAt(GLU glu, double r,                        // Kamera-System ausrichten
                 double alpha,                             // Azimut in Grad
                 double beta)                              // Elevation
     { alpha = Math.toRadians(alpha);                      // Umwandlung in rad
       beta = Math.toRadians(beta);
       double sinAlpha = Math.sin(alpha);
       double cosAlpha = Math.cos(alpha);
       double sinBeta = Math.sin(beta);
       double cosBeta = Math.cos(beta);
       double x = r*sinAlpha*cosBeta;
       double y = r*sinBeta;
       double z = r*cosAlpha*cosBeta;
       glu.gluLookAt(x, y, z,                              // Kamera-System positionieren
                     0, 0, 0,
                     0, 1, 0);
     }


     void zeichneAchsen(GL2 gl, double len)               // Koordinatenachsen zeichnen
     {  gl.glBegin(gl.GL_LINES);
        gl.glVertex3d(0,0,0);        // x-Achse
        gl.glVertex3d(len,0,0);
        gl.glVertex3d(0,0,0);        // y-Achse
        gl.glVertex3d(0,len,0);
        gl.glVertex3d(0,0,0);        // z-Achse
        gl.glVertex3d(0,0,len);
        gl.glEnd();
     }


     void zeichneAchsen2(GL2 gl, double len, Matrix T)               // Koordinatenachsen zeichnen
     {   Vector O = new Vector(0,0,0,1);
         Vector X = new Vector(len,0,0,1);
         Vector Y = new Vector(0,len,0,1);
         Vector Z = new Vector(0,0,len,1);
         O = T.times(O);
         X = T.times(X);
         Y = T.times(Y);
         Z = T.times(Z);
         gl.glBegin(gl.GL_LINES);
         gl.glVertex3d(O.x(0),O.x(1),O.x(2));        // x-Achse
         gl.glVertex3d(X.x(0),X.x(1),X.x(2));
         gl.glVertex3d(O.x(0),O.x(1),O.x(2));        // y-Achse
         gl.glVertex3d(Y.x(0),Y.x(1),Y.x(2));
         gl.glVertex3d(O.x(0),O.x(1),O.x(2));        // z-Achse
         gl.glVertex3d(Z.x(0),Z.x(1),Z.x(2));
         gl.glEnd();
     }


     public EulerAngles()                                            // Konstruktor
     {  Frame f = new Frame("Euler'sche Winkel");
        f.setSize(800, 600);
        f.addWindowListener(this);
        canvas = new GLCanvas();                                  // OpenGL-Window
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        f.add(canvas);
        f.setVisible(true);
     }


     public static void main(String[] args)                       // main-Methode der Applikation
     {  new EulerAngles();
     }


     //  ---------  OpenGL-Events  -----------------------

     public void init(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();                                // OpenGL-Objekt
        GL2 gl = gl0.getGL2();
        gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);                  // erasing color (RGBA)
        gl.glEnable(gl.GL_DEPTH_TEST);                            // Sichtbarkeitstest (z-Buffer)
     }


     public void display(GLAutoDrawable drawable)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glClear(gl.GL_COLOR_BUFFER_BIT |                      // Bild loeschen
                   gl.GL_DEPTH_BUFFER_BIT);                      // z-Buffer loeschen
        gl.glColor3d(0.5,0.5,0.5);                               // Zeichenfarbe (RGB)
        gl.glMatrixMode(gl.GL_MODELVIEW);
        
        CamSys camSys = new CamSys();
        camSys.rotateRel(alpha, 0, 1, 0);
        camSys.rotateRel(beta, 1, 0, 0);
        
        
        gl.glLoadMatrixd(camSys.getViewMatrixLinear(),0);
        zeichneAchsen(gl,10);
        gl.glColor3d(1, 1, 0);                                   // Zeichenfarbe
        
        ModelSys modelSys = new ModelSys();
        modelSys.rotateAbs(alphaEuler, 0, 1, 0);
        modelSys.rotateAbs(betaEuler, 1, 0, 0);
        modelSys.rotateAbs(gammaEuler, 0, 1, 0);
        
        gl.glMultMatrixd(modelSys.getModelMatrixLinear(), 0);
        
        zeichneAchsen(gl,2);
     }


     public void reshape(GLAutoDrawable drawable,                // Window resized
                         int x, int y,
                         int width, int height)
     {  GL gl0 = drawable.getGL();
        GL2 gl = gl0.getGL2();
        gl.glViewport(0, 0, width, height);                      // Ausgabe-Window
        double aspect = (double)height/width;                    // Window-Verhaeltnis
        bottom=aspect*left;
        top=aspect*right;
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();                                     // Rueckstellung (Einheitsmatrix)
        gl.glOrtho(left,right,bottom,top,near,far);              // ViewingVolume fuer Orthogonalprojektion
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


     //  ---------  Keyboard-Events  ------------------

     public void keyPressed(KeyEvent e)
     { int key = e.getKeyCode();
       switch (key)
       { case KeyEvent.VK_UP : beta++;
                               canvas.repaint();
                               break;
         case KeyEvent.VK_DOWN : beta--;
                               canvas.repaint();
                               break;
         case KeyEvent.VK_LEFT : alpha--;
                               canvas.repaint();
                               break;
         case KeyEvent.VK_RIGHT : alpha++;
                               canvas.repaint();
                               break;
       }
     }
     public void keyReleased(KeyEvent e) { }
     public void keyTyped(KeyEvent e)
     { char key = e.getKeyChar();
       switch (key)
       {  case 'a' : alphaEuler--;
                     canvas.repaint();
                     break;
          case 'A' : alphaEuler++;
                     canvas.repaint();
                     break;
          case 'b' : betaEuler--;
                     canvas.repaint();
                     break;
          case 'B' : betaEuler++;
                     canvas.repaint();
                     break;
          case 'c' : gammaEuler--;
                     canvas.repaint();
                     break;
          case 'C' : gammaEuler++;
                     canvas.repaint();
                     break;
        }
     }

  }
