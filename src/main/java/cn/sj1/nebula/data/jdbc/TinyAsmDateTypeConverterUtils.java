package cn.sj1.nebula.data.jdbc;

import java.util.Date;

class TinyAsmDateTypeConverterUtils {
	public static java.sql.Date dateValueOf(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static java.sql.Time timeValueOf(Date date) {
		return new java.sql.Time(date.getTime());
	}

	public static java.sql.Timestamp timestampValueOf(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public static Date dateValueOf(java.sql.Date date) {
		return new Date(date.getTime());
	}

	public static Date dateValueOf(java.sql.Time date) {
		return new Date(date.getTime());
	}

	public static Date dateValueOf(java.sql.Timestamp date) {
		return new Date(date.getTime());
	}
}
