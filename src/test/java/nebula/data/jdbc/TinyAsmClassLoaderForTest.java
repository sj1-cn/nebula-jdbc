package nebula.data.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TinyAsmClassLoaderForTest extends ClassLoader {
	static Logger log = LoggerFactory.getLogger(TinyAsmClassLoaderForTest.class);

	public TinyAsmClassLoaderForTest() {
		super(TinyAsmClassLoaderForTest.class.getClassLoader());
	}

	static TinyAsmClassLoaderForTest instance;

	public static final Class<?> defineClass(String name, byte[] b) {
		if (log.isTraceEnabled()) {
			log.trace("defineClass [ " + name + " ]");
		}
		if (instance == null) instance = new TinyAsmClassLoaderForTest();
		return instance.defineClass(name, b, 0, b.length);
	}

	static ClassLoader getInstance() {
		if (instance == null) instance = new TinyAsmClassLoaderForTest();
		return instance;
	}

	public static void doResolveClass(Class<?> clzBroker) {
		if (instance == null) instance = new TinyAsmClassLoaderForTest();
		instance.resolveClass(clzBroker);
	}

	public final Class<?> doDefineClass(String name, byte[] b) {
		return super.defineClass(name, b, 0, b.length);
	}
}
