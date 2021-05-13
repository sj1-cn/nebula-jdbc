/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.sql.builders.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nebula.jdbc.sql.builders.HasSQLRepresentation;

public class Update implements HasSQLRepresentation {
	private String table;
	private List<String> columns;
	private Where where;

	private Update(String table) {
		this.table = table;
		columns = new ArrayList<>();
		where = Where.empty();
	}

	public Update columns(String... columns) {
		Collections.addAll(this.columns, columns);
		return this;
	}

//	public <T extends Column> Update columns(List<T> columns) {
//		return columns(ColumnList.namesOf(columns));
//	}
//
//	public <T extends Column> Update where(List<T> columns) {
//		return columns(ColumnList.namesOf(columns));
//	}

	public Update where(String expression) {
		where.and(expression);
		return this;
	}

	public Update where(String[] columns) {
		for (int i = 0; i < columns.length; i++) {
			where.and(String.format("%s = ?", columns[i]));
		}
		return this;
	}

	public Update orWhere(String expression) {
		where.or(expression);
		return this;
	}

	public static Update table(String table) {
		return new Update(table);
	}

	@Override
	public String toSQL() {
		assertNonEmptyColumns();
		return String.format("UPDATE %s SET %s %s", table, columnsToSQL(), where.toSQL()).trim();
	}

	private void assertNonEmptyColumns() {
		if (columns.isEmpty()) {
			throw new IllegalStateException("Cannot determine what columns to update");
		}
	}

	private String columnsToSQL() {
		return String.join(", ", columns.stream().map(column -> column + " = ?").toArray(String[]::new));
	}
}
