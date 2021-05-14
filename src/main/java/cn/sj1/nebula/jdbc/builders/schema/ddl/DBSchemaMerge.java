package cn.sj1.nebula.jdbc.builders.schema.ddl;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.nebula.jdbc.builders.schema.ColumnList;
import cn.sj1.nebula.jdbc.builders.schema.JDBC;
import cn.sj1.nebula.jdbc.builders.schema.db.JdbcDababaseMetadata;

public class DBSchemaMerge {
	static Logger logger = LoggerFactory.getLogger(DBSchemaMerge.class);

	JdbcDababaseMetadata jdbcDababaseMetadata = new JdbcDababaseMetadata();

	public boolean merge(Connection conn, String tableName, ColumnList columnsExpected) throws SQLException {
		{
			ColumnList columnsActual = jdbcDababaseMetadata.getColumns(conn, tableName);
			if (columnsActual.size() == 0) {
				return false;
			}

			List<AlterTableColumnCommand> commandBus = compare(columnsExpected, columnsActual);
			logger.info("commandBus {}", commandBus);
			Statement statement = conn.createStatement();
			prepareMerge(statement, tableName, commandBus);
			statement.executeBatch();
			statement.close();
		}
		{
			ColumnList columnsActual = jdbcDababaseMetadata.getColumns(conn, tableName);
			List<AlterTableColumnCommand> commandBus = compare(columnsExpected, columnsActual);
			assert commandBus.size() == 0;
			return true;
		}
	}

	void prepareMerge(Statement statement, String tableName, List<AlterTableColumnCommand> commandBus) throws SQLException {
		for (AlterTableColumnCommand command : commandBus) {
			prepareMerge(statement, tableName, command);
		}
	}

	void prepareMerge(Statement statement, String tableName, AlterTableColumnCommand command) throws SQLException {
		if (command instanceof AlterTable.AlterColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBC.sql("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} ${columndefinition}", tableName, column.name(), makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AddColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBC.sql("ALTER TABLE ${tablename} ADD COLUMN ${columnname} ${columndefinition}", tableName, column.name(), this.makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.DropColumnCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} DROP COLUMN ${columnname}", tableName, command.getColumn().name());
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AlterColumnNullableCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} ALTER COLUMN ${oldname} SET ${nullable}", tableName, command.getColumn().name(), command.getColumn().getNullable() == ResultSetMetaData.columnNoNulls ? "NOT NULL" : "NULL");
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AlterColumnRemarksCommand) {
			String sql = JDBC.sql("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} REMARKS ${remarks}", tableName, command.getColumn().name(), command.getColumn().getRemarks().replaceAll("'", "''"));
			statement.addBatch(sql);
		}
	}

	String makeColumnDefinition(ColumnDefinition column) {
		return JDBC.typeDefinition(column.getDataType(), column.getColumnSize(), column.getDecimalDigits());
	}

	List<AlterTableColumnCommand> compare(ColumnList columnsExpected, ColumnList columnsActual) throws SQLException {

		List<AlterTableColumnCommand> commandBus = new ArrayList<>();

		for (ColumnDefinition exptected : columnsExpected) {
			ColumnDefinition actual = columnsActual.get(exptected.name());
			if (actual == null) {
				commandBus.add(new AlterTable.AddColumnCommand(exptected));
				continue;
			}

			if (actual.getDataType() != exptected.getDataType() || !JDBC.ignoreSize(exptected.getDataType()) && (exptected.getColumnSize() > actual.getColumnSize() || exptected.getDecimalDigits() > actual.getDecimalDigits())) {
				commandBus.add(new AlterTable.AlterColumnCommand(exptected));
			}

			if (exptected.getNullable() != actual.getNullable()) {
				commandBus.add(new AlterTable.AlterColumnNullableCommand(exptected));
			}

			if (!Objects.equals(exptected.getRemarks(), actual.getRemarks())) {
				commandBus.add(new AlterTable.AlterColumnRemarksCommand(exptected));
			}
		}

		for (ColumnDefinition actual : columnsActual) {
			ColumnDefinition exptected = columnsExpected.get(actual.name());
			if (exptected == null) {
				commandBus.add(new AlterTable.DropColumnCommand(actual));
				continue;
			}
		}

		return commandBus;

	}

	public ColumnList getColumns(Connection conn, String tableName) throws SQLException {
		return this.jdbcDababaseMetadata.getColumns(conn, tableName);
	}

}
