package webserver;

import java.util.HashMap;
import java.util.Map;
import was.WarExtract;
import was.WarLoader;
import was.WarLoader;
import webcontainer.Servlet;

public class ServerLaunch {

  public static void main(String[] args) {

    Map<String, Servlet> executableServlets = new HashMap<String, Servlet>();

    // 압축풀기
    WarExtract warExtract = new WarExtract();
    warExtract.start();

    // xml분석, 클래스 로딩
    WarLoader warLoader = new WarLoader();
    warLoader.parseXml();
    warLoader.initServlet();


    executableServlets = warLoader.getExecutableServlets();
    // 웹 서버 시작
    WebServer webServer = new WebServer();
    webServer.start(executableServlets);
  }


}
