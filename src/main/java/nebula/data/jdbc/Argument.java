package nebula.data.jdbc;

import cc1sj.tinyasm.MethodCode;

public interface Argument {
	void apply(MethodCode mv);
}
