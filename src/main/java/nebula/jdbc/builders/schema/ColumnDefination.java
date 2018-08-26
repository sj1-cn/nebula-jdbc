package nebula.jdbc.builders.schema;

import java.util.ArrayList;
import java.util.List;

public class ColumnDefination implements Column {
	private final String name;
	private final JDBCTypes datatype;

	private int size = -1;
	private int digits = -1;
	private boolean unsigned = false;
	private boolean required = false;
	private String defaultValue;
	private boolean autoIncrements = false;
	private boolean primarykey = false;

	public ColumnDefination(String name, JDBCTypes datatype) {
		this.name = name;
		this.datatype = datatype;
	}

	public ColumnDefination(String name, JDBCTypes datatype, int length) {
		this.name = name;
		this.datatype = datatype;
		this.size = length;
	}

	public ColumnDefination size(int size) {
		this.size = size;
		return this;
	}

	public ColumnDefination digits(int digits) {
		this.digits = digits;
		return this;
	}

	public ColumnDefination required() {
		this.required = true;
		return this;
	}

	public ColumnDefination primarykey() {
		this.primarykey = true;
		return this;
	}

	public ColumnDefination required(boolean required) {
		this.required = required;
		return this;
	}

	public ColumnDefination defaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	ColumnDefination autoIncrement() {
		autoIncrements = true;
		return this;
	}

	@Override
	public String toString() {
		return this.toSQL();
	}

//
//	    @Override
	public String toSQL() {
		List<String> sql = new ArrayList<>();
		sql.add(this.name);
		sql.add(JDBCConfiguration.columnDefinations.get(datatype) + size(size, digits));
		if (this.primarykey) sql.add("PRIMARY KEY");
		if (this.unsigned) sql.add("UNSIGNED");
		if (this.required) sql.add("NOT NULL");
		if (this.defaultValue != null) sql.add("DEFAULT '" + this.defaultValue + "'");
		if (this.autoIncrements) sql.add("AUTO_INCREMENT");

		return String.join(" ", sql);
	}

	private String size(int precision, int scale) {
		if (precision > 0 && scale > 0) {
			return "(" + precision + "," + scale + ")";
		} else if (precision > 0) {
			return "(" + precision + ")";
		} else {
			return "";
		}
	}

	@Override
	public String name() {
		return this.name;
	}

	public ColumnDefination unsigned() {
		this.unsigned = true;
		return this;
	}
}