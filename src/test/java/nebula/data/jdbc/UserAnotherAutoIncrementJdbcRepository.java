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

public class UserAnotherAutoIncrementJdbcRepository implements JdbcRepository<UserAnother> {
	private Connection conn;
//	private UserExtendJdbcRowMapper mapper;
	private SqlHelper sqlHelper;

	public UserAnotherAutoIncrementJdbcRepository() {
//		mapper = new UserExtendJdbcRowMapper();
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
		if (!mergeIfExists(conn, "USERANOTHER", columnList)) {
			// @formatter:off
			conn.prepareStatement("CREATE TABLE USERANOTHER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
			// @formatter:on
		}
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<UserAnother> listJdbc(int start, int max) throws SQLException {
		PageList<UserAnother> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,name,description,createAt,updateAt").from("USERANOTHER").offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(new UserAnotherExtend(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),resultSet.getTimestamp("createAt"),resultSet.getTimestamp("updateAt")));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USERANOTHER").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		boolean result = resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<UserAnother> listJdbc(Condition condition, OrderBy orderBy, int start, int max) throws SQLException {
		PageList<UserAnother> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,name,description,createAt,updateAt").from("USERANOTHER").where(condition).orderby(orderBy).offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(new UserAnotherExtend(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),resultSet.getTimestamp("createAt"),resultSet.getTimestamp("updateAt")));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USERANOTHER").where(condition).toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		boolean result = resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public UserAnother findByIdJdbc(long id) throws SQLException {
		List<UserAnother> datas = new ArrayList<>();

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USERANOTHER WHERE id = ?");
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(new UserAnotherExtend(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),resultSet.getTimestamp("createAt"),resultSet.getTimestamp("updateAt")));
		}
		
		return datas.get(0);
	}

	@Override
	public UserAnother insertJdbc(UserAnother data) throws SQLException {
		ResultSet resultSet = null;

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO USERANOTHER(name,description,createAt,updateAt) VALUES(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
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
	public UserAnother updateJdbc(UserAnother data) throws SQLException {
		ClassExtend extend = (ClassExtend) findByIdJdbc(data.getId());
		ClassExtend dataExtend = (ClassExtend)data;
		if (extend.getUpdateAt() == dataExtend.getUpdateAt()) {
			return null;
		}

		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE USERANOTHER SET name=?,description=?,updateAt=? WHERE id=?");
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
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE USERANOTHER WHERE id=?");
		// @formatter:on

//		Object key = keys[0];
		preparedStatement.setLong(1, id);

		return preparedStatement.executeUpdate();
	}

}
