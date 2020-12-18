package env;


public class Paths {
  
  private static String contextName = null;
  
  private static final String DEFAULT_RUN_PATH = ".\\webapps";
  
  private static final String PATH_SEPARATE = "\\";
  
  private static String XML_PATH = null; 
  
  private static String SERVLET_PATH = null;
  
  private static String STATIC_FILE_PATH = null;
  
  private static String WELCOME_FILE = "index.html";
  
  public static String getContextName() {
    
    return contextName;
  }  
  
  public static void setContextName(String contextName) {
    
    Paths.contextName = contextName;
    
    Paths.XML_PATH = DEFAULT_RUN_PATH + PATH_SEPARATE + contextName+ "\\WEB-INF\\web.xml";
    
    Paths.SERVLET_PATH =  DEFAULT_RUN_PATH + PATH_SEPARATE + contextName+ "\\WEB-INF\\classes\\";
    
    Paths.STATIC_FILE_PATH = DEFAULT_RUN_PATH + PATH_SEPARATE + contextName + "\\basic";
  }
  
  
  public static String getStaticFilePath() {
  
    return STATIC_FILE_PATH;
  }

  public static String getDefaultRunPath() {
  
    return DEFAULT_RUN_PATH;
  }

  public static String getPathSeparate() {
    
    return PATH_SEPARATE;
  }

  public static String getXMLPath() {
  
    return XML_PATH;
  }

  public static String getServletPath() {
  
    return SERVLET_PATH;
  }

  
  public static String getWELCOME_FILE() {
  
    return WELCOME_FILE;
  }

  
  public static void setWELCOME_FILE(String wELCOME_FILE) {
  
    WELCOME_FILE = wELCOME_FILE;
  }


}
