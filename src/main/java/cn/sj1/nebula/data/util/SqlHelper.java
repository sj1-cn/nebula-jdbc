package cn.sj1.nebula.data.util;

import nebula.jdbc.builders.queries.Select;

public class SqlHelper {

	public Select select(String string) {
		return Select.columns(string);
	}

}
