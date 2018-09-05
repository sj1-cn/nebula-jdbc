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
import static nebula.jdbc.builders.schema.ColumnDefinition.*;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class JdbcRowMapperBuilderTest extends TestBase {

	ClazzExtendBuilder clazzExtendBuilder;
	JdbcRowMapperBuilder builder;
	Arguments arguments = new Arguments();

	@Before
	public void before() {
		builder = new JdbcRowMapperBuilder(arguments);
		clazzExtendBuilder = new ClazzExtendBuilder();
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
	public void testMockRunning() throws SQLException, InstantiationException, IllegalAccessException {

		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper("id", "getId", long.class, INTEGER("ID")));
		clazzFields.push(new FieldMapper("name", "getName", String.class, VARCHAR("NAME")));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, VARCHAR("description")));

		FieldList extend = new FieldList();
		extend.push(new FieldMapper("createAt", "getCreateAt", Timestamp.class, TIMESTAMP("createAt")));
		extend.push(new FieldMapper("updateAt", "getUpdateAt", Timestamp.class, TIMESTAMP("updateAt")));
		FieldList all = new FieldList();
		all.push(clazzFields.list());
		all.push(extend.list());

		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.getLong("id")).thenReturn(1047L);
		when(resultSet.getString("name")).thenReturn("TestName1047");
		when(resultSet.getString("description")).thenReturn("TestDescription1047");

		String targetClazz = User.class.getName();
		String clazzExtend = User.class.getName() + "Extend";
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzFields);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);

		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		byte[] code = builder.make(clazzRowMapper, clazzExtend, all);

		String codeActual = toString(code);
		String codeExpected = toString(clazzRowMapper);
		assertEquals("Code", codeExpected, codeActual);

		Class<JdbcRowMapper<?>> rowMapper = (Class<JdbcRowMapper<?>>) classLoader.defineClassByName(clazzRowMapper, code);
		JdbcRowMapper<?> mapper = rowMapper.newInstance();
		User user = (User) mapper.map(resultSet);

		assertEquals(1047L, user.getId());
		assertEquals("TestName1047", user.getName());
		assertEquals("TestDescription1047", user.getDescription());
	}

	@Test
	public void testUserJdbcRowMapper() {

		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper("id", "getId", long.class, INTEGER("ID")));
		clazzFields.push(new FieldMapper("name", "getName", String.class, VARCHAR("NAME")));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, VARCHAR("description")));

		FieldList extend = new FieldList();
		extend.push(new FieldMapper("createAt", "getCreateAt", Timestamp.class, TIMESTAMP("createAt")));
		extend.push(new FieldMapper("updateAt", "getUpdateAt", Timestamp.class, TIMESTAMP("updateAt")));
		FieldList all = new FieldList();
		all.push(clazzFields.list());
		all.push(extend.list());


		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		String targetClazz = User.class.getName();
		String clazzExtend = User.class.getName() + "Extend";
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzFields);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);
		byte[] code = builder.make(clazzRowMapper, clazzExtend, all);

		String codeActual = toString(code);
		String codeExpected = toString(clazzRowMapper);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void UserMoreComplexJdbcRowMapper() {
		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper("id", "getId", Long.class, new ColumnDefinition("id", BIGINT).primarykey().autoIncrement()));
		clazzFields.push(new FieldMapper("string", "getString", String.class, new ColumnDefinition("string", VARCHAR)));
		clazzFields.push(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, new ColumnDefinition("bigDecimal", NUMERIC)));
		clazzFields.push(new FieldMapper("z", "getZ", Boolean.class, new ColumnDefinition("z", BOOLEAN)));
		clazzFields.push(new FieldMapper("c", "getC", Character.class, new ColumnDefinition("c", CHAR)));
		clazzFields.push(new FieldMapper("b", "getB", Byte.class, new ColumnDefinition("b", BIT)));
		clazzFields.push(new FieldMapper("s", "getS", Short.class, new ColumnDefinition("s", JDBCType.SMALLINT)));
		clazzFields.push(new FieldMapper("i", "getI", Integer.class, new ColumnDefinition("i", INTEGER)));
		clazzFields.push(new FieldMapper("l", "getL", Long.class, new ColumnDefinition("l", BIGINT)));
		clazzFields.push(new FieldMapper("f", "getF", Float.class, new ColumnDefinition("f", FLOAT)));
		clazzFields.push(new FieldMapper("d", "getD", Double.class, new ColumnDefinition("d", DOUBLE)));
		clazzFields.push(new FieldMapper("date", "getDate", java.sql.Date.class, new ColumnDefinition("date", JDBCType.DATE)));
		clazzFields.push(new FieldMapper("time", "getTime", java.sql.Time.class, new ColumnDefinition("time", JDBCType.TIME)));
		clazzFields.push(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class, new ColumnDefinition("timestamp", TIMESTAMP)));

		FieldList extend = new FieldList();
		extend.push(new FieldMapper("createAt", "getCreateAt", Timestamp.class, TIMESTAMP("createAt")));
		extend.push(new FieldMapper("updateAt", "getUpdateAt", Timestamp.class, TIMESTAMP("updateAt")));
		
		FieldList all = new FieldList();
		all.push(clazzFields.list());
		all.push(extend.list());
		
		String targetClazz = UserMoreComplex.class.getName();
		String clazzExtend = UserMoreComplex.class.getName() + "Extend";
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzFields);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);
		byte[] code = builder.make(UserMoreComplexExtendJdbcRowMapper.class.getName(), clazzExtend, all);

		String codeActual = toString(code);
		String codeExpected = toString(UserMoreComplexExtendJdbcRowMapper.class);
		assertEquals("Code", codeExpected, codeActual);
	}
}
