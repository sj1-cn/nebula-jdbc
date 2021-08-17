package cn.sj1.tinydb.data.query;

public interface OrderByBuilder {
	OrderByBuilder asc();
	OrderByBuilder desc();
	OrderByBuilder field(String name);
}
