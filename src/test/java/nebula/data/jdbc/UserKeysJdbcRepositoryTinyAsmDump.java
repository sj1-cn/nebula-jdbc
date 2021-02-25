package nebula.data.jdbc;
import org.objectweb.asm.Label;
import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.MethodCode;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Opcodes.*;
import cc1sj.tinyasm.Annotation;
import cc1sj.tinyasm.Clazz;
import nebula.jdbc.builders.schema.JDBC;
import nebula.jdbc.builders.schema.ColumnDefinition;
import java.sql.Connection;
import nebula.data.query.OrderBy;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import nebula.jdbc.builders.queries.Select;
import nebula.data.jdbc.User;
import nebula.data.jdbc.UserExtendJdbcRowMapper;
import nebula.data.jdbc.UserExtend;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
import java.lang.Long;
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
public class UserKeysJdbcRepositoryTinyAsmDump {

public static byte[] dump () throws Exception {

ClassBody classBody = ClassBuilder.make("nebula.data.jdbc.UserKeysJdbcRepository", Clazz.of(java.lang.Object.class),Clazz.of(nebula.data.jdbc.JdbcRepository.class,Clazz.of(nebula.data.jdbc.User.class))).access(ACC_PUBLIC | ACC_SUPER).body();

classBody.field("conn", Clazz.of(Connection.class));
classBody.field("mapper", Clazz.of(UserExtendJdbcRowMapper.class));
{
	MethodCode code = classBody.method("<init>").begin();

	code.LINE(17);
	code.LOAD("this");
	code.SPECIAL(Object.class, "<init>").INVOKE();

	code.LINE(19);
	code.LOAD("this");
	code.NEW(UserExtendJdbcRowMapper.class);
	code.DUP();
	code.SPECIAL(UserExtendJdbcRowMapper.class, "<init>").INVOKE();
	code.PUTFIELD("mapper", UserExtendJdbcRowMapper.class);

	code.LINE(17);
	code.RETURN();
	code.END();
}

{
	MethodCode code = classBody.method("setConnection")
	.parameter("conn",Connection.class).begin();

	code.LINE(23);
	code.LOAD("this");
	code.LOAD("conn");
	code.PUTFIELD("conn", Connection.class);

	code.LINE(24);
	code.RETURN();
	code.END();
}

{
	MethodCode code = classBody.method("initJdbc")
	.tHrow(SQLException.class ).begin();

	code.LINE(28);
	code.NEW(ColumnList.class);
	code.DUP();
	code.SPECIAL(ColumnList.class, "<init>").INVOKE();
	code.STORE("columnList",ColumnList.class);

	code.LINE(29);
	code.LOAD("columnList");
	code.LOADConst("id INTEGER(10) PRIMARY KEY");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(30);
	code.LOAD("columnList");
	code.LOADConst("name VARCHAR(256) PRIMARY KEY");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(31);
	code.LOAD("columnList");
	code.LOADConst("description VARCHAR(256)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(32);
	code.LOAD("columnList");
	code.LOADConst("createAt TIMESTAMP");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(33);
	code.LOAD("columnList");
	code.LOADConst("updateAt TIMESTAMP");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(34);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("USER");
	code.LOAD("columnList");
	code.STATIC(JDBC.class, "mergeIfExists")
		.reTurn(boolean.class)
		.parameter(Connection.class)
		.parameter(String.class)
		.parameter(ColumnList.class).INVOKE();
	Label label7OfIFNE = new Label();
	code.IFNE(label7OfIFNE);

	code.LINE(36);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("CREATE TABLE USER(id INTEGER(10),name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP,PRIMARY KEY(id,name))");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "execute")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.visitLabel(label7OfIFNE);

	code.LINE(39);
	code.RETURN();
	code.END();
}

{
	MethodCode code = classBody.method(Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.User.class)), "listJdbc")
	.tHrow(SQLException.class )
	.parameter("start",int.class)
	.parameter("max",int.class).begin();

	code.LINE(43);
	code.NEW(PageListImpl.class);
	code.DUP();
	code.LOAD("start");
	code.LOAD("max");
	code.SPECIAL(PageListImpl.class, "<init>")
		.parameter(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("datas",Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.User.class)));

	code.LINE(46);
	code.LOADConst("id,name,description,createAt,updateAt");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USER");
	code.VIRTUAL(Select.class, "from")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOAD("start");
	code.VIRTUAL(Select.class, "offset")
		.reTurn(Select.class)
		.parameter(int.class).INVOKE();
	code.LOAD("max");
	code.VIRTUAL(Select.class, "max")
		.reTurn(Select.class)
		.parameter(int.class).INVOKE();
	code.VIRTUAL(Select.class, "toSQL")
		.reTurn(String.class).INVOKE();
	code.STORE("sql",String.class);

	code.LINE(49);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOAD("sql");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);

	code.LINE(51);
	Label label4OfGOTO = new Label();
	code.GOTO(label4OfGOTO);
	Label label6OfIFNE = new Label();

	code.visitLabel(label6OfIFNE);

	code.LINE(52);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserExtendJdbcRowMapper.class, "map")
		.reTurn(UserExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(PageList.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();

	code.visitLabel(label4OfGOTO);

	code.LINE(51);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.IFNE(label6OfIFNE);

	code.LINE(54);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(56);
	code.LOADConst("count(1)");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USER");
	code.VIRTUAL(Select.class, "from")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(Select.class, "toSQL")
		.reTurn(String.class).INVOKE();
	code.STORE("sqlCount",String.class);

	code.LINE(57);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.INTERFACE(Connection.class, "createStatement")
		.reTurn(Statement.class).INVOKE();
	code.LOAD("sqlCount");
	code.INTERFACE(Statement.class, "executeQuery")
		.reTurn(ResultSet.class)
		.parameter(String.class).INVOKE();
	code.STORE("resultSetCount",ResultSet.class);

	code.LINE(58);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.LINE(59);
	code.LOAD("resultSetCount");
	code.LOADConst(1);
	code.INTERFACE(ResultSet.class, "getInt")
		.reTurn(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("totalSize",int.class);

	code.LINE(60);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(61);
	code.LOAD("datas");
	code.LOAD("totalSize");
	code.INTERFACE(PageList.class, "totalSize")
		.parameter(int.class).INVOKE();

	code.LINE(63);
	code.LOAD("datas");
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.User.class)), "listJdbc")
	.tHrow(SQLException.class )
	.parameter("condition",Clazz.of(nebula.data.query.Condition.class))
	.parameter("orderBy",Clazz.of(nebula.data.query.OrderBy.class))
	.parameter("start",int.class)
	.parameter("max",int.class).begin();

	code.LINE(69);
	code.NEW(PageListImpl.class);
	code.DUP();
	code.LOAD("start");
	code.LOAD("max");
	code.SPECIAL(PageListImpl.class, "<init>")
		.parameter(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("datas",Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.User.class)));

	code.LINE(72);
	code.LOADConst("id,name,description,createAt,updateAt");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USER");
	code.VIRTUAL(Select.class, "from")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOAD("condition");
	code.VIRTUAL(Select.class, "where")
		.reTurn(Select.class)
		.parameter(Condition.class).INVOKE();
	code.LOAD("orderBy");
	code.VIRTUAL(Select.class, "orderby")
		.reTurn(Select.class)
		.parameter(OrderBy.class).INVOKE();
	code.LOAD("start");
	code.VIRTUAL(Select.class, "offset")
		.reTurn(Select.class)
		.parameter(int.class).INVOKE();
	code.LOAD("max");
	code.VIRTUAL(Select.class, "max")
		.reTurn(Select.class)
		.parameter(int.class).INVOKE();
	code.VIRTUAL(Select.class, "toSQL")
		.reTurn(String.class).INVOKE();
	code.STORE("sql",String.class);

	code.LINE(75);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOAD("sql");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);

	code.LINE(77);
	Label label4OfGOTO = new Label();
	code.GOTO(label4OfGOTO);
	Label label6OfIFNE = new Label();

	code.visitLabel(label6OfIFNE);

	code.LINE(78);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserExtendJdbcRowMapper.class, "map")
		.reTurn(UserExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(PageList.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();

	code.visitLabel(label4OfGOTO);

	code.LINE(77);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.IFNE(label6OfIFNE);

	code.LINE(80);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(82);
	code.LOADConst("count(1)");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USER");
	code.VIRTUAL(Select.class, "from")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOAD("condition");
	code.VIRTUAL(Select.class, "where")
		.reTurn(Select.class)
		.parameter(Condition.class).INVOKE();
	code.VIRTUAL(Select.class, "toSQL")
		.reTurn(String.class).INVOKE();
	code.STORE("sqlCount",String.class);

	code.LINE(83);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.INTERFACE(Connection.class, "createStatement")
		.reTurn(Statement.class).INVOKE();
	code.LOAD("sqlCount");
	code.INTERFACE(Statement.class, "executeQuery")
		.reTurn(ResultSet.class)
		.parameter(String.class).INVOKE();
	code.STORE("resultSetCount",ResultSet.class);

	code.LINE(84);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.LINE(85);
	code.LOAD("resultSetCount");
	code.LOADConst(1);
	code.INTERFACE(ResultSet.class, "getInt")
		.reTurn(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("totalSize",int.class);

	code.LINE(86);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(87);
	code.LOAD("datas");
	code.LOAD("totalSize");
	code.INTERFACE(PageList.class, "totalSize")
		.parameter(int.class).INVOKE();

	code.LINE(89);
	code.LOAD("datas");
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(ACC_PUBLIC | ACC_VARARGS, User.class, "findByIdJdbc")
	.tHrow(SQLException.class )
	.parameter("keys",Object[].class).begin();
	code.define("preparedStatement",PreparedStatement.class);
	code.define("resultSet",ResultSet.class);
	code.define("datas",Clazz.of(java.util.List.class,Clazz.of(nebula.data.jdbc.User.class)));
	code.define("key",Object.class);

	code.LINE(96);
	code.NEW(ArrayList.class);
	code.DUP();
	code.SPECIAL(ArrayList.class, "<init>").INVOKE();
	code.STORE("datas",Clazz.of(java.util.List.class,Clazz.of(nebula.data.jdbc.User.class)));

	code.LINE(99);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ? AND name = ?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(102);
	code.LOAD("keys");
	code.LOADConst(0);
	code.ARRAYLOAD();
	code.STORE("key",Object.class);

	code.LINE(103);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("key");
	code.CHECKCAST(Long.class);
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(104);
	code.LOAD("keys");
	code.LOADConst(1);
	code.ARRAYLOAD();
	code.STORE("key");

	code.LINE(105);
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.LOAD("key");
	code.CHECKCAST(String.class);
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(107);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);

	code.LINE(109);
	Label label8OfGOTO = new Label();
	code.GOTO(label8OfGOTO);
	Label label10OfIFNE = new Label();

	code.visitLabel(label10OfIFNE);

	code.LINE(110);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserExtendJdbcRowMapper.class, "map")
		.reTurn(UserExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(List.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();

	code.visitLabel(label8OfGOTO);

	code.LINE(109);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.IFNE(label10OfIFNE);

	code.LINE(112);
	code.LOAD("datas");
	code.LOADConst(0);
	code.INTERFACE(List.class, "get")
		.reTurn(Object.class)
		.parameter(int.class).INVOKE();
	code.CHECKCAST(User.class);
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(User.class, "insertJdbc")
	.tHrow(SQLException.class )
	.parameter("data",User.class).begin();

	code.LINE(118);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("INSERT INTO USER(id,name,description,createAt,updateAt) VALUES(?,?,?,?,?)");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(121);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getId")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(122);
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getName")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(123);
	code.LOAD("preparedStatement");
	code.LOADConst(3);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getDescription")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(125);
	code.LOAD("this");
	code.LOAD("preparedStatement");
	code.LOADConst(4);
	code.VIRTUAL("bindInsertExtend")
		.reTurn(int.class)
		.parameter(PreparedStatement.class)
		.parameter(int.class).INVOKE();
	code.POP();

	code.LINE(127);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	Label label6OfIFLE = new Label();
	code.IFLE(label6OfIFLE);

	code.LINE(128);
	code.LOAD("this");
	code.LOADConst(2);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getId")
		.reTurn(long.class).INVOKE();
	code.STATIC(Long.class, "valueOf")
		.reTurn(Long.class)
		.parameter(long.class).INVOKE();
	code.ARRAYSTORE();
	code.DUP();
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getName")
		.reTurn(String.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(User.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();

	code.visitLabel(label6OfIFLE);

	code.LINE(130);
	code.LOADConstNULL();
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(User.class, "updateJdbc")
	.tHrow(SQLException.class )
	.parameter("data",User.class).begin();

	code.LINE(135);
	code.LOAD("this");
	code.LOADConst(2);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getId")
		.reTurn(long.class).INVOKE();
	code.STATIC(Long.class, "valueOf")
		.reTurn(Long.class)
		.parameter(long.class).INVOKE();
	code.ARRAYSTORE();
	code.DUP();
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getName")
		.reTurn(String.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(User.class)
		.parameter(Object[].class).INVOKE();
	code.CHECKCAST(ClassExtend.class);
	code.STORE("extend",ClassExtend.class);

	code.LINE(136);
	code.LOAD("extend");
	code.INTERFACE(ClassExtend.class, "getUpdateAt")
		.reTurn(Timestamp.class).INVOKE();
	code.LOAD("data");
	code.CHECKCAST(ClassExtend.class);
	code.INTERFACE(ClassExtend.class, "getUpdateAt")
		.reTurn(Timestamp.class).INVOKE();
	Label label2OfIF_ACMPNE = new Label();
	code.IF_ACMPNE(label2OfIF_ACMPNE);

	code.LINE(137);
	code.LOADConstNULL();
	code.RETURNTop();

	code.visitLabel(label2OfIF_ACMPNE);

	code.LINE(141);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("UPDATE USER SET description=?,updateAt=? WHERE id=? AND name=?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(143);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getDescription")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(144);
	code.LOAD("this");
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.VIRTUAL("bindUpdateExtend")
		.reTurn(int.class)
		.parameter(PreparedStatement.class)
		.parameter(int.class).INVOKE();
	code.POP();

	code.LINE(145);
	code.LOAD("preparedStatement");
	code.LOADConst(3);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getId")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(146);
	code.LOAD("preparedStatement");
	code.LOADConst(4);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getName")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(148);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	Label label9OfIFLE = new Label();
	code.IFLE(label9OfIFLE);

	code.LINE(149);
	code.LOAD("this");
	code.LOADConst(2);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getId")
		.reTurn(long.class).INVOKE();
	code.STATIC(Long.class, "valueOf")
		.reTurn(Long.class)
		.parameter(long.class).INVOKE();
	code.ARRAYSTORE();
	code.DUP();
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(User.class, "getName")
		.reTurn(String.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(User.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();

	code.visitLabel(label9OfIFLE);

	code.LINE(151);
	code.LOADConstNULL();
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(ACC_PUBLIC | ACC_VARARGS, int.class, "deleteJdbc")
	.tHrow(SQLException.class )
	.parameter("keys",Object[].class).begin();

	code.LINE(157);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("DELETE USER WHERE id=? AND name=?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(160);
	code.LOAD("keys");
	code.LOADConst(0);
	code.ARRAYLOAD();
	code.STORE("key",Object.class);

	code.LINE(161);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("key");
	code.CHECKCAST(Long.class);
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(162);
	code.LOAD("keys");
	code.LOADConst(1);
	code.ARRAYLOAD();
	code.STORE("key");

	code.LINE(163);
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.LOAD("key");
	code.CHECKCAST(String.class);
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(165);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_VARARGS | ACC_SYNTHETIC, Object.class, "findByIdJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object[].class).begin();

	code.LINE(1);
	code.LOAD("this");
	code.LOAD("var1");
	code.VIRTUAL("findByIdJdbc")
		.reTurn(User.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, Object.class, "updateJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object.class).begin();

	code.LINE(1);
	code.LOAD("this");
	code.LOAD("var1");
	code.CHECKCAST(User.class);
	code.VIRTUAL("updateJdbc")
		.reTurn(User.class)
		.parameter(User.class).INVOKE();
	code.RETURNTop();
	code.END();
}

{
	MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, Object.class, "insertJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object.class).begin();

	code.LINE(1);
	code.LOAD("this");
	code.LOAD("var1");
	code.CHECKCAST(User.class);
	code.VIRTUAL("insertJdbc")
		.reTurn(User.class)
		.parameter(User.class).INVOKE();
	code.RETURNTop();
	code.END();
}

return classBody.end().toByteArray();
}
}
