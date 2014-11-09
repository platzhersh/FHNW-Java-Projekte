package patterns.clone.airplane;
class Airplane implements Cloneable {

	final class Engine implements Cloneable {
		private boolean onFire = false;
		
		public void catchFire() {
			if(!onFire){ onFire = true; faultCounter++;}
		}
		public void drawFire() {onFire = false;}
		
		public Engine() {
			
		}
		public Engine(Engine e) {
			this.onFire = e.onFire;
		}
		
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				throw new InternalError();
			}

		}
	}

	private Engine[] engines = new Engine[4];
	private int faultCounter = 0;
	
	public Airplane(){
		for (int i=0; i<engines.length; i++){
			engines[i] = new Engine();
		}
	}
	
	public boolean isWarningLightOn(){return faultCounter > 0;}
	public void catchFire(int index){engines[index].catchFire();}
	public void drawFire(){for(Engine e : engines) e.drawFire(); faultCounter = 0; }

	public Object clone(){
		try {
			Airplane clone = (Airplane)super.clone();
			clone.engines = clone.cloneInner();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	
	public Engine[] cloneInner() {
		Engine[] e = new Engine[4];
		for(int i = 0; i < engines.length; i++) {
			e[i] = new Engine(engines[i]);
		}
		return e;
	}

}
