package nebula.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbcRowMapper implements JdbcRowMapper<User> {

	@Override
	public User map(ResultSet rs) throws SQLException {// @formatter:off
		return new User(
				rs.getLong("id"), 
				rs.getString("name"), 
				rs.getString("description"));// @formatter:on
	}

}
