package nebula.data.jdbc;

import static cn.sj1.tinyasm.tools.TinyAsmTestUtils.dumpTinyAsm;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.data.jdbc.sample.UserExtend;

public class JdbcRepositoryBuilderTinyASMifierTest {

	@Test
	public void test_UserExtend_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserExtend.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);

	}
	
	@Test
	public void test_UserJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);

	}

	@Test
	public void test_UserAutoIncrementJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void UserMoreComplexAutoIncrementJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserMoreComplexAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);

	}
//
//	@Test
//	public void test_UserAutoIncrementJdbcRepository_dumpTinyAsm_compare() throws Exception {
//		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
//		String codeExpected = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));
//		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UserAutoIncrementJdbcRepositoryTinyAsmDump2.dump());
//
//		assertEquals("Code", codeExpected, codeActual);
//
//	}


}
