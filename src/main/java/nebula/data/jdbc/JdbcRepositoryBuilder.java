package nebula.data.jdbc;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;

import nebula.jdbc.builders.queries.Select;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.TypeMapping;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.data.ClassBody;
import nebula.tinyasm.data.GenericClazz;
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

		cw = ClassBuilder.make(clazz).imPlements(JdbcRepository.class, clazzTarget).body();

		cw.field(ACC_PRIVATE, "conn", Connection.class);

		cw.field(ACC_PRIVATE, "mapper", this.clazzRowMapper);

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

	private void constructor() {
		cw.method(ACC_PUBLIC, "<init>").code(mv -> {
			mv.line().initThis();
			mv.line().setNew("mapper", clazzRowMapper);
			mv.returnVoid();
		});
	}

	private void setConnection() {
		cw.method(ACC_PUBLIC, "setConnection").parameter("conn", Connection.class).code(mv -> {
			{
				mv.line().putThisFieldWithVar("conn", "conn");
				mv.line().returnVoid();
			}
		});
	}

	private void initJdbc(String tablename, FieldList mappers) {
		cw.method("initJdbc").tHrow(SQLException.class).code(mv -> {
			mv.define("columnList", ColumnList.class);
			// ColumnList columnList = new ColumnList();
			mv.line().init(ColumnList.class).setTo("columnList");

			for (FieldMapper field : mappers) {
				// columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
				mv.line().load("columnList").virtual("push").parameter(Object.class).invokeVoid(p0 -> {
					p0.clazz(ColumnDefinition.class)
						.call("valueOf")
						.reTurn(ColumnDefinition.class)
						.invoke(p00 -> p00.LOADConst(field.column.toString()));
				});

			}

			// JDBCConfiguration.mergeIfExists(conn, "user", columnList)
			mv.line()
				.clazz(JDBC.class)
				.call("mergeIfExists")
				.reTurn(boolean.class)
				.invoke(m -> m.load("conn"), m -> m.LOADConst(tablename), m -> m.LOAD("columnList"));

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
				mv.line()
					.load("conn")
					.inter("prepareStatement")
					.reTurn(PreparedStatement.class)
					.invoke(m -> m.LOADConst(sql))
					.inter("execute")
					.reTurn(boolean.class)
					.invoke();

				mv.POP();
			});
			mv.line().returnVoid();

		});
	}

	private void listJdbc(String clazzTarget, FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(List.class, clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("datas", GenericClazz.generic(List.class, clazzTarget));
				mv.define("resultSet", ResultSet.class);

				mv.line().setNew("datas", ArrayList.class);

				{
					String sql = JDBC.sql("SELECT ${columns} FROM ${tablename}", String.join(",", mappers.map(f -> f.column.getName())),
							tablename);

					mv.line()
						.load("conn")
						.inter("prepareStatement")
						.reTurn(PreparedStatement.class)
						.invoke(m -> m.LOADConst(sql))
						.inter("executeQuery")
						.reTurn(ResultSet.class)
						.invoke()
						.setTo("resultSet");
				}
				mv.line();
				Label whileStart = mv.codeNewLabel();
				Label whileEnd = mv.codeNewLabel();
				mv.visitLabel(whileStart);
				mv.load("resultSet").inter("next").reTurn(boolean.class).invoke();
				mv.IFEQ(whileEnd);
				{
					mv.line();
					mv.load("datas");
					mv.load("mapper").virtual("map").reTurn(this.clazzTarget).invoke("resultSet");
					mv.INTERFACE(List.class, "add").parameter(Object.class).reTurn(boolean.class).INVOKE();
					mv.POP();

					mv.GOTO(whileStart);
				}
				mv.visitLabel(whileEnd);

				mv.line().RETURN("datas");

			});
	}

	private void findByIdJdbc(String clazzTarget, Class<Long> clazzID, FieldList mappers, String tablename) {
		cw.method("findByIdJdbc")
			.ACC_PUBLIC()
			.ACC_VARARGS()
			.parameter("keys", GenericClazz.generic(Object.class, true))
			.reTurn(this.clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				mv.define("resultSet", ResultSet.class);
				mv.define("datas", GenericClazz.generic(List.class, clazzTarget));

				mv.line().setNew("datas", ArrayList.class);

				List<Column> all = mappers.map(f -> f.column);
				List<Column> keys = mappers.filter(f -> f.primaryKey).map(f -> f.column);

				String sql = Select.columns(ColumnList.namesOf(all)).from(tablename).where(ColumnList.namesOf(keys)).toSQL();

				mv.line()
					.load("conn")
					.inter("prepareStatement")
					.reTurn(PreparedStatement.class)
					.invoke(m -> m.LOADConst(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						mv.line();
						mv.LOAD("preparedStatement");
						mv.LOADConst(i++);

						mv.load("keys").loadElement(j++);

						if (fieldMapper.pojoClazz.isPrimitive()) {
							Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojoClazz);
							mv.CHECKCAST(objectclass);
							arguments.getConvert(objectclass, fieldMapper.pojoClazz).apply(mv);
						} else {
							mv.CHECKCAST(fieldMapper.pojoClazz);
						}

						TypeMapping jdbcType = JDBC.mapJavaClazz2JdbcTypes.get(fieldMapper.pojoClazz.getName());

						if (fieldMapper.pojoClazz != jdbcType.jdbcClazz) {
							arguments.getConvert(fieldMapper.pojoClazz, jdbcType.jdbcClazz).apply(mv);
						}

						mv.INTERFACE(PreparedStatement.class, jdbcType.setname).parameter(int.class, jdbcType.jdbcClazz).INVOKE();
					}
				}

				mv.line().load("preparedStatement").inter("executeQuery").reTurn(ResultSet.class).invoke().setTo("resultSet");

				mv.line().wHile(f -> mv.load("resultSet").inter("next").reTurn(boolean.class).invoke(), b -> {
					mv.line()
						.load("datas")
						.inter("add")
						.parameter(Object.class)
						.reTurn(boolean.class)
						.invoke(p0 -> mv.load("mapper").virtual("map").reTurn(this.clazzTarget).invoke("resultSet"))
						.pop();
				});

				mv.line()
					.load("datas")
					.inter("get")
					.reTurn(Object.class)
					.invoke(m -> m.LOADConst(0))
					.checkcast(this.clazzTarget)
					.returnValue();
			});
	}

	private void insertJdbc(FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "insertJdbc")
			.parameter("data", this.clazzTarget)
			.reTurn(this.clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {

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

					mv.line()
						.load("conn")
						.inter("prepareStatement")
						.reTurn(PreparedStatement.class)
						.invoke(m -> m.LOADConst(sql), m -> m.LOADConst(PreparedStatement.RETURN_GENERATED_KEYS))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : mappers) {
						if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {
							bindField(mv, i++, clazzTarget, fieldMapper);
						}
					}

					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						// rs = preparedStatement.getGeneratedKeys();
						m.line().load("preparedStatement").inter("getGeneratedKeys").reTurn(ResultSet.class).invoke().setTo("rs");
						// preparedStatement
						m.line().load("rs").inter("next").reTurn(boolean.class).invoke().pop();

						// findById(new Object[] { rs.getLong(1) });
						m.line()
							.load("this")
							.virtual("findById")
							.parameter(GenericClazz.generic(Object.class, true))
							.reTurn(Object.class)
							.invoke(p -> {

								m.newarray(Object.class, 1);

								m.dup().setElement(0, e -> {
									TypeMapping jdbcType = JDBC.mapJavaClazz2JdbcTypes.get(keyMapper.pojoClazz.getName());

									// rs.getLong(1)
									m.load("rs")
										.inter(jdbcType.getname)
										.parameter(int.class)
										.reTurn(jdbcType.jdbcClazz)
										.invoke(r -> r.LOADConst(1));

									revertFromJdbc(m, keyMapper.pojoClazz, jdbcType.jdbcClazz);

									box(m, keyMapper.pojoClazz);
								});
							})
							.checkcast(this.clazzTarget)
							.returnValue();
					});

					mv.line().returnNull();

				} else {
					List<String> names = mappers.map(f -> f.column.getName());
					List<String> values = mappers.map(f -> "?");

					mv.define("preparedStatement", PreparedStatement.class);

					String sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", tablename, String.join(",", names),
							String.join(",", values));

					mv.line()
						.load("conn")
						.inter("prepareStatement")
						.reTurn(PreparedStatement.class)
						.invoke(m -> m.LOADConst(sql))
						.setTo("preparedStatement");

					int i = 1;
					for (FieldMapper fieldMapper : mappers) {
						bindField(mv, i++, clazzTarget, fieldMapper);
					}

					mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

					mv.ifGreatThan(m -> {
						m.line();
						m.load("this")
							.virtual("findById")
							.parameter(GenericClazz.generic(Object.class, true))
							.reTurn(Object.class)
							.invoke(p -> {
								p.newarray(Object.class, mappers.filter(f -> f.primaryKey).size());
								int j = 0;

								for (FieldMapper field : mappers) {
									if (field.primaryKey) {
										p.dup()
											.setElement(j++,
													p0 -> m.load("data")
														.virtual(field.pojo_getname)
														.reTurn(field.pojoClazz)
														.invoke()
														.boxWhenNeed());
									}
								}
							});

						m.CHECKCAST(this.clazzTarget);
						m.RETURNTop();
					});
					mv.line().returnNull();

				}

			});

	}

	private void updateJdbc(FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "updateJdbc")
			.parameter("data", this.clazzTarget)
			.reTurn(this.clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				ListMap<String, FieldMapper> keys = mappers.filter(f -> f.primaryKey);
				ListMap<String, FieldMapper> others = mappers.filter(f -> !f.primaryKey);

				String sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", tablename,
						String.join(",", others.map(f -> f.fieldName + "=?")), String.join(" AND ", keys.map(f -> f.fieldName + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user SET
				// name=?,description=? WHERE id=?");
				mv.line()
					.load("conn")
					.inter("prepareStatement")
					.reTurn(PreparedStatement.class)
					.invoke(m -> m.LOADConst(sql))
					.setTo("preparedStatement");

				// preparedStatement.setString(1, data.getName());
				int i = 1;
				for (FieldMapper field : others) {
					bindField(mv, i++, clazzTarget, field);
				}

				// preparedStatement.setLong(3, data.getId());
				for (FieldMapper field : keys) {
					bindField(mv, i++, clazzTarget, field);
				}

				// preparedStatement.executeUpdate()
				mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();

				mv.ifGreatThan(m -> {// if (preparedStatement.executeUpdate() > 0) {
					// return findById(data.getId());
					m.line()
						.load("this")
						.virtual("findById")
						.parameter(GenericClazz.generic(Object.class, true))
						.reTurn(Object.class)
						.invoke(p -> {
							// (data.getId())
							mv.newarray(Object.class, keys.size());
							int j = 0;
							for (FieldMapper fieldMapper : keys) {
								mv.dup()
									.setElement(j++,
											x -> mv.load("data")
												.virtual(fieldMapper.pojo_getname)
												.reTurn(fieldMapper.pojoClazz)
												.invoke()
												.boxWhenNeed());
							}
						})
						.checkcast(this.clazzTarget)
						.returnValue();
				});
				// return null;
				mv.line().returnNull();

			});
	}

	private void deleteJdbc(Class<Long> clazzID, FieldList mappers, String tablename) {
		cw.method(ACC_PUBLIC, "deleteJdbc")
			.ACC_PUBLIC()
			.ACC_VARARGS()
			.parameter("keys", GenericClazz.generic(Object.class, true))
			.reTurn(int.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);

				ListMap<String, FieldMapper> keys = mappers.filter(f -> f.primaryKey);

				String sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", tablename,
						String.join(" AND ", keys.map(f -> f.column.getName() + "=?")));

				// PreparedStatement preparedStatement = conn.prepareStatement("DELETE user
				// WHERE id=?");
				mv.line()
					.load("conn")
					.inter("prepareStatement")
					.reTurn(PreparedStatement.class)
					.invoke(m -> m.LOADConst(sql))
					.setTo("preparedStatement");

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : keys) {
					mv.line();
					mv.LOAD("preparedStatement");
					mv.LOADConst(i++);
					mv.load("keys").loadElement(j++);// .boxWhenNeed().checkcast();

					if (fieldMapper.pojoClazz.isPrimitive()) {
						Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojoClazz);
						mv.CHECKCAST(objectclass);
						arguments.getConvert(objectclass, fieldMapper.pojoClazz).apply(mv);
					} else {
						mv.CHECKCAST(fieldMapper.pojoClazz);
					}

					TypeMapping jdbcType = JDBC.mapJavaClazz2JdbcTypes.get(fieldMapper.pojoClazz.getName());

					if (fieldMapper.pojoClazz != jdbcType.jdbcClazz) {
						arguments.getConvert(fieldMapper.pojoClazz, jdbcType.jdbcClazz).apply(mv);
					}

					mv.INTERFACE(PreparedStatement.class, jdbcType.setname).parameter(int.class, jdbcType.jdbcClazz).INVOKE();

				}

				mv.line().load("preparedStatement").inter("executeUpdate").reTurn(int.class).invoke();
				mv.RETURNTop();

			});
	}

	private void insertJdbcBridge() {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "insertJdbc")
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line()
					.load("this")
					.virtual("insertJdbc")
					.reTurn(this.clazzTarget)
					.invoke(f -> f.load("data").checkcast(this.clazzTarget));
				mv.RETURNTop();
			});
	}

	private void updateJdbcBridge() {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "updateJdbc")
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line()
					.load("this")
					.virtual("updateJdbc")
					.reTurn(this.clazzTarget)
					.invoke(f -> f.load("data").checkcast(this.clazzTarget));
				mv.RETURNTop();
			});
	}

	private void findByIdJdbcBridge(Class<Long> clazzID) {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "findByIdJdbc")
			.parameter("keys", GenericClazz.generic(Object.class, true))
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line().load("this").virtual("findByIdJdbc").reTurn(this.clazzTarget).invoke(f -> f.load("keys"));
				mv.RETURNTop();
			});
	}
}
