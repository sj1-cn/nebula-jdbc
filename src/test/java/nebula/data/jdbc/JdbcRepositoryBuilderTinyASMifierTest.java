package nebula.data.jdbc;

import static cc1sj.tinyasm.util.TinyAsmTestUtils.dumpTinyAsm;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc1sj.tinyasm.AdvAsmProxyMagicClassAdvAsmBuilder;
import cc1sj.tinyasm.util.TinyAsmTestUtils;

public class JdbcRepositoryBuilderTinyASMifierTest {

	@Test
	public void test_UserKeysJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserKeysJdbcRepository.class;
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
	public void test_UserAutoIncrementJdbcRepository_MagicBuilder_Build() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepositoryMagicBuilderObjenesisAdvAsmProxy_.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);
		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(),
				AdvAsmProxyMagicClassAdvAsmBuilder.dumpMagic(UserAutoIncrementJdbcRepositoryMagicBuilder.class, expectedClazz.getName(),
						UserAutoIncrementJdbcRepository.class.getName()));

		assertEquals("Code", codeExpected, codeActual);
	}

//	@Test
//	public void test_ProxyMagic_Build() throws Exception {
//		Class<?> expectedClazz = UsingSimplePojoClassSampleMagicBuilderAdvAsmProxy.class;
//		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);
//		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(),
//				AdvAsmProxyMagicClassAdvAsmBuilder.dumpMagic(UsingSimplePojoClassSampleMagicBuilder.class, expectedClazz.getName()));
//
//		assertEquals("Code", codeExpected, codeActual);
//	}

	@Test
	public void test_UserAutoIncrementJdbcRepositoryMagicBuilder_build() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UserAutoIncrementJdbcRepositoryMagicBuilder.dump());

		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void UserMoreComplexAutoIncrementJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserMoreComplexAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);

	}

	@Test
	public void test_UserAutoIncrementJdbcRepository_dumpTinyAsm_compare() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));
		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UserAutoIncrementJdbcRepositoryTinyAsmDump2.dump());

		assertEquals("Code", codeExpected, codeActual);

	}

	@Test
	public void test_UsingUserExtendJdbcRowMapperMagicBuilder_build() throws Exception {
		Class<?> expectedClazz = UsingUserExtendJdbcRowMapper.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), UsingUserExtendJdbcRowMapperMagicBuilder.dump());

		assertEquals("Code", codeExpected, codeActual);
	}

}
