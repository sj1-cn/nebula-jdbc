package nebula.data.jdbc;

public class PojoUtil {
	public static String getName(String fieldname, Class<?> clazz) {
		if (clazz == boolean.class) {
			return "is" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
		} else {
			return "get" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
		}
	}
}
