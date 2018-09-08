package nebula.data.query;

class OrderByItem {
	String field;
	OrderByOp op;
	public OrderByItem(String field, OrderByOp op) {
		super();
		this.field = field;
		this.op = op;
	}	
}

enum OrderByOp {
	ASC("%s ASC"), DESC("%s DESC");
	final String format;

	OrderByOp(String format) {
		this.format = format;
	}
}
