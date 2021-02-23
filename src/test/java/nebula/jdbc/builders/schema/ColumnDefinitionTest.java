package nebula.jdbc.builders.schema;

import static nebula.jdbc.builders.schema.ColumnDefinition.BIT;
import static nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static nebula.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColumnDefinitionTest {

	@Test
	public void testValueOf() {
		{
			String expected = IDENTITY("id").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = BIT("female").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = IDENTITY("id").autoIncrement().toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = VARCHAR("name").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

		{
			String expected = DECIMAL("height").size(10).toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

		{
			String expected = DECIMAL("height").size(10).digits(10).toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").size(10).digits(10).remarks("height's remarks").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").size(10).digits(10).remarks("height's remarks").defaultValue("height's defaultvalue").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

	}

}
