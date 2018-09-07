package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import nebula.data.query.Condition;
import nebula.jdbc.builders.schema.JDBC;

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
			return listJdbc(condition,start, max);
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
	default T findById(Object... keys) {
		try {
			return findByIdJdbc(keys);
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
	default int delete(Object... keys) {
		try {
			return deleteJdbc(keys);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	void initJdbc() throws SQLException;

	PageList<T> listJdbc(int start, int max) throws SQLException;
	
	PageList<T> listJdbc(Condition condition, int start, int max) throws SQLException;

	T findByIdJdbc(Object... keys) throws SQLException;

	T insertJdbc(T pet) throws SQLException;

	T updateJdbc(T pet) throws SQLException;

	int deleteJdbc(Object... keys) throws SQLException;

}
