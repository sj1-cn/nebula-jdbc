package nebula.data.jdbc;

import static cc1sj.tinyasm.Adv.*;
import static cc1sj.tinyasm.Adv._b;
import static cc1sj.tinyasm.Adv._if;
import static cc1sj.tinyasm.Adv._return;
import static cc1sj.tinyasm.Adv._while;
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
import nebula.commons.list.ListMap;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;

public class JdbcRepositoryMagicBuilder implements JdbcRepository<User> {

	final private Connection conn = private_().field("conn", Connection.class);
//	final private UserExtendJdbcRowMapper mapper = private_().field("mapper", UserExtendJdbcRowMapper.class);
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

//			__(mapper, new_(UserExtendJdbcRowMapper.class));
			__(sqlHelper, new_(SqlHelper.class));

			code.LINE();
			code.RETURN();
		});
	}

	String $clazzRepository;
	Class<?> $clazzEntity;
	Class<?> $clazzEntityImpl;
	EntityDefinition $entityDefinition;
	Arguments arguments = new Arguments();
	private boolean hasAutoIncrment = true;

	public void dumpInit(String clazzRepository, Class<?> classEntity, Class<?> classEntityImpl, EntityDefinition entityDefinition) {
		this.$clazzRepository = clazzRepository;
		this.$clazzEntity = classEntity;
		this.$clazzEntityImpl = classEntityImpl;
		this.$entityDefinition = entityDefinition;
	}

	@Override
	public void setConnection(Connection conn) {
		__(this.conn, conn);
	}

	@Override
	public void initJdbc() throws SQLException {
		final ColumnList columnList = __("columnList", new_(ColumnList.class));

		FieldList $mappers = $entityDefinition.getFieldsAll();

		for (FieldMapper $field : $mappers) {
			columnList.addColumn($field.column.toString());
		}

		_if(isFalse(mergeIfExists(conn, $entityDefinition.getTablename(), columnList))).then(c -> {

			boolean hasAutoIncrment = $mappers.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));

			List<String> keys = $mappers.filter(f -> f.primaryKey).map(f -> f.column.getName());
			List<String> columnDefinitions = $mappers.map(f -> f.column.toSQL());

			String $sql;
			if (hasAutoIncrment) {
				$sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions}))", $entityDefinition.getTablename(), String.join(",", columnDefinitions));
			} else {
				$sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", $entityDefinition.getTablename(), String.join(",", columnDefinitions),
						String.join(",", keys));
			}

			// @formatter:off
			conn.prepareStatement($sql).execute();
			// @formatter:on
		});
	}

	@SuppressWarnings("unused")
	@Override
	public PageList<User> listJdbc(int start, int max) throws SQLException {
		final PageList<User> datas = __(PageList.class, User.class, "datas", new_(PageListImpl.class, User.class, new Object[] { start, max }));

		String $sqlColumns = String.join(",", $entityDefinition.fieldsAll.map(f -> f.column.getName()));

		// @formatter:off
		final String sql = __("sql",sqlHelper.select($sqlColumns).from($entityDefinition.getTablename()).offset(start).max(max).toSQL());
		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),
					resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
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

		String sqlColumns = String.join(",", $entityDefinition.fieldsAll.map(f -> f.column.getName()));
		// @formatter:off
		final String sql =__("sql", sqlHelper.select(sqlColumns).from($entityDefinition.getTablename()).where(condition).orderby(orderBy).offset(start).max(max).toSQL());
		// @formatter:on

		final ResultSet resultSet = __("resultSet", conn.prepareStatement(sql).executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),
					resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
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
		final List<User> datas = __(List.class, User.class, "datas", new_(ArrayList.class, User.class));

		List<Column> $selAllColoumns = $entityDefinition.fieldsAll.map(f -> f.column);
		List<Column> $sqlKeys = $entityDefinition.fieldsAll.filter(f -> f.primaryKey).map(f -> f.column);

		String $sqlQuery = Select.columns(ColumnList.namesOf($selAllColoumns)).from($entityDefinition.tablename).where(ColumnList.namesOf($sqlKeys)).toSQL();

		// @formatter:off
		 PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement($sqlQuery));
		// @formatter:on

		preparedStatement.setLong(1, id);

		ResultSet resultSet = __("resultSet", preparedStatement.executeQuery());

		_while(isTrue(resultSet.next())).block(c -> {
			datas.add(new_(UserExtend.class, Adv.params(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"),
					resultSet.getTimestamp("createAt"), resultSet.getTimestamp("updateAt"))));
		});

		return _return(datas.get(0));
	}

	@Override
	public User insertJdbc(User data) throws SQLException {
		ResultSet resultSet = __("resultSet", null_(ResultSet.class));
		if (hasAutoIncrment) {
			List<String> names = $entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
			List<String> values = $entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
//		{
//			code.define("preparedStatement", PreparedStatement.class);
//			code.line().setNull("preparedStatement");
//
//			code.define("rs", ResultSet.class);
//			code.line().setNull("rs");
//		}
			String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", $entityDefinition.tablename, String.join(",", names), String.join(",", values));
			// @formatter:off
			PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS));
			// @formatter:on

			preparedStatement.setString(1, data.getName());
			preparedStatement.setString(2, data.getDescription());
			// int i = 1;
			// for (FieldMapper fieldMapper : $entityDefinition.fields) {
			// if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {
			// Class<?> jdbcClazz = fieldMapper.clazz;
			// JdbcMapping jdbcMapping = JDBC.map(jdbcClazz);
			//
			// code.line().Var("preparedStatement").Interface(jdbcMapping.setName).invokeVoid(Const(i++),
			// p -> {
			// p.Var("data").Virtual(fieldMapper.getName).Return(fieldMapper.clazz).Invoke();
			// arguments.toJdbcClazz(jdbcClazz, jdbcMapping.jdbcClazz).accept(p);
			// });
			// }
			// }

			bindInsertExtend(preparedStatement, 3);

			_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
				__(resultSet, preparedStatement.getGeneratedKeys());
				resultSet.next();

				_return(findByIdJdbc(resultSet.getLong(1)));
			});

//
		} else {
			List<String> names = $entityDefinition.fieldsAll.map(f -> f.column.getName());
			List<String> values = $entityDefinition.fieldsAll.map(f -> "?");

			String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", $entityDefinition.tablename, String.join(",", names), String.join(",", values));

			// @formatter:off
			PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement(sql));
			// @formatter:on

			preparedStatement.setString(1, data.getName());
			preparedStatement.setString(2, data.getDescription());

//
//		int i = 1;
//		for (FieldMapper field : $entityDefinition.fields) {
//				JdbcMapping jdbcMapping = JDBC.map(field.clazz);
//			
//				code.line().Var("preparedStatement").Interface(jdbcMapping.setName).invokeVoid(Const(i++), p -> {
//					p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
//					arguments.toJdbcClazz(field.clazz, jdbcMapping.jdbcClazz).accept(p);
//				});
//		}

			bindInsertExtend(preparedStatement, 4);
			_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
				_return(findByIdJdbc(data.getId()));
			});

		}

		return _return(null_(User.class));
	}

	@Override
	public User updateJdbc(User data) throws SQLException {
		ClassExtend extend = __(ClassExtend.class, "extend", findByIdJdbc(data.getId()));
		ClassExtend dataExtend = __(ClassExtend.class, "dataExtend", data);
		_if(isEqual(extend.getUpdateAt(), dataExtend.getUpdateAt())).then(c -> {
			_return(null_(User.class));
		});

		FieldList fieldsAll = $entityDefinition.fieldsAll;
		ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);
		ListMap<String, FieldMapper> othersWithoutCreate = fieldsAll.filter(f -> !f.primaryKey && !"createAt".equals(f.fieldName));
		ListMap<String, FieldMapper> othersWithoutExtend = $entityDefinition.fields.filter(f -> !f.primaryKey);

		String sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", $entityDefinition.tablename,
				String.join(",", othersWithoutCreate.map(f -> f.fieldName + "=?")), String.join(" AND ", keys.map(f -> f.fieldName + "=?")));

		// @formatter:off
		PreparedStatement preparedStatement =__("preparedStatement", conn.prepareStatement(sql));
		// @formatter:on

//		// preparedStatement.setString(1, data.getName());
//		int i = 1;
//		for (FieldMapper field : othersWithoutExtend) {
//			JdbcMapping jdbc = JDBC.map(field.clazz);
//
//			code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++), p -> {
//				p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
//				arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
//			});
//
//		}

		preparedStatement.setString(1, data.getName());
		preparedStatement.setString(2, data.getDescription());

		bindUpdateExtend(preparedStatement, 3);

//		// preparedStatement.setLong(3, data.getId());
//		for (FieldMapper field : keys) {
//			
//			JdbcMapping jdbc = JDBC.map(field.clazz);
//		
//			code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++), p -> {
//				p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
//				arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
//			});					
//		}

		preparedStatement.setLong(4, data.getId());

		_if(isGreaterThan(preparedStatement.executeUpdate(), 0)).then(c -> {
			_return(findByIdJdbc(data.getId()));
		});
		return _return(null_(User.class));
	}

	@Override
	public int deleteByIdJdbc(long id) throws SQLException {
		FieldList fieldsAll = $entityDefinition.fieldsAll;

		ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);

		String sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", $entityDefinition.tablename, String.join(" AND ", keys.map(f -> f.column.getName() + "=?")));

//		// @formatter:off
		PreparedStatement preparedStatement = __("preparedStatement", conn.prepareStatement(sql));
//		// @formatter:on
//
		preparedStatement.setLong(1, id);

		return _return(preparedStatement.executeUpdate());
	}

	public static byte[] dump() {
		JdbcRepositoryMagicBuilder magicBuilderProxy = AdvMagic.build("nebula.data.jdbc.UserAutoIncrementJdbcRepository", JdbcRepositoryMagicBuilder.class);

//		magicBuilderProxy.dumpInit("sayNothing");

		return AdvMagic.dump(magicBuilderProxy);
	}

	public static byte[] dump(String clazzRepository2, Class<?> clazzEntity, Class<?> classEntityImpl, EntityDefinition entityDefinition) {

		JdbcRepositoryMagicBuilder magicBuilderProxy = AdvMagic.build("nebula.data.jdbc.UserAutoIncrementJdbcRepository", JdbcRepositoryMagicBuilder.class);

		magicBuilderProxy.dumpInit(clazzRepository2, clazzEntity, classEntityImpl, entityDefinition);

		return AdvMagic.dump(magicBuilderProxy);
	}
}
