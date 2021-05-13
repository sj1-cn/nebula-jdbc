/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dbal.jdbc.QueryParameters;
import com.dbal.jdbc.RowMapper;

import cn.sj1.nebula.jdbc.sql.builders.queries.Insert;

public class InsertStatement<T> extends SQLStatement {
    private Insert insert;
    private final RowMapper<T> mapper;

    public InsertStatement(
        Connection connection,
        String table,
        RowMapper<T> mapper
    ) {
        super(connection);
        this.insert = Insert.into(table);
        this.mapper = mapper;
    }

    public InsertStatement<T> columns(String... columns) {
        insert.columns(columns);
        return this;
    }

    public Hydrator<T> execute(Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return new Hydrator<>(key.getLong(1), parameters, mapper);
        } catch (SQLException e) {
            throw SQLError.producedBy(insert, parameters, e);
        }
    }
}
