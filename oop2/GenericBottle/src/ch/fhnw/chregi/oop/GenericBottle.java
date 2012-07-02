package ch.fhnw.chregi.oop;

import ch.fhnw.chregi.oop.*;


public class GenericBottle<T> {
	
	T bottleContent;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GenericBottle<RedWine> bottle = new GenericBottle<RedWine>();
		bottle.fill(new RedWine());
		
		Wine w = new Wine();
		Drink d = w;
	}
	
	// Constructor
	public GenericBottle() {
		
	}
	
	// Getter
	
	// Setter
	public void fill(T o){
		this.bottleContent = o;
		
	}
	
	public void empty(){
		this.bottleContent = null;
		
	}
	

}
