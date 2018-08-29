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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Label;

import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.jdbc.builders.schema.JDBCConfiguration;
import nebula.jdbc.builders.schema.JDBCConfiguration.TypeMapping;
import nebula.tinyasm.ClassBuilder;
import nebula.tinyasm.data.ClassBody;
import nebula.tinyasm.data.GenericClazz;
import nebula.tinyasm.data.MethodCode;

public class JdbcRepositoryBuilder {
	Arguments arguments;

	public JdbcRepositoryBuilder(Arguments arguments) {
		this.arguments = arguments;
	}

	ClassBody cw;

	String clazz;

	String clazzTarget;

	String clazzRowMapper;

	public byte[] make(String clazz, String clazzTarget, String clazzRowMapper, String tablename,
			List<FieldMapper> mappers) {

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
			{
				mv.line();
				mv.LOAD("this");
				mv.SPECIAL(Object.class, "<init>").INVOKE();
			}
			{
				mv.line();
				mv.LOAD("this");
				mv.NEW(this.clazzRowMapper);
				mv.DUP();
				mv.SPECIAL(this.clazzRowMapper, "<init>").INVOKE();
				mv.PUTFIELD("mapper", this.clazzRowMapper);
			}
			{
				mv.RETURN();
			}
		});
	}

	private void setConnection() {
		cw.method(ACC_PUBLIC, "setConnection").parameter("conn", Connection.class).code(mv -> {
			{
				mv.line();
				mv.LOAD("this");
				mv.LOAD("conn");
				mv.PUTFIELD("conn", Connection.class);
				mv.line();
				mv.RETURN();
			}
		});
	}

	private void initJdbc(String tablename, List<FieldMapper> mappers) {
		cw.method("initJdbc").tHrow(SQLException.class).code(mv -> {
			mv.define("columnList", ColumnList.class);
			{// ColumnList columnList = new ColumnList();
				mv.line();
				mv.NEW(ColumnList.class);
				mv.DUP();
				mv.SPECIAL(ColumnList.class, "<init>").INVOKE();
				mv.STORE("columnList");
			}
			for (FieldMapper fieldMapper : mappers) {
				// columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
				mv.line();
				mv.LOAD("columnList");
				mv.LOADConst(fieldMapper.column.toString());
				mv.STATIC(ColumnDefinition.class, "valueOf")
					.parameter(String.class)
					.reTurn(ColumnDefinition.class)
					.INVOKE();
				mv.VIRTUAL(ColumnList.class, "push").parameter(Object.class).INVOKE();

			}

			{// JDBCConfiguration.mergeIfExists(conn, "user", columnList)
				mv.line();
				mv.LOAD(0);
				mv.GETFIELD("conn", Connection.class);
				mv.LOADConst(tablename);
				mv.LOAD("columnList");
				mv.STATIC(JDBCConfiguration.class, "mergeIfExists")
					.parameter(Connection.class, String.class, ColumnList.class)
					.reTurn(boolean.class)
					.INVOKE();
			}
			Label iffalse = mv.codeNewLabel();
			mv.IFNE(iffalse);
			{// if (!) {
				List<String> columns = new ArrayList<>();
				List<String> keys = new ArrayList<>();

				boolean hasAutoIncrment = false;
				for (FieldMapper fieldMapper : mappers) {
					columns.add(fieldMapper.column.toSQL());
					if (fieldMapper.primaryKey) {
						if ("YES".equals(fieldMapper.column.getAutoIncrment())) {
							hasAutoIncrment = true;
						}
						keys.add(fieldMapper.column.getName());
					}
				}
				String sql;
				if (hasAutoIncrment) {
					sql = JDBCConfiguration.sql("CREATE TABLE ${tablename}(${columndefinitions}))", tablename,
							String.join(",", columns));
				} else {
					sql = JDBCConfiguration.sql("CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))",
							tablename, String.join(",", columns), String.join(",", keys));
				}

				// PreparedStatement preparedStatement = conn.prepareStatement(sql);
				mv.line();
				mv.LOAD(0);
				mv.GETFIELD("conn", Connection.class);
				mv.LOADConst(sql);
				mv.INTERFACE(Connection.class, "prepareStatement")
					.parameter(String.class)
					.reTurn(PreparedStatement.class)
					.INVOKE();
				mv.INTERFACE(PreparedStatement.class, "execute").reTurn(boolean.class).INVOKE();
				mv.POP();
			}
			mv.visitLabel(iffalse);
			{
				mv.line();
				mv.RETURN();
			}
		});
	}

	private void listJdbc(String clazzTarget, List<FieldMapper> mappers, String tablename) {
		cw.method(ACC_PUBLIC, "listJdbc")
			.parameter("start", int.class)
			.parameter("max", int.class)
			.reTurn(GenericClazz.generic(List.class, clazzTarget))
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("datas", GenericClazz.generic(List.class, clazzTarget));
				mv.define("resultSet", ResultSet.class);
				{
					mv.line();
					mv.NEW(ArrayList.class);
					mv.DUP();
					mv.SPECIAL(ArrayList.class, "<init>").INVOKE();
					mv.STORE("datas");
				}
				{
					List<String> names = new ArrayList<>();
					for (FieldMapper fieldMapper : mappers) {
						names.add(fieldMapper.column.getName());
					}

					String sql = JDBCConfiguration.sql("SELECT ${columns} FROM ${tablename}", String.join(",", names),
							tablename);

					mv.line();
					mv.LOAD("this");
					mv.GETFIELD("conn", Connection.class);
					mv.LOADConst(sql);
					mv.INTERFACE(Connection.class, "prepareStatement")
						.parameter(String.class)
						.reTurn(PreparedStatement.class)
						.INVOKE();
					mv.INTERFACE(PreparedStatement.class, "executeQuery").reTurn(ResultSet.class).INVOKE();
					mv.STORE("resultSet");
				}
				mv.line();
				Label whileStart = mv.codeNewLabel();
				Label whileEnd = mv.codeNewLabel();
				mv.visitLabel(whileStart);
				mv.LOAD("resultSet");
				mv.INTERFACE(ResultSet.class, "next").reTurn(boolean.class).INVOKE();
				mv.IFEQ(whileEnd);
				{
					mv.line();
					mv.LOAD("datas");
					mv.LOAD("this");
					mv.GETFIELD("mapper", this.clazzRowMapper);
					mv.LOAD("resultSet");
					mv.VIRTUAL(this.clazzRowMapper, "map").parameter(ResultSet.class).reTurn(this.clazzTarget).INVOKE();
					mv.INTERFACE(List.class, "add").parameter(Object.class).reTurn(boolean.class).INVOKE();
					mv.POP();
					mv.GOTO(whileStart);
				}
				mv.visitLabel(whileEnd);

				{
					mv.line();
					mv.LOAD("datas");
					mv.RETURNTop();
				}
			});
	}

	private void findByIdJdbc(String clazzTarget, Class<Long> clazzID, List<FieldMapper> mappers, String tablename) {
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
				{
					mv.line();
					mv.NEW(ArrayList.class);
					mv.DUP();
					mv.SPECIAL(ArrayList.class, "<init>").INVOKE();
					mv.STORE("datas");
				}
				{
					List<String> names = new ArrayList<>();
					List<String> keys = new ArrayList<>();

					for (FieldMapper fieldMapper : mappers) {
						names.add(fieldMapper.column.getName());
						if (fieldMapper.primaryKey) {
							keys.add(fieldMapper.column.getName() + "=?");
						}
					}

					String sql = JDBCConfiguration.sql("SELECT ${columns} FROM ${tablename} WHERE ${causes}",
							String.join(",", names), tablename, String.join(" AND ", keys));

					mv.line();
					mv.LOAD("this");
					mv.GETFIELD("conn", Connection.class);
					mv.LOADConst(sql);
					mv.INTERFACE(Connection.class, "prepareStatement")
						.parameter(String.class)
						.reTurn(PreparedStatement.class)
						.INVOKE();
					mv.STORE("preparedStatement");
				}

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						mv.line();
						mv.LOAD("preparedStatement");
						mv.LOADConst(i++);
						mv.LOAD("keys");
						mv.LOADConst(j++);
						mv.ARRAYLOAD(Object.class);

						if (fieldMapper.pojo_clazz.isPrimitive()) {
							Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojo_clazz);
							mv.CHECKCAST(objectclass);
							arguments.getConvert(objectclass, fieldMapper.pojo_clazz).apply(mv);
						} else {
							mv.CHECKCAST(fieldMapper.pojo_clazz);
						}

						TypeMapping jdbcType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(fieldMapper.pojo_clazz.getName());

						if (fieldMapper.pojo_clazz != jdbcType.jdbcClazz) {
							arguments.getConvert(fieldMapper.pojo_clazz, jdbcType.jdbcClazz).apply(mv);
						}

						mv.INTERFACE(PreparedStatement.class, jdbcType.setname)
							.parameter(int.class, jdbcType.jdbcClazz)
							.INVOKE();
					}
				}

				{
					mv.line();
					mv.LOAD("preparedStatement");
					mv.INTERFACE(PreparedStatement.class, "executeQuery").reTurn(ResultSet.class).INVOKE();
					mv.STORE("resultSet");
				}

				mv.line();
				Label whileStart = mv.codeNewLabel();
				Label whileEnd = mv.codeNewLabel();
				mv.visitLabel(whileStart);
				mv.LOAD("resultSet");
				mv.INTERFACE(ResultSet.class, "next").reTurn(boolean.class).INVOKE();
				mv.IFEQ(whileEnd);
				{
					mv.line();
					mv.LOAD("datas");
					mv.LOAD("this");
					mv.GETFIELD("mapper", this.clazzRowMapper);
					mv.LOAD("resultSet");
					mv.VIRTUAL(this.clazzRowMapper, "map").parameter(ResultSet.class).reTurn(this.clazzTarget).INVOKE();
					mv.INTERFACE(List.class, "add").parameter(Object.class).reTurn(boolean.class).INVOKE();
					mv.POP();
					mv.GOTO(whileStart);
				}
				mv.visitLabel(whileEnd);

				{
					mv.line();
					mv.LOAD("datas");
					mv.LOADConst(0);
					mv.INTERFACE(List.class, "get").parameter(int.class).reTurn(Object.class).INVOKE();
					mv.CHECKCAST(this.clazzTarget);
					mv.RETURNTop();
				}
			});
	}

	private void insertJdbc(List<FieldMapper> mappers, String tablename) {
		cw.method(ACC_PUBLIC, "insertJdbc")
			.parameter("data", this.clazzTarget)
			.reTurn(this.clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {
				List<String> names = new ArrayList<>();
				List<String> values = new ArrayList<>();

				boolean hasAutoIncrment = false;
				FieldMapper keyMapper = null;
				for (FieldMapper fieldMapper : mappers) {
					if ("YES".equals(fieldMapper.column.getAutoIncrment())) {
						hasAutoIncrment = true;
						keyMapper = fieldMapper;
					} else {
						names.add(fieldMapper.column.getName());
						values.add("?");
					}
				}
				if (hasAutoIncrment) {
					mv.line();
					mv.define("preparedStatement", PreparedStatement.class);
					mv.LOADConstNULL();
					mv.STORE("preparedStatement");
					mv.line();
					mv.define("rs", ResultSet.class);
					mv.LOADConstNULL();
					mv.STORE("rs");
				} else {
					mv.define("preparedStatement", PreparedStatement.class);
				}

				{
					String sql = JDBCConfiguration.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})",
							tablename, String.join(",", names), String.join(",", values));

					mv.line();
					if (hasAutoIncrment) {
						mv.LOAD("this");
						mv.GETFIELD("conn", Connection.class);
						mv.LOADConst(sql);
						mv.LOADConst(PreparedStatement.RETURN_GENERATED_KEYS);
						mv.INTERFACE(Connection.class, "prepareStatement")
							.parameter(String.class, int.class)
							.reTurn(PreparedStatement.class)
							.INVOKE();
						mv.STORE("preparedStatement");
					} else {
						mv.LOAD("this");
						mv.GETFIELD("conn", Connection.class);
						mv.LOADConst(sql);
						mv.INTERFACE(Connection.class, "prepareStatement")
							.parameter(String.class)
							.reTurn(PreparedStatement.class)
							.INVOKE();
						mv.STORE("preparedStatement");

					}
				}

				int i = 1;
				for (FieldMapper fieldMapper : mappers) {
					if (!"YES".equals(fieldMapper.column.getAutoIncrment())) {
						TypeMapping javaType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(fieldMapper.pojo_clazz.getName());
						bindField(mv, i++, fieldMapper.pojo_getname, fieldMapper.pojo_clazz, javaType.setname,
								javaType.jdbcClazz);
					}
				}

				{
					mv.line();
					mv.LOAD("preparedStatement");
					mv.INTERFACE(PreparedStatement.class, "executeUpdate").reTurn(int.class).INVOKE();
					Label elseOfIf = mv.codeNewLabel();
					mv.IFLE(elseOfIf);

					if (hasAutoIncrment) {
						mv.line();
						mv.LOAD("preparedStatement");
						mv.INTERFACE(PreparedStatement.class, "getGeneratedKeys").reTurn(ResultSet.class).INVOKE();
						mv.STORE("rs");
						mv.line();
						mv.LOAD("rs");
						mv.INTERFACE(ResultSet.class, "next").reTurn(boolean.class).INVOKE();
						mv.POP();

						// this.findById(id);
						mv.line();
						mv.LOAD("this");
						mv.LOADConst(1);
						mv.NEWARRAY(Object.class);

						mv.DUP();
						mv.LOADConst(0);

						TypeMapping javaType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(keyMapper.pojo_clazz.getName());

						mv.LOAD("rs");
						mv.LOADConst(1);
						mv.INTERFACE(ResultSet.class, javaType.getname)
							.parameter(int.class)
							.reTurn(javaType.jdbcClazz)
							.INVOKE();
						if (keyMapper.pojo_clazz.isPrimitive()) {
							Class<?> objectclass = mapPrimaryToObject.get(keyMapper.pojo_clazz);
							arguments.getRevert(objectclass, keyMapper.pojo_clazz).apply(mv);
						}

						mv.ARRAYSTORE();

						mv.VIRTUAL(clazz, "findById")
							.parameter(GenericClazz.generic(Object.class, true))
							.reTurn(Object.class)
							.INVOKE();
						mv.CHECKCAST(this.clazzTarget);
						mv.RETURNTop();

					} else {

						// this.findById(id);
						int cntKeys = 0;

						for (FieldMapper fieldMapper : mappers) {
							if (fieldMapper.primaryKey) {
								cntKeys++;
							}
						}
						mv.line();
						mv.LOAD("this");

						mv.LOADConst(cntKeys);
						mv.NEWARRAY(Object.class);
						int j = 0;
						for (FieldMapper fieldMapper : mappers) {
							if (fieldMapper.primaryKey) {
								mv.DUP();
								mv.LOADConst(j++);

								mv.LOAD("data");
								mv.VIRTUAL(this.clazzTarget, fieldMapper.pojo_getname)
									.reTurn(fieldMapper.pojo_clazz)
									.INVOKE();

								if (fieldMapper.pojo_clazz.isPrimitive()) {
									Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojo_clazz);
									arguments.getRevert(objectclass, fieldMapper.pojo_clazz).apply(mv);
								}

								mv.ARRAYSTORE();
							}
						}

						mv.VIRTUAL(clazz, "findById")
							.parameter(GenericClazz.generic(Object.class, true))
							.reTurn(Object.class)
							.INVOKE();
						mv.CHECKCAST(this.clazzTarget);
						mv.RETURNTop();

					}
					mv.visitLabel(elseOfIf);
					{
						mv.line();
						mv.LOADConstNULL();
						mv.RETURNTop();
					}

				}
			});
	}

	static Map<Class<?>, Class<?>> mapPrimaryToObject = new HashMap<>();
	static {
		mapPrimaryToObject.put(boolean.class, Boolean.class);
		mapPrimaryToObject.put(char.class, Character.class);
		mapPrimaryToObject.put(byte.class, Byte.class);
		mapPrimaryToObject.put(short.class, Short.class);
		mapPrimaryToObject.put(int.class, Integer.class);
		mapPrimaryToObject.put(long.class, Long.class);
		mapPrimaryToObject.put(float.class, Float.class);
		mapPrimaryToObject.put(double.class, Double.class);
	}

	private void updateJdbc(List<FieldMapper> mappers, String tablename) {
		cw.method(ACC_PUBLIC, "updateJdbc")
			.parameter("data", this.clazzTarget)
			.reTurn(this.clazzTarget)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				{
					List<String> setvalues = new ArrayList<>();
					List<String> keys = new ArrayList<>();

					for (FieldMapper fieldMapper : mappers) {
						if (!fieldMapper.primaryKey) {
							setvalues.add(fieldMapper.column.getName() + "=?");
						} else {
							keys.add(fieldMapper.column.getName() + "=?");
						}
					}

					String sql = JDBCConfiguration.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}",
							tablename, String.join(",", setvalues), String.join(" AND ", keys));

					mv.line();
					mv.LOAD("this");
					mv.GETFIELD("conn", Connection.class);
					mv.LOADConst(sql);
					mv.INTERFACE(Connection.class, "prepareStatement")
						.parameter(String.class)
						.reTurn(PreparedStatement.class)
						.INVOKE();
					mv.STORE("preparedStatement");
				}

				int i = 1;
				for (FieldMapper fieldMapper : mappers) {
					if (!fieldMapper.primaryKey) {
						TypeMapping jdbcType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(fieldMapper.pojo_clazz.getName());
						bindField(mv, i++, fieldMapper.pojo_getname, fieldMapper.pojo_clazz, jdbcType.setname,
								jdbcType.jdbcClazz);
					}
				}

				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						TypeMapping javaType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(fieldMapper.pojo_clazz.getName());
						bindField(mv, i++, fieldMapper.pojo_getname, javaType.jdbcClazz, javaType.setname,
								javaType.jdbcClazz);
					}
				}
				{
					mv.line();
					mv.LOAD("preparedStatement");
					mv.INTERFACE(PreparedStatement.class, "executeUpdate").reTurn(int.class).INVOKE();
					Label elseOfIf = mv.codeNewLabel();
					mv.IFLE(elseOfIf);
					{
						int cntKeys = 0;

						for (FieldMapper fieldMapper : mappers) {
							if (fieldMapper.primaryKey) {
								cntKeys++;
							}
						}
						mv.line();
						mv.LOAD("this");

						mv.LOADConst(cntKeys);
						mv.NEWARRAY(Object.class);
						int j = 0;
						for (FieldMapper fieldMapper : mappers) {
							if (fieldMapper.primaryKey) {
								mv.DUP();
								mv.LOADConst(j++);

								mv.LOAD("data");
								mv.VIRTUAL(this.clazzTarget, fieldMapper.pojo_getname)
									.reTurn(fieldMapper.pojo_clazz)
									.INVOKE();

								if (fieldMapper.pojo_clazz.isPrimitive()) {
									Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojo_clazz);
									arguments.getRevert(objectclass, fieldMapper.pojo_clazz).apply(mv);
								}

								mv.ARRAYSTORE();
							}
						}

						mv.VIRTUAL(clazz, "findById")
							.parameter(GenericClazz.generic(Object.class, true))
							.reTurn(Object.class)
							.INVOKE();
						mv.CHECKCAST(this.clazzTarget);
						mv.RETURNTop();
					}
					mv.visitLabel(elseOfIf);
					{
						mv.line();
						mv.LOADConstNULL();
						mv.RETURNTop();
					}

				}

			});
	}

	private void deleteJdbc(Class<Long> clazzID, List<FieldMapper> mappers, String tablename) {
		cw.method(ACC_PUBLIC, "deleteJdbc")
			.ACC_PUBLIC()
			.ACC_VARARGS()
			.parameter("keys", GenericClazz.generic(Object.class, true))
			.reTurn(int.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.define("preparedStatement", PreparedStatement.class);
				{

					List<String> keys = new ArrayList<>();

					for (FieldMapper fieldMapper : mappers) {
						if (!fieldMapper.primaryKey) {
						} else {
							keys.add(fieldMapper.column.getName() + "=?");
						}
					}

					String sql = JDBCConfiguration.sql("DELETE ${tablename} WHERE ${causes}", tablename,
							String.join(" AND ", keys));

					mv.line();
					mv.LOAD("this");
					mv.GETFIELD("conn", Connection.class);
					mv.LOADConst(sql);
					mv.INTERFACE(Connection.class, "prepareStatement")
						.parameter(String.class)
						.reTurn(PreparedStatement.class)
						.INVOKE();
					mv.STORE("preparedStatement");
				}

				int i = 1;
				int j = 0;
				for (FieldMapper fieldMapper : mappers) {
					if (fieldMapper.primaryKey) {
						mv.line();
						mv.LOAD("preparedStatement");
						mv.LOADConst(i++);
						mv.LOAD("keys");
						mv.LOADConst(j++);
						mv.ARRAYLOAD(Object.class);

						if (fieldMapper.pojo_clazz.isPrimitive()) {
							Class<?> objectclass = mapPrimaryToObject.get(fieldMapper.pojo_clazz);
							mv.CHECKCAST(objectclass);
							arguments.getConvert(objectclass, fieldMapper.pojo_clazz).apply(mv);
						} else {
							mv.CHECKCAST(fieldMapper.pojo_clazz);
						}

						TypeMapping jdbcType = JDBCConfiguration.mapJavaClazz2JdbcTypes
							.get(fieldMapper.pojo_clazz.getName());

						if (fieldMapper.pojo_clazz != jdbcType.jdbcClazz) {
							arguments.getConvert(fieldMapper.pojo_clazz, jdbcType.jdbcClazz).apply(mv);
						}

						mv.INTERFACE(PreparedStatement.class, jdbcType.setname)
							.parameter(int.class, jdbcType.jdbcClazz)
							.INVOKE();
					}
				}
				{
					mv.line();
					mv.LOAD("preparedStatement");
					mv.INTERFACE(PreparedStatement.class, "executeUpdate").reTurn(int.class).INVOKE();
					mv.RETURNTop();
				}
			});
	}

	private void bindField(MethodCode mv, int index, String propGetName, Class<?> pojoGetClazz, String jdbcSetName,
			Class<?> jdbcSetClazz) {
		{
			mv.line();
			mv.LOAD("preparedStatement");
			mv.LOADConst(index);
			mv.LOAD("data");
			mv.VIRTUAL(this.clazzTarget, propGetName).reTurn(pojoGetClazz).INVOKE();
			if (pojoGetClazz != jdbcSetClazz) {
				arguments.getConvert(pojoGetClazz, jdbcSetClazz).apply(mv);
			}
			mv.INTERFACE(PreparedStatement.class, jdbcSetName).parameter(int.class, jdbcSetClazz).INVOKE();
		}
	}

	private void insertJdbcBridge() {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "insertJdbc")
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line();
				mv.LOAD("this");
				mv.LOAD("data");
				mv.CHECKCAST(this.clazzTarget);
				mv.VIRTUAL(this.clazz, "insertJdbc").parameter(this.clazzTarget).reTurn(this.clazzTarget).INVOKE();
				mv.RETURNTop();
			});
	}

	private void updateJdbcBridge() {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "updateJdbc")
			.parameter("data", Object.class)
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line();
				mv.LOAD("this");
				mv.LOAD("data");
				mv.CHECKCAST(this.clazzTarget);
				mv.VIRTUAL(this.clazz, "updateJdbc").parameter(this.clazzTarget).reTurn(this.clazzTarget).INVOKE();
				mv.RETURNTop();
			});
	}

	private void findByIdJdbcBridge(Class<Long> clazzID) {
		cw.method(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "findByIdJdbc")
			.parameter("keys", GenericClazz.generic(Object.class, true))
			.reTurn(Object.class)
			.tHrow(SQLException.class)
			.code(mv -> {
				mv.line();
				mv.LOAD("this");
				mv.LOAD("keys");
				mv.VIRTUAL(this.clazz, "findByIdJdbc")
					.parameter(GenericClazz.generic(Object.class, true))
					.reTurn(this.clazzTarget)
					.INVOKE();
				mv.RETURNTop();
			});
	}
}
