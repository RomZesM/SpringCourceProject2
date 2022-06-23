package myCRUDappRMZ.config;

import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.ApplicationContext;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.ComponentScan;
		import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
		import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
		import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
		import org.thymeleaf.spring5.SpringTemplateEngine;
		import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
		import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("myCRUDappRMZ")
@EnableWebMvc
//add a file with data for BD connection
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement //spring would start and stop transaction
@EnableJpaRepositories("myCRUDappRMZ.repositories") //path for hibernate config file
public class SpringConfig implements WebMvcConfigurer {
	
	private final ApplicationContext applicationContext;
	private final Environment environment;//Environment is Spring class, so bean can be autowired
	
	@Autowired
	public SpringConfig(ApplicationContext applicationContext, Environment environment) {
		this.applicationContext = applicationContext;
		this.environment = environment;
		
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		registry.viewResolver(resolver);
	}
	//using JDBC-Template
	//1. create Bean -> dataSource (info for our JDBC template, with DB param (pass, user, name etc)
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		//take all params for db connection from hibernate.properties
		dataSource.setDriverClassName(environment.getRequiredProperty("hibernate.driver_class"));
		dataSource.setUrl(environment.getRequiredProperty("hibernate.connection.url"));
		dataSource.setUsername(environment.getRequiredProperty("hibernate.connection.username"));
		dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));
		
		/**
		 * better if we do that from external file
		 * //driver for current DB
		 * 		dataSource.setDriverClassName("org.postgresql.Driver");
		 * 		//url, user and password to create connection object (spring do that for us)
		 * 		dataSource.setUrl("jdbc:postgresql://localhost:5432/first_db");
		 * 		dataSource.setUsername("postgres");
		 * 		dataSource.setPassword("12345678");
		 * */
		
		return dataSource;
	}
//	@Bean
//	public JdbcTemplate jdbcTemplate(){
//		//spring inject a dataSource bean here with data for connection to the DB
//		return new JdbcTemplate(dataSource());
//	}
	//using hibernate, instead of jdbcTemplate
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		
		return properties;
	}
	//pure hibernate sessionafactory
//	@Bean
//	public LocalSessionFactoryBean sessionFactory(){
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource()); //information about DB connection
//		sessionFactory.setPackagesToScan("myCRUDappRMZ.model"); //folder with models
//		sessionFactory.setHibernateProperties(hibernateProperties());
//
//		return sessionFactory;
//	}
//	@Bean
//	public PlatformTransactionManager hibernateTransactionManager(){
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//		transactionManager.setSessionFactory(sessionFactory().getObject()); //get sessoin object from here
//
//		return transactionManager;
//	}
	
	
	//if we use Spring DATA JPA:
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("myCRUDappRMZ.model");
		
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());
		
		return em;
	}
	
	//jpa transactionManager
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
	
	
}