package nebula.data.jdbc;

import java.sql.Timestamp;

public class UserExtend extends User implements ClassExtend {

	public UserExtend(long id, String name, String description, Timestamp createAt, Timestamp updateAt) {
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
