package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import util.Paths;

public class XmlParsing {

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

      for (int i = 1; (line = br.readLine()) != null; i++) {
        if (oldLine.contains("<servlet-name>") && line.contains("<servlet-class>")) {
          int name_firstText = oldLine.indexOf(">") + 1;
          int name_lastText = oldLine.indexOf("</") - 1;

          int firstText = line.indexOf(">") + 1;
          int lastText = line.indexOf("</") - 1;

          servletName = oldLine.substring(name_firstText, name_lastText + 1);
          servletClass = line.substring(firstText, lastText + 1);

        } else if (oldLine.contains("<servlet-name>") && line.contains("<url-pattern>")) {
          int name_firstText = oldLine.indexOf(">") + 1;
          int name_lastText = oldLine.indexOf("</") - 1;

          String servletName2 = oldLine.substring(name_firstText, name_lastText + 1);

          if (servletName2.equals(servletName)) {
            int firstText = line.indexOf(">") + 1;
            int lastText = line.indexOf("</") - 1;
            String servletUrl = line.substring(firstText, lastText + 1);

            servletMap.put(servletUrl, servletClass);
          }
        }
        oldLine = line;
      }
      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    for(String key : servletMap.keySet()) {
      System.out.println("aa" + key + "  bb: " + servletMap.get(key));
    }
    
     return servletMap;

  }

}
