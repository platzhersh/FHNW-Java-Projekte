package jdraw.figures;

import jdraw.figures.handles.Handle;
import jdraw.figures.handles.states.HandleStateE;
import jdraw.figures.handles.states.HandleStateN;
import jdraw.figures.handles.states.HandleStateNE;
import jdraw.figures.handles.states.HandleStateNW;
import jdraw.figures.handles.states.HandleStateS;
import jdraw.figures.handles.states.HandleStateSE;
import jdraw.figures.handles.states.HandleStateSW;
import jdraw.figures.handles.states.HandleStateW;

public abstract class AbstractRectangularDrawTool extends AbstractDrawTool {
	
	@Override
	public void createHandles() {
		fig.handles.addHandler(new Handle(new HandleStateNW(),fig));
		fig.handles.addHandler(new Handle(new HandleStateN(),fig));
		fig.handles.addHandler(new Handle(new HandleStateNE(),fig));
		fig.handles.addHandler(new Handle(new HandleStateW(),fig));
		fig.handles.addHandler(new Handle(new HandleStateE(),fig));
		fig.handles.addHandler(new Handle(new HandleStateSW(),fig));
		fig.handles.addHandler(new Handle(new HandleStateS(),fig));
		fig.handles.addHandler(new Handle(new HandleStateSE(),fig));
	}

}
