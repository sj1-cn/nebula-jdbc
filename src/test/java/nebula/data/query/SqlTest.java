package nebula.data.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static nebula.data.query.Condition.*;

public class SqlTest {

	@Test
	public void testOrderBy() {
		assertEquals("ORDER BY name ASC", OrderBy.empty().orderBy("name").toString());
		assertEquals("ORDER BY name DESC", OrderBy.empty().orderBy("name").desc().toString());
		assertEquals("ORDER BY name ASC, age DESC", OrderBy.empty().orderBy("name").orderBy("age").desc().toString());
	}

	@Test
	public void testCondition() {
		assertEquals("name = \'testname\'", str(field("name").eq("testname")));
		assertEquals("name <> \'testname\'", str(field("name").ne("testname")));
		assertEquals("name LIKE 'testname%'", str(field("name").startWith("testname")));
		assertEquals("name LIKE '%testname'", str(field("name").endWith("testname")));
		assertEquals("name LIKE '%testname%'", str(field("name").contain("testname")));
		assertEquals("name LIKE 'testname'", str(field("name").like("testname")));
		assertEquals("age = 10", str(field("age").eq(10)));
		assertEquals("age <> 10", str(field("age").ne(10)));
		assertEquals("age > 10", str(field("age").gt(10)));
		assertEquals("age >= 10", str(field("age").ge(10)));
		assertEquals("age < 10", str(field("age").lt(10)));
		assertEquals("age <= 10", str(field("age").le(10)));
		assertEquals("age IN (\'10\', \'20\', \'30\', \'40\')", str(field("age").in("10", "20", "30", "40")));
		assertEquals("age IN (10, 20, 30, 40)", str(field("age").in(10, 20, 30, 40)));
		assertEquals("age BETWEEN 10 AND 20", str(field("age").between(10, 20)));
		assertEquals("name = \'testname\' AND id = 10", str(field("name").eq("testname").and("id").eq(10)));
		assertEquals("(name = \'testname\' AND id = 10) AND age > 50",
				str(field("name").eq("testname").and("id").eq(10).and("age").gt(50)));
		assertEquals("name = \'testname\' AND (id = 10 AND age > 50)",
				str(field("name").eq("testname").and(field("id").eq(10).and("age").gt(50))));
		assertEquals("name = \'testname\' AND id >= 10", str(and(field("name").eq("testname"), field("id").ge(10))));
	}

	private String str(Condition c) {
		CommonSQLConditionVisitor conditionVisitorImpl = new CommonSQLConditionVisitor();
		c.accept(conditionVisitorImpl);
		return conditionVisitorImpl.toString();
	}
}
