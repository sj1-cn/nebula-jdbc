package nebula.data.jdbc;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

import java.sql.ResultSet;
import java.sql.SQLException;

import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;
import nebula.tinyasm.ClassBody;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.MethodCode;

public class JdbcRowMapperBuilder {
	Arguments arguments;

	public JdbcRowMapperBuilder(Arguments arguments) {
		this.arguments = arguments;
	}

	public byte[] make(String clazzRowMapper, String clazzExtend, FieldList maps) {

		ClassBody cw = ClassBuilder.make(clazzRowMapper).implement(JdbcRowMapper.class, clazzExtend).body();

		cw.constructerEmpty();
		{
			cw.method("map").parameter("rs", ResultSet.class).reTurn(clazzExtend).tHrow(SQLException.class).code(mv -> {
				mv.LINE();
				mv.NEW(clazzExtend);
				mv.DUP();
				Class<?>[] clazzes = new Class<?>[maps.size()];
				for (int i = 0; i < maps.size(); i++) {
					FieldMapper fieldMapper = maps.get(i);
					String name = fieldMapper.fieldName;
					JdbcMapping jdbcMapping = JDBC.map(fieldMapper.clazz);
					assert jdbcMapping != null : name + "'s type [" + fieldMapper.clazz.getName() + "] has not jdbc type";
					String getname = jdbcMapping.getname;
					Class<?> jdbcClazz = jdbcMapping.jdbcClazz;
					Class<?> pojoClazz = fieldMapper.clazz;
					clazzes[i] = pojoClazz;

					map(mv, name, getname, pojoClazz, jdbcClazz);
				}
				mv.SPECIAL(clazzExtend, "<init>").parameter(clazzes).INVOKE();
				mv.LINE();
				mv.RETURNTop();
			});
		}
		{
			cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "map")
				.parameter("rs", ResultSet.class)
				.reTurn(Object.class)
				.tHrow(SQLException.class)
				.code(mv -> {
					mv.LINE();
					mv.LOAD(0);
					mv.LOAD(1);
					mv.VIRTUAL(clazzRowMapper, "map").parameter(ResultSet.class).reTurn(clazzExtend).INVOKE();
					mv.RETURNTop();
				});
		}

		return cw.end().toByteArray();
	}

	private void map(MethodCode mv, String name, String jdbcFuncName, Class<?> pojoClazz, Class<?> jdbcClazz) {
		{
			mv.LOAD("rs");
			mv.LOADConst(name);
			mv.LINE();
			mv.INTERFACE(ResultSet.class, jdbcFuncName).parameter(String.class).reTurn(jdbcClazz).INVOKE();

			arguments.fromJdbcClazz(pojoClazz, jdbcClazz).accept(mv);

		}
	}
}
