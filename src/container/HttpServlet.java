package container;


public abstract class HttpServlet implements Servlet {
  

  private static final String METHOD_GET = "GET";
  
  ServletConfig servletConfig;

  @Override
  public void init(ServletConfig config) {

    servletConfig = config;
    this.init();
  }
  

  public void init() {

    System.out.println("서블릿 init()초기화!");
  }


  public void destroy() {

  }

//  public String getInitParameter(String name) {
//
//    return getServletConfig().getInitParameter(name);
//  }

  @Override
  public void service(HTTPServletRequest httpServletRequest,
      HTTPServletResponse httpServletResponse) {

    System.out.println("service() 메소드는 HttpServlet 클래스 여기로");
    
    String method = httpServletRequest.getMethod();
    
    if(method.equals(METHOD_GET)) {
      System.out.println("doGet() 메소드 실행");
      
      doGet(httpServletRequest, httpServletResponse);
    }
    
  }

  public void doGet(HTTPServletRequest httpServletRequest,
      HTTPServletResponse httpServletResponse) {
    
  }
  
  
  
  @Override
  public String getServletInfo() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ServletConfig getServletConfig() {

    // TODO Auto-generated method stub
    return null;
  }

}
