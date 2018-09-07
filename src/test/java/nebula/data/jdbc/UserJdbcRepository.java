package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nebula.data.query.Condition;
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
		if (!JDBC.mergeIfExists(conn, "USER", columnList)) {
			// @formatter:off
			conn.prepareStatement("CREATE TABLE USER(id INTEGER(10),name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP,PRIMARY KEY(id))").execute();
			// @formatter:on
		}
	}

	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageList<User> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = Select.columns("id,name,description,createAt,updateAt").from("USER").offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = Select.columns("count(1)").from("USER").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public PageList<User> listJdbc(Condition condition, int start, int max) throws SQLException {

		PageList<User> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = Select.columns("id,name,description,createAt,updateAt").from("USER").where(condition).offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = Select.columns("count(1)").from("USER").where(condition).toSQL();
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

		// @formatter:off
		preparedStatement = conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?");
		// @formatter:on

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
		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO USER(id,name,description,createAt,updateAt) VALUES(?,?,?,?,?)");
		// @formatter:on

		preparedStatement.setLong(1, data.getId());
		preparedStatement.setString(2, data.getName());
		preparedStatement.setString(3, data.getDescription());

		bindInsertExtend(preparedStatement, 4);

		if (preparedStatement.executeUpdate() > 0) {
			return findByIdJdbc(data.getId());
		}
		return null;
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		ClassExtend extend = (ClassExtend) findByIdJdbc(data.getId());
		if (extend.getUpdateAt() == ((ClassExtend) data).getUpdateAt()) {
			return null;
		}

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?");
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());
		bindUpdateExtend(preparedStatement, 3);
		preparedStatement.setLong(4, data.getId());

		if (preparedStatement.executeUpdate() > 0) {
			return findByIdJdbc(data.getId());
		}
		return null;
	}

	@Override
	public int deleteJdbc(Object... keys) throws SQLException {
		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE USER WHERE id=?");
		// @formatter:on

		Object key = keys[0];
		preparedStatement.setLong(1, ((Long) key).longValue());

		return preparedStatement.executeUpdate();
	}

}
