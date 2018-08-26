package nebula.jdbc.builders.schema;

public class ColumnFactory {
	static public  ColumnDefination CHAR(String name) {
		return new ColumnDefination(name, JDBCTypes.CHAR);
	}

	static public  ColumnDefination VARCHAR(String name) {
		return new ColumnDefination(name, JDBCTypes.VARCHAR, 256);
	}

	static public  ColumnDefination VARCHAR(String name, int length) {
		return new ColumnDefination(name, JDBCTypes.VARCHAR, length);
	}

	static public  ColumnDefination LONGVARCHAR(String name) {
		return new ColumnDefination(name, JDBCTypes.LONGVARCHAR);
	}

	static public  ColumnDefination Column(JDBCTypes type, String name) {
		return new ColumnDefination(name, type);
	}

	static public  ColumnDefination NUMERIC(String name) {
		return new ColumnDefination(name, JDBCTypes.NUMERIC);
	}

	static public  ColumnDefination DECIMAL(String name) {
		return new ColumnDefination(name, JDBCTypes.DECIMAL);
	}

	static public  ColumnDefination IDENTITY(String name) {
		return new ColumnDefination(name, JDBCTypes.IDENTITY);
	}

	static public  ColumnDefination BIT(String name) {
		return new ColumnDefination(name, JDBCTypes.BIT);
	}

	static public  ColumnDefination TINYINT(String name) {
		return new ColumnDefination(name, JDBCTypes.TINYINT);
	}

	static public  ColumnDefination SMALLINT(String name) {
		return new ColumnDefination(name, JDBCTypes.SMALLINT);
	}

	static public  ColumnDefination INTEGER(String name) {
		return new ColumnDefination(name, JDBCTypes.INTEGER);
	}

	static public  ColumnDefination BIGINT(String name) {
		return new ColumnDefination(name, JDBCTypes.BIGINT);
	}

	static public  ColumnDefination REAL(String name) {
		return new ColumnDefination(name, JDBCTypes.REAL);
	}

	static public  ColumnDefination FLOAT(String name) {
		return new ColumnDefination(name, JDBCTypes.FLOAT);
	}

	static public  ColumnDefination DOUBLE(String name) {
		return new ColumnDefination(name, JDBCTypes.DOUBLE);
	}

	static public  ColumnDefination BINARY(String name) {
		return new ColumnDefination(name, JDBCTypes.BINARY);
	}

	static public  ColumnDefination VARBINARY(String name) {
		return new ColumnDefination(name, JDBCTypes.VARBINARY);
	}

	static public  ColumnDefination LONGVARBINARY(String name) {
		return new ColumnDefination(name, JDBCTypes.LONGVARBINARY);
	}

	static public  ColumnDefination DATE(String name) {
		return new ColumnDefination(name, JDBCTypes.DATE);
	}

	static public  ColumnDefination TIME(String name) {
		return new ColumnDefination(name, JDBCTypes.TIME);
	}

	static public  ColumnDefination TIMESTAMP(String name) {
		return new ColumnDefination(name, JDBCTypes.TIMESTAMP);
	}

}
