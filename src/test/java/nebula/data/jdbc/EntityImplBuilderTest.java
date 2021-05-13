package nebula.data.jdbc;

import static cn.sj1.tinyasm.tools.TinyAsmTestUtils.dumpTinyAsm;
import static nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.BIT;
import static nebula.jdbc.builders.schema.ColumnDefinition.BOOLEAN;
import static nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static nebula.jdbc.builders.schema.ColumnDefinition.DATE;
import static nebula.jdbc.builders.schema.ColumnDefinition.DOUBLE;
import static nebula.jdbc.builders.schema.ColumnDefinition.FLOAT;
import static nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static nebula.jdbc.builders.schema.ColumnDefinition.NUMERIC;
import static nebula.jdbc.builders.schema.ColumnDefinition.SMALLINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIME;
import static nebula.jdbc.builders.schema.ColumnDefinition.TIMESTAMP;
import static nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.sj1.tinyasm.tools.TinyAsmTestUtils;
import nebula.data.jdbc.sample.User;
import nebula.data.jdbc.sample.UserMoreComplex;
import nebula.data.jdbc.sample.UserMoreComplexExtend;
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

		clazzFields.push(new PojoFieldJdbcMapper("id", "getId", long.class, INTEGER("ID")));
		clazzFields.push(new PojoFieldJdbcMapper("name", "getName", String.class, VARCHAR("NAME")));
		clazzFields.push(new PojoFieldJdbcMapper("description", "getDescription", String.class, VARCHAR("description")));

		EntityPojoDbMappingDefinitions clazzDefinition = new EntityPojoDbMappingDefinitions(User.class.getSimpleName(), User.class.getName(), User.class.getSimpleName().toUpperCase(), clazzFields);

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
		clazzFields.push(new PojoFieldJdbcMapper("id", "getId", long.class, BIGINT("id").primarykey().autoIncrement()));
		clazzFields.push(new PojoFieldJdbcMapper("string", "getString", String.class, VARCHAR("string")));
		clazzFields.push(new PojoFieldJdbcMapper("bigDecimal", "getBigDecimal", java.math.BigDecimal.class, NUMERIC("bigDecimal")));
		clazzFields.push(new PojoFieldJdbcMapper("z", "getZ", Boolean.class, BOOLEAN("z")));
		clazzFields.push(new PojoFieldJdbcMapper("c", "getC", Character.class, CHAR("c")));
		clazzFields.push(new PojoFieldJdbcMapper("b", "getB", Byte.class, BIT("b")));
		clazzFields.push(new PojoFieldJdbcMapper("s", "getS", Short.class, SMALLINT("s")));
		clazzFields.push(new PojoFieldJdbcMapper("i", "getI", Integer.class, INTEGER("i")));
		clazzFields.push(new PojoFieldJdbcMapper("l", "getL", Long.class, BIGINT("l")));
		clazzFields.push(new PojoFieldJdbcMapper("f", "getF", Float.class, FLOAT("f")));
		clazzFields.push(new PojoFieldJdbcMapper("d", "getD", Double.class, DOUBLE("d")));
		clazzFields.push(new PojoFieldJdbcMapper("date", "getDate", java.sql.Date.class, DATE("date")));
		clazzFields.push(new PojoFieldJdbcMapper("time", "getTime", java.sql.Time.class, TIME("time")));
		clazzFields.push(new PojoFieldJdbcMapper("timestamp", "getTimestamp", java.sql.Timestamp.class, TIMESTAMP("timestamp")));

		String targetClazz = UserMoreComplex.class.getName();
		String clazzExtend = targetClazz + "Extend";
		EntityPojoDbMappingDefinitions clazzDefinition = new EntityPojoDbMappingDefinitions(UserMoreComplex.class.getSimpleName(),UserMoreComplex.class.getName(), UserMoreComplex.class.getSimpleName().toUpperCase(),
				clazzFields);

		byte[] code = builder.make(clazzExtend, targetClazz, clazzDefinition);

		String codeActual = toString(targetClazz, code);
		String codeExpected = TinyAsmTestUtils.toString(UserMoreComplexExtend.class.getName(), dumpTinyAsm(UserMoreComplexExtend.class));
		assertEquals("Code", codeExpected, codeActual);
	}
}
