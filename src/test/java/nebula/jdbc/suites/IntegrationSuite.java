/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.suites;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import nebula.jdbc.DatabaseTest;
import nebula.jdbc.statements.InsertStatementTest;
import nebula.jdbc.statements.SelectStatementTest;
import nebula.jdbc.statements.UpdateStatementTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DatabaseTest.class,
    InsertStatementTest.class,
    SelectStatementTest.class,
    UpdateStatementTest.class
})
public class IntegrationSuite {
    public static Connection connection;
    public static Properties credentials;

    @ClassRule
    public static ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
//            credentials = new Properties();
//            credentials.load(new FileInputStream("src/test/resources/tests.properties"));
//            DataSource dataSource = ConfigurableDataSource.connectAsUserWith(credentials);
//            connection = source.getConnection();
            
			HikariConfig config = new HikariConfig();
			config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
			config.setConnectionTestQuery("VALUES 1");
			config.addDataSourceProperty("URL",
					"jdbc:h2:~/testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
			config.addDataSourceProperty("user", "sa");
			config.addDataSourceProperty("password", "sa");
//			HikariDataSource ds = new HikariDataSource(config);

			HikariDataSource dataSource = new HikariDataSource(config);
			connection =dataSource.getConnection();
        }

        @Override
        protected void after() {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
