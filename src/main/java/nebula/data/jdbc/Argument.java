package nebula.data.jdbc;

import nebula.tinyasm.data.MethodCode;

public interface Argument {
	void apply(MethodCode mv);
}
