package nebula.data.jdbc;

import java.sql.Timestamp;

public interface EntitySystem {

	public Timestamp getCreateAt();

//	public long getCreateBy();

	public Timestamp getUpdateAt();

//	public long getUpdateBy();
}
