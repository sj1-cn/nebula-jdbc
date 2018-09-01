package nebula.data.jdbc;

import nebula.tinyasm.data.ListMap;

public class FieldList extends ListMap<String, FieldMapper> {

	public FieldList() {
		super(v -> v.fieldName);
	}

}
