package nebula.data.jdbc;

import nebula.commons.list.ListMap;

public class FieldList extends ListMap<String, FieldMapper> {

	public FieldList() {
		super(v -> v.fieldName);
	}

}
