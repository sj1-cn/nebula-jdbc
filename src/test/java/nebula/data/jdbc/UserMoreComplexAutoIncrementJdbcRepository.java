package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;

public class UserMoreComplexAutoIncrementJdbcRepository implements JdbcRepository<UserMoreComplex> {
	private Connection conn;
	private UserMoreComplexJdbcRowMapper mapper = new UserMoreComplexJdbcRowMapper();

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// @formatter:off
	@Override
	public void initJdbc() throws SQLException {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id BIGINT(19) PRIMARY KEY AUTO_INCREMENT"));
		columnList.push(ColumnDefinition.valueOf("string VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("bigDecimal DECIMAL(15,6)"));
		columnList.push(ColumnDefinition.valueOf("z BOOLEAN"));
		columnList.push(ColumnDefinition.valueOf("c CHAR(1)"));
		columnList.push(ColumnDefinition.valueOf("b TINYINT(3)"));
		columnList.push(ColumnDefinition.valueOf("s SMALLINT(5)"));
		columnList.push(ColumnDefinition.valueOf("i INTEGER(10)"));
		columnList.push(ColumnDefinition.valueOf("l BIGINT(19)"));
		columnList.push(ColumnDefinition.valueOf("f FLOAT(7)"));
		columnList.push(ColumnDefinition.valueOf("d DOUBLE(17)"));
		columnList.push(ColumnDefinition.valueOf("date DATE"));
		columnList.push(ColumnDefinition.valueOf("time TIME"));
		columnList.push(ColumnDefinition.valueOf("timestamp TIMESTAMP"));
		if (!JDBC.mergeIfExists(this.conn, "UserMoreComplex", columnList)) {
			this.conn.prepareStatement("CREATE TABLE UserMoreComplex(id BIGINT(19) PRIMARY KEY AUTO_INCREMENT,string VARCHAR(256),bigDecimal DECIMAL(15,6),z BOOLEAN,c CHAR(1),b TINYINT(3),s SMALLINT(5),i INTEGER(10),l BIGINT(19),f FLOAT(7),d DOUBLE(17),date DATE,time TIME,timestamp TIMESTAMP)").execute();
		}
	}

	@Override
	public PageList<UserMoreComplex> listJdbc(int start, int max) throws SQLException {
		PageList<UserMoreComplex> datas = new PageListImpl<>(start, max);

		String sql = Select.columns("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp").from("UserMoreComplex").offset(start).max(max).toSQL();
		
		ResultSet resultSet = this.conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = Select.columns("count(1)").from("UserMoreComplex").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public UserMoreComplex findByIdJdbc(Object... keys) throws SQLException {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<UserMoreComplex> datas = new ArrayList<>();

		preparedStatement = conn.prepareStatement("SELECT id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp FROM UserMoreComplex WHERE id = ?");

		Object key = keys[0];
		preparedStatement.setLong(1, ((Long)key).longValue());

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		return datas.get(0);
	}

	@Override
	public UserMoreComplex insertJdbc(UserMoreComplex data) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		preparedStatement = this.conn.prepareStatement("INSERT INTO UserMoreComplex(string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", 1);

		preparedStatement.setString(1, data.getString());
		preparedStatement.setBigDecimal(2, data.getBigDecimal());
		preparedStatement.setBoolean(3, data.getZ().booleanValue());
		preparedStatement.setString(4, String.valueOf(data.getC().charValue()));
		preparedStatement.setByte(5, data.getB().byteValue());
		preparedStatement.setShort(6, data.getS().shortValue());
		preparedStatement.setInt(7, data.getI().intValue());
		preparedStatement.setLong(8, data.getL().longValue());
		preparedStatement.setFloat(9, data.getF().floatValue());
		preparedStatement.setDouble(10, data.getD().doubleValue());
		preparedStatement.setDate(11, data.getDate());
		preparedStatement.setTime(12, data.getTime());
		preparedStatement.setTimestamp(13, data.getTimestamp());

		if (preparedStatement.executeUpdate() > 0) {
			rs = preparedStatement.getGeneratedKeys();
			rs.next();
			return (UserMoreComplex) findById(new Object[] { rs.getLong(1) });
		}
		return null;
	}

	@Override
	public UserMoreComplex updateJdbc(UserMoreComplex data) throws SQLException {
		PreparedStatement preparedStatement = this.conn.prepareStatement("UPDATE UserMoreComplex SET string=?,bigDecimal=?,z=?,c=?,b=?,s=?,i=?,l=?,f=?,d=?,date=?,time=?,timestamp=? WHERE id=?");

		preparedStatement.setString(1, data.getString());
		preparedStatement.setBigDecimal(2, data.getBigDecimal());
		preparedStatement.setBoolean(3, data.getZ().booleanValue());
		preparedStatement.setString(4, String.valueOf(data.getC().charValue()));
		preparedStatement.setByte(5, data.getB().byteValue());
		preparedStatement.setShort(6, data.getS().shortValue());
		preparedStatement.setInt(7, data.getI().intValue());
		preparedStatement.setLong(8, data.getL().longValue());
		preparedStatement.setFloat(9, data.getF().floatValue());
		preparedStatement.setDouble(10, data.getD().doubleValue());
		preparedStatement.setDate(11, data.getDate());
		preparedStatement.setTime(12, data.getTime());
		preparedStatement.setTimestamp(13, data.getTimestamp());
		preparedStatement.setLong(14, data.getId());

		if (preparedStatement.executeUpdate() > 0) {
			return findById(data.getId());
		}
		return null;
	}

	@Override
	public int deleteJdbc(Object... keys) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE UserMoreComplex WHERE id=?");

		preparedStatement.setLong(1, ((Long) keys[0]).longValue());

		return preparedStatement.executeUpdate();
	}

}
