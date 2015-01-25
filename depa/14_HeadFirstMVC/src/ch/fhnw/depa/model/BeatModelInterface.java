package ch.fhnw.depa.model;

import ch.fhnw.depa.view.BPMObserver;
import ch.fhnw.depa.view.BeatObserver;

public interface BeatModelInterface {

	void initialize();
	
	public void on();
	public void off();
	
	public void setBPM(int bpm);
	public int getBPM();
	
	void registerObserver(BeatObserver o);
	void removeObserver(BeatObserver o);
	void registerObserver(BPMObserver o);
	void removeObserver(BPMObserver o);
	
}
