package cn.sj1.nebula.data;

import java.sql.Timestamp;

public interface EntityAudit {

	public Timestamp getCreateAt();

//	public long getCreateBy();

	public Timestamp getUpdateAt();

//	public long getUpdateBy();
}
