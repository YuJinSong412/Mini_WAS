package server;

import java.util.HashMap;
import java.util.Map;
import container.Servlet;

public class ServerLaunch {

  public static void main(String[] args) {

    Map<String, String> serlvetMap = new HashMap<String, String>();
    Map<String, Servlet> servletMap2= new HashMap<String, Servlet>();
    
    //압축풀기
    WarExtract warExtract = new WarExtract();
    warExtract.start();
 
    //xml분석
    XmlParsing xmlParsing = new XmlParsing();
    serlvetMap = xmlParsing.parseXml();
    
    //클래스 로딩
    WarLoader warLoader = new WarLoader();
    warLoader.start(serlvetMap);
    servletMap2 = warLoader.getExecuteServlet();
    
    //웹 서버 시작
    WebServer webServer = new WebServer();
    webServer.start(servletMap2);

  }


}
