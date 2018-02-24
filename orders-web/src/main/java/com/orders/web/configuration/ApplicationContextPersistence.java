package com.orders.web.configuration;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@Profile("init-persistence-mysql")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.orders.persistence.model.mysql.repository",
    entityManagerFactoryRef = "mySqlEmf")
@PropertySource(value = "classpath:/config/persistence.properties", ignoreResourceNotFound = false)
public class ApplicationContextPersistence {

  @Value("${driverClass}")
  String driverClass;
  @Value("${jdbcUrl}")
  String jdbcUrl;
  @Value("${jdbc.user}")
  String user;
  @Value("${jdbc.password}")
  String password;
  @Value("${initialPoolSize}")
  int initialPoolSize;
  @Value("${minPoolSize}")
  int minPoolSize;
  @Value("${maxPoolSize}")
  int maxPoolSize;
  @Value("${acquireIncrement}")
  int acquireIncrement;
  @Value("${maxStatements}")
  int maxStatements;
  @Value("${acquireRetryAttempts}")
  int acquireRetryAttempts;
  @Value("${acquireRetryDelay}")
  int acquireRetryDelay;
  @Value("${breakAfterAcquireFailure}")
  boolean breakAfterAcquireFailure;
  @Value("${maxIdleTime}")
  int maxIdleTime;
  @Value("${maxConnectionAge}")
  int maxConnectionAge;
  @Value("${checkoutTimeout}")
  int checkoutTimeout;
  @Value("${idleConnectionTestPeriod}")
  int idleConnectionTestPeriod;
  @Value("${testConnectionOnCheckout}")
  boolean testConnectionOnCheckout;
  @Value("${testConnectionOnCheckin}")
  boolean testConnectionOnCheckin;
  @Value("${preferredTestQuery}")
  String preferredTestQuery;
  @Value("${hibernate.dialect.mysql}")
  String hibernateDialect;
  @Value("${hibernate.show_sql}")
  String hibernateShowSql;
  @Value("${hibernate.hbm2ddl.auto}")
  String hbm2ddlAauto;
  @Value("${hibernate.hbm2ddl.import_files}")
  String hbm2ddlImportFiles;

  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean(name = "mySqlEmf")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
      throws PropertyVetoException, SQLException {
    final LocalContainerEntityManagerFactoryBean entityManager =
        new LocalContainerEntityManagerFactoryBean();
    entityManager.setDataSource(comboPooledDataSource());
    entityManager.setPackagesToScan("com.orders.persistence.model.mysql.entity");
    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManager.setJpaVendorAdapter(vendorAdapter);
    final Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", hibernateDialect);
    properties.setProperty("hibernate.show_sql", hibernateShowSql);
    properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAauto);
    properties.setProperty("hibernate.hbm2ddl.import_files", hbm2ddlImportFiles);
    entityManager.setJpaProperties(properties);
    return entityManager;
  }

  @Bean
  public PlatformTransactionManager transactionManager()
      throws PropertyVetoException, SQLException {
    final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return jpaTransactionManager;
  }

  /**
   * Init c3p0 Datasource
   * 
   * @throws PropertyVetoException
   * @throws SQLException
   */
  @Bean(name = "mySqlDataSource", destroyMethod = "close")
  public ComboPooledDataSource comboPooledDataSource() {
    final ComboPooledDataSource poolDataSource = new ComboPooledDataSource();
    try {
      poolDataSource.setDriverClass(driverClass);
      poolDataSource.setJdbcUrl(jdbcUrl);
      poolDataSource.setUser(user);
      poolDataSource.setPassword(password);
      poolDataSource.setInitialPoolSize(initialPoolSize);
      poolDataSource.setMinPoolSize(minPoolSize);
      poolDataSource.setMaxPoolSize(maxPoolSize);
      poolDataSource.setAcquireIncrement(acquireIncrement);
      poolDataSource.setMaxStatements(maxStatements);
      poolDataSource.setAcquireRetryAttempts(acquireRetryAttempts);
      poolDataSource.setAcquireRetryDelay(acquireRetryDelay);
      poolDataSource.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
      poolDataSource.setMaxIdleTime(maxIdleTime);
      poolDataSource.setMaxConnectionAge(maxConnectionAge);
      poolDataSource.setCheckoutTimeout(checkoutTimeout);
      poolDataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
      poolDataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
      poolDataSource.setPreferredTestQuery(preferredTestQuery);
      poolDataSource.setTestConnectionOnCheckin(testConnectionOnCheckin);
      poolDataSource.setPreferredTestQuery(preferredTestQuery);
      return poolDataSource;
    } catch (PropertyVetoException e) {
      throw new RuntimeException(e);
    }
  }

}
