package nebula.data.jdbc;

import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

	String clazz;
	List<FieldMapper> maps;
	JdbcRowMapperBuilder builder;
	Arguments arguments = new Arguments();

	@Before
	public void before() {
		builder = new JdbcRowMapperBuilder(arguments);
		maps = new ArrayList<FieldMapper>();
		clazz = UserJdbcRowMapper.class.getName();
		maps.add(new FieldMapper("id", "getId", long.class, new ColumnDefinition("ID", INTEGER)));
		maps.add(new FieldMapper("name", "getName", String.class, new ColumnDefinition("NAME", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("DESCRIPTION", VARCHAR)));
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
	public void testMockRunning()
			throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, SQLException {

		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.getLong("id")).thenReturn(1047L);
		when(resultSet.getString("name")).thenReturn("TestName1047");
		when(resultSet.getString("description")).thenReturn("TestDescription1047");

		String targetClazz = User.class.getName();
		byte[] code = builder.make(clazz, targetClazz, maps);
		Class<JdbcRowMapper<User>> row = (Class<JdbcRowMapper<User>>) classLoader.defineClassByName(this.clazz, code);
		JdbcRowMapper<User> mapper = row.newInstance();
		User user = mapper.map(resultSet);

		assertEquals(1047L, user.getId());
		assertEquals("TestName1047", user.getName());
		assertEquals("TestDescription1047", user.getDescription());
	}

	@Test
	public void testMakedCode() {

		String targetClazz = User.class.getName();
		byte[] code = builder.make(clazz, targetClazz, maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazz);
		assertEquals("Code", codeExpected, codeActual);
	}
}
