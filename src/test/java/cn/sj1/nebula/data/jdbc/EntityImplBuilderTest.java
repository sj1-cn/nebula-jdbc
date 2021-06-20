package cn.sj1.nebula.data.jdbc;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BIT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BOOLEAN;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DATE;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DOUBLE;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.FLOAT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.NUMERIC;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.SMALLINT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIME;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static cn.sj1.tinyasm.tools.TinyAsmTestUtils.dumpTinyAsm;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.sj1.nebula.data.jdbc.sample.User;
import cn.sj1.nebula.data.jdbc.sample.UserMoreComplex;
import cn.sj1.nebula.data.jdbc.sample.UserMoreComplexExtend;
import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.jdbc.TestBase;

public class EntityImplBuilderTest extends TestBase {

	EntityImplBuilder builder;

	@Before
	public void before() {
		builder = new EntityImplBuilder();
	}

	@After
	public void after() {
	}

//	public void testPrint() throws IOException {
//		System.out.println(RefineCode.refineCode(toString(UserJdbcRowMapper.class), ResultSet.class,
//				PreparedStatement.class, JdbcRepository.class));
//	}

	@Test
	public void testUserImpl() {

		FieldList clazzFields = new FieldList();

		clazzFields.push(new PersistentProperty("id", "getId", long.class, INTEGER("ID")));
		clazzFields.push(new PersistentProperty("name", "getName", String.class, VARCHAR("NAME")));
		clazzFields.push(new PersistentProperty("description", "getDescription", String.class, VARCHAR("description")));

		PersistentEntity clazzDefinition = new PersistentEntity(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName().toUpperCase(), clazzFields);

		String targetClazz = User.class.getName();
		String clazzExtend = targetClazz + "Extend";
		byte[] code = builder.make(clazzExtend, targetClazz, clazzDefinition);

		String codeActual = toString(targetClazz, code);
		String codeExpected = TinyAsmTestUtils.toString(clazzExtend);
		assertEquals("Code", codeExpected, codeActual);
	}

	@Test
	public void testUserMoreComplexJdbcRowMapper() {
		FieldList clazzFields = new FieldList();
		clazzFields.push(new PersistentProperty("id", "getId", long.class, BIGINT("id").primarykey().autoIncrement()));
		clazzFields.push(new PersistentProperty("string", "getString", String.class, VARCHAR("string")));
		clazzFields.push(new PersistentProperty("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, NUMERIC("bigDecimal")));
		clazzFields.push(new PersistentProperty("z", "getZ", Boolean.class, BOOLEAN("z")));
		clazzFields.push(new PersistentProperty("c", "getC", Character.class, CHAR("c")));
		clazzFields.push(new PersistentProperty("b", "getB", Byte.class, BIT("b")));
		clazzFields.push(new PersistentProperty("s", "getS", Short.class, SMALLINT("s")));
		clazzFields.push(new PersistentProperty("i", "getI", Integer.class, INTEGER("i")));
		clazzFields.push(new PersistentProperty("l", "getL", Long.class, BIGINT("l")));
		clazzFields.push(new PersistentProperty("f", "getF", Float.class, FLOAT("f")));
		clazzFields.push(new PersistentProperty("d", "getD", Double.class, DOUBLE("d")));
		clazzFields.push(new PersistentProperty("date", "getDate", java.sql.Date.class, DATE("date")));
		clazzFields.push(new PersistentProperty("time", "getTime", java.sql.Time.class, TIME("time")));
		clazzFields.push(new PersistentProperty("timestamp", "getTimestamp", java.sql.Timestamp.class, TIMESTAMP("timestamp")));

		String targetClazz = UserMoreComplex.class.getName();
		String clazzExtend = targetClazz + "Extend";
		PersistentEntity clazzDefinition = new PersistentEntity(UserMoreComplex.class.getSimpleName(),UserMoreComplex.class.getName(), UserMoreComplex.class.getSimpleName().toUpperCase(),
				clazzFields);

		byte[] code = builder.make(clazzExtend, targetClazz, clazzDefinition);

		String codeActual = toString(targetClazz, code);
		String codeExpected = TinyAsmTestUtils.toString(UserMoreComplexExtend.class.getName(), dumpTinyAsm(UserMoreComplexExtend.class));
		assertEquals("Code", codeExpected, codeActual);
	}
}
