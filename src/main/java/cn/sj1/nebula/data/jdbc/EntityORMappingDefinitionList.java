package cn.sj1.nebula.data.jdbc;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;

class EntityORMappingDefinitionList {
	final String name;
	final String entityPojoClassName;
	final String jdbcTablename;
	final FieldList entityFields;
	final FieldList systemFields;
	final FieldList fieldsAll;

	public EntityORMappingDefinitionList(String name,String clazz, String tablename, FieldList entityFields) {
		super();
		this.name = name;
		this.entityPojoClassName = clazz;
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
		return entityPojoClassName;
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
		defaultSystemFields.push(new EntityORMappingDefinition("createAt", "getCreateAt", java.sql.Timestamp.class, TIMESTAMP("createAt")));
		defaultSystemFields.push(new EntityORMappingDefinition("updateAt", "getUpdateAt", java.sql.Timestamp.class, TIMESTAMP("updateAt")));
	}
}
