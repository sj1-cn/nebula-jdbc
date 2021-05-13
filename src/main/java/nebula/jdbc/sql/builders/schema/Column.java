/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.sql.builders.schema;

import nebula.jdbc.sql.builders.HasSQLRepresentation;

public interface Column extends HasSQLRepresentation {
	String getName();
}