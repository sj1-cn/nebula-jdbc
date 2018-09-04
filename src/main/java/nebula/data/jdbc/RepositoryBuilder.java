package nebula.data.jdbc;

import static nebula.tinyasm.data.CodeHelper.Const;

import java.util.HashMap;
import java.util.Map;

import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;
import nebula.tinyasm.data.MethodCode;

public class RepositoryBuilder {
	Arguments arguments;

	public RepositoryBuilder(Arguments arguments) {
		this.arguments = arguments;
	}

	protected void revertFromJdbc(MethodCode mv, Class<?> pojoClazz, Class<?> jdbcClazz) {
		if (pojoClazz != jdbcClazz) {
			arguments.getRevert(pojoClazz, jdbcClazz).apply(mv);
		}
	}

	protected Class<?> box(MethodCode mv, Class<?> primaryClazz) {
		Class<?> objectClazz = mapPrimaryToObject.get(primaryClazz);
		if (objectClazz != null) {
			arguments.getRevert(objectClazz, primaryClazz).apply(mv);
			return objectClazz;
		}
		return primaryClazz;
	}

	static Map<Class<?>, Class<?>> mapPrimaryToObject = new HashMap<>();
	static Map<Class<?>, Class<?>> mapObjectToPrimary = new HashMap<>();
	static {
		mapPrimaryToObject.put(boolean.class, Boolean.class);
		mapPrimaryToObject.put(char.class, Character.class);
		mapPrimaryToObject.put(byte.class, Byte.class);
		mapPrimaryToObject.put(short.class, Short.class);
		mapPrimaryToObject.put(int.class, Integer.class);
		mapPrimaryToObject.put(long.class, Long.class);
		mapPrimaryToObject.put(float.class, Float.class);
		mapPrimaryToObject.put(double.class, Double.class);

		mapObjectToPrimary.put(Boolean.class, boolean.class);
		mapObjectToPrimary.put(Character.class, char.class);
		mapObjectToPrimary.put(Byte.class, byte.class);
		mapObjectToPrimary.put(Short.class, short.class);
		mapObjectToPrimary.put(Integer.class, int.class);
		mapObjectToPrimary.put(Long.class, long.class);
		mapObjectToPrimary.put(Float.class, float.class);
		mapObjectToPrimary.put(Double.class, double.class);
	}

	@SuppressWarnings("unused")
	private Class<?> unbox(MethodCode mv, Class<?> objectClazz) {
		Class<?> primaryClazz = mapObjectToPrimary.get(objectClazz);
		if (primaryClazz != null) {
			arguments.getConvert(objectClazz, primaryClazz).apply(mv);
			return primaryClazz;
		}
		return objectClazz;
	}

	protected void bindField(MethodCode mv, int index, String clazzTarget, FieldMapper field) {
		{
			Class<?> jdbcClazz = field.pojoClazz;
			JdbcMapping jdbcType = JDBC.mapJavaClazz2JdbcTypes.get(jdbcClazz.getName());

			mv.line();
			mv.load("preparedStatement").inter(jdbcType.setname).invokeVoid(Const(index), p -> {
				mv.load("data").virtual(field.pojo_getname).reTurn(field.pojoClazz).invoke();
				if (jdbcClazz != jdbcType.jdbcClazz) {
					arguments.getConvert(jdbcClazz, jdbcType.jdbcClazz).apply(mv);
				}
			});
		}
	}

}