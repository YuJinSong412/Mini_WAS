package server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import container.Servlet;
import util.Paths;

public class WarLoader {
  
  private Map<String, Servlet> executeServlet;
  
  public WarLoader() {
    executeServlet= new HashMap<String, Servlet>();
  }

  public void start(Map<String, String> servletMap) {

    File classRepo =
        new File(Paths.getServletPath());
    System.out.println(classRepo.exists());

    try {
      URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {classRepo.toURI().toURL()});

      for (String key : servletMap.keySet()) {
        Class<?> externalFromUrl = urlClassLoader.loadClass(servletMap.get(key));
        Servlet my = (Servlet) externalFromUrl.newInstance();
        my.init();
        
        executeServlet.put(key, my);
        
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  
  public Map<String, Servlet> getExecuteServlet() {
  
    return executeServlet;
  }

  
  public void setExecuteServlet(Map<String, Servlet> executeServlet) {
  
    this.executeServlet = executeServlet;
  }
  
  

}
