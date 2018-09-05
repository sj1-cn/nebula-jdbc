package nebula.data.jdbc;

import static nebula.data.jdbc.PojoUtil.getName;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.JDBC;

public class RepositoryFactory {

	Connection conn;

	Arguments arguments = new Arguments();

	private MyClassLoader classLoader = new MyClassLoader();

	public RepositoryFactory(Connection conn) {
		this.conn = conn;
	}

	Map<String, ClazzDefinition> clazzDefinations = new HashMap<>();

	public ClazzDefinition build(Class<?> type) {
		if (clazzDefinations.containsKey(type.getName())) return clazzDefinations.get(type.getName());

		String name = type.getSimpleName();
		String tablename = name.toUpperCase();

		FieldList fields = new FieldList();
		for (Field field : type.getDeclaredFields()) {
			int modifier = field.getModifiers();
			if (!Modifier.isStatic(modifier) && !Modifier.isSynchronized(modifier) && !Modifier.isAbstract(modifier)
					&& !Modifier.isNative(modifier)) {
				String fieldname = field.getName();
				Class<?> fieldClazz = field.getType();
				JDBCType jdbctype = JDBC.jdbcType(fieldClazz);
				assert jdbctype != null : field.getName() + "'s class " + fieldClazz.getName() + " hasn't exist!";
				ColumnDefinition column = ColumnDefinition.Column(jdbctype, fieldname);
				String getname = getName(field.getName(), field.getType());
				boolean primaryKey = "id".equals(fieldname);
				if (primaryKey) {
					FieldMapper mapper = new FieldMapper(primaryKey, fieldname, getname, fieldClazz, column.autoIncrement());
					fields.push(mapper);
				} else {
					FieldMapper mapper = new FieldMapper(primaryKey, fieldname, getname, fieldClazz, column);
					fields.push(mapper);
				}
			}
		}

		ClazzDefinition clazzDefinition = new ClazzDefinition(name, type.getName(), tablename, fields);
		clazzDefinations.put(type.getName(), clazzDefinition);
		return clazzDefinition;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getClazzExtend(Class<T> clazzTarget) {
		if (clazzExtends.containsKey(clazzTarget.getName())) return (Class<T>) clazzExtends.get(clazzTarget.getName());

		ClazzDefinition clazzDefinition = build(clazzTarget);

		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getClazzExtend(ClazzDefinition clazzDefinition) {
		if (clazzExtends.containsKey(clazzDefinition.clazz)) return (Class<T>) clazzExtends.get(clazzDefinition.clazz);
		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(Class<T> clazz) {
		if (repositories.containsKey(clazz.getName())) return (Repository<T>) repositories.get(clazz.getName());

		ClazzDefinition clazzDefinition = build(clazz);

		return makeJdbcRepository(clazzDefinition);
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(ClazzDefinition clazzDefinition) {
		if (repositories.containsKey(clazzDefinition.clazz)) return (Repository<T>) repositories.get(clazzDefinition.clazz);

		return makeJdbcRepository(clazzDefinition);
	}

	private ClazzExtendBuilder clazzExtendBuilder = new ClazzExtendBuilder();
	Map<String, Class<?>> clazzExtends = new HashMap<>();

	@SuppressWarnings("unchecked")
	private <T> Class<T> makeClazzExtend(ClazzDefinition clazzDefinition) {
		String clazzTargetName = clazzDefinition.getClazz();
		String clazzExtendName = clazzTargetName + "Extend";

		byte[] codeClazzExtend = clazzExtendBuilder.make(clazzExtendName, clazzTargetName, clazzDefinition);

		Class<T> clazzClazzExtend = (Class<T>) classLoader.defineClassByName(clazzExtendName, codeClazzExtend);
		clazzExtends.put(clazzTargetName, clazzClazzExtend);
		return clazzClazzExtend;
	}

	private JdbcRepositoryBuilder repositoryBuilder = new JdbcRepositoryBuilder(arguments);
	Map<String, Repository<?>> repositories = new HashMap<>();

	private <T> Repository<T> makeJdbcRepository(ClazzDefinition clazzDefinition) {
		String clazzRepositoryName = clazzDefinition.clazz + "Repository";
		String clazzTargetName = clazzDefinition.clazz;
		String clazzExtendName = getClazzExtend(clazzDefinition).getName();
		String clazzRowMapperName = makeJdbcRowMapper(clazzDefinition).getName();

		byte[] codeRepository = repositoryBuilder.make(clazzRepositoryName, clazzTargetName, clazzExtendName, clazzRowMapperName,
				clazzDefinition);

		try {
			@SuppressWarnings("unchecked")
			Class<JdbcRepository<T>> clazzJdbcRepository = (Class<JdbcRepository<T>>) classLoader.defineClassByName(clazzRepositoryName,
					codeRepository);
			JdbcRepository<T> jdbcRepository = clazzJdbcRepository.newInstance();
			jdbcRepository.setConnection(this.conn);
			repositories.put(clazzDefinition.clazz, jdbcRepository);
			return jdbcRepository;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JdbcRowMapperBuilder rowMapperBuilder = new JdbcRowMapperBuilder(arguments);
	Map<ClazzDefinition, Class<?>> rowMappers = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> Class<JdbcRowMapper<T>> makeJdbcRowMapper(ClazzDefinition clazzDefinition) {
		if (rowMappers.containsKey(clazzDefinition)) return (Class<JdbcRowMapper<T>>) rowMappers.get(clazzDefinition);

		String clazzExtend = clazzDefinition.clazz + "Extend";
		String clazzRowMapper = clazzExtend + "RowMapper";

		byte[] code = rowMapperBuilder.make(clazzRowMapper, clazzExtend, clazzDefinition.fieldsAll);

		Class<JdbcRowMapper<T>> rowMapperClazz = (Class<JdbcRowMapper<T>>) classLoader.defineClassByName(clazzRowMapper, code);
		rowMappers.put(clazzDefinition, rowMapperClazz);
		return rowMapperClazz;
	}

}
