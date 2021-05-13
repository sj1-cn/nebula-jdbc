package nebula.data.jdbc.sample;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import nebula.data.jdbc.EntitySystem;

public class UserComplexExtend extends UserComplex implements EntitySystem {

	public UserComplexExtend(long id, String string, BigDecimal bigDecimal, boolean z, char c, byte b, short s, int i, long l, float f,
			double d, Date date, Time time, Timestamp timestamp, Timestamp createAt, Timestamp updateAt) {
		super(id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp);
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	Timestamp createAt;
	Timestamp updateAt;

	public Timestamp getCreateAt() {
		return createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

}
