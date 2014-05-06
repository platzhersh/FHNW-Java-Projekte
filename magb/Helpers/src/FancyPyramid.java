import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;


public class FancyPyramid implements WindowListener, GLEventListener, KeyListener {

	
	//  --------------  globale Daten  -----------------
	
	double alpha=40, beta=10, r=1;                        // Azimut, Elevation Kamera-System in Grad
	double alphaEuler, betaEuler, gammaEuler;
	double left=-10, right=10;                            // ViewingVolume (Orthogonalprojktion)
	double bottom, top;
	double near=-100, far=100;
	GLU glu = new GLU();                                  // Objekt fuer Utility-Library
	GLUT glut = new GLUT();                               // Objekt fuer Utility-Library
	GLCanvas canvas;                                      // OpenGL-Window
	double l = 2;

	
	//  ------------------  Methoden  --------------------
	
	void lookAt(GLU glu, double r,             // Kamera-System ausrichten
	double alpha,                              // Azimut in Grad
	double beta) {                             // Elevation
		alpha = Math.toRadians(alpha);         // Umwandlung in rad
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
	
	
	void zeichnePyramide(GL2 gl) {
		gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex3d(0,0,0);        
		gl.glVertex3d(l,0,0);
		gl.glVertex3d(l,0,l);
		gl.glVertex3d(0,0,l);   
		gl.glVertex3d(l/2,l,l/2);
		gl.glVertex3d(0,0,0);
		
		gl.glEnd();
	}
	
	void zeichneAchsen(GL2 gl, double len) {             // Koordinatenachsen zeichnen
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3d(0,0,0);        // x-Achse
		gl.glVertex3d(len,0,0);
		gl.glVertex3d(0,0,0);        // y-Achse
		gl.glVertex3d(0,len,0);
		gl.glVertex3d(0,0,0);        // z-Achse
		gl.glVertex3d(0,0,len);
		gl.glEnd();
	}

	public static void main(String[] args)                       // main-Methode der Applikation
	{  
		new FancyPyramid();
	}
	
	public FancyPyramid()                                            // Konstruktor
	{  
		Frame f = new Frame("FancyPyramid");
		f.setSize(800, 600);
		f.addWindowListener(this);
		canvas = new GLCanvas();                                  // OpenGL-Window
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		f.add(canvas);
		f.setVisible(true);
	}

	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
		
		CamSys camSys = new CamSys();
	    camSys.rotateRel(alpha, 0, 1, 0);
	    camSys.rotateRel(beta, 1, 0, 0);    
	    gl.glLoadMatrixd(camSys.getViewMatrixLinear(),0);
	    
	    
	    zeichneAchsen(gl, 10);
	    
	    ModelSys modelSys = new ModelSys();
	    modelSys.rotateAbs(alphaEuler, 0, 1, 0);
	    modelSys.rotateAbs(betaEuler, 1, 0, 0);
	    modelSys.rotateAbs(gammaEuler, 0, 1, 0);
	    gl.glMultMatrixd(modelSys.getModelMatrixLinear(), 0);
		
		zeichnePyramide(gl);
		
		zeichneAchsen(gl, 10);

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // erasing color
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyChar();
		switch (key)
			{  
			case 'a' : alphaEuler--;
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
			case KeyEvent.VK_UP : 
				beta++;
				System.out.println("up");
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
			case KeyEvent.VK_PAGE_UP : 
				System.out.println("zoom in");
				break;
			case KeyEvent.VK_PAGE_DOWN : right--; right--; near--; far++;
				System.out.println("zoom out");
				break;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}


	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

}
