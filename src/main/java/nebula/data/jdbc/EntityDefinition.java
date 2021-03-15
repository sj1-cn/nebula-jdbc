package nebula.data.jdbc;

import static nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;

public class EntityDefinition {
	final String name;
	final String clazz;
	final String tablename;
	final FieldList fields;
	final FieldList fieldsExtend;
	final FieldList fieldsAll;

	public EntityDefinition(String name,String clazz, String tablename, FieldList fields) {
		super();
		this.name = name;
		this.clazz = clazz;
		this.tablename = tablename.toUpperCase();
		this.fields = fields;
		this.fieldsExtend = defaultFieldsExtend;
		this.fieldsAll = new FieldList();
		this.fieldsAll.push(this.fields.list());
		this.fieldsAll.push(this.fieldsExtend.list());
	}

	public String getName() {
		return name;
	}

	public String getTablename() {
		return tablename;
	}

	public String getClazz() {
		return clazz;
	}

	public FieldList getFields() {
		return fields;
	}

	public FieldList getFieldsExtend() {
		return fieldsExtend;
	}

	public FieldList getFieldsAll() {
		return fieldsAll;
	}

	static FieldList defaultFieldsExtend = new FieldList();
	static {
		defaultFieldsExtend.push(new FieldMapper("createAt", "getCreateAt", java.sql.Timestamp.class, TIMESTAMP("createAt")));
		defaultFieldsExtend.push(new FieldMapper("updateAt", "getUpdateAt", java.sql.Timestamp.class, TIMESTAMP("updateAt")));
	}
}
