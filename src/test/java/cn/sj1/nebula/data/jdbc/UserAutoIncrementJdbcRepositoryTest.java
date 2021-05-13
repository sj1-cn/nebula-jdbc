package cn.sj1.nebula.data.jdbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.sj1.nebula.data.jdbc.JdbcRepository;
import cn.sj1.nebula.data.jdbc.TinyAsmPrimativeTypeConverters;
import cn.sj1.nebula.data.jdbc.sample.User;
import nebula.jdbc.TestBase;

public class UserAutoIncrementJdbcRepositoryTest extends TestBase {

	String clazz;
	TinyAsmPrimativeTypeConverters arguments = new TinyAsmPrimativeTypeConverters();
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
//		userRepository.init();

		List<User> users1 = userRepository.list(0, 10);

		User a = new User(0, "name_a10", "description_a10");
		User b = new User(0, "name_b20", "description_b20");
		User b2;
		{
			a = userRepository.insert(a);
			assertEquals("User [id=1, name=name_a10, description=description_a10]", String.valueOf(a));
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=1, name=name_a10, description=description_a10]]", users1.toString());
		}
		{
			b = userRepository.insert(b);
			assertEquals("User [id=2, name=name_b20, description=description_b20]", String.valueOf(b));
			users1 = userRepository.list(0, 10);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20, description=description_b20]]",
					users1.toString());
		}
		b2 = b;
		b2.setName("name_b20_new");
		b2.setDescription("description_b20_new");
		{
			b = userRepository.update(b2);
			assertEquals("User [id=2, name=name_b20_new, description=description_b20_new]", String.valueOf(b));

			users1 = userRepository.list(0, 10);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20_new, description=description_b20_new]]",
					users1.toString());
		}
		{
			userRepository.deleteById(a.getId());
			users1 = userRepository.list(0, 10);
			assertEquals("[User [id=2, name=name_b20_new, description=description_b20_new]]", users1.toString());
		}
		{
			userRepository.deleteById(b.getId());
			users1 = userRepository.list(0, 10);
			assertEquals("[]", users1.toString());
		}
	}
}
