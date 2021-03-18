package nebula.data.jdbc;
import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import cn.sj1.tinyasm.ClassBody;
import cn.sj1.tinyasm.ClassBuilder;
import cn.sj1.tinyasm.MethodCode;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Opcodes.*;
import cn.sj1.tinyasm.Annotation;
import cn.sj1.tinyasm.Clazz;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.lang.Object;
import java.lang.String;
import nebula.data.jdbc.UserExtendJdbcRowMapper;
import nebula.data.jdbc.UserExtend;
@SuppressWarnings("unused")
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
