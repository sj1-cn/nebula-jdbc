package cn.sj1.nebula.data.jdbc;

import static nebula.data.jdbc.PojoNameUtils.getName;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

import com.dbal.jdbc.builders.schema.ColumnDefinition;
import com.dbal.jdbc.builders.schema.JDBC;

public class JdbcRepositoryFactory {

	Connection conn;

	private final MyClassLoader classLoader = new MyClassLoader();
	private final TinyAsmPrimativeTypeConverters primativeTypeConverters = new TinyAsmPrimativeTypeConverters();
	private TinyAsmJdbcRepositoryBuilder repositoryBuilder = new TinyAsmJdbcRepositoryBuilder(primativeTypeConverters);

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
					EntityPojoFieldJdbcMapper mapper = new EntityPojoFieldJdbcMapper(primaryKey, fieldname, getname, fieldClazz, column.autoIncrement());
					fields.push(mapper);
				} else {
					EntityPojoFieldJdbcMapper mapper = new EntityPojoFieldJdbcMapper(primaryKey, fieldname, getname, fieldClazz, column);
					fields.push(mapper);
				}
			}
		}

		EntityPojoDbMappingDefinitions clazzDefinition = new EntityPojoDbMappingDefinitions(name, type.getName(), tablename, fields);
		cachedClazzDefinations.put(type.getName(), clazzDefinition);
		return clazzDefinition;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getEntityImplClass(Class<T> clazzTarget) {
		if (cachedEntityImplClasses.containsKey(clazzTarget.getName())) return (Class<T>) cachedEntityImplClasses.get(clazzTarget.getName());

		EntityPojoDbMappingDefinitions clazzDefinition = build(clazzTarget);

		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getEntityImplClass(EntityPojoDbMappingDefinitions clazzDefinition) {
		if (cachedEntityImplClasses.containsKey(clazzDefinition.entityPojoClassName)) return (Class<T>) cachedEntityImplClasses.get(clazzDefinition.entityPojoClassName);
		Class<T> clazzClazzExtend = makeClazzExtend(clazzDefinition);
		return clazzClazzExtend;
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(Class<T> clazz) {
		if (cachedRepositories.containsKey(clazz.getName())) return (Repository<T>) cachedRepositories.get(clazz.getName());

		EntityPojoDbMappingDefinitions clazzDefinition = build(clazz);

		return makeJdbcRepository(clazzDefinition);
	}

	@SuppressWarnings("unchecked")
	public <T> Repository<T> getRepository(EntityPojoDbMappingDefinitions clazzDefinition) {
		if (cachedRepositories.containsKey(clazzDefinition.entityPojoClassName)) return (Repository<T>) cachedRepositories.get(clazzDefinition.entityPojoClassName);

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

	Map<String, Repository<?>> cachedRepositories = new HashMap<>();

	private <T> Repository<T> makeJdbcRepository(EntityPojoDbMappingDefinitions entityPojoDbMappingDefinitions) {
		String repositoryClassName = entityPojoDbMappingDefinitions.entityPojoClassName + "Repository";
		String entityPojoClassName = entityPojoDbMappingDefinitions.entityPojoClassName;
		String clazzExtendName = getEntityImplClass(entityPojoDbMappingDefinitions).getName();

		byte[] codeRepository = repositoryBuilder.dump(repositoryClassName, entityPojoClassName, clazzExtendName,
				entityPojoDbMappingDefinitions);

		try {
			@SuppressWarnings("unchecked")
			Class<JdbcRepository<T>> clazzJdbcRepository = (Class<JdbcRepository<T>>) classLoader.defineClassByName(repositoryClassName,
					codeRepository);
			JdbcRepository<T> jdbcRepository = clazzJdbcRepository.getConstructor().newInstance();
			jdbcRepository.setConnection(this.conn);
			cachedRepositories.put(entityPojoDbMappingDefinitions.entityPojoClassName, jdbcRepository);
			return jdbcRepository;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
