/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.builders.schema;

import nebula.jdbc.builders.HasSQLRepresentation;

public interface Column extends HasSQLRepresentation {
	String getName();
}
