package cn.sj1.nebula.data.jdbc;

import static nebula.jdbc.sql.builders.schema.ColumnDefinition.TIMESTAMP;

public class EntityPojoDbMappingDefinitions {
	final String name;
	final String entityPojoClassName;
	final String jdbcTablename;
	final FieldList entityFields;
	final FieldList systemFields;
	final FieldList fieldsAll;

	public EntityPojoDbMappingDefinitions(String name,String clazz, String tablename, FieldList entityFields) {
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
		defaultSystemFields.push(new EntityPojoFieldJdbcMapper("createAt", "getCreateAt", java.sql.Timestamp.class, TIMESTAMP("createAt")));
		defaultSystemFields.push(new EntityPojoFieldJdbcMapper("updateAt", "getUpdateAt", java.sql.Timestamp.class, TIMESTAMP("updateAt")));
	}
}
