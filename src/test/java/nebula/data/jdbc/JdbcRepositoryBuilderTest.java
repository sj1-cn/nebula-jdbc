package nebula.data.jdbc;

import static java.sql.JDBCType.BIGINT;
import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.CHAR;
import static java.sql.JDBCType.DATE;
import static java.sql.JDBCType.DECIMAL;
import static java.sql.JDBCType.DOUBLE;
import static java.sql.JDBCType.FLOAT;
import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.SMALLINT;
import static java.sql.JDBCType.TIME;
import static java.sql.JDBCType.TIMESTAMP;
import static java.sql.JDBCType.TINYINT;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class JdbcRepositoryBuilderTest extends TestBase {

	String clazz;
	List<FieldMapper> maps;
	JdbcRowMapperBuilder jdbcRowMapperBuilder;

	JdbcRepositoryBuilder jdbcRepositoryBuilder;
	Arguments arguments = new Arguments();
	Connection connection;

	@Before
	public void before() {
		connection = super.openConnection();

		jdbcRowMapperBuilder = new JdbcRowMapperBuilder(arguments);
		jdbcRepositoryBuilder = new JdbcRepositoryBuilder(arguments);
		maps = new ArrayList<FieldMapper>();
		clazz = UserJdbcRowMapper.class.getName();
		maps.add(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER)));
		maps.add(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

	}

	@After
	public void after() {
		super.closeConnection();
	}

//	@Test
//	public void testPrint() throws IOException {
//		System.out.println(RefineCode.refineCode(toString(UserJdbcRepository.class), ResultSet.class,
//				PreparedStatement.class, JdbcRepository.class, Connection.class));
//	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUser() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, SQLException {

		String clazzTarget = User.class.getName();
		String clazzRowMapper = UserJdbcRowMapper.class.getName() + "testUser";

		byte[] codeRowMapper = jdbcRowMapperBuilder.make(clazzRowMapper, clazzTarget, maps);
		@SuppressWarnings("unused")
		Class<JdbcRowMapper<User>> clazzJdbcRowMapper = (Class<JdbcRowMapper<User>>) classLoader
			.defineClassByName(clazzRowMapper, codeRowMapper);
		byte[] codeRepository = jdbcRepositoryBuilder.make(clazz, clazzTarget, clazzRowMapper, "user", maps);
		Class<JdbcRepository<User>> clazzJdbcRepository = (Class<JdbcRepository<User>>) classLoader
			.defineClassByName(this.clazz, codeRepository);

		// 利用反射创建对象
		JdbcRepository<User> userRepository = clazzJdbcRepository.newInstance();
		userRepository.setConnection(connection);

		connection.createStatement().execute("CREATE TABLE user (id INTEGER PRIMARY KEY,description VARCHAR)");

		userRepository.init();

		List<User> users1 = userRepository.list(0, 0);

		User a = new User(10, "name_a10", "description_a10");
		User b = new User(20, "name_b20", "description_b20");
		User b2 = new User(20, "name_b20_new", "description_b20_new");
		{
			userRepository.insert(a);
			users1 = userRepository.list(0, 0);
			assertEquals("[User [id=10, name=name_a10, description=description_a10]]", users1.toString());
		}
		{
			userRepository.insert(b);
			users1 = userRepository.list(0, 0);
			assertEquals(
					"[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20, description=description_b20]]",
					users1.toString());
		}
		{
			userRepository.update(b2);

			users1 = userRepository.list(0, 0);
			assertEquals(
					"[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20_new, description=description_b20_new]]",
					users1.toString());
		}
		{
			userRepository.delete(a.getId());
			users1 = userRepository.list(0, 0);
			assertEquals("[User [id=20, name=name_b20_new, description=description_b20_new]]", users1.toString());
		}
		{
			userRepository.delete(b.getId());
			users1 = userRepository.list(0, 0);
			assertEquals("[]", users1.toString());
		}
	}

	@Test
	public void testUserJdbcRepository() {

		String clazz = UserJdbcRepository.class.getName();
		String targetClazz = User.class.getName();
		String mapClazz = UserJdbcRowMapper.class.getName();

		JdbcRepositoryBuilder builder = new JdbcRepositoryBuilder(new Arguments());
		byte[] code = builder.make(clazz, targetClazz, mapClazz, "user", maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazz);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserKeysJdbcRepository() {
		maps = new ArrayList<FieldMapper>();
		maps.add(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER)));
		maps.add(new FieldMapper(true, "name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

		String clazz = UserKeysJdbcRepository.class.getName();
		String targetClazz = User.class.getName();
		String mapClazz = UserJdbcRowMapper.class.getName();

		JdbcRepositoryBuilder builder = new JdbcRepositoryBuilder(new Arguments());
		byte[] code = builder.make(clazz, targetClazz, mapClazz, "user", maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazz);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserMoreComplexAutoIncrementJdbcRepository() {
		List<FieldMapper> maps = new ArrayList<FieldMapper>();
		maps.add(new FieldMapper(true, "id", "getId", Long.class,
				new ColumnDefinition("id", BIGINT).primarykey().autoIncrement()));
		maps.add(new FieldMapper("string", "getString", String.class, new ColumnDefinition("string", VARCHAR)));
		maps.add(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class,
				new ColumnDefinition("bigDecimal", DECIMAL)));
		maps.add(new FieldMapper("z", "getZ", Boolean.class, new ColumnDefinition("z", BOOLEAN)));
		maps.add(new FieldMapper("c", "getC", Character.class, new ColumnDefinition("c", CHAR)));
		maps.add(new FieldMapper("b", "getB", Byte.class, new ColumnDefinition("b", TINYINT)));
		maps.add(new FieldMapper("s", "getS", Short.class, new ColumnDefinition("s", SMALLINT)));
		maps.add(new FieldMapper("i", "getI", Integer.class, new ColumnDefinition("i", INTEGER)));
		maps.add(new FieldMapper("l", "getL", Long.class, new ColumnDefinition("l", BIGINT)));
		maps.add(new FieldMapper("f", "getF", Float.class, new ColumnDefinition("f", FLOAT)));
		maps.add(new FieldMapper("d", "getD", Double.class, new ColumnDefinition("d", DOUBLE)));
		maps.add(new FieldMapper("date", "getDate", java.sql.Date.class, new ColumnDefinition("date", DATE)));
		maps.add(new FieldMapper("time", "getTime", java.sql.Time.class, new ColumnDefinition("time", TIME)));
		maps.add(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class,
				new ColumnDefinition("timestamp", TIMESTAMP)));

		String clazz = UserMoreComplexAutoIncrementJdbcRepository.class.getName();
		String targetClazz = UserMoreComplex.class.getName();
		String mapClazz = UserMoreComplexJdbcRowMapper.class.getName();

		byte[] code = jdbcRepositoryBuilder.make(clazz, targetClazz, mapClazz, "UserMoreComplex", maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazz);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserAutoIncrementJdbcRepository() {
		maps = new ArrayList<FieldMapper>();
		maps.add(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER).autoIncrement()));
		maps.add(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		maps.add(new FieldMapper("description", "getDescription", String.class,
				new ColumnDefinition("description", VARCHAR)));

		String clazz = UserAutoIncrementJdbcRepository.class.getName();
		String targetClazz = User.class.getName();
		String mapClazz = UserJdbcRowMapper.class.getName();

		byte[] code = jdbcRepositoryBuilder.make(clazz, targetClazz, mapClazz, "user", maps);

		String codeActual = toString(code);
		String codeExpected = toString(clazz);
		assertEquals("Code", codeExpected, codeActual);
	}
}
