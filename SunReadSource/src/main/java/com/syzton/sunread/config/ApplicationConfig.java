package com.syzton.sunread.config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author Petri Kainulainen
 */
public class ApplicationConfig implements WebApplicationInitializer {
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";
    private static final String ENTITYMANAGER_FILTER_NAME = "SpringOpenEntityManagerInViewFilter";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationContext.class);

        //XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        //rootContext.setConfigLocation("classpath:exampleApplicationContext.xml");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", org.springframework.web.filter.DelegatingFilterProxy.class);
        springSecurityFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic encoder = servletContext.addFilter("EncoderFilter",new CharacterEncodingFilter());
        encoder.setInitParameter("encoding","UTF-8");
        encoder.setInitParameter("forceEncoding","true");
        encoder.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, DISPATCHER_SERVLET_NAME);
 
        FilterRegistration.Dynamic filter = servletContext.addFilter(ENTITYMANAGER_FILTER_NAME,new OpenEntityManagerInViewFilter());
        filter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),true, DISPATCHER_SERVLET_NAME);

        
    }

}
