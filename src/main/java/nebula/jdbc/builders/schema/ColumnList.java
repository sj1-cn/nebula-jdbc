package nebula.jdbc.builders.schema;

import nebula.tinyasm.data.ArrayListMap;

public class ColumnList extends ArrayListMap<ColumnDefinition> {
	public ColumnList() {
		super(c -> c.columnName);
	}

	public ColumnList copy() {
		ColumnList newobject = new ColumnList();
		for (ColumnDefinition columnDefinition : super.list()) {
			newobject.push(columnDefinition);
		}
		return newobject;
	}

}
