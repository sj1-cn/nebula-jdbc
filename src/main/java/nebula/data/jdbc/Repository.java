package nebula.data.jdbc;

import nebula.data.query.Condition;

/**
 * Database access for pets.
 */
public interface Repository<T> {

	void init();

	PageList<T> list(int start, int max);
	
	PageList<T> list(Condition condition, int start, int max);

	T findById(Object... keys);

	T insert(T pet);

	T update(T pet);

	int delete(Object... keys);
}
