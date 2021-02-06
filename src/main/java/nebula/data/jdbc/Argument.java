package nebula.data.jdbc;

import nebula.tinyasm.MethodCode;

public interface Argument {
	void apply(MethodCode mv);
}
