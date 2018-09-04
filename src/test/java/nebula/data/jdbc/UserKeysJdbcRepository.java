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

public class UserKeysJdbcRepository implements JdbcRepository<User> {
	private Connection conn;
	private UserJdbcRowMapper mapper = new UserJdbcRowMapper();

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void initJdbc() throws SQLException {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		if (!JDBC.mergeIfExists(conn, "user", columnList)) {
			conn.prepareStatement("CREATE TABLE user(id INTEGER(10),name VARCHAR(256),description VARCHAR(256),PRIMARY KEY(id,name))").execute();
		}
	}

	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageList<User> datas = new PageListImpl<>(start, max);

		String sql = Select.columns("id,name,description").from("user").offset(start).max(max).toSQL();

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = Select.columns("count(1)").from("user").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public User findByIdJdbc(Object... keys) throws SQLException {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<User> datas = new ArrayList<>();

		preparedStatement = conn.prepareStatement("SELECT id, name, description FROM user WHERE id = ? AND name = ?");

		Object key = keys[0];
		preparedStatement.setLong(1, ((Long)key).longValue());
		key = keys[1];
		preparedStatement.setString(2, (String) key);

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		return datas.get(0);
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO user(id,name,description) VALUES(?,?,?)");

		preparedStatement.setLong(1, data.getId());
		preparedStatement.setString(2, data.getName());
		preparedStatement.setString(3, data.getDescription());

		if (preparedStatement.executeUpdate() > 0) {
			return this.findById(data.getId(),data.getName());
		}
		return null;
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET description=? WHERE id=? AND name=?");

		preparedStatement.setString(1, data.getDescription());
		preparedStatement.setLong(2, data.getId());
		preparedStatement.setString(3, data.getName());

		if (preparedStatement.executeUpdate() > 0) {
			return findById(data.getId(),data.getName());
		}
		return null;
	}

	@Override
	public int deleteJdbc(Object... keys) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE user WHERE id=? AND name=?");

		preparedStatement.setLong(1, ((Long) keys[0]).longValue());
		preparedStatement.setString(2, (String) keys[1]);

		return preparedStatement.executeUpdate();
	}

}
