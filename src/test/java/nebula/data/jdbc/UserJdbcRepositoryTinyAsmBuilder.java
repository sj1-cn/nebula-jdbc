package nebula.data.jdbc;

import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import cn.sj1.tinyasm.core.ClassBody;
import cn.sj1.tinyasm.core.ClassBuilder;
import cn.sj1.tinyasm.core.MethodCode;
import org.objectweb.asm.Type;

import static cn.sj1.tinyasm.core.Adv._line;
import static org.objectweb.asm.Opcodes.*;
import cn.sj1.tinyasm.core.Annotation;
import cn.sj1.tinyasm.core.Clazz;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.JDBC.JdbcMapping;
import nebula.data.jdbc.JdbcRepository;
import nebula.jdbc.builders.schema.Column;
import nebula.jdbc.builders.schema.ColumnDefinition;
import java.sql.Connection;
import nebula.data.query.OrderBy;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import nebula.data.jdbc.User;
import nebula.jdbc.builders.queries.Select;
import nebula.data.jdbc.UserExtendJdbcRowMapper;
import nebula.data.jdbc.UserExtend;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

import nebula.commons.list.ListMap;
import nebula.data.jdbc.ClassExtend;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.Object;
import nebula.data.query.Condition;
import nebula.jdbc.builders.schema.ColumnList;
import java.lang.String;
import nebula.data.jdbc.PageListImpl;
import nebula.data.jdbc.PageList;

@SuppressWarnings("unused")
public class UserJdbcRepositoryTinyAsmBuilder extends UserJdbcRepositoryTinyAsmDump {

	public static byte[] dump(String clazzRepository, Class<?> classEntity, Class<?> classEntityImpl, EntityDefinition entityDefinition) throws Exception {
		UserJdbcRepositoryTinyAsmBuilder userJdbcRepositoryTinyAsmBuilder = new UserJdbcRepositoryTinyAsmBuilder();
		userJdbcRepositoryTinyAsmBuilder.dumpInit(clazzRepository, classEntity, classEntityImpl, entityDefinition);
		return userJdbcRepositoryTinyAsmBuilder.dump(clazzRepository);
	}

	String $_clazzRepository;
	Class<?> $_clazzEntity;
	Class<?> $_clazzEntityImpl;
	EntityDefinition $_entityDefinition;
	Arguments $_arguments = new Arguments();
	private boolean $_hasAutoIncrment = true;
	private String $_tableName;

	public void dumpInit(String clazzRepository, Class<?> classEntity, Class<?> classEntityImpl, EntityDefinition entityDefinition) {
		this.$_clazzRepository = clazzRepository;
		this.$_clazzEntity = classEntity;
		this.$_clazzEntityImpl = classEntityImpl;
		this.$_entityDefinition = entityDefinition;
		this.$_tableName = entityDefinition.tablename;
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className, Clazz.of(Object.class),Clazz.of(JdbcRepository.class,Clazz.of($_clazzEntity)))
			.access(ACC_PUBLIC | ACC_SUPER).body();

		classBody.private_().field("conn", Clazz.of(Connection.class));
		classBody.private_().field("sqlHelper", Clazz.of(SqlHelper.class));
		__init_(classBody);
		_setConnection(classBody);
		_initJdbc(classBody);
		_listJdbc(classBody);
		_listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int_nebuladatajdbcPageList(classBody);
		_toEntity(classBody);
		_findByIdJdbc(classBody);
		_insertJdbc(classBody);
		_updateJdbc(classBody);
		_deleteByIdJdbc(classBody);
		_bridge_updateJdbc(classBody);
		_bridge_insertJdbc(classBody);
		_bridge_findByIdJdbc(classBody);

		return classBody.end().toByteArray();
	}
	
	protected void _initJdbc(ClassBody classBody) {
		FieldList $mappers = $_entityDefinition.getFieldsAll();
		boolean hasAutoIncrment = $mappers.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));

		List<String> keys = $mappers.filter(f -> f.primaryKey).map(f -> f.column.getName());
		List<String> columnDefinitions = $mappers.map(f -> f.column.toSQL());

		String $_sql;
		if (hasAutoIncrment) {
			$_sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions}))", $_entityDefinition.getTablename(), String.join(",", columnDefinitions));
		} else {
			$_sql = JDBC.sql("CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", $_entityDefinition.getTablename(), String.join(",", columnDefinitions), String.join(",", keys));
		}

		MethodCode code = classBody.public_().method("initJdbc").throws_(SQLException.class).begin();

		code.LINE();
		code.NEW(ColumnList.class);
		code.DUP();
		code.SPECIAL(ColumnList.class, "<init>").INVOKE();
		code.STORE("columnList", ColumnList.class);

		for (FieldMapper $field : $mappers) {
			code.LINE();
			code.LOAD("columnList");
			code.LOADConst($field.column.toString());
			code.VIRTUAL(ColumnList.class, "addColumn").parameter(String.class).INVOKE();
		}

		code.LINE();
		code.LOAD("this");
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst($_tableName);
		code.LOAD("columnList");
		code.VIRTUAL("mergeIfExists").return_(boolean.class).parameter(Connection.class).parameter(String.class).parameter(ColumnList.class).INVOKE();
		Label label7OfIFNE = new Label();
		code.IFNE(label7OfIFNE);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst($_sql);
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "execute").return_(boolean.class).INVOKE();
		code.POP();

		code.visitLabel(label7OfIFNE);

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _listJdbc(ClassBody classBody) {
		String $_sqlColumns = String.join(",", $_entityDefinition.fieldsAll.map(f -> f.column.getName()));

		MethodCode code = classBody.public_().method("listJdbc").return_(Clazz.of(PageList.class, Clazz.of($_clazzEntity))).throws_(SQLException.class).parameter("start", Clazz.of(int.class)).parameter("max", Clazz.of(int.class)).begin();

		code.LINE();
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>").parameter(int.class).parameter(int.class).INVOKE();
		code.STORE("datas", Clazz.of(PageList.class, Clazz.of($_clazzEntity)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst($_sqlColumns);
		code.VIRTUAL(SqlHelper.class, "select").return_(Select.class).parameter(String.class).INVOKE();
		code.LOADConst($_tableName);
		code.VIRTUAL(Select.class, "from").return_(Select.class).parameter(String.class).INVOKE();
		code.LOAD("start");
		code.VIRTUAL(Select.class, "offset").return_(Select.class).parameter(int.class).INVOKE();
		code.LOAD("max");
		code.VIRTUAL(Select.class, "max").return_(Select.class).parameter(int.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL").return_(String.class).INVOKE();
		code.STORE("sql", String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery").return_(ResultSet.class).INVOKE();
		code.STORE("resultSet", ResultSet.class);
		Label label6OfGOTO = new Label();

		code.visitLabel(label6OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next").return_(boolean.class).INVOKE();
		Label label4OfIFEQ = new Label();
		code.IFEQ(label4OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity").return_($_clazzEntityImpl).parameter(ResultSet.class).INVOKE();
		code.INTERFACE(PageList.class, "add").return_(boolean.class).parameter(Object.class).INVOKE();
		code.POP();
		code.GOTO(label6OfGOTO);

		code.visitLabel(label4OfIFEQ);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst("count(1)");
		code.VIRTUAL(SqlHelper.class, "select").return_(Select.class).parameter(String.class).INVOKE();
		code.LOADConst($_tableName);
		code.VIRTUAL(Select.class, "from").return_(Select.class).parameter(String.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL").return_(String.class).INVOKE();
		code.STORE("sqlCount", String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.INTERFACE(Connection.class, "createStatement").return_(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery").return_(ResultSet.class).parameter(String.class).INVOKE();
		code.STORE("resultSetCount", ResultSet.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next").return_(boolean.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt").return_(int.class).parameter(int.class).INVOKE();
		code.STORE("totalSize", int.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize").parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int_nebuladatajdbcPageList(ClassBody classBody) {
		String $_sqlColumns = String.join(",", $_entityDefinition.fieldsAll.map(f -> f.column.getName()));
		MethodCode code = classBody.public_().method("listJdbc").return_(Clazz.of(PageList.class, Clazz.of($_clazzEntity))).throws_(SQLException.class).parameter("condition", Clazz.of(Condition.class))
				.parameter("orderBy", Clazz.of(OrderBy.class)).parameter("start", Clazz.of(int.class)).parameter("max", Clazz.of(int.class)).begin();

		code.LINE();
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>").parameter(int.class).parameter(int.class).INVOKE();
		code.STORE("datas", Clazz.of(PageList.class, Clazz.of($_clazzEntity)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst($_sqlColumns);
		code.VIRTUAL(SqlHelper.class, "select").return_(Select.class).parameter(String.class).INVOKE();
		code.LOADConst($_tableName);
		code.VIRTUAL(Select.class, "from").return_(Select.class).parameter(String.class).INVOKE();
		code.LOAD("condition");
		code.VIRTUAL(Select.class, "where").return_(Select.class).parameter(Condition.class).INVOKE();
		code.LOAD("orderBy");
		code.VIRTUAL(Select.class, "orderby").return_(Select.class).parameter(OrderBy.class).INVOKE();
		code.LOAD("start");
		code.VIRTUAL(Select.class, "offset").return_(Select.class).parameter(int.class).INVOKE();
		code.LOAD("max");
		code.VIRTUAL(Select.class, "max").return_(Select.class).parameter(int.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL").return_(String.class).INVOKE();
		code.STORE("sql", String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery").return_(ResultSet.class).INVOKE();
		code.STORE("resultSet", ResultSet.class);
		Label label6OfGOTO = new Label();

		code.visitLabel(label6OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next").return_(boolean.class).INVOKE();
		Label label4OfIFEQ = new Label();
		code.IFEQ(label4OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity").return_($_clazzEntityImpl).parameter(ResultSet.class).INVOKE();
		code.INTERFACE(PageList.class, "add").return_(boolean.class).parameter(Object.class).INVOKE();
		code.POP();
		code.GOTO(label6OfGOTO);

		code.visitLabel(label4OfIFEQ);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst("count(1)");
		code.VIRTUAL(SqlHelper.class, "select").return_(Select.class).parameter(String.class).INVOKE();
		code.LOADConst($_tableName);
		code.VIRTUAL(Select.class, "from").return_(Select.class).parameter(String.class).INVOKE();
		code.LOAD("condition");
		code.VIRTUAL(Select.class, "where").return_(Select.class).parameter(Condition.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL").return_(String.class).INVOKE();
		code.STORE("sqlCount", String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.INTERFACE(Connection.class, "createStatement").return_(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery").return_(ResultSet.class).parameter(String.class).INVOKE();
		code.STORE("resultSetCount", ResultSet.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next").return_(boolean.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt").return_(int.class).parameter(int.class).INVOKE();
		code.STORE("totalSize", int.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize").parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _toEntity(ClassBody classBody) {
		MethodCode code = classBody.private_().method("toEntity").return_($_clazzEntityImpl).throws_(SQLException.class).parameter("resultSet", ResultSet.class).begin();

		code.LINE();
		code.NEW($_clazzEntityImpl);
		code.DUP();
		code.SPECIAL($_clazzEntityImpl, "<init>").INVOKE();
		code.STORE("impl", $_clazzEntityImpl);

		FieldList $fields = $_entityDefinition.getFieldsAll();
		for (int i = 0; i < $fields.size(); i++) {
			FieldMapper fieldMapper = $fields.get(i);
			String name = fieldMapper.fieldName;
			JdbcMapping jdbcMapping = JDBC.map(fieldMapper.clazz);
			assert jdbcMapping != null : name + "'s type [" + fieldMapper.clazz.getName() + "] has not jdbc type";
			Class<?> jdbcClazz = jdbcMapping.jdbcClazz;
			Class<?> pojoClazz = fieldMapper.clazz;

			code.LINE();
			code.LOAD("impl");
			code.LOAD("resultSet");
			code.LOADConst(i + 1);
			code.INTERFACE(ResultSet.class, jdbcMapping.getname).return_((Class<?>) jdbcClazz).parameter(int.class).INVOKE();
			$_arguments.fromJdbcClazz(pojoClazz, jdbcClazz).accept(code);
			code.VIRTUAL($_clazzEntityImpl, "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1)).parameter(fieldMapper.getPojoClazz()).INVOKE();
		}

		code.LINE();
		code.LOAD("impl");
		code.RETURNTop();

		code.END();
	}

	protected void _findByIdJdbc(ClassBody classBody) {
		List<Column> $selAllColoumns = $_entityDefinition.fieldsAll.map(f -> f.column);
		List<Column> $_sqlKeys = $_entityDefinition.fieldsAll.filter(f -> f.primaryKey).map(f -> f.column);

		String $_sql = Select.columns(ColumnList.namesOf($selAllColoumns)).from($_entityDefinition.tablename).where(ColumnList.namesOf($_sqlKeys)).toSQL();

		MethodCode code = classBody.public_().method("findByIdJdbc").return_($_clazzEntity).throws_(SQLException.class).parameter("id", long.class).begin();

		code.LINE();
		code.NEW(ArrayList.class);
		code.DUP();
		code.SPECIAL(ArrayList.class, "<init>").INVOKE();
		code.STORE("datas", Clazz.of(List.class, Clazz.of($_clazzEntity)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst($_sql);
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.STORE("preparedStatement", PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("id");
		code.INTERFACE(PreparedStatement.class, "setLong").parameter(int.class).parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeQuery").return_(ResultSet.class).INVOKE();
		code.STORE("resultSet", ResultSet.class);
		Label label7OfGOTO = new Label();

		code.visitLabel(label7OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next").return_(boolean.class).INVOKE();
		Label label5OfIFEQ = new Label();
		code.IFEQ(label5OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity").return_($_clazzEntityImpl).parameter(ResultSet.class).INVOKE();
		code.INTERFACE(List.class, "add").return_(boolean.class).parameter(Object.class).INVOKE();
		code.POP();
		code.GOTO(label7OfGOTO);

		code.visitLabel(label5OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOADConst(0);
		code.INTERFACE(List.class, "get").return_(Object.class).parameter(int.class).INVOKE();
		code.CHECKCAST($_clazzEntity);
		code.RETURNTop();

		code.END();
	}

	protected void _insertJdbc(ClassBody classBody) {
		FieldList fields = $_entityDefinition.fields;
		boolean hasAutoIncrment = fields.anyMatch(f -> "YES".equals(f.column.getAutoIncrment()));
		FieldMapper keyField = fields.filter(f -> f.isPrimaryKey()).get(0);

		if (hasAutoIncrment) {
			List<String> names = $_entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
			List<String> values = $_entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
			String $_sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", $_entityDefinition.tablename, String.join(",", names), String.join(",", values));
			
			MethodCode code = classBody.public_().method("insertJdbc")
					.return_($_clazzEntity )
					.throws_(SQLException.class )
					.parameter("data",$_clazzEntity).begin();

				code.LINE();
				code.LOADConstNULL();
				code.STORE("resultSet",ResultSet.class);

				code.LINE();
				code.LOAD("this");
				code.GETFIELD_OF_THIS("conn");
				code.LOADConst($_sql);
				code.LOADConst(1);
				code.INTERFACE(Connection.class, "prepareStatement")
					.return_(PreparedStatement.class)
					.parameter(String.class)
					.parameter(int.class).INVOKE();
				code.STORE("preparedStatement",PreparedStatement.class);


				int $i = 1;
				for (FieldMapper field : $_entityDefinition.fields) {
					if (!"YES".equals(field.column.getAutoIncrment())) {							
						JdbcMapping jdbc = JDBC.map(field.clazz);
						int fieldIndex = $i;
						code.LINE();
						code.LOAD("preparedStatement");
						code.LOADConst(fieldIndex);
						code.LOAD("data");
						code.VIRTUAL($_clazzEntity, field.getName).return_(field.clazz).INVOKE();
						$_arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(code);
						code.INTERFACE(PreparedStatement.class, jdbc.setName).parameter(int.class).parameter(jdbc.jdbcClazz).INVOKE();

						$i++;						
					}
				}

				code.LINE();
				code.LOAD("this");
				code.LOAD("preparedStatement");
				code.LOADConst($i);
				code.VIRTUAL("bindInsertExtend").return_(int.class).parameter(PreparedStatement.class).parameter(int.class).INVOKE();
				code.POP();

				code.LINE();
				code.LOAD("preparedStatement");
				code.INTERFACE(PreparedStatement.class, "executeUpdate")
					.return_(int.class).INVOKE();
				Label label6OfIFLE = new Label();
				code.IFLE(label6OfIFLE);

				code.LINE();
				code.LOAD("preparedStatement");
				code.INTERFACE(PreparedStatement.class, "getGeneratedKeys")
					.return_(ResultSet.class).INVOKE();
				code.STORE("resultSet");

				code.LINE();
				code.LOAD("resultSet");
				code.INTERFACE(ResultSet.class, "next")
					.return_(boolean.class).INVOKE();
				code.POP();

				code.LINE();
				code.LOAD("this");
				code.LOAD("resultSet");
				code.LOADConst(1);
				code.INTERFACE(ResultSet.class, "getLong")
					.return_(long.class)
					.parameter(int.class).INVOKE();
				code.VIRTUAL("findByIdJdbc")
					.return_($_clazzEntity)
					.parameter(long.class).INVOKE();
				code.RETURNTop();

				code.visitLabel(label6OfIFLE);

				code.LINE();
				code.LOADConstNULL();
				code.RETURNTop();

				code.END();
				
				
		} else {

			List<String> names = $_entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> f.column.getName());
			List<String> values = $_entityDefinition.fieldsAll.filter(f -> !"YES".equals(f.column.getAutoIncrment())).map(f -> "?");
			String $_sql = JDBC.sql("INSERT INTO ${tablename}(${columns}) VALUES(${values})", $_entityDefinition.tablename, String.join(",", names), String.join(",", values));

			MethodCode code = classBody.public_().method("insertJdbc").return_($_clazzEntity).throws_(SQLException.class).parameter("data", $_clazzEntity).begin();

			code.LINE();
			code.LOAD("this");
			code.GETFIELD_OF_THIS("conn");
			code.LOADConst($_sql);
			code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
			code.STORE("preparedStatement", PreparedStatement.class);

			int $i = 1;
			for (FieldMapper field : $_entityDefinition.fields) {
				JdbcMapping jdbc = JDBC.map(field.clazz);
				int fieldIndex = $i;
				code.LINE();
				code.LOAD("preparedStatement");
				code.LOADConst(fieldIndex);
				code.LOAD("data");
				code.VIRTUAL($_clazzEntity, field.getName).return_(field.clazz).INVOKE();
				$_arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(code);
				code.INTERFACE(PreparedStatement.class, jdbc.setName).parameter(int.class).parameter(jdbc.jdbcClazz).INVOKE();

				$i++;
			}

			code.LINE();
			code.LOAD("this");
			code.LOAD("preparedStatement");
			code.LOADConst($i);
			code.VIRTUAL("bindInsertExtend").return_(int.class).parameter(PreparedStatement.class).parameter(int.class).INVOKE();
			code.POP();

			code.LINE();
			code.LOAD("preparedStatement");
			code.INTERFACE(PreparedStatement.class, "executeUpdate").return_(int.class).INVOKE();
			Label label6OfIFLE = new Label();
			code.IFLE(label6OfIFLE);

			code.LINE();
			code.LOAD("this");
			code.LOAD("data");
			code.VIRTUAL($_clazzEntity, "getId").return_(long.class).INVOKE();
			code.VIRTUAL("findByIdJdbc").return_($_clazzEntity).parameter(long.class).INVOKE();
			code.RETURNTop();

			code.visitLabel(label6OfIFLE);

			code.LINE();
			code.LOADConstNULL();
			code.RETURNTop();

			code.END();
		}
	}

	protected void _updateJdbc(ClassBody classBody) {
		FieldList $_fieldsAll = $_entityDefinition.fieldsAll;
		ListMap<String, FieldMapper> $_keys = $_fieldsAll.filter(f -> f.primaryKey);
		ListMap<String, FieldMapper> $_othersWithoutCreate = $_fieldsAll.filter(f -> !f.primaryKey && !"createAt".equals(f.fieldName));
		ListMap<String, FieldMapper> $_othersWithoutExtend = $_entityDefinition.fields.filter(f -> !f.primaryKey);
		String $_sql = JDBC.sql("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", $_entityDefinition.tablename, String.join(",", $_othersWithoutCreate.map(f -> f.fieldName + "=?")),
				String.join(" AND ", $_keys.map(f -> f.fieldName + "=?")));

		MethodCode code = classBody.public_().method("updateJdbc").return_($_clazzEntity).throws_(SQLException.class).parameter("data", $_clazzEntity).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL($_clazzEntity, "getId").return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc").return_($_clazzEntity).parameter(long.class).INVOKE();
		code.CHECKCAST(ClassExtend.class);
		code.STORE("extend", ClassExtend.class);

		code.LINE();
		code.LOAD("extend");
		code.INTERFACE(ClassExtend.class, "getUpdateAt").return_(Timestamp.class).INVOKE();
		code.LOAD("data");
		code.CHECKCAST(ClassExtend.class);
		code.INTERFACE(ClassExtend.class, "getUpdateAt").return_(Timestamp.class).INVOKE();
		Label label2OfIF_ACMPNE = new Label();
		code.IF_ACMPNE(label2OfIF_ACMPNE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.visitLabel(label2OfIF_ACMPNE);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst($_sql);
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.STORE("preparedStatement", PreparedStatement.class);

		int i = 1;
		for (FieldMapper field : $_othersWithoutExtend) {
			JdbcMapping jdbc = JDBC.map(field.clazz);

			int fieldIndex = i;
			code.LINE();
			code.LOAD("preparedStatement");
			code.LOADConst(fieldIndex);
			code.LOAD("data");
			code.VIRTUAL($_clazzEntity, field.getName).return_(field.clazz).INVOKE();
			$_arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(code);
			code.INTERFACE(PreparedStatement.class, jdbc.setName).parameter(int.class).parameter(jdbc.jdbcClazz).INVOKE();

			i++;
		}

		code.LINE();
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(i++);
		code.VIRTUAL("bindUpdateExtend").return_(int.class).parameter(PreparedStatement.class).parameter(int.class).INVOKE();
		code.POP();

		// preparedStatement.setLong(3, data.getId());
		for (FieldMapper field : $_keys) {

			JdbcMapping jdbc = JDBC.map(field.clazz);

			int fieldIndex = i;
			code.LINE();
			code.LOAD("preparedStatement");
			code.LOADConst(fieldIndex);
			code.LOAD("data");
			code.VIRTUAL($_clazzEntity, field.getName).return_(field.clazz).INVOKE();
			$_arguments.toJdbcClazz(field.clazz, jdbc.jdbcClazz).accept(code);
			code.INTERFACE(PreparedStatement.class, jdbc.setName).parameter(int.class).parameter(jdbc.jdbcClazz).INVOKE();

			i++;
		}

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate").return_(int.class).INVOKE();
		Label label9OfIFLE = new Label();
		code.IFLE(label9OfIFLE);

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL($_clazzEntity, "getId").return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc").return_($_clazzEntity).parameter(long.class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label9OfIFLE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _deleteByIdJdbc(ClassBody classBody) {
		FieldList $_fieldsAll = $_entityDefinition.fieldsAll;
		ListMap<String, FieldMapper> $_keys = $_fieldsAll.filter(f -> f.primaryKey);
		String $_sql = JDBC.sql("DELETE ${tablename} WHERE ${causes}", $_entityDefinition.tablename, String.join(" AND ", $_keys.map(f -> f.column.getName() + "=?")));

		MethodCode code = classBody.public_().method("deleteByIdJdbc").return_(int.class).throws_(SQLException.class).parameter("id", long.class).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst($_sql);
		code.INTERFACE(Connection.class, "prepareStatement").return_(PreparedStatement.class).parameter(String.class).INVOKE();
		code.STORE("preparedStatement", PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("id");
		code.INTERFACE(PreparedStatement.class, "setLong").parameter(int.class).parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate").return_(int.class).INVOKE();
		code.RETURNTop();

		code.END();
	}


	protected void _bridge_updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "updateJdbc")
			.return_(Object.class )
			.throws_(SQLException.class )
			.parameter(ACC_SYNTHETIC,"data",Object.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.CHECKCAST($_clazzEntity);
		code.VIRTUAL("updateJdbc")
			.return_($_clazzEntity)
			.parameter($_clazzEntity).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void _bridge_insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "insertJdbc")
			.return_(Object.class )
			.throws_(SQLException.class )
			.parameter(ACC_SYNTHETIC,"data",Object.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.CHECKCAST($_clazzEntity);
		code.VIRTUAL("insertJdbc")
			.return_($_clazzEntity)
			.parameter($_clazzEntity).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void _bridge_findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "findByIdJdbc")
			.return_(Object.class )
			.throws_(SQLException.class )
			.parameter(ACC_SYNTHETIC,"id",long.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("id");
		code.VIRTUAL("findByIdJdbc")
			.return_($_clazzEntity)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.END();
	}
}
