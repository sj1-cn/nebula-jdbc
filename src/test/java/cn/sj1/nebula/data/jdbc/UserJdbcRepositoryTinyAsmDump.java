package cn.sj1.nebula.data.jdbc;
import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;

import cn.sj1.nebula.data.basic.EntitySystem;
import cn.sj1.nebula.data.basic.PageList;
import cn.sj1.nebula.data.basic.PageListImpl;
import cn.sj1.nebula.data.jdbc.sample.User;
import cn.sj1.nebula.data.jdbc.sample.UserExtend;
import cn.sj1.nebula.data.query.Condition;
import cn.sj1.nebula.data.query.OrderBy;
import cn.sj1.nebula.data.util.SqlHelper;
import cn.sj1.tinyasm.core.ClassBody;
import cn.sj1.tinyasm.core.ClassBuilder;
import cn.sj1.tinyasm.core.Clazz;
import cn.sj1.tinyasm.core.MethodCode;
import nebula.jdbc.sql.builders.queries.Select;
import nebula.jdbc.sql.builders.schema.ColumnList;
@SuppressWarnings("unused")
public class UserJdbcRepositoryTinyAsmDump {

	public static byte[] dump () throws Exception {
		return new UserJdbcRepositoryTinyAsmDump().dump("cn.sj1.nebula.data.jdbc.UserJdbcRepository");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className, Clazz.of(Object.class),Clazz.of(JdbcRepository.class,Clazz.of(User.class)))
			.access(ACC_PUBLIC | ACC_SUPER).body();

		classBody.private_().field("conn", Clazz.of(Connection.class));
		classBody.private_().field("sqlHelper", Clazz.of(SqlHelper.class));
		__init_(classBody);
		_setConnection(classBody);
		_initJdbc(classBody);
		_listJdbc(classBody);
		_listJdbc_cnsj1nebuladataqueryCondition_cnsj1nebuladataqueryOrderBy_int_int_cnsj1nebuladatabasicPageList(classBody);
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

	protected void __init_(ClassBody classBody) {
		MethodCode code = classBody.public_().method("<init>").begin();

		code.LINE();
		code.LOAD("this");
		code.SPECIAL(Object.class, "<init>").INVOKE();

		code.LINE();
		code.LOAD("this");
		code.NEW(SqlHelper.class);
		code.DUP();
		code.SPECIAL(SqlHelper.class, "<init>").INVOKE();
		code.PUTFIELD_OF_THIS("sqlHelper");

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _setConnection(ClassBody classBody) {
		MethodCode code = classBody.public_().method("setConnection")
			.parameter("conn",Connection.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("conn");
		code.PUTFIELD_OF_THIS("conn");

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _initJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("initJdbc")
			.throws_(SQLException.class ).begin();

		code.LINE();
		code.NEW(ColumnList.class);
		code.DUP();
		code.SPECIAL(ColumnList.class, "<init>").INVOKE();
		code.STORE("columnList",ColumnList.class);

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("id INTEGER(10) PRIMARY KEY");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("name VARCHAR(256)");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("description VARCHAR(256)");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("createAt TIMESTAMP");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("updateAt TIMESTAMP");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("USER");
		code.LOAD("columnList");
		code.VIRTUAL("mergeIfExists")
			.return_(boolean.class)
			.parameter(Connection.class)
			.parameter(String.class)
			.parameter(ColumnList.class).INVOKE();
		Label label7OfIFNE = new Label();
		code.IFNE(label7OfIFNE);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("CREATE TABLE USER(id INTEGER(10),name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP,PRIMARY KEY(id))");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "execute")
			.return_(boolean.class).INVOKE();
		code.POP();

		code.visitLabel(label7OfIFNE);

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _listJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("listJdbc")
			.return_(Clazz.of(PageList.class,Clazz.of(User.class)) )
			.throws_(SQLException.class )
			.parameter("start",Clazz.of(int.class))
			.parameter("max",Clazz.of(int.class)).begin();

		code.LINE();
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>")
			.parameter(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(User.class)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(SqlHelper.class, "select")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USER");
		code.VIRTUAL(Select.class, "from")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOAD("start");
		code.VIRTUAL(Select.class, "offset")
			.return_(Select.class)
			.parameter(int.class).INVOKE();
		code.LOAD("max");
		code.VIRTUAL(Select.class, "max")
			.return_(Select.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL")
			.return_(String.class).INVOKE();
		code.STORE("sql",String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.return_(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);
		Label label6OfGOTO = new Label();

		code.visitLabel(label6OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		Label label4OfIFEQ = new Label();
		code.IFEQ(label4OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity")
			.return_(UserExtend.class)
			.parameter(ResultSet.class).INVOKE();
		code.INTERFACE(PageList.class, "add")
			.return_(boolean.class)
			.parameter(Object.class).INVOKE();
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
		code.VIRTUAL(SqlHelper.class, "select")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USER");
		code.VIRTUAL(Select.class, "from")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL")
			.return_(String.class).INVOKE();
		code.STORE("sqlCount",String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.INTERFACE(Connection.class, "createStatement")
			.return_(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery")
			.return_(ResultSet.class)
			.parameter(String.class).INVOKE();
		code.STORE("resultSetCount",ResultSet.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt")
			.return_(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("totalSize",int.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _listJdbc_cnsj1nebuladataqueryCondition_cnsj1nebuladataqueryOrderBy_int_int_cnsj1nebuladatabasicPageList(ClassBody classBody) {
		MethodCode code = classBody.public_().method("listJdbc")
			.return_(Clazz.of(PageList.class,Clazz.of(User.class)) )
			.throws_(SQLException.class )
			.parameter("condition",Clazz.of(Condition.class))
			.parameter("orderBy",Clazz.of(OrderBy.class))
			.parameter("start",Clazz.of(int.class))
			.parameter("max",Clazz.of(int.class)).begin();

		code.LINE();
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>")
			.parameter(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(User.class)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("sqlHelper");
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(SqlHelper.class, "select")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USER");
		code.VIRTUAL(Select.class, "from")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOAD("condition");
		code.VIRTUAL(Select.class, "where")
			.return_(Select.class)
			.parameter(Condition.class).INVOKE();
		code.LOAD("orderBy");
		code.VIRTUAL(Select.class, "orderby")
			.return_(Select.class)
			.parameter(OrderBy.class).INVOKE();
		code.LOAD("start");
		code.VIRTUAL(Select.class, "offset")
			.return_(Select.class)
			.parameter(int.class).INVOKE();
		code.LOAD("max");
		code.VIRTUAL(Select.class, "max")
			.return_(Select.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL")
			.return_(String.class).INVOKE();
		code.STORE("sql",String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.return_(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);
		Label label6OfGOTO = new Label();

		code.visitLabel(label6OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		Label label4OfIFEQ = new Label();
		code.IFEQ(label4OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity")
			.return_(UserExtend.class)
			.parameter(ResultSet.class).INVOKE();
		code.INTERFACE(PageList.class, "add")
			.return_(boolean.class)
			.parameter(Object.class).INVOKE();
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
		code.VIRTUAL(SqlHelper.class, "select")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USER");
		code.VIRTUAL(Select.class, "from")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOAD("condition");
		code.VIRTUAL(Select.class, "where")
			.return_(Select.class)
			.parameter(Condition.class).INVOKE();
		code.VIRTUAL(Select.class, "toSQL")
			.return_(String.class).INVOKE();
		code.STORE("sqlCount",String.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.INTERFACE(Connection.class, "createStatement")
			.return_(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery")
			.return_(ResultSet.class)
			.parameter(String.class).INVOKE();
		code.STORE("resultSetCount",ResultSet.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt")
			.return_(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("totalSize",int.class);

		code.LINE();
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize")
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _toEntity(ClassBody classBody) {
		MethodCode code = classBody.private_().method("toEntity")
			.return_(UserExtend.class )
			.throws_(SQLException.class )
			.parameter("resultSet",ResultSet.class).begin();

		code.LINE();
		code.NEW(UserExtend.class);
		code.DUP();
		code.SPECIAL(UserExtend.class, "<init>").INVOKE();
		code.STORE("impl",UserExtend.class);

		code.LINE();
		code.LOAD("impl");
		code.LOAD("resultSet");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getLong")
			.return_(long.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(UserExtend.class, "setId")
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("impl");
		code.LOAD("resultSet");
		code.LOADConst(2);
		code.INTERFACE(ResultSet.class, "getString")
			.return_(String.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(UserExtend.class, "setName")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("impl");
		code.LOAD("resultSet");
		code.LOADConst(3);
		code.INTERFACE(ResultSet.class, "getString")
			.return_(String.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(UserExtend.class, "setDescription")
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("impl");
		code.LOAD("resultSet");
		code.LOADConst(4);
		code.INTERFACE(ResultSet.class, "getTimestamp")
			.return_(Timestamp.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(UserExtend.class, "setCreateAt")
			.parameter(Timestamp.class).INVOKE();

		code.LINE();
		code.LOAD("impl");
		code.LOAD("resultSet");
		code.LOADConst(5);
		code.INTERFACE(ResultSet.class, "getTimestamp")
			.return_(Timestamp.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL(UserExtend.class, "setUpdateAt")
			.parameter(Timestamp.class).INVOKE();

		code.LINE();
		code.LOAD("impl");
		code.RETURNTop();

		code.END();
	}

	protected void _findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("findByIdJdbc")
			.return_(User.class )
			.throws_(SQLException.class )
			.parameter("id",long.class).begin();

		code.LINE();
		code.NEW(ArrayList.class);
		code.DUP();
		code.SPECIAL(ArrayList.class, "<init>").INVOKE();
		code.STORE("datas",Clazz.of(List.class,Clazz.of(User.class)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("id");
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.return_(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);
		Label label7OfGOTO = new Label();

		code.visitLabel(label7OfGOTO);

		code.LINE();
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		Label label5OfIFEQ = new Label();
		code.IFEQ(label5OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOAD("this");
		code.LOAD("resultSet");
		code.SPECIAL("toEntity")
			.return_(UserExtend.class)
			.parameter(ResultSet.class).INVOKE();
		code.INTERFACE(List.class, "add")
			.return_(boolean.class)
			.parameter(Object.class).INVOKE();
		code.POP();
		code.GOTO(label7OfGOTO);

		code.visitLabel(label5OfIFEQ);

		code.LINE();
		code.LOAD("datas");
		code.LOADConst(0);
		code.INTERFACE(List.class, "get")
			.return_(Object.class)
			.parameter(int.class).INVOKE();
		code.CHECKCAST(User.class);
		code.RETURNTop();

		code.END();
	}

	protected void _insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("insertJdbc")
			.return_(User.class )
			.throws_(SQLException.class )
			.parameter("data",User.class).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("INSERT INTO USER(id,name,description,createAt,updateAt) VALUES(?,?,?,?,?)");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.return_(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getName")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getDescription")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(4);
		code.VIRTUAL("bindInsertExtend")
			.return_(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.return_(int.class).INVOKE();
		Label label6OfIFLE = new Label();
		code.IFLE(label6OfIFLE);

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(User.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label6OfIFLE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("updateJdbc")
			.return_(User.class )
			.throws_(SQLException.class )
			.parameter("data",User.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(User.class)
			.parameter(long.class).INVOKE();
		code.CHECKCAST(EntitySystem.class);
		code.STORE("extend",EntitySystem.class);

		code.LINE();
		code.LOAD("extend");
		code.INTERFACE(EntitySystem.class, "getUpdateAt")
			.return_(Timestamp.class).INVOKE();
		code.LOAD("data");
		code.CHECKCAST(EntitySystem.class);
		code.INTERFACE(EntitySystem.class, "getUpdateAt")
			.return_(Timestamp.class).INVOKE();
		Label label2OfIF_ACMPNE = new Label();
		code.IF_ACMPNE(label2OfIF_ACMPNE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.visitLabel(label2OfIF_ACMPNE);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getName")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getDescription")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.VIRTUAL("bindUpdateExtend")
			.return_(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(4);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.return_(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.return_(int.class).INVOKE();
		Label label9OfIFLE = new Label();
		code.IFLE(label9OfIFLE);

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(User.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label9OfIFLE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _deleteByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("deleteByIdJdbc")
			.return_(int.class )
			.throws_(SQLException.class )
			.parameter("id",long.class).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("DELETE USER WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("id");
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.return_(int.class).INVOKE();
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
		code.CHECKCAST(User.class);
		code.VIRTUAL("updateJdbc")
			.return_(User.class)
			.parameter(User.class).INVOKE();
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
		code.CHECKCAST(User.class);
		code.VIRTUAL("insertJdbc")
			.return_(User.class)
			.parameter(User.class).INVOKE();
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
			.return_(User.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

}
