package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nebula.data.jdbc.sample.UserMoreComplex;
import nebula.data.jdbc.sample.UserMoreComplexExtend;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;

public class UserMoreComplexAutoIncrementJdbcRepository implements JdbcRepository<UserMoreComplex> {
	private Connection conn;
	private SqlHelper sqlHelper;

	public UserMoreComplexAutoIncrementJdbcRepository() {
		sqlHelper = new SqlHelper();
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void initJdbc() throws SQLException {
		ColumnList columnList = new ColumnList();
		columnList.addColumn("id BIGINT(19) PRIMARY KEY AUTO_INCREMENT");
		columnList.addColumn("string VARCHAR(256)");
		columnList.addColumn("bigDecimal DECIMAL(15,6)");
		columnList.addColumn("z BOOLEAN");
		columnList.addColumn("c CHAR(1)");
		columnList.addColumn("b TINYINT(3)");
		columnList.addColumn("s SMALLINT(5)");
		columnList.addColumn("i INTEGER(10)");
		columnList.addColumn("l BIGINT(19)");
		columnList.addColumn("f FLOAT(7)");
		columnList.addColumn("d DOUBLE(17)");
		columnList.addColumn("date DATE");
		columnList.addColumn("time TIME");
		columnList.addColumn("timestamp TIMESTAMP");
		columnList.addColumn("createAt TIMESTAMP");
		columnList.addColumn("updateAt TIMESTAMP");
		if (!mergeIfExists(conn, "USERMORECOMPLEX", columnList)) {
			this.conn.prepareStatement("CREATE TABLE USERMORECOMPLEX(id BIGINT(19) PRIMARY KEY AUTO_INCREMENT,string VARCHAR(256),bigDecimal DECIMAL(15,6),z BOOLEAN,c CHAR(1),b TINYINT(3),s SMALLINT(5),i INTEGER(10),l BIGINT(19),f FLOAT(7),d DOUBLE(17),date DATE,time TIME,timestamp TIMESTAMP,createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
		}
	}

	@Override
	public PageList<UserMoreComplex> listJdbc(int start, int max) throws SQLException {
		PageList<UserMoreComplex> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt").from("USERMORECOMPLEX").offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(toEntity(resultSet));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USERMORECOMPLEX").toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}

	@Override
	public PageList<UserMoreComplex> listJdbc(Condition condition,OrderBy orderBy, int start, int max) throws SQLException {

		PageList<UserMoreComplex> datas = new PageListImpl<>(start, max);

		// @formatter:off
		String sql = sqlHelper.select("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt").from("USERMORECOMPLEX").where(condition).orderby(orderBy).offset(start).max(max).toSQL();
		// @formatter:on

		ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

		while (resultSet.next()) {
			datas.add(toEntity(resultSet));
		}
		resultSet.close();

		String sqlCount = sqlHelper.select("count(1)").from("USERMORECOMPLEX").where(condition).toSQL();
		ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
		resultSetCount.next();
		int totalSize = resultSetCount.getInt(1);
		resultSetCount.close();
		datas.totalSize(totalSize);

		return datas;
	}


	private UserMoreComplexExtend toEntity(ResultSet resultSet) throws SQLException {
		UserMoreComplexExtend impl = new UserMoreComplexExtend();

		impl.setId(resultSet.getLong(1));
		impl.setString(resultSet.getString(2));
		impl.setBigDecimal(resultSet.getBigDecimal(3)); 
		impl.setZ(resultSet.getBoolean(4));
		impl.setC(resultSet.getString(5).charAt(0));
		impl.setB(Byte.valueOf(resultSet.getByte(6)));
		impl.setS(Short.valueOf(resultSet.getShort(7)));
		impl.setI(Integer.valueOf(resultSet.getInt(8)));
		impl.setL(Long.valueOf(resultSet.getLong(9)));
		impl.setF(Float.valueOf(resultSet.getFloat(10)));
		impl.setD(Double.valueOf(resultSet.getDouble(11)));
		impl.setDate(resultSet.getDate(12));
		impl.setTime(resultSet.getTime(13)); 
		impl.setTimestamp(resultSet.getTimestamp(14));
		impl.setCreateAt(resultSet.getTimestamp(15));
		impl.setUpdateAt(resultSet.getTimestamp(16));
		
		return impl;
	}

	@Override
	public UserMoreComplex findByIdJdbc(long id) throws SQLException {
		List<UserMoreComplex> datas = new ArrayList<>();

		PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp, createAt, updateAt FROM USERMORECOMPLEX WHERE id = ?");

		preparedStatement.setLong(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			datas.add(toEntity(resultSet));
		}
		return datas.get(0);
	}

	@Override
	public UserMoreComplex insertJdbc(UserMoreComplex data) throws SQLException {
		ResultSet resultSet = null;
		// @formatter:off
		PreparedStatement preparedStatement = this.conn.prepareStatement("INSERT INTO USERMORECOMPLEX(string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 1);
		// @formatter:on

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

		bindInsertExtend(preparedStatement, 14);

		if (preparedStatement.executeUpdate() > 0) {
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			return findByIdJdbc(resultSet.getLong(1));
		}
		return null;
	}

	@Override
	public UserMoreComplex updateJdbc(UserMoreComplex data) throws SQLException {
		EntitySystem extend = (EntitySystem) findByIdJdbc(data.getId());
		if (extend.getUpdateAt() == ((EntitySystem) data).getUpdateAt()) {
			return null;
		}

		// @formatter:off
		PreparedStatement preparedStatement =  conn.prepareStatement("UPDATE USERMORECOMPLEX SET string=?,bigDecimal=?,z=?,c=?,b=?,s=?,i=?,l=?,f=?,d=?,date=?,time=?,timestamp=?,updateAt=? WHERE id=?");
		// @formatter:on

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
		bindUpdateExtend(preparedStatement, 14);
		preparedStatement.setLong(15, data.getId());

		if (preparedStatement.executeUpdate() > 0) {
			return findByIdJdbc(data.getId());
		}
		return null;
	}

	@Override
	public int deleteByIdJdbc(long id) throws SQLException {
		// @formatter:off
		PreparedStatement preparedStatement = conn.prepareStatement("DELETE USERMORECOMPLEX WHERE id=?");
		// @formatter:on

		preparedStatement.setLong(1, id);

		return preparedStatement.executeUpdate();
	}

}
