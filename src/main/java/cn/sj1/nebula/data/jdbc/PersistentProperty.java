package cn.sj1.nebula.data.jdbc;

import cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition;

public class PersistentProperty {
	final boolean primaryKey;
	final String fieldName;
	final String fieldGetName;
	final Class<?> clazz;
	final ColumnDefinition column;

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldGetName() {
		return fieldGetName;
	}

	public Class<?> getPojoClazz() {
		return clazz;
	}

	public ColumnDefinition getColumn() {
		return column;
	}

	public PersistentProperty(boolean primaryKey, String javaname, String fieldGetName, Class<?> fieldClazz,
			ColumnDefinition column) {
		super();
		this.primaryKey = primaryKey;
		this.fieldName = javaname;
		this.clazz = fieldClazz;
		this.fieldGetName = fieldGetName;
		this.column = column;
		if(primaryKey) this.column.primarykey();
	}

	public PersistentProperty(String javaname, String getname, Class<?> fieldClazz, ColumnDefinition column) {
		this(false, javaname, getname, fieldClazz, column);
	}

}