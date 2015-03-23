package com.syzton.sunread.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.syzton.sunread.security.SecurityConfiguration;

public class SpringWebMvcInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SecurityConfiguration.class};
	}

	@Override  
    public void onStartup(ServletContext servletContext) throws ServletException {  
        super.onStartup(servletContext);  
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);  
        encodingFilter.setInitParameter("encoding", "UTF-8");  
        encodingFilter.setInitParameter("forceEncoding", "true");  
        encodingFilter.setAsyncSupported(true);  
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");  
    }  
    
    @Override  
    protected Class<?>[] getServletConfigClasses() {  
        return new Class<?>[] { WebAppContext.class };  
    }  
    @Override  
    protected String[] getServletMappings() {  
        return new String[] { "/" };  
    }  

}
