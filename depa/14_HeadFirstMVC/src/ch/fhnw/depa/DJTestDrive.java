package ch.fhnw.depa;

import ch.fhnw.depa.controller.BeatController;
import ch.fhnw.depa.controller.ControllerInterface;
import ch.fhnw.depa.model.BeatModel;
import ch.fhnw.depa.model.BeatModelInterface;

public class DJTestDrive {

	public static void main(String[] args) {
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
		//ControllerInterface controller2 = new BPMController(model);
	}

}
