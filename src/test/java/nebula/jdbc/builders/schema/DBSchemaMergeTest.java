package nebula.jdbc.builders.schema;

import static nebula.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static nebula.jdbc.builders.schema.ColumnDefinition.CHAR;
import static nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static nebula.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import nebula.jdbc.TestBase;
import nebula.jdbc.meta.JdbcDababaseMetadata;

public class DBSchemaMergeTest extends TestBase {
	@Rule
	public TestName name = new TestName();
	String tableName = "UserComplex";
	DBSchemaMerge dbSchemaMerge = new DBSchemaMerge();
	JdbcDababaseMetadata meta = new JdbcDababaseMetadata();
	ColumnList columnsPrepared;
	Connection conn;

	@Before
	public void before() throws SQLException {
		columnsPrepared = new ColumnList();
		columnsPrepared.push(IDENTITY("id"));
		columnsPrepared.push(VARCHAR("name"));
		columnsPrepared.push(DECIMAL("height"));
		columnsPrepared.push(VARCHAR("description"));
		conn = super.openConnection(name.getMethodName());
		{
			List<String> ls = new ArrayList<>();
			List<String> keys = new ArrayList<>();
			for (ColumnDefinition columnDefinition : columnsPrepared) {
				ls.add(columnDefinition.toSQL());
				if (columnDefinition.primarykey) {
					keys.add(columnDefinition.columnName);
				}
			}
			String sqlCreateTable = JDBC.sql(
					"CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", tableName,
					String.join(",", ls), String.join(",", keys));

			conn.createStatement().execute(sqlCreateTable);
		}
	}

	@After
	public void after() {
	}

	@Test
	public void testCompare_eq() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(0, commandBus.size());
	}

	@Test
	public void testCompare_addColumn() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));
		columnsExpected.push(DECIMAL("age"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof DDLAlterTable.AddColumnCommand);
		assertEquals("age", commandBus.get(0).getColumn().getName());
	}

	@Test
	public void test_prepareMerge_addColumn_prepare() throws SQLException {

		DDLAlterTable.AddColumnCommand addColumn = new DDLAlterTable.AddColumnCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE UserComplex ADD COLUMN name VARCHAR(256)");
	}

	@Test
	public void test_prepareMerge_alterColumn_prepare() throws SQLException {

		DDLAlterTable.AlterColumnCommand addColumn = new DDLAlterTable.AlterColumnCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE UserComplex ALTER COLUMN name VARCHAR(256)");
	}

	@Test
	public void test_prepareMerge_dropColumn_prepare() throws SQLException {

		DDLAlterTable.DropColumnCommand addColumn = new DDLAlterTable.DropColumnCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE UserComplex DROP COLUMN name");
	}

	@Test
	public void test_merge_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").required());// nullables
		columnsExpected.push(VARCHAR("favor").size(1024));// add
		columnsExpected.push(DECIMAL("height").size(32).digits(10));// size and digit
		columnsExpected.push(CHAR("description"));// change type

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
			assertNotEquals(columnsExpected.get("description").toString(), columnsActual.get("description").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsActual.get("favor").toString(), columnsActual.get("favor").toString());
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
			assertEquals(columnsExpected.get("description").toString(), columnsActual.get("description").toString());
		}
	}

	@Test
	public void test_merge_addColumn_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("favor").size(1024));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsActual.get("favor").toString(), columnsActual.get("favor").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_type_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(BIGINT("height"));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height"), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_size_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(32));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_digit_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(32).digits(10));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_remarks_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").remarks("person's name"));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_size_digit_remarks_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(50).digits(10).required().remarks("person's height"));

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_nullable_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").required());

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_merge_dropColumn_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.remove("name");

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotNull(columnsActual.get("name"));
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNull(columnsActual.get("name"));
		}
	}

	@Test
	public void test_prepareMerge_addColumn_exec() throws SQLException {

		DDLAlterTable.AddColumnCommand addColumn = new DDLAlterTable.AddColumnCommand(VARCHAR("favor").size(1024));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(addColumn.getColumn().toString(), columnsActual.get("favor").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_type_exec() throws SQLException {

		DDLAlterTable.AlterColumnCommand alterColumn = new DDLAlterTable.AlterColumnCommand(BIGINT("height"));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_size_exec() throws SQLException {

		DDLAlterTable.AlterColumnCommand alterColumn = new DDLAlterTable.AlterColumnCommand(DECIMAL("height").size(32));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_digit_exec() throws SQLException {

		DDLAlterTable.AlterColumnCommand alterColumn = new DDLAlterTable.AlterColumnCommand(
				DECIMAL("height").size(32).digits(10));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_remarks_exec() throws SQLException {

		DDLAlterTable.AlterColumnRemarksCommand alterColumn = new DDLAlterTable.AlterColumnRemarksCommand(
				VARCHAR("name").remarks("person's name"));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_nullable_exec() throws SQLException {

		DDLAlterTable.AlterColumnNullableCommand alterColumn = new DDLAlterTable.AlterColumnNullableCommand(
				VARCHAR("name").required());
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_prepareMerge_dropColumn_exec() throws SQLException {

		DDLAlterTable.DropColumnCommand addColumn = new DDLAlterTable.DropColumnCommand(VARCHAR("name").size(1024));
		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNotNull(columnsActual.get("name"));
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = meta.getColumns(conn, tableName);
			assertNull(columnsActual.get("name"));
		}
	}

	@Test
	public void testCompare_change_type() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(BIGINT("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof DDLAlterTable.AlterColumnCommand);
		assertEquals("height", commandBus.get(0).getColumn().getName());
		assertEquals(JDBCType.BIGINT, commandBus.get(0).getColumn().dataType);
	}

	@Test
	public void testCompare_change_size() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").size(1024));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof DDLAlterTable.AlterColumnCommand);
		assertEquals("name", commandBus.get(0).getColumn().getName());
		assertEquals(1024, commandBus.get(0).getColumn().columnSize);
	}

	@Test
	public void testCompare_change_digit() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height").size(20).digits(2));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof DDLAlterTable.AlterColumnCommand);
		assertEquals("height", commandBus.get(0).getColumn().getName());
		assertEquals(20, commandBus.get(0).getColumn().columnSize);
		assertEquals(2, commandBus.get(0).getColumn().decimalDigits);
	}

	@Test
	public void testCompare_change_remark() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").remarks("name's Remark"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof DDLAlterTable.AlterColumnRemarksCommand);
		assertEquals("AlterColumnRemarksCommand [name VARCHAR(256) REMARKS 'name''s Remark']",
				commandBus.get(0).toString());
		assertEquals("name's Remark", commandBus.get(0).getColumn().remarks);
	}

	@Test
	public void testCompare_change_nullable() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").required());
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertEquals("AlterColumnNullableCommand [name VARCHAR(256) NOT NULL]", commandBus.get(0).toString());
	}

	@Test
	public void testCompare_change_remove_column() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
//		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = meta.getColumns(conn, tableName);

		List<DDLCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual);

		assertEquals(1, commandBus.size());
		assertEquals("DropColumnCommand [name VARCHAR(256)]", commandBus.get(0).toString());
	}

	@Test
	public void testGetCurrentActualColumns() throws SQLException {
		List<ColumnDefinition> actual = meta.getColumns(conn, tableName).list();
		assertListtoString(columnsPrepared.list(), actual);
	}

}
