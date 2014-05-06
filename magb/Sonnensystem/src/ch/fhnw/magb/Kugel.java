package ch.fhnw.magb;

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

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Kugel implements WindowListener, GLEventListener, KeyListener {

//  --------------  globale Daten  -----------------

	double alpha = 0, beta = 0, r = 1; // Azimut & Elevation in Grad
	double centerX = 0, centerY = 0, centerZ = 0;
	double upX = 0, upY = 1, upZ = 0;
	double left = -5, right = 5; // ViewingVolume
	double bottom, top;
	double near = -100, far = 100;
	double objAlpha = 0, objBeta = 0, objGamma = 0;

	double d = 5; //distanz kamera
	
	double dAlpha = 0, dBeta = 0; 

	double phi = 0.0;

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
	public Kugel() {
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
		new Kugel();
	}

//  ---------  OpenGL-Events  -----------------------

	public void init(GLAutoDrawable drawable) {
		GL gl0 = drawable.getGL(); // OpenGL-Objekt
		GL2 gl = gl0.getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // erasing color (RGBA)
		gl.glEnable(gl.GL_DEPTH_TEST); // Sichtbarkeitstest (z-Buffer)
		gl.glEnable(gl.GL_LIGHT0); // Lichtquelle 0 einschalten

		float[] amb =
			{ 0.4f, 0.4f, 0.4f, 1.0f };
		gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, amb, 0);

		FPSAnimator anim = new FPSAnimator(canvas, 200, true);
		anim.start();
	}

	public void display(GLAutoDrawable drawable) {
		
		double rSonne= 1, rErde=0.5;
		double rBahn = 10;
		double eps = 23.5;
		
		GL gl0 = drawable.getGL();
		GL2 gl = gl0.getGL2();
		
		
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | // Bild loeschen
				gl.GL_DEPTH_BUFFER_BIT); // z-Buffer loeschen
		gl.glColor3d(0.5, 0.5, 0.5); // Zeichenfarbe (RGB)
		gl.glMatrixMode(gl.GL_MODELVIEW);
		
		CamSys camSys = new CamSys();
		camSys.rotateAbs(-beta, 1, 0, 0);
		camSys.rotateAbs(alpha, 0, 1, 0);
		
		gl.glLoadMatrixd(camSys.getViewMatrixLinear(), 0);
		gl.glDisable(gl.GL_LIGHTING);
		zeichneAchsen(gl, 10);
		gl.glEnable(gl.GL_LIGHTING);
		float[] pos = { -20, 20, 100, 1};
		gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, pos, 0);
		glut.glutSolidSphere(rSonne, 20, 20);
		
		gl.glPushMatrix();
		
		ModelSys modelSys = new ModelSys();
		modelSys.translateAbs(0, 0, 10);
		modelSys.rotateAbs(dAlpha+=0.1, 0, 1, 0);
		modelSys.rotateRel(23.5,0,0,1);
		
		modelSys.rotateRel(dBeta+=0.2, 0, 1, 0);
		gl.glMultMatrixd(modelSys.getModelMatrixLinear(), 0);
		
		glut.glutSolidSphere(rErde, 20, 20);
		
//		/* ----------------- */
//		gl.glLoadIdentity(); // Rueckstellung (Einheitsmatrix)
//
//		float[] amb =
//			{ 0.6f, 0.6f, 0.6f, 1.0f };
////		gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, amb, 0); // festlegen des ambienten Lichts
//		gl.glMaterialfv(gl.GL_FRONT, gl.GL_AMBIENT, amb, 0); // Reflexionskoeffizient
//
//		float[] diff =
//			{ 0.5f, 0.5f, 0.5f, 1.0f };
//		gl.glMaterialfv(gl.GL_FRONT, gl.GL_DIFFUSE, diff, 0); // Streulicht
//
//		float[] spec =
//			{ 0.5f, 0.5f, 0.5f, 1 };
//		gl.glMaterialfv(gl.GL_FRONT, gl.GL_SPECULAR, spec, 0); // spiegelnde Reflexion
//
//		float[] specExp =
//			{ 20 };
//		gl.glMaterialfv(gl.GL_FRONT, gl.GL_SHININESS, specExp, 0); // Exponent (shininess)
//
////		CamSys camSys = new CamSys();
////
////		camSys.translateAbs(0, 0, d);
////		camSys.rotateAbs(beta, 1, 0, 0);
////		camSys.rotateAbs(-alpha, 0, 1, 0);
////		camSys.rotateRel(Math.sin(Math.toRadians(phi)) * r * d, 0, 1, 0);
//
//		gl.glLoadMatrixd(camSys.getViewMatrixLinear(), 0);
//		gl.glDisable(gl.GL_LIGHTING);
//		zeichneAchsen(gl, 10);
//
//		gl.glPushMatrix();
//
//		ModelSys modelSys = new ModelSys();
//		modelSys.translateRel(0, 0, -0.5 * r);
//		modelSys.rotateRel(phi, 0, 1, 0);
//		modelSys.translateRel(0, 0, 4 * r);
//
//		gl.glMultMatrixd(modelSys.getModelMatrixLinear(), 0);
//
//		gl.glPopMatrix();
//
//		gl.glEnable(gl.GL_LIGHTING);
//		gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);
//
//		glut.glutSolidTeapot(r);
//
////		modelSys.rotateRel(phi, 0, 1, 0);
////		modelSys.translateRel(0, 0, 4 * r);
//		gl.glMultMatrixd(modelSys.getModelMatrixLinear(), 0);
//		glut.glutWireSphere(r, 10, 10);
//
//		float[] pos =
//			{ -4f, 20f, 20f, 1f };
//		gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, pos, 0);
//		phi++;
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
//		gl.glFrustum(left, right, bottom, top, 1, 10);
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

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
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
