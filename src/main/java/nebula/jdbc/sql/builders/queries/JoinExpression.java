/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.sql.builders.queries;

import cn.sj1.nebula.jdbc.sql.HasSQLRepresentation;

class JoinExpression implements HasSQLRepresentation {
    private final String table;
    private final String on;
    private final Type type;

    enum Type {INNER, OUTER};

    JoinExpression(String table, String on, Type type) {
        this.table = table;
        this.on = on;
        this.type = type;
    }

    public String toSQL() {
        return String.format(
            "%s JOIN %s ON %s",
            type.toString(),
            table,
            on
        );
    }
}
