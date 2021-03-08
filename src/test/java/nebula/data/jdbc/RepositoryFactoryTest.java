package nebula.data.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nebula.jdbc.TestBase;

public class RepositoryFactoryTest extends TestBase {

	RepositoryFactory repositoryFactory;

	@Before
	public void before() {
		repositoryFactory = new RepositoryFactory(super.openConnection(this.getClass().getName()));
	}

	@After
	public void after() {
		super.closeConnection();
	}

	@Test
	public void test_getMapper() {
		ClazzDefinition clazzDefinition = repositoryFactory.build(User.class);
		repositoryFactory.makeJdbcRowMapper(clazzDefinition);
	}

	@Test
	public void test_getRepository() {
		Repository<User> userRepository = repositoryFactory.getRepository(User.class);

		assert userRepository.getClass().getName() != null;
	}

	@Test
	public void test_getUserRepository() {

		Repository<User> userRepository = repositoryFactory.getRepository(User.class);

		userRepository.init();

		User a = new User(1, "name_a10", "description_a10");
		User b = new User(2, "name_b20", "description_b20");
		User b2;
		List<User> userlist;
		{
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}
		{
			a = userRepository.insert(a);
			userlist = userRepository.list(0, 10);
			assertEquals("[User [id=1, name=name_a10, description=description_a10]]", userlist.toString());
		}
		{
			b = userRepository.insert(b);
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20, description=description_b20]]",
					userlist.toString());
		}
		b2 = b;
		b2.setName("name_b20_new");
		b2.setDescription("description_b20_new");
		{
			userRepository.update(b2);

			userlist = userRepository.list(0, 10);
			assertEquals(
					"[User [id=1, name=name_a10, description=description_a10], User [id=2, name=name_b20_new, description=description_b20_new]]",
					userlist.toString());
		}
		{
			userRepository.deleteById(a.getId());
			userlist = userRepository.list(0, 10);
			assertEquals("[User [id=2, name=name_b20_new, description=description_b20_new]]", userlist.toString());
		}
		{
			userRepository.deleteById(b.getId());
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}
	}

	@Test
	public void test_getUserComplexRepository() {
		Repository<UserComplex> userRepository = repositoryFactory.getRepository(UserComplex.class);
		List<UserComplex> userlist;
		userRepository.init();
		{
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}
		UserComplex a = new UserComplex(1, "name_a10", new BigDecimal(10000), false, 'A', (byte) 51, (short) 601, 701, 801L, 90.1F, 100.1D,
				new Date(1100), new Time(1200), new Timestamp(1300));
		UserComplex b = new UserComplex(2, "name_b20", new BigDecimal(20000), false, 'B', (byte) 251, (short) 2601, 2701, 2801L, 290.1F,
				2100.1D, new Date(21100), new Time(21200), new Timestamp(21300));
		UserComplex b2;// = new UserComplex(2, "name_b30", new BigDecimal(30000), false, 'C', (byte)
						// 351, (short) 3601,3701, 3801L, 390.1F, 3100.1D, new Date(31100), new
						// Time(31300), new Timestamp(31300));

		{
			a = userRepository.insert(a);
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3]]",
					userlist.toString());
		}
		{
			b = userRepository.insert(b);
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3], "
							+ "UserComplex [id=2, string=name_b20, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}
		b2 = b;
		b2.setString("name_b30");
		b2.setDate(new Date(21100));
		b2.setTime(new Time(21200));
		b2.setTimestamp(new Timestamp(21300));
		{
			b2 = userRepository.update(b2);
			assertNotNull(b2);
			
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3], "
							+ "UserComplex [id=2, string=name_b30, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}
		{
			userRepository.deleteById(a.getId());
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserComplex [id=2, string=name_b30, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}
		{
			userRepository.deleteById(b.getId());
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}
	}

	@Test
	public void test_getUserMoreComplexRepository() {

		Repository<UserMoreComplex> userRepository = repositoryFactory.getRepository(UserMoreComplex.class);
		List<UserMoreComplex> userlist;
		userRepository.init();
		{
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}

//		long id, String string, BigDecimal bigDecimal, Boolean z, Character c, Byte b, Short s,
//		Integer i, Long l, Float f, Double d, Date date, Time time, Timestamp timestamp
//		UserMoreComplex a = new UserMoreComplex(1, "name_b20", null, null, null, null, null, null, null, null, null, null, null, null);

		UserMoreComplex a = new UserMoreComplex(1L, "name_a10", new BigDecimal(10000), false, 'A', (byte) 51, (short) 601, 701, 801L, 90.1F,
				100.1D, new Date(1100), new Time(1200), new Timestamp(1300));

		UserMoreComplex b = new UserMoreComplex(2L, "name_b20", new BigDecimal(20000), false, 'B', (byte) 251, (short) 2601, 2701, 2801L,
				290.1F, 2100.1D, new Date(21100), new Time(21200), new Timestamp(21300));
		UserMoreComplex b2;// = new UserMoreComplex(2L, "name_b30", new BigDecimal(30000), false, 'C',
							// (byte) 351, (short) 3601, 3701, 3801L, 390.1F, 3100.1D, new Date(31100), new
							// Time(31300), new Timestamp(31300));

		{
			userRepository.insert(a);
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserMoreComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3]]",
					userlist.toString());
		}
		{
			b = userRepository.insert(b);
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserMoreComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3], "
							+ "UserMoreComplex [id=2, string=name_b20, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}

		b2 = b;
		b2.setString("name_b30");
		b2.setDate(new Date(21100));
		b2.setTime(new Time(21200));
		b2.setTimestamp(new Timestamp(21300));
//		b2.setName("name_b20_new");
//		b2.setDescription("description_b20_new");
		{
			userRepository.update(b2);

			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserMoreComplex [id=1, string=name_a10, bigDecimal=10000.000000, z=false, c=A, b=51, s=601, i=701, l=801, f=90.1, d=100.1, date=1970-01-01, time=08:00:01, timestamp=1970-01-01 08:00:01.3], "
							+ "UserMoreComplex [id=2, string=name_b30, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}
		{
			userRepository.deleteById(a.getId());
			userlist = userRepository.list(0, 10);
			assertEquals(
					"[UserMoreComplex [id=2, string=name_b30, bigDecimal=20000.000000, z=false, c=B, b=-5, s=2601, i=2701, l=2801, f=290.1, d=2100.1, date=1970-01-01, time=08:00:21, timestamp=1970-01-01 08:00:21.3]]",
					userlist.toString());
		}
		{
			userRepository.deleteById(b.getId());
			userlist = userRepository.list(0, 10);
			assertEquals("[]", userlist.toString());
		}
	}
//	@Test
//	public void printUserMapper() {
//		System.out.println(RefineCode.refineCode(toString(UserMapper.class.getName())));
//	}

}
