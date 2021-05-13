package nebula.data.jdbc;

import static nebula.data.jdbc.PojoNameUtils.getName;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

import nebula.jdbc.builders.schema.ColumnDefinition;
import nebula.jdbc.builders.schema.JDBC;

public class JdbcRepositoryFactory {

	Connection conn;

	private final PrimativeTypeConverters primativeTypeConverters = new PrimativeTypeConverters();
	private final MyClassLoader classLoader = new MyClassLoader();

	Map<String, EntityPojoDbMappingDefinitions> cachedClazzDefinations = new HashMap<>();
	
	public JdbcRepositoryFactory(Connection conn) {
		this.conn = conn;
	}


	public EntityPojoDbMappingDefinitions build(Class<?> type) {
		if (cachedClazzDefinations.containsKey(type.getName())) return cachedClazzDefinations.get(type.getName());

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

		EntityPojoDbMappingDefinitions clazzDefinition = new EntityPojoDbMappingDefinitions(name, type.getName(), tablename, fields);
		cachedClazzDefinations.put(type.getName(), clazzDefinition);
		return clazzDefinition;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getClazzExtend(Class<T> clazzTarget) {
		if (cachedEntityImplClasses.containsKey(clazzTarget.getName())) return (Class<T>) cachedEntityImplClasses.get(clazzTarget.getName());

		EntityPojoDbMappingDefinitions clazzDefinition = build(clazzTarget);

		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> actualClazz(EntityPojoDbMappingDefinitions clazzDefinition) {
		if (cachedEntityImplClasses.containsKey(clazzDefinition.entityPojoClassName)) return (Class<T>) cachedEntityImplClasses.get(clazzDefinition.entityPojoClassName);
		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(Class<T> clazz) {
		if (repositories.containsKey(clazz.getName())) return (Repository<T>) repositories.get(clazz.getName());

		EntityPojoDbMappingDefinitions clazzDefinition = build(clazz);

		return makeJdbcRepository(clazzDefinition);
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(EntityPojoDbMappingDefinitions clazzDefinition) {
		if (repositories.containsKey(clazzDefinition.entityPojoClassName)) return (Repository<T>) repositories.get(clazzDefinition.entityPojoClassName);

		return makeJdbcRepository(clazzDefinition);
	}

	private EntityImplBuilder entityImplBuilder = new EntityImplBuilder();
	Map<String, Class<?>> cachedEntityImplClasses = new HashMap<>();

	@SuppressWarnings("unchecked")
	private <T> Class<T> makeClazzExtend(EntityPojoDbMappingDefinitions clazzDefinition) {
		String entityPojoClassName = clazzDefinition.getEntityPojoClassName();
		String entityPojoImplClassName = entityPojoClassName + "Extend";

		byte[] codeImpl = entityImplBuilder.make(entityPojoImplClassName, entityPojoClassName, clazzDefinition);

		Class<T> clazzClazzExtend = (Class<T>) classLoader.defineClassByName(entityPojoImplClassName, codeImpl);
		cachedEntityImplClasses.put(entityPojoClassName, clazzClazzExtend);
		return clazzClazzExtend;
	}

	private JdbcRepositoryBuilder repositoryBuilder = new JdbcRepositoryBuilder(primativeTypeConverters);
	Map<String, Repository<?>> repositories = new HashMap<>();

	private <T> Repository<T> makeJdbcRepository(EntityPojoDbMappingDefinitions clazzDefinition) {
		String clazzRepositoryName = clazzDefinition.entityPojoClassName + "Repository";
		String clazzTargetName = clazzDefinition.entityPojoClassName;
		String clazzExtendName = actualClazz(clazzDefinition).getName();

		byte[] codeRepository = repositoryBuilder.dump(clazzRepositoryName, clazzTargetName, clazzExtendName,
				clazzDefinition);

		try {
			@SuppressWarnings("unchecked")
			Class<JdbcRepository<T>> clazzJdbcRepository = (Class<JdbcRepository<T>>) classLoader.defineClassByName(clazzRepositoryName,
					codeRepository);
			JdbcRepository<T> jdbcRepository = clazzJdbcRepository.getConstructor().newInstance();
			jdbcRepository.setConnection(this.conn);
			repositories.put(clazzDefinition.entityPojoClassName, jdbcRepository);
			return jdbcRepository;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
