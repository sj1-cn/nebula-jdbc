package nebula.data.jdbc;

import static cc1sj.tinyasm.Adv.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cc1sj.tinyasm.Adv;
import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.MethodCode;
import cc1sj.tinyasm.boolean_;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;

public class UserAutoIncrementJdbcRepositoryMagicBuilder implements JdbcRepository<User> {

	final private Connection conn = private_().field("conn", Connection.class);
	final private UserExtendJdbcRowMapper mapper = private_().field("mapper", UserExtendJdbcRowMapper.class);
	final private SqlHelper sqlHelper = private_().field("sqlHelper", SqlHelper.class);

//	public UserAutoIncrementJdbcRepositoryMagicBuilder() {
//		mapper = new UserExtendJdbcRowMapper();
//		sqlHelper = new SqlHelper();
//	}
//	public void __init_(ClassBody classBody) {
//		MethodCode code = classBody.publicMethod("<init>").begin();
//
//		code.LINE();
//		code.LOAD("this");
//		code.SPECIAL(Object.class, "<init>").INVOKE();
//
//		code.LINE();
//		code.LOAD("this");
//		code.NEW(UserExtendJdbcRowMapper.class);
//		code.DUP();
//		code.SPECIAL(UserExtendJdbcRowMapper.class, "<init>").INVOKE();
//		code.PUTFIELD_OF_THIS("mapper");
//
//		code.LINE();
//		code.LOAD("this");
//		code.NEW(SqlHelper.class);
//		code.DUP();
//		code.SPECIAL(SqlHelper.class, "<init>").INVOKE();
//		code.PUTFIELD_OF_THIS("sqlHelper");
//
//		code.LINE();
//		code.RETURN();
//
//		code.END();
//	}
//	
	@Override
	public void setConnection(Connection conn) {
		__(this.conn, conn);
	}

	@Override
	public void initJdbc() throws SQLException {
		final ColumnList columnList = __("columnList", new_(ColumnList.class));
		columnList.addColumn("id INTEGER(10) PRIMARY KEY AUTO_INCREMENT");
		columnList.addColumn("name VARCHAR(256)");
		columnList.addColumn("description VARCHAR(256)");
		columnList.addColumn("createAt TIMESTAMP");
		columnList.addColumn("updateAt TIMESTAMP");
//		if (!JDBC.mergeIfExists(conn, "USER", columnList)) {
//			// @formatter:off
//			conn.prepareStatement("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
//			// @formatter:on
//		}
	}

	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageListImpl<User> datas = __("datas", new_(PageListImpl.class, start, max));

		// @formatter:off
		String sql = __("sql",sqlHelper.select("id,name,description,createAt,updateAt").from("USER").offset(start).max(max).toSQL());
		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());
		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(mapper.map(resultSet));
		});
		resultSet.close();

		final String sqlCount = __("sqlCount", sqlHelper.select("count(1)").from("USER").toSQL());
		final ResultSet resultSetCount = __("resultSetCount", conn.createStatement().executeQuery(sqlCount));
		final boolean_ result = __("result", resultSetCount.next());
		final int totalSize = __("totalSize", resultSetCount.getInt(1));
		resultSetCount.close();
		datas.totalSize(totalSize);

		return _return(datas);
	}

	@Override
	public PageList<User> listJdbc(Condition condition, OrderBy orderBy, int start, int max) throws SQLException {

		final PageListImpl<User> datas = __("datas", new_(PageListImpl.class, start, max));
//
//		// @formatter:off
		final String sql =__("sql", sqlHelper.select("id,name,description,createAt,updateAt").from("USER").where(condition).orderby(orderBy).offset(start).max(max).toSQL());
//		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());
		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(mapper.map(resultSet));
		});
		resultSet.close();

		final String sqlCount = __("sqlCount", sqlHelper.select("count(1)").from("USER").toSQL());
		final ResultSet resultSetCount = __("resultSetCount", conn.createStatement().executeQuery(sqlCount));
		final boolean_ result = __("result", resultSetCount.next());
		final int totalSize = __("totalSize", resultSetCount.getInt(1));
		resultSetCount.close();
		datas.totalSize(totalSize);

		return _return(datas);
	}
	
	<T,D> List<T> list_(Class<D>  d){
		return null;
	}
	
	@Override
	public User findByIdJdbc(long id) throws SQLException {

		List<User> datas = __("datas", newlist_(ArrayList.class,User.class));

		// @formatter:off
		PreparedStatement preparedStatement =__("preparedStatement", conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?"));
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = __("resultSet", preparedStatement.executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(mapper.map(resultSet));
		});

		User user = _null("user", User.class);//datas.get(0));
		
		return _return(user);
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		ResultSet rs = _null("rs", ResultSet.class);

		// @formatter:off
		PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement("INSERT INTO USER(name,description,createAt,updateAt) VALUES(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS));
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());

		bindInsertExtend(preparedStatement, 3);

		User user = _null("user", User.class);
		_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
			__(rs, preparedStatement.getGeneratedKeys());
			rs.next();

			__(user, findByIdJdbc(rs.getLong(1)));
		}).else_(c -> {
			__(user, null);
		});
		return _return(user);
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
//		ClassExtend extend = (ClassExtend) findByIdJdbc(data.getId());
//		if (extend.getUpdateAt() == ((ClassExtend) data).getUpdateAt()) {
//			return null;
//		}
//
//		// @formatter:off
//		PreparedStatement preparedStatement = conn.prepareStatement("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?");
//		// @formatter:on
//
//		preparedStatement.setString(1, data.getName());
//		preparedStatement.setString(2, data.getDescription());
//		bindUpdateExtend(preparedStatement, 3);
//		preparedStatement.setLong(4, data.getId());
//
//		if (preparedStatement.executeUpdate() > 0) {
//			return findByIdJdbc(data.getId());
//		}
//		return null;
		return null;
	}

	@Override
	public int deleteByIdJdbc(long key) throws SQLException {
//		// @formatter:off
//		PreparedStatement preparedStatement = conn.prepareStatement("DELETE USER WHERE id=?");
//		// @formatter:on
//
//		Object key = keys[0];
//		preparedStatement.setLong(1, ((Long) key));
//
//		return preparedStatement.executeUpdate();
		return 0;
	}

	public static byte[] dump() {
		return dumpClass(publicClass_("nebula.data.jdbc.UserAutoIncrementJdbcRepository").implements_(JdbcRepository.class, User.class)
				.enterClassBody(), UserAutoIncrementJdbcRepositoryMagicBuilder.class);
	}
}
