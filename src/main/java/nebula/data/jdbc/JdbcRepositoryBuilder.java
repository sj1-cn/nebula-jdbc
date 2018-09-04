package nebula.data.jdbc;

import static nebula.tinyasm.data.CodeHelper.Const;
import static nebula.tinyasm.data.CodeHelper.Var;
import static nebula.tinyasm.data.GenericClazz.generic;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.data.ClassBody;
import nebula.tinyasm.data.ListMap;

public class JdbcRepositoryBuilder extends RepositoryBuilder {

	public JdbcRepositoryBuilder(Arguments arguments) {
		super(arguments);
	}

	ClassBody cw;

	String clazz;

	String clazzTarget;

	String clazzRowMapper;

	public byte[] make(String clazz, String clazzTarget, String clazzRowMapper, String tablename, FieldList mappers) {

		this.clazz = clazz;
		this.clazzTarget = clazzTarget;
		this.clazzRowMapper = clazzRowMapper;

		Class<Long> clazzID = long.class;

		cw = ClassBuilder.make(clazz).implement(JdbcRepository.class, clazzTarget).body();

		cw.field(ACC_PRIVATE, "conn", Connection.class);

		cw.field(ACC_PRIVATE, "mapper", clazzRowMapper);

		constructor();

		setConnection();

		initJdbc(tablename, mappers);

		listJdbc(clazzTarget, mappers, tablename);

		findByIdJdbc(clazzTarget, clazzID, mappers, tablename);

		insertJdbc(mappers, tablename);

		updateJdbc(mappers, tablename);

		deleteJdbc(clazzID, mappers, tablename);

		updateJdbcBridge();

		insertJdbcBridge();

		findByIdJdbcBridge(clazzID);
		return cw.end().toByteArray();
	}

	// @formatter:off
	private void constructor() {
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

	private void listJdbc(String clazzTarget, FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(PageList.class, clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.define("datas", generic(PageList.class, clazzTarget));

				mv.line().init(PageListImpl.class,"start", "max");
				mv.store("datas");

				String sqlColumns = String.join(",", mappers.map(f -> f.column.getName()));
				// String sql = Select.columns("id,name,description").from("user").offset(start).limit(max).toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const(sqlColumns))
					.virtual("from").reTurn(Select.class).invoke(Const(tablename))
					.virtual("offset").reTurn(Select.class).invoke("start")
					.virtual("max").reTurn(Select.class).invoke("max")
					.virtual("toSQL").reTurn(String.class).invoke()
					.setTo("sql");

				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke("sql")
					.inter("executeQuery").reTurn(ResultSet.class).invoke()
					.setTo("resultSet");

				mv.line().wHile(cause -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), block -> {
					mv.line().load("datas").inter("add").parameter(Object.class).reTurn(boolean.class)
						.invoke(p -> p.load("mapper").virtual("map").reTurn(clazzTarget).invoke("resultSet"))
						.pop();
				});
				mv.line().load("resultSet").inter("close").invokeVoid();

				//String sqlCount = Select.columns("count(1)").from("user").toSQL();
				mv.line().clazz(Select.class).call("columns").parameter(String.class).reTurn(Select.class).invoke(Const("count(1)"))
					.virtual("from").reTurn(Select.class).invoke(Const(tablename))
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

	private void findByIdJdbc(String clazzTarget, Class<Long> clazzID, FieldList mappers, String tablename) {
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

				List<Column> all = mappers.map(f -> f.column);
				List<Column> keys = mappers.filter(f -> f.primaryKey).map(f -> f.column);

				String sql = Select.columns(ColumnList.namesOf(all)).from(tablename).where(ColumnList.namesOf(keys)).toSQL();

				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						mv.line().load("keys").loadElement(j++).setTo("key");
						mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++),p->{

							mv.load("key").checkcastAndUnbox(fieldMapper.clazz);							
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(mv);
						});
					}
				}

				mv.line().load("preparedStatement").inter("executeQuery").reTurn(ResultSet.class).invoke().setTo("resultSet");

				mv.line().wHile(f -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), b -> {
					mv.line().load("datas").inter("add").parameter(Object.class).reTurn(boolean.class).invoke(
							p0 -> mv.load("mapper").virtual("map").reTurn(clazzTarget).invoke("resultSet"))
						.pop();
				});

				mv.line().load("datas").inter("get").reTurn(Object.class).invoke(Const(0))
					.checkcast(clazzTarget).returnValue();
			});
	}

	private void insertJdbc(FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "insertJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {

				boolean hasAutoIncrment = mappers.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));
				FieldMapper keyMapper = mappers.filter(f -> f.isPrimaryKey()).get(0);

				if (hasAutoIncrment) {
					List<String> names = mappers.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
					List<String> values = mappers.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
					{
						mv.define("preparedStatement", PreparedStatement.class);
						mv.line().setNull("preparedStatement");

						mv.define("rs", ResultSet.class);
						mv.line().setNull("rs");
					}
					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", tablename, String.join(",", names),
							String.join(",", values));

					mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql),Const(PreparedStatement.RETURN_GENERATED_KEYS))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : mappers) {
						if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {
							{
								Class<?> jdbcClazz = fieldMapper.clazz;
								JdbcMapping jdbcType = JDBC.mapJavaClazz2JdbcMapping.get(jdbcClazz.getName());
							
								mv.line().load("preparedStatement").inter(jdbcType.setName).invokeVoid(Const(i++), p -> {
									p.load("data").virtual(fieldMapper.getName).reTurn(fieldMapper.clazz).invoke();
									arguments.toJdbcClazz(jdbcClazz, jdbcType.jdbcClazz).accept(p);
								});
							}
						}
					}

					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						// rs = preparedStatement.getGeneratedKeys();
						m.line().load("preparedStatement").inter("getGeneratedKeys").reTurn(ResultSet.class).invoke().setTo("rs");
						// preparedStatement
						m.line().load("rs").inter("next").reTurn(boolean.class).invoke().pop();

						// findById(new Object[] { rs.getLong(1) });
						m.line().load("this").virtual("findById").parameter(Object.class, true).reTurn(Object.class).invoke(p -> {
								m.newarray(Object.class, 1);

								m.dup().setElement(0, e -> {
									JdbcMapping jdbcType = JDBC.mapJavaClazz2JdbcMapping.get(keyMapper.clazz.getName());

									// rs.getLong(1)
									m.load("rs").inter(jdbcType.getname).parameter(int.class).reTurn(jdbcType.jdbcClazz).invoke(Const(1));

									arguments.fromJdbcClazz(keyMapper.clazz, jdbcType.jdbcClazz).accept(m);
									
									mv.topInstance().boxWhenNeed();
								});
							}).checkcast(clazzTarget).returnValue();
					});

					mv.line().returnNull();

				} else {
					List<String> names = mappers.map(f -> f.column.getName());
					List<String> values = mappers.map(f -> "?");

					mv.define("preparedStatement", PreparedStatement.class);

					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", tablename, String.join(",", names),
							String.join(",", values));

					mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : mappers) {
							Class<?> jdbcClazz = fieldMapper.clazz;
							JdbcMapping jdbcType = JDBC.mapJavaClazz2JdbcMapping.get(jdbcClazz.getName());
						
							mv.line().load("preparedStatement").inter(jdbcType.setName).invokeVoid(Const(i++), p -> {
								p.load("data").virtual(fieldMapper.getName).reTurn(fieldMapper.clazz).invoke();
								arguments.toJdbcClazz(jdbcClazz, jdbcType.jdbcClazz).accept(p);
							});
					}

					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						m.line().load("this").virtual("findById").parameter(Object.class, true).reTurn(Object.class).invoke(p -> {
								p.newarray(Object.class, mappers.filter(f -> f.primaryKey).size());
								int j = 0;
	
								for (FieldMapper field : mappers) {
									if (field.primaryKey) {
										p.dup().setElement(j++,	p0 -> m.load("data").virtual(field.getName).reTurn(field.clazz).invoke().boxWhenNeed());
									}
								}
							}).checkcast(clazzTarget).returnValue();
					});
					mv.line().returnNull();
				}
			});
	}

	private void updateJdbc(FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "updateJdbc")
			.parameter("data", clazzTarget)
			.reTurn(clazzTarget)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				ListMap<String, FieldMapper> keys = mappers.filter(f -> f.primaryKey);
				ListMap<String, FieldMapper> others = mappers.filter(f -> !f.primaryKey);

				String sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", tablename,
						String.join(",", others.map(f -> f.fieldName + "=?")), String.join(" AND ", keys.map(f -> f.fieldName + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET
				// name=?,description=? WHERE id=?");
				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				// preparedStatement.setString(1, data.getName());
				int i = 1;
				for (FieldMapper field : others) {
					JdbcMapping jdbc = JDBC.map(field.clazz);
				
					mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++), p -> {
						p.load("data").virtual(field.getName).reTurn(field.clazz).invoke();
						arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(p);
					});
					
				}

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
					m.line().load("this").virtual("findById").parameter(Object.class, true).reTurn(Object.class).invoke(p -> {
							// (data.getId())
							mv.newarray(Object.class, keys.size());
							int j = 0;
							for (FieldMapper field : keys) {
								mv.dup().setElement(j++,
									x -> mv.load("data").virtual(field.getName).reTurn(field.clazz).invoke().boxWhenNeed());
							}
						}).checkcast(clazzTarget).returnValue();
				});
				// return null;
				mv.line().returnNull();

			});
	}

	private void deleteJdbc(Class<Long> clazzID, FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "deleteJdbc").ACC_PUBLIC().ACC_VARARGS()
			.parameter("keys", generic(Object.class, true))
			.reTurn(int.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);

				ListMap<String, FieldMapper> keys = mappers.filter(f -> f.primaryKey);

				String sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", tablename,
						String.join(" AND ", keys.map(f -> f.column.getName() + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("DELETE user
				// WHERE id=?");
				mv.line().load("conn").inter("prepareStatement").reTurn(PreparedStatement.class).invoke(Const(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						JdbcMapping jdbc = JDBC.map(fieldMapper.clazz);

						mv.line().load("keys").loadElement(j++).setTo("key");
						mv.line().load("preparedStatement").inter(jdbc.setName).invokeVoid(Const(i++),p->{
							mv.load("key").checkcastAndUnbox(fieldMapper.clazz);							
							arguments.toJdbcClazz(fieldMapper.clazz, jdbc.jdbcClazz).accept(mv);
						});
					}
				}

				mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke()
					.returnValue();

			});
	}

	private void insertJdbcBridge() {
		cw.method("insertJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("insertJdbc").reTurn(clazzTarget).invoke(f -> f.load("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void updateJdbcBridge() {
		cw.method("updateJdbc").bridge()
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("updateJdbc").reTurn(clazzTarget).invoke(f -> f.load("data").checkcast(clazzTarget)).returnValue();
			});
	}

	private void findByIdJdbcBridge(Class<Long> clazzID) {
		cw.method("findByIdJdbc").bridge()
			.parameter("keys", generic(Object.class, true))
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.friendly(mv -> {
				mv.line().load("this").virtual("findByIdJdbc").reTurn(clazzTarget).invoke(f -> f.load("keys")).returnValue();
			});
	}
}
