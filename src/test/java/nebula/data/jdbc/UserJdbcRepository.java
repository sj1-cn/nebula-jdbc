package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;

public class UserJdbcRepository implements JdbcRepository<User> {
	private Connection conn;
	private UserExtendJdbcRowMapper mapper = new UserExtendJdbcRowMapper();

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void initJdbc() throws SQLException {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("createAt TIMESTAMP"));
		columnList.push(ColumnDefinition.valueOf("updateAt TIMESTAMP"));
		if (!JDBC.mergeIfExists(conn, "user", columnList)) {
			conn.prepareStatement("CREATE TABLE user(id INTEGER(10),name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP,PRIMARY KEY(id))").execute();
		}
	}

	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageList<User> datas = new PageListImpl<>(start, max);

		String sql = Select.columns("id,name,description,createAt,updateAt").from("user").offset(start).max(max).toSQL();

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

		preparedStatement = conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM user WHERE id = ?");

		Object key = keys[0];
		preparedStatement.setLong(1, ((Long) key).longValue());

		resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		return datas.get(0);
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO user(id,name,description,createAt,updateAt) VALUES(?,?,?,?,?)");

		preparedStatement.setLong(1, data.getId());
		preparedStatement.setString(2, data.getName());
		preparedStatement.setString(3, data.getDescription());
		preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
		preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));

		if (preparedStatement.executeUpdate() > 0) {
			return this.findById(data.getId());
		}
		return null;
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET name=?,description=?,updateAt=? WHERE id=?");

		ClassExtend userLast = (ClassExtend)findByIdJdbc(data.getId());
		if(userLast.getUpdateAt() == ((ClassExtend)data).getUpdateAt()) {
			return null;
		}
		
		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());
		preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));		
		preparedStatement.setLong(4, data.getId());


		if (preparedStatement.executeUpdate() > 0) {
			return findById(data.getId());
		}
		return null;
	}

	@Override
	public int deleteJdbc(Object... keys) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE user WHERE id=?");

		Object key = keys[0];
		preparedStatement.setLong(1, ((Long) key).longValue());

		return preparedStatement.executeUpdate();
	}

}
