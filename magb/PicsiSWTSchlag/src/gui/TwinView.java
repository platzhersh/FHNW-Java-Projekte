package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import files.Document;

public class TwinView extends Composite {
	public MainWindow m_mainWnd;
	
	private Document m_doc1, m_doc2;
	private View m_view1, m_view2;
	private float m_zoom = 1.0f;
	private boolean m_autoZoom = true;
	
	public TwinView(MainWindow mainWnd, Composite parent, int style) {
		super(parent, style);
		
		m_mainWnd = mainWnd;
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 800;
		data.heightHint = 600;
		setLayoutData(data);
		setLayout(new FillLayout());
		
		m_doc1 = new Document();
		m_view1 = new View(this);
	}

	public void clean() {
		m_view1.setImage(null, true);
		if (m_view2 != null) m_view2.setImage(null, true);
	}
	
	public boolean isEmpty() {
		return m_view1.getImage() == null;
	}
	
	public View getView(boolean first) {
		return (first) ? m_view1 : m_view2;
	}

	public Document getDocument(View view) {
		return (view == m_view1) ? m_doc1 : m_doc2;
	}
	
	public Document getDocument(boolean first) {
		return (first) ? m_doc1 : m_doc2;
	}
	
	public void load(String filename, int filetype, Display display) throws Exception {
		m_view1.setImage(m_doc1.load(filename, filetype, display), m_view2 == null || m_view1.getImage() != m_view2.getImage());
		layout();
		refresh();
}
	
	public void save(String filename, int filetype) throws Exception {
		assert m_doc2 != null : "m_doc2 is null";
		m_doc2.save(m_view2.getImage(), filename, filetype);
	}
	
	public boolean hasAutoZoom() {
		return m_autoZoom;
	}
	
	public boolean hasSecondView() {
		return m_view2 != null;
	}
	
	public void zoom(float f) {
		float zoom = m_zoom*f;
		if (zoom > 100) {
			zoom = 100;
		} else if (zoom < 0.01f) {
			zoom = 0.01f;
		}
		setZoom(zoom);
	}
	
	public float getZoom() {
		return m_zoom;
	}

	public Image getFirstImage() {
		return m_view1.getImage();
	}
	
	public int getFirstimageType() {
		return m_view1.getimageType();
	}
	
	public void swapImages() {
		assert hasSecondView() : "m_view2 is null";
		
		// swap images
		Image tmp = m_view1.getImage();
		m_view1.setImage(m_view2.getImage(), false);
		m_view2.setImage(tmp, false);
	}
	
	public void showImageInSecondView(Image image) {
		if (image == null) return;
		
		if (!hasSecondView()) m_mainWnd.toggleOutputWindow();
		if (hasSecondView()) {
			m_view2.setImage(image, m_view1.getImage() != m_view2.getImage());
			layout();
			refresh();
		}
	}
	
	public void split(boolean split) {
		if (split && !hasSecondView()) {
			// create second view
			m_doc2 = new Document();
			m_view2 = new View(this);
			m_view2.setImage(m_view1.getImage(), false);
		} else if (hasSecondView()) {
			// destroy second view
			m_view2.setImage(null, m_view1.getImage() != m_view2.getImage());
			m_view2.dispose();
			m_view2 = null;
			m_doc2 = null;
		}
		layout();		
	}
	
	@Override
	public void layout() {
		if (!isEmpty()) {
			Layout l = getLayout();
			assert l instanceof FillLayout : "wrong layout";
			FillLayout fillLayout = (FillLayout)l;
			fillLayout.type = (m_view1.isPortrait()) ? SWT.HORIZONTAL : SWT.VERTICAL;
		}
		super.layout();
	}
	
	public void refresh() {
		setCursor(getDisplay().getSystemCursor(SWT.CURSOR_CROSS));
		if (m_autoZoom) setAutoZoom(true);
		else setZoom(m_zoom);
	}
	
	public void setAutoZoom(boolean b) {
		m_autoZoom = b;
		if (m_autoZoom) {
			// compute minimal zoom factor
			
			setZoom(m_view1.computeBestZoomFactor());
		}
	}
	
	public void zoom100() {
		setAutoZoom(false);
		setZoom(1.0f);
	}
	
	public void setCursor(Cursor cursor) {
		m_view1.setCursor(cursor);
		if (m_view2 != null) m_view2.setCursor(cursor);
	}
	
	private void setZoom(float zoom) {
		m_zoom = zoom;
		
		m_mainWnd.showZoomFactor(m_zoom);
		
		m_view1.updateScrollBars();
		if (m_view2 != null) m_view2.updateScrollBars();
	}
}
