package cn.sj1.nebula.data.jdbc;

import nebula.commons.list.ListMap;

class FieldList extends ListMap<String, EntityORMappingDefinition> {

	public FieldList() {
		super(v -> v.fieldName);
	}

}
