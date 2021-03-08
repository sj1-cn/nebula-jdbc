package nebula.data.jdbc;

import static cc1sj.tinyasm.util.TinyAsmTestUtils.dumpTinyAsm;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc1sj.tinyasm.util.TinyAsmTestUtils;

public class JdbcRepositoryBuilderTinyASMifierTest {

	@Test
	public void test_UserKeysJdbcRepository() throws Exception {
		Class<?> expectedClazz = UserKeysJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		try {
			String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

			assertEquals("Code", codeExpected, codeActual);
		} finally {

			System.out.println(codeExpected);

		}

	}

	@Test
	public void test_UserJdbcRepository() throws Exception {
		Class<?> expectedClazz = UserJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		try {
			String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

			assertEquals("Code", codeExpected, codeActual);
		} finally {

			System.out.println(codeExpected);

		}

	}

	@Test
	public void test_UserAutoIncrementJdbcRepository() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		try {
			String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

			assertEquals("Code", codeExpected, codeActual);
		} finally {

			System.out.println(codeExpected);

		}

	}

	@Test
	public void test_UserAutoIncrementJdbcRepository_dump() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UserAutoIncrementJdbcRepositoryMagicBuilder.dump());

		assertEquals("Code", codeExpected, codeActual);
	}
//	@Test
//	public void test_UserAutoIncrementJdbcRepositoryWithProxy() throws Exception {
//		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
//		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);
//
//		try {
//			String codeActual = TinyAsmTestUtils.toString(UserAutoIncrementJdbcRepositoryTinyAsmWithProxyBuilder.dump());
//
////			assertEquals("Code", codeExpected, codeActual);
//		} finally {
//
//			System.out.println(codeExpected);
//
//		}
//
//	}

	@Test
	public void test_UserMoreComplexAutoIncrementJdbcRepository() throws Exception {
		Class<?> expectedClazz = UserMoreComplexAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		try {
			String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

			assertEquals("Code", codeExpected, codeActual);
		} finally {

			System.out.println(codeExpected);

		}

	}

	@Test
	public void test_UserAutoIncrementJdbcRepositoryTinyAsmDump2() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));
		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UserAutoIncrementJdbcRepositoryTinyAsmDump2.dump());

		assertEquals("Code", codeExpected, codeActual);
//
//		try {
//		} finally {
//
//			System.out.println(codeExpected);
//
//		}

	}
}
