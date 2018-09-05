package nebula.data.jdbc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.JDBCType;

import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.JDBC;

public class RepositoryFactory {
	Connection conn;
	Arguments arguments = new Arguments();

	public RepositoryFactory(Connection conn) {
		this.conn = conn;
	}

	class MyClassLoader extends ClassLoader {
		public Class<?> defineClassByName(String name, byte[] b, int off, int len) {
			{
				File root = new File("target/generated-sources");
				if (!root.exists()) root.mkdirs();

				File file = new File(root, name.replace(".", "/") + ".class");
				if (!file.getParentFile().exists()) {
					makesureFolderExists(file.getParentFile());
				}

				try {
					FileOutputStream o = new FileOutputStream(file);
					o.write(b);
					o.close();
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			Class<?> clazz = super.defineClass(name, b, 0, b.length);
			return clazz;
		}

		public Class<?> defineClassByName(String name, byte[] b) {
			return defineClassByName(name, b, 0, b.length);
		}
	}

	private void makesureFolderExists(File file) {
		if (!file.getParentFile().exists()) makesureFolderExists(file.getParentFile());
		file.mkdir();
	}

	private MyClassLoader myClassLoader = new MyClassLoader();

	public <T> Repository<T> getRepository(Class<T> type) {
		FieldList mappers = build(type);

		getMapper(type);

		String clazzRepository = type.getName() + "Repository";
		String clazzTarget = type.getName();
		String clazzRowMapper = type.getName() + "RowMapper";
		String clazzExtend = type.getName() + "Extend";
		ClazzDefinition clazzDefinition = new ClazzDefinition(type.getSimpleName(), type.getSimpleName(), mappers);

		byte[] codeRepository = repositoryBuilder.make(clazzRepository, clazzTarget, clazzExtend, clazzRowMapper, clazzDefinition);

		try {
			@SuppressWarnings("unchecked")
			Class<JdbcRepository<T>> clazzJdbcRepository = (Class<JdbcRepository<T>>) myClassLoader.defineClassByName(clazzRepository, codeRepository);
			JdbcRepository<T> jdbcRepository = clazzJdbcRepository.newInstance();
			jdbcRepository.setConnection(this.conn);
			return jdbcRepository;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public FieldList build(Class<?> type) {
		FieldList mappers = new FieldList();

		for (Field field : type.getDeclaredFields()) {
			int modifier = field.getModifiers();
			if (!Modifier.isStatic(modifier) && !Modifier.isSynchronized(modifier) && !Modifier.isAbstract(modifier)
					&& !Modifier.isNative(modifier)) {
				String fieldname = field.getName();
				Class<?> fieldClazz = field.getType();
				JDBCType jdbctype = JDBC.jdbcType(fieldClazz);
				assert jdbctype != null : field.getName() + "'s class " + fieldClazz.getName() + " hasn't exist!";
				ColumnDefinition column = ColumnDefinition.Column(jdbctype, fieldname);
				String getname = getGetName(field.getName(), field.getType());
				boolean primaryKey = "id".equals(fieldname);
				if (primaryKey) {
					FieldMapper mapper = new FieldMapper(primaryKey, fieldname, getname, fieldClazz, column.autoIncrement());
					mappers.push(mapper);
				} else {
					FieldMapper mapper = new FieldMapper(primaryKey, fieldname, getname, fieldClazz, column);
					mappers.push(mapper);
				}
			}
		}
		return mappers;
	}

	public String getGetName(String fieldname, Class<?> clazz) {
		if (clazz == boolean.class) {
			return "is" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
		} else {
			return "get" + Character.toUpperCase(fieldname.charAt(0)) + fieldname.substring(1);
		}
	}

	public JdbcRowMapperBuilder rowMapperBuilder = new JdbcRowMapperBuilder(arguments);
	public JdbcRepositoryBuilder repositoryBuilder = new JdbcRepositoryBuilder(arguments);

	public <T> JdbcRowMapper<T> getMapper(Class<T> type) {
		FieldList mappers = build(type);

		String clazz = type.getName() + "RowMapper";
		String targetClazz = type.getName();

		byte[] code = rowMapperBuilder.make(clazz, targetClazz, mappers);

		try {
			@SuppressWarnings("unchecked")
			Class<JdbcRowMapper<T>> rowMapperClazz = (Class<JdbcRowMapper<T>>) myClassLoader.defineClassByName(clazz, code);
			JdbcRowMapper<T> rowMapper = rowMapperClazz.newInstance();
			return rowMapper;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
