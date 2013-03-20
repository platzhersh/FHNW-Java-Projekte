package carpark;
public class CarPark5 implements CarPark {
   private int places;
   public CarPark5(int places){ this.places = places; }
   private Object lock = new Object(); 
   public void enter() {
      synchronized(lock) {
         while( places == 0) {
            try { lock.wait(); } 
            catch (InterruptedException e) { }
         }
         places--;
      }
   }
   public void exit() {
      synchronized(lock) {
         places++; lock.notify();
      }
   }
}
