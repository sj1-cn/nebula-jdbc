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

public class JDBCConfiguration {
	public static EnumMap<JDBCTypes, String> mapper = new EnumMap<>(JDBCTypes.class);
	public static Map<String, JDBCTypes> mapperRevert = new HashMap<>();
	static {
		mapper.put(CHAR, "java.lang.String");
		mapper.put(VARCHAR, "java.lang.String");
		mapper.put(LONGVARCHAR, "java.lang.String");
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

		mapperRevert.put("java.lang.String", VARCHAR);
		mapperRevert.put("java.math.BigDecimal", NUMERIC);
		mapperRevert.put("char", VARCHAR);
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

		mapperRevert.put("java.lang.Boolean", BIT);
		mapperRevert.put("java.lang.Character", VARCHAR);
		mapperRevert.put("java.lang.Byte", TINYINT);
		mapperRevert.put("java.lang.Short", SMALLINT);
		mapperRevert.put("java.lang.Integer", INTEGER);
		mapperRevert.put("java.lang.Long", BIGINT);
		mapperRevert.put("java.lang.Float", REAL);
		mapperRevert.put("java.lang.Double", DOUBLE);
		mapperRevert.put("java.lang.Date", DATE);
	}

	public static EnumMap<JDBCTypes, String> columnDefinations = new EnumMap<>(JDBCTypes.class);
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

	public static class JDBCType {
		public String typename;
		public String getname;
		public String setname;
		public Class<?> jdbcClazz;

		public JDBCType(String typename, String getname, String setname, Class<?> getclazz) {
			super();
			this.typename = typename;
			this.getname = getname;
			this.setname = setname;
			this.jdbcClazz = getclazz;
		}
	}

	public static Map<String, JDBCType> javaJdbcTypes = new HashMap<>();

	static void rigester(Class<?> clazz, JDBCType jdbctype) {
		javaJdbcTypes.put(clazz.getName(), jdbctype);
	}

	static void rigester(String clazz, JDBCType jdbctype) {
		javaJdbcTypes.put(clazz, jdbctype);
	}

	static {
		// @formatter:off
		rigester(String.class, new JDBCType("String", "getString","setString", String.class));
		rigester(boolean.class, new JDBCType("boolean", "getBoolean", "setBoolean", boolean.class));
		rigester(char.class, new JDBCType("String", "getString","setString", String.class));
		rigester(byte.class, new JDBCType("byte", "getByte", "setByte", byte.class));
		rigester(short.class, new JDBCType("short", "getShort",  "setShort", short.class));
		rigester(int.class, new JDBCType("int", "getInt", "setInt",int.class));
		rigester(long.class, new JDBCType("long", "getLong","setLong", long.class));
		rigester(float.class, new JDBCType("float", "getFloat", "setFloat",float.class));
		rigester(double.class, new JDBCType("double", "getDouble", "setDouble",  double.class));
		rigester("bytes", new JDBCType("bytes", "getBytes", "setBytes",  byte[].class));// byte[]
		rigester(java.sql.Date.class, new JDBCType("java.sql.Date", "getDate","setDate", java.sql.Date.class));// java.sql.Date
		rigester(java.sql.Time.class, new JDBCType("Time", "getTime", "setTime", java.sql.Time.class));// java.sql.Time
		rigester(java.sql.Timestamp.class, new JDBCType("Timestamp", "getTimestamp","setTimestamp", java.sql.Timestamp.class));// java.sql.Timestamp
		rigester(java.math.BigDecimal.class, new JDBCType("java.math.BigDecimal", "getBigDecimal", "setBigDecimal",BigDecimal.class));// java.math.BigDecimal

		rigester(String.class, new JDBCType("String", "getString","setString", String.class));
		rigester(Character.class, new JDBCType("String", "getString","setString", String.class));
		rigester(Boolean.class, new JDBCType("boolean", "getBoolean", "setBoolean", boolean.class));
		rigester(Byte.class, new JDBCType("byte", "getByte", "setByte", byte.class));
		rigester(Short.class, new JDBCType("short", "getShort",  "setShort", short.class));
		rigester(Integer.class, new JDBCType("int", "getInt", "setInt",int.class));
		rigester(Long.class, new JDBCType("long", "getLong","setLong", long.class));
		rigester(Float.class, new JDBCType("float", "getFloat", "setFloat",float.class));
		rigester(Double.class, new JDBCType("double", "getDouble", "setDouble",  double.class));
		// @formatter:on
	}

}
