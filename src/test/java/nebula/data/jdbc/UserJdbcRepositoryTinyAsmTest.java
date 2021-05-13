package nebula.data.jdbc;

import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.data.query.Condition;
import nebula.jdbc.TestBase;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class UserJdbcRepositoryTinyAsmTest extends TestBase {

	String clazz;
	Arguments arguments = new Arguments();
	Connection connection;
	JdbcRepository<User> userRepository;
	EntityDefinition entityDefinition;

	@SuppressWarnings("unchecked")
	@Before
	public void before() throws Exception {
		connection = super.openConnection();

		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER).autoIncrement()));
		clazzFields.push(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, new ColumnDefinition("description", VARCHAR)));
		entityDefinition = new EntityDefinition(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserJdbcRepository.class.getName();

		byte[] codeRepository = UserJdbcRepositoryTinyAsmBuilder.dumpStatic(clazzRepository, User.class.getName(), UserExtend.class.getName(), entityDefinition);

		TinyAsmClassLoader classLoader = new TinyAsmClassLoader();

		Class<?> clazz = classLoader.doDefineClass(UserJdbcRepository.class.getName(), codeRepository);
		TinyAsmClassLoader.doResolveClass(clazz);

		// 利用反射创建对象
		userRepository = (JdbcRepository<User>) clazz.getConstructor().newInstance();
		userRepository.setConnection(connection);

	}

	@After
	public void after() {
		super.closeConnection();
	}

	@Test
	public void testc() {
//		String code = "id INTEGER(10,6) PRIMARY KEY REMARK 'DSF'";
////		  List<Parameter> parms = new ArrayList<>();
//		Matcher matcher = CONST_PATTERN.matcher(code);
//		int start = 0;
//		StringBuilder buf = new StringBuilder();
//		while (matcher.find()) {
//			int pos = matcher.start();
//			for (int i = 1; i <= matcher.groupCount(); i++) {
//				System.out.println(String.format("%d %s", i, matcher.group(i)));
//			}
//
//			start = matcher.end();
//		}

//		String dd = code.replaceAll(match, "[$1] [$2] [$3] [$4] [$5] [$6] [$7] [$8]");
//		System.out.println(dd);

	}

//	@Test
//	public void testPrint() throws IOException {
//		System.out.println(nebula.tinyasm.util.RefineCode.refineCode(toString(UserJdbcRepository.class), ResultSet.class,
//				PreparedStatement.class, JdbcRepository.class));
//	}

	@Test
	public void testUserPagination() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		userRepository.init();
		userRepository.init();

		PageList<User> users = userRepository.list(0, 10);

		for (int i = 1; i < 101; i++) {
			User a0 = new User(i, "name_a10", "description_a10");
			userRepository.insert(a0);
		}

		{
			users = userRepository.list(0, 0);
			assertEquals(100, users.getTotalSize());
			assertEquals(1, users.get(0).getId());
		}

		{
			users = userRepository.list(0, 9);
			assertEquals(10, users.size());
			assertEquals(1, users.get(0).getId());
			assertEquals(10, users.get(9).getId());
			assertEquals(100, users.getTotalSize());
		}
		{
			users = userRepository.list(10, 19);
			assertEquals(10, users.size());
			assertEquals(11, users.get(0).getId());
			assertEquals(20, users.get(9).getId());
			assertEquals(100, users.getTotalSize());
		}
	}

	@Test
	public void testUserWhere() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		userRepository.init();
		userRepository.init();

		PageList<User> users = userRepository.list(0, 10);

		for (int i = 1; i < 101; i++) {
			User a0 = new User(i, "name_a1" + i, "description_a10");
			userRepository.insert(a0);
		}

		{
			users = userRepository.list(Condition.field("name").eq("name_a11"), 0, 0);
			assertEquals(1, users.getTotalSize());
			assertEquals(1, users.get(0).getId());
		}

		{
			users = userRepository.list(0, 9);
			assertEquals(10, users.size());
			assertEquals(1, users.get(0).getId());
			assertEquals(10, users.get(9).getId());
			assertEquals(100, users.getTotalSize());
		}
		{
			users = userRepository.list(Condition.field("name").like("name_a12%"), 0, 0);
			assertEquals(11, users.getTotalSize());
			assertEquals(2, users.get(0).getId());
		}
		{
			users = userRepository.list(10, 19);
			assertEquals(10, users.size());
			assertEquals(11, users.get(0).getId());
			assertEquals(20, users.get(9).getId());
			assertEquals(100, users.getTotalSize());
		}
	}

	@Test
	public void testUser() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		userRepository.init();
		userRepository.init();

		PageList<User> users = userRepository.list(0, 10);

		User a = new User(10, "name_a10", "description_a10");
		User b = new User(20, "name_b20", "description_b20");
		{
			User realuser = userRepository.insert(a);
			assertEquals("User [id=10, name=name_a10, description=description_a10]", String.valueOf(realuser));
			users = userRepository.list(0, 10);
			assertEquals(1, users.getTotalSize());
			assertEquals("[User [id=10, name=name_a10, description=description_a10]]", users.toString());
		}
		{
			User realuser = userRepository.insert(b);
			assertEquals("User [id=20, name=name_b20, description=description_b20]", String.valueOf(realuser));
			users = userRepository.list(0, 10);
			assertEquals(2, users.getTotalSize());
			assertEquals("[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20, description=description_b20]]", users.toString());

			User updateUser = realuser;
			updateUser.setName("name_b20_new");
			updateUser.setDescription("description_b20_new");

			realuser = userRepository.update(updateUser);

			assertEquals("User [id=20, name=name_b20_new, description=description_b20_new]", String.valueOf(realuser));
			users = userRepository.list(0, 10);
			assertEquals(2, users.getTotalSize());
			assertEquals("[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20_new, description=description_b20_new]]", users.toString());
		}
		{
			userRepository.deleteById(a.getId());
			users = userRepository.list(0, 10);
			assertEquals(1, users.getTotalSize());
			assertEquals("[User [id=20, name=name_b20_new, description=description_b20_new]]", users.toString());
		}
		{
			userRepository.deleteById(b.getId());
			users = userRepository.list(0, 10);
			assertEquals(0, users.getTotalSize());
			assertEquals("[]", users.toString());
		}
	}

	@Test
	public void testUserExist() throws SQLException {

		connection.createStatement().execute("CREATE TABLE USER (id INTEGER PRIMARY KEY, description VARCHAR)");

		userRepository.init();

		List<User> users1 = userRepository.list(0, 10);

		User a = new User(10, "name_a10", "description_a10");
		User b = new User(20, "name_b20", "description_b20");
		{
			userRepository.insert(a);
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=10, name=name_a10, description=description_a10]]", users1.toString());
		}
		{
			b = userRepository.insert(b);
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=10, name=name_a10, description=description_a10], User [id=20, name=name_b20, description=description_b20]]", users1.toString());
			b.setName("name_b20_new");
			b.setDescription("description_b20_new");
			userRepository.update(b);

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
