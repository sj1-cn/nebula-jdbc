package nebula.data.jdbc.sample;

import java.sql.Timestamp;

import nebula.data.jdbc.EntitySystem;

public class UserExtend extends User implements EntitySystem {
	private Timestamp createAt;
	private Timestamp updateAt;

	public UserExtend() {
	}

	public UserExtend(long id, String name, String description, Timestamp createAt, Timestamp updateAt) {
		super(id, name, description);
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