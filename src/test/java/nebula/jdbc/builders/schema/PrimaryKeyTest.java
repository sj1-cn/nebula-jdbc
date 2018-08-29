/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.builders.schema;

import static nebula.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimaryKeyTest {
	@Test
	public void it_converts_to_sql_a_primary_key() {
		PrimaryKey key = new PrimaryKey(IDENTITY("user_id"));
		assertEquals("PRIMARY KEY (user_id)", key.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_composite_key() {
		PrimaryKey key = PrimaryKey.composed(IDENTITY("user_id"), IDENTITY("post_id"));
		assertEquals("PRIMARY KEY (user_id, post_id)", key.toSQL());
	}
}
