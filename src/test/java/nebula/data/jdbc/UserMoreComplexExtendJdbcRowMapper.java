package nebula.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMoreComplexExtendJdbcRowMapper implements JdbcRowMapper<UserMoreComplexExtend> {

	@Override
	public UserMoreComplexExtend map(ResultSet rs) throws SQLException {// @formatter:off
		return new UserMoreComplexExtend(
				Long.valueOf(rs.getLong("id")), 
				rs.getString("string"),
				rs.getBigDecimal("bigDecimal"), 
				Boolean.valueOf(rs.getBoolean("z")),
				Character.valueOf(rs.getString("c").charAt(0)), 
				Byte.valueOf(rs.getByte("b")),
				Short.valueOf(rs.getShort("s")), 
				Integer.valueOf(rs.getInt("i")), 
				Long.valueOf(rs.getLong("l")),
				Float.valueOf(rs.getFloat("f")), 
				Double.valueOf(rs.getDouble("d")), 
				rs.getDate("date"),
				rs.getTime("time"), 
				rs.getTimestamp("timestamp"),
				rs.getTimestamp("createAt"),
				rs.getTimestamp("updateAt"));// @formatter:on
	}

}
