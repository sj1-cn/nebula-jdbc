package nebula.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsingUserExtendJdbcRowMapper {
	public String test(ResultSet resultSet) throws SQLException {

		UserExtendJdbcRowMapper mapper = new UserExtendJdbcRowMapper();
		UserExtend userExtend = mapper.map(resultSet);
		return userExtend.getName();
	}
}
