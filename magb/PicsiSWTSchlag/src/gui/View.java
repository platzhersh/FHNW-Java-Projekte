package gui;
import main.PicsiSWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class View extends Canvas {
	private TwinView m_twins;
	private int m_scrollPosX, m_scrollPosY;
	private Image m_image;
	private ImageData m_imageData;
	private int m_imageType;
	private PrinterData m_printerData;
	
	public View(TwinView compo) {
		super(compo, SWT.V_SCROLL | SWT.H_SCROLL | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		m_twins = compo;
		
		setBackground(new Color(getDisplay(), 128, 128, 255));
		setCursor(getDisplay().getSystemCursor(SWT.CURSOR_CROSS));
		
		// Hook resize listener
		addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				if (m_image != null) m_twins.refresh();
			}
		});
		
		// Set up the scroll bars.
		ScrollBar horizontal = getHorizontalBar();
		horizontal.setVisible(true);
		horizontal.setMinimum(0);
		horizontal.setEnabled(false);
		horizontal.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (m_image != null) {
					Rectangle canvasBounds = getClientArea();
					int width = zoomedWidth();
					int height = zoomedHeight();
					if (width > canvasBounds.width) {
						// Only scroll if the image is bigger than the canvas.
						int x = -((ScrollBar)event.widget).getSelection();
						if (x + width < canvasBounds.width) {
							// Don't scroll past the end of the image.
							x = canvasBounds.width - width;
						}
						scroll(x, m_scrollPosY, m_scrollPosX, m_scrollPosY, width, height, false);
						m_scrollPosX = x;
					}
				}
			}
		});
		ScrollBar vertical = getVerticalBar();
		vertical.setVisible(true);
		vertical.setMinimum(0);
		vertical.setEnabled(false);
		vertical.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (m_image != null) {
					Rectangle canvasBounds = getClientArea();
					int width = zoomedWidth();
					int height = zoomedHeight();
					if (height > canvasBounds.height) {
						// Only scroll if the image is bigger than the canvas.
						int y = -((ScrollBar)event.widget).getSelection();
						if (y + height < canvasBounds.height) {
							// Don't scroll past the end of the image.
							y = canvasBounds.height - height;
						}
						scroll(m_scrollPosX, y, m_scrollPosX, m_scrollPosY, width, height, false);
						m_scrollPosY = y;
					}
				}
			}
		});
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent event) {
				if (m_image != null) {
					paint(event);
				} else {
					Rectangle bounds = getBounds();
					event.gc.fillRectangle(0, 0, bounds.width, bounds.height);
				}
			}
		});
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent event) {
				if (m_image != null) {
					m_twins.m_mainWnd.showColorForPixel(getPixelInfoAt(event.x - m_scrollPosX,  event.y - m_scrollPosY));
				}
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseScrolled(MouseEvent event) {
				if (m_image != null && !m_twins.hasAutoZoom()) {
					//System.out.println("" + event.x + " " + event.y + " " + event.count);
					if (event.count < 0) {
						m_twins.zoom(1.5f);
					} else if (event.count > 0) {
						m_twins.zoom(1/1.5f);
					}
				}
			}
		});
	}

	public boolean isPortrait() {
		// assume 16:16 screen resolution
		assert m_imageData != null : "m_imageData is null";
		return m_imageData.width*10 < m_imageData.height*16;
	}
	
	public float computeBestZoomFactor() {
		assert m_imageData != null : "m_imageData is null";

		Rectangle canvasBounds = getClientArea();
		float xFactor = (float)canvasBounds.width/m_imageData.width;
		float yFactor = (float)canvasBounds.height/m_imageData.height;
		
		return Math.min(xFactor, yFactor);
	}
	
	public PrinterData getPrinterData() {
		return m_printerData;
	}
	
	public int getimageType() {
		return m_imageType;
	}
	
	public Image getImage() {
		return m_image;
	}
	
	public void setImage(Image image, boolean dispose) {
		if (dispose && m_image != null && m_image != image) {
			m_image.dispose();
		}
		m_image = image;
		if (m_image != null) {
			m_imageData = image.getImageData();
			m_imageType = PicsiSWT.determineimageType(m_imageData);
		} else {
			m_imageData = null;
		}
		updateScrollBars();
	}
	
	public void updateScrollBars() {
		// Set the max and thumb for the image canvas scroll bars.
		ScrollBar horizontal = getHorizontalBar();
		ScrollBar vertical = getVerticalBar();
		Rectangle canvasBounds = getClientArea();
		
		int width = zoomedWidth();
		if (width > canvasBounds.width) {
			// The image is wider than the canvas.
			horizontal.setEnabled(true);
			horizontal.setMaximum(width);
			horizontal.setThumb(canvasBounds.width);
			horizontal.setPageIncrement(canvasBounds.width);
		} else {
			// The canvas is wider than the image.
			horizontal.setEnabled(false);
			if (m_scrollPosX != 0) {
				// Make sure the image is completely visible.
				m_scrollPosX = 0;
			}
		}
		int height = zoomedHeight();
		if (height > canvasBounds.height) {
			// The image is taller than the canvas.
			vertical.setEnabled(true);
			vertical.setMaximum(height);
			vertical.setThumb(canvasBounds.height);
			vertical.setPageIncrement(canvasBounds.height);
		} else {
			// The canvas is taller than the image.
			vertical.setEnabled(false);
			if (m_scrollPosY != 0) {
				// Make sure the image is completely visible.
				m_scrollPosY = 0;
			}
		}
		redraw();
	}
	
	public Object[] getPixelInfoAt(int x, int y) {
		if (m_image == null) return null;
		
		float zoom = m_twins.getZoom();
		
		x = (int)(x/zoom);
		y = (int)(y/zoom);
		
		if (x >= 0 && x < m_imageData.width && y >= 0 && y < m_imageData.height) {
			int pixel = m_imageData.getPixel(x, y);
			RGB rgb = m_imageData.palette.getRGB(pixel);
			boolean hasAlpha = false;
			int alphaValue = 0;
			if (m_imageData.alphaData != null && m_imageData.alphaData.length > 0) {
				hasAlpha = true;
				alphaValue = m_imageData.getAlpha(x, y);
			}
			String rgbMessageFormat = (hasAlpha) ? "RGBA '{'{0}, {1}, {2}, {3}'}'" : "RGB '{'{0}, {1}, {2}'}'";
			Object[] rgbArgs = {
					Integer.toString(rgb.red),
					Integer.toString(rgb.green),
					Integer.toString(rgb.blue),
					Integer.toString(alphaValue)
			};
			Object[] rgbHexArgs = {
					Integer.toHexString(rgb.red),
					Integer.toHexString(rgb.green),
					Integer.toHexString(rgb.blue),
					Integer.toHexString(alphaValue)
			};
			Object[] args = {
					new Integer(x),
					new Integer(y),
					new Integer(pixel),
					Integer.toHexString(pixel),
					PicsiSWT.createMsg(rgbMessageFormat, rgbArgs),
					PicsiSWT.createMsg(rgbMessageFormat, rgbHexArgs),
					(pixel == m_imageData.transparentPixel) ? "(transparent)" : ""};
			return args;
		}
		return null;
	}
	
	private void paint(PaintEvent event) {		
		GC gc = event.gc;
		final int w = zoomedWidth();
		final int h = zoomedHeight();
		
		/* If any of the background is visible, fill it with the background color. */
		Rectangle bounds = getBounds();
		if (m_imageData.getTransparencyType() != SWT.TRANSPARENCY_NONE) {
			/* If there is any transparency at all, fill the whole background. */
			gc.fillRectangle(0, 0, bounds.width, bounds.height);
		} else {
			/* Otherwise, just fill in the backwards L. */
			if (m_scrollPosX + w < bounds.width) gc.fillRectangle(m_scrollPosX + w, 0, bounds.width - (m_scrollPosX + w), bounds.height);
			if (m_scrollPosY + h < bounds.height) gc.fillRectangle(0, m_scrollPosY + h, m_scrollPosX + w, bounds.height - (m_scrollPosY + h));
		}

		if (m_image != null) {
			/* Draw the image */
			gc.drawImage(
				m_image,
				0,
				0,
				m_imageData.width,
				m_imageData.height,
				m_scrollPosX + m_imageData.x,
				m_scrollPosY + m_imageData.y,
				w,
				h);		
		}
	}
	
	private int zoomedWidth() {
		return (m_image == null) ? 0 : Math.round(m_twins.getZoom()*m_imageData.width);
	}

	private int zoomedHeight() {
		return (m_image == null) ? 0 : Math.round(m_twins.getZoom()*m_imageData.height);
	}

	public Throwable print(Display display) {
		ImageData imageData = m_image.getImageData();
		
		try {
			Printer printer = new Printer(m_printerData);
			Point screenDPI = display.getDPI();
			Point printerDPI = printer.getDPI();
			int scaleFactor = printerDPI.x/screenDPI.x;
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			if (printer.startJob(m_twins.getDocument(this).getFileName())) {
				if (printer.startPage()) {
					GC gc = new GC(printer);
					Image printerImage = new Image(printer, imageData);
					gc.drawImage(
						printerImage,
						0,
						0,
						imageData.width,
						imageData.height,
						-trim.x,
						-trim.y,
						scaleFactor*imageData.width,
						scaleFactor*imageData.height);
					printerImage.dispose();
					gc.dispose();
					printer.endPage();
				}
				printer.endJob();
			}
			printer.dispose();
		} catch (SWTError e) {
			return e;
		}
		return null;
	}
	
}
