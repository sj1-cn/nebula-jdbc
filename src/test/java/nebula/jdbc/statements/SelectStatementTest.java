/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.statements;

import com.dbal.jdbc.RowMapper;

import nebula.jdbc.statements.SQLError;
import nebula.jdbc.statements.SelectStatement;
import nebula.jdbc.suites.IntegrationSuite;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

public class SelectStatementTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void it_throws_exception_if_table_does_not_exists() {
        @SuppressWarnings("unchecked")
		SelectStatement<Object> select = new SelectStatement<>(
            IntegrationSuite.connection,
            "non_existing_table",
            mock(RowMapper.class)
        );

        exception.expect(SQLError.class);
        select.execute();
    }
}