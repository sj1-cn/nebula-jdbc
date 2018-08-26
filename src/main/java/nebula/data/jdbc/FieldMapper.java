package nebula.data.jdbc;

import nebula.jdbc.builders.schema.ColumnDefination;

class FieldMapper {
	final boolean primaryKey;
	final String fieldName;
	final String getname;
	final Class<?> pojoClazz;
	final ColumnDefination column;

	public FieldMapper(boolean primaryKey, String javaname, String getname, Class<?> fieldClazz,
			ColumnDefination column) {
		super();
		this.primaryKey = primaryKey;
		this.fieldName = javaname;
		this.pojoClazz = fieldClazz;
		this.getname = getname;
		this.column = column;
		if(primaryKey) this.column.primarykey();
	}

	public FieldMapper(String javaname, String getname, Class<?> fieldClazz, ColumnDefination column) {
		this(false, javaname, getname, fieldClazz, column);
	}

}