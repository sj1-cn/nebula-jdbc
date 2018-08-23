/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.builders.schema;

import org.junit.Before;
import org.junit.Test;

import static nebula.jdbc.builders.schema.ColumnFactory.*;
import static org.junit.Assert.assertEquals;

public class IntColumnTest {

	@Before
	public void newColumn() {
	}

	@Test
	public void it_converts_to_sql_an_integer_column() {
		assertEquals("user_id INT", INTEGER("user_id").toSQL());
	}

	@Test
	public void it_converts_to_sql_required_integer_column() {
		assertEquals("user_id INT NOT NULL", INTEGER("user_id").required().toSQL());
	}

	@Test
	public void it_converts_to_sql_an_autoincrementing_integer_column() {
		assertEquals("user_id INT AUTO_INCREMENT", INTEGER("user_id").autoIncrement().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_autoincrementing_integer_column() {
		assertEquals("user_id INT NOT NULL AUTO_INCREMENT", INTEGER("user_id").autoIncrement().required().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_integer_column_with_a_default() {
		assertEquals("user_id INT NOT NULL DEFAULT '1'", INTEGER("user_id").required().defaultValue("1").toSQL());
	}
}
