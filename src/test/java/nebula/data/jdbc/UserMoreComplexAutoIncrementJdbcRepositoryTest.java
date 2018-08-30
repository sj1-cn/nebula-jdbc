package nebula.data.jdbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;

public class UserMoreComplexAutoIncrementJdbcRepositoryTest extends TestBase {

	String clazz;
	Arguments arguments = new Arguments();
	Connection connection;

	@Before
	public void before() {
		connection = super.openConnection();
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
	public void testUser() throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {

		// 利用反射创建对象
		JdbcRepository<User> userRepository = new UserAutoIncrementJdbcRepository();
		userRepository.setConnection(connection);

		userRepository.init();
		userRepository.init();

		List<User> users1 = userRepository.list(0, 0);

		User a = new User(0, "name_a10", "description_a10");
		User b = new User(0, "name_b20", "description_b20");
		User b2 = new User(2, "name_b20_new", "description_b20_new");
		{
			a = userRepository.insert(a);
			assertEquals("User [id=1, name=name_a10, description=description_a10]", String.valueOf(a));
			users1 = userRepository.list(0, 0);
			assertEquals("[User [id=1, name=name_a10, description=description_a10]]", users1.toString());
		}
		{
			b = userRepository.insert(b);
			assertEquals("User [id=2, name=name_b20, description=description_b20]", String.valueOf(b));
			users1 = userRepository.list(0, 0);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20, description=description_b20]]",
					users1.toString());
		}
		{
			b = userRepository.update(b2);
			assertEquals("User [id=2, name=name_b20_new, description=description_b20_new]", String.valueOf(b));

			users1 = userRepository.list(0, 0);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20_new, description=description_b20_new]]",
					users1.toString());
		}
		{
			userRepository.delete(a.getId());
			users1 = userRepository.list(0, 0);
			assertEquals("[User [id=2, name=name_b20_new, description=description_b20_new]]", users1.toString());
		}
		{
			userRepository.delete(b.getId());
			users1 = userRepository.list(0, 0);
			assertEquals("[]", users1.toString());
		}
	}

	@Test
	public void testUserExist() throws SQLException {
		// 利用反射创建对象
		JdbcRepository<User> userRepository = new UserJdbcRepository();
		userRepository.setConnection(connection);

		connection.createStatement().execute("CREATE TABLE user (id INTEGER PRIMARY KEY, description VARCHAR)");

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
}
