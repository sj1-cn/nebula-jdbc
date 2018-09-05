package nebula.data.jdbc;

import static nebula.tinyasm.util.TypeUtils.typeOf;

import org.objectweb.asm.Type;

import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.data.ClassBody;
import nebula.tinyasm.data.Field;
import nebula.tinyasm.data.GenericClazz;

public class ClazzExtendBuilder {
	
	public byte[] make(String clazzExtend, String targetClazz, FieldList fields) {
		// public class UserImpl extends User implements EntityTime {
		ClassBody cw = ClassBuilder.make(clazzExtend).subclass(targetClazz).implement(ClassExtend.class).body();

		cw.field("createAt", java.sql.Timestamp.class);
		cw.field("updateAt", java.sql.Timestamp.class);

		FieldList all = new FieldList();
		all.push(fields.list());
		FieldList extend = new FieldList();
		extend.push(new FieldMapper("createAt", "getCreateAt", java.sql.Timestamp.class, ColumnDefinition.TIMESTAMP("createAt")));
		extend.push(new FieldMapper("updateAt", "getUpdateAt", java.sql.Timestamp.class, ColumnDefinition.TIMESTAMP("updateAt")));
		all.push(extend.list());

		cw.publicMethod("<init>").parameter(all.map(f -> new Field(f.fieldName, GenericClazz.generic(f.clazz)))).code(mv -> {
			mv.line();
			mv.LOAD(0);
			for (FieldMapper fieldMapper : fields) {
				mv.load(fieldMapper.fieldName);
			}
			mv.INVOKESPECIAL(typeOf(targetClazz), Type.VOID_TYPE, "<init>", fields.map(f -> typeOf(f.clazz)).toArray(new Type[0]));
			mv.line().putThisFieldWithVar("createAt", "createAt");
			mv.line().putThisFieldWithVar("updateAt", "updateAt");
			mv.line().returnVoid();
		});

		cw.makeAllPropertyGet();

		return cw.end().toByteArray();
	}
}
