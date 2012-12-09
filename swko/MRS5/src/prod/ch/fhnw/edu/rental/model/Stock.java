package ch.fhnw.edu.rental.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Manages the stock of videos of the rental shop.
 * @author Christoph Denzler
 *
 */
public class Stock {
  
  /** The stock of videos. */
  private HashMap<String, Integer> stock = new HashMap<String, Integer>();
  
  /** low stock listeners. */
  private List<LowStockListener> listeners = new LinkedList<LowStockListener>();
  
  /**
   * add a movie to the stock.
   * @param movie the movie to add to the stock.
   * @return the number of items of this movie in stock after this operation.
   */
  public int addToStock(IMovie movie) {
    Integer i = stock.get(movie.getTitle());
    int inStock = (i == null) ? 0 : i; 
    stock.put(movie.getTitle(), ++inStock);
    return inStock;
  }
  
  /**
   * removes a movie from the stock.
   * @param movie the movie to remove from the stock.
   * @return the number of items of this movie in stock after this operation.
   */
  public int removeFromStock(IMovie movie) {
    Integer i = stock.get(movie.getTitle());
    int inStock = (i == null) ? 0 : i;
    if (inStock <= 0) { throw new MovieRentalException("no video in stock"); }
    stock.put(movie.getTitle(), --inStock);
    notifyListeners(movie, inStock);
    return inStock;
  }
  
  /**
   * Notify all LowStockListeners with a threshold of c or below that movie m is low in stock.
   * @param m movie to notify
   * @param c threshold for notification
   */
  private void notifyListeners(IMovie m, int c) {
    for (LowStockListener l : listeners) {
      if (l.getThreshold() >= c) {
        l.stockLow(m, c);
      }
    }
  }

  /**
   * @param title the movie title to get the stock count.
   * @return the number copies of the movie still in stock.
   */
  public int getInStock(String title) {
    Integer i = stock.get(title);
    return (i == null) ? 0 : i;
  }
  
  /**
   * Add a stock listener.
   * @param l listener
   */
  public void addLowStockListener(LowStockListener l) {
    if (l != null && !listeners.contains(l)) {
      listeners.add(l);
    }
  }
  
  /**
   * Remove a stock listener.
   * @param l listener
   */
  public void removeLowStockListener(LowStockListener l) {
    listeners.remove(l);
  }
  
}
