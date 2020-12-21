package was;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import env.Paths;
import webcontainer.Servlet;

public class WarLoader {

  private final String SERVLET_NAME = "<servlet-name>";

  private final String SERVLET_CLASS = "<servlet-class>";

  private final String URL_PATTERN = "<url-pattern>";

  private Map<String, String> servletMap;

  private Map<String, Servlet> executableServlets;

  private URLClassLoader urlClassLoader;

  public WarLoader() {

    servletMap = new HashMap<String, String>();

    executableServlets = new HashMap<String, Servlet>();

  }

  public Map<String, Servlet> getExecutableServlets() {

    return executableServlets;
  }

  public void setExecutableServlets(Map<String, Servlet> executableServlets) {

    this.executableServlets = executableServlets;
  }

  public void parseXml() { 

    File file = new File(Paths.getXMLPath());

    try {
      FileInputStream fis = new FileInputStream(file);

      BufferedReader br = new BufferedReader(new InputStreamReader(fis));

      String line = "";
      String oldLine = "";
      String servletClass = "";
      String servletName = "";

      while ((line = br.readLine()) != null) {
        if (oldLine.contains(SERVLET_NAME) && line.contains(SERVLET_CLASS)) {
          servletName = getServletName(oldLine);
          servletClass = getServletClass(line);

        } else if (oldLine.contains(SERVLET_NAME) && line.contains(URL_PATTERN)) {
          String servletName2 = getServletName(oldLine);

          if (servletName2.equals(servletName)) {
            String servletUrl = getUrlPattern(line);
            servletMap.put(servletUrl, servletClass);
          }
        }
        oldLine = line;
      }
      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getServletName(String oldLine) {

    int name_firstText = oldLine.indexOf(">") + 1;
    int name_lastText = oldLine.indexOf("</") - 1;

    String servletName = oldLine.substring(name_firstText, name_lastText + 1);

    return servletName;

  }

  private String getServletClass(String line) {

    int class_firstText = line.indexOf(">") + 1;
    int class_lastText = line.indexOf("</") - 1;

    String servletClass = line.substring(class_firstText, class_lastText + 1);

    return servletClass;
  }

  private String getUrlPattern(String line) {

    int class_firstText = line.indexOf(">") + 1;
    int class_lastText = line.indexOf("</") - 1;

    String servletUrl = line.substring(class_firstText, class_lastText + 1);


    return servletUrl;
  }


  public void initServlet() {

    File classRepo = new File(Paths.getServletPath());

    try {
      urlClassLoader = new URLClassLoader(new URL[] {classRepo.toURI().toURL()});

      for (String key : servletMap.keySet()) {
        Class<?> externalFromUrl = urlClassLoader.loadClass(servletMap.get(key));

        Servlet myServlet = (Servlet) externalFromUrl.newInstance();
        myServlet.init();

        executableServlets.put(key, myServlet);

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

}
