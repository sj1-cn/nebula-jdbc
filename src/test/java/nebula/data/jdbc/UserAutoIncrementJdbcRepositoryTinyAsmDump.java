package nebula.data.jdbc;
import org.objectweb.asm.Label;
import cc1sj.tinyasm.ClassBody;
import cc1sj.tinyasm.ClassBuilder;
import cc1sj.tinyasm.MethodCode;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Opcodes.*;
import cc1sj.tinyasm.Annotation;
import cc1sj.tinyasm.Clazz;
import nebula.data.jdbc.JdbcRepository;
import java.sql.Connection;
import nebula.data.query.OrderBy;
import nebula.data.jdbc.JdbcRepositoryBase;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import nebula.data.jdbc.User;
import nebula.jdbc.builders.queries.Select;
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
import nebula.data.jdbc.SqlHelper;
import nebula.jdbc.builders.schema.ColumnList;
import java.lang.String;
import nebula.data.jdbc.PageListImpl;
import nebula.data.jdbc.PageList;
@SuppressWarnings("unused")
public class UserAutoIncrementJdbcRepositoryTinyAsmDump {

	public static byte[] dump () throws Exception {
		return new UserAutoIncrementJdbcRepositoryTinyAsmDump().dump("nebula.data.jdbc.UserAutoIncrementJdbcRepository");
	}

	public byte[] dump(String className) throws Exception {
		ClassBody classBody = ClassBuilder.make(className, Clazz.of(JdbcRepositoryBase.class),Clazz.of(JdbcRepository.class,Clazz.of(User.class)))
			.access(ACC_PUBLIC | ACC_SUPER).body();

		classBody.field(0, "conn", Clazz.of(Connection.class));
		classBody.field("mapper", Clazz.of(UserExtendJdbcRowMapper.class));
		classBody.field(0, "sqlHelper", Clazz.of(SqlHelper.class));
		__init_(classBody);
		_setConnection(classBody);
		_initJdbc(classBody);
		_listJdbc(classBody);
		_listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(classBody);
		_findByIdJdbc(classBody);
		_insertJdbc(classBody);
		_updateJdbc(classBody);
		_deleteJdbc(classBody);
		_bridge_findByIdJdbc(classBody);
		_bridge_updateJdbc(classBody);
		_bridge_insertJdbc(classBody);

		return classBody.end().toByteArray();
	}

	protected void __init_(ClassBody classBody) {
		MethodCode code = classBody.method("<init>").begin();

		code.LINE(15);
		code.LOAD("this");
		code.SPECIAL(JdbcRepositoryBase.class, "<init>").INVOKE();

		code.LINE(17);
		code.LOAD("this");
		code.NEW(UserExtendJdbcRowMapper.class);
		code.DUP();
		code.SPECIAL(UserExtendJdbcRowMapper.class, "<init>").INVOKE();
		code.PUTFIELD("mapper", UserExtendJdbcRowMapper.class);

		code.LINE(18);
		code.LOAD("this");
		code.NEW(SqlHelper.class);
		code.DUP();
		code.SPECIAL(SqlHelper.class, "<init>").INVOKE();
		code.PUTFIELD("sqlHelper", SqlHelper.class);

		code.LINE(15);
		code.RETURN();

		code.END();
	}

	protected void _setConnection(ClassBody classBody) {
		MethodCode code = classBody.method("setConnection")
			.parameter("conn",Connection.class).begin();

		code.LINE(22);
		code.LOAD("this");
		code.LOAD("conn");
		code.PUTFIELD("conn", Connection.class);

		code.LINE(23);
		code.RETURN();

		code.END();
	}

	protected void _initJdbc(ClassBody classBody) {
		MethodCode code = classBody.method("initJdbc")
			.tHrow(SQLException.class ).begin();

		code.LINE(27);
		code.NEW(ColumnList.class);
		code.DUP();
		code.SPECIAL(ColumnList.class, "<init>").INVOKE();
		code.STORE("columnList",ColumnList.class);

		code.LINE(28);
		code.LOAD("columnList");
		code.LOADConst("id INTEGER(10) PRIMARY KEY AUTO_INCREMENT");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE(29);
		code.LOAD("columnList");
		code.LOADConst("name VARCHAR(256)");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE(30);
		code.LOAD("columnList");
		code.LOADConst("description VARCHAR(256)");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE(31);
		code.LOAD("columnList");
		code.LOADConst("createAt TIMESTAMP");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE(32);
		code.LOAD("columnList");
		code.LOADConst("updateAt TIMESTAMP");
		code.VIRTUAL(ColumnList.class, "addColumn")
			.parameter(String.class).INVOKE();

		code.LINE(33);
		code.LOAD("this");
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("USER");
		code.LOAD("columnList");
		code.VIRTUAL("checkIsExist")
			.reTurn(boolean.class)
			.parameter(Connection.class)
			.parameter(String.class)
			.parameter(ColumnList.class).INVOKE();
		Label label7OfIFEQ = new Label();
		code.IFEQ(label7OfIFEQ);

		code.LINE(35);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("CREATE TABLE USER(id INTEGER(10) PRIMARY KEY AUTO_INCREMENT,name VARCHAR(256),description VARCHAR(256),createAt TIMESTAMP,updateAt TIMESTAMP)");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "execute")
			.reTurn(boolean.class).INVOKE();
		code.POP();

		code.visitLabel(label7OfIFEQ);

		code.LINE(38);
		code.RETURN();

		code.END();
	}

	protected void _listJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(Clazz.of(PageList.class,Clazz.of(User.class)), "listJdbc")
			.tHrow(SQLException.class )
			.parameter("start",int.class)
			.parameter("max",int.class).begin();

		code.LINE(42);
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>")
			.parameter(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(User.class)));

		code.LINE(45);
		code.LOAD("this");
		code.GETFIELD("sqlHelper", SqlHelper.class);
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(SqlHelper.class, "select")
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

		code.LINE(48);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.reTurn(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);

		code.LINE(50);
		Label label4OfGOTO = new Label();
		code.GOTO(label4OfGOTO);
		Label label6OfIFNE = new Label();

		code.visitLabel(label6OfIFNE);

		code.LINE(51);
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

		code.LINE(50);
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.IFNE(label6OfIFNE);

		code.LINE(53);
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE(55);
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

		code.LINE(56);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.INTERFACE(Connection.class, "createStatement")
			.reTurn(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery")
			.reTurn(ResultSet.class)
			.parameter(String.class).INVOKE();
		code.STORE("resultSetCount",ResultSet.class);

		code.LINE(57);
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.POP();

		code.LINE(58);
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt")
			.reTurn(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("totalSize",int.class);

		code.LINE(59);
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE(60);
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize")
			.parameter(int.class).INVOKE();

		code.LINE(62);
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _listJdbc_nebuladataqueryCondition_nebuladataqueryOrderBy_int_int(ClassBody classBody) {
		MethodCode code = classBody.method(Clazz.of(PageList.class,Clazz.of(User.class)), "listJdbc")
			.tHrow(SQLException.class )
			.parameter("condition",Clazz.of(Condition.class))
			.parameter("orderBy",Clazz.of(OrderBy.class))
			.parameter("start",int.class)
			.parameter("max",int.class).begin();

		code.LINE(68);
		code.NEW(PageListImpl.class);
		code.DUP();
		code.LOAD("start");
		code.LOAD("max");
		code.SPECIAL(PageListImpl.class, "<init>")
			.parameter(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("datas",Clazz.of(PageList.class,Clazz.of(User.class)));

		code.LINE(71);
		code.LOAD("this");
		code.GETFIELD("sqlHelper", SqlHelper.class);
		code.LOADConst("id,name,description,createAt,updateAt");
		code.VIRTUAL(SqlHelper.class, "select")
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

		code.LINE(74);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOAD("sql");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.reTurn(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);

		code.LINE(76);
		Label label4OfGOTO = new Label();
		code.GOTO(label4OfGOTO);
		Label label6OfIFNE = new Label();

		code.visitLabel(label6OfIFNE);

		code.LINE(77);
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

		code.LINE(76);
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.IFNE(label6OfIFNE);

		code.LINE(79);
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE(81);
		code.LOAD("this");
		code.GETFIELD("sqlHelper", SqlHelper.class);
		code.LOADConst("count(1)");
		code.VIRTUAL(SqlHelper.class, "select")
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

		code.LINE(82);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.INTERFACE(Connection.class, "createStatement")
			.reTurn(Statement.class).INVOKE();
		code.LOAD("sqlCount");
		code.INTERFACE(Statement.class, "executeQuery")
			.reTurn(ResultSet.class)
			.parameter(String.class).INVOKE();
		code.STORE("resultSetCount",ResultSet.class);

		code.LINE(83);
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.POP();

		code.LINE(84);
		code.LOAD("resultSetCount");
		code.LOADConst(1);
		code.INTERFACE(ResultSet.class, "getInt")
			.reTurn(int.class)
			.parameter(int.class).INVOKE();
		code.STORE("totalSize",int.class);

		code.LINE(85);
		code.LOAD("resultSetCount");
		code.INTERFACE(ResultSet.class, "close").INVOKE();

		code.LINE(86);
		code.LOAD("datas");
		code.LOAD("totalSize");
		code.INTERFACE(PageList.class, "totalSize")
			.parameter(int.class).INVOKE();

		code.LINE(88);
		code.LOAD("datas");
		code.RETURNTop();

		code.END();
	}

	protected void _findByIdJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_VARARGS, User.class, "findByIdJdbc")
			.tHrow(SQLException.class )
			.parameter("keys",Object[].class).begin();
		code.define("preparedStatement",PreparedStatement.class);
		code.define("resultSet",ResultSet.class);
		code.define("datas",Clazz.of(List.class,Clazz.of(User.class)));
		code.define("key",Object.class);

		code.LINE(95);
		code.NEW(ArrayList.class);
		code.DUP();
		code.SPECIAL(ArrayList.class, "<init>").INVOKE();
		code.STORE("datas",Clazz.of(List.class,Clazz.of(User.class)));

		code.LINE(98);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("SELECT id, name, description, createAt, updateAt FROM USER WHERE id = ?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE(101);
		code.LOAD("keys");
		code.LOADConst(0);
		code.ARRAYLOAD();
		code.STORE("key",Object.class);

		code.LINE(102);
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
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeQuery")
			.reTurn(ResultSet.class).INVOKE();
		code.STORE("resultSet",ResultSet.class);

		code.LINE(106);
		Label label6OfGOTO = new Label();
		code.GOTO(label6OfGOTO);
		Label label8OfIFNE = new Label();

		code.visitLabel(label8OfIFNE);

		code.LINE(107);
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

		code.visitLabel(label6OfGOTO);

		code.LINE(106);
		code.LOAD("resultSet");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.IFNE(label8OfIFNE);

		code.LINE(109);
		code.LOAD("datas");
		code.LOADConst(0);
		code.INTERFACE(List.class, "get")
			.reTurn(Object.class)
			.parameter(int.class).INVOKE();
		code.CHECKCAST(User.class);
		code.RETURNTop();

		code.END();
	}

	protected void _insertJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(User.class, "insertJdbc")
			.tHrow(SQLException.class )
			.parameter("data",User.class).begin();

		code.LINE(114);
		code.LOADConstNULL();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE(115);
		code.LOADConstNULL();
		code.STORE("rs",ResultSet.class);

		code.LINE(118);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("INSERT INTO USER(name,description,createAt,updateAt) VALUES(?,?,?,?)");
		code.LOADConst(1);
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class)
			.parameter(int.class).INVOKE();
		code.STORE("preparedStatement");

		code.LINE(121);
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getName")
			.reTurn(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE(122);
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getDescription")
			.reTurn(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE(124);
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.VIRTUAL("bindInsertExtend")
			.reTurn(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE(126);
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.reTurn(int.class).INVOKE();
		Label label7OfIFLE = new Label();
		code.IFLE(label7OfIFLE);

		code.LINE(127);
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "getGeneratedKeys")
			.reTurn(ResultSet.class).INVOKE();
		code.STORE("rs");

		code.LINE(128);
		code.LOAD("rs");
		code.INTERFACE(ResultSet.class, "next")
			.reTurn(boolean.class).INVOKE();
		code.POP();

		code.LINE(130);
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
			.reTurn(User.class)
			.parameter(Object[].class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label7OfIFLE);

		code.LINE(132);
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _updateJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(User.class, "updateJdbc")
			.tHrow(SQLException.class )
			.parameter("data",User.class).begin();

		code.LINE(138);
		code.LOAD("this");
		code.LOADConst(1);
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
		code.VIRTUAL("findByIdJdbc")
			.reTurn(User.class)
			.parameter(Object[].class).INVOKE();
		code.CHECKCAST(ClassExtend.class);
		code.STORE("extend",ClassExtend.class);

		code.LINE(139);
		code.LOAD("extend");
		code.INTERFACE(ClassExtend.class, "getUpdateAt")
			.reTurn(Timestamp.class).INVOKE();
		code.LOAD("data");
		code.CHECKCAST(ClassExtend.class);
		code.INTERFACE(ClassExtend.class, "getUpdateAt")
			.reTurn(Timestamp.class).INVOKE();
		Label label2OfIF_ACMPNE = new Label();
		code.IF_ACMPNE(label2OfIF_ACMPNE);

		code.LINE(140);
		code.LOADConstNULL();
		code.RETURNTop();

		code.visitLabel(label2OfIF_ACMPNE);

		code.LINE(144);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("UPDATE USER SET name=?,description=?,updateAt=? WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE(147);
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getName")
			.reTurn(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE(148);
		code.LOAD("preparedStatement");
		code.LOADConst(2);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getDescription")
			.reTurn(String.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setString")
			.parameter(int.class)
			.parameter(String.class).INVOKE();

		code.LINE(149);
		code.LOAD("this");
		code.LOAD("preparedStatement");
		code.LOADConst(3);
		code.VIRTUAL("bindUpdateExtend")
			.reTurn(int.class)
			.parameter(PreparedStatement.class)
			.parameter(int.class).INVOKE();
		code.POP();

		code.LINE(150);
		code.LOAD("preparedStatement");
		code.LOADConst(4);
		code.LOAD("data");
		code.VIRTUAL(User.class, "getId")
			.reTurn(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE(152);
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.reTurn(int.class).INVOKE();
		Label label9OfIFLE = new Label();
		code.IFLE(label9OfIFLE);

		code.LINE(153);
		code.LOAD("this");
		code.LOADConst(1);
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
		code.VIRTUAL("findByIdJdbc")
			.reTurn(User.class)
			.parameter(Object[].class).INVOKE();
		code.RETURNTop();

		code.visitLabel(label9OfIFLE);

		code.LINE(155);
		code.LOADConstNULL();
		code.RETURNTop();

		code.END();
	}

	protected void _deleteJdbc(ClassBody classBody) {
		MethodCode code = classBody.method(ACC_PUBLIC | ACC_VARARGS, int.class, "deleteJdbc")
			.tHrow(SQLException.class )
			.parameter("keys",Object[].class).begin();

		code.LINE(161);
		code.LOAD("this");
		code.GETFIELD("conn", Connection.class);
		code.LOADConst("DELETE USER WHERE id=?");
		code.INTERFACE(Connection.class, "prepareStatement")
			.reTurn(PreparedStatement.class)
			.parameter(String.class).INVOKE();
		code.STORE("preparedStatement",PreparedStatement.class);

		code.LINE(164);
		code.LOAD("keys");
		code.LOADConst(0);
		code.ARRAYLOAD();
		code.STORE("key",Object.class);

		code.LINE(165);
		code.LOAD("preparedStatement");
		code.LOADConst(1);
		code.LOAD("key");
		code.CHECKCAST(Long.class);
		code.VIRTUAL(Long.class, "longValue")
			.reTurn(long.class).INVOKE();
		code.INTERFACE(PreparedStatement.class, "setLong")
			.parameter(int.class)
			.parameter(long.class).INVOKE();

		code.LINE(167);
		code.LOAD("preparedStatement");
		code.INTERFACE(PreparedStatement.class, "executeUpdate")
			.reTurn(int.class).INVOKE();
		code.RETURNTop();

		code.END();
	}

	protected void _bridge_findByIdJdbc(ClassBody classBody) {
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

	protected void _bridge_updateJdbc(ClassBody classBody) {
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

	protected void _bridge_insertJdbc(ClassBody classBody) {
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

}
