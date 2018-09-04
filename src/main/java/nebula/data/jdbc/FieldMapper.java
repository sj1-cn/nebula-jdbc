package nebula.data.jdbc;

import nebula.jdbc.builders.schema.ColumnDefinition;

public class FieldMapper {
	final boolean primaryKey;
	final String fieldName;
	final String getName;
	final Class<?> clazz;
	final ColumnDefinition column;

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getGetname() {
		return getName;
	}

	public Class<?> getPojoClazz() {
		return clazz;
	}

	public ColumnDefinition getColumn() {
		return column;
	}

	public FieldMapper(boolean primaryKey, String javaname, String getname, Class<?> fieldClazz,
			ColumnDefinition column) {
		super();
		this.primaryKey = primaryKey;
		this.fieldName = javaname;
		this.clazz = fieldClazz;
		this.getName = getname;
		this.column = column;
		if(primaryKey) this.column.primarykey();
	}

	public FieldMapper(String javaname, String getname, Class<?> fieldClazz, ColumnDefinition column) {
		this(false, javaname, getname, fieldClazz, column);
	}

}