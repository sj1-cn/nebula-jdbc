package nebula.data.jdbc;

import static cc1sj.tinyasm.Adv.__;
import static cc1sj.tinyasm.Adv._return;
import static cc1sj.tinyasm.Adv.new_;

import java.sql.ResultSet;
import java.sql.SQLException;

import cc1sj.tinyasm.AdvMagic;

public class UsingUserExtendJdbcRowMapperMagicBuilder {

	public String test(ResultSet resultSet) throws SQLException {
		UserExtendJdbcRowMapper mapper = __("mapper", new_(UserExtendJdbcRowMapper.class));
		UserExtend userExtend = __("userExtend", mapper.map(resultSet));
		return _return(userExtend.getName());
	}

	public static byte[] dump() {
		return AdvMagic.dump("nebula.data.jdbc.UsingUserExtendJdbcRowMapper", UsingUserExtendJdbcRowMapperMagicBuilder.class);
	}
}
