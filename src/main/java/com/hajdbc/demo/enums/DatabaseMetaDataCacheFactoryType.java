package com.hajdbc.demo.enums;

import net.sf.hajdbc.cache.DatabaseMetaDataCacheFactory;
import net.sf.hajdbc.cache.eager.EagerDatabaseMetaDataCacheFactory;
import net.sf.hajdbc.cache.eager.SharedEagerDatabaseMetaDataCacheFactory;
import net.sf.hajdbc.cache.lazy.LazyDatabaseMetaDataCacheFactory;
import net.sf.hajdbc.cache.lazy.SharedLazyDatabaseMetaDataCacheFactory;
import net.sf.hajdbc.cache.simple.SimpleDatabaseMetaDataCacheFactory;

public enum DatabaseMetaDataCacheFactoryType {
	SIMPLE(new SimpleDatabaseMetaDataCacheFactory()), LAZY(new LazyDatabaseMetaDataCacheFactory()),
	EAGER(new EagerDatabaseMetaDataCacheFactory()), SHARED_LAZY(new SharedLazyDatabaseMetaDataCacheFactory()),
	SHARED_EAGER(new SharedEagerDatabaseMetaDataCacheFactory());

	private DatabaseMetaDataCacheFactory databaseMetaDataCacheFactory;

	private DatabaseMetaDataCacheFactoryType(DatabaseMetaDataCacheFactory databaseMetaDataCacheFactory) {
		this.databaseMetaDataCacheFactory = databaseMetaDataCacheFactory;
	}

	public static DatabaseMetaDataCacheFactory byId(String id) {
		for (DatabaseMetaDataCacheFactoryType type : values()) {
			if (type.databaseMetaDataCacheFactory.getId().equals(id)) {
				return type.databaseMetaDataCacheFactory;
			}
		}
		return null;
	}
}
