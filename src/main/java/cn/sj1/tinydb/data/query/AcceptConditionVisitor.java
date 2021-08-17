package cn.sj1.tinydb.data.query;

public interface AcceptConditionVisitor {
	void accept(SQLConditionVisitor visitor);
}
