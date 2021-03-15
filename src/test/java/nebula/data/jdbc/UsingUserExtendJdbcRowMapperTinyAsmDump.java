package nebula.data.jdbc;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.MethodCode;
public class UsingUserExtendJdbcRowMapperTinyAsmDump {

	public static byte[] dump () throws Exception {
		return new UsingUserExtendJdbcRowMapperTinyAsmDump().dump("nebula.data.jdbc.UsingUserExtendJdbcRowMapper");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className)
			.access(ACC_PUBLIC | ACC_SUPER).body();

		__init_(classBody);
		_test(classBody);

		return classBody.end().toByteArray();
	}

	protected void __init_(ClassBody classBody) {
		MethodCode code = classBody.public_().method("<init>").begin();

		code.LINE();
		code.LOAD("this");
		code.SPECIAL(Object.class, "<init>").INVOKE();
		code.RETURN();

		code.END();
	}

	protected void _test(ClassBody classBody) {
		MethodCode code = classBody.public_().method("test")
			.return_(String.class )
			.throws_(SQLException.class )
			.parameter("resultSet",ResultSet.class).begin();

		code.LINE();
		code.NEW(UserExtendJdbcRowMapper.class);
		code.DUP();
		code.SPECIAL(UserExtendJdbcRowMapper.class, "<init>").INVOKE();
		code.STORE("mapper",UserExtendJdbcRowMapper.class);

		code.LINE();
		code.LOAD("mapper");
		code.LOAD("resultSet");
		code.VIRTUAL(UserExtendJdbcRowMapper.class, "map")
			.return_(UserExtend.class)
			.parameter(ResultSet.class).INVOKE();
		code.STORE("userExtend",UserExtend.class);

		code.LINE();
		code.LOAD("userExtend");
		code.VIRTUAL(UserExtend.class, "getName")
			.return_(String.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

}
