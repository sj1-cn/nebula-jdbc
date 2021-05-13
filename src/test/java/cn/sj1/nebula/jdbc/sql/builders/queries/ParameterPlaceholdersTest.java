/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.nebula.jdbc.sql.builders.queries;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cn.sj1.nebula.jdbc.sql.builders.queries.ParameterPlaceholders;

public class ParameterPlaceholdersTest {
    @Test
    public void it_converts_to_sql_a_single_parameter() {
        assertEquals("(?)", ParameterPlaceholders.generate(1));
    }

    @Test
    public void it_converts_to_sql_several_parameter_placeholders() {
        assertEquals("(?, ?, ?, ?)", ParameterPlaceholders.generate(4));
    }

    @Test(expected = IllegalStateException.class)
    public void it_does_not_generate_placeholders_for_non_positive_numeric_values() {
        ParameterPlaceholders.generate(0);
    }
}
