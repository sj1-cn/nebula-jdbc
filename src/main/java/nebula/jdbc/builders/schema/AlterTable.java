package nebula.jdbc.builders.schema;

import nebula.data.jdbc.Command;

public class AlterTable {
	public static class AddColumnCommand extends Command {

		public AddColumnCommand(ColumnDefinition column) {
			super(column);
		}

	}

	public static class AlterColumnCommand extends Command {

		public AlterColumnCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class DropColumnCommand extends Command {

		public DropColumnCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnNullableCommand extends Command {

		public AlterColumnNullableCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnRemarksCommand extends Command {

		public AlterColumnRemarksCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class RenameColumnCommand extends Command {

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
