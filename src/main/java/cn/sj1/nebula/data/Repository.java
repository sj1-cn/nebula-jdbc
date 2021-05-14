package cn.sj1.nebula.data;

import cn.sj1.nebula.data.query.Condition;
import cn.sj1.nebula.data.query.OrderBy;

/**
 * Database access for pets.
 */
public interface Repository<T> {

	void init();

	PageList<T> list(int start, int max);

	PageList<T> list(Condition condition, int start, int max);

	PageList<T> list(Condition condition, OrderBy orderby, int start, int max);

	T findById(long id);

//	T findByName(String id);

	T insert(T pet);

	T update(T pet);

	int deleteById(long key);

//	int deleteByName(String name);
}
