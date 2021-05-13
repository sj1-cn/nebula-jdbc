package cn.sj1.nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import cn.sj1.nebula.data.basic.PageList;
import cn.sj1.nebula.data.query.Condition;
import cn.sj1.nebula.data.query.OrderBy;
import cn.sj1.nebula.jdbc.sql.builders.schema.ColumnList;
import cn.sj1.nebula.jdbc.sql.builders.schema.JDBC;

public interface JdbcRepository<T> extends Repository<T> {
	void setConnection(Connection conn);

	@Override
	default void init() {
		try {
			initJdbc();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default PageList<T> list(Condition condition, int start, int max) {
		try {
			return listJdbc(condition, OrderBy.empty(), start, max);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default PageList<T> list(Condition condition, OrderBy orderby, int start, int max) {
		try {
			return listJdbc(condition, orderby, start, max);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default PageList<T> list(int start, int max) {
		try {
			return listJdbc(start, max);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default T findById(long key) {
		try {
			return findByIdJdbc(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default T insert(T pet) {
		try {
			return insertJdbc(pet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default T update(T pet) {
		try {
			return updateJdbc(pet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	default int bindInsertExtend(PreparedStatement preparedStatement, int index) throws SQLException {
		Timestamp timestamp = JDBC.timestamp();
		preparedStatement.setTimestamp(index++, timestamp);// createAt
		preparedStatement.setTimestamp(index++, timestamp);// updateAt
		return index;
	}

	default int bindUpdateExtend(PreparedStatement preparedStatement, int index) throws SQLException {
		Timestamp timestamp = JDBC.timestamp();
		preparedStatement.setTimestamp(index++, timestamp);// updateAt
		return index;
	}

	@Override
	default int deleteById(long key) {
		try {
			return deleteByIdJdbc(key);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

//	@Override
//	default int deleteByName(String name) {
//		try {
//			return deleteJdbcByName(name);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}

	void initJdbc() throws SQLException;

	PageList<T> listJdbc(int start, int max) throws SQLException;

	PageList<T> listJdbc(Condition condition, OrderBy orderby, int start, int max) throws SQLException;

	T findByIdJdbc(long id) throws SQLException;

	T insertJdbc(T pet) throws SQLException;

	T updateJdbc(T pet) throws SQLException;

	int deleteByIdJdbc(long id) throws SQLException;

	default boolean mergeIfExists(Connection conn, String tableName, ColumnList columnsExpected) {
		return false;
	}

//	int deleteJdbcByName(String name) throws SQLException;

}
