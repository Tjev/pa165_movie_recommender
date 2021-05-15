package cz.muni.fi.pa165;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Replaces web.xml file.
 * Extends the class {@link AbstractAnnotationConfigDispatcherServletInitializer} that
 * <ul>
 * <li>creates spring context specified in the class returned by {@link #getRootConfigClasses()}</li>
 * <li>initializes {@link org.springframework.web.servlet.DispatcherServlet Spring MVC dispatcher servlet} with it</li>
 * <li>maps dispatcher servlet to URL pattern returned by {@link #getServletMappings()}</li>
 * </ul>
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RestMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("utf-8",true);
        ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();

        return new Filter[]{encodingFilter, shallowEtagHeaderFilter};
    }

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws javax.servlet.ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(RequestContextListener.class);
    }

}
