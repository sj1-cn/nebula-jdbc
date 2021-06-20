package cn.sj1.nebula.data.jdbc;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;

public class PersistentEntity {
	final String name;
	final String javaClass;
	final String jdbcTablename;
	final FieldList entityFields;
	final FieldList systemFields;
	final FieldList fieldsAll;

	public PersistentEntity(String name,String clazz, String tablename, FieldList entityFields) {
		super();
		this.name = name;
		this.javaClass = clazz;
		this.jdbcTablename = tablename.toUpperCase();
		this.entityFields = entityFields;
		this.systemFields = defaultSystemFields;
		this.fieldsAll = new FieldList();
		this.fieldsAll.push(this.entityFields.list());
		this.fieldsAll.push(this.systemFields.list());
	}

	public String getName() {
		return name;
	}

	public String getJdbcTablename() {
		return jdbcTablename;
	}

	public String getEntityPojoClassName() {
		return javaClass;
	}

	public FieldList getFields() {
		return entityFields;
	}

	public FieldList getSystemFields() {
		return systemFields;
	}

	public FieldList getFieldsAll() {
		return fieldsAll;
	}

	static FieldList defaultSystemFields = new FieldList();
	static {
		defaultSystemFields.push(new PersistentProperty("createAt", "getCreateAt", java.sql.Timestamp.class, TIMESTAMP("createAt")));
		defaultSystemFields.push(new PersistentProperty("updateAt", "getUpdateAt", java.sql.Timestamp.class, TIMESTAMP("updateAt")));
	}
}
