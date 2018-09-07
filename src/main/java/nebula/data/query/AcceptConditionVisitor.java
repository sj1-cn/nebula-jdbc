package nebula.data.query;

public interface AcceptConditionVisitor {
	void accept(ConditionVisitor visitor);
}
