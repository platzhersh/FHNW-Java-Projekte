package ch.fhnw.depa.model;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import ch.fhnw.depa.view.BPMObserver;
import ch.fhnw.depa.view.BeatObserver;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	
	
	ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	int bpm = 90;

	@Override
	public void initialize() {
		setUpMidi();
		buildTrackAndStart();
	}

	private void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildTrackAndStart() {
		int[] trackList = { 35, 0, 46, 0 };
		sequence.deleteTrack(null);
		track = sequence.createTrack();
		makeTracks(trackList);
		track.add(makeEvent(192,9,1,0,4));
		try {
			sequencer.setSequence(sequence);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeTracks(int[] trackList) {
		for (int i = 0; i < trackList.length; i++) {
			int key = trackList[i];
			
			if (key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i+1));
			}
		}
	}
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public void on() {
		sequencer.start();
		setBPM(90);
	}

	@Override
	public void off() {
		setBPM(0);
		sequencer.stop();
	}

	@Override
	public void setBPM(int bpm) {
		this.bpm = bpm;
	}

	@Override
	public int getBPM() {
		return bpm;
	}
	
	void beatEvent() {
		notifyBeatObservers();
	}
	
	void notifyBeatObservers() {
		//ArrayList<BeatObserver> observers = (ArrayList<BeatObserver>) beatObservers.clone();
		Iterator<BeatObserver> it = beatObservers.iterator();
		
		while (it.hasNext()) {
			it.next().updateBeat();
		}
	}

	void notifyBPMObservers() {
		//ArrayList<BPMObserver> observers = (ArrayList<BPMObserver>) bpmObservers.clone();
		Iterator<BPMObserver> it = bpmObservers.iterator();
		
		while (it.hasNext()) {
			it.next().updateBPM();
		}
	}
	
	@Override
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	@Override
	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0)	
			beatObservers.remove(o);
	}

	@Override
	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	@Override
	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0)	
			bpmObservers.remove(o);
	}

	@Override
	public void meta(MetaMessage meta) {
		if (meta.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
	}
	


}
