package cn.sj1.tinydb.data.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cn.sj1.tinydb.jdbc.TestBase;
import cn.sj1.tinydb.jdbc.builders.schema.db.JdbcDababaseMetadata;
import cn.sj1.tinydb.jdbc.builders.schema.db.JdbcDatabaseInfo;

public class JdbcDababaseMetadataTest extends TestBase {
	Connection connection;

	@BeforeEach
	public void before() {
		connection = super.openConnection();
	}

	@AfterEach
	public void after() {
		super.closeConnection();
	}

	@Test
	@SuppressWarnings("static-access")
	public void test_JdbcTypeInfo() throws SQLException {

		JdbcDatabaseInfo jdbcDababaseInfo = new JdbcDababaseMetadata().getDatabaseInfo(connection);
		System.out.println(jdbcDababaseInfo);
	}

//    *  <LI><B>NULLABLE</B> short {@code =>} can you use NULL for this type.
//    *      <UL>
//    *      <LI> typeNoNulls - does not allow NULL values
//    *      <LI> typeNullable - allows NULL values
//    *      <LI> typeNullableUnknown - nullability unknown
//    *      </UL>
//    *  <LI><B>CASE_SENSITIVE</B> boolean{@code =>} is it case sensitive.
//    *  <LI><B>SEARCHABLE</B> short {@code =>} can you use "WHERE" based on this type:
//    *      <UL>
//    *      <LI> typePredNone - No support
//    *      <LI> typePredChar - Only supported with WHERE .. LIKE
//    *      <LI> typePredBasic - Supported except for WHERE .. LIKE
//    *      <LI> typeSearchable - Supported for all WHERE ..
//    *      </UL>
//	@Test
//	public void printUserMapper() {
//		System.out.println(RefineCode.refineCode(toString(UserMapper.class.getName())));
//	}

}
