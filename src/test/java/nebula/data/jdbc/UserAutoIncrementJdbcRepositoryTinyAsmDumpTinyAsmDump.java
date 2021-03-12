package nebula.data.jdbc;
import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.MethodCode;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Opcodes.*;
import cc1sj.tinyasm.Annotation;
import cc1sj.tinyasm.Clazz;
import cc1sj.tinyasm.ClassHeader;
import cc1sj.tinyasm.ClassBody;
import org.objectweb.asm.Label;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.AfterModifier;
import java.lang.Integer;
import java.lang.Exception;
import cc1sj.tinyasm.ClazzSimple;
import cc1sj.tinyasm.MethodCode;
import cc1sj.tinyasm.MethodCaller;
import java.lang.Long;
import cc1sj.tinyasm.MethodHeader;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Class;
import java.lang.String;
import nebula.data.jdbc.UserAutoIncrementJdbcRepositoryTinyAsmDump;
import cc1sj.tinyasm.Clazz;
@SuppressWarnings("unused")
public class UserAutoIncrementJdbcRepositoryTinyAsmDumpTinyAsmDump {

	public static byte[] dump () throws Exception {
		return new UserAutoIncrementJdbcRepositoryTinyAsmDumpTinyAsmDump().dump("nebula.data.jdbc.UserAutoIncrementJdbcRepositoryTinyAsmDump");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className)
			.access(ACC_PUBLIC | ACC_SUPER).body();

		__init_(classBody);
		_dump(classBody);
		_dump_String(classBody);
		___init_(classBody);
		__setConnection(classBody);
		__initJdbc(classBody);
		__listJdbc(classBody);
		__listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(classBody);
		__findByIdJdbc(classBody);
		__insertJdbc(classBody);
		__updateJdbc(classBody);
		__deleteByIdJdbc(classBody);
		__bridge_updateJdbc(classBody);
		__bridge_insertJdbc(classBody);
		__bridge_findByIdJdbc(classBody);

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

	protected void _dump(ClassBody classBody) {
		MethodCode code = classBody.staticMethod(ACC_PUBLIC | ACC_STATIC, "dump")
			.return_(byte[].class )
			.throws_(Exception.class ).begin();

		code.LINE();
		code.NEW(UserAutoIncrementJdbcRepositoryTinyAsmDump.class);
		code.DUP();
		code.SPECIAL("<init>").INVOKE();
		code.LOADConst("nebula.data.jdbc.UserAutoIncrementJdbcRepository");
		code.VIRTUAL("dump")
			.return_(byte[].class)
			.parameter(String.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void _dump_String(ClassBody classBody) {
		MethodCode code = classBody.public_().method("dump")
			.return_(byte[].class )
			.throws_(Exception.class )
			.parameter("className",String.class).begin();

		code.LINE();
		code.LOAD("className");
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/JdbcRepository;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(ClassBuilder.class, "class_")
			.return_(ClassHeader.class)
			.parameter(String.class)
			.parameter(Clazz.class)
			.parameter(Clazz[].class).INVOKE();

		code.LINE();
		code.LOADConst(33);
		code.INTERFACE(ClassHeader.class, "access")
			.return_(Object.class)
			.parameter(int.class).INVOKE();
		code.CHECKCAST(ClassHeader.class);
		code.INTERFACE(ClassHeader.class, "body")
			.return_(ClassBody.class).INVOKE();

		code.LINE();
		code.STORE("classBody",ClassBody.class);

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "private_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("conn");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(AfterModifier.class, "field")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "private_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("mapper");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(AfterModifier.class, "field")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "private_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("sqlHelper");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(AfterModifier.class, "field")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("__init_")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_setConnection")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_initJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_listJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_findByIdJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_insertJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_updateJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_deleteByIdJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_bridge_updateJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_bridge_insertJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("classBody");
		code.VIRTUAL("_bridge_findByIdJdbc")
			.parameter(ClassBody.class).INVOKE();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "end")
			.return_(ClassBuilder.class).INVOKE();
		code.INTERFACE(ClassBuilder.class, "toByteArray")
			.return_(byte[].class).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void ___init_(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("__init_")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("<init>");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("mapper");
		code.VIRTUAL(MethodCode.class, "PUTFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlHelper");
		code.VIRTUAL(MethodCode.class, "PUTFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURN").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __setConnection(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_setConnection")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("setConnection");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst("conn");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "PUTFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURN").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __initJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_initJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("initJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id INTEGER(10) PRIMARY KEY AUTO_INCREMENT");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("addColumn");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("name VARCHAR(256)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("addColumn");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("description VARCHAR(256)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("addColumn");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("createAt TIMESTAMP");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("addColumn");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("updateAt TIMESTAMP");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.LOADConst("addColumn");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("USER");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("columnList");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("mergeIfExists");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/schema/ColumnList;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label7OfIFNE",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfIFNE");
		code.VIRTUAL(MethodCode.class, "IFNE")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("execute");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfIFNE");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURN").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __listJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_listJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("listJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Clazz.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("start");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);

		code.LINE();
		code.LOADConst("max");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageListImpl;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("start");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageListImpl;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlHelper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.LOADConst("select");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("USER");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("from");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("start");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("offset");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("toSQL");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sql");
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sql");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeQuery");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label6OfGOTO",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label6OfGOTO");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label4OfIFEQ",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label4OfIFEQ");
		code.VIRTUAL(MethodCode.class, "IFEQ")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("mapper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.LOADConst("map");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtend;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst("add");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label6OfGOTO");
		code.VIRTUAL(MethodCode.class, "GOTO")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label4OfIFEQ");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("close");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlHelper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("count(1)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.LOADConst("select");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("USER");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("from");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("toSQL");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlCount");
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("createStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/Statement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Statement;"));
		code.LOADConst("executeQuery");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("result");
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("getInt");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("totalSize");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("close");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("totalSize");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst("totalSize");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("listJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Clazz.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("condition");
		code.LOADConst(Type.getType("Lnebula/data/query/Condition;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);

		code.LINE();
		code.LOADConst("orderBy");
		code.LOADConst(Type.getType("Lnebula/data/query/OrderBy;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);

		code.LINE();
		code.LOADConst("start");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);

		code.LINE();
		code.LOADConst("max");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageListImpl;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("start");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageListImpl;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlHelper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.LOADConst("select");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("USER");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("from");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("condition");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("where");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/query/Condition;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("orderBy");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("orderby");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/query/OrderBy;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("start");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("offset");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("max");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("toSQL");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sql");
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sql");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeQuery");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label6OfGOTO",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label6OfGOTO");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label4OfIFEQ",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label4OfIFEQ");
		code.VIRTUAL(MethodCode.class, "IFEQ")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("mapper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.LOADConst("map");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtend;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst("add");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label6OfGOTO");
		code.VIRTUAL(MethodCode.class, "GOTO")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label4OfIFEQ");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("close");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlHelper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("count(1)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/SqlHelper;"));
		code.LOADConst("select");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("USER");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("from");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("condition");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("where");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/query/Condition;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/jdbc/builders/queries/Select;"));
		code.LOADConst("toSQL");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlCount");
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("createStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/Statement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("sqlCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Statement;"));
		code.LOADConst("executeQuery");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("result");
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("getInt");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("totalSize");
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSetCount");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("close");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("totalSize");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/PageList;"));
		code.LOADConst("totalSize");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_findByIdJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("findByIdJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("id");
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/util/ArrayList;"));
		code.VIRTUAL(MethodCode.class, "NEW")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "DUP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/util/ArrayList;"));
		code.LOADConst("<init>");
		code.VIRTUAL(MethodCode.class, "SPECIAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.LOADConst(Type.getType("Ljava/util/List;"));
		code.LOADConst(1);
		code.NEWARRAY(Clazz.class);
		code.DUP();
		code.LOADConst(0);
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.STATIC(Clazz.class, "of")
			.return_(ClazzSimple.class)
			.parameter(Class.class).INVOKE();
		code.ARRAYSTORE();
		code.STATIC(Clazz.class, "of")
			.return_(Clazz.class)
			.parameter(Class.class)
			.parameter(Clazz[].class).INVOKE();
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Clazz.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setLong");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeQuery");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label7OfGOTO",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfGOTO");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label5OfIFEQ",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label5OfIFEQ");
		code.VIRTUAL(MethodCode.class, "IFEQ")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("mapper");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtendJdbcRowMapper;"));
		code.LOADConst("map");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/UserExtend;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/util/List;"));
		code.LOADConst("add");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfGOTO");
		code.VIRTUAL(MethodCode.class, "GOTO")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label5OfIFEQ");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("datas");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(0);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/util/List;"));
		code.LOADConst("get");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.VIRTUAL(MethodCode.class, "CHECKCAST")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_insertJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("insertJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("data");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LOADConstNULL").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(0);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("result");
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("INSERT INTO USER(name,description,createAt,updateAt) VALUES(?,?,?,?)");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getName");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setString");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(2);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getDescription");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setString");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(3);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("bindInsertExtend");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeUpdate");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label7OfIFLE",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfIFLE");
		code.VIRTUAL(MethodCode.class, "IFLE")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("getGeneratedKeys");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("next");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Boolean.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("result");
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("resultSet");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/ResultSet;"));
		code.LOADConst("getLong");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("findByIdJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label7OfIFLE");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LOADConstNULL").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_updateJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("updateJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("data");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getId");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("findByIdJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/ClassExtend;"));
		code.VIRTUAL(MethodCode.class, "CHECKCAST")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("extend");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/ClassExtend;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("extend");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/ClassExtend;"));
		code.LOADConst("getUpdateAt");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/Timestamp;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/ClassExtend;"));
		code.VIRTUAL(MethodCode.class, "CHECKCAST")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/ClassExtend;"));
		code.LOADConst("getUpdateAt");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/Timestamp;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label2OfIF_ACMPNE",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label2OfIF_ACMPNE");
		code.VIRTUAL(MethodCode.class, "IF_ACMPNE")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LOADConstNULL").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label2OfIF_ACMPNE");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getName");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setString");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(2);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getDescription");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setString");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(3);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("bindUpdateExtend");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "POP").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(4);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getId");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setLong");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeUpdate");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.NEW(Label.class);
		code.DUP();
		code.SPECIAL(Label.class, "<init>").INVOKE();
		code.STORE("label9OfIFLE",Label.class);

		code.LINE();
		code.LOAD("code");
		code.LOAD("label9OfIFLE");
		code.VIRTUAL(MethodCode.class, "IFLE")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.LOADConst("getId");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("findByIdJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOAD("label9OfIFLE");
		code.VIRTUAL(MethodCode.class, "visitLabel")
			.parameter(Label.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LOADConstNULL").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __deleteByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_deleteByIdJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.INTERFACE(ClassBody.class, "public_")
			.return_(AfterModifier.class).INVOKE();
		code.LOADConst("deleteByIdJdbc");
		code.INTERFACE(AfterModifier.class, "method")
			.return_(MethodHeader.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst("id");
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("conn");
		code.VIRTUAL(MethodCode.class, "GETFIELD_OF_THIS")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("DELETE USER WHERE id=?");
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/Connection;"));
		code.LOADConst("prepareStatement");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/String;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.VIRTUAL(MethodCode.class, "STORE")
			.return_(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(1);
		code.VIRTUAL(MethodCode.class, "LOADConst")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("setLong");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("preparedStatement");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Ljava/sql/PreparedStatement;"));
		code.LOADConst("executeUpdate");
		code.VIRTUAL(MethodCode.class, "INTERFACE")
			.return_(MethodCaller.class)
			.parameter(Class.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Integer.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __bridge_updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_bridge_updateJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.LOADConst(4161);
		code.LOADConst("updateJdbc");
		code.INTERFACE(ClassBody.class, "method")
			.return_(MethodHeader.class)
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(4096);
		code.LOADConst("data");
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.VIRTUAL(MethodCode.class, "CHECKCAST")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("updateJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __bridge_insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_bridge_insertJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.LOADConst(4161);
		code.LOADConst("insertJdbc");
		code.INTERFACE(ClassBody.class, "method")
			.return_(MethodHeader.class)
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(4096);
		code.LOADConst("data");
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("data");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.VIRTUAL(MethodCode.class, "CHECKCAST")
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("insertJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __bridge_findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.protected_().method("_bridge_findByIdJdbc")
			.parameter("classBody",ClassBody.class).begin();

		code.LINE();
		code.LOAD("classBody");
		code.LOADConst(4161);
		code.LOADConst("findByIdJdbc");
		code.INTERFACE(ClassBody.class, "method")
			.return_(MethodHeader.class)
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/lang/Object;"));
		code.INTERFACE(MethodHeader.class, "return_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Ljava/sql/SQLException;"));
		code.INTERFACE(MethodHeader.class, "throws_")
			.return_(MethodHeader.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.LOADConst(4096);
		code.LOADConst("id");
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodHeader.class, "parameter")
			.return_(Object.class)
			.parameter(int.class)
			.parameter(String.class)
			.parameter(Class.class).INVOKE();
		code.CHECKCAST(MethodHeader.class);
		code.INTERFACE(MethodHeader.class, "begin")
			.return_(MethodCode.class).INVOKE();

		code.LINE();
		code.STORE("code",MethodCode.class);

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "LINE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("this");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("id");
		code.VIRTUAL(MethodCode.class, "LOAD")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("code");
		code.LOADConst("findByIdJdbc");
		code.VIRTUAL(MethodCode.class, "VIRTUAL")
			.return_(MethodCaller.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOADConst(Type.getType("Lnebula/data/jdbc/User;"));
		code.INTERFACE(MethodCaller.class, "return_")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();

		code.LINE();
		code.GETSTATIC(Long.class, "TYPE", Class.class);
		code.INTERFACE(MethodCaller.class, "parameter")
			.return_(MethodCaller.class)
			.parameter(Class.class).INVOKE();
		code.INTERFACE(MethodCaller.class, "INVOKE").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "RETURNTop").INVOKE();

		code.LINE();
		code.LOAD("code");
		code.VIRTUAL(MethodCode.class, "END").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

}
