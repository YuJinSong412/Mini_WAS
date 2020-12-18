package webcontainer;


public interface Servlet {

  public void init(ServletConfig servletConfig);

  public void init();

  public void service(HTTPServletRequest httpServletRequest,
      HTTPServletResponse httpServletResponse);

  public void destroy();

  public String getServletInfo();

  public ServletConfig getServletConfig();
}
