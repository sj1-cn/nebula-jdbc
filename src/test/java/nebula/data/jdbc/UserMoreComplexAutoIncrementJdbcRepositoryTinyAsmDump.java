package nebula.data.jdbc;
import org.objectweb.asm.Label;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
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
import nebula.data.jdbc.JdbcRepository;
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
		return new UserMoreComplexAutoIncrementJdbcRepositoryTinyAsmDump().dump("nebula.data.jdbc.UserMoreComplexAutoIncrementJdbcRepository");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.class_(className, Clazz.of(Object.class),Clazz.of(JdbcRepository.class,Clazz.of(UserMoreComplex.class)))
			.access(ACC_PUBLIC | ACC_SUPER).body();

		classBody.private_().field("conn", Clazz.of(Connection.class));
		classBody.private_().field("mapper", Clazz.of(UserMoreComplexExtendJdbcRowMapper.class));
		__init_(classBody);
		_setConnection(classBody);
		_initJdbc(classBody);
		_listJdbc(classBody);
		_listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(classBody);
		_findByIdJdbc(classBody);
		_insertJdbc(classBody);
		_updateJdbc(classBody);
		_deleteByIdJdbc(classBody);
		_mergeIfExists(classBody);
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
		code.NEW(UserMoreComplexExtendJdbcRowMapper.class);
		code.DUP();
		code.SPECIAL(UserMoreComplexExtendJdbcRowMapper.class, "<init>").INVOKE();
		code.PUTFIELD_OF_THIS("mapper");
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
		code.LOADConst("id BIGINT(19) PRIMARY KEY AUTO_INCREMENT");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("string VARCHAR(256)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("bigDecimal DECIMAL(15,6)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("z BOOLEAN");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("c CHAR(1)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("b TINYINT(3)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("s SMALLINT(5)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("i INTEGER(10)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("l BIGINT(19)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("f FLOAT(7)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("d DOUBLE(17)");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("date DATE");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("time TIME");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("timestamp TIMESTAMP");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("createAt TIMESTAMP");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("columnList");
		code.LOADConst("updateAt TIMESTAMP");
		code.STATIC(ColumnDefinition.class, "valueOf")
			.return_(ColumnDefinition.class)
			.parameter(String.class).INVOKE();
		code.VIRTUAL(ColumnList.class, "push")
			.parameter(Object.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("USERMORECOMPLEX");
		code.LOAD("columnList");
		code.STATIC(JDBC.class, "mergeIfExists")
			.return_(boolean.class)
			.parameter(Connection.class)
			.parameter(String.class)
			.parameter(ColumnList.class).INVOKE();
		Label label18OfIFNE = new Label();
		code.IFNE(label18OfIFNE);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("CREATE TABLE USERMORECOMPLEX(id BIGINT(19) PRIMARY KEY AUTO_INCREMENT,string VARCHAR(256),bigDecimal DECIMAL(15,6),z BOOLEAN,c CHAR(1),b TINYINT(3),s SMALLINT(5),i INTEGER(10),l BIGINT(19),f FLOAT(7),d DOUBLE(17),date DATE,time TIME,timestamp TIMESTAMP,createAt TIMESTAMP,updateAt TIMESTAMP)");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "execute")
			.return_(boolean.class).INVOKE();
		code.POP();

		code.visitLabel(label18OfIFNE);

		code.LINE();
		code.RETURN();

		code.END();
	}

	protected void _listJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("listJdbc")
			.return_(Clazz.of(PageList.class,Clazz.of(UserMoreComplex.class)) )
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
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(UserMoreComplex.class)));

		code.LINE();
		code.LOADConst("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt");
		code.STATIC(Select.class, "columns")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USERMORECOMPLEX");
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
		code.GETFIELD_OF_THIS("mapper");
		code.LOAD("resultSet");
		code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
			.return_(UserMoreComplexExtend.class)
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
		code.LOADConst("count(1)");
		code.STATIC(Select.class, "columns")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USERMORECOMPLEX");
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

	protected void _listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(ClassBody classBody) {
		MethodCode code = classBody.public_().method("listJdbc")
			.return_(Clazz.of(PageList.class,Clazz.of(UserMoreComplex.class)) )
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
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(UserMoreComplex.class)));

		code.LINE();
		code.LOADConst("id,string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt");
		code.STATIC(Select.class, "columns")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USERMORECOMPLEX");
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
		code.GETFIELD_OF_THIS("mapper");
		code.LOAD("resultSet");
		code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
			.return_(UserMoreComplexExtend.class)
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
		code.LOADConst("count(1)");
		code.STATIC(Select.class, "columns")
			.return_(Select.class)
			.parameter(String.class).INVOKE();
		code.LOADConst("USERMORECOMPLEX");
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

	protected void _findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("findByIdJdbc")
			.return_(UserMoreComplex.class )
			.throws_(SQLException.class )
			.parameter("key",long.class).begin();
		code.define("preparedStatement",PreparedStatement.class);
		code.define("resultSet",ResultSet.class);
		code.define("datas",Clazz.of(List.class,Clazz.of(UserMoreComplex.class)));

		code.LINE();
		code.NEW(ArrayList.class);
		code.DUP();
		code.SPECIAL(ArrayList.class, "<init>").INVOKE();
		code.STORE("datas",Clazz.of(List.class,Clazz.of(UserMoreComplex.class)));

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("SELECT id, string, bigDecimal, z, c, b, s, i, l, f, d, date, time, timestamp, createAt, updateAt FROM USERMORECOMPLEX WHERE id = ?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("key");
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
		code.GETFIELD_OF_THIS("mapper");
		code.LOAD("resultSet");
		code.VIRTUAL(UserMoreComplexExtendJdbcRowMapper.class, "map")
			.return_(UserMoreComplexExtend.class)
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
		code.CHECKCAST(UserMoreComplex.class);
		code.RETURNTop();

		code.END();
	}

	protected void _insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("insertJdbc")
			.return_(UserMoreComplex.class )
			.throws_(SQLException.class )
			.parameter("data",UserMoreComplex.class).begin();

		code.LINE();
		code.LOADConstNULL();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOADConstNULL();
		code.STORE("rs",ResultSet.class);

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("INSERT INTO USERMORECOMPLEX(string,bigDecimal,z,c,b,s,i,l,f,d,date,time,timestamp,createAt,updateAt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		code.LOADConst(1);
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class)
			.parameter(int.class).INVOKE();
		code.STORE("preparedStatement");

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getString")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getBigDecimal")
			.return_(BigDecimal.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setBigDecimal")
			.parameter(int.class)
			.parameter(BigDecimal.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getZ")
			.return_(Boolean.class).INVOKE();
		code.VIRTUAL(Boolean.class, "booleanValue")
			.return_(boolean.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setBoolean")
			.parameter(int.class)
			.parameter(boolean.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(4);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getC")
			.return_(Character.class).INVOKE();
		code.VIRTUAL(Character.class, "charValue")
			.return_(char.class).INVOKE();
		code.STATIC(String.class, "valueOf")
			.return_(String.class)
			.parameter(char.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(5);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getB")
			.return_(Byte.class).INVOKE();
		code.VIRTUAL(Byte.class, "byteValue")
			.return_(byte.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setByte")
			.parameter(int.class)
			.parameter(byte.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(6);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getS")
			.return_(Short.class).INVOKE();
		code.VIRTUAL(Short.class, "shortValue")
			.return_(short.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setShort")
			.parameter(int.class)
			.parameter(short.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(7);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getI")
			.return_(Integer.class).INVOKE();
		code.VIRTUAL(Integer.class, "intValue")
			.return_(int.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setInt")
			.parameter(int.class)
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(8);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getL")
			.return_(Long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(9);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getF")
			.return_(Float.class).INVOKE();
		code.VIRTUAL(Float.class, "floatValue")
			.return_(float.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setFloat")
			.parameter(int.class)
			.parameter(float.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(10);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getD")
			.return_(Double.class).INVOKE();
		code.VIRTUAL(Double.class, "doubleValue")
			.return_(double.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setDouble")
			.parameter(int.class)
			.parameter(double.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(11);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getDate")
			.return_(Date.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setDate")
			.parameter(int.class)
			.parameter(Date.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(12);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getTime")
			.return_(Time.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setTime")
			.parameter(int.class)
			.parameter(Time.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(13);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getTimestamp")
			.return_(Timestamp.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setTimestamp")
			.parameter(int.class)
			.parameter(Timestamp.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(14);
		code.VIRTUAL("bindInsertExtend")
			.return_(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.return_(int.class).INVOKE();
		Label label18OfIFLE = new Label();
		code.IFLE(label18OfIFLE);

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "getGeneratedKeys")
			.return_(ResultSet.class).INVOKE();
		code.STORE("rs");

		code.LINE();
		code.LOAD("rs");
		code.INTERFACE(ResultSet.class, "next")
			.return_(boolean.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("this");
		code.LOAD("rs");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getLong")
			.return_(long.class)
			.parameter(int.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(UserMoreComplex.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label18OfIFLE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("updateJdbc")
			.return_(UserMoreComplex.class )
			.throws_(SQLException.class )
			.parameter("data",UserMoreComplex.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getId")
			.return_(Long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(UserMoreComplex.class)
			.parameter(long.class).INVOKE();
		code.CHECKCAST(ClassExtend.class);
		code.STORE("extend",ClassExtend.class);

		code.LINE();
		code.LOAD("extend");
		code.INTERFACE(ClassExtend.class, "getUpdateAt")
			.return_(Timestamp.class).INVOKE();
		code.LOAD("data");
		code.CHECKCAST(ClassExtend.class);
		code.INTERFACE(ClassExtend.class, "getUpdateAt")
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
		code.LOADConst("UPDATE USERMORECOMPLEX SET string=?,bigDecimal=?,z=?,c=?,b=?,s=?,i=?,l=?,f=?,d=?,date=?,time=?,timestamp=?,updateAt=? WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getString")
			.return_(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getBigDecimal")
			.return_(BigDecimal.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setBigDecimal")
			.parameter(int.class)
			.parameter(BigDecimal.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getZ")
			.return_(Boolean.class).INVOKE();
		code.VIRTUAL(Boolean.class, "booleanValue")
			.return_(boolean.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setBoolean")
			.parameter(int.class)
			.parameter(boolean.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(4);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getC")
			.return_(Character.class).INVOKE();
		code.VIRTUAL(Character.class, "charValue")
			.return_(char.class).INVOKE();
		code.STATIC(String.class, "valueOf")
			.return_(String.class)
			.parameter(char.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(5);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getB")
			.return_(Byte.class).INVOKE();
		code.VIRTUAL(Byte.class, "byteValue")
			.return_(byte.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setByte")
			.parameter(int.class)
			.parameter(byte.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(6);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getS")
			.return_(Short.class).INVOKE();
		code.VIRTUAL(Short.class, "shortValue")
			.return_(short.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setShort")
			.parameter(int.class)
			.parameter(short.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(7);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getI")
			.return_(Integer.class).INVOKE();
		code.VIRTUAL(Integer.class, "intValue")
			.return_(int.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setInt")
			.parameter(int.class)
			.parameter(int.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(8);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getL")
			.return_(Long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(9);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getF")
			.return_(Float.class).INVOKE();
		code.VIRTUAL(Float.class, "floatValue")
			.return_(float.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setFloat")
			.parameter(int.class)
			.parameter(float.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(10);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getD")
			.return_(Double.class).INVOKE();
		code.VIRTUAL(Double.class, "doubleValue")
			.return_(double.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setDouble")
			.parameter(int.class)
			.parameter(double.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(11);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getDate")
			.return_(Date.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setDate")
			.parameter(int.class)
			.parameter(Date.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(12);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getTime")
			.return_(Time.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setTime")
			.parameter(int.class)
			.parameter(Time.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(13);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getTimestamp")
			.return_(Timestamp.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setTimestamp")
			.parameter(int.class)
			.parameter(Timestamp.class).INVOKE();

		code.LINE();
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(14);
		code.VIRTUAL("bindUpdateExtend")
			.return_(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(15);
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getId")
			.return_(Long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE();
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.return_(int.class).INVOKE();
		Label label20OfIFLE = new Label();
		code.IFLE(label20OfIFLE);

		code.LINE();
		code.LOAD("this");
		code.LOAD("data");
		code.VIRTUAL(UserMoreComplex.class, "getId")
			.return_(Long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
		code.VIRTUAL("findByIdJdbc")
			.return_(UserMoreComplex.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label20OfIFLE);

		code.LINE();
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _deleteByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.public_().method("deleteByIdJdbc")
			.return_(int.class )
			.throws_(SQLException.class )
			.parameter("key",long.class).begin();

		code.LINE();
		code.LOAD("this");
		code.GETFIELD_OF_THIS("conn");
		code.LOADConst("DELETE USERMORECOMPLEX WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.return_(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE();
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("key");
		code.STATIC(Long.class, "valueOf")
			.return_(Long.class)
			.parameter(long.class).INVOKE();
		code.VIRTUAL(Long.class, "longValue")
			.return_(long.class).INVOKE();
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

	protected void _mergeIfExists(ClassBody classBody) {
		MethodCode code = classBody.public_().method("mergeIfExists")
			.return_(boolean.class )
			.parameter("conn",Connection.class)
			.parameter("tableName",String.class)
			.parameter("columnsExpected",ColumnList.class).begin();

		code.LINE();
		code.LOADConst(0);
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
		code.CHECKCAST(UserMoreComplex.class);
		code.VIRTUAL("updateJdbc")
			.return_(UserMoreComplex.class)
			.parameter(UserMoreComplex.class).INVOKE();
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
		code.CHECKCAST(UserMoreComplex.class);
		code.VIRTUAL("insertJdbc")
			.return_(UserMoreComplex.class)
			.parameter(UserMoreComplex.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void _bridge_findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "findByIdJdbc")
			.return_(Object.class )
			.throws_(SQLException.class )
			.parameter(ACC_SYNTHETIC,"key",long.class).begin();

		code.LINE();
		code.LOAD("this");
		code.LOAD("key");
		code.VIRTUAL("findByIdJdbc")
			.return_(UserMoreComplex.class)
			.parameter(long.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

}
