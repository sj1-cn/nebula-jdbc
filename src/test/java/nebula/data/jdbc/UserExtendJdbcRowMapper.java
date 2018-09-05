package nebula.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtendJdbcRowMapper implements JdbcRowMapper<UserExtend> {

	@Override
	public UserExtend map(ResultSet rs) throws SQLException {// @formatter:off
		return new UserExtend(
				rs.getLong("id"), 
				rs.getString("name"), 
				rs.getString("description"),
				rs.getTimestamp("createAt"),
				rs.getTimestamp("updateAt"));// @formatter:on
	}

}
