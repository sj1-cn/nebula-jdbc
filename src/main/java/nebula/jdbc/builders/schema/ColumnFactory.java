package nebula.jdbc.builders.schema;

public class ColumnFactory {
	static ColumnBuilder CHAR(String name) {
		return new ColumnBuilder(name, JDBCTypes.CHAR);
	}

	static ColumnBuilder VARCHAR(String name) {
		return new ColumnBuilder(name, JDBCTypes.VARCHAR, 256);
	}

	static ColumnBuilder VARCHAR(String name, int length) {
		return new ColumnBuilder(name, JDBCTypes.VARCHAR, length);
	}

	static ColumnBuilder LONGVARCHAR(String name) {
		return new ColumnBuilder(name, JDBCTypes.LONGVARCHAR);
	}

	static ColumnBuilder NUMERIC(String name) {
		return new ColumnBuilder(name, JDBCTypes.NUMERIC);
	}

	static ColumnBuilder DECIMAL(String name) {
		return new ColumnBuilder(name, JDBCTypes.DECIMAL);
	}

	static ColumnBuilder IDENTITY(String name) {
		return new ColumnBuilder(name, JDBCTypes.IDENTITY);
	}

	static ColumnBuilder BIT(String name) {
		return new ColumnBuilder(name, JDBCTypes.BIT);
	}

	static ColumnBuilder TINYINT(String name) {
		return new ColumnBuilder(name, JDBCTypes.TINYINT);
	}

	static ColumnBuilder SMALLINT(String name) {
		return new ColumnBuilder(name, JDBCTypes.SMALLINT);
	}

	static ColumnBuilder INTEGER(String name) {
		return new ColumnBuilder(name, JDBCTypes.INTEGER);
	}

	static ColumnBuilder BIGINT(String name) {
		return new ColumnBuilder(name, JDBCTypes.BIGINT);
	}

	static ColumnBuilder REAL(String name) {
		return new ColumnBuilder(name, JDBCTypes.REAL);
	}

	static ColumnBuilder FLOAT(String name) {
		return new ColumnBuilder(name, JDBCTypes.FLOAT);
	}

	static ColumnBuilder DOUBLE(String name) {
		return new ColumnBuilder(name, JDBCTypes.DOUBLE);
	}

	static ColumnBuilder BINARY(String name) {
		return new ColumnBuilder(name, JDBCTypes.BINARY);
	}

	static ColumnBuilder VARBINARY(String name) {
		return new ColumnBuilder(name, JDBCTypes.VARBINARY);
	}

	static ColumnBuilder LONGVARBINARY(String name) {
		return new ColumnBuilder(name, JDBCTypes.LONGVARBINARY);
	}

	static ColumnBuilder DATE(String name) {
		return new ColumnBuilder(name, JDBCTypes.DATE);
	}

	static ColumnBuilder TIME(String name) {
		return new ColumnBuilder(name, JDBCTypes.TIME);
	}

	static ColumnBuilder TIMESTAMP(String name) {
		return new ColumnBuilder(name, JDBCTypes.TIMESTAMP);
	}

}
