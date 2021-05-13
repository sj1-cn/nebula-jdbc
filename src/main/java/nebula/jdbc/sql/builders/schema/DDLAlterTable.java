package nebula.jdbc.sql.builders.schema;

public class DDLAlterTable {
	public static class AddColumnCommand extends DDLCommand {

		public AddColumnCommand(ColumnDefinition column) {
			super(column);
		}

	}

	public static class AlterColumnCommand extends DDLCommand {

		public AlterColumnCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class DropColumnCommand extends DDLCommand {

		public DropColumnCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnNullableCommand extends DDLCommand {

		public AlterColumnNullableCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnRemarksCommand extends DDLCommand {

		public AlterColumnRemarksCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class RenameColumnCommand extends DDLCommand {

		ColumnDefinition oldColumn;

		public RenameColumnCommand(ColumnDefinition oldColumn, ColumnDefinition newColumn) {
			super(newColumn);
			this.oldColumn = oldColumn;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(this.getClass().getSimpleName());
			builder.append(" [");
			builder.append(oldColumn);
			builder.append("]");
			builder.append(">");
			builder.append("[");
			builder.append(column);
			builder.append("]");
			return builder.toString();
		}
	}
}
