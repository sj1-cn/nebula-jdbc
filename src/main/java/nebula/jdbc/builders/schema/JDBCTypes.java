package nebula.jdbc.builders.schema;

public enum JDBCTypes {
	CHAR, // String
	VARCHAR, // String
	LONGVARCHAR, // String
	NUMERIC, // java.math.BigDecimal
	DECIMAL, // java.math.BigDecimal
	BIT, // boolean
	TINYINT, // byte
	SMALLINT, // short
	INTEGER, // int
	BIGINT, // long
	REAL, // float
	FLOAT, // double
	DOUBLE, // double
	BINARY, // byte[]
	VARBINARY, // byte[]
	LONGVARBINARY, // byte[]
	DATE, // java.sql.Date
	TIME, // java.sql.Time
	TIME_WITH_TIMEZONE,
	TIMESTAMP, // java.sql.Timestamp
	TIMESTAMP_WITH_TIMEZONE,
	IDENTITY,// java.sql.Timestamp
}
