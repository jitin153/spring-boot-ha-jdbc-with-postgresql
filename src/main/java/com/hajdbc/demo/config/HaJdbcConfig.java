package com.hajdbc.demo.config;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hajdbc.demo.enums.BalancerFactoryType;
import com.hajdbc.demo.enums.DatabaseMetaDataCacheFactoryType;
import com.hajdbc.demo.enums.DurabilityFactoryType;
import com.hajdbc.demo.enums.StateManagerFactoryType;
import com.hajdbc.demo.enums.SynchronizationStrategyType;

import net.sf.hajdbc.SimpleDatabaseClusterConfigurationFactory;
import net.sf.hajdbc.dialect.postgresql.PostgreSQLDialectFactory;
import net.sf.hajdbc.sql.Driver;
import net.sf.hajdbc.sql.DriverDatabase;
import net.sf.hajdbc.sql.DriverDatabaseClusterConfiguration;
import net.sf.hajdbc.util.concurrent.cron.CronExpression;

/*
 * 1. http://ha-jdbc.org/doc.html
 * 2. https://github.com/ha-jdbc/ha-jdbc/blob/master/test/src/test/java/net/sf/hajdbc/SmokeTest.java
 * 3. https://dzone.com/articles/using-ha-jdbc-with-spring-boot
 * Check below URL to setup 2nd instance of Postgresql database.
 * 1. https://stackoverflow.com/questions/39842725/postgresql-how-to-create-two-instances-in-same-window-machine
 */
@Configuration
public class HaJdbcConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(HaJdbcConfig.class);
	
	@Value("${app.database1.id}")
	private String database1Id;

	@Value("${app.database1.url}")
	private String database1Url;
	
	@Value("${app.database1.weight:1}")
	private int database1Weight;
	
	@Value("${app.database2.id}")
	private String database2Id;

	@Value("${app.database2.url}")
	private String database2Url;

	@Value("${app.database2.weight:1}")
	private int database2Weight;
	
	@Value("${app.database.username}")
	private String databaseUsername;

	@Value("${app.database.password}")
	private String databasePassword;

	@Value("${app.hajdbc.cluster.name}")
	private String clusterName;

	@Value("${app.hajdbc.auto-activation.cron-expression}")
	private String autoActivationCronExpression;
	
	@Value("${app.hajdbc.failure-detection.cron-expression}")
	private String failureDetectionCronExpression;

	@Value("${app.hajdbc.balancer-factory.id}")
	private String balancerFactoryId;

	@Value("${app.hajdbc.default-sync-strategy.id}")
	private String defaultSynchronizationStrategyId;

	@Value("${app.hajdbc.database-metadata-cache-factory.id}")
	private String databaseMetadataCacheFactoryId;

	@Value("${app.hajdbc.state-manager-factory.id}")
	private String stateManagerFactoryId;

	@Value("${app.hajdbc.durability-factory.id}")
	private String durabilityFactoryId;

	@Value("${app.hajdbc.identity-column-detection-enabled}")
	private boolean isIdentityColumnDetectionEnabled;

	@Value("${app.hajdbc.sequence-detection-enabled}")
	private boolean isSequenceDetectionEnabled;

	/*
	 * Below properties might need in case of configuring persistent(Database based)
	 * state manager factory except SimpleStateManagerFactory because it's non
	 * persitent.
	 */
	/*
	 @Value("${app.hajdbc.state-manager-factory.url}")
	 private String stateManagerUrl;
	 
	 @Value("${app.hajdbc.state-manager-factory.user}")
	 private String stateManagerUser;
	 
	 @Value("${app.hajdbc.state-manager-factory.password}")
	 private String stateManagerPassword;
	 
	 @Value("${app.hajdbc.state-manager-factory.location}")
	 private String stateManagerLocation;
	 */
	@Bean
	@PostConstruct
	public void register() {
		DriverDatabaseClusterConfiguration config = new DriverDatabaseClusterConfiguration();
		try {			
			config.setDatabases(driverDatabases());
			config.setDialectFactory(new PostgreSQLDialectFactory());
			config.setDatabaseMetaDataCacheFactory(
					DatabaseMetaDataCacheFactoryType.byId(databaseMetadataCacheFactoryId));
			config.setStateManagerFactory(StateManagerFactoryType.byId(stateManagerFactoryId));
			// Below configuration to enable Distributed capabilities. It might need more configuration.
			// config.setDispatcherFactory(new JGroupsCommandDispatcherFactory());
			config.setBalancerFactory(BalancerFactoryType.byId(balancerFactoryId));
			config.setSynchronizationStrategyMap(SynchronizationStrategyType.map());
			config.setDefaultSynchronizationStrategy(defaultSynchronizationStrategyId);
			config.setDurabilityFactory(DurabilityFactoryType.byId(durabilityFactoryId));
			config.setIdentityColumnDetectionEnabled(isIdentityColumnDetectionEnabled);
			config.setSequenceDetectionEnabled(isSequenceDetectionEnabled);
			config.setAutoActivationExpression(new CronExpression(autoActivationCronExpression));
			config.setFailureDetectionExpression(new CronExpression(failureDetectionCronExpression));
			//config.setEmptyClusterAllowed(false);
			Driver.setConfigurationFactory(clusterName,
					new SimpleDatabaseClusterConfigurationFactory<java.sql.Driver, DriverDatabase>(config));
		} catch (ParseException e) {
			LOG.error("Error occurred: {}",e.getMessage());
		}
	}

	private List<DriverDatabase> driverDatabases() {
		DriverDatabase db1 = createDriverDatabase(database1Id, database1Url, database1Weight);
		DriverDatabase db2 = createDriverDatabase(database2Id, database2Url, database2Weight);
		return Arrays.asList(db1, db2);
	}

	private DriverDatabase createDriverDatabase(String id, String url, int weight) {
		DriverDatabase driverDatabase = new DriverDatabase();
		driverDatabase.setId(id);
		driverDatabase.setLocation(url);
		driverDatabase.setUser(databaseUsername);
		driverDatabase.setPassword(databasePassword);
		driverDatabase.setWeight(weight);
		return driverDatabase;
	}
}
