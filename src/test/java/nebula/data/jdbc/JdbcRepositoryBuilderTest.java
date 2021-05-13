package nebula.data.jdbc;

import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.VARCHAR;
import static nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.BOOLEAN;
import static nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static nebula.jdbc.builders.schema.ColumnDefinition.DATE;
import static nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static nebula.jdbc.builders.schema.ColumnDefinition.DOUBLE;
import static nebula.jdbc.builders.schema.ColumnDefinition.FLOAT;
import static nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static nebula.jdbc.builders.schema.ColumnDefinition.SMALLINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIME;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;
import static nebula.jdbc.builders.schema.ColumnDefinition.TINYINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class JdbcRepositoryBuilderTest extends TestBase {
	EntityDefinition clazzDefinition;
	JdbcRowMapperBuilder jdbcRowMapperBuilder;

	JdbcRepositoryBuilder jdbcRepositoryBuilder;
	Arguments arguments = new Arguments();
	Connection connection;

	@Before
	public void before() {
		connection = super.openConnection();

		jdbcRowMapperBuilder = new JdbcRowMapperBuilder(arguments);
		jdbcRepositoryBuilder = new JdbcRepositoryBuilder(arguments);

		FieldList clazzFields = new FieldList();

		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, INTEGER("id")));
		clazzFields.push(new FieldMapper("name", "getName", String.class, VARCHAR("name")));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, VARCHAR("description")));

		clazzDefinition = new EntityDefinition(User.class.getSimpleName(),User.class.getName(),  User.class.getSimpleName(), clazzFields);
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
	public void testUser() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException, SQLException {

		String clazzRepository = UserJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRowMapper = jdbcRowMapperBuilder.make(clazzRowMapper, clazzExtend, clazzDefinition.fieldsAll);
		@SuppressWarnings("unused")
		Class<JdbcRowMapper<?>> clazzJdbcRowMapper = (Class<JdbcRowMapper<?>>) classLoader.defineClassByName(clazzRowMapper,
				codeRowMapper);
		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);
		Class<JdbcRepository<User>> clazzJdbcRepository = (Class<JdbcRepository<User>>) classLoader.defineClassByName(clazzRepository,
				codeRepository);

		JdbcRepository<User> userRepository = clazzJdbcRepository.getConstructor().newInstance();
		userRepository.setConnection(connection);

		connection.createStatement().execute("CREATE TABLE USER (id INTEGER PRIMARY KEY,description VARCHAR)");

		userRepository.init();

		List<User> users1 = userRepository.list(0, 10);

		User a = new User(10, "name_a10", "description_a10");
		User b = new User(20, "name_b20", "description_b20");
		User b2 = new User(20, "name_b20_new", "description_b20_new");
		{
			userRepository.insert(a);
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=10, name=name_a10, description=description_a10]]", users1.toString());
		}
		{
			b2 = userRepository.insert(b);
			users1 = userRepository.list(0, 10);
			assertEquals(
					"[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20, description=description_b20]]",
					users1.toString());
			b2.setName("name_b20_new");
			b2.setDescription("description_b20_new");
			userRepository.update(b2);

			users1 = userRepository.list(0, 10);
			assertEquals(
					"[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20_new, description=description_b20_new]]",
					users1.toString());
		}
		{
			userRepository.deleteById(a.getId());
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=20, name=name_b20_new, description=description_b20_new]]", users1.toString());
		}
		{
			userRepository.deleteById(b.getId());
			users1 = userRepository.list(0, 10);
			assertEquals("[]", users1.toString());
		}
	}

	@Test
	public void testUserJdbcRepository() {

		String clazzRepository = UserJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}
//
//	@Test
//	public void testUserKeysJdbcRepository() {
//		FieldList clazzFields = new FieldList();
//		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, INTEGER("id")));
//		clazzFields.push(new FieldMapper(true, "name", "getName", String.class, VARCHAR("name")));
//		clazzFields.push(new FieldMapper("description", "getDescription", String.class, VARCHAR("description")));
//
//		clazzDefinition = new EntityDefinition(User.class.getSimpleName(), User.class.getName(),User.class.getSimpleName(), clazzFields);
//
//		String clazzRepository = UserKeysJdbcRepository.class.getName();
//		String clazzTarget = User.class.getName();
//		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
//		String clazzExtend = clazzTarget + "Extend";
//
//		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);
//
//		String codeActual = toString(clazzRepository, codeRepository);
//		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
//		assertEquals("Code", codeExpected, codeActual);
//	}

	@Test
	public void testUserMoreComplexAutoIncrementJdbcRepository() {
		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper(true,"id", "getId", Long.class, BIGINT("id").primarykey().autoIncrement()));
		clazzFields.push(new FieldMapper("string", "getString", String.class, VARCHAR("string")));
		clazzFields.push(new FieldMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, DECIMAL("bigDecimal")));
		clazzFields.push(new FieldMapper("z", "getZ", Boolean.class, BOOLEAN("z")));
		clazzFields.push(new FieldMapper("c", "getC", Character.class, CHAR("c")));
		clazzFields.push(new FieldMapper("b", "getB", Byte.class, TINYINT("b")));
		clazzFields.push(new FieldMapper("s", "getS", Short.class, SMALLINT("s")));
		clazzFields.push(new FieldMapper("i", "getI", Integer.class, INTEGER("i")));
		clazzFields.push(new FieldMapper("l", "getL", Long.class, BIGINT("l")));
		clazzFields.push(new FieldMapper("f", "getF", Float.class, FLOAT("f")));
		clazzFields.push(new FieldMapper("d", "getD", Double.class, DOUBLE("d")));
		clazzFields.push(new FieldMapper("date", "getDate", java.sql.Date.class, DATE("date")));
		clazzFields.push(new FieldMapper("time", "getTime", java.sql.Time.class, TIME("time")));
		clazzFields.push(new FieldMapper("timestamp", "getTimestamp", java.sql.Timestamp.class, TIMESTAMP("timestamp")));
		clazzDefinition = new EntityDefinition(UserMoreComplex.class.getSimpleName(), UserMoreComplex.class.getName(),UserMoreComplex.class.getSimpleName(), clazzFields);

		String clazzRepository = UserMoreComplexAutoIncrementJdbcRepository.class.getName();
		String clazzTarget = UserMoreComplex.class.getName();
		String clazzRowMapper = UserMoreComplexExtendJdbcRowMapper.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserAutoIncrementJdbcRepository() {
		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER).autoIncrement()));
		clazzFields.push(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, new ColumnDefinition("description", VARCHAR)));
		clazzDefinition = new EntityDefinition(User.class.getSimpleName(),UserMoreComplex.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserAutoIncrementJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzRowMapper = UserExtendJdbcRowMapper.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}
}
