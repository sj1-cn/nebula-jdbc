/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.sql.builders.queries;

import java.util.ArrayList;
import java.util.List;

import nebula.jdbc.sql.builders.HasSQLRepresentation;
import nebula.jdbc.sql.builders.queries.JoinExpression.Type;

class Join implements HasSQLRepresentation {
    private List<JoinExpression> joins;

    private Join() {
        joins = new ArrayList<>();
    }

    public static Join empty() {
        return new Join();
    }

    Join inner(String table, String on) {
        joins.add(new JoinExpression(table, on, Type.INNER));
        return this;
    }

    Join outer(String table, String on) {
        joins.add(new JoinExpression(table, on, Type.OUTER));
        return this;
    }

    public boolean isEmpty() {
        return joins.isEmpty();
    }

    public String toSQL() {
        StringBuilder joinClauses = new StringBuilder();
        joins.forEach(join -> joinClauses.append(join.toSQL()).append(" "));
        return joinClauses.toString().replaceAll(" $", "");
    }
}
