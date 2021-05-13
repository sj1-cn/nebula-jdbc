/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.nebula.jdbc.sql.builders.schema;

import cn.sj1.nebula.jdbc.sql.HasSQLRepresentation;

public interface Column extends HasSQLRepresentation {
	String getName();
}
