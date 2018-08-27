package nebula.jdbc;

import static nebula.tinyasm.util.RefineCode.excludeLineNumber;
import static nebula.tinyasm.util.RefineCode.skipToString;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestBase {

	public DataSource dataSource() throws SQLException {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
		config.setConnectionTestQuery("VALUES 1");
		config.addDataSourceProperty("URL",
				"jdbc:h2:~/testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
		config.addDataSourceProperty("user", "sa");
		config.addDataSourceProperty("password", "sa");
		HikariDataSource ds = new HikariDataSource(config);

		HikariDataSource dataSource = new HikariDataSource(config);

		return dataSource;
	}

	public Connection getConnection() {
		try {
			HikariConfig config = new HikariConfig();
			config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
			config.setConnectionTestQuery("VALUES 1");
			config.addDataSourceProperty("URL", // jdbc:h2:mem:test
					"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
			config.addDataSourceProperty("user", "sa");
			config.addDataSourceProperty("password", "sa");
			HikariDataSource ds = new HikariDataSource(config);

			HikariDataSource dataSource = new HikariDataSource(config);

			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection(String name) {
		try {
			HikariConfig config = new HikariConfig();
			config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
			config.setConnectionTestQuery("VALUES 1");
			config.addDataSourceProperty("URL", // jdbc:h2:mem:test
					"jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
			config.addDataSourceProperty("user", "sa");
			config.addDataSourceProperty("password", "sa");
			HikariDataSource ds = new HikariDataSource(config);

			HikariDataSource dataSource = new HikariDataSource(config);

			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MyClassLoader classLoader = new MyClassLoader();

	protected class MyClassLoader extends ClassLoader {
		public Class<?> defineClassByName(String name, byte[] b, int off, int len) {
			Class<?> clazz = super.defineClass(name, b, off, len);
			return clazz;
		}

		public Class<?> defineClassByName(String name, byte[] b) {
			Class<?> clazz = super.defineClass(name, b, 0, b.length);
			return clazz;
		}
	}

	public static String toString(byte[] code) {
		try {
			ClassReader cr = new ClassReader(code);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Class<?> clazz) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(String className) {
		try {
			ClassReader cr = new ClassReader(className);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ClassVisitor visitor = new TraceClassVisitor(null, new ASMifier(), pw);
			cr.accept(visitor, ClassReader.EXPAND_FRAMES);
			return skipToString(excludeLineNumber(sw.toString()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
//
//	public static String refineCode(String input) {
//
//		// match by line
//		input = input.replaceAll("Label (l\\d*) = new Label\\(\\);\\s*\\n", "Label $1 = mv.codeNewLabel();");
//		input = input.replaceAll("mv.visitLabel\\((l\\d*)\\);", "mv.codeAccessLabel\\($1\\);");
//		input = input.replaceAll("mv.visitLineNumber\\(\\d, \\w\\d\\);\\s*\\n", "");
//
//		input = input.replaceAll("mv.visitInsn\\(LCMP\\);", "mv.CMP();");
//		input = input.replaceAll("mv.visitInsn\\((?:D|F)CMPG\\);", "mv.CMPG();");
//		input = input.replaceAll("mv.visitInsn\\((?:D|F)CMPL\\);", "mv.CMPL();");
//
//		input = input.replaceAll("mv.visitInsn\\(\\wRETURN\\);", "mv.RETURNTop();");
//		input = input.replaceAll("mv.visitVarInsn\\(\\wLOAD, (\\d*)\\);", "mv.LOAD($1);");
//		input = input.replaceAll("mv.visitInsn\\(\\wALOAD\\);", "mv.ARRAYLOAD();");
//		input = input.replaceAll("mv.visitInsn\\(\\wASTORE\\);", "mv.ARRAYSTORE();");
//		input = input.replaceAll("mv.visitFieldInsn\\(GETFIELD, \"[^\"]*\", (\"[^\"]*\")[^\\n]*;\\n",
//				"mv.GETFIELD($1);");
//		input = input.replaceAll("mv.visitFieldInsn\\(PUTFIELD, \"[^\"]*\", (\"[^\"]*\")[^\\n]*;\\n",
//				"mv.PUTFIELD($1);");
//
//		input = input.replaceAll("mv.visitLdcInsn(\\([^;]*)[^\\n]*;\\n", "mv.LOADConst$1;");
//
//		input = input.replaceAll("mv.visitJumpInsn\\((\\w+), (l\\d+)\\);", "mv.$1($2);");
//
//		input = input.replaceAll("mv.visitLocalVariable\\(\"([\\d\\w]*)\", \"(\\w+)\"[^;]+;",
//				"<def>mv.define(\"$1\",\"$2\"\\);</def>");
//		input = input.replaceAll("mv.visitLocalVariable\\(\\\"this\\\"[^\\n]*\\n", "");
//		input = input.replaceAll("mv.visitFrame[^;]*;\\s*\\n", "");
//		input = input.replaceAll("mv.visitMaxs[^;]*;\\s*\\n", "");
//		input = input.replaceAll("mv.visitInsn\\(ICONST_(\\d)\\);", "mv.LOADConst($1);");
//
//		// match by method
//		input = input.replaceAll("\\n", "");
//		input = input.replaceAll("\\{(mv = cw.visitMethod\\()", "\n\n<method>$1");
//		input = input.replaceAll("(mv.visitCode\\(\\);)", "<code>");
//		input = input.replaceAll("(mv.visitEnd\\(\\);})", "</code></method>\n\n");
//
//		input = input.replaceAll(";(<def>.*)(?:;</def></code>)", ";<defs>$1;</def></defs></code>");
//		input = input.replaceAll("#def#", ";");
//
//		input = input.replaceAll("(<code>)(.*)(<defs>.*</defs>)", "$1$3$2");
////			
//		input = input.replaceAll("</?def>", "");
//		input = input.replaceAll("<defs>(.*)</defs>", "$1");
//
//		input = input.replaceAll("mv = cw.visitMethod\\(([^\\n]*);\\n", "cw.method($1");
//		input = input.replaceAll("<method>(.*)</method>", "$1");
//		input = input.replaceAll("<code>(.*)</code>", ".code(mv -> {\n\t$1});");
//
////		mv.visitFieldInsn(GETFIELD, "nebula/tinyasm/MethodASMArraySample", "ia", "[I");
//
//		// reformater by line
//		input = input.replaceAll("\\);", "\\);\n\t");
//
//		input = input.replaceAll("mv = cw.visitMethod\\(ACC_PUBLIC, (\"[^\"]*\"), \"\\(([^\\)]*)\\)([^\"]*)\",[^;]*;",
//				"\tcw.method(/*$3*/, $1,/*$2*/)");
//
//		input = input.replaceAll("(?:\\})?\\{fv = cw.visitField\\(ACC_PRIVATE, \"([^\"]*)\", \"([^\"]*)\",[^;]*;",
//				"cw.field(\"$1\", \"$2\");");
//		input = input.replaceAll("fv.visitEnd\\(\\);", "");
//		return input;
//	}
//
//	public static String excludeLineNumber(String input) {
//		input = input.replaceAll("LineNumber\\([0-9]*\\,", "LineNumber(1,");
//		return input;
//	}
//
//	public static String skipToString(String input) {
//		input = input.replaceAll("\\n", "<br/>");
//		input = input.replaceAll("(\\{<br/>mv = cw.visitMethod\\()", "\n<method>$1");
//		input = input.replaceAll("(mv.visitEnd\\(\\);<br/>})", "$1</method>\n");
//
//		input = input.replaceAll("(<method>\\{<br/>mv = cw.visitMethod\\(ACC_PUBLIC, \"toString\",[^\\n]*)", "");
//
//		input = input.replaceAll("\\n<method>", "");
//		input = input.replaceAll("</method>\\n", "");
//		input = input.replaceAll("<br/>", "\n");
//		return input;
//	}

	public TestBase() {
		super();
	}

}