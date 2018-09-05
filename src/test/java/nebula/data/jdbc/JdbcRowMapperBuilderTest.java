package nebula.data.jdbc;

import static nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.BIT;
import static nebula.jdbc.builders.schema.ColumnDefinition.BOOLEAN;
import static nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static nebula.jdbc.builders.schema.ColumnDefinition.DATE;
import static nebula.jdbc.builders.schema.ColumnDefinition.DOUBLE;
import static nebula.jdbc.builders.schema.ColumnDefinition.FLOAT;
import static nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static nebula.jdbc.builders.schema.ColumnDefinition.NUMERIC;
import static nebula.jdbc.builders.schema.ColumnDefinition.SMALLINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIME;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;
import static nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;

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

		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.getLong("id")).thenReturn(1047L);
		when(resultSet.getString("name")).thenReturn("TestName1047");
		when(resultSet.getString("description")).thenReturn("TestDescription1047");

		String targetClazz = User.class.getName();
		String clazzExtend = targetClazz + "Extend";
		ClazzDefinition clazzDefinition = new ClazzDefinition(User.class.getSimpleName(),User.class.getName(), User.class.getSimpleName(),
				clazzFields);
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzDefinition);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);

		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		byte[] code = builder.make(clazzRowMapper, clazzExtend, clazzDefinition.fieldsAll);

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

		String targetClazz = User.class.getName();
		String clazzExtend = targetClazz + "Extend";
		ClazzDefinition clazzDefinition = new ClazzDefinition(User.class.getSimpleName(), User.class.getName(),User.class.getSimpleName(),
				clazzFields);
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzDefinition);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);

		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		byte[] code = builder.make(clazzRowMapper, clazzExtend, clazzDefinition.fieldsAll);

		String codeActual = toString(code);
		String codeExpected = toString(clazzRowMapper);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void UserMoreComplexJdbcRowMapper() {
		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper("id", "getId", Long.class, BIGINT("id").primarykey().autoIncrement()));
		clazzFields.push(new FieldMapper("string", "getString", String.class, VARCHAR("string")));
		clazzFields.push(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, NUMERIC("bigDecimal")));
		clazzFields.push(new FieldMapper("z", "getZ", Boolean.class, BOOLEAN("z")));
		clazzFields.push(new FieldMapper("c", "getC", Character.class, CHAR("c")));
		clazzFields.push(new FieldMapper("b", "getB", Byte.class, BIT("b")));
		clazzFields.push(new FieldMapper("s", "getS", Short.class, SMALLINT("s")));
		clazzFields.push(new FieldMapper("i", "getI", Integer.class, INTEGER("i")));
		clazzFields.push(new FieldMapper("l", "getL", Long.class, BIGINT("l")));
		clazzFields.push(new FieldMapper("f", "getF", Float.class, FLOAT("f")));
		clazzFields.push(new FieldMapper("d", "getD", Double.class, DOUBLE("d")));
		clazzFields.push(new FieldMapper("date", "getDate", java.sql.Date.class, DATE("date")));
		clazzFields.push(new FieldMapper("time", "getTime", java.sql.Time.class, TIME("time")));
		clazzFields.push(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class, TIMESTAMP("timestamp")));

		String targetClazz = UserMoreComplex.class.getName();
		String clazzExtend = targetClazz + "Extend";
		ClazzDefinition clazzDefinition = new ClazzDefinition(UserMoreComplex.class.getSimpleName(),User.class.getName(), UserMoreComplex.class.getSimpleName(),
				clazzFields);
		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtend, targetClazz, clazzDefinition);
		classLoader.defineClassByName(clazzExtend, codeClazzExtend);

		String clazzRowMapper = UserMoreComplexExtendJdbcRowMapper.class.getName();
		byte[] code = builder.make(clazzRowMapper, clazzExtend, clazzDefinition.fieldsAll);

		String codeActual = toString(code);
		String codeExpected = toString(clazzRowMapper);
		assertEquals("Code", codeExpected, codeActual);
	}
}
