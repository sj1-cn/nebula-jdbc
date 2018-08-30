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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class JdbcRowMapperBuilderTest extends TestBase {

	JdbcRowMapperBuilder builder;
	Arguments arguments = new Arguments();

	@Before
	public void before() {
		builder = new JdbcRowMapperBuilder(arguments);
	}

	@After
	public void after() {
	}

//	public void testPrint() throws IOException {
//		System.out.println(RefineCode.refineCode(toString(UserJdbcRowMapper.class), ResultSet.class,
//				PreparedStatement.class, JdbcRepository.class));
//	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMockRunning() throws SQLException, InstantiationException, IllegalAccessException{

		List<FieldMapper> maps = new ArrayList<FieldMapper>();
		String clazzRowMapper = UserJdbcRowMapper.class.getName();
		maps.add(new FieldMapper("id", "getId", long.class, new ColumnDefinition("ID", INTEGER)));
		maps.add(new FieldMapper("name", "getName", String.class, new ColumnDefinition("NAME", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.getLong("id")).thenReturn(1047L);
		when(resultSet.getString("name")).thenReturn("TestName1047");
		when(resultSet.getString("description")).thenReturn("TestDescription1047");

		String targetClazz = User.class.getName();
		byte[] code = builder.make(clazzRowMapper, targetClazz, maps);
		Class<JdbcRowMapper<User>> row = (Class<JdbcRowMapper<User>>) classLoader.defineClassByName(clazzRowMapper,
				code);
		JdbcRowMapper<User> mapper = row.newInstance();
		User user = mapper.map(resultSet);

		assertEquals(1047L, user.getId());
		assertEquals("TestName1047", user.getName());
		assertEquals("TestDescription1047", user.getDescription());
	}

	@Test
	public void testUserJdbcRowMapper() {

		List<FieldMapper> maps = new ArrayList<FieldMapper>();
		String clazzRowMapper = UserJdbcRowMapper.class.getName();

		maps.add(new FieldMapper("id", "getId", long.class, new ColumnDefinition("ID", INTEGER)));
		maps.add(new FieldMapper("name", "getName", String.class, new ColumnDefinition("NAME", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

		String targetClazz = User.class.getName();
		byte[] code = builder.make(clazzRowMapper, targetClazz, maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazzRowMapper);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void UserMoreComplexJdbcRowMapper() {
		List<FieldMapper> maps = new ArrayList<FieldMapper>();
		maps.add(new FieldMapper("id", "getId", Long.class,	new ColumnDefinition("id", BIGINT).primarykey().autoIncrement()));
		maps.add(new FieldMapper("string", "getString", String.class, new ColumnDefinition("string", VARCHAR)));
		maps.add(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class,
				new ColumnDefinition("bigDecimal", NUMERIC)));
		maps.add(new FieldMapper("z", "getZ", Boolean.class, new ColumnDefinition("z", BOOLEAN)));
		maps.add(new FieldMapper("c", "getC", Character.class, new ColumnDefinition("c", CHAR)));
		maps.add(new FieldMapper("b", "getB", Byte.class, new ColumnDefinition("b", BIT)));
		maps.add(new FieldMapper("s", "getS", Short.class, new ColumnDefinition("s", JDBCType.SMALLINT)));
		maps.add(new FieldMapper("i", "getI", Integer.class, new ColumnDefinition("i", INTEGER)));
		maps.add(new FieldMapper("l", "getL", Long.class, new ColumnDefinition("l", BIGINT)));
		maps.add(new FieldMapper("f", "getF", Float.class, new ColumnDefinition("f", FLOAT)));
		maps.add(new FieldMapper("d", "getD", Double.class, new ColumnDefinition("d", DOUBLE)));
		maps.add(new FieldMapper("date", "getDate", java.sql.Date.class, new ColumnDefinition("date", JDBCType.DATE)));
		maps.add(new FieldMapper("time", "getTime", java.sql.Time.class, new ColumnDefinition("time", JDBCType.TIME)));
		maps.add(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class,
				new ColumnDefinition("timestamp", TIMESTAMP)));

		String targetClazz = UserMoreComplex.class.getName();
		byte[] code = builder.make(UserMoreComplexJdbcRowMapper.class.getName(), targetClazz, maps);

		String codeActual = toString(code);
		String codeExpected = toString(UserMoreComplexJdbcRowMapper.class);
		assertEquals("Code", codeExpected, codeActual);
	}
}
