package nebula.data.jdbc;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;

import java.sql.Timestamp;

import cn.sj1.tinyasm.core.ClassBody;
import cn.sj1.tinyasm.core.ClassBuilder;
import cn.sj1.tinyasm.core.Clazz;
import cn.sj1.tinyasm.core.Field;
import cn.sj1.tinyasm.core.MethodCode;

public class ClazzExtendBuilder {
	EntityDefinition clazz;
	String clazzExtend;
	String targetClazz;
//	public byte[] make(String clazzExtend, String targetClazz, EntityDefinition clazz) {
//		this.clazzExtend = clazzExtend;
//		this.targetClazz = targetClazz;
//		this.clazz = clazz;
//		// public class UserImpl extends User implements EntityTime {
//		ClassBody cw = ClassBuilder.class_(clazzExtend).extends_(targetClazz).implements_(ClassExtend.class).body();
//
//		cw.private_().field("createAt", java.sql.Timestamp.class);
//		cw.private_().field("updateAt", java.sql.Timestamp.class);
//
//		cw.public_().method("<init>").parameter(clazz.fieldsAll.map(f -> new Field(f.fieldName, f.clazz))).code(mv -> {
//			mv.LINE();
//			mv.LOAD(0);
//			for (FieldMapper fieldMapper : clazz.fields) {
//				mv.LOAD(fieldMapper.fieldName);
//			}
//			Class<?>[] clazzes = new Class<?>[clazz.fields.size()];
//			for (int i = 0; i < clazz.fields.size(); i++) {
//				clazzes[i] = clazz.fields.get(i).clazz;
//			}
//			mv.SPECIAL(targetClazz, "<init>").parameter(clazzes).INVOKE();
//			mv.LINE();
//			mv.LOAD_THIS();
//			mv.LOAD("createAt");
//			mv.PUTFIELD_OF_THIS("createAt");
//			
//			mv.LINE();
//			mv.LOAD_THIS();
//			mv.LOAD("updateAt");
//			mv.PUTFIELD_OF_THIS("updateAt");
//			
//			mv.LINE();
//			mv.RETURN();
//		});
//		
//		cw.constructerEmpty();
//
//		cw.makeAllPropertyGet();
//
//		return cw.end().toByteArray();
//	}

	public byte[] make(String clazzExtend, String targetClazz, EntityDefinition clazz)  {
		this.clazzExtend = clazzExtend;
		this.targetClazz = targetClazz;
		this.clazz = clazz;
		ClassBody classBody = ClassBuilder.class_(clazzExtend).extends_(targetClazz).implements_(ClassExtend.class).body();

		classBody.private_().field("createAt", Clazz.of(Timestamp.class));
		classBody.private_().field("updateAt", Clazz.of(Timestamp.class));
		__init_(classBody);
		__init__long_String_String_javasqlTimestamp_javasqlTimestamp_void(classBody);
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
		code.SPECIAL(targetClazz, "<init>").INVOKE();

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void __init__long_String_String_javasqlTimestamp_javasqlTimestamp_void(ClassBody classBody) {
		MethodCode code = classBody.public_().method("<init>").parameter(clazz.fieldsAll.map(f -> new Field(f.fieldName, f.clazz))).begin();

		code.LINE();
		code.LOAD("this");
		
		for (FieldMapper fieldMapper : clazz.fields) {
			code.LOAD(fieldMapper.fieldName);
		}
		Class<?>[] clazzes = new Class<?>[clazz.fields.size()];
		for (int i = 0; i < clazz.fields.size(); i++) {
			clazzes[i] = clazz.fields.get(i).clazz;
		}
		
		code.SPECIAL(targetClazz, "<init>").parameter(clazzes).INVOKE();

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
