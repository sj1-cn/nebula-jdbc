package nebula.data.jdbc;

import java.sql.ResultSet;
import static cc1sj.tinyasm.Adv.*;
import java.sql.SQLException;

import cc1sj.tinyasm.AdvClassBuilder;
import cc1sj.tinyasm.AdvMagic;

public class UsingUserExtendJdbcRowMapperMagicBuilder {
	public void __init_(AdvClassBuilder classBody) {
		classBody.public_().method("<init>").code(code -> {
			code.LINE();
			code.LOAD("this");
			code.SPECIAL(Object.class, "<init>").INVOKE();
			code.RETURN();
		});
	}

	public String test(ResultSet resultSet) throws SQLException {
		UserExtendJdbcRowMapper mapper = __("mapper", new_(UserExtendJdbcRowMapper.class));
		UserExtend userExtend = __("userExtend", mapper.map(resultSet));
		return _return(userExtend.getName());
	}

	public static byte[] dump() {
		UsingUserExtendJdbcRowMapperMagicBuilder magicBuilderProxy = AdvMagic.build("nebula.data.jdbc.UsingUserExtendJdbcRowMapper",
				UsingUserExtendJdbcRowMapperMagicBuilder.class);

//		magicBuilderProxy.dumpInit("sayNothing");

		return AdvMagic.dump(magicBuilderProxy);
	}
}
