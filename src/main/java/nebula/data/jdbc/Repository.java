package nebula.data.jdbc;

/**
 * Database access for pets.
 */
public interface Repository<T> {

	void init();

	/**
	 * List pets using start/max limits.
	 *
	 * @param start Start offset.
	 * @param max   Max number of rows.
	 * @return Available pets.
	 */
	PageList<T> list(int start, int max);

	/**
	 * Find a pet by ID.
	 *
	 * @param id T ID.
	 * @return T or null.
	 */
	T findById(Object... keys);

	/**
	 * Insert a pet and returns the generated PK.
	 *
	 * @param pet T to insert.
	 * @return Primary key.
	 */
	T insert(T pet);

	/**
	 * Update a pet and returns true if the pets was updated.
	 *
	 * @param pet T to update.
	 * @return True if the pet was updated.
	 */
	T update(T pet);

	/**
	 * Delete a pet by ID.
	 *
	 * @param id T ID.
	 * @return True if the pet was deleted.
	 */
	int delete(Object... keys);
}
