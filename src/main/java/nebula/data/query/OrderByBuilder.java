package nebula.data.query;

public interface OrderByBuilder {
	OrderByBuilder asc();
	OrderByBuilder desc();
	OrderByBuilder field(String name);
}
