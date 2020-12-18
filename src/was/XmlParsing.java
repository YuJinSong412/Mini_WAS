package was;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import env.Paths;

public class XmlParsing {

  private final String SERVLET_NAME = "<servlet-name>";

  private final String SERVLET_CLASS = "<servlet-class>";

  private final String URL_PATTERN = "<url-pattern>";

  public Map<String, String> parseXml() {

    Map<String, String> servletMap = new HashMap<String, String>();

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
    return servletMap;

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

}
