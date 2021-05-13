package nebula.jdbc.sql.builders.schema;

public class DDLCommand {
	
	public DDLCommand(ColumnDefinition column) {
		this.column = column;
	}

	protected ColumnDefinition column;

	public ColumnDefinition getColumn() {
		return column;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append(" [");
		builder.append(column);
		builder.append("]");
		return builder.toString();
	}
}
