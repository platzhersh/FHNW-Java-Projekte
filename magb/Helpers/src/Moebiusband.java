//-------------   JOGL SampleProgram  (Dreieck im Raum) ------------

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

public class Moebiusband implements WindowListener, GLEventListener,
		KeyListener {

//  --------------  globale Daten  -----------------

	double alpha = 40, beta = 10, r = 1; // Azimut & Elevation in Grad
	double centerX = 0, centerY = 0, centerZ = 0;
	double upX = 0, upY = 1, upZ = 0;
	double left = -20, right = 20; // ViewingVolume
	double bottom, top;
	double near = -100, far = 100;
	double objAlpha = 0, objBeta = 0, objGamma = 0;

	double d = 5; // distanz kamera

	GLCanvas canvas = null;

	GLU glu = new GLU(); // Objekt fuer Utility-Library
	GLUT glut = new GLUT();

//  ------------------  Methoden  --------------------

// Koordinatenachsen zeichnen
	void zeichneAchsen(GL2 gl, double len) {
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3d(0, 0, 0); // x-Achse
		gl.glVertex3d(len, 0, 0);
		gl.glVertex3d(0, 0, 0); // y-Achse
		gl.glVertex3d(0, len, 0);
		gl.glVertex3d(0, 0, 0); // z-Achse
		gl.glVertex3d(0, 0, len);
		gl.glEnd();
	}

	void zeichneMobius(GL2 gl, double a, double b, double radius, double winkel) {
		ModelSys mat = new ModelSys();

		Vector p1 = new Vector(a, 0.0, b, 1.0);
		Vector p2 = new Vector(a, 0.0, -b, 1.0);
		Vector p3 = new Vector(-a, 0.0, -b, 1.0);
		Vector p4 = new Vector(-a, 0.0, b, 1.0);

		gl.glColor3d(0.98, 0.72, 0.16); // Farbe

		mat.translateRel(0.0, 0.0, radius);

		for (int i = 0; i < 360 / winkel; i++) {
			gl.glPushMatrix();

			mat.rotateAbs(winkel, 0.0, 1.0, 0.0);
			mat.rotateRel(winkel / 2, 1.0, 0.0, 0.0);

			gl.glMultMatrixd(mat.getModelMatrixLinear(), 0);
			gl.glBegin(gl.GL_POLYGON);

			gl.glVertex3d(p1.x(0), p1.x(1), p1.x(2));
			gl.glVertex3d(p2.x(0), p2.x(1), p2.x(2));
			gl.glVertex3d(p3.x(0), p3.x(1), p3.x(2));
			gl.glVertex3d(p4.x(0), p4.x(1), p4.x(2));

			gl.glEnd();
			gl.glPopMatrix();
		}
	}

	double getCos(double phi) {
		return Math.cos(Math.toRadians(phi));
	}

	double getSin(double phi) {
		return Math.sin(Math.toRadians(phi));
	}

// position der Kamera
	void lookAt(GL2 gl, double r, double alpha, double beta) { // alpha = Azimut, beta = Elevation
		double sinAlpha = Math.sin(Math.toRadians(alpha));
		double cosAlpha = Math.cos(Math.toRadians(alpha));
		double sinBeta = Math.sin(Math.toRadians(beta));
		double cosBeta = Math.cos(Math.toRadians(beta));

		double x = r * cosBeta * sinAlpha;
		double y = r * sinBeta;
		double z = r * cosBeta * cosAlpha;

		glu.gluLookAt(x, y, z, 0, 0, 0, 0, 1, 0); // Kamera-System positionieren
	}

// Konstruktor
	public Moebiusband() {
		Frame f = new Frame("MyFirst");
		f.setSize(800, 600);
		f.addWindowListener(this);
		canvas = new GLCanvas(); // OpenGL-Window
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		f.add(canvas);
		f.setVisible(true);
	}

// main-Methode der Applikation
	public static void main(String[] args) {
		new Moebiusband();
	}

//  ---------  OpenGL-Events  -----------------------

	public void init(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL(); // OpenGL-Objekt
		GL2 gl = gl0.getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // erasing color (RGBA)
		gl.glEnable(gl.GL_DEPTH_TEST); // Sichtbarkeitstest (z-Buffer)
	}

	public void display(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | // Bild loeschen
				gl.GL_DEPTH_BUFFER_BIT); // z-Buffer loeschen
		gl.glColor3d(0.5, 0.5, 0.5); // Zeichenfarbe (RGB)
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity(); // Rueckstellung (Einheitsmatrix)

		lookAt(gl, r, alpha, beta);

		zeichneAchsen(gl, 10);

		zeichneMobius(gl, 0.1, 1, 10, 2);

	}

// Window resized
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		gl.glViewport(0, 0, width, height); // Ausgabe-Window
		double aspect = (double) height / width; // Window-Verhaeltnis
		bottom = aspect * left;
		top = aspect * right;
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity(); // Rueckstellung (Einheitsmatrix)
		gl.glOrtho(left, right, bottom, top, near, far); // ViewingVolume fuer Orthogonalprojektion
	}

	public void dispose(GLAutoDrawable drawable) {}

//  ---------  Window-Events  --------------------

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowActivated(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowOpened(WindowEvent e) {}

// ----------  Keyboard-Events  --------------------

	@Override
	public void keyTyped(KeyEvent e) {}

// Verschiebung der Kamera
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			beta += 10;
			break;
		case KeyEvent.VK_DOWN:
			beta -= 10;
			break;
		case KeyEvent.VK_RIGHT:
			alpha += 10;
			break;
		case KeyEvent.VK_LEFT:
			alpha -= 10;
			break;
		default:
			break;
		}
		canvas.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
