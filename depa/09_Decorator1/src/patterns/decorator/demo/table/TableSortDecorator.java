package patterns.decorator.demo.table;

import javax.swing.table.TableModel;

public abstract class TableSortDecorator extends TableModelDecorator {
	// Extensions of TableSortDecorator must implement the
	// abstract sort method, in addition to tableChanged. The
	// latter is required because TableModelDecorator
	// implements the TableModelListener interface.
	abstract public void sort(int column);

	public TableSortDecorator(TableModel realModel) {
		super(realModel);
	}
}
