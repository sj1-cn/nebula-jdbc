package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;

public class UserAutoIncrementJdbcRepository implements JdbcRepository<User> {
	private Connection conn;
	private UserExtendJdbcRowMapper mapper;
	private SqlHelper sqlHelper;

	public UserAutoIncrementJdbcRepository() {
		mapper = new UserExtendJdbcRowMapper();
		sqlHelper = new SqlHelper();
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void initJdbc() throws SQLException {
		ColumnList columnList = new ColumnList();
		columnList.addColumn("id INTEGER(10) PRIMARY KEY AUTO_INCREMENT");
		columnList.addColumn("name VARCHAR(256)");
		columnList.addColumn("description VARCHAR(256)");
		columnList.addColumn("createAt TIMESTAMP");
		columnList.addColumn("updateAt TIMESTAMP");
		if (!mergeIfExists(conn, "USER", columnList)) {
			// @formatter:off
			conn.prepareStatement("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
			// @formatter:on
		}
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageList<User> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,name,description,createAt,updateAt").from("USER").offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USER").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		boolean result = resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(Condition condition, OrderBy orderBy, int start, int max) throws SQLException {
		PageList<User> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,name,description,createAt,updateAt").from("USER").where(condition).orderby(orderBy).offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USER").where(condition).toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		boolean result = resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public User findByIdJdbc(long id) throws SQLException {
		List<User> datas = new ArrayList<>();

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?");
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(mapper.map(resultSet));
		}
		
		return datas.get(0);
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		ResultSet resultSet = null;

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO USER(name,description,createAt,updateAt) VALUES(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());

		bindInsertExtend(preparedStatement, 3);

		if (preparedStatement.executeUpdate() > 0) {
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();

			return findByIdJdbc(resultSet.getLong(1));
		} else {
			return null;
		}
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
	public int deleteByIdJdbc(long id) throws SQLException {
		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE USER WHERE id=?");
		// @formatter:on

//		Object key = keys[0];
		preparedStatement.setLong(1, id);

		return preparedStatement.executeUpdate();
	}

}
