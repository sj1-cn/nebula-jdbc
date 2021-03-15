package nebula.data.jdbc;

import static cc1sj.tinyasm.Adv.__;
import static cc1sj.tinyasm.Adv._b;
import static cc1sj.tinyasm.Adv._if;
import static cc1sj.tinyasm.Adv._return;
import static cc1sj.tinyasm.Adv._while;
import static cc1sj.tinyasm.Adv.isEqual;
import static cc1sj.tinyasm.Adv.isFalse;
import static cc1sj.tinyasm.Adv.isGreaterThan;
import static cc1sj.tinyasm.Adv.isTrue;
import static cc1sj.tinyasm.Adv.new_;
import static cc1sj.tinyasm.Adv.null_;
import static cc1sj.tinyasm.Adv.params;
import static cc1sj.tinyasm.Adv.private_;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cc1sj.tinyasm.Adv;
import cc1sj.tinyasm.AdvClassBuilder;
import cc1sj.tinyasm.AdvMagic;
import cc1sj.tinyasm.boolean_;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;

public class UserAutoIncrementJdbcRepositoryMagicBuilder implements JdbcRepository<User> {

	private Connection conn;// = private_().field("conn", Connection.class);
	private SqlHelper sqlHelper;// = private_().field("sqlHelper", SqlHelper.class);

	// 这个将来要取消掉
	public void __init_fields(AdvClassBuilder classBody) {
		conn = private_().field("conn", Connection.class);
		sqlHelper = private_().field("sqlHelper", SqlHelper.class);
	}

	public void __init_(AdvClassBuilder classBody) {
		classBody.public_().method("<init>").code(code -> {
			code.LINE();
			code.LOAD("this");
			code.SPECIAL(Object.class, "<init>").INVOKE();

//			__(mapper, new_(UserExtendJdbcRowMapper.class));
			__(sqlHelper, new_(SqlHelper.class));

			code.LINE();
			code.RETURN();
		});
	}

	String clazzRepository;
	String clazzTarget;
	String clazzExtend;
	EntityDefinition clazzDefinition;

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
		_if(isFalse(mergeIfExists(conn, "USER", columnList))).then(c -> {
			// @formatter:off
			conn.prepareStatement("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
			// @formatter:on
		});
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		final PageList<User> datas = __(PageList.class, User.class, "datas", new_(PageListImpl.class, User.class, new Object[] { start, max }));

		// @formatter:off
		final String sql = __("sql",sqlHelper.select("id,name,description,createAt,updateAt").from("USER").offset(start).max(max).toSQL());
		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
		});
		resultSet.close();

		final String sqlCount = __("sqlCount", sqlHelper.select("count(1)").from("USER").toSQL());
		final ResultSet resultSetCount = __("resultSetCount", conn.createStatement().executeQuery(sqlCount));
		final boolean_ result = _b("result", resultSetCount.next());
		final int totalSize = __("totalSize", resultSetCount.getInt(1));
		resultSetCount.close();
		datas.totalSize(totalSize);

		return _return(datas);
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(Condition condition, OrderBy orderBy, int start, int max) throws SQLException {
		final PageList<User> datas = __(PageList.class, User.class, "datas", new_(PageListImpl.class, User.class, params(start, max)));

		// @formatter:off
		final String sql =__("sql", sqlHelper.select("id,name,description,createAt,updateAt").from("USER").where(condition).orderby(orderBy).offset(start).max(max).toSQL());
		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
		});
		resultSet.close();

		final String sqlCount = __("sqlCount", sqlHelper.select("count(1)").from("USER").where(condition).toSQL());
		final ResultSet resultSetCount = __("resultSetCount", conn.createStatement().executeQuery(sqlCount));
		final boolean_ result = _b("result", resultSetCount.next());
		final int totalSize = __("totalSize", resultSetCount.getInt(1));
		resultSetCount.close();
		datas.totalSize(totalSize);

		return _return(datas);
	}

	@Override
	public User findByIdJdbc(long id) throws SQLException {
		List<User> datas = __(List.class, User.class, "datas", new_(ArrayList.class, User.class));

		// @formatter:off
		 PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?"));
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = __("resultSet", preparedStatement.executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
		});

		return _return(datas.get(0));
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		ResultSet resultSet = __("resultSet", null_(ResultSet.class));

		// @formatter:off
		PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement("INSERT INTO USER(name,description,createAt,updateAt) VALUES(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS));
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());

		bindInsertExtend(preparedStatement, 3);

		_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
			__(resultSet, preparedStatement.getGeneratedKeys());
			resultSet.next();

			_return(findByIdJdbc(resultSet.getLong(1)));
		});

		return _return(null_(User.class));
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		ClassExtend extend = __(ClassExtend.class, "extend", findByIdJdbc(data.getId()));
		ClassExtend dataExtend = __(ClassExtend.class, "dataExtend", data);
		_if(isEqual(extend.getUpdateAt(), dataExtend.getUpdateAt())).then(c -> {
			_return(null_(User.class));
		});

		// @formatter:off
		PreparedStatement preparedStatement =__("preparedStatement", conn.prepareStatement("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?"));
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());
		bindUpdateExtend(preparedStatement, 3);
		preparedStatement.setLong(4, data.getId());

		_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
			_return(findByIdJdbc(data.getId()));
		});
		return _return(null_(User.class));
	}

	@Override
	public int deleteByIdJdbc(long id) throws SQLException {
//		// @formatter:off
		PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement("DELETE USER WHERE id=?"));
//		// @formatter:on
//
		preparedStatement.setLong(1, id);

		return _return(preparedStatement.executeUpdate());
	}

	public static byte[] dump() {
		return AdvMagic.dump("nebula.data.jdbc.UserAutoIncrementJdbcRepository", UserAutoIncrementJdbcRepositoryMagicBuilder.class);
	}
}
