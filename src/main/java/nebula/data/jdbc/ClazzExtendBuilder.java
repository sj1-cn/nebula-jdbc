package nebula.data.jdbc;

import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.Field;

public class ClazzExtendBuilder {
	
	public byte[] make(String clazzExtend, String targetClazz, EntityDefinition clazz) {
		// public class UserImpl extends User implements EntityTime {
		ClassBody cw = ClassBuilder.class_(clazzExtend).extends_(targetClazz).implements_(ClassExtend.class).body();

		cw.private_().field("createAt", java.sql.Timestamp.class);
		cw.private_().field("updateAt", java.sql.Timestamp.class);

		cw.public_().method("<init>").parameter(clazz.fieldsAll.map(f -> new Field(f.fieldName, f.clazz))).code(mv -> {
			mv.LINE();
			mv.LOAD(0);
			for (FieldMapper fieldMapper : clazz.fields) {
				mv.LOAD(fieldMapper.fieldName);
			}
			Class<?>[] clazzes = new Class<?>[clazz.fields.size()];
			for (int i = 0; i < clazz.fields.size(); i++) {
				clazzes[i] = clazz.fields.get(i).clazz;
			}
			mv.SPECIAL(targetClazz, "<init>").parameter(clazzes).INVOKE();
			mv.LINE();
			mv.LOAD_THIS();
			mv.LOAD("createAt");
			mv.PUTFIELD_OF_THIS("createAt");
			
			mv.LINE();
			mv.LOAD_THIS();
			mv.LOAD("updateAt");
			mv.PUTFIELD_OF_THIS("updateAt");
			
			mv.LINE();
			mv.RETURN();
		});

		cw.makeAllPropertyGet();

		return cw.end().toByteArray();
	}
}
