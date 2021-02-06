package nebula.data.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import nebula.tinyasm.MethodCode;
import nebula.tinyasm.MethodCodeFriendly;

//Primitives: boolean, byte, short, int, long, char, float, and double
//
//java.lang: Boolean, Byte, Short, Integer, Long, Character, Float, Double, String, and Enum (stored as the enum value’s name)
//
//java.math: BigDecimal
//
//java.net: Inet4Address, Inet6Address, URL, and URI
//
//java.sql: Blob, Clob, Date, Time, and Timestamp
//
//java.time: Instant, LocalDate, LocalDateTime, LocalTime, OffsetDateTime, ZonedDateTime, and ZoneId
//
//java.util: Date, Optional (around any other supported type), and UUID
//
//java.util.Collection and Java arrays (stored as SQL arrays). Some additional setup may be required depending on the type of array element.
public class Arguments {

	static Consumer<MethodCodeFriendly> doNothing = f -> {
	};
	Map<Class<?>, Map<Class<?>, Entry>> arguments = new HashMap<>();

	static class Entry {
		Consumer<MethodCodeFriendly> convert;
		Consumer<MethodCodeFriendly> revert;

		public Entry(Consumer<MethodCodeFriendly> convert, Consumer<MethodCodeFriendly> revert) {
			this.convert = convert;
			this.revert = revert;
		}

	}

	void register(Class<?> fromClazz, Class<?> toClazz, Consumer<MethodCodeFriendly> convert, Consumer<MethodCodeFriendly> revert) {
		if (!arguments.containsKey(toClazz)) {
			arguments.put(toClazz, new HashMap<>());
		}
		arguments.get(toClazz).put(fromClazz, new Entry(convert, revert));
	}

	Consumer<MethodCodeFriendly> toJdbcClazz(Class<?> pojoClazz, Class<?> jdbcClazz) {
		if(pojoClazz == jdbcClazz)return doNothing;
		if (!arguments.containsKey(jdbcClazz)) return doNothing;
		return arguments.get(jdbcClazz).get(pojoClazz).convert;
	}

	Consumer<MethodCodeFriendly> fromJdbcClazz(Class<?> pojoClazz, Class<?> jdbcClazz) {
		if(pojoClazz == jdbcClazz)return doNothing;
		if (!arguments.containsKey(jdbcClazz)) return doNothing;
		return arguments.get(jdbcClazz).get(pojoClazz).revert;
	}

	{
		register(char.class, String.class, mv -> {
			((MethodCode)mv).STATIC(String.class, "valueOf").parameter(char.class).reTurn(String.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).LOADConst(0);
			((MethodCode)mv).VIRTUAL(String.class, "charAt").parameter(int.class).reTurn(char.class).INVOKE();
		});

		register(Boolean.class, boolean.class, mv -> {
			((MethodCode)mv).VIRTUAL(Boolean.class, "booleanValue").reTurn(boolean.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Boolean.class, "valueOf").parameter(boolean.class).reTurn(Boolean.class).INVOKE();
		});

		register(Byte.class, byte.class, mv -> {
			((MethodCode)mv).VIRTUAL(Byte.class, "byteValue").reTurn(byte.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Byte.class, "valueOf").parameter(byte.class).reTurn(Byte.class).INVOKE();
		});

		register(Short.class, short.class, mv -> {
			((MethodCode)mv).VIRTUAL(Short.class, "shortValue").reTurn(short.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Short.class, "valueOf").parameter(short.class).reTurn(Short.class).INVOKE();
		});

		register(Integer.class, int.class, mv -> {
			((MethodCode)mv).VIRTUAL(Integer.class, "intValue").reTurn(int.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Integer.class, "valueOf").parameter(int.class).reTurn(Integer.class).INVOKE();
		});

		register(Long.class, long.class, mv -> {
			((MethodCode)mv).VIRTUAL(Long.class, "longValue").reTurn(long.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Long.class, "valueOf").parameter(long.class).reTurn(Long.class).INVOKE();
		});

		register(Character.class, String.class, mv -> {//
			((MethodCode)mv).VIRTUAL(Character.class, "charValue").reTurn(char.class).INVOKE();
			((MethodCode)mv).STATIC(String.class, "valueOf").parameter(char.class).reTurn(String.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).LOADConst(0);
			((MethodCode)mv).VIRTUAL(String.class, "charAt").parameter(int.class).reTurn(char.class).INVOKE();
			((MethodCode)mv).STATIC(Character.class, "valueOf").parameter(char.class).reTurn(Character.class).INVOKE();
		});

		register(Float.class, float.class, mv -> {
			((MethodCode)mv).VIRTUAL(Float.class, "floatValue").reTurn(float.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Float.class, "valueOf").parameter(float.class).reTurn(Float.class).INVOKE();
		});

		register(Double.class, double.class, mv -> {
			((MethodCode)mv).VIRTUAL(Double.class, "doubleValue").reTurn(double.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(Double.class, "valueOf").parameter(double.class).reTurn(Double.class).INVOKE();
		});

		register(Date.class, java.sql.Date.class, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "dateValueOf").parameter(Date.class).reTurn(java.sql.Date.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "dateValueOf").parameter(java.sql.Date.class).reTurn(Date.class).INVOKE();
		});
		register(Date.class, java.sql.Time.class, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "timeValueOf").parameter(Date.class).reTurn(java.sql.Time.class).INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "dateValueOf").parameter(java.sql.Time.class).reTurn(Date.class).INVOKE();
		});
		register(Date.class, java.sql.Timestamp.class, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "timestampValueOf")
				.parameter(Date.class)
				.reTurn(java.sql.Timestamp.class)
				.INVOKE();
		}, mv -> {
			((MethodCode)mv).STATIC(TypeUtil.class, "dateValueOf").parameter(java.sql.Timestamp.class).reTurn(Date.class).INVOKE();
		});
	}

}
