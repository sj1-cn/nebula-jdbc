package nebula.data.jdbc;

import java.sql.Timestamp;

public class UserAnotherExtend extends UserAnother implements ClassExtend {

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
