package nebula.data.jdbc;

import static cn.sj1.tinyasm.core.Adv.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.sj1.tinyasm.core.AdvMagic;

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
