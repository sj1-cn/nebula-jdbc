package nebula.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import cc1sj.tinyasm.Adv;
import cc1sj.tinyasm.AdvContext;
import cc1sj.tinyasm.AdvRuntimeReferNameObject;
import cc1sj.tinyasm.ConsumerWithException;
import cc1sj.tinyasm.MethodCode;
import nebula.data.query.Condition;
import nebula.data.query.OrderBy;
import nebula.jdbc.builders.schema.ColumnList;

public class UserAutoIncrementJdbcRepositoryMagicBuilderObjenesisAdvAsmProxy extends UserAutoIncrementJdbcRepositoryMagicBuilder
		implements AdvRuntimeReferNameObject {
	private byte _magicNumber;

	private ThreadLocal<AdvContext> _contextThreadLocal;

	public byte get__MagicNumber() {
		return this._magicNumber;
	}

	public void set__MagicNumber(byte _magicNumber) {
		this._magicNumber = _magicNumber;
	}

	public void set__Context(ThreadLocal<AdvContext> _contextThreadLocal, byte _magicNumber) {
		this._contextThreadLocal = _contextThreadLocal;
		this._magicNumber = _magicNumber;
	}

	public void init() {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		context.execLine(c -> {
			objEval.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "init").INVOKE();
		});
	}

	public PageList<User> list(int param0, int param1) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(PageList.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "list").parameter(int.class).parameter(int.class).return_(PageList.class).INVOKE();
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(PageList.class)) {
			return (PageList<User>) Adv.buildProxyClass(PageList.class, User.class, magicNumber);
		} else {
			return null;
		}
	}

	public PageList<User> list(Condition param0, int param1, int param2) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param2 = context.resolve(param2);
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(PageList.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			eval_param2.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "list").parameter(Condition.class).parameter(int.class).parameter(int.class).return_(PageList.class).INVOKE();
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(PageList.class)) {
			return (PageList<User>) Adv.buildProxyClass(PageList.class, User.class, magicNumber);
		} else {
			return null;
		}
	}

	public PageList<User> list(Condition param0, OrderBy param1, int param2, int param3) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param3 = context.resolve(param3);
		ConsumerWithException<MethodCode> eval_param2 = context.resolve(param2);
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(PageList.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			eval_param2.accept(c);
			eval_param3.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "list").parameter(Condition.class).parameter(OrderBy.class).parameter(int.class).parameter(int.class).return_(PageList.class).INVOKE();
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(PageList.class)) {
			return (PageList<User>) Adv.buildProxyClass(PageList.class, User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User findById(long param0) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "findById").parameter(long.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User insert(User param0) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "insert").parameter(Object.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User update(User param0) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "update").parameter(Object.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public int deleteById(long param0) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(int.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "deleteById").parameter(long.class).return_(int.class).INVOKE();
		});
		return 80 + codeIndex;
	}

	public void setConnection(Connection param0) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		context.execLine(c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "setConnection").parameter(Connection.class).INVOKE();
		});
	}

	public int bindInsertExtend(PreparedStatement param0, int param1) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(int.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "bindInsertExtend").parameter(PreparedStatement.class).parameter(int.class).return_(int.class).INVOKE();
		});
		return 80 + codeIndex;
	}

	public int bindUpdateExtend(PreparedStatement param0, int param1) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(int.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "bindUpdateExtend").parameter(PreparedStatement.class).parameter(int.class).return_(int.class).INVOKE();
		});
		return 80 + codeIndex;
	}

	public void initJdbc() throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		context.execLine(c -> {
			objEval.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "initJdbc").INVOKE();
		});
	}

	public PageList<User> listJdbc(int param0, int param1) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(PageList.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "listJdbc").parameter(int.class).parameter(int.class).return_(PageList.class).INVOKE();
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(PageList.class)) {
			return (PageList<User>) Adv.buildProxyClass(PageList.class, User.class, magicNumber);
		} else {
			return null;
		}
	}

	public PageList<User> listJdbc(Condition param0, OrderBy param1, int param2, int param3) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param3 = context.resolve(param3);
		ConsumerWithException<MethodCode> eval_param2 = context.resolve(param2);
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(PageList.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			eval_param2.accept(c);
			eval_param3.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "listJdbc").parameter(Condition.class).parameter(OrderBy.class).parameter(int.class).parameter(int.class).return_(PageList.class).INVOKE();
		});
		byte magicNumber = (byte) (80 + codeIndex);

		if (Adv.canProxy(PageList.class)) {
			return (PageList<User>) Adv.buildProxyClass(PageList.class, User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User findByIdJdbc(long param0) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "findByIdJdbc").parameter(long.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User insertJdbc(User param0) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "insertJdbc").parameter(Object.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public User updateJdbc(User param0) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(User.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "updateJdbc").parameter(Object.class).return_(Object.class).INVOKE();
			c.CHECKCAST(User.class);
		});
		byte magicNumber = (byte) (80 + codeIndex);
		if (Adv.canProxy(User.class)) {
			return (User) Adv.buildProxyClass(User.class, magicNumber);
		} else {
			return null;
		}
	}

	public int deleteByIdJdbc(long param0) throws SQLException {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		byte codeIndex = context.push(int.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "deleteByIdJdbc").parameter(long.class).return_(int.class).INVOKE();
		});
		return 80 + codeIndex;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean mergeIfExists(Connection param0, String param1, ColumnList param2) {
		AdvContext context = this._contextThreadLocal.get();
		ConsumerWithException<MethodCode> eval_param2 = context.resolve(param2);
		ConsumerWithException<MethodCode> eval_param1 = context.resolve(param1);
		ConsumerWithException<MethodCode> eval_param0 = context.resolve(param0);
		ConsumerWithException<MethodCode> objEval = context.resolve(this);
		context.push(boolean.class, c -> {
			objEval.accept(c);
			eval_param0.accept(c);
			eval_param1.accept(c);
			eval_param2.accept(c);
			c.VIRTUAL(UserAutoIncrementJdbcRepositoryMagicBuilder.class, "mergeIfExists").parameter(Connection.class).parameter(String.class).parameter(ColumnList.class).return_(boolean.class).INVOKE();
		});
	
		return false;
	}

	public void $_setConnection(Connection param0) {
		super.setConnection(param0);
	}

	public void $_initJdbc() throws SQLException {
		super.initJdbc();
	}

	public PageList $_listJdbc(int param0, int param1) throws SQLException {
		return super.listJdbc(param0, param1);
	}

	public PageList $_listJdbc(Condition param0, OrderBy param1, int param2, int param3) throws SQLException {
		return super.listJdbc(param0, param1, param2, param3);
	}

	public User $_findByIdJdbc(long param0) throws SQLException {
		return super.findByIdJdbc(param0);
	}

	public User $_insertJdbc(User param0) throws SQLException {
		return super.insertJdbc(param0);
	}

	public User $_updateJdbc(User param0) throws SQLException {
		return super.updateJdbc(param0);
	}

	public int $_deleteByIdJdbc(long param0) throws SQLException {
		return super.deleteByIdJdbc(param0);
	}
}