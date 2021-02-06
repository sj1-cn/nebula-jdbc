package nebula.data.jdbc;

import static nebula.tinyasm.CodeHelper.Const;
import static nebula.tinyasm.CodeHelper.Var;
import static nebula.tinyasm.GenericClazz.generic;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import nebula.commons.list.ListMap;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;
import nebula.tinyasm.BoxUnbox;
import nebula.tinyasm.ClassBody;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.Instance;
import nebula.tinyasm.MethodCodeFriendly;

public class JdbcRepositoryBuilder extends RepositoryBuilder {

	public JdbcRepositoryBuilder(Arguments arguments) {
		super(arguments);
	}

	ClassBody cw;

	public byte[] make(String clazzRepository, String clazzTarget, String clazzExtend, String clazzRowMapper,
			ClazzDefinition clazzDefinition) {

		cw = ClassBuilder.make(clazzRepository).implement(JdbcRepository.class, clazzTarget).body();

		cw.field(ACC_PRIVATE, "conn", Connection.class);

		cw.field(ACC_PRIVATE, "mapper", clazzRowMapper);

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
		cw.method(ACC_PUBLIC, "<init>").code(mv -> {
			mv.line().initThis();
			mv.line().initTo(clazzRowMapper, "mapper");
			mv.returnVoid();
		});
	}

	private void setConnection() {
		cw.method(ACC_PUBLIC, "setConnection").parameter("conn", Connection.class).friendly(mv -> {
			mv.line().putThisFieldWithVar("conn", "conn");
			mv.line().returnVoid();
		});
	}

	private void initJdbc(String tablename, FieldList mappers) {
		cw.method("initJdbc").tHrow(SQLException.class).friendly(mv -> {
			mv.define("columnList", ColumnList.class);
			// ColumnList columnList = new ColumnList();
			mv.line().initTo(ColumnList.class,"columnList");

			for (FieldMapper field : mappers) {
				// columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
				mv.line().load("columnList").virtual("push").parameter(Object.class).invokeVoid(p -> {
					p.clazz(ColumnDefinition.class).call("valueOf").reTurn(ColumnDefinition.class).invoke(Const(field.column.toString()));
				});
			}		
			
			// JDBC.mergeIfExists(conn, "user", columnList)
			mv.line().clazz(JDBC.class).call("mergeIfExists").reTurn(boolean.class).invoke(Var("conn"), Const(tablename), Var("columnList"));

			mv.ifFalse(b -> {// if (!) {
				
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
				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.inter("execute").reTurn(boolean.class).invoke()
					.pop();
			});
			mv.line().returnVoid();
		});
	}

	private void listJdbc(String clazzTarget, String clazzExtend, ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(PageList.class, clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.define("datas", generic(PageList.class, clazzTarget));

				mv.line().init(PageListImpl.class,"start", "max").setTo("datas");

				String sqlColumns = String.join(",", clazzDefinition.fieldsAll.map(f -> f.column.getName()));
				// String sql = Select.columns("id,name,description").from("user").offset(start).limit(max).toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const(sqlColumns))
					.virtual("from").reTurn(Select.class).invoke(Const(clazzDefinition.tablename))
					.virtual("offset").reTurn(Select.class).invoke("start")
					.virtual("max").reTurn(Select.class).invoke("max")
					.virtual("toSQL").reTurn(String.class).invoke()
					.setTo("sql");

				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke("sql")
					.inter("executeQuery").reTurn(ResultSet.class).invoke()
					.setTo("resultSet");

				mv.line().wHile(cause -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), block -> {
					mv.line().load("datas").inter("add").parameter(Object.class).reTurn(boolean.class)
						.invoke(p -> p.load("mapper").virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				});
				mv.line().load("resultSet").inter("close").invokeVoid();

				//String sqlCount = Select.columns("count(1)").from("user").toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const("count(1)"))
					.virtual("from").reTurn(Select.class).invoke(Const(clazzDefinition.tablename))
					.virtual("toSQL").reTurn(String.class).invoke()
					.setTo("sqlCount");
				

				// ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
				mv.line().load("conn").inter("createStatement").reTurn(Statement.class).invoke()
					.inter("executeQuery").reTurn(ResultSet.class).invoke("sqlCount")
					.setTo("resultSetCount");
				
				// resultSetCount.next();
				mv.line().load("resultSetCount").inter("next").reTurn(boolean.class).invoke().pop();

				// int totalSize = resultSetCount.getInt(1);
				mv.line().load("resultSetCount").inter("getInt").reTurn(int.class).invoke(Const(1)).setTo("totalSize");
				
				// resultSetCount.close();
				mv.line().load("resultSetCount").inter("close").invokeVoid();

				//datas.totalSize(totalSize);
				mv.line().load("datas").inter("totalSize").invokeVoid("totalSize");

				mv.line().returnVar("datas");

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
			.friendly(mv -> {
				mv.define("datas", generic(PageList.class, clazzTarget));

				mv.line().init(PageListImpl.class,"start", "max").setTo("datas");

				String sqlColumns = String.join(",", clazzDefinition.fieldsAll.map(f -> f.column.getName()));
				// String sql = Select.columns("id,name,description").from("user").offset(start).limit(max).toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const(sqlColumns))
					.virtual("from").reTurn(Select.class).invoke(Const(clazzDefinition.tablename))
					.virtual("where").reTurn(Select.class).invoke("condition")
					.virtual("orderby").reTurn(Select.class).invoke("orderBy")
					.virtual("offset").reTurn(Select.class).invoke("start")
					.virtual("max").reTurn(Select.class).invoke("max")
					.virtual("toSQL").reTurn(String.class).invoke()
					.setTo("sql");

				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke("sql")
					.inter("executeQuery").reTurn(ResultSet.class).invoke()
					.setTo("resultSet");

				mv.line().wHile(cause -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), block -> {
					mv.line().load("datas").inter("add").parameter(Object.class).reTurn(boolean.class)
						.invoke(p -> p.load("mapper").virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				});
				mv.line().load("resultSet").inter("close").invokeVoid();

				//String sqlCount = Select.columns("count(1)").from("user").toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const("count(1)"))
					.virtual("from").reTurn(Select.class).invoke(Const(clazzDefinition.tablename))
					.virtual("where").reTurn(Select.class).invoke("condition")
					.virtual("toSQL").reTurn(String.class).invoke()
					.setTo("sqlCount");
				

				// ResultSet resultSetCount = conn.createStatement().executeQuery(sqlCount);
				mv.line().load("conn").inter("createStatement").reTurn(Statement.class).invoke()
					.inter("executeQuery").reTurn(ResultSet.class).invoke("sqlCount")
					.setTo("resultSetCount");
				
				// resultSetCount.next();
				mv.line().load("resultSetCount").inter("next").reTurn(boolean.class).invoke().pop();

				// int totalSize = resultSetCount.getInt(1);
				mv.line().load("resultSetCount").inter("getInt").reTurn(int.class).invoke(Const(1)).setTo("totalSize");
				
				// resultSetCount.close();
				mv.line().load("resultSetCount").inter("close").invokeVoid();

				//datas.totalSize(totalSize);
				mv.line().load("datas").inter("totalSize").invokeVoid("totalSize");

				mv.line().returnVar("datas");

			});
	}

	private void findByIdJdbc(String clazzTarget, String clazzExtend, ClazzDefinition clazzDefinition) {
		cw.method("findByIdJdbc")
			.ACC_PUBLIC()
			.ACC_VARARGS()
			.parameter("keys", generic(Object.class, true))
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				mv.define("resultSet", ResultSet.class);
				mv.define("datas", generic(List.class, clazzTarget));

				mv.line().setNew("datas", ArrayList.class);

				List<Column> all = clazzDefinition.fieldsAll.map(f -> f.column);
				List<Column> keys = clazzDefinition.fieldsAll.filter(f -> f.primaryKey).map(f -> f.column);

				String sql = Select.columns(ColumnList.namesOf(all)).from(clazzDefinition.tablename).where(ColumnList.namesOf(keys)).toSQL();

				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : clazzDefinition.fieldsAll) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						mv.line().load("keys").loadElement(j++).setTo("key");
						mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++),p->{
							mv.load("key");
							BoxUnbox.unboxToWhenNeed(fieldMapper.clazz).accept(p);							
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(mv);
						});
					}
				}

				mv.line().load("preparedStatement").inter("executeQuery").reTurn(ResultSet.class).invoke().setTo("resultSet");

				mv.line().wHile(f -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), b -> {
					mv.line().load("datas").inter("add").parameter(Object.class).reTurn(boolean.class).invoke(
							p0 -> mv.load("mapper").virtual("map").reTurn(clazzExtend).invoke("resultSet"))
						.pop();
				});

				mv.line().load("datas").inter("get").reTurn(Object.class).invoke(Const(0))
					.checkcast(clazzTarget).returnValue();
			});
	}

	private void insertJdbc(String clazzTarget, String clazzExtend,ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "insertJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				FieldList fields = clazzDefinition.fields;

				boolean hasAutoIncrment = fields.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));
				FieldMapper keyField = fields.filter(f -> f.isPrimaryKey()).get(0);

				if (hasAutoIncrment) {
					List<String> names = clazzDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
					List<String> values =  clazzDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
					{
						mv.define("preparedStatement", PreparedStatement.class);
						mv.line().setNull("preparedStatement");

						mv.define("rs", ResultSet.class);
						mv.line().setNull("rs");
					}
					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", clazzDefinition.tablename, String.join(",", names),
							String.join(",", values));

					mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql),Const(PreparedStatement.RETURN_GENERATED_KEYS))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : clazzDefinition.fields) {
						if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {							
							Class<?> jdbcClazz = fieldMapper.clazz;
							JdbcMapping jdbcMapping = JDBC.map(jdbcClazz);
						
							mv.line().load("preparedStatement").inter(jdbcMapping.setName).invokeVoid(Const(i++), p -> {
								p.load("data").virtual(fieldMapper.getName).reTurn(fieldMapper.clazz).invoke();
								arguments.toJdbcClazz(jdbcClazz, jdbcMapping.jdbcClazz).accept(p);
							});							
						}
					}
					//bindInsertExtend(preparedStatement, 4);
					mv.line().loadThis().virtual("bindInsertExtend").reTurn(int.class).invoke(Var("preparedStatement"),Const(i)).pop();

					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						// rs = preparedStatement.getGeneratedKeys();
						m.line().load("preparedStatement").inter("getGeneratedKeys").reTurn(ResultSet.class).invoke().setTo("rs");
						// preparedStatement
						m.line().load("rs").inter("next").reTurn(boolean.class).invoke().pop();

						// findById(new Object[] { rs.getLong(1) });
						m.line().load("this").virtual("findByIdJdbc").parameter(Object.class, true).reTurn(clazzTarget).invoke(p -> {
								m.newarray(Object.class, 1);

								m.dup().setElement(0, e -> {
									JdbcMapping jdbcMapping = JDBC.map(keyField.clazz);

									// rs.getLong(1)
									m.load("rs").inter(jdbcMapping.getname).parameter(int.class).reTurn(jdbcMapping.jdbcClazz).invoke(Const(1));

									arguments.fromJdbcClazz(keyField.clazz, jdbcMapping.jdbcClazz).accept(m);
									
									mv.topInstance().boxWhenNeed();
								});
							}).returnValue();
					});

					mv.line().returnNull();

				} else {
					List<String> names = clazzDefinition.fieldsAll.map(f -> f.column.getName());
					List<String> values = clazzDefinition.fieldsAll.map(f -> "?");

					mv.define("preparedStatement", PreparedStatement.class);

					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", clazzDefinition.tablename, String.join(",", names),
							String.join(",", values));

					mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper field : clazzDefinition.fields) {
							JdbcMapping jdbcMapping = JDBC.map(field.clazz);
						
							mv.line().load("preparedStatement").inter(jdbcMapping.setName).invokeVoid(Const(i++), p -> {
								p.load("data").virtual(field.getName).reTurn(field.clazz).invoke();
								arguments.toJdbcClazz(field.clazz, jdbcMapping.jdbcClazz).accept(p);
							});
					}
					//bindInsertExtend(preparedStatement, 4);
					mv.line().loadThis().virtual("bindInsertExtend").reTurn(int.class).invoke(Var("preparedStatement"),Const(i)).pop();
					
					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						invokeFindByid(m, clazzDefinition, clazzTarget, "data").returnValue();
					});
					mv.line().returnNull();
				}
			});
	}

	private Instance invokeFindByid(MethodCodeFriendly m, ClazzDefinition clazzDefinition,String clazzTarget, String varThing) {
		return m.line().load("this").virtual("findByIdJdbc").parameter(Object.class, true).reTurn(clazzTarget).invoke(p -> {
				p.newarray(Object.class, clazzDefinition.fields.filter(f -> f.primaryKey).size());
				int j = 0;

				for (FieldMapper field : clazzDefinition.fields) {
					if (field.primaryKey) {
						p.dup().setElement(j++,	p0 -> m.load(varThing).virtual(field.getName).reTurn(field.clazz).invoke().boxWhenNeed());
					}
				}
			});
	}

	private void updateJdbc(String clazzTarget, String clazzExtend,ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "updateJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				//ClassExtend userLast = (ClassExtend) findByIdJdbc(data.getId());
				invokeFindByid(mv, clazzDefinition, clazzTarget,"data").checkcast(ClassExtend.class).setTo("extend");
				//if (userLast.getUpdateAt() == ((ClassExtend) data).getUpdateAt()) {
				mv.line().ifObjectNotEqual(
						p->mv.load("extend").inter("getUpdateAt").reTurn(Timestamp.class).invoke(),
						p-> p.load("data").checkcast(ClassExtend.class).inter("getUpdateAt").reTurn(Timestamp.class).invoke(), b->{
					//return null;
					b.line().returnNull();
				});			
				
				FieldList fieldsAll = clazzDefinition.fieldsAll;
				ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);
				ListMap<String, FieldMapper> othersWithoutCreate = fieldsAll.filter(f -> !f.primaryKey && !"createAt".equals(f.fieldName));
				ListMap<String, FieldMapper> othersWithoutExtend = clazzDefinition.fields.filter(f -> !f.primaryKey);
				
				mv.define("preparedStatement", PreparedStatement.class);

				String sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", clazzDefinition.tablename,
						String.join(",", othersWithoutCreate.map(f -> f.fieldName + "=?")), String.join(" AND ", keys.map(f -> f.fieldName + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET
				// name=?,description=? WHERE id=?");
				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				// preparedStatement.setString(1, data.getName());
				int i = 1;
				for (FieldMapper field : othersWithoutExtend) {
					JdbcMapping jdbc = JDBC.map(field.clazz);
				
					mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++), p -> {
						p.load("data").virtual(field.getName).reTurn(field.clazz).invoke();
						arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
					});
					
				}

				//bindUpdateExtend(preparedStatement, 3);
				mv.line().loadThis().virtual("bindUpdateExtend").reTurn(int.class).invoke(Var("preparedStatement"),Const(i++)).pop();
				
				// preparedStatement.setLong(3, data.getId());
				for (FieldMapper field : keys) {
					
					JdbcMapping jdbc = JDBC.map(field.clazz);
				
					mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++), p -> {
						p.load("data").virtual(field.getName).reTurn(field.clazz).invoke();
						arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
					});					
				}
				

				// preparedStatement.executeUpdate()
				mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

				mv.ifGreatThan(m -> {// if (preparedStatement.executeUpdate() > 0) {
					// return findById(data.getId());
					invokeFindByid(mv, clazzDefinition, clazzTarget,"data").returnValue();
				});
				// return null;
				mv.line().returnNull();

			});
	}

	private void deleteJdbc(ClazzDefinition clazzDefinition) {
		cw.method(ACC_PUBLIC, "deleteJdbc").ACC_PUBLIC().ACC_VARARGS()
			.parameter("keys", generic(Object.class, true))
			.reTurn(int.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				FieldList fieldsAll = clazzDefinition.fieldsAll;
				
				mv.define("preparedStatement", PreparedStatement.class);

				ListMap<String, FieldMapper> keys = fieldsAll.filter(f -> f.primaryKey);

				String sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", clazzDefinition.tablename,
						String.join(" AND ", keys.map(f -> f.column.getName() + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("DELETE user
				// WHERE id=?");
				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : fieldsAll) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						mv.line().load("keys").loadElement(j++).setTo("key");
						mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++),p->{
							mv.load("key");
							BoxUnbox.unboxToWhenNeed(fieldMapper.clazz).accept(p);									
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(mv);
						});
					}
				}

				mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke()
					.returnValue();

			});
	}

	private void insertJdbcBridge(String clazzTarget) {
		cw.method("insertJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("insertJdbc").reTurn(clazzTarget).invoke(f -> f.load("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void updateJdbcBridge(String clazzTarget) {
		cw.method("updateJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("updateJdbc").reTurn(clazzTarget).invoke(f -> f.load("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void findByIdJdbcBridge(String clazzTarget) {
		cw.method("findByIdJdbc").bridge()
			.parameter("keys", generic(Object.class, true))
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("findByIdJdbc").reTurn(clazzTarget).invoke(f -> f.load("keys")).returnValue();
			});
	}
}
