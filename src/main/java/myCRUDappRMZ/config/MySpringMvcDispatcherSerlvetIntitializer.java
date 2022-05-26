package myCRUDappRMZ.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbmg0d1lUZ1dzNlhCajc3Ukpqdnk4WWYwb21VQXxBQ3Jtc0tralFyTU9MNmN4dUNXcTVjWWJPWS1uVzdibXhZeWVuNU4xNXhrZ0FrbnAzc3dvVFktMDlibFRiTTFsdzNVLXJWQXVuSktHZjZnQjgta2JBcENaaHVvX1BVRDJQS1E1elRjRHRNN0JBU0NaOWNEOUhVcw&q=https%3A%2F%2Fgithub.com%2FNeilAlishev%2FSpringCourse%2Ftree%2Fmaster%2FLesson23.CRUD_App3&v=JaVGIYxE23c

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
		@Override
		protected Class<?>[] getRootConfigClasses() {
			return null;
		}
		
		@Override
		protected Class<?>[] getServletConfigClasses() {
			return new Class[]{SpringConfig.class};
		}
		
		@Override
		protected String[] getServletMappings() {
			return new String[]{"/"};
		}
	
		//create Spring filter ("hiddenHttpMethodFilter") for all requests, it starts onStartup,
		//search hidden fields from spring forms for realization PATCH, DELETE and
		//other unsupported method in HTML5
	@Override
	public void onStartup(ServletContext aServletContext) throws ServletException {
		super.onStartup(aServletContext);
		registerHiddenFieldFilter(aServletContext);
	}
	//filter for search hidden fields in HTTP request (because there is no supporting of PATCH DELETE and other in html5)
	private void registerHiddenFieldFilter(ServletContext aContext) {
		aContext.addFilter("hiddenHttpMethodFilter",
				new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
	}
	}

