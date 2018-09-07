package nebula.data.query;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

interface ConditionVisitor {
	void visit(Condition left, ConditionOp op, Condition right);

	void visit(String name, ConditionOp op);

	void visit(String name, ConditionOp op, String value);

	void visit(String name, ConditionOp op, int value);

	void visit(String name, ConditionOp op, long value);

	void visit(String name, ConditionOp op, Date value);

	void visit(String name, ConditionOp op, Time value);

	void visit(String name, ConditionOp op, Timestamp value);

	void visit(String name, ConditionOp op, String first, String second);

	void visit(String name, ConditionOp op, int first, int second);

	void visit(String name, ConditionOp op, long first, long second);

	void visit(String name, ConditionOp op, Date first, Date second);

	void visit(String name, ConditionOp op, Time first, Time second);

	void visit(String name, ConditionOp op, Timestamp first, Timestamp second);
	
	void visitComplex(String name, ConditionOp op, String... value);

	void visitComplex(String name, ConditionOp op, int... value);

	void visitComplex(String name, ConditionOp op, long... value);

	void visitComplex(String name, ConditionOp op, Date... value);

	void visitComplex(String name, ConditionOp op, Time... value);

	void visitComplex(String name, ConditionOp op, Timestamp... value);
}
