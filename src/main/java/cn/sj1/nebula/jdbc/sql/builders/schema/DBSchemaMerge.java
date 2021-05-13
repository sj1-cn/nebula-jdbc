package cn.sj1.nebula.jdbc.sql.builders.schema;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nebula.jdbc.meta.JdbcDababaseMetadata;

public class DBSchemaMerge {
	static Logger logger = LoggerFactory.getLogger(DBSchemaMerge.class);

	JdbcDababaseMetadata jdbcDababaseMetadata = new JdbcDababaseMetadata();

	public boolean merge(Connection conn, String tableName, ColumnList columnsExpected) throws SQLException {
		{
			ColumnList columnsActual = jdbcDababaseMetadata.getColumns(conn, tableName);
			if (columnsActual.size() == 0) {
				return false;
			}

			List<DDLCommand> commandBus = compare(columnsExpected, columnsActual);
			logger.info("commandBus {}", commandBus);
			Statement statement = conn.createStatement();
			prepareMerge(statement, tableName, commandBus);
			statement.executeBatch();
			statement.close();
		}
		{
			ColumnList columnsActual = jdbcDababaseMetadata.getColumns(conn, tableName);
			List<DDLCommand> commandBus = compare(columnsExpected, columnsActual);
			assert commandBus.size() == 0;
			return true;
		}
	}

	void prepareMerge(Statement statement, String tableName, List<DDLCommand> commandBus) throws SQLException {
		for (DDLCommand command : commandBus) {
			prepareMerge(statement, tableName, command);
		}
	}

	void prepareMerge(Statement statement, String tableName, DDLCommand command) throws SQLException {
		if (command instanceof DDLAlterTable.AlterColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBC.sql(
					"ALTER TABLE ${tablename} ALTER COLUMN ${columnname} ${columndefinition}", tableName,
					column.columnName, makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof DDLAlterTable.AddColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBC.sql("ALTER TABLE ${tablename} ADD COLUMN ${columnname} ${columndefinition}",
					tableName, column.columnName, this.makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof DDLAlterTable.DropColumnCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} DROP COLUMN ${columnname}", tableName,
					command.getColumn().columnName);
			statement.addBatch(sql);
		} else if (command instanceof DDLAlterTable.AlterColumnNullableCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} ALTER COLUMN ${oldname} SET ${nullable}",
					tableName, command.getColumn().columnName,
					command.getColumn().nullable == ResultSetMetaData.columnNoNulls ? "NOT NULL" : "NULL");
			statement.addBatch(sql);
		} else if (command instanceof DDLAlterTable.AlterColumnRemarksCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} REMARKS ${remarks}",
					tableName, command.getColumn().columnName, command.getColumn().remarks.replaceAll("'", "''"));
			statement.addBatch(sql);
		}
	}

	String makeColumnDefinition(ColumnDefinition column) {
		return JDBC.typeDefinition(column.dataType, column.columnSize, column.decimalDigits);
	}

	List<DDLCommand> compare(ColumnList columnsExpected, ColumnList columnsActual) throws SQLException {

		List<DDLCommand> commandBus = new ArrayList<>();

		for (ColumnDefinition exptected : columnsExpected) {
			ColumnDefinition actual = columnsActual.get(exptected.getName());
			if (actual == null) {
				commandBus.add(new DDLAlterTable.AddColumnCommand(exptected));
				continue;
			}

			if (actual.dataType != exptected.dataType || !JDBC.ignoreSize(exptected.dataType)
					&& (exptected.columnSize > actual.columnSize || exptected.decimalDigits > actual.decimalDigits)) {
				commandBus.add(new DDLAlterTable.AlterColumnCommand(exptected));
			}

			if (exptected.nullable != actual.nullable) {
				commandBus.add(new DDLAlterTable.AlterColumnNullableCommand(exptected));
			}

			if (!Objects.equals(exptected.remarks, actual.remarks)) {
				commandBus.add(new DDLAlterTable.AlterColumnRemarksCommand(exptected));
			}
		}

		for (ColumnDefinition actual : columnsActual) {
			ColumnDefinition exptected = columnsExpected.get(actual.getName());
			if (exptected == null) {
				commandBus.add(new DDLAlterTable.DropColumnCommand(actual));
				continue;
			}
		}

		return commandBus;

	}
	public ColumnList getColumns(Connection conn, String tableName) throws SQLException {
		return this.jdbcDababaseMetadata.getColumns(conn, tableName);
	}

}
