package nebula.jdbc.builders.schema;

import static nebula.jdbc.builders.schema.JDBCTypes.BIGINT;
import static nebula.jdbc.builders.schema.JDBCTypes.BINARY;
import static nebula.jdbc.builders.schema.JDBCTypes.BIT;
import static nebula.jdbc.builders.schema.JDBCTypes.CHAR;
import static nebula.jdbc.builders.schema.JDBCTypes.DATE;
import static nebula.jdbc.builders.schema.JDBCTypes.DECIMAL;
import static nebula.jdbc.builders.schema.JDBCTypes.DOUBLE;
import static nebula.jdbc.builders.schema.JDBCTypes.FLOAT;
import static nebula.jdbc.builders.schema.JDBCTypes.INTEGER;
import static nebula.jdbc.builders.schema.JDBCTypes.LONGVARBINARY;
import static nebula.jdbc.builders.schema.JDBCTypes.LONGVARCHAR;
import static nebula.jdbc.builders.schema.JDBCTypes.NUMERIC;
import static nebula.jdbc.builders.schema.JDBCTypes.REAL;
import static nebula.jdbc.builders.schema.JDBCTypes.SMALLINT;
import static nebula.jdbc.builders.schema.JDBCTypes.TIME;
import static nebula.jdbc.builders.schema.JDBCTypes.TIMESTAMP;
import static nebula.jdbc.builders.schema.JDBCTypes.TIMESTAMP_WITH_TIMEZONE;
import static nebula.jdbc.builders.schema.JDBCTypes.TIME_WITH_TIMEZONE;
import static nebula.jdbc.builders.schema.JDBCTypes.TINYINT;
import static nebula.jdbc.builders.schema.JDBCTypes.VARBINARY;
import static nebula.jdbc.builders.schema.JDBCTypes.VARCHAR;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
	static EnumMap<JDBCTypes, String> mapper = new EnumMap<>(JDBCTypes.class);
	static Map<String, JDBCTypes> mapperRevert = new HashMap<>();
	static {
		mapper.put(CHAR, "String");
		mapper.put(VARCHAR, "String");
		mapper.put(LONGVARCHAR, "String");
		mapper.put(NUMERIC, "java.math.BigDecimal");
		mapper.put(DECIMAL, "java.math.BigDecimal");
		mapper.put(BIT, "boolean");
		mapper.put(TINYINT, "byte");
		mapper.put(SMALLINT, "short");
		mapper.put(INTEGER, "int");
		mapper.put(BIGINT, "long");
		mapper.put(REAL, "float");
		mapper.put(FLOAT, "double");
		mapper.put(DOUBLE, "double");
		mapper.put(BINARY, "bytes");
		mapper.put(VARBINARY, "bytes");
		mapper.put(LONGVARBINARY, "bytes");
		mapper.put(DATE, "java.sql.Date");
		mapper.put(TIME, "java.sql.Time");
		mapper.put(TIME_WITH_TIMEZONE, "java.sql.Time");
		mapper.put(TIMESTAMP, "java.sql.Timestamp");
		mapper.put(TIMESTAMP_WITH_TIMEZONE, "java.sql.Timestamp");

		mapperRevert.put("String", VARCHAR);
		mapperRevert.put("java.math.BigDecimal", NUMERIC);
		mapperRevert.put("boolean", BIT);
		mapperRevert.put("byte", TINYINT);
		mapperRevert.put("short", SMALLINT);
		mapperRevert.put("int", INTEGER);
		mapperRevert.put("long", BIGINT);
		mapperRevert.put("float", REAL);
		mapperRevert.put("double", DOUBLE);
		mapperRevert.put("bytes", BINARY);
		mapperRevert.put("java.sql.Date", DATE);
		mapperRevert.put("java.sql.Time", TIME);
		mapperRevert.put("java.sql.Timestamp", TIMESTAMP);
	}

	static EnumMap<JDBCTypes, String> columnDefinations = new EnumMap<>(JDBCTypes.class);
	static {
		columnDefinations.put(CHAR, "CHAR");// precisionInt
		columnDefinations.put(VARCHAR, "VARCHAR");
		columnDefinations.put(LONGVARCHAR, "LONGVARCHAR");// precisionInt
		columnDefinations.put(NUMERIC, "NUMERIC");// precisionInt , scaleInt
		columnDefinations.put(DECIMAL, "DECIMAL");// precisionInt , scaleInt
		columnDefinations.put(BIT, "BIT");
		columnDefinations.put(TINYINT, "TINYINT");
		columnDefinations.put(SMALLINT, "SMALLINT");
		columnDefinations.put(INTEGER, "INT");
		columnDefinations.put(BIGINT, "BIGINT");
		columnDefinations.put(REAL, "REAL");
		columnDefinations.put(FLOAT, "FLOAT");// precisionInt
		columnDefinations.put(DOUBLE, "DOUBLE");// precisionInt
		columnDefinations.put(BINARY, "BINARY");
		columnDefinations.put(VARBINARY, "VARBINARY");
		columnDefinations.put(LONGVARBINARY, "LONGVARBINARY");
		columnDefinations.put(DATE, "DATE");
		columnDefinations.put(TIME, "TIME");
		columnDefinations.put(TIME_WITH_TIMEZONE, "TIME");
		columnDefinations.put(TIMESTAMP, "TIMESTAMP");
		columnDefinations.put(TIMESTAMP_WITH_TIMEZONE, "TIME");
	}

	static class JavaType {
		String typename;
		String getname;
		String setname;
		Class<?> getclazz;

		public JavaType(String typename, String getname, String setname, Class<?> getclazz) {
			super();
			this.typename = typename;
			this.getname = getname;
			this.setname = setname;
			this.getclazz = getclazz;
		}
	}

	static Map<String, JavaType> javaJdbcTypes = new HashMap<>();

	static {
		// @formatter:off
		javaJdbcTypes.put("String", new JavaType("String", "getString","setString", String.class));
		javaJdbcTypes.put("boolean", new JavaType("boolean", "getBoolean", "setBoolean", boolean.class));
		javaJdbcTypes.put("byte", new JavaType("byte", "getByte", "setByte", byte.class));
		javaJdbcTypes.put("short", new JavaType("short", "getShort",  "setShort", short.class));
		javaJdbcTypes.put("int", new JavaType("int", "getInt", "setInt",int.class));
		javaJdbcTypes.put("long", new JavaType("long", "getLong","setLong", long.class));
		javaJdbcTypes.put("float", new JavaType("float", "getFloat", "setFloat",float.class));
		javaJdbcTypes.put("double", new JavaType("double", "getDouble", "setDouble",  double.class));
		javaJdbcTypes.put("bytes", new JavaType("bytes", "getBytes", "setBytes",  byte[].class));// byte[]
		javaJdbcTypes.put("java.sql.Date", new JavaType("java.sql.Date", "getDate","setDate", java.sql.Date.class));// java.sql.Date
		javaJdbcTypes.put("java.sql.Time", new JavaType("Time", "getTime", "setTime", java.sql.Time.class));// java.sql.Time
		javaJdbcTypes.put("java.sql.Timestamp", new JavaType("Timestamp", "getTimestamp","setTimestamp", java.sql.Timestamp.class));// java.sql.Timestamp
		javaJdbcTypes.put("java.math.BigDecimal", new JavaType("java.math.BigDecimal", "getBigDecimal", "setBigDecimal",BigDecimal.class));// java.math.BigDecimal
		// @formatter:on
	}

}
