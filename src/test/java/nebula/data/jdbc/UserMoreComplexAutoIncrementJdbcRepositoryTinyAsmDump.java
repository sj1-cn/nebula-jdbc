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
import java.lang.Float;
import nebula.jdbc.builders.schema.ColumnDefinition;
import java.sql.Connection;
import java.sql.Time;
import java.lang.Double;
import nebula.jdbc.builders.queries.Select;
import java.lang.Integer;
import nebula.data.jdbc.UserMoreComplexExtend;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.lang.Character;
import java.lang.Long;
import nebula.data.jdbc.ClassExtend;
import java.sql.ResultSet;
import java.lang.Short;
import java.lang.Boolean;
import nebula.data.query.Condition;
import java.lang.String;
import java.lang.Byte;
import nebula.data.jdbc.PageList;
import nebula.data.query.OrderBy;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.List;
import nebula.data.jdbc.UserMoreComplex;
import nebula.data.jdbc.UserMoreComplexExtendJdbcRowMapper;
import java.sql.Statement;
import java.lang.Object;
import nebula.jdbc.builders.schema.ColumnList;
import nebula.data.jdbc.PageListImpl;
@SuppressWarnings("unused")
public class UserMoreComplexAutoIncrementJdbcRepositoryTinyAsmDump {

public static byte[] dump () throws Exception {

ClassBody classWriter = ClassBuilder.make("nebula.data.jdbc.UserMoreComplexAutoIncrementJdbcRepository", Clazz.of(java.lang.Object.class),Clazz.of(nebula.data.jdbc.JdbcRepository.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class))).access(ACC_PUBLIC | ACC_SUPER).body();

classWriter.field("conn", Clazz.of(Connection.class));
classWriter.field("mapper", Clazz.of(UserMoreComplexExtendJdbcRowMapper.class));
classWriter.method("<init>").code(code -> {

	code.LINE(17);
	code.LOAD("this");
	code.SPECIAL(Object.class, "<init>").INVOKE();

	code.LINE(19);
	code.LOAD("this");
	code.NEW(UserMoreComplexExtendJdbcRowMapper.class);
	code.DUP();
	code.SPECIAL(UserMoreComplexExtendJdbcRowMapper.class, "<init>").INVOKE();
	code.PUTFIELD("mapper", UserMoreComplexExtendJdbcRowMapper.class);
	code.RETURN();
});
classWriter.method("setConnection")
	.parameter("conn",Connection.class).code(code -> {

	code.LINE(23);
	code.LOAD("this");
	code.LOAD("conn");
	code.PUTFIELD("conn", Connection.class);

	code.LINE(24);
	code.RETURN();
});
classWriter.method("initJdbc")
	.tHrow(SQLException.class ).code(code -> {

	code.LINE(29);
	code.NEW(ColumnList.class);
	code.DUP();
	code.SPECIAL(ColumnList.class, "<init>").INVOKE();
	code.STORE("columnList",ColumnList.class);

	code.LINE(30);
	code.LOAD("columnList");
	code.LOADConst("id BIGINT(19) PRIMARY KEY AUTO_INCREMENT");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(31);
	code.LOAD("columnList");
	code.LOADConst("string VARCHAR(256)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(32);
	code.LOAD("columnList");
	code.LOADConst("bigDecimal DECIMAL(15,6)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(33);
	code.LOAD("columnList");
	code.LOADConst("z BOOLEAN");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(34);
	code.LOAD("columnList");
	code.LOADConst("c CHAR(1)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(35);
	code.LOAD("columnList");
	code.LOADConst("b TINYINT(3)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(36);
	code.LOAD("columnList");
	code.LOADConst("s SMALLINT(5)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(37);
	code.LOAD("columnList");
	code.LOADConst("i INTEGER(10)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(38);
	code.LOAD("columnList");
	code.LOADConst("l BIGINT(19)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(39);
	code.LOAD("columnList");
	code.LOADConst("f FLOAT(7)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(40);
	code.LOAD("columnList");
	code.LOADConst("d DOUBLE(17)");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(41);
	code.LOAD("columnList");
	code.LOADConst("date DATE");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(42);
	code.LOAD("columnList");
	code.LOADConst("time TIME");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(43);
	code.LOAD("columnList");
	code.LOADConst("timestamp TIMESTAMP");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(44);
	code.LOAD("columnList");
	code.LOADConst("createAt TIMESTAMP");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(45);
	code.LOAD("columnList");
	code.LOADConst("updateAt TIMESTAMP");
	code.STATIC(ColumnDefinition.class, "valueOf")
		.reTurn(ColumnDefinition.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(ColumnList.class, "push")
		.parameter(Object.class).INVOKE();

	code.LINE(46);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("USERMORECOMPLEX");
	code.LOAD("columnList");
	code.STATIC(JDBC.class, "mergeIfExists")
		.reTurn(boolean.class)
		.parameter(Connection.class)
		.parameter(String.class)
		.parameter(ColumnList.class).INVOKE();
	Label label18OfIFNE = new Label();
	code.IFNE(label18OfIFNE);

	code.LINE(47);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("CREATE TABLE USERMORECOMPLEX(id BIGINT(19) PRIMARY KEY AUTO_INCREMENT,string VARCHAR(256),bigDecimal DECIMAL(15,6),z BOOLEAN,c CHAR(1),b TINYINT(3),s SMALLINT(5),i INTEGER(10),l BIGINT(19),f FLOAT(7),d DOUBLE(17),date DATE,time TIME,timestamp TIMESTAMP,createAt TIMESTAMP,updateAt TIMESTAMP)");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "execute")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.visitLabel(label18OfIFNE);

	code.LINE(49);
	code.RETURN();
});
classWriter.method(Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)), "listJdbc")
	.tHrow(SQLException.class )
	.parameter("start",int.class)
	.parameter("max",int.class).code(code -> {

	code.LINE(53);
	code.NEW(PageListImpl.class);
	code.DUP();
	code.LOAD("start");
	code.LOAD("max");
	code.SPECIAL(PageListImpl.class, "<init>")
		.parameter(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("datas",Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)));

	code.LINE(56);
	code.LOADConst("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USERMORECOMPLEX");
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

	code.LINE(59);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOAD("sql");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);
	Label label6OfGOTO = new Label();

	code.visitLabel(label6OfGOTO);

	code.LINE(61);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	Label label4OfIFEQ = new Label();
	code.IFEQ(label4OfIFEQ);

	code.LINE(62);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserMoreComplexExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
		.reTurn(UserMoreComplexExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(PageList.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();
	code.GOTO(label6OfGOTO);

	code.visitLabel(label4OfIFEQ);

	code.LINE(64);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(66);
	code.LOADConst("count(1)");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USERMORECOMPLEX");
	code.VIRTUAL(Select.class, "from")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.VIRTUAL(Select.class, "toSQL")
		.reTurn(String.class).INVOKE();
	code.STORE("sqlCount",String.class);

	code.LINE(67);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.INTERFACE(Connection.class, "createStatement")
		.reTurn(Statement.class).INVOKE();
	code.LOAD("sqlCount");
	code.INTERFACE(Statement.class, "executeQuery")
		.reTurn(ResultSet.class)
		.parameter(String.class).INVOKE();
	code.STORE("resultSetCount",ResultSet.class);

	code.LINE(68);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.LINE(69);
	code.LOAD("resultSetCount");
	code.LOADConst(1);
	code.INTERFACE(ResultSet.class, "getInt")
		.reTurn(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("totalSize",int.class);

	code.LINE(70);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(71);
	code.LOAD("datas");
	code.LOAD("totalSize");
	code.INTERFACE(PageList.class, "totalSize")
		.parameter(int.class).INVOKE();

	code.LINE(73);
	code.LOAD("datas");
	code.RETURNTop();
});
classWriter.method(Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)), "listJdbc")
	.tHrow(SQLException.class )
	.parameter("condition",Clazz.of(nebula.data.query.Condition.class))
	.parameter("orderBy",Clazz.of(nebula.data.query.OrderBy.class))
	.parameter("start",int.class)
	.parameter("max",int.class).code(code -> {

	code.LINE(79);
	code.NEW(PageListImpl.class);
	code.DUP();
	code.LOAD("start");
	code.LOAD("max");
	code.SPECIAL(PageListImpl.class, "<init>")
		.parameter(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("datas",Clazz.of(nebula.data.jdbc.PageList.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)));

	code.LINE(82);
	code.LOADConst("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USERMORECOMPLEX");
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

	code.LINE(85);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOAD("sql");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);
	Label label6OfGOTO = new Label();

	code.visitLabel(label6OfGOTO);

	code.LINE(87);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	Label label4OfIFEQ = new Label();
	code.IFEQ(label4OfIFEQ);

	code.LINE(88);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserMoreComplexExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
		.reTurn(UserMoreComplexExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(PageList.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();
	code.GOTO(label6OfGOTO);

	code.visitLabel(label4OfIFEQ);

	code.LINE(90);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(92);
	code.LOADConst("count(1)");
	code.STATIC(Select.class, "columns")
		.reTurn(Select.class)
		.parameter(String.class).INVOKE();
	code.LOADConst("USERMORECOMPLEX");
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

	code.LINE(93);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.INTERFACE(Connection.class, "createStatement")
		.reTurn(Statement.class).INVOKE();
	code.LOAD("sqlCount");
	code.INTERFACE(Statement.class, "executeQuery")
		.reTurn(ResultSet.class)
		.parameter(String.class).INVOKE();
	code.STORE("resultSetCount",ResultSet.class);

	code.LINE(94);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.LINE(95);
	code.LOAD("resultSetCount");
	code.LOADConst(1);
	code.INTERFACE(ResultSet.class, "getInt")
		.reTurn(int.class)
		.parameter(int.class).INVOKE();
	code.STORE("totalSize",int.class);

	code.LINE(96);
	code.LOAD("resultSetCount");
	code.INTERFACE(ResultSet.class, "close").INVOKE();

	code.LINE(97);
	code.LOAD("datas");
	code.LOAD("totalSize");
	code.INTERFACE(PageList.class, "totalSize")
		.parameter(int.class).INVOKE();

	code.LINE(99);
	code.LOAD("datas");
	code.RETURNTop();
});
classWriter.method(ACC_PUBLIC | ACC_VARARGS, UserMoreComplex.class, "findByIdJdbc")
	.tHrow(SQLException.class )
	.parameter("keys",Object[].class).code(code -> {
	code.define("preparedStatement",PreparedStatement.class);
	code.define("resultSet",ResultSet.class);
	code.define("datas",Clazz.of(java.util.List.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)));
	code.define("key",Object.class);

	code.LINE(106);
	code.NEW(ArrayList.class);
	code.DUP();
	code.SPECIAL(ArrayList.class, "<init>").INVOKE();
	code.STORE("datas",Clazz.of(java.util.List.class,Clazz.of(nebula.data.jdbc.UserMoreComplex.class)));

	code.LINE(108);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("SELECT id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp, createAt, updateAt FROM USERMORECOMPLEX WHERE id = ?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(110);
	code.LOAD("keys");
	code.LOADConst(0);
	code.ARRAYLOAD();
	code.STORE("key",Object.class);

	code.LINE(111);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("key");
	code.CHECKCAST(Long.class);
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(113);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeQuery")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("resultSet",ResultSet.class);
	Label label8OfGOTO = new Label();

	code.visitLabel(label8OfGOTO);

	code.LINE(115);
	code.LOAD("resultSet");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	Label label6OfIFEQ = new Label();
	code.IFEQ(label6OfIFEQ);

	code.LINE(116);
	code.LOAD("datas");
	code.LOAD("this");
	code.GETFIELD("mapper", UserMoreComplexExtendJdbcRowMapper.class);
	code.LOAD("resultSet");
	code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
		.reTurn(UserMoreComplexExtend.class)
		.parameter(ResultSet.class).INVOKE();
	code.INTERFACE(List.class, "add")
		.reTurn(boolean.class)
		.parameter(Object.class).INVOKE();
	code.POP();
	code.GOTO(label8OfGOTO);

	code.visitLabel(label6OfIFEQ);

	code.LINE(118);
	code.LOAD("datas");
	code.LOADConst(0);
	code.INTERFACE(List.class, "get")
		.reTurn(Object.class)
		.parameter(int.class).INVOKE();
	code.CHECKCAST(UserMoreComplex.class);
	code.RETURNTop();
});
classWriter.method(UserMoreComplex.class, "insertJdbc")
	.tHrow(SQLException.class )
	.parameter("data",UserMoreComplex.class).code(code -> {

	code.LINE(123);
	code.LOADConstNULL();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(124);
	code.LOADConstNULL();
	code.STORE("rs",ResultSet.class);

	code.LINE(126);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("INSERT INTO USERMORECOMPLEX(string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	code.LOADConst(1);
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class)
		.parameter(int.class).INVOKE();
	code.STORE("preparedStatement");

	code.LINE(129);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getString")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(130);
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getBigDecimal")
		.reTurn(BigDecimal.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setBigDecimal")
		.parameter(int.class)
		.parameter(BigDecimal.class).INVOKE();

	code.LINE(131);
	code.LOAD("preparedStatement");
	code.LOADConst(3);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getZ")
		.reTurn(Boolean.class).INVOKE();
	code.VIRTUAL(Boolean.class, "booleanValue")
		.reTurn(boolean.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setBoolean")
		.parameter(int.class)
		.parameter(boolean.class).INVOKE();

	code.LINE(132);
	code.LOAD("preparedStatement");
	code.LOADConst(4);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getC")
		.reTurn(Character.class).INVOKE();
	code.VIRTUAL(Character.class, "charValue")
		.reTurn(char.class).INVOKE();
	code.STATIC(String.class, "valueOf")
		.reTurn(String.class)
		.parameter(char.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(133);
	code.LOAD("preparedStatement");
	code.LOADConst(5);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getB")
		.reTurn(Byte.class).INVOKE();
	code.VIRTUAL(Byte.class, "byteValue")
		.reTurn(byte.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setByte")
		.parameter(int.class)
		.parameter(byte.class).INVOKE();

	code.LINE(134);
	code.LOAD("preparedStatement");
	code.LOADConst(6);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getS")
		.reTurn(Short.class).INVOKE();
	code.VIRTUAL(Short.class, "shortValue")
		.reTurn(short.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setShort")
		.parameter(int.class)
		.parameter(short.class).INVOKE();

	code.LINE(135);
	code.LOAD("preparedStatement");
	code.LOADConst(7);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getI")
		.reTurn(Integer.class).INVOKE();
	code.VIRTUAL(Integer.class, "intValue")
		.reTurn(int.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setInt")
		.parameter(int.class)
		.parameter(int.class).INVOKE();

	code.LINE(136);
	code.LOAD("preparedStatement");
	code.LOADConst(8);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getL")
		.reTurn(Long.class).INVOKE();
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(137);
	code.LOAD("preparedStatement");
	code.LOADConst(9);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getF")
		.reTurn(Float.class).INVOKE();
	code.VIRTUAL(Float.class, "floatValue")
		.reTurn(float.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setFloat")
		.parameter(int.class)
		.parameter(float.class).INVOKE();

	code.LINE(138);
	code.LOAD("preparedStatement");
	code.LOADConst(10);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getD")
		.reTurn(Double.class).INVOKE();
	code.VIRTUAL(Double.class, "doubleValue")
		.reTurn(double.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setDouble")
		.parameter(int.class)
		.parameter(double.class).INVOKE();

	code.LINE(139);
	code.LOAD("preparedStatement");
	code.LOADConst(11);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getDate")
		.reTurn(Date.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setDate")
		.parameter(int.class)
		.parameter(Date.class).INVOKE();

	code.LINE(140);
	code.LOAD("preparedStatement");
	code.LOADConst(12);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getTime")
		.reTurn(Time.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setTime")
		.parameter(int.class)
		.parameter(Time.class).INVOKE();

	code.LINE(141);
	code.LOAD("preparedStatement");
	code.LOADConst(13);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getTimestamp")
		.reTurn(Timestamp.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setTimestamp")
		.parameter(int.class)
		.parameter(Timestamp.class).INVOKE();

	code.LINE(143);
	code.LOAD("this");
	code.LOAD("preparedStatement");
	code.LOADConst(14);
	code.VIRTUAL("bindInsertExtend")
		.reTurn(int.class)
		.parameter(PreparedStatement.class)
		.parameter(int.class).INVOKE();
	code.POP();

	code.LINE(145);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	Label label18OfIFLE = new Label();
	code.IFLE(label18OfIFLE);

	code.LINE(146);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "getGeneratedKeys")
		.reTurn(ResultSet.class).INVOKE();
	code.STORE("rs");

	code.LINE(147);
	code.LOAD("rs");
	code.INTERFACE(ResultSet.class, "next")
		.reTurn(boolean.class).INVOKE();
	code.POP();

	code.LINE(148);
	code.LOAD("this");
	code.LOADConst(1);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("rs");
	code.LOADConst(1);
	code.INTERFACE(ResultSet.class, "getLong")
		.reTurn(long.class)
		.parameter(int.class).INVOKE();
	code.STATIC(Long.class, "valueOf")
		.reTurn(Long.class)
		.parameter(long.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();

	code.visitLabel(label18OfIFLE);

	code.LINE(150);
	code.LOADConstNULL();
	code.RETURNTop();
});
classWriter.method(UserMoreComplex.class, "updateJdbc")
	.tHrow(SQLException.class )
	.parameter("data",UserMoreComplex.class).code(code -> {

	code.LINE(155);
	code.LOAD("this");
	code.LOADConst(1);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getId")
		.reTurn(Long.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(Object[].class).INVOKE();
	code.CHECKCAST(ClassExtend.class);
	code.STORE("extend",ClassExtend.class);

	code.LINE(156);
	code.LOAD("extend");
	code.INTERFACE(ClassExtend.class, "getUpdateAt")
		.reTurn(Timestamp.class).INVOKE();
	code.LOAD("data");
	code.CHECKCAST(ClassExtend.class);
	code.INTERFACE(ClassExtend.class, "getUpdateAt")
		.reTurn(Timestamp.class).INVOKE();
	Label label2OfIF_ACMPNE = new Label();
	code.IF_ACMPNE(label2OfIF_ACMPNE);

	code.LINE(157);
	code.LOADConstNULL();
	code.RETURNTop();

	code.visitLabel(label2OfIF_ACMPNE);

	code.LINE(161);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("UPDATE USERMORECOMPLEX SET string=?,bigDecimal=?,z=?,c=?,b=?,s=?,i=?,l=?,f=?,d=?,date=?,time=?,timestamp=?,updateAt=? WHERE id=?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(164);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getString")
		.reTurn(String.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(165);
	code.LOAD("preparedStatement");
	code.LOADConst(2);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getBigDecimal")
		.reTurn(BigDecimal.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setBigDecimal")
		.parameter(int.class)
		.parameter(BigDecimal.class).INVOKE();

	code.LINE(166);
	code.LOAD("preparedStatement");
	code.LOADConst(3);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getZ")
		.reTurn(Boolean.class).INVOKE();
	code.VIRTUAL(Boolean.class, "booleanValue")
		.reTurn(boolean.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setBoolean")
		.parameter(int.class)
		.parameter(boolean.class).INVOKE();

	code.LINE(167);
	code.LOAD("preparedStatement");
	code.LOADConst(4);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getC")
		.reTurn(Character.class).INVOKE();
	code.VIRTUAL(Character.class, "charValue")
		.reTurn(char.class).INVOKE();
	code.STATIC(String.class, "valueOf")
		.reTurn(String.class)
		.parameter(char.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setString")
		.parameter(int.class)
		.parameter(String.class).INVOKE();

	code.LINE(168);
	code.LOAD("preparedStatement");
	code.LOADConst(5);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getB")
		.reTurn(Byte.class).INVOKE();
	code.VIRTUAL(Byte.class, "byteValue")
		.reTurn(byte.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setByte")
		.parameter(int.class)
		.parameter(byte.class).INVOKE();

	code.LINE(169);
	code.LOAD("preparedStatement");
	code.LOADConst(6);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getS")
		.reTurn(Short.class).INVOKE();
	code.VIRTUAL(Short.class, "shortValue")
		.reTurn(short.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setShort")
		.parameter(int.class)
		.parameter(short.class).INVOKE();

	code.LINE(170);
	code.LOAD("preparedStatement");
	code.LOADConst(7);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getI")
		.reTurn(Integer.class).INVOKE();
	code.VIRTUAL(Integer.class, "intValue")
		.reTurn(int.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setInt")
		.parameter(int.class)
		.parameter(int.class).INVOKE();

	code.LINE(171);
	code.LOAD("preparedStatement");
	code.LOADConst(8);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getL")
		.reTurn(Long.class).INVOKE();
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(172);
	code.LOAD("preparedStatement");
	code.LOADConst(9);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getF")
		.reTurn(Float.class).INVOKE();
	code.VIRTUAL(Float.class, "floatValue")
		.reTurn(float.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setFloat")
		.parameter(int.class)
		.parameter(float.class).INVOKE();

	code.LINE(173);
	code.LOAD("preparedStatement");
	code.LOADConst(10);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getD")
		.reTurn(Double.class).INVOKE();
	code.VIRTUAL(Double.class, "doubleValue")
		.reTurn(double.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setDouble")
		.parameter(int.class)
		.parameter(double.class).INVOKE();

	code.LINE(174);
	code.LOAD("preparedStatement");
	code.LOADConst(11);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getDate")
		.reTurn(Date.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setDate")
		.parameter(int.class)
		.parameter(Date.class).INVOKE();

	code.LINE(175);
	code.LOAD("preparedStatement");
	code.LOADConst(12);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getTime")
		.reTurn(Time.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setTime")
		.parameter(int.class)
		.parameter(Time.class).INVOKE();

	code.LINE(176);
	code.LOAD("preparedStatement");
	code.LOADConst(13);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getTimestamp")
		.reTurn(Timestamp.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setTimestamp")
		.parameter(int.class)
		.parameter(Timestamp.class).INVOKE();

	code.LINE(177);
	code.LOAD("this");
	code.LOAD("preparedStatement");
	code.LOADConst(14);
	code.VIRTUAL("bindUpdateExtend")
		.reTurn(int.class)
		.parameter(PreparedStatement.class)
		.parameter(int.class).INVOKE();
	code.POP();

	code.LINE(178);
	code.LOAD("preparedStatement");
	code.LOADConst(15);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getId")
		.reTurn(Long.class).INVOKE();
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(180);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	Label label20OfIFLE = new Label();
	code.IFLE(label20OfIFLE);

	code.LINE(181);
	code.LOAD("this");
	code.LOADConst(1);
	code.NEWARRAY(Object.class);
	code.DUP();
	code.LOADConst(0);
	code.LOAD("data");
	code.VIRTUAL(UserMoreComplex.class, "getId")
		.reTurn(Long.class).INVOKE();
	code.ARRAYSTORE();
	code.VIRTUAL("findByIdJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();

	code.visitLabel(label20OfIFLE);

	code.LINE(183);
	code.LOADConstNULL();
	code.RETURNTop();
});
classWriter.method(ACC_PUBLIC | ACC_VARARGS, int.class, "deleteJdbc")
	.tHrow(SQLException.class )
	.parameter("keys",Object[].class).code(code -> {

	code.LINE(189);
	code.LOAD("this");
	code.GETFIELD("conn", Connection.class);
	code.LOADConst("DELETE USERMORECOMPLEX WHERE id=?");
	code.INTERFACE(Connection.class, "prepareStatement")
		.reTurn(PreparedStatement.class)
		.parameter(String.class).INVOKE();
	code.STORE("preparedStatement",PreparedStatement.class);

	code.LINE(192);
	code.LOAD("keys");
	code.LOADConst(0);
	code.ARRAYLOAD();
	code.STORE("key",Object.class);

	code.LINE(193);
	code.LOAD("preparedStatement");
	code.LOADConst(1);
	code.LOAD("key");
	code.CHECKCAST(Long.class);
	code.VIRTUAL(Long.class, "longValue")
		.reTurn(long.class).INVOKE();
	code.INTERFACE(PreparedStatement.class, "setLong")
		.parameter(int.class)
		.parameter(long.class).INVOKE();

	code.LINE(195);
	code.LOAD("preparedStatement");
	code.INTERFACE(PreparedStatement.class, "executeUpdate")
		.reTurn(int.class).INVOKE();
	code.RETURNTop();
});
classWriter.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, Object.class, "updateJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object.class).code(code -> {

	code.LINE(17);
	code.LOAD("this");
	code.LOAD("var1");
	code.CHECKCAST(UserMoreComplex.class);
	code.VIRTUAL("updateJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(UserMoreComplex.class).INVOKE();
	code.RETURNTop();
});
classWriter.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, Object.class, "insertJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object.class).code(code -> {

	code.LINE(17);
	code.LOAD("this");
	code.LOAD("var1");
	code.CHECKCAST(UserMoreComplex.class);
	code.VIRTUAL("insertJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(UserMoreComplex.class).INVOKE();
	code.RETURNTop();
});
classWriter.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, Object.class, "findByIdJdbc")
	.tHrow(SQLException.class )
	.parameter("var1",Object[].class).code(code -> {

	code.LINE(17);
	code.LOAD("this");
	code.LOAD("var1");
	code.VIRTUAL("findByIdJdbc")
		.reTurn(UserMoreComplex.class)
		.parameter(Object[].class).INVOKE();
	code.RETURNTop();
});
return classWriter.end().toByteArray();
}
}
