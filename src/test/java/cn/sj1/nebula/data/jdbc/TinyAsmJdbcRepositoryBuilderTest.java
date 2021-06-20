package cn.sj1.nebula.data.jdbc;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BOOLEAN;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DATE;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DOUBLE;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.FLOAT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.SMALLINT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIME;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TINYINT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.sj1.nebula.data.jdbc.sample.User;
import cn.sj1.nebula.data.jdbc.sample.UserMoreComplex;
import cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.jdbc.TestBase;

public class TinyAsmJdbcRepositoryBuilderTest extends TestBase {

	TinyAsmJdbcRepositoryBuilder jdbcRepositoryBuilder;
	TinyAsmPrimativeTypeConverters arguments = new TinyAsmPrimativeTypeConverters();
	Connection connection;

	@Before
	public void before() {
		connection = super.openConnection();

		jdbcRepositoryBuilder = new TinyAsmJdbcRepositoryBuilder(arguments);

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

	@Test
	public void testUserJdbcRepository() {
		PersistentEntity clazzDefinition;

		FieldList clazzFields = new FieldList();

		clazzFields.push(new PersistentProperty(true, "id", "getId", long.class, INTEGER("id")));
		clazzFields.push(new PersistentProperty("name", "getName", String.class, VARCHAR("name")));
		clazzFields.push(new PersistentProperty("description", "getDescription", String.class, VARCHAR("description")));

		clazzDefinition = new PersistentEntity(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserAutoIncrementJdbcRepository() {
		PersistentEntity clazzDefinition;
		FieldList clazzFields = new FieldList();
		clazzFields.push(new PersistentProperty(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER).autoIncrement()));
		clazzFields.push(new PersistentProperty("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		clazzFields.push(new PersistentProperty("description", "getDescription", String.class, new ColumnDefinition("description", VARCHAR)));
		clazzDefinition = new PersistentEntity(User.class.getSimpleName(), UserMoreComplex.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserAutoIncrementJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserMoreComplexAutoIncrementJdbcRepository() {
		PersistentEntity clazzDefinition;
		FieldList clazzFields = new FieldList();
		clazzFields.push(new PersistentProperty(true, "id", "getId", long.class, BIGINT("id").primarykey().autoIncrement()));
		clazzFields.push(new PersistentProperty("string", "getString", String.class, VARCHAR("string")));
		clazzFields.push(new PersistentProperty("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, DECIMAL("bigDecimal")));
		clazzFields.push(new PersistentProperty("z", "getZ", Boolean.class, BOOLEAN("z")));
		clazzFields.push(new PersistentProperty("c", "getC", Character.class, CHAR("c")));
		clazzFields.push(new PersistentProperty("b", "getB", Byte.class, TINYINT("b")));
		clazzFields.push(new PersistentProperty("s", "getS", Short.class, SMALLINT("s")));
		clazzFields.push(new PersistentProperty("i", "getI", Integer.class, INTEGER("i")));
		clazzFields.push(new PersistentProperty("l", "getL", Long.class, BIGINT("l")));
		clazzFields.push(new PersistentProperty("f", "getF", Float.class, FLOAT("f")));
		clazzFields.push(new PersistentProperty("d", "getD", Double.class, DOUBLE("d")));
		clazzFields.push(new PersistentProperty("date", "getDate", java.sql.Date.class, DATE("date")));
		clazzFields.push(new PersistentProperty("time", "getTime", java.sql.Time.class, TIME("time")));
		clazzFields.push(new PersistentProperty("timestamp", "getTimestamp", java.sql.Timestamp.class, TIMESTAMP("timestamp")));
		clazzDefinition = new PersistentEntity(UserMoreComplex.class.getSimpleName(), UserMoreComplex.class.getName(), UserMoreComplex.class.getSimpleName(), clazzFields);

		String clazzRepository = UserMoreComplexAutoIncrementJdbcRepository.class.getName();
		String clazzTarget = UserMoreComplex.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzDefinition);

		String codeActual = toString(clazzRepository, codeRepository);
		String codeExpected = TinyAsmTestUtils.toString(clazzRepository);
		assertEquals("Code", codeExpected, codeActual);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUser() throws Exception {
		PersistentEntity clazzDefinition;

		FieldList clazzFields = new FieldList();

		clazzFields.push(new PersistentProperty(true, "id", "getId", long.class, INTEGER("id")));
		clazzFields.push(new PersistentProperty("name", "getName", String.class, VARCHAR("name")));
		clazzFields.push(new PersistentProperty("description", "getDescription", String.class, VARCHAR("description")));
		clazzDefinition = new PersistentEntity(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserJdbcRepository.class.getName();
		String clazzTarget = User.class.getName();
		String clazzExtend = clazzTarget + "Extend";

		byte[] codeRepository = jdbcRepositoryBuilder.dump(clazzRepository, clazzTarget, clazzExtend, clazzDefinition);
		Class<JdbcRepository<User>> clazzJdbcRepository = (Class<JdbcRepository<User>>) classLoader.defineClassByName(clazzRepository, codeRepository);

		JdbcRepository<User> userRepository = clazzJdbcRepository.getConstructor().newInstance();
		userRepository.setConnection(connection);

		// connection.createStatement().execute("CREATE TABLE USER (id INTEGER PRIMARY
		// KEY,description VARCHAR)");

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
			assertEquals("[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20, description=description_b20]]", users1.toString());
			b2.setName("name_b20_new");
			b2.setDescription("description_b20_new");
			userRepository.update(b2);

			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20_new, description=description_b20_new]]", users1.toString());
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
}
