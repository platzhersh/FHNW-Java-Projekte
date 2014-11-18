package patterns.decorator.demo.table;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelEvent;

public class TableBubbleSortDecorator extends TableSortDecorator {
	// The lone constructor must be passed a reference to a
	// TableModel. This class decorates that model with additional
	// sorting functionality.
	public TableBubbleSortDecorator(TableModel model) {
		super(model);
		allocate();
	}

	// tableChanged is defined in TableModelListener, which
	// is implemented by TableSortDecorator.
	public void tableChanged(TableModelEvent e) {
		System.out.println("table changed => allocate");
		allocate();
	}

	// Two TableModel methods are overridden from
	// TableModelDecorator ...
	@Override
	public Object getValueAt(int row, int column) {
		return getRealModel().getValueAt(indexes[row], column);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		getRealModel().setValueAt(aValue, indexes[row], column);
	}

	// The following methods perform the bubble sort ...

	@Override
	public void sort(int column) {
		int rowCount = getRowCount();

		for (int i = 0; i < rowCount; i++) {
			for (int j = i + 1; j < rowCount; j++) {
				if (compare(indexes[i], indexes[j], column) < 0) {
					swap(i, j);
				}
			}
		}
	}
	
	private void swap(int i, int j) {
		int tmp = indexes[i];
		indexes[i] = indexes[j];
		indexes[j] = tmp;
	}

	private int compare(int i, int j, int column) {
		TableModel realModel = getRealModel();
		Object io = realModel.getValueAt(i, column);
		Object jo = realModel.getValueAt(j, column);

		int c = jo.toString().compareTo(io.toString());
		return (c < 0) ? -1 : ((c > 0) ? 1 : 0);
	}

	private void allocate() {
		indexes = new int[getRowCount()];
		for (int i = 0; i < indexes.length; ++i) {
			indexes[i] = i;
		}
	}

	private int indexes[];
}
