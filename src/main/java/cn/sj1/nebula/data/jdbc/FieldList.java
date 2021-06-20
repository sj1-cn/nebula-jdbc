package cn.sj1.nebula.data.jdbc;

import nebula.commons.list.ListMap;

public class FieldList extends ListMap<String, PersistentProperty> {

	public FieldList() {
		super(v -> v.fieldName);
	}

}
