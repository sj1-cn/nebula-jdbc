package nebula.data.query;

public enum ConditionOp {
	AND("%s AND %s"),
	OR("%s OR %s"),
	EQ("%s = %s"),
	STARTWITH("%s LIKE \'%s%%\'"),
	ENDWITH("%s LIKE \'%%%s\'"),
	CONTAIN("%s LIKE \'%%%s%%\'"),
	NE("%s <> %s"),
	GE("%s >= %s"),
	GT("%s > %s"),
	LE("%s <= %s"),
	LT("%s < %s"),
	ISNULL("%s IS NULL"),
	ISNOTNULL("%s IS NOT NULL"),
	LIKE("%s LIKE \'%s\'"),
	NOTLIKE("%s NOT LIKE %s"),
	IN("%s IN (%s)"),
	NotIn("%s NOT IN (%s)"),
	BETWEEN("%s BETWEEN %s AND %s");
	final String format ;
	ConditionOp(String format){
		this.format = format;
	}
}
