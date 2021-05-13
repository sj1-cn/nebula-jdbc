package cn.sj1.nebula.data.jdbc.sample;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import cn.sj1.nebula.data.basic.EntitySystem;

public class UserMoreComplexExtend extends UserMoreComplex implements EntitySystem {

	private Timestamp createAt;
	private Timestamp updateAt;
	public UserMoreComplexExtend() {
		
	}

	public UserMoreComplexExtend(long id, String string, BigDecimal bigDecimal, Boolean z, Character c, Byte b, Short s, Integer i, Long l,
			Float f, Double d, Date date, Time time, Timestamp timestamp, Timestamp createAt, Timestamp updateAt) {
		super(id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp);
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

}
