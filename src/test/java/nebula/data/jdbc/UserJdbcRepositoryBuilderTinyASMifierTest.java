package nebula.data.jdbc;

import static cn.sj1.tinyasm.tools.TinyAsmTestUtils.dumpTinyAsm;
import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.VARCHAR;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cn.sj1.tinyasm.core.AdvAsmProxyMagicClassAdvAsmBuilder;
import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.jdbc.builders.schema.ColumnDefinition;

public class UserJdbcRepositoryBuilderTinyASMifierTest {

	@Test
	public void test_UserJdbcRepository_dumpTinyAsm() throws Exception {
		Class<?> expectedClazz = UserJdbcRepository.class;
		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), dumpTinyAsm(expectedClazz));

		assertEquals("Code", codeExpected, codeActual);

	}

	@Test
	public void test_UserJdbcRepository_build() throws Exception {
		Class<?> expectedClazz = UserJdbcRepository.class;

		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER)));
		clazzFields.push(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, new ColumnDefinition("description", VARCHAR)));
		EntityDefinition entityDefinition = new EntityDefinition(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserJdbcRepository.class.getName();

		byte[] codeRepository = UserJdbcRepositoryTinyAsmBuilder.dump(clazzRepository, User.class, UserExtend.class, entityDefinition);

		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), codeRepository);

		assertEquals("Code", codeExpected, codeActual);
	}
	

	@Test
	public void test_UserAutoIncrementJdbcRepository_build() throws Exception {
		Class<?> expectedClazz = UserAutoIncrementJdbcRepository.class;

		FieldList clazzFields = new FieldList();
		clazzFields.push(new FieldMapper(true, "id", "getId", long.class, new ColumnDefinition("id", INTEGER)));
		clazzFields.push(new FieldMapper("name", "getName", String.class, new ColumnDefinition("name", VARCHAR)));
		clazzFields.push(new FieldMapper("description", "getDescription", String.class, new ColumnDefinition("description", VARCHAR)));
		EntityDefinition entityDefinition = new EntityDefinition(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName(), clazzFields);

		String clazzRepository = UserAutoIncrementJdbcRepository.class.getName();

		byte[] codeRepository = UserJdbcRepositoryTinyAsmBuilder.dump(clazzRepository, User.class, UserExtend.class, entityDefinition);

		String codeExpected = TinyAsmTestUtils.toString(expectedClazz);

		String codeActual = TinyAsmTestUtils.toString(expectedClazz.getName(), codeRepository);

		assertEquals("Code", codeExpected, codeActual);
	}

}
