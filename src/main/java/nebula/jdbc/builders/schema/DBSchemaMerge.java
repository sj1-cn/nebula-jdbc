package nebula.jdbc.builders.schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nebula.data.jdbc.Command;

public class DBSchemaMerge {
	static Logger logger = LoggerFactory.getLogger(DBSchemaMerge.class);

	public boolean merge(Connection conn, String tableName, ColumnList columnsExpected) throws SQLException {
		{
			ColumnList columnsActual = getCurrentActualColumns(conn, tableName);
			if (columnsActual.size() == 0) {
				return false;
			}

			List<Command> commandBus = compare(columnsExpected, columnsActual);
			logger.info("commandBus {}", commandBus);
			Statement statement = conn.createStatement();
			prepareMerge(statement, tableName, commandBus);
			statement.executeBatch();
			statement.close();
		}
		{
			ColumnList columnsActual = getCurrentActualColumns(conn, tableName);
			List<Command> commandBus = compare(columnsExpected, columnsActual);
			assert commandBus.size() == 0;
			return true;
		}
	}

	void prepareMerge(Statement statement, String tableName, List<Command> commandBus) throws SQLException {
		for (Command command : commandBus) {
			prepareMerge(statement, tableName, command);
		}
	}

	void prepareMerge(Statement statement, String tableName, Command command) throws SQLException {
		if (command instanceof AlterTable.AlterColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBCConfiguration.sql(
					"ALTER TABLE ${tablename} ALTER COLUMN ${columnname} ${columndefinition}", tableName,
					column.columnName, makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AddColumnCommand) {
			ColumnDefinition column = command.getColumn();
			String sql = JDBCConfiguration.sql("ALTER TABLE ${tablename} ADD COLUMN ${columnname} ${columndefinition}",
					tableName, column.columnName, this.makeColumnDefinition(column));
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.DropColumnCommand) {
			String sql = JDBCConfiguration.sql("ALTER TABLE ${tablename} DROP COLUMN ${columnname}", tableName,
					command.getColumn().columnName);
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AlterColumnNullableCommand) {
			String sql = JDBCConfiguration.sql("ALTER TABLE ${tablename} ALTER COLUMN ${oldname} SET ${nullable}",
					tableName, command.getColumn().columnName,
					command.getColumn().nullable == ResultSetMetaData.columnNoNulls ? "NOT NULL" : "NULL");
			statement.addBatch(sql);
		} else if (command instanceof AlterTable.AlterColumnRemarksCommand) {
			String sql = JDBCConfiguration.sql("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} REMARKS ${remarks}",
					tableName, command.getColumn().columnName, command.getColumn().remarks.replaceAll("'", "''"));
			statement.addBatch(sql);
		}
	}

	String makeColumnDefinition(ColumnDefinition column) {
		return JDBCConfiguration.typeDefinition(column.dataType, column.columnSize, column.decimalDigits);
	}

	List<Command> compare(ColumnList columnsExpected, ColumnList columnsActual) throws SQLException {

		List<Command> commandBus = new ArrayList<>();

		for (ColumnDefinition exptected : columnsExpected) {
			ColumnDefinition actual = columnsActual.get(exptected.getName());
			if (actual == null) {
				commandBus.add(new AlterTable.AddColumnCommand(exptected));
				continue;
			}

			if (actual.dataType != exptected.dataType || !JDBCConfiguration.ignoreSize(exptected.dataType)
					&& (exptected.columnSize > actual.columnSize || exptected.decimalDigits > actual.decimalDigits)) {
				commandBus.add(new AlterTable.AlterColumnCommand(exptected));
			}

			if (exptected.nullable != actual.nullable) {
				commandBus.add(new AlterTable.AlterColumnNullableCommand(exptected));
			}

			if (!Objects.equals(exptected.remarks, actual.remarks)) {
				commandBus.add(new AlterTable.AlterColumnRemarksCommand(exptected));
			}
		}

		for (ColumnDefinition actual : columnsActual) {
			ColumnDefinition exptected = columnsExpected.get(actual.getName());
			if (exptected == null) {
				commandBus.add(new AlterTable.DropColumnCommand(actual));
				continue;
			}
		}

		return commandBus;

	}

	ColumnList getCurrentActualColumns(Connection conn, String tableName) throws SQLException {
		ColumnList columnsActual = new ColumnList();
		DatabaseMetaData meta = conn.getMetaData();
		try (ResultSet rs = meta.getColumns(null, null, tableName, null)) {

			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				JDBCType dataType = JDBCType.valueOf(rs.getInt("DATA_TYPE"));
				String typeName = rs.getString("TYPE_NAME");
				int columnSize = rs.getInt("COLUMN_SIZE");
				int decimalDigits = rs.getInt("DECIMAL_DIGITS");
				int nullable = rs.getInt("NULLABLE");
				String remarks = rs.getString("REMARKS");
				String defaultValue = rs.getString("COLUMN_DEF");
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
				int ordinalPosition = rs.getInt("ORDINAL_POSITION");
				short sourceDataType = rs.getShort("SOURCE_DATA_TYPE");
				String autoincrment = rs.getString("IS_AUTOINCREMENT");

				ColumnDefinition column = new ColumnDefinition(columnName, dataType, typeName, columnSize,
						decimalDigits, nullable, remarks, defaultValue, charOctetLength, ordinalPosition,
						sourceDataType, autoincrment);
				columnsActual.push(column);
			}

			try (ResultSet primaryKeys = meta.getPrimaryKeys(null, null, tableName)) {
				while (primaryKeys.next()) {
					String columnName = primaryKeys.getString("COLUMN_NAME");
					columnsActual.get(columnName).primarykey();
				}
			}
		}
		return columnsActual;
	}
}
