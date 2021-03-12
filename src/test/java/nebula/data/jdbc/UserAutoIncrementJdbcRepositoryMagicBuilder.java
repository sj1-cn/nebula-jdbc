package nebula.data.jdbc;

import static cc1sj.tinyasm.Adv.__;
import static cc1sj.tinyasm.Adv._b;
import static cc1sj.tinyasm.Adv._if;
import static cc1sj.tinyasm.Adv.*;
import static cc1sj.tinyasm.Adv._return;
import static cc1sj.tinyasm.Adv._while;
import static cc1sj.tinyasm.Adv.dumpClass;
import static cc1sj.tinyasm.Adv.isGreaterThan;
import static cc1sj.tinyasm.Adv.isTrue;
import static cc1sj.tinyasm.Adv.new_;
import static cc1sj.tinyasm.Adv.private_;
import static cc1sj.tinyasm.Adv.public_class_;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cc1sj.tinyasm.AdvClassBuilder;
import cc1sj.tinyasm.boolean_;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;

public class UserAutoIncrementJdbcRepositoryMagicBuilder implements JdbcRepository<User> {

	final private Connection conn = private_().field("conn", Connection.class);
	final private UserExtendJdbcRowMapper mapper = private_().field("mapper", UserExtendJdbcRowMapper.class);
	final private SqlHelper sqlHelper = private_().field("sqlHelper", SqlHelper.class);

//	public UserAutoIncrementJdbcRepositoryMagicBuilder() {
//		mapper = new UserExtendJdbcRowMapper();
//		sqlHelper = new SqlHelper();
//	}
	public void __init_(AdvClassBuilder classBody) {
		classBody.public_().method("<init>").code(code -> {
			code.LINE();
			code.LOAD("this");
			code.SPECIAL(Object.class, "<init>").INVOKE();

			__(mapper, new_(UserExtendJdbcRowMapper.class));
			__(sqlHelper, new_(SqlHelper.class));
		});
	}

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
		if (!mergeIfExists(conn, "USER", columnList)) {
			// @formatter:off
			conn.prepareStatement("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)").execute();
			// @formatter:on
		}
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		PageList<User> datas = __(PageList.class, User.class, "datas", new_(PageListImpl.class, User.class, new Object[] { start, max }));

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
		final boolean_ result = _b("result", resultSetCount.next());
		final int totalSize = __("totalSize", resultSetCount.getInt(1));
		resultSetCount.close();
		datas.totalSize(totalSize);

		return _return(datas);
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(Condition condition, OrderBy orderBy, int start, int max) throws SQLException {

		final PageList<User> datas = __("datas", new_(PageListImpl.class, PageList.class, User.class, params(start, max)));
//
//		// @formatter:off
		final String sql =__("sql", sqlHelper.select("id,name,description,createAt,updateAt").from("USER").where(condition).orderby(orderBy).offset(start).max(max).toSQL());
//		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());
		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(mapper.map(resultSet));
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
		PreparedStatement preparedStatement = __("preparedStatement", null_(PreparedStatement.class));

		List<User> datas = __(List.class, User.class, "datas", new_(ArrayList.class, User.class));

		// @formatter:off
		preparedStatement =__("preparedStatement", conn.prepareStatement("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?"));
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = __("resultSet", preparedStatement.executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(mapper.map(resultSet));
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
		}).else_(c -> {
			_return(null_(User.class));
		});

		return null;
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		ClassExtend extend = __(ClassExtend.class, "extend", findByIdJdbc(data.getId()));
		ClassExtend dataExtend = __(ClassExtend.class, "dataExtend", data);
//		_if(isEqual(extend.getUpdateAt(), dataExtend.getUpdateAt())) {
//			return null;
//		}

		// @formatter:off
		PreparedStatement preparedStatement =__("preparedStatement", conn.prepareStatement("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?"));
		// @formatter:on

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());
		bindUpdateExtend(preparedStatement, 3);
		preparedStatement.setLong(4, data.getId());

		if (preparedStatement.executeUpdate() > 0) {
			return _return(findByIdJdbc(data.getId()));
		}
		return null;
	}

	@Override
	public int deleteByIdJdbc(long key) throws SQLException {
//		// @formatter:off
		PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement("DELETE USER WHERE id=?"));
//		// @formatter:on
//
		preparedStatement.setLong(1, key);

		return _return(preparedStatement.executeUpdate());
	}

	public static byte[] dump() {
		return dumpClass(public_class_("nebula.data.jdbc.UserAutoIncrementJdbcRepository").implements_(JdbcRepository.class, User.class)
				.enterClassBody(), UserAutoIncrementJdbcRepositoryMagicBuilder.class);
	}
}
