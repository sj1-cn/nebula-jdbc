# nebula-jdbc

处理JDBC相关功能。根据映射结构创建、更新数据库结构。初步设想可以根据 pojo对象（已实现）、nebula定义等。也可以直接以数据库结构生成pojo对象结构（未实现）

生成JdbcDatabaseMetadata获取当前数据库信息。只生成了获取信息部分。还没有实现利用这些信息构建SQL等功能。

# TODOS

优化SQL生成结构，以便根据数据库的不同生成不同的SQL文，以兼容不同的数据库。

当前给了Pojo对象直接使用，需要改成扩展当前pojo对象的子对象。

简化repositorybuild代码，以便后期处理较复杂结构。

