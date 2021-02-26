package nebula.data.jdbc;

import nebula.jdbc.builders.queries.Select;

public class SqlHelper {

	public Select select(String string) {
		return Select.columns(string);
	}

}
