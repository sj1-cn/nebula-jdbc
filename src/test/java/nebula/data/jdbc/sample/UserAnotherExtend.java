package nebula.data.jdbc.sample;

import java.sql.Timestamp;

import nebula.data.jdbc.EntitySystem;

public class UserAnotherExtend extends UserAnother implements EntitySystem {

	public UserAnotherExtend(long id, String name, String description, Timestamp createAt, Timestamp updateAt) {
		super(id, name, description);
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	private Timestamp createAt;
	private Timestamp updateAt;

	public Timestamp getCreateAt() {
		return createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}
}
