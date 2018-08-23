/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.builders.schema;

import org.junit.Test;

import nebula.jdbc.builders.schema.ForeignKey;

import static nebula.jdbc.builders.schema.ColumnFactory.*;
import static org.junit.Assert.assertEquals;

public class ForeignKeyTest {
	@Test
	public void it_converts_to_sql_a_foreign_key() {
		ForeignKey key = new ForeignKey(IDENTITY("user_id")).references("id").on("users");
		assertEquals("FOREIGN KEY (user_id) REFERENCES users(id)", key.toSQL());
	}

	@Test(expected = IllegalStateException.class)
	public void it_fails_to_convert_if_no_table_is_referenced() {
		ForeignKey key = new ForeignKey(IDENTITY("user_id")).references("id");
		key.toSQL();
	}

	@Test(expected = IllegalStateException.class)
	public void it_fails_to_convert_if_no_column_is_referenced() {
		ForeignKey key = new ForeignKey(IDENTITY("user_id")).on("users");
		key.toSQL();
	}
}
