package patterns.clone.immutable;

import java.awt.Rectangle;

public class RectangleFigure extends Figure {
   private Rectangle bounds;
   
   @Override
   public Rectangle getBounds() { return bounds; }
   
   public RectangleFigure(Rectangle bounds) { this.bounds = bounds; }

   public boolean contains(int x, int y) { 
      return bounds.contains(x, y); 
   }

   public void move(int dx, int dy) { // controlled state change
      bounds.translate(dx, dy);
      notifyChange(new FigureEvent(this));
   }

}
