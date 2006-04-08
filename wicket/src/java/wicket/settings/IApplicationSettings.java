package wicket.settings;

import java.util.Locale;

import wicket.Application;
import wicket.application.IClassResolver;
import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebRequest;
import wicket.util.convert.IConverterFactory;

/**
 * Settings interface for application settings.
 * <p>
 * <i>internalErrorPage </i>- You can override this with your own page class to
 * display internal errors in a different way.
 * <p>
 * <i>pageExpiredErrorPage </i>- You can override this with your own
 * bookmarkable page class to display expired page errors in a different way.
 * You can set property homePageRenderStrategy to choose from different ways the
 * home page url shows up in your browser.
 * <p>
 * <b>A Converter Factory </b>- By overriding getConverterFactory(), you can
 * provide your own factory which creates locale sensitive Converter instances.
 * 
 * @author Jonathan Locke
 */
public interface IApplicationSettings
{
	/**
	 * Gets the access denied page class.
	 * 
	 * @return Returns the accessDeniedPage.
	 * @see IApplicationSettings#setAccessDeniedPage(Class)
	 */
	Class getAccessDeniedPage();

	/**
	 * Gets the default resolver to use when finding classes
	 * 
	 * @return Default class resolver
	 */
	IClassResolver getClassResolver();

	/**
	 * Gets context path to use for absolute path generation.
	 * For example an Application Server that is used as a 
	 * virtual server on a Webserver:
	 *  
	 * <pre>
	 *    appserver.com/context mapped to webserver/ (context path should be '/')
	 * </pre>
	 * 
	 * @return The context path
	 * 
	 * @see IApplicationSettings#setContextPath(String) what the possible values can be.
	 */
	String getContextPath();

	/**
	 * Returns the servlet context key where the application object is stored under.
	 * 
	 * @return contextKey
	 */
	String getServletContextKey();

	/**
	 * Gets the converter factory.
	 * 
	 * @return the converter factory
	 */
	IConverterFactory getConverterFactory();

	/**
	 * @return Returns the defaultLocale.
	 */
	Locale getDefaultLocale();

	/**
	 * Gets internal error page class.
	 * 
	 * @return Returns the internalErrorPage.
	 * @see IApplicationSettings#setInternalErrorPage(Class)
	 */
	Class getInternalErrorPage();

	/**
	 * Gets the page expired page class.
	 * 
	 * @return Returns the pageExpiredErrorPage.
	 * @see IApplicationSettings#setPageExpiredErrorPage(Class)
	 */
	Class getPageExpiredErrorPage();

	/**
	 * Sets the access denied page class. The class must be bookmarkable and
	 * must extend Page.
	 * 
	 * @param accessDeniedPage
	 *            The accessDeniedPage to set.
	 */
	void setAccessDeniedPage(final Class accessDeniedPage);

	/**
	 * Sets the default class resolver to use when finding classes.
	 * 
	 * @param defaultClassResolver
	 *            The default class resolver
	 */
	void setClassResolver(final IClassResolver defaultClassResolver);

	/**
	 * Sets context path to use for absolute path generation.
	 * For example an Application Server that is used as a 
	 * virtual server on a Webserver:
	 *  
	 * <pre>
	 *    appserver.com/context mapped to webserver/ (context path should be '/')
	 * </pre>
	 * 
	 * This method can be called in the init phase of the application 
	 * with the servlet init parameter {@link Application#CONTEXTPATH} if it is specified
	 * or by the developer itself in the {@link WebApplication} init() method.
	 * If it is not set in the init phase of the application it will be 
	 * set automatically on the context path of the request {@link WebRequest#getContextPath()}
	 * 
	 * @param contextPath The context path to use. 
	 */
	void setContextPath(String contextPath);

	/**
	 * Sets converter factory
	 * 
	 * @param factory
	 *            new factory
	 */
	void setConverterFactory(IConverterFactory factory);

	/**
	 * @param defaultLocale
	 *            The defaultLocale to set.
	 */
	void setDefaultLocale(Locale defaultLocale);

	/**
	 * Sets internal error page class. The class must be bookmarkable and must
	 * extend Page.
	 * 
	 * @param internalErrorPage
	 *            The internalErrorPage to set.
	 */
	void setInternalErrorPage(final Class internalErrorPage);

	/**
	 * Sets the page expired page class. The class must be bookmarkable and must
	 * extend Page.
	 * 
	 * @param pageExpiredErrorPage
	 *            The pageExpiredErrorPage to set.
	 */
	void setPageExpiredErrorPage(final Class pageExpiredErrorPage);

	/**
	 * @param contextKey
	 */
	void setServletContextKey(String contextKey);

}
