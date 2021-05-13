package nebula.data.jdbc;
import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import cn.sj1.tinyasm.core.ClassBody;
import cn.sj1.tinyasm.core.ClassBuilder;
import cn.sj1.tinyasm.core.MethodCode;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Opcodes.*;
import cn.sj1.tinyasm.core.Annotation;
import cn.sj1.tinyasm.core.Clazz;
import java.lang.Float;
import java.sql.Time;
import java.lang.Double;
import java.sql.Timestamp;
import java.lang.Integer;
import java.sql.Date;
import java.math.BigDecimal;
import java.lang.Character;
import java.lang.Long;
import nebula.data.jdbc.UserMoreComplex;
import java.lang.Short;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Byte;
import nebula.data.jdbc.EntitySystem;
@SuppressWarnings("unused")
public class UserMoreComplexExtendTinyAsmDump {

	public static byte[] dump () throws Exception {
		return new UserMoreComplexExtendTinyAsmDump().dump("nebula.data.jdbc.UserMoreComplexExtend");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className, UserMoreComplex.class, EntitySystem.class)
			.access(ACC_PUBLIC | ACC_SUPER).body();

		classBody.private_().field("createAt", Clazz.of(Timestamp.class));
		classBody.private_().field("updateAt", Clazz.of(Timestamp.class));
		__init_(classBody);
		__init__Long_String_javamathBigDecimal_Boolean_Character_Byte_Short_Integer_Long_Float_Double_javasqlDate_javasqlTime_javasqlTimestamp_javasqlTimestamp_javasqlTimestamp_void(classBody);
		_getCreateAt(classBody);
		_setCreateAt(classBody);
		_getUpdateAt(classBody);
		_setUpdateAt(classBody);

		return classBody.end().toByteArray();
	}

	protected void __init_(ClassBody classBody) {
		MethodCode code = classBody.public_().method("<init>").begin();

		code.LINE();
		code.LOAD("this");
		code.SPECIAL(UserMoreComplex.class, "<init>").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __init__Long_String_javamathBigDecimal_Boolean_Character_Byte_Short_Integer_Long_Float_Double_javasqlDate_javasqlTime_javasqlTimestamp_javasqlTimestamp_javasqlTimestamp_void(ClassBody classBody) {
		MethodCode code = classBody.public_().method("<init>")
			.parameter("id",Long.class)
			.parameter("string",String.class)
			.parameter("bigDecimal",BigDecimal.class)
			.parameter("z",Boolean.class)
			.parameter("c",Character.class)
			.parameter("b",Byte.class)
			.parameter("s",Short.class)
			.parameter("i",Integer.class)
			.parameter("l",Long.class)
			.parameter("f",Float.class)
			.parameter("d",Double.class)
			.parameter("date",Date.class)
			.parameter("time",Time.class)
			.parameter("timestamp",Timestamp.class)
			.parameter("createAt",Timestamp.class)
			.parameter("updateAt",Timestamp.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("id");
		code.LOAD("string");
		code.LOAD("bigDecimal");
		code.LOAD("z");
		code.LOAD("c");
		code.LOAD("b");
		code.LOAD("s");
		code.LOAD("i");
		code.LOAD("l");
		code.LOAD("f");
		code.LOAD("d");
		code.LOAD("date");
		code.LOAD("time");
		code.LOAD("timestamp");
		code.SPECIAL(UserMoreComplex.class, "<init>")
			.parameter(Long.class)
			.parameter(String.class)
			.parameter(BigDecimal.class)
			.parameter(Boolean.class)
			.parameter(Character.class)
			.parameter(Byte.class)
			.parameter(Short.class)
			.parameter(Integer.class)
			.parameter(Long.class)
			.parameter(Float.class)
			.parameter(Double.class)
			.parameter(Date.class)
			.parameter(Time.class)
			.parameter(Timestamp.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("createAt");
		code.PUTFIELD_OF_THIS("createAt");

		code.LINE();
		code.LOAD("this");
		code.LOAD("updateAt");
		code.PUTFIELD_OF_THIS("updateAt");

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _getCreateAt(ClassBody classBody) {
		MethodCode code = classBody.public_().method("getCreateAt")
			.return_(Timestamp.class ).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("createAt");
		code.RETURNTop();

		code.END();
	}

	protected void _setCreateAt(ClassBody classBody) {
		MethodCode code = classBody.public_().method("setCreateAt")
			.parameter("createAt",Timestamp.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("createAt");
		code.PUTFIELD_OF_THIS("createAt");

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _getUpdateAt(ClassBody classBody) {
		MethodCode code = classBody.public_().method("getUpdateAt")
			.return_(Timestamp.class ).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("updateAt");
		code.RETURNTop();

		code.END();
	}

	protected void _setUpdateAt(ClassBody classBody) {
		MethodCode code = classBody.public_().method("setUpdateAt")
			.parameter("updateAt",Timestamp.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("updateAt");
		code.PUTFIELD_OF_THIS("updateAt");

		code.LINE();
		code.RETURN();

		code.END();
	}

}
