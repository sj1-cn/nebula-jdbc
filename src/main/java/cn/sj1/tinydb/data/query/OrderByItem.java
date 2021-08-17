package cn.sj1.tinydb.data.query;

class OrderByItem {
	String field;
	OrderByOp op;
	public OrderByItem(String field, OrderByOp op) {
		super();
		this.field = field;
		this.op = op;
	}	
}
