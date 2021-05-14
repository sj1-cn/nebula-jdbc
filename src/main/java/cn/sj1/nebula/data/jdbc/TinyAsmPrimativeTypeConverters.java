package cn.sj1.nebula.data.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cn.sj1.tinyasm.core.MethodCode;

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
class TinyAsmPrimativeTypeConverters {

	static Consumer<MethodCode> doNothing = f -> {
	};
	Map<Class<?>, Map<Class<?>, ConverterEntry>> arguments = new HashMap<>();

	static class ConverterEntry {
		Consumer<MethodCode> convertFromPrimaryType;
		Consumer<MethodCode> revertToPrimaryType;

		public ConverterEntry(Consumer<MethodCode> convert, Consumer<MethodCode> revert) {
			this.convertFromPrimaryType = convert;
			this.revertToPrimaryType = revert;
		}

	}

	void register(Class<?> fromClazz, Class<?> toClazz, Consumer<MethodCode> convert, Consumer<MethodCode> revert) {
		if (!arguments.containsKey(toClazz)) {
			arguments.put(toClazz, new HashMap<>());
		}
		arguments.get(toClazz).put(fromClazz, new ConverterEntry(convert, revert));
	}

	Consumer<MethodCode> toJdbcClazz(Class<?> pojoClazz, Class<?> jdbcClazz) {
		if (pojoClazz == jdbcClazz) return doNothing;
		if (!arguments.containsKey(jdbcClazz)) return doNothing;
		return arguments.get(jdbcClazz).get(pojoClazz).convertFromPrimaryType;
	}

	Consumer<MethodCode> fromJdbcClazz(Class<?> pojoClazz, Class<?> jdbcClazz) {
		if (pojoClazz == jdbcClazz) return doNothing;
		if (!arguments.containsKey(jdbcClazz)) return doNothing;
		return arguments.get(jdbcClazz).get(pojoClazz).revertToPrimaryType;
	}

	{
		register(char.class, String.class, code -> {
			code.STATIC(String.class, "valueOf").parameter(char.class).return_(String.class).INVOKE();
		}, code -> {
			code.LOADConst(0);
			code.VIRTUAL(String.class, "charAt").parameter(int.class).return_(char.class).INVOKE();
		});

		register(Boolean.class, boolean.class, code -> {
			code.VIRTUAL(Boolean.class, "booleanValue").return_(boolean.class).INVOKE();
		}, code -> {
			code.STATIC(Boolean.class, "valueOf").parameter(boolean.class).return_(Boolean.class).INVOKE();
		});

		register(Byte.class, byte.class, code -> {
			code.VIRTUAL(Byte.class, "byteValue").return_(byte.class).INVOKE();
		}, code -> {
			code.STATIC(Byte.class, "valueOf").parameter(byte.class).return_(Byte.class).INVOKE();
		});

		register(Short.class, short.class, code -> {
			code.VIRTUAL(Short.class, "shortValue").return_(short.class).INVOKE();
		}, code -> {
			code.STATIC(Short.class, "valueOf").parameter(short.class).return_(Short.class).INVOKE();
		});

		register(Integer.class, int.class, code -> {
			code.VIRTUAL(Integer.class, "intValue").return_(int.class).INVOKE();
		}, code -> {
			code.STATIC(Integer.class, "valueOf").parameter(int.class).return_(Integer.class).INVOKE();
		});

		register(Long.class, long.class, code -> {
			code.VIRTUAL(Long.class, "longValue").return_(long.class).INVOKE();
		}, code -> {
			code.STATIC(Long.class, "valueOf").parameter(long.class).return_(Long.class).INVOKE();
		});

		register(Character.class, String.class, code -> {//
			code.VIRTUAL(Character.class, "charValue").return_(char.class).INVOKE();
			code.STATIC(String.class, "valueOf").parameter(char.class).return_(String.class).INVOKE();
		}, code -> {
			code.LOADConst(0);
			code.VIRTUAL(String.class, "charAt").parameter(int.class).return_(char.class).INVOKE();
			code.STATIC(Character.class, "valueOf").parameter(char.class).return_(Character.class).INVOKE();
		});

		register(Float.class, float.class, code -> {
			code.VIRTUAL(Float.class, "floatValue").return_(float.class).INVOKE();
		}, code -> {
			code.STATIC(Float.class, "valueOf").parameter(float.class).return_(Float.class).INVOKE();
		});

		register(Double.class, double.class, code -> {
			code.VIRTUAL(Double.class, "doubleValue").return_(double.class).INVOKE();
		}, code -> {
			code.STATIC(Double.class, "valueOf").parameter(double.class).return_(Double.class).INVOKE();
		});

		register(Date.class, java.sql.Date.class, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "dateValueOf").parameter(Date.class).return_(java.sql.Date.class).INVOKE();
		}, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "dateValueOf").parameter(java.sql.Date.class).return_(Date.class).INVOKE();
		});
		register(Date.class, java.sql.Time.class, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "timeValueOf").parameter(Date.class).return_(java.sql.Time.class).INVOKE();
		}, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "dateValueOf").parameter(java.sql.Time.class).return_(Date.class).INVOKE();
		});
		register(Date.class, java.sql.Timestamp.class, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "timestampValueOf").parameter(Date.class).return_(java.sql.Timestamp.class).INVOKE();
		}, code -> {
			code.STATIC(TinyAsmDateTypeConverterUtils.class, "dateValueOf").parameter(java.sql.Timestamp.class).return_(Date.class).INVOKE();
		});
	}

}
