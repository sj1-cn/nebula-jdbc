package nebula.data.jdbc;

import nebula.tinyasm.ClassBody;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.Field;

public class ClazzExtendBuilder {
	
	public byte[] make(String clazzExtend, String targetClazz, ClazzDefinition clazz) {
		// public class UserImpl extends User implements EntityTime {
		ClassBody cw = ClassBuilder.make(clazzExtend).subclass(targetClazz).implement(ClassExtend.class).body();

		cw.field("createAt", java.sql.Timestamp.class);
		cw.field("updateAt", java.sql.Timestamp.class);

		cw.publicMethod("<init>").parameter(clazz.fieldsAll.map(f -> new Field(f.fieldName, f.clazz))).code(mv -> {
			mv.line();
			mv.LOAD(0);
			for (FieldMapper fieldMapper : clazz.fields) {
				mv.load(fieldMapper.fieldName);
			}
			Class<?>[] clazzes = new Class<?>[clazz.fields.size()];
			for (int i = 0; i < clazz.fields.size(); i++) {
				clazzes[i] = clazz.fields.get(i).clazz;
			}
			mv.SPECIAL(targetClazz, "<init>").parameter(clazzes).INVOKE();
			mv.line().putThisFieldWithVar("createAt", "createAt");
			mv.line().putThisFieldWithVar("updateAt", "updateAt");
			mv.line().returnVoid();
		});

		cw.makeAllPropertyGet();

		return cw.end().toByteArray();
	}
}
