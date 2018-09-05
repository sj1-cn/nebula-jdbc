package nebula.data.jdbc;

import static java.sql.JDBCType.BIGINT;
import static java.sql.JDBCType.BIT;
import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.CHAR;
import static java.sql.JDBCType.DOUBLE;
import static java.sql.JDBCType.FLOAT;
import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.NUMERIC;
import static java.sql.JDBCType.TIMESTAMP;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;

import java.sql.JDBCType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class ClazzExtendBuilderTest extends TestBase {

	ClazzExtendBuilder builder;

	@Before
	public void before() {
		builder = new ClazzExtendBuilder();
	}

	@After
	public void after() {
	}

//	public void testPrint() throws IOException {
//		System.out.println(RefineCode.refineCode(toString(UserJdbcRowMapper.class), ResultSet.class,
//				PreparedStatement.class, JdbcRepository.class));
//	}

	@Test
	public void testUserImpl() {

		FieldList maps = new FieldList();
		String clazzImpl = UserExtend.class.getName();

		maps.push(new FieldMapper("id", "getId", long.class, new ColumnDefinition("ID", INTEGER)));
		maps.push(new FieldMapper("name", "getName", String.class, new ColumnDefinition("NAME", VARCHAR)));
		maps.push(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

		String targetClazz = User.class.getName();
		byte[] code = builder.make(clazzImpl, targetClazz, maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazzImpl);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserMoreComplexJdbcRowMapper() {
		FieldList maps = new FieldList();
		maps.push(new FieldMapper("id", "getId", Long.class,
				new ColumnDefinition("id", BIGINT).primarykey().autoIncrement()));
		maps.push(new FieldMapper("string", "getString", String.class, new ColumnDefinition("string", VARCHAR)));
		maps.push(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class,
				new ColumnDefinition("bigDecimal", NUMERIC)));
		maps.push(new FieldMapper("z", "getZ", Boolean.class, new ColumnDefinition("z", BOOLEAN)));
		maps.push(new FieldMapper("c", "getC", Character.class, new ColumnDefinition("c", CHAR)));
		maps.push(new FieldMapper("b", "getB", Byte.class, new ColumnDefinition("b", BIT)));
		maps.push(new FieldMapper("s", "getS", Short.class, new ColumnDefinition("s", JDBCType.SMALLINT)));
		maps.push(new FieldMapper("i", "getI", Integer.class, new ColumnDefinition("i", INTEGER)));
		maps.push(new FieldMapper("l", "getL", Long.class, new ColumnDefinition("l", BIGINT)));
		maps.push(new FieldMapper("f", "getF", Float.class, new ColumnDefinition("f", FLOAT)));
		maps.push(new FieldMapper("d", "getD", Double.class, new ColumnDefinition("d", DOUBLE)));
		maps.push(new FieldMapper("date", "getDate", java.sql.Date.class, new ColumnDefinition("date", JDBCType.DATE)));
		maps.push(new FieldMapper("time", "getTime", java.sql.Time.class, new ColumnDefinition("time", JDBCType.TIME)));
		maps.push(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class,
				new ColumnDefinition("timestamp", TIMESTAMP)));

		String targetClazz = UserMoreComplex.class.getName();
		byte[] code = builder.make(UserMoreComplexExtend.class.getName(), targetClazz, maps);

		String codeActual = toString(code);
		String codeExpected = toString(UserMoreComplexExtend.class);
		assertEquals("Code", codeExpected, codeActual);
	}
}
