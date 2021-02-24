package nebula.data.jdbc;

import static cc1sj.tinyasm.CodeHelper.Const;
import static cc1sj.tinyasm.CodeHelper.Var;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cc1sj.tinyasm.BoxUnbox;
import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.Clazz;
import cc1sj.tinyasm.Instance;
import cc1sj.tinyasm.MethodCodeFriendly;
import nebula.commons.list.ListMap;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;

public class JdbcRepositoryBuilder extends RepositoryBuilder {

	public JdbcRepositoryBuilder(Arguments arguments) {
		super(arguments);
	}

	ClassBody cw;

	public byte[] make(String clazzRepository, String clazzTarget, String clazzExtend, String clazzRowMapper,
			ClazzDefinition clazzDefinition) {

		cw = ClassBuilder.make(clazzRepository).implement(JdbcRepository.class, clazzTarget).body();

		cw.field("conn", Connection.class);

		cw.field("mapper", clazzRowMapper);

		constructor(clazzRowMapper);

		setConnection();

		initJdbc(clazzDefinition.tablename, clazzDefinition.fieldsAll);

		listJdbc(clazzTarget, clazzExtend, clazzDefinition);

		listJdbcCondition(clazzTarget, clazzExtend, clazzDefinition);

		findByIdJdbc(clazzTarget, clazzExtend, clazzDefinition);

		insertJdbc(clazzTarget, clazzExtend, clazzDefinition);

		updateJdbc(clazzTarget, clazzExtend, clazzDefinition);

		deleteJdbc(clazzDefinition);

		updateJdbcBridge(clazzTarget);

		insertJdbcBridge(clazzTarget);

		findByIdJdbcBridge(clazzTarget);
		return cw.end().toByteArray();
	}

	// @formatter:off
	private void constructor(String clazzRowMapper) {
		cw.method(ACC_PUBLIC, "<init>").friendly(code -> {
			code.LINE(17);
			code.LOAD("this");
			code.SPECIAL(Object.class, "<init>").INVOKE();

			code.LINE(19);
			code.LOAD("this");
			code.NEW(clazzRowMapper);
			code.DUP();
			code.SPECIAL(clazzRowMapper, "<init>").INVOKE();
			code.PUTFIELD("mapper", clazzRowMapper);
			code.RETURN();
		});
	}

	private void setConnection() {
		cw.method(ACC_PUBLIC, "setConnection").parameter("conn", Connection.class).code(code -> {
			code.LINE();
			code.LOAD_THIS();
			code.LOAD("conn");
			code.PUTFIELD_OF_THIS("conn");
			code.LINE();
			code.RETURN();
		});
	}

	private void initJdbc(String tablename, FieldList mappers) {
		cw.method("initJdbc").tHrow(SQLException.class).friendly(code -> {
			code.define("columnList", ColumnList.class);
			// ColumnList columnList = new ColumnList();
			code.line().initTo(ColumnList.class,"columnList");

			for (FieldMapper field : mappers) {
				// columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
				code.line().Var("columnList").Virtual("push").parameter(Object.class).invokeVoid(p -> {
					p.clazz(ColumnDefinition.class).call("valueOf").Return(ColumnDefinition.class).Invoke(Const(field.column.toString()));
				});
			}		
			
			// JDBC.mergeIfExists(conn, "user", columnList)
			code.line().clazz(JDBC.class).call("mergeIfExists").Return(boolean.class).invoke(Var("conn"), Const(tablename), Var("columnList"));

			code.ifFalse(b -> {// if (!) {
				
				boolean hasAutoIncrment = mappers.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));

				List<String> keys = mappers.filter(f -> f.primaryKey).map(f -> f.column.getName());
				List<String> columnDefinitions = mappers.map(f -> f.column.toSQL());
				

				String sql;
				if (hasAutoIncrment) {
					sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions}))", tablename, String.join(",", columnDefinitions));
				} else {
					sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", tablename,
							String.join(",", columnDefinitions), String.join(",", keys));
				}

				// PreparedStatement preparedStatement = conn.prepareStatement(sql);
				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).Invoke(Const(sql))
					.Interface("execute").Return(boolean.class).Invoke()
					.pop();
			});
			code.line().returnVoid();
		});
	}

	private void listJdbc(String clazzTarget, String clazzExtend, ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(PageList.class, clazzTarget)
			.tHrow(SQLException.class)
			.friendly(code -> {
				String[] genericParameterClazz = { clazzTarget };
				code.define("datas", Clazz.of(PageList.class.getName(), genericParameterClazz));

				code.line().init(PageListImpl.class,"start", "max").setTo("datas");

				String sqlColumns = String.join(",", clazzDefinition.fieldsAll.map(f -> f.column.getName()));
				// String sql = Select.columns("id,name,description").from("user").offset(start).limit(max).toSQL();
				code.line().clazz(Select.class).call("columns").parameter(String.class).Return(Select.class).Invoke(Const(sqlColumns))
					.Virtual("from").Return(Select.class).Invoke(Const(clazzDefinition.tablename))
					.Virtual("offset").Return(Select.class).invoke("start")
					.Virtual("max").Return(Select.class).invoke("max")
					.Virtual("toSQL").Return(String.class).Invoke()
					.setTo("sql");

				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).invoke("sql")
					.Interface("executeQuery").Return(ResultSet.class).Invoke()
					.setTo("resultSet");

				code.line().wHile(cause -> code.Var("resultSet").Interface("next").Return(boolean.class).Invoke(), block -> {
					code.line().Var("datas").Interface("add").parameter(Object.class).Return(boolean.class)
						.Invoke(p -> p.Var("mapper").Virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				});
				code.line().Var("resultSet").Interface("close").InvokeVoid();

				//String sqlCount = Select.columns("count(1)").from("user").toSQL();
				code.line().clazz(Select.class).call("columns").parameter(String.class).Return(Select.class).Invoke(Const("count(1)"))
					.Virtual("from").Return(Select.class).Invoke(Const(clazzDefinition.tablename))
					.Virtual("toSQL").Return(String.class).Invoke()
					.setTo("sqlCount");
				

				// ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
				code.line().Var("conn").Interface("createStatement").Return(Statement.class).Invoke()
					.Interface("executeQuery").Return(ResultSet.class).invoke("sqlCount")
					.setTo("resultSetCount");
				
				// resultSetCount.next();
				code.line().Var("resultSetCount").Interface("next").Return(boolean.class).Invoke().pop();

				// int totalSize = resultSetCount.getInt(1);
				code.line().Var("resultSetCount").Interface("getInt").Return(int.class).Invoke(Const(1)).setTo("totalSize");
				
				// resultSetCount.close();
				code.line().Var("resultSetCount").Interface("close").InvokeVoid();

				//datas.totalSize(totalSize);
				code.line().Var("datas").Interface("totalSize").InvokeVoid("totalSize");

				code.line().returnVar("datas");

			});
	}
	

	private void listJdbcCondition(String clazzTarget, String clazzExtend, ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("condition", Condition.class)
			.parameter("orderBy", OrderBy.class)
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(PageList.class, clazzTarget)
			.tHrow(SQLException.class)
			.friendly(code -> {
				String[] genericParameterClazz = { clazzTarget };
				code.define("datas", Clazz.of(PageList.class.getName(), genericParameterClazz));

				code.line().init(PageListImpl.class,"start", "max").setTo("datas");

				String sqlColumns = String.join(",", clazzDefinition.fieldsAll.map(f -> f.column.getName()));
				// String sql = Select.columns("id,name,description").from("user").offset(start).limit(max).toSQL();
				code.line().clazz(Select.class).call("columns").parameter(String.class).Return(Select.class).Invoke(Const(sqlColumns))
					.Virtual("from").Return(Select.class).Invoke(Const(clazzDefinition.tablename))
					.Virtual("where").Return(Select.class).invoke("condition")
					.Virtual("orderby").Return(Select.class).invoke("orderBy")
					.Virtual("offset").Return(Select.class).invoke("start")
					.Virtual("max").Return(Select.class).invoke("max")
					.Virtual("toSQL").Return(String.class).Invoke()
					.setTo("sql");

				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).invoke("sql")
					.Interface("executeQuery").Return(ResultSet.class).Invoke()
					.setTo("resultSet");

				code.line().wHile(cause -> code.Var("resultSet").Interface("next").Return(boolean.class).Invoke(), block -> {
					code.line().Var("datas").Interface("add").parameter(Object.class).Return(boolean.class)
						.Invoke(p -> p.Var("mapper").Virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				});
				code.line().Var("resultSet").Interface("close").InvokeVoid();

				//String sqlCount = Select.columns("count(1)").from("user").toSQL();
				code.line().clazz(Select.class).call("columns").parameter(String.class).Return(Select.class).Invoke(Const("count(1)"))
					.Virtual("from").Return(Select.class).Invoke(Const(clazzDefinition.tablename))
					.Virtual("where").Return(Select.class).invoke("condition")
					.Virtual("toSQL").Return(String.class).Invoke()
					.setTo("sqlCount");
				

				// ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
				code.line().Var("conn").Interface("createStatement").Return(Statement.class).Invoke()
					.Interface("executeQuery").Return(ResultSet.class).invoke("sqlCount")
					.setTo("resultSetCount");
				
				// resultSetCount.next();
				code.line().Var("resultSetCount").Interface("next").Return(boolean.class).Invoke().pop();

				// int totalSize = resultSetCount.getInt(1);
				code.line().Var("resultSetCount").Interface("getInt").Return(int.class).Invoke(Const(1)).setTo("totalSize");
				
				// resultSetCount.close();
				code.line().Var("resultSetCount").Interface("close").InvokeVoid();

				//datas.totalSize(totalSize);
				code.line().Var("datas").Interface("totalSize").InvokeVoid("totalSize");

				code.line().returnVar("datas");

			});
	}

	private void findByIdJdbc(String clazzTarget, String clazzExtend, ClazzDefinition clazzDefinition) {
		cw.method("findByIdJdbc")
			.ACC_PUBLIC()
			.ACC_VARARGS()
			.parameter("keys", Object.class, true)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(code -> {
				code.define("preparedStatement", PreparedStatement.class);
				code.define("resultSet", ResultSet.class);
				code.define("datas", Clazz.of(List.class, clazzTarget));

				code.line().setNew("datas", ArrayList.class);

				List<Column> all = clazzDefinition.fieldsAll.map(f -> f.column);
				List<Column> keys = clazzDefinition.fieldsAll.filter(f -> f.primaryKey).map(f -> f.column);

				String sql = Select.columns(ColumnList.namesOf(all)).from(clazzDefinition.tablename).where(ColumnList.namesOf(keys)).toSQL();

				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).Invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : clazzDefinition.fieldsAll) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						code.line().Var("keys").loadElement(j++).setTo("key");
						code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++),p->{
							code.Var("key");
							BoxUnbox.unboxToWhenNeed(fieldMapper.clazz).accept(p);							
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(code);
						});
					}
				}

				code.line().Var("preparedStatement").Interface("executeQuery").Return(ResultSet.class).Invoke().setTo("resultSet");

				code.line().wHile(f -> code.Var("resultSet").Interface("next").Return(boolean.class).Invoke(), b -> {
					code.line().Var("datas").Interface("add").parameter(Object.class).Return(boolean.class).Invoke(
							p0 -> code.Var("mapper").Virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				}); 

				code.line().Var("datas").Interface("get").Return(Object.class).Invoke(Const(0))
					.checkcast(clazzTarget).returnValue();
			});
	}

	private void insertJdbc(String clazzTarget, String clazzExtend,ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "insertJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(code -> {
				FieldList fields = clazzDefinition.fields;

				boolean hasAutoIncrment = fields.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));
				FieldMapper keyField = fields.filter(f -> f.isPrimaryKey()).get(0);

				if (hasAutoIncrment) {
					List<String> names = clazzDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
					List<String> values =  clazzDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
					{
						code.define("preparedStatement", PreparedStatement.class);
						code.line().setNull("preparedStatement");

						code.define("rs", ResultSet.class);
						code.line().setNull("rs");
					}
					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", clazzDefinition.tablename, String.join(",", names),
							String.join(",", values));

					code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).invoke(Const(sql),Const(PreparedStatement.RETURN_GENERATED_KEYS))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : clazzDefinition.fields) {
						if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {							
							Class<?> jdbcClazz = fieldMapper.clazz;
							JdbcMapping jdbcMapping = JDBC.map(jdbcClazz);
						
							code.line().Var("preparedStatement").Interface(jdbcMapping.setName).invokeVoid(Const(i++), p -> {
								p.Var("data").Virtual(fieldMapper.getName).Return(fieldMapper.clazz).Invoke();
								arguments.toJdbcClazz(jdbcClazz, jdbcMapping.jdbcClazz).accept(p);
							});							
						}
					}
					//bindInsertExtend(preparedStatement, 4);
					code.line().loadThis().Virtual("bindInsertExtend").Return(int.class).invoke(Var("preparedStatement"),Const(i)).pop();

					code.line().Var("preparedStatement").Interface("executeUpdate").Return(int.class).Invoke();

					code.ifGreatThan(m -> {
						// rs = preparedStatement.getGeneratedKeys();
						m.line().Var("preparedStatement").Interface("getGeneratedKeys").Return(ResultSet.class).Invoke().setTo("rs");
						// preparedStatement
						m.line().Var("rs").Interface("next").Return(boolean.class).Invoke().pop();

						// findById(new Object[] { rs.getLong(1) });
						m.line().Var("this").Virtual("findByIdJdbc").parameter(Object.class, true).reTurn(clazzTarget).Invoke(p -> {
								m.newarray(Object.class, 1);

								m.dup().setElement(0, e -> {
									JdbcMapping jdbcMapping = JDBC.map(keyField.clazz);

									// rs.getLong(1)
									m.Var("rs").Interface(jdbcMapping.getname).parameter(int.class).Return(jdbcMapping.jdbcClazz).Invoke(Const(1));

									arguments.fromJdbcClazz(keyField.clazz, jdbcMapping.jdbcClazz).accept(m);
									
									code.topInstance().boxWhenNeed();
								});
							}).returnValue();
					});

					code.line().returnNull();

				} else {
					List<String> names = clazzDefinition.fieldsAll.map(f -> f.column.getName());
					List<String> values = clazzDefinition.fieldsAll.map(f -> "?");

					code.define("preparedStatement", PreparedStatement.class);

					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", clazzDefinition.tablename, String.join(",", names),
							String.join(",", values));

					code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).Invoke(Const(sql))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper field : clazzDefinition.fields) {
							JdbcMapping jdbcMapping = JDBC.map(field.clazz);
						
							code.line().Var("preparedStatement").Interface(jdbcMapping.setName).invokeVoid(Const(i++), p -> {
								p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
								arguments.toJdbcClazz(field.clazz, jdbcMapping.jdbcClazz).accept(p);
							});
					}
					//bindInsertExtend(preparedStatement, 4);
					code.line().loadThis().Virtual("bindInsertExtend").Return(int.class).invoke(Var("preparedStatement"),Const(i)).pop();
					
					code.line().Var("preparedStatement").Interface("executeUpdate").Return(int.class).Invoke();

					code.ifGreatThan(m -> {
						invokeFindByid(m, clazzDefinition, clazzTarget, "data").returnValue();
					});
					code.line().returnNull();
				}
			});
	}

	private Instance invokeFindByid(MethodCodeFriendly m, ClazzDefinition clazzDefinition,String clazzTarget, String varThing) {
		return m.line().Var("this").Virtual("findByIdJdbc").parameter(Object.class, true).reTurn(clazzTarget).Invoke(p -> {
				p.newarray(Object.class, clazzDefinition.fields.filter(f -> f.primaryKey).size());
				int j = 0;

				for (FieldMapper field : clazzDefinition.fields) {
					if (field.primaryKey) {
						p.dup().setElement(j++,	p0 -> m.Var(varThing).Virtual(field.getName).Return(field.clazz).Invoke().boxWhenNeed());
					}
				}
			});
	}

	private void updateJdbc(String clazzTarget, String clazzExtend,ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "updateJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(code -> {
				//ClassExtend userLast = (ClassExtend) findByIdJdbc(data.getId());
				invokeFindByid(code, clazzDefinition, clazzTarget,"data").checkcast(ClassExtend.class).setTo("extend");
				//if (userLast.getUpdateAt() == ((ClassExtend) data).getUpdateAt()) {
				code.line().ifObjectNotEqual(
						p->code.Var("extend").Interface("getUpdateAt").Return(Timestamp.class).Invoke(),
						p-> p.Var("data").checkcast(ClassExtend.class).Interface("getUpdateAt").Return(Timestamp.class).Invoke(), b->{
					//return null;
					b.line().returnNull();
				});			
				
				FieldList fieldsAll = clazzDefinition.fieldsAll;
				ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);
				ListMap<String, FieldMapper> othersWithoutCreate = fieldsAll.filter(f -> !f.primaryKey && !"createAt".equals(f.fieldName));
				ListMap<String, FieldMapper> othersWithoutExtend = clazzDefinition.fields.filter(f -> !f.primaryKey);
				
				code.define("preparedStatement", PreparedStatement.class);

				String sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", clazzDefinition.tablename,
						String.join(",", othersWithoutCreate.map(f -> f.fieldName + "=?")), String.join(" AND ", keys.map(f -> f.fieldName + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET
				// name=?,description=? WHERE id=?");
				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).Invoke(Const(sql))
					.setTo("preparedStatement");

				// preparedStatement.setString(1, data.getName());
				int i = 1;
				for (FieldMapper field : othersWithoutExtend) {
					JdbcMapping jdbc = JDBC.map(field.clazz);
				
					code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++), p -> {
						p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
						arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
					});
					
				}

				//bindUpdateExtend(preparedStatement, 3);
				code.line().loadThis().Virtual("bindUpdateExtend").Return(int.class).invoke(Var("preparedStatement"),Const(i++)).pop();
				
				// preparedStatement.setLong(3, data.getId());
				for (FieldMapper field : keys) {
					
					JdbcMapping jdbc = JDBC.map(field.clazz);
				
					code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++), p -> {
						p.Var("data").Virtual(field.getName).Return(field.clazz).Invoke();
						arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
					});					
				}
				

				// preparedStatement.executeUpdate()
				code.line().Var("preparedStatement").Interface("executeUpdate").Return(int.class).Invoke();

				code.ifGreatThan(m -> {// if (preparedStatement.executeUpdate() > 0) {
					// return findById(data.getId());
					invokeFindByid(code, clazzDefinition, clazzTarget,"data").returnValue();
				});
				// return null;
				code.line().returnNull();

			});
	}

	private void deleteJdbc(ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "deleteJdbc").ACC_PUBLIC().ACC_VARARGS()
			.parameter("keys", Object.class, true)
			.reTurn(int.class)
			.tHrow(SQLException.class)
			.friendly(code -> {
				FieldList fieldsAll = clazzDefinition.fieldsAll;
				
				code.define("preparedStatement", PreparedStatement.class);

				ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);

				String sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", clazzDefinition.tablename,
						String.join(" AND ", keys.map(f -> f.column.getName() + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("DELETE user
				// WHERE id=?");
				code.line().Var("conn").Interface("prepareStatement").Return(PreparedStatement.class).Invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : fieldsAll) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						code.line().Var("keys").loadElement(j++).setTo("key");
						code.line().Var("preparedStatement").Interface(jdbc.setName).invokeVoid(Const(i++),p->{
							code.Var("key");
							BoxUnbox.unboxToWhenNeed(fieldMapper.clazz).accept(p);									
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(code);
						});
					}
				}

				code.line().Var("preparedStatement").Interface("executeUpdate").Return(int.class).Invoke()
					.returnValue();

			});
	}

	private void insertJdbcBridge(String clazzTarget) {
		cw.method("insertJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(code -> {
				code.line().Var("this").Virtual("insertJdbc").reTurn(clazzTarget).Invoke(f -> f.Var("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void updateJdbcBridge(String clazzTarget) {
		cw.method("updateJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(code -> {
				code.line().Var("this").Virtual("updateJdbc").reTurn(clazzTarget).Invoke(f -> f.Var("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void findByIdJdbcBridge(String clazzTarget) {
		cw.method("findByIdJdbc").bridge()
			.parameter("keys", Object.class, true)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(code -> {
				code.line().Var("this").Virtual("findByIdJdbc").reTurn(clazzTarget).Invoke(f -> f.Var("keys")).returnValue();
			});
	}
}
