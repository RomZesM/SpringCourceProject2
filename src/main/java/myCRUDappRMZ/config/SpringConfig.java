package myCRUDappRMZ.config;

import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.ApplicationContext;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.ComponentScan;
		import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
		import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
		import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
		import org.thymeleaf.spring5.SpringTemplateEngine;
		import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
		import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;

@Configuration
@ComponentScan("myCRUDappRMZ")
@EnableWebMvc
//add a file with data for BD connection
@PropertySource("classpath:db.properties")
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
		//take all params for db connection from db.properties
		dataSource.setDriverClassName(environment.getProperty("driver"));
		dataSource.setUrl(environment.getProperty("url"));
		dataSource.setUsername(environment.getProperty("username"));
		dataSource.setPassword(environment.getProperty("password"));
		
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
	@Bean
	public JdbcTemplate jdbcTemplate(){
		//spring inject a dataSource bean here with data for connection to the DB
		return new JdbcTemplate(dataSource());
	}
}