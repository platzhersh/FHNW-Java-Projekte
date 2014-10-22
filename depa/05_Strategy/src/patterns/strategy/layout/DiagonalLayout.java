package patterns.strategy.layout;

import java.awt.*;

public class DiagonalLayout implements LayoutManager {

    private int vgap;
    private int preferredWidth = 0, preferredHeight = 0;

    public DiagonalLayout() {
        this(5);
    }

    public DiagonalLayout(int v) {
        vgap = v;
    }

    public void addLayoutComponent(String name, Component comp) {}
    public void removeLayoutComponent(Component comp) {}

    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d;

        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();

                if (i > 0) {
                    preferredWidth += d.width/2; 
                	  preferredHeight += d.height + vgap;
                } else {
                    preferredWidth = d.width;
                	  preferredHeight = d.height;
                }
            }
        }
    }


    public Dimension preferredLayoutSize(Container parent) {
        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        return new Dimension(
        		preferredWidth  + insets.left + insets.right,
        		preferredHeight + insets.top + insets.bottom);
    }

    public Dimension minimumLayoutSize(Container parent) {
    		return preferredLayoutSize(parent);
    }

    /* 
     * This is called when the panel is first displayed, 
     * and every time its size changes. 
     * Note: You CAN'T assume preferredLayoutSize or 
     * minimumLayoutSize will be called -- in the case 
     * of applets, at least, they probably won't be. 
     */
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getSize().width
                       - (insets.left + insets.right);
        int maxHeight = parent.getSize().height
                        - (insets.top + insets.bottom);
        int nComps = parent.getComponentCount();
        int previousWidth = 0, previousHeight = 0;
        int x = insets.left, y = insets.top;
        // int rowh = 0, start = 0;
        int xFudge = 0, yFudge = 0;

        setSizes(parent);
            
        if (nComps > 1 && maxWidth != preferredWidth) {
            xFudge = (maxWidth - preferredWidth)/(nComps - 1);
        }

        if (nComps > 1 && maxHeight > preferredHeight) {
            yFudge = (maxHeight - preferredHeight)/(nComps - 1);
        }

        for (int i = 0 ; i < nComps ; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                Dimension d = c.getPreferredSize();
                
                 // increase x and y, if appropriate
                if (i > 0) { 
                    x += previousWidth/2 + xFudge;
                    y += previousHeight + vgap + yFudge;
                }
                
                // If x is too large, 
                if ((x + d.width) > (parent.getSize().width - insets.right)) {
                    // reduce x to a reasonable number.
                    x = parent.getSize().width 
                        - insets.bottom - d.width;
                }

                // If y is too large, 
                if ((y + d.height) 
                    > (parent.getSize().height - insets.bottom)) {
                    // do nothing.
                    // Another choice would be to do what we do to x.
                }

                // Set the component's size and position.
                c.setBounds(x, y, d.width, d.height);

                previousWidth = d.width;
                previousHeight = d.height;
            }
        }
    }
    
    public String toString() {
        String str = "";
        return getClass().getName() + "[vgap=" + vgap + str + "]";
    }
}