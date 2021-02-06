package nebula.data.jdbc;

import static nebula.tinyasm.GenericClazz.*;
import static nebula.tinyasm.util.TypeUtils.typeOf;

import org.objectweb.asm.Type;

import nebula.tinyasm.ClassBody;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.Field;

public class ClazzExtendBuilder {
	
	public byte[] make(String clazzExtend, String targetClazz, ClazzDefinition clazz) {
		// public class UserImpl extends User implements EntityTime {
		ClassBody cw = ClassBuilder.make(clazzExtend).subclass(targetClazz).implement(ClassExtend.class).body();

		cw.field("createAt", java.sql.Timestamp.class);
		cw.field("updateAt", java.sql.Timestamp.class);

		cw.publicMethod("<init>").parameter(clazz.fieldsAll.map(f -> new Field(f.fieldName, generic(f.clazz)))).code(mv -> {
			mv.line();
			mv.LOAD(0);
			for (FieldMapper fieldMapper : clazz.fields) {
				mv.load(fieldMapper.fieldName);
			}
			mv.INVOKESPECIAL(typeOf(targetClazz), Type.VOID_TYPE, "<init>", clazz.fields.map(f -> typeOf(f.clazz)).toArray(new Type[0]));
			mv.line().putThisFieldWithVar("createAt", "createAt");
			mv.line().putThisFieldWithVar("updateAt", "updateAt");
			mv.line().returnVoid();
		});

		cw.makeAllPropertyGet();

		return cw.end().toByteArray();
	}
}
